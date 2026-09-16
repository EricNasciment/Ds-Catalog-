package com.Dscatalog.ds.Service;


import com.Dscatalog.ds.Dto.CategoryDto;
import com.Dscatalog.ds.Dto.ProductDto;
import com.Dscatalog.ds.Entities.Category;
import com.Dscatalog.ds.Entities.Product;
import com.Dscatalog.ds.Repositories.CategoryRepository;
import com.Dscatalog.ds.Repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
  private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

@Transactional(readOnly = true)
public Page<ProductDto> findPagedAll(Pageable pageable){
    Page<Product> list = productRepository.findAll(pageable);
    return list.map(x -> new ProductDto(x));

}

@Transactional(readOnly = true)
public ProductDto findById(Long id){
    Optional<Product>  obj = productRepository.findById(id);
   Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
    return new ProductDto(entity,entity.getCategories());
}

@Transactional
public ProductDto insert(ProductDto dto){
       Product  entity = new Product();
       copyDtoToEntity(dto,entity);
       entity = productRepository.save(entity);
       return new ProductDto(entity);
}

@Transactional
public ProductDto update(ProductDto dto, Long id){

    try {
        Product entity = productRepository.getReferenceById(id);
        copyDtoToEntity(dto,entity);
        entity = productRepository.save(entity);
        return new ProductDto(entity);
    }
    catch (EntityNotFoundException e){
        throw new  ResourceNotFoundException("Id não encontrado:" + " " + id);
    }
}

@Transactional(propagation = Propagation.SUPPORTS)
public void delete(Long id){
    if(!productRepository.existsById(id)){
        throw new ResourceNotFoundException("Id não existe");
    }
    try{
        productRepository.deleteById(id);
    }
    catch (DataIntegrityViolationException e){
        throw new DataBaseException("Entidade não pode ser deletada, Falha de Integridade no banco");
    }

}

   private void copyDtoToEntity(ProductDto dto,Product entity){
       entity.setName(dto.getName());
       entity.setDescription(dto.getDescription());
       entity.setPrice(dto.getPrice());
       entity.setImgUrl(dto.getImgUrl());
       entity.setDate(dto.getDate());

       entity.getCategories().clear();
       for(CategoryDto catDto : dto.getCategories()){
           Category category = categoryRepository.getOne(catDto.getId());
           entity.getCategories().add(category);
       }
   }

 }
