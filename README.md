# animal-products-app
Spring boot application for eshop with animals' products

# Development environment setup
Project uses dependency for lombok, IDE must have lombok support to be able to make changes

Docker is used for databases

# Things need to be prepared
Run postgres service in ``docker-compose.yml`` - It automatically creates 2 databases based on environments variables ``POSTGRES_DB`` and ``TEST_POSTGRES_DB``

``TEST_POSTGRES_DB`` is needed for integration tests