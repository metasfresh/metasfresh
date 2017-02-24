#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

def String getEffectiveDownStreamJobName(final String jobFolderName, final String upstreamBranch)
{
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
	
	return jobName;
}

/**
 * This method will be used further down to call additional jobs such as metasfresh-procurement and metasfresh-webui.
 * 
 * TODO: move it into a shared library
 * IMPORTANT: i'm now wrapping up this work (i.e. https://github.com/metasfresh/metasfresh/issues/968) to do other things! it's not yet finsined or tested!
 *
 * @return the the build result's buildVariables (a map) which ususally also contain (to be set by our Jenkinsfiles):
 * <li>{@code BUILD_VERSION}: the version the maven artifacts were deployed with
 * <li>{@code BUILD_ARTIFACT_URL}: the URL on our nexus repos from where one can download the "main" artifact that was build and deplyoed
 *
 */
def Map invokeDownStreamJobs(final String jobFolderName, final String buildId, final String upstreamBranch, final String metasfreshVersion, final boolean wait)
{
	echo "Invoking downstream job from folder=${jobFolderName} with preferred branch=${upstreamBranch}"

	final String jobName = getEffectiveDownStreamJobName(jobFolderName, upstreamBranch);
	
	final buildResult = build job: jobName, 
		parameters: [
			string(name: 'MF_UPSTREAM_BRANCH', value: upstreamBranch),
			string(name: 'MF_UPSTREAM_BUILDNO', value: buildId), // can be used together with the upstream branch name to construct this upstream job's URL
			string(name: 'MF_UPSTREAM_VERSION', value: metasfreshVersion), // the downstream job shall use *this* metasfresh.version, as opposed to whatever is the latest at the time it runs
			booleanParam(name: 'MF_TRIGGER_DOWNSTREAM_BUILDS', value: false) // the job shall just run but not trigger further builds because we are doing all the orchestration
		], wait: wait
	;

	echo "Job invokation done; buildResult.getBuildVariables()=${buildResult.getBuildVariables()}"
	
	return buildResult.getBuildVariables();
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

// keep the last 20 builds for master and stable, but onkly the last 5 for the rest, to preserve disk space on jenkins
final numberOfBuildsToKeepStr = (MF_UPSTREAM_BRANCH == 'master' || MF_UPSTREAM_BRANCH == 'stable' || MF_UPSTREAM_BRANCH == 'FRESH-112') ? '20' : '5'

// thx to http://stackoverflow.com/a/36949007/1012103 with respect to the parameters
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
			description: 'Name of the upstream job which called us. Required only in conjunction with MF_UPSTREAM_VERSION', 
			name: 'MF_UPSTREAM_JOBNAME'),
		string(defaultValue: '',
			description: 'Version of the upstream job\'s artifact that was build by the job which called us. Shall used when resolving the upstream depdendency. Leave empty and this build will use the latest.', 
			name: 'MF_UPSTREAM_VERSION'),
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
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: numberOfBuildsToKeepStr)) // keep the last $numberOfBuildsToKeepStr builds
]);

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

// metasfresh-task-repo is a constant (does not depend or the task/branch name) so that maven can find the credentials in our provided settings.xml file
final MF_MAVEN_REPO_ID = "metasfresh-task-repo";
echo "Setting MF_MAVEN_REPO_ID=$MF_MAVEN_REPO_ID";

// name of the task/branch specific maven nexus-repository that we will create if it doesn't exist and and resolve from
// make sure the maven repo name is OK, to avoid an error message saying
// "Only letters, digits, underscores(_), hyphens(-), and dots(.) are allowed in Repository ID"
final MF_MAVEN_REPO_NAME = "mvn-${MF_UPSTREAM_BRANCH}".replaceAll('[^a-zA-Z0-9_-]', '_'); 
echo "Setting MF_MAVEN_REPO_NAME=$MF_MAVEN_REPO_NAME";


final MF_MAVEN_REPO_URL = "https://repo.metasfresh.com/content/repositories/${MF_MAVEN_REPO_NAME}";
echo "Setting MF_MAVEN_REPO_URL=$MF_MAVEN_REPO_URL";

// IMPORTANT: the task-repo-url which we set in MF_MAVEN_TASK_RESOLVE_PARAMS is used within the settings.xml that our jenkins provides to the build. That's why we need it in the mvn parameters
final MF_MAVEN_TASK_RESOLVE_PARAMS="-Dtask-repo-id=${MF_MAVEN_REPO_ID} -Dtask-repo-name=\"${MF_MAVEN_REPO_NAME}\" -Dtask-repo-url=\"${MF_MAVEN_REPO_URL}\"";
echo "Setting MF_MAVEN_TASK_RESOLVE_PARAMS=$MF_MAVEN_TASK_RESOLVE_PARAMS";

// the repository to which we are going to deploy
final MF_MAVEN_DEPLOY_REPO_URL = "https://repo.metasfresh.com/content/repositories/${MF_MAVEN_REPO_NAME}-releases";
echo "Setting MF_MAVEN_DEPLOY_REPO_URL=$MF_MAVEN_DEPLOY_REPO_URL";

// provide these cmdline params to all maven invocations that do a deploy
// deploy-repo-id=metasfresh-task-repo so that maven can find the credentials in our provided settings.xml file
// deployAtEnd=true so that
final MF_MAVEN_TASK_DEPLOY_PARAMS = "-DaltDeploymentRepository=\"${MF_MAVEN_REPO_ID}::default::${MF_MAVEN_DEPLOY_REPO_URL}\"";
echo "Setting MF_MAVEN_TASK_DEPLOY_PARAMS=$MF_MAVEN_TASK_DEPLOY_PARAMS";

// these two are shown in jenkins, for each build
currentBuild.displayName="${MF_UPSTREAM_BRANCH} - build #${currentBuild.number} - artifact-version ${BUILD_VERSION}";

timestamps 
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
				if(params.MF_SKIP_TO_DIST) 
				{
					echo "params.MF_SKIP_TO_DIST=true so don't build metasfresh and esb jars and don't invoke downstream jobs"
				}
				else
				{

				if(!isRepoExists(MF_MAVEN_REPO_NAME))
				{
					createRepo(MF_MAVEN_REPO_NAME);
				}

				checkout scm; // i hope this to do all the magic we need
				sh 'git clean -d --force -x' // clean the workspace

				// update the parent-pom version of our de.metas.parent/pom.xml to the latest from the metasfresh-parent project
				// --non-recursive is not strictly needed, but it will spare us a lot of messages saying "building blah..Project's parent is part of the reactor"
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode --non-recursive -DallowSnapshots=false -DgenerateBackupPoms=true ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:update-parent"

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
							
				} // if(params.MF_SKIP_TO_DIST)
            }

            stage('Set versions and build esb') 
            {
				if(params.MF_SKIP_TO_DIST) 
				{
					echo "params.MF_SKIP_TO_DIST=true so don't build metasfresh and esb jars and don't invoke downstream jobs"
				}
				else
				{

				// update the parent-pom version of our de.metas.esb/pom.xml to the latest from the metasfresh-parent project
				// --non-recursive is not strictly needed, but it will spare us a lot of messages saying "building blah..Project's parent is part of the reactor"
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode --non-recursive -DallowSnapshots=false -DgenerateBackupPoms=true ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:update-parent"

				// set the artifact version of everything below de.metas.esb/pom.xml
	           	sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -DnewVersion=${BUILD_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:set"
				
				// update the versions of metas dependencies that are external to the de.metas.esb reactor modules
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:use-latest-versions"
						
				// build and deploy
				// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
				// maven.test.failure.ignore=true: see metasfresh stage
    		    sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${BUILD_VERSION} ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${MF_MAVEN_TASK_DEPLOY_PARAMS} clean deploy"
								
				} // if(params.MF_SKIP_TO_DIST)
			}

			if(!params.MF_SKIP_TO_DIST) 
			{
				// collect the test results for the two preceeding stages. call this step once to avoid counting the tests twice.
				junit '**/target/surefire-reports/*.xml'
			}
			
		} // withMaven
	} // configFileProvider
} // node			

// this map is populated in the "Invoke downstream jobs" stage
final MF_ARTIFACT_VERSIONS = [:];

// invoke external build jobs like webui
// wait for the results, but don't block a node while waiting
// TODO: invoke them in parallel
stage('Invoke downstream jobs') 
{
	if(params.MF_SKIP_TO_DIST) 
	{
		echo "params.MF_SKIP_TO_DIST is true so don't build metasfresh and esb jars and don't invoke downstream jobs";

		// if params.MF_SKIP_TO_DIST is true, it might mean that we were invoked via a change in metasfresh-webui or metasfresh-webui-frontend.. 
		// note: if params.MF_UPSTREAM_JOBNAME is set, it means that we were called from upstream and therefore also params.MF_UPSTREAM_VERSION is set
		if(params.MF_UPSTREAM_JOBNAME == 'metasfresh-webui')
		{
			// note: we call it "metasfresh-webui" (as opposed to "metasfresh-webui-api"), because it's the repo's and the build job's name.
			MF_ARTIFACT_VERSIONS['metasfresh-webui']=params.MF_UPSTREAM_VERSION;
			echo "Set MF_ARTIFACT_VERSIONS.metasfresh-webui=${MF_ARTIFACT_VERSIONS['metasfresh-webui']}"
		}
		
		if(params.MF_UPSTREAM_JOBNAME == 'metasfresh-webui-frontend')
		{
			MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend']=params.MF_UPSTREAM_VERSION;
			echo "Set MF_ARTIFACT_VERSIONS.metasfresh-webui-frontend=${MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend']}"
		}
		
		if(params.MF_UPSTREAM_JOBNAME == 'metasfresh-procurement-webui')
		{
			MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui']=params.MF_UPSTREAM_VERSION;
			echo "Set MF_ARTIFACT_VERSIONS.metasfresh-procurement-webui=${MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui']}"
		}
		// TODO: also handle procurement-webui
	}
	else
	{
		MF_ARTIFACT_VERSIONS['metasfresh'] = BUILD_VERSION;
		
		// params.MF_SKIP_TO_DIST == false, so invoke downstream jobs and get the build versions which came out of them
		
//		 parallel 
//			"metasfresh-webui" : {
				// note: we call it "metasfresh-webui" (as opposed to "metasfresh-webui-api"), because it's the repo's and the build job's name.
				final webuiDownStreamJobMap = invokeDownStreamJobs('metasfresh-webui', MF_BUILD_ID, MF_UPSTREAM_BRANCH, BUILD_VERSION, true); // wait=true
				MF_ARTIFACT_VERSIONS['metasfresh-webui']=webuiDownStreamJobMap.BUILD_VERSION;
//			}, 
//			"metasfresh-procurement-webui" : {
				// yup, metasfresh-procurement-webui does share *some* code with this repo
				final procurementWebuiDownStreamJobMap = invokeDownStreamJobs('metasfresh-procurement-webui', MF_BUILD_ID, MF_UPSTREAM_BRANCH, BUILD_VERSION, true); // wait=true
				MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui']=procurementWebuiDownStreamJobMap.BUILD_VERSION;
//			},
//			failFast : true
//		;
			
		// gh #968: note that there is no point invoking metasfresh-webui-frontend from here. the frontend doesn't depend on this repo. 
		// Therefore we will just get the latest webui-frontend later, when we need it.

		// more to come: admin-webui
	} // if(params.MF_SKIP_TO_DIST)
	
	// complement the MF_ARTIFACT_VERSIONS we did not set so far
	MF_ARTIFACT_VERSIONS['metasfresh'] = MF_ARTIFACT_VERSIONS['metasfresh'] ?: "LATEST";
	MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'] = MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'] ?: "LATEST";
	MF_ARTIFACT_VERSIONS['metasfresh-webui'] = MF_ARTIFACT_VERSIONS['metasfresh-webui'] ?: "LATEST";
	MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'] = MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'] ?: "LATEST";

	// now that the "basic" build is done, notify zapier so we can do further things external to this jenkins instance
	// note: even with "skiptodis=true we do this, because we still want to make the notifcations
	echo "Going to notify external systems via zapier webhook"
	node('linux')
	{
		withCredentials([string(credentialsId: 'zapier-metasfresh-build-notification-webhook', variable: 'ZAPPIER_WEBHOOK_SECRET')]) 
		{
			final webhookUrl = "https://hooks.zapier.com/hooks/catch/${ZAPPIER_WEBHOOK_SECRET}/"
			
			/* we need to make sure we know "our own" MF_METASFRESH_VERSION, also if we were called by e.g. metasfresh-webui-api or metasfresh-webui--frontend */
			final jsonPayload = """{ 
				\"MF_UPSTREAM_BUILDNO\":\"${MF_BUILD_ID}\", 
				\"MF_UPSTREAM_BRANCH\":\"${MF_UPSTREAM_BRANCH}\", 
				\"MF_METASFRESH_VERSION\":\"${MF_ARTIFACT_VERSIONS['metasfresh']}\",
				\"MF_METASFRESH_PROCUREMENT_WEBUI_VERSION\":\"${MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui']}\",
				\"MF_METASFRESH_WEBUI_API_VERSION\":\"${MF_ARTIFACT_VERSIONS['metasfresh-webui']}\",
				\"MF_METASFRESH_WEBUI_FRONTEND_VERSION\":\"${MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend']}\"
			}""";
			// echo "jsonPayload=${jsonPayload}";

			sh "curl -X POST -d \'${jsonPayload}\' ${webhookUrl}";
		}
	}
	
	echo "Invoking downstream job 'metasfresh-dist' with preferred branch=${MF_UPSTREAM_BRANCH}"

	final String jobName = getEffectiveDownStreamJobName('metasfresh-dist', MF_UPSTREAM_BRANCH);
	
	final buildResult = build job: jobName, 
		parameters: [
			string(name: 'MF_UPSTREAM_BUILDNO', value: buildId), // can be used together with the upstream branch name to construct this upstream job's URL
			string(name: 'MF_UPSTREAM_BRANCH', value: MF_UPSTREAM_BRANCH),
			string(name: 'MF_METASFRESH_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh']), // the downstream job shall use *this* metasfresh.version, as opposed to whatever is the latest at the time it runs
			string(name: 'MF_METASFRESH_PROCUREMENT_WEBUI_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui']),
			string(name: 'MF_METASFRESH_WEBUI_API_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh-webui']),
			string(name: 'MF_METASFRESH_WEBUI_FRONTEND_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'])
		], wait: false
	;
}
} // timestamps

