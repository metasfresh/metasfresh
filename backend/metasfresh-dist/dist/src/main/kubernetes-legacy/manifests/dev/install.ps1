#       == How to Run
#
# either run the script in powershell, or copy paste the contents in a powershell window and run.
#


echo "This will install all needed k8s services and deployments needed for metasfresh development"

Start-Sleep 5;

kubectl get all

# metasfresh admin
kubectl apply -f .\admin\metasfresh-admin-svc.yaml
kubectl apply -f .\admin\metasfresh-admin-deploy.yaml


# postgress
cd .\postgresql\
kubectl create configmap metasfresh-postgresql-config --from-file=postgresql.conf
cd ..
kubectl apply -f .\postgresql\metasfresh-postgresql-deploy_demouser_demo.yaml
kubectl apply -f .\postgresql\metasfresh-postgresql-svc_demouser_demo.yaml


# rabbitmq
kubectl apply -f .\rabbitmq\metasfresh-rabbitmq-deploy_demouser_demo.yaml
kubectl apply -f .\rabbitmq\metasfresh-rabbitmq-svc_demouser_demo.yaml


# elastic search should not be needed locally, right?
# kubectl apply -f .\search\metasfresh-search-deploy_demouser_demo.yaml
# kubectl apply -f .\search\metasfresh-search-svc_demouser_demo.yaml

echo "Done installing! Waiting for stuff to start"
Start-Sleep 10;



while($true)
{
    $TheCommandOutput = kubectl get all;
    clear;
    echo $TheCommandOutput;
    Date;
    sleep 1
}
