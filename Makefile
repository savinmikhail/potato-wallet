.PHONY: ps
ps:
	sudo docker-compose ps

.PHONY: start
start:
	sudo docker-compose build && sudo docker-compose up -d

.PHONY: down
down:
	sudo docker-compose down

.PHONY: logs
logs:
	sudo docker-compose logs

.PHONY: db
db:
	sudo docker-compose exec postgre bash