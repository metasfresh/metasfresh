ARG BASE_IMAGE_REPO=metasfresh-report

#
# The base images version to use can be set from outside using
# --build-arg BASE_IMAGE_VERSION=<value>
# 
# The default "latest" is usually the latest build from the release branch
# It is by our convention the same as "release-latest"
ARG BASE_IMAGE_VERSION=latest


FROM metasfresh/${BASE_IMAGE_REPO}:${BASE_IMAGE_VERSION}

COPY ./reports/ /opt/metasfresh/reports
