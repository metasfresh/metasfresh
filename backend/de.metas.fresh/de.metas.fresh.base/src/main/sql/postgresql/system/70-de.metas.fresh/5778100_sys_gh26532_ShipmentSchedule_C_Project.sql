-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule.C_Project_ID
-- 2025-11-24T17:51:42.601Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591599,208,0,30,141,500221,540759,'XX','C_Project_ID',TO_TIMESTAMP('2025-11-24 17:51:42.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Financial Project','de.metas.inoutcandidate',0,10,'E','A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Projekt',0,0,TO_TIMESTAMP('2025-11-24 17:51:42.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-24T17:51:42.666Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591599 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-24T17:51:42.795Z
/* DDL */  select update_Column_Translation_From_AD_Element(208)
;

-- 2025-11-24T17:53:04.372Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN C_Project_ID NUMERIC(10)')
;

-- 2025-11-24T17:53:04.606Z
ALTER TABLE M_ShipmentSchedule ADD CONSTRAINT CProject_MShipmentSchedule FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED
;
