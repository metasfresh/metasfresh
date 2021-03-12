#!/usr/bin/env groovy @Library('misc') @Library('misc')
import de.metas.jenkins.MvnConf
@Library('misc')
import de.metas.jenkins.MvnConf

// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here

def build(final MvnConf mvnConf, final Map scmVars, final boolean forceBuild = false) {
    final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.7'

    // stage('Build procurement-webui')  // too many stages clutter the build info
    //{
    currentBuild.description = """${currentBuild.description}<p/>
			<h3>procurement-webui</h3>
		"""

    def anyFileChanged
    try {
        def vgitout = sh(returnStdout: true, script: "git diff --name-only ${scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT} ${scmVars.GIT_COMMIT} .").trim()
        echo "git diff output (modified files):\n>>>>>\n${vgitout}\n<<<<<"
        anyFileChanged = !vgitout.isEmpty()
        // see if anything at all changed in this folder
        echo "Any file changed compared to last build: ${anyFileChanged}"
    } catch (ignored) {
        echo "git diff error => assume something must have changed"
        anyFileChanged = true
    }

    if (scmVars.GIT_COMMIT && scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT && !anyFileChanged && !forceBuild) {
        currentBuild.description = """${currentBuild.description}<p/>
					No changes happened in procurement-webui.
					"""
        echo "no changes happened in procurement-webui; skip building procurement-webui";
        return;
    }

    // set the root-pom's parent pom. Although the parent pom is available via relativePath, we need it to be this build's version then the root pom is deployed to our maven-repo
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DparentVersion=${env.MF_VERSION} ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:update-parent"

    // update the metasfresh.version property. either to the latest version or to the given params.MF_UPSTREAM_VERSION.
    final String mavenUpdatePropertyParam = '-Dproperty=metasfresh.version -DallowDowngrade=true'
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${mavenUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"

    // update the metasfresh-common.version property. either to the latest version or to the given params.MF_UPSTREAM_VERSION.
    final String mavenUpdateCommonPropertyParam = '-Dproperty=metasfresh-common.version -DallowDowngrade=true'
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${mavenUpdateCommonPropertyParam} ${VERSIONS_PLUGIN}:update-property"

    // set the artifact version of everything below the webui's pom.xml
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"

    // do the actual building and deployment
    // maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"

    currentBuild.description = """${currentBuild.description}<p/>
		artifacts (if not yet cleaned up)
			<ul>
<li><a href=\"https://repo.metasfresh.com/content/repositories/${mvnConf.mvnRepoName}/de/metas/procurement/de.metas.procurement.webui/${MF_VERSION}/de.metas.procurement.webui-${MF_VERSION}.jar\">de.metas.procurement.webui-${MF_VERSION}.jar</a></li>
</ul>""";
    //} // stage
}

return this

