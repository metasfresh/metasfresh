-- gh#28565 Promotion Code / Aktionskennzeichen
-- Step 1.4: Add C_PromotionCode_ID + C_PromotionCode2_ID to C_Invoice_Candidate

-- ============================================================
-- C_PromotionCode_ID on C_Invoice_Candidate (AD_Table_ID=540270)
-- ============================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
                       IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                       IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn,
                       IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592184, 584619, 0, 19, 540270,
        'C_PromotionCode_ID', TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'de.metas.invoicecandidate',
        0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Aktionskennzeichen',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592184
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(584619);

/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate', 'ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_PromotionCode_ID NUMERIC(10)');

ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT CPromotionCode_CInvoiceCandidate
    FOREIGN KEY (C_PromotionCode_ID) REFERENCES public.C_PromotionCode DEFERRABLE INITIALLY DEFERRED;

-- ============================================================
-- C_PromotionCode2_ID on C_Invoice_Candidate
-- ============================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType,
                       FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary,
                       IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel,
                       IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory,
                       IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592185, 584620, 0, 18, 542070,
        540270, 'C_PromotionCode2_ID', TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'de.metas.invoicecandidate',
        0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Aktionskennzeichen 2',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592185
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(584620);

/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate', 'ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_PromotionCode2_ID NUMERIC(10)');

ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT CPromotionCode2_CInvoiceCandidate
    FOREIGN KEY (C_PromotionCode2_ID) REFERENCES public.C_PromotionCode DEFERRABLE INITIALLY DEFERRED;

-- ============================================================
-- AD_Field on IC tab (AD_Tab_ID=540279) — read-only, advanced
-- ============================================================

-- C_PromotionCode_ID field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592184, 774837, 0, 540279, 0,
        TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100, 0, 'de.metas.invoicecandidate', 0, 'Y', 'Y', 'N',
        'N', 'N', 'N', 'Y', 'N', 'Aktionskennzeichen', 0, 0, 0, 1, 1,
        TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774837
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584619);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774837;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774837);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774837, 0, 540279, 540056, 648485, 'F', TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'Y', 'N', 'Y', 'N', 'N', 'N', 0, 'Aktionskennzeichen', 1050, 0, 0,
        TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100);
-- Note: AD_UI_ElementGroup_ID=540056 is the existing advanced group on IC tab (same as used by Incoterms)

-- C_PromotionCode2_ID field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592185, 774838, 0, 540279, 0,
        TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100, 0, 'de.metas.invoicecandidate', 0, 'Y', 'Y', 'N',
        'N', 'N', 'N', 'Y', 'N', 'Aktionskennzeichen 2', 0, 0, 0, 1, 1,
        TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774838
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584620);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774838;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774838);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774838, 0, 540279, 540056, 648486, 'F', TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'Y', 'N', 'Y', 'N', 'N', 'N', 0, 'Aktionskennzeichen 2', 1060, 0, 0,
        TO_TIMESTAMP('2026-03-05 12:03', 'YYYY-MM-DD HH24:MI'), 100);
