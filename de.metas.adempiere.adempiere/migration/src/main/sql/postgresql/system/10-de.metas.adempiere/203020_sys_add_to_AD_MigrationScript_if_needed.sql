--
--
-- if the three migration scripts x00530_sys_mo73_08917_AddAD_Form_ID_In_AD_Note, x005580_sys_mo73_09565_AddWhereClauseInAd_Note and x005620_sys_mo73_09727_AddProcessinAD_Note were already applied,
-- then insert records with their new filenames into AD_MigrationScript.
--


INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for x00530_sys_mo73_08917_AddAD_Form_ID_In_AD_Note.sql', '370lts-release->203021_sys_mo73_08917_AddAD_Form_ID_In_AD_Note.sql', '370lts-release'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%x00530_sys_mo73_08917_AddAD_Form_ID_In_AD_Note%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%203021_sys_mo73_08917_AddAD_Form_ID_In_AD_Note%');

INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for x005580_sys_mo73_09565_AddWhereClauseInAd_Note.sql', '370lts-release->203523_sys_mo73_09565_AddWhereClauseInAd_Note.sql', '370lts-release'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%x005580_sys_mo73_09565_AddWhereClauseInAd_Note%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%203523_sys_mo73_09565_AddWhereClauseInAd_Note%');

INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for x005620_sys_mo73_09727_AddProcessinAD_Note.sql', '370lts-release->203878_sys_mo73_09727_AddProcessinAD_Note.sql', '370lts-release'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%x005620_sys_mo73_09727_AddProcessinAD_Note.sql%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%203878_sys_mo73_09727_AddProcessinAD_Note.sql%');