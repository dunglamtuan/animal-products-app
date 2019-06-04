package sk.garwan.animal.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sk.garwan.animal.shop.model.MinimalProduct;

@Repository
public interface MinimalProductRepository extends
    PagingAndSortingRepository<MinimalProduct, Integer> {

  @Override
  Page<MinimalProduct> findAll(Pageable pageable);
}
