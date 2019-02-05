
-- 2019-02-04T14:13:50.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler 
SET AD_Role_ID=1000000, 
	CronPattern=NULL, 
	EntityType='de.metas.ordercandidate', 
	IsActive='N', /*still, don't have it run, unless it's activated*/
	Name='C_OLCand process to C_OrderLines',
	Description='Process C_OLcands into C_OrderLines; try "select WhereClause from AD_Ref_Table where AD_Reference_ID=540476" to see which C_OLCands are picked up',
	Updated=TO_TIMESTAMP('2019-02-04 14:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550015
;

-- then it runs, then also process C_OLCands with AD_InputDataSource_ID=1000001 /*MSV3-Order*/
UPDATE AD_Ref_Table
SET Updated=now(), Updatedby=99,
	WhereClause='C_OLCand.AD_InputDataSource_ID IN (150/*EDI-ORDERS*/, 160/*Excel-Pricelist*/, 170/*SQL COPY Script*/, 1000001/*MSV3-Order*/) AND C_OLCand.Processed=''N'' AND C_OLCand.IsActive=''Y'' AND C_OLCand.IsError=''N'' AND C_OLCand.IsImportedWithIssues=''N'''
WHERE AD_Reference_ID=540476;
