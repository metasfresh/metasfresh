#!/bin/sh

reset_config_file="/etc/rabbitmq/delete_me_to_reset_config.txt"

if [ ! -f "$reset_config_file" ]; then
        echo "init.sh - before rabbitmq start - $reset_config_file does not exist; -> resetting config files"
        cp --recursive --verbose /etc/rabbitmq_default_configs/* /etc/rabbitmq/
else
        echo "init.sh - before rabbitmq start - $reset_config_file exits; -> not resetting config files"
fi

echo "Auto-generated file; delete this file to let the rabbitmq container reset the config to internal defaults on startup." > $reset_config_file

#echo "init.sh - before rabbitmq start - RABBITMQ_PROCUREMENT_WEBUI_PASSWORD=$RABBITMQ_PROCUREMENT_WEBUI_PASSWORD"
#echo "init.sh - before rabbitmq start - RABBITMQ_METASFRESH_PASSWORD=$RABBITMQ_METASFRESH_PASSWORD"

# thx to https://stackoverflow.com/a/30773882/1012103
# update Rabbitmq user passwords when the server is up
( rabbitmqctl wait /var/lib/rabbitmq/mnesia/rabbit@$(hostname).pid --timeout 30 && \
echo "init.sh - Assuming that rabbitmq is up; setting passwords" && \
rabbitmqctl change_password procurement_webui $RABBITMQ_PROCUREMENT_WEBUI_PASSWORD 2>/dev/null && \
echo "init.sh - New password for user procurement_webui set from environment variable RABBITMQ_PROCUREMENT_WEBUI_PASSWORD" && \
rabbitmqctl change_password procurement_mf $RABBITMQ_PROCUREMENT_MF_PASSWORD 2>/dev/null && \
echo "init.sh - New password for user procurement_mf set from environment variable RABBITMQ_PROCUREMENT_MF_PASSWORD") &

# $@ is used to pass arguments to the rabbitmq-server command.
# For example if you use it like this: docker run -d rabbitmq arg1 arg2,
# it will be as you run in the container rabbitmq-server arg1 arg2
rabbitmq-server "$@"
