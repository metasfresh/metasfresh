-- 08.03.2017 17:22
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=542135, ColumnName='M_HU_PI_ID', Name='Packvorschrift',Updated=TO_TIMESTAMP('2017-03-08 17:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541153
;

-- 08.03.2017 17:22
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541153
;

-- 08.03.2017 17:23
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''TU_To_NewLU'' | @Action@ = ''TU_To_NewTUs''',Updated=TO_TIMESTAMP('2017-03-08 17:23:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541153
;

-- 08.03.2017 17:31
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_NewTUs'' | @Action@ = ''TU_To_NewLU''',Updated=TO_TIMESTAMP('2017-03-08 17:31:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541152
;

-- 08.03.2017 17:31
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_NewTUs''',Updated=TO_TIMESTAMP('2017-03-08 17:31:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541152
;

-- 08.03.2017 17:37
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-03-08 17:37:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541152
;

-- 08.03.2017 17:37
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541152
;

-- 08.03.2017 17:37
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541152
;

-- 08.03.2017 17:37
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_NewTUs'' | @Action@ = ''TU_To_NewTUs'' | @Action@ = ''TU_To_NewLU''',Updated=TO_TIMESTAMP('2017-03-08 17:37:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541153
;

-- 09.03.2017 10:08
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='Ziel LU',Updated=TO_TIMESTAMP('2017-03-09 10:08:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541158
;

-- 09.03.2017 10:08
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541158
;

-- 09.03.2017 10:08
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='Ziel TU',Updated=TO_TIMESTAMP('2017-03-09 10:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541159
;

-- 09.03.2017 10:08
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541159
;

-- 09.03.2017 10:08
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='Eigene Gebinde',Updated=TO_TIMESTAMP('2017-03-09 10:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 10:08
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 10:09
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=542132, AD_Reference_ID=18, AD_Reference_Value_ID=540500, ColumnName='M_HU_PI_Item_Product_ID', Name='Packvorschrift-Produkt Zuordnung',Updated=TO_TIMESTAMP('2017-03-09 10:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541153
;

-- 09.03.2017 10:09
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541153
;

-- 09.03.2017 10:09
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N', Name='Packvorschrift TU',Updated=TO_TIMESTAMP('2017-03-09 10:09:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541153
;

-- 09.03.2017 10:09
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541153
;

-- 09.03.2017 10:11
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542133,0,540763,541165,19,'M_HU_PI_Item_ID',TO_TIMESTAMP('2017-03-09 10:11:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','N','N','N','N','Packvorschrift LU',25,TO_TIMESTAMP('2017-03-09 10:11:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.03.2017 10:11
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541165 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 09.03.2017 10:11
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2017-03-09 10:11:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541159
;

-- 09.03.2017 10:11
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2017-03-09 10:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541158
;

-- 09.03.2017 10:11
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2017-03-09 10:11:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 10:11
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2017-03-09 10:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541155
;

-- 09.03.2017 10:11
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2017-03-09 10:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541154
;

-- 09.03.2017 10:11
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2017-03-09 10:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541161
;

-- 09.03.2017 10:11
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2017-03-09 10:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541155
;

-- 09.03.2017 10:12
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2017-03-09 10:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541153
;

-- 09.03.2017 10:12
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2017-03-09 10:12:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541153
;

-- 09.03.2017 10:15
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=10,Updated=TO_TIMESTAMP('2017-03-09 10:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541161
;

-- 09.03.2017 10:15
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=30,Updated=TO_TIMESTAMP('2017-03-09 10:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541165
;

-- 09.03.2017 10:15
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=40,Updated=TO_TIMESTAMP('2017-03-09 10:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541158
;

-- 09.03.2017 10:15
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=50,Updated=TO_TIMESTAMP('2017-03-09 10:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541159
;

-- 09.03.2017 10:15
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=60,Updated=TO_TIMESTAMP('2017-03-09 10:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541154
;

-- 09.03.2017 10:15
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=70,Updated=TO_TIMESTAMP('2017-03-09 10:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541155
;

-- 09.03.2017 10:15
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=80,Updated=TO_TIMESTAMP('2017-03-09 10:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 10:16
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-03-09 10:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541153
;

-- 09.03.2017 10:16
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-03-09 10:16:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541154
;

-- 09.03.2017 10:16
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-03-09 10:16:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541155
;

-- 09.03.2017 10:16
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-03-09 10:16:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541158
;

-- 09.03.2017 10:16
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-03-09 10:16:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541159
;

-- 09.03.2017 10:17
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2017-03-09 10:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541160
;

-- 09.03.2017 10:17
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_NewCU''',Updated=TO_TIMESTAMP('2017-03-09 10:17:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541154
;

-- 09.03.2017 10:18
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_NewTUs''',Updated=TO_TIMESTAMP('2017-03-09 10:18:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541153
;

-- 09.03.2017 10:18
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_NewCU'' | @Action@ = ''CU_To_NewTUs''',Updated=TO_TIMESTAMP('2017-03-09 10:18:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541154
;

-- 09.03.2017 10:19
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_ExistingTU''',Updated=TO_TIMESTAMP('2017-03-09 10:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541159
;

-- 09.03.2017 10:19
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_NewCU'' | @Action@ = ''CU_To_NewTUs'' | @Action@ = ''CU_To_ExistingTU''',Updated=TO_TIMESTAMP('2017-03-09 10:19:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541154
;

-- 09.03.2017 10:20
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''TU_To_NewTUs''',Updated=TO_TIMESTAMP('2017-03-09 10:20:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541155
;

-- 09.03.2017 10:21
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''TU_To_NewLUs''',Updated=TO_TIMESTAMP('2017-03-09 10:21:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541165
;

-- 09.03.2017 10:21
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''TU_To_NewTUs'' | @Action@ = ''TU_To_NewLUs''',Updated=TO_TIMESTAMP('2017-03-09 10:21:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541155
;

-- 09.03.2017 10:22
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''TU_To_ExistingLU''',Updated=TO_TIMESTAMP('2017-03-09 10:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541158
;

-- 09.03.2017 10:22
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''TU_To_NewTUs'' | @Action@ = ''TU_To_NewLUs'' | @Action@ = ''TU_To_ExistingLU''',Updated=TO_TIMESTAMP('2017-03-09 10:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541155
;

-- 09.03.2017 10:43
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='CU entnehmen',Updated=TO_TIMESTAMP('2017-03-09 10:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541243
;

-- 09.03.2017 10:43
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541243
;

-- 09.03.2017 10:43
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='CU in neue TUs packen',Updated=TO_TIMESTAMP('2017-03-09 10:43:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541245
;

-- 09.03.2017 10:43
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541245
;

-- 09.03.2017 10:44
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='CU in bestehende TU packen',Updated=TO_TIMESTAMP('2017-03-09 10:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541244
;

-- 09.03.2017 10:44
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541244
;

-- 09.03.2017 10:44
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='TU entnehmen',Updated=TO_TIMESTAMP('2017-03-09 10:44:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541246
;

-- 09.03.2017 10:44
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541246
;

-- 09.03.2017 10:44
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='TU_To_NewLUs',Updated=TO_TIMESTAMP('2017-03-09 10:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541247
;

-- 09.03.2017 10:44
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='TU auf neue LUs laden', ValueName='TU_To_NewLUs',Updated=TO_TIMESTAMP('2017-03-09 10:44:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541247
;

-- 09.03.2017 10:44
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541247
;

-- 09.03.2017 10:45
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='TU auf bestehende LU laden',Updated=TO_TIMESTAMP('2017-03-09 10:45:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541248
;

-- 09.03.2017 10:45
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541248
;

