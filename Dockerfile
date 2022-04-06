FROM maven:3.8.4-jdk-8 AS build-jdk8

COPY docker/settings.xml /root/.m2/

WORKDIR /parent
COPY misc/parent-pom .
RUN mvn clean install

WORKDIR /commons
COPY misc/de-metas-common .
RUN mvn clean install

WORKDIR /backend
COPY backend .
RUN mvn clean install -DskipTests

# WORKDIR /distribution
# COPY distribution .
# RUN mvn clean install


FROM openjdk:14-jdk

ARG MAVEN_VERSION=3.8.5
ARG USER_HOME_DIR="/root"
ARG SHA=a9b2d825eacf2e771ed5d6b0e01398589ac1bfa4171f36154d1b5787879605507802f699da6f7cfc80732a5282fd31b28e4cd6052338cbef0fa1358b48a5e3c8
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

COPY docker/java-mvn/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY docker/java-mvn/settings-docker.xml /usr/share/maven/ref/

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
CMD ["mvn"]

COPY docker/settings.xml /root/.m2/
COPY --from=build-jdk8 /root/.m2/ /root/.m2

WORKDIR /camel
COPY misc/services/camel .
RUN mvn clean install -DskipTests

