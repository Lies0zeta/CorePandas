all: run

run: build
	docker run -it pandas_builder:latest 

build:
	docker build -t pandas_builder .

clean:
	maven clean
