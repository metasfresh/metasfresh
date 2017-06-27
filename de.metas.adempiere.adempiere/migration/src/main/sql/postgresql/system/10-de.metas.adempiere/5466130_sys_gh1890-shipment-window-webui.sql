-- 2017-06-25T10:43:06.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,257,540326,TO_TIMESTAMP('2017-06-25 10:43:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-25 10:43:06','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-06-25T10:43:06.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540326 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-25T10:43:59.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,257,1000027,546173,TO_TIMESTAMP('2017-06-25 10:43:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Streckengeschäft',20,0,0,TO_TIMESTAMP('2017-06-25 10:43:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T10:44:02.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-06-25 10:44:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000215
;

-- 2017-06-25T10:44:09.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=55419,Updated=TO_TIMESTAMP('2017-06-25 10:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546173
;

-- 2017-06-25T10:45:21.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,55429,0,257,1000027,546174,TO_TIMESTAMP('2017-06-25 10:45:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferempfänger',30,0,0,TO_TIMESTAMP('2017-06-25 10:45:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T10:46:04.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,55430,0,540158,546174,TO_TIMESTAMP('2017-06-25 10:46:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-25 10:46:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T10:46:29.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,55431,0,540159,546174,TO_TIMESTAMP('2017-06-25 10:46:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-25 10:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T10:50:48.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000216
;

-- 2017-06-25T10:51:27.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=1000017
;

-- 2017-06-25T10:51:30.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=1000018
;

-- 2017-06-25T10:51:33.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000217
;

-- 2017-06-25T10:51:37.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=1000028
;

-- 2017-06-25T10:52:00.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='default',Updated=TO_TIMESTAMP('2017-06-25 10:52:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000027
;

-- 2017-06-25T10:52:07.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit',Updated=TO_TIMESTAMP('2017-06-25 10:52:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540128
;

-- 2017-06-25T10:52:41.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-25 10:52:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541803
;

-- 2017-06-25T10:52:42.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-25 10:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541804
;

-- 2017-06-26T16:33:09.517
-- mk: Added manually after Rollout Fail because of missing UI Elelemtgroup
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) 
SELECT 0,0,540446,540128,TO_TIMESTAMP('2017-06-26 16:33:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2017-06-26 16:33:09','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from AD_UI_ElementGroup where AD_UI_ElementGroup_ID=540128)
;

-- 2017-06-25T10:52:51.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2017-06-25 10:52:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540128
;

-- 2017-06-25T10:53:01.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540446,540326,TO_TIMESTAMP('2017-06-25 10:53:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-25 10:53:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T10:53:15.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540446, SeqNo=10,Updated=TO_TIMESTAMP('2017-06-25 10:53:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540128
;

-- 2017-06-25T10:54:02.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2700,0,257,540019,546175,TO_TIMESTAMP('2017-06-25 10:54:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',30,0,0,TO_TIMESTAMP('2017-06-25 10:54:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T10:56:00.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Datum Bereitstellung',Updated=TO_TIMESTAMP('2017-06-25 10:56:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540616
;

-- 2017-06-25T10:57:23.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Lieferdatum',Updated=TO_TIMESTAMP('2017-06-25 10:57:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000219
;

-- 2017-06-25T10:57:28.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Auftragsdatum',Updated=TO_TIMESTAMP('2017-06-25 10:57:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000220
;

-- 2017-06-25T10:57:35.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Bereitstellungsdatum',Updated=TO_TIMESTAMP('2017-06-25 10:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540616
;

-- 2017-06-25T10:58:05.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='dates',Updated=TO_TIMESTAMP('2017-06-25 10:58:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000029
;

-- 2017-06-25T10:58:10.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='org',Updated=TO_TIMESTAMP('2017-06-25 10:58:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540019
;

-- 2017-06-25T10:58:20.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2017-06-25 10:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540618
;

-- 2017-06-25T10:59:21.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bewegungsdatum',Updated=TO_TIMESTAMP('2017-06-25 10:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2706
;

-- 2017-06-25T10:59:47.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Bewegungsdatum', PrintName='Bewegungsdatum',Updated=TO_TIMESTAMP('2017-06-25 10:59:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1037
;

-- 2017-06-25T10:59:47.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MovementDate', Name='Bewegungsdatum', Description='Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewqegung.' WHERE AD_Element_ID=1037
;

-- 2017-06-25T10:59:47.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MovementDate', Name='Bewegungsdatum', Description='Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewqegung.', AD_Element_ID=1037 WHERE UPPER(ColumnName)='MOVEMENTDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-25T10:59:47.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MovementDate', Name='Bewegungsdatum', Description='Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewqegung.' WHERE AD_Element_ID=1037 AND IsCentrallyMaintained='Y'
;

-- 2017-06-25T10:59:47.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bewegungsdatum', Description='Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewqegung.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1037) AND IsCentrallyMaintained='Y'
;

-- 2017-06-25T10:59:47.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bewegungsdatum', Name='Bewegungsdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1037)
;

-- 2017-06-25T11:00:16.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommissionierdatum',Updated=TO_TIMESTAMP('2017-06-25 11:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6877
;

-- 2017-06-25T11:00:27.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Name='Kommissionierdatum',Updated=TO_TIMESTAMP('2017-06-25 11:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=9334
;

-- 2017-06-25T11:00:27.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommissionierdatum', Description='Date/Time when picked for Shipment', Help=NULL WHERE AD_Column_ID=9334 AND IsCentrallyMaintained='Y'
;

-- 2017-06-25T11:00:45.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Kommissionierdatum', PrintName='Kommissionierdatum',Updated=TO_TIMESTAMP('2017-06-25 11:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2117
;

-- 2017-06-25T11:00:45.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PickDate', Name='Kommissionierdatum', Description='Datum/Zeit der Kommissionierung für die Lieferung', Help=NULL WHERE AD_Element_ID=2117
;

-- 2017-06-25T11:00:45.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickDate', Name='Kommissionierdatum', Description='Datum/Zeit der Kommissionierung für die Lieferung', Help=NULL, AD_Element_ID=2117 WHERE UPPER(ColumnName)='PICKDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-25T11:00:45.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickDate', Name='Kommissionierdatum', Description='Datum/Zeit der Kommissionierung für die Lieferung', Help=NULL WHERE AD_Element_ID=2117 AND IsCentrallyMaintained='Y'
;

-- 2017-06-25T11:00:45.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommissionierdatum', Description='Datum/Zeit der Kommissionierung für die Lieferung', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2117) AND IsCentrallyMaintained='Y'
;

-- 2017-06-25T11:00:45.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kommissionierdatum', Name='Kommissionierdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2117)
;

-- 2017-06-25T11:04:11.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000014,540772,TO_TIMESTAMP('2017-06-25 11:04:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','delivery',20,TO_TIMESTAMP('2017-06-25 11:04:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T11:04:44.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2936,0,257,540772,546176,TO_TIMESTAMP('2017-06-25 11:04:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferart',10,0,0,TO_TIMESTAMP('2017-06-25 11:04:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T11:06:37.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2943,0,257,540772,546177,TO_TIMESTAMP('2017-06-25 11:06:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferweg',20,0,0,TO_TIMESTAMP('2017-06-25 11:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T11:07:11.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6880,0,257,540772,546178,TO_TIMESTAMP('2017-06-25 11:07:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferdatum',30,0,0,TO_TIMESTAMP('2017-06-25 11:07:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T11:07:30.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6879,0,257,540772,546179,TO_TIMESTAMP('2017-06-25 11:07:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anzahl Handling Units',40,0,0,TO_TIMESTAMP('2017-06-25 11:07:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T11:07:55.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6878,0,257,540772,546180,TO_TIMESTAMP('2017-06-25 11:07:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sendungsverfolgung',50,0,0,TO_TIMESTAMP('2017-06-25 11:07:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T13:51:30.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2703,0,257,540772,546181,TO_TIMESTAMP('2017-06-25 13:51:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',60,0,0,TO_TIMESTAMP('2017-06-25 13:51:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T13:51:33.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2017-06-25 13:51:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546181
;

-- 2017-06-25T13:52:08.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2017-06-25 13:52:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541803
;

-- 2017-06-25T13:52:37.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-25 13:52:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541803
;

-- 2017-06-25T13:54:22.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2937,0,257,540772,546182,TO_TIMESTAMP('2017-06-25 13:54:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferung via',60,0,0,TO_TIMESTAMP('2017-06-25 13:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T13:54:27.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2017-06-25 13:54:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546182
;

-- 2017-06-25T13:56:20.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Handling Units',Updated=TO_TIMESTAMP('2017-06-25 13:56:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6879
;

-- 2017-06-25T13:56:54.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Anzahl der Handling Units die versendet werden', Name='Anzahl Handling Units', PrintName='Anzahl Handling Units',Updated=TO_TIMESTAMP('2017-06-25 13:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2113
;

-- 2017-06-25T13:56:54.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NoPackages', Name='Anzahl Handling Units', Description='Anzahl der Handling Units die versendet werden', Help=NULL WHERE AD_Element_ID=2113
;

-- 2017-06-25T13:56:54.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NoPackages', Name='Anzahl Handling Units', Description='Anzahl der Handling Units die versendet werden', Help=NULL, AD_Element_ID=2113 WHERE UPPER(ColumnName)='NOPACKAGES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-25T13:56:54.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NoPackages', Name='Anzahl Handling Units', Description='Anzahl der Handling Units die versendet werden', Help=NULL WHERE AD_Element_ID=2113 AND IsCentrallyMaintained='Y'
;

-- 2017-06-25T13:56:54.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Handling Units', Description='Anzahl der Handling Units die versendet werden', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2113) AND IsCentrallyMaintained='Y'
;

-- 2017-06-25T13:56:54.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anzahl Handling Units', Name='Anzahl Handling Units' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2113)
;

-- 2017-06-25T13:57:17.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sendungsverfolgung',Updated=TO_TIMESTAMP('2017-06-25 13:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6878
;

-- 2017-06-25T13:58:04.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='ID zur Verfolgung der Warensendung', Name='Sendungsverfolgung', PrintName='Sendungsverfolgung',Updated=TO_TIMESTAMP('2017-06-25 13:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2126
;

-- 2017-06-25T13:58:04.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TrackingNo', Name='Sendungsverfolgung', Description='ID zur Verfolgung der Warensendung', Help=NULL WHERE AD_Element_ID=2126
;

-- 2017-06-25T13:58:04.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TrackingNo', Name='Sendungsverfolgung', Description='ID zur Verfolgung der Warensendung', Help=NULL, AD_Element_ID=2126 WHERE UPPER(ColumnName)='TRACKINGNO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-25T13:58:04.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TrackingNo', Name='Sendungsverfolgung', Description='ID zur Verfolgung der Warensendung', Help=NULL WHERE AD_Element_ID=2126 AND IsCentrallyMaintained='Y'
;

-- 2017-06-25T13:58:04.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sendungsverfolgung', Description='ID zur Verfolgung der Warensendung', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2126) AND IsCentrallyMaintained='Y'
;

-- 2017-06-25T13:58:04.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sendungsverfolgung', Name='Sendungsverfolgung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2126)
;

-- 2017-06-25T14:11:40.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501622,0,257,540128,546183,TO_TIMESTAMP('2017-06-25 14:11:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Incoterm',30,0,0,TO_TIMESTAMP('2017-06-25 14:11:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T14:11:59.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501623,0,257,540128,546184,TO_TIMESTAMP('2017-06-25 14:11:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Incoterm Ort',40,0,0,TO_TIMESTAMP('2017-06-25 14:11:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T14:12:13.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2946,0,257,540128,546185,TO_TIMESTAMP('2017-06-25 14:12:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Priorität',50,0,0,TO_TIMESTAMP('2017-06-25 14:12:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T14:12:33.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2940,0,257,540128,546186,TO_TIMESTAMP('2017-06-25 14:12:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Frachkostenberechnung',60,0,0,TO_TIMESTAMP('2017-06-25 14:12:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T14:12:47.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2939,0,257,540128,546187,TO_TIMESTAMP('2017-06-25 14:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Frachtbetrag',70,0,0,TO_TIMESTAMP('2017-06-25 14:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T14:13:51.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553213,0,257,540128,546188,TO_TIMESTAMP('2017-06-25 14:13:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','per EDI übermitteln',80,0,0,TO_TIMESTAMP('2017-06-25 14:13:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T14:14:09.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553215,0,257,540128,546189,TO_TIMESTAMP('2017-06-25 14:14:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','EDI Fehlermeldung',90,0,0,TO_TIMESTAMP('2017-06-25 14:14:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-25T14:14:36.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-25 14:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546183
;

-- 2017-06-25T14:14:36.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-25 14:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546184
;

-- 2017-06-25T14:14:37.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-25 14:14:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546185
;

-- 2017-06-25T14:14:37.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-25 14:14:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546186
;

-- 2017-06-25T14:14:37.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-25 14:14:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546187
;

-- 2017-06-25T14:14:38.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-25 14:14:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546188
;

-- 2017-06-25T14:14:40.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-25 14:14:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546189
;

-- 2017-06-25T14:15:23.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546181
;

-- 2017-06-25T14:16:18.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-06-25 14:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546182
;

-- 2017-06-25T14:16:18.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-06-25 14:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540617
;

-- 2017-06-25T14:16:18.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-06-25 14:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546175
;

