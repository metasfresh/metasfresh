-- gh#28565 Promotion Code / Aktionskennzeichen
-- Step 1.5: Add C_PromotionCode_ID + C_PromotionCode2_ID to C_Invoice

-- ============================================================
-- C_PromotionCode_ID on C_Invoice (AD_Table_ID=318)
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
VALUES (0, 592186, 584619, 0, 19, 318,
        540772, 'C_PromotionCode_ID', TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D',
        0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Aktionskennzeichen',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592186
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(584619);

/* DDL */ SELECT public.db_alter_table('C_Invoice', 'ALTER TABLE public.C_Invoice ADD COLUMN C_PromotionCode_ID NUMERIC(10)');

ALTER TABLE C_Invoice ADD CONSTRAINT CPromotionCode_CInvoice
    FOREIGN KEY (C_PromotionCode_ID) REFERENCES public.C_PromotionCode DEFERRABLE INITIALLY DEFERRED;

-- ============================================================
-- C_PromotionCode2_ID on C_Invoice
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
VALUES (0, 592187, 584620, 0, 18, 542070,
        318, 540772, 'C_PromotionCode2_ID', TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D',
        0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Aktionskennzeichen 2',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592187
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(584620);

/* DDL */ SELECT public.db_alter_table('C_Invoice', 'ALTER TABLE public.C_Invoice ADD COLUMN C_PromotionCode2_ID NUMERIC(10)');

ALTER TABLE C_Invoice ADD CONSTRAINT CPromotionCode2_CInvoice
    FOREIGN KEY (C_PromotionCode2_ID) REFERENCES public.C_PromotionCode DEFERRABLE INITIALLY DEFERRED;

-- ============================================================
-- AD_Field on base Invoice tab — advanced edit, editable (IsReadOnly='N')
-- We need to find the base invoice tab. For base Invoice window (AD_Window_ID=167), tab is typically 263.
-- ============================================================

-- C_PromotionCode_ID field on Invoice base tab
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592186, 774839, 0, 263, 0,
        TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100, 0, 'D', 0, 'Y', 'Y', 'N',
        'N', 'N', 'N', 'N', 'N', 'Aktionskennzeichen', 0, 0, 0, 1, 1,
        TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774839
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584619);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774839;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774839);

-- AD_UI_Element (advanced)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774839, 0, 263, (SELECT AD_UI_ElementGroup_ID FROM AD_UI_ElementGroup WHERE AD_UI_Column_ID IN
        (SELECT AD_UI_Column_ID FROM AD_UI_Column WHERE AD_UI_Section_ID IN
        (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID = 263 ORDER BY SeqNo LIMIT 1)
        ORDER BY SeqNo LIMIT 1) ORDER BY SeqNo LIMIT 1),
        648487, 'F', TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'Y', 'N', 'Y', 'N', 'N', 'N', 0, 'Aktionskennzeichen', 1050, 0, 0,
        TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100);

-- C_PromotionCode2_ID field on Invoice base tab
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592187, 774840, 0, 263, 0,
        TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100, 0, 'D', 0, 'Y', 'Y', 'N',
        'N', 'N', 'N', 'N', 'N', 'Aktionskennzeichen 2', 0, 0, 0, 1, 1,
        TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774840
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584620);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774840;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774840);

-- AD_UI_Element (advanced)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774840, 0, 263, (SELECT AD_UI_ElementGroup_ID FROM AD_UI_ElementGroup WHERE AD_UI_Column_ID IN
        (SELECT AD_UI_Column_ID FROM AD_UI_Column WHERE AD_UI_Section_ID IN
        (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID = 263 ORDER BY SeqNo LIMIT 1)
        ORDER BY SeqNo LIMIT 1) ORDER BY SeqNo LIMIT 1),
        648488, 'F', TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'Y', 'N', 'Y', 'N', 'N', 'N', 0, 'Aktionskennzeichen 2', 1060, 0, 0,
        TO_TIMESTAMP('2026-03-05 12:04', 'YYYY-MM-DD HH24:MI'), 100);
