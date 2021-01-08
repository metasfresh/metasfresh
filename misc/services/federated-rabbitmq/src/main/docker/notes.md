
i created the cert & key with

```bash
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout /etc/ssl/private/nginx-selfsigned.key -out /etc/ssl/certs/nginx-selfsigned.crt
```

from localhost, both

```bash
openssl s_client -connect localhost:25673 # nginx of metasfresh_rabbitmq
openssl s_client -connect localhost:25674 # nginx of procurement_webui_rabbitmq
```

seem to work.
