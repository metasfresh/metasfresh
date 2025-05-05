-- Column: ModCntr_Log.PP_Cost_Collector_ID
-- 2023-09-14T05:05:34.738375900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587472,53310,0,30,53254,542338,'PP_Cost_Collector_ID',TO_TIMESTAMP('2023-09-14 08:05:34.511','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Manufacturing Cost Collector',0,0,TO_TIMESTAMP('2023-09-14 08:05:34.511','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-14T05:05:34.749377900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587472 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-14T05:05:34.806618200Z
/* DDL */  select update_Column_Translation_From_AD_Element(53310) 
;

-- 2023-09-14T05:05:39.724169900Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN PP_Cost_Collector_ID NUMERIC(10)')
;

-- 2023-09-14T05:05:40.088320300Z
ALTER TABLE ModCntr_Log ADD CONSTRAINT PPCostCollector_ModCntrLog FOREIGN KEY (PP_Cost_Collector_ID) REFERENCES public.PP_Cost_Collector DEFERRABLE INITIALLY DEFERRED
;

