package com.Dscatalog.ds.Service;

import com.Dscatalog.ds.Dto.CategoryDto;
import com.Dscatalog.ds.Entities.Category;
import com.Dscatalog.ds.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<CategoryDto> findAll(){
        List<Category> list =  categoryRepository.findAll();
        List<CategoryDto> listDto = new ArrayList<>();
        return list.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());
       /* for(Category cat: list){  outro exemplo com for each acima sera usada expressao lambda com map.
            listDto.add(new CategoryDto(cat));
        }
        return listDto;*/
    }


}
