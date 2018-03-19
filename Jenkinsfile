#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

final String MF_UPSTREAM_BRANCH = params.MF_UPSTREAM_BRANCH ?: env.BRANCH_NAME
echo "params.MF_UPSTREAM_BRANCH=${params.MF_UPSTREAM_BRANCH}; env.BRANCH_NAME=${env.BRANCH_NAME}; => MF_UPSTREAM_BRANCH=${MF_UPSTREAM_BRANCH}"

// keep the last 20 builds for master and stable, but onkly the last 5 for the rest, to preserve disk space on jenkins
final String numberOfBuildsToKeepStr = (MF_UPSTREAM_BRANCH == 'master' || MF_UPSTREAM_BRANCH == 'stable') ? '20' : '5'

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
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: numberOfBuildsToKeepStr)) // keep the last $numberOfBuildsToKeepStr builds
]);

final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.5'

try
{
timestamps
{
	// https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
	final String MF_VERSION=retrieveArtifactVersion(MF_UPSTREAM_BRANCH, env.BUILD_NUMBER)
	currentBuild.displayName="artifact-version ${MF_VERSION}";

node('agent && linux')
{
	configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')])
	{
		// create our config instance to be used further on
		final MvnConf mvnConf = new MvnConf(
			'pom.xml', // pomFile
			MAVEN_SETTINGS, // settingsFile
			"mvn-${MF_UPSTREAM_BRANCH}", // mvnRepoName
			'https://repo.metasfresh.com' // mvnRepoBaseURL
		)
		echo "mvnConf=${mvnConf}"

		// we need to provide MF_VERSION because otherwise  the profile "MF_VERSION-env-missing" would be activated from the metasfresh-parent pom.xml
		// and therefore, the jenkins information would not be added to the build.properties info file.
		withEnv(["MF_VERSION=${MF_VERSION}"])
		{
		withMaven(jdk: 'java-8', maven: 'maven-3.5.2', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M')
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
				// processAllModules=true: also update those modules that have a parent version range!
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DprocessAllModules=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"

				// Set the metasfresh.version property from [1,10.0.0] to our current build version
				// From the documentation: "Set a property to a given version without any sanity checks"; that's what we want here..sanity is clearly overated
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dproperty=metasfresh.version -DnewVersion=${MF_VERSION} ${VERSIONS_PLUGIN}:set-property"

				// build and deploy
				// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
				// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${MF_VERSION} ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"
				} // if(params.MF_SKIP_TO_DIST)
			}
			stage('Build metasfresh docker image(s)')
			{

				if(params.MF_SKIP_TO_DIST)
				{
					echo "params.MF_SKIP_TO_DIST=true so don't create docker images"
				}
				else
				{
					final DockerConf materialDispoDockerConf = new DockerConf(
						'metasfresh-material-dispo', // artifactName
						MF_UPSTREAM_BRANCH, // branchName
						MF_VERSION, // versionSuffix
						'de.metas.material/dispo-service/target/docker' // workDir
					);
					dockerBuildAndPush(materialDispoDockerConf)
				
					final DockerConf reportDockerConf = materialDispoDockerConf
						.withArtifactName('metasfresh-report')
						.withWorkDir('de.metas.report/report-service/target/docker');
					dockerBuildAndPush(reportDockerConf)

					final DockerConf printDockerConf = materialDispoDockerConf
						.withArtifactName('metasfresh-print')
						.withWorkDir('de.metas.printing.rest-api-impl/target/docker');
					dockerBuildAndPush(printDockerConf)

				} // if(params.MF_SKIP_TO_DIST)
      } // stage

			if(!params.MF_SKIP_TO_DIST)
			{
        final MvnConf mvnJacocoConf = mvnConf.withPomFile('pom_for_jacoco_aggregate_coverage_report.xml');
        mvnUpdateParentPomVersion mvnJacocoConf
        collectTestResultsAndReportCoverage()

        // creating one aggregated jacoco.xml and uploading it to codacy doesn't work right now :-(
        // sh "mvn --settings ${mvnJacocoConf.settingsFile} --file ${mvnJacocoConf.pomFile} --batch-mode ${mvnJacocoConf.resolveParams} org.jacoco:jacoco-maven-plugin:0.7.9:report-aggregate"
        // uploadCoverageResultsForCodacy('./target/site/jacoco-aggregate', 'jacoco.xml')
			}
		} // withMaven
    } // withEnv
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
          env.BUILD_NUMBER,
          MF_UPSTREAM_BRANCH,
          MF_ARTIFACT_VERSIONS['metasfresh-parent'],
          MF_VERSION,
          true, // wait=true
          'metasfresh-webui');
				MF_ARTIFACT_VERSIONS['metasfresh-webui'] = webuiDownStreamJobMap.MF_VERSION;
			},
			metasfresh_procurement_webui: {
				// yup, metasfresh-procurement-webui does share *some* code with this repo
				final procurementWebuiDownStreamJobMap = invokeDownStreamJobs(
          env.BUILD_NUMBER,
          MF_UPSTREAM_BRANCH,
          MF_ARTIFACT_VERSIONS['metasfresh-parent'],
          MF_VERSION,
          true, // wait=true
          'metasfresh-procurement-webui');
				MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'] = procurementWebuiDownStreamJobMap.MF_VERSION;
			},
			metasfresh_esb_camel: {
				// yup, metasfresh-procurement-webui does share *some* code with this repo
				final esbCamelDownStreamJobMap = invokeDownStreamJobs(
          env.BUILD_NUMBER,
          MF_UPSTREAM_BRANCH,
          MF_ARTIFACT_VERSIONS['metasfresh-parent'],
          MF_VERSION,
          true, // wait=true
          'metasfresh-esb-camel');
				MF_ARTIFACT_VERSIONS['metasfresh-esb-camel'] = esbCamelDownStreamJobMap.MF_VERSION;
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

	echo "Invoking downstream jobs 'metasfresh-dist' and 'metasfresh-dist-orgs' with preferred branch=${MF_UPSTREAM_BRANCH}"

	final List distJobParameters = [
			string(name: 'MF_UPSTREAM_BUILDNO', value: env.BUILD_NUMBER), // can be used together with the upstream branch name to construct this upstream job's URL
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
			  wait: true
		},
    zapier: {
      invokeZapier(env.BUILD_NUMBER, // upstreamBuildNo
        MF_UPSTREAM_BRANCH, // upstreamBranch
        MF_ARTIFACT_VERSIONS['metasfresh'], // metasfreshVersion
        MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'], // metasfreshProcurementWebuiVersion
        MF_ARTIFACT_VERSIONS['metasfresh-webui'], // metasfreshWebuiApiVersion
        MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'] // metasfreshWebuiFrontendVersion
      )
    }
	)
} // stage
} // timestamps
} catch(all)
{
  final String mattermostMsg = "This **${MF_UPSTREAM_BRANCH}** build failed or was aborted: ${BUILD_URL}"
  if(MF_UPSTREAM_BRANCH=='master' || MF_UPSTREAM_BRANCH=='release')
  {
    mattermostSend color: 'danger', message: mattermostMsg
  }
  else
  {
    withCredentials([string(credentialsId: 'jenkins-issue-branches-webhook-URL', variable: 'secretWebhookUrl')])
    {
      mattermostSend color: 'danger', endpoint: secretWebhookUrl, channel: 'jenkins-low-prio', message: mattermostMsg
    }
  }
  throw all
}

/**
  *	collect the test results for the two preceeding stages. call this once to avoid counting the tests twice.
  */
void collectTestResultsAndReportCoverage()
{
	// after upgrading the "Pipeline Maven Integration Plugin" from 0.7 to 3.1.0, collecting the tests is now done by that plugin.
	// comment it out to avoid all tests being counted twice
  // junit '**/target/surefire-reports/*.xml'

  jacoco exclusionPattern: '**/src/main/java-gen' // collect coverage results for jenkins
}

void uploadCoverageResultsForCodacy(final String aggregatedJacocoFilePath, final String aggregatedJacocoFilename)
{
  withCredentials([string(credentialsId: 'codacy_project_token_for_metasfresh_repo', variable: 'CODACY_PROJECT_TOKEN')])
  {
    withEnv(['CODACY_PROJECT_TOKEN=${CODACY_PROJECT_TOKEN}'])
    {
      final String version='2.0.1'
      final String classpathParam = "-cp codacy-coverage-reporter-${version}-assembly.jar"
      final String reportFileParam = "-r ${aggregatedJacocoFilePath}/${aggregatedJacocoFilename}"
      final String prefixParam = "--prefix ${aggregatedJacocoFilePath}" // thx to https://github.com/codacy/codacy-coverage-reporter#failed-to-upload-report-not-found
      sh "wget --quiet https://repo.metasfresh.com/service/local/repositories/mvn-3rdparty/content/com/codacy/codacy-coverage-reporter/${version}/codacy-coverage-reporter-${version}-assembly.jar"
      sh "java ${classpathParam} com.codacy.CodacyCoverageReporter -l Java ${reportFileParam} ${prefixParam}"
    }
  }
}

/**
 * This method will be used further down to call additional jobs such as metasfresh-procurement and metasfresh-webui.
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
  final def misc = new de.metas.jenkins.Misc();
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

void invokeZapier(
  final String upstreamBuildNo,
  final String upstreamBranch,
  final String metasfreshVersion,
  final String metasfreshProcurementWebuiVersion,
  final String metasfreshWebuiApiVersion,
  final String metasfreshWebuiFrontendVersion
    )
{
  // now that the "basic" build is done, notify zapier so we can do further things external to this jenkins instance
  // note: even with "skiptodist=true we do this, because we still want to make the notifcations

  echo "Going to notify external systems via zapier webhook"

    final def hook = registerWebhook()
    echo "Waiting for POST to ${hook.getURL()}"

		final jsonPayload = """{
				\"MF_UPSTREAM_BUILDNO\":\"${upstreamBuildNo}\",
				\"MF_UPSTREAM_BRANCH\":\"${upstreamBranch}\",
				\"MF_METASFRESH_VERSION\":\"${metasfreshVersion}\",
				\"MF_METASFRESH_PROCUREMENT_WEBUI_VERSION\":\"${metasfreshProcurementWebuiVersion}\",
				\"MF_METASFRESH_WEBUI_API_VERSION\":\"${metasfreshWebuiApiVersion}\",
				\"MF_METASFRESH_WEBUI_FRONTEND_VERSION\":\"${metasfreshWebuiFrontendVersion}\",
				\"MF_WEBHOOK_CALLBACK_URL\":\"${hook.getURL()}\"
		}"""

		// invoke zapier to trigger external jobs
  	nodeIfNeeded('linux')
  	{
  			sh "curl -X POST -d \'${jsonPayload}\' ${createZapierUrl()}";
  	}
		waitForWebhookCall(hook);
}

String createZapierUrl()
{
	  withCredentials([string(credentialsId: 'zapier-metasfresh-build-notification-webhook', variable: 'zapier_WEBHOOK_SECRET')])
	  {
	    // the zapier secret contains a trailing slash and another slash that is somewhere in the middle.
	  	return "https://hooks.zapier.com/hooks/catch/${zapier_WEBHOOK_SECRET}"
		}
}

void waitForWebhookCall(final def hook)
{
	    echo "Wait 30 minutes for the zapier-triggered downstream jobs to succeed or fail"
	    timeout(time: 30, unit: 'MINUTES')
	    {
				// stop and wait, for someone to do e.g. curl -X POST -d 'OK' <hook-URL>
	      final def message = waitForWebhook hook ?: '<webhook returned NULL>'
	      if(message.trim() == 'OK')
	      {
					echo "The external jobs that were invoked by zapier succeeeded; message='${message}'; hook-URL=${hook.getURL()}"
	      }
				else
				{
					error "An external job that was invoked by zapier failed; message='${message}'; hook-URL=${hook.getURL()}"
				}
	    }
}
