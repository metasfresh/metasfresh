#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Nexus
import de.metas.jenkins.Misc


Map build(final Map scmVars,
          final boolean forceBuild = false,
          final boolean forceSkip = false) {

    final misc = new Misc()

    final def resultsMap = [:]
    resultsMap.buildDescription = "<h4>procurement-webui-frontend</h4>"

    final String dockerLatestTag = "${misc.mkDockerTag(env.BRANCH_NAME)}_LATEST"

    if (forceSkip || (!misc.isAnyFileChanged(scmVars) && !forceBuild)) {

        final String dockerImageName = "metasfresh/procurement-webui-frontend"

        final Nexus nexus = new Nexus()
        resultsMap.dockerImage = nexus.retrieveDockerUrlToUse("${DockerConf.PULL_REGISTRY}:6001/${dockerImageName}:${dockerLatestTag}")

        resultsMap.buildDescription = """${resultsMap.buildDescription}<p/>
					No changes happened or forceSkip=true in procurement-webui-frontend; latest docker image: <code>${resultsMap.dockerImage}</code>
					"""

        echo "no changes happened or forceSkip=true in procurement-webui-frontend; skip building procurement-webui-frontend";
        return resultsMap
    }

    final DockerConf dockerConf = new DockerConf(
            'procurement-webui-frontend', // artifactName
            env.BRANCH_NAME, // branchName
            env.MF_VERSION, // versionSuffix
            '.' // workDir
    )
    resultsMap.dockerImage = dockerBuildAndPush(dockerConf)

    resultsMap.buildDescription = """${resultsMap.buildDescription}<p/>
		artifacts (if not yet cleaned up)
			<ul>
<li>a docker image with name <code>${resultsMap.dockerImage}</code>; Note that you can also use the tag <code>${dockerLatestTag}</code></li>
</ul>""";

    echo "Build and pushed docker image ${resultsMap.dockerImage}"
    return resultsMap
}

return this
