-- 2017-06-12T08:52:12.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Name Zusatz', PrintName='Name Zusatz',Updated=TO_TIMESTAMP('2017-06-12 08:52:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1111
;

-- 2017-06-12T08:52:12.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=1111
;

-- 2017-06-12T08:52:12.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Name2', Name='Name Zusatz', Description='Zusätzliche Bezeichnung', Help=NULL WHERE AD_Element_ID=1111
;

-- 2017-06-12T08:52:12.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Name2', Name='Name Zusatz', Description='Zusätzliche Bezeichnung', Help=NULL, AD_Element_ID=1111 WHERE UPPER(ColumnName)='NAME2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-12T08:52:12.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Name2', Name='Name Zusatz', Description='Zusätzliche Bezeichnung', Help=NULL WHERE AD_Element_ID=1111 AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T08:52:12.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name Zusatz', Description='Zusätzliche Bezeichnung', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1111) AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T08:52:12.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Name Zusatz', Name='Name Zusatz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1111)
;

-- 2017-06-12T08:56:17.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,220,540287,TO_TIMESTAMP('2017-06-12 08:56:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-12 08:56:17','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-06-12T08:56:17.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540287 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-12T08:56:25.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540391,540287,TO_TIMESTAMP('2017-06-12 08:56:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-12 08:56:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T08:56:40.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540391,540671,TO_TIMESTAMP('2017-06-12 08:56:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2017-06-12 08:56:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T08:57:22.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,UIStyle,Updated,UpdatedBy) VALUES (0,2146,0,220,540671,545714,TO_TIMESTAMP('2017-06-12 08:57:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anzahl Beschäftigte',10,0,0,'',TO_TIMESTAMP('2017-06-12 08:57:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T08:57:57.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557309,0,220,540671,545715,TO_TIMESTAMP('2017-06-12 08:57:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rechnung per eMail',20,0,0,TO_TIMESTAMP('2017-06-12 08:57:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T08:58:34.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556620,0,220,540671,545716,TO_TIMESTAMP('2017-06-12 08:58:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Automatische Referenz',30,0,0,TO_TIMESTAMP('2017-06-12 08:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T08:59:04.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556621,0,220,540671,545717,TO_TIMESTAMP('2017-06-12 08:59:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Referenz Vorgabe',40,0,0,TO_TIMESTAMP('2017-06-12 08:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T08:59:32.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Description='Anzahl der Mitarbeiter im Unternehmen', Name='Anzahl Beschäftigte',Updated=TO_TIMESTAMP('2017-06-12 08:59:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2146
;

-- 2017-06-12T08:59:32.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=2146
;

-- 2017-06-12T08:59:55.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Anzahl Beschäftigte', PrintName='Anzahl Beschäftigte',Updated=TO_TIMESTAMP('2017-06-12 08:59:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=473
;

-- 2017-06-12T08:59:55.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=473
;

-- 2017-06-12T08:59:55.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NumberEmployees', Name='Anzahl Beschäftigte', Description='Anzahl der Mitarbeiter', Help='Indicates the number of employees for this Business Partner.  This field displays only for Prospects.' WHERE AD_Element_ID=473
;

-- 2017-06-12T08:59:55.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NumberEmployees', Name='Anzahl Beschäftigte', Description='Anzahl der Mitarbeiter', Help='Indicates the number of employees for this Business Partner.  This field displays only for Prospects.', AD_Element_ID=473 WHERE UPPER(ColumnName)='NUMBEREMPLOYEES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-12T08:59:55.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NumberEmployees', Name='Anzahl Beschäftigte', Description='Anzahl der Mitarbeiter', Help='Indicates the number of employees for this Business Partner.  This field displays only for Prospects.' WHERE AD_Element_ID=473 AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T08:59:55.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Beschäftigte', Description='Anzahl der Mitarbeiter', Help='Indicates the number of employees for this Business Partner.  This field displays only for Prospects.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=473) AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T08:59:55.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anzahl Beschäftigte', Name='Anzahl Beschäftigte' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=473)
;

-- 2017-06-12T09:00:28.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnung per eMail',Updated=TO_TIMESTAMP('2017-06-12 09:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557309
;

-- 2017-06-12T09:00:28.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=557309
;

-- 2017-06-12T09:00:38.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Rechnung per eMail', PrintName='Rechnung per eMail',Updated=TO_TIMESTAMP('2017-06-12 09:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543189
;

-- 2017-06-12T09:00:38.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543189
;

-- 2017-06-12T09:00:38.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInvoiceEmailEnabled', Name='Rechnung per eMail', Description=NULL, Help=NULL WHERE AD_Element_ID=543189
;

-- 2017-06-12T09:00:38.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceEmailEnabled', Name='Rechnung per eMail', Description=NULL, Help=NULL, AD_Element_ID=543189 WHERE UPPER(ColumnName)='ISINVOICEEMAILENABLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-12T09:00:38.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceEmailEnabled', Name='Rechnung per eMail', Description=NULL, Help=NULL WHERE AD_Element_ID=543189 AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T09:00:38.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnung per eMail', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543189) AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T09:00:38.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung per eMail', Name='Rechnung per eMail' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543189)
;

-- 2017-06-12T09:01:11.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Autom. Referenz',Updated=TO_TIMESTAMP('2017-06-12 09:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556620
;

-- 2017-06-12T09:01:11.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=556620
;

-- 2017-06-12T09:01:26.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Autom. Referenz', PrintName='Autom. Referenz',Updated=TO_TIMESTAMP('2017-06-12 09:01:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542973
;

-- 2017-06-12T09:01:26.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542973
;

-- 2017-06-12T09:01:26.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsCreateDefaultPOReference', Name='Autom. Referenz', Description='Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.', Help=NULL WHERE AD_Element_ID=542973
;

-- 2017-06-12T09:01:26.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreateDefaultPOReference', Name='Autom. Referenz', Description='Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.', Help=NULL, AD_Element_ID=542973 WHERE UPPER(ColumnName)='ISCREATEDEFAULTPOREFERENCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-12T09:01:26.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreateDefaultPOReference', Name='Autom. Referenz', Description='Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.', Help=NULL WHERE AD_Element_ID=542973 AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T09:01:26.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Autom. Referenz', Description='Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542973) AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T09:01:26.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Autom. Referenz', Name='Autom. Referenz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542973)
;

-- 2017-06-12T09:01:59.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Referenz Vorgabe',Updated=TO_TIMESTAMP('2017-06-12 09:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556621
;

-- 2017-06-12T09:01:59.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=556621
;

-- 2017-06-12T09:02:16.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Referenz Vorgabe', PrintName='Referenz Vorgabe',Updated=TO_TIMESTAMP('2017-06-12 09:02:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542974
;

-- 2017-06-12T09:02:16.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542974
;

-- 2017-06-12T09:02:16.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='POReferencePattern', Name='Referenz Vorgabe', Description='Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen', Help='Beispiel:
<ul>
<li>Vorlage: 00600000000</li>
<li>Auftragsnumer: 12345</li>
<li>Erzeugte Referenz: 00600012345</li>
</ul>' WHERE AD_Element_ID=542974
;

-- 2017-06-12T09:02:16.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='POReferencePattern', Name='Referenz Vorgabe', Description='Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen', Help='Beispiel:
<ul>
<li>Vorlage: 00600000000</li>
<li>Auftragsnumer: 12345</li>
<li>Erzeugte Referenz: 00600012345</li>
</ul>', AD_Element_ID=542974 WHERE UPPER(ColumnName)='POREFERENCEPATTERN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-12T09:02:16.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='POReferencePattern', Name='Referenz Vorgabe', Description='Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen', Help='Beispiel:
<ul>
<li>Vorlage: 00600000000</li>
<li>Auftragsnumer: 12345</li>
<li>Erzeugte Referenz: 00600012345</li>
</ul>' WHERE AD_Element_ID=542974 AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T09:02:16.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Referenz Vorgabe', Description='Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen', Help='Beispiel:
<ul>
<li>Vorlage: 00600000000</li>
<li>Auftragsnumer: 12345</li>
<li>Erzeugte Referenz: 00600012345</li>
</ul>' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542974) AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T09:02:16.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Referenz Vorgabe', Name='Referenz Vorgabe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542974)
;

-- 2017-06-12T09:03:41.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000087
;

-- 2017-06-12T09:04:00.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Geschäftspartnergruppe',Updated=TO_TIMESTAMP('2017-06-12 09:04:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000222
;

-- 2017-06-12T09:04:28.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000084
;

-- 2017-06-12T09:04:37.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Erhält EDI-Belege',Updated=TO_TIMESTAMP('2017-06-12 09:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000083
;

-- 2017-06-12T09:04:54.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-12 09:04:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545714
;

-- 2017-06-12T09:04:54.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-12 09:04:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545715
;

-- 2017-06-12T09:04:55.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-12 09:04:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545716
;

-- 2017-06-12T09:04:56.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-12 09:04:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545717
;

-- 2017-06-12T09:12:17.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='payment conditions', UIStyle='primary',Updated=TO_TIMESTAMP('2017-06-12 09:12:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000032
;

-- 2017-06-12T09:12:37.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='default', UIStyle='',Updated=TO_TIMESTAMP('2017-06-12 09:12:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000032
;

-- 2017-06-12T09:12:45.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000018,540672,TO_TIMESTAMP('2017-06-12 09:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','payment conditions',20,TO_TIMESTAMP('2017-06-12 09:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:13:17.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3114,0,223,540672,545718,TO_TIMESTAMP('2017-06-12 09:13:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsweise',10,0,0,TO_TIMESTAMP('2017-06-12 09:13:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:13:35.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2427,0,223,540672,545719,TO_TIMESTAMP('2017-06-12 09:13:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kreditlimit',20,0,0,TO_TIMESTAMP('2017-06-12 09:13:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:14:07.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2408,0,223,540672,545720,TO_TIMESTAMP('2017-06-12 09:14:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsbedingung',30,0,0,TO_TIMESTAMP('2017-06-12 09:14:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:14:28.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zahlungsbedingung',Updated=TO_TIMESTAMP('2017-06-12 09:14:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2408
;

-- 2017-06-12T09:14:28.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=2408
;

-- 2017-06-12T09:14:47.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Zahlungsbedingung', PrintName='Zahlungsbedingung',Updated=TO_TIMESTAMP('2017-06-12 09:14:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=204
;

-- 2017-06-12T09:14:47.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=204
;

-- 2017-06-12T09:14:47.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_PaymentTerm_ID', Name='Zahlungsbedingung', Description='Die Bedingungen für die Bezahlung dieses Vorgangs', Help='Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.' WHERE AD_Element_ID=204
;

-- 2017-06-12T09:14:47.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_PaymentTerm_ID', Name='Zahlungsbedingung', Description='Die Bedingungen für die Bezahlung dieses Vorgangs', Help='Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.', AD_Element_ID=204 WHERE UPPER(ColumnName)='C_PAYMENTTERM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-12T09:14:47.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_PaymentTerm_ID', Name='Zahlungsbedingung', Description='Die Bedingungen für die Bezahlung dieses Vorgangs', Help='Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.' WHERE AD_Element_ID=204 AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T09:14:47.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zahlungsbedingung', Description='Die Bedingungen für die Bezahlung dieses Vorgangs', Help='Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=204) AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T09:14:47.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zahlungsbedingung', Name='Zahlungsbedingung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=204)
;

-- 2017-06-12T09:15:09.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2434,0,223,540672,545721,TO_TIMESTAMP('2017-06-12 09:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mahnung',40,0,0,TO_TIMESTAMP('2017-06-12 09:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:16:03.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,505287,0,223,540672,545722,TO_TIMESTAMP('2017-06-12 09:16:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preissystem',50,0,0,TO_TIMESTAMP('2017-06-12 09:16:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:16:25.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5282,0,223,540672,545723,TO_TIMESTAMP('2017-06-12 09:16:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rabatt Schema',60,0,0,TO_TIMESTAMP('2017-06-12 09:16:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:16:34.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rabatt Schema',Updated=TO_TIMESTAMP('2017-06-12 09:16:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5282
;

-- 2017-06-12T09:16:34.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=5282
;

-- 2017-06-12T09:16:46.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Rabatt Schema', PrintName='Rabatt Schema',Updated=TO_TIMESTAMP('2017-06-12 09:16:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1714
;

-- 2017-06-12T09:16:46.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=1714
;

-- 2017-06-12T09:16:46.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_DiscountSchema_ID', Name='Rabatt Schema', Description='Schema um den prozentualen Rabatt zu berechnen', Help='Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.' WHERE AD_Element_ID=1714
;

-- 2017-06-12T09:16:46.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_DiscountSchema_ID', Name='Rabatt Schema', Description='Schema um den prozentualen Rabatt zu berechnen', Help='Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.', AD_Element_ID=1714 WHERE UPPER(ColumnName)='M_DISCOUNTSCHEMA_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-12T09:16:46.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_DiscountSchema_ID', Name='Rabatt Schema', Description='Schema um den prozentualen Rabatt zu berechnen', Help='Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.' WHERE AD_Element_ID=1714 AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T09:16:46.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rabatt Schema', Description='Schema um den prozentualen Rabatt zu berechnen', Help='Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1714) AND IsCentrallyMaintained='Y'
;

-- 2017-06-12T09:16:46.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rabatt Schema', Name='Rabatt Schema' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1714)
;

-- 2017-06-12T09:18:01.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545721
;

-- 2017-06-12T09:18:12.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000018,540673,TO_TIMESTAMP('2017-06-12 09:18:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','dunning',30,TO_TIMESTAMP('2017-06-12 09:18:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:18:22.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2434,0,223,540673,545724,TO_TIMESTAMP('2017-06-12 09:18:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mahnung',10,0,0,TO_TIMESTAMP('2017-06-12 09:18:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:18:32.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53256,0,223,540673,545725,TO_TIMESTAMP('2017-06-12 09:18:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mahnkarenz',20,0,0,TO_TIMESTAMP('2017-06-12 09:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:19:27.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000018,540674,TO_TIMESTAMP('2017-06-12 09:19:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','aggregation',40,TO_TIMESTAMP('2017-06-12 09:19:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:19:48.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555566,0,223,540674,545726,TO_TIMESTAMP('2017-06-12 09:19:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aggregation Rechnungen',10,0,0,TO_TIMESTAMP('2017-06-12 09:19:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:20:06.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556192,0,223,540674,545727,TO_TIMESTAMP('2017-06-12 09:20:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aggregation Rechnungszeilen',20,0,0,TO_TIMESTAMP('2017-06-12 09:20:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:20:28.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,502495,0,223,540674,545728,TO_TIMESTAMP('2017-06-12 09:20:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sammel Lieferscheine',30,0,0,TO_TIMESTAMP('2017-06-12 09:20:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:21:12.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3276,0,223,540672,545729,TO_TIMESTAMP('2017-06-12 09:21:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rabatte drucken',70,0,0,TO_TIMESTAMP('2017-06-12 09:21:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:21:33.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=99,Updated=TO_TIMESTAMP('2017-06-12 09:21:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000032
;

-- 2017-06-12T09:21:41.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-06-12 09:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540672
;

-- 2017-06-12T09:22:31.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000253
;

-- 2017-06-12T09:22:31.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000257
;

-- 2017-06-12T09:23:30.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000254
;

-- 2017-06-12T09:23:30.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000270
;

-- 2017-06-12T09:23:30.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000251
;

-- 2017-06-12T09:23:30.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000252
;

-- 2017-06-12T09:23:30.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000255
;

-- 2017-06-12T09:23:30.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000260
;

-- 2017-06-12T09:23:30.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000262
;

-- 2017-06-12T09:23:30.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000269
;

-- 2017-06-12T09:24:33.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2568,0,223,540672,545730,TO_TIMESTAMP('2017-06-12 09:24:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kunde',5,0,0,TO_TIMESTAMP('2017-06-12 09:24:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:25:21.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000018,540675,TO_TIMESTAMP('2017-06-12 09:25:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',90,TO_TIMESTAMP('2017-06-12 09:25:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:25:32.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2402,0,223,540675,545731,TO_TIMESTAMP('2017-06-12 09:25:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-06-12 09:25:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:25:42.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2400,0,223,540675,545732,TO_TIMESTAMP('2017-06-12 09:25:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-06-12 09:25:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T09:25:52.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000241
;

-- 2017-06-12T09:26:10.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000261
;

-- 2017-06-12T09:26:16.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000245
;

-- 2017-06-12T09:27:48.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Description='Suchschlüssel fÃ¼r den Eintrag im erforderlichen Format - muss eindeutig sein', Name='Suchschlüssel',Updated=TO_TIMESTAMP('2017-06-12 09:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000242
;

-- 2017-06-12T09:57:11.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-06-12 09:57:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000244
;

-- 2017-06-12T09:57:23.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-06-12 09:57:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000263
;

-- 2017-06-12T09:57:32.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-06-12 09:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000259
;

-- 2017-06-12T09:57:40.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-06-12 09:57:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000248
;

-- 2017-06-12T09:57:45.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-06-12 09:57:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000246
;

-- 2017-06-12T09:57:56.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-06-12 09:57:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000249
;

-- 2017-06-12T09:58:03.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-06-12 09:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000256
;

-- 2017-06-12T09:58:18.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-06-12 09:58:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000258
;

-- 2017-06-12T09:58:24.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-06-12 09:58:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000265
;

-- 2017-06-12T09:58:31.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2017-06-12 09:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000247
;

-- 2017-06-12T09:58:38.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2017-06-12 09:58:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000250
;

-- 2017-06-12T09:58:45.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2017-06-12 09:58:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000266
;

-- 2017-06-12T09:59:00.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2017-06-12 09:59:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000250
;

-- 2017-06-12T09:59:06.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2017-06-12 09:59:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000264
;

-- 2017-06-12T09:59:18.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2017-06-12 09:59:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000250
;

-- 2017-06-12T09:59:22.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2017-06-12 09:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000267
;

-- 2017-06-12T09:59:26.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2017-06-12 09:59:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000268
;

-- 2017-06-12T10:00:12.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000243
;

-- 2017-06-12T10:00:16.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000242
;

-- 2017-06-12T10:00:31.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=50,Updated=TO_TIMESTAMP('2017-06-12 10:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000032
;

-- 2017-06-12T10:01:34.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-06-12 10:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545730
;

-- 2017-06-12T10:01:34.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-06-12 10:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545718
;

-- 2017-06-12T10:01:34.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-06-12 10:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545720
;

-- 2017-06-12T10:01:34.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-06-12 10:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545719
;

-- 2017-06-12T10:01:34.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-06-12 10:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000266
;

-- 2017-06-12T10:02:17.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-06-12 10:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545722
;

-- 2017-06-12T10:02:17.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-06-12 10:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545723
;

-- 2017-06-12T10:02:17.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-06-12 10:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545724
;

-- 2017-06-12T10:02:17.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-06-12 10:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000266
;

-- 2017-06-12T10:09:30.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='advanced edit',Updated=TO_TIMESTAMP('2017-06-12 10:09:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540287
;

-- 2017-06-12T10:09:30.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540287
;

-- 2017-06-12T10:10:49.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='',Updated=TO_TIMESTAMP('2017-06-12 10:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540287
;

-- 2017-06-12T10:10:49.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540287
;

-- 2017-06-12T10:21:13.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='main',Updated=TO_TIMESTAMP('2017-06-12 10:21:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=1000015
;

-- 2017-06-12T10:21:13.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=1000015
;

-- 2017-06-12T10:24:47.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='',Updated=TO_TIMESTAMP('2017-06-12 10:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=1000015
;

-- 2017-06-12T10:24:47.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=1000015
;

-- 2017-06-12T10:26:48.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,223,540288,TO_TIMESTAMP('2017-06-12 10:26:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-12 10:26:48','YYYY-MM-DD HH24:MI:SS'),100,'dunning')
;

-- 2017-06-12T10:26:48.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540288 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-12T10:27:03.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,223,540289,TO_TIMESTAMP('2017-06-12 10:27:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',30,TO_TIMESTAMP('2017-06-12 10:27:03','YYYY-MM-DD HH24:MI:SS'),100,'aggregation')
;

-- 2017-06-12T10:27:03.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540289 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-12T10:27:10.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,223,540290,TO_TIMESTAMP('2017-06-12 10:27:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',40,TO_TIMESTAMP('2017-06-12 10:27:10','YYYY-MM-DD HH24:MI:SS'),100,'extended')
;

-- 2017-06-12T10:27:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540290 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-12T10:27:19.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,223,540291,TO_TIMESTAMP('2017-06-12 10:27:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',50,TO_TIMESTAMP('2017-06-12 10:27:19','YYYY-MM-DD HH24:MI:SS'),100,'org')
;

-- 2017-06-12T10:27:19.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540291 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-12T10:27:36.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540392,540288,TO_TIMESTAMP('2017-06-12 10:27:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-12 10:27:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T10:27:41.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540393,540289,TO_TIMESTAMP('2017-06-12 10:27:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-12 10:27:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T10:27:47.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540394,540290,TO_TIMESTAMP('2017-06-12 10:27:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-12 10:27:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T10:27:53.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540395,540291,TO_TIMESTAMP('2017-06-12 10:27:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-12 10:27:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-12T10:28:51.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540392, SeqNo=10,Updated=TO_TIMESTAMP('2017-06-12 10:28:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540673
;

-- 2017-06-12T10:29:06.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540393, SeqNo=10,Updated=TO_TIMESTAMP('2017-06-12 10:29:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540674
;

-- 2017-06-12T10:29:21.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540394, SeqNo=10,Updated=TO_TIMESTAMP('2017-06-12 10:29:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000032
;

-- 2017-06-12T10:29:30.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540395, SeqNo=10,Updated=TO_TIMESTAMP('2017-06-12 10:29:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540675
;

-- 2017-06-12T10:30:03.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='org',Updated=TO_TIMESTAMP('2017-06-12 10:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540291
;

-- 2017-06-12T10:30:03.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540291
;

-- 2017-06-12T10:31:19.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Organisation',Updated=TO_TIMESTAMP('2017-06-12 10:31:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540291
;

-- 2017-06-12T10:31:19.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540291
;

-- 2017-06-12T10:31:24.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Erweitert',Updated=TO_TIMESTAMP('2017-06-12 10:31:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540290
;

-- 2017-06-12T10:31:24.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540290
;

-- 2017-06-12T10:31:29.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Aggregation',Updated=TO_TIMESTAMP('2017-06-12 10:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540289
;

-- 2017-06-12T10:31:29.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540289
;

-- 2017-06-12T10:31:33.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Mahnung',Updated=TO_TIMESTAMP('2017-06-12 10:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540288
;

-- 2017-06-12T10:31:33.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540288
;

