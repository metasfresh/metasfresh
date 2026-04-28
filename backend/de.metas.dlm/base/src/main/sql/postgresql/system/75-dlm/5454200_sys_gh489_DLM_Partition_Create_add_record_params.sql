-- 09.12.2016 13:52
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,126,0,540728,541122,30,'AD_Table_ID',TO_TIMESTAMP('2016-12-09 13:52:36','YYYY-MM-DD HH24:MI:SS'),100,'Tabelle, deren Datensatz verarbetiet werden soll (optional)','de.metas.dlm',0,'','Y','Y','N','N','N','N','DB-Tabelle',40,TO_TIMESTAMP('2016-12-09 13:52:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.12.2016 13:52
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541122 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 09.12.2016 13:54
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,538,0,540728,541123,11,'Record_ID',TO_TIMESTAMP('2016-12-09 13:54:09','YYYY-MM-DD HH24:MI:SS'),100,'Optional: Datensatz, der Verarbeitet werden soll. Wird ignoriert sofern nicht auch eine Tabelle angegeben wird.','de.metas.dlm',0,'','Y','N','N','N','N','N','Datensatz-ID',50,TO_TIMESTAMP('2016-12-09 13:54:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.12.2016 13:54
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541123 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 09.12.2016 13:54
-- URL zum Konzept
UPDATE AD_Process_Para SET Description='Optional: Tabelle, deren Datensatz verarbeitet werden soll. Wird ignoriert sofern nicht auch eine Datensatz-ID angegeben wird.',Updated=TO_TIMESTAMP('2016-12-09 13:54:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541122
;

-- 09.12.2016 13:54
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541122
;

-- 09.12.2016 13:55
-- URL zum Konzept
UPDATE AD_Process_Para SET Description='Optional: ID des Datensatzes, der verarbeitet werden soll. Wird ignoriert sofern nicht auch eine Tabelle angegeben wird.',Updated=TO_TIMESTAMP('2016-12-09 13:55:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541123
;

-- 09.12.2016 13:55
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541123
;

