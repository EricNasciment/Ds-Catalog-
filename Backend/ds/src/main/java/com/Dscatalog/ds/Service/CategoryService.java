package com.Dscatalog.ds.Service;

import com.Dscatalog.ds.Entities.Category;
import com.Dscatalog.ds.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }


}
