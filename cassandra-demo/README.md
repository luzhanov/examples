
CASSANDRA SETUP
1. Run Cassandra Docker container docker `docker run --name cassandra-cnt -p 9142:9042 -p 9160:9160 -d cassandra:2.1`
1. Connect via cqlsh `docker run -it --link cassandra-cnt:cassandra --rm cassandra cqlsh cassandra 0`
1. Stop Cassandra `docker stop cassandra-cnt` 