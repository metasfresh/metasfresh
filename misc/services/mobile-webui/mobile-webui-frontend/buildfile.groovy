#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

Map build(final MvnConf mvnConf,
          final Map scmVars,
          final boolean forceBuild = false,
          final boolean forceSkip = false) {

    stage('Build mobile frontend') {

        currentBuild.description = """${currentBuild.description}<br/>
			<h2>Mobile frontend</h2>
		"""
        if (forceSkip) {
            currentBuild.description = """${currentBuild.description}<p/>
            Forced to skip.
            """
            echo "forced to skip mobile frontend"
            return
        }

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

        if (scmVars.GIT_COMMIT && scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT && !anyFileChanged && !forceBuild) {
            currentBuild.description = """${currentBuild.description}<p/>
					No changes happened in frontend.
					"""
            echo "no changes happened in frontend; skip building frontend"
            return
        }

        // set nodejs version defined in tool name of NodeJS installations located in Jenkins global plugins
        final NODEJS_TOOL_NAME = "nodejs-14"
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

        if (params.MF_MF_SKIP_UNIT_TESTS) {
            echo "params.MF_MF_SKIP_UNIT_TESTS=${params.MF_MF_SKIP_UNIT_TESTS}, so we skip the jest unit tests."
        } else {
            //sh 'yarn test --ci --reporters="default" --reporters="jest-junit"' TODO comment back in
            //junit 'junit.xml' // commenting out; might be that it published "everything"
        }

        sh 'yarn build'

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
}"""
        writeFile encoding: 'UTF-8', file: 'dist/info.json', text: version_info_json

        sh "tar cvzf mobile-frontend-dist-${env.MF_VERSION}.tar.gz build"

        withMaven(jdk: 'java-8-AdoptOpenJDK', maven: 'maven-3.6.3', mavenLocalRepo: '.repository', options: [artifactsPublisher(disabled: true)]) {
            sh "mvn --settings ${mvnConf.settingsFile} ${mvnConf.resolveParams} -Dfile=mobile-frontend-dist-${env.MF_VERSION}.tar.gz -Durl=${mvnConf.deployRepoURL} -DrepositoryId=${mvnConf.MF_MAVEN_REPO_ID} -DgroupId=de.metas.ui.web -DartifactId=metasfresh-mobile-frontend -Dversion=${env.MF_VERSION} -Dpackaging=tar.gz -DgeneratePom=true org.apache.maven.plugins:maven-deploy-plugin:2.7:deploy-file"
        }

        final Misc misc = new Misc()
        BUILD_ARTIFACT_URL = "${mvnConf.deployRepoURL}/de/metas/ui/web/metasfresh-mobile-frontend/${misc.urlEncode(env.MF_VERSION)}/metasfresh-mobile-frontend-${misc.urlEncode(env.MF_VERSION)}.tar.gz"
  

        currentBuild.description = """${currentBuild.description}<br/>
		This build's main artifacts (if not yet cleaned up) are
<ul>
<li><a href=\"${BUILD_ARTIFACT_URL}\">metasfresh-mobile-frontend-${env.MF_VERSION}.tar.gz</a></li>
</ul>"""
    }
}

return this
