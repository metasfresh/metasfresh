#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf

// first things first
chuckNorris()

// thx to http://stackoverflow.com/a/36949007/1012103 with respect to the paramters
properties([
	parameters([
		booleanParam(defaultValue: false, description: '''Set to true if this build shall trigger metasfresh builds.<br>
Set to false if this build is called from elsewhere and the orchestrating also takes place elsewhere<br><br>
Currently, downstream builds fail for some reason..that's why the default value is now <code>false</code> for the time being''',
			name: 'MF_TRIGGER_DOWNSTREAM_BUILDS')
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '100')) // keep the last 20 builds
])


timestamps
{
	// https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
	final String MF_VERSION = retrieveArtifactVersion(env.BRANCH_NAME, env.BUILD_NUMBER)
	currentBuild.displayName = "artifact-version ${MF_VERSION}";

stage('Build')
{
node('agent && linux') // shall only run on a jenkins agent with linux
{
	final def scmVars = checkout scm
	sh 'git clean -d --force -x' // clean the workspace

	final def WEBUI_FRONTEND_SHA1

 	final def dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd")
    final String currentDate = dateFormat.format(new Date())

	final String additionalBuildArgs = "--build-arg CACHEBUST=${currentDate}"
	final DockerConf e2eDockerConf = new DockerConf(
		'metasfresh-e2e', // artifactName
		env.BRANCH_NAME, // branchName
		MF_VERSION, // versionSuffix
		'.', // workDir
		additionalBuildArgs
	);
	final String publishedE2eDockerImageName;
	stage('Build and push e2e docker image')
	{
		// echo "e2eDockerConf=${e2eDockerConf.toString()}"
		publishedE2eDockerImageName = dockerBuildAndPush(e2eDockerConf)
	}

	final String e2eDockerImageNameNoRegistry=publishedE2eDockerImageName.substring("${e2eDockerConf.pushRegistry}/".length());

	currentBuild.description="""This build's main artifact (if not yet cleaned up) is
<ul>
<li>a docker image with name <code>${publishedE2eDockerImageName}</code>; Note that you can also use the tag <code>${env.BRANCH_NAME}_LATEST</code></li>
</ul>
<p/>
You can run the docker image like this:<br>
<pre>
hostname=yourinstance.metasfresh.com<br>
docker run --ipc=host --rm\\
 -e "FRONTEND_URL=https://\${hostname}:443"\\
 -e "API_URL=https://\${hostname}:443/rest/api"\\
 -e "WS_URL=https://\${hostname}:443/stomp"\\
 -e "USERNAME=dev"\\
 -e "PASSWORD=password"\\
 -e "CYPRESS_SPEC=NOT_SET"\\
 -e "CYPRESS_RECORD_KEY=NOT_SET"\\
 -e "CYPRESS_BROWSER=chrome"\\
 -e "DEBUG_CYPRESS_OUTPUT=n"\\
 -e "DEBUG_PRINT_BASH_CMDS=n"\\
 -e "DEBUG_SLEEP_AFTER_FAIL=n"\\
 ${publishedE2eDockerImageName}
</pre>
<p/>
Also see metasfresh-e2e's <a href="https://github.com/metasfresh/metasfresh-e2e/blob/master/README.md">README.md</a>.
<p/>
Related jenkins jobs:
<ul>
<li><a href=\"https://jenkins.metasfresh.com/job/ops/job/run_e2e_tests/parambuild/?MF_DOCKER_IMAGE_FULL_NAME=${publishedE2eDockerImageName}&MF_DOCKER_REGISTRY=&MF_DOCKER_IMAGE=&MF_UPSTREAM_BUILD_URL=${BUILD_URL}\"><b>This link</b></a> lets you jump to a job that will perform an <b>e2e-test</b> using this job's docker image.</li>
</ul>
"""
	// gh #968: set version and docker image name to be available to a possible upstream job that might have called us
	env.MF_VERSION=MF_VERSION
	env.MF_DOCKER_IMAGE=publishedE2eDockerImageName
	env.BUILD_GIT_SHA1=scmVars.GIT_COMMIT
} // node
}

	if(params.MF_TRIGGER_DOWNSTREAM_BUILDS)
	{
		stage('Invoke downstream job')
		{
			final def misc = new de.metas.jenkins.Misc()
			final String metasfreshJobName = misc.getEffectiveDownStreamJobName('metasfresh', env.BRANCH_NAME)
			final def metasfreshBuildResult = build job: metasfreshJobName,
				parameters: [
					string(name: 'MF_UPSTREAM_BRANCH', value: env.BRANCH_NAME),
					string(name: 'MF_UPSTREAM_BUILDNO', value: env.BUILD_NUMBER),
					string(name: 'MF_UPSTREAM_VERSION', value: env.MF_VERSION),
					string(name: 'MF_UPSTREAM_JOBNAME', value: 'metasfresh-e2e'),
					string(name: 'MF_METASFRESH_E2E_ARTIFACT_VERSION', value: env.MF_VERSION),
					string(name: 'MF_METASFRESH_E2E_DOCKER_IMAGE', value: env.MF_DOCKER_IMAGE),
					booleanParam(name: 'MF_TRIGGER_DOWNSTREAM_BUILDS', value: true), // metasfresh shall trigger the "-dist" jobs
					booleanParam(name: 'MF_SKIP_TO_DIST', value: true) // this param is only recognised by metasfresh
				],
				wait: true
			currentBuild.description="""${currentBuild.description}
		<p/>
		This build triggered the <b>metasfresh</b> jenkins job <a href="${metasfreshBuildResult.absoluteUrl}">${metasfreshBuildResult.displayName}</a>
		"""
		}
	}
	else
	{
		echo "params.MF_TRIGGER_DOWNSTREAM_BUILDS=${params.MF_TRIGGER_DOWNSTREAM_BUILDS}, so we do not trigger metasfresh as downstream build."
	}
} // timestamps
