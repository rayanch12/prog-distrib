# RentaCar Service

## Description
RentaCar est une application Spring Boot qui fournit une API REST pour la gestion d'une flotte de voitures. L'application est conteneurisée avec Docker et déployée sur Kubernetes avec un accès via Ingress.

## Technologies Utilisées
- Java 21
- Spring Boot 3.3.4
- Docker
- Kubernetes (Minikube)
- NGINX Ingress Controller
- Gradle

## Structure du Projet
```
prog-distrib/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── cherifi/
│                   └── Rentacar/
│                       ├── controller/
│                       │   └── CarController.java
│                       ├── Car.java
│                       └── RentacarApplication.java
├── k8s/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── ingress.yml
├── Dockerfile
├── build.gradle
└── README.md
```

## Fonctionnalités
L'API expose les endpoints suivants :
- `GET /` : Page d'accueil
- `GET /cars` : Liste toutes les voitures
- `GET /cars/{id}` : Récupère une voiture par son ID
- `POST /cars` : Ajoute une nouvelle voiture
- `PUT /cars/{id}` : Met à jour une voiture existante
- `DELETE /cars/{id}` : Supprime une voiture

## Guide d'Installation et de Déploiement

### Prérequis
- Java 21
- Docker
- Kubernetes (Minikube)
- kubectl

### 1. Construction de l'Application
```bash
./gradlew build -x test
```

### 2. Construction de l'Image Docker
```bash
docker build -t votre-username/rentacar:latest .
```

### 3. Publication sur Docker Hub
```bash
docker login
docker push votre-username/rentacar:latest
```

### 4. Déploiement sur Kubernetes

#### Démarrer Minikube
```bash
minikube start
```

#### Appliquer les configurations Kubernetes
```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

#### Vérifier le Déploiement
```bash
kubectl get pods
kubectl get services
```

### 5. Configuration de l'Ingress

#### Activer l'Ingress Controller
```bash
minikube addons enable ingress
```

#### Vérifier l'Installation de l'Ingress Controller
```bash
kubectl get pods -n ingress-nginx
```

#### Déployer l'Ingress
```bash
kubectl apply -f k8s/ingress.yml
```

#### Configurer l'Accès Local
1. Obtenir l'IP de Minikube :
```bash
minikube ip
```

2. Ajouter l'entrée dans le fichier hosts :
```bash
# Sur Linux
echo "$(minikube ip) rentacar.info" | sudo tee -a /etc/hosts

# Sur Windows
# Ajouter manuellement dans C:\Windows\System32\drivers\etc\hosts :
# <minikube-ip> rentacar.info
```

#### Activer le Tunnel Minikube
```bash
minikube addons enable ingress-dns
minikube tunnel
```

### 6. Accès à l'Application
L'application est accessible via :
```
http://rentacar.info
```

## Test de l'API

### Exemples de Requêtes

1. Obtenir la page d'accueil :
```bash
curl http://rentacar.info/
```

2. Lister toutes les voitures :
```bash
curl http://rentacar.info/cars
```

3. Ajouter une nouvelle voiture :
```bash
curl -X POST -H "Content-Type: application/json" \
     -d '{"brand":"BMW", "model":"X5", "price":45000}' \
     http://rentacar.info/cars
```

4. Obtenir une voiture spécifique :
```bash
curl http://rentacar.info/cars/0
```

5. Mettre à jour une voiture :
```bash
curl -X PUT -H "Content-Type: application/json" \
     -d '{"brand":"BMW", "model":"X5", "price":50000}' \
     http://rentacar.info/cars/0
```

6. Supprimer une voiture :
```bash
curl -X DELETE http://rentacar.info/cars/0
```

## Maintenance

### Mise à Jour de l'Application
Pour mettre à jour l'application avec une nouvelle version :

1. Modifier le code source
2. Reconstruire l'application : `./gradlew build -x test`
3. Reconstruire l'image Docker : `docker build -t votre-username/rentacar:latest .`
4. Pousser la nouvelle image : `docker push votre-username/rentacar:latest`
5. Redémarrer le déploiement : `kubectl rollout restart deployment rentacar-deployment`

### Nettoyage
Pour nettoyer le déploiement :
```bash
kubectl delete -f k8s/ingress.yml
kubectl delete -f k8s/service.yaml
kubectl delete -f k8s/deployment.yaml
```

## Dépannage
- Si les pods ne démarrent pas, vérifier les logs : `kubectl logs <nom-du-pod>`
- Si le service n'est pas accessible, vérifier le service : `kubectl describe service rentacar-service`
- Pour les problèmes de stockage Docker : `docker system prune -a`
- Pour les problèmes d'Ingress :
  - Vérifier le statut : `kubectl get ingress`
  - Vérifier les logs : `kubectl logs -n ingress-nginx -l app.kubernetes.io/name=ingress-nginx`
  - Vérifier que le tunnel est actif : `ps aux | grep "minikube tunnel"
