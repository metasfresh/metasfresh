#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.MvnConf

// thx to http://stackoverflow.com/a/36949007/1012103 with respect to the paramters
properties([
	parameters([
		booleanParam(defaultValue: true, description: '''Set to true if this build shall trigger downstream builds.''',
			name: 'MF_TRIGGER_DOWNSTREAM_BUILDS')
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '100')) // keep the last 100 builds
	// , disableConcurrentBuilds() // concurrent builds are ok now. we still work with "-SNAPSHOTS" bit there is a unique MF_UPSTREAM_BUILDNO in each snapshot artifact's version
])

//
// setup: we'll need the following variables in different stages, that's we we create them here
//
echo "Setting MF_UPSTREAM_BRANCH from env.BRANCH_NAME=${env.BRANCH_NAME}";
final MF_UPSTREAM_BRANCH=env.BRANCH_NAME;

echo "Setting MF_UPSTREAM_BUILDNO from env.BUILD_NUMBER=${env.BUILD_NUMBER}"
MF_UPSTREAM_BUILDNO=env.BUILD_NUMBER

// set the version prefix, 1 for "master", 2 for "not-master" a.k.a. feature
final BUILD_VERSION_PREFIX = MF_UPSTREAM_BRANCH.equals('master') ? "1" : "2"
echo "Setting BUILD_VERSION_PREFIX=$BUILD_VERSION_PREFIX"

// the artifacts we build in this pipeline will have this version
// never incorporate params.MF_UPSTREAM_BUILDNO into the version anymore. Always go with the build number.
final BUILD_VERSION=BUILD_VERSION_PREFIX + "." + env.BUILD_NUMBER;
echo "Setting BUILD_VERSION=$BUILD_VERSION"

currentBuild.displayName="${MF_UPSTREAM_BRANCH} - build #${currentBuild.number} - artifact-version ${BUILD_VERSION}";
// note: going to set currentBuild.description after we deployed

timestamps
{
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
					'https://repo.metasfresh.com', // mvnRepoBaseURL
					"mvn-${MF_UPSTREAM_BRANCH}" // mvnRepoName
				)
				echo "mvnConf=${mvnConf}"

				nexusCreateRepoIfNotExists mvnConf.mvnRepoBaseURL, mvnConf.mvnRepoName

        withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository')
        {
            stage('Set versions and build')
            {
							// set the artifact version of everything below the webui's pom.xml
							sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${BUILD_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas.*:*\" ${mvnConf.resolveParams} versions:set"

							// do the actual building and deployment
							// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
							sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"

							// junit '**/target/surefire-reports/*.xml' // in metasfresh-parent we don't have tests
            }
		}
	}
} // node

stage('Invoke downstream job')
{
	if(params.MF_TRIGGER_DOWNSTREAM_BUILDS)
	{
		def invokeAndWait = invokeDownStreamJobs.curry(
			MF_UPSTREAM_BUILDNO,
			MF_UPSTREAM_BRANCH,
			BUILD_VERSION, // parentPomVersion; this build *is* the parent version
			false, // skipToDist=false; when we invoke the metasfresh build, we want it to do a full build
			true, // triggerDownStreamBuilds=true; we want "everything" beeing build
			true // wait=true; if a downstream job fails with this parent pom then we want to know about it
		);

		parallel (
			metasfresh_admin: {
				invokeAndWait('metasfresh-admin');
			},
			metasfresh: {
				invokeAndWait('metasfresh');
			}
		);
	}
	else
	{
		echo "params.MF_TRIGGER_DOWNSTREAM_BUILDS=${params.MF_TRIGGER_DOWNSTREAM_BUILDS}, so we do not trigger any downstream builds"
	}
}

} // timestamps
