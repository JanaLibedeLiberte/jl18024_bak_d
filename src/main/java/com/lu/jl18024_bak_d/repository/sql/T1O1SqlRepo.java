package com.lu.jl18024_bak_d.repository.sql;

import com.lu.jl18024_bak_d.model.Test1Object1;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface T1O1SqlRepo extends CrudRepository<Test1Object1,Long> {
}
