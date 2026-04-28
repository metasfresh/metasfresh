-- 19.12.2016 13:42
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540737,541132,20,'IsClear_DLM_Partition_Workqueue',TO_TIMESTAMP('2016-12-19 13:42:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.dlm',0,'Y','N','Y','N','Y','N','Ebenfalls Warteschlange Löschen',10,TO_TIMESTAMP('2016-12-19 13:42:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.12.2016 13:42
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541132 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 19.12.2016 13:43
-- URL zum Konzept
UPDATE AD_Process SET Description='Löst die Zuordnung aller dieser Partition zugeordneten Datensätze.',Updated=TO_TIMESTAMP('2016-12-19 13:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540737
;

-- 19.12.2016 13:43
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540737
;

