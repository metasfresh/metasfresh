-- Column: M_Picking_Job_Step.IsDynamic
-- Column: M_Picking_Job_Step.IsDynamic
-- 2023-10-23T12:21:20.619Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587603,542448,0,20,541908,'IsDynamic',TO_TIMESTAMP('2023-10-23 15:21:20','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Is Dynamic',0,0,TO_TIMESTAMP('2023-10-23 15:21:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-23T12:21:20.625Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587603 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-23T12:21:20.633Z
/* DDL */  select update_Column_Translation_From_AD_Element(542448) 
;

-- 2023-10-23T12:21:21.708Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step','ALTER TABLE public.M_Picking_Job_Step ADD COLUMN IsDynamic CHAR(1) DEFAULT ''N'' CHECK (IsDynamic IN (''Y'',''N'')) NOT NULL')
;

