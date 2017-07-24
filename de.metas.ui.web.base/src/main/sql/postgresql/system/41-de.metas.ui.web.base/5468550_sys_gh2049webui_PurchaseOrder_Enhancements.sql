-- 2017-07-24T15:24:50.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 15:24:50','YYYY-MM-DD HH24:MI:SS'),Name='No.' WHERE AD_Field_ID=3424 AND AD_Language='en_US'
;

-- 2017-07-24T15:25:04.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 15:25:04','YYYY-MM-DD HH24:MI:SS'),Name='Date' WHERE AD_Field_ID=3435 AND AD_Language='en_US'
;

-- 2017-07-24T15:25:24.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 15:25:24','YYYY-MM-DD HH24:MI:SS'),Name='Reference' WHERE AD_Field_ID=3455 AND AD_Language='en_US'
;

-- 2017-07-24T15:25:53.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540076, SeqNo=40,Updated=TO_TIMESTAMP('2017-07-24 15:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541283
;

-- 2017-07-24T15:26:33.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 15:26:33','YYYY-MM-DD HH24:MI:SS'),Name='Document Type' WHERE AD_Field_ID=3428 AND AD_Language='en_US'
;

-- 2017-07-24T15:28:12.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,3424,0,294,540073,547078,TO_TIMESTAMP('2017-07-24 15:28:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beleg Nr.',20,0,0,TO_TIMESTAMP('2017-07-24 15:28:12','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2017-07-24T15:28:18.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540076, SeqNo=50,Updated=TO_TIMESTAMP('2017-07-24 15:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541284
;

-- 2017-07-24T15:40:11.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-24 15:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541282
;

-- 2017-07-24T15:40:33.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3455,0,294,540073,547079,TO_TIMESTAMP('2017-07-24 15:40:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Referenz',40,0,0,TO_TIMESTAMP('2017-07-24 15:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T15:41:06.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541285
;

-- 2017-07-24T15:41:13.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-24 15:41:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541286
;

-- 2017-07-24T15:41:22.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3422,0,294,540074,547080,TO_TIMESTAMP('2017-07-24 15:41:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-07-24 15:41:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T15:43:01.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541290
;

-- 2017-07-24T15:43:11.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-24 15:43:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540076
;

-- 2017-07-24T15:43:15.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-24 15:43:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540074
;

-- 2017-07-24T15:44:01.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2017-07-24 15:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540075
;

-- 2017-07-24T15:47:48.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3450,0,294,540075,547081,TO_TIMESTAMP('2017-07-24 15:47:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Summe Gesamt',30,0,0,TO_TIMESTAMP('2017-07-24 15:47:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T15:50:37.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541253
;

-- 2017-07-24T15:51:45.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 15:51:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Pricing System' WHERE AD_Field_ID=543372 AND AD_Language='en_US'
;

-- 2017-07-24T15:52:18.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 15:52:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Lot No. Date' WHERE AD_Field_ID=557472 AND AD_Language='en_US'
;

-- 2017-07-24T15:54:30.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 15:54:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Billing Address' WHERE AD_Field_ID=547267 AND AD_Language='en_US'
;

-- 2017-07-24T15:55:13.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 15:55:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Delivery Address' WHERE AD_Field_ID=547270 AND AD_Language='en_US'
;

-- 2017-07-24T16:27:56.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-24 16:27:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541802
;

-- 2017-07-24T16:28:49.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-24 16:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541280
;

-- 2017-07-24T16:29:02.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540056,540926,TO_TIMESTAMP('2017-07-24 16:29:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','dropship',109,TO_TIMESTAMP('2017-07-24 16:29:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T16:29:06.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-07-24 16:29:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540926
;

-- 2017-07-24T16:49:22.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540926, SeqNo=10,Updated=TO_TIMESTAMP('2017-07-24 16:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541229
;

-- 2017-07-24T16:50:14.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540926, SeqNo=20,Updated=TO_TIMESTAMP('2017-07-24 16:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541230
;

-- 2017-07-24T16:50:28.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-24 16:50:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540926
;

-- 2017-07-24T16:50:31.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-24 16:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540075
;

-- 2017-07-24T16:50:38.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-24 16:50:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540069
;

-- 2017-07-24T16:51:23.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2017-07-24 16:51:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541229
;

-- 2017-07-24T16:51:24.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2017-07-24 16:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541230
;

-- 2017-07-24T16:56:37.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540058,540927,TO_TIMESTAMP('2017-07-24 16:56:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',20,TO_TIMESTAMP('2017-07-24 16:56:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T16:56:43.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540058,540928,TO_TIMESTAMP('2017-07-24 16:56:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','handling unit',30,TO_TIMESTAMP('2017-07-24 16:56:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T16:56:47.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540058,540929,TO_TIMESTAMP('2017-07-24 16:56:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','pricing',40,TO_TIMESTAMP('2017-07-24 16:56:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T16:56:51.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540058,540930,TO_TIMESTAMP('2017-07-24 16:56:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','rest',50,TO_TIMESTAMP('2017-07-24 16:56:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:00:24.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3403,0,293,540927,547082,TO_TIMESTAMP('2017-07-24 17:00:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeile Nr.',10,0,0,TO_TIMESTAMP('2017-07-24 17:00:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:07:50.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3408,0,293,540927,547083,TO_TIMESTAMP('2017-07-24 17:07:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferung von',20,0,0,TO_TIMESTAMP('2017-07-24 17:07:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:08:03.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3386,0,293,540927,547084,TO_TIMESTAMP('2017-07-24 17:08:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produkt',30,0,0,TO_TIMESTAMP('2017-07-24 17:08:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:08:16.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6535,0,293,540927,547085,TO_TIMESTAMP('2017-07-24 17:08:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Merkmale',40,0,0,TO_TIMESTAMP('2017-07-24 17:08:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:08:24.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10826,0,293,540927,547086,TO_TIMESTAMP('2017-07-24 17:08:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Menge',50,0,0,TO_TIMESTAMP('2017-07-24 17:08:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:08:44.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3387,0,293,540927,547087,TO_TIMESTAMP('2017-07-24 17:08:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Maßeinheit',60,0,0,TO_TIMESTAMP('2017-07-24 17:08:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:09:17.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554373,0,293,540927,547088,TO_TIMESTAMP('2017-07-24 17:09:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preisenheit',70,0,0,TO_TIMESTAMP('2017-07-24 17:09:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:09:41.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554674,0,293,540927,547089,TO_TIMESTAMP('2017-07-24 17:09:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Menge in Preisenheit',80,0,0,TO_TIMESTAMP('2017-07-24 17:09:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:10:32.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553021,0,293,540928,547090,TO_TIMESTAMP('2017-07-24 17:10:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gebinde',10,0,0,TO_TIMESTAMP('2017-07-24 17:10:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:10:52.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554024,0,293,540928,547091,TO_TIMESTAMP('2017-07-24 17:10:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gebindemenge',20,0,0,TO_TIMESTAMP('2017-07-24 17:10:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:11:26.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10827,0,293,540929,547092,TO_TIMESTAMP('2017-07-24 17:11:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preis',10,0,0,TO_TIMESTAMP('2017-07-24 17:11:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:20:12.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3396,0,293,540929,547093,TO_TIMESTAMP('2017-07-24 17:20:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Einzelpreis',20,0,0,TO_TIMESTAMP('2017-07-24 17:20:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:22:05.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3395,0,293,540929,547094,TO_TIMESTAMP('2017-07-24 17:22:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Auszeichnungspreis',30,0,0,TO_TIMESTAMP('2017-07-24 17:22:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:22:37.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3383,0,293,540929,547095,TO_TIMESTAMP('2017-07-24 17:22:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rabat %',40,0,0,TO_TIMESTAMP('2017-07-24 17:22:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:22:47.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3397,0,293,540929,547096,TO_TIMESTAMP('2017-07-24 17:22:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Steuer',50,0,0,TO_TIMESTAMP('2017-07-24 17:22:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:23:53.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3416,0,293,540929,547097,TO_TIMESTAMP('2017-07-24 17:23:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeilenneto',60,0,0,TO_TIMESTAMP('2017-07-24 17:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:26:36.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3398,0,293,540930,547098,TO_TIMESTAMP('2017-07-24 17:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-07-24 17:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:26:56.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541260
;

-- 2017-07-24T17:26:56.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541265
;

-- 2017-07-24T17:26:56.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541262
;

-- 2017-07-24T17:26:56.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541269
;

-- 2017-07-24T17:26:56.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541266
;

-- 2017-07-24T17:26:56.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541268
;

-- 2017-07-24T17:26:56.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541264
;

-- 2017-07-24T17:26:56.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541261
;

-- 2017-07-24T17:27:03.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541263
;

-- 2017-07-24T17:27:03.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541267
;

-- 2017-07-24T17:27:03.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541293
;

-- 2017-07-24T17:27:03.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541294
;

-- 2017-07-24T17:27:03.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541295
;

-- 2017-07-24T17:27:03.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541296
;

-- 2017-07-24T17:27:09.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540070
;

-- 2017-07-24T17:27:13.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-24 17:27:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540927
;

-- 2017-07-24T17:27:16.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-24 17:27:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540928
;

-- 2017-07-24T17:27:19.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-24 17:27:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540929
;

-- 2017-07-24T17:27:23.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-24 17:27:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540930
;

-- 2017-07-24T17:30:00.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='primary',Updated=TO_TIMESTAMP('2017-07-24 17:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540075
;

-- 2017-07-24T17:30:11.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540056,540931,TO_TIMESTAMP('2017-07-24 17:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','adv-status',50,TO_TIMESTAMP('2017-07-24 17:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:30:27.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556290,0,294,540931,547099,TO_TIMESTAMP('2017-07-24 17:30:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Invoice Status',10,0,0,TO_TIMESTAMP('2017-07-24 17:30:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:30:37.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556291,0,294,540931,547100,TO_TIMESTAMP('2017-07-24 17:30:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Delivery Status',20,0,0,TO_TIMESTAMP('2017-07-24 17:30:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:30:44.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-24 17:30:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547099
;

-- 2017-07-24T17:30:46.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-24 17:30:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547100
;

-- 2017-07-24T17:30:58.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540056,540932,TO_TIMESTAMP('2017-07-24 17:30:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','adv-address',60,TO_TIMESTAMP('2017-07-24 17:30:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:32:17.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547266,0,294,540932,547101,TO_TIMESTAMP('2017-07-24 17:32:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anschrift-Text',10,0,0,TO_TIMESTAMP('2017-07-24 17:32:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:32:31.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547267,0,294,540932,547102,TO_TIMESTAMP('2017-07-24 17:32:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Abw. Rechnungsadresse',20,0,0,TO_TIMESTAMP('2017-07-24 17:32:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:33:00.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,55414,0,294,540932,547103,TO_TIMESTAMP('2017-07-24 17:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferadresse',30,0,0,TO_TIMESTAMP('2017-07-24 17:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:33:09.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-24 17:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547101
;

-- 2017-07-24T17:33:10.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-24 17:33:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547102
;

-- 2017-07-24T17:33:11.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-24 17:33:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547103
;

-- 2017-07-24T17:33:33.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540056,540933,TO_TIMESTAMP('2017-07-24 17:33:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','adv-rest',70,TO_TIMESTAMP('2017-07-24 17:33:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:34:45.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553947,0,294,540933,547104,TO_TIMESTAMP('2017-07-24 17:34:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bereitstellungsdatum',10,0,0,TO_TIMESTAMP('2017-07-24 17:34:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:34:53.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540073, SeqNo=50,Updated=TO_TIMESTAMP('2017-07-24 17:34:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547104
;

-- 2017-07-24T17:38:47.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='main',Updated=TO_TIMESTAMP('2017-07-24 17:38:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540073
;

-- 2017-07-24T17:38:51.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='dates',Updated=TO_TIMESTAMP('2017-07-24 17:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540076
;

-- 2017-07-24T17:39:24.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540076, SeqNo=60,Updated=TO_TIMESTAMP('2017-07-24 17:39:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547104
;

-- 2017-07-24T17:40:28.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-07-24 17:40:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547104
;

-- 2017-07-24T17:41:32.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2017-07-24 17:41:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541221
;

-- 2017-07-24T17:41:35.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541221
;

-- 2017-07-24T17:46:04.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3442,0,294,540933,547105,TO_TIMESTAMP('2017-07-24 17:46:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rechnungsstellung',10,0,0,TO_TIMESTAMP('2017-07-24 17:46:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:46:22.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3448,0,294,540933,547106,TO_TIMESTAMP('2017-07-24 17:46:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Priorität',20,0,0,TO_TIMESTAMP('2017-07-24 17:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:46:51.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3444,0,294,540933,547107,TO_TIMESTAMP('2017-07-24 17:46:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferung',30,0,0,TO_TIMESTAMP('2017-07-24 17:46:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:51:32.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3464,0,294,540933,547108,TO_TIMESTAMP('2017-07-24 17:51:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Frachtkostenberechnung',40,0,0,TO_TIMESTAMP('2017-07-24 17:51:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:51:51.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3467,0,294,540933,547109,TO_TIMESTAMP('2017-07-24 17:51:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rabatte drucken',50,0,0,TO_TIMESTAMP('2017-07-24 17:51:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:52:10.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3466,0,294,540933,547110,TO_TIMESTAMP('2017-07-24 17:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsweise',60,0,0,TO_TIMESTAMP('2017-07-24 17:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:52:42.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3457,0,294,540933,547111,TO_TIMESTAMP('2017-07-24 17:52:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kostenstelle',70,0,0,TO_TIMESTAMP('2017-07-24 17:52:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:53:24.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3449,0,294,540933,547112,TO_TIMESTAMP('2017-07-24 17:53:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Summe Zeilen',80,0,0,TO_TIMESTAMP('2017-07-24 17:53:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T17:54:12.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6506,0,294,540933,547113,TO_TIMESTAMP('2017-07-24 17:54:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Positionen Kopieren',90,0,0,TO_TIMESTAMP('2017-07-24 17:54:12','YYYY-MM-DD HH24:MI:SS'),100)
;

