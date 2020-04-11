#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.MvnConf
import de.metas.jenkins.Misc


def build(final MvnConf mvnConf, final Map scmVars, final bolean forceBuild=false)
{
	final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.5'
	
    // stage('Build procurement-webui')  // too many stages clutter the build info
    //{
		currentBuild.description= """${currentBuild.description}<p/>
			<h3>procurement-webui</h3>
		"""
		def status = sh(returnStatus: true, script: "git diff --name-only ${scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT} ${scmVars.GIT_COMMIT} . | grep .") // see if anything at all changed in this folder
		echo "status of git dif command=${status}"
		if(scmVars.GIT_COMMIT && scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT && status != 0 && !forceBuild)
		{
			currentBuild.description="""${currentBuild.description}<p/>
			No changes happened in procurement-webu.
			"""
			echo "no changes happened in procurement-webu; skip building procurement-webu";
			return;
		}
		
		// update the parent pom version
		mvnUpdateParentPomVersion mvnConf

		final String mavenUpdatePropertyParam='-Dproperty=metasfresh.version'
		
		// update the metasfresh.version property. either to the latest version or to the given params.MF_UPSTREAM_VERSION.
		sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${mavenUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"

		// set the artifact version of everything below the webui's pom.xml
		sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"

		// do the actual building and deployment
		// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
		sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"

		currentBuild.description="""${currentBuild.description}<p/>
		artifacts (if not yet cleaned up)
			<ul>
<li><a href=\"https://repo.metasfresh.com/content/repositories/${mvnConf.mvnRepoName}/de/metas/procurement/de.metas.procurement.webui/${MF_VERSION}/de.metas.procurement.webui-${MF_VERSION}.jar\">de.metas.procurement.webui-${MF_VERSION}.jar</a></li>
</ul>""";
    //} // stage
} 

return this

