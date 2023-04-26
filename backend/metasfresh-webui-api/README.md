[![Codacy Badge](https://api.codacy.com/project/badge/Grade/39f7f2f16c634233aae159c219169b7b)](https://www.codacy.com/app/metasfresh/metasfresh-webui?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=metasfresh/metasfresh-webui&amp;utm_campaign=Badge_Grade)
[![Join the chat at https://gitter.im/metasfresh/metasfresh](https://badges.gitter.im/metasfresh/metasfresh.svg)](https://gitter.im/metasfresh/metasfresh?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Krihelimeter](http://krihelinator.xyz/badge/metasfresh/metasfresh-webui-api)](http://krihelinator.xyz)

# metasfresh-webui-api

This repo contains the API service of our webui. The frontend can be found in its [own repository](https://github.com/metasfresh/metasfresh-webui-frontend).

# Some notes for developers:

* This can be run from from eclipse
* The `main` method is located at `/metasfresh-webui-api/src/main/java/de/metas/ui/web/WebRestApiApplication.java`
  * When running the API for the first time, you probably need to run it with `-Dwebui-api-run-headless=false` so that the swing dialog to set the DB connection can be displayed
  * If you also run metasfresh-admin locally on port 9090, you might also want to run this API with `-Dspring.boot.admin.url=http://localhost:9090 -Dmanagement.security.enabled=false`
* By default, this listens on port 8080
* Swagger UI url: http://localhost:8080/swagger-ui/index.html
* If you don't run elastic search in your development environment, you might also add the property `-Delastic_enable=false` when running the API so that the console will not be flooded with elastic search issues

