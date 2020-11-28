
-- select * from ad_migrationscript where name ilike '%00480_sys_mo73_uat_05745_AD_Message_for_Paid%' -- 202846
INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for 00480_sys_mo73_uat_05745_AD_Message_for_Paid.sql', '50-de.metas.dunning->202846_sys_mo73_uat_05745_AD_Message_for_Paid.sql', '50-de.metas.dunning'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%00480_sys_mo73_uat_05745_AD_Message_for_Paid%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%202846_sys_mo73_uat_05745_AD_Message_for_Paid%');
