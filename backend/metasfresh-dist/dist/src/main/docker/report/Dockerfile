ARG BASE_IMAGE_NAME=metasfresh-report

#
# The base images version to use can be set from outside using
# --build-arg BASE_IMAGE_VERSION=<value>
# 
# The default "latest" is usually the latest build from the release branch
# It is by our convention the same as "release-latest"
ARG BASE_IMAGE_VERSION=latest


FROM docker.metasfresh.com:6001/metasfresh/${BASE_IMAGE_NAME}:${BASE_IMAGE_VERSION}

# Note: it's the pom.xml's job to provide these 2 folders
COPY ./external-lib/* /opt/metasfresh/metasfresh-report/external-lib/
COPY ./reports/ /opt/metasfresh/reports

# Note: we go with the entry-point that was already specified by the base image's Dockerfile
