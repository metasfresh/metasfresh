-- 2021-11-18T13:54:27.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580245,0,'Current_Receiving_LU_HU_ID',TO_TIMESTAMP('2021-11-18 15:54:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Current Receiving LU','Current Receiving LU',TO_TIMESTAMP('2021-11-18 15:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-18T13:54:27.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580245 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-18T13:54:53.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578473,580245,0,30,540499,53027,'Current_Receiving_LU_HU_ID',TO_TIMESTAMP('2021-11-18 15:54:53','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Current Receiving LU',0,0,TO_TIMESTAMP('2021-11-18 15:54:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-18T13:54:53.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578473 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-18T13:54:53.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580245) 
;

-- 2021-11-18T13:54:55.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Order','ALTER TABLE public.PP_Order ADD COLUMN Current_Receiving_LU_HU_ID NUMERIC(10)')
;

-- 2021-11-18T13:54:55.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Order ADD CONSTRAINT CurrentReceivingLUHU_PPOrder FOREIGN KEY (Current_Receiving_LU_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- 2021-11-18T14:44:55.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578474,580245,0,30,540499,53025,'Current_Receiving_LU_HU_ID',TO_TIMESTAMP('2021-11-18 16:44:55','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Current Receiving LU',0,0,TO_TIMESTAMP('2021-11-18 16:44:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-18T14:44:55.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578474 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-18T14:44:55.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580245) 
;

-- 2021-11-18T14:44:56.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Order_BOMLine','ALTER TABLE public.PP_Order_BOMLine ADD COLUMN Current_Receiving_LU_HU_ID NUMERIC(10)')
;

-- 2021-11-18T14:44:56.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Order_BOMLine ADD CONSTRAINT CurrentReceivingLUHU_PPOrderBOMLine FOREIGN KEY (Current_Receiving_LU_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- 2021-11-18T14:45:18.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2021-11-18 16:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578473
;

-- 2021-11-18T14:48:30.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2021-11-18 16:48:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578474
;

