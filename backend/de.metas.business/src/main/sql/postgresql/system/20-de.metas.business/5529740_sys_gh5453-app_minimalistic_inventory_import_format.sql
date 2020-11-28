

-- 2019-09-03T11:52:46.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Externe Datensatz-Kopf-ID', PrintName='Externe Datensatz-Kopf-ID',Updated=TO_TIMESTAMP('2019-09-03 13:52:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575915 AND AD_Language='de_CH'
;

-- 2019-09-03T11:52:46.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575915,'de_CH') 
;

-- 2019-09-03T11:52:51.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Externe Datensatz-Kopf-ID', PrintName='Externe Datensatz-Kopf-ID',Updated=TO_TIMESTAMP('2019-09-03 13:52:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575915 AND AD_Language='de_DE'
;

-- 2019-09-03T11:52:51.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575915,'de_DE') 
;

-- 2019-09-03T11:52:51.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575915,'de_DE') 
;

-- 2019-09-03T11:52:51.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalHeaderId', Name='Externe Datensatz-Kopf-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=575915
;

-- 2019-09-03T11:52:51.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalHeaderId', Name='Externe Datensatz-Kopf-ID', Description=NULL, Help=NULL, AD_Element_ID=575915 WHERE UPPER(ColumnName)='EXTERNALHEADERID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-03T11:52:51.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalHeaderId', Name='Externe Datensatz-Kopf-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=575915 AND IsCentrallyMaintained='Y'
;

-- 2019-09-03T11:52:51.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Externe Datensatz-Kopf-ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575915) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575915)
;

-- 2019-09-03T11:52:51.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Externe Datensatz-Kopf-ID', Name='Externe Datensatz-Kopf-ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575915)
;

-- 2019-09-03T11:52:51.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Externe Datensatz-Kopf-ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575915
;

-- 2019-09-03T11:52:51.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Externe Datensatz-Kopf-ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 575915
;

-- 2019-09-03T11:52:51.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Externe Datensatz-Kopf-ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575915
;

-- 2019-09-03T11:52:54.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-03 13:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575915 AND AD_Language='en_US'
;

-- 2019-09-03T11:52:54.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575915,'en_US') 
;

-- 2019-09-03T11:53:25.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,568622,540044,541371,0,TO_TIMESTAMP('2019-09-03 13:53:25','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Externe Datensatz-Kopf-ID',50,0,TO_TIMESTAMP('2019-09-03 13:53:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-03T11:54:05.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Externe Datensatz-Zeilen-ID', PrintName='Externe Datensatz-Zeilen-ID',Updated=TO_TIMESTAMP('2019-09-03 13:54:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575914 AND AD_Language='de_CH'
;

-- 2019-09-03T11:54:05.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575914,'de_CH') 
;

-- 2019-09-03T11:54:09.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Externe Datensatz-Zeilen-ID', PrintName='Externe Datensatz-Zeilen-ID',Updated=TO_TIMESTAMP('2019-09-03 13:54:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575914 AND AD_Language='de_DE'
;

-- 2019-09-03T11:54:09.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575914,'de_DE') 
;

-- 2019-09-03T11:54:09.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575914,'de_DE') 
;

-- 2019-09-03T11:54:09.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalLineId', Name='Externe Datensatz-Zeilen-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=575914
;

-- 2019-09-03T11:54:09.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalLineId', Name='Externe Datensatz-Zeilen-ID', Description=NULL, Help=NULL, AD_Element_ID=575914 WHERE UPPER(ColumnName)='EXTERNALLINEID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-03T11:54:09.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalLineId', Name='Externe Datensatz-Zeilen-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=575914 AND IsCentrallyMaintained='Y'
;

-- 2019-09-03T11:54:09.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Externe Datensatz-Zeilen-ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575914) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575914)
;

-- 2019-09-03T11:54:09.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Externe Datensatz-Zeilen-ID', Name='Externe Datensatz-Zeilen-ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575914)
;

-- 2019-09-03T11:54:09.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Externe Datensatz-Zeilen-ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575914
;

-- 2019-09-03T11:54:09.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Externe Datensatz-Zeilen-ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 575914
;

-- 2019-09-03T11:54:09.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Externe Datensatz-Zeilen-ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575914
;

-- 2019-09-03T11:54:12.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-03 13:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575914 AND AD_Language='en_US'
;

-- 2019-09-03T11:54:12.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575914,'en_US') 
;

-- 2019-09-03T11:54:29.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,568623,540044,541372,0,TO_TIMESTAMP('2019-09-03 13:54:28','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Externe Datensatz-Zeilen-ID',60,0,TO_TIMESTAMP('2019-09-03 13:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-03T11:54:52.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET Name='Inventur Minimal',Updated=TO_TIMESTAMP('2019-09-03 13:54:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540044
;

-- 2019-09-03T12:22:59.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=9764, Name='Inventurdatum',Updated=TO_TIMESTAMP('2019-09-03 14:22:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541368
;

-- 2019-09-03T12:24:55.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=8821, Name='Lager-Schlüssel',Updated=TO_TIMESTAMP('2019-09-03 14:24:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541367
;

-- 2019-09-03T12:27:49.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2019-09-03 14:27:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541369
;

-- 2019-09-03T12:27:50.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=4,Updated=TO_TIMESTAMP('2019-09-03 14:27:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541370
;

-- 2019-09-03T12:27:51.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=50,Updated=TO_TIMESTAMP('2019-09-03 14:27:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541371
;

-- 2019-09-03T12:27:54.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=6,Updated=TO_TIMESTAMP('2019-09-03 14:27:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541372
;

-- 2019-09-03T12:37:00.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=5,Updated=TO_TIMESTAMP('2019-09-03 14:37:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541371
;

-- 2019-09-03T13:23:19.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=8989, Name='Lagerort-Schlüssel',Updated=TO_TIMESTAMP('2019-09-03 15:23:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541367
;

-- 2019-09-03T13:23:49.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-03 15:23:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2109 AND AD_Language='de_CH'
;

-- 2019-09-03T13:23:49.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2109,'de_CH') 
;

-- 2019-09-03T13:24:52.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=8821, Name='Lager-Schlüssel',Updated=TO_TIMESTAMP('2019-09-03 15:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541367
;

-- 2019-09-03T13:30:22.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=8989, Name='Lagerort-Schlüssel',Updated=TO_TIMESTAMP('2019-09-03 15:30:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541367
;



-- 2019-09-03T11:41:32.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FormatType,IsActive,IsManualImport,IsMultiLine,Name,Processing,Updated,UpdatedBy) VALUES (0,540044,0,572,TO_TIMESTAMP('2019-09-03 13:41:31','YYYY-MM-DD HH24:MI:SS'),100,'S','Y','N','N','Warenbestände Minimal','N',TO_TIMESTAMP('2019-09-03 13:41:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-03T11:42:30.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,8989,540044,541367,0,TO_TIMESTAMP('2019-09-03 13:42:30','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Lagerort-Schlüssel',10,1,TO_TIMESTAMP('2019-09-03 13:42:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-03T11:43:14.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559688,540044,541368,0,TO_TIMESTAMP('2019-09-03 13:43:14','YYYY-MM-DD HH24:MI:SS'),100,'dd.MM.yyyy','D','.','N',0,'Y','Datum',20,2,TO_TIMESTAMP('2019-09-03 13:43:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-03T11:44:00.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,8824,540044,541369,0,TO_TIMESTAMP('2019-09-03 13:44:00','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Produkt-Suchschlüssel',30,0,TO_TIMESTAMP('2019-09-03 13:44:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-03T11:44:27.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,8823,540044,541370,0,TO_TIMESTAMP('2019-09-03 13:44:27','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N',0,'Y','Zählmenge',40,0,TO_TIMESTAMP('2019-09-03 13:44:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-04T05:35:37.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET Description='Einfaches Datenformat mit wenigen Feldern',Updated=TO_TIMESTAMP('2019-09-04 07:35:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540044
;

