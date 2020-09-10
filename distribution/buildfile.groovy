// note that we set a default version for this library in jenkins, so we don't have to specify it here

@Library('misc')
import de.metas.jenkins.MvnConf

Map build(final MvnConf mvnConf) {
    final artifactURLs = [:];

    stage('Resolve all distribution artifacts')
            {
                final String VERSIONS_PLUGIN = 'org.codehaus.mojo:versions-maven-plugin:2.5' // make sure we know which plugin version we run

                final def misc = new de.metas.jenkins.Misc();

                mvnUpdateParentPomVersion mvnConf

                // update the metasfresh.version property. either to the latest version or to the given params.MF_METASFRESH_VERSION.
                final String metasfreshUpdatePropertyParam = '-Dproperty=metasfresh.version -DallowDowngrade=true'
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"

                // gh #968 also update the metasfresh-webui-frontend.version, metasfresh-webui-api.versions and procurement versions.
                final String metasfreshAdminPropertyParam = '-Dproperty=metasfresh-admin.version -DallowDowngrade=true'
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshAdminPropertyParam} ${VERSIONS_PLUGIN}:update-property"
                final String metasfreshWebFrontEndUpdatePropertyParam = '-Dproperty=metasfresh-webui-frontend.version -DallowDowngrade=true'
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshWebFrontEndUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"
                final String metasfreshWebApiUpdatePropertyParam = '-Dproperty=metasfresh-webui-api.version -DallowDowngrade=true'
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshWebApiUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"
                final String metasfreshProcurementWebuiUpdatePropertyParam = '-Dproperty=metasfresh-procurement-webui.version -DallowDowngrade=true'
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} ${metasfreshProcurementWebuiUpdatePropertyParam} ${VERSIONS_PLUGIN}:update-property"

                // set the artifact version of everything below the parent ${mvnConf.pomFile}
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode -DnewVersion=${MF_VERSION} -DallowSnapshots=false -DgenerateBackupPoms=true -DprocessDependencies=true -DprocessParent=true -DexcludeReactor=true ${mvnConf.resolveParams} ${VERSIONS_PLUGIN}:set"

                // we now have set the versions of metas-webui etc within the pom.xml. In order to document them, write them into a file.
                // the file's name is app.properties, as configured in metasfresh-parent's pom.xml. Thx to http://stackoverflow.com/a/26589696/1012103
                sh "mvn --settings ${mvnConf.settingsFile} --file ${mvnConf.pomFile} --batch-mode ${mvnConf.resolveParams} org.codehaus.mojo:properties-maven-plugin:1.0.0:write-project-properties"

                // now load the properties we got from the pom.xml. Thx to http://stackoverflow.com/a/39644024/1012103
                final def mavenProps = readProperties file: 'app.properties'
                final def urlEncodedMavenProps = misc.urlEncodeMapValues(mavenProps);
                // echo "DONE calling misc.urlEncodeMapValues"

                artifactURLs['metasfresh-admin'] = "${mvnConf.resolveRepoURL}/de/metas/admin/metasfresh-admin/${urlEncodedMavenProps['metasfresh-admin.version']}/metasfresh-admin-${urlEncodedMavenProps['metasfresh-admin.version']}.jar"
                artifactURLs['metasfresh-dist'] = "${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-dist/${urlEncodedMavenProps['metasfresh.version']}/metasfresh-dist-dist-${urlEncodedMavenProps['metasfresh.version']}-dist.tar.gz"
                artifactURLs['metasfresh-dist-sql-only'] = "${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-dist/${urlEncodedMavenProps['metasfresh.version']}/metasfresh-dist-dist-${urlEncodedMavenProps['metasfresh.version']}-sql-only.tar.gz"
                artifactURLs['metasfresh-procurement-webui'] = "${mvnConf.resolveRepoURL}/de/metas/procurement/de.metas.procurement.webui/${urlEncodedMavenProps['metasfresh-procurement-webui.version']}/de.metas.procurement.webui-${urlEncodedMavenProps['metasfresh-procurement-webui.version']}.jar"
                artifactURLs['metasfresh-webui-api'] = "${mvnConf.resolveRepoURL}/de/metas/ui/web/metasfresh-webui-api/${urlEncodedMavenProps['metasfresh.version']}/metasfresh-webui-api-${urlEncodedMavenProps['metasfresh.version']}.jar"
                artifactURLs['metasfresh-webui-frontend'] = "${mvnConf.resolveRepoURL}/de/metas/ui/web/metasfresh-webui-frontend/${urlEncodedMavenProps['metasfresh-webui-frontend.version']}/metasfresh-webui-frontend-${urlEncodedMavenProps['metasfresh-webui-frontend.version']}.tar.gz"
                echo "DONE populating artifactURLs"

                final String MF_RELEASE_VERSION = misc.extractReleaseVersion(MF_VERSION)
                // echo "DONE calling misc.extractReleaseVersion"

                // Note: for the rollout-job's URL with the 'parambuild' to work on this pipelined jenkins, we need the https://wiki.jenkins-ci.org/display/JENKINS/Build+With+Parameters+Plugin, and *not* version 1.3, but later.
                // See
                //  * https://github.com/jenkinsci/build-with-parameters-plugin/pull/10
                //  * https://jenkins.ci.cloudbees.com/job/plugins/job/build-with-parameters-plugin/15/org.jenkins-ci.plugins$build-with-parameters/
                String releaseLinkWithText = "	<li>..and ${misc.createReleaseLinkWithText(MF_RELEASE_VERSION, MF_VERSION, artifactURLs, null/*dockerImages*/)}</li>";
                if (env.BRANCH_NAME == 'release') {
                    releaseLinkWithText = """	${releaseLinkWithText}
<li>..aaand ${misc.createWeeklyReleaseLinkWithText(MF_RELEASE_VERSION, MF_VERSION, artifactURLs, null/*dockerImages*/)}</li>"""
                }
                // echo "DONE calling misc.createReleaseLinkWithText"


                String latestE2eDockerImageName = "nexus.metasfresh.com:6001/metasfresh/metasfresh-e2e:${env.BRANCH_NAME}_LATEST"

                currentBuild.description = """${currentBuild.description}<p/>
<h2>Distribution</h2>						
<h3>Version infos</h3>
<ul>
  <li>metasfresh-webui-frontend: version <b>${mavenProps['metasfresh-webui-frontend.version']}</b></li>
  <li>metasfresh-procurement-webui: version <b>${mavenProps['metasfresh-procurement-webui.version']}</b></li>
  <li>metasfresh-admin webui: version <b>${mavenProps['metasfresh-admin.version']}</b></li>
  <li>metasfresh-backend: version <b>${mavenProps['metasfresh.version']}</b></li>
</ul>
<p>
<h3>Uploaded maven artifacts</h3>
<ul>
	<li><a href=\"${artifactURLs['metasfresh-dist']}\">dist-tar.gz</a></li>
	<li><a href=\"${artifactURLs['metasfresh-dist-sql-only']}\">sql-only-tar.gz</a></li>
	<li><a href=\"${mvnConf.deployRepoURL}/de/metas/dist/metasfresh-dist-swingui/${urlEncodedMavenProps['metasfresh.version']}/metasfresh-dist-swingui-${urlEncodedMavenProps['metasfresh.version']}-client.zip\">client.zip</a></li>
	<li><a href=\"${artifactURLs['metasfresh-webui-api']}\">metasfresh-webui-api.jar</a></li>
	<li><a href=\"${artifactURLs['metasfresh-webui-frontend']}\">metasfresh-webui-frontend.tar.gz</a></li>
	<li><a href=\"${artifactURLs['metasfresh-procurement-webui']}\">metasfresh-procurement-webui.jar</a></li>
	<li><a href=\"${artifactURLs['metasfresh-admin']}\">metasfresh-admin.jar</a></li>
</ul>
Note: all the separately listed artifacts are also included in the dist-tar.gz
<p>
<h3>Deploy</h3>
<ul>
	<li><a href=\"https://jenkins.metasfresh.com/job/ops/job/deploy_metasfresh/parambuild/?MF_ROLLOUT_FILE_URL=${artifactURLs['metasfresh-dist']}&MF_UPSTREAM_BUILD_URL=${BUILD_URL}\"><b>This link</b></a> lets you jump to a rollout job that will deploy (roll out) the <b>tar.gz to a host of your choice</b>.</li>
	${releaseLinkWithText}
	<li><a href=\"https://jenkins.metasfresh.com/job/ops/job/run_e2e_tests/parambuild/?MF_DOCKER_IMAGE_FULL_NAME=${latestE2eDockerImageName}&MF_DOCKER_REGISTRY=&MF_DOCKER_IMAGE=&MF_UPSTREAM_BUILD_URL=${BUILD_URL}\"><b>This link</b></a> lets you jump to a job that will perform an <b>e2e-test</b> using this branch's latest e2e-docker image.</li>
</ul>
<p>
<h3>Additional notes</h3>
<ul>
  <li>The artifacts on <a href="${mvnConf.mvnDeployRepoBaseURL}">repo.metasfresh.com</a> are cleaned up on a regular schedule to preserve disk space.<br/>
    Therefore the artifacts that are linked to by the URLs above might already have been deleted.</li>
  <li>It is important to note that both the <i>"metasfresh-dist"</i> artifacts (client and backend server) build by this job and the <i>"webui"</i> artifacts that are also linked here are based on the same underlying metasfresh version.
</ul>
""";
            }
    return artifactURLs;
}

return this;