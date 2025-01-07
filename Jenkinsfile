pipeline {
    agent any

    environment {
        DOCKER_HOST = 'tcp://docker:2375'
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                sh './mvnw test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco execPattern: '**/target/*.exec'
                }
            }
        }

        stage('Code Quality Analysis') {
            steps {
                sh './mvnw sonar:sonar -Dsonar.projectKey=hunters-league -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=$SONAR_TOKEN'
            }
        }

        stage('Dockerize') {
            steps {
                sh 'docker build -t hunters-league-api .'
                sh 'docker-compose -f src/main/docker/compose.yml up -d'
            }
        }

        stage('Deploy') {
            steps {
                input message: "Deploy to production?"
                sh './deploy.sh'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
        success {
            mail to: 'developer@example.com',
                subject: "Build Successful: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "The build was successful. You can view it at ${env.BUILD_URL}"
        }
        failure {
            mail to: 'developer@example.com',
                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "The build failed. Please check the logs at ${env.BUILD_URL}"
        }
    }
}
