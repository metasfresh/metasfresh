
ALTER TABLE AD_MigrationScript ADD Column DurationMillis bigint;
COMMIT;

/* not now, and there is also code to clean up
ALTER TABLE AD_MigrationScript DROP COLUMN DeveloperName;
ALTER TABLE AD_MigrationScript DROP COLUMN Reference;
ALTER TABLE AD_MigrationScript DROP COLUMN ReleaseNo;
ALTER TABLE AD_MigrationScript DROP COLUMN ScriptRoll;
ALTER TABLE AD_MigrationScript DROP COLUMN Status;
ALTER TABLE AD_MigrationScript DROP COLUMN URL;
ALTER TABLE AD_MigrationScript DROP COLUMN Script;
*/
