-- 2017-10-19T20:33:12.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Über-/ Unterzahlung',Updated=TO_TIMESTAMP('2017-10-19 20:33:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11041
;

-- 2017-10-19T20:33:20.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abschreibung',Updated=TO_TIMESTAMP('2017-10-19 20:33:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11042
;

-- 2017-10-19T20:35:14.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,263,540523,TO_TIMESTAMP('2017-10-19 20:35:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-10-19 20:35:14','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-10-19T20:35:14.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540523 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-10-19T20:35:26.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540698,540523,TO_TIMESTAMP('2017-10-19 20:35:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-10-19 20:35:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-19T20:35:51.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540698,541214,TO_TIMESTAMP('2017-10-19 20:35:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2017-10-19 20:35:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-19T20:36:06.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541214, SeqNo=10,Updated=TO_TIMESTAMP('2017-10-19 20:36:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547011
;

-- 2017-10-19T20:36:13.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541214, SeqNo=20,Updated=TO_TIMESTAMP('2017-10-19 20:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547012
;

-- 2017-10-19T20:36:17.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540908
;

-- 2017-10-19T20:36:21.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540529
;

-- 2017-10-19T20:36:25.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=540392
;

-- 2017-10-19T20:36:25.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540392
;

-- 2017-10-19T20:36:41.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541214, SeqNo=30,Updated=TO_TIMESTAMP('2017-10-19 20:36:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547014
;

-- 2017-10-19T20:36:49.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541214, SeqNo=40,Updated=TO_TIMESTAMP('2017-10-19 20:36:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547015
;

-- 2017-10-19T20:36:57.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541214, SeqNo=50,Updated=TO_TIMESTAMP('2017-10-19 20:36:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547016
;

-- 2017-10-19T20:37:14.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540909
;

-- 2017-10-19T20:37:20.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540530
;

-- 2017-10-19T20:37:23.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=540393
;

-- 2017-10-19T20:37:23.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540393
;

-- 2017-10-19T20:37:39.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541214, SeqNo=60,Updated=TO_TIMESTAMP('2017-10-19 20:37:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544482
;

-- 2017-10-19T20:37:47.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541214, SeqNo=70,Updated=TO_TIMESTAMP('2017-10-19 20:37:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544483
;

-- 2017-10-19T20:37:51.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540457
;

-- 2017-10-19T20:37:55.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540278
;

-- 2017-10-19T20:37:59.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=540198
;

-- 2017-10-19T20:37:59.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540198
;

-- 2017-10-19T20:38:07.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-10-19 20:38:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541214
;

-- 2017-10-19T20:38:37.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-10-19 20:38:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547015
;

-- 2017-10-19T20:38:38.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-10-19 20:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547016
;

-- 2017-10-19T20:46:12.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548075,0,540338,1000043,549102,'F',TO_TIMESTAMP('2017-10-19 20:46:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-10-19 20:46:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-19T20:46:22.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548074,0,540338,1000043,549103,'F',TO_TIMESTAMP('2017-10-19 20:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-10-19 20:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-19T20:46:28.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-10-19 20:46:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549102
;

-- 2017-10-19T20:47:13.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548186,0,540342,1000044,549104,'F',TO_TIMESTAMP('2017-10-19 20:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-10-19 20:47:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-19T20:47:24.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548185,0,540342,1000044,549105,'F',TO_TIMESTAMP('2017-10-19 20:47:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-10-19 20:47:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-19T20:47:31.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-10-19 20:47:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549104
;

-- 2017-10-19T20:48:10.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-10-19 20:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000488
;

-- 2017-10-19T20:48:10.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-10-19 20:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000489
;

-- 2017-10-19T20:48:10.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-10-19 20:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000490
;

-- 2017-10-19T20:48:10.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-10-19 20:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000491
;

-- 2017-10-19T20:48:10.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-10-19 20:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000487
;

-- 2017-10-19T20:48:33.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-10-19 20:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000480
;

-- 2017-10-19T20:48:33.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-10-19 20:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000481
;

-- 2017-10-19T20:48:33.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-10-19 20:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000482
;

-- 2017-10-19T20:48:33.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-10-19 20:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000483
;

-- 2017-10-19T20:48:33.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-10-19 20:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000484
;

-- 2017-10-19T20:48:33.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-10-19 20:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000485
;

-- 2017-10-19T20:48:33.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-10-19 20:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549104
;

-- 2017-10-19T20:48:51.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-10-19 20:48:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000480
;

-- 2017-10-19T20:48:53.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-10-19 20:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000481
;

-- 2017-10-19T20:48:55.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-10-19 20:48:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000482
;

-- 2017-10-19T20:48:57.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-10-19 20:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000483
;

-- 2017-10-19T20:48:59.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-10-19 20:48:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000484
;

-- 2017-10-19T20:49:00.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-10-19 20:49:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000485
;

-- 2017-10-19T20:49:02.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-10-19 20:49:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549104
;

-- 2017-10-19T20:49:05.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-10-19 20:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549105
;

-- 2017-10-19T20:49:22.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-10-19 20:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000476
;

-- 2017-10-19T20:49:24.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-10-19 20:49:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000477
;

-- 2017-10-19T20:49:26.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-10-19 20:49:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000478
;

-- 2017-10-19T20:49:28.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-10-19 20:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000479
;

-- 2017-10-19T20:49:30.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-10-19 20:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549102
;

-- 2017-10-19T20:49:35.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-10-19 20:49:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549103
;

-- 2017-10-19T20:49:56.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-10-19 20:49:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000489
;

-- 2017-10-19T20:49:58.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-10-19 20:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000491
;

-- 2017-10-19T20:49:59.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-10-19 20:49:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000490
;

-- 2017-10-19T20:50:01.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-10-19 20:50:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000487
;

-- 2017-10-19T20:50:05.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-10-19 20:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000486
;

-- 2017-10-19T20:50:07.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-10-19 20:50:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000488
;

-- 2017-10-19T20:50:27.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-10-19 20:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000479
;

-- 2017-10-19T20:50:39.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-10-19 20:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000476
;

-- 2017-10-19T20:50:39.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-10-19 20:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000477
;

-- 2017-10-19T20:50:39.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-10-19 20:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000478
;

-- 2017-10-19T20:50:39.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-10-19 20:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000479
;

-- 2017-10-19T20:50:39.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-10-19 20:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549102
;

-- 2017-10-19T20:51:00.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-10-19 20:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000479
;

-- 2017-10-19T20:51:00.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-10-19 20:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000476
;

-- 2017-10-19T20:51:00.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-10-19 20:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000477
;

-- 2017-10-19T20:51:00.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-10-19 20:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000478
;

-- 2017-10-19T20:51:45.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-10-19 20:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549102
;

-- 2017-10-19T20:51:48.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-10-19 20:51:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549103
;

-- 2017-10-19T20:52:46.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554839,0,540622,1000046,549106,'F',TO_TIMESTAMP('2017-10-19 20:52:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-10-19 20:52:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-19T20:52:55.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554838,0,540622,1000046,549107,'F',TO_TIMESTAMP('2017-10-19 20:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-10-19 20:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-19T20:53:03.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Maßeinheit',Updated=TO_TIMESTAMP('2017-10-19 20:53:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000507
;

-- 2017-10-19T20:53:20.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2017-10-19 20:53:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549106
;

-- 2017-10-19T20:53:36.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-10-19 20:53:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000492
;

-- 2017-10-19T20:53:37.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-10-19 20:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000493
;

-- 2017-10-19T20:53:39.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-10-19 20:53:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000494
;

-- 2017-10-19T20:53:40.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-10-19 20:53:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000495
;

-- 2017-10-19T20:53:42.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-10-19 20:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000496
;

-- 2017-10-19T20:53:43.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-10-19 20:53:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000497
;

-- 2017-10-19T20:53:45.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-10-19 20:53:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000498
;

-- 2017-10-19T20:53:48.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-10-19 20:53:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000499
;

-- 2017-10-19T20:53:49.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-10-19 20:53:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000500
;

-- 2017-10-19T20:53:51.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2017-10-19 20:53:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000501
;

-- 2017-10-19T20:53:53.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2017-10-19 20:53:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000502
;

-- 2017-10-19T20:53:54.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2017-10-19 20:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000503
;

-- 2017-10-19T20:53:56.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2017-10-19 20:53:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000504
;

-- 2017-10-19T20:53:58.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2017-10-19 20:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000505
;

-- 2017-10-19T20:54:00.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2017-10-19 20:54:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000506
;

-- 2017-10-19T20:54:01.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=160,Updated=TO_TIMESTAMP('2017-10-19 20:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000507
;

-- 2017-10-19T20:54:03.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2017-10-19 20:54:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000508
;

-- 2017-10-19T20:54:05.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=180,Updated=TO_TIMESTAMP('2017-10-19 20:54:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000509
;

-- 2017-10-19T20:54:10.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=190,Updated=TO_TIMESTAMP('2017-10-19 20:54:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000510
;

-- 2017-10-19T20:54:14.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=200,Updated=TO_TIMESTAMP('2017-10-19 20:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000511
;

-- 2017-10-19T20:54:16.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=210,Updated=TO_TIMESTAMP('2017-10-19 20:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549106
;

-- 2017-10-19T20:54:23.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=220,Updated=TO_TIMESTAMP('2017-10-19 20:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549107
;

-- 2017-10-19T20:54:35.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-10-19 20:54:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000046
;

-- 2017-10-19T20:54:46.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-10-19 20:54:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000045
;

-- 2017-10-19T20:54:56.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-10-19 20:54:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000044
;

-- 2017-10-19T20:55:04.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-10-19 20:55:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000043
;

-- 2017-10-19T20:59:47.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 20:59:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoicecandidate',Description='' WHERE AD_Field_ID=546902 AND AD_Language='en_US'
;

-- 2017-10-19T21:00:03.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:00:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sales Invoice',Description='' WHERE AD_Field_ID=553181 AND AD_Language='en_US'
;

-- 2017-10-19T21:00:20.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:00:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Agrregation Group' WHERE AD_Field_ID=555549 AND AD_Language='en_US'
;

-- 2017-10-19T21:00:31.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:00:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Manual',Description='',Help='' WHERE AD_Field_ID=551089 AND AD_Language='en_US'
;

-- 2017-10-19T21:00:44.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:00:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Material Tracking' WHERE AD_Field_ID=555481 AND AD_Language='en_US'
;

-- 2017-10-19T21:01:03.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:01:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Material Tracking ID' WHERE AD_Field_ID=554869 AND AD_Language='en_US'
;

-- 2017-10-19T21:01:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:01:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='SeqNo.',Description='' WHERE AD_Field_ID=554866 AND AD_Language='en_US'
;

-- 2017-10-19T21:01:29.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:01:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='DocType Invoice' WHERE AD_Field_ID=554867 AND AD_Language='en_US'
;

-- 2017-10-19T21:01:42.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:01:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoicecandidate Controller' WHERE AD_Field_ID=548750 AND AD_Language='en_US'
;

-- 2017-10-19T21:02:00.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:02:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Description' WHERE AD_Field_ID=551170 AND AD_Language='en_US'
;

-- 2017-10-19T21:02:29.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:02:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='To Clear',Description='',Help='' WHERE AD_Field_ID=548114 AND AD_Language='en_US'
;

-- 2017-10-19T21:02:37.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:02:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Order',Description='' WHERE AD_Field_ID=546904 AND AD_Language='en_US'
;

-- 2017-10-19T21:02:41.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:02:41','YYYY-MM-DD HH24:MI:SS'),Name='Sales Order' WHERE AD_Field_ID=546904 AND AD_Language='en_US'
;

-- 2017-10-19T21:02:57.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:02:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Order Date',Description='',Help='' WHERE AD_Field_ID=546897 AND AD_Language='en_US'
;

-- 2017-10-19T21:03:06.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:03:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Orderline',Description='',Help='' WHERE AD_Field_ID=546898 AND AD_Language='en_US'
;

-- 2017-10-19T21:03:20.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:03:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipment/ Receipt',Description='' WHERE AD_Field_ID=555409 AND AD_Language='en_US'
;

-- 2017-10-19T21:03:40.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:03:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Datepromised',Description='',Help='' WHERE AD_Field_ID=556919 AND AD_Language='en_US'
;

-- 2017-10-19T21:04:02.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:04:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Ship Location' WHERE AD_Field_ID=555636 AND AD_Language='en_US'
;

-- 2017-10-19T21:04:14.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:04:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Reference',Description='' WHERE AD_Field_ID=555142 AND AD_Language='en_US'
;

-- 2017-10-19T21:04:33.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:04:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipmentdate',Description='' WHERE AD_Field_ID=555410 AND AD_Language='en_US'
;

-- 2017-10-19T21:04:44.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:04:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Order Partner' WHERE AD_Field_ID=555715 AND AD_Language='en_US'
;

-- 2017-10-19T21:04:55.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:04:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Activity',Description='',Help='' WHERE AD_Field_ID=554661 AND AD_Language='en_US'
;

-- 2017-10-19T21:05:04.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:05:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Costs',Description='',Help='' WHERE AD_Field_ID=547031 AND AD_Language='en_US'
;

-- 2017-10-19T21:05:14.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:05:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product',Description='',Help='' WHERE AD_Field_ID=546906 AND AD_Language='en_US'
;

-- 2017-10-19T21:05:26.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:05:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product Category',Description='',Help='' WHERE AD_Field_ID=555851 AND AD_Language='en_US'
;

-- 2017-10-19T21:05:41.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:05:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product Type',Description='',Help='' WHERE AD_Field_ID=555849 AND AD_Language='en_US'
;

-- 2017-10-19T21:05:55.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:05:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Pricing System',Description='',Help='' WHERE AD_Field_ID=548198 AND AD_Language='en_US'
;

-- 2017-10-19T21:06:08.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:06:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Pricelist Version',Description='',Help='' WHERE AD_Field_ID=556415 AND AD_Language='en_US'
;

-- 2017-10-19T21:06:19.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:06:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Currency',Description='',Help='' WHERE AD_Field_ID=547555 AND AD_Language='en_US'
;

-- 2017-10-19T21:06:31.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:06:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Conversion type',Description='',Help='' WHERE AD_Field_ID=549712 AND AD_Language='en_US'
;

-- 2017-10-19T21:06:44.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:06:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Tax',Description='',Help='' WHERE AD_Field_ID=554662 AND AD_Language='en_US'
;

-- 2017-10-19T21:06:56.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:06:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Tax override',Description='' WHERE AD_Field_ID=555019 AND AD_Language='en_US'
;

-- 2017-10-19T21:07:08.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:07:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Tax eff.' WHERE AD_Field_ID=555020 AND AD_Language='en_US'
;

-- 2017-10-19T21:07:20.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:07:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Ordered Qty',Description='',Help='' WHERE AD_Field_ID=547551 AND AD_Language='en_US'
;

-- 2017-10-19T21:07:31.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:07:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipped Qty',Description='',Help='' WHERE AD_Field_ID=547552 AND AD_Language='en_US'
;

-- 2017-10-19T21:08:04.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:08:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Over-/ Under Qty',Description='' WHERE AD_Field_ID=554131 AND AD_Language='en_US'
;

-- 2017-10-19T21:08:18.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:08:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoicerule' WHERE AD_Field_ID=546912 AND AD_Language='en_US'
;

-- 2017-10-19T21:08:39.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:08:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Rule override',Description='',Help='' WHERE AD_Field_ID=548192 AND AD_Language='en_US'
;

-- 2017-10-19T21:08:53.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:08:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Rule eff.' WHERE AD_Field_ID=549754 AND AD_Language='en_US'
;

-- 2017-10-19T21:09:39.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:09:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Schedule',Description='',Help='' WHERE AD_Field_ID=549714 AND AD_Language='en_US'
;

-- 2017-10-19T21:09:52.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:09:52','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Schedule Status',Description='' WHERE AD_Field_ID=549713 AND AD_Language='en_US'
;

-- 2017-10-19T21:10:17.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:10:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Date Invoice from',Description='',Help='' WHERE AD_Field_ID=548972 AND AD_Language='en_US'
;

-- 2017-10-19T21:10:55.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:10:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Date Invoice from override',Description='',Help='' WHERE AD_Field_ID=548973 AND AD_Language='en_US'
;

-- 2017-10-19T21:11:07.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:11:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Date Invoice from eff.',Description='' WHERE AD_Field_ID=549735 AND AD_Language='en_US'
;

-- 2017-10-19T21:11:21.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:11:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoicedate',Description='',Help='' WHERE AD_Field_ID=547561 AND AD_Language='en_US'
;

-- 2017-10-19T21:11:37.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:11:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Accounting Date' WHERE AD_Field_ID=555397 AND AD_Language='en_US'
;

-- 2017-10-19T21:11:54.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:11:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quality Discount %' WHERE AD_Field_ID=554060 AND AD_Language='en_US'
;

-- 2017-10-19T21:12:23.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:12:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quality Discount % override' WHERE AD_Field_ID=554061 AND AD_Language='en_US'
;

-- 2017-10-19T21:12:38.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:12:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quality Discount % eff.' WHERE AD_Field_ID=553212 AND AD_Language='en_US'
;

-- 2017-10-19T21:13:00.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:13:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty with Issues',Description='' WHERE AD_Field_ID=554062 AND AD_Language='en_US'
;

-- 2017-10-19T21:13:18.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:13:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='In Dispute',Description='',Help='' WHERE AD_Field_ID=554063 AND AD_Language='en_US'
;

-- 2017-10-19T21:15:45.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:15:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty with Issues eff.',Description='' WHERE AD_Field_ID=555544 AND AD_Language='en_US'
;

-- 2017-10-19T21:15:56.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:15:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quality Note' WHERE AD_Field_ID=553926 AND AD_Language='en_US'
;

-- 2017-10-19T21:16:11.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:16:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quality Discount Reason' WHERE AD_Field_ID=554132 AND AD_Language='en_US'
;

-- 2017-10-19T21:16:24.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:16:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price',Description='',Help='' WHERE AD_Field_ID=552878 AND AD_Language='en_US'
;

-- 2017-10-19T21:16:35.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:16:35','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price override',Help='' WHERE AD_Field_ID=552879 AND AD_Language='en_US'
;

-- 2017-10-19T21:16:56.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:16:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price incl. Tax override' WHERE AD_Field_ID=555436 AND AD_Language='en_US'
;

-- 2017-10-19T21:17:09.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:17:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Discount %',Description='',Help='' WHERE AD_Field_ID=547032 AND AD_Language='en_US'
;

-- 2017-10-19T21:17:25.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:17:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Discount override %',Description='',Help='' WHERE AD_Field_ID=547033 AND AD_Language='en_US'
;

-- 2017-10-19T21:17:39.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:17:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price eff.',Help='' WHERE AD_Field_ID=547029 AND AD_Language='en_US'
;

-- 2017-10-19T21:17:51.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:17:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price eff. override',Description='',Help='' WHERE AD_Field_ID=547030 AND AD_Language='en_US'
;

-- 2017-10-19T21:18:08.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:18:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price net eff.' WHERE AD_Field_ID=555411 AND AD_Language='en_US'
;

-- 2017-10-19T21:18:21.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:18:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Name Bill Partner' WHERE AD_Field_ID=555850 AND AD_Language='en_US'
;

-- 2017-10-19T21:18:44.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:18:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='EDI Recipient',Description='' WHERE AD_Field_ID=556203 AND AD_Language='en_US'
;

-- 2017-10-19T21:18:57.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:18:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='EDI Enabled' WHERE AD_Field_ID=556235 AND AD_Language='en_US'
;

-- 2017-10-19T21:19:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:19:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Bill Partner',Description='' WHERE AD_Field_ID=546910 AND AD_Language='en_US'
;

-- 2017-10-19T21:19:20.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:19:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Location',Description='' WHERE AD_Field_ID=546911 AND AD_Language='en_US'
;

-- 2017-10-19T21:19:31.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:19:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Contact',Description='' WHERE AD_Field_ID=546909 AND AD_Language='en_US'
;

-- 2017-10-19T21:19:48.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:19:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty Invoiced',Description='',Help='' WHERE AD_Field_ID=547554 AND AD_Language='en_US'
;

-- 2017-10-19T21:19:57.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:19:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='UOM',Description='',Help='' WHERE AD_Field_ID=554200 AND AD_Language='en_US'
;

-- 2017-10-19T21:20:25.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:20:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Qty before Quality Discount' WHERE AD_Field_ID=555542 AND AD_Language='en_US'
;

-- 2017-10-19T21:20:57.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:20:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty Invoice before Quality Discount override',Description='',Help='' WHERE AD_Field_ID=548190 AND AD_Language='en_US'
;

-- 2017-10-19T21:21:22.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:21:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty to Invoice eff.',Description='' WHERE AD_Field_ID=546908 AND AD_Language='en_US'
;

-- 2017-10-19T21:21:42.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:21:42','YYYY-MM-DD HH24:MI:SS'),Name='Qty to Invoice in Price UOM',Description='' WHERE AD_Field_ID=554677 AND AD_Language='en_US'
;

-- 2017-10-19T21:21:50.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:21:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price UOM' WHERE AD_Field_ID=554361 AND AD_Language='en_US'
;

-- 2017-10-19T21:21:57.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:21:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty TU' WHERE AD_Field_ID=555434 AND AD_Language='en_US'
;

-- 2017-10-19T21:22:06.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:22:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Total of Order' WHERE AD_Field_ID=554865 AND AD_Language='en_US'
;

-- 2017-10-19T21:22:29.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:22:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Order Total without Discount' WHERE AD_Field_ID=555407 AND AD_Language='en_US'
;

-- 2017-10-19T21:22:44.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:22:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Line Netamount',Description='' WHERE AD_Field_ID=555477 AND AD_Language='en_US'
;

-- 2017-10-19T21:23:11.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:23:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Approved for Invoicing' WHERE AD_Field_ID=554872 AND AD_Language='en_US'
;

-- 2017-10-19T21:23:39.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:23:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipment/ Receipt Approval' WHERE AD_Field_ID=555480 AND AD_Language='en_US'
;

-- 2017-10-19T21:23:59.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-19 21:23:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Net Amount to Invoice',Description='' WHERE AD_Field_ID=547553 AND AD_Language='en_US'
;

-- 2017-10-20T06:01:05.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:01:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoiced Amount',Description='',Help='' WHERE AD_Field_ID=548199 AND AD_Language='en_US'
;

-- 2017-10-20T06:01:19.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:01:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Split Amount',Help='' WHERE AD_Field_ID=551679 AND AD_Language='en_US'
;

-- 2017-10-20T06:01:33.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:01:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='' WHERE AD_Field_ID=548259 AND AD_Language='en_US'
;

-- 2017-10-20T06:01:53.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:01:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Aggregation Header' WHERE AD_Field_ID=551671 AND AD_Language='en_US'
;

-- 2017-10-20T06:02:11.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:02:11','YYYY-MM-DD HH24:MI:SS'),Name='Line Aggregation',Description='',Help='
<li>Die Kandidaten gehören zu selben Rechnung (d.h. Rechnungsempfänger, Währung usw. sind gleich)</li>
<li>Die Kandidaten haben das gleiche Merkmal</li>
</ul>' WHERE AD_Field_ID=548260 AND AD_Language='en_US'
;

-- 2017-10-20T06:02:16.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:02:16','YYYY-MM-DD HH24:MI:SS'),Help='' WHERE AD_Field_ID=548260 AND AD_Language='en_US'
;

-- 2017-10-20T06:02:26.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:02:26','YYYY-MM-DD HH24:MI:SS'),Name='Header Aggregation' WHERE AD_Field_ID=551671 AND AD_Language='en_US'
;

-- 2017-10-20T06:02:48.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:02:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Agrregation Suffix',Description='',Help='' WHERE AD_Field_ID=548261 AND AD_Language='en_US'
;

-- 2017-10-20T06:03:02.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:03:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Responsible',Description='' WHERE AD_Field_ID=551579 AND AD_Language='en_US'
;

-- 2017-10-20T06:03:17.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:03:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Recompute',Description='',Help='' WHERE AD_Field_ID=546901 AND AD_Language='en_US'
;

-- 2017-10-20T06:03:26.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:03:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Error',Description='' WHERE AD_Field_ID=548749 AND AD_Language='en_US'
;

-- 2017-10-20T06:03:36.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:03:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Processing' WHERE AD_Field_ID=555869 AND AD_Language='en_US'
;

-- 2017-10-20T06:04:07.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:04:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoiced completely' WHERE AD_Field_ID=555694 AND AD_Language='en_US'
;

-- 2017-10-20T06:04:23.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:04:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoiced completely override' WHERE AD_Field_ID=555693 AND AD_Language='en_US'
;

-- 2017-10-20T06:04:46.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:04:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoiced completely eff.',Description='',Help='' WHERE AD_Field_ID=548115 AND AD_Language='en_US'
;

-- 2017-10-20T06:05:34.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:05:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Update Status',Description='',Help='' WHERE AD_Field_ID=546913 AND AD_Language='en_US'
;

-- 2017-10-20T06:05:50.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:05:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Error in Invoicing' WHERE AD_Field_ID=549596 AND AD_Language='en_US'
;

-- 2017-10-20T06:06:06.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:06:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Description Header' WHERE AD_Field_ID=551903 AND AD_Language='en_US'
;

-- 2017-10-20T06:06:18.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:06:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Bottom Description' WHERE AD_Field_ID=551904 AND AD_Language='en_US'
;

-- 2017-10-20T06:06:34.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:06:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Printed',Description='',Help='' WHERE AD_Field_ID=554837 AND AD_Language='en_US'
;

-- 2017-10-20T06:07:03.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:07:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quality Discount %' WHERE AD_Field_ID=553211 AND AD_Language='en_US'
;

-- 2017-10-20T06:07:16.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:07:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Note',Description='' WHERE AD_Field_ID=548751 AND AD_Language='en_US'
;

-- 2017-10-20T06:07:47.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:07:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Header Aggregation override' WHERE AD_Field_ID=555547 AND AD_Language='en_US'
;

-- 2017-10-20T06:08:00.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:08:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price incl. Tax' WHERE AD_Field_ID=555435 AND AD_Language='en_US'
;

-- 2017-10-20T06:08:22.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:08:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Aggregration Group' WHERE AD_Field_ID=555546 AND AD_Language='en_US'
;

-- 2017-10-20T06:08:53.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:08:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Header Aggregation Preset' WHERE AD_Field_ID=555548 AND AD_Language='en_US'
;

-- 2017-10-20T06:10:23.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:10:23','YYYY-MM-DD HH24:MI:SS'),Name='Line Net Amount' WHERE AD_Field_ID=555477 AND AD_Language='en_US'
;

-- 2017-10-20T06:13:01.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:13:01','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Candidates',Description='',Help='' WHERE AD_Tab_ID=540279 AND AD_Language='en_US'
;

-- 2017-10-20T06:13:47.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:13:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Flatrate Allocation',Description='' WHERE AD_Tab_ID=540338 AND AD_Language='en_US'
;

-- 2017-10-20T06:14:16.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:14:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoiceline Allocation',Description='',Help='' WHERE AD_Tab_ID=540342 AND AD_Language='en_US'
;

-- 2017-10-20T06:14:33.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:14:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipment/ Receipt Allocation' WHERE AD_Tab_ID=540665 AND AD_Language='en_US'
;

-- 2017-10-20T06:14:39.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:14:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Tab_ID=540622 AND AD_Language='en_US'
;

-- 2017-10-20T06:15:02.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:15:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Candidate to Clear' WHERE AD_Field_ID=548078 AND AD_Language='en_US'
;

-- 2017-10-20T06:15:18.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:15:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Flatrate Term' WHERE AD_Field_ID=548512 AND AD_Language='en_US'
;

-- 2017-10-20T06:15:48.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:15:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Flatrate Invoicing Record' WHERE AD_Field_ID=548079 AND AD_Language='en_US'
;

-- 2017-10-20T06:16:00.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:16:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Candidate',Description='' WHERE AD_Field_ID=548073 AND AD_Language='en_US'
;

-- 2017-10-20T06:16:27.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:16:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Clearing Invoice' WHERE AD_Field_ID=548077 AND AD_Language='en_US'
;

-- 2017-10-20T06:18:06.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:18:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Candidate',Description='' WHERE AD_Field_ID=548187 AND AD_Language='en_US'
;

-- 2017-10-20T06:18:21.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:18:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoiceline',Description='',Help='' WHERE AD_Field_ID=548189 AND AD_Language='en_US'
;

-- 2017-10-20T06:18:33.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:18:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty invoiced',Description='' WHERE AD_Field_ID=548194 AND AD_Language='en_US'
;

-- 2017-10-20T06:18:44.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:18:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Docstatus' WHERE AD_Field_ID=548193 AND AD_Language='en_US'
;

-- 2017-10-20T06:18:53.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:18:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='' WHERE AD_Field_ID=548745 AND AD_Language='en_US'
;

-- 2017-10-20T06:19:02.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:19:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Note',Description='',Help='' WHERE AD_Field_ID=556176 AND AD_Language='en_US'
;

-- 2017-10-20T06:19:38.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:19:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty to invoice override',Description='',Help='' WHERE AD_Field_ID=557544 AND AD_Language='en_US'
;

-- 2017-10-20T06:19:48.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:19:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price override',Help='' WHERE AD_Field_ID=557545 AND AD_Language='en_US'
;

-- 2017-10-20T06:20:55.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:20:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoiceline Allocation' WHERE AD_Field_ID=548188 AND AD_Language='en_US'
;

-- 2017-10-20T06:21:40.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:21:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Candidate',Description='' WHERE AD_Field_ID=555484 AND AD_Language='en_US'
;

-- 2017-10-20T06:21:56.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:21:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipment/ Receipt Line',Description='',Help='' WHERE AD_Field_ID=555485 AND AD_Language='en_US'
;

-- 2017-10-20T06:22:08.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:22:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty Delivered',Description='',Help='' WHERE AD_Field_ID=555488 AND AD_Language='en_US'
;

-- 2017-10-20T06:22:27.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:22:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipment/ Receipt approval' WHERE AD_Field_ID=555489 AND AD_Language='en_US'
;

-- 2017-10-20T06:22:53.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:22:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='SeqNo.',Description='',Help='' WHERE AD_Field_ID=554844 AND AD_Language='en_US'
;

-- 2017-10-20T06:23:04.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:23:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Printed' WHERE AD_Field_ID=554857 AND AD_Language='en_US'
;

-- 2017-10-20T06:23:16.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:23:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Print before' WHERE AD_Field_ID=554845 AND AD_Language='en_US'
;

-- 2017-10-20T06:23:38.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:23:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Print Details override' WHERE AD_Field_ID=554870 AND AD_Language='en_US'
;

-- 2017-10-20T06:23:49.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:23:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product',Description='',Help='' WHERE AD_Field_ID=554846 AND AD_Language='en_US'
;

-- 2017-10-20T06:24:31.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:24:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Manufacturing Order' WHERE AD_Field_ID=556430 AND AD_Language='en_US'
;

-- 2017-10-20T06:24:40.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:24:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Note',Description='',Help='' WHERE AD_Field_ID=554848 AND AD_Language='en_US'
;

-- 2017-10-20T06:24:49.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:24:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Description' WHERE AD_Field_ID=554847 AND AD_Language='en_US'
;

-- 2017-10-20T06:24:57.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:24:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=554862 AND AD_Language='en_US'
;

-- 2017-10-20T06:25:14.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:25:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Ordered Qty in Price UOM',Description='',Help='' WHERE AD_Field_ID=554852 AND AD_Language='en_US'
;

-- 2017-10-20T06:25:23.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:25:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price',Description='',Help='' WHERE AD_Field_ID=554854 AND AD_Language='en_US'
;

-- 2017-10-20T06:25:34.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:25:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price',Description='',Help='' WHERE AD_Field_ID=554855 AND AD_Language='en_US'
;

-- 2017-10-20T06:25:46.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:25:46','YYYY-MM-DD HH24:MI:SS'),Name='Price Actual' WHERE AD_Field_ID=554855 AND AD_Language='en_US'
;

-- 2017-10-20T06:25:59.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:25:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Discount %',Description='',Help='' WHERE AD_Field_ID=554856 AND AD_Language='en_US'
;

-- 2017-10-20T06:26:09.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:26:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Price UOM' WHERE AD_Field_ID=554853 AND AD_Language='en_US'
;

-- 2017-10-20T06:26:18.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:26:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty',Description='',Help='' WHERE AD_Field_ID=554850 AND AD_Language='en_US'
;

-- 2017-10-20T06:26:26.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:26:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='UOM',Description='',Help='' WHERE AD_Field_ID=554851 AND AD_Language='en_US'
;

-- 2017-10-20T06:26:35.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:26:35','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice' WHERE AD_Field_ID=554842 AND AD_Language='en_US'
;

-- 2017-10-20T06:26:45.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:26:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoiceline',Description='',Help='' WHERE AD_Field_ID=554843 AND AD_Language='en_US'
;

-- 2017-10-20T06:26:50.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:26:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=554863 AND AD_Language='en_US'
;

-- 2017-10-20T06:27:10.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:27:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Packing Instruction (TU)' WHERE AD_Field_ID=554868 AND AD_Language='en_US'
;

-- 2017-10-20T06:27:17.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:27:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty TU' WHERE AD_Field_ID=554864 AND AD_Language='en_US'
;

-- 2017-10-20T06:27:32.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:27:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Attributes',Description='' WHERE AD_Field_ID=554849 AND AD_Language='en_US'
;

-- 2017-10-20T06:27:43.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:27:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Candidate',Description='' WHERE AD_Field_ID=554861 AND AD_Language='en_US'
;

-- 2017-10-20T06:28:00.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 06:28:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=554841 AND AD_Language='en_US'
;

