package sk.garwan.animal.shop.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.List;
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
import org.springframework.test.context.junit4.SpringRunner;
import sk.garwan.animal.shop.model.MinimalProduct;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void basicEmptyEndpointTest() {

    // execute
    String productUrl = "http://localhost:" + port + "/products?page=0&pSize=1";
    Response response = RestAssured.get(productUrl);

    // verify
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
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
    assertThat(minimalProducts.get(0).getPrice()).isEqualByComparingTo(BigDecimal.valueOf(1.1));
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
    assertThat(minimalProducts.get(0).getPrice()).isEqualByComparingTo(BigDecimal.valueOf(2.2));
  }

  @Test
  public void filterWithDescSortingTest() {

    // execute
    String productUrl = "http://localhost:" + port + "/products?page=0&pSize=10&sorting=desc";
    List<MinimalProduct> minimalProducts = restTemplate
        .exchange(productUrl, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<MinimalProduct>>() {
            }).getBody();

    //verify
    assertThat(minimalProducts).isNotNull();
    assertThat(minimalProducts).isNotEmpty();
    assertThat(minimalProducts.size()).isEqualTo(5);
    assertThat(minimalProducts.get(0).getPrice()).isEqualByComparingTo(BigDecimal.valueOf(5.5));
  }

}
