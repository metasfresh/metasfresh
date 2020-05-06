

INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for 00740_sys_FRESH-FixSkipException.sql', '42-de.metas.async->205124_sys_FRESH-FixSkipException.sql', '42-de.metas.async'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%00740_sys_FRESH-FixSkipException%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%205124_sys_FRESH-FixSkipException%');
