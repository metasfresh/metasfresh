-- 15.09.2016 17:22
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=50, SeqNoGrid=50,Updated=TO_TIMESTAMP('2016-09-15 17:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4286
;

-- 15.09.2016 17:22
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=160, SeqNoGrid=160,Updated=TO_TIMESTAMP('2016-09-15 17:22:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11413
;

-- 15.09.2016 17:23
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=115, SeqNoGrid=115,Updated=TO_TIMESTAMP('2016-09-15 17:23:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4288
;

-- 15.09.2016 17:23
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=90, SeqNoGrid=90,Updated=TO_TIMESTAMP('2016-09-15 17:23:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4289
;

-- 15.09.2016 17:23
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-15 17:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4288
;

-- 15.09.2016 17:24
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=70, SeqNoGrid=70,Updated=TO_TIMESTAMP('2016-09-15 17:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4290
;

-- 15.09.2016 17:24
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=110, SeqNoGrid=110,Updated=TO_TIMESTAMP('2016-09-15 17:24:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4291
;

-- 15.09.2016 17:24
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=60, SeqNoGrid=60,Updated=TO_TIMESTAMP('2016-09-15 17:24:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4547
;

-- 15.09.2016 17:24
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=360, SeqNoGrid=360,Updated=TO_TIMESTAMP('2016-09-15 17:24:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5846
;

-- 15.09.2016 17:24
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=370, SeqNoGrid=370,Updated=TO_TIMESTAMP('2016-09-15 17:24:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5845
;

-- 15.09.2016 17:26
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555039,557256,0,344,0,TO_TIMESTAMP('2016-09-15 17:26:07','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Ware geliefert wurde',0,'de.metas.swat',0,'Y','Y','Y','Y','N','N','N','N','Y','Lieferdatum',80,80,0,1,1,TO_TIMESTAMP('2016-09-15 17:26:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.09.2016 17:26
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557256 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 15.09.2016 17:26
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=340, SeqNoGrid=340,Updated=TO_TIMESTAMP('2016-09-15 17:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4292
;

-- 15.09.2016 17:27
-- URL zum Konzept
UPDATE AD_Field SET Name='Nächste Aktion',Updated=TO_TIMESTAMP('2016-09-15 17:27:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557241
;

-- 15.09.2016 17:27
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=557241
;

-- 15.09.2016 17:27
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=180, SeqNoGrid=180,Updated=TO_TIMESTAMP('2016-09-15 17:27:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4293
;

-- 15.09.2016 17:27
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=40, SeqNoGrid=40,Updated=TO_TIMESTAMP('2016-09-15 17:27:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4294
;

-- 15.09.2016 17:28
-- URL zum Konzept
UPDATE AD_Field SET Name='Dringlichkeit',Updated=TO_TIMESTAMP('2016-09-15 17:28:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557242
;

-- 15.09.2016 17:28
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=557242
;

-- 15.09.2016 17:28
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=190,Updated=TO_TIMESTAMP('2016-09-15 17:28:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4295
;

-- 15.09.2016 17:28
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=190,Updated=TO_TIMESTAMP('2016-09-15 17:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4295
;

-- 15.09.2016 17:29
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555040,557257,0,344,0,TO_TIMESTAMP('2016-09-15 17:29:37','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.swat',0,'Y','Y','Y','Y','N','N','N','N','Y','Rücklieferung',140,140,0,1,1,TO_TIMESTAMP('2016-09-15 17:29:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.09.2016 17:29
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557257 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 15.09.2016 17:30
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=355, SeqNoGrid=355,Updated=TO_TIMESTAMP('2016-09-15 17:30:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4298
;

-- 15.09.2016 17:30
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-15 17:30:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4298
;

-- 15.09.2016 17:30
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=120, SeqNoGrid=120,Updated=TO_TIMESTAMP('2016-09-15 17:30:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11501
;

-- 15.09.2016 17:30
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=100, SeqNoGrid=100,Updated=TO_TIMESTAMP('2016-09-15 17:30:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4299
;

-- 15.09.2016 17:31
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=130, SeqNoGrid=130,Updated=TO_TIMESTAMP('2016-09-15 17:31:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11500
;

-- 15.09.2016 17:32
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', SeqNo=200, SeqNoGrid=200,Updated=TO_TIMESTAMP('2016-09-15 17:32:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4301
;

-- 15.09.2016 17:33
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555046,557258,0,344,0,TO_TIMESTAMP('2016-09-15 17:33:16','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','Y','PerformanceType',265,265,0,1,1,TO_TIMESTAMP('2016-09-15 17:33:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.09.2016 17:33
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557258 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 15.09.2016 17:33
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=260, SeqNoGrid=260,Updated=TO_TIMESTAMP('2016-09-15 17:33:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4302
;

-- 15.09.2016 17:33
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=270, SeqNoGrid=270,Updated=TO_TIMESTAMP('2016-09-15 17:33:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11418
;

-- 15.09.2016 17:34
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-15 17:34:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4302
;

-- 15.09.2016 17:34
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-15 17:34:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11418
;

-- 15.09.2016 17:34
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=400, SeqNoGrid=400,Updated=TO_TIMESTAMP('2016-09-15 17:34:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5858
;

-- 15.09.2016 17:36
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=210, SeqNoGrid=210,Updated=TO_TIMESTAMP('2016-09-15 17:36:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5858
;

-- 15.09.2016 17:36
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=400, SeqNoGrid=400,Updated=TO_TIMESTAMP('2016-09-15 17:36:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4303
;

-- 15.09.2016 17:37
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555041,557259,0,344,0,TO_TIMESTAMP('2016-09-15 17:37:23','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','Y','Qualität-Notiz',250,250,0,1,1,TO_TIMESTAMP('2016-09-15 17:37:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.09.2016 17:37
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557259 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 15.09.2016 17:37
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114, SeqNo=220, SeqNoGrid=220,Updated=TO_TIMESTAMP('2016-09-15 17:37:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11422
;

-- 15.09.2016 17:38
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=105,Updated=TO_TIMESTAMP('2016-09-15 17:38:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4306
;

-- 15.09.2016 17:39
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2016-09-15 17:39:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557249
;

-- 15.09.2016 17:39
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555043,557260,0,344,0,TO_TIMESTAMP('2016-09-15 17:39:11','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','N','Request Type Interner Name',0,0,0,1,1,TO_TIMESTAMP('2016-09-15 17:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.09.2016 17:39
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557260 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 15.09.2016 17:39
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114, IsSameLine='N', SeqNo=240, SeqNoGrid=240,Updated=TO_TIMESTAMP('2016-09-15 17:39:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11424
;

-- 15.09.2016 17:40
-- URL zum Konzept
UPDATE AD_Field SET Name='Ergebnisvorlagen', SeqNo=300, SeqNoGrid=300,Updated=TO_TIMESTAMP('2016-09-15 17:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11425
;

-- 15.09.2016 17:40
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=11425
;

-- 15.09.2016 17:40
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114, SeqNo=320, SeqNoGrid=320,Updated=TO_TIMESTAMP('2016-09-15 17:40:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11426
;

-- 15.09.2016 17:40
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=150, SeqNoGrid=150,Updated=TO_TIMESTAMP('2016-09-15 17:40:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11177
;

-- 15.09.2016 17:40
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=310, SeqNoGrid=310,Updated=TO_TIMESTAMP('2016-09-15 17:40:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4309
;

-- 15.09.2016 17:40
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=290, SeqNoGrid=290,Updated=TO_TIMESTAMP('2016-09-15 17:40:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4284
;

-- 15.09.2016 17:41
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=280, SeqNoGrid=280,Updated=TO_TIMESTAMP('2016-09-15 17:41:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4310
;

-- 15.09.2016 17:41
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=380, SeqNoGrid=380,Updated=TO_TIMESTAMP('2016-09-15 17:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5848
;

-- 15.09.2016 17:41
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=390, SeqNoGrid=390,Updated=TO_TIMESTAMP('2016-09-15 17:41:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5847
;

-- 15.09.2016 17:42
-- URL zum Konzept
UPDATE AD_Field SET Name='Erstellt durch',Updated=TO_TIMESTAMP('2016-09-15 17:42:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5845
;

-- 15.09.2016 17:42
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=5845
;

-- 15.09.2016 17:42
-- URL zum Konzept
UPDATE AD_Field SET Name='Erstellt durch',Updated=TO_TIMESTAMP('2016-09-15 17:42:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5841
;

-- 15.09.2016 17:42
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=5841
;

-- 15.09.2016 17:44
-- URL zum Konzept
UPDATE AD_Field SET Name='Ansprechpartner',Updated=TO_TIMESTAMP('2016-09-15 17:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4286
;

-- 15.09.2016 17:44
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=4286
;

-- 15.09.2016 17:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:45:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11413
;

-- 15.09.2016 17:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:45:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4289
;

-- 15.09.2016 17:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:45:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4291
;

-- 15.09.2016 17:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:45:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4547
;

-- 15.09.2016 17:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:45:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4293
;

-- 15.09.2016 17:46
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:46:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4295
;

-- 15.09.2016 17:46
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:46:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4299
;

-- 15.09.2016 17:46
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:46:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11418
;

-- 15.09.2016 17:46
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:46:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11422
;

-- 15.09.2016 17:46
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint''', Name='Beanstandung',Updated=TO_TIMESTAMP('2016-09-15 17:46:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557259
;

-- 15.09.2016 17:46
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=557259
;

-- 15.09.2016 17:47
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:47:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557258
;

-- 15.09.2016 17:47
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4301
;

-- 15.09.2016 17:47
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:47:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557257
;

-- 15.09.2016 17:47
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-15 17:47:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557256
;

