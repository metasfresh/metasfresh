-- Run mode: SWING_CLIENT

-- Adds M_Picking_Job.ExternalSystem_ID, so an ALREADY-STARTED picking job keeps the external system
-- of the order it was created from.
--
-- Why a stored column rather than a lookup through C_Order: the launcher shows started jobs and
-- not-yet-started work items in ONE list. The not-yet-started half reads M_Packageable_V (which
-- exposes ExternalSystem_ID as of 5819920); the started half reads M_Picking_Job. Without the value
-- on both, the external-system filter would apply to only half the list -- exactly the defect PR
-- 25526 had to fix for the preparation date. C_Order is not a substitute: M_Picking_Job.C_Order_ID
-- is only set for the sales_order aggregation, while PreparationDate/DeliveryDate are already
-- stored here for every aggregation type. This column follows those two.
--
-- IDs allocated from idserver.metas.de on 2026-08-23:
--   AD_Column 593395 (M_Picking_Job.ExternalSystem_ID)

-- Column: M_Picking_Job.ExternalSystem_ID
-- Reuses AD_Element 583968 ("Externes System"), the element behind every ExternalSystem_ID column.
-- EntityType matches the table's own (de.metas.handlingunits), so GenerateModel includes it without
-- IsForceIncludeInGeneratedModel.
-- 2026-08-23T10:00:05.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593395 /*From ID Server*/,583968,0,30,541906,'XX','ExternalSystem_ID',TO_TIMESTAMP('2026-08-23 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2026-08-23 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-08-23T10:00:05.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593395 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-08-23T10:00:05.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- Nullable on purpose: an order without an external system produces a job without one, and that job
-- must still appear in the launcher.
-- 2026-08-23T10:00:06.000Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2026-08-23T10:00:06.000Z
ALTER TABLE M_Picking_Job ADD CONSTRAINT ExternalSystem_MPickingJob FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- 2026-08-23T10:00:06.000Z
INSERT INTO t_alter_column values('m_picking_job','ExternalSystem_ID','NUMERIC(10)',null,null)
;
