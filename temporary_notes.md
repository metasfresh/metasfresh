

git clone git@github.com:metasfresh/metasfresh.git

cd metasfresh

git checkout -b gh6205_2nd

# move (almost) everything into a new "backend" folder
mkdir backend
git mv -k * backend
git mv .codacy.yaml .codebeatignore .gitignore .mvn backend # also move most hidden files
git commit -m "gh6205-app - move files to new backend folder" -m "metasfresh/metasfresh#6205"

# add metasfresh-webui-api to backend
git remote add -f webui-api git@github.com:metasfresh/metasfresh-webui-api.git
git fetch webui-api master
git merge webui-api/master --allow-unrelated-histories
mkdir backend/metasfresh-webui-api
git mv ISSUE_TEMPLATE.md  Jenkinsfile*  LICENSE.txt  metasfresh-webui-api.launch  metasfresh-webui-api_jrebel.launch  metasfresh-webui-api_logstash.launch  pom.xml  README.md src backend/metasfresh-webui-api
git mv .gitattributes .gitignore  .skip-archive-generated-artifacts  .workspace-sql-scripts.properties backend/metasfresh-webui-api
git commit -m "gh6205-app - move metasfresh-webui-api files to new backend folder" -m "metasfresh/metasfresh#6205"

# add metas-fresh-dist as a sibling to backend
git remote add -f dist git@github.com:metasfresh/metasfresh-dist.git
git fetch dist master
git merge dist/master --allow-unrelated-histories
mkdir distribution
git mv *.launch distribution
git mv .gitignore .workspace-sql-scripts.properties ait base dist Jenkinsfile LICENSE.txt pom.xml README.md serverRoot swingui distribution
git commit -m "gh6205-app - move metasfresh-dist files to new distribution folder" -m "metasfresh/metasfresh#6205"

# add metasfresh-webui-frontend as another sibling to backend
git remote add -f webui-frontend git@github.com:metasfresh/metasfresh-webui-frontend.git
git fetch webui-frontend master

mkdir temp # make space
git mv CODE_OF_CONDUCT.md  README.md Jenkinsfile CONTRIBUTING.md ReleaseNotes.md temp/
git commit -m "gh6205-app - temporarily make space for frontend files" -m "metasfresh/metasfresh#6205"

git merge webui-frontend/master --allow-unrelated-histories
mkdir frontend
git mv CONTRIBUTING.md docker index.html Jenkinsfile LICENSE.md package.json plugins.js.dist server.js src test_setup webpack.config.js config.js.dist favicon.png images ISSUE_TEMPLATE.md jsdoc.json licenses.json  plugins README.md snapshots.js tutorials webpack.prod.js frontend
git mv .babelrc .editorconfig .eslintrc .gitignore .storybook .dockerignore .eslintignore .htaccess .stylelintrc frontend
git commit -m "gh6205-app - move frontend files to new frontend folder" -m "metasfresh/metasfresh#6205"
git mv temp/* .
git commit -m "gh6205-app - move temporarily moved files back from temp folder" -m "metasfresh/metasfresh#6205"

# add metasfresh-e2e as another sibling to backend
git remote add -f e2e git@github.com:metasfresh/metasfresh-e2e.git
git fetch e2e master
mkdir temp # make space
git mv CODE_OF_CONDUCT.md  README.md Jenkinsfile CONTRIBUTING.md ReleaseNotes.md temp/
git commit -m "gh6205-app - temporarily make space for frontend files" -m "metasfresh/metasfresh#6205"
git merge e2e/master --allow-unrelated-histories
mkdir e2e
git mv .babelrc .eslintrc .github cypress.json Dockerfile Jenkinsfile package.json reporter-config.json run_docker_via_jenkins .editorconfig .gitignore cypress metasfresh-e2e.code-workspace README.md run_cypress.sh  src webpack.config.js e2e
git commit -m "gh6205-app - move e2e files to new e2e folder" -m "metasfresh/metasfresh#6205"

# add and commit that root Jenkinsfile

# later
# add metasfresh-procurement-webui and metasfresh-edi to misc subfolder that shall be another sibling to backend

# keep up to date

git pull && \
git merge origin/master && \
git fetch webui-api master && \
git merge webui-api/master --allow-unrelated-histories && \
git fetch dist master && \
git merge dist/master --allow-unrelated-histories && \
git fetch webui-frontend master && \
git merge webui-frontend/master --allow-unrelated-histories