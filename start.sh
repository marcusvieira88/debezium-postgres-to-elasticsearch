# create indices with mappings
curl -i -X PUT -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:9200/products -d @debezium-connections/mappings/products.json
# setup connections
curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d @debezium-connections/tables/es-sink-products.json
curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d @debezium-connections/sources/source.json