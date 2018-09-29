# movies-monorepo
## Orchestrator Service API
- POST :8081/movies/review
```json
{
	"movieId":"2",
	"comment":"excellent",
	"rate":"4"
}
```
- GET :8081/movies/{movie_name}
- GET :8081/movies/movie/{movie_id}
## Movie Service API
- GET :8082/{movie_name}
- GET :8082/movie/{movie_id}
## Video Service API
- GET :8083/{movie_id}?title={movie_name}
## Reviews Service API
- POST :8084/
```json
{
	"movieId":"2",
	"comment":"excellent",
	"rate":"4"
}
```
- GET /{movie_id}
## Redis
### Installation
```sh
sudo apt update             
sudo apt install redis-server
```
### Console
```sh
redis-cli 
```
### Logs
```sh
redis-cli monitor
```
### Restart service
```sh
sudo systemctl restart redis
```
## MongoDB
### Installation
[Install MongoDB CE](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/)
### Console
```sh
mongo
```
### Example
```sh
use movies
db.reviews.find({})
```
## Eureka Dashboard
```
http://localhost:8761/
```
## How to use
1. Make sure API keys for Youtube and The Movie db are set up.
2. Run all services starting with eureka-service, otherwise client services will 
not be able to register in Eureka Service.
## Sample Usage
- GET call to retrieve all movies with matching movie title
```
GET http://localhost:8081/movies/scary%20movie
```
- GET movie with specific movie id
```
http://localhost:8081/movies/movie/4256
```
- Add review by sending a json with movie id
```
POST http://localhost:8081/movies/movie/review
```
body:
```json
{
	"movieId":"4256",
	"comment":"it's okay",
	"rate":"3"
}
```
- GET movie one more time to see the added review
```
http://localhost:8081/movies/movie/4256
```

