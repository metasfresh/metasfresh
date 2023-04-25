// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.MvnConf

Map build(
        final MvnConf mvnConf,
        final Map scmVars,
        final boolean forceBuild = false,
        final boolean forceSkipBackend = false,
        final boolean forceSkipCucumber = false,
        final String multithreadParam = "-T 2C") {

    final dockerImages = [:]
    String publishedDBInitDockerImageName

    stage('Build backend')
            {
                currentBuild.description = """${currentBuild.description}<p/>
				<h2>Backend</h2>
			"""
                if (forceSkipBackend) {
                    currentBuild.description = """${currentBuild.description}<p/>
            Forced to skip.
            """
                    echo "forced to skip backend";
                    return;
                }

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
					No changes happened in backend.
					"""
                    echo "no changes happened in backend; skip building backend";
                    return;
                }

                final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.8.1' // make sure we know which plugin version we run

                // set the root-pom's parent pom. Although the parent pom is available via relativePath, we need it to be this build's version then the root pom is deployed to our maven-repo
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DparentVersion=${env.MF_VERSION} ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:update-parent"

                // set the artifact version of everything below ${mvnConf.pomFile}
                // processAllModules=true: also update those modules that have a parent version range!
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${env.MF_VERSION} -DprocessAllModules=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"
                // Set the metasfresh.version property from to our current build version
                // From the documentation: "Set a property to a given version without any sanity checks"; that's what we want here..sanity is clearly overated
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dproperty=metasfresh.version -DnewVersion=${env.MF_VERSION} ${VERSIONS_PLUGIN}:set-property"

                final String metasfreshCommonUpdatePropertyParam = "-Dproperty=metasfresh-common.version -DallowDowngrade=true"
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshCommonUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"

                // build and deploy
                // GOAL: don't deploy - but we are not there yet
                // TODO: put alls jaspers&SQLs into their respective Docker images within the backend-build. Then we only need to deploy a few selected individual files; see frontend's build.grooy for how to do that			// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
                // about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
                // about -T 2C: it means "run with 2 threads per CPU"; note that for us this is highly experimental
                sh "mvn --settings ${mvnConf.settingsFile} ${multithreadParam} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${env.MF_VERSION} ${mvnConf.resolveParams} ${mvnConf.deployParam} clean deploy"

                final DockerConf reportDockerConf = new DockerConf(
                        'metasfresh-report', // artifactName
                        env.BRANCH_NAME, // branchName
                        env.MF_VERSION, // versionSuffix
                        'de.metas.report/metasfresh-report-service-standalone/target/docker' // workDir
                );
                final String publishedReportDockerImageName = dockerBuildAndPush(reportDockerConf)

                final DockerConf msv3ServerDockerConf = reportDockerConf
                        .withArtifactName('de.metas.vertical.pharma.msv3.server')
                        .withWorkDir('de.metas.vertical.pharma.msv3.server/target/docker');
                final String publishedMsv3ServerImageName = dockerBuildAndPush(msv3ServerDockerConf)

                final DockerConf webuiApiDockerConf = reportDockerConf
                        .withArtifactName('metasfresh-webui-api')
                        .withWorkDir('metasfresh-webui-api/target/docker');
                final String publishedWebuiApiImageName = dockerBuildAndPush(webuiApiDockerConf)

final DockerConf appDockerConf = reportDockerConf
                        .withArtifactName('metasfresh-app')
                        .withWorkDir('metasfresh-dist/dist/target/docker/app');
                final String publishedAppImageName = dockerBuildAndPush(appDockerConf)

//                // postgres DB init container
//                final DockerConf dbInitDockerConf = reportDockerConf
//                        .withArtifactName('metasfresh-db-init-pg-14-2')
//                        .withWorkDir('metasfresh-dist/dist/target/docker/db-init')
//                publishedDBInitDockerImageName = dockerBuildAndPush(dbInitDockerConf)

                dockerImages['report'] = publishedReportDockerImageName
                dockerImages['msv3Server'] = publishedMsv3ServerImageName
                dockerImages['webuiApi'] = publishedWebuiApiImageName
                dockerImages['app'] = publishedAppImageName
//                dockerImages['dbInit'] = publishedDBInitDockerImageName

                currentBuild.description = """${currentBuild.description}<br/>
				This build created the following deployable docker images 
				<ul>
				<li><code>${publishedMsv3ServerImageName}</code></li>
				<li><code>${publishedWebuiApiImageName}</code></li>
				<li><code>${publishedReportDockerImageName}</code> that can be used as <b>base image</b> for custom metasfresh-report docker images</li>
                <li><code>${publishedAppImageName}</code></li>
				<!-- <li><code>${publishedDBInitDockerImageName}</code></li> -->
				</ul>
				"""

                if(forceSkipCucumber) {
                    echo "forced to skip cucumber testing";
                } else {
                    dir('de.metas.cucumber') {
                        def cucumberBuildFile = load('buildfile.groovy')
                        cucumberBuildFile.build(mvnConf, scmVars)
                    }
                }
//                final String metasfreshDistSQLOnlyURL = "${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-dist/${misc.urlEncode(env.MF_VERSION)}/metasfresh-dist-dist-${misc.urlEncode(env.MF_VERSION)}-sql-only.tar.gz"
//                testSQLMigrationScripts(
//                        params.MF_SQL_SEED_DUMP_URL,
//                        metasfreshDistSQLOnlyURL,
//                        publishedDBInitDockerImageName,
//                        scmVars,
//                        forceBuild)

            } // stage build Backend

    return dockerImages
}

//void testSQLMigrationScripts(
//        final String sqlSeedDumpURL,
//        final String metasfreshDistSQLOnlyURL,
//        final String dbInitDockerImageName,
//        final Map scmVars,
//        final boolean forceBuild) {
//    stage('Test SQL-Migrationscripts')
//            {
//
//                def anyFileChanged
//                try {
//                    def vgitout = sh(returnStdout: true, script: "git diff --name-only ${scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT} ${scmVars.GIT_COMMIT} .").trim()
//                    echo "git diff output (modified files):\n>>>>>\n${vgitout}\n<<<<<"
//                    anyFileChanged = vgitout.contains(".sql") // see if any .sql file changed in this folder
//                    // see if anything at all changed in this folder
//                    echo "Any *.sql* file changed compared to last build: ${anyFileChanged}"
//                } catch (ignored) {
//                    echo "git diff error => assume something must have changed"
//                    anyFileChanged = true
//                }
//
//                if (scmVars.GIT_COMMIT && scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT && !anyFileChanged && !forceBuild) {
//                    echo "no *.sql changes happened; skip applying SQL migration scripts";
//                    return;
//                }
//
//                if (sqlSeedDumpURL) {
//                    // run the pg-init docker image to check that the migration scripts work; make sure to clean up afterwards
//                    sh "docker run --rm -e \"URL_SEED_DUMP=${sqlSeedDumpURL}\" -e \"URL_MIGRATION_SCRIPTS_PACKAGE=${metasfreshDistSQLOnlyURL}\" ${dbInitDockerImageName}"
//                    sh "docker rmi ${dbInitDockerImageName}"
//                } else {
//                    echo "We skip applying the migration scripts because params.MF_SQL_SEED_DUMP_URL was not set"
//                }
//            }
//}

return this;
