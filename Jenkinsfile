pipeline {

    agent any

    environment {
        IMAGE_NAME = "narasimhamadesh/spring-crud-app"
        KUBECONFIG = "/var/jenkins_home/.kube/config"
    }

    stages {

        stage('Build Maven Project') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                sh "docker push ${IMAGE_NAME}:${BUILD_NUMBER}"
            }
        }

        stage('Deploy To Kubernetes') {
            steps {
                sh """
                sed -i "s|image:.*|image: ${IMAGE_NAME}:${BUILD_NUMBER}|g" deployment.yml
                """
                sh 'kubectl apply -f mysql-deployment.yml'
                sh 'kubectl apply -f mysql-service.yml'
                sh 'kubectl apply -f deployment.yml'
                sh 'kubectl apply -f service.yml'
            }
        }

        stage('Verify Deployment') {
            steps {
                sh 'kubectl get pods'
                sh 'kubectl get svc'
            }
        }

    }

}
