--select * from ad_migrationscript where name ilike '%31230_sys_mo73_05916_extend_letter%'
INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for 31230_sys_mo73_05916_extend_letter.sql', '40-sw01_swat->202852_sys_mo73_05916_extend_letter.sql', '40-sw01_swat'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%31230_sys_mo73_05916_extend_letter.sql%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%202852_sys_mo73_05916_extend_letter.sql%');
