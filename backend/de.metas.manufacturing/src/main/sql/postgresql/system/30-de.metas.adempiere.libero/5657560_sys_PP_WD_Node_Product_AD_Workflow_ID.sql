-- 2022-09-26T14:58:01.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,
                       IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,584457,144,0,30,53016,'AD_Workflow_ID',TO_TIMESTAMP('2022-09-26 17:58:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Workflow oder Kombination von Aufgaben','EE01',0,10,'"Workflow" bezeichnet einen eindeutigen Workflow im System.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',
        'N','N','N','N','N','N','Y','N',0,'Arbeitsablauf',0,0,TO_TIMESTAMP('2022-09-26 17:58:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-26T14:58:01.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584457 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-26T14:58:02.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(144) 
;

-- 2022-09-26T14:58:06.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_WF_Node_Product','ALTER TABLE public.PP_WF_Node_Product ADD COLUMN AD_Workflow_ID NUMERIC(10)')
;

-- 2022-09-26T14:58:06.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_WF_Node_Product ADD CONSTRAINT ADWorkflow_PPWFNodeProduct FOREIGN KEY (AD_Workflow_ID) REFERENCES public.AD_Workflow DEFERRABLE INITIALLY DEFERRED
;

