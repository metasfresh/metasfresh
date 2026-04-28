-- 2021-11-11T10:59:05.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580216,0,'PickTo_HU_PI_Item_Product_ID',TO_TIMESTAMP('2021-11-11 12:59:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Packvorschrift','Packvorschrift',TO_TIMESTAMP('2021-11-11 12:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-11T10:59:05.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580216 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-11T10:59:29.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Pick To Packing Instructions', PrintName='Pick To Packing Instructions',Updated=TO_TIMESTAMP('2021-11-11 12:59:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580216 AND AD_Language='en_US'
;

-- 2021-11-11T10:59:29.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580216,'en_US') 
;

-- 2021-11-11T10:59:32.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-11-11 12:59:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580216 AND AD_Language='de_DE'
;

-- 2021-11-11T10:59:32.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580216,'de_DE') 
;

-- 2021-11-11T10:59:33.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580216,'de_DE') 
;

-- 2021-11-11T10:59:35.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-11-11 12:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580216 AND AD_Language='de_CH'
;

-- 2021-11-11T10:59:35.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580216,'de_CH') 
;

-- 2021-11-11T11:00:14.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578390,580216,0,30,540500,541908,'PickTo_HU_PI_Item_Product_ID',TO_TIMESTAMP('2021-11-11 13:00:14','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Packvorschrift',0,0,TO_TIMESTAMP('2021-11-11 13:00:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-11T11:00:14.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578390 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-11T11:00:14.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580216) 
;

-- 2021-11-11T11:00:20.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- /* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step','ALTER TABLE public.M_Picking_Job_Step ADD COLUMN PickTo_HU_PI_Item_Product_ID NUMERIC(10)')
-- ;

-- 2021-11-11T11:00:20.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- ALTER TABLE M_Picking_Job_Step ADD CONSTRAINT PickToHUPIItemProduct_MPickingJobStep FOREIGN KEY (PickTo_HU_PI_Item_Product_ID) REFERENCES public.M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED
-- ;

-- 2021-11-11T11:27:13.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='PackTo_HU_PI_Item_Product_ID',Updated=TO_TIMESTAMP('2021-11-11 13:27:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580216
;

-- 2021-11-11T11:27:13.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PackTo_HU_PI_Item_Product_ID', Name='Packvorschrift', Description=NULL, Help=NULL WHERE AD_Element_ID=580216
;

-- 2021-11-11T11:27:13.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PackTo_HU_PI_Item_Product_ID', Name='Packvorschrift', Description=NULL, Help=NULL, AD_Element_ID=580216 WHERE UPPER(ColumnName)='PACKTO_HU_PI_ITEM_PRODUCT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-11T11:27:13.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PackTo_HU_PI_Item_Product_ID', Name='Packvorschrift', Description=NULL, Help=NULL WHERE AD_Element_ID=580216 AND IsCentrallyMaintained='Y'
;

-- 2021-11-11T11:31:49.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step','ALTER TABLE public.M_Picking_Job_Step ADD COLUMN PackTo_HU_PI_Item_Product_ID NUMERIC(10)')
;

-- 2021-11-11T11:31:49.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Picking_Job_Step ADD CONSTRAINT PackToHUPIItemProduct_MPickingJobStep FOREIGN KEY (PackTo_HU_PI_Item_Product_ID) REFERENCES public.M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED
;

