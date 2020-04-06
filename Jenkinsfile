#!/usr/bin/env groovy

// thx to https://stackoverflow.com/a/51545570/1012103 although i don't yet fully get it

pipeline {
    agent any
    options {
        timestamps()
    }
    // triggers {
    //     bitbucketPush()
    // }
    stages {
        stage('backend and distribution') {
            when {
                changeset "backend/**"
            }
            steps {
                build 'backend'
                build 'distribution'
            }
        }
        stage('distribution only') {
            when {
                changeset "distribution/**"
            }
            steps {
                build 'distribution'
            }
        }
    }
}