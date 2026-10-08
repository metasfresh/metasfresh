-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job_Schedule.M_ShipmentSchedule_ID
-- 2025-08-29T14:17:11.546Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2025-08-29 14:17:11.546000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590650
;

-- Column: M_Picking_Job_Schedule.C_Workplace_ID
-- 2025-08-29T14:17:23.043Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2025-08-29 14:17:23.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590651
;

-- Column: M_Picking_Job_Line.M_Picking_Job_Schedule_ID
-- 2025-08-29T14:17:28.500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590703,583882,0,30,541907,'XX','M_Picking_Job_Schedule_ID',TO_TIMESTAMP('2025-08-29 14:17:27.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Picking Job Schedule',0,0,TO_TIMESTAMP('2025-08-29 14:17:27.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-29T14:17:28.506Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590703 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-29T14:17:28.763Z
/* DDL */  select update_Column_Translation_From_AD_Element(583882)
;

-- 2025-08-29T14:17:39.698Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN M_Picking_Job_Schedule_ID NUMERIC(10)')
;

-- 2025-08-29T14:17:39.737Z
ALTER TABLE M_Picking_Job_Line ADD CONSTRAINT MPickingJobSchedule_MPickingJobLine FOREIGN KEY (M_Picking_Job_Schedule_ID) REFERENCES public.M_Picking_Job_Schedule DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Picking_Job_Line.M_Picking_Job_Schedule_ID
-- 2025-08-29T14:18:07.648Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2025-08-29 14:18:07.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590703
;

-- Column: M_Picking_Job_Step.M_Picking_Job_Schedule_ID
-- 2025-08-29T14:18:32.625Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590704,583882,0,30,541908,'XX','M_Picking_Job_Schedule_ID',TO_TIMESTAMP('2025-08-29 14:18:32.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Picking Job Schedule',0,0,TO_TIMESTAMP('2025-08-29 14:18:32.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-29T14:18:32.631Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590704 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-29T14:18:32.927Z
/* DDL */  select update_Column_Translation_From_AD_Element(583882)
;

-- 2025-08-29T14:18:34.846Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step','ALTER TABLE public.M_Picking_Job_Step ADD COLUMN M_Picking_Job_Schedule_ID NUMERIC(10)')
;

-- 2025-08-29T14:18:34.869Z
ALTER TABLE M_Picking_Job_Step ADD CONSTRAINT MPickingJobSchedule_MPickingJobStep FOREIGN KEY (M_Picking_Job_Schedule_ID) REFERENCES public.M_Picking_Job_Schedule DEFERRABLE INITIALLY DEFERRED
;

