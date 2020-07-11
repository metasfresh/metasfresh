#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

def build(final MvnConf mvnConf, final Map scmVars, final boolean forceBuild = false) {

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
					No changes happened in admin.
					"""
        echo "no changes happened in admin; skip building admin";
        return;
    }

    dir('pojos')
            {
                def buildFile = load('buildfile.groovy')
                buildFile.build(mvnConf, scmVars, forceBuild)
            }
}

return this
