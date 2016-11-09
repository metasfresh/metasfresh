#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

node('agent && linux') // shall only run on a jenkins agent with linux
{
timestamps 
{
	//
	// setup: we'll need these variables in different stages, that's we we create them here
	//
	def BRANCH_NAME=env.BRANCH_NAME
	
    // set the version prefix, 1 for "master", 2 for "not-master" a.k.a. feature
    def BUILD_MAVEN_VERSION_PREFIX = BRANCH_NAME.equals('master') ? "1" : "2"
    
	// examples: "1-master-SNAPSHOT", "2-FRESH-123-SNAPSHOT"
    def BUILD_MAVEN_VERSION=BUILD_MAVEN_VERSION_PREFIX+"-"+BRANCH_NAME+"-SNAPSHOT"
    
	// example: "[1-master-SNAPSHOT],[2-FRESH-123-SNAPSHOT]
    def BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION="[1-master-SNAPSHOT],["+BUILD_MAVEN_VERSION+"]"

	stage('Preparation') // for display purposes
	{
		echo "BRANCH_NAME=${env.BRANCH_NAME}"
		checkout scm
	}

    configFileProvider([configFile(fileId: 'aa1d8797-5020-4a20-aa7b-2334c15179be', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
    {
        withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M -XX:MaxHeapSize=512m') 
        {
            stage('Set artifact versions') 
            {
                // set the artifact version of everything below the webui's pom.xml
                sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
            }
            
			stage('Build metasfresh-webui-api') 
            {
        		// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
        		sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode -Dmetasfresh-dependency.version=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -Dmaven.test.failure.ignore=true clean deploy"
            }
		}
	}

   stage('Collect test results') 
   {
      junit '**/target/surefire-reports/*.xml'
   }
   
	stage('Invoke downstream jobs') 
	{
		// TODO: trigger endcustomer.mf15 build
		build job: '../metasfresh-multibranch/${BRANCH_NAME}', wait: false
	}
}
   
} // timestamps   
} // node
