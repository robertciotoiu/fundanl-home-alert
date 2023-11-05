# Script to deploy the application

# Get the latest version of the code
git fetch && git pull

# Build the application
mvn clean install -DskipTests

# Build the docker image
docker build -t localhost:5000/com.robertciotoiu/fundanl-home-alert:latest .

# Push the docker image
docker push localhost:5000/com.robertciotoiu/fundanl-home-alert:latest

# Create the namespace if it doesn't exist
kubectl delete namespace fundanl
kubectl create namespace fundanl

# Deploy the configmap and secret
kubectl -n fundanl apply -f k8s/configmap.yaml
kubectl -n fundanl apply -f k8s/secret.yaml

# Deploy the application
kubectl -n fundanl apply -f k8s/deployment.yaml

# Check the status of the deployment
kubectl -n fundanl rollout status deployment/fundanl-home-alert --watch