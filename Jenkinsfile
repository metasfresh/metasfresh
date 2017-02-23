#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md



/**
 * This method will be used further down to call additional jobs such as metasfresh-procurement and metasfresh-webui
 */
def invokeDownStreamJobs(String jobFolderName, String buildId, String upstreamBranch, String version, boolean wait)
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
			string(name: 'MF_UPSTREAM_BUILDNO', value: buildId),
			string(name: 'MF_UPSTREAM_VERSION', value: version),
			string(name: 'MF_UPSTREAM_JOBNAME', value: 'metasfresh-webui'),
			booleanParam(name: 'MF_TRIGGER_DOWNSTREAM_BUILDS', value: false), // the job shall just run but not trigger further builds because we are doing all the orchestration
			booleanParam(name: 'MF_SKIP_TO_DIST', value: true) // this param is only recognised by metasfresh
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
	  <!-- include mvn-public that contains everything we need to perform the build-->
      <repo-group-member>
        <name>mvn-public</name>
        <id>mvn-public</id>
        <resourceURI>https://repo.metasfresh.com/content/repositories/mvn-public/</resourceURI>
      </repo-group-member>
	  <!-- include ${repoId}-releases which is the repo to which we release everything we build within this branch -->
      <repo-group-member>
        <name>${repoId}-releases</name>
        <id>${repoId}-releases</id>
        <resourceURI>https://repo.metasfresh.com/content/repositories/${repoId}-releases/</resourceURI>
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
""";

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
        <value>3</value>
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
		string(defaultValue: '', 
			description: 'Build number of the upstream job that called us, if any.', 
			name: 'MF_UPSTREAM_BUILDNO'),
		string(defaultValue: '', 
			description: 'Version of the metasfresh "main" code we shall use when resolving dependencies. Leave empty and this build will use the latest.', 
			name: 'MF_UPSTREAM_VERSION'),
		booleanParam(defaultValue: true, description: 'Set to true if this build shall trigger "endcustomer" builds.<br>Set to false if this build is called from elsewhere and the orchestrating also takes place elsewhere', 
			name: 'MF_TRIGGER_DOWNSTREAM_BUILDS')
	]), 
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')) // keep the last 20 builds
	// , disableConcurrentBuilds() // concurrent builds are ok now. we still work with "-SNAPSHOTS" bit there is a unique MF_UPSTREAM_BUILDNO in each snapshot artifact's version
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
if(params.MF_UPSTREAM_BUILDNO)
{
	echo "Setting MF_UPSTREAM_BUILDNO from params.MF_UPSTREAM_BUILDNO=${params.MF_UPSTREAM_BUILDNO}"
	MF_UPSTREAM_BUILDNO=params.MF_UPSTREAM_BUILDNO
}
else
{
	echo "Setting MF_UPSTREAM_BUILDNO from env.BUILD_NUMBER=${env.BUILD_NUMBER}"
	MF_UPSTREAM_BUILDNO=env.BUILD_NUMBER
}

// set the version prefix, 1 for "master", 2 for "not-master" a.k.a. feature
final BUILD_VERSION_PREFIX = MF_UPSTREAM_BRANCH.equals('master') ? "1" : "2"
echo "Setting BUILD_VERSION_PREFIX=$BUILD_VERSION_PREFIX"

// the artifacts we build in this pipeline will have this version
// never incorporate params.MF_UPSTREAM_BUILDNO into the version anymore. Always go with the build number.
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

currentBuild.displayName="${MF_UPSTREAM_BRANCH} - build #${currentBuild.number} - artifact-version ${BUILD_VERSION}";
// note: going to set currentBuild.description after we deployed

timestamps 
{
node('agent && linux') // shall only run on a jenkins agent with linux
{
	stage('Preparation') // for display purposes
	{
		// checkout our code
		checkout([
			$class: 'GitSCM', 
			branches: [[name: "${env.BRANCH_NAME}"]], 
			doGenerateSubmoduleConfigurations: false, 
			extensions: [
				[$class: 'CleanCheckout']
			], 
			submoduleCfg: [], 
			userRemoteConfigs: [[credentialsId: 'github_metas-dev', url: 'https://github.com/metasfresh/metasfresh-webui.git']]
		])
	}

    configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
    {
        withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository') 
        {
			stage('Set versions and build metasfresh-webui-api') 
            {
				if(!isRepoExists(MF_MAVEN_REPO_NAME))
				{
					createRepo(MF_MAVEN_REPO_NAME);
				}

				final String mavenUpdateParentParam=''; // empty string for now. Uncomment the two assignements in the if and else *if* and when metasfresh-webui switcheds its parent pom to de.metas.parent
				final String mavenUpdatePropertyParam;
				if(params.MF_UPSTREAM_VERSION)
				{
					// mavenUpdateParentParam="-DparentVersion=${params.MF_UPSTREAM_VERSION}"
					mavenUpdatePropertyParam="-Dproperty=metasfresh.version -DnewVersion=${params.MF_UPSTREAM_VERSION}"; // update the property, use the metasfresh version that we were given by the upstream job
				}
				else
				{
					// mavenUpdateParentParam=''; 
					mavenUpdatePropertyParam='-Dproperty=metasfresh.version' // still update the property, but use the latest version
				}

				// update the parent pom version
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${mavenUpdateParentParam} versions:update-parent"

				// update the metasfresh.version property. either to the latest version or to the given params.MF_UPSTREAM_VERSION.
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${mavenUpdatePropertyParam} versions:update-property"
		
				// set the artifact version of everything below the webui's pom.xml
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode -DnewVersion=${BUILD_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=false -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas.ui.web*:*\" ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:set"
				
				// do the actual building and deployment
				// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode -Dmaven.test.failure.ignore=true ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${MF_MAVEN_TASK_DEPLOY_PARAMS} clean deploy"

				final BUILD_ARTIFACT_URL="https://repo.metasfresh.com/content/repositories/${MF_MAVEN_REPO_NAME}/de/metas/ui/web/metasfresh-webui-api/${BUILD_VERSION}/metasfresh-webui-api-${BUILD_VERSION}.jar";
				
				// IMPORTANT: we might parse this build description's href value in downstream builds!
currentBuild.description="""artifacts (if not yet cleaned up)
				<ul>
<li><a href=\"${BUILD_ARTIFACT_URL}\">metasfresh-webui-api-${BUILD_VERSION}.jar</a></li>
</ul>"""

				junit '**/target/surefire-reports/*.xml'

				// gh #968:
				// set env variables which will be available to a possible upstream job that might have called us
				// all those env variables can be gotten from <buildResultInstance>.getBuildVariables()
				env.BUILD_ARTIFACT_URL="${BUILD_ARTIFACT_URL}";
				env.BUILD_CHANGE_URL="${env.CHANGE_URL}";
				env.BUILD_VERSION="${BUILD_VERSION}";
            }
		}
	}
 } // node

if(params.MF_TRIGGER_DOWNSTREAM_BUILDS)
{
	stage('Invoke downstream job') 
	{
		invokeDownStreamJobs('metasfresh', MF_UPSTREAM_BUILDNO, MF_UPSTREAM_BRANCH, BUILD_VERSION, false); // wait=false 
	}
}
else
{
	echo "params.MF_TRIGGER_DOWNSTREAM_BUILDS=${params.MF_TRIGGER_DOWNSTREAM_BUILDS}, so we do not trigger any downstream builds"
}
} // timestamps   
