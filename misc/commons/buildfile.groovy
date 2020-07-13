#!/usr/bin/env groovy
// the "!#/usr/bin... is just to to help IDEs, GitHub diffs, etc properly detect the language and do syntax highlighting for you.
// thx to https://github.com/jenkinsci/pipeline-examples/blob/master/docs/BEST_PRACTICES.md

// note that we set a default version for this library in jenkins, so we don't have to specify it here
@Library('misc')
import de.metas.jenkins.DockerConf
import de.metas.jenkins.Misc
import de.metas.jenkins.MvnConf

def build(final MvnConf mvnConf, final Map scmVars) {

    // let's just always compile them. otherwise we need to resolve ranges in the depending classes which makes things once again more complicated
    dir('pojos')
            {
                def buildFile = load('buildfile.groovy')
                buildFile.build(mvnConf, scmVars)
            }
}

return this
