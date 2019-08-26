# banzai
test tasks

****
For Start app:
Проект обычный springBoot2 application. 
Внешняя зависимость:
0. Postgresql установить/инициалзировать
(как вариант докер )

docker run --rm --name pg-docker -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres
(настройки портов и прочего в конфиге)
в resources лежит sql создания таблицы, НО  в конфиге ddl-auto = update
так что применять его не обязательно
1. git clone https://github.com/kirilldogadin/banzai
2. собрать jar 
./gradlew bootJar
по умолчанию  в build/libs/banzai-0.1.jar
java -Dspring.config.location=target/application.properties -jar target/build/libs/banzai-0.1.jar
Если конфиг находится в той же dir, что и jar
	java -jar banzai-0.0.1.jar

