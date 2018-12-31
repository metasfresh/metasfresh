#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf

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
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')) // keep the last 20 builds
])

chuckNorris()

timestamps
{
	final String MF_UPSTREAM_BRANCH = params.MF_UPSTREAM_BRANCH ?: env.BRANCH_NAME
	echo "params.MF_UPSTREAM_BRANCH=${params.MF_UPSTREAM_BRANCH}; env.BRANCH_NAME=${env.BRANCH_NAME}; => MF_UPSTREAM_BRANCH=${MF_UPSTREAM_BRANCH}"

	// https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
	final String MF_VERSION = retrieveArtifactVersion(MF_UPSTREAM_BRANCH, env.BUILD_NUMBER)
	currentBuild.displayName = "artifact-version ${MF_VERSION}";

node('agent && linux') // shall only run on a jenkins agent with linux
{
	checkout scm // i hope this to do all the magic we need
	sh 'git clean -d --force -x' // clean the workspace

 	final def dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd")
    final def date = new Date()
    final String currentDate = dateFormat.format(date)

	final String additionalBuildArgs = "--build-arg CACHEBUST=${currentDate} --build-arg GIT_BRANCH=${MF_UPSTREAM_BRANCH}"
	final String publishedE2eDockerImageName;
	stage('Build and push e2e docker image')
	{
		final DockerConf e2eDockerConf = new DockerConf(
			'metasfresh-e2e', // artifactName
			MF_UPSTREAM_BRANCH, // branchName
			MF_VERSION, // versionSuffix
			'.', // workDir
			additionalBuildArgs
		);
		// echo "e2eDockerConf=${e2eDockerConf.toString()}"
		publishedE2eDockerImageName = dockerBuildAndPush(e2eDockerConf)
	}

	currentBuild.description="""This build's main artifacts (if not yet cleaned up) are
<ul>
<li>a docker image with name <code>${publishedE2eDockerImageName}</code><br>
</ul>
<p/>
To run the docker image like this:<br>
<code>
docker run --rm\
 -e "FRONTEND_URL=http://172.17.0.1:30080"\
 -e "API_URL=http=http://172.17.0.1:8080/rest/api"\
 -e "WS_URL=http=http://172.17.0.1:8080/stomp"\
 ${publishedE2eDockerImageName}
</code>
<p/>
If you want to upload the test results to the cypress dashboard of metasfresh, then also include the parameter
<code>
-e "RECORD_KEY=<the-secret-key>"
</code>
"""
} // node
} // timestamps
