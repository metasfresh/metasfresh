#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

chuckNorris()

final String MF_UPSTREAM_BRANCH = params.MF_UPSTREAM_BRANCH ?: env.BRANCH_NAME
echo "params.MF_UPSTREAM_BRANCH=${params.MF_UPSTREAM_BRANCH}; env.BRANCH_NAME=${env.BRANCH_NAME}; => MF_UPSTREAM_BRANCH=${MF_UPSTREAM_BRANCH}"

// keep the last 20 builds for master and stable, but onkly the last 5 for the rest, to preserve disk space on jenkins
final String numberOfBuildsToKeepStr = (MF_UPSTREAM_BRANCH == 'master' || MF_UPSTREAM_BRANCH == 'stable') ? '50' : '20'

final String MF_SQL_SEED_DUMP_URL_DEFAULT = 
	env.BRANCH_NAME == 'release' 
		? 'https://metasfresh.com/wp-content/releases/db_seeds/metasfresh-5_39.pgdump' 
		: 'https://metasfresh.com/wp-content/releases/db_seeds/metasfresh_latest.pgdump'

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
			description: 'Version of the upstream job\'s artifact that was build by the job which called us. Shall be used when resolving the upstream depdendency. Leave empty and this build will use the latest.',
			name: 'MF_UPSTREAM_ARTIFACT_VERSION'),

		string(defaultValue: '',
				description: 'If metasfresh-frontend calls this job, then it uses this variable to forward the metasfresh-edi docker image name which it build',
				name: 'MF_METASFRESH_EDI_DOCKER_IMAGE'),

		string(defaultValue: MF_SQL_SEED_DUMP_URL_DEFAULT,
				description: 'metasfresh database seed against which the build shall apply its migrate scripts for QA; leave empty to avoid this QA.',
				name: 'MF_SQL_SEED_DUMP_URL')
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: numberOfBuildsToKeepStr)) // keep the last $numberOfBuildsToKeepStr builds
]);

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

			final def scmVars = checkout scm // i hope this to do all the magic we need
			//echo "scmVars=${scmVars}"

			currentBuild.description = """${currentBuild.description}
			<b>
			<ul>
			<li>This job builds commit <a href="https://github.com/metasfresh/metasfresh/commit/${scmVars.GIT_COMMIT}">${scmVars.GIT_COMMIT}</a></li>
			<li>The changes since the last successful commit are <a href="https://github.com/metasfresh/metasfresh/compare/${scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT}..${scmVars.GIT_COMMIT}">here</a></li>
			</ul>
			</b>
			"""

			sh 'git clean -d --force -x' // clean the workspace

			// we need to provide MF_VERSION because otherwise  the profile "MF_VERSION-env-missing" would be activated from the metasfresh-parent pom.xml
			// and therefore, the jenkins information would not be added to the build.properties info file.
			withEnv(["MF_VERSION=${MF_VERSION}"])
			{
				// disable automatic fingerprinting and archiving by artifactsPublisher, because in particular the archiving takes up too much space on the jenkins server.
				withMaven(jdk: 'java-8', maven: 'maven-3.5.4', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M', options: [artifactsPublisher(disabled: true)])
				{
					nexusCreateRepoIfNotExists(mvnConf.mvnDeployRepoBaseURL, mvnConf.mvnRepoName)

					dir('misc/parent-pom')
					{
						def parentPom = load('buildfile.groovy')
						parentPom.build(mvnConf, scmVars)
					}
					// note: to do some of this in parallel, we first need to make sure that the different parts don't concurrently write to the build description
					dir('frontend')
					{
						def frontendBuildFile = load('buildfile.groovy')
						frontendBuildFile.build(mvnConf, scmVars)
					}
					dir('backend')
					{
						def backendBuildFile = load('buildfile.groovy')
						backendBuildFile.build(mvnConf, scmVars)
					}
					dir('misc/services')
					{
						def miscServices = load('buildfile.groovy')
						miscServices.build(mvnConf, scmVars)
					}
					dir('e2e')
					{
						def e2eBuildFile = load('buildfile.groovy')
						e2eBuildFile.build(scmVars)
					}
					dir('distribution')
					{
						def distributionBuildFile = load('buildfile.groovy')
						distributionBuildFile.build(mvnConf);
					}
					
					//junit '**/target/surefire-reports/*.xml'
					publishJacocoReports(scmVars.GIT_COMMIT, 'codacy_project_token_for_metasfresh_repo')
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
