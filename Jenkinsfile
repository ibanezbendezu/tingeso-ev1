pipeline {
    agent any
    tools{
        maven 'maven'
    }
    stages {
        stage('Build jar file') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/ibanezbendezu/tingeso-ev1.git']]])
                powershell 'mvn --version'
                powershell 'mvn clean package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                powershell 'mvn test'
            }
        }
        stage('Build docker image'){
            steps {
                powershell 'docker build -t ibanezbendezu/mueblesstgo .'
            }
        }
        stage('Push docker image'){
            steps {
                script{
                    withCredentials([string(credentialsId: 'dockerpass', variable: 'dockerhubpass')]) {
                        powershell 'docker login -u ibanezbendezu -p ${env:dockerhubpass}'
                    }
                    
                    powershell 'docker push ibanezbendezu/mueblesstgo'
                }
                
            }
        }
    }
    post {
        always {
            powershell 'docker logout'
        }
    }
}