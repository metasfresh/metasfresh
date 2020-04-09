#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf

def build(final Map scmVars)
{
	// https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
	//final String MF_VERSION = retrieveArtifactVersion(env.BRANCH_NAME, env.BUILD_NUMBER)
	
	stage('Build e2e')
	{
		currentBuild.description="""${currentBuild.description}<br/>
				<h2>e2e</h2>
			"""
		def status = sh(returnStatus: true, script: "git diff --name-only ${scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT} ${scmVars.GIT_COMMIT} .")
		echo "status of git dif command=${status}"
		if(scmVars.GIT_COMMIT && scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT && status != 0)
		{
			currentBuild.description= """${currentBuild.description}<p/>
					No changes happened in e2e; skipped building it.
					"""
			echo "no changes happened in backend; skip building backend";
			return;
		}

		final def dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd")
		final String currentDate = dateFormat.format(new Date())

		final String additionalBuildArgs = "--build-arg CACHEBUST=${currentDate}"
		final DockerConf e2eDockerConf = new DockerConf(
			'metasfresh-e2e', // artifactName
			env.BRANCH_NAME, // branchName
			env.MF_VERSION, // versionSuffix
			'.', // workDir
			additionalBuildArgs
		);
		final String publishedE2eDockerImageName;
		// stage('Build and push e2e docker image')
		// {
			// echo "e2eDockerConf=${e2eDockerConf.toString()}"
			publishedE2eDockerImageName = dockerBuildAndPush(e2eDockerConf)
		// }

		final String e2eDockerImageNameNoRegistry=publishedE2eDockerImageName.substring("${e2eDockerConf.pushRegistry}/".length());

		currentBuild.description="""${currentBuild.description}<br/>
		This build's main artifact (if not yet cleaned up) is
	<ul>
	<li>a docker image with name <code>${publishedE2eDockerImageName}</code>; Note that you can also use the tag <code>${env.BRANCH_NAME}_LATEST</code></li>
	</ul>
	<p/>
	You can run the docker image like this:<br>
	<code>
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
	</code>
	<p/>
	Also see metasfresh-e2e's <a href="https://github.com/metasfresh/metasfresh-e2e/blob/master/README.md">README.md</a>.
	<p/>
	Related jenkins jobs:
	<ul>
	<li><a href=\"https://jenkins.metasfresh.com/job/ops/job/run_e2e_tests/parambuild/?MF_DOCKER_IMAGE_FULL_NAME=${publishedE2eDockerImageName}&MF_DOCKER_REGISTRY=&MF_DOCKER_IMAGE=&MF_UPSTREAM_BUILD_URL=${BUILD_URL}\"><b>This link</b></a> lets you jump to a job that will perform an <b>e2e-test</b> using this job's docker image.</li>
	</ul>
	"""
		// // gh #968: set version and docker image name to be available to a possible upstream job that might have called us
		// env.MF_VERSION=MF_VERSION
		// env.MF_DOCKER_IMAGE=publishedE2eDockerImageName
		// env.BUILD_GIT_SHA1=scmVars.GIT_COMMIT
	} // stage
}

return this;
