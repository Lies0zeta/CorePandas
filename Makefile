all: run

run: build
	docker run -it pandas_builder:latest /bin/bash

build:
	docker build -t pandas_builder .

	
