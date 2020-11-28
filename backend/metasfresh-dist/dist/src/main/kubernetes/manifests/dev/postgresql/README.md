Before creating the postgres deployment please make sure you create the 'metasfresh-postgresql-config' configmap:

`kubectl create configmap metasfresh-postgresql-config --from-file=postgresql.conf`
