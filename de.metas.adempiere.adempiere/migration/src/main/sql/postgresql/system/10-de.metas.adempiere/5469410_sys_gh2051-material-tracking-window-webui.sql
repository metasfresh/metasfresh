-- 2017-08-15T16:24:03.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-08-15 16:24:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540159
;

-- 2017-08-15T16:24:20.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554692,0,540610,540159,547280,TO_TIMESTAMP('2017-08-15 16:24:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-08-15 16:24:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-15T16:24:57.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540610,540408,TO_TIMESTAMP('2017-08-15 16:24:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-08-15 16:24:57','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-08-15T16:24:57.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540408 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-15T16:25:00.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540548,540408,TO_TIMESTAMP('2017-08-15 16:25:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-15 16:25:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-15T16:25:15.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540548, SeqNo=10,Updated=TO_TIMESTAMP('2017-08-15 16:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540158
;

-- 2017-08-15T16:25:20.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2017-08-15 16:25:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540159
;

-- 2017-08-15T16:25:39.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540548, SeqNo=20,Updated=TO_TIMESTAMP('2017-08-15 16:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540157
;

-- 2017-08-15T16:26:58.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542022
;

-- 2017-08-15T16:27:00.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542023
;

-- 2017-08-15T16:27:04.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540157
;

-- 2017-08-15T16:27:15.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', UIStyle='primary',Updated=TO_TIMESTAMP('2017-08-15 16:27:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540158
;

-- 2017-08-15T16:27:37.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554701,0,540610,540158,547281,TO_TIMESTAMP('2017-08-15 16:27:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',40,0,0,TO_TIMESTAMP('2017-08-15 16:27:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-15T16:27:39.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-15 16:27:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542024
;

-- 2017-08-15T16:27:39.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-15 16:27:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542025
;

-- 2017-08-15T16:27:39.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-15 16:27:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542026
;

-- 2017-08-15T16:27:41.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-15 16:27:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547281
;

-- 2017-08-15T16:31:36.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547281
;

-- 2017-08-15T16:31:50.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540101,540970,TO_TIMESTAMP('2017-08-15 16:31:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2017-08-15 16:31:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-15T16:32:03.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554701,0,540610,540970,547282,TO_TIMESTAMP('2017-08-15 16:32:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-08-15 16:32:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-15T16:47:39.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Material ID',Updated=TO_TIMESTAMP('2017-08-15 16:47:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554699
;

-- 2017-08-15T16:47:58.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:47:58','YYYY-MM-DD HH24:MI:SS'),Name='Lot No.' WHERE AD_Field_ID=554699 AND AD_Language='en_US'
;

-- 2017-08-15T16:48:09.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:48:09','YYYY-MM-DD HH24:MI:SS'),Name='Allotment' WHERE AD_Field_ID=554754 AND AD_Language='en_US'
;

-- 2017-08-15T16:48:33.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:48:33','YYYY-MM-DD HH24:MI:SS'),Name='Sales Representative' WHERE AD_Field_ID=554751 AND AD_Language='en_US'
;

-- 2017-08-15T16:48:45.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:48:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Season' WHERE AD_Field_ID=556347 AND AD_Language='en_US'
;

-- 2017-08-15T16:48:55.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:48:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product' WHERE AD_Field_ID=554696 AND AD_Language='en_US'
;

-- 2017-08-15T16:49:16.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:49:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Business Partner' WHERE AD_Field_ID=554697 AND AD_Language='en_US'
;

-- 2017-08-15T16:49:46.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:49:46','YYYY-MM-DD HH24:MI:SS'),Name='Quality Inspection Version' WHERE AD_Field_ID=554790 AND AD_Language='en_US'
;

-- 2017-08-15T16:50:03.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:50:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Valid from' WHERE AD_Field_ID=554705 AND AD_Language='en_US'
;

-- 2017-08-15T16:50:22.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:50:22','YYYY-MM-DD HH24:MI:SS'),Name='Valid to' WHERE AD_Field_ID=554706 AND AD_Language='en_US'
;

-- 2017-08-15T16:50:24.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:50:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=554706 AND AD_Language='en_US'
;

-- 2017-08-15T16:50:59.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:50:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Description' WHERE AD_Field_ID=554701 AND AD_Language='en_US'
;

-- 2017-08-15T16:51:16.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:51:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Processed',Description='' WHERE AD_Field_ID=554700 AND AD_Language='en_US'
;

-- 2017-08-15T16:51:38.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:51:38','YYYY-MM-DD HH24:MI:SS'),Name='Quantity Received',Description='' WHERE AD_Field_ID=554761 AND AD_Language='en_US'
;

-- 2017-08-15T16:51:53.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:51:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quantity Issued' WHERE AD_Field_ID=556322 AND AD_Language='en_US'
;

-- 2017-08-15T16:52:50.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:52:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quality Inspections Qty',Description='The Quantity of Quality Inspections' WHERE AD_Field_ID=556380 AND AD_Language='en_US'
;

-- 2017-08-15T16:53:52.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 16:53:52','YYYY-MM-DD HH24:MI:SS'),Name='Attribute Value' WHERE AD_Field_ID=554704 AND AD_Language='en_US'
;

-- 2017-08-15T17:01:44.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:01:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Received' WHERE AD_Tab_ID=540705 AND AD_Language='en_US'
;

-- 2017-08-15T17:02:03.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:02:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quality Inspection' WHERE AD_Tab_ID=540611 AND AD_Language='en_US'
;

-- 2017-08-15T17:04:02.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:04:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Stock Removal' WHERE AD_Tab_ID=540612 AND AD_Language='en_US'
;

-- 2017-08-15T17:05:00.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:05:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Referenced Documents' WHERE AD_Tab_ID=540613 AND AD_Language='en_US'
;

-- 2017-08-15T17:05:15.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:05:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Handling Units' WHERE AD_Tab_ID=540702 AND AD_Language='en_US'
;

-- 2017-08-15T17:05:22.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Handling Units',Updated=TO_TIMESTAMP('2017-08-15 17:05:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540702
;

-- 2017-08-15T17:10:23.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:10:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Document No.' WHERE AD_Field_ID=556338 AND AD_Language='en_US'
;

-- 2017-08-15T17:10:42.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:10:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Movement Date',Description='',Help='' WHERE AD_Field_ID=556339 AND AD_Language='en_US'
;

-- 2017-08-15T17:11:54.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:11:54','YYYY-MM-DD HH24:MI:SS'),Name='Quantity Received' WHERE AD_Field_ID=556342 AND AD_Language='en_US'
;

-- 2017-08-15T17:12:09.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:12:09','YYYY-MM-DD HH24:MI:SS'),Name='Quantity Issued' WHERE AD_Field_ID=556340 AND AD_Language='en_US'
;

-- 2017-08-15T17:13:23.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-08-15 17:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556335
;

-- 2017-08-15T17:13:23.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-08-15 17:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556336
;

-- 2017-08-15T17:13:23.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-08-15 17:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556337
;

-- 2017-08-15T17:13:23.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-08-15 17:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556338
;

-- 2017-08-15T17:13:23.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-08-15 17:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556339
;

-- 2017-08-15T17:13:23.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-08-15 17:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556340
;

-- 2017-08-15T17:13:23.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-08-15 17:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556342
;

-- 2017-08-15T17:13:23.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-08-15 17:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556333
;

-- 2017-08-15T17:13:52.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:13:52','YYYY-MM-DD HH24:MI:SS'),Name='Document No.' WHERE AD_Field_ID=556323 AND AD_Language='en_US'
;

-- 2017-08-15T17:14:10.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:14:10','YYYY-MM-DD HH24:MI:SS'),Name='Movement Date',Description='',Help='' WHERE AD_Field_ID=556324 AND AD_Language='en_US'
;

-- 2017-08-15T17:14:55.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:14:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quantity Issued' WHERE AD_Field_ID=556327 AND AD_Language='en_US'
;

-- 2017-08-15T17:16:52.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:16:52','YYYY-MM-DD HH24:MI:SS'),Name='Issue' WHERE AD_Tab_ID=540612 AND AD_Language='en_US'
;

-- 2017-08-15T17:17:47.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:17:47','YYYY-MM-DD HH24:MI:SS'),Name='Document No.' WHERE AD_Field_ID=556325 AND AD_Language='en_US'
;

-- 2017-08-15T17:18:27.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:18:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Movement Date',Description='',Help='' WHERE AD_Field_ID=556326 AND AD_Language='en_US'
;

-- 2017-08-15T17:18:56.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:18:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quantity Issued' WHERE AD_Field_ID=556328 AND AD_Language='en_US'
;

-- 2017-08-15T17:20:02.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:20:02','YYYY-MM-DD HH24:MI:SS'),Name='Lot No.',Description='',Help='' WHERE AD_Field_ID=556279 AND AD_Language='en_US'
;

-- 2017-08-15T17:20:24.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:20:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Status' WHERE AD_Field_ID=556281 AND AD_Language='en_US'
;

-- 2017-08-15T17:20:34.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:20:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product' WHERE AD_Field_ID=556283 AND AD_Language='en_US'
;

-- 2017-08-15T17:20:48.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:20:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Locator',Description='',Help='' WHERE AD_Field_ID=556282 AND AD_Language='en_US'
;

-- 2017-08-15T17:21:08.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:21:08','YYYY-MM-DD HH24:MI:SS'),Name='Quantity',Description='',Help='' WHERE AD_Field_ID=556284 AND AD_Language='en_US'
;

-- 2017-08-15T17:21:11.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:21:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=556284 AND AD_Language='en_US'
;

-- 2017-08-15T17:21:29.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:21:29','YYYY-MM-DD HH24:MI:SS'),Name='UOM',Description='',Help='' WHERE AD_Field_ID=556285 AND AD_Language='en_US'
;

-- 2017-08-15T17:21:32.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:21:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=556285 AND AD_Language='en_US'
;

-- 2017-08-15T17:24:29.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:24:29','YYYY-MM-DD HH24:MI:SS'),Name='Issue Document No.' WHERE AD_Field_ID=556370 AND AD_Language='en_US'
;

-- 2017-08-15T17:24:49.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:24:49','YYYY-MM-DD HH24:MI:SS'),Name='Receipt Document No.' WHERE AD_Field_ID=556370 AND AD_Language='en_US'
;

-- 2017-08-15T17:24:59.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:24:59','YYYY-MM-DD HH24:MI:SS'),Name='Issue Document No.' WHERE AD_Field_ID=556370 AND AD_Language='nl_NL'
;

-- 2017-08-15T17:25:00.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:25:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=556370 AND AD_Language='en_US'
;

-- 2017-08-15T17:25:01.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:25:01','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=556370 AND AD_Language='nl_NL'
;

-- 2017-08-15T17:26:10.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:26:10','YYYY-MM-DD HH24:MI:SS'),Name='Empf. aus Prod.-Auftrag' WHERE AD_Field_ID=556370 AND AD_Language='nl_NL'
;

-- 2017-08-15T17:26:26.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-15 17:26:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Issue Document No.' WHERE AD_Field_ID=556371 AND AD_Language='en_US'
;

