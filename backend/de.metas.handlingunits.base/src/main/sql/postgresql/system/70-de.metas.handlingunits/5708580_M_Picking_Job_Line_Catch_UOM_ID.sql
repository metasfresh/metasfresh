-- Column: M_Picking_Job_Line.Catch_UOM_ID
-- Column: M_Picking_Job_Line.Catch_UOM_ID
-- 2023-10-25T07:07:00.101Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587605,576953,0,30,114,541907,'Catch_UOM_ID',TO_TIMESTAMP('2023-10-25 10:06:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Aus dem Produktstamm übenommene Catch Weight Einheit.','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Catch Einheit',0,0,TO_TIMESTAMP('2023-10-25 10:06:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-25T07:07:00.104Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587605 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-25T07:07:00.136Z
/* DDL */  select update_Column_Translation_From_AD_Element(576953) 
;

-- 2023-10-25T07:07:02.961Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN Catch_UOM_ID NUMERIC(10)')
;

-- 2023-10-25T07:07:02.974Z
ALTER TABLE M_Picking_Job_Line ADD CONSTRAINT CatchUOM_MPickingJobLine FOREIGN KEY (Catch_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

