-- 2021-01-24T15:41:30.979Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,584787,541908,14,'SalesRepIds',TO_TIMESTAMP('2021-01-24 16:41:29','YYYY-MM-DD HH24:MI:SS'),100,'Optional comma separated list of C_BPartner_IDs of SalesRepIds to update','de.metas.contracts.commission',999999999,'Y','N','Y','N','N','N','SalesRepIds',20,TO_TIMESTAMP('2021-01-24 16:41:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-24T15:41:31.216Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541908 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

