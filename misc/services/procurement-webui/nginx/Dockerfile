FROM rabbitmq:3.8.9-management

COPY ./configs/certs/nginx-selfsigned.crt /etc/nginx/ssl/cert.pem
COPY ./configs/certs/nginx-selfsigned.key /etc/nginx/ssl/key.pem
COPY ./configs/default.conf /opt/nginx/stream.conf.d/
