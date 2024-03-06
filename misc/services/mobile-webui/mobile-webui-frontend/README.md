# <img src='/images/metasfresh-logo-slogan-969x248.png' height='60' alt='metasfresh Logo - We do Open Source ERP' aria-label='metasfresh.com' /></a>
[![license](https://img.shields.io/badge/license-GPL-blue.svg)](https://github.com/metasfresh/metasfresh/blob/master/LICENSE.md)
# Mobile WebUI

## PWA

## Notes:
  You cannot test the service worker in 'developer' mode. This works only in prod

  For developers to test the results of the service-worker they can use the serve tool

  https://www.npmjs.com/package/serve

  Once this is installed you can run `yarn build`:

  then `serve -s build`


## Running the unit tests

`cd metasfresh/misc/services/mobile-webui/mobile-webui-frontend`

Considering that you already installed the dependencies ( with `yarn install`) just run this to initiate the unit tests run:
`yarn test`


