# Location of our cypress tests

Right now, the actual cypress tests reside in the [metasfresh-webui-frontend](https://github.com/metasfresh/metasfresh-webui-frontend) repo. This is going to change as of issue [#7](https://github.com/metasfresh/metasfresh-e2e/issues/7) Move existing cypress tests into this repo

# Docker

Note that if you just want to run or develop cypress tests from your local machine, you probably won't need to deal with this docker-stuff at all.

## Build the docker image

To build the docker image locally, you can do

```
# build the docker image; the CACHEBUST build-arg is optional and merely makes sure that apt upgrade etc is performed at least once a day
docker build --tag metasfresh-e2e --build-arg CACHEBUST=$(date "+%Y-%m-%d") .
```

## Run the docker image

run the docker container, e.g. like this

```
hostname=dev289.metasfresh.com
docker run --ipc=host --rm\
 -e "FRONTEND_URL=https://${hostname}:443"\
 -e "API_URL=https://${hostname}:443/rest/api"\
 -e "WS_URL=https://${hostname}:443/stomp"\
 -e "USERNAME=dev"\
 -e "PASSWORD=password"\
 -e "CYPRESS_SPEC=NOT_SET"\
 -e "CYPRESS_RECORD_KEY=NOT_SET"\
 -e "CYPRESS_BROWSER=chrome"\
 -e "DEBUG_CYPRESS_OUTPUT=n"\
 -e "DEBUG_PRINT_BASH_CMDS=n"\
 -e "DEBUG_SLEEP_AFTER_FAIL=y"\
 metasfresh-e2e
```

Notes:
* Movies are only made with `electron`

### About `--ipc=host`

See https://docs.cypress.io/guides/references/error-messages.html#The-Chromium-Renderer-process-just-crashed
(which links to https://github.com/cypress-io/cypress/issues/350 )

### About the `CYPRESS_SPEC` environment variable

If `CYPRESS_SPEC` is set, then cypress is run with the `--spec` command line parameter. 
See the documentation at https://docs.cypress.io/guides/guides/command-line.html#cypress-run-spec-lt-spec-gt 

Examples:
* `CYPRESS_SPEC=cypress/integration/currency/**` runs all specs in the `currency` folder
* `CYPRESS_SPEC=cypress/integration/currency/currency_activate_spec.js` runs exactly the `currency_activate_spec.js` spec

### About the `CYPRESS_BROWSER` environment variable

Tells the image, against which browser to run.
Currently supported:
* `chrome`
* `electron`


# Develop

## Prequisites

Note that you might need to first install cypress; [this documentation](https://docs.cypress.io/guides/getting-started/installing-cypress.html#npm-install) tells you how:

```
npm install cypress --save-dev
```

Also note that in addition you might also need to do a full `npm install` afterwards.

Also, you'll need to configure the login credentials/API endpoints. One file is responsible for this:

- `cypress/config.js` - stores API endpoints and login credentials

There are 2 files: 
- `cypress/config.js_template_local`
- `cypress/config.js_template_remote`

which you can copy to `cypress/config.js` and edit according to your needs.


## Running

To start cypress and run the tests, navigate to the e2e folder and type this in the terminal:

```
npm run cypress:open
```

## Examples:

#### Local machine
For my local machine, where webui-api is running in my IDE

...edit `cypress/config.js` like this (see: `cypress/config.js_template_local`)

```javascript
module.exports = {
  FRONTEND_URL: 'http://localhost:3000',
  API_URL: 'http://localhost:8080/rest/api',
  WS_URL: 'http://localhost:8080/stomp',
  PLUGIN_API_URL: 'http://localhost:9192/',
  username: 'metasfresh',
  password: '<your-pw>',
};
```

...and start cypress like this:

```
npm run cypress:open
```

#### Dev instance

For a dev instance (`dev333` in this example)

...edit `cypress/config.js` like this (see `cypress/config.js_template_remote`):

```javascript
module.exports = {
  FRONTEND_URL: 'https://dev333.metasfresh.com',
  API_URL: 'https://dev333.metasfresh.com/rest/api',
  WS_URL: 'https://dev333.metasfresh.com/stomp',
  PLUGIN_API_URL: 'http://dev333.metasfresh.com:9192/',
  username: 'dev',
  password: '<your-pw>',
};
```

...and start cypress like this:

```
npm run cypress:open
```

## Setting up Visual Studio Code

We currently use [VS code](https://code.visualstudio.com/download) to develop cypress tests.
Once you installed it, you can use IntelliSense for auto completion. 
Details here: https://docs.cypress.io/guides/tooling/intelligent-code-completion.html#Set-up-in-your-Dev-Environment-1 (Note: in the VS Code preferences, look for "edit settings.json").

Also, we recomment to setup your IDE to use `LF` (linux line endings) for EOL.
Otherwise there might be issues with our linter.

Additionally recommended VS Code plugins:
* `ESLint` for developing test specs
* `Docker` for editing our docker file
