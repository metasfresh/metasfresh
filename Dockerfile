FROM maven:3.8.4-jdk-8

COPY misc/dev-support/maven/settings.xml /root/.m2/

WORKDIR /parent
COPY misc/parent-pom .
RUN mvn clean install -Dmaven.gitcommitid.skip=true

WORKDIR /commons
COPY misc/de-metas-common .
RUN mvn clean install -Dmaven.gitcommitid.skip=true

WORKDIR /backend
COPY backend .
RUN mvn clean install -Dmaven.gitcommitid.skip=true -DskipTests

RUN cd de.metas.adempiere.adempiere/base && mvn surefire:test -Dtest="StringLikeFilterTest"