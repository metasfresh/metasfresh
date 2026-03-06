-- gh#28565: Add Description column to C_PromotionCode

-- AD_Column
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                        ColumnName, Created, CreatedBy, EntityType, FacetFilterSeqNo, FieldLength,
                        IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                        IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets,
                        IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                        IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn,
                        IsTranslated, IsUpdateable, Name, PersonalDataCategory, SelectionColumnSeqNo, SeqNo,
                        Updated, UpdatedBy, Version)
VALUES (0, 592197, 275, 0, 10, 542586,
        'Description', TO_TIMESTAMP('2026-03-06 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'D', 0, 1024,
        'Y', 'N', 'Y', 'N', 'N',
        'N', 'N', 'N', 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Beschreibung', 'NP', 0, 0,
        TO_TIMESTAMP('2026-03-06 10:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

-- DDL
ALTER TABLE C_PromotionCode ADD COLUMN IF NOT EXISTS Description VARCHAR(1024);

-- AD_Field on C_PromotionCode tab (AD_Tab_ID=549080)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                       Created, CreatedBy, EntityType, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid,
                       IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid,
                       SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592197, 774854, 0, 549080, 0,
        TO_TIMESTAMP('2026-03-06 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'D', 0, 'Y', 'Y', 'Y',
        'N', 'N', 'N', 'N', 'N', 'Beschreibung', 0, 0,
        0, 1, 1, TO_TIMESTAMP('2026-03-06 10:00', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 774854
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- AD_UI_Element for Description (SeqNo=25: between Name=20 and ValidTo=30)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                            AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                            IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                            Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774854, 0, 549080, 554978, 648504, 'F', TO_TIMESTAMP('2026-03-06 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'N', 'N', 'Y', 'Y', 'N', 'N', 0, 'Beschreibung', 25, 25, 0, TO_TIMESTAMP('2026-03-06 10:00', 'YYYY-MM-DD HH24:MI'), 100);
