-- gh30350 — backing data for the mobile picking carrier-advise REST flow.
-- Adds two columns to M_Picking_Job_Line (NOT user-facing in the desktop AD_Window; AD_Column only, no AD_Field/AD_UI_Element):
--   1) Carrier_Product_ID        — nullable FK to Carrier_Product (TableDir, reuses existing AD_Element 584116 'Lieferweg-Produkt')
--   2) IsCarrierAdviseReadOnly   — mandatory YesNo, default 'N' (existing rows backfilled to 'N')
--
-- IDs allocated from idserver.metas.de on 2026-06-17:
--   AD_Element 585012 (IsCarrierAdviseReadOnly — new)
--   AD_Column  592829 (M_Picking_Job_Line.Carrier_Product_ID)
--   AD_Column  592830 (M_Picking_Job_Line.IsCarrierAdviseReadOnly)
--
-- Referenced existing IDs (verified from the local DB):
--   AD_Element 584116 (Carrier_Product_ID — already used by M_ShipmentSchedule.Carrier_Product_ID; reused, NOT duplicated)
--   AD_Table   541907 (M_Picking_Job_Line)
--   AD_Reference 19   (TableDir — FK whose ColumnName = referenced table's PK Carrier_Product_ID)
--   AD_Reference 20   (Yes-No — boolean accessor, like every other Is* flag)

-- =========================================================================
-- Column 1: M_Picking_Job_Line.Carrier_Product_ID (nullable FK, reuse AD_Element 584116)
-- =========================================================================

-- 2026-06-17T10:00:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592829 /*From ID Server*/,584116,0,19,541907,'XX','Carrier_Product_ID',TO_TIMESTAMP('2026-06-17 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferweg-Produkt','NP',0,0,TO_TIMESTAMP('2026-06-17 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-17T10:00:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592829
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-17T10:00:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(584116)
;

-- Physical column (new nullable FK column — db_alter_table; no NOT NULL)
-- 2026-06-17T10:00:03.000Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN IF NOT EXISTS Carrier_Product_ID NUMERIC(10)')
;

-- 2026-06-17T10:00:04.000Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD CONSTRAINT CarrierProduct_MPickingJobLine FOREIGN KEY (Carrier_Product_ID) REFERENCES public.Carrier_Product DEFERRABLE INITIALLY DEFERRED')
;

-- =========================================================================
-- Column 2: M_Picking_Job_Line.IsCarrierAdviseReadOnly (mandatory YesNo, default 'N')
-- =========================================================================

-- AD_Element: IsCarrierAdviseReadOnly (new)
-- 2026-06-17T10:01:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585012 /*From ID Server*/,0,'IsCarrierAdviseReadOnly',TO_TIMESTAMP('2026-06-17 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Lieferweg-Abfrage schreibgeschützt','Lieferweg-Abfrage schreibgeschützt',TO_TIMESTAMP('2026-06-17 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-17T10:01:01.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585012
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US override
-- 2026-06-17T10:01:12.000Z
UPDATE AD_Element_Trl SET Name='Carrier Advise Read-Only', PrintName='Carrier Advise Read-Only', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-17 10:01:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585012 AND AD_Language='en_US'
;

-- de_DE / de_CH active-translated (same text as base; de_CH identical, no ß present)
-- 2026-06-17T10:01:13.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-17 10:01:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585012 AND AD_Language IN ('de_DE','de_CH')
;

-- propagate the element overrides down to all dependent _Trl tables
-- 2026-06-17T10:01:14.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585012 /*AD_Element_ID*/, 'en_US')
;

-- 2026-06-17T10:01:15.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585012 /*AD_Element_ID*/, 'de_DE')
;

-- 2026-06-17T10:01:16.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585012 /*AD_Element_ID*/, 'de_CH')
;

-- AD_Column: M_Picking_Job_Line.IsCarrierAdviseReadOnly (YesNo list, mandatory)
-- 2026-06-17T10:02:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592830 /*From ID Server*/,585012,0,20,NULL,541907,'XX','IsCarrierAdviseReadOnly',TO_TIMESTAMP('2026-06-17 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lieferweg-Abfrage schreibgeschützt','NP',0,0,TO_TIMESTAMP('2026-06-17 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-17T10:02:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592830
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-17T10:02:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(585012)
;

-- Physical column: new mandatory YesNo column, default 'N' (ADD COLUMN NOT NULL DEFAULT backfills existing rows)
-- 2026-06-17T10:02:03.000Z
ALTER TABLE public.M_Picking_Job_Line ADD COLUMN IF NOT EXISTS IsCarrierAdviseReadOnly CHAR(1) NOT NULL DEFAULT 'N'
;

-- 2026-06-17T10:02:06.000Z
ALTER TABLE public.M_Picking_Job_Line ADD CONSTRAINT M_Picking_Job_Line_IsCarrierAdviseReadOnly_Check CHECK (IsCarrierAdviseReadOnly IN ('Y','N'))
;
