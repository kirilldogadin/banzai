# banzai
test tasks

****
For Start app:
Проект обычный springBoot2 application. 
Внешняя зависимость:

1. Postgresql установить/инициалзировать
(как вариант докер )

`docker run --rm --name pg-docker -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres`

в resources лежит sql создания таблицы, но  в конфиге ddl-auto = update
так что применять его не обязательно

2. Клонировать

`git clone https://github.com/kirilldogadin/banzai`

3. Cобрать jar 

`./gradlew bootJar`

сохранит по умолчанию  в build/libs/banzai-0.1.jar

4. Запуск 

`java -Dspring.config.location=target/application.properties -jar target/build/libs/banzai-0.1.jar`

Если конфиг находится в той же dir, что и jar

	java -jar banzai-0.0.1.jar

