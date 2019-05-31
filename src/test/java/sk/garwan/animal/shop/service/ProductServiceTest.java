package sk.garwan.animal.shop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sk.garwan.animal.shop.model.AnimalCategory;
import sk.garwan.animal.shop.model.MinimalProduct;
import sk.garwan.animal.shop.model.Product;
import sk.garwan.animal.shop.repository.MinimalProductRepository;
import sk.garwan.animal.shop.repository.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private MinimalProductRepository minimalProductRepository;

  private ProductService productService;

  @Before
  public void setup() {
    Product productById = makeTestProduct();
    when(productRepository.findById(anyInt())).thenReturn(Optional.of(productById));

    List<MinimalProduct> minimalProducts = makeTestMinimalProducts();
    when(minimalProductRepository.findAll(any(Pageable.class)))
        .thenReturn(new PageImpl<>(minimalProducts));

    productService = new SimpleProductService(productRepository, minimalProductRepository);
  }

  @Test
  public void getByProductIdTest() {
    // execute
    Product productByID = productService.findById(1).get();

    // verify
    assertThat(productByID).isNotNull();
    assertThat(productByID.getId()).isEqualTo(1);
    assertThat(productByID.getName()).isEqualToIgnoringCase("name");
    assertThat(productByID.getPrice()).isEqualByComparingTo(BigDecimal.ONE);
    assertThat(productByID.getDescription()).isEqualToIgnoringCase("basic description");
  }

  @Test
  public void getProductsTest() {
    //execute
    List<MinimalProduct> products = productService.findProductsWithPageAndSort(0, 2);

    //verify
    assertThat(products.size()).isEqualTo(1);
    assertThat(products.get(0).getId()).isEqualTo(1);
    assertThat(products.get(0).getName()).isEqualToIgnoringCase("name");
    assertThat(products.get(0).getPrice()).isEqualByComparingTo(BigDecimal.ONE);
  }

  private Product makeTestProduct() {
    return Product.builder()
        .id(1)
        .name("name")
        .animalCategory(AnimalCategory.CATS)
        .price(BigDecimal.ONE)
        .description("basic description")
        .gallery(new String[]{"link1", "ink1"})
        .build();
  }

  private List<MinimalProduct> makeTestMinimalProducts() {
    MinimalProduct mini = MinimalProduct.builder()
        .id(1)
        .name("name")
        .animalCategory(AnimalCategory.CATS)
        .price(BigDecimal.ONE)
        .build();

    return Collections.singletonList(mini);
  }

}
