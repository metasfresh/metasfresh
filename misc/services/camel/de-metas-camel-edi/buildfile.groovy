#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf
import de.metas.jenkins.Nexus

def build(final MvnConf mvnConf, final Map scmVars, final boolean forceBuild = false) {
    final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.7'

    currentBuild.description = """${currentBuild.description}<p/>
			<h3>EDI</h3>
		"""

    final misc = new Misc()
    final String dockerLatestTag = "${misc.mkDockerTag(env.BRANCH_NAME)}_LATEST"

    if (!misc.isAnyFileChanged(scmVars) && !forceBuild) {

        final Nexus nexus = new Nexus()
        final String dockerImageName = 'metasfresh/de-metas-edi-esb-camel'
        final String latestDockerImageName = nexus.retrieveDockerUrlToUse("${DockerConf.PULL_REGISTRY}:6001/${dockerImageName}:${dockerLatestTag}")

        currentBuild.description = """${currentBuild.description}<p/>
					No changes happened in EDI; latest docker image: <code>${latestDockerImageName}</code>
					"""
        echo 'no changes happened in EDI; skip building EDI';
        return
    }

    // set the root-pom's parent pom. Although the parent pom is available via relativePath, we need it to be this build's version then the root pom is deployed to our maven-repo
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DparentVersion=${env.MF_VERSION} ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:update-parent"

    // set the artifact version of everything below de.metas.esb/pom.xml
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} -DnewVersion=${env.MF_VERSION} ${VERSIONS_PLUGIN}:set"

    // resolve the version property ${metasfresh-common.version} in the pom.xml
    final String commonPropertyParam = "-Dproperty=metasfresh-common.version -DnewVersion=LATEST"
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} -DallowDowngrade=true ${commonPropertyParam} ${VERSIONS_PLUGIN}:update-property"

    // build and install
    // about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
    // maven.test.failure.ignore=true: see metasfresh stage
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${env.MF_VERSION} ${mvnConf.resolveParams} ${mvnConf.deployParam} clean install"

    final DockerConf dockerConf = new DockerConf(
            'de-metas-edi-esb-camel', // artifactName
            env.BRANCH_NAME, // branchName
            env.MF_VERSION, // versionSuffix
            './') // workDir
    final String publishedDockerImageName = dockerBuildAndPush(dockerConf)

    currentBuild.description = """${currentBuild.description}<p/>
		This build's main artifact (if not yet cleaned up) is
<ul>
<li>a docker image with name <code>${publishedDockerImageName}</code>; Note that you can also use the tag <code>${env.BRANCH_NAME}_LATEST</code></li>
</ul>
You can run the docker image like this:<br>
<code>
docker run --rm\\<br/>
 -e "DEBUG_PORT=8792"\\<br/>
 -e "DEBUG_SUSPEND=n"\\<br/>
 -e "DEBUG_PRINT_BASH_CMDS=n"\\<br/>
 -e "SERVER_PORT=8184"\\<br/>
 -e "RABBITMQ_HOST=your.rabbitmq.host"\\<br/>
 -e "RABBITMQ_PORT=your.rabbitmq.port"\\<br/>
 -e "RABBITMQ_USER=your.rabbitmq.user"\\<br/>
 -e "RABBITMQ_PASSWORD=your.rabbitmq.password"\\<br/>
 ${publishedDockerImageName}
</code>
<p/>
"""
}

return this
