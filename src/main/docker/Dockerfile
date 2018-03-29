
# the base image comes from "spring cloud in action"
# the rest mostly comes from https://spring.io/guides/gs/spring-boot-docker/
FROM openjdk:8-jdk-alpine
ADD metasfresh-admin.jar metasfresh-admin.jar
RUN sh -c 'touch /metasfresh-admin.jar'
ENV JAVA_OPTS="-Xmx220M"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /metasfresh-admin.jar" ]
