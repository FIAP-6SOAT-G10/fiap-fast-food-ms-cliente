include .env

build:
	mvn compile

unit-test:
	mvn test -P unit-test

system-test:
	mvn test -P bdd-test
