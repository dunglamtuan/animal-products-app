# animal-products-app
Spring boot application for eshop with animals' products

## Development environment setup
Project uses dependency for lombok, IDE must have lombok support to be able to make changes

Docker is used for databases

## Things need to be prepared
Run postgres service in ``docker-compose.yml`` - It automatically creates 2 databases based on environments variables ``POSTGRES_DB`` and ``TEST_POSTGRES_DB``

``TEST_POSTGRES_DB`` is needed for integration tests

## Scenario to test endpoints
App is running on ``https://animal-products.herokuapp.com`` (Use it as hostname for ``POSTMAN``)\
Prepared postman requests are in ``/postman`` (import collection to postman)\
1 admin user is created by default ``admin/admin``\
All available endpoint is shown in ``/documentation``
  * Login as admin, and use returned token for auth endpoints
  * Create new products by endpoint
  * Test endpoints