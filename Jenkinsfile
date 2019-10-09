#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc@ghNewNexus')
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
			description: 'Name of the upstream job which called us. Required only in conjunction with MF_UPSTREAM_ARTIFACT_VERSION',
			name: 'MF_UPSTREAM_JOBNAME'),

		string(defaultValue: '',
			description: 'Version of the upstream job\'s artifact that was build by the job which called us. Shall used when resolving the upstream depdendency. Leave empty and this build will use the latest.',
			name: 'MF_UPSTREAM_ARTIFACT_VERSION'),

		string(defaultValue: '',
			description: 'If metasfresh-frontend calls this job, then it uses this variable to forward the metasfresh-e2e version which it build',
			name: 'MF_METASFRESH_E2E_ARTIFACT_VERSION'),
		string(defaultValue: '',
			description: 'If metasfresh-frontend calls this job, then it uses this variable to forward the metasfresh-e2e docker image name which it build',
			name: 'MF_METASFRESH_E2E_DOCKER_IMAGE'),

		string(defaultValue: '',
				description: 'If metasfresh-frontend calls this job, then it uses this variable to forward the metasfresh-edi docker image name which it build',
				name: 'MF_METASFRESH_EDI_DOCKER_IMAGE'),

		booleanParam(defaultValue: false, description: '''Set to true to only create the distributable files and assume that the underlying jars were already created and deployed''',
			name: 'MF_SKIP_TO_DIST'),
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: numberOfBuildsToKeepStr)) // keep the last $numberOfBuildsToKeepStr builds
]);

final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.5'

currentBuild.description = currentBuild.description ?: '';

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
			'https://nexus.metasfresh.com' // mvnRepoBaseURL
		)
		echo "mvnConf=${mvnConf}"

		def scmVars = checkout scm; // i hope this to do all the magic we need
		def gitCommitHash = scmVars.GIT_COMMIT

		sh 'git clean -d --force -x' // clean the workspace

		// we need to provide MF_VERSION because otherwise  the profile "MF_VERSION-env-missing" would be activated from the metasfresh-parent pom.xml
		// and therefore, the jenkins information would not be added to the build.properties info file.
		withEnv(["MF_VERSION=${MF_VERSION}"])
		{
		withMaven(jdk: 'java-8', maven: 'maven-3.5.4', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M')
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

					publishJacocoReports(scmVars.GIT_COMMIT, 'codacy_project_token_for_metasfresh_repo')
				} // if(params.MF_SKIP_TO_DIST)
			}
		} // withMaven
	} // withEnv


	stage('Build metasfresh docker image(s)')
	{
		if(params.MF_SKIP_TO_DIST)
		{
			echo "params.MF_SKIP_TO_DIST=true so don't create docker images"
		}
		else
		{
			final def misc = new de.metas.jenkins.Misc();

			final DockerConf materialDispoDockerConf = new DockerConf(
				'metasfresh-material-dispo', // artifactName
				MF_UPSTREAM_BRANCH, // branchName
				MF_VERSION, // versionSuffix
				'de.metas.material/dispo-service/target/docker' // workDir
			);
			final String publishedMaterialDispoDockerImageName = dockerBuildAndPush(materialDispoDockerConf)

			final DockerConf reportDockerConf = materialDispoDockerConf
				.withArtifactName('metasfresh-report')
				.withWorkDir('de.metas.report/report-service/target/docker');
			final String publishedReportDockerImageName = dockerBuildAndPush(reportDockerConf)

			final DockerConf printDockerConf = materialDispoDockerConf
				.withArtifactName('metasfresh-print')
				.withWorkDir('de.metas.printing.rest-api-impl/target/docker');
			final String publishedPrintDockerImageName = dockerBuildAndPush(printDockerConf)

			final DockerConf msv3ServerDockerConf = materialDispoDockerConf
				.withArtifactName('de.metas.vertical.pharma.msv3.server')
				.withWorkDir('de.metas.vertical.pharma.msv3.server/target/docker');
			final String publishedMsv3ServerImageName =dockerBuildAndPush(msv3ServerDockerConf)

			currentBuild.description= """${currentBuild.description}<p/>
			<h3>Docker</h3>
			This build created the following deployable docker images 
			<ul>
			<li><code>${publishedMaterialDispoDockerImageName}</code></li>
			<li><code>${publishedPrintDockerImageName}</code></li>
			<li><code>${publishedMsv3ServerImageName}</code></li>
			</ul>
			<p>
			This build also created the image <code>${publishedReportDockerImageName}</code> that can be used as <b>base image</b> for custom metasfresh-report docker images.
			<p>
			"""

		} // if(params.MF_SKIP_TO_DIST)
	} // stage
	} // configFileProvider

	// clean up the workspace after (successfull) builds
	cleanWs cleanWhenAborted: false, cleanWhenFailure: false

} // node

// these maps are populated in the "Invoke downstream jobs" stage
final MF_ARTIFACT_VERSIONS = [:];
final MF_DOCKER_IMAGES = [:];
MF_DOCKER_IMAGES['metasfresh-edi'] = params.MF_METASFRESH_EDI_DOCKER_IMAGE

currentBuild.description="""${currentBuild.description}<p/>
			<h3>Downstream-Jobs</h3>"""

// invoke external build jobs like webui
// wait for the results, but don't block a node while waiting
stage('Invoke downstream jobs')
{
	if(params.MF_SKIP_TO_DIST)
	{
		echo "params.MF_SKIP_TO_DIST is true so don't build metasfresh and esb jars and don't invoke downstream jobs";

		// if params.MF_SKIP_TO_DIST is true, it might mean that we were invoked via a change in metasfresh-webui or metasfresh-webui-frontend..
		// note: if params.MF_UPSTREAM_JOBNAME is set, it means that we were called from upstream and therefore also params.MF_UPSTREAM_ARTIFACT_VERSION is set
		if(params.MF_UPSTREAM_JOBNAME == 'metasfresh-webui')
		{
			// note: we call it "metasfresh-webui" (as opposed to "metasfresh-webui-api"), because it's the repo's and the build job's name.
			MF_ARTIFACT_VERSIONS['metasfresh-webui']=params.MF_UPSTREAM_ARTIFACT_VERSION;
			echo "Set MF_ARTIFACT_VERSIONS.metasfresh-webui=${MF_ARTIFACT_VERSIONS['metasfresh-webui']}"
		}

		if(params.MF_UPSTREAM_JOBNAME == 'metasfresh-webui-frontend')
		{
			MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend']=params.MF_UPSTREAM_ARTIFACT_VERSION;
			echo "Set MF_ARTIFACT_VERSIONS.metasfresh-webui-frontend=${MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend']}"
		}

		if(params.MF_UPSTREAM_JOBNAME == 'metasfresh-procurement-webui')
		{
			MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui']=params.MF_UPSTREAM_ARTIFACT_VERSION;
			echo "Set MF_ARTIFACT_VERSIONS.metasfresh-procurement-webui=${MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui']}"
		}

		// Anyways, if we don't invoke metasfresh-e2e ourselves (in this if's else block!), then take whatever we were invoked with.
		// Might well be '', but we need to make sure not to invoke the downstream metasfresh-dist job with MF_METASFRESH_E2E_DOCKER_IMAGE = null,
		// because that would fail the job with "java.lang.IllegalArgumentException: Null value not allowed as an environment variable: MF_METASFRESH_E2E_DOCKER_IMAGE"
		MF_ARTIFACT_VERSIONS['metasfresh-e2e'] = params.MF_METASFRESH_E2E_ARTIFACT_VERSION
		MF_DOCKER_IMAGES['metasfresh-e2e'] = params.MF_METASFRESH_E2E_DOCKER_IMAGE
	}
	else
	{
		MF_ARTIFACT_VERSIONS['metasfresh'] = MF_VERSION;

		// params.MF_SKIP_TO_DIST == false, so invoke downstream jobs and get the build versions which came out of them
		parallel (
			metasfresh_webui: {
				// TODO: rename the build job to metasfresh-webui-api
				final def webuiDownStreamBuildResult = invokeDownStreamJobs(
          env.BUILD_NUMBER,
          MF_UPSTREAM_BRANCH,
          MF_ARTIFACT_VERSIONS['metasfresh-parent'],
          MF_VERSION,
          true, // wait=true
          'metasfresh-webui')

				MF_ARTIFACT_VERSIONS['metasfresh-webui'] = webuiDownStreamBuildResult.buildVariables.MF_VERSION
				MF_DOCKER_IMAGES['metasfresh-webui'] = webuiDownStreamBuildResult.buildVariables.MF_DOCKER_IMAGE

				currentBuild.description="""${currentBuild.description}<p/>
This build triggered the <b>metasfresh-webui</b> jenkins job <a href="${webuiDownStreamBuildResult.absoluteUrl}">${webuiDownStreamBuildResult.displayName}</a>
				"""
			},
			// So why did we invoke metasfresh-e2e anyways?
			// It does not depend on metasfresh after all.
			// TODO: see what negative impacts we have without triggering it and the probably remove this block
			// Result 1: running this used to provide us with an actual metasfresh-e2e docker image; but we can as well let the downstam branch sort this out
			/* metasfresh_e2e: {

				final def misc = new de.metas.jenkins.Misc();
				final String metasfreshE2eJobName = misc.getEffectiveDownStreamJobName('metasfresh-e2e', MF_UPSTREAM_BRANCH);

				final def e2eDownStreamBuildResult = build job: metasfreshE2eJobName,
					parameters: [
						string(name: 'MF_TRIGGER_DOWNSTREAM_BUILDS', value: false)
					],
					wait: true,
					propagate: false

				currentBuild.description="""${currentBuild.description}<p/>
This build triggered the <b>metasfresh-e2e</b> jenkins job <a href="${e2eDownStreamBuildResult.absoluteUrl}">${e2eDownStreamBuildResult.displayName}</a>
				"""
			},*/
			metasfresh_procurement_webui: {
				// yup, metasfresh-procurement-webui does share *some* code with this repo
				final def procurementWebuiDownStreamBuildResult = invokeDownStreamJobs(
          env.BUILD_NUMBER,
          MF_UPSTREAM_BRANCH,
          MF_ARTIFACT_VERSIONS['metasfresh-parent'],
          MF_VERSION,
          true, // wait=true
          'metasfresh-procurement-webui');
				MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'] = procurementWebuiDownStreamBuildResult.buildVariables.MF_VERSION;

				// note that as of now, metasfresh-procurement-webui does not publish a docker image
				currentBuild.description = """${currentBuild.description}<p/>
This build triggered the <b>metasfresh-procurement-webui</b> jenkins job <a href="${procurementWebuiDownStreamBuildResult.absoluteUrl}">${procurementWebuiDownStreamBuildResult.displayName}</a>
				"""
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
	MF_ARTIFACT_VERSIONS['metasfresh-e2e'] = MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'] ?: "LATEST";

	echo "Invoking downstream jobs 'metasfresh-dist' and 'metasfresh-dist-orgs' with preferred branch=${MF_UPSTREAM_BRANCH}"

	final List distJobParameters = [
			string(name: 'MF_UPSTREAM_BUILDNO', value: env.BUILD_NUMBER), // can be used together with the upstream branch name to construct this upstream job's URL
			string(name: 'MF_UPSTREAM_BRANCH', value: MF_UPSTREAM_BRANCH),

			string(name: 'MF_METASFRESH_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh']), // the downstream job shall use *this* metasfresh.version, as opposed to whatever is the latest at the time it runs
			string(name: 'MF_METASFRESH_PROCUREMENT_WEBUI_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui']),
			string(name: 'MF_METASFRESH_WEBUI_API_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh-webui']),
			string(name: 'MF_METASFRESH_WEBUI_FRONTEND_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend']),
			string(name: 'MF_METASFRESH_E2E_VERSION', value: MF_ARTIFACT_VERSIONS['metasfresh-e2e']),

			//string(name: 'MF_METASFRESH_PROCUREMENT_WEBUI_DOCKER_IMAGE', value: MF_DOCKER_IMAGES['metasfresh-procurement-webui']), // currently no docker image created
			string(name: 'MF_METASFRESH_WEBUI_API_DOCKER_IMAGE', value: MF_DOCKER_IMAGES['metasfresh-webui'] ?: ''),
			string(name: 'MF_METASFRESH_WEBUI_FRONTEND_DOCKER_IMAGE', value: MF_DOCKER_IMAGES['metasfresh-webui-frontend'] ?: ''),
			string(name: 'MF_METASFRESH_E2E_DOCKER_IMAGE', value: MF_DOCKER_IMAGES['metasfresh-e2e'] ?: ''),
			string(name: 'MF_METASFRESH_EDI_DOCKER_IMAGE', value: MF_DOCKER_IMAGES['metasfresh-edi'] ?: ''),
		];

	final def misc = new de.metas.jenkins.Misc();

	// Run the downstream dist jobs in parallel.
	// Wait for their result, because they will apply our SQL migration scripts and when one fails, we want this job to also fail.
	parallel (
		metasfresh_dist: {
			final def metasFreshDistBuildResult = build job: misc.getEffectiveDownStreamJobName('metasfresh-dist', MF_UPSTREAM_BRANCH),
			  parameters: distJobParameters,
			  wait: true

			currentBuild.description="""${currentBuild.description}<p/>
This build triggered the <b>metasfresh-dist</b> jenkins job <a href="${metasFreshDistBuildResult.absoluteUrl}">${metasFreshDistBuildResult.displayName}</a>
				"""
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
 * This method will be used further down to call additional jobs such as metasfresh-procurement and metasfresh-webui.
 *
 * @return the the build result. Its buildVariables (a map) ususally also contaisn (to be set by our Jenkinsfiles):
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
		],
		wait: wait

	echo "Job invokation done; Returning buildResult; buildResult.getBuildVariables()=${buildResult.getBuildVariables()}"
	return buildResult;
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
