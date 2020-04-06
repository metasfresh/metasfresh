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
				"mvn-${MF_UPSTREAM_BRANCH}".replace("/", "-"), // mvnRepoName
				'https://repo.metasfresh.com' // mvnRepoBaseURL
			)
			echo "mvnConf=${mvnConf.toString()}"

			def scmVars = checkout scm; // i hope this to do all the magic we need
			def gitCommitHash = scmVars.GIT_COMMIT

			sh 'git clean -d --force -x' // clean the workspace

			// we need to provide MF_VERSION because otherwise  the profile "MF_VERSION-env-missing" would be activated from the metasfresh-parent pom.xml
			// and therefore, the jenkins information would not be added to the build.properties info file.
			withEnv(["MF_VERSION=${MF_VERSION}"])
			{
			// disable automatic fingerprinting and archiving by artifactsPublisher, because in particular the archiving takes up too much space on the jenkins server.
			withMaven(jdk: 'java-8', maven: 'maven-3.5.4', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M', options: [artifactsPublisher(disabled: true)])
			{
				nexusCreateRepoIfNotExists mvnConf.mvnDeployRepoBaseURL, mvnConf.mvnRepoName

				dir('backend')
				{
					stage('Build backend code')
					{
						// update the parent pom version
						mvnUpdateParentPomVersion mvnConf

						// set the artifact version of everything below ${mvnConf.pomFile}
						// processAllModules=true: also update those modules that have a parent version range!
						sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DprocessAllModules=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"
						// Set the metasfresh.version property from [1,10.0.0] to our current build version
						// From the documentation: "Set a property to a given version without any sanity checks"; that's what we want here..sanity is clearly overated
						sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dproperty=metasfresh.version -DnewVersion=${MF_VERSION} ${VERSIONS_PLUGIN}:set-property"
						// build and install
						// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
						// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
						sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${MF_VERSION} ${mvnConf.resolveParams} ${mvnConf.deployParam} clean install"

						publishJacocoReports(scmVars.GIT_COMMIT, 'codacy_project_token_for_metasfresh_repo')
					} // stage

					stage('Build backend docker images')
					{
						final def misc = new de.metas.jenkins.Misc();
						final DockerConf reportDockerConf = new DockerConf(
							'metasfresh-report', // artifactName
							MF_UPSTREAM_BRANCH, // branchName
							MF_VERSION, // versionSuffix
							'de.metas.report/metasfresh-report-service-standalone/target/docker' // workDir
						);
						final String publishedReportDockerImageName = dockerBuildAndPush(reportDockerConf)

						final DockerConf msv3ServerDockerConf = reportDockerConf
							.withArtifactName('de.metas.vertical.pharma.msv3.server')
							.withWorkDir('de.metas.vertical.pharma.msv3.server/target/docker');
						final String publishedMsv3ServerImageName = dockerBuildAndPush(msv3ServerDockerConf)

						final DockerConf webuiApiDockerConf = reportDockerConf
							.withArtifactName('metasfresh-webui-api')
							.withWorkDir('metasfresh-webui-api/target/docker');
						final String publishedWebuiApiImageName = dockerBuildAndPush(webuiApiDockerConf)

						currentBuild.description= """${currentBuild.description}<p/>
							<h3>Backend docker images</h3>
							This build created the following deployable docker images 
							<ul>
							<li><code>${publishedMsv3ServerImageName}</code></li>
							<li><code>${publishedWebuiApiImageName}</code></li>
							<li><code>${publishedReportDockerImageName}</code> that can be used as <b>base image</b> for custom metasfresh-report docker images</li>
							</ul>
							"""
					}
				} // dir

				dir('distribution')
				{
					stage('Build distribution artifacts')
					{
						mvnUpdateParentPomVersion mvnConf
						sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DprocessAllModules=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"
						sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dproperty=metasfresh.version -DnewVersion=${MF_VERSION} ${VERSIONS_PLUGIN}:set-property"
						// *deploy* dist-artifacts
						sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${MF_VERSION} ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"
					}
					stage('Build distribution docker images')
					{
						final def misc = new de.metas.jenkins.Misc();

						// postgres DB init container
						final DockerConf dbInitDockerConf = reportDockerConf
										.withArtifactName('metasfresh-db-init-pg-9-5')
										.withWorkDir('dist/target/docker/db-init')
						final String publishedDBInitDockerImageName = dockerBuildAndPush(dbInitDockerConf)

						currentBuild.description= """${currentBuild.description}<p/>
							<h3>Distribution docker images</h3>
							This build created the following deployable docker images 
							<ul>
							<li><code>${publishedDBInitDockerImageName}</code></li>
							</ul>
							"""
					} // stage
				} 
			} // withMaven
			} // withEnv
		} // configFileProvider
		
		cleanWs cleanWhenAborted: false, cleanWhenFailure: false // clean up the workspace after (successfull) builds
	} // node
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
