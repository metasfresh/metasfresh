-- 2021-07-14T19:27:49.083Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575030,650312,0,543016,TO_TIMESTAMP('2021-07-14 22:27:48','YYYY-MM-DD HH24:MI:SS'),100,4000,'EE01','Y','N','N','N','N','N','N','N','Zusammenfassung',TO_TIMESTAMP('2021-07-14 22:27:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-14T19:27:49.120Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-14T19:27:49.160Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579488) 
;

-- 2021-07-14T19:27:49.206Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650312
;

-- 2021-07-14T19:27:49.244Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(650312)
;

-- 2021-07-14T19:28:13.284Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621445
;

-- 2021-07-14T19:28:13.466Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621508
;

-- 2021-07-14T19:28:13.617Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621449
;

-- 2021-07-14T19:28:13.767Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621447
;

-- 2021-07-14T19:28:13.910Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621448
;

-- 2021-07-14T19:28:14.055Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621518
;

-- 2021-07-14T19:28:14.200Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621455
;

-- 2021-07-14T19:28:14.342Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621454
;

-- 2021-07-14T19:28:14.488Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621505
;

-- 2021-07-14T19:28:14.632Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621452
;

-- 2021-07-14T19:28:14.780Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621450
;

-- 2021-07-14T19:28:14.974Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621453
;

-- 2021-07-14T19:28:15.119Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2021-07-14 22:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621451
;

-- 2021-07-14T19:28:15.269Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2021-07-14 22:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621516
;

-- 2021-07-14T19:28:15.413Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2021-07-14 22:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621462
;

-- 2021-07-14T19:28:15.555Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2021-07-14 22:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621463
;

-- 2021-07-14T19:28:15.697Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2021-07-14 22:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621464
;

-- 2021-07-14T19:28:15.839Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2021-07-14 22:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621465
;

-- 2021-07-14T19:28:15.985Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2021-07-14 22:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621466
;

-- 2021-07-14T19:28:16.126Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2021-07-14 22:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621467
;

-- 2021-07-14T19:28:16.273Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2021-07-14 22:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621468
;

-- 2021-07-14T19:28:16.417Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2021-07-14 22:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621469
;

-- 2021-07-14T19:28:16.560Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2021-07-14 22:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621470
;

-- 2021-07-14T19:28:16.705Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2021-07-14 22:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621471
;

-- 2021-07-14T19:28:16.848Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2021-07-14 22:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621472
;

-- 2021-07-14T19:28:16.992Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2021-07-14 22:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621473
;

-- 2021-07-14T19:28:17.164Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2021-07-14 22:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621474
;

-- 2021-07-14T19:28:17.309Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2021-07-14 22:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621475
;

-- 2021-07-14T19:28:17.453Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2021-07-14 22:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621476
;

-- 2021-07-14T19:28:17.596Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2021-07-14 22:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621477
;

-- 2021-07-14T19:28:17.737Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2021-07-14 22:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621478
;

-- 2021-07-14T19:28:17.876Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2021-07-14 22:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621479
;

-- 2021-07-14T19:28:18.017Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2021-07-14 22:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621480
;

-- 2021-07-14T19:28:18.158Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2021-07-14 22:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621504
;

-- 2021-07-14T19:28:18.296Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2021-07-14 22:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621481
;

-- 2021-07-14T19:28:18.443Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2021-07-14 22:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621482
;

-- 2021-07-14T19:28:18.588Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2021-07-14 22:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621483
;

-- 2021-07-14T19:28:18.733Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=310,Updated=TO_TIMESTAMP('2021-07-14 22:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621484
;

-- 2021-07-14T19:28:18.881Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=320,Updated=TO_TIMESTAMP('2021-07-14 22:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621485
;

-- 2021-07-14T19:28:19.023Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=330,Updated=TO_TIMESTAMP('2021-07-14 22:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621486
;

-- 2021-07-14T19:28:19.181Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=340,Updated=TO_TIMESTAMP('2021-07-14 22:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621487
;

-- 2021-07-14T19:28:19.328Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=350,Updated=TO_TIMESTAMP('2021-07-14 22:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621488
;

-- 2021-07-14T19:28:19.474Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=360,Updated=TO_TIMESTAMP('2021-07-14 22:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621489
;

-- 2021-07-14T19:28:19.620Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=370,Updated=TO_TIMESTAMP('2021-07-14 22:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621515
;

-- 2021-07-14T19:28:19.766Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=380,Updated=TO_TIMESTAMP('2021-07-14 22:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621446
;

-- 2021-07-14T19:28:19.934Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=390,Updated=TO_TIMESTAMP('2021-07-14 22:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621509
;

-- 2021-07-14T19:28:20.082Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=400,Updated=TO_TIMESTAMP('2021-07-14 22:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621506
;

-- 2021-07-14T19:28:20.228Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=410,Updated=TO_TIMESTAMP('2021-07-14 22:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621490
;

-- 2021-07-14T19:28:20.370Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=420,Updated=TO_TIMESTAMP('2021-07-14 22:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621491
;

-- 2021-07-14T19:28:20.517Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=430,Updated=TO_TIMESTAMP('2021-07-14 22:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621492
;

-- 2021-07-14T19:28:20.664Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=440,Updated=TO_TIMESTAMP('2021-07-14 22:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621493
;

-- 2021-07-14T19:28:20.809Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=450,Updated=TO_TIMESTAMP('2021-07-14 22:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621494
;

-- 2021-07-14T19:28:20.955Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=460,Updated=TO_TIMESTAMP('2021-07-14 22:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621495
;

-- 2021-07-14T19:28:21.127Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=470,Updated=TO_TIMESTAMP('2021-07-14 22:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621496
;

-- 2021-07-14T19:28:21.270Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=480,Updated=TO_TIMESTAMP('2021-07-14 22:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621497
;

-- 2021-07-14T19:28:21.418Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=490,Updated=TO_TIMESTAMP('2021-07-14 22:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621498
;

-- 2021-07-14T19:28:21.565Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=500,Updated=TO_TIMESTAMP('2021-07-14 22:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621499
;

-- 2021-07-14T19:28:21.710Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=510,Updated=TO_TIMESTAMP('2021-07-14 22:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621500
;

-- 2021-07-14T19:28:21.858Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=520,Updated=TO_TIMESTAMP('2021-07-14 22:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621501
;

-- 2021-07-14T19:28:22.005Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=530,Updated=TO_TIMESTAMP('2021-07-14 22:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621502
;

-- 2021-07-14T19:28:22.154Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=540,Updated=TO_TIMESTAMP('2021-07-14 22:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621503
;

-- 2021-07-14T19:28:22.300Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=550,Updated=TO_TIMESTAMP('2021-07-14 22:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621507
;

-- 2021-07-14T19:28:22.444Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=560,Updated=TO_TIMESTAMP('2021-07-14 22:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621510
;

-- 2021-07-14T19:28:22.587Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=570,Updated=TO_TIMESTAMP('2021-07-14 22:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621511
;

-- 2021-07-14T19:28:22.736Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=580,Updated=TO_TIMESTAMP('2021-07-14 22:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621517
;

-- 2021-07-14T19:28:22.881Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=590,Updated=TO_TIMESTAMP('2021-07-14 22:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621512
;

-- 2021-07-14T19:28:23.027Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=600,Updated=TO_TIMESTAMP('2021-07-14 22:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621513
;

-- 2021-07-14T19:28:23.205Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=610,Updated=TO_TIMESTAMP('2021-07-14 22:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=621514
;

-- 2021-07-14T19:28:23.351Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=620,Updated=TO_TIMESTAMP('2021-07-14 22:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=631831
;

-- 2021-07-14T19:28:23.492Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=630,Updated=TO_TIMESTAMP('2021-07-14 22:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650312
;

-- 2021-07-14T19:29:41.636Z
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2021-07-14 22:29:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575030
;

-- 2021-07-14T19:31:00.090Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@DocBaseType/X@=MRO',Updated=TO_TIMESTAMP('2021-07-14 22:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650312
;

-- 2021-07-14T19:31:33.238Z
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@DocStatus/X@!CO',Updated=TO_TIMESTAMP('2021-07-14 22:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575030
;

-- 2021-07-14T19:33:07.075Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542846,546201,TO_TIMESTAMP('2021-07-14 22:33:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','repair order fields',15,TO_TIMESTAMP('2021-07-14 22:33:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-14T19:36:18.724Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650312,0,543016,546201,587079,'F',TO_TIMESTAMP('2021-07-14 22:36:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zusammenfassung',10,0,0,TO_TIMESTAMP('2021-07-14 22:36:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-14T20:30:06.713Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575029,650313,0,543308,TO_TIMESTAMP('2021-07-14 23:30:06','YYYY-MM-DD HH24:MI:SS'),100,4000,'de.metas.servicerepair','Y','N','N','N','N','N','N','N','Zusammenfassung',TO_TIMESTAMP('2021-07-14 23:30:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-14T20:30:06.751Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650313 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-14T20:30:06.786Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579488) 
;

-- 2021-07-14T20:30:06.824Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650313
;

-- 2021-07-14T20:30:06.888Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(650313)
;

-- 2021-07-14T20:37:39.140Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2021-07-14 23:37:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543308
;

-- 2021-07-14T20:38:28.899Z
-- URL zum Konzept
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2021-07-14 23:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541563
;

-- 2021-07-14T20:38:46.352Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-14 23:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628583
;

-- 2021-07-14T20:38:53.770Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-14 23:38:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628587
;

-- 2021-07-14T20:38:56.851Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-14 23:38:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628637
;

-- 2021-07-14T20:38:59.811Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-14 23:38:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=637523
;

-- 2021-07-14T20:39:02.859Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-14 23:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628588
;

-- 2021-07-14T20:40:56.885Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@Type/X@=W & @Status/X@=CO',Updated=TO_TIMESTAMP('2021-07-14 23:40:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650313
;

-- 2021-07-14T20:41:48.074Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-07-14 23:41:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650313
;

-- 2021-07-14T20:41:53.856Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2021-07-14 23:41:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650313
;

-- 2021-07-14T20:42:49.028Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2021-07-14 23:42:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650313
;

-- 2021-07-14T20:42:54.828Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2021-07-14 23:42:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650313
;

