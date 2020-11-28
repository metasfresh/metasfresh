--
--
-- if the three migration scripts x00530_sys_mo73_08917_AddAD_Form_ID_In_AD_Note, x005580_sys_mo73_09565_AddWhereClauseInAd_Note and x005620_sys_mo73_09727_AddProcessinAD_Note were already applied,
-- then insert records with their new filenames into AD_MigrationScript.
--


INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for x00530_sys_mo73_08917_AddAD_Form_ID_In_AD_Note.sql', '10-de.metas.adempiere->203021_sys_mo73_08917_AddAD_Form_ID_In_AD_Note.sql', '10-de.metas.adempiere'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%x00530_sys_mo73_08917_AddAD_Form_ID_In_AD_Note%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%203021_sys_mo73_08917_AddAD_Form_ID_In_AD_Note%');

INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for x005580_sys_mo73_09565_AddWhereClauseInAd_Note.sql', '10-de.metas.adempiere->203523_sys_mo73_09565_AddWhereClauseInAd_Note.sql', '10-de.metas.adempiere'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%x005580_sys_mo73_09565_AddWhereClauseInAd_Note%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%203523_sys_mo73_09565_AddWhereClauseInAd_Note%');

INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for x005620_sys_mo73_09727_AddProcessinAD_Note.sql', '10-de.metas.adempiere->203878_sys_mo73_09727_AddProcessinAD_Note.sql', '10-de.metas.adempiere'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%x005620_sys_mo73_09727_AddProcessinAD_Note.sql%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%203878_sys_mo73_09727_AddProcessinAD_Note.sql%');
	

--select * from ad_migrationscript where name ilike '%31250_sys_mo73_07760_AddFlagIsAllowedTrlBox.sql%' --202905
INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for 31250_sys_mo73_07760_AddFlagIsAllowedTrlBox.sql', '10-de.metas.adempiere->202905_sys_mo73_07760_AddFlagIsAllowedTrlBox.sql', '10-de.metas.adempiere'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%31250_sys_mo73_07760_AddFlagIsAllowedTrlBox.sql%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%202905_sys_mo73_07760_AddFlagIsAllowedTrlBox.sql%');

--select * from ad_migrationscript where name ilike '%31260_sys_mo73_07760_SetDefaultForFlagIsAllowedTrlBox.sql%' --202955
INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for 31260_sys_mo73_07760_SetDefaultForFlagIsAllowedTrlBox.sql', '10-de.metas.adempiere->202955_sys_mo73_07760_SetDefaultForFlagIsAllowedTrlBox.sql', '10-de.metas.adempiere'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%31260_sys_mo73_07760_SetDefaultForFlagIsAllowedTrlBox.sql%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%202955_sys_mo73_07760_SetDefaultForFlagIsAllowedTrlBox.sql%');

--select * from ad_migrationscript where name ilike '%x005590_sys_mo73_09691_AllowSetPrio.sql%' -- 203815
INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for x005590_sys_mo73_09691_AllowSetPrio.sql', '10-de.metas.adempiere->203815_sys_mo73_09691_AllowSetPrio.sql', '10-de.metas.adempiere'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%x005590_sys_mo73_09691_AllowSetPrio.sql%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%203815_sys_mo73_09691_AllowSetPrio.sql%');

--select * from ad_migrationscript where name ilike '%31330_sys_mo73_08956_AddFlag.sql%' -- 203524
INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for 31330_sys_mo73_08956_AddFlag.sql', '10-de.metas.adempiere->203524_sys_mo73_08956_AddFlag.sql', '10-de.metas.adempiere'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%31330_sys_mo73_08956_AddFlag.sql%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%203524_sys_mo73_08956_AddFlag.sql%');

--select * from ad_migrationscript where name ilike '%31340_sys_mo73_08956_AddFlagIsAllowedMigrationScriptInWindow.sql%' -- 203526
INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for 31340_sys_mo73_08956_AddFlagIsAllowedMigrationScriptInWindow.sql', '10-de.metas.adempiere->203526_sys_mo73_08956_AddFlagIsAllowedMigrationScriptInWindow.sql', '10-de.metas.adempiere'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%31340_sys_mo73_08956_AddFlagIsAllowedMigrationScriptInWindow.sql%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%203526_sys_mo73_08956_AddFlagIsAllowedMigrationScriptInWindow.sql%');

	