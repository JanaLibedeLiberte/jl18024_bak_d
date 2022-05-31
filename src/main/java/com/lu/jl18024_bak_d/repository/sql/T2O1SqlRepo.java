package com.lu.jl18024_bak_d.repository.sql;

import com.lu.jl18024_bak_d.model.Test2Object1;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface T2O1SqlRepo extends CrudRepository<Test2Object1,Long> {
}
