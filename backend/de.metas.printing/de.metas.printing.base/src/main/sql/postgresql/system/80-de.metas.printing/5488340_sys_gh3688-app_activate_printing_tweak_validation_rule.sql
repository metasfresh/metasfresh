
--
-- set "de.metas.printing.Enabled" = Y
--
-- 2018-03-14T09:53:33.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2018-03-14 09:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540880
;



-- 2018-03-15T11:33:51.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540422,0,540443,TO_TIMESTAMP('2018-03-15 11:33:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','Y','AD_PrinterHW_MediaTray_UQ_Number_PrinterHW','N',TO_TIMESTAMP('2018-03-15 11:33:50','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2018-03-15T11:33:51.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540422 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-03-15T11:34:10.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,548089,540841,540422,0,TO_TIMESTAMP('2018-03-15 11:34:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y',10,TO_TIMESTAMP('2018-03-15 11:34:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-15T11:34:28.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,548092,540842,540422,0,TO_TIMESTAMP('2018-03-15 11:34:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y',20,TO_TIMESTAMP('2018-03-15 11:34:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-15T11:34:30.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_PrinterHW_MediaTray_UQ_Number_PrinterHW ON AD_PrinterHW_MediaTray (AD_PrinterHW_ID,TrayNumber) WHERE IsActive='Y'
;

-- 2018-03-15T11:44:12.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_PrinterHW.HostKey=''@CurrentConfigHostKey@'' OR AD_PrinterHW.OutputType=''PDF''',Updated=TO_TIMESTAMP('2018-03-15 11:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540166
;

-- 2018-03-15T11:44:56.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543929,0,'CurrentConfigHostKey',TO_TIMESTAMP('2018-03-15 11:44:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','Host Key','Host Key',TO_TIMESTAMP('2018-03-15 11:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-15T11:44:56.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543929 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-03-15T11:45:59.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543929, ColumnName='CurrentConfigHostKey', Description=NULL, Help=NULL, Name='Host Key', TechnicalNote='Renamed this column from HostKey to CurrentConfigHostKey to avid  overlapping the the "actual" HostKey ctx variable',Updated=TO_TIMESTAMP('2018-03-15 11:45:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551593
;

-- 2018-03-15T11:45:59.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Host Key', Description=NULL, Help=NULL WHERE AD_Column_ID=551593
;

-- 2018-03-15T12:00:08.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2018-03-15 12:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548243
;

-- 2018-03-15T12:01:07.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ConfigHostKey',Updated=TO_TIMESTAMP('2018-03-15 12:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929
;

-- 2018-03-15T12:01:07.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ConfigHostKey', Name='Host Key', Description=NULL, Help=NULL WHERE AD_Element_ID=543929
;

-- 2018-03-15T12:01:07.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ConfigHostKey', Name='Host Key', Description=NULL, Help=NULL, AD_Element_ID=543929 WHERE UPPER(ColumnName)='CONFIGHOSTKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-03-15T12:01:07.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ConfigHostKey', Name='Host Key', Description=NULL, Help=NULL WHERE AD_Element_ID=543929 AND IsCentrallyMaintained='Y'
;

-- 2018-03-15T12:01:18.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Renamed this column from HostKey to ConfigHostKey to avid  overlapping the the "actual" HostKey ctx variable',Updated=TO_TIMESTAMP('2018-03-15 12:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551593
;


ALTER TABLE AD_Printer_Config RENAME COLUMN HostKey TO ConfigHostKey;



-- 2018-03-15T12:04:50.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2018-03-15 12:04:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548140
;

-- 2018-03-15T12:05:12.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2018-03-15 12:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548129
;

