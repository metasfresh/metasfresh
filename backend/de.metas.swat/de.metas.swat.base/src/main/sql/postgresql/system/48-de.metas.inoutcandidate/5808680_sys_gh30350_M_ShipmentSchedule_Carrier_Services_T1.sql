-- me03 #30350 (STEP3 T1) — add the carrier-services LABEL field (string) to M_ShipmentSchedule.
-- The schedule already has Carrier_Product_ID, Carrier_Goods_Type_ID, Carrier_Advising_Status but no services column
-- (services were only a loaded Set<CarrierServiceId> on the domain object). Adding it lets the picking-job line be
-- seeded from the schedule consistently (later task). The field is configured READ-ONLY on the standard window —
-- all carrier-advise fields are written only by the advise process, never by generic field editing.
--
-- This script handles the STANDARD window 500221 (de.metas.inoutcandidate). The dt204 customer OVERRIDE window 541965
-- gets the same read-only field in a separate next-numbered customer-repo script (5808690).
--
-- IDs allocated from idserver.metas.de on 2026-06-18:
--   AD_Element    585024 (Carrier_Services — the schedule's services LABEL string; this script now owns its creation,
--                         since the picking-line side no longer carries a String column — it uses a junction table instead)
--   AD_Column     592841 (M_ShipmentSchedule.Carrier_Services)
--   AD_Field      781217 (standard window 500221, tab 500221)
--   AD_UI_Element 652329 (standard window 500221, tab 500221, group "advanced edit" 540052)
--
-- Referenced existing IDs (verified from the local DB):
--   AD_Table   500221 (M_ShipmentSchedule)
--   AD_Tab     500221 (Auslieferplan, standard window 500221)
--   AD_UI_ElementGroup 540052 ("advanced edit", standard window tab 500221)
--   AD_Reference 10 (String)

-- =========================================================================
-- AD_Element: Carrier_Services (the schedule services label string)
-- =========================================================================

-- 2026-06-18T10:59:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585024 /*From ID Server*/,0,'Carrier_Services',TO_TIMESTAMP('2026-06-18 10:59:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Lieferweg-Services','Lieferweg-Services',TO_TIMESTAMP('2026-06-18 10:59:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-18T10:59:01.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585024
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US override
-- 2026-06-18T10:59:12.000Z
UPDATE AD_Element_Trl SET Name='Carrier Services', PrintName='Carrier Services', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-18 10:59:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585024 AND AD_Language='en_US'
;

-- de_DE / de_CH active-translated (same text as base; de_CH identical, no ß present)
-- 2026-06-18T10:59:13.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-18 10:59:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585024 AND AD_Language IN ('de_DE','de_CH')
;

-- propagate the element overrides down to all dependent _Trl tables
-- 2026-06-18T10:59:14.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585024 /*AD_Element_ID*/, 'en_US')
;

-- 2026-06-18T10:59:15.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585024 /*AD_Element_ID*/, 'de_DE')
;

-- 2026-06-18T10:59:16.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585024 /*AD_Element_ID*/, 'de_CH')
;

-- =========================================================================
-- AD_Column: M_ShipmentSchedule.Carrier_Services (String, nullable, read-only via field config)
-- =========================================================================

-- 2026-06-18T11:00:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592841 /*From ID Server*/,585024,0,10,500221,'XX','Carrier_Services',TO_TIMESTAMP('2026-06-18 11:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.inoutcandidate',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferweg-Services','NP',0,0,TO_TIMESTAMP('2026-06-18 11:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-18T11:00:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592841
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-18T11:00:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(585024)
;

-- Physical column (new nullable String column — db_alter_table; no NOT NULL)
-- 2026-06-18T11:00:03.000Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN IF NOT EXISTS Carrier_Services VARCHAR(255)')
;

-- =========================================================================
-- AD_Field on the STANDARD window 500221 / tab 500221 (read-only, alongside the existing carrier fields)
-- =========================================================================

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221) -> Lieferweg-Services
-- Column: M_ShipmentSchedule.Carrier_Services
-- 2026-06-18T11:01:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,592841,781217 /*From ID Server*/,0,500221,0,TO_TIMESTAMP('2026-06-18 11:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','Y','N',0,'Lieferweg-Services',0,0,770,0,1,1,TO_TIMESTAMP('2026-06-18 11:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-18T11:01:01.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781217
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-18T11:01:02.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585024)
;

-- 2026-06-18T11:01:03.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781217
;

-- 2026-06-18T11:01:04.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781217)
;

-- EntityType normalization: the AD_Field is dictionary metadata ('D'), matching the sibling carrier AD_Fields
-- on this window (Carrier_Advising_Status / Carrier_Goods_Type_ID / Carrier_Product_ID, all 'D' in 5773622).
-- Self-healing UPDATE so the field ends 'D' even where it was previously created with the AD_Column's EntityType.
-- 2026-06-18T11:01:05.000Z
UPDATE AD_Field SET EntityType='D',
  Updated=TO_TIMESTAMP('2026-06-18 11:01:05.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Field_ID=781217 AND EntityType<>'D'
;

-- AD_UI_Element pairing (group 540052 "advanced edit"; mirrors the existing carrier fields on this window)
-- 2026-06-18T11:01:30.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781217,0,500221,540052,652329 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-18 11:01:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Lieferweg-Services',400,0,0,TO_TIMESTAMP('2026-06-18 11:01:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
