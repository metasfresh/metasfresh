
node('agent && linux') // shall only run on a jenkins agent with linux
{
timestamps 
{
	def GIT_BRANCH=${env.BRANCH_NAME}

	stage('Preparation') // for display purposes
	{
		echo "BRANCH_NAME=${env.BRANCH_NAME}"
		scm checkout
	}
   
    configFileProvider([configFile(fileId: 'aa1d8797-5020-4a20-aa7b-2334c15179be', replaceTokens: true, variable: 'MAVEN_SETTINGS')]) 
    {
        withMaven(jdk: 'java-8', maven: 'maven-3.3.9', mavenLocalRepo: '.repository', mavenOpts: '-Xmx1536M -XX:MaxHeapSize=512m') 
        {
            stage('Set artifact versions') 
            {
                // get our version strings
                echo "GIT_BRANCH=${GIT_BRANCH}"
                def GIT_BRANCH_LOCALNAME=GIT_BRANCH
                
                // set the version prefix, 1 for "master", 2 for "not-master" a.k.a. feature
                if(GIT_BRANCH_LOCALNAME.equals('master'))
                { BUILD_MAVEN_VERSION_PREFIX='1' } 
                else 
                { BUILD_MAVEN_VERSION_PREFIX='2' }
                
                // examples: "1-master-SNAPSHOT", "2-FRESH-123-SNAPSHOT"
                def BUILD_MAVEN_VERSION_LOCAL=BUILD_MAVEN_VERSION_PREFIX+"-"+GIT_BRANCH_LOCALNAME+"-SNAPSHOT"
                
                def BUILD_MAVEN_VERSION=BUILD_MAVEN_VERSION_LOCAL
                
                def BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION="[1-master-SNAPSHOT],["+BUILD_MAVEN_VERSION+"]"
                
                // output them to make things more clear
                echo "GIT_BRANCH_LOCALNAME=${GIT_BRANCH_LOCALNAME}"
                echo "BUILD_MAVEN_VERSION_LOCAL=${BUILD_MAVEN_VERSION_LOCAL}"
                echo "BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION}"
                echo "BUILD_MAVEN_VERSION=${BUILD_MAVEN_VERSION}"
                
                // set the artifact version of everything below de.metas.parent/pom.xml and then also for everything below de.metas.esb/pom.xml
                sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
                sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode -DnewVersion=${BUILD_MAVEN_VERSION} -DparentVersion=${BUILD_MAVEN_METASFRESH_DEPENDENCY_VERSION} -DallowSnapshots=true -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.1:update-parent org.codehaus.mojo:versions-maven-plugin:2.1:set"
            }
            
            // we can build the "main" and "esb" stuff in parallel
            parallel main: { 
                stage('Build metasfresh') 
                {
        		    //	input 'Ready to go?'
        
        			// deploy the de.metas.parent pom.xml to our "permanent" snapshot repo, but don't do anything with the modules that are declared in there
        			sh "mvn --settings $MAVEN_SETTINGS --file de.metas.parent/pom.xml --batch-mode --non-recursive --activate-profiles metasfresh-perm-snapshots-repo clean deploy"
        
        			sh "mvn --settings $MAVEN_SETTINGS --file de.metas.reactor/pom.xml --batch-mode clean deploy"
                }
            },esb: { 
                stage('Build esb') 
                {
    			    sh "mvn --settings $MAVEN_SETTINGS --file de.metas.esb/pom.xml --batch-mode clean deploy"
                }
            }, failFast: true
		}
	}

   stage('Results') {
      junit '**/target/surefire-reports/*.xml'
   }
} // timestamps   
} // node
