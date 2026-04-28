-- 23.12.2016 10:51
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540735,541133,20,'IsTest',TO_TIMESTAMP('2016-12-23 10:51:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Falls angehackt wird nur überprüft, ob die jeweilige(n) Parttione(n) geändert werden könnten, oder ob inzwischen weitere Datensätze dazu gekommen sind, die eine Änderung des DLM-Levels verhindern.','de.metas.dlm',1,'Y','N','Y','N','Y','N','Testlauf',10,TO_TIMESTAMP('2016-12-23 10:51:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.12.2016 10:51
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541133 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

