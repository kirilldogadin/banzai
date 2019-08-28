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

	java -jar banzai-0.1.jar



**Небольшое описание приложения**

Наличие Unit тестов

независимые тесты от базы (H2)

Тесты работы с файловой системой

Hikari Pool для бд (по умолчанию от SpringBoot)

project Reactor

Описание классов в /** */ комментариев классов с суффиком *Impl

Можно задать настройки (все настройки приложения в application.properties - раздел app) - можно даже задать формат даты,  который ожидается в сущности

Основная логика приложения находится в MonitorServiceImpl.asyncHandleDir(MonitorContext monitorContext)

Идея была сделать архитектуру понятной и расширяемой. 



Логика работы:
При старте приложения значения настроек берутся из конфига и инициализируется объект контекста (MonitorContext)
Если настройки не заданы то берутся дефолтные

StartupTask запускает задачу передавая Monitor task в MonitorServiceImpl.asyncHandleDir
Используя TaskManager - менеджер задач
asyncHandleDir использует асбтракции project Reactor
пайплайн этого метода описан в /** */ коментарии к нему, если вкратце, то
для каждого файла из директории мониторинга 
1. замапить его в сущность java
2. провалидировать поля (только дата, т.к. в задании нет ничего о доп ограничениях валидации. Формат даты взят на основе примера сущности)
3. в случае неудачной валидации или неудачного маппинга сущности записать в лог причину и перенести файл в директорию Не успешно обработанных файлов
4. в случае удачной валидации и мапинга сохранить сущность в базу и перенести обработанный файл в директорию успешно обработанных файлов

**Что хотелось бы, но не успелось**
1. Изменение конфигурации без перестарта
2. Добавление какого-то ui ( просто фронт на websocket)
3. Чуть расширить архитектуру и разделить сущность MonitorContext на 2 - Сontext и Task
4. Выводить в UI информацию о работающих задачах, добавить возомжность их менеджерить, запускать, смотреть сколько файлов обработано
5. Добавить кэш в котором можно было бы проверять был уже файл проверен или нет (фильтр блума)
6. Расширить типы ошибок
7. Расширить валидацию
8. Создать контейнер/скрипт , например докера для быстрого развертывания сервиса с зависимостями, но учесть , что он работает с файловой системой (volumes)
9. Создание нагрузочного теста на миллион + файлов



