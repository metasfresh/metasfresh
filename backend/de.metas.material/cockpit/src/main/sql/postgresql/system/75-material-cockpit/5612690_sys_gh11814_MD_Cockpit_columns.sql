-- 2021-11-09T15:12:51.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='1',Updated=TO_TIMESTAMP('2021-11-09 16:12:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540891
;

-- 2021-11-10T13:35:34.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580208,0,'QtyStockEstimateOrdering',TO_TIMESTAMP('2021-11-10 14:35:34','YYYY-MM-DD HH24:MI:SS'),100,'"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde','de.metas.material.cockpit','Y','Zählbestand Reihenfolge','Zählbestand Reihenfolge',TO_TIMESTAMP('2021-11-10 14:35:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-10T13:35:34.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580208 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-10T13:35:45.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyStockEstimateSeqNo',Updated=TO_TIMESTAMP('2021-11-10 14:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580208
;

-- 2021-11-10T13:35:45.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyStockEstimateSeqNo', Name='Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE AD_Element_ID=580208
;

-- 2021-11-10T13:35:45.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateSeqNo', Name='Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL, AD_Element_ID=580208 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATESEQNO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-10T13:35:45.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateSeqNo', Name='Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE AD_Element_ID=580208 AND IsCentrallyMaintained='Y'
;

-- 2021-11-10T13:36:24.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578380,580208,0,11,540863,'QtyStockEstimateSeqNo',TO_TIMESTAMP('2021-11-10 14:36:24','YYYY-MM-DD HH24:MI:SS'),100,'N','0','"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde','de.metas.material.cockpit',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zählbestand Reihenfolge',0,0,TO_TIMESTAMP('2021-11-10 14:36:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-10T13:36:24.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578380 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-10T13:36:24.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580208) 
;

-- 2021-11-10T13:36:26.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Cockpit','ALTER TABLE public.MD_Cockpit ADD COLUMN QtyStockEstimateSeqNo NUMERIC(10) DEFAULT 0')
;

-- 2021-11-10T13:39:05.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Table_ID=540863, Help=NULL, InternalName='MD_Cockpit',Updated=TO_TIMESTAMP('2021-11-10 14:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542860
;

-- 2021-11-10T14:12:03.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541425,'S',TO_TIMESTAMP('2021-11-10 15:12:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.field.QtyStockEstimateSeqNo.IsDisplayed',TO_TIMESTAMP('2021-11-10 15:12:03','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2021-11-10T15:32:13.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='Fresh_QtyOnHand_UpdateSeqNo_And_Export_SortPref',Updated=TO_TIMESTAMP('2021-11-10 16:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540577
;

-- 2021-11-10T15:32:54.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Aktualisiert die Sortierung für das aktuelle Belegdatum (alle verarbeiteten Zählbestände!) und übernimmt sie in das Material-cockpit', Name='Sortierun Aktualisieren',Updated=TO_TIMESTAMP('2021-11-10 16:32:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540577
;



-- 2021-11-10T19:41:22.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-10,Updated=TO_TIMESTAMP('2021-11-10 20:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555213
;

-- 2021-11-10T23:55:54.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2021-11-11 00:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578380
;

-- 2021-11-10T23:55:55.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('md_cockpit','QtyStockEstimateSeqNo','NUMERIC(10)',null,null)
;


