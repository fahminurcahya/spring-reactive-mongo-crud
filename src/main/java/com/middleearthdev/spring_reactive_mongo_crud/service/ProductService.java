package com.middleearthdev.spring_reactive_mongo_crud.service;

import com.middleearthdev.spring_reactive_mongo_crud.dto.ProductDto;
import com.middleearthdev.spring_reactive_mongo_crud.repository.ProductRepository;
import com.middleearthdev.spring_reactive_mongo_crud.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getProducts() {
        return productRepository.findAll()
                .map(MapUtils::entityToDto);
    }

    public Mono<ProductDto> getProduct(String id) {
        return productRepository.findById(id)
                .map(MapUtils::entityToDto);
    }

    public Flux<ProductDto> getProductInRange(double min, double max) {
        return productRepository.findByPriceBetween(Range.closed(min,max))
                .map(MapUtils::entityToDto);
    }

    public Mono<ProductDto> createProduct(Mono<ProductDto> productDto) {
        return productDto.map(MapUtils::dtoToEntity)
                .flatMap(productRepository::insert)
                .map(MapUtils::entityToDto);
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDto, String id) {
        return productRepository.findById(id)
                .flatMap(product ->  productDto.map(MapUtils::dtoToEntity)
                            .doOnNext(p->product.setId(id)))
                .flatMap(productRepository::save)
                .map(MapUtils::entityToDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }

}
