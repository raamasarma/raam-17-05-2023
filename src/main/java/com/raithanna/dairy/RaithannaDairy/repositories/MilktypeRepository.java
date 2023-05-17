package com.raithanna.dairy.RaithannaDairy.repositories;

import com.azure.spring.data.cosmos.repository.Query;
import com.raithanna.dairy.RaithannaDairy.models.milktype;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MilktypeRepository extends CrudRepository<milktype,Integer> {
    @Query("select * from milktype where code=?1")
    milktype findByCode(String code);
}
