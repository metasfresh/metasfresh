
-- stop filtering by AD_InputDataSource_ID because anyways we have just one olcandprocessor for all of them
UPDATE AD_Ref_Table
SET WhereClause='C_OLCand.Processed=''N'' AND C_OLCand.IsActive=''Y'' AND C_OLCand.IsError=''N'' AND C_OLCand.IsImportedWithIssues=''N''',
                        Updated='2020-08-24 17:02:11.556055 +02:00',
                        UpdatedBy=99
WHERE AD_Reference_ID=540476 AND WhereClause ILIKE '%AD_InputDataSource_ID IN (%';
