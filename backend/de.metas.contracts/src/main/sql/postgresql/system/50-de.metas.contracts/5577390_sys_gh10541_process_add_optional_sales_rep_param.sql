-- 2021-01-23T22:32:16.615Z
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.contracts.commission.salesrep.process.C_Commission_Share_CreateMissingForSalesRep', EntityType='de.metas.contracts.commission', Name='Create missing commission shares for level-1 sales reps', Value='C_Commission_Share_CreateMissingForSalesRep',Updated=TO_TIMESTAMP('2021-01-23 23:32:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584787
;

-- 2021-01-23T22:32:17.029Z
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Create missing commission shares for level-1 sales reps',Updated=TO_TIMESTAMP('2021-01-23 23:32:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541580
;

-- 2021-01-23T22:33:47.738Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541357,0,584787,541907,30,138,'C_BPartner_SalesRep_ID',TO_TIMESTAMP('2021-01-23 23:33:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission',0,'Y','Y','Y','N','N','N','Zugeordneter Vertriebspartner',10,TO_TIMESTAMP('2021-01-23 23:33:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-23T22:33:47.767Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541907 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

