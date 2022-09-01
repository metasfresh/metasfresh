-- 2022-01-06T17:50:30.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt Sperre Verkauf', PrintName='Produkt Sperre Verkauf',Updated=TO_TIMESTAMP('2022-01-06 19:50:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543955 AND AD_Language='de_DE'
;

-- 2022-01-06T17:50:30.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543955,'de_DE') 
;

-- 2022-01-06T17:50:30.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543955,'de_DE') 
;

-- 2022-01-06T17:50:30.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExcludedFromSale', Name='Produkt Sperre Verkauf', Description=NULL, Help=NULL WHERE AD_Element_ID=543955
;

-- 2022-01-06T17:50:30.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludedFromSale', Name='Produkt Sperre Verkauf', Description=NULL, Help=NULL, AD_Element_ID=543955 WHERE UPPER(ColumnName)='ISEXCLUDEDFROMSALE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-06T17:50:30.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludedFromSale', Name='Produkt Sperre Verkauf', Description=NULL, Help=NULL WHERE AD_Element_ID=543955 AND IsCentrallyMaintained='Y'
;

-- 2022-01-06T17:50:30.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt Sperre Verkauf', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543955) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543955)
;

-- 2022-01-06T17:50:30.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt Sperre Verkauf', Name='Produkt Sperre Verkauf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543955)
;

-- 2022-01-06T17:50:30.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt Sperre Verkauf', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543955
;

-- 2022-01-06T17:50:30.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkt Sperre Verkauf', Description=NULL, Help=NULL WHERE AD_Element_ID = 543955
;

-- 2022-01-06T17:50:30.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkt Sperre Verkauf', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543955
;

-- 2022-01-06T17:50:33.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt Sperre Verkauf', PrintName='Produkt Sperre Verkauf',Updated=TO_TIMESTAMP('2022-01-06 19:50:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543955 AND AD_Language='nl_NL'
;

-- 2022-01-06T17:50:33.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543955,'nl_NL') 
;

-- 2022-01-06T17:50:37.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt Sperre Verkauf', PrintName='Produkt Sperre Verkauf',Updated=TO_TIMESTAMP('2022-01-06 19:50:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543955 AND AD_Language='de_CH'
;

-- 2022-01-06T17:50:37.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543955,'de_CH') 
;

-- 2022-01-06T17:50:47.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exclude from sales', PrintName='Exclude from sales',Updated=TO_TIMESTAMP('2022-01-06 19:50:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543955 AND AD_Language='en_US'
;

-- 2022-01-06T17:50:47.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543955,'en_US') 
;

-- 2022-01-06T17:52:49.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580467,0,'IsExcludedFromPurchase',TO_TIMESTAMP('2022-01-06 19:52:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Exclude from purchase','Exclude from purchase',TO_TIMESTAMP('2022-01-06 19:52:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-06T17:52:49.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580467 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-06T17:53:01.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt Sperre Einkauf', PrintName='Produkt Sperre Einkauf',Updated=TO_TIMESTAMP('2022-01-06 19:53:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580467 AND AD_Language='de_CH'
;

-- 2022-01-06T17:53:01.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580467,'de_CH') 
;

-- 2022-01-06T17:53:04.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt Sperre Einkauf', PrintName='Produkt Sperre Einkauf',Updated=TO_TIMESTAMP('2022-01-06 19:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580467 AND AD_Language='de_DE'
;

-- 2022-01-06T17:53:04.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580467,'de_DE') 
;

-- 2022-01-06T17:53:04.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580467,'de_DE') 
;

-- 2022-01-06T17:53:04.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExcludedFromPurchase', Name='Produkt Sperre Einkauf', Description=NULL, Help=NULL WHERE AD_Element_ID=580467
;

-- 2022-01-06T17:53:04.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludedFromPurchase', Name='Produkt Sperre Einkauf', Description=NULL, Help=NULL, AD_Element_ID=580467 WHERE UPPER(ColumnName)='ISEXCLUDEDFROMPURCHASE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-06T17:53:04.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludedFromPurchase', Name='Produkt Sperre Einkauf', Description=NULL, Help=NULL WHERE AD_Element_ID=580467 AND IsCentrallyMaintained='Y'
;

-- 2022-01-06T17:53:04.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt Sperre Einkauf', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580467) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580467)
;

-- 2022-01-06T17:53:04.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt Sperre Einkauf', Name='Produkt Sperre Einkauf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580467)
;

-- 2022-01-06T17:53:04.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt Sperre Einkauf', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580467
;

-- 2022-01-06T17:53:04.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkt Sperre Einkauf', Description=NULL, Help=NULL WHERE AD_Element_ID = 580467
;

-- 2022-01-06T17:53:04.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkt Sperre Einkauf', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580467
;

-- 2022-01-06T17:53:08.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt Sperre Einkauf', PrintName='Produkt Sperre Einkauf',Updated=TO_TIMESTAMP('2022-01-06 19:53:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580467 AND AD_Language='nl_NL'
;

-- 2022-01-06T17:53:08.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580467,'nl_NL') 
;

-- 2022-01-06T17:53:57.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579082,580467,0,20,632,'IsExcludedFromPurchase',TO_TIMESTAMP('2022-01-06 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Produkt Sperre Einkauf',0,0,TO_TIMESTAMP('2022-01-06 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-06T17:53:57.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579082 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-06T17:53:57.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580467) 
;

-- 2022-01-06T17:54:00.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product','ALTER TABLE public.C_BPartner_Product ADD COLUMN IsExcludedFromPurchase CHAR(1) DEFAULT ''N'' CHECK (IsExcludedFromPurchase IN (''Y'',''N'')) NOT NULL')
;

-- 2022-01-06T17:59:31.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579082,676481,0,562,TO_TIMESTAMP('2022-01-06 19:59:31','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Produkt Sperre Einkauf',TO_TIMESTAMP('2022-01-06 19:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-06T17:59:31.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=676481 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-06T17:59:31.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580467) 
;

-- 2022-01-06T17:59:31.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=676481
;

-- 2022-01-06T17:59:31.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(676481)
;

-- 2022-01-06T18:03:01.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,676481,0,562,599181,1000021,'F',TO_TIMESTAMP('2022-01-06 20:03:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Produkt Sperre Einkauf',215,0,0,TO_TIMESTAMP('2022-01-06 20:03:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-06T18:07:59.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580468,0,'ExclusionFromPurchaseReason',TO_TIMESTAMP('2022-01-06 20:07:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Exclusion From Purchase Reason','Exclusion From Purchase Reason',TO_TIMESTAMP('2022-01-06 20:07:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-06T18:07:59.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580468 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-06T18:08:31.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sperre Kaufen Grund', PrintName='Sperre Kaufen Grund',Updated=TO_TIMESTAMP('2022-01-06 20:08:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580468 AND AD_Language='de_CH'
;

-- 2022-01-06T18:08:31.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580468,'de_CH') 
;

-- 2022-01-06T18:08:35.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sperre Kaufen Grund', PrintName='Sperre Kaufen Grund',Updated=TO_TIMESTAMP('2022-01-06 20:08:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580468 AND AD_Language='de_DE'
;

-- 2022-01-06T18:08:35.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580468,'de_DE') 
;

-- 2022-01-06T18:08:35.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580468,'de_DE') 
;

-- 2022-01-06T18:08:35.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExclusionFromPurchaseReason', Name='Sperre Kaufen Grund', Description=NULL, Help=NULL WHERE AD_Element_ID=580468
;

-- 2022-01-06T18:08:35.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromPurchaseReason', Name='Sperre Kaufen Grund', Description=NULL, Help=NULL, AD_Element_ID=580468 WHERE UPPER(ColumnName)='EXCLUSIONFROMPURCHASEREASON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-06T18:08:35.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromPurchaseReason', Name='Sperre Kaufen Grund', Description=NULL, Help=NULL WHERE AD_Element_ID=580468 AND IsCentrallyMaintained='Y'
;

-- 2022-01-06T18:08:35.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sperre Kaufen Grund', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580468) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580468)
;

-- 2022-01-06T18:08:35.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sperre Kaufen Grund', Name='Sperre Kaufen Grund' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580468)
;

-- 2022-01-06T18:08:35.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Sperre Kaufen Grund', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580468
;

-- 2022-01-06T18:08:35.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Sperre Kaufen Grund', Description=NULL, Help=NULL WHERE AD_Element_ID = 580468
;

-- 2022-01-06T18:08:35.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Sperre Kaufen Grund', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580468
;

-- 2022-01-06T18:08:38.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sperre Kaufen Grund', PrintName='Sperre Kaufen Grund',Updated=TO_TIMESTAMP('2022-01-06 20:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580468 AND AD_Language='nl_NL'
;

-- 2022-01-06T18:08:38.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580468,'nl_NL') 
;

-- 2022-01-06T18:09:27.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579083,580468,0,14,632,'ExclusionFromPurchaseReason',TO_TIMESTAMP('2022-01-06 20:09:27','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@IsExcludedFromPurchase@ = ''Y''',0,'Sperre Kaufen Grund',0,0,TO_TIMESTAMP('2022-01-06 20:09:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-06T18:09:27.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579083 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-06T18:09:27.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580468) 
;

-- 2022-01-06T18:09:38.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2022-01-06 20:09:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579083
;

-- 2022-01-06T18:09:54.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product','ALTER TABLE public.C_BPartner_Product ADD COLUMN ExclusionFromPurchaseReason VARCHAR(2000)')
;

-- 2022-01-06T18:10:31.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579083,676482,0,562,TO_TIMESTAMP('2022-01-06 20:10:31','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Sperre Kaufen Grund',TO_TIMESTAMP('2022-01-06 20:10:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-06T18:10:31.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=676482 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-06T18:10:31.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580468) 
;

-- 2022-01-06T18:10:31.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=676482
;

-- 2022-01-06T18:10:31.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(676482)
;

-- 2022-01-06T18:11:20.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-01-06 20:11:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=676482
;

-- 2022-01-06T18:12:09.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsExcludedFromPurchase/N@=Y',Updated=TO_TIMESTAMP('2022-01-06 20:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=676482
;

-- 2022-01-06T18:13:05.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,676482,0,562,599182,1000021,'F',TO_TIMESTAMP('2022-01-06 20:13:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sperre Kaufen Grund',216,0,0,TO_TIMESTAMP('2022-01-06 20:13:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-06T18:58:33.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545090,0,TO_TIMESTAMP('2022-01-06 20:58:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product not allowed to be purchased from vendor {0} because {1}.','E',TO_TIMESTAMP('2022-01-06 20:58:33','YYYY-MM-DD HH24:MI:SS'),100,'ProductPurchaseExclusionError')
;

-- 2022-01-06T18:58:33.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545090 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-01-06T18:58:41.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Produkt darf nicht vom Lieferanten {0} bezogen werden. Grund: {1}.',Updated=TO_TIMESTAMP('2022-01-06 20:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545090
;

-- 2022-01-06T18:58:43.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Produkt darf nicht vom Lieferanten {0} bezogen werden. Grund: {1}.',Updated=TO_TIMESTAMP('2022-01-06 20:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545090
;

-- 2022-01-06T18:58:46.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Produkt darf nicht vom Lieferanten {0} bezogen werden. Grund: {1}.',Updated=TO_TIMESTAMP('2022-01-06 20:58:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545090
;

-- 2022-01-11T12:32:40.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsExcludedFromPurchase/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2022-01-11 14:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579083
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2022-01-11T12:33:10.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_product','ExclusionFromPurchaseReason','VARCHAR(2000)',null,null)
;