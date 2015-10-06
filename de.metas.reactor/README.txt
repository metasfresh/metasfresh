
This project adds all adempiere projects into one reactor. 

This could allow us to use fixed release numbers (instead of -SNAPSHOTS) in our customer rollouts.

Currently i use it to build home-grown versions of local task-branches

To achive this, I first delete all existing snapshot artifacts from the local repo (to make sure that my task-branch artifact is not "shadowed" by a baseline artifact from jenkins):

mvn -DsnapshotsOnly=true -DreResolve=false -Dverbose=true dependency:purge-local-repository

After that i do a full clean and build, in offline mode:

mvn -o clean install

Further possible uses:

Also, it could make it easy do always have the correct (i.e. usually latest) version of metas project dependencies (metas, not 3rd-party!).

More specific:

*make maven-releases of arbitraty projects as requiered (maybe all of them, regardless of changes)

*use the maven versions plugin to update dependent projects to the new version

 mvn release:prepare 
 
seems to be one of the killer applications for this module.
It attempts to make a release for all modules! 
Note: I didn't really run it yet..first I need to make sure that the versions plugin also works (and is properly configured to ignore not-metas stuff)


Further possible uses that is explored to far:

Update all modules (note that currently the preferred way to do this is mg scmSync)

Note:

*this update to -r tip..i don't that this is what we want
 mvn scm:update-subprojects
 
Tag all modules

Notes

*currently I don't yet know how to exclude sub-sub-modules that don't have a repo of their own)

*mvn scm:tag does not tag subrepos

 mvn exec:exec -Dexec.executable="hg" -Dexec.args="tag -u <myname> <tagname>"