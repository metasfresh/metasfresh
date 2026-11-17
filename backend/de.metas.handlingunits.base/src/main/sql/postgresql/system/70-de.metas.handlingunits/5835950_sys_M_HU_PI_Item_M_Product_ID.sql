-- IDs allocated from idserver.metas.de on 2026-11-16:
--   AD_Column     593850 (M_HU_PI_Item.M_Product_ID)
--   AD_Field      788183 (Packvorschrift Version window 540344, tab Position 540824)
--   AD_UI_Element 656500 (same tab, default group 540436)
-- Reused: AD_Element 454 (M_Product_ID); AD_Table 540509 (M_HU_PI_Item); AD_Reference 540272 (M_Product search)
--
-- Optional product on a packing-instruction item (ItemType=HU): lets a product get its own TUs-per-LU quantity.

-- Column: M_HU_PI_Item.M_Product_ID
-- 2026-11-16T10:00:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593850 /*From ID Server*/,454,0,30,540272,540509,'XX','M_Product_ID',TO_TIMESTAMP('2026-11-16 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt','NP',0,0,TO_TIMESTAMP('2026-11-16 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-11-16T10:00:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593850
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-11-16T10:00:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(454)
;

-- Physical column + FK (new column: ALTER TABLE ADD COLUMN, not t_alter_column)
-- 2026-11-16T10:01:00.000Z
/* DDL */ SELECT public.db_alter_table('M_HU_PI_Item','ALTER TABLE public.M_HU_PI_Item ADD COLUMN IF NOT EXISTS M_Product_ID NUMERIC(10)')
;

-- 2026-11-16T10:01:01.000Z
ALTER TABLE M_HU_PI_Item ADD CONSTRAINT MProduct_MHUPIItem FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Field: Packvorschrift Version(540344,de.metas.handlingunits) -> Position(540824,de.metas.handlingunits) -> Produkt
-- Column: M_HU_PI_Item.M_Product_ID
-- 2026-11-16T10:02:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593850,788183 /*From ID Server*/,0,540824,TO_TIMESTAMP('2026-11-16 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'@ItemType@ = HU','de.metas.handlingunits','Y','Y','Y','N','N','N','N','N','Produkt',TO_TIMESTAMP('2026-11-16 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-11-16T10:02:01.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=788183
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-11-16T10:02:02.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2026-11-16T10:02:03.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=788183
;

-- 2026-11-16T10:02:04.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(788183)
;

-- UI Element: Packvorschrift Version(540344) -> Position(540824) -> main -> default group: Produkt
-- grid: between Unter-Packvorschrift (40) and Geschaeftspartner (50)
-- 2026-11-16T10:03:00.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize)
VALUES (0,788183,0,540824,540436,656500 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-16 10:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Produkt',0,45,0,TO_TIMESTAMP('2026-11-16 10:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;
