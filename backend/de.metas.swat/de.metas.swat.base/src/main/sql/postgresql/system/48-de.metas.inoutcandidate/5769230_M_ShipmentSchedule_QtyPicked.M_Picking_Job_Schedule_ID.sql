-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule_QtyPicked.M_Picking_Job_Schedule_ID
-- 2025-09-17T07:54:11.698Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590921,583882,0,30,540542,'XX','M_Picking_Job_Schedule_ID',TO_TIMESTAMP('2025-09-17 07:54:11.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.inoutcandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Picking Job Schedule',0,0,TO_TIMESTAMP('2025-09-17 07:54:11.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-17T07:54:11.704Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590921 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-17T07:54:11.963Z
/* DDL */  select update_Column_Translation_From_AD_Element(583882)
;

-- 2025-09-17T07:54:14.148Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_QtyPicked','ALTER TABLE public.M_ShipmentSchedule_QtyPicked ADD COLUMN M_Picking_Job_Schedule_ID NUMERIC(10)')
;

-- 2025-09-17T07:54:14.560Z
ALTER TABLE M_ShipmentSchedule_QtyPicked ADD CONSTRAINT MPickingJobSchedule_MShipmentScheduleQtyPicked FOREIGN KEY (M_Picking_Job_Schedule_ID) REFERENCES public.M_Picking_Job_Schedule DEFERRABLE INITIALLY DEFERRED
;

