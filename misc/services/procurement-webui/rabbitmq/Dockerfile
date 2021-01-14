FROM rabbitmq:3.8.9-management

# to make sure that the cache is only used during one day, run docker build --build-arg CACHEBUST=$(date "+%Y-%m-%d")
# that way we should get the latest updates since the release of our base image
# thx to https://github.com/moby/moby/issues/1996#issuecomment-185872769
ARG CACHEBUST=1

RUN apt update && apt upgrade -y

# init.sh will copy them to /etc/rabbitmq, but won't overwrite existing files
COPY ./configs /etc/rabbitmq_default_configs

COPY ./init.sh /init.sh
RUN chmod +x /init.sh

# Define default command
CMD ["/init.sh"]
