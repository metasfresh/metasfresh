#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.MvnConf
import de.metas.jenkins.Misc

def build(final MvnConf mvnConf, final Map scmVars)
{
	final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.7'

	stage('parent-pom') // for display purposes
	{
		nexusCreateRepoIfNotExists mvnConf.mvnDeployRepoBaseURL, mvnConf.mvnRepoName

		// set the artifact version of everything below the webui's pom.xml
		sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${env.MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas.*:*\" ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"

		// do the actual building and deployment
		sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"

		// aaaand now, set it back to version 10.0.0 because otherwise the child-poms can't find their parent-poms when we run "versions:update-parent" on them, to get them to ${env.MF_VERSION}
		sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=10.0.0 -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas.*:*\" ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"
	}
}

return this
