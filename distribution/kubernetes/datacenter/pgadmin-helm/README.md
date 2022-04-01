# pgadmin

## Install pgadmin
customize [values.yaml](./values.yaml)
```
helm install pgadmin pgadmin-helm --namespace demo --create-namespace
```
<br><br>

## Connect to db
- url: ingress.pgadmin.url as set in [values.yaml](./values.yaml)
- login 
    - pgadmin.defaultEmail as set in [values.yaml](./values.yaml)
    - pgadmin.defaultPassword as set in [values.yaml](./values.yaml)
- create server
    - Host: name/address ```metasfresh-demo-db```
    - Port: 5432
    - Username: db.username as set in [values.yaml](./values.yaml)
    - Password: db.password as set in [values.yaml](./values.yaml)