










-- 2021-07-02T12:50:19.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579455,0,'RequiresSupplierApproval',TO_TIMESTAMP('2021-07-02 15:50:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Requires Supplier Approval','Requires Supplier Approval',TO_TIMESTAMP('2021-07-02 15:50:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-02T12:50:19.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579455 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-02T12:50:31.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferantenfreigabe Prüfung', PrintName='Lieferantenfreigabe Prüfung',Updated=TO_TIMESTAMP('2021-07-02 15:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579455 AND AD_Language='de_CH'
;

-- 2021-07-02T12:50:31.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579455,'de_CH') 
;

-- 2021-07-02T12:50:34.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferantenfreigabe Prüfung', PrintName='Lieferantenfreigabe Prüfung',Updated=TO_TIMESTAMP('2021-07-02 15:50:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579455 AND AD_Language='de_DE'
;

-- 2021-07-02T12:50:34.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579455,'de_DE') 
;

-- 2021-07-02T12:50:34.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579455,'de_DE') 
;

-- 2021-07-02T12:50:34.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RequiresSupplierApproval', Name='Lieferantenfreigabe Prüfung', Description=NULL, Help=NULL WHERE AD_Element_ID=579455
;

-- 2021-07-02T12:50:34.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RequiresSupplierApproval', Name='Lieferantenfreigabe Prüfung', Description=NULL, Help=NULL, AD_Element_ID=579455 WHERE UPPER(ColumnName)='REQUIRESSUPPLIERAPPROVAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-02T12:50:34.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RequiresSupplierApproval', Name='Lieferantenfreigabe Prüfung', Description=NULL, Help=NULL WHERE AD_Element_ID=579455 AND IsCentrallyMaintained='Y'
;

-- 2021-07-02T12:50:34.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferantenfreigabe Prüfung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579455) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579455)
;

-- 2021-07-02T12:50:34.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferantenfreigabe Prüfung', Name='Lieferantenfreigabe Prüfung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579455)
;

-- 2021-07-02T12:50:34.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferantenfreigabe Prüfung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579455
;

-- 2021-07-02T12:50:34.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferantenfreigabe Prüfung', Description=NULL, Help=NULL WHERE AD_Element_ID = 579455
;

-- 2021-07-02T12:50:34.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferantenfreigabe Prüfung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579455
;

-- 2021-07-02T12:53:05.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574949,579455,0,20,208,'RequiresSupplierApproval',TO_TIMESTAMP('2021-07-02 15:53:04','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Lieferantenfreigabe Prüfung',0,0,TO_TIMESTAMP('2021-07-02 15:53:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-02T12:53:05.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574949 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-02T12:53:05.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579455) 
;

-- 2021-07-02T12:53:06.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN RequiresSupplierApproval CHAR(1) DEFAULT ''N'' CHECK (RequiresSupplierApproval IN (''Y'',''N'')) NOT NULL')
;




-- 2021-07-07T08:54:46.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@RequiresSupplierApproval@ = ''Y''',Updated=TO_TIMESTAMP('2021-07-07 11:54:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574949
;


