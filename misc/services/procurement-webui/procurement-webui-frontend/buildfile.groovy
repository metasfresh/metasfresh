#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

@Library('misc')
import de.metas.jenkins.Misc
import de.metas.jenkins.DockerConf

def build(final Map scmVars, final boolean forceBuild=false)
{
	final misc = new Misc()
	if(!misc.isAnyFileChanged(scmVars) && !forceBuild)
	{
		return
	}

	final DockerConf materialDispoDockerConf = new DockerConf(
			'procurement-webui-frontend', // artifactName
			env.BRANCH_NAME, // branchName
			env.MF_VERSION, // versionSuffix
			'.' // workDir
	)
	final String publishedDockerImageName = dockerBuildAndPush(materialDispoDockerConf)
	echo "Build and pushed docker image ${publishedDockerImageName}"
} 

return this
