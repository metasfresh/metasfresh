# metasfresh in minikube with helm
## Requirements

1. [minikube](https://minikube.sigs.k8s.io/docs/start/#what-youll-need) (used v1.25.1)
2. [kubectl](https://minikube.sigs.k8s.io/docs/handbook/kubectl/) (used GitVersion 1.23.3)
3. [helm](https://helm.sh/docs/intro/install/) (used v3.8.0)

## Pre-Configuration
[values.yaml](./metasfresh-helm/values.yaml)  
[install.sh](install.sh)

## Installation
run "/bin/bash [install.sh](install.sh)"

## Configuration for local use
- get ip with "minikube ip"  
- for local use modify etc/hosts like following: (replace 192.168.49.2 with your minikube ip and url like set in [values.yaml](./metasfresh-helm/values.yaml))
```
127.0.0.1       localhost  
192.168.49.2    demouser-demo.metasfresh.com  
192.168.49.2    pgadmin.demouser-demo.metasfresh.com  
```

## pgadmin
- url: ingress.pgadmin.url as set in [values.yaml](./metasfresh-helm/values.yaml)
- login 
    - pgadmin.defaultEmail as set in [values.yaml](./metasfresh-helm/values.yaml)
    - pgadmin.defaultPassword as set in [values.yaml](./metasfresh-helm/values.yaml)
- create server
    - Host: name/address metasfresh-yourreleasename-db
    - Port: 5432
    - Username: db.username as set in [values.yaml](./metasfresh-helm/values.yaml)
    - Password: db.password as set in [values.yaml](./metasfresh-helm/values.yaml)

## dev section
### helm
dry helm run to see generated .yaml (after first install upgrade can be used)
```
helm install <releasename> <dir> --namespace <namespace> --create-namespace --dry-run --debug

helm install dev metasfresh-helm --namespace dev --create-namespace --dry-run --debug
```  
### kubectl
show all pods, services, deployments and replicasets
```
kubectl -n <namespace> get all
``` 
get log of pod
```
kubectl -n <namespace> logs <podname>
``` 
get console of pod
```
kubectl -n <namespace> exec -it <podname> -- /bin/sh
``` 
