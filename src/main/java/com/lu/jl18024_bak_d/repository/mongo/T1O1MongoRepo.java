package com.lu.jl18024_bak_d.repository.mongo;

import com.lu.jl18024_bak_d.model.Test1Object1;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface T1O1MongoRepo extends MongoRepository<Test1Object1,Long> {
}
