#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.MvnConf
import de.metas.jenkins.Misc

/**
 * This method will be used further down to call additional jobs such as metasfresh-procurement and metasfresh-webui.
 *
 * TODO: move it into a shared library
 * IMPORTANT: i'm now wrapping up this work (i.e. https://github.com/metasfresh/metasfresh/issues/968) to do other things! it's not yet finsined or tested!
 *
 * @return the the build result's buildVariables (a map) which ususally also contain (to be set by our Jenkinsfiles):
 * <li>{@code MF_BUILD_VERSION}: the version the maven artifacts were deployed with
 * <li>{@code BUILD_ARTIFACT_URL}: the URL on our nexus repos from where one can download the "main" artifact that was build and deplyoed
 *
 */
Map invokeDownStreamJobs(
          final String buildId,
          final String upstreamBranch,
          final String parentPomVersion,
          final String metasfreshVersion,
          final boolean wait,
          final String jobFolderName
        )
{
	echo "Invoking downstream job from folder=${jobFolderName} with preferred branch=${upstreamBranch}"

  def misc = new de.metas.jenkins.Misc();
	final String jobName = misc.getEffectiveDownStreamJobName(jobFolderName, upstreamBranch);

	final buildResult = build job: jobName,
		parameters: [
			string(name: 'MF_UPSTREAM_BRANCH', value: upstreamBranch),
			string(name: 'MF_UPSTREAM_BUILDNO', value: buildId), // can be used together with the upstream branch name to construct this upstream job's URL
			string(name: 'MF_UPSTREAM_VERSION', value: metasfreshVersion), // the downstream job shall use *this* metasfresh.version, as opposed to whatever is the latest at the time it runs
			booleanParam(name: 'MF_TRIGGER_DOWNSTREAM_BUILDS', value: false) // the job shall just run but not trigger further builds because we are doing all the orchestration
		], wait: wait

	echo "Job invokation done; buildResult.getBuildVariables()=${buildResult.getBuildVariables()}"
	return buildResult.getBuildVariables();
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
final numberOfBuildsToKeepStr = (MF_UPSTREAM_BRANCH == 'master' || MF_UPSTREAM_BRANCH == 'stable') ? '20' : '5'

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
final MF_BUILD_VERSION_PREFIX = MF_UPSTREAM_BRANCH.equals('master') ? "1" : "2"
echo "Setting MF_BUILD_VERSION_PREFIX=$MF_BUILD_VERSION_PREFIX"

// the artifacts we build in this pipeline will have a version that ends with this string
final MF_BUILD_VERSION=MF_BUILD_VERSION_PREFIX + "-" + env.BUILD_NUMBER;
echo "Setting MF_BUILD_VERSION=$MF_BUILD_VERSION"

timestamps
{
// https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
final String MF_RELEASE_VERSION = retrieveReleaseInfo(MF_UPSTREAM_BRANCH);
echo "Retrieved MF_RELEASE_VERSION=${MF_RELEASE_VERSION}"
final String MF_VERSION="${MF_RELEASE_VERSION}.${MF_BUILD_VERSION}";
echo "set MF_VERSION=${MF_VERSION}";

// shown in jenkins, for each build
currentBuild.displayName="${MF_UPSTREAM_BRANCH} - build #${currentBuild.number} - artifact-version ${MF_VERSION}";

node('agent && linux')
{
	configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')])
	{
		// create our config instance to be used further on
		final MvnConf mvnConf = new MvnConf(
			'de.metas.parent/pom.xml', // pomFile
			MAVEN_SETTINGS, // settingsFile
			"mvn-${MF_UPSTREAM_BRANCH}", // mvnRepoName
			'https://repo.metasfresh.com' // mvnRepoBaseURL
		)
		echo "mvnConf=${mvnConf}"

		withMaven(jdk: 'java-8', maven: 'maven-3.5.0', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M')
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

        nexusCreateRepoIfNotExists mvnConf.mvnDeployRepoBaseURL, mvnConf.mvnRepoName

				checkout scm; // i hope this to do all the magic we need
				sh 'git clean -d --force -x' // clean the workspace

				// update the parent pom version
 				mvnUpdateParentPomVersion mvnConf

				// set the artifact version of everything below ${mvnConf.pomFile}
				// do not set versions for de.metas.endcustomer.mf15/pom.xml, because that one will be build in another node!
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -DprocessPlugins=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} versions:set"

				// deploy the de.metas.parent pom.xml to our repo. Other projects that are not build right now on this node will also need it. But don't build the modules that are declared in there
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode --non-recursive ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy";

 				final MvnConf mvnReactorConf = mvnConf.withPomFile('de.metas.reactor/pom.xml');
				echo "mvnReactorConf=${mvnReactorConf}"

				// update the versions of metas dependencies that are external to our reactor modules
				sh "mvn --settings ${mvnReactorConf.settingsFile} --file ${mvnReactorConf.pomFile} --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnReactorConf.resolveParams} versions:use-latest-versions"

				// build and deploy
				// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
				// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
				sh "mvn --settings ${mvnReactorConf.settingsFile} --file ${mvnReactorConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${MF_VERSION} ${mvnReactorConf.resolveParams} ${mvnReactorConf.deployParam} clean deploy";
				} // if(params.MF_SKIP_TO_DIST)
			}
			stage('Build metasfresh docker image(s)')
			{
				if(params.MF_SKIP_TO_DIST)
				{
					echo "params.MF_SKIP_TO_DIST=true so don't build metasfresh and esb jars and don't invoke downstream jobs"
				}
				else
				{
				// now create and publish some docker image..well, 1 docker image for starts
				final dockerWorkDir='docker-build/metasfresh-material-dispo'
				sh "mkdir -p ${dockerWorkDir}"

 				// copy the files so they can be handled by the docker build
				sh "cp de.metas.material/dispo-service/target/metasfresh-material-dispo-service-${MF_VERSION}.jar ${dockerWorkDir}/metasfresh-material-dispo-service.jar" // please keep in sync with DockerFile!
				sh "cp -R de.metas.material/dispo-service/src/main/docker/* ${dockerWorkDir}"
				sh "cp -R de.metas.material/dispo-service/src/main/configs ${dockerWorkDir}"
				docker.withRegistry('https://index.docker.io/v1/', 'dockerhub_metasfresh')
				{
					// note: we ommit the "-service" in the docker image name, because we also don't have "-service" in the webui-api and backend and it's pretty clear that it is a service
					def app = docker.build 'metasfresh/metasfresh-material-dispo', "${dockerWorkDir}";

          			def misc = new de.metas.jenkins.Misc();
					app.push misc.mkDockerTag("${MF_UPSTREAM_BRANCH}-latest");
					app.push misc.mkDockerTag("${MF_UPSTREAM_BRANCH}-${MF_VERSION}");
          if(MF_UPSTREAM_BRANCH=='release')
          {
          	echo 'MF_UPSTREAM_BRANCH=release, so we also push this with the "latest" tag'
          	app.push misc.mkDockerTag('latest');
          }
				} // docker.withRegistry
				} // if(params.MF_SKIP_TO_DIST)
      } // stage

      stage('Set versions and build esb')
      {
				if(params.MF_SKIP_TO_DIST)
				{
					echo "params.MF_SKIP_TO_DIST=true so don't build metasfresh and esb jars and don't invoke downstream jobs"
				}
				else
				{

        // create our config instance to be used further on
        final MvnConf mvnEsbConf = mvnConf.withPomFile('de.metas.esb/pom.xml');
        echo "mvnEsbConf=${mvnEsbConf}"

				// update the parent pom version
 				mvnUpdateParentPomVersion mvnEsbConf

				// set the artifact version of everything below de.metas.esb/pom.xml
				sh "mvn --settings ${mvnEsbConf.settingsFile} --file ${mvnEsbConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnEsbConf.resolveParams} versions:set"

				// update the versions of metas dependencies that are external to the de.metas.esb reactor modules
				sh "mvn --settings ${mvnEsbConf.settingsFile} --file ${mvnEsbConf.pomFile} --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnEsbConf.resolveParams} versions:use-latest-versions"

				// build and deploy
				// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
				// maven.test.failure.ignore=true: see metasfresh stage
				sh "mvn --settings ${mvnEsbConf.settingsFile} --file ${mvnEsbConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${MF_VERSION} ${mvnEsbConf.resolveParams} ${mvnEsbConf.deployParam} clean deploy"
				} // if(params.MF_SKIP_TO_DIST)
			} // stage

			if(!params.MF_SKIP_TO_DIST)
			{
				// collect the test results for the two preceeding stages. call this step once to avoid counting the tests twice.
				junit '**/target/surefire-reports/*.xml'
			}

		} // withMaven
	} // configFileProvider

	// clean up the workspace after (successfull) builds
	cleanWs cleanWhenAborted: false, cleanWhenFailure: false

} // node

// this map is populated in the "Invoke downstream jobs" stage
final MF_ARTIFACT_VERSIONS = [:];

// invoke external build jobs like webui
// wait for the results, but don't block a node while waiting
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
	}
	else
	{
		MF_ARTIFACT_VERSIONS['metasfresh'] = MF_VERSION;

		// params.MF_SKIP_TO_DIST == false, so invoke downstream jobs and get the build versions which came out of them
		parallel (
			metasfresh_webui: {
				// TODO: rename the build job to metasfresh-webui-api
				final webuiDownStreamJobMap = invokeDownStreamJobs(
          MF_BUILD_ID,
          MF_UPSTREAM_BRANCH,
          MF_ARTIFACT_VERSIONS['metasfresh-parent'],
          MF_VERSION,
          true,
          'metasfresh-webui'); // wait=true
				MF_ARTIFACT_VERSIONS['metasfresh-webui'] = webuiDownStreamJobMap.MF_VERSION;
			},
			metasfresh_procurement_webui: {
				// yup, metasfresh-procurement-webui does share *some* code with this repo
				final procurementWebuiDownStreamJobMap = invokeDownStreamJobs(
          MF_BUILD_ID,
          MF_UPSTREAM_BRANCH,
          MF_ARTIFACT_VERSIONS['metasfresh-parent'],
          MF_VERSION,
          true,
          'metasfresh-procurement-webui'); // wait=true
				MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'] = procurementWebuiDownStreamJobMap.MF_VERSION;
			}
		);

		// gh #968: note that there is no point invoking metasfresh-webui-frontend from here. The frontend doesn't depend on this repo.
		// Therefore we will just get the latest webui-frontend later, when we need it.

		// more to come: admin-webui
	} // if(params.MF_SKIP_TO_DIST)

	// complement the MF_ARTIFACT_VERSIONS we did not set so far
  MF_ARTIFACT_VERSIONS['metasfresh'] = MF_ARTIFACT_VERSIONS['metasfresh'] ?: "LATEST";
	MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'] = MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'] ?: "LATEST";
	MF_ARTIFACT_VERSIONS['metasfresh-webui'] = MF_ARTIFACT_VERSIONS['metasfresh-webui'] ?: "LATEST";
	MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'] = MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'] ?: "LATEST";

	// now that the "basic" build is done, notify zapier so we can do further things external to this jenkins instance
	// note: even with "skiptodist=true we do this, because we still want to make the notifcations
	echo "Going to notify external systems via zapier webhook"
	node('linux')
	{
		withCredentials([string(credentialsId: 'zapier-metasfresh-build-notification-webhook', variable: 'ZAPPIER_WEBHOOK_SECRET')])
		{
      // the zapier secret contains a trailing slash and one that is somewhere in the middle.
			final webhookUrl = "https://hooks.zapier.com/hooks/catch/${ZAPPIER_WEBHOOK_SECRET}"

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

	echo "Invoking downstream jobs 'metasfresh-dist' with preferred branch=${MF_UPSTREAM_BRANCH}"

	final List distJobParameters = [
			string(name: 'MF_UPSTREAM_BUILDNO', value: MF_BUILD_ID), // can be used together with the upstream branch name to construct this upstream job's URL
			string(name: 'MF_UPSTREAM_BRANCH', value: MF_UPSTREAM_BRANCH),
			string(name: 'MF_METASFRESH_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh']), // the downstream job shall use *this* metasfresh.version, as opposed to whatever is the latest at the time it runs
			string(name: 'MF_METASFRESH_PROCUREMENT_WEBUI_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui']),
			string(name: 'MF_METASFRESH_WEBUI_API_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh-webui']),
			string(name: 'MF_METASFRESH_WEBUI_FRONTEND_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'])
		];

  def misc = new de.metas.jenkins.Misc();

	// Run the downstream dist jobs in parallel.
	// Wait for their result, because they will apply our SQL migration scripts and when one fails, we want this job to also fail.
	parallel (
		metasfresh_dist: {
			build job: misc.getEffectiveDownStreamJobName('metasfresh-dist', MF_UPSTREAM_BRANCH),
			parameters: distJobParameters,
			wait: true;
		},
		metasfresh_dist_orgs: {
			build job: misc.getEffectiveDownStreamJobName('metasfresh-dist-orgs', MF_UPSTREAM_BRANCH),
			parameters: distJobParameters,
			wait: true;
		}
	)
}
} // timestamps
