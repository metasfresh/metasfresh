
UPDATE AD_MigrationScript
SET
	Description='"moved" from 75-material-dispo to 10-de.metas.adempiere by script 5475289;Applied by de.metas.migration.impl.SQLDatabaseScriptsRegistry',
	ProjectName='10-de.metas.adempiere',
	Name='10-de.metas.adempiere->5475290_gh2816-app_rename_M_Attribute_column_to_IsStorageRelevant.sql'
WHERE Name='75-material-dispo->5475290_gh2816-app_rename_M_Attribute_column_to_IsStorageRelevant.sql';
