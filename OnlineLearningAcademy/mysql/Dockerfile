FROM mysql:latest
ENV MYSQL_DATABASE=online_learning_academy
ENV MYSQL_ROOT_PASSWORD=root
COPY init.sql /docker-entrypoint-initdb.d/
EXPOSE 3306
