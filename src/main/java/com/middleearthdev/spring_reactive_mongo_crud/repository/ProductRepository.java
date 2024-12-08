package com.middleearthdev.spring_reactive_mongo_crud.repository;

import com.middleearthdev.spring_reactive_mongo_crud.dto.ProductDto;
import com.middleearthdev.spring_reactive_mongo_crud.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findByPriceBetween(Range<Double> priceRange);
}
