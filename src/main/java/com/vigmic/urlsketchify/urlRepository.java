package com.vigmic.urlsketchify;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface urlRepository extends MongoRepository<Url, Long> {
    Url findById(Long id);
    List<Url> findAllByIpAddress(String ipAddress);
}
