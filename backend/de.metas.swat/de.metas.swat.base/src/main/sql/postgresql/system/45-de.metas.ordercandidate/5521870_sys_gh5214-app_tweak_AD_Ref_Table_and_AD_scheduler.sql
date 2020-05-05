

-- 2019-05-17T22:19:07.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern=NULL, Description='Process C_OLcands into C_OrderLines. IMPORTANT: to see which C_OLCands are selected for processing, do a "select WhereClause from AD_Ref_Table where AD_Reference_ID=540476".', Updated=TO_TIMESTAMP('2019-05-17 22:19:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550015
;

UPDATE AD_Scheduler SET Supervisor_ID=100 WHERE AD_Scheduler_ID=550015 AND Supervisor_ID IS NULL;

--
-- Also process C_OLCands that are coming from the OrderLineCandidate REST endpoint
UPDATE AD_Ref_Table
SET Updated=now(), Updatedby=99,
	WhereClause='C_OLCand.AD_InputDataSource_ID IN (150/*EDI-ORDERS*/, 160/*Excel-Pricelist*/, 170/*SQL COPY Script*/, 1000001/*MSV3-Order*/, 1000002/*OrderLineCandidate-REST-EP*/) AND C_OLCand.Processed=''N'' AND C_OLCand.IsActive=''Y'' AND C_OLCand.IsError=''N'' AND C_OLCand.IsImportedWithIssues=''N'''
WHERE AD_Reference_ID=540476;
