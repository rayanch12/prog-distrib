# Application de Gestion de Nombres avec Kubernetes

Cette application est un système distribué de gestion de nombres, construit avec Spring Boot et déployé sur Kubernetes. Elle utilise une architecture microservices avec un frontend, un backend, et une base de données MySQL persistante.

## Architecture

L'application est composée de trois composants principaux :

1. **Frontend** (Spring Boot)
   - Gère l'interface utilisateur
   - Communique avec le backend via HTTP
   - Expose les endpoints pour l'utilisateur final

2. **Backend** (Spring Boot)
   - Gère la logique métier
   - Communique avec MySQL
   - Expose une API REST
   - Utilise Spring Data JPA pour la persistance

3. **MySQL**
   - Stocke les données de manière persistante
   - Déployé avec un volume persistant dans Kubernetes
   - Accessible uniquement depuis le backend

## Prérequis

- Docker
- Kubernetes (Minikube)
- kubectl
- Java 17 ou supérieur
- Gradle

## Configuration Kubernetes

### Composants déployés

1. **Secrets**
   - `mysql-secret.yaml` : Stocke les credentials MySQL

2. **Stockage**
   - `mysql-storage.yaml` : Définit le PV et PVC pour MySQL

3. **Déploiements**
   - `mysql-deployment.yaml` : Déploie MySQL
   - `backend-deployment.yaml` : Déploie le backend
   - `frontend-deployment.yaml` : Déploie le frontend

4. **Services**
   - `mysql-service.yaml` : Expose MySQL (ClusterIP)
   - `backend-service.yaml` : Expose le backend
   - `frontend-service.yaml` : Expose le frontend

## Installation et Déploiement

1. **Démarrer Minikube**
   ```bash
   minikube start
   ```

2. **Construire les images Docker**
   ```bash
   # Dans le dossier backend
   ./gradlew build
   docker build -t rayanch1/numbers-backend:latest .
   docker push rayanch1/numbers-backend:latest

   # Dans le dossier frontend
   ./gradlew build
   docker build -t rayanch1/numbers-frontend:latest .
   docker push rayanch1/numbers-frontend:latest
   ```

3. **Déployer MySQL**
   ```bash
   kubectl apply -f k8s/mysql-secret.yaml
   kubectl apply -f k8s/mysql-storage.yaml
   kubectl apply -f k8s/mysql-deployment.yaml
   kubectl apply -f k8s/mysql-service.yaml
   ```

4. **Déployer le Backend et le Frontend**
   ```bash
   kubectl apply -f k8s/backend-deployment.yaml
   kubectl apply -f k8s/backend-service.yaml
   kubectl apply -f k8s/frontend-deployment.yaml
   kubectl apply -f k8s/frontend-service.yaml
   ```

## API Endpoints

### Frontend Endpoints

- `GET /` : Page d'accueil
- `GET /numbers` : Liste tous les nombres
- `GET /numbers/{id}` : Affiche un nombre spécifique
- `POST /numbers` : Ajoute un nouveau nombre
- `DELETE /numbers/{id}` : Supprime un nombre

### Backend Endpoints

- `GET /api/numbers` : Récupère tous les nombres
- `GET /api/numbers/{id}` : Récupère un nombre spécifique
- `POST /api/numbers` : Ajoute un nouveau nombre
- `DELETE /api/numbers/{id}` : Supprime un nombre

## Structure de la Base de Données

### Table : number
- `id` : Long (Primary Key, Auto Increment)
- `value` : Integer

## Tests et Vérification

### Vérifier l'état des pods
```bash
kubectl get pods
```

### Vérifier les logs
```bash
# Logs MySQL
kubectl logs <mysql-pod-name>

# Logs Backend
kubectl logs <backend-pod-name>

# Logs Frontend
kubectl logs <frontend-pod-name>
```

### Tester l'API
```bash
# Liste tous les nombres
curl -H "Host: numbers.info" http://192.168.49.2/numbers

# Ajoute un nombre
curl -X POST -H "Host: numbers.info" -H "Content-Type: application/json" -d "99" http://192.168.49.2/numbers

# Supprime un nombre
curl -X DELETE -H "Host: numbers.info" http://192.168.49.2/numbers/{id}
```

### Accéder à MySQL
```bash
# Se connecter à MySQL
kubectl exec -it <mysql-pod-name> -- mysql -uspringuser -pThePassword db_example

# Vérifier les données
SELECT * FROM number;
```

## Dépendances Principales

### Backend
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- MySQL Connector Java
- Spring Boot Starter Test

### Frontend
- Spring Boot Starter Web
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Test

## Maintenance et Dépannage

### Redémarrer un déploiement
```bash
kubectl rollout restart deployment/<deployment-name>
```

### Vérifier les logs en temps réel
```bash
kubectl logs -f <pod-name>
```

### Accéder au shell d'un pod
```bash
kubectl exec -it <pod-name> -- /bin/bash
```

### Vérifier la configuration MySQL
```bash
kubectl exec -it <mysql-pod-name> -- mysql -u root -ptest1234 -e "SHOW DATABASES;"
```

## Sécurité

- Les credentials MySQL sont stockés dans des secrets Kubernetes
- MySQL n'est accessible que depuis le backend (ClusterIP)
- Les communications entre les services sont internes au cluster

## Persistance des Données

Les données sont persistantes grâce à :
- Volume persistant (PV) pour MySQL
- PersistentVolumeClaim (PVC) lié au PV
- Stockage monté dans le pod MySQL

## Contribution

1. Forker le projet
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Créer une Pull Request

## Licence

Ce projet est sous licence MIT.
