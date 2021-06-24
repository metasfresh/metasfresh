-- 2021-06-15T13:39:28.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579351,0,'ExternalPurchaseOrderURL',TO_TIMESTAMP('2021-06-15 16:39:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ExternalPurchaseOrderURL','ExternalPurchaseOrderURL',TO_TIMESTAMP('2021-06-15 16:39:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-15T13:39:28.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579351 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-15T13:41:03.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574446,579351,0,40,540861,'ExternalPurchaseOrderURL',TO_TIMESTAMP('2021-06-15 16:41:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.purchasecandidate',0,1000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ExternalPurchaseOrderURL',0,0,TO_TIMESTAMP('2021-06-15 16:41:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-15T13:41:03.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574446 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-15T13:41:03.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579351) 
;

-- 2021-06-15T13:41:04.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN ExternalPurchaseOrderURL VARCHAR(1000)')
;

-- 2021-06-15T13:42:35.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574447,579351,0,40,259,'ExternalPurchaseOrderURL',TO_TIMESTAMP('2021-06-15 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ExternalPurchaseOrderURL',0,0,TO_TIMESTAMP('2021-06-15 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-15T13:42:35.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574447 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-15T13:42:35.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579351) 
;

-- 2021-06-15T13:42:35.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN ExternalPurchaseOrderURL VARCHAR(1000)')
;

-- 2021-06-15T13:43:31.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574448,577603,0,40,541486,'ExternalReferenceURL',TO_TIMESTAMP('2021-06-15 16:43:31','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalreference',0,1000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ExternalReferenceURL',0,0,TO_TIMESTAMP('2021-06-15 16:43:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-15T13:43:31.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574448 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-15T13:43:31.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577603) 
;

-- 2021-06-15T13:43:32.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('S_ExternalReference','ALTER TABLE public.S_ExternalReference ADD COLUMN ExternalReferenceURL VARCHAR(1000)')
;

-- 2021-06-15T13:55:25.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='URL in external system', PrintName='URL in external system',Updated=TO_TIMESTAMP('2021-06-15 16:55:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577603 AND AD_Language='en_US'
;

-- 2021-06-15T13:55:25.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577603,'en_US') 
;

-- 2021-06-15T13:55:45.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='URL im externen system', PrintName='URL im externen system',Updated=TO_TIMESTAMP('2021-06-15 16:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577603 AND AD_Language='nl_NL'
;

-- 2021-06-15T13:55:45.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577603,'nl_NL') 
;

-- 2021-06-15T13:55:49.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='URL im externen system', PrintName='URL im externen system',Updated=TO_TIMESTAMP('2021-06-15 16:55:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577603 AND AD_Language='de_DE'
;

-- 2021-06-15T13:55:49.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577603,'de_DE') 
;

-- 2021-06-15T13:55:49.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577603,'de_DE') 
;

-- 2021-06-15T13:55:49.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalReferenceURL', Name='URL im externen system', Description=NULL, Help=NULL WHERE AD_Element_ID=577603
;

-- 2021-06-15T13:55:49.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalReferenceURL', Name='URL im externen system', Description=NULL, Help=NULL, AD_Element_ID=577603 WHERE UPPER(ColumnName)='EXTERNALREFERENCEURL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-15T13:55:49.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalReferenceURL', Name='URL im externen system', Description=NULL, Help=NULL WHERE AD_Element_ID=577603 AND IsCentrallyMaintained='Y'
;

-- 2021-06-15T13:55:49.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='URL im externen system', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577603) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577603)
;

-- 2021-06-15T13:55:49.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='URL im externen system', Name='URL im externen system' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577603)
;

-- 2021-06-15T13:55:49.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='URL im externen system', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577603
;

-- 2021-06-15T13:55:49.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='URL im externen system', Description=NULL, Help=NULL WHERE AD_Element_ID = 577603
;

-- 2021-06-15T13:55:49.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'URL im externen system', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577603
;

-- 2021-06-15T13:55:53.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='URL im externen system', PrintName='URL im externen system',Updated=TO_TIMESTAMP('2021-06-15 16:55:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577603 AND AD_Language='de_CH'
;

-- 2021-06-15T13:55:53.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577603,'de_CH') 
;

-- 2021-06-15T13:57:32.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If a record was synched from an external system, this field can be used to store its URL',Updated=TO_TIMESTAMP('2021-06-15 16:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577603 AND AD_Language='en_US'
;

-- 2021-06-15T13:57:32.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577603,'en_US') 
;

-- 2021-06-15T13:57:36.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten',Updated=TO_TIMESTAMP('2021-06-15 16:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577603 AND AD_Language='de_DE'
;

-- 2021-06-15T13:57:36.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577603,'de_DE') 
;

-- 2021-06-15T13:57:36.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577603,'de_DE') 
;

-- 2021-06-15T13:57:36.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalReferenceURL', Name='URL im externen system', Description='Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL WHERE AD_Element_ID=577603
;

-- 2021-06-15T13:57:36.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalReferenceURL', Name='URL im externen system', Description='Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL, AD_Element_ID=577603 WHERE UPPER(ColumnName)='EXTERNALREFERENCEURL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-15T13:57:36.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalReferenceURL', Name='URL im externen system', Description='Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL WHERE AD_Element_ID=577603 AND IsCentrallyMaintained='Y'
;

-- 2021-06-15T13:57:36.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='URL im externen system', Description='Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577603) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577603)
;

-- 2021-06-15T13:57:36.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='URL im externen system', Description='Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577603
;

-- 2021-06-15T13:57:36.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='URL im externen system', Description='Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL WHERE AD_Element_ID = 577603
;

-- 2021-06-15T13:57:36.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'URL im externen system', Description = 'Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577603
;

-- 2021-06-15T13:57:39.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten',Updated=TO_TIMESTAMP('2021-06-15 16:57:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577603 AND AD_Language='de_CH'
;

-- 2021-06-15T13:57:39.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577603,'de_CH') 
;

-- 2021-06-15T13:57:47.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten',Updated=TO_TIMESTAMP('2021-06-15 16:57:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577603 AND AD_Language='nl_NL'
;

-- 2021-06-15T13:57:47.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577603,'nl_NL') 
;

-- 2021-06-15T13:58:52.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bestell-URL im externen system', PrintName='Bestell-URL im externen system',Updated=TO_TIMESTAMP('2021-06-15 16:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579351 AND AD_Language='de_DE'
;

-- 2021-06-15T13:58:52.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579351,'de_DE') 
;

-- 2021-06-15T13:58:52.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579351,'de_DE') 
;

-- 2021-06-15T13:58:52.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalPurchaseOrderURL', Name='Bestell-URL im externen system', Description=NULL, Help=NULL WHERE AD_Element_ID=579351
;

-- 2021-06-15T13:58:52.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalPurchaseOrderURL', Name='Bestell-URL im externen system', Description=NULL, Help=NULL, AD_Element_ID=579351 WHERE UPPER(ColumnName)='EXTERNALPURCHASEORDERURL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-15T13:58:52.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalPurchaseOrderURL', Name='Bestell-URL im externen system', Description=NULL, Help=NULL WHERE AD_Element_ID=579351 AND IsCentrallyMaintained='Y'
;

-- 2021-06-15T13:58:52.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bestell-URL im externen system', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579351) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579351)
;

-- 2021-06-15T13:58:52.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bestell-URL im externen system', Name='Bestell-URL im externen system' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579351)
;

-- 2021-06-15T13:58:52.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Bestell-URL im externen system', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579351
;

-- 2021-06-15T13:58:52.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Bestell-URL im externen system', Description=NULL, Help=NULL WHERE AD_Element_ID = 579351
;

-- 2021-06-15T13:58:52.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Bestell-URL im externen system', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579351
;

-- 2021-06-15T13:58:57.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bestell-URL im externen system', PrintName='Bestell-URL im externen system',Updated=TO_TIMESTAMP('2021-06-15 16:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579351 AND AD_Language='nl_NL'
;

-- 2021-06-15T13:58:57.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579351,'nl_NL') 
;

-- 2021-06-15T13:59:02.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bestell-URL im externen system', PrintName='Bestell-URL im externen system',Updated=TO_TIMESTAMP('2021-06-15 16:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579351 AND AD_Language='de_CH'
;

-- 2021-06-15T13:59:02.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579351,'de_CH') 
;

-- 2021-06-15T13:59:11.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='URL of the purchase order in an external system', PrintName='URL of the purchase order in an external system',Updated=TO_TIMESTAMP('2021-06-15 16:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579351 AND AD_Language='en_US'
;

-- 2021-06-15T13:59:11.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579351,'en_US') 
;

-- 2021-06-15T13:59:27.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If a purchase order was synched from an external system, this field can be used to store its URL',Updated=TO_TIMESTAMP('2021-06-15 16:59:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579351 AND AD_Language='en_US'
;

-- 2021-06-15T13:59:27.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579351,'en_US') 
;

-- 2021-06-15T13:59:33.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten',Updated=TO_TIMESTAMP('2021-06-15 16:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579351 AND AD_Language='nl_NL'
;

-- 2021-06-15T13:59:33.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579351,'nl_NL') 
;

-- 2021-06-15T13:59:36.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten',Updated=TO_TIMESTAMP('2021-06-15 16:59:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579351 AND AD_Language='de_DE'
;

-- 2021-06-15T13:59:36.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579351,'de_DE') 
;

-- 2021-06-15T13:59:36.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579351,'de_DE') 
;

-- 2021-06-15T13:59:36.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalPurchaseOrderURL', Name='Bestell-URL im externen system', Description='Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL WHERE AD_Element_ID=579351
;

-- 2021-06-15T13:59:36.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalPurchaseOrderURL', Name='Bestell-URL im externen system', Description='Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL, AD_Element_ID=579351 WHERE UPPER(ColumnName)='EXTERNALPURCHASEORDERURL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-15T13:59:36.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalPurchaseOrderURL', Name='Bestell-URL im externen system', Description='Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL WHERE AD_Element_ID=579351 AND IsCentrallyMaintained='Y'
;

-- 2021-06-15T13:59:36.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bestell-URL im externen system', Description='Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579351) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579351)
;

-- 2021-06-15T13:59:36.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Bestell-URL im externen system', Description='Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579351
;

-- 2021-06-15T13:59:36.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Bestell-URL im externen system', Description='Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', Help=NULL WHERE AD_Element_ID = 579351
;

-- 2021-06-15T13:59:36.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Bestell-URL im externen system', Description = 'Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579351
;

-- 2021-06-15T13:59:39.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten',Updated=TO_TIMESTAMP('2021-06-15 16:59:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579351 AND AD_Language='de_CH'
;

-- 2021-06-15T13:59:39.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579351,'de_CH') 
;

-- 2021-06-15T14:04:19.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574448,647937,0,542376,0,TO_TIMESTAMP('2021-06-15 17:04:19','YYYY-MM-DD HH24:MI:SS'),100,'Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten',0,'D',0,'Y','Y','Y','N','N','N','N','N','URL im externen system',20,20,0,1,1,TO_TIMESTAMP('2021-06-15 17:04:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-15T14:04:19.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647937 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-15T14:04:19.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577603) 
;

-- 2021-06-15T14:04:19.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647937
;

-- 2021-06-15T14:04:19.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647937)
;

-- 2021-06-15T14:05:22.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,647937,0,542376,585968,543614,'F',TO_TIMESTAMP('2021-06-15 17:05:22','YYYY-MM-DD HH24:MI:SS'),100,'Wenn ein Datensatz aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten','Y','N','N','Y','N','N','N',0,'URL im externen system',70,0,0,TO_TIMESTAMP('2021-06-15 17:05:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-15T14:07:16.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574446,647938,0,540894,0,TO_TIMESTAMP('2021-06-15 17:07:16','YYYY-MM-DD HH24:MI:SS'),100,'Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten',0,'D',0,'Y','Y','Y','N','N','N','N','N','Bestell-URL im externen system',300,310,0,1,1,TO_TIMESTAMP('2021-06-15 17:07:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-15T14:07:16.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647938 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-15T14:07:16.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579351) 
;

-- 2021-06-15T14:07:16.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647938
;

-- 2021-06-15T14:07:16.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647938)
;

-- 2021-06-15T14:09:50.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,647938,0,540894,585969,541247,'F',TO_TIMESTAMP('2021-06-15 17:09:50','YYYY-MM-DD HH24:MI:SS'),100,'Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten','Y','N','N','Y','N','N','N',0,'Bestell-URL im externen system',50,0,0,TO_TIMESTAMP('2021-06-15 17:09:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-15T14:10:24.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574447,647939,0,294,0,TO_TIMESTAMP('2021-06-15 17:10:24','YYYY-MM-DD HH24:MI:SS'),100,'Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten',0,'D',0,'Y','Y','Y','N','N','N','N','N','Bestell-URL im externen system',580,210,0,1,1,TO_TIMESTAMP('2021-06-15 17:10:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-15T14:10:24.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647939 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-15T14:10:24.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579351) 
;

-- 2021-06-15T14:10:24.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647939
;

-- 2021-06-15T14:10:24.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647939)
;

-- 2021-06-15T14:11:31.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,647939,0,294,585970,540961,'F',TO_TIMESTAMP('2021-06-15 17:11:31','YYYY-MM-DD HH24:MI:SS'),100,'Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten','Y','N','N','Y','N','N','N',0,'Bestell-URL im externen system',290,0,0,TO_TIMESTAMP('2021-06-15 17:11:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-15T14:21:09.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2021-06-15 17:21:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585970
;

-- 2021-06-15T14:22:51.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2021-06-15 17:22:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585968
;

-- 2021-06-15T14:24:01.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540720,546025,TO_TIMESTAMP('2021-06-15 17:24:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','ref',40,TO_TIMESTAMP('2021-06-15 17:24:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-15T14:24:11.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=25,Updated=TO_TIMESTAMP('2021-06-15 17:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=546025
;

-- 2021-06-15T14:24:44.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,647938,0,540894,585971,546025,'F',TO_TIMESTAMP('2021-06-15 17:24:44','YYYY-MM-DD HH24:MI:SS'),100,'Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten','Y','Y','N','Y','N','N','N',0,'Bestell-URL im externen system',10,0,0,TO_TIMESTAMP('2021-06-15 17:24:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-15T14:25:13.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=585969
;