#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

timestamps 
{
configFileProvider([configFile(fileId: 'aa1d8797-5020-4a20-aa7b-2334c15179be', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
{
	withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M -XX:MaxHeapSize=512m') 
	{
		// to build the client-exe on linux, we need 32bit libs!
		node('agent && linux')
		{
	
			//
			// setup: we'll need the following variables in different stages, that's we we create them here
			//
			
			// thx to http://stackoverflow.com/a/36949007/1012103 with respect to the paramters
			// disabling concurrent builds as long as we work with "SNAPSHOTS"
			properties([[$class: 'GithubProjectProperty', displayName: '', projectUrlStr: 'https://github.com/metasfresh/metasfresh-webui/'], parameters([string(defaultValue: '', description: '''If this job is invoked via an updstream build job, than that job can provide either its branch or the respective <code>MF_UPSTREAM_BRANCH</code> that was passed to it.<br>
			This build will then attempt to use maven dependencies from that branch, and it will sets its own name to reflect the given value.
			<p>
			So if this is a "master" build, but it was invoked by a "feature-branch" build then this build will try to get the feature-branch\'s build artifacts annd will set its
			<code>currentBuild.displayname</code> and <code>currentBuild.description</code> to make it obvious that the build contains code from the feature branch.''', name: 'MF_UPSTREAM_BRANCH')]), pipelineTriggers([]), disableConcurrentBuilds()])

			if(params.MF_UPSTREAM_BRANCH == '')
			{
				echo "Setting BRANCH_NAME from env.BRANCH_NAME=${env.BRANCH_NAME}"
				BRANCH_NAME=env.BRANCH_NAME
			}
			else
			{
				echo "Setting BRANCH_NAME from params.MF_UPSTREAM_BRANCH=${params.MF_UPSTREAM_BRANCH}"
				BRANCH_NAME=params.MF_UPSTREAM_BRANCH
			}
			
			currentBuild.description="Parameter MF_UPSTREAM_BRANCH="+params.MF_UPSTREAM_BRANCH
			currentBuild.displayName="#"+currentBuild.number+"-"+BRANCH_NAME
			
			// set the version prefix, 1 for "master", 2 for "not-master" a.k.a. feature
			def BUILD_MAVEN_VERSION_PREFIX = BRANCH_NAME.equals('master') ? "1" : "2"
			
			// examples: "1-master-SNAPSHOT", "2-FRESH-123-SNAPSHOT"
			def BUILD_MAVEN_VERSION=BUILD_MAVEN_VERSION_PREFIX+"-"+BRANCH_NAME+"-SNAPSHOT"
			
			// example: "[1-master-SNAPSHOT],[2-FRESH-123-SNAPSHOT]
			def BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION="[1-master-SNAPSHOT],["+BUILD_MAVEN_VERSION+"]"

			stage('Preparation') // for display purposes
			{
				checkout scm
			}
		
            stage('Set artifact versions') 
            {
                // output them to make things more clear
                echo "BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION}"
                echo "BUILD_MAVEN_VERSION=${BUILD_MAVEN_VERSION}"

                // set the artifact version of everything below de.metas.parent/pom.xml and then also for everything below de.metas.esb/pom.xml and de.metas.endcustomer.mf15/pom.xml
                sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
                sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.endcustomer.mf15/pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
            }

			// Note: we can't build the "main" and "esb" stuff in parallel, because the esb stuff depends on (at least!) de.metas.printing.api
            stage('Build metasfresh') 
            {
        		// deploy the de.metas.parent pom.xml to our "permanent" snapshot repo, but don't do anything with the modules that are declared in there
        		sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode --non-recursive --activate-profiles metasfresh-perm-snapshots-repo clean deploy"
        
				// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
        		sh "mvn --settings $MAVEN_SETTINGS --file de.metas.reactor/pom.xml --batch-mode -Dmetasfresh-dependency.version=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -Dmaven.test.failure.ignore=true clean deploy"
            }

            stage('Build esb') 
            {
				// maven.test.failure.ignore=true: see metasfresh stage
    		    sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -Dmetasfresh-dependency.version=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -Dmaven.test.failure.ignore=true clean deploy"
            }
			
			// collect test results
			junit '**/target/surefire-reports/*.xml'
			
			// TODO: notify zapier that the "main" stuff was build
		} // node			
		
		// to build the client-exe on linux, we need 32bit libs!
		node('agent && linux && libc6-i386')
		{
			stage('Build dist') 
			{
				// maven.test.failure.ignore=true: see metasfresh stage
				// we currently deploy *and* also archive, but that might change in future
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.endcustomer.mf15/pom.xml --batch-mode -Dmetasfresh-dependency.version=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -Dmaven.test.failure.ignore=true clean deploy"
				
				 // collect test results
				junit '**/target/surefire-reports/*.xml'
				
			  	// we currently deploy *and* also archive, but that might change in future
				archiveArtifacts 'de.metas.endcustomer.mf15/de.metas.endcustomer.mf15.dist/target/*.tar.gz,de.metas.endcustomer.mf15/de.metas.endcustomer.mf15.swingui/target/*.zip,de.metas.endcustomer.mf15/de.metas.endcustomer.mf15.swingui/target/*.exe'
			}

		} // node
	} // withMaven
} // configFileProvider
} // timestamps

