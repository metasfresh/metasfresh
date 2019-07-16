-- 2019-07-15T14:09:00.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576908,0,'qtyDeliveredinPriceUOM',TO_TIMESTAMP('2019-07-15 14:09:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Quantity deliverd in price UOM','Quantity deliverd in price UOM',TO_TIMESTAMP('2019-07-15 14:09:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-15T14:09:00.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576908 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-15T14:10:06.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-15 14:10:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='en_US'
;

-- 2019-07-15T14:10:06.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'en_US') 
;

-- 2019-07-15T14:12:05.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Quantity delivered in price UOM', PrintName='Quantity delivered in price UOM',Updated=TO_TIMESTAMP('2019-07-15 14:12:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='de_CH'
;

-- 2019-07-15T14:12:05.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'de_CH') 
;

-- 2019-07-15T14:12:35.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Quantity delivered in price UOM', PrintName='Quantity delivered in price UOM',Updated=TO_TIMESTAMP('2019-07-15 14:12:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908
;

-- 2019-07-15T14:12:35.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='qtyDeliveredinPriceUOM', Name='Quantity delivered in price UOM', Description=NULL, Help=NULL WHERE AD_Element_ID=576908
;

-- 2019-07-15T14:12:35.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='qtyDeliveredinPriceUOM', Name='Quantity delivered in price UOM', Description=NULL, Help=NULL, AD_Element_ID=576908 WHERE UPPER(ColumnName)='QTYDELIVEREDINPRICEUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-15T14:12:35.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='qtyDeliveredinPriceUOM', Name='Quantity delivered in price UOM', Description=NULL, Help=NULL WHERE AD_Element_ID=576908 AND IsCentrallyMaintained='Y'
;

-- 2019-07-15T14:12:35.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Quantity delivered in price UOM', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576908) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576908)
;

-- 2019-07-15T14:12:35.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Quantity delivered in price UOM', Name='Quantity delivered in price UOM' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576908)
;

-- 2019-07-15T14:12:35.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Quantity delivered in price UOM', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576908
;

-- 2019-07-15T14:12:35.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Quantity delivered in price UOM', Description=NULL, Help=NULL WHERE AD_Element_ID = 576908
;

-- 2019-07-15T14:12:35.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Quantity delivered in price UOM', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576908
;

-- 2019-07-15T14:14:12.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Quantity delivered in price UOM',Updated=TO_TIMESTAMP('2019-07-15 14:14:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='de_DE'
;

-- 2019-07-15T14:14:12.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'de_DE') 
;

-- 2019-07-15T14:14:12.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576908,'de_DE') 
;

-- 2019-07-15T14:14:13.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Quantity delivered in price UOM', Name='Quantity deliverd in price UOM' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576908)
;

-- 2019-07-15T14:14:15.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Quantity delivered in price UOM',Updated=TO_TIMESTAMP('2019-07-15 14:14:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='en_US'
;

-- 2019-07-15T14:14:15.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'en_US') 
;

-- 2019-07-15T14:14:24.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Quantity delivered in price UOM',Updated=TO_TIMESTAMP('2019-07-15 14:14:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='nl_NL'
;

-- 2019-07-15T14:14:24.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'nl_NL') 
;

-- 2019-07-15T14:14:27.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Quantity delivered in price UOM',Updated=TO_TIMESTAMP('2019-07-15 14:14:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='de_DE'
;

-- 2019-07-15T14:14:27.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'de_DE') 
;

-- 2019-07-15T14:14:27.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576908,'de_DE') 
;

-- 2019-07-15T14:14:27.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='qtyDeliveredinPriceUOM', Name='Quantity delivered in price UOM', Description=NULL, Help=NULL WHERE AD_Element_ID=576908
;

-- 2019-07-15T14:14:27.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='qtyDeliveredinPriceUOM', Name='Quantity delivered in price UOM', Description=NULL, Help=NULL, AD_Element_ID=576908 WHERE UPPER(ColumnName)='QTYDELIVEREDINPRICEUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-15T14:14:27.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='qtyDeliveredinPriceUOM', Name='Quantity delivered in price UOM', Description=NULL, Help=NULL WHERE AD_Element_ID=576908 AND IsCentrallyMaintained='Y'
;

-- 2019-07-15T14:14:27.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Quantity delivered in price UOM', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576908) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576908)
;

-- 2019-07-15T14:14:27.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Quantity delivered in price UOM', Name='Quantity delivered in price UOM' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576908)
;

-- 2019-07-15T14:14:27.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Quantity delivered in price UOM', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576908
;

-- 2019-07-15T14:14:27.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Quantity delivered in price UOM', Description=NULL, Help=NULL WHERE AD_Element_ID = 576908
;

-- 2019-07-15T14:14:27.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Quantity delivered in price UOM', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576908
;

-- 2019-07-15T14:14:29.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Quantity delivered in price UOM',Updated=TO_TIMESTAMP('2019-07-15 14:14:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='en_US'
;

-- 2019-07-15T14:14:29.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'en_US') 
;

-- 2019-07-15T14:14:36.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Quantity delivered in price UOM',Updated=TO_TIMESTAMP('2019-07-15 14:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='nl_NL'
;

-- 2019-07-15T14:14:36.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'nl_NL') 
;

-- 2019-07-15T14:16:46.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568421,576908,0,29,500221,'qtyDeliveredinPriceUOM',TO_TIMESTAMP('2019-07-15 14:16:46','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Quantity delivered in price UOM',0,0,TO_TIMESTAMP('2019-07-15 14:16:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-15T14:16:46.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568421 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-15T14:16:46.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576908) 
;

-- 2019-07-15T14:24:00.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Menge in Preiseinheit', PrintName='Gelieferte Menge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-15 14:24:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='de_CH'
;

-- 2019-07-15T14:24:00.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'de_CH') 
;

-- 2019-07-15T14:24:06.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gelieferte Menge in Preiseinheit', PrintName='Gelieferte Menge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-15 14:24:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='de_DE'
;

-- 2019-07-15T14:24:06.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'de_DE') 
;

-- 2019-07-15T14:24:06.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576908,'de_DE') 
;

-- 2019-07-15T14:24:06.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='qtyDeliveredinPriceUOM', Name='Gelieferte Menge in Preiseinheit', Description=NULL, Help=NULL WHERE AD_Element_ID=576908
;

-- 2019-07-15T14:24:06.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='qtyDeliveredinPriceUOM', Name='Gelieferte Menge in Preiseinheit', Description=NULL, Help=NULL, AD_Element_ID=576908 WHERE UPPER(ColumnName)='QTYDELIVEREDINPRICEUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-15T14:24:06.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='qtyDeliveredinPriceUOM', Name='Gelieferte Menge in Preiseinheit', Description=NULL, Help=NULL WHERE AD_Element_ID=576908 AND IsCentrallyMaintained='Y'
;

-- 2019-07-15T14:24:06.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gelieferte Menge in Preiseinheit', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576908) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576908)
;

-- 2019-07-15T14:24:06.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gelieferte Menge in Preiseinheit', Name='Gelieferte Menge in Preiseinheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576908)
;

-- 2019-07-15T14:24:06.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gelieferte Menge in Preiseinheit', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576908
;

-- 2019-07-15T14:24:06.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gelieferte Menge in Preiseinheit', Description=NULL, Help=NULL WHERE AD_Element_ID = 576908
;

-- 2019-07-15T14:24:06.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gelieferte Menge in Preiseinheit', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576908
;

-- 2019-07-15T14:24:09.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-15 14:24:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908 AND AD_Language='de_CH'
;

-- 2019-07-15T14:24:09.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576908,'de_CH') 
;

-- 2019-07-15T19:31:17.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568426,576908,0,29,320,'QtyDeliveredInPriceUOM',TO_TIMESTAMP('2019-07-15 19:31:17','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Gelieferte Menge in Preiseinheit',0,0,TO_TIMESTAMP('2019-07-15 19:31:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-15T19:31:17.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568426 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-15T19:31:17.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576908) 
;

-- 2019-07-15T19:31:36.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN QtyDeliveredInPriceUOM NUMERIC')
;
