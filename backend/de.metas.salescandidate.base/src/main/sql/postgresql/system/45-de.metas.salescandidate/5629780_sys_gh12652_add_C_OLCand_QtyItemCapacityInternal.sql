-- 2022-03-11T20:19:17.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580710,0,'QtyItemCapacityInternal',TO_TIMESTAMP('2022-03-11 21:19:17','YYYY-MM-DD HH24:MI:SS'),100,'In den metasfresh Stammdaten hinterlegte Verpackungskapazität','de.metas.handlingunits','Y','Verpackungskapazität int.','Verpackungskapazität int.',TO_TIMESTAMP('2022-03-11 21:19:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-11T20:19:17.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580710 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-03-11T20:19:54.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-11 21:19:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580710 AND AD_Language='de_CH'
;

-- 2022-03-11T20:19:54.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580710,'de_CH') 
;

-- 2022-03-11T20:19:57.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-11 21:19:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580710 AND AD_Language='de_DE'
;

-- 2022-03-11T20:19:57.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580710,'de_DE') 
;

-- 2022-03-11T20:19:57.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580710,'de_DE') 
;

-- 2022-03-11T20:20:32.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582457,580710,0,29,540244,'QtyItemCapacityInternal',TO_TIMESTAMP('2022-03-11 21:20:31','YYYY-MM-DD HH24:MI:SS'),100,'N','In den metasfresh Stammdaten hinterlegte Verpackungskapazität','de.metas.ordercandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verpackungskapazität int.',0,0,TO_TIMESTAMP('2022-03-11 21:20:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-11T20:20:32.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582457 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-11T20:20:32.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580710) 
;

-- 2022-03-11T20:20:53.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Packaging capacity stored in the metasfresh master data', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-11 21:20:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580710 AND AD_Language='en_US'
;

-- 2022-03-11T20:20:53.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580710,'en_US') 
;

-- 2022-03-11T20:23:27.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', IsTranslated='Y', Name='Manuelle Verpackingskapazität', PrintName='Manuelle Verpackingskapazität',Updated=TO_TIMESTAMP('2022-03-11 21:23:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542287 AND AD_Language='de_DE'
;

-- 2022-03-11T20:23:27.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542287,'de_DE') 
;

-- 2022-03-11T20:23:28.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542287,'de_DE') 
;

-- 2022-03-11T20:23:28.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsManualQtyItemCapacity', Name='Manuelle Verpackingskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL WHERE AD_Element_ID=542287
;

-- 2022-03-11T20:23:28.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsManualQtyItemCapacity', Name='Manuelle Verpackingskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL, AD_Element_ID=542287 WHERE UPPER(ColumnName)='ISMANUALQTYITEMCAPACITY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-11T20:23:28.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsManualQtyItemCapacity', Name='Manuelle Verpackingskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL WHERE AD_Element_ID=542287 AND IsCentrallyMaintained='Y'
;

-- 2022-03-11T20:23:28.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Manuelle Verpackingskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542287) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542287)
;

-- 2022-03-11T20:23:28.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Manuelle Verpackingskapazität', Name='Manuelle Verpackingskapazität' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542287)
;

-- 2022-03-11T20:23:28.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Manuelle Verpackingskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542287
;

-- 2022-03-11T20:23:28.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Manuelle Verpackingskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL WHERE AD_Element_ID = 542287
;

-- 2022-03-11T20:23:28.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Manuelle Verpackingskapazität', Description = 'Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542287
;

-- 2022-03-11T20:23:34.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt',Updated=TO_TIMESTAMP('2022-03-11 21:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542287 AND AD_Language='de_CH'
;

-- 2022-03-11T20:23:34.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542287,'de_CH') 
;

-- 2022-03-11T20:23:50.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Manuelle Verpackungskapazität', PrintName='Manuelle Verpackungskapazität',Updated=TO_TIMESTAMP('2022-03-11 21:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542287 AND AD_Language='de_CH'
;

-- 2022-03-11T20:23:50.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542287,'de_CH') 
;

-- 2022-03-11T20:23:56.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Manuelle Verpackungskapazität', PrintName='Manuelle Verpackungskapazität',Updated=TO_TIMESTAMP('2022-03-11 21:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542287 AND AD_Language='de_DE'
;

-- 2022-03-11T20:23:56.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542287,'de_DE') 
;

-- 2022-03-11T20:23:56.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542287,'de_DE') 
;

-- 2022-03-11T20:23:56.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsManualQtyItemCapacity', Name='Manuelle Verpackungskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL WHERE AD_Element_ID=542287
;

-- 2022-03-11T20:23:56.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsManualQtyItemCapacity', Name='Manuelle Verpackungskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL, AD_Element_ID=542287 WHERE UPPER(ColumnName)='ISMANUALQTYITEMCAPACITY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-11T20:23:56.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsManualQtyItemCapacity', Name='Manuelle Verpackungskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL WHERE AD_Element_ID=542287 AND IsCentrallyMaintained='Y'
;

-- 2022-03-11T20:23:56.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Manuelle Verpackungskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542287) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542287)
;

-- 2022-03-11T20:23:56.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Manuelle Verpackungskapazität', Name='Manuelle Verpackungskapazität' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542287)
;

-- 2022-03-11T20:23:56.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Manuelle Verpackungskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542287
;

-- 2022-03-11T20:23:56.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Manuelle Verpackungskapazität', Description='Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', Help=NULL WHERE AD_Element_ID = 542287
;

-- 2022-03-11T20:23:56.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Manuelle Verpackungskapazität', Description = 'Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542287
;

-- 2022-03-11T20:24:40.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If "no", then the internal packaging capactity is applied', IsTranslated='Y', Name='Manual Packaging capacity', PrintName='Manual Packaging capacity',Updated=TO_TIMESTAMP('2022-03-11 21:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542287 AND AD_Language='nl_NL'
;

-- 2022-03-11T20:24:40.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542287,'nl_NL') 
;

-- 2022-03-11T20:24:48.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If "no", then the internal packaging capactity is applied',Updated=TO_TIMESTAMP('2022-03-11 21:24:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542287 AND AD_Language='en_US'
;

-- 2022-03-11T20:24:48.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542287,'en_US') 
;

-- 2022-03-11T20:24:54.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Manual Packaging capacity', PrintName='Manual Packaging capacity',Updated=TO_TIMESTAMP('2022-03-11 21:24:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542287 AND AD_Language='en_US'
;

-- 2022-03-11T20:24:54.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542287,'en_US') 
;

-- 2022-03-11T20:25:02.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Manual packaging capacity', PrintName='Manual packaging capacity',Updated=TO_TIMESTAMP('2022-03-11 21:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542287 AND AD_Language='en_US'
;

-- 2022-03-11T20:25:02.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542287,'en_US') 
;

-- 2022-03-11T20:25:20.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN QtyItemCapacityInternal NUMERIC')
;

