pipeline {
    agent any
    tools {
        maven 'maven_3.9.5'
    }
    stages {
        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package'
                }
            }
        }
    }
}