
INSERT INTO AD_MigrationScript (AD_Client_ID, AD_Org_ID, IsActive, ReleaseNo, Status, Created, CreatedBy, Updated, UpdatedBy, IsApply, FileName, Description, Name, ProjectName)
SELECT 0, 0, 'Y', 1, 'CO', now(), 100, now(), 100, 'Y', 'dummyfilename', 'Duplicating the record for 01500_US743_ResetAccountPassword.sql', '40-sw01_swat->177290_US743_ResetAccountPassword.sql', '40-sw01_swat'
WHERE 
	EXISTS (select 1 from AD_MigrationScript where name ilike '%01500_US743_ResetAccountPassword%')
	AND NOT EXISTS (select 1 from AD_MigrationScript where name ilike '%177290_US743_ResetAccountPassword%');
	