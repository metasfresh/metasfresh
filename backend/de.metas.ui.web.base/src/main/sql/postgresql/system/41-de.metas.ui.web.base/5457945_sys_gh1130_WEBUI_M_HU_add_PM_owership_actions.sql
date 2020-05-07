
-- 09.03.2017 17:52
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=NULL, ColumnName='HUPlanningReceiptOwnerPM_LU', Name='LU ist eigenes Gebinde',Updated=TO_TIMESTAMP('2017-03-09 17:52:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 17:52
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 17:58
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541251,540702,TO_TIMESTAMP('2017-03-09 17:58:18','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Eigene LU',TO_TIMESTAMP('2017-03-09 17:58:18','YYYY-MM-DD HH24:MI:SS'),100,'LU_Set_Ownership','Eigene LU')
;

-- 09.03.2017 17:58
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541251 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 09.03.2017 17:58
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='Eigene LU',Updated=TO_TIMESTAMP('2017-03-09 17:58:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 17:58
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 17:58
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541252,540702,TO_TIMESTAMP('2017-03-09 17:58:49','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Eigene TU',TO_TIMESTAMP('2017-03-09 17:58:49','YYYY-MM-DD HH24:MI:SS'),100,'TU_Set_Ownership','Eigene TU')
;

-- 09.03.2017 17:58
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541252 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 09.03.2017 17:58
-- URL zum Konzept
UPDATE AD_Ref_List SET ValueName='LU_Set_Ownership',Updated=TO_TIMESTAMP('2017-03-09 17:58:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541251
;

-- 09.03.2017 17:58
-- URL zum Konzept
UPDATE AD_Ref_List SET ValueName='LU_Set_Ownership',Updated=TO_TIMESTAMP('2017-03-09 17:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541252
;

-- 09.03.2017 17:59
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-03-09 17:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541251
;

-- 09.03.2017 17:59
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-03-09 17:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541252
;

UPDATE AD_Ref_List SET ValueName='TU_Set_Ownership' WHERE AD_Ref_List_ID=541252;

-- 09.03.2017 18:00
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540763,541167,20,'HUPlanningReceiptOwnerPM_TU',TO_TIMESTAMP('2017-03-09 18:00:25','YYYY-MM-DD HH24:MI:SS'),100,'N','If true, then the packing material''s owner is "us" (the guys who ordered it). If false, then the packing material''s owner is the PO''s partner.','','de.metas.ui.web',0,'Y','N','N','N','Y','N','Eigene TU',90,TO_TIMESTAMP('2017-03-09 18:00:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.03.2017 18:00
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541167 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 09.03.2017 18:00
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''LU_Set_Ownership''',Updated=TO_TIMESTAMP('2017-03-09 18:00:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 18:01
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''LU_Set_Ownership'' | @Action@ = ''TU_To_NewLUs''',Updated=TO_TIMESTAMP('2017-03-09 18:01:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 18:01
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''TU_Set_Ownership'' | @Action@ = ''CU_To_NewTUs''',Updated=TO_TIMESTAMP('2017-03-09 18:01:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541167
;

-- 09.03.2017 18:02
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-03-09 18:02:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541167
;

-- 09.03.2017 18:02
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-03-09 18:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 18:02
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-03-09 18:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 18:02
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-03-09 18:02:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541167
;

--
-- do disable e.g. TU_Set_Ownership, so the following:
--
/*
UPDATE AD_Process_Para SET IsActive='N' WHERE ColumnName='HUPlanningReceiptOwnerPM_TU';
UPDATE AD_Ref_List SET IsActive='N' WHERE ValueName='TU_Set_Ownership';
*/
