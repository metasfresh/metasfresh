
-- 2021-11-19T12:03:15.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541428,'S',TO_TIMESTAMP('2021-11-19 13:03:15','YYYY-MM-DD HH24:MI:SS'),100,'If set to Y, then metasfresh will not only create an invoice candidate, but directly invoice a new subscription contract, 
unless the respective customer already has some running contracts at the respective point in time.','de.metas.contracts','Y','de.metas.contracts.invoicecandidate.ALLOW_AUTO_INVOICE',TO_TIMESTAMP('2021-11-19 13:03:15','YYYY-MM-DD HH24:MI:SS'),100,'N')
;


/*
select db_fail_unless_row_count_greater_zero('UPDATE AD_SysConfig SET Value=''Y'', updated=''2021-11-19 13:47'', updatedby=99 where Name=''de.metas.contracts.invoicecandidate.ALLOW_AUTO_INVOICE''');
*/
