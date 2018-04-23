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
		booleanParam(defaultValue: true, description: '''Set to true if this build shall trigger downstream builds and wait for them to succeed orr fail''',
			name: 'MF_TRIGGER_DOWNSTREAM_BUILDS')
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '100')) // keep the last 100 builds
])

timestamps
{
// https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
final String MF_VERSION=retrieveArtifactVersion(env.BRANCH_NAME, env.BUILD_NUMBER)
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
			"mvn-${env.BRANCH_NAME}", // mvnRepoName
			'https://repo.metasfresh.com' // mvnRepoBaseURL (resolve and deploy)
		)
		echo "mvnConf=${mvnConf}"

		nexusCreateRepoIfNotExists mvnConf.mvnDeployRepoBaseURL, mvnConf.mvnRepoName

        withMaven(jdk: 'java-8', maven: 'maven-3.5.2', mavenLocalRepo: '.repository')
        {
            stage('Set versions and build')
            {
							// set the artifact version of everything below the webui's pom.xml
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas.*:*\" ${mvnConf.resolveParams} versions:set"

							// do the actual building and deployment
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"
            }
		} // withMaven
	} // configFileProvider
} // node

stage('Invoke downstream jobs')
{
	if(params.MF_TRIGGER_DOWNSTREAM_BUILDS)
	{
		def misc = new de.metas.jenkins.Misc();
		echo "Created instance; misc=${misc}";

		parallel (
			metasfresh_admin: {
				misc.invokeDownStreamJobs(
				 	env.BUILD_NUMBER, // MF_UPSTREAM_BUILDNO,
				 	env.BRANCH_NAME, // MF_UPSTREAM_BRANCH
				 	MF_VERSION, // parentPomVersion; this build *is* the parent version, so we hand down our own version
				 	false, // skipToDist=false; when we invoke the metasfresh build, we want it to do a full build
				 	true, // triggerDownStreamBuilds=true; we want "everything" beeing build
				 	true, // wait=true; if a downstream job fails with this parent pom then we want to know about it
					'metasfresh-admin')
			},
			metasfresh: {
				misc.invokeDownStreamJobs(
				 	env.BUILD_NUMBER, // MF_UPSTREAM_BUILDNO,
				 	env.BRANCH_NAME, // MF_UPSTREAM_BRANCH
				 	MF_VERSION, // parentPomVersion; this build *is* the parent version, so we hand down our own version
				 	false, // skipToDist=false; when we invoke the metasfresh build, we want it to do a full build
				 	true, // triggerDownStreamBuilds=true; we want "everything" beeing build
				 	true, // wait=true; if a downstream job fails with this parent pom then we want to know about it
					'metasfresh')
			},
			metasfresh_webui_frontend: {
				misc.invokeDownStreamJobs(
				 	env.BUILD_NUMBER, // MF_UPSTREAM_BUILDNO,
				 	env.BRANCH_NAME, // MF_UPSTREAM_BRANCH
				 	MF_VERSION, // parentPomVersion; this build *is* the parent version, so we hand down our own version
				 	false, // skipToDist=false; when we invoke the metasfresh build, we want it to do a full build
				 	false, // triggerDownStreamBuilds=false; we already started 'metasfresh' with triggerDownStreamBuilds=true
				 	true, // wait=true; if a downstream job fails with this parent pom then we want to know about it
					'metasfresh-webui-frontend')
			}
		)
	}
	else
	{
		echo "params.MF_TRIGGER_DOWNSTREAM_BUILDS=${params.MF_TRIGGER_DOWNSTREAM_BUILDS}, so we do not trigger any downstream builds"
	}
}

} // timestamps
