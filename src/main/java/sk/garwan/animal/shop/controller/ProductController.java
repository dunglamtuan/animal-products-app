package sk.garwan.animal.shop.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.garwan.animal.shop.model.MinimalProduct;
import sk.garwan.animal.shop.model.Product;
import sk.garwan.animal.shop.service.ProductService;

@RestController
@AllArgsConstructor
public class ProductController {

  private final ProductService service;

  @ApiOperation(value = "${ProductController.handleProductsGetRequest}")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of products")})
  @GetMapping(value = "/products")
  public ResponseEntity<List<MinimalProduct>> handleProductsGetRequest(
    @RequestParam("page") int page,
    @RequestParam("pSize") Integer pageSize,
    @RequestParam(value = "sorting", required = false) String sorting) {

    List<MinimalProduct> resultProducts = service.findProductsWithPageAndSort(page, pageSize, sorting);
    return new ResponseEntity<>(resultProducts, HttpStatus.OK);
  }

  @ApiOperation(value = "${ProductController.handleProductByIdGetRequest}")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Product"),
      @ApiResponse(code = 404, message = "None product was found")})
  @GetMapping(value = "/products/{id}")
  public ResponseEntity<Product> handleProductByIdGetRequest(@PathVariable("id") Integer id) {

    Optional<Product> product = service.findById(id);
    return product.map(product1 -> new ResponseEntity<>(product1, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }

}
