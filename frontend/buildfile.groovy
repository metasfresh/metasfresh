#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

Map build(final MvnConf mvnConf, final Map scmVars)
{

	def status = sh(returnStatus: true, script: "git diff --name-only ${scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT} ${scmVars.GIT_COMMIT} .")
	echo "status of git dif command=${status}"
	if(scmVars.GIT_COMMIT && scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT && status != 0)
	{
		echo "no changes happened in backend; skip building backend";
		return;
	}

	// set nodejs version defined in tool name of NodeJS installations located in Jenkins global plugins
	final NODEJS_TOOL_NAME="nodejs-13"
	echo "Setting NODEJS_TOOL_NAME=$NODEJS_TOOL_NAME"

	final String MF_UPSTREAM_BRANCH = params.MF_UPSTREAM_BRANCH ?: env.BRANCH_NAME
	echo "params.MF_UPSTREAM_BRANCH=${params.MF_UPSTREAM_BRANCH}; env.BRANCH_NAME=${env.BRANCH_NAME}; => MF_UPSTREAM_BRANCH=${MF_UPSTREAM_BRANCH}"

	// https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
	final String MF_VERSION = retrieveArtifactVersion(MF_UPSTREAM_BRANCH, env.BUILD_NUMBER)
	//currentBuild.displayName = "artifact-version ${MF_VERSION}";

	//String BUILD_GIT_SHA1 = "NOT_YET_SET" // will be set when we check out

	String BUILD_ARTIFACT_URL

	stage('Build frontend')
	{
		//final def scmVars = checkout scm
		//BUILD_GIT_SHA1 = scmVars.GIT_COMMIT
		//sh 'git clean -d --force -x' // clean the workspace

		sh "if [ -d ~/.npm ]; then rm -r ~/.npm; fi" // make sure the .npm folder isn't there. it caused us problems in the past when it contained "stale files".

		def nodeHome = tool name: "$NODEJS_TOOL_NAME"
		env.PATH = "${nodeHome}/bin:${env.PATH}"

		sh 'yarn install'
		sh 'yarn lint --quiet'
							
		sh "yarn add jest jest-junit --dev"
		if(params.MF_MF_SKIP_UNIT_TESTS)
		{
			echo "params.MF_MF_SKIP_UNIT_TESTS=${params.MF_MF_SKIP_UNIT_TESTS}, so we skip the jest unit tests."
		}
		else 
		{
			sh 'yarn test --ci --reporters="default" --reporters="jest-junit"'
			junit 'junit.xml'
		}
					
		sh "webpack --config webpack.prod.js --bail --display-error-details"

		// https://github.com/metasfresh/metasfresh-webui-frontend/issues/292
		// add a file info.json whose shall look similar to the info which spring-boot provides unter the /info URL
		final version_info_json = """{
  \"build\": {
	\"releaseVersion\": \"${MF_VERSION}\",
    \"jenkinsBuildUrl\": \"${env.BUILD_URL}\",
    \"jenkinsBuildNo\": \"${env.BUILD_NUMBER}\",
    \"jenkinsJobName\": \"${env.JOB_NAME}\",
    \"jenkinsBuildTag\": \"${env.BUILD_TAG}\"
    \"gitSHA1\": \"${scmVars.GIT_COMMIT}\"
  }
}""";
		writeFile encoding: 'UTF-8', file: 'dist/info.json', text: version_info_json;

		sh "tar cvzf webui-dist-${MF_VERSION}.tar.gz dist"

		// upload our results to the maven repo

		configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')])
		{
			// create our config instance to be used further on
			// final MvnConf mvnConf = new MvnConf(
			// 	'pom.xml', // pomFile
			// 	MAVEN_SETTINGS, // settingsFile
			// 	"mvn-${MF_UPSTREAM_BRANCH}".replace("/", "-"), // mvnRepoName
			// 	'https://repo.metasfresh.com' // mvnRepoBaseURL - for resolve and deploy
			// )
			// echo "mvnConf=${mvnConf.toString()}"
			//nexusCreateRepoIfNotExists mvnConf.mvnDeployRepoBaseURL, mvnConf.mvnRepoName
			//withMaven(jdk: 'java-8', maven: 'maven-3.5.0', mavenLocalRepo: '.repository')
			//{
				sh "mvn --settings ${mvnConf.settingsFile} ${mvnConf.resolveParams} -Dfile=webui-dist-${MF_VERSION}.tar.gz -Durl=${mvnConf.deployRepoURL} -DrepositoryId=${mvnConf.MF_MAVEN_REPO_ID} -DgroupId=de.metas.ui.web -DartifactId=metasfresh-webui-frontend -Dversion=${MF_VERSION} -Dpackaging=tar.gz -DgeneratePom=true org.apache.maven.plugins:maven-deploy-plugin:2.7:deploy-file"

				final misc = new de.metas.jenkins.Misc()
				BUILD_ARTIFACT_URL="${mvnConf.deployRepoURL}/de/metas/ui/web/metasfresh-webui-frontend/${misc.urlEncode(MF_VERSION)}/metasfresh-webui-frontend-${misc.urlEncode(MF_VERSION)}.tar.gz"

			//} // withMaven
		} // configFileProvider

		// gh #968:
		// set env variables which will be available to a possible upstream job that might have called us
		// all those env variables can be gotten from <buildResultInstance>.getBuildVariables()
		// env.MF_VERSION=MF_VERSION
		// env.BUILD_GIT_SHA1=BUILD_GIT_SHA1


		final String publishedDockerImageName;
	
		sh 'cp -r dist docker/nginx'

		final DockerConf materialDispoDockerConf = new DockerConf(
			'metasfresh-webui-dev', // artifactName
			MF_UPSTREAM_BRANCH, // branchName
			MF_VERSION, // versionSuffix
			'docker/nginx' // workDir
		);
		publishedDockerImageName = dockerBuildAndPush(materialDispoDockerConf)
	}

	final misc = new de.metas.jenkins.Misc()
	currentBuild.description="""${currentBuild.description}<p/>
		<h2>Frontend</h2>
		This build's main artifacts (if not yet cleaned up) are
<ul>
<li><a href=\"${BUILD_ARTIFACT_URL}\">metasfresh-webui-frontend-${MF_VERSION}.tar.gz</a></li>
<li>a docker image with name <code>${publishedDockerImageName}</code>; Note that you can also use the tag <code>${misc.mkDockerTag(MF_UPSTREAM_BRANCH)}_LATEST</code></li>
</ul>"""

	// gh #968: set docker image name to be available to a possible upstream job that might have called us
//	env.MF_DOCKER_IMAGE=publishedDockerImageName

}

return this;