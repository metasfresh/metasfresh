# Metasfresh in minikube with helm
## Requirements

1. [minikube](https://minikube.sigs.k8s.io/docs/start/#what-youll-need) (used v1.25.1)
2. [kubectl](https://minikube.sigs.k8s.io/docs/handbook/kubectl/) (used GitVersion 1.23.3)
3. [helm](https://helm.sh/docs/intro/install/) (used v3.8.0)
<br><br>

## Pre-Configuration
[values.yaml](./metasfresh-helm/values.yaml)  
[install.sh](install.sh)
<br><br>

## Installation
run [install.sh](install.sh)
```
cd <path-to-kubernetes-project>
/bin/bash install.sh
```
<br><br>

## Configuration for local use
- get ip with "minikube ip"  
- for local use modify etc/hosts like following: (replace 192.168.49.2 with your minikube ip and url like set in [values.yaml](./metasfresh-helm/values.yaml))
```
127.0.0.1       localhost  
192.168.49.2    demouser-demo.metasfresh.com  
192.168.49.2    pgadmin.demouser-demo.metasfresh.com  
```
<br><br>

## Pgadmin
- url: ingress.pgadmin.url as set in [values.yaml](./metasfresh-helm/values.yaml)
- login 
    - pgadmin.defaultEmail as set in [values.yaml](./metasfresh-helm/values.yaml)
    - pgadmin.defaultPassword as set in [values.yaml](./metasfresh-helm/values.yaml)
- create server
    - Host: name/address ```metasfresh-<releasename>-db```
    - Port: 5432
    - Username: db.username as set in [values.yaml](./metasfresh-helm/values.yaml)
    - Password: db.password as set in [values.yaml](./metasfresh-helm/values.yaml)
<br><br>

## How to reset database
delete kubernetes cluster
```
minikube delete
```
remove db data
```
sudo rm -r db <path-to-kubernetes-project>/volumes/<namespace of kubernetes instance>/db
```
restart minikube with [install.sh](install.sh) script
```
/bin/bash install.sh
```
<br><br>

## Local https development
- https://deliciousbrains.com/ssl-certificate-authority-for-local-https-development/
- tls.crt and tls.key need to be placed in files directory (*.metasfresh.com for wildcard domain)
<br><br>

## Dev section
### Debug
port forwarding: https://kubernetes.io/docs/tasks/access-application-cluster/port-forward-access-application-cluster/#forward-a-local-port-to-a-port-on-the-pod  
for example:
```
# kubectl port-forward -n <namespace> service/metasfresh-<releasename>-app 8788:8788
kubectl port-forward -n dev service/metasfresh-dev-app 8788:8788
```
<br>

### Minikube
dashbord of the kubernetes cluster
```
minikube dashboard
```
minikube access
```
minikube ssh
```  
<br>

### Helm
dry helm run to see generated .yaml (after first install upgrade can be used)
```
helm install <releasename> <dir> --namespace <namespace> --create-namespace --dry-run --debug

helm install demo metasfresh-helm --namespace demo --create-namespace --dry-run --debug
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
