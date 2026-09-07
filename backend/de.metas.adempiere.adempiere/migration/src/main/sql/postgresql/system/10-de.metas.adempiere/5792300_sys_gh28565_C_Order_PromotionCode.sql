-- gh#28565 Promotion Code / Aktionskennzeichen
-- Step 1.3: Drop old PromotionCode varchar from C_Order, add C_PromotionCode_ID + C_PromotionCode2_ID

-- ============================================================
-- Drop old PromotionCode varchar column (AD_Column_ID=57127)
-- ============================================================

-- Deactivate AD_Column (keep record for audit, remove from model generation)
UPDATE AD_Column SET IsActive = 'N', Updated = TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Column_ID = 57127;

-- Remove any AD_UI_Element pointing to fields of this column
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 57127);

-- Remove AD_Element_Link for these fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 57127);

-- Remove AD_Field_Trl
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 57127);

-- Remove AD_Field
DELETE FROM AD_Field WHERE AD_Column_ID = 57127;

-- Drop physical column
/* DDL */ SELECT public.db_alter_table('C_Order', 'ALTER TABLE public.C_Order DROP COLUMN IF EXISTS PromotionCode');

-- ============================================================
-- Add C_PromotionCode_ID (AD_Reference_ID=19 TableDir, AD_Val_Rule for Order)
-- ============================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       AD_Val_Rule_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType,
                       FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary,
                       IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel,
                       IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory,
                       IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592182, 584619, 0, 19, 259,
        540771, 'C_PromotionCode_ID', TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D',
        0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Aktionskennzeichen',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592182
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(584619);

/* DDL */ SELECT public.db_alter_table('C_Order', 'ALTER TABLE public.C_Order ADD COLUMN C_PromotionCode_ID NUMERIC(10)');

ALTER TABLE C_Order ADD CONSTRAINT CPromotionCode_COrder
    FOREIGN KEY (C_PromotionCode_ID) REFERENCES public.C_PromotionCode DEFERRABLE INITIALLY DEFERRED;

-- ============================================================
-- Add C_PromotionCode2_ID (AD_Reference_ID=18 Table, AD_Reference_Value_ID=542070, AD_Val_Rule for Order)
-- ============================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       AD_Table_ID, AD_Val_Rule_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType,
                       FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary,
                       IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel,
                       IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory,
                       IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592183, 584620, 0, 18, 542070,
        259, 540771, 'C_PromotionCode2_ID', TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D',
        0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Aktionskennzeichen 2',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592183
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(584620);

/* DDL */ SELECT public.db_alter_table('C_Order', 'ALTER TABLE public.C_Order ADD COLUMN C_PromotionCode2_ID NUMERIC(10)');

ALTER TABLE C_Order ADD CONSTRAINT CPromotionCode2_COrder
    FOREIGN KEY (C_PromotionCode2_ID) REFERENCES public.C_PromotionCode DEFERRABLE INITIALLY DEFERRED;

-- ============================================================
-- AD_Field on base Sales Order tab (AD_Tab_ID=186) — advanced edit
-- ============================================================

-- C_PromotionCode_ID field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592182, 774835, 0, 186, 0,
        TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100, 0, 'D', 0, 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N',
        'Aktionskennzeichen', 1050, 0, 0, 1, 1, TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774835
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584619);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774835;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774835);

-- AD_UI_Element (advanced)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774835, 0, 186, (SELECT AD_UI_ElementGroup_ID FROM AD_UI_ElementGroup WHERE AD_UI_Column_ID IN
        (SELECT AD_UI_Column_ID FROM AD_UI_Column WHERE AD_UI_Section_ID IN
        (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID = 186 ORDER BY SeqNo LIMIT 1)
        ORDER BY SeqNo LIMIT 1) ORDER BY SeqNo LIMIT 1),
        648483, 'F', TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'Y', 'N', 'Y', 'N', 'N', 'N', 0, 'Aktionskennzeichen', 1050, 0, 0,
        TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100);

-- C_PromotionCode2_ID field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592183, 774836, 0, 186, 0,
        TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100, 0, 'D', 0, 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N',
        'Aktionskennzeichen 2', 1060, 0, 0, 1, 1, TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774836
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584620);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774836;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774836);

-- AD_UI_Element (advanced)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774836, 0, 186, (SELECT AD_UI_ElementGroup_ID FROM AD_UI_ElementGroup WHERE AD_UI_Column_ID IN
        (SELECT AD_UI_Column_ID FROM AD_UI_Column WHERE AD_UI_Section_ID IN
        (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID = 186 ORDER BY SeqNo LIMIT 1)
        ORDER BY SeqNo LIMIT 1) ORDER BY SeqNo LIMIT 1),
        648484, 'F', TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'Y', 'N', 'Y', 'N', 'N', 'N', 0, 'Aktionskennzeichen 2', 1060, 0, 0,
        TO_TIMESTAMP('2026-03-05 12:02', 'YYYY-MM-DD HH24:MI'), 100);
