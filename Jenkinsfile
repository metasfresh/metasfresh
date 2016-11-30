#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

/**
 * This method will be used further down to call additional jobs such as metasfresh-procurement and metasfresh-webui
 */
def invokeDownStreamJobs(String jobFolderName, String buildId, String upstreamBranch, boolean wait)
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
		
		exitCode = sh returnStatus: true, script: "git ls-remote --exit-code https://github.com/metasfresh/${jobFolderName} ${upstreamBranch}"
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
		], wait: wait
}

def boolean isRepoExists(String repoId)
{
	withCredentials([usernameColonPassword(credentialsId: 'nexus_jenkins', variable: 'NEXUS_LOGIN')])
	{
		echo "Check if the nexus repository ${repoId} exists";

		// check if there is a repository for ur branch
		final String checkForRepoCommand = "curl --silent -X GET -u ${NEXUS_LOGIN} https://repo.metasfresh.com/service/local/repositories | grep '<id>${repoId}-releases</id>'";
		final grepExitCode = sh returnStatus: true, script: checkForRepoCommand;
		final repoExists = grepExitCode == 0;

		echo "The nexus repository ${repoId} exists: ${repoExists}";
		return repoExists;
	}
}

def createRepo(String repoId)
{
	withCredentials([usernameColonPassword(credentialsId: 'nexus_jenkins', variable: 'NEXUS_LOGIN')])
	{
		echo "Create the repository ${repoId}-releases";

		final String createRepoPayload = """<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<repository>
  <data>
	<id>${repoId}-releases</id>
	<name>${repoId}-releases</name>
	<exposed>true</exposed>
	<repoType>hosted</repoType>
	<writePolicy>ALLOW_WRITE_ONCE</writePolicy>
    <browseable>true</browseable>
    <indexable>true</indexable>
	<repoPolicy>RELEASE</repoPolicy>
	<providerRole>org.sonatype.nexus.proxy.repository.Repository</providerRole>
	<provider>maven2</provider>
	<format>maven2</format>
  </data>
</repository>
""";

		// # nexus ignored application/json
		final String createRepoCommand =  "curl --silent -H \"Content-Type: application/xml\" -X POST -u ${NEXUS_LOGIN} -d \'${createRepoPayload}\' https://repo.metasfresh.com/service/local/repositories"
		sh "${createRepoCommand}"
		
		echo "Create the repository-group ${repoId}";
		
		final String createGroupPayload = """<?xml version="1.0" encoding="UTF-8"?>
<repo-group>
  <data>
    <repositories>
      <repo-group-member>
        <name>${repoId}-releases</name>
        <id>${repoId}-releases</id>
        <resourceURI>https://repo.metasfresh.com/content/repositories/${repoId}-releases/</resourceURI>
      </repo-group-member>
      <repo-group-member>
        <name>mvn-public-new</name>
        <id>mvn-public-new</id>
        <resourceURI>https://repo.metasfresh.com/content/repositories/mvn-public-new/</resourceURI>
      </repo-group-member>
    </repositories>
    <name>${repoId}</name>
    <repoType>group</repoType>
    <providerRole>org.sonatype.nexus.proxy.repository.Repository</providerRole>
    <exposed>true</exposed>
    <id>${repoId}</id>
	<provider>maven2</provider>
	<format>maven2</format>
  </data>
</repo-group>
"""

		// # nexus ignored application/json
		final String createGroupCommand =  "curl --silent -H \"Content-Type: application/xml\" -X POST -u ${NEXUS_LOGIN} -d \'${createGroupPayload}\' https://repo.metasfresh.com/service/local/repo_groups"
		sh "${createGroupCommand}"

		echo "Create the scheduled task to keep ${repoId}-releases from growing too big";
		
final String createSchedulePayload = """<?xml version="1.0" encoding="UTF-8"?>
<scheduled-task>
  <data>
	<id>cleanup-repo-${repoId}-releases</id>
	<enabled>true</enabled>
	<name>Remove Releases from ${repoId}-releases</name>
	<typeId>ReleaseRemoverTask</typeId>
	<schedule>daily</schedule>
	<startDate>${currentBuild.startTimeInMillis}</startDate>
	<recurringTime>03:00</recurringTime>
	<properties>
      <scheduled-task-property>
        <key>numberOfVersionsToKeep</key>
        <value>1</value>
      </scheduled-task-property>
      <scheduled-task-property>
        <key>indexBackend</key>
        <value>false</value>
      </scheduled-task-property>
      <scheduled-task-property>
        <key>repositoryId</key>
        <value>${repoId}-releases</value>
      </scheduled-task-property>
	</properties>
  </data>
</scheduled-task>"""
	
		// # nexus ignored application/json
		final String createScheduleCommand =  "curl --silent -H \"Content-Type: application/xml\" -X POST -u ${NEXUS_LOGIN} -d \'${createSchedulePayload}\' https://repo.metasfresh.com/service/local/schedules"
		sh "${createScheduleCommand}"		
	} // withCredentials
}

def deleteRepo(String repoId)
{
	withCredentials([usernameColonPassword(credentialsId: 'nexus_jenkins', variable: 'NEXUS_LOGIN')])
	{
		echo "Delete the repository ${repoId}";
		
		final String deleteGroupCommand = "curl --silent -X DELETE -u ${NEXUS_LOGIN} https://repo.metasfresh.com/service/local/repo_groups/${repoId}"
		sh "${deleteGroupCommand}"
		
		final String deleteRepoCommand = "curl --silent -X DELETE -u ${NEXUS_LOGIN} https://repo.metasfresh.com/service/local/repositories/${repoId}-releases"
		sh "${deleteRepoCommand}"
		
		final String deleteScheduleCommand = "curl --silent -X DELETE -u ${NEXUS_LOGIN} https://repo.metasfresh.com/service/local/schedules/cleanup-repo-${repoId}-releases"
		sh "${deleteScheduleCommand}"
	}
}

//
// setup: we'll need the following variables in different stages, that's we we create them here
//

// thx to http://stackoverflow.com/a/36949007/1012103 with respect to the paramters
properties([
	parameters([
		string(defaultValue: '', 
			description: '''If this job is invoked via an updstream build job, then that job can provide either its branch or the respective <code>MF_UPSTREAM_BRANCH</code> that was passed to it.<br>
This build will then attempt to use maven dependencies from that branch, and it will sets its own name to reflect the given value.
<p>
So if this is a "master" build, but it was invoked by a "feature-branch" build then this build will try to get the feature-branch\'s build artifacts annd will set its
<code>currentBuild.displayname</code> and <code>currentBuild.description</code> to make it obvious that the build contains code from the feature branch.''', 
			name: 'MF_UPSTREAM_BRANCH'),
		booleanParam(defaultValue: false, description: '''Set to true to skip over the stage that creates a copy of our reference DB and then applies the migration script to it to look for trouble with the migration.''', 
			name: 'MF_SKIP_SQL_MIGRATION_TEST'),
		booleanParam(defaultValue: (env.BRANCH_NAME != 'master' && env.BRANCH_NAME != 'stable' && env.BRANCH_NAME != 'FRESH-112'), description: '''If this is true, then there will be a deployment step at the end of this pipeline.
Task branch builds are usually not deployed, so the pipeline can finish without waiting.''', 
			name: 'MF_SKIP_DEPLOYMENT'),
		booleanParam(defaultValue: false, description: '''Set to true to only create the distributable files and assume that the underlying jars were already created and deployed''', 
			name: 'MF_SKIP_TO_DIST'),	
		string(defaultValue: '', 
			description: 'Will be forwarded to jobs triggered by this job. Leave empty to go with <code>env.BUILD_NUMBER</code>', 
			name: 'MF_BUILD_ID')
	]), 
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')) // keep the last 20 builds
	// , disableConcurrentBuilds() // concurrent builds are ok now. we still work with "-SNAPSHOTS" bit there is a unique MF_BUILD_ID in each snapshot artifact's version
])

final MF_UPSTREAM_BRANCH;
if(params.MF_UPSTREAM_BRANCH)
{
	echo "Setting MF_UPSTREAM_BRANCH from params.MF_UPSTREAM_BRANCH=${params.MF_UPSTREAM_BRANCH}"
	MF_UPSTREAM_BRANCH=params.MF_UPSTREAM_BRANCH
}
else
{
	echo "Setting MF_UPSTREAM_BRANCH from env.BRANCH_NAME=${env.BRANCH_NAME}"
	MF_UPSTREAM_BRANCH=env.BRANCH_NAME
}
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
final BUILD_VERSION_PREFIX = MF_UPSTREAM_BRANCH.equals('master') ? "1" : "2"
echo "Setting BUILD_VERSION_PREFIX=$BUILD_VERSION_PREFIX"

// the artifacts we build in this pipeline will have this version
// never incorporate params.MF_BUILD_ID into the version anymore. Always go with the build number.
final BUILD_VERSION=BUILD_VERSION_PREFIX + "." + env.BUILD_NUMBER;
echo "Setting BUILD_VERSION=$BUILD_VERSION"

// metasfresh-task-repo is a constrant (does not depent or the task/branch name) so that maven can find the credentials in our provided settings.xml file
final MF_MAVEN_REPO_ID = "metasfresh-task-repo";
echo "Setting MF_MAVEN_REPO_ID=$MF_MAVEN_REPO_ID";

// name of the task/branch specific maven nexus-repository that we will create if it doesn't exist and and resolve from
final MF_MAVEN_REPO_NAME = "mvn-${MF_UPSTREAM_BRANCH}";
echo "Setting MF_MAVEN_REPO_NAME=$MF_MAVEN_REPO_NAME";

final MF_MAVEN_REPO_URL = "https://repo.metasfresh.com/content/repositories/${MF_MAVEN_REPO_NAME}";
echo "Setting MF_MAVEN_REPO_URL=$MF_MAVEN_REPO_URL";

final MF_MAVEN_TASK_RESOLVE_PARAMS="-Dtask-repo-id=${MF_MAVEN_REPO_ID} -Dtask-repo-name=\"${MF_MAVEN_REPO_NAME}\" -Dtask-repo-url=\"${MF_MAVEN_REPO_URL}\"";
echo "Setting MF_MAVEN_TASK_RESOLVE_PARAMS=$MF_MAVEN_TASK_RESOLVE_PARAMS";

// the repository to which we are going to deploy
final MF_MAVEN_DEPLOY_REPO_URL = "https://repo.metasfresh.com/content/repositories/${MF_MAVEN_REPO_NAME}-releases";
echo "Setting MF_MAVEN_DEPLOY_REPO_URL=$MF_MAVEN_DEPLOY_REPO_URL";

// provide these cmdline params to all maven invocations that do a deploy
// deploy-repo-id=metasfresh-task-repo so that maven can find the credentials in our provided settings.xml file
final MF_MAVEN_TASK_DEPLOY_PARAMS = "-DaltDeploymentRepository=\"${MF_MAVEN_REPO_ID}::default::${MF_MAVEN_DEPLOY_REPO_URL}\"";
echo "Setting MF_MAVEN_TASK_DEPLOY_PARAMS=$MF_MAVEN_TASK_DEPLOY_PARAMS";

currentBuild.description="Parameter MF_UPSTREAM_BRANCH="+params.MF_UPSTREAM_BRANCH;
currentBuild.displayName="#" + currentBuild.number + "-" + MF_UPSTREAM_BRANCH + "-" + MF_BUILD_ID;


timestamps 
{
if(params.MF_SKIP_TO_DIST)
{
		echo "params.MF_SKIP_TO_DIST=true so don't build metasfresh and esb jars and don't invoke downstream jobs"
}
else
{
node('agent && linux')
{
	configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
	{
		withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M') 
		{
			// Note: we can't build the "main" and "esb" stuff in parallel, because the esb stuff depends on (at least!) de.metas.printing.api
            stage('Set versions and build metasfresh') 
            {
				if(!isRepoExists(MF_MAVEN_REPO_NAME))
				{
					createRepo(MF_MAVEN_REPO_NAME);
				}

				// checkout our code. 
				// git branch: "${env.BRANCH_NAME}", url: 'https://github.com/metasfresh/metasfresh.git'
				// we use this more complicated approach because that way we can also clean the workspace after checkout ('CleanCheckout') and we can ignore edits in ReleaseNotes, Readme etc
				checkout([
					$class: 'GitSCM', 
					branches: [[name: "${env.BRANCH_NAME}"]], 
					doGenerateSubmoduleConfigurations: false, 
					extensions: [
						[$class: 'PathRestriction', excludedRegions: '''ReleaseNotes\\.md
README\\.md
CONTRIBUTING\\.md
CODE_OF_CONDUCT\\.md''', includedRegions: ''],
						[$class: 'CleanCheckout']
					], 
					submoduleCfg: [], 
					userRemoteConfigs: [[credentialsId: 'github_metas-dev', url: 'https://github.com/metasfresh/metasfresh.git']]
				])
			
				// deploy de.metas.parent/pom.xml as it is now (still with version "1.0.0") so that other nodes can find it when they modify their own pom.xml versions
				// doesn't work because 1.0.0 is not SNAPSHOT anymore and there is already a 1.0.0 parent pom
				// sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode --non-recursive ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${MF_MAVEN_TASK_DEPLOY_PARAMS} clean deploy"
				
				// set the artifact version of everything below de.metas.parent/pom.xml
				// do not set versions for de.metas.endcustomer.mf15/pom.xml, because that one will be build in another node!
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode -DnewVersion=${BUILD_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -DprocessPlugins=true -Dincludes=\"de.metas*:*\" ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:set"

				// deploy the de.metas.parent pom.xml to our repo. Other projects that are not build right now on this node will also need it. But don't build the modules that are declared in there
        		sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode --non-recursive ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${MF_MAVEN_TASK_DEPLOY_PARAMS} clean deploy";
				
				// update the versions of metas dependencies that are external to our reactor modules
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.reactor/pom.xml --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:use-latest-versions"
        
				// build and deploy
				// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
				// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.reactor/pom.xml --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${BUILD_VERSION} ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${MF_MAVEN_TASK_DEPLOY_PARAMS} clean deploy";
            }

            stage('Set versions and build esb') 
            {
				// set the artifact version of everything below de.metas.esb/pom.xml
	           	sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -DnewVersion=${BUILD_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:set"
				
				// update the versions of metas dependencies that are external to the de.metas.esb reactor modules
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:use-latest-versions"
						
				// build and deploy
				// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
				// maven.test.failure.ignore=true: see metasfresh stage
    		    sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${BUILD_VERSION} ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${MF_MAVEN_TASK_DEPLOY_PARAMS} clean deploy"
            }
			
			// collect the test results
			junit '**/target/surefire-reports/*.xml'

		} // withMaven
	} // configFileProvider
} // node			

// invoke external build jobs like webui
// wait for the results, but don't block a node for it
stage('Invoke downstream jobs') 
{
	invokeDownStreamJobs('metasfresh-webui', MF_BUILD_ID, MF_UPSTREAM_BRANCH, true); // wait=true
	invokeDownStreamJobs('metasfresh-procurement-webui', MF_BUILD_ID, MF_UPSTREAM_BRANCH, true); // wait=true
	// more do come: admin-webui, maybe the webui-javascript frontend too

	// now that the "basic" build is done, notify zapier so we can do further things external to this jenkins instance
	node('linux')
	{	
		withCredentials([string(credentialsId: 'zapier-metasfresh-build-notification-webhook', variable: 'ZAPPIER_WEBHOOK_SECRET')]) 
		{
			final webhookUrl = "https://hooks.zapier.com/hooks/catch/${ZAPPIER_WEBHOOK_SECRET}"
			final jsonPayload = "{\"MF_BUILD_ID\":\"${MF_BUILD_ID}\",\"MF_UPSTREAM_BRANCH\":\"${MF_UPSTREAM_BRANCH}\"}"
			
			sh "curl -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X POST -d \'${jsonPayload}\' ${webhookUrl}"
		}
	}
}
} // if(params.MF_SKIP_TO_DIST)
	
// to build the client-exe on linux, we need 32bit libs!
node('agent && linux && libc6-i386')
{
	configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
	{
		withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository') 
		{
			stage('Build dist') 
			{
				// checkout our code
				// note that we do not know if the stuff we checked out in the other node is available here, so we somehow need to make sure by checking out (again).
				// see: https://groups.google.com/forum/#!topic/jenkinsci-users/513qLiYlXHc
				checkout([
					$class: 'GitSCM', 
					branches: [[name: "${env.BRANCH_NAME}"]], 
					doGenerateSubmoduleConfigurations: false, 
					extensions: [
						[$class: 'CleanCheckout'], 
						// [$class: 'SparseCheckoutPaths', sparseCheckoutPaths: [[path: '/de.metas.endcustomer.mf15']]] // failed if there was stale stuff checked out around /de.metas.endcustomer.mf15
					], 
					submoduleCfg: [], 
					userRemoteConfigs: [[credentialsId: 'github_metas-dev', url: 'https://github.com/metasfresh/metasfresh.git']]
				])
		
				// *Now* set the artifact versions of everything below de.metas.endcustomer.mf15/pom.xml and also update their metas dependencies to the latest versions
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.endcustomer.mf15/pom.xml --batch-mode -DnewVersion=${BUILD_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:set"
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.endcustomer.mf15/pom.xml --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:use-latest-versions"
			
				// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
				// about -Dmaven.test.failure.ignore=true: see metasfresh stage
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.endcustomer.mf15/pom.xml --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${BUILD_VERSION} ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${MF_MAVEN_TASK_DEPLOY_PARAMS} clean deploy"

				// endcustomer.mf15 currently has no tests. Don't try to collect any, or a typical error migh look like this:
				// ERROR: Test reports were found but none of them are new. Did tests run? 
				// For example, /var/lib/jenkins/workspace/metasfresh_FRESH-854-gh569-M6AHOWSSP3FKCR7CHWVIRO5S7G64X4JFSD4EZJZLAT5DONP2ZA7Q/de.metas.acct.base/target/surefire-reports/TEST-de.metas.acct.impl.FactAcctLogBLTest.xml is 2 min 57 sec old
				// junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
			}
		} // withMaven
	} // configFileProvider

	// clean up the workspace, including the local maven repositories that the withMaven steps created
	// don't clean up the work space..we do it when we check out next time
	// step([$class: 'WsCleanup', cleanWhenFailure: true])
} // node

// we need this one for both "Test-SQL" and "Deployment
def downloadForDeployment = { String groupId, String artifactId, String packaging, String classifier, String sshTargetHost, String sshTargetUser ->

	final packagingPart=packaging ? ":${packaging}" : ""
	final classifierPart=classifier ? ":${classifier}" : ""
	final artifact = "${groupId}:${artifactId}:${BUILD_VERSION}${packagingPart}${classifierPart}"

	// we need configFileProvider because in mvn get -DremoteRepositories=https://repo.metasfresh.com/repository/mvn-public is ignored. 
	// See http://maven.apache.org/plugins/maven-dependency-plugin/get-mojo.html "Caveat: will always check thecentral repository defined in the super pom" 
	configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
	{
		withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository') 
		{
			sh "mvn --settings $MAVEN_SETTINGS org.apache.maven.plugins:maven-dependency-plugin:2.10:get -Dtransitive=false -Dartifact=${artifact} ${MF_MAVEN_TASK_RESOLVE_PARAMS}"
			
			// copy the artifact to a deploy folder.
			sh "mvn --settings $MAVEN_SETTINGS org.apache.maven.plugins:maven-dependency-plugin:2.10:copy -Dartifact=${artifact} -DoutputDirectory=deploy -Dmdep.stripClassifier=false -Dmdep.stripVersion=false ${MF_MAVEN_TASK_RESOLVE_PARAMS}"
		}
	}
	sh "scp ${WORKSPACE}/deploy/${artifactId}-${BUILD_VERSION}-${classifier}.${packaging} ${sshTargetUser}@${sshTargetHost}:/home/${sshTargetUser}/${artifactId}-${BUILD_VERSION}-${classifier}.${packaging}"
}

// we need this one for both "Test-SQL" and "Deployment
def invokeRemote = { String sshTargetHost, String sshTargetUser, String directory, String shellScript -> 

// no echo needed: the log already shows what's done via the sh step
//	echo "Going to invoke the following as user ${sshTargetUser} on host ${sshTargetHost} in directory ${directory}:";
//	echo "${shellScript}"
	sh "ssh ${sshTargetUser}@${sshTargetHost} \"cd ${directory} && ${shellScript}\"" 
}

if(params.MF_SKIP_SQL_MIGRATION_TEST)
{
	echo "We skip the deployment step because params.MF_SKIP_SQL_MIGRATION_TEST=${params.MF_SKIP_SQL_MIGRATION_TEST}"
}
else
{
	stage('Test SQL-Migration')
	{
		node('master')
		{
			final distArtifactId='de.metas.endcustomer.mf15.dist';
			final classifier='sql-only';
			final packaging='tar.gz';
			final sshTargetHost='mf15cloudit';
			final sshTargetUser='metasfresh'

			downloadForDeployment('de.metas.endcustomer.mf15', distArtifactId, packaging, classifier, sshTargetHost, sshTargetUser);

			final fileAndDirName="${distArtifactId}-${BUILD_VERSION}-${classifier}"
			final deployDir="/home/${sshTargetUser}/${fileAndDirName}"
			
			// Look Ma, I'm currying!!
			final invokeRemoteInHomeDir = invokeRemote.curry(sshTargetHost, sshTargetUser, "/home/${sshTargetUser}");				
			invokeRemoteInHomeDir("mkdir -p ${deployDir} && mv ${fileAndDirName}.${packaging} ${deployDir} && cd ${deployDir} && tar -xf ${fileAndDirName}.${packaging}")

			final invokeRemoteInInstallDir = invokeRemote.curry(sshTargetHost, sshTargetUser, "/home/${sshTargetUser}/${fileAndDirName}/dist/install");				
			final VALIDATE_MIGRATION_TEMPLATE_DB='mf15_template';
			final VALIDATE_MIGRATION_TEST_DB="mf15_cloud_it-${env.BUILD_NUMBER}-${BUILD_VERSION}"
					.replaceAll('[^a-zA-B0-9]', '_') // // postgresql is in a way is allergic to '-' and '.' and many other characters in in DB names
					.toLowerCase(); // also, DB names are generally in lowercase

			invokeRemoteInInstallDir("./sql_remote.sh -n ${VALIDATE_MIGRATION_TEMPLATE_DB} ${VALIDATE_MIGRATION_TEST_DB}");
			
			invokeRemoteInHomeDir("rm -r ${deployDir}"); // cleanup
		}
	}
}

if(params.MF_SKIP_DEPLOYMENT)
{
	echo "We skip the deployment step because params.MF_SKIP_DEPLOYMENT=${params.MF_SKIP_DEPLOYMENT}"
}
else
{
	stage('Deployment')
	{
		final userInput;

		try 
		{
			// after one day, snapshot artifacts will be purged from repo.metasfresh.com anyways
			timeout(time:1, unit:'DAYS') 
			{
				// use milestones to abort older builds as soon as a receent build is deployed
				// see https://wiki.jenkins-ci.org/display/JENKINS/Pipeline+Milestone+Step+Plugin
				milestone 1;
				userInput = input message: 'Deploy to server?', parameters: [string(defaultValue: 'mf15cloudit', description: 'Host to deploy the "main" metasfresh backend server to.', name: 'MF_TARGET_HOST')];
				milestone 2;
				echo "Received userInput=$userInput";
			}
		} 
		catch (error) 
		{
			userInput = null;
			echo "We hit the timeout or the deployment was canceled by a user; set userinput to NULL";
		}
	
		if(userInput)
		{
			node('master')
			{
				final distArtifactId='de.metas.endcustomer.mf15.dist';
				final classifier='dist';
				final packaging='tar.gz';
				final sshTargetHost=userInput;
				final sshTargetUser='metasfresh'

				// main part: provide and rollout the "main" distributable
				// get the deployable dist file to the target host
				downloadForDeployment('de.metas.endcustomer.mf15', distArtifactId, packaging, classifier, sshTargetHost, sshTargetUser);

				// extract the tar.gz
				final fileAndDirName="${distArtifactId}-${BUILD_VERSION}-${classifier}"
				final deployDir="/home/${sshTargetUser}/${fileAndDirName}"

				// Look Ma, I'm currying!!
				final invokeRemoteInHomeDir = invokeRemote.curry(sshTargetHost, sshTargetUser, "/home/${sshTargetUser}");				
				invokeRemoteInHomeDir("mkdir -p ${deployDir} && mv ${fileAndDirName}.${packaging} ${deployDir} && cd ${deployDir} && tar -xf ${fileAndDirName}.${packaging}")

				// stop the service, perform the rollout and start the service
				final invokeRemoteInInstallDir = invokeRemote.curry(sshTargetHost, sshTargetUser, "/home/${sshTargetUser}/${fileAndDirName}/dist/install");
				invokeRemoteInInstallDir('./stop_service.sh');
				invokeRemoteInInstallDir('./sql_remote.sh');
				invokeRemoteInInstallDir('./minor_remote.sh');
				invokeRemoteInInstallDir('./start_service.sh');

				// clean up what we just rolled out
				invokeRemoteInHomeDir("rm -r ${deployDir}")
				
				// also provide the webui-api and procurement-webui; TODO actually deploy them
				//downloadForDeployment('de.metas.ui.web', 'metasfresh-webui-api', 'jar', null, sshTargetHost, sshTargetUser);
				//downloadForDeployment('de.metas.procurement', 'de.metas.procurement.webui', 'jar', null, sshTargetHost, sshTargetUser);

				// clean up the workspace, including the local maven repositories that the withMaven steps created
				step([$class: 'WsCleanup', cleanWhenFailure: false])
			} // node
		}
		else
		{
			echo 'We skip the deployment step because no user clicked on "proceed" within the timeout.'
		} // if(userinput)
	} // stage
} // if(params.MF_SKIP_DEPLOYMENT)
} // timestamps

