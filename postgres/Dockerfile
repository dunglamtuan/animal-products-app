FROM postgres:11

COPY create-test-db.sh /docker-entrypoint-initdb.d/create-test-db.sh

RUN chmod +x /docker-entrypoint-initdb.d/create-test-db.sh