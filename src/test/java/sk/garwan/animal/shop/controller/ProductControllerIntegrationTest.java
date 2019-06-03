package sk.garwan.animal.shop.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sk.garwan.animal.shop.model.AnimalCategory;
import sk.garwan.animal.shop.model.MinimalProduct;
import sk.garwan.animal.shop.model.Product;
import sk.garwan.animal.shop.repository.ProductRepository;
import sk.garwan.animal.shop.service.SimpleProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private SimpleProductService productService;

  @Autowired
  EntityManager entityManager;

  @Autowired
  ProductRepository productRepository;

  @Before
  public void insertSomeProductData() {
    productRepository.saveAll(createTestProducts());
  }

  @Transactional
  @After
  public void clearProductTable() {
    productRepository.deleteAll();
  }

  @Test
  public void basicEmptyEndpointTest() {

    // execute
    String productUrl = "http://localhost:" + port + "/products?page=0&pSize=1";
    ResponseEntity<String> response = restTemplate.getForEntity(productUrl, String.class);

    // verify
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void filterWithSortingBasicForProduct1Test() {

    // execute
    String productUrl = "http://localhost:" + port + "/products?page=0&pSize=1";
    List<MinimalProduct> minimalProducts = restTemplate
        .exchange(productUrl, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<MinimalProduct>>() {
            }).getBody();

    //verify
    assertThat(minimalProducts).isNotNull();
    assertThat(minimalProducts).isNotEmpty();
    assertThat(minimalProducts.size()).isEqualTo(1);

    MinimalProduct minimalProduct = minimalProducts.get(0);
    assertThat(minimalProduct.getPrice()).isEqualByComparingTo(BigDecimal.ONE);
    assertThat(minimalProduct.getName()).isEqualToIgnoringCase("testProduct1");
    assertThat(minimalProduct.getAnimalCategory()).isEqualByComparingTo(AnimalCategory.CATS);
  }

  @Test
  public void filterWithSortingBasicForProduct2Test() {

    // execute
    String productUrl = "http://localhost:" + port + "/products?page=1&pSize=1";
    List<MinimalProduct> minimalProducts = restTemplate
        .exchange(productUrl, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<MinimalProduct>>() {
            }).getBody();

    //verify
    assertThat(minimalProducts).isNotNull();
    assertThat(minimalProducts).isNotEmpty();
    assertThat(minimalProducts.size()).isEqualTo(1);

    MinimalProduct minimalProduct = minimalProducts.get(0);
    assertThat(minimalProduct.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(2.0));
    assertThat(minimalProduct.getName()).isEqualToIgnoringCase("testProduct2");
    assertThat(minimalProduct.getAnimalCategory()).isEqualByComparingTo(AnimalCategory.DOGS);
  }

  @Test
  public void allProductTest() {

    // execute
    String productUrl = "http://localhost:" + port + "/products?page=0&pSize=5";
    List<MinimalProduct> minimalProducts = restTemplate
        .exchange(productUrl, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<MinimalProduct>>() {
            }).getBody();

    //verify
    assertThat(minimalProducts).isNotNull();
    assertThat(minimalProducts).isNotEmpty();
    assertThat(minimalProducts.size()).isEqualTo(4);
  }

  @Test
  public void filterWithDescSortingTest() {

    // execute
    String productUrl = "http://localhost:" + port + "/products?page=0&pSize=1&sorting=desc";
    List<MinimalProduct> minimalProducts = restTemplate
        .exchange(productUrl, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<MinimalProduct>>() {
            }).getBody();

    //verify
    assertThat(minimalProducts).isNotNull();
    assertThat(minimalProducts).isNotEmpty();
    assertThat(minimalProducts.size()).isEqualTo(1);

    MinimalProduct minimalProduct = minimalProducts.get(0);
    assertThat(minimalProduct.getPrice()).isEqualByComparingTo(BigDecimal.TEN);
    assertThat(minimalProduct.getName()).isEqualToIgnoringCase("testProduct4");
    assertThat(minimalProduct.getAnimalCategory()).isEqualByComparingTo(AnimalCategory.OTHER);
  }

  private List<Product> createTestProducts() {
    Product product1 = Product.builder()
        .animalCategory(AnimalCategory.CATS)
        .name("testProduct1")
        .description("testProduct1 description")
        .price(BigDecimal.ONE)
        .build();
    Product product2 = Product.builder()
        .animalCategory(AnimalCategory.DOGS)
        .name("testProduct2")
        .description("testProduct2 description")
        .price(BigDecimal.valueOf(2.0))
        .build();
    Product product3 = Product.builder()
        .animalCategory(AnimalCategory.CATS)
        .name("testProduct3")
        .description("testProduct3 description")
        .price(BigDecimal.valueOf(3.0))
        .build();
    Product product4 = Product.builder()
        .animalCategory(AnimalCategory.OTHER)
        .name("testProduct4")
        .description("testProduct4 description")
        .price(BigDecimal.TEN)
        .build();
    List<Product> results = new ArrayList<>();
    Collections.addAll(results, product1, product2, product3, product4);
    return results;
  }

}
