pipeline {
    agent any

    environment {
        COMPOSE_FILE = 'docker-compose.yml'
        DOCKERHUB_CREDENTIALS = 'dockerhub'
        DOCKERHUB_NAMESPACE = 'sbonillagarcia'
    }

    stages {
        stage('Preparar entorno') {
            steps {
                echo 'Iniciando pipeline...'
                sh 'docker --version'
                sh 'docker-compose --version'
            }
        }

        stage('Compilar Backend') {
            steps {
                echo 'Compilando proyecto Spring Boot...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Construir imágenes Docker') {
            steps {
                echo 'Construyendo imágenes Docker...'
                sh "docker build -t ${DOCKERHUB_NAMESPACE}/frontend-app ./frontend"
                sh "docker build -t ${DOCKERHUB_NAMESPACE}/backend-app ."
            }
        }

        stage('Push a Docker Hub') {
            steps {
                echo 'Subiendo imágenes a Docker Hub...'
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh "docker push ${DOCKERHUB_NAMESPACE}/frontend-app"
                    sh "docker push ${DOCKERHUB_NAMESPACE}/backend-app"
                }
            }
        }

        stage('Desplegar con Docker Compose') {
            steps {
                echo 'Desplegando contenedores localmente...'
                sh 'docker-compose down -v'
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        success {
            echo ' Despliegue completado con éxito.'
        }
        failure {
            echo ' Falló el pipeline.'
        }
    }
}
