#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here

@Library('misc')
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

def build(final MvnConf mvnConf, final Map scmVars, final boolean forceBuild = false) {

    currentBuild.description = """${currentBuild.description}
    <h3>procurement-webui</h3>"""

    final String backendBuildDescription
    final String backendDockerImage
    final String frontendBuildDescription
    final String frontendDockerImage
    final String rabbitmqBuildDescription
    final String rabbitmqDockerImage
    final String nginxBuildDescription
    final String nginxDockerImage

    dir('rabbitmq') {

        final def buildFile = load('buildfile.groovy')
        final Map results = buildFile.build(scmVars, forceBuild)

        rabbitmqBuildDescription = results.buildDescription
        rabbitmqDockerImage = results.dockerImage
    }
    dir('nginx') {
        final def buildFile = load('buildfile.groovy')
        final Map results = buildFile.build(scmVars, forceBuild)

        nginxBuildDescription = results.buildDescription
        nginxDockerImage = results.dockerImage
    }

    withMaven(jdk: 'java-14', maven: 'maven-3.6.3', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M', options: [artifactsPublisher(disabled: true)]) {
        dir('procurement-webui-backend') {
            final def buildFile = load('buildfile.groovy')
            final Map results = buildFile.build(mvnConf, scmVars, forceBuild)

            backendBuildDescription = results.buildDescription
            backendDockerImage = results.dockerImage
        }
    }
    dir('procurement-webui-frontend') {
        final def buildFile = load('buildfile.groovy')
        final Map results = buildFile.build(scmVars, forceBuild)

        frontendBuildDescription = results.buildDescription
        frontendDockerImage = results.dockerImage
    }

    // create and push your docker-compose.yml file
    sh 'cp docker-compose/docker-compose-template.yml docker-compose/docker-compose.yml'
    sh "sed -i 's|\${dockerImage.procurement_rabbitmq}|${rabbitmqDockerImage}|g' docker-compose/docker-compose.yml"
    sh "sed -i 's|\${dockerImage.procurement_nginx}|${nginxDockerImage}|g' docker-compose/docker-compose.yml"
    sh "sed -i 's|\${dockerImage.procurement_backend}|${backendDockerImage}|g' docker-compose/docker-compose.yml"
    sh "sed -i 's|\${dockerImage.procurement_frontend}|${frontendDockerImage}|g' docker-compose/docker-compose.yml"

    String dockerComposeGroupId='de.metas.procurement'
    String dockerComposeArtifactId='procurement-webui'
    String dockerComposeClassifier='docker-compose'
    withMaven(jdk: 'java-14', maven: 'maven-3.6.3', mavenLocalRepo: '.repository', options: [artifactsPublisher(disabled: true)]) {
        sh "mvn --settings ${mvnConf.settingsFile} ${mvnConf.resolveParams} -Dfile=docker-compose/docker-compose.yml -Durl=${mvnConf.deployRepoURL} -DrepositoryId=${mvnConf.MF_MAVEN_REPO_ID} -DgroupId=${dockerComposeGroupId} -DartifactId=${dockerComposeArtifactId} -Dversion=${env.MF_VERSION} -Dclassifier=${dockerComposeClassifier} -Dpackaging=yml -DgeneratePom=true org.apache.maven.plugins:maven-deploy-plugin:2.7:deploy-file"
    }

    final Misc misc = new Misc()
    currentBuild.description = """${currentBuild.description}
<p>
  this build's <a href="${mvnConf.deployRepoURL}/de/metas/procurement/procurement-webui/${misc.urlEncode(env.MF_VERSION)}/procurement-webui-${misc.urlEncode(env.MF_VERSION)}-${dockerComposeClassifier}.yml">docker-compose.yml</a>.
<p>
Note: you can always get the <b>latest</b> docker-compose.yml for this branch at <code>${mvnConf.mvnResolveRepoBaseURL}/service/rest/v1/search/assets/download?sort=version&repository=${mvnConf.mvnRepoName}&maven.groupId=${dockerComposeGroupId}&maven.artifactId=${dockerComposeArtifactId}&maven.classifier=${dockerComposeClassifier}&maven.extension=yml</code>

${frontendBuildDescription}<p>
${backendBuildDescription}<p>
${rabbitmqBuildDescription}<p>
${nginxBuildDescription}<p>
"""
}

return this
