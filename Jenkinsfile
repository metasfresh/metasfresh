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

		string(defaultValue: '',
			description: 'Build number of the upstream job that called us, if any.',
			name: 'MF_UPSTREAM_BUILDNO'),

		string(defaultValue: '',
			description: 'Version of the metasfresh "main" code we shall use when resolving dependencies. Leave empty and this build will use the latest.',
			name: 'MF_METASFRESH_VERSION'),

		booleanParam(defaultValue: true, description: '''Set to true if this build shall trigger "endcustomer" builds.<br>
Set to false if this build is called from elsewhere and the orchestrating also takes place elsewhere''',
			name: 'MF_TRIGGER_DOWNSTREAM_BUILDS')
	]),
	pipelineTriggers([]),
	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10')) // keep the last 10 builds
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

        withMaven(jdk: 'java-8', maven: 'maven-3.5.0', mavenLocalRepo: '.repository')
        {
        stage('Set versions and build metasfresh-procurement-webui')
        {

				// update the parent pom version
				mvnUpdateParentPomVersion mvnConf

				final String mavenUpdatePropertyParam;
				if(params.MF_METASFRESH_VERSION)
				{
					final inSquaresIfNeeded = { String version -> return version == "LATEST" ? version: "[${version}]"; }

					// update the property, use the metasfresh version that we were given by the upstream job
					// the square brackets are required if we have a conrete version (i.e. not "LATEST"); see https://github.com/mojohaus/versions-maven-plugin/issues/141 for details
					mavenUpdatePropertyParam="-Dproperty=metasfresh.version -DnewVersion=${inSquaresIfNeeded(params.MF_METASFRESH_VERSION)}";
				}
				else
				{
					// still update the property, but use the latest version
					mavenUpdatePropertyParam='-Dproperty=metasfresh.version';
				}

				// update the metasfresh.version property. either to the latest version or to the given params.MF_METASFRESH_VERSION.
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${mavenUpdatePropertyParam} versions:update-property"

				// set the artifact version of everything below the webui's pom.xml
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas.procurement*:*\" ${mvnConf.resolveParams} versions:set"

				// do the actual building and deployment
				// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
				sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"

				currentBuild.description="""artifacts (if not yet cleaned up)
				<ul>
<li><a href=\"https://repo.metasfresh.com/content/repositories/${mvnConf.mvnRepoName}/de/metas/procurement/de.metas.procurement.webui/${MF_VERSION}/de.metas.procurement.webui-${MF_VERSION}.jar\">metasfresh-webui-api-${MF_VERSION}.jar</a></li>
</ul>""";

				junit '**/target/surefire-reports/*.xml'

				jacoco exclusionPattern: '**/src/main/java-gen'

				// gh #968:
				// set env variables which will be available to a possible upstream job that might have called us
				// all those env variables can be gotten from <buildResultInstance>.getBuildVariables()
				env.MF_VERSION="${MF_VERSION}";
      } // stage
		} // withMaven
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
	       string(name: 'MF_UPSTREAM_BUILDNO', value: MF_UPSTREAM_BUILDNO),
	       string(name: 'MF_UPSTREAM_VERSION', value: MF_VERSION),
	       string(name: 'MF_UPSTREAM_JOBNAME', value: 'metasfresh-webui'),
	       booleanParam(name: 'MF_TRIGGER_DOWNSTREAM_BUILDS', value: false), // the job shall just run but not trigger further builds because we are doing all the orchestration
	       booleanParam(name: 'MF_SKIP_TO_DIST', value: true) // this param is only recognised by metasfresh
	     ], wait: false
	}
}
else
{
	echo "params.MF_TRIGGER_DOWNSTREAM_BUILDS=${params.MF_TRIGGER_DOWNSTREAM_BUILDS}, so we do not trigger any downstream builds"
}

} // timestamps
