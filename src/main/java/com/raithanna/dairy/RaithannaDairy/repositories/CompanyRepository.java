package com.raithanna.dairy.RaithannaDairy.repositories;

import com.azure.spring.data.cosmos.repository.Query;
import com.raithanna.dairy.RaithannaDairy.models.company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository<company, Integer> {
    List<company> findByOrderByIdDesc();
    @Query("select * from company where org_code=?1")
    company findByOrgCode(String orgCode);
}
