#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

/**
 * This method will be used further down to call additional jobs such as metasfresh-procurement and metasfresh-webui
 */
def invokeDownStreamJobs(String jobFolderName, String buildId, String upstreamBranch)
{
	echo "Invoking downstream job from folder=${jobFolderName} with preferred branch=${upstreamBranch}"
	
	// if this is not the master branch but a feature branch, we need to find out if the "BRANCH_NAME" job exists or not
	//
	// Here i'm not checking if the build job exists but if the respective branch on github exists. If the branch is there, then I assume that the multibranch plugin also created the job
	def exitCode;
	node('linux')
	{
		// We run this within a node to avoid the error saying:
		// Required context class hudson.FilePath is missing
		// Perhaps you forgot to surround the code with a step that provides this, such as: node
		// ...
		// org.jenkinsci.plugins.workflow.steps.MissingContextVariableException: Required context class hudson.FilePath is missing
		
		exitCode = sh returnStatus: true, script: "git ls-remote --exit-code https://github.com/metasfresh/metasfresh ${upstreamBranch}"
	}
	if(exitCode == 0)
	{
		echo "Branch ${upstreamBranch} also exists in ${jobFolderName}"
		jobName = jobFolderName + "/" + upstreamBranch
	}
	else 
	{
		echo "Branch ${upstreamBranch} does not exist in ${jobFolderName}; falling back to master"
		jobName = jobFolderName + "/master"
	}
	
	// I also tried
	// https://jenkins.metasfresh.com/job/metasfresh-multibranch/api/xml?tree=jobs[name] 
	// which worked from chrome, also for metas-dev.
	// It worked from the shell using curl (with [ and ] escaped) for user metas-ts and an access token,
	// but did not work from the shell with curl and user metas-dev with "metas-dev is missing the Overall/Read permission"
	// the curl string was sh "curl -XGET 'https://jenkins.metasfresh.com/job/metasfresh-multibranch/api/xml?tree=jobs%5Bname%5D' --user metas-dev:access-token
	
	// and I also tried inspecting the list returned by 
	// Jenkins.instance.getAllItems()
	// but there I got a scurity exception and am not sure if an how I can have a SCM maintained script that is approved by an admin
	
	build job: jobName, 
		parameters: [
			string(name: 'MF_UPSTREAM_BRANCH', value: upstreamBranch),
			string(name: 'MF_BUILD_ID', value: buildId),
			booleanParam(name: 'MF_TRIGGER_DOWNSTREAM_BUILDS', value: false) // the job shall just run but not trigger further builds because we are doing all the orchestration
		], wait: true
}

//
// setup: we'll need the following variables in different stages, that's we we create them here
//

// thx to http://stackoverflow.com/a/36949007/1012103 with respect to the paramters
// disabling concurrent builds as long as we work with "SNAPSHOTS"
properties([
	[$class: 'GithubProjectProperty', displayName: '', projectUrlStr: 'https://github.com/metasfresh/metasfresh-webui/'], 
	parameters([
		string(defaultValue: '', 
			description: '''If this job is invoked via an updstream build job, than that job can provide either its branch or the respective <code>MF_UPSTREAM_BRANCH</code> that was passed to it.<br>
This build will then attempt to use maven dependencies from that branch, and it will sets its own name to reflect the given value.
<p>
So if this is a "master" build, but it was invoked by a "feature-branch" build then this build will try to get the feature-branch\'s build artifacts annd will set its
<code>currentBuild.displayname</code> and <code>currentBuild.description</code> to make it obvious that the build contains code from the feature branch.''', 
			name: 'MF_UPSTREAM_BRANCH'),
		booleanParam(defaultValue: true, description: '''Set to true if this build shall trigger "endcustomer" builds.<br>
Set to false if this build is called from elsewhere and the orchestrating also takes place elsewhere''', 
			name: 'MF_TRIGGER_DOWNSTREAM_BUILDS'),
		string(defaultValue: '', 
			description: 'Will be incorporated into the artifact version and forwarded to jobs triggered by this job. Leave empty to go with <code>env.BUILD_NUMBER</code>', 
			name: 'MF_BUILD_ID')
	]), 
	pipelineTriggers([]), 
	disableConcurrentBuilds()
])

def BRANCH_NAME
if(params.MF_UPSTREAM_BRANCH)
{
	echo "Setting BRANCH_NAME from params.MF_UPSTREAM_BRANCH=${params.MF_UPSTREAM_BRANCH}"
	BRANCH_NAME=params.MF_UPSTREAM_BRANCH
}
else
{
	echo "Setting BRANCH_NAME from env.BRANCH_NAME=${env.BRANCH_NAME}"
	BRANCH_NAME=env.BRANCH_NAME
}

def MF_BUILD_ID
if(params.MF_BUILD_ID)
{
	echo "Setting MF_BUILD_ID from params.MF_BUILD_ID=${params.MF_BUILD_ID}"
	MF_BUILD_ID=params.MF_BUILD_ID
}
else
{
	echo "Setting MF_BUILD_ID from env.BUILD_NUMBER=${env.BUILD_NUMBER}"
	MF_BUILD_ID=env.BUILD_NUMBER
}

// set the version prefix, 1 for "master", 2 for "not-master" a.k.a. feature
def BUILD_MAVEN_VERSION_PREFIX = BRANCH_NAME.equals('master') ? "1" : "2"

// the maven artifact version that will be set to the artifacts in this build
// Thanks to https://zeroturnaround.com/rebellabs/jenkins-protip-artifact-propagation/ for the idea of incorporating the a build-number into the artifact version
// examples: "1-master-543-SNAPSHOT", "2-FRESH-123-9842-SNAPSHOT"
def BUILD_MAVEN_VERSION=BUILD_MAVEN_VERSION_PREFIX + "-" + BRANCH_NAME + "-" + MF_BUILD_ID + "-SNAPSHOT"

// the version range used when resolving depdendencies for this build
// example: "[1-master-SNAPSHOT],[2-FRESH-123-SNAPSHOT]
def BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION="[1-master-SNAPSHOT],["+BUILD_MAVEN_VERSION+"]"

echo "Setting BUILD_MAVEN_VERSION_PREFIX=$BUILD_MAVEN_VERSION_PREFIX"
echo "Setting BUILD_MAVEN_VERSION=$BUILD_MAVEN_VERSION"
echo "Setting BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION=$BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION"

currentBuild.description="Parameter MF_UPSTREAM_BRANCH="+params.MF_UPSTREAM_BRANCH
currentBuild.displayName="#" + currentBuild.number + "-" + BRANCH_NAME + "-" + MF_BUILD_ID

timestamps 
{
node('agent && linux')
{
	configFileProvider([configFile(fileId: 'aa1d8797-5020-4a20-aa7b-2334c15179be', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
	{
		withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M -XX:MaxHeapSize=512m') 
		{	
			stage('Preparation') // for display purposes
			{	
				// use this line in the "real" multibranch pipeline builds
				// checkout scm
				
				// use this line when developing this scrip in a normal pipeline job
				git branch: "$BRANCH_NAME", url: 'https://github.com/metasfresh/metasfresh.git'
			}
		
			// Note: we can't build the "main" and "esb" stuff in parallel, because the esb stuff depends on (at least!) de.metas.printing.api
            stage('Set versions and build metasfresh') 
            {
				// output them to make things more clear
                echo "BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION}"
                echo "BUILD_MAVEN_VERSION=${BUILD_MAVEN_VERSION}"

				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
				
                // set the artifact version of everything below de.metas.parent/pom.xml
				// IMPORTANT: do not set versions for de.metas.endcustomer.mf15/pom.xml, because that one will be build in another node!
							
        		// deploy the de.metas.parent pom.xml to our "permanent" snapshot repo. Other projects that are not build right now also need it. Don't do anything with the modules that are declared in there
        		sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode --non-recursive --activate-profiles metasfresh-perm-snapshots-repo clean deploy"
        
				// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
        		sh "mvn --settings $MAVEN_SETTINGS --file de.metas.reactor/pom.xml --batch-mode -Dmetasfresh-dependency.version=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -Dmaven.test.failure.ignore=true clean deploy"
            }

            stage('Set versions and build esb') 
            {
				// set the artifact version of everything below de.metas.esb/pom.xml
	            sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
			
				// maven.test.failure.ignore=true: see metasfresh stage
    		    sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -Dmetasfresh-dependency.version=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -Dmaven.test.failure.ignore=true clean deploy"
            }
			
			// collect test results
			junit '**/target/surefire-reports/*.xml'
				
			// TODO: notify zapier that the "main" stuff was build
		} // withMaven
	} // configFileProvider
} // node			

// invoke external build jobs like webui
// wait for the results, but don't block a node for it
stage('Invoke downstream jobs') 
{
	invokeDownStreamJobs('metasfresh-webui', MF_BUILD_ID, BRANCH_NAME)
	// more do come: admin-webui, procurement-webui, maybe the webui-javascript frontend too 
}

	
// to build the client-exe on linux, we need 32bit libs!
node('agent && linux && libc6-i386')
{
	configFileProvider([configFile(fileId: 'aa1d8797-5020-4a20-aa7b-2334c15179be', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
	{
		withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M -XX:MaxHeapSize=512m') 
		{
			stage('Build dist') 
			{
				// *Now* set the artifact version of everything below de.metas.endcustomer.mf15/pom.xml
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.endcustomer.mf15/pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
			
				// maven.test.failure.ignore=true: see metasfresh stage
				// we currently deploy *and* also archive, but that might change in future
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.endcustomer.mf15/pom.xml --batch-mode -Dmetasfresh-dependency.version=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -Dmaven.test.failure.ignore=true clean deploy"
		
				// endcustomer.mf15 currently has no tests, so we allow empty results.
				junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
				
			  	// we currently deploy *and* also archive, but that might change in future
				// archiveArtifacts 'de.metas.endcustomer.mf15/de.metas.endcustomer.mf15.dist/target/*.tar.gz,de.metas.endcustomer.mf15/de.metas.endcustomer.mf15.swingui/target/*.zip,de.metas.endcustomer.mf15/de.metas.endcustomer.mf15.swingui/target/*.exe'
			}
		} // withMaven
	} // configFileProvider

	
	stage('Deployement')
	{
		def userInput = input message: 'Deploy artifacts?', 
		parameters: [
			string(defaultValue: '', description: 'host to deploy the webui to', name: 'MF_metasfresh-webui-api_TARGET_HOST'), 
			string(defaultValue: '', description: 'host to deploy the swing client to', name: 'MF_de.metas.endcustomer.mf15.swingui_TARGET_HOST'), 
			string(defaultValue: '', description: '''Host to deploy the SQL migration script to. The scripts will also applied in this host.
It doesn\'t need to be the DB, but needs to have java and a local_setting.properties file.
Leve empty to deploy the SQL on <code>MF_de.metas.endcustomer.mf15.dist_TARGET_HOST</code>.''', name: 'MF_de.metas.endcustomer.mf15.dist-sql-only_TARGET_HOST'), 
			string(defaultValue: '', description: 'Host to deploy the "main" metasfresh backend server to.', name: 'MF_de.metas.endcustomer.mf15.dist_TARGET_HOST')
		]

		node('master')
		{
			sh "scp ${WORKSPACE}/de.metas.endcustomer.mf15.swingui-2-FRESH-112-SNAPSHOT-client.zip ${TARGET_USER}@${TARGET_HOST}/home/metasfresh/metasfresh-client.zip"
			sh "ssh ${TARGET_USER}@${TARGET_HOST} \"/bin/bash /home/metasfresh/install_client.sh\""
		}	
	}
	
} // node
} // timestamps

