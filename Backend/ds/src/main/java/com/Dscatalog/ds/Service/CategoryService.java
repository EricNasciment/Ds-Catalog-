package com.Dscatalog.ds.Service;

import com.Dscatalog.ds.Dto.CategoryDto;
import com.Dscatalog.ds.Entities.Category;
import com.Dscatalog.ds.Repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public Page<CategoryDto> findAllPaged(Pageable pageable){
        Page<Category> list =  categoryRepository.findAll(pageable);
        return list.map(x -> new CategoryDto(x));

       /*  outro exemplo com for each acima sera usada expressao lambda com map.
        List<CategoryDto> listDto = new ArrayList<>();
        for(Category cat: list){
            listDto.add(new CategoryDto(cat));
        }
        return listDto;*/
    }

    @Transactional(readOnly = true)
    public CategoryDto FindById(Long id){
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
        return new CategoryDto(entity);
    }


    @Transactional
    public CategoryDto insert(CategoryDto dto){
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDto(entity);
    }

    @Transactional
    public CategoryDto update(Long id,CategoryDto dto){
        try{
        Category entity = categoryRepository.getReferenceById(id);
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDto(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado:" + " " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
     if(!categoryRepository.existsById(id)){
        throw new ResourceNotFoundException("Id não encontrado:" + " " + id);
     }
     try{
         categoryRepository.deleteById(id);
     }
     catch (DataIntegrityViolationException e){
         throw new DataBaseException("Entidade não pode ser deletada,Falha de Integridade no banco");
     }
    }
}
