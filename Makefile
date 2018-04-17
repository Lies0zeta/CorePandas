all: run

run: build
	docker run -it dht_builder:latest 

build:
	docker build -t pandas_builder .

clean:
	maven clean
