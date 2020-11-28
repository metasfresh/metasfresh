
INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for x005650_sys_56_AddLoginAsHostKey.sql', '10-de.metas.adempiere->204275_sys_56_AddLoginAsHostKey.sql', '10-de.metas.adempiere'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%x005650_sys_56_AddLoginAsHostKey.sql%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%204275_sys_56_AddLoginAsHostKey.sql%');
