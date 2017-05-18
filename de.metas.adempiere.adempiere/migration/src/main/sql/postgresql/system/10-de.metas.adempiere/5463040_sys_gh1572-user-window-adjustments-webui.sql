-- 2017-05-18T17:01:16.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551889,0,118,540199,544468,TO_TIMESTAMP('2017-05-18 17:01:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Systembenutzer',70,0,0,TO_TIMESTAMP('2017-05-18 17:01:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:01:48.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551886,0,118,540199,544469,TO_TIMESTAMP('2017-05-18 17:01:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Login',80,0,0,TO_TIMESTAMP('2017-05-18 17:01:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:02:13.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544360
;

-- 2017-05-18T17:02:57.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-05-18 17:02:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542483
;

-- 2017-05-18T17:04:00.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSystemUser@=Y',Updated=TO_TIMESTAMP('2017-05-18 17:04:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551886
;

-- 2017-05-18T17:04:51.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542492
;

-- 2017-05-18T17:05:30.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-05-18 17:05:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=300
;

-- 2017-05-18T17:07:58.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542418
;

-- 2017-05-18T17:15:47.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6522,0,118,540190,544470,TO_TIMESTAMP('2017-05-18 17:15:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',350,0,0,TO_TIMESTAMP('2017-05-18 17:15:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:16:49.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,301,0,118,540190,544471,TO_TIMESTAMP('2017-05-18 17:16:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',360,0,0,TO_TIMESTAMP('2017-05-18 17:16:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:16:54.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Bemerkung',Updated=TO_TIMESTAMP('2017-05-18 17:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544470
;

-- 2017-05-18T17:17:25.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4623,0,118,540190,544472,TO_TIMESTAMP('2017-05-18 17:17:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartner',370,0,0,TO_TIMESTAMP('2017-05-18 17:17:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:17:39.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4261,0,118,540190,544473,TO_TIMESTAMP('2017-05-18 17:17:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vorgesetzter',380,0,0,TO_TIMESTAMP('2017-05-18 17:17:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:18:01.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='login',Updated=TO_TIMESTAMP('2017-05-18 17:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540203
;

-- 2017-05-18T17:18:10.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542499
;

-- 2017-05-18T17:18:10.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542500
;

-- 2017-05-18T17:18:12.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542502
;

-- 2017-05-18T17:18:12.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542504
;

-- 2017-05-18T17:18:16.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540099
;

-- 2017-05-18T17:18:20.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542501
;

-- 2017-05-18T17:19:06.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551898,0,118,540203,544474,TO_TIMESTAMP('2017-05-18 17:19:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Login Fehler Datum',10,0,0,TO_TIMESTAMP('2017-05-18 17:19:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:19:31.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551895,0,118,540203,544475,TO_TIMESTAMP('2017-05-18 17:19:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anzahl Login Fehlversuche',20,0,0,TO_TIMESTAMP('2017-05-18 17:19:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:19:46.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Datum Login Fehlversuche',Updated=TO_TIMESTAMP('2017-05-18 17:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544474
;

-- 2017-05-18T17:20:08.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551896,0,118,540203,544476,TO_TIMESTAMP('2017-05-18 17:20:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Login gesperrt',30,0,0,TO_TIMESTAMP('2017-05-18 17:20:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:20:29.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551897,0,118,540203,544477,TO_TIMESTAMP('2017-05-18 17:20:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gesperrt von IP',40,0,0,TO_TIMESTAMP('2017-05-18 17:20:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:20:47.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551899,0,118,540203,544478,TO_TIMESTAMP('2017-05-18 17:20:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Login freigeben',50,0,0,TO_TIMESTAMP('2017-05-18 17:20:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:21:02.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='org',Updated=TO_TIMESTAMP('2017-05-18 17:21:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540202
;

-- 2017-05-18T17:21:07.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542495
;

-- 2017-05-18T17:21:07.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542496
;

-- 2017-05-18T17:21:07.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542497
;

-- 2017-05-18T17:21:07.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542498
;

-- 2017-05-18T17:21:07.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542505
;

-- 2017-05-18T17:21:22.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2001,0,118,540202,544479,TO_TIMESTAMP('2017-05-18 17:21:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-05-18 17:21:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:21:30.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,309,0,118,540202,544480,TO_TIMESTAMP('2017-05-18 17:21:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-05-18 17:21:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:22:02.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,6516,0,540126,544472,TO_TIMESTAMP('2017-05-18 17:22:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-18 17:22:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-18T17:22:10.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-18 17:22:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544470
;

-- 2017-05-18T17:22:11.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-18 17:22:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544471
;

-- 2017-05-18T17:22:11.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-18 17:22:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544472
;

-- 2017-05-18T17:22:13.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-18 17:22:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544473
;

-- 2017-05-18T17:22:34.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542380
;

