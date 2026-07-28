-- gh#28565 Promotion Code / Aktionskennzeichen
-- Step 1.2: AD_Reference (Table type for C_PromotionCode2_ID), AD_Val_Rules, base window + menu

-- ============================================================
-- AD_Element for C_PromotionCode2_ID
-- ============================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy,
                        EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584620, 0, 'C_PromotionCode2_ID', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'D', 'Y', 'Aktionskennzeichen 2', 'Aktionskennzeichen 2', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Element_ID = 584620
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'Promotion Code 2', PrintName = 'Promotion Code 2', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584620 AND AD_Language = 'en_US';

-- ============================================================
-- AD_Reference (Table type) for C_PromotionCode2_ID lookups
-- ============================================================
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType,
                          IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542070, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 'D',
        'Y', 'N', 'C_PromotionCode', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 'T');

INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Reference_ID = 542070
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID);

-- AD_Ref_Table: points to C_PromotionCode table
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Display, AD_Key, AD_Org_ID, AD_Reference_ID,
                          AD_Table_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, Updated, UpdatedBy)
VALUES (0, 592180, 592171, 0, 542070,
        542586, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 'D', 'Y', 'N',
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);
-- AD_Key=592171 (C_PromotionCode_ID column), AD_Display=592180 (Name column), AD_Table_ID=542586

-- ============================================================
-- AD_Val_Rules: dynamic dropdown filtering
-- ============================================================

-- Order context: filter by DateOrdered
INSERT INTO AD_Val_Rule (AD_Client_ID, AD_Org_ID, AD_Val_Rule_ID, Code, Created, CreatedBy, EntityType,
                         IsActive, Name, Type, Updated, UpdatedBy)
VALUES (0, 0, 540771,
        'C_PromotionCode.IsActive=''Y'' AND (C_PromotionCode.ValidTo IS NULL OR C_PromotionCode.ValidTo >= @DateOrdered@)',
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 'D',
        'Y', 'C_PromotionCode for Order (active + valid)', 'S', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

-- Invoice context: filter by DateInvoiced
INSERT INTO AD_Val_Rule (AD_Client_ID, AD_Org_ID, AD_Val_Rule_ID, Code, Created, CreatedBy, EntityType,
                         IsActive, Name, Type, Updated, UpdatedBy)
VALUES (0, 0, 540772,
        'C_PromotionCode.IsActive=''Y'' AND (C_PromotionCode.ValidTo IS NULL OR C_PromotionCode.ValidTo >= @DateInvoiced@)',
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 'D',
        'Y', 'C_PromotionCode for Invoice (active + valid)', 'S', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

-- ============================================================
-- Base Window: Aktionskennzeichen (Promotion Code)
-- ============================================================
-- AD_Element for the window
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive,
                        Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584625, 0, 'Aktionskennzeichen_Window', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 'D', 'Y',
        'Aktionskennzeichen', 'Aktionskennzeichen', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Element_ID = 584625
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);
UPDATE AD_Element_Trl SET Name = 'Promotion Code', PrintName = 'Promotion Code', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584625 AND AD_Language = 'en_US';

INSERT INTO AD_Window (AD_Client_ID, AD_Org_ID, AD_Window_ID, AD_Element_ID, Created, CreatedBy, EntityType, IsActive,
                       IsBetaFunctionality, IsDefault, IsEnableRemoteCacheInvalidation, IsExcludeFromZoomTargets,
                       IsOneInstanceOnly, IsSOTrx, Name, Processing, WindowType, WinHeight, WinWidth, Updated, UpdatedBy)
VALUES (0, 0, 542105, 584625, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 'D', 'Y',
        'N', 'N', 'N', 'N', 'N', 'Y', 'Aktionskennzeichen', 'N', 'M', 0, 0,
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Window_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Window t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Window_ID = 542105
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = t.AD_Window_ID);

UPDATE AD_Window_Trl SET Name = 'Promotion Code', IsTranslated = 'Y',
                         Updated = TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Window_ID = 542105 AND AD_Language = 'en_US';

-- Tab
INSERT INTO AD_Tab (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Element_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy,
                    EntityType, HasTree, ImportFields, IsActive, IsAdvancedTab, IsCheckParentsChanged,
                    IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord, IsQueryOnLoad, IsReadOnly,
                    IsRefreshAllOnActivate, IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab,
                    IsTranslationTab, MaxQueryRecords, Name, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 0, 549080, 584625, 542586, 542105, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'D', 'N', 'N', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N',
        'N', 'Y', 'Y', 'N', 'N', 'N', 0, 'Aktionskennzeichen', 'N', 10, 0,
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Tab_ID = 549080
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID);

UPDATE AD_Tab_Trl SET Name = 'Promotion Code', IsTranslated = 'Y',
                      Updated = TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Tab_ID = 549080 AND AD_Language = 'en_US';

-- ============================================================
-- Fields on C_PromotionCode window tab
-- ============================================================

-- Value field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592179, 774829, 0, 549080, 0,
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N',
        'Suchschlüssel', 10, 10, 0, 1, 1, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774829
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(620);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774829;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774829);

-- Name field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592180, 774830, 0, 549080, 0,
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y',
        'Name', 20, 20, 0, 1, 1, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774830
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(469);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774830;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774830);

-- ValidTo field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592181, 774831, 0, 549080, 0,
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N',
        'Gültig bis', 30, 30, 0, 1, 1, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774831
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(618);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774831;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774831);

-- IsActive field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592176, 774832, 0, 549080, 0,
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y',
        'Aktiv', 40, 40, 0, 1, 1, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774832
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774832;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774832);

-- Org field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592173, 774833, 0, 549080, 0,
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N',
        'Sektion', 50, 50, 0, 1, 1, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774833
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(113);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774833;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774833);

-- Client field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength,
                      Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed,
                      IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
                      SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592172, 774834, 0, 549080, 0,
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 0, 'D', 0, 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y',
        'Mandant', 60, 60, 0, 1, 1, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y') AND t.AD_Field_ID = 774834
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(102);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 774834;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774834);

-- ============================================================
-- AD_UI layout for C_PromotionCode window
-- ============================================================

-- UI Section
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Section_ID, Created, CreatedBy,
                           IsActive, Name, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 549080, 547597, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'main', 10, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100, 'main');

-- UI Column
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy,
                          IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549273, 547597, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 10, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

-- UI Element Group (main fields)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy,
                                IsActive, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549273, 554978, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'default', 10, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

-- UI Elements
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774829, 0, 549080, 554978, 648477, 'F', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'N', 'Y', 'Y', 'Y', 'N', 'N', 0, 'Suchschlüssel', 10, 10, 0, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774830, 0, 549080, 554978, 648478, 'F', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'N', 'Y', 'Y', 'Y', 'N', 'N', 0, 'Name', 20, 20, 0, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774831, 0, 549080, 554978, 648479, 'F', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'N', 'N', 'Y', 'Y', 'N', 'N', 0, 'Gültig bis', 30, 30, 0, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774832, 0, 549080, 554978, 648480, 'F', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'N', 'N', 'Y', 'Y', 'N', 'N', 0, 'Aktiv', 40, 40, 0, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

-- Org + Client as advanced fields (design guide: bottom-right)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774833, 0, 549080, 554978, 648481, 'F', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'Y', 'N', 'Y', 'N', 'N', 'N', 0, 'Sektion', 50, 0, 0, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774834, 0, 549080, 554978, 648482, 'F', TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'Y', 'Y', 'N', 'Y', 'N', 'N', 'N', 0, 'Mandant', 60, 0, 0, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

-- ============================================================
-- Menu entry
-- ============================================================
INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Window_ID, Created, CreatedBy,
                     EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name,
                     Updated, UpdatedBy)
VALUES ('W', 0, 584625, 542302, 0, 542105, TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100,
        'D', 'C_PromotionCode', 'Y', 'N', 'N', 'N', 'N', 'Aktionskennzeichen',
        TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WebUI_NameBrowse, WebUI_NameNew, WebUI_NameNewBreadcrumb,
                         IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WebUI_NameBrowse, t.WebUI_NameNew, t.WebUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Menu_ID = 542302
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID);

UPDATE AD_Menu_Trl SET Name = 'Promotion Code', IsTranslated = 'Y',
                       Updated = TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Menu_ID = 542302 AND AD_Language = 'en_US';

-- Tree node
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID, 0, 'Y', now(), 100, now(), 100,
       t.AD_Tree_ID, 542302, 0, 999
FROM AD_Tree t
WHERE t.AD_Client_ID = 0 AND t.IsActive = 'Y' AND t.IsAllNodes = 'Y'
  AND t.AD_Table_ID = 116
  AND NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM tnm WHERE tnm.AD_Tree_ID = t.AD_Tree_ID AND tnm.Node_ID = 542302);

-- Link AD_Ref_Table to the window for zoom
UPDATE AD_Ref_Table SET AD_Window_ID = 542105,
                        Updated = TO_TIMESTAMP('2026-03-05 12:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Reference_ID = 542070;
