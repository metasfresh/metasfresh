-- 2018-03-23T17:33:43.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='org.adempiere.bpartner.process.BPartnerCreditLimit_AddRemoveCreditStopStatus', Help='Add/Remove Credit Stop Status', Name='Add/Remove Credit Stop Status', Value='BPartnerCreditLimit_AddRemoveCreditStopStatus',Updated=TO_TIMESTAMP('2018-03-23 17:33:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540938
;

-- 2018-03-23T18:36:32.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,3090,0,540938,541273,20,'IsSetCreditStop',TO_TIMESTAMP('2018-03-23 18:36:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','Set the business partner to credit stop','D',0,'If a dunning letter of this level is created, the business partner is set to Credit Stop (needs to be manually changed).','Y','N','Y','N','Y','N','Kredit Stop',10,TO_TIMESTAMP('2018-03-23 18:36:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-23T18:36:32.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541273 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

