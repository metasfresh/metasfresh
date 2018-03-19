#/bin/bash

mkdir -p private
mkdir -p certs

# this should work under windows/cygwin
# thx to https://stackoverflow.com/questions/31506158/running-openssl-from-a-bash-script-on-windows-subject-does-not-start-with
subject="//C=DE\ST=NRW\L=Bonn\O=metasfresh\OU=IT-Department\CN=tobi.metasfresh.com"
# under windows cygwin, this yields "Subject does not start with '/'."
#subject="/C=DE/ST=NRW/L=Bonn/O=metasfresh/OU=IT-Department/CN=tobi.metasfresh.com"


# thanks to https://www.digitalocean.com/community/tutorials/how-to-create-a-self-signed-ssl-certificate-for-nginx-on-centos-7
# Note that according to https://kubernetes.io/docs/concepts/services-networking/ingress/#tls
# the key and crt have to be name tls.key resp. tls.crt, but we can still shoose our output filenames freely
openssl req -x509 -nodes -days 365 -newkey rsa:2048\
 -keyout ./private/nginx-selfsigned.key\
 -out ./certs/nginx-selfsigned.crt\
 -subj ${subject}

openssl dhparam -out ./certs/dhparam.pem 2048

# first, attempt to delete old versions, if they exist
kubectl delete secret tls-certificate
kubectl delete secret tls-dhparam

kubectl create secret tls tls-certificate --key ./private/nginx-selfsigned.key --cert ./certs/nginx-selfsigned.crt
kubectl create secret generic tls-dhparam --from-file=./certs/dhparam.pem
