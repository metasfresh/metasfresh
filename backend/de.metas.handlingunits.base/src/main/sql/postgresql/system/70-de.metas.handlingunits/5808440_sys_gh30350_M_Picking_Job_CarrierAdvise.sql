-- gh30350 — backing data for the mobile picking carrier-advise REST flow.
-- Mirrors the 5808430 M_Picking_Job_Line migration, now for the M_Picking_Job HEADER table.
-- Adds two columns to M_Picking_Job (NOT user-facing in the desktop AD_Window; AD_Column only, no AD_Field/AD_UI_Element):
--   1) Carrier_Product_ID        — nullable FK to Carrier_Product (TableDir, reuses existing AD_Element 584116 'Lieferweg-Produkt')
--   2) IsCarrierAdviseReadOnly   — mandatory YesNo, default 'N' (existing rows backfilled to 'N')
--
-- IDs allocated from idserver.metas.de on 2026-06-17:
--   AD_Column  592831 (M_Picking_Job.Carrier_Product_ID)
--   AD_Column  592832 (M_Picking_Job.IsCarrierAdviseReadOnly)
--
-- Referenced existing IDs (verified from the local DB):
--   AD_Element 584116 (Carrier_Product_ID — reused, NOT duplicated)
--   AD_Element 585012 (IsCarrierAdviseReadOnly — created by the 5808430 script; reused, NOT duplicated)
--   AD_Table   541906 (M_Picking_Job; dominant EntityType de.metas.handlingunits)
--   AD_Reference 19   (TableDir — FK whose ColumnName = referenced table's PK Carrier_Product_ID)
--   AD_Reference 20   (Yes-No — boolean accessor, like every other Is* flag)

-- =========================================================================
-- Column 1: M_Picking_Job.Carrier_Product_ID (nullable FK, reuse AD_Element 584116)
-- =========================================================================

-- 2026-06-17T11:00:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592831 /*From ID Server*/,584116,0,19,541906,'XX','Carrier_Product_ID',TO_TIMESTAMP('2026-06-17 11:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferweg-Produkt','NP',0,0,TO_TIMESTAMP('2026-06-17 11:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-17T11:00:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592831
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-17T11:00:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(584116)
;

-- Physical column (new nullable FK column — db_alter_table; no NOT NULL)
-- 2026-06-17T11:00:03.000Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN IF NOT EXISTS Carrier_Product_ID NUMERIC(10)')
;

-- 2026-06-17T11:00:04.000Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD CONSTRAINT CarrierProduct_MPickingJob FOREIGN KEY (Carrier_Product_ID) REFERENCES public.Carrier_Product DEFERRABLE INITIALLY DEFERRED')
;

-- =========================================================================
-- Column 2: M_Picking_Job.IsCarrierAdviseReadOnly (mandatory YesNo, default 'N', reuse AD_Element 585012)
-- =========================================================================

-- AD_Column: M_Picking_Job.IsCarrierAdviseReadOnly (YesNo list, mandatory)
-- 2026-06-17T11:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592832 /*From ID Server*/,585012,0,20,NULL,541906,'XX','IsCarrierAdviseReadOnly',TO_TIMESTAMP('2026-06-17 11:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lieferweg-Abfrage schreibgeschützt','NP',0,0,TO_TIMESTAMP('2026-06-17 11:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-17T11:01:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592832
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-17T11:01:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(585012)
;

-- Physical column: new mandatory YesNo column, default 'N' (ADD COLUMN NOT NULL DEFAULT backfills existing rows)
-- 2026-06-17T11:01:03.000Z
ALTER TABLE public.M_Picking_Job ADD COLUMN IF NOT EXISTS IsCarrierAdviseReadOnly CHAR(1) NOT NULL DEFAULT 'N'
;

-- 2026-06-17T11:01:06.000Z
ALTER TABLE public.M_Picking_Job ADD CONSTRAINT M_Picking_Job_IsCarrierAdviseReadOnly_Check CHECK (IsCarrierAdviseReadOnly IN ('Y','N'))
;
