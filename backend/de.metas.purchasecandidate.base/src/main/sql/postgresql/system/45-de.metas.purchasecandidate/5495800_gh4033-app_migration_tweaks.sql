-- 2018-06-13T13:43:03.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-06-13 13:43:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560251
;

-- 2018-06-13T13:44:34.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-06-13 13:44:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560251
;

-- 2018-06-13T13:44:45.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=20,Updated=TO_TIMESTAMP('2018-06-13 13:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560251
;

-- 2018-06-13T13:44:46.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_purchasecandidate','DemandReference','VARCHAR(20)',null,null)
;

UPDATE C_PurchaseCandidate SET DemandReference = C_OrderLineSO_ID::text WHERE DemandReference IS NULL;
-- 2018-06-13T13:45:25.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-06-13 13:45:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560251
;

commit;

-- 2018-06-13T13:45:29.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_purchasecandidate','DemandReference','VARCHAR(20)',null,null)
;

commit;

-- 2018-06-13T13:45:29.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_purchasecandidate','DemandReference',null,'NOT NULL',null)
;

commit;

-- 2018-06-13T18:24:18.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Referenz', PrintName='Referenz',Updated=TO_TIMESTAMP('2018-06-13 18:24:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544110
;

-- 2018-06-13T18:24:18.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DemandReference', Name='Referenz', Description=NULL, Help=NULL WHERE AD_Element_ID=544110
;

-- 2018-06-13T18:24:18.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DemandReference', Name='Referenz', Description=NULL, Help=NULL, AD_Element_ID=544110 WHERE UPPER(ColumnName)='DEMANDREFERENCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-06-13T18:24:18.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DemandReference', Name='Referenz', Description=NULL, Help=NULL WHERE AD_Element_ID=544110 AND IsCentrallyMaintained='Y'
;

-- 2018-06-13T18:24:18.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Referenz', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544110) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544110)
;

-- 2018-06-13T18:24:18.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Referenz', Name='Referenz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544110)
;

-- 2018-06-13T18:25:41.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Bestelldispo-Zeilen, die den selben Bedarf (z.b. die selbe Auftragszeile) addressieren habe den selben Referenz-Wert',Updated=TO_TIMESTAMP('2018-06-13 18:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544110
;

-- 2018-06-13T18:25:41.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DemandReference', Name='Referenz', Description='Bestelldispo-Zeilen, die den selben Bedarf (z.b. die selbe Auftragszeile) addressieren habe den selben Referenz-Wert', Help=NULL WHERE AD_Element_ID=544110
;

-- 2018-06-13T18:25:41.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DemandReference', Name='Referenz', Description='Bestelldispo-Zeilen, die den selben Bedarf (z.b. die selbe Auftragszeile) addressieren habe den selben Referenz-Wert', Help=NULL, AD_Element_ID=544110 WHERE UPPER(ColumnName)='DEMANDREFERENCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-06-13T18:25:41.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DemandReference', Name='Referenz', Description='Bestelldispo-Zeilen, die den selben Bedarf (z.b. die selbe Auftragszeile) addressieren habe den selben Referenz-Wert', Help=NULL WHERE AD_Element_ID=544110 AND IsCentrallyMaintained='Y'
;

-- 2018-06-13T18:25:41.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Referenz', Description='Bestelldispo-Zeilen, die den selben Bedarf (z.b. die selbe Auftragszeile) addressieren habe den selben Referenz-Wert', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544110) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544110)
;

-- 2018-06-13T19:08:04.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=15, IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2018-06-13 19:08:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560251
;

-- 2018-06-13T19:08:14.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2018-06-13 19:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557851
;

-- 2018-06-13T19:08:23.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2018-06-13 19:08:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557870
;

-- 2018-06-13T19:08:36.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2018-06-13 19:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557863
;

