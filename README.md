# movies-monorepo
## Orchestrator API
- POST :8081/movies/review
```json
{
	"movieId":"2",
	"comment":"excellent",
	"rate":"4"
}
```
- GET :8081/movies/{movie_name}
## Movie Service API
- GET :8082/movie_name
- GET :8082/movie/movie_id
## Video Service API
- GET :8083/movie_id?title=movie_name
## Reviews Service API
- POST :8084/
```json
{
	"movieId":"2",
	"comment":"excellent",
	"rate":"4"
}
```
- GET /movie_id
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
