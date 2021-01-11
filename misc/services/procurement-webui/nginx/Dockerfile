FROM tekn0ir/nginx-stream

COPY ./configs/certs/nginx-selfsigned.crt /etc/nginx/ssl/cert.pem
COPY ./configs/certs/nginx-selfsigned.key /etc/nginx/ssl/key.pem
COPY ./configs/default.conf /opt/nginx/stream.conf.d/
