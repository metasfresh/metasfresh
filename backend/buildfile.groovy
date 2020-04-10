// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

Map build(final MvnConf mvnConf, final Map scmVars)
{
		final dockerImages = [:]
		String publishedDBInitDockerImageName
		final def misc = new de.metas.jenkins.Misc()

		stage('Build backend')
		{
			currentBuild.description= """${currentBuild.description}<p/>
				<h2>Backend</h2>
			"""
			def status = sh(returnStatus: true, script: "git diff --name-only ${scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT} ${scmVars.GIT_COMMIT} . | grep .") // see if anything at all changed in this folder
			echo "status of git dif command=${status}"
			if(scmVars.GIT_COMMIT && scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT && status != 0)
			{
				currentBuild.description= """${currentBuild.description}<p/>
				No changes happened in backend.
				"""
				echo "no changes happened in backend; skip building backend";
				return;
			}
			final String VERSIONS_PLUGIN='org.codehaus.mojo:versions-maven-plugin:2.5' // make sure we know which plugin version we run
			
			// update the parent pom version
			mvnUpdateParentPomVersion mvnConf

			// set the artifact version of everything below ${mvnConf.pomFile}
			// processAllModules=true: also update those modules that have a parent version range!
			sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${env.MF_VERSION} -DprocessAllModules=true -Dincludes=\"de.metas*:*\" ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"
			// Set the metasfresh.version property from [1,10.0.0] to our current build version
			// From the documentation: "Set a property to a given version without any sanity checks"; that's what we want here..sanity is clearly overated
			sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dproperty=metasfresh.version -DnewVersion=${env.MF_VERSION} ${VERSIONS_PLUGIN}:set-property"
			// build and install
			// maven.test.failure.ignore=true: continue if tests fail, because we want a full report.
			// about -Dmetasfresh.assembly.descriptor.version: the versions plugin can't update the version of our shared assembly descriptor de.metas.assemblies. Therefore we need to provide the version from outside via this property
			sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${env.MF_VERSION} ${mvnConf.resolveParams} ${mvnConf.deployParam} clean install"

			// deploy dist-artifacts. they were already installed further up, together with the rest
			final MvnConf webapiMvnConf = mvnConf.withPomFile('metasfresh-webui-api/pom.xml');
			sh "mvn --settings ${webapiMvnConf.settingsFile} --file ${webapiMvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${env.MF_VERSION} ${webapiMvnConf.resolveParams} ${webapiMvnConf.deployParam} deploy"
			
			// -T 1C means "run with 1 thread per CPU"; note that for us this is highly experimental
			final MvnConf distMvnConf = mvnConf.withPomFile('metasfresh-dist/dist/pom.xml');
			sh "mvn -T 1C --settings ${distMvnConf.settingsFile} --file ${distMvnConf.pomFile} --batch-mode -Dmaven.test.failure.ignore=true -Dmetasfresh.assembly.descriptor.version=${env.MF_VERSION} ${distMvnConf.resolveParams} ${distMvnConf.deployParam} deploy"

			publishJacocoReports(scmVars.GIT_COMMIT, 'codacy_project_token_for_metasfresh_repo')
				
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

			// postgres DB init container
			final DockerConf dbInitDockerConf = reportDockerConf
							.withArtifactName('metasfresh-db-init-pg-9-5')
							.withWorkDir('metasfresh-dist/dist/target/docker/db-init')
			publishedDBInitDockerImageName = dockerBuildAndPush(dbInitDockerConf)

			dockerImages['report'] = publishedReportDockerImageName
			dockerImages['msv3Server'] = publishedMsv3ServerImageName
			dockerImages['webuiApi'] = publishedWebuiApiImageName
			dockerImages['dbInit'] = publishedDBInitDockerImageName

			currentBuild.description= """${currentBuild.description}<br/>
				This build created the following deployable docker images 
				<ul>
				<li><code>${publishedMsv3ServerImageName}</code></li>
				<li><code>${publishedWebuiApiImageName}</code></li>
				<li><code>${publishedReportDockerImageName}</code> that can be used as <b>base image</b> for custom metasfresh-report docker images</li>
				<li><code>${publishedDBInitDockerImageName}</code></li>
				</ul>
				"""
		}

		final String metasfreshDistSQLOnlyURL = "${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-dist/${misc.urlEncode(env.MF_VERSION)}/metasfresh-dist-dist-${misc.urlEncode(env.MF_VERSION)}-sql-only.tar.gz"
		testSQLMigrationScripts(
			params.MF_SQL_SEED_DUMP_URL, 
			metasfreshDistSQLOnlyURL, 
			publishedDBInitDockerImageName,
			scmVars)
		return dockerImages
}

void testSQLMigrationScripts(
	final String sqlSeedDumpURL, 
	final String metasfreshDistSQLOnlyURL, 
	final String dbInitDockerImageName,
	final Map scmVars)
{
	stage('Test SQL-Migrationscripts')
	{
		def status = sh(returnStatus: true, script: "git diff --name-only ${scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT} ${scmVars.GIT_COMMIT} . | grep sql\$") // see if any *sql file changed in this folder
		echo "status of git dif command=${status}"
		if(scmVars.GIT_COMMIT && scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT && status != 0)
		{
			echo "no *.sql changes happened; skip applying SQL migration scripts";
			return;
		}
		if(sqlSeedDumpURL)
		{
			// run the pg-init docker image to check that the migration scripts work; make sure to clean up afterwards
			sh "docker run --rm -e \"URL_SEED_DUMP=${sqlSeedDumpURL}\" -e \"URL_MIGRATION_SCRIPTS_PACKAGE=${metasfreshDistSQLOnlyURL}\" ${dbInitDockerImageName}"
			sh "docker rmi ${dbInitDockerImageName}"
		}
		else
		{
			echo "We skip applying the migration scripts because params.MF_SQL_SEED_DUMP_URL was not set"
		}
	}
}

return this;