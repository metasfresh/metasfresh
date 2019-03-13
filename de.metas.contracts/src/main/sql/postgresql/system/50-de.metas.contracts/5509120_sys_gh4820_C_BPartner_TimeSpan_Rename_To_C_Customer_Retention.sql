


ALTER TABLE C_BPartner_TimeSpan
RENAME TO C_Customer_Retention;




ALTER TABLE C_Customer_Retention
RENAME COLUMN C_BPartner_TimeSpan_ID TO C_Customer_Retention_ID;





ALTER TABLE C_Customer_Retention
RENAME COLUMN C_BPartner_TimeSpan TO CustomerRetention;


-- 2019-01-08T14:39:33.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Customer Retention', TableName='C_Customer_Retention',Updated=TO_TIMESTAMP('2019-01-08 14:39:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541157
;

-- 2019-01-08T14:39:33.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='C_Customer_Retention',Updated=TO_TIMESTAMP('2019-01-08 14:39:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554774
;

-- 2019-01-08T14:39:33.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--ALTER SEQUENCE C_BPartner_TimeSpan_SEQ RENAME TO C_Customer_Retention_SEQ
--;

-- 2019-01-08T14:40:34.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575932,0,'C_Customer_Retention_ID',TO_TIMESTAMP('2019-01-08 14:40:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','C_Customer_Retention_ID','C_Customer_Retention_ID',TO_TIMESTAMP('2019-01-08 14:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-08T14:40:34.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575932 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-08T14:40:55.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575933,0,'CustomerRetention',TO_TIMESTAMP('2019-01-08 14:40:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Customer Retention','Customer Retention',TO_TIMESTAMP('2019-01-08 14:40:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-08T14:40:55.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575933 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-08T14:41:14.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=575932, ColumnName='C_Customer_Retention_ID', Description=NULL, EntityType='de.metas.contracts', Help=NULL, IsUpdateable='N', Name='C_Customer_Retention_ID',Updated=TO_TIMESTAMP('2019-01-08 14:41:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563681
;

-- 2019-01-08T14:41:14.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='C_Customer_Retention_ID', Description=NULL, Help=NULL WHERE AD_Column_ID=563681
;

-- 2019-01-08T14:42:38.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=575933, ColumnName='CustomerRetention', Description=NULL, EntityType='de.metas.contracts', Help=NULL, Name='Customer Retention',Updated=TO_TIMESTAMP('2019-01-08 14:42:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563683
;

-- 2019-01-08T14:42:38.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Customer Retention', Description=NULL, Help=NULL WHERE AD_Column_ID=563683
;


DELETE FROM AD_Element_Link WHERE AD_Tab_ID = 541397;

-- 2019-01-08T14:44:21.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575900,625054,0,541397,123,TO_TIMESTAMP('2019-01-08 14:44:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-01-08 14:44:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-08T14:44:23.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=575933, Name='Customer Retention',Updated=TO_TIMESTAMP('2019-01-08 14:44:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541397
;

-- 2019-01-08T14:44:23.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625054
;

-- 2019-01-08T14:44:23.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575933,625055,0,541397,123,TO_TIMESTAMP('2019-01-08 14:44:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-01-08 14:44:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-08T14:44:24.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(575933) 
;

-- 2019-01-08T14:46:10.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Customer Retention',Updated=TO_TIMESTAMP('2019-01-08 14:46:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554470
;


-- 2019-01-08T14:52:26.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-01-08 14:52:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563683
;

-- 2019-01-08T14:52:29.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-01-08 14:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563681
;




-- 2019-01-08T15:10:33.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='CustomerRetention',Updated=TO_TIMESTAMP('2019-01-08 15:10:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575933
;

-- 2019-01-08T15:10:33.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CustomerRetention', Name='Customer Retention', Description=NULL, Help=NULL WHERE AD_Element_ID=575933
;

-- 2019-01-08T15:10:33.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CustomerRetention', Name='Customer Retention', Description=NULL, Help=NULL, AD_Element_ID=575933 WHERE UPPER(ColumnName)='CUSTOMERRETENTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-08T15:10:33.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CustomerRetention', Name='Customer Retention', Description=NULL, Help=NULL WHERE AD_Element_ID=575933 AND IsCentrallyMaintained='Y'
;






-- 2019-01-08T15:45:18.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='C_CustomerRetention_Threshold',Updated=TO_TIMESTAMP('2019-01-08 15:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541251
;

-- 2019-01-08T15:45:27.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='C_Customer_Retention_Threshold',Updated=TO_TIMESTAMP('2019-01-08 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541251
;


