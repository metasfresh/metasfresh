#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.MvnConf


def build(final MvnConf mvnConf, final Map scmVars, final boolean forceBuild = false) {
    final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.7'

    // stage('Build edi')  // too many stages clutter the build info
    //{
    currentBuild.description = """${currentBuild.description}<p/>
			<h4>de-metas-camel-shipping</h4>
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
					No changes happened in EDI.
					"""
        echo "no changes happened in EDI; skip building EDI";
        return;
    }

    // set the root-pom's parent pom. Although the parent pom is avaialbe via relativePath, we need it to be this build's version then the root pom is deployed to our maven-repo
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DparentVersion=${env.MF_VERSION} ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:update-parent"

    // set the artifact version of everything below de.metas.esb/pom.xml
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} -DnewVersion=${env.MF_VERSION} ${VERSIONS_PLUGIN}:set"

    // Set the metasfresh.version property from 10.0.0 to our current build version
    // From the documentation: "Set a property to a given version without any sanity checks"; that's what we want here..sanity is too much in the eye of the beholder
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dproperty=metasfresh.version -DnewVersion=${env.MF_VERSION} ${VERSIONS_PLUGIN}:set-property"

    final def misc = new de.metas.jenkins.Misc();
    withEnv(["BRANCH_NAME_DOCKERIZED=${misc.mkDockerTag(env.BRANCH_NAME)}", "MF_VERSION_DOCKERIZED=${misc.mkDockerTag(env.MF_VERSION)}"]) {
        // some block
        // build and install
        // about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
        // maven.test.failure.ignore=true: see metasfresh stage
        sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${env.MF_VERSION} ${mvnConf.resolveParams} ${mvnConf.deployParam} clean install"
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
 -p 8793:8793 \\<br/>
 -e "JAVA_TOOL_OPTIONS='agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8793'"\\<br/>
 ${dockerInfo.image}
</code><br/>
If will run with it's local <code>application.properties</code> and <code>log4j2.properties</code> that probably make no sense for you.
To run with your own instead, include something as <code>-v /tmp/my-own-resources:/app/resources</code> in the <code>docker run</code>.
<p/>
"""
    //} // stage
}

return this
