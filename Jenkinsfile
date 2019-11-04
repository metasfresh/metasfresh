#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

// always offer deployment, because there might be different tasks/branches to roll out
final skipDeploymentParamDefaultValue = false;

final String MF_SQL_SEED_DUMP_URL_DEFAULT = 
	env.BRANCH_NAME == 'release' 
		? 'https://metasfresh.com/wp-content/releases/db_seeds/metasfresh-5_39.pgdump' 
		: 'https://metasfresh.com/wp-content/releases/db_seeds/metasfresh_latest.pgdump'

// thx to http://stackoverflow.com/a/36949007/1012103 with respect to the paramters
properties([
	parameters([
		string(defaultValue: '',
			description: '''If this job is invoked via an updstream build job, then that job can provide either its branch or the respective <code>MF_UPSTREAM_BRANCH</code> that was passed to it.<br>
This build will then attempt to use maven dependencies from that branch, and it will set its own name to reflect the given value.
<p>
So if this is a "master" build, but it was invoked by a "feature-branch" build then this build will try to get the feature-branch\'s build artifacts annd will set its
<code>currentBuild.displayname</code> and <code>currentBuild.description</code> to make it obvious that the build contains code from the feature branch.''',
			name: 'MF_UPSTREAM_BRANCH'),

		string(defaultValue: '',
			description: 'Build number of the upstream job that called us, if any.',
			name: 'MF_UPSTREAM_BUILDNO'),

		string(defaultValue: 'release_LATEST',
			description: '''Tag/version of the metasfresh-report base image. Note: the image does not contain jasper files, so there is no need to have a particual base image for that.
<p>
Backouground: if you e.g. specify gh47 as MF_UPSTREAM_BRANCH and a particular artifact doesn exist in that maven repo, then the maven repo at nexus is set to fall back to "master". 
For docker we currently don not have such an arrangement.''',
			name: 'MF_METASFRESH_REPORT_DOCKER_BASE_IMAGE_VERSION'),

		string(defaultValue: '',
			description: 'Version of the metasfresh "main" code we shall use when resolving dependencies. Leave empty and this build will use the latest.',
			name: 'MF_METASFRESH_VERSION'),

		string(defaultValue: '',
				description: 'Version of the metasfresh-admin (spring-boot-admin) webui to include in the distribution. Leave empty and this build will use the latest.',
				name: 'MF_METASFRESH_ADMIN_VERSION'),

		string(defaultValue: '',
			description: 'Version of the metasfresh procurement webui to include in the distribution. Leave empty and this build will use the latest.',
			name: 'MF_METASFRESH_PROCUREMENT_WEBUI_VERSION'),

		string(defaultValue: '',
			description: 'Version of the metasfresh-webui(-API) service to include in the distribution. Leave empty and this build will use the latest.',
			name: 'MF_METASFRESH_WEBUI_API_VERSION'),

		string(defaultValue: '',
			description: 'Version of the metasfresh-webui-frontend webui to include in the distribution. Leave empty and this build will use the latest.',
			name: 'MF_METASFRESH_WEBUI_FRONTEND_VERSION'),

		string(defaultValue: '',
			description: 'metasfresh-edi (camel) docker image. Leave empty and this build will <code>&gt;effective-branch-name&lt;_LATEST</code>',
			name: 'MF_METASFRESH_EDI_DOCKER_IMAGE'),

		string(defaultValue: '',
			description: 'e2e docker image. Leave empty and this build will <code>&lt;effective-branch-name&gt;_LATEST</code>',
			name: 'MF_METASFRESH_E2E_DOCKER_IMAGE'),

		string(defaultValue: MF_SQL_SEED_DUMP_URL_DEFAULT,
			description: 'metasfresh database seed against which the build shall apply its migrate scripts for QA; leave empty to avoid this QA.',
			name: 'MF_SQL_SEED_DUMP_URL')

	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')) // keep the last 20 builds
])

timestamps
{
	MF_UPSTREAM_BRANCH = params.MF_UPSTREAM_BRANCH ?: env.BRANCH_NAME
	echo "params.MF_UPSTREAM_BRANCH=${params.MF_UPSTREAM_BRANCH}; env.BRANCH_NAME=${env.BRANCH_NAME}; => MF_UPSTREAM_BRANCH=${MF_UPSTREAM_BRANCH}"

	// gh #968 create a map equal to the one we create in metasfresh/Jenkinsfile. The way we use it further down is also similar
	final MF_ARTIFACT_VERSIONS = [:];
	MF_ARTIFACT_VERSIONS['metasfresh'] = params.MF_METASFRESH_VERSION ?: "LATEST";
	MF_ARTIFACT_VERSIONS['metasfresh-admin'] = params.MF_METASFRESH_ADMIN_VERSION ?: "LATEST";
	MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'] = params.MF_METASFRESH_PROCUREMENT_WEBUI_VERSION ?: "LATEST";
	MF_ARTIFACT_VERSIONS['metasfresh-webui'] = params.MF_METASFRESH_WEBUI_API_VERSION ?: "LATEST";
	MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'] = params.MF_METASFRESH_WEBUI_FRONTEND_VERSION ?: "LATEST";

	final def misc = new de.metas.jenkins.Misc();
	
	final String fallbackDockerTag =  misc.mkDockerTag("${MF_UPSTREAM_BRANCH}_LATEST")
	final MF_DOCKER_IMAGES = [:];
	MF_DOCKER_IMAGES['metasfresh-e2e'] = params.MF_METASFRESH_E2E_DOCKER_IMAGE ?: "nexus.metasfresh.com:6001/metasfresh/metasfresh-e2e:${fallbackDockerTag}"
	MF_DOCKER_IMAGES['metasfresh-edi'] = params.MF_METASFRESH_EDI_DOCKER_IMAGE ?: "nexus.metasfresh.com:6001/metasfresh/de-metas-edi-esb-camel:${fallbackDockerTag}"

	final MF_ARTIFACT_URLS = [:];
	String dbInitDockerImageName; // will be set if and when the docker image is created


	// https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
	final String MF_VERSION = retrieveArtifactVersion(MF_UPSTREAM_BRANCH, env.BUILD_NUMBER)
	currentBuild.displayName = "artifact-version ${MF_VERSION}";

	final String MF_RELEASE_VERSION = misc.extractReleaseVersion(MF_VERSION)

node('agent && linux')
{
	configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')])
	{

		// as of now, /base/src/main/resources/org/adempiere/version.properties contains "env.MF_BUILD_VERSION", "env.MF_UPSTREAM_BRANCH" and others,
		// which needs to be replaced when version.properties is dealt with by the ressources plugin, see https://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html
		withEnv([
				"MF_VERSION=${MF_VERSION}",
				"MF_RELEASE_VERSION=${MF_RELEASE_VERSION}",
				"MF_BUILD_DATE=${misc.mkReleaseDate()}",
				"MF_UPSTREAM_BRANCH=${MF_UPSTREAM_BRANCH}",
				"CHANGE_URL=${env.CHANGE_URL}",
				"BUILD_NUMBER=${env.BUILD_NUMBER}"])
		{
		withMaven(jdk: 'java-8', maven: 'maven-3.5.0', mavenLocalRepo: '.repository')
		{
			// create our config instance to be used further on
			final MvnConf mvnConf = new MvnConf(
				'pom.xml', // pomFile
				MAVEN_SETTINGS, // settingsFile
				"mvn-${MF_UPSTREAM_BRANCH}".replace("/", "-"), // mvnRepoName
				'https://repo.metasfresh.com' // mvnRepoBaseURL - for resolve and deploy
			)
			echo "Created mvnConf=${mvnConf.toString()}"

			nexusCreateRepoIfNotExists mvnConf.mvnDeployRepoBaseURL, mvnConf.mvnRepoName

			stage('Set versions and build dist')
			{
				// checkout our code
				// note that we do not know if the stuff we checked out in the other node is available here, so we somehow need to make sure by checking out (again).
				// see: https://groups.google.com/forum/#!topic/jenkinsci-users/513qLiYlXHc
				checkout scm; // i hope this to do all the magic we need
				sh 'git clean -d --force -x' // clean the workspace

				// update the parent pom version
				mvnUpdateParentPomVersion mvnConf

				// make sure we know which plugin version we run
				final String versionsPlugin='org.codehaus.mojo:versions-maven-plugin:2.4'

				final inSquaresIfNeeded = { String version -> return version == "LATEST" ? version: "[${version}]"; }

				// the square brackets in "-DnewVersion" are required if we have a concrete version (i.e. not "LATEST"); see https://github.com/mojohaus/versions-maven-plugin/issues/141 for details
				final String metasfreshAdminPropertyParam="-Dproperty=metasfresh-admin.version -DnewVersion=${inSquaresIfNeeded(MF_ARTIFACT_VERSIONS['metasfresh-admin'])}"
				final String metasfreshWebFrontEndUpdatePropertyParam = "-Dproperty=metasfresh-webui-frontend.version -DnewVersion=${inSquaresIfNeeded(MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'])}"
				final String metasfreshWebApiUpdatePropertyParam = "-Dproperty=metasfresh-webui-api.version -DnewVersion=${inSquaresIfNeeded(MF_ARTIFACT_VERSIONS['metasfresh-webui'])}"
				final String metasfreshProcurementWebuiUpdatePropertyParam = "-Dproperty=metasfresh-procurement-webui.version -DnewVersion=${inSquaresIfNeeded(MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'])}"
				final String metasfreshUpdatePropertyParam="-Dproperty=metasfresh.version -DnewVersion=${inSquaresIfNeeded(MF_ARTIFACT_VERSIONS['metasfresh'])}"


				// update the metasfresh.version property. either to the latest version or to the given params.MF_METASFRESH_VERSION.
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshUpdatePropertyParam} ${versionsPlugin}:update-property"

				// gh #968 also update the metasfresh-webui-frontend.version, metasfresh-webui-api.versions and procurement versions.
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshAdminPropertyParam} ${versionsPlugin}:update-property"
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshWebFrontEndUpdatePropertyParam} ${versionsPlugin}:update-property"
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshWebApiUpdatePropertyParam} ${versionsPlugin}:update-property"
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshProcurementWebuiUpdatePropertyParam} ${versionsPlugin}:update-property"

				// set the artifact version of everything below the parent ${mvnConf.pomFile}
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true ${mvnConf.resolveParams} ${versionsPlugin}:set"

				// do the actual building and deployment
				// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
				// about -Dmaven.test.failure.ignore=true: continue if tests fail, because we want a full report.
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${MF_VERSION} ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"


				// Currently there are no tests. Don't try to collect any, or a typical error migh look like this:
				// ERROR: Test reports were found but none of them are new. Did tests run?
				// For example, /var/lib/jenkins/workspace/metasfresh_FRESH-854-gh569-M6AHOWSSP3FKCR7CHWVIRO5S7G64X4JFSD4EZJZLAT5DONP2ZA7Q/de.metas.acct.base/target/surefire-reports/TEST-de.metas.acct.impl.FactAcctLogBLTest.xml is 2 min 57 sec old
				// junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

				// we now have set the versions of metas-webui etc within the pom.xml. In order to document them, write them into a file.
				// the file's name is app.properties, as configured in metasfresh-parent's pom.xml. Thx to http://stackoverflow.com/a/26589696/1012103
				final MvnConf mvnDistConf = mvnConf.withPomFile('dist/pom.xml')
				sh "mvn --settings ${mvnDistConf.settingsFile} --file ${mvnDistConf.pomFile} --batch-mode ${mvnDistConf.resolveParams} org.codehaus.mojo:properties-maven-plugin:1.0.0:write-project-properties"

				// now load the properties we got from the pom.xml. Thx to http://stackoverflow.com/a/39644024/1012103
				final def mavenProps = readProperties file: 'dist/app.properties'
				final def urlEncodedMavenProps = misc.urlEncodeMapValues(mavenProps);

				final MF_ARTIFACT_URLS = [:];
				MF_ARTIFACT_URLS['metasfresh-admin'] = "${mvnConf.resolveRepoURL}/de/metas/admin/metasfresh-admin/${urlEncodedMavenProps['metasfresh-admin.version']}/metasfresh-admin-${urlEncodedMavenProps['metasfresh-admin.version']}.jar"
				MF_ARTIFACT_URLS['metasfresh-dist'] = "${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-dist/${misc.urlEncode(MF_VERSION)}/metasfresh-dist-dist-${misc.urlEncode(MF_VERSION)}-dist.tar.gz"
				MF_ARTIFACT_URLS['metasfresh-dist-sql-only'] = "${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-dist/${misc.urlEncode(MF_VERSION)}/metasfresh-dist-dist-${misc.urlEncode(MF_VERSION)}-sql-only.tar.gz"
				MF_ARTIFACT_URLS['metasfresh-material-dispo'] = "${mvnConf.resolveRepoURL}/de/metas/material/metasfresh-material-dispo-service/${urlEncodedMavenProps['metasfresh.version']}/metasfresh-material-dispo-service-${urlEncodedMavenProps['metasfresh.version']}.jar"
				MF_ARTIFACT_URLS['metasfresh-procurement-webui'] = "${mvnConf.resolveRepoURL}/de/metas/procurement/de.metas.procurement.webui/${urlEncodedMavenProps['metasfresh-procurement-webui.version']}/de.metas.procurement.webui-${urlEncodedMavenProps['metasfresh-procurement-webui.version']}.jar"
				MF_ARTIFACT_URLS['metasfresh-webui'] = "${mvnConf.resolveRepoURL}/de/metas/ui/web/metasfresh-webui-api/${urlEncodedMavenProps['metasfresh-webui-api.version']}/metasfresh-webui-api-${urlEncodedMavenProps['metasfresh-webui-api.version']}.jar"
				MF_ARTIFACT_URLS['metasfresh-webui-frontend'] = "${mvnConf.resolveRepoURL}/de/metas/ui/web/metasfresh-webui-frontend/${urlEncodedMavenProps['metasfresh-webui-frontend.version']}/metasfresh-webui-frontend-${urlEncodedMavenProps['metasfresh-webui-frontend.version']}.tar.gz"

				// Note: for the rollout-job's URL with the 'parambuild' to work on this pipelined jenkins, we need the https://wiki.jenkins-ci.org/display/JENKINS/Build+With+Parameters+Plugin, and *not* version 1.3, but later.
				// See
				//  * https://github.com/jenkinsci/build-with-parameters-plugin/pull/10
				//  * https://jenkins.ci.cloudbees.com/job/plugins/job/build-with-parameters-plugin/15/org.jenkins-ci.plugins$build-with-parameters/

				String releaseLinkWithText = "	<li>..and ${misc.createReleaseLinkWithText(MF_RELEASE_VERSION, MF_VERSION, MF_ARTIFACT_URLS, MF_DOCKER_IMAGES)}</li>";
				if(MF_UPSTREAM_BRANCH == 'release')
				{
					releaseLinkWithText = """	${releaseLinkWithText}
	<li>..aaand ${misc.createWeeklyReleaseLinkWithText(MF_RELEASE_VERSION, MF_VERSION, MF_ARTIFACT_URLS, MF_DOCKER_IMAGES)}</li>"""
				} 

				currentBuild.description="""
<h3>Version infos</h3>
<ul>
  <li>metasfresh-dist: version <b>${MF_VERSION}</b></li>
  <li>metasfresh-webui-API: version <b>${mavenProps['metasfresh-webui-api.version']}</b></li>
  <li>metasfresh-webui-frontend: version <b>${mavenProps['metasfresh-webui-frontend.version']}</b></li>
  <li>metasfresh-procurement-webui: version <b>${mavenProps['metasfresh-procurement-webui.version']}</b></li>
  <li>metasfresh-admin webui: version <b>${mavenProps['metasfresh-admin.version']}</b></li>
  <li>metasfresh base: version <b>${mavenProps['metasfresh.version']}</b></li>
  <ul>
		<li>metasfresh-material-dispo (always the same as metasfresh base): version <b>${mavenProps['metasfresh.version']}</b></li>
  </ul>
</ul>
<p>
<h3>Deployable maven artifacts</h3>
<ul>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-dist']}\">dist-tar.gz</a></li>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-dist-sql-only']}\">sql-only-tar.gz</a></li>
	<li><a href=\"${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-swingui/${misc.urlEncode(MF_VERSION)}/metasfresh-dist-swingui-${misc.urlEncode(MF_VERSION)}-client.zip\">client.zip</a></li>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-webui']}\">metasfresh-webui-api.jar</a></li>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-webui-frontend']}\">metasfresh-webui-frontend.tar.gz</a></li>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-procurement-webui']}\">metasfresh-procurement-webui.jar</a></li>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-material-dispo']}\">metasfresh-material-dispo.jar</a></li>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-admin']}\">metasfresh-admin.jar</a></li>
</ul>
Note: all the separately listed artifacts are also included in the dist-tar.gz
<p>
<h3>Deploy</h3>
<ul>
	<li><a href=\"https://jenkins.metasfresh.com/job/ops/job/deploy_metasfresh/parambuild/?MF_ROLLOUT_FILE_URL=${MF_ARTIFACT_URLS['metasfresh-dist']}&MF_UPSTREAM_BUILD_URL=${BUILD_URL}\"><b>This link</b></a> lets you jump to a rollout job that will deploy (roll out) the <b>tar.gz to a host of your choice</b>.</li>
	${releaseLinkWithText}
	<li>Oh, and <a href=\"https://jenkins.metasfresh.com/job/ops/job/run_e2e_tests/parambuild/?MF_DOCKER_IMAGE_FULL_NAME=${MF_DOCKER_IMAGES['metasfresh-e2e']}&MF_UPSTREAM_BUILD_URL=${BUILD_URL}&MF_DOCKER_REGISTRY=&MF_DOCKER_IMAGE=\"><b>this link</b></a> lets you jump to a job that will perform an <b>e2e-test</b> using the upstream metasfresh-e2e tests.</li>
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
		} // withMaven
		} // withEnv
	} // configFileProvider

	stage('Build&Push docker images')
	{
		// app
		final DockerConf appDockerConf = new DockerConf(
						'metasfresh-dist-app', // artifactName
						MF_UPSTREAM_BRANCH, // branchName
						MF_VERSION, // versionSuffix
						'dist/target/docker/app') // workDir
		final String publishedDistAppImageName = dockerBuildAndPush(appDockerConf)

		// report
		final String reportDockerBaseImageRepo = 'metasfresh-report-dev'
		final String reportDockerBaseImageTag = misc.mkDockerTag("${params.MF_METASFRESH_REPORT_DOCKER_BASE_IMAGE_VERSION}")
		final String reportAdditionalBuildArgs = "--build-arg BASE_IMAGE_REPO=${reportDockerBaseImageRepo} --build-arg BASE_IMAGE_VERSION=${reportDockerBaseImageTag}"

		final DockerConf reportDockerConf = appDockerConf
						.withArtifactName('metasfresh-dist-report')
						.withWorkDir('dist/target/docker/report')
						.withAdditionalBuildArgs(reportAdditionalBuildArgs)
		final String publishedReportDockerImageName = dockerBuildAndPush(reportDockerConf)				

		// postgres DB init container
		final DockerConf dbInitDockerConf = appDockerConf
						.withArtifactName('metasfresh-db-init-pg-9-5')
						.withWorkDir('dist/target/docker/db-init')
		dbInitDockerImageName = dockerBuildAndPush(dbInitDockerConf)

		currentBuild.description= """${currentBuild.description}<p/>
			<h3>Docker</h3>
			This build created the following deployable docker images 
			<ul>
			<li><code>${publishedReportDockerImageName}</code> with base-image <code>metasfresh/${reportDockerBaseImageRepo}:${reportDockerBaseImageTag}</code></li>
			<li><code>${publishedDistAppImageName}</code></li>
			<li><code>${dbInitDockerImageName}</code></li>
			</ul>
			"""

		// clean up the workspace after (successfull) builds
		cleanWs cleanWhenAborted: false, cleanWhenFailure: false
	}

	stage('Test SQL-Migration (docker)')
	{
		if(params.MF_SQL_SEED_DUMP_URL)
		{
			// run the pg-init docker image to check that the migration scripts work; make sure to clean up afterwards
			sh "docker run --rm -e \"URL_SEED_DUMP=${params.MF_SQL_SEED_DUMP_URL}\" -e \"URL_MIGRATION_SCRIPTS_PACKAGE=${MF_ARTIFACT_URLS['metasfresh-dist-sql-only']}\" ${dbInitDockerImageName}"
			sh "docker rmi ${dbInitDockerImageName}"
		}
		else
		{
			echo "We skip applying the migration scripts because params.MF_SQL_SEED_DUMP_URL was not set"
		}
	}
} // node
} // timestamps
