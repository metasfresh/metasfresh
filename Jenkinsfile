#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

node('agent && linux') // shall only run on a jenkins agent with linux
{
timestamps 
{
	def BRANCH_NAME=env.BRANCH_NAME

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
                // get our version strings
				
                // set the version prefix, 1 for "master", 2 for "not-master" a.k.a. feature
                def BUILD_MAVEN_VERSION_PREFIX = BRANCH_NAME.equals('master') ? "1" : "2"
                
                // examples: "1-master-SNAPSHOT", "2-FRESH-123-SNAPSHOT"
                def BUILD_MAVEN_VERSION=BUILD_MAVEN_VERSION_PREFIX+"-"+BRANCH_NAME+"-SNAPSHOT"
                
                def BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION="[1-master-SNAPSHOT],["+BUILD_MAVEN_VERSION+"]"
                
                // output them to make things more clear
                echo "BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION}"
                echo "BUILD_MAVEN_VERSION=${BUILD_MAVEN_VERSION}"
                
                // set the artifact version of everything below de.metas.parent/pom.xml and then also for everything below de.metas.esb/pom.xml
                sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
                sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
            }
            
            // Note: we can't build the "main" and "esb" stuff in parallel, because the esb stuff depends on (at least!) de.metas.printing.api
            stage('Build metasfresh') 
            {
        		// deploy the de.metas.parent pom.xml to our "permanent" snapshot repo, but don't do anything with the modules that are declared in there
        		sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode --non-recursive --activate-profiles metasfresh-perm-snapshots-repo clean deploy"
        
				// fail-never: continue if tests fail, because we want a full report. thx to http://stackoverflow.com/questions/4174696/making-maven-run-all-tests-even-when-some-fail
        		sh "mvn --settings $MAVEN_SETTINGS --file de.metas.reactor/pom.xml --batch-mode --fail-never clean deploy"
            }

            stage('Build esb') 
            {
				// fail-never: see metasfresh stage
    		    sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode --fail-never clean deploy"
            }
		}
	}

   stage('Results') {
      junit '**/target/surefire-reports/*.xml'
   }
} // timestamps   
} // node
