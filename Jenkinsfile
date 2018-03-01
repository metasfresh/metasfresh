#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

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

		string(defaultValue: '',
			description: 'Version of the metasfresh "main" code we shall use when resolving dependencies. Leave empty and this build will use the latest.',
			name: 'MF_UPSTREAM_VERSION'),

		booleanParam(defaultValue: true, description: 'Set to true if this build shall trigger "endcustomer" builds.<br>Set to false if this build is called from elsewhere and the orchestrating also takes place elsewhere',
			name: 'MF_TRIGGER_DOWNSTREAM_BUILDS')
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')) // keep the last 20 builds
])

timestamps
{
	MF_UPSTREAM_BRANCH = params.MF_UPSTREAM_BRANCH ?: env.BRANCH_NAME
	echo "params.MF_UPSTREAM_BRANCH=${params.MF_UPSTREAM_BRANCH}; env.BRANCH_NAME=${env.BRANCH_NAME}; => MF_UPSTREAM_BRANCH=${MF_UPSTREAM_BRANCH}"

	// https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
	final String MF_VERSION=retrieveArtifactVersion(MF_UPSTREAM_BRANCH, env.BUILD_NUMBER)
	currentBuild.displayName="artifact-version ${MF_VERSION}";

node('agent && linux') // shall only run on a jenkins agent with linux
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
        withMaven(jdk: 'java-8', maven: 'maven-3.5.2', mavenLocalRepo: '.repository')
        {
				stage('Set versions and build metasfresh-webui-api')
        {

        checkout scm; // i hope this to do all the magic we need
        sh 'git clean -d --force -x' // clean the workspace

				nexusCreateRepoIfNotExists mvnConf.mvnDeployRepoBaseURL, mvnConf.mvnRepoName

        // update the parent pom version
        mvnUpdateParentPomVersion mvnConf

		final String mavenUpdatePropertyParam;
		if(params.MF_UPSTREAM_VERSION)
		{
			final inSquaresIfNeeded = { String version -> return version == "LATEST" ? version: "[${version}]"; }
			// update the property, use the metasfresh version that we were given by the upstream job.
			// the square brackets are required if we have a conrete version (i.e. not "LATEST"); see https://github.com/mojohaus/versions-maven-plugin/issues/141 for details
			mavenUpdatePropertyParam="-Dproperty=metasfresh.version -DnewVersion=${inSquaresIfNeeded(params.MF_UPSTREAM_VERSION)}";
		}
		else
		{
			// still update the property, but use the latest version
			mavenUpdatePropertyParam='-Dproperty=metasfresh.version';
		}

				// update the metasfresh.version property. either to the latest version or to the given params.MF_UPSTREAM_VERSION.
				sh "mvn --debug --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${mavenUpdatePropertyParam} versions:update-property"

				// set the artifact version of everything below the webui's ${mvnConf.pomFile}
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=false -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas.ui.web*:*\" ${mvnConf.resolveParams} versions:set"

		final def misc = new de.metas.jenkins.Misc();

		final String BUILD_ARTIFACT_URL = "${mvnConf.deployRepoURL}/de/metas/ui/web/metasfresh-webui-api/${misc.urlEncode(MF_VERSION)}/metasfresh-webui-api-${misc.urlEncode(MF_VERSION)}.jar"

		// do the actual building and deployment
		// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
		sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"

		final DockerConf dockerConf = new DockerConf(
						'metasfresh-webui-api-dev', // artifactName
						MF_UPSTREAM_BRANCH, // branchName
						MF_VERSION, // versionSuffix
						'target/docker' // workDir
					)
		final String publishedDockerImageName =
					dockerBuildAndPush(dockerConf)

		// gh #968:
		// set env variables which will be available to a possible upstream job that might have called us
		// all those env variables can be gotten from <buildResultInstance>.getBuildVariables()
		// note: we do it here, because we also expect these vars to end up in the application.properties within our artifact
		env.BUILD_ARTIFACT_URL = BUILD_ARTIFACT_URL
		env.BUILD_CHANGE_URL = env.CHANGE_URL
		env.MF_VERSION = MF_VERSION
		env.BUILD_GIT_SHA1 = misc.getCommitSha1()
		env.BUILD_DOCKER_IMAGE = publishedDockerImageName
		env.MF_VERSION = MF_VERSION

		currentBuild.description="""This build's main artifacts (if not yet cleaned up) are
<ul>
<li>The executable jar <a href=\"${BUILD_ARTIFACT_URL}\">metasfresh-webui-api-${MF_VERSION}.jar</a></li>
<li>A docker image which you can run in docker via<br>
<code>docker run --rm -d -p 8080:8080 -e "DB_HOST=localhost" --name metasfresh-webui-api-${MF_VERSION} ${publishedDockerImageName}</code></li>
</ul>"""

				// after upgrading the "Pipeline Maven Integration Plugin" from 0.7 to 3.1.0, collecting the tests is now done by that plugin.
				// comment it out to avoid all tests being counted twice
				// junit '**/target/surefire-reports/*.xml'

				// thx to https://github.com/jenkinsci/jacoco-plugin/pull/83
				jacoco exclusionPattern: '**/src/main/java-gen'
      } // stage
		  } // withMaven
			} // withEnv
   } // configFileProvider
 } // node

if(params.MF_TRIGGER_DOWNSTREAM_BUILDS)
{
	stage('Invoke downstream job')
	{
   def misc = new de.metas.jenkins.Misc();
   final String jobName = misc.getEffectiveDownStreamJobName('metasfresh', MF_UPSTREAM_BRANCH);

   build job: jobName,
     parameters: [
       string(name: 'MF_UPSTREAM_BRANCH', value: MF_UPSTREAM_BRANCH),
       string(name: 'MF_UPSTREAM_BUILDNO', value: env.BUILD_NUMBER),
       string(name: 'MF_UPSTREAM_VERSION', value: MF_VERSION),
       string(name: 'MF_UPSTREAM_JOBNAME', value: 'metasfresh-webui'),
       booleanParam(name: 'MF_TRIGGER_DOWNSTREAM_BUILDS', value: true), // metasfresh shall trigger the "-dist" jobs
       booleanParam(name: 'MF_SKIP_TO_DIST', value: true) // this param is only recognised by metasfresh
     ], wait: false
	}
}
else
{
	echo "params.MF_TRIGGER_DOWNSTREAM_BUILDS=${params.MF_TRIGGER_DOWNSTREAM_BUILDS}, so we do not trigger any downstream builds"
}
} // timestamps
