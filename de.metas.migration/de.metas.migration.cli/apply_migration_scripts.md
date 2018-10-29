

The eclipse launch config `apply_migration_scripts_print_help.launch` just prints the tool's help text to console and exits.

The launch config `apply_migration_scripts_run.launch` runs the RolloutMigrate tool and can be used to apply migration script from a particular folder structure to your local developer-DB


The launch config has two variables:
* `settings file`: this variable's default value is migrate.properties in the local folder; the value is passed as RolloutMigrate's `-s` parameter
* `SQL directory`: this variable is passed to RolloutMigrate's `-d` parameter.
