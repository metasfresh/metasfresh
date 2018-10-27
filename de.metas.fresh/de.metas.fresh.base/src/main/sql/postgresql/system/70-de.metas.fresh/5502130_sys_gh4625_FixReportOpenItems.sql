-- 2018-09-24T15:21:43.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Reference_Date', Name='Stichtag',Updated=TO_TIMESTAMP('2018-09-24 15:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540570
;

-- 2018-09-24T15:21:52.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544428,0,'Reference_Date',TO_TIMESTAMP('2018-09-24 15:21:52','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Stichtag','Stichtag',TO_TIMESTAMP('2018-09-24 15:21:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-24T15:21:52.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544428 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-24T15:21:59.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=544428, Description=NULL, EntityType='U',Updated=TO_TIMESTAMP('2018-09-24 15:21:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540570
;

-- 2018-09-24T15:22:13.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-24 15:22:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_Para_ID=540570 AND AD_Language='it_CH'
;

-- 2018-09-24T15:22:20.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-24 15:22:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Stichtag' WHERE AD_Process_Para_ID=540570 AND AD_Language='fr_CH'
;

-- 2018-09-24T15:22:24.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-24 15:22:24','YYYY-MM-DD HH24:MI:SS'),Name='Stichtag' WHERE AD_Process_Para_ID=540570 AND AD_Language='it_CH'
;

-- 2018-09-24T15:22:33.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-24 15:22:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Reference Date',Description='' WHERE AD_Process_Para_ID=540570 AND AD_Language='en_GB'
;

-- 2018-09-24T15:22:35.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-24 15:22:35','YYYY-MM-DD HH24:MI:SS'),Description='Referenzdatum zur Berechnung der Tage der Fälligkeit eines Postens' WHERE AD_Process_Para_ID=540570 AND AD_Language='it_CH'
;

-- 2018-09-24T15:22:37.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-24 15:22:37','YYYY-MM-DD HH24:MI:SS'),Description='Referenzdatum zur Berechnung der Tage der Fälligkeit eines Postens' WHERE AD_Process_Para_ID=540570 AND AD_Language='fr_CH'
;

