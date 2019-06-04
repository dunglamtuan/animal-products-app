## Endpoints
  * ### Public endpoints
    * #### List of product (``GET /products?page=0&pSize=1&sorting=desc``)
      * default for ``sorting`` is ``asc``; accepted values: anything or `desc` 
      
    * #### Product's detail (``GET /products/{id}``)
    
    * #### User registration (``POST /users/reg``)
      * Request body (accepted ``application/json``)
        ```
        {
          "username": "user1",
          "password": "1111",
          "email": "email1"
        }
        ```
  
  * ### Authenticated endpoints
    * #### User login (``GET /users/login?username={username}&password={password}``)
      * Returns access ``token`` for next endpoints
      
    * #### Get orders by user (``GET /orders/auth?username={username}``)
      * Header ``Authorization`` with value ``Bearer {token}``
      
    * #### Make a new order by user (``POST /orders/auth?username={username}``)
      * Header ``Authorization`` with value ``Bearer {token}``
      * Request body (accepted ``application/json``) - array of products
        ```
        [
          {
            "productName": "product_by_endpoint",
            "productPrice": 66.66,
            "amount": 2
          }
        ]
        ```
    
    * #### Admin login (``GET /admin/login?username={username}&password={password}``)
      * Returns access ``token`` for next endpoints
      
    * #### Getting all orders (``GET /admin/orders?username=user2&page=0&pSize=1``)
      * Header ``Authorization`` with value ``Bearer {token}``
      
    * #### Create a new product (``POST /admin/products?username={username}``)
      * Header ``Authorization`` with value ``Bearer {token}``
      * Request body (accepted ``application/json``) - Product
        ```
        {
          "name": "product_by_endpoint",
            "animalCategory": "OTHER",
            "price": 66.66,
            "description": "product_by_endpoint has price of 66.66",
            "gallery": [
                "link66.66",
                "link66.66"
            ]
        }
        ```