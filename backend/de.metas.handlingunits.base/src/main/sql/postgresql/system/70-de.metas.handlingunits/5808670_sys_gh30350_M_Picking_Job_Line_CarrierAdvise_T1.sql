-- gh30350 (STEP3 T1) — make the picking-job LINE the carrier-advise source of truth (additive, behaviour-neutral).
-- Adds two columns to M_Picking_Job_Line (mobile-only; NO desktop AD_Field/AD_UI_Element, exactly like Phase-0's Carrier_Product_ID):
--   1) IsCarrierAdviseManual   — mandatory YesNo, default 'N' (per-line manual override; readOnly/anyManual derives from it)
--   2) Carrier_Goods_Type_ID   — nullable FK to Carrier_Goods_Type (TableDir, reuses existing AD_Element 584112)
-- The carrier services are modelled as a junction table M_Picking_Job_Line_Carrier_Service (next script), NOT a String column.
--
-- IDs allocated from idserver.metas.de on 2026-06-18:
--   AD_Element 585023 (IsCarrierAdviseManual — new)
--   AD_Column  592838 (M_Picking_Job_Line.IsCarrierAdviseManual)
--   AD_Column  592839 (M_Picking_Job_Line.Carrier_Goods_Type_ID)
--
-- Referenced existing IDs (verified from the local DB):
--   AD_Element 584112 (Carrier_Goods_Type_ID — already used by M_ShipmentSchedule.Carrier_Goods_Type_ID; reused, NOT duplicated)
--   AD_Table   541907 (M_Picking_Job_Line)
--   AD_Reference 19   (TableDir — FK whose ColumnName = referenced table's PK Carrier_Goods_Type_ID)
--   AD_Reference 20   (Yes-No — boolean accessor, like every other Is* flag)
--   AD_Reference 10   (String)

-- =========================================================================
-- Column 1: M_Picking_Job_Line.IsCarrierAdviseManual (mandatory YesNo, default 'N')
-- =========================================================================

-- AD_Element: IsCarrierAdviseManual (new)
-- 2026-06-18T10:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585023 /*From ID Server*/,0,'IsCarrierAdviseManual',TO_TIMESTAMP('2026-06-18 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Lieferweg-Abfrage manuell','Lieferweg-Abfrage manuell',TO_TIMESTAMP('2026-06-18 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-18T10:00:01.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585023
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US override
-- 2026-06-18T10:00:12.000Z
UPDATE AD_Element_Trl SET Name='Carrier Advise Manual', PrintName='Carrier Advise Manual', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-18 10:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585023 AND AD_Language='en_US'
;

-- de_DE / de_CH active-translated (same text as base; de_CH identical, no ß present)
-- 2026-06-18T10:00:13.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-18 10:00:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585023 AND AD_Language IN ('de_DE','de_CH')
;

-- propagate the element overrides down to all dependent _Trl tables
-- 2026-06-18T10:00:14.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585023 /*AD_Element_ID*/, 'en_US')
;

-- 2026-06-18T10:00:15.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585023 /*AD_Element_ID*/, 'de_DE')
;

-- 2026-06-18T10:00:16.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585023 /*AD_Element_ID*/, 'de_CH')
;

-- AD_Column: M_Picking_Job_Line.IsCarrierAdviseManual (YesNo list, mandatory)
-- 2026-06-18T10:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592838 /*From ID Server*/,585023,0,20,NULL,541907,'XX','IsCarrierAdviseManual',TO_TIMESTAMP('2026-06-18 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lieferweg-Abfrage manuell','NP',0,0,TO_TIMESTAMP('2026-06-18 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-18T10:01:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592838
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-18T10:01:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(585023)
;

-- Physical column (new mandatory YesNo column — db_alter_table, combined DEFAULT 'N' + CHECK + NOT NULL in one call;
-- mirrors the sibling mandatory boolean IsManuallyClosed in script 5719240 on the same table. DEFAULT 'N' backfills
-- existing rows atomically so the NOT NULL is satisfied in the same statement.)
-- 2026-06-18T10:01:03.000Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN IsCarrierAdviseManual CHAR(1) DEFAULT ''N'' CHECK (IsCarrierAdviseManual IN (''Y'',''N'')) NOT NULL')
;

-- =========================================================================
-- Column 2: M_Picking_Job_Line.Carrier_Goods_Type_ID (nullable FK, reuse AD_Element 584112)
-- =========================================================================

-- 2026-06-18T10:02:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592839 /*From ID Server*/,584112,0,19,541907,'XX','Carrier_Goods_Type_ID',TO_TIMESTAMP('2026-06-18 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Materialzuordnung je Lieferweg','NP',0,0,TO_TIMESTAMP('2026-06-18 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-18T10:02:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592839
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-18T10:02:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(584112)
;

-- Physical column (new nullable FK column — db_alter_table; no NOT NULL)
-- 2026-06-18T10:02:03.000Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN IF NOT EXISTS Carrier_Goods_Type_ID NUMERIC(10)')
;

-- 2026-06-18T10:02:04.000Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD CONSTRAINT CarrierGoodsType_MPickingJobLine FOREIGN KEY (Carrier_Goods_Type_ID) REFERENCES public.Carrier_Goods_Type DEFERRABLE INITIALLY DEFERRED')
;
