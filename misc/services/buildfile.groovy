#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here

@Library('misc')
import de.metas.jenkins.MvnConf

def build(final MvnConf mvnConf, final Map scmVars, final boolean forceBuild = false) {
    stage('Build misc services') {
        currentBuild.description = """${currentBuild.description}<p/>
			<h2>misc services</h2>"""

        dir('procurement-webui') {
            def buildFile = load('buildfile.groovy')
            buildFile.build(mvnConf, scmVars, forceBuild)
        }

        withMaven(jdk: 'java-14', maven: 'maven-3.6.3', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M', options: [artifactsPublisher(disabled: true)]) {
            dir('camel/de-metas-camel-edi') {
                def ediBuildFile = load('buildfile.groovy')
                ediBuildFile.build(mvnConf, scmVars, forceBuild)
            }

            dir('camel/de-metas-camel-externalsystems') {
                def ediBuildFile = load('buildfile.groovy')
                ediBuildFile.build(mvnConf, scmVars, forceBuild)
            }
        }

        withMaven(jdk: 'java-8', maven: 'maven-3.6.3', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M', options: [artifactsPublisher(disabled: true)]) {
            
            dir('admin') {
                def procurementWebuiBuildFile = load('buildfile.groovy')
                procurementWebuiBuildFile.build(mvnConf, scmVars, forceBuild)
            }
        }
    } // stage
}

return this
