#!/bin/sh

/etc/rabbitmq_default_configs

echo "init.sh - before rabbitmq start - copy not-yet-existing config files"
cp --no-clobber --recursive --verbose /etc/rabbitmq_default_configs/* /etc/rabbitmq/

#echo "init.sh - before rabbitmq start - RABBITMQ_PROCUREMENT_WEBUI_PASSWORD=$RABBITMQ_PROCUREMENT_WEBUI_PASSWORD"
#echo "init.sh - before rabbitmq start - RABBITMQ_METASFRESH_PASSWORD=$RABBITMQ_METASFRESH_PASSWORD"

# thx to https://stackoverflow.com/a/30773882/1012103
# Create Rabbitmq user when the server is up
# IDK how to wait for rabbitmq to be up
# rabbitmqctl wait /var/lib/rabbitmq/mnesia/rabbitmq --timeout 30 &&
( rabbitmqctl wait /var/lib/rabbitmq/mnesia/rabbit@$(hostname).pid --timeout 30 && \
echo "init.sh - Assuming that rabbitmq is up; setting passwords" && \
rabbitmqctl change_password procurement_webui $RABBITMQ_PROCUREMENT_WEBUI_PASSWORD 2>/dev/null && \
echo "init.sh - New password for user procurement_webui set from environment variable RABBITMQ_PROCUREMENT_WEBUI_PASSWORD" && \
rabbitmqctl change_password metasfresh $RABBITMQ_METASFRESH_PASSWORD 2>/dev/null && \
echo "init.sh - New password for user metasfresh set from environment variable RABBITMQ_PROCUREMENT_WEBUI_PASSWORD") &

# $@ is used to pass arguments to the rabbitmq-server command.
# For example if you use it like this: docker run -d rabbitmq arg1 arg2,
# it will be as you run in the container rabbitmq-server arg1 arg2
rabbitmq-server $@
