#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

Map build(final MvnConf mvnConf, final Map scmVars, final boolean forceBuild=false)
{
	stage('Build frontend')
	{
		currentBuild.description="""${currentBuild.description}<br/>
			<h2>Frontend</h2>
		"""

    def anyFileChanged
    try {
      def vgitout = sh(returnStdout: true, script: "git diff --name-only ${scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT} ${scmVars.GIT_COMMIT} .").trim()
      echo "git diff output (modified files):\n>>>>>\n${vgitout}\n<<<<<"
      anyFileChanged = !vgitout.isEmpty()
      // see if anything at all changed in this folder
      echo "Any file changed compared to last build: ${anyFileChanged}"
    } catch (ignored) {
      echo "git diff error => assume something must have changed"
      anyFileChanged = true
    }

		if(scmVars.GIT_COMMIT && scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT && !anyFileChanged && !forceBuild)
		{
			currentBuild.description= """${currentBuild.description}<p/>
					No changes happened in frontend.
					"""
			echo "no changes happened in frontend; skip building frontend";
			return;
		}

		// set nodejs version defined in tool name of NodeJS installations located in Jenkins global plugins
		final NODEJS_TOOL_NAME="nodejs-13"
		echo "Setting NODEJS_TOOL_NAME=$NODEJS_TOOL_NAME"
		
		String BUILD_ARTIFACT_URL

		//final def scmVars = checkout scm
		//BUILD_GIT_SHA1 = scmVars.GIT_COMMIT
		//sh 'git clean -d --force -x' // clean the workspace

		sh "if [ -d ~/.npm ]; then rm -r ~/.npm; fi" // make sure the .npm folder isn't there. it caused us problems in the past when it contained "stale files".

		def nodeHome = tool name: "$NODEJS_TOOL_NAME"
		env.PATH = "${nodeHome}/bin:${env.PATH}"

		sh 'yarn install'
		sh 'yarn lint --quiet'
							
		if(params.MF_MF_SKIP_UNIT_TESTS)
		{
			echo "params.MF_MF_SKIP_UNIT_TESTS=${params.MF_MF_SKIP_UNIT_TESTS}, so we skip the jest unit tests."
		}
		else 
		{
			sh 'yarn test --ci --reporters="default" --reporters="jest-junit"'
			//junit 'junit.xml' // commenting out; might be that it published "everything"
		}
					
		sh "webpack --config webpack.prod.js --bail --display-error-details"

		// https://github.com/metasfresh/metasfresh-webui-frontend/issues/292
		// add a file info.json whose shall look similar to the info which spring-boot provides unter the /info URL
		final version_info_json = """{
  \"build\": {
	\"releaseVersion\": \"${env.MF_VERSION}\",
    \"jenkinsBuildUrl\": \"${env.BUILD_URL}\",
    \"jenkinsBuildNo\": \"${env.BUILD_NUMBER}\",
    \"jenkinsJobName\": \"${env.JOB_NAME}\",
    \"jenkinsBuildTag\": \"${env.BUILD_TAG}\"
    \"gitSHA1\": \"${scmVars.GIT_COMMIT}\"
  }
}""";
		writeFile encoding: 'UTF-8', file: 'dist/info.json', text: version_info_json;

		sh "tar cvzf webui-dist-${env.MF_VERSION}.tar.gz dist"

		sh "mvn --settings ${mvnConf.settingsFile} ${mvnConf.resolveParams} -Dfile=webui-dist-${env.MF_VERSION}.tar.gz -Durl=${mvnConf.deployRepoURL} -DrepositoryId=${mvnConf.MF_MAVEN_REPO_ID} -DgroupId=de.metas.ui.web -DartifactId=metasfresh-webui-frontend -Dversion=${env.MF_VERSION} -Dpackaging=tar.gz -DgeneratePom=true org.apache.maven.plugins:maven-deploy-plugin:2.7:deploy-file"

		final misc = new de.metas.jenkins.Misc()
		BUILD_ARTIFACT_URL="${mvnConf.deployRepoURL}/de/metas/ui/web/metasfresh-webui-frontend/${misc.urlEncode(env.MF_VERSION)}/metasfresh-webui-frontend-${misc.urlEncode(env.MF_VERSION)}.tar.gz"
	
		sh 'cp -r dist docker/nginx'

		final DockerConf materialDispoDockerConf = new DockerConf(
			'metasfresh-webui-dev', // artifactName
			env.BRANCH_NAME, // branchName
			env.MF_VERSION, // versionSuffix
			'docker/nginx' // workDir
		);
		final String publishedDockerImageName = dockerBuildAndPush(materialDispoDockerConf)

		currentBuild.description="""${currentBuild.description}<br/>
		This build's main artifacts (if not yet cleaned up) are
<ul>
<li><a href=\"${BUILD_ARTIFACT_URL}\">metasfresh-webui-frontend-${env.MF_VERSION}.tar.gz</a></li>
<li>a docker image with name <code>${publishedDockerImageName}</code>; Note that you can also use the tag <code>${misc.mkDockerTag(env.BRANCH_NAME)}_LATEST</code></li>
</ul>"""
	}
}

return this;
