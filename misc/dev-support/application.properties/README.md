These folders contain `application.properties` files that java developers can copy to their workspace to be used when they run metasfresh components from their workstation.

The directory structure is similar to the workspace so if you copy this whole folder structure, the different `application.properties` files should end up at their correct places.

Note that the current settings are suited for my personal workspace; yours might be different; E.g. I run elastic search, metasfresh-admin, postgresql and rabbitmq not directly on localhost, but in docker containers/minikube. 
Therefore, feel free to change them for your needs.

### application.properties load order
From the classpath
- The classpath root (resources)
- The classpath /config package

From the current directory
- The current directory
- The config/ subdirectory in the current directory
- Immediate child directories of the config/ subdirectory