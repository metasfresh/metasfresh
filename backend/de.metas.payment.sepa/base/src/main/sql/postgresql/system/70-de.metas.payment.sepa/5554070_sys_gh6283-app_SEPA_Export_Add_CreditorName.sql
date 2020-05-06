
-- 2020-03-06T14:27:35.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='SEPA Gläubiger-ID', PrintName='SEPA Gläubiger-ID',Updated=TO_TIMESTAMP('2020-03-06 15:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542478 AND AD_Language='de_CH'
;

-- 2020-03-06T14:27:35.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542478,'de_CH') 
;

-- 2020-03-06T14:27:44.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='SEPA Gläubiger-ID', PrintName='SEPA Gläubiger-ID',Updated=TO_TIMESTAMP('2020-03-06 15:27:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542478 AND AD_Language='de_DE'
;

-- 2020-03-06T14:27:44.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542478,'de_DE') 
;

-- 2020-03-06T14:27:44.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542478,'de_DE') 
;

-- 2020-03-06T14:27:44.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SEPA_CreditorIdentifier', Name='SEPA Gläubiger-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=542478
;

-- 2020-03-06T14:27:44.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SEPA_CreditorIdentifier', Name='SEPA Gläubiger-ID', Description=NULL, Help=NULL, AD_Element_ID=542478 WHERE UPPER(ColumnName)='SEPA_CREDITORIDENTIFIER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-03-06T14:27:44.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SEPA_CreditorIdentifier', Name='SEPA Gläubiger-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=542478 AND IsCentrallyMaintained='Y'
;

-- 2020-03-06T14:27:44.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='SEPA Gläubiger-ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542478) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542478)
;

-- 2020-03-06T14:27:44.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='SEPA Gläubiger-ID', Name='SEPA Gläubiger-ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542478)
;

-- 2020-03-06T14:27:44.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='SEPA Gläubiger-ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542478
;

-- 2020-03-06T14:27:44.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='SEPA Gläubiger-ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 542478
;

-- 2020-03-06T14:27:44.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'SEPA Gläubiger-ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542478
;

-- 2020-03-06T14:29:45.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gläubiger-Identifikationsnummer', PrintName='Gläubiger-Identifikationsnummer',Updated=TO_TIMESTAMP('2020-03-06 15:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542478 AND AD_Language='de_CH'
;

-- 2020-03-06T14:29:45.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542478,'de_CH') 
;

-- 2020-03-06T14:29:50.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gläubiger-Identifikationsnummer', PrintName='Gläubiger-Identifikationsnummer',Updated=TO_TIMESTAMP('2020-03-06 15:29:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542478 AND AD_Language='de_DE'
;

-- 2020-03-06T14:29:50.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542478,'de_DE') 
;

-- 2020-03-06T14:29:50.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542478,'de_DE') 
;

-- 2020-03-06T14:29:50.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SEPA_CreditorIdentifier', Name='Gläubiger-Identifikationsnummer', Description=NULL, Help=NULL WHERE AD_Element_ID=542478
;

-- 2020-03-06T14:29:50.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SEPA_CreditorIdentifier', Name='Gläubiger-Identifikationsnummer', Description=NULL, Help=NULL, AD_Element_ID=542478 WHERE UPPER(ColumnName)='SEPA_CREDITORIDENTIFIER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-03-06T14:29:50.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SEPA_CreditorIdentifier', Name='Gläubiger-Identifikationsnummer', Description=NULL, Help=NULL WHERE AD_Element_ID=542478 AND IsCentrallyMaintained='Y'
;

-- 2020-03-06T14:29:50.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gläubiger-Identifikationsnummer', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542478) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542478)
;

-- 2020-03-06T14:29:50.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gläubiger-Identifikationsnummer', Name='Gläubiger-Identifikationsnummer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542478)
;

-- 2020-03-06T14:29:50.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gläubiger-Identifikationsnummer', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542478
;

-- 2020-03-06T14:29:50.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gläubiger-Identifikationsnummer', Description=NULL, Help=NULL WHERE AD_Element_ID = 542478
;

-- 2020-03-06T14:29:50.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gläubiger-Identifikationsnummer', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542478
;

-- 2020-03-06T14:30:51.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577590,0,'SEPA_CreditorName',TO_TIMESTAMP('2020-03-06 15:30:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Gläubigername','Gläubigername',TO_TIMESTAMP('2020-03-06 15:30:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-06T14:30:51.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577590 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-03-06T14:30:55.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-03-06 15:30:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577590 AND AD_Language='de_CH'
;

-- 2020-03-06T14:30:55.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577590,'de_CH') 
;

-- 2020-03-06T14:30:57.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-03-06 15:30:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577590 AND AD_Language='de_DE'
;

-- 2020-03-06T14:30:57.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577590,'de_DE') 
;

-- 2020-03-06T14:30:57.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577590,'de_DE') 
;

-- 2020-03-06T14:31:04.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Creditor name', PrintName='Creditor name',Updated=TO_TIMESTAMP('2020-03-06 15:31:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577590 AND AD_Language='en_US'
;

-- 2020-03-06T14:31:04.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577590,'en_US') 
;

-- 2020-03-06T14:31:33.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=1024,Updated=TO_TIMESTAMP('2020-03-06 15:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550908
;

-- 2020-03-06T14:31:33.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_CreditorIdentifier','VARCHAR(1024)',null,null)
;

-- 2020-03-06T14:31:53.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=35,Updated=TO_TIMESTAMP('2020-03-06 15:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550908
;

-- 2020-03-06T14:31:53.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_CreditorIdentifier','VARCHAR(35)',null,null)
;

-- 2020-03-06T14:32:10.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570097,577590,0,10,540603,'SEPA_CreditorName',TO_TIMESTAMP('2020-03-06 15:32:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sepa',1024,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Gläubigername',0,0,TO_TIMESTAMP('2020-03-06 15:32:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-03-06T14:32:10.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570097 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-03-06T14:32:10.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577590) 
;

-- 2020-03-06T14:32:12.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('SEPA_Export','ALTER TABLE public.SEPA_Export ADD COLUMN SEPA_CreditorName VARCHAR(1024)')
;


-- 2020-03-06T14:32:31.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2020-03-06 15:32:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570097
;

-- 2020-03-06T14:32:31.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_CreditorName','VARCHAR(1024)',null,null)
;

-- 2020-03-06T14:32:37.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_CreditorName','VARCHAR(1024)',null,null)
;

-- 2020-03-06T14:38:07.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=60,Updated=TO_TIMESTAMP('2020-03-06 15:38:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570097
;

-- 2020-03-06T14:38:08.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_CreditorName','VARCHAR(60)',null,null)
;
