# Rest API with CDC (Change data capture) Debezium to propagate PostgreSQL changes to ElasticSearch

### Architecture

```
          +---------------v------------------+
          |                                  |
          |           REST API               |
          |                                  |
          +---------------+------------------+
                          |
                          |
                          |
                          |
                   +------v------+
                   |             |
                   |  PostgreSQL |
                   |             |
                   +------+------+
                          |
                          |
                          |
          +---------------v------------------+
          |                                  |
          |           Kafka Connect          |
          |    (Debezium, ES connectors)     |
          |                                  |
          +---------------+------------------+
                          |
                          |
                          |
                          |
                  +-------v--------+
                  |                |
                  | Elasticsearch  |
                  |                |
                  +----------------+
```
Use Docker Compose to create the following components:

* PostgreSQL
* Kafka
  * ZooKeeper
  * Kafka Broker
  * Kafka Connect with [Debezium](http://debezium.io/) and [Elasticsearch](https://github.com/confluentinc/kafka-connect-elasticsearch) Connectors
* Elasticsearch

### Run

```shell
docker-compose up --build
```
After create the components through docker-compose we need to execute the start.sh file, it will:
- Create the Product table on ElasticSearch
- Create a Kafka topic on Debezium to receive the data from Postgres and propagate it on ElasticSearch.
- Configure a the Product Postgres table as source into Debezium

```shell
./start.sh
```

Start the REST API built in Spring and Java11:

```shell
mvn spring-boot:run
```

### Test

Save a product:
```shell
curl -X POST \
  http://localhost:8080/products \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: c387f518-d1f8-45dc-af40-898c75791bca' \
  -H 'cache-control: no-cache' \
  -d '{
	"description": "product1",
	"units":1000
}'
```

Check if it was saved on Postgres:
```shell
curl -X GET \
  http://localhost:8080/products \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 581387df-6346-4825-a906-af555e5520d6' \
  -H 'cache-control: no-cache'
```

Check the propagated data on ElasticSearch:
```shell
curl http://localhost:9200/products/_search?q=id:1
```

If you update or delete the item on Postgres it will be propagate on ElasticSearch too: 
```shell
curl -X DELETE \
  http://localhost:8080/products/1 \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 079ad59b-2bb3-42fd-8cbb-883759241113' \
  -H 'cache-control: no-cache'
```

## Reference 

https://github.com/YegorZaremba/sync-postgresql-with-elasticsearch-example
