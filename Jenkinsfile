pipeline {
    agent any

    tools {
        maven 'maven_3.9.5'
    }

    environment {
        RP_TOKEN = credentials('rpToken')
        RP_API_KEY = credentials('rp.api.key')
        RP_CREDS = credentials('RP credentials')
        SL_ACCESS_KEY = credentials('sauce.accessKey')
        JIRA_CREDS = credentials('jira')
    }

    stages {
        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
              steps {
                   script {
                        sh "mvn test -DrpToken=$RP_TOKEN -Drp.api.key=$RP_API_KEY -DrpUsername=$RP_CREDS_USR -DrpPassword=$RP_CREDS_PSW -Dsauce.accessKey=$SL_ACCESS_KEY -Djira.username=$JIRA_CREDS_USR -Djira.password=$JIRA_CREDS_PSW"
                   }
              }
        }
    }
}