
-- deactivate the "Posted" AD_column of the "Doc_HRProcess" table. we don't use this feature and want to avoid 
-- java.lang.ClassNotFoundException: org.compiere.acct.Doc_HRProcess
-- in DocFactory.loadDocMetaInfo()
UPDATE AD_Column
SET IsActive='N',
	Updated=now(),
	UpdatedBy=99,
	Help='Deactivated; we don''t use this feature and want to avoid 
java.lang.ClassNotFoundException: org.compiere.acct.Doc_HRProcess
in DocFactory.loadDocMetaInfo()'
WHERE AD_Table_ID=53092 AND ColumnName='Posted';
