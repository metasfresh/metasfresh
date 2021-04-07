-- 2021-02-01T09:39:40.879Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,584656,541919,19,'AD_Org_ID',TO_TIMESTAMP('2021-02-01 10:39:35','YYYY-MM-DD HH24:MI:SS'),100,'1000000','Organisatorische Einheit des Mandanten','U',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie kĂ¶nnen Daten ĂĽber Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Sektion',5,TO_TIMESTAMP('2021-02-01 10:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-01T09:39:40.882Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541919 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-02-01T09:46:35.443Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,584748,541920,19,'AD_Org_ID',TO_TIMESTAMP('2021-02-01 10:46:30','YYYY-MM-DD HH24:MI:SS'),100,'1000000','Organisatorische Einheit des Mandanten','U',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie kĂ¶nnen Daten ĂĽber Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Sektion',30,TO_TIMESTAMP('2021-02-01 10:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-01T09:46:35.443Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541920 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-02-01T09:46:40.182Z
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=5,Updated=TO_TIMESTAMP('2021-02-01 10:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541920
;



-- 2021-02-01T14:02:54.625Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,584654,541921,19,'AD_Org_ID',TO_TIMESTAMP('2021-02-01 15:02:49','YYYY-MM-DD HH24:MI:SS'),100,'1000000','Organisatorische Einheit des Mandanten','U',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie kĂ¶nnen Daten ĂĽber Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Sektion',50,TO_TIMESTAMP('2021-02-01 15:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-01T14:02:54.627Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541921 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-02-01T14:02:58.629Z
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=5,Updated=TO_TIMESTAMP('2021-02-01 15:02:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541921
;



update AD_process set sqlstatement='select * from de_metas_endcustomer_fresh_reports.IntraTradeShipments(@C_Period_ID@, @AD_Org_ID@)' where ad_process_ID=584748;

