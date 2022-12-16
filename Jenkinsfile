#!/usr/bin/env groovy

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.MvnConf

chuckNorris()

// keep the last 20 builds for master and stable, but onkly the last 10 for the rest, to preserve disk space on jenkins
final String numberOfBuildsToKeepStr = (env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'stable') ? '20' : '10'

final String MF_SQL_SEED_DUMP_URL_DEFAULT = 'https://nexus.metasfresh.com/repository/mvn-release-releases/de/metas/dist/metasfresh-dist-dist/5.173/metasfresh-dist-dist-5.173-customfmt.pgdump'

// thx to http://stackoverflow.com/a/36949007/1012103 with respect to the parameters
properties([
        parameters([
                booleanParam(defaultValue: false,
                        description: 'If true, then rebuild everything, no matter if there were changes',
                        name: 'MF_FORCE_FULL_BUILD'),

                booleanParam(defaultValue: false,
                        description: 'If true, then don\'t build frontend, even if there were changes or <code>MF_FORCE_FULL_BUILD</code> is set to <code>true<code>',
                        name: 'MF_FORCE_SKIP_FRONTEND_BUILD'),

                booleanParam(defaultValue: false,
                        description: 'If true, then don\'t build backend, even if there were changes or <code>MF_FORCE_FULL_BUILD</code> is set to <code>true<code>',
                        name: 'MF_FORCE_SKIP_BACKEND_BUILD'),

                booleanParam(defaultValue: true,
                        description: 'If true, and the backend is build, then don\'t run the cucumber tests.<br/>Note that the cucumber tests are run by the github Actions (docker-based) builds',
                        name: 'MF_FORCE_SKIP_CUCUMBER_BUILD'),

                booleanParam(defaultValue: false,
                        description: 'If true, then don\'t build the mobile webui, even if there were changes or <code>MF_FORCE_FULL_BUILD</code> is set to <code>true<code>',
                        name: 'MF_FORCE_SKIP_MOBILE_WEBUI_BUILD'),

                booleanParam(defaultValue: true,
                        description: 'If true, then don\'t build the procurement webui, even if there were changes or <code>MF_FORCE_FULL_BUILD</code> is set to <code>true<code>',
                        name: 'MF_FORCE_SKIP_PROCUREMENT_WEBUI_BUILD'),

                booleanParam(defaultValue: false,
                        description: 'If true, then don\'t build cypress (e2e), even if there were changes or <code>MF_FORCE_FULL_BUILD</code> is set to <code>true<code>',
                        name: 'MF_FORCE_SKIP_CYPRESS_BUILD'),

                string(defaultValue: MF_SQL_SEED_DUMP_URL_DEFAULT,
                        description: 'metasfresh database seed against which the build shall apply its migrate scripts for QA; leave empty to avoid this QA.',
                        name: 'MF_SQL_SEED_DUMP_URL'),
        ]),
        pipelineTriggers([]),
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: numberOfBuildsToKeepStr)) // keep the last $numberOfBuildsToKeepStr builds
])

currentBuild.description = currentBuild.description ?: ''

try {
    timestamps {
        // https://github.com/metasfresh/metasfresh/issues/2110 make version/build infos more transparent
        final String MF_VERSION = retrieveArtifactVersion(env.BRANCH_NAME, env.BUILD_NUMBER)
        currentBuild.displayName = "artifact-version ${MF_VERSION}"

        node('agent && linux') {

            configFileProvider([configFile(fileId: 'metasfresh-global-maven-settings', replaceTokens: true, variable: 'MAVEN_SETTINGS')])
                    {
                        // create our config instance to be used further on
                        final MvnConf mvnConf = new MvnConf(
                                'pom.xml', // pomFile
                                MAVEN_SETTINGS, // settingsFile
                                "mvn-${env.BRANCH_NAME}".replace("/", "-"), // mvnRepoName
                                'https://repo.metasfresh.com' // mvnRepoBaseURL
                        )
                        // This toString() method causes problems in the area of "CPS".
                        // Namely, our cucumber-tests are not executed and the message is
                        // "expected to call Script5.build but wound up catching de.metas.jenkins.MvnConf.toString; see: https://jenkins.io/redirect/pipeline-cps-method-mismatches/"
                        //echo "mvnConf=${mvnConf.toString()}" this can interfere

                        final def scmVars = checkout scm
                        echo "git debug scmVars=>>>>>${scmVars}<<<<<"

                        currentBuild.description = """${currentBuild.description}
			<b>
			<ul>
			<li>This job builds commit <a href="https://github.com/metasfresh/metasfresh/commit/${scmVars.GIT_COMMIT}">${scmVars.GIT_COMMIT}</a></li>
			<li>The changes since the last successful commit are <a href="https://github.com/metasfresh/metasfresh/compare/${scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT}..${scmVars.GIT_COMMIT}">here</a></li>
			</ul>
			</b>
			"""
                        sh 'git clean -d --force -x' // clean the workspace

                        // we need to provide MF_VERSION because otherwise the profile "MF_VERSION-env-missing" would be activated from the metasfresh-parent pom.xml
                        // and therefore, the jenkins information would not be added to the build.properties info file.
                        buildAll(MF_VERSION, mvnConf, scmVars) // withEnv

                    } // configFileProvider

            cucumber failedFeaturesNumber: -1, failedScenariosNumber: -1, failedStepsNumber: -1, fileIncludePattern: '**/target/cucumber.json', pendingStepsNumber: -1, skippedStepsNumber: -1, sortingMethod: 'ALPHABETICAL', undefinedStepsNumber: -1

            // always clean up the workspace. otherwise, if e.g. github or docker-hub acts up all out jenkins nodes' disk might run full
            cleanWs cleanWhenAborted: true, cleanWhenFailure: true
        } // node
    } // timestamps
} catch (all) {
    final String mattermostMsg = "This **${env.BRANCH_NAME}** build failed or was aborted: ${BUILD_URL}"
    if (env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'release') {
        mattermostSend color: 'danger', message: mattermostMsg
    } else {
        withCredentials([string(credentialsId: 'jenkins-issue-branches-webhook-URL', variable: 'secretWebhookUrl')])
                {
                    mattermostSend color: 'danger', endpoint: secretWebhookUrl, channel: 'jenkins-low-prio', message: mattermostMsg
                }
    }
    throw all
}

private void buildAll(String mfVersion, MvnConf mvnConf, scmVars) {

    withEnv(["MF_VERSION=${mfVersion}"]) {
                // disable automatic fingerprinting and archiving by artifactsPublisher, because in particular the archiving takes up too much space on the jenkins server.
                withMaven(jdk: 'java-8-AdoptOpenJDK', maven: 'maven-3.6.3', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M', options: [artifactsPublisher(disabled: true)]) {

                    nexusCreateRepoIfNotExists(mvnConf.mvnDeployRepoBaseURL, mvnConf.mvnRepoName)
                    stage('Build parent-pom & commons') { // for display purposes

                                dir('misc/parent-pom') {
                                            def buildFile = load('buildfile.groovy')
                                            buildFile.build(mvnConf, scmVars) // in there we don't do diff..we always build&deploy it.
                                }
                                dir('misc/de-metas-common') {
                                            def buildFile = load('buildfile.groovy')
                                            buildFile.build(mvnConf, scmVars) // this one we also always build&deploy; it's tiny
                                }
                    }
                    // note: to do some of this in parallel, we first need to make sure that the different parts don't concurrently write to the build description
                    dir('frontend') {
                                def frontendBuildFile = load('buildfile.groovy')
                                frontendBuildFile.build(mvnConf, scmVars, params.MF_FORCE_FULL_BUILD, params.MF_FORCE_SKIP_FRONTEND_BUILD)
                            }
                    dir('backend') {
                                def backendBuildFile = load('buildfile.groovy')
                                backendBuildFile.build(mvnConf,
                                        scmVars,
                                        params.MF_FORCE_FULL_BUILD,
                                        params.MF_FORCE_SKIP_BACKEND_BUILD,
                                        params.MF_FORCE_SKIP_CUCUMBER_BUILD)
                            }
                }
                dir('misc/services') { // misc/services has modules with different maven/jdk settings
                            def miscServices = load('buildfile.groovy')
                            miscServices.build(mvnConf, scmVars, params.MF_FORCE_FULL_BUILD, params.MF_FORCE_SKIP_MOBILE_WEBUI_BUILD, params.MF_FORCE_SKIP_PROCUREMENT_WEBUI_BUILD)
                        }

                withMaven(jdk: 'java-8-AdoptOpenJDK', maven: 'maven-3.6.3', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M', options: [artifactsPublisher(disabled: true)]) {
                            dir('e2e') {
                                        def e2eBuildFile = load('buildfile.groovy')
                                        e2eBuildFile.build(scmVars, params.MF_FORCE_FULL_BUILD, params.MF_FORCE_SKIP_CYPRESS_BUILD)
                                    }
                            dir('distribution') {
                                        def distributionBuildFile = load('buildfile.groovy')
                                        distributionBuildFile.build(mvnConf)
                                    }
                            //junit '**/target/surefire-reports/*.xml'
                            publishJacocoReports(scmVars.GIT_COMMIT, 'codacy_project_token_for_metasfresh_repo')
                        } // withMaven
            }
}
