package sk.garwan.animal.shop.service;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sk.garwan.animal.shop.model.MinimalProduct;
import sk.garwan.animal.shop.model.Product;
import sk.garwan.animal.shop.repository.MinimalProductRepository;
import sk.garwan.animal.shop.repository.ProductRepository;

@Service
@AllArgsConstructor
public class SimpleProductService implements ProductService {

  private static final String DESC_SORTING_STRATEGY = "desc";

  private final ProductRepository productRepository;
  private final MinimalProductRepository minimalProductRepository;

  @Override
  public Optional<Product> findById(Integer id) {
    return productRepository.findById(id);
  }

  @Override
  public List<MinimalProduct> findProductsWithPageAndSort(int page, int pageSize, String asc) {

    Pageable pageable;
    if (asc == null || !asc.equalsIgnoreCase(DESC_SORTING_STRATEGY)) {
      pageable = PageRequest.of(page, pageSize, Sort.by("price").ascending());
    } else {
      pageable = PageRequest.of(page, pageSize, Sort.by("price").descending());
    }

    return minimalProductRepository.findAll(pageable).getContent();
  }
}
