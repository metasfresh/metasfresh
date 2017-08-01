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
			description: '''If this job is invoked via an updstream build job, than that job can provide either its branch or the respective <code>MF_UPSTREAM_BRANCH</code> that was passed to it.<br>
This build will then attempt to use maven dependencies from that branch, and it will sets its own name to reflect the given value.
<p>
So if this is a "master" build, but it was invoked by a "feature-branch" build then this build will try to get the feature-branch\'s build artifacts annd will set its
<code>currentBuild.displayname</code> and <code>currentBuild.description</code> to make it obvious that the build contains code from the feature branch.''',
			name: 'MF_UPSTREAM_BRANCH'),

		booleanParam(defaultValue: true, description: '''Set to true if this build shall trigger "endcustomer" builds.<br>
Set to false if this build is called from elsewhere and the orchestrating also takes place elsewhere''',
			name: 'MF_TRIGGER_DOWNSTREAM_BUILDS'),

		string(defaultValue: '',
			description: 'Version of the metasfresh-parent parent pom.xml we shall use when building. Leave empty and this build will use the latest.',
			name: 'MF_PARENT_VERSION'),

		string(defaultValue: '',
			description: 'Will be incorporated into the artifact version and forwarded to jobs triggered by this job. Leave empty to go with <code>env.BUILD_NUMBER</code>',
			name: 'MF_BUILD_ID')
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')) // keep the last 20 builds
	// , disableConcurrentBuilds() // concurrent builds are ok now. we still work with "-SNAPSHOTS" bit there is a unique MF_BUILD_ID in each snapshot artifact's version
])

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
if(params.MF_BUILD_ID)
{
	echo "Setting MF_BUILD_ID from params.MF_BUILD_ID=${params.MF_BUILD_ID}"
	MF_BUILD_ID=params.MF_BUILD_ID
}
else
{
	echo "Setting MF_BUILD_ID from env.BUILD_NUMBER=${env.BUILD_NUMBER}"
	MF_BUILD_ID=env.BUILD_NUMBER
}

// set the version prefix, 1 for "master", 2 for "not-master" a.k.a. feature
final BUILD_VERSION_PREFIX = MF_UPSTREAM_BRANCH.equals('master') ? "1" : "2"
echo "Setting BUILD_VERSION_PREFIX=$BUILD_VERSION_PREFIX"

// the artifacts we build in this pipeline will have this version
// never incorporate params.MF_BUILD_ID into the version anymore. Always go with the build number.
final BUILD_VERSION=BUILD_VERSION_PREFIX + "." + env.BUILD_NUMBER;
echo "Setting BUILD_VERSION=$BUILD_VERSION"

currentBuild.displayName="#" + currentBuild.number + "-" + MF_UPSTREAM_BRANCH + "-" + MF_BUILD_ID

timestamps
{
node('agent && linux && dejenkinsnode001') // shall only run on a jenkins agent with linux
{
	stage('Preparation') // for display purposes
	{
		// checkout our code
		// note that we do not know if the stuff we checked out in the other node is available here, so we somehow need to make sure by checking out (again).
		// see: https://groups.google.com/forum/#!topic/jenkinsci-users/513qLiYlXHc
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
            stage('Set versions and build metasfresh-admin')
            {
							// update the parent pom version
      				mvnUpdateParentPomVersion mvnConf, params.MF_PARENT_VERSION

              // set the artifact version of everything below the pom.xml
							sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${BUILD_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} versions:set"
							sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} versions:use-latest-versions"

							sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"

              // create and upload a docker image
							sh "cp target/metasfresh-admin-${BUILD_VERSION}.jar src/main/docker/metasfresh-admin.jar" // copy the file so it can be handled by the docker build
							docker.withRegistry('https://index.docker.io/v1/', 'dockerhub_metasfresh')
							{
								def app = docker.build 'metasfresh/metasfresh-admin', 'src/main/docker';

								def misc = new de.metas.jenkins.Misc();
								app.push misc.mkDockerTag("${MF_UPSTREAM_BRANCH}-latest");
								app.push misc.mkDockerTag("${MF_UPSTREAM_BRANCH}-${BUILD_VERSION}");

								if(MF_UPSTREAM_BRANCH=='release')
								{
									echo 'MF_UPSTREAM_BRANCH=release, so we also push this with the "latest" tag'
									app.push misc.mkDockerTag('latest');
								}
							}
            } // stage
		}
	}
	// clean up the work space, including the local maven repositories that the withMaven steps created
	step([$class: 'WsCleanup', cleanWhenFailure: false])
} // node
} // timestamps
