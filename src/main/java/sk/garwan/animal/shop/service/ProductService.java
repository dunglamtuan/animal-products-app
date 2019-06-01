package sk.garwan.animal.shop.service;

import java.util.List;
import java.util.Optional;
import sk.garwan.animal.shop.model.MinimalProduct;
import sk.garwan.animal.shop.model.Product;

public interface ProductService {

  Optional<Product> findById(Integer id);

  List<MinimalProduct> findProductsWithPageAndSort(int page, int pageSize, String asc);

}
