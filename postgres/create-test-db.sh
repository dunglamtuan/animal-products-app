#!/bin/bash
set -e

POSTGRES="psql --username ${POSTGRES_USER}"

echo "Creating database: ${TEST_POSTGRES_DB}"

${POSTGRES} << EOSQL
CREATE DATABASE "${TEST_POSTGRES_DB}" OWNER ${POSTGRES_USER};
EOSQL