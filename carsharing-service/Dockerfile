FROM openjdk:8
VOLUME /tmp
ADD target/*.jar /app.jar
ADD ./wait-for-it.sh .
ENV JAVA_OPTS=''
ENTRYPOINT ["./wait-for-it.sh", "carbase-db:3306", "--",  "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
