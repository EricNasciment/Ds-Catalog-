package com.Dscatalog.ds.Resources;

import com.Dscatalog.ds.Dto.CategoryDto;
import com.Dscatalog.ds.Service.CategoryService;
import jakarta.servlet.Servlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResources {

    @Autowired
      private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll(){
        List<CategoryDto> list = categoryService.findAll();
        return ResponseEntity.ok().body(list);
    }


    @GetMapping( value = "/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id){
        CategoryDto dto = categoryService.FindById(id);
        return ResponseEntity.ok().body(dto);
    }


    @PostMapping
    public ResponseEntity<CategoryDto> insert(@RequestBody CategoryDto dto){
        dto = categoryService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> update( @PathVariable Long id,  @RequestBody CategoryDto dto){
       dto = categoryService.update(id,dto);
     return ResponseEntity.ok().body(dto);
    }
}
