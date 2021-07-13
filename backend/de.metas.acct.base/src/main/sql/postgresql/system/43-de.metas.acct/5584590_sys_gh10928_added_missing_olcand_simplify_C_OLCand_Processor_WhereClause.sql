-- add filtering for IsGroupingError
UPDATE AD_Ref_Table
SET WhereClause='C_OLCand.Processed=''N'' AND C_OLCand.IsActive=''Y'' AND C_OLCand.IsError=''N'' AND C_OLCand.IsImportedWithIssues=''N'' AND C_OLCand.IsGroupingError=''N''',
    Updated='2021-04-06 17:38:00.0 +02:00',
    UpdatedBy=99
WHERE AD_Reference_ID = 540476
;
