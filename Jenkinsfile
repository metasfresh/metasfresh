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

					buildBackend(mvnConf, scmVars)

					final Map artifactURLs = buildDistribution(mvnConf)

					testSQLMigrationScripts(
						params.MF_SQL_SEED_DUMP_URL, 
						artifactURLs['metasfresh-dist-sql-only'], 
						dbInitDockerImageName)
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

void buildBackend(final MvnConf mvnConf, final Map scmVars)
{
	dir('backend')
	{
		final List<String> changes = sh(returnStdout: true, script: "git diff --name-only ${scmVars.GIT_PREVIOUS_COMMIT} ${scmVars.GIT_COMMIT} .").split()
		// echo "changes=${changes}"
		if(changes.isEmpty())
		{
			echo "no changes happened in backend; skip building backend";
			return;
		}
		stage('Build backend code')
		{
			final String VERSIONS_PLUGIN='org.codehaus.mojo:versions-maven-plugin:2.5' // make sure we know which plugin version we run
			
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

			// deploy dist-artifacts. they were already installed further up, together with the rest
			final MvnConf webapiMvnConf = mvnConf.withPomFile('metasfresh-webui-api/pom.xml');
			sh "mvn --settings ${webapiMvnConf.settingsFile} --file ${webapiMvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${MF_VERSION} ${webapiMvnConf.resolveParams} ${webapiMvnConf.deployParam} deploy"
			
			final MvnConf distMvnConf = mvnConf.withPomFile('metasfresh-dist/dist/pom.xml');
			sh "mvn --settings ${distMvnConf.settingsFile} --file ${distMvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${MF_VERSION} ${distMvnConf.resolveParams} ${distMvnConf.deployParam} deploy"

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

			// postgres DB init container
			final DockerConf dbInitDockerConf = reportDockerConf
							.withArtifactName('metasfresh-db-init-pg-9-5')
							.withWorkDir('metasfresh-dist/dist/target/docker/db-init')
			final String publishedDBInitDockerImageName = dockerBuildAndPush(dbInitDockerConf)

			currentBuild.description= """${currentBuild.description}<p/>
				<h2>Backend</h2>
				<h3>Docker images</h3>
				This build created the following deployable docker images 
				<ul>
				<li><code>${publishedMsv3ServerImageName}</code></li>
				<li><code>${publishedWebuiApiImageName}</code></li>
				<li><code>${publishedReportDockerImageName}</code> that can be used as <b>base image</b> for custom metasfresh-report docker images</li>
				<li><code>${publishedDBInitDockerImageName}</code></li>
				</ul>
				"""
		}
	} // dir
}

Map buildDistribution(final MvnConf mvnConf)
{
	final artifactURLs = [:];

	dir('distribution')
	{
		stage('Resolve all distribution artifacts')
		{
			final String VERSIONS_PLUGIN='org.codehaus.mojo:versions-maven-plugin:2.5' // make sure we know which plugin version we run
			
			final def misc = new de.metas.jenkins.Misc();

			mvnUpdateParentPomVersion mvnConf

			final String metasfreshAdminPropertyParam="-Dproperty=metasfresh-admin.version -DnewVersion=LATEST"
			final String metasfreshWebFrontEndUpdatePropertyParam = "-Dproperty=metasfresh-webui-frontend.version -DnewVersion=LATEST"
			final String metasfreshWebApiUpdatePropertyParam = "-Dproperty=metasfresh-webui-api.version -DnewVersion=LATEST"
			final String metasfreshProcurementWebuiUpdatePropertyParam = "-Dproperty=metasfresh-procurement-webui.version -DnewVersion=LATEST"
			final String metasfreshUpdatePropertyParam="-Dproperty=metasfresh.version -DnewVersion=LATEST"

			// update the metasfresh.version property. either to the latest version or to the given params.MF_METASFRESH_VERSION.
			sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"

			// gh #968 also update the metasfresh-webui-frontend.version, metasfresh-webui-api.versions and procurement versions.
			sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshAdminPropertyParam} ${VERSIONS_PLUGIN}:update-property"
			sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshWebFrontEndUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"
			sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshWebApiUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"
			sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshProcurementWebuiUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"

			// set the artifact version of everything below the parent ${mvnConf.pomFile}
			sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"
			
			// we now have set the versions of metas-webui etc within the pom.xml. In order to document them, write them into a file.
			// the file's name is app.properties, as configured in metasfresh-parent's pom.xml. Thx to http://stackoverflow.com/a/26589696/1012103
			sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} org.codehaus.mojo:properties-maven-plugin:1.0.0:write-project-properties"

			// now load the properties we got from the pom.xml. Thx to http://stackoverflow.com/a/39644024/1012103
			final def mavenProps = readProperties file: 'app.properties'
			final def urlEncodedMavenProps = misc.urlEncodeMapValues(mavenProps);
			
			artifactURLs['metasfresh-admin'] = "${mvnConf.resolveRepoURL}/de/metas/admin/metasfresh-admin/${urlEncodedMavenProps['metasfresh-admin.version']}/metasfresh-admin-${urlEncodedMavenProps['metasfresh-admin.version']}.jar"
			artifactURLs['metasfresh-dist'] = "${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-dist/${misc.urlEncode(MF_VERSION)}/metasfresh-dist-dist-${misc.urlEncode(MF_VERSION)}-dist.tar.gz"
			artifactURLs['metasfresh-dist-sql-only'] = "${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-dist/${misc.urlEncode(MF_VERSION)}/metasfresh-dist-dist-${misc.urlEncode(MF_VERSION)}-sql-only.tar.gz"
			artifactURLs['metasfresh-material-dispo'] = "${mvnConf.resolveRepoURL}/de/metas/material/metasfresh-material-dispo-service/${urlEncodedMavenProps['metasfresh.version']}/metasfresh-material-dispo-service-${urlEncodedMavenProps['metasfresh.version']}.jar"
			artifactURLs['metasfresh-procurement-webui'] = "${mvnConf.resolveRepoURL}/de/metas/procurement/de.metas.procurement.webui/${urlEncodedMavenProps['metasfresh-procurement-webui.version']}/de.metas.procurement.webui-${urlEncodedMavenProps['metasfresh-procurement-webui.version']}.jar"
			artifactURLs['metasfresh-webui-api'] = "${mvnConf.resolveRepoURL}/de/metas/ui/web/metasfresh-webui-api/${urlEncodedMavenProps['metasfresh.version']}/metasfresh-webui-api-${urlEncodedMavenProps['metasfresh.version']}.jar"
			artifactURLs['metasfresh-webui-frontend'] = "${mvnConf.resolveRepoURL}/de/metas/ui/web/metasfresh-webui-frontend/${urlEncodedMavenProps['metasfresh-webui-frontend.version']}/metasfresh-webui-frontend-${urlEncodedMavenProps['metasfresh-webui-frontend.version']}.tar.gz"

			final MF_DOCKER_IMAGES = [:];

			final String MF_RELEASE_VERSION = misc.extractReleaseVersion(MF_VERSION)

			// Note: for the rollout-job's URL with the 'parambuild' to work on this pipelined jenkins, we need the https://wiki.jenkins-ci.org/display/JENKINS/Build+With+Parameters+Plugin, and *not* version 1.3, but later.
			// See
			//  * https://github.com/jenkinsci/build-with-parameters-plugin/pull/10
			//  * https://jenkins.ci.cloudbees.com/job/plugins/job/build-with-parameters-plugin/15/org.jenkins-ci.plugins$build-with-parameters/
			String releaseLinkWithText = "	<li>..and ${misc.createReleaseLinkWithText(MF_RELEASE_VERSION, MF_VERSION, artifactURLs, MF_DOCKER_IMAGES)}</li>";

			// don't link to the e2e-run-job with the lastest e2e-docker-image; users shall instead call that job from metasfresh-e2e-builds, so it's documented in the job-params which docker-tag was used
			final String e2eLinkWithText = "";
			//final String e2eLinkWithText = "<li>Oh, and <a href=\"https://jenkins.metasfresh.com/job/ops/job/run_e2e_tests/parambuild/?MF_DOCKER_IMAGE_FULL_NAME=${MF_DOCKER_IMAGES['metasfresh-e2e']}&MF_UPSTREAM_BUILD_URL=${BUILD_URL}&MF_DOCKER_REGISTRY=&MF_DOCKER_IMAGE=\"><b>this link</b></a> lets you jump to a job that will perform an <b>e2e-test</b> using the upstream metasfresh-e2e tests.</li>";

			currentBuild.description="""${currentBuild.description}<p/>
<h2>Distribution</h2>						
<h3>Version infos</h3>
<ul>
  <li>metasfresh-webui-frontend: version <b>${mavenProps['metasfresh-webui-frontend.version']}</b></li>
  <li>metasfresh-procurement-webui: version <b>${mavenProps['metasfresh-procurement-webui.version']}</b></li>
  <li>metasfresh-admin webui: version <b>${mavenProps['metasfresh-admin.version']}</b></li>
  <li>metasfresh backend: version <b>${mavenProps['metasfresh.version']}</b></li>
</ul>
<p>
<h3>Deployable maven artifacts</h3>
<ul>
	<li><a href=\"${artifactURLs['metasfresh-dist']}\">dist-tar.gz</a></li>
	<li><a href=\"${artifactURLs['metasfresh-dist-sql-only']}\">sql-only-tar.gz</a></li>
	<li><a href=\"${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-swingui/${misc.urlEncode(MF_VERSION)}/metasfresh-dist-swingui-${misc.urlEncode(MF_VERSION)}-client.zip\">client.zip</a></li>
	<li><a href=\"${artifactURLs['metasfresh-webui-api']}\">metasfresh-webui-api.jar</a></li>
	<li><a href=\"${artifactURLs['metasfresh-webui-frontend']}\">metasfresh-webui-frontend.tar.gz</a></li>
	<li><a href=\"${artifactURLs['metasfresh-procurement-webui']}\">metasfresh-procurement-webui.jar</a></li>
	<li><a href=\"${artifactURLs['metasfresh-material-dispo']}\">metasfresh-material-dispo.jar</a></li>
	<li><a href=\"${artifactURLs['metasfresh-admin']}\">metasfresh-admin.jar</a></li>
</ul>
Note: all the separately listed artifacts are also included in the dist-tar.gz
<p>
<h3>Deploy</h3>
<ul>
	<li><a href=\"https://jenkins.metasfresh.com/job/ops/job/deploy_metasfresh/parambuild/?MF_ROLLOUT_FILE_URL=${artifactURLs['metasfresh-dist']}&MF_UPSTREAM_BUILD_URL=${BUILD_URL}\"><b>This link</b></a> lets you jump to a rollout job that will deploy (roll out) the <b>tar.gz to a host of your choice</b>.</li>
	${releaseLinkWithText}
	${e2eLinkWithText}
</ul>
<p>
<h3>Additional notes</h3>
<ul>
  <li>The artifacts on <a href="${mvnConf.mvnDeployRepoBaseURL}">repo.metasfresh.com</a> are cleaned up on a regular schedule to preserve disk space.<br/>
    Therefore the artifacts that are linked to by the URLs above might already have been deleted.</li>
  <li>It is important to note that both the <i>"metasfresh-dist"</i> artifacts (client and backend server) build by this job and the <i>"webui"</i> artifacts that are also linked here are based on the same underlying metasfresh version.
</ul>
""";
		}
	}
	return artifactURLs;
}

void testSQLMigrationScripts(final String sqlSeedDumpURL, final String mestasfreshDistSQLOnlyURL, final String dbInitDockerImageName)
{
	final List<String> changes = sh(returnStdout: true, script: "git diff --name-only ${scmVars.GIT_PREVIOUS_COMMIT} ${scmVars.GIT_COMMIT} . | grep *.sql").split()
	echo "changes=${changes}"
	if(changes.isEmpty())
	{
		echo "no *.sql changes happened; skip building backend";
		return;
	}
	stage('Test SQL-Migration (docker)')
	{
		if(sqlSeedDumpURL)
		{
			// run the pg-init docker image to check that the migration scripts work; make sure to clean up afterwards
			sh "docker run --rm -e \"URL_SEED_DUMP=${sqlSeedDumpURL}\" -e \"URL_MIGRATION_SCRIPTS_PACKAGE=${mestasfreshDistSQLOnlyURL}\" ${dbInitDockerImageName}"
			sh "docker rmi ${dbInitDockerImageName}"
		}
		else
		{
			echo "We skip applying the migration scripts because params.MF_SQL_SEED_DUMP_URL was not set"
		}
	}
}