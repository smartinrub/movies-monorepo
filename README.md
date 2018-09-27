# movies-monorepo
## Movie Service API
- GET /movies-api/movie_name
- GET /movies-api/movie/movie_id
## Video Service API
- GET /video-api/movie_id?title=movie_name
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
