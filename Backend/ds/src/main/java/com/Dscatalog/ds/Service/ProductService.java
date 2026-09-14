package com.Dscatalog.ds.Service;


import com.Dscatalog.ds.Dto.ProductDto;
import com.Dscatalog.ds.Entities.Category;
import com.Dscatalog.ds.Entities.Product;
import com.Dscatalog.ds.Repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
  private ProductRepository productRepository;

@Transactional(readOnly = true)
public Page<ProductDto> findPagedAll(PageRequest pageRequet){
    Page<Product> list = productRepository.findAll(pageRequet);
   return list.map(x -> new ProductDto(x));
}

@Transactional(readOnly = true)
public ProductDto findById(Long id){
    Optional<Product>  obj = productRepository.findById(id);
   Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
    return new ProductDto(entity);
}

@Transactional
public ProductDto insert(ProductDto dto){
       Product  entity = new Product();
       entity.setName(dto.getName());
       entity.setDescription(dto.getDescription());
       entity.setPrice(dto.getPrice());
       entity.setImgUrl(dto.getImgUrl());
       entity.setDate(dto.getDate());
       entity = productRepository.save(entity);
       return new ProductDto(entity);
}

@Transactional
public ProductDto update(ProductDto dto, Long id){

    try {
        Product entity = productRepository.getReferenceById(id);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());
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
}
