all: run

run: build
	docker run -it pandas_builder:latest /bin/bash

build:
	docker build -t pandas_builder .

publish: build
	docker push davtyannarek/corepandas:pandas_builder
	
