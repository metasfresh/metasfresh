#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

import de.metas.jenkins.MvnConf
import de.metas.jenkins.Misc

// note that we set a default version for this library in jenkins, so we don't have to specify it here

def build(final MvnConf mvnConf, final Map scmVars, final boolean forceBuild = false) {
    final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.7'

    currentBuild.description = """${currentBuild.description}<p/>
			<h4>procurement-webui-backend</h4>
		"""

    final misc = new Misc()
    if (!misc.isAnyFileChanged(scmVars) && !forceBuild) {
        currentBuild.description = """${currentBuild.description}<p/>
					No changes happened in procurement-webui-backend.
					"""
        echo "no changes happened in procurement-webui-backend; skip building procurement-webui-backend";
        return
    }

    // set the root-pom's parent pom. Although the parent pom is avaialbe via relativePath, we need it to be this build's version then the root pom is deployed to our maven-repo
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DparentVersion=${env.MF_VERSION} ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:update-parent"

    // update the metasfresh-common.version property. either to the latest version or to the given params.MF_UPSTREAM_VERSION.
    final String mavenUpdateCommonPropertyParam = '-Dproperty=metasfresh-common.version -DallowDowngrade=true'
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${mavenUpdateCommonPropertyParam} ${VERSIONS_PLUGIN}:update-property"

    // set the artifact version of everything below the webui's pom.xml
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"

    withEnv(["BRANCH_NAME_DOCKERIZED=${misc.mkDockerTag(env.BRANCH_NAME)}", "MF_VERSION_DOCKERIZED=${misc.mkDockerTag(env.MF_VERSION)}"]) {

        withCredentials([usernamePassword(credentialsId: 'nexus.metasfresh.com_jenkins', passwordVariable: 'DOCKER_PUSH_REGISTRY_PASSWORD', usernameVariable: 'DOCKER_PUSH_REGISTRY_USERNAME')]) {
    // do the actual building and deployment
    // maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -DtrimStackTrace=false ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"
        }
    }
    final def dockerInfo = readJSON file: 'target/jib-image.json'

    currentBuild.description = """${currentBuild.description}<p/>
		This build's main artifact (if not yet cleaned up) is
<ul>
<li>a docker image with name<br>
<code>${dockerInfo.image}</code></li>
<li>Alternative tag: <code>${dockerInfo.tags.last()}</code></li>
<li>Image-Id: <code>${dockerInfo.imageId}</code></li>
</ul>
Example: to connect a debugger on port 8793, you can run the docker image like this:<br>
<code>
docker run --rm\\<br/>
 -p 8082:8082\\<br/>
 -e "JAVA_TOOL_OPTIONS='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8793'"\\<br/>
 ${dockerInfo.image}
</code><br/>
<p/>
To run with your <code>application.properties</code>, include something as <code>-v /tmp/my-own-resources:/app/resources</code> in the <code>docker run</code> command.
"""
}

return this

