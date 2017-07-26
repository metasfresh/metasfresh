-- 2017-07-26T14:40:30.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordnet',Updated=TO_TIMESTAMP('2017-07-26 14:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4266
;

-- 2017-07-26T14:41:09.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Zeigt an ob eine Zahlung bereits zugeordnet wurde', Help='', Name='Zugeordnet', PrintName='Zugeordnet',Updated=TO_TIMESTAMP('2017-07-26 14:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1508
;

-- 2017-07-26T14:41:09.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAllocated', Name='Zugeordnet', Description='Zeigt an ob eine Zahlung bereits zugeordnet wurde', Help='' WHERE AD_Element_ID=1508
;

-- 2017-07-26T14:41:09.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAllocated', Name='Zugeordnet', Description='Zeigt an ob eine Zahlung bereits zugeordnet wurde', Help='', AD_Element_ID=1508 WHERE UPPER(ColumnName)='ISALLOCATED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-26T14:41:09.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAllocated', Name='Zugeordnet', Description='Zeigt an ob eine Zahlung bereits zugeordnet wurde', Help='' WHERE AD_Element_ID=1508 AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T14:41:09.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordnet', Description='Zeigt an ob eine Zahlung bereits zugeordnet wurde', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1508) AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T14:41:09.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugeordnet', Name='Zugeordnet' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1508)
;

-- 2017-07-26T14:42:25.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abgeglichen',Updated=TO_TIMESTAMP('2017-07-26 14:42:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4040
;

-- 2017-07-26T14:43:07.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde', Name='Abgeglichen', PrintName='Abgeglichen',Updated=TO_TIMESTAMP('2017-07-26 14:43:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1105
;

-- 2017-07-26T14:43:07.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsReconciled', Name='Abgeglichen', Description='Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde', Help=NULL WHERE AD_Element_ID=1105
;

-- 2017-07-26T14:43:07.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReconciled', Name='Abgeglichen', Description='Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde', Help=NULL, AD_Element_ID=1105 WHERE UPPER(ColumnName)='ISRECONCILED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-26T14:43:07.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReconciled', Name='Abgeglichen', Description='Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde', Help=NULL WHERE AD_Element_ID=1105 AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T14:43:07.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abgeglichen', Description='Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1105) AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T14:43:07.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abgeglichen', Name='Abgeglichen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1105)
;

-- 2017-07-26T14:44:58.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-26 14:44:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541197
;

-- 2017-07-26T14:44:58.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-07-26 14:44:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541198
;

-- 2017-07-26T14:44:58.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-07-26 14:44:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541189
;

-- 2017-07-26T15:14:59.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-07-26 15:14:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=755
;

-- 2017-07-26T15:16:24.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='Y', Name='Manuelle Zuordnung', SeqNo=40,Updated=TO_TIMESTAMP('2017-07-26 15:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=755
;

-- 2017-07-26T15:23:37.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abschreibung Betrag',Updated=TO_TIMESTAMP('2017-07-26 15:23:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556330
;

-- 2017-07-26T15:23:50.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Abschreibung Betrag', Name='Abschreibung Betrag', PrintName='Abschreibung Betrag',Updated=TO_TIMESTAMP('2017-07-26 15:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542893
;

-- 2017-07-26T15:23:50.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PaymentWriteOffAmt', Name='Abschreibung Betrag', Description='Abschreibung Betrag', Help=NULL WHERE AD_Element_ID=542893
;

-- 2017-07-26T15:23:50.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PaymentWriteOffAmt', Name='Abschreibung Betrag', Description='Abschreibung Betrag', Help=NULL, AD_Element_ID=542893 WHERE UPPER(ColumnName)='PAYMENTWRITEOFFAMT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-26T15:23:50.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PaymentWriteOffAmt', Name='Abschreibung Betrag', Description='Abschreibung Betrag', Help=NULL WHERE AD_Element_ID=542893 AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T15:23:50.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abschreibung Betrag', Description='Abschreibung Betrag', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542893) AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T15:23:50.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abschreibung Betrag', Name='Abschreibung Betrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542893)
;

-- 2017-07-26T15:24:43.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 15:24:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Write-off Amount',PrintName='Write-off Amount' WHERE AD_Element_ID=542893 AND AD_Language='en_US'
;

-- 2017-07-26T15:24:43.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542893,'en_US') 
;

-- 2017-07-26T15:26:38.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vorauszahlung',Updated=TO_TIMESTAMP('2017-07-26 15:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11013
;

-- 2017-07-26T15:27:09.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Die Zahlung ist eine Vorauszahlung', Help='', Name='Vorauszahlung', PrintName='Vorauszahlung',Updated=TO_TIMESTAMP('2017-07-26 15:27:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2663
;

-- 2017-07-26T15:27:09.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPrepayment', Name='Vorauszahlung', Description='Die Zahlung ist eine Vorauszahlung', Help='' WHERE AD_Element_ID=2663
;

-- 2017-07-26T15:27:09.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPrepayment', Name='Vorauszahlung', Description='Die Zahlung ist eine Vorauszahlung', Help='', AD_Element_ID=2663 WHERE UPPER(ColumnName)='ISPREPAYMENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-26T15:27:09.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPrepayment', Name='Vorauszahlung', Description='Die Zahlung ist eine Vorauszahlung', Help='' WHERE AD_Element_ID=2663 AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T15:27:09.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vorauszahlung', Description='Die Zahlung ist eine Vorauszahlung', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2663) AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T15:27:09.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vorauszahlung', Name='Vorauszahlung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2663)
;

-- 2017-07-26T15:28:54.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4133,0,330,540064,547161,TO_TIMESTAMP('2017-07-26 15:28:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsbetrag',40,0,0,TO_TIMESTAMP('2017-07-26 15:28:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T15:29:06.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-07-26 15:29:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547161
;

-- 2017-07-26T15:29:37.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541195
;

-- 2017-07-26T15:30:35.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540049,540955,TO_TIMESTAMP('2017-07-26 15:30:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','order invoice',20,TO_TIMESTAMP('2017-07-26 15:30:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T15:30:49.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10902,0,330,540955,547162,TO_TIMESTAMP('2017-07-26 15:30:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Auftrag',10,0,0,TO_TIMESTAMP('2017-07-26 15:30:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T15:30:58.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4257,0,330,540955,547163,TO_TIMESTAMP('2017-07-26 15:30:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rechnung',20,0,0,TO_TIMESTAMP('2017-07-26 15:30:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T15:31:08.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6969,0,330,540955,547164,TO_TIMESTAMP('2017-07-26 15:31:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kosten',30,0,0,TO_TIMESTAMP('2017-07-26 15:31:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T15:31:19.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7809,0,330,540955,547165,TO_TIMESTAMP('2017-07-26 15:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kostenstelle',40,0,0,TO_TIMESTAMP('2017-07-26 15:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:23:38.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4129,0,330,540064,547166,TO_TIMESTAMP('2017-07-26 16:23:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Währung',50,0,0,TO_TIMESTAMP('2017-07-26 16:23:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:23:44.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-07-26 16:23:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547166
;

-- 2017-07-26T16:26:09.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4132,0,330,540065,547167,TO_TIMESTAMP('2017-07-26 16:26:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Belegart',80,0,0,TO_TIMESTAMP('2017-07-26 16:26:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:26:20.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10, WidgetSize='S',Updated=TO_TIMESTAMP('2017-07-26 16:26:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547167
;

-- 2017-07-26T16:26:36.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Datum', SeqNo=20,Updated=TO_TIMESTAMP('2017-07-26 16:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541192
;

-- 2017-07-26T16:26:40.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541193
;

-- 2017-07-26T16:27:43.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Nr.', SeqNo=20,Updated=TO_TIMESTAMP('2017-07-26 16:27:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541209
;

-- 2017-07-26T16:28:04.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541208
;

-- 2017-07-26T16:28:15.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-07-26 16:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541210
;

-- 2017-07-26T16:28:41.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541194
;

-- 2017-07-26T16:28:48.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541211
;

-- 2017-07-26T16:29:15.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nr.',Updated=TO_TIMESTAMP('2017-07-26 16:29:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4267
;

-- 2017-07-26T16:29:22.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Datum',Updated=TO_TIMESTAMP('2017-07-26 16:29:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4265
;

-- 2017-07-26T16:29:32.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 16:29:32','YYYY-MM-DD HH24:MI:SS'),Name='No.' WHERE AD_Field_ID=4267 AND AD_Language='en_US'
;

-- 2017-07-26T16:29:42.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 16:29:42','YYYY-MM-DD HH24:MI:SS'),Name='Date' WHERE AD_Field_ID=4265 AND AD_Language='en_US'
;

-- 2017-07-26T16:30:26.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541197
;

-- 2017-07-26T16:30:26.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541198
;

-- 2017-07-26T16:30:26.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541199
;

-- 2017-07-26T16:30:26.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541200
;

-- 2017-07-26T16:30:30.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540066
;

-- 2017-07-26T16:30:36.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540054
;

-- 2017-07-26T16:31:15.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540050,540956,TO_TIMESTAMP('2017-07-26 16:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',20,TO_TIMESTAMP('2017-07-26 16:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:31:19.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540050,540957,TO_TIMESTAMP('2017-07-26 16:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2017-07-26 16:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:31:28.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4018,0,330,540957,547168,TO_TIMESTAMP('2017-07-26 16:31:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-07-26 16:31:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:31:35.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4017,0,330,540957,547169,TO_TIMESTAMP('2017-07-26 16:31:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-07-26 16:31:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:31:57.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4933,0,330,540956,547170,TO_TIMESTAMP('2017-07-26 16:31:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungseingang',10,0,0,TO_TIMESTAMP('2017-07-26 16:31:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:32:11.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11013,0,330,540956,547171,TO_TIMESTAMP('2017-07-26 16:32:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vorauszahlung',20,0,0,TO_TIMESTAMP('2017-07-26 16:32:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:32:36.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4040,0,330,540956,547172,TO_TIMESTAMP('2017-07-26 16:32:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Abgeglichen',30,0,0,TO_TIMESTAMP('2017-07-26 16:32:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:32:57.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4266,0,330,540956,547173,TO_TIMESTAMP('2017-07-26 16:32:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zugeordnet',40,0,0,TO_TIMESTAMP('2017-07-26 16:32:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:33:16.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541201
;

-- 2017-07-26T16:33:16.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541202
;

-- 2017-07-26T16:33:16.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541203
;

-- 2017-07-26T16:33:16.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541204
;

-- 2017-07-26T16:33:16.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541205
;

-- 2017-07-26T16:33:20.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540067
;

-- 2017-07-26T16:33:23.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540055
;

-- 2017-07-26T16:33:29.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=540039
;

-- 2017-07-26T16:33:29.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540039
;

-- 2017-07-26T16:34:52.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540065, SeqNo=30,Updated=TO_TIMESTAMP('2017-07-26 16:34:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547170
;

-- 2017-07-26T16:35:00.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540065, SeqNo=40,Updated=TO_TIMESTAMP('2017-07-26 16:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547171
;

-- 2017-07-26T16:35:07.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540065, SeqNo=50,Updated=TO_TIMESTAMP('2017-07-26 16:35:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547172
;

-- 2017-07-26T16:35:13.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540065, SeqNo=60,Updated=TO_TIMESTAMP('2017-07-26 16:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547173
;

-- 2017-07-26T16:35:52.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2017-07-26 16:35:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541192
;

-- 2017-07-26T16:56:36.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-07-26 16:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541189
;

-- 2017-07-26T16:56:36.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-07-26 16:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547161
;

-- 2017-07-26T16:56:36.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-07-26 16:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547166
;

-- 2017-07-26T16:56:36.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-07-26 16:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547173
;

-- 2017-07-26T16:56:36.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-07-26 16:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547172
;

-- 2017-07-26T16:56:36.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-07-26 16:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547163
;

-- 2017-07-26T16:56:36.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-07-26 16:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547168
;

