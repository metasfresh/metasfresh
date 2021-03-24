
## Workspace based tool

* See startup log messages and `migrate.properties_template`
* The whole metasfresh-repo is by default labeled as "common" by the file `.workspace-sql-scripts.properties`
  * gotcha: `.workspace-sql-scripts.properties` needs to be in a sub-folder. So if you put it into `metasfresh/backend`,
  then your workspace needs to be `metasfresh`.

## Non-Workspace

The eclipse launch config `apply_migration_scripts_print_help.launch` just prints the tool's help text to console and exits.

The launch config `apply_migration_scripts_run.launch` runs the RolloutMigrate tool and can be used to apply migration script from a particular folder structure to your local developer-DB


The launch config has two variables:
* `settings file`: this variable's default value is migrate.properties in the local folder; the value is passed as RolloutMigrate's `-s` parameter
* `SQL directory`: this variable is passed to RolloutMigrate's `-d` parameter.
