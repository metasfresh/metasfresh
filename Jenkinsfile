#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf

// thx to http://stackoverflow.com/a/36949007/1012103 with respect to the paramters
properties([
	parameters([
		string(defaultValue: 'master',
			description: '''Revison or branch name of the https://github.com/metasfresh/metasfresh-webui-frontend version to take the cypress tests from.<br>
Examples:
<ul>
<li><code>master</code></li>
<li><code>release</code></li>
<li><code>98ad2dbbf35127564461b07c6fa55260bec17d77</code></li>
</ul>''',
			name: 'MF_WEBUI_FRONTEND_REVISION'),
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')) // keep the last 20 builds
])

chuckNorris()

timestamps
{
	// https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
	final String MF_VERSION = retrieveArtifactVersion(env.BRANCH_NAME, env.BUILD_NUMBER)
	currentBuild.displayName = "artifact-version ${MF_VERSION}";

node('agent && linux') // shall only run on a jenkins agent with linux
{
	checkout scm // i hope this to do all the magic we need
	sh 'git clean -d --force -x' // clean the workspace

	// check out the metasfresh-webui-frontend version that whose cypress tests we are going to execute
	dir('cypress-git-repo') {
    	checkout([$class: 'GitSCM', branches: [[name: params.MF_WEBUI_FRONTEND_REVISION ]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CloneOption', noTags: false, reference: '', shallow: true]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'metas-dev-ssh-key', url: 'git@github.com:metasfresh/metasfresh-webui-frontend.git']]])
	}

 	final def dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd")
    final def date = new Date()
    final String currentDate = dateFormat.format(date)

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

	currentBuild.description="""This build's main artifacts (if not yet cleaned up) are
<ul>
<li>a docker image with name <code>${publishedE2eDockerImageName}</code><br>
</ul>
<p/>
To run the docker image like this:<br>
<pre>
hostname=yourinstance.metasfresh.com
docker run --rm\
 -e "FRONTEND_URL=https://\${hostname}:443"\
 -e "API_URL=https://\${hostname}:443/rest/api"\
 -e "WS_URL=https://\${hostname}:443/stomp"\
 -e "USERNAME=dev"\
 -e "PASSWORD=password"\
 -e "RECORD_KEY=NOT_SET"\
 -e "BROWSER=chrome"\
 -e "DEBUG_CYPRESS_OUTPUT=n"\
 -e "DEBUG_PRINT_BASH_CMDS=n"\
 -e "DEBUG_PRINT_BASH_CMDS=n"\
 e- "DEBUG_SLEEP_AFTER_FAIL=n"\
${publishedE2eDockerImageName}
</pre>
<p/>
If you want to upload the test results to the cypress dashboard of metasfresh, then also include the parameter
<code>
-e "RECORD_KEY=<the-secret-key>"
</code>
<p/>
Related jenkins jobs:
<ul>
<li><a href=\"https://jenkins.metasfresh.com/job/ops/job/run_e2e_tests/parambuild/?MF_DOCKER_REGISTRY=${e2eDockerConf.pushRegistry}&MF_DOCKER_IMAGE=${e2eDockerImageNameNoRegistry}&MF_UPSTREAM_BUILD_URL=${BUILD_URL}\"><b>This link</b></a> lets you jump to a job that will perform an <b>e2e-test</b> using this job's docker image.</li>
</ul>
"""
} // node
} // timestamps
