#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.MvnConf


def build(
        final MvnConf mvnConf, 
        final Map scmVars) {

    final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.7'

    // set the root-pom's parent pom. Although the parent pom is available via relativePath, we need it to be this build's version when the root pom is deployed to our maven-repo
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DparentVersion=${env.MF_VERSION} ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:update-parent"

    // set the artifact version of everything below de.metas.esb/pom.xml
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} -DnewVersion=${env.MF_VERSION} ${VERSIONS_PLUGIN}:set"

    // Set the metasfresh.version property from 10.0.0 to our current build version
    // From the documentation: "Set a property to a given version without any sanity checks"; that's what we want here..sanity is too much in the eye of the beholder
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dproperty=metasfresh.version -DnewVersion=${env.MF_VERSION} ${VERSIONS_PLUGIN}:set-property"

    // build and test
    // about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
    // maven.test.failure.ignore=true: see metasfresh stage
    // trimStackTrace=false: thx to https://github.com/cucumber/cucumber-jvm/issues/1877#issuecomment-578685012
    sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -DtrimStackTrace=false -Dmetasfresh.assembly.descriptor.version=${env.MF_VERSION} ${mvnConf.resolveParams} ${mvnConf.deployParam} clean test"

    withCredentials([string(credentialsId: 'studio.cucumber.io-API-token', variable: 'secretCucumberTokenl')]) {
        sh """curl -X POST https://studio.cucumber.io/cucumber_project/results \
     -F messages=@target/cucumber.message \
     -H \"project-access-token: ${secretCucumberTokenl}\" \
     -H \"provider: github\" \
     -H \"repo: metasfresh/metasfresh\" \
     -H \"branch: master\" \
     -H \"revision: ${scmVars.GIT_COMMIT}\""""
    }
}

return this
