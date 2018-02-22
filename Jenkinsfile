#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.MvnConf
import de.metas.jenkins.Misc

// thx to http://stackoverflow.com/a/36949007/1012103 with respect to the paramters
properties([
	parameters([
		string(defaultValue: '',
			description: '''If this job is invoked via an updstream build job, than that upstream job can provide either its branch or the respective <code>MF_UPSTREAM_BRANCH</code> that was passed to it.<br>
This build will then attempt to use maven dependencies from that branch, and it will set its own version name to reflect the given value.
<p>
So if this is a "master" build, but it was invoked by a "feature-branch" build then this build will try to get the feature-branch\'s build artifacts annd will set its
<code>currentBuild.displayname</code> and <code>currentBuild.description</code> to make it obvious that the build contains code from the feature branch.''',
			name: 'MF_UPSTREAM_BRANCH'),

		booleanParam(defaultValue: true, description: '''Set to true if this build shall trigger "endcustomer" builds.<br>
Set to false if this build is called from elsewhere and the orchestrating also takes place elsewhere''',
			name: 'MF_TRIGGER_DOWNSTREAM_BUILDS'),

		string(defaultValue: '',
			description: 'Will be incorporated into the artifact version and forwarded to jobs triggered by this job. Leave empty to go with <code>env.BUILD_NUMBER</code>',
			name: 'MF_BUILD_ID')
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')) // keep the last 20 builds
	// , disableConcurrentBuilds() // concurrent builds are ok now. we still work with "-SNAPSHOTS" bit there is a unique MF_BUILD_ID in each snapshot artifact's version
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
	stage('Preparation') // for display purposes
	{
		// checkout our code
		checkout scm; // i hope this to do all the magic we need
		sh 'git clean -d --force -x' // clean the workspace
	}

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

			nexusCreateRepoIfNotExists mvnConf.mvnDeployRepoBaseURL, mvnConf.mvnRepoName

    	// env.MF_RELEASE_VERSION is used by spring-boot's build-info goal

      	withMaven(jdk: 'java-8', maven: 'maven-3.5.0', mavenLocalRepo: '.repository')
        {
            stage('Set versions and build metasfresh-admin')
            {
							// update the parent pom version
      				mvnUpdateParentPomVersion mvnConf

              // set the artifact version of everything below the pom.xml
							sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} versions:set"
							sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} versions:use-latest-versions"

							sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"

							sh "cp target/metasfresh-admin-${MF_VERSION}.jar src/main/docker/metasfresh-admin.jar" // copy the file so it can be handled by the docker build

							createAndPublishDockerImage_nexus(
								'metasfresh-admin', // dockerRepositoryName
								'',  // dockerModuleDir
								MF_UPSTREAM_BRANCH, // dockerBranchName
								MF_VERSION // dockerVersionSuffix
							)
							/*
							docker.withRegistry('https://index.docker.io/v1/', 'dockerhub_metasfresh')
							{
								def app = docker.build 'metasfresh/metasfresh-admin', 'src/main/docker';

								def misc = new de.metas.jenkins.Misc();
								app.push misc.mkDockerTag("${MF_UPSTREAM_BRANCH}-latest");
								app.push misc.mkDockerTag("${MF_UPSTREAM_BRANCH}-${MF_VERSION}");

								if(MF_UPSTREAM_BRANCH=='release')
								{
									echo 'MF_UPSTREAM_BRANCH=release, so we also push this with the "latest" tag'
									app.push misc.mkDockerTag('latest');
								}
							}
							*/
            } // stage
		   } // withMaven
		}
	// clean up the work space after (successfull) builds, including the local maven repositories that the withMaven steps created
	cleanWs cleanWhenAborted: false, cleanWhenFailure: false
} // node
} // timestamps
