
-- 2018-01-10T11:10:12.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-01-10 11:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558125
;

-- 2018-01-10T11:10:57.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='AD_Name_ID (alternative AD_Element_ID)', PrintName='AD_Name_ID (alternative AD_Element_ID)',Updated=TO_TIMESTAMP('2018-01-10 11:10:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543525
;



-- 2018-01-10T11:10:57.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Name_ID', Name='AD_Name_ID (alternative AD_Element_ID)', Description=NULL, Help=NULL WHERE AD_Element_ID=543525
;

-- 2018-01-10T11:10:57.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Name_ID', Name='AD_Name_ID (alternative AD_Element_ID)', Description=NULL, Help=NULL, AD_Element_ID=543525 WHERE UPPER(ColumnName)='AD_NAME_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-10T11:10:57.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Name_ID', Name='AD_Name_ID (alternative AD_Element_ID)', Description=NULL, Help=NULL WHERE AD_Element_ID=543525 AND IsCentrallyMaintained='Y'
;

-- 2018-01-10T11:10:57.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AD_Name_ID (alternative AD_Element_ID)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543525) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543525)
;

-- 2018-01-10T11:10:57.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='AD_Name_ID (alternative AD_Element_ID)', Name='AD_Name_ID (alternative AD_Element_ID)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543525)
;

-- 2018-01-10T11:11:29.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Help='https://github.com/metasfresh/metasfresh/issues/1752',Updated=TO_TIMESTAMP('2018-01-10 11:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543525
;

-- 2018-01-10T11:11:29.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Name_ID', Name='AD_Name_ID (alternative AD_Element_ID)', Description=NULL, Help='https://github.com/metasfresh/metasfresh/issues/1752' WHERE AD_Element_ID=543525
;

-- 2018-01-10T11:11:29.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Name_ID', Name='AD_Name_ID (alternative AD_Element_ID)', Description=NULL, Help='https://github.com/metasfresh/metasfresh/issues/1752', AD_Element_ID=543525 WHERE UPPER(ColumnName)='AD_NAME_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-10T11:11:29.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Name_ID', Name='AD_Name_ID (alternative AD_Element_ID)', Description=NULL, Help='https://github.com/metasfresh/metasfresh/issues/1752' WHERE AD_Element_ID=543525 AND IsCentrallyMaintained='Y'
;

-- 2018-01-10T11:11:29.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AD_Name_ID (alternative AD_Element_ID)', Description=NULL, Help='https://github.com/metasfresh/metasfresh/issues/1752' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543525) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543525)
;


-- 2018-01-10T11:25:41.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='This is an AD_Element_ID that can does not need to have a ColumnName and can be used to name things',Updated=TO_TIMESTAMP('2018-01-10 11:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543525
;

-- 2018-01-10T11:25:41.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Name_ID', Name='AD_Name_ID (alternative AD_Element_ID)', Description='This is an AD_Element_ID that can does not need to have a ColumnName and can be used to name things', Help='https://github.com/metasfresh/metasfresh/issues/1752' WHERE AD_Element_ID=543525
;

-- 2018-01-10T11:25:41.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Name_ID', Name='AD_Name_ID (alternative AD_Element_ID)', Description='This is an AD_Element_ID that can does not need to have a ColumnName and can be used to name things', Help='https://github.com/metasfresh/metasfresh/issues/1752', AD_Element_ID=543525 WHERE UPPER(ColumnName)='AD_NAME_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-10T11:25:41.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Name_ID', Name='AD_Name_ID (alternative AD_Element_ID)', Description='This is an AD_Element_ID that can does not need to have a ColumnName and can be used to name things', Help='https://github.com/metasfresh/metasfresh/issues/1752' WHERE AD_Element_ID=543525 AND IsCentrallyMaintained='Y'
;

-- 2018-01-10T11:25:41.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AD_Name_ID (alternative AD_Element_ID)', Description='This is an AD_Element_ID that can does not need to have a ColumnName and can be used to name things', Help='https://github.com/metasfresh/metasfresh/issues/1752' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543525) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543525)
;

-- 2018-01-10T11:28:00.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543756,0,TO_TIMESTAMP('2018-01-10 11:28:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abw. Feldbenennung','Abw. Feldbenennung',TO_TIMESTAMP('2018-01-10 11:28:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T11:28:00.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543756 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-10T11:29:05.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET ACTriggerLength=3, IsAutocomplete='Y',Updated=TO_TIMESTAMP('2018-01-10 11:29:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=276
;

-- 2018-01-10T11:29:25.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=543756, Description=NULL, Help=NULL, Name='Abw. Feldbenennung',Updated=TO_TIMESTAMP('2018-01-10 11:29:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560802
;
