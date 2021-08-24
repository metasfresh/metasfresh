-- 2021-03-15T15:43:15.752Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='N', SeqNo=20,Updated=TO_TIMESTAMP('2021-03-15 17:43:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540712
;

-- 2021-03-15T15:43:52.970Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1103,0,540574,541950,20,'IsDefault',TO_TIMESTAMP('2021-03-15 17:43:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','Default value','de.metas.esb.edi',0,'The Default Checkbox indicates if this record will be used as a default value.','Y','N','Y','N','Y','N','Standard',30,TO_TIMESTAMP('2021-03-15 17:43:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-15T15:43:53.015Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541950 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-03-15T15:44:13.883Z
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=10,Updated=TO_TIMESTAMP('2021-03-15 17:44:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541950
;

-- 2021-03-15T16:18:15.795Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@IsDefault/X@=N',Updated=TO_TIMESTAMP('2021-03-15 18:18:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540712
;

