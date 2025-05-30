package com.codewithmosh.store.products;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts(Byte categoryId) {

        List<Product> products;

        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }

        return products
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDto getProduct(Long id) {
        var product = productRepository
                .findById(id)
                .orElseThrow(ProductNotFoundException::new);

        return productMapper.toDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        var product = productMapper.toEntity(productDto);

        var category = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        product.setCategory(category);
        productRepository.save(product);

        return  productMapper.toDto(product);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        var product = productRepository
                .findById(id)
                .orElseThrow(ProductNotFoundException::new);

        var category = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        productMapper.update(productDto, product);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());

        return productMapper.toDto(product);
    }

    public void deleteProduct(Long id) {
        var product = productRepository
                .findById(id)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.deleteById(id);
    }

}
