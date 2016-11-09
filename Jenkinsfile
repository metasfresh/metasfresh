#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

node('agent && linux') // shall only run on a jenkins agent with linux
{
timestamps 
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
   
	stage('Invoke downstream job') 
	{
		// if this is not the master branch but a feature branch, we need to find out if the "BRANCH_NAME" job exists or not
		//
		// Here i'm not checking if the build job exists but if the respective branch und github exists. If the branch is there, then i assume that the multibranch plugin also create the job
		exitCode = sh returnStatus: true, script: 'git ls-remote --exit-code https://github.com/metasfresh/metasfresh ${BRANCH_NAME}'
		if(exitCode == 0)
		{
			echo "Branch ${BRANCH_NAME} also exists in metasfresh"
			jobName = "metasfresh/"+BRANCH_NAME
		}
		else 
		{
			echo "Branch ${BRANCH_NAME} does not exist in metasfresh; falling back to master"
			jobName = "metasfresh/master"
		}
		
		// I also tried
		// https://jenkins.metasfresh.com/job/metasfresh-multibranch/api/xml?tree=jobs[name] 
		// worked from chrome, also for metas-dev
		// worked from the shell using curl (with [ and ] escaped) for user metas-ts and an access token
		// did not work from the shell with curl and user metas-dev with "metas-dev is missing the Overall/Read permission"
		// the curl string was sh "curl -XGET 'https://jenkins.metasfresh.com/job/metasfresh-multibranch/api/xml?tree=jobs%5Bname%5D' --user metas-dev:access-token
		
		// and I also tried inspecting the list returned by 
		// Jenkins.instance.getAllItems()
		// but there is got a scurity exception and am not sure if an how i can have an SCM maintained script that is approved by an admin
		
		build job: jobName, parameters: [string(name: 'MF_UPSTREAM_BRANCH', value: BRANCH_NAME)], wait: false
	}   
} // timestamps   
} // node
