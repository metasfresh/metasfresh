-- 2022-02-15T07:30:29.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580583,0,'IsAutoSendWhenCreatedByUserGroup',TO_TIMESTAMP('2022-02-15 09:30:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Auto-send if created by users group','Auto-send if created by users group',TO_TIMESTAMP('2022-02-15 09:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-15T07:30:29.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580583 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-02-15T07:30:53.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If a business bartner was created by a user from the given group, then it is automatically send.',Updated=TO_TIMESTAMP('2022-02-15 09:30:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580583 AND AD_Language='en_US'
;

-- 2022-02-15T07:30:53.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580583,'en_US') 
;

-- 2022-02-15T07:31:07.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Name='Erstellt von Nutzergrp. autom. senden ', PrintName='Erstellt von Nutzergrp. autom. senden ',Updated=TO_TIMESTAMP('2022-02-15 09:31:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580583 AND AD_Language='de_DE'
;

-- 2022-02-15T07:31:07.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580583,'de_DE') 
;

-- 2022-02-15T07:31:07.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580583,'de_DE') 
;

-- 2022-02-15T07:31:07.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutoSendWhenCreatedByUserGroup', Name='Erstellt von Nutzergrp. autom. senden ', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL WHERE AD_Element_ID=580583
;

-- 2022-02-15T07:31:07.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSendWhenCreatedByUserGroup', Name='Erstellt von Nutzergrp. autom. senden ', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL, AD_Element_ID=580583 WHERE UPPER(ColumnName)='ISAUTOSENDWHENCREATEDBYUSERGROUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-02-15T07:31:07.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSendWhenCreatedByUserGroup', Name='Erstellt von Nutzergrp. autom. senden ', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL WHERE AD_Element_ID=580583 AND IsCentrallyMaintained='Y'
;

-- 2022-02-15T07:31:07.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erstellt von Nutzergrp. autom. senden ', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580583) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580583)
;

-- 2022-02-15T07:31:07.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erstellt von Nutzergrp. autom. senden ', Name='Erstellt von Nutzergrp. autom. senden ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580583)
;

-- 2022-02-15T07:31:07.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erstellt von Nutzergrp. autom. senden ', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580583
;

-- 2022-02-15T07:31:07.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erstellt von Nutzergrp. autom. senden ', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL WHERE AD_Element_ID = 580583
;

-- 2022-02-15T07:31:07.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erstellt von Nutzergrp. autom. senden ', Description = 'Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580583
;

-- 2022-02-15T07:31:16.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Name='Erstellt von Nutzergrp. autom. senden ', PrintName='Erstellt von Nutzergrp. autom. senden ',Updated=TO_TIMESTAMP('2022-02-15 09:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580583 AND AD_Language='de_CH'
;

-- 2022-02-15T07:31:16.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580583,'de_CH') 
;

-- 2022-02-15T07:31:28.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Name='Erstellt von Nutzergrp. autom. senden', PrintName='Erstellt von Nutzergrp. autom. senden',Updated=TO_TIMESTAMP('2022-02-15 09:31:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580583 AND AD_Language='nl_NL'
;

-- 2022-02-15T07:31:28.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580583,'nl_NL') 
;

-- 2022-02-15T07:31:36.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Erstellt von Nutzergrp. autom. senden', PrintName='Erstellt von Nutzergrp. autom. senden',Updated=TO_TIMESTAMP('2022-02-15 09:31:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580583 AND AD_Language='de_DE'
;

-- 2022-02-15T07:31:36.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580583,'de_DE') 
;

-- 2022-02-15T07:31:36.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580583,'de_DE') 
;

-- 2022-02-15T07:31:36.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutoSendWhenCreatedByUserGroup', Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL WHERE AD_Element_ID=580583
;

-- 2022-02-15T07:31:36.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSendWhenCreatedByUserGroup', Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL, AD_Element_ID=580583 WHERE UPPER(ColumnName)='ISAUTOSENDWHENCREATEDBYUSERGROUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-02-15T07:31:36.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSendWhenCreatedByUserGroup', Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL WHERE AD_Element_ID=580583 AND IsCentrallyMaintained='Y'
;

-- 2022-02-15T07:31:36.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580583) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580583)
;

-- 2022-02-15T07:31:36.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erstellt von Nutzergrp. autom. senden', Name='Erstellt von Nutzergrp. autom. senden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580583)
;

-- 2022-02-15T07:31:36.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580583
;

-- 2022-02-15T07:31:36.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', Help=NULL WHERE AD_Element_ID = 580583
;

-- 2022-02-15T07:31:36.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erstellt von Nutzergrp. autom. senden', Description = 'Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580583
;

-- 2022-02-15T07:31:44.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Erstellt von Nutzergrp. autom. senden', PrintName='Erstellt von Nutzergrp. autom. senden',Updated=TO_TIMESTAMP('2022-02-15 09:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580583 AND AD_Language='de_CH'
;

-- 2022-02-15T07:31:44.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580583,'de_CH') 
;

-- 2022-02-15T07:32:06.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579253,580583,0,20,541803,'IsAutoSendWhenCreatedByUserGroup',TO_TIMESTAMP('2022-02-15 09:32:06','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Erstellt von Nutzergrp. autom. senden',0,0,TO_TIMESTAMP('2022-02-15 09:32:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-02-15T07:32:06.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579253 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-02-15T07:32:06.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580583) 
;

-- 2022-02-15T07:32:23.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_RabbitMQ_HTTP','ALTER TABLE public.ExternalSystem_Config_RabbitMQ_HTTP ADD COLUMN IsAutoSendWhenCreatedByUserGroup CHAR(1) DEFAULT ''N'' CHECK (IsAutoSendWhenCreatedByUserGroup IN (''Y'',''N'')) NOT NULL')
;

-- 2022-02-15T07:34:19.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580584,0,'BPartnerCreatedByUserGroup_ID',TO_TIMESTAMP('2022-02-15 09:34:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Users Group','Users Group',TO_TIMESTAMP('2022-02-15 09:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-15T07:34:19.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580584 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-02-15T07:34:29.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nutzergruppe', PrintName='Nutzergruppe',Updated=TO_TIMESTAMP('2022-02-15 09:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580584 AND AD_Language='de_CH'
;

-- 2022-02-15T07:34:29.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580584,'de_CH') 
;

-- 2022-02-15T07:34:33.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nutzergruppe', PrintName='Nutzergruppe',Updated=TO_TIMESTAMP('2022-02-15 09:34:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580584 AND AD_Language='de_DE'
;

-- 2022-02-15T07:34:33.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580584,'de_DE') 
;

-- 2022-02-15T07:34:33.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580584,'de_DE') 
;

-- 2022-02-15T07:34:33.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPartnerCreatedByUserGroup_ID', Name='Nutzergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=580584
;

-- 2022-02-15T07:34:33.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerCreatedByUserGroup_ID', Name='Nutzergruppe', Description=NULL, Help=NULL, AD_Element_ID=580584 WHERE UPPER(ColumnName)='BPARTNERCREATEDBYUSERGROUP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-02-15T07:34:33.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerCreatedByUserGroup_ID', Name='Nutzergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=580584 AND IsCentrallyMaintained='Y'
;

-- 2022-02-15T07:34:33.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nutzergruppe', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580584) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580584)
;

-- 2022-02-15T07:34:33.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Nutzergruppe', Name='Nutzergruppe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580584)
;

-- 2022-02-15T07:34:33.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nutzergruppe', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580584
;

-- 2022-02-15T07:34:33.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nutzergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID = 580584
;

-- 2022-02-15T07:34:33.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nutzergruppe', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580584
;

-- 2022-02-15T07:34:36.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nutzergruppe', PrintName='Nutzergruppe',Updated=TO_TIMESTAMP('2022-02-15 09:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580584 AND AD_Language='nl_NL'
;

-- 2022-02-15T07:34:36.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580584,'nl_NL') 
;

-- 2022-02-15T07:35:42.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579254,580584,0,18,541355,541803,'BPartnerCreatedByUserGroup_ID',TO_TIMESTAMP('2022-02-15 09:35:42','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@IsAutoSendWhenCreatedByUserGroup@=Y',0,'Nutzergruppe',0,0,TO_TIMESTAMP('2022-02-15 09:35:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-02-15T07:35:42.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579254 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-02-15T07:35:42.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580584) 
;

-- 2022-02-15T07:35:45.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_RabbitMQ_HTTP','ALTER TABLE public.ExternalSystem_Config_RabbitMQ_HTTP ADD COLUMN BPartnerCreatedByUserGroup_ID NUMERIC(10)')
;

-- 2022-02-15T07:35:45.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE ExternalSystem_Config_RabbitMQ_HTTP ADD CONSTRAINT BPartnerCreatedByUserGroup_ExternalSystemConfigRabbitMQHTTP FOREIGN KEY (BPartnerCreatedByUserGroup_ID) REFERENCES public.AD_UserGroup DEFERRABLE INITIALLY DEFERRED
;

-- 2022-02-15T07:36:18.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579253,680055,0,544410,TO_TIMESTAMP('2022-02-15 09:36:18','YYYY-MM-DD HH24:MI:SS'),100,'Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.',1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Erstellt von Nutzergrp. autom. senden',TO_TIMESTAMP('2022-02-15 09:36:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-15T07:36:18.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=680055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-02-15T07:36:18.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580583) 
;

-- 2022-02-15T07:36:18.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=680055
;

-- 2022-02-15T07:36:18.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(680055)
;

-- 2022-02-15T07:36:18.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579254,680056,0,544410,TO_TIMESTAMP('2022-02-15 09:36:18','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Nutzergruppe',TO_TIMESTAMP('2022-02-15 09:36:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-15T07:36:18.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=680056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-02-15T07:36:18.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580584) 
;

-- 2022-02-15T07:36:18.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=680056
;

-- 2022-02-15T07:36:18.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(680056)
;

-- 2022-02-15T07:37:39.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsAutoSendWhenCreatedByUserGroup@=''Y''',Updated=TO_TIMESTAMP('2022-02-15 09:37:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680056
;

-- 2022-02-15T07:38:00.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=546533, SeqNo=20,Updated=TO_TIMESTAMP('2022-02-15 09:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589827
;

-- 2022-02-15T07:38:10.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544336,548110,TO_TIMESTAMP('2022-02-15 09:38:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','configs',20,TO_TIMESTAMP('2022-02-15 09:38:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-15T07:38:20.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,680056,0,544410,601118,548110,'F',TO_TIMESTAMP('2022-02-15 09:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Nutzergruppe',10,0,0,TO_TIMESTAMP('2022-02-15 09:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-15T07:38:38.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,680055,0,544410,601119,546533,'F',TO_TIMESTAMP('2022-02-15 09:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Wenn ein Geschäftspartner von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird er automatisch gesendet.','Y','N','N','Y','N','N','N',0,'Erstellt von Nutzergrp. autom. senden',30,0,0,TO_TIMESTAMP('2022-02-15 09:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-15T15:34:42.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSyncBPartnersToRabbitMQ@=''Y''',Updated=TO_TIMESTAMP('2022-02-15 17:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680055
;

-- 2022-02-16T11:40:10.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=544337, SeqNo=30,Updated=TO_TIMESTAMP('2022-02-16 13:40:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=548110
;

-- 2022-02-16T11:40:30.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=15,Updated=TO_TIMESTAMP('2022-02-16 13:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=548110
;

