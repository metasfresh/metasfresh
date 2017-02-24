#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md


//
// setup: we'll need the following variables in different stages, that's we we create them here
//
final MF_UPSTREAM_BRANCH;
if(params.MF_UPSTREAM_BRANCH)
{
	echo "Setting MF_UPSTREAM_BRANCH from params.MF_UPSTREAM_BRANCH=${params.MF_UPSTREAM_BRANCH}"
	MF_UPSTREAM_BRANCH=params.MF_UPSTREAM_BRANCH
}
else
{
	echo "Setting MF_UPSTREAM_BRANCH from env.BRANCH_NAME=${env.BRANCH_NAME}"
	MF_UPSTREAM_BRANCH=env.BRANCH_NAME
}

// always offer deployment, because there might be different tasks/branches to roll out
final skipDeploymentParamDefaultValue = false;

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
			description: 'Build number of the upstream job that called us, if any.', 
			name: 'MF_UPSTREAM_BUILDNO'),
		string(defaultValue: '', 
			description: 'Version of the metasfresh "main" code we shall use when resolving dependencies. Leave empty and this build will use the latest.', 
			name: 'MF_METASFRESH_VERSION'),
			string(defaultValue: '', 
			description: 'Version of the metasfresh procurement webui code we shall se when resolving dependencies. Leave empty and this build will use the latest.', 
			name: 'MF_METASFRESH_PROCUREMENT_WEBUI_VERSION'),
		string(defaultValue: '', 
			description: 'Version of the metasfresh-webui(-API) code we shall use when resolving dependencies. Leave empty and this build will use the latest.', 
			name: 'MF_METASFRESH_WEBUI_API_VERSION'),
		string(defaultValue: '', 
			description: 'Version of the metasfresh-webui-frontend code we shall use when resolving dependencies. Leave empty and this build will use the latest.', 
			name: 'MF_METASFRESH_WEBUI_FRONTEND_VERSION'),
		booleanParam(defaultValue: skipDeploymentParamDefaultValue, description: '''If this is true, then there will be a deployment step at the end of this pipeline.
Task branch builds are usually not deployed, so the pipeline can finish without waiting.''', 
			name: 'MF_SKIP_DEPLOYMENT')
	]), 
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')) // keep the last 20 builds
	// , disableConcurrentBuilds() // concurrent builds are ok now. we still work with "-SNAPSHOTS" bit there is a unique MF_UPSTREAM_BUILDNO in each snapshot artifact's version
])

if(params.MF_UPSTREAM_BUILDNO)
{
	echo "Setting MF_UPSTREAM_BUILDNO from params.MF_UPSTREAM_BUILDNO=${params.MF_UPSTREAM_BUILDNO}"
	MF_UPSTREAM_BUILDNO=params.MF_UPSTREAM_BUILDNO
}
else
{
	echo "Setting MF_UPSTREAM_BUILDNO from env.BUILD_NUMBER=${env.BUILD_NUMBER}"
	MF_UPSTREAM_BUILDNO=env.BUILD_NUMBER
}

// set the version prefix, 1 for "master", 2 for "not-master" a.k.a. feature
final BUILD_VERSION_PREFIX = MF_UPSTREAM_BRANCH.equals('master') ? "1" : "2"
echo "Setting BUILD_VERSION_PREFIX=$BUILD_VERSION_PREFIX"

// the artifacts we build in this pipeline will have this version
// never incorporate params.MF_UPSTREAM_BUILDNO into the version anymore. Always go with the build number.
final BUILD_VERSION=BUILD_VERSION_PREFIX + "." + env.BUILD_NUMBER;
echo "Setting BUILD_VERSION=$BUILD_VERSION"

// metasfresh-task-repo is a constant (does not depent or the task/branch name) so that maven can find the credentials in our provided settings.xml file
final MF_MAVEN_REPO_ID = "metasfresh-task-repo";
echo "Setting MF_MAVEN_REPO_ID=$MF_MAVEN_REPO_ID";

// name of the task/branch specific maven nexus-repository that we will create if it doesn't exist and and resolve from
// make sure the maven repo name is OK, to avoid an error message saying
// "Only letters, digits, underscores(_), hyphens(-), and dots(.) are allowed in Repository ID"
final MF_MAVEN_REPO_NAME = "mvn-${MF_UPSTREAM_BRANCH}".replaceAll('[^a-zA-Z0-9_-]', '_'); 
echo "Setting MF_MAVEN_REPO_NAME=$MF_MAVEN_REPO_NAME";

final MF_MAVEN_REPO_URL = "https://repo.metasfresh.com/content/repositories/${MF_MAVEN_REPO_NAME}";
echo "Setting MF_MAVEN_REPO_URL=$MF_MAVEN_REPO_URL";

// IMPORTANT: the task-repo-url which we set in MF_MAVEN_TASK_RESOLVE_PARAMS is used within the settings.xml that our jenkins provides to the build. That's why we need it in the mvn parameters
final MF_MAVEN_TASK_RESOLVE_PARAMS="-Dtask-repo-id=${MF_MAVEN_REPO_ID} -Dtask-repo-name=\"${MF_MAVEN_REPO_NAME}\" -Dtask-repo-url=\"${MF_MAVEN_REPO_URL}\"";
echo "Setting MF_MAVEN_TASK_RESOLVE_PARAMS=$MF_MAVEN_TASK_RESOLVE_PARAMS";

// the repository to which we are going to deploy
final MF_MAVEN_DEPLOY_REPO_URL = "https://repo.metasfresh.com/content/repositories/${MF_MAVEN_REPO_NAME}-releases";
echo "Setting MF_MAVEN_DEPLOY_REPO_URL=$MF_MAVEN_DEPLOY_REPO_URL";

// provide these cmdline params to all maven invocations that do a deploy
// deploy-repo-id=metasfresh-task-repo so that maven can find the credentials in our provided settings.xml file
// deployAtEnd=true so that
final MF_MAVEN_TASK_DEPLOY_PARAMS = "-DaltDeploymentRepository=\"${MF_MAVEN_REPO_ID}::default::${MF_MAVEN_DEPLOY_REPO_URL}\"";
echo "Setting MF_MAVEN_TASK_DEPLOY_PARAMS=$MF_MAVEN_TASK_DEPLOY_PARAMS";

// gh #968 make create a map equal to the one we create in metasfresh/Jenkinsfile. The way we used it further down is also similar
final MF_ARTIFACT_VERSIONS = [:];
MF_ARTIFACT_VERSIONS['metasfresh'] = params.MF_METASFRESH_VERSION ?: "LATEST";
MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui'] = params.MF_METASFRESH_PROCUREMENT_WEBUI_VERSION ?: "LATEST";
MF_ARTIFACT_VERSIONS['metasfresh-webui'] = params.MF_METASFRESH_WEBUI_API_VERSION ?: "LATEST";
MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend'] = params.MF_METASFRESH_WEBUI_FRONTEND_VERSION ?: "LATEST";

// these two are shown in jenkins, for each build
currentBuild.displayName="${MF_UPSTREAM_BRANCH} - build #${currentBuild.number} - artifact-version ${BUILD_VERSION}";

timestamps 
{
// to build the client-exe on linux, we need 32bit libs!
node('agent && linux && libc6-i386')
{
	configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
	{
		// as of now, /de.metas.endcustomer.mf15.base/src/main/resources/org/adempiere/version.properties contains "env.BUILD_VERSION", "env.MF_UPSTREAM_BRANCH" and others,
		// which needs to be replaced when version.properties is dealt with by the ressources plugin, see https://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html
		withEnv(["BUILD_VERSION=${BUILD_VERSION}", "MF_UPSTREAM_BRANCH=${MF_UPSTREAM_BRANCH}", "CHANGE_URL=${env.CHANGE_URL}", "BUILD_NUMBER=${env.BUILD_NUMBER}"])
		{
		sh "echo \"testing 'witEnv' using shell: BUILD_VERSION=${BUILD_VERSION}\""
		withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository') 
		{
			stage('Set versions and build endcustomer-dist') 
			{
				// checkout our code
				// note that we do not know if the stuff we checked out in the other node is available here, so we somehow need to make sure by checking out (again).
				// see: https://groups.google.com/forum/#!topic/jenkinsci-users/513qLiYlXHc

				checkout scm; // i hope this to do all the magic we need
				sh 'git clean -d --force -x' // clean the workspace
				
				final String metasfreshUpdateParentParam="-DparentVersion=${MF_ARTIFACT_VERSIONS['metasfresh']}";;

				final String metasfreshWebFrontEndUpdatePropertyParam = "-Dproperty=metasfresh-webui-frontend.version -DnewVersion=${MF_ARTIFACT_VERSIONS['metasfresh-webui-frontend']}";
				final String metasfreshWebApiUpdatePropertyParam = "-Dproperty=metasfresh-webui-api.version -DnewVersion=${MF_ARTIFACT_VERSIONS['metasfresh-webui']}";
				final String metasfreshProcurementWebuiUpdatePropertyParam = "-Dproperty=metasfresh-procurement-webui.version -DnewVersion=${MF_ARTIFACT_VERSIONS['metasfresh-procurement-webui']}";
				final String metasfreshUpdatePropertyParam="-Dproperty=metasfresh.version -DnewVersion=${MF_ARTIFACT_VERSIONS['metasfresh']}";
		
				// update the parent pom version
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${metasfreshUpdateParentParam} versions:update-parent"
				
				// update the metasfresh.version property. either to the latest version or to the given params.MF_METASFRESH_VERSION.
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${metasfreshUpdatePropertyParam} versions:update-property"

				// gh #968 also update the metasfresh-webui-frontend.version, metasfresh-webui-api.versions and procurement versions.
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${metasfreshWebFrontEndUpdatePropertyParam} versions:update-property"
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${metasfreshWebApiUpdatePropertyParam} versions:update-property"
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${metasfreshProcurementWebuiUpdatePropertyParam} versions:update-property"

				// set the artifact version of everything below the endcustomer.mf15's parent pom.xml
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode -DnewVersion=${BUILD_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true ${MF_MAVEN_TASK_RESOLVE_PARAMS} versions:set"

				// do the actual building and deployment
				// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
				// about -Dmaven.test.failure.ignore=true: continue if tests fail, because we want a full report.
				sh "mvn --settings $MAVEN_SETTINGS --file pom.xml --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${BUILD_VERSION} ${MF_MAVEN_TASK_RESOLVE_PARAMS} ${MF_MAVEN_TASK_DEPLOY_PARAMS} clean deploy"
			

				// endcustomer.mf15 currently has no tests. Don't try to collect any, or a typical error migh look like this:
				// ERROR: Test reports were found but none of them are new. Did tests run? 
				// For example, /var/lib/jenkins/workspace/metasfresh_FRESH-854-gh569-M6AHOWSSP3FKCR7CHWVIRO5S7G64X4JFSD4EZJZLAT5DONP2ZA7Q/de.metas.acct.base/target/surefire-reports/TEST-de.metas.acct.impl.FactAcctLogBLTest.xml is 2 min 57 sec old
				// junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

				// we now have set the versions of metas-webui etc within the pom.xml. In order to document them, write them into a file.
				// the file's name is app.properties, as configured in metasfresh-parent's pom.xml. Thx to http://stackoverflow.com/a/26589696/1012103
				sh "mvn --settings $MAVEN_SETTINGS --file de.metas.endcustomer.mf15.dist/pom.xml --batch-mode properties:write-project-properties"

				// now load the properties we got from the pom.xml. Thx to http://stackoverflow.com/a/39644024/1012103
				def mavenProps = readProperties  file: 'de.metas.endcustomer.mf15.dist/app.properties'

				final MF_ARTIFACT_URLS = [:];
				MF_ARTIFACT_URLS['metasfresh-dist'] = "https://repo.metasfresh.com/service/local/repositories/${MF_MAVEN_REPO_NAME}/content/de/metas/endcustomer/mf15/de.metas.endcustomer.mf15.dist/${BUILD_VERSION}/de.metas.endcustomer.mf15.dist-${BUILD_VERSION}-dist.tar.gz";
				MF_ARTIFACT_URLS['metasfresh-webui'] = "http://repo.metasfresh.com/service/local/artifact/maven/redirect?r=${MF_MAVEN_REPO_NAME}&g=de.metas.ui.web&a=metasfresh-webui-api&v=${mavenProps['metasfresh-webui-api.version']}";
				MF_ARTIFACT_URLS['metasfresh-webui-frontend'] = "http://repo.metasfresh.com/service/local/artifact/maven/redirect?r=${MF_MAVEN_REPO_NAME}&g=de.metas.ui.web&a=metasfresh-webui-frontend&p=tar.gz&v=${mavenProps['metasfresh-webui-frontend.version']}";
				MF_ARTIFACT_URLS['metasfresh-procurement-webui']= "http://repo.metasfresh.com/service/local/artifact/maven/redirect?r=${MF_MAVEN_REPO_NAME}&g=de.metas.procurement&a=de.metas.procurement.webui&v=${mavenProps['metasfresh-procurement-webui.version']}";


				// Note: for the rollout-job's URL with the 'parambuild' to work on this pipelined jenkins, we need the https://wiki.jenkins-ci.org/display/JENKINS/Build+With+Parameters+Plugin, and *not* version 1.3, but later.
				// See 
				//  * https://github.com/jenkinsci/build-with-parameters-plugin/pull/10
				//  * https://jenkins.ci.cloudbees.com/job/plugins/job/build-with-parameters-plugin/15/org.jenkins-ci.plugins$build-with-parameters/
				currentBuild.description="""
<h3>Version infos</h3>
<ul>
  <li>endcustomer.mf15: version <b>${BUILD_VERSION}</b></li>
  <li>metasfresh-webui-API: version <b>${mavenProps['metasfresh-webui-api.version']}</b></li>
  <li>metasfresh-webui-frontend: version <b>${mavenProps['metasfresh-webui-frontend.version']}</b>
  <li>metasfresh-procurement-webui: version <b>${mavenProps['metasfresh-procurement-webui.version']}</b>
  <li>metasfresh base: version <b>${mavenProps['metasfresh.version']}</b>
</ul>
<p>
<h3>Deployable artifacts</h3>
<ul>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-dist']}\">dist-tar.gz</a></li>
	<li><a href=\"https://repo.metasfresh.com/service/local/repositories/${MF_MAVEN_REPO_NAME}/content/de/metas/endcustomer/mf15/de.metas.endcustomer.mf15.dist/${BUILD_VERSION}/de.metas.endcustomer.mf15.dist-${BUILD_VERSION}-sql-only.tar.gz\">sql-only-tar.gz</a></li>
	<li><a href=\"https://repo.metasfresh.com/service/local/repositories/${MF_MAVEN_REPO_NAME}/content/de/metas/endcustomer/mf15/de.metas.endcustomer.mf15.swingui/${BUILD_VERSION}/de.metas.endcustomer.mf15.swingui-${BUILD_VERSION}-client.zip\">client.zip</a></li>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-webui']}\">metasfresh-webui-api.jar</a></li>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-webui-frontend']}\">metasfresh-webui-frontend.tar.gz</a></li>
	<li><a href=\"${MF_ARTIFACT_URLS['metasfresh-procurement-webui']}\">metasfresh-procurement-webui.jar</a></li>
</ul>
<p>
<h3>Deploy</h3>
<ul>
	<li><a href=\"https://jenkins.metasfresh.com/job/ops/job/deploy_metasfresh/parambuild/?MF_ROLLOUT_FILE_URL=${MF_ARTIFACT_URLS['metasfresh-dist']}\"><b>This link</b></a> lets you jump to a rollout job that will deploy (roll out) the tar.gz to a host of your choice.</li>
</ul>
<p>
<h3>Additional notes</h3>
<ul>
  <li>The artifacts on <a href="https://repo.metasfresh.com">repo.metasfresh.com</a> are cleaned up on a regular schedule to preserve disk space.<br/>
    Therefore the artifacts that are linked to by the URLs above might already have been deleted.</li>
  <li>It is important to note that both the <i>"endcustomer"</i> artifacts (client and backend server) build by this job and the <i>"webui"</i> artifacts that are also linked here are based on the same underlying metasfresh version.
</ul>
""";		
			}
		} // withMaven
		} // withEnv(['"BUILD_VERSION=${BUILD_VERSION}"'])
	} // configFileProvider

	// clean up the workspace, including the local maven repositories that the withMaven steps created
	// don't clean up the work space..we do it when we check out next time
	// step([$class: 'WsCleanup', cleanWhenFailure: true])
} // node

// we need this one for both "Test-SQL" and "Deployment
def downloadForDeployment = { String groupId, String artifactId, String version, String packaging, String classifier, String sshTargetHost, String sshTargetUser ->

	final packagingPart=packaging ? ":${packaging}" : ""
	final classifierPart=classifier ? ":${classifier}" : ""
	final artifact = "${groupId}:${artifactId}:${version}${packagingPart}${classifierPart}"

	// we need configFileProvider because in mvn get -DremoteRepositories=https://repo.metasfresh.com/repository/mvn-public is ignored. 
	// See http://maven.apache.org/plugins/maven-dependency-plugin/get-mojo.html "Caveat: will always check thecentral repository defined in the super pom" 
	configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
	{
		withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository') 
		{
			sh "mvn --settings $MAVEN_SETTINGS org.apache.maven.plugins:maven-dependency-plugin:2.10:get -Dtransitive=false -Dartifact=${artifact} ${MF_MAVEN_TASK_RESOLVE_PARAMS}"
			
			// copy the artifact to a deploy folder.
			sh "mvn --settings $MAVEN_SETTINGS org.apache.maven.plugins:maven-dependency-plugin:2.10:copy -Dartifact=${artifact} -DoutputDirectory=deploy -Dmdep.stripClassifier=false -Dmdep.stripVersion=false ${MF_MAVEN_TASK_RESOLVE_PARAMS}"
		}
	}
	sh "scp ${WORKSPACE}/deploy/${artifactId}-${version}-${classifier}.${packaging} ${sshTargetUser}@${sshTargetHost}:/home/${sshTargetUser}/${artifactId}-${version}-${classifier}.${packaging}"
}

// we need this one for both "Test-SQL" and "Deployment
def invokeRemote = { String sshTargetHost, String sshTargetUser, String directory, String shellScript -> 

// no echo needed: the log already shows what's done via the sh step
//	echo "Going to invoke the following as user ${sshTargetUser} on host ${sshTargetHost} in directory ${directory}:";
//	echo "${shellScript}"
	sh "ssh ${sshTargetUser}@${sshTargetHost} \"cd ${directory} && ${shellScript}\"" 
}


stage('Test SQL-Migration')
{
	if(params.MF_SKIP_SQL_MIGRATION_TEST)
	{
		echo "We skip the deployment step because params.MF_SKIP_SQL_MIGRATION_TEST=${params.MF_SKIP_SQL_MIGRATION_TEST}"
	}
	else
	{
		node('master')
		{
			final distArtifactId='de.metas.endcustomer.mf15.dist';
			final classifier='sql-only';
			final packaging='tar.gz';
			final sshTargetHost='mf15cloudit';
			final sshTargetUser='metasfresh'

			downloadForDeployment('de.metas.endcustomer.mf15', distArtifactId, BUILD_VERSION, packaging, classifier, sshTargetHost, sshTargetUser);

			final fileAndDirName="${distArtifactId}-${BUILD_VERSION}-${classifier}"
			final deployDir="/home/${sshTargetUser}/${fileAndDirName}-${MF_UPSTREAM_BRANCH}"
			
			// Look Ma, I'm currying!!
			final invokeRemoteInHomeDir = invokeRemote.curry(sshTargetHost, sshTargetUser, "/home/${sshTargetUser}");				
			invokeRemoteInHomeDir("mkdir -p ${deployDir} && mv ${fileAndDirName}.${packaging} ${deployDir} && cd ${deployDir} && tar -xf ${fileAndDirName}.${packaging}")

			final invokeRemoteInInstallDir = invokeRemote.curry(sshTargetHost, sshTargetUser, "${deployDir}/dist/install");				
			final VALIDATE_MIGRATION_TEMPLATE_DB='mf15_template';
			final VALIDATE_MIGRATION_TEST_DB="tmp-mf15-${MF_UPSTREAM_BRANCH}-${env.BUILD_NUMBER}-${BUILD_VERSION}"
					.replaceAll('[^a-zA-Z0-9]', '_') // // postgresql is in a way is allergic to '-' and '.' and many other characters in in DB names
					.toLowerCase(); // also, DB names are generally in lowercase

			invokeRemoteInInstallDir("./sql_remote.sh -n ${VALIDATE_MIGRATION_TEMPLATE_DB} ${VALIDATE_MIGRATION_TEST_DB}");
			
			invokeRemoteInHomeDir("rm -r ${deployDir}"); // cleanup
		}
	} // if(params.MF_SKIP_SQL_MIGRATION_TEST)
} // stage

} // timestamps

