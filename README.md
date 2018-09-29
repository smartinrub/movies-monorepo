# movies-monorepo
## Movie Service API
- GET /movies-api/movie_name
- GET /movies-api/movie/movie_id
## Video Service API
- GET /video-api/movie_id?title=movie_name
## Reviews Service API
- POST /
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
