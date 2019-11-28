-- 2019-11-28T13:35:31.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577399,0,'LastSaleInvoicableQtyBasedOn',TO_TIMESTAMP('2019-11-28 14:35:31','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','Abr. Menge basierte auf','Abr. Menge basierte auf',TO_TIMESTAMP('2019-11-28 14:35:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-28T13:35:31.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577399 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-11-28T13:35:36.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-28 14:35:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577399 AND AD_Language='de_CH'
;

-- 2019-11-28T13:35:36.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577399,'de_CH') 
;

-- 2019-11-28T13:35:38.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-28 14:35:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577399 AND AD_Language='de_DE'
;

-- 2019-11-28T13:35:38.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577399,'de_DE') 
;

-- 2019-11-28T13:35:38.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577399,'de_DE') 
;

-- 2019-11-28T13:36:07.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoicable quantity per', PrintName='Invoicable quantity per',Updated=TO_TIMESTAMP('2019-11-28 14:36:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='en_US'
;

-- 2019-11-28T13:36:07.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'en_US') 
;

-- 2019-11-28T13:36:25.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoicable quantity per', PrintName='Invoicable quantity per',Updated=TO_TIMESTAMP('2019-11-28 14:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577399 AND AD_Language='en_US'
;

-- 2019-11-28T13:36:25.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577399,'en_US') 
;

-- 2019-11-28T13:37:17.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569686,577399,0,17,541023,541171,'LastSaleInvoicableQtyBasedOn',TO_TIMESTAMP('2019-11-28 14:37:17','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',11,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Abr. Menge basierte auf',0,0,TO_TIMESTAMP('2019-11-28 14:37:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-11-28T13:37:17.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569686 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-28T13:37:17.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577399) 
;

-- 2019-11-28T13:37:22.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product_Stats','ALTER TABLE public.C_BPartner_Product_Stats ADD COLUMN LastSaleInvoicableQtyBasedOn VARCHAR(11)')
;

-- 2019-11-28T13:38:30.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='LastSalesInvoicableQtyBasedOn',Updated=TO_TIMESTAMP('2019-11-28 14:38:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577399
;

-- 2019-11-28T13:38:30.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSalesInvoicableQtyBasedOn', Name='Abr. Menge basierte auf', Description='', Help=NULL WHERE AD_Element_ID=577399
;

-- 2019-11-28T13:38:30.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSalesInvoicableQtyBasedOn', Name='Abr. Menge basierte auf', Description='', Help=NULL, AD_Element_ID=577399 WHERE UPPER(ColumnName)='LASTSALESINVOICABLEQTYBASEDON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-28T13:38:30.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSalesInvoicableQtyBasedOn', Name='Abr. Menge basierte auf', Description='', Help=NULL WHERE AD_Element_ID=577399 AND IsCentrallyMaintained='Y'
;

/* DDL */ SELECT public.db_alter_table('C_BPartner_Product_Stats','ALTER TABLE public.C_BPartner_Product_Stats RENAME COLUMN LastSaleInvoicableQtyBasedOn TO LastSalesInvoicableQtyBasedOn')
;

-- 2019-11-28T14:51:20.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TechnicalNote='This view is used by metasfresh to create and update BPartner-Product-Stats',Updated=TO_TIMESTAMP('2019-11-28 15:51:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541172
;

-- 2019-11-28T14:51:26.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TechnicalNote='This view is used by metasfresh to create and update BPartner-Product-Stats',Updated=TO_TIMESTAMP('2019-11-28 15:51:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541177
;

-- 2019-11-28T15:35:11.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsSOTrx/''N''@=''Y''',Updated=TO_TIMESTAMP('2019-11-28 16:35:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569684
;

