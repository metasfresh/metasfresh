#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here

@Library('misc')
import de.metas.jenkins.MvnConf

def build(final MvnConf mvnConf, final Map scmVars, final boolean forceBuild = false) {

    final String backendBuildDescription;
    final String backendDockerImage;
    final String frontendBuildDescription;
    final String frontendDockerImage;

    withMaven(jdk: 'java-14', maven: 'maven-3.6.3', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M', options: [artifactsPublisher(disabled: true)]) {
        dir('procurement-webui-backend') {
            final def buildFile = load('buildfile.groovy')
            final Map results = buildFile.build(mvnConf, scmVars, forceBuild)

            backendBuildDescription = results.buildDescription
            backendDockerImage = results.dockerImage
        }
    }
    dir('procurement-webui-frontend') {
        def buildFile = load('buildfile.groovy')
        buildFile.build(scmVars, forceBuild)

        frontendBuildDescription = results.buildDescription
        frontendDockerImage = results.dockerImage
    }
    dir('rabbitmq') {

        def buildFile = load('buildfile.groovy')
        buildFile.build(scmVars, forceBuild)
    }
    dir('nginx') {
        def buildFile = load('buildfile.groovy')
        buildFile.build(scmVars, forceBuild)
    }

    currentBuild.description = """
${frontendBuildDescription}<p>
${backendBuildDescription}<p>
"""
}

return this
