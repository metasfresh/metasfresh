-------
-- Trl and description

-- 2019-02-05T06:19:31.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=541878 AND AD_Language='it_CH'
;

-- 2019-02-05T06:19:31.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=541878 AND AD_Language='fr_CH'
;

-- 2019-02-05T06:19:31.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=541878 AND AD_Language='en_GB'
;

-- 2019-02-05T06:20:06.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-05 06:20:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Verarbeitungsziel',PrintName='Verarbeitungsziel' WHERE AD_Element_ID=541878 AND AD_Language='de_CH'
;

-- 2019-02-05T06:20:06.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541878,'de_CH') 
;

-- 2019-02-05T06:20:13.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-05 06:20:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Data destination',PrintName='Data destination' WHERE AD_Element_ID=541878 AND AD_Language='en_US'
;

-- 2019-02-05T06:20:13.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541878,'en_US') 
;

-- 2019-02-05T06:21:14.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-05 06:21:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Verarbeitungsziel',PrintName='Verarbeitungsziel',Description='Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll' WHERE AD_Element_ID=541878 AND AD_Language='de_DE'
;

-- 2019-02-05T06:21:14.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541878,'de_DE') 
;

-- 2019-02-05T06:21:14.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541878,'de_DE') 
;

-- 2019-02-05T06:21:14.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_DataDestination_ID', Name='Verarbeitungsziel', Description='Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll', Help=NULL WHERE AD_Element_ID=541878
;

-- 2019-02-05T06:21:14.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_DataDestination_ID', Name='Verarbeitungsziel', Description='Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll', Help=NULL, AD_Element_ID=541878 WHERE UPPER(ColumnName)='AD_DATADESTINATION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-05T06:21:14.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_DataDestination_ID', Name='Verarbeitungsziel', Description='Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll', Help=NULL WHERE AD_Element_ID=541878 AND IsCentrallyMaintained='Y'
;

-- 2019-02-05T06:21:14.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verarbeitungsziel', Description='Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541878) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541878)
;

-- 2019-02-05T06:21:14.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verarbeitungsziel', Name='Verarbeitungsziel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541878)
;

-- 2019-02-05T06:21:14.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verarbeitungsziel', Description='Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541878
;

-- 2019-02-05T06:21:14.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verarbeitungsziel', Description='Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll', Help=NULL WHERE AD_Element_ID = 541878
;

-- 2019-02-05T06:21:14.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Verarbeitungsziel', Description='Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541878
;

-- 2019-02-05T06:21:21.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-05 06:21:21','YYYY-MM-DD HH24:MI:SS'),Description='Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll' WHERE AD_Element_ID=541878 AND AD_Language='de_CH'
;

-- 2019-02-05T06:21:21.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541878,'de_CH') 
;

-- 2019-02-05T06:21:37.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-05 06:21:37','YYYY-MM-DD HH24:MI:SS'),Description='Specifies which part of metasfresh shall process the given record' WHERE AD_Element_ID=541878 AND AD_Language='en_US'
;

-- 2019-02-05T06:21:37.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541878,'en_US') 
;




-------
-- Make it mandatory

UPDATE AD_Column SET IsMandatory='Y', Updated=TO_TIMESTAMP('2019-02-04 17:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547734
;

-- AD_DataDestination_ID=540003 is "DEST.de.metas.ordercandidate"
-- 2019-02-04T17:26:42.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCand SET AD_DataDestination_ID=540003 WHERE AD_DataDestination_ID IS NULL
;

COMMIT;

-- 2019-02-04T17:26:42.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_olcand','AD_DataDestination_ID',null,'NOT NULL',null)
;

