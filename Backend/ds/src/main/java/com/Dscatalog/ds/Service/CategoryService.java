package com.Dscatalog.ds.Service;

import com.Dscatalog.ds.Dto.CategoryDto;
import com.Dscatalog.ds.Entities.Category;
import com.Dscatalog.ds.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<CategoryDto> findAll(){
        List<Category> list =  categoryRepository.findAll();
        return list.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());

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
        Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada"));
        return new CategoryDto(entity);
    }



}
