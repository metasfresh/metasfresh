-- 2021-11-19T07:15:03.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580247,0,'Current_Receiving_TU_PI_Item_Product_ID',TO_TIMESTAMP('2021-11-19 09:15:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Packvorschrift','Packvorschrift',TO_TIMESTAMP('2021-11-19 09:15:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-19T07:15:03.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580247 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-19T07:15:41.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Current Receiving TU instructions', PrintName='Current Receiving TU instructions',Updated=TO_TIMESTAMP('2021-11-19 09:15:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580247 AND AD_Language='en_US'
;

-- 2021-11-19T07:15:41.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580247,'en_US') 
;

-- 2021-11-19T07:16:05.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578476,580247,0,30,540500,53027,'Current_Receiving_TU_PI_Item_Product_ID',TO_TIMESTAMP('2021-11-19 09:16:04','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Packvorschrift',0,0,TO_TIMESTAMP('2021-11-19 09:16:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-19T07:16:05.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578476 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-19T07:16:05.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580247) 
;

-- 2021-11-19T07:16:18.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsCalculated='Y', IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2021-11-19 09:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578476
;

-- 2021-11-19T07:16:25.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Order','ALTER TABLE public.PP_Order ADD COLUMN Current_Receiving_TU_PI_Item_Product_ID NUMERIC(10)')
;

-- 2021-11-19T07:16:26.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Order ADD CONSTRAINT CurrentReceivingTUPIItemProduct_PPOrder FOREIGN KEY (Current_Receiving_TU_PI_Item_Product_ID) REFERENCES public.M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2021-11-19T07:16:49.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578477,580247,0,30,540500,53025,'Current_Receiving_TU_PI_Item_Product_ID',TO_TIMESTAMP('2021-11-19 09:16:49','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Packvorschrift',0,0,TO_TIMESTAMP('2021-11-19 09:16:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-19T07:16:49.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578477 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-19T07:16:49.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580247) 
;

-- 2021-11-19T07:16:58.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsCalculated='Y', IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2021-11-19 09:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578477
;

-- 2021-11-19T07:16:59.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Order_BOMLine','ALTER TABLE public.PP_Order_BOMLine ADD COLUMN Current_Receiving_TU_PI_Item_Product_ID NUMERIC(10)')
;

-- 2021-11-19T07:17:00.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Order_BOMLine ADD CONSTRAINT CurrentReceivingTUPIItemProduct_PPOrderBOMLine FOREIGN KEY (Current_Receiving_TU_PI_Item_Product_ID) REFERENCES public.M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED
;

