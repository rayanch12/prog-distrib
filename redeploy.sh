#!/bin/bash

echo "Building and deploying frontend..."
cd frontend
./gradlew bootJar
docker build -t rayanch1/numbers-frontend:latest .
docker push rayanch1/numbers-frontend:latest

echo "Building and deploying backend..."
cd ../backend
./gradlew bootJar
docker build -t rayanch1/numbers-backend:latest .
docker push rayanch1/numbers-backend:latest

echo "Restarting Kubernetes deployments..."
cd ..
kubectl rollout restart deployment/frontend-deployment
kubectl rollout restart deployment/backend-deployment

echo "Waiting for deployments to be ready..."
kubectl rollout status deployment/frontend-deployment
kubectl rollout status deployment/backend-deployment

echo "Deployment complete! Access the application at http://numbers.info"
