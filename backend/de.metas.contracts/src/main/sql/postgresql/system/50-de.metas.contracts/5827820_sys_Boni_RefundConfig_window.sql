-- Boni refund engine: expose the new C_Flatrate_RefundConfig settings in window 540113 (Vertragsbedingungen)
-- and add the packaging-option child tab.
--
--   Tab 541106 (Rückvergütung): new fields M_Product_Category_ID, Bonus_Product_ID (after the product),
--     BonusRecipient (after 'Rückvergütung per'), IsPackingOptionFiltered (after 'In Roherlösberechnung').
--   New tab 549512 on C_Flatrate_RefundConfig_PackingOption (parent link C_Flatrate_RefundConfig_ID),
--     shown only if the parent has IsPackingOptionFiltered='Y'.
--
-- IDs allocated from idserver.metas.de on 2026-10-05:
--   AD_Field 785601..785609, AD_Tab 549512, AD_UI_Section 548004, AD_UI_Column 549773,
--   AD_UI_ElementGroup 555800, AD_UI_Element 654929..654936

-- 1) Fields on tab 541106
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785601, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541106, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'Y', 'Y', 33, 33, 0, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Table_ID = 540980 AND c.ColumnName = 'M_Product_Category_ID'
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, COALESCE(ct.Name, f.Name), COALESCE(ct.Description, f.Description), f.Help, 'Y',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785601
         LEFT JOIN AD_Column_Trl ct ON ct.AD_Column_ID = f.AD_Column_ID AND ct.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785602, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541106, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'Y', 'Y', 34, 34, 0, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Table_ID = 540980 AND c.ColumnName = 'Bonus_Product_ID'
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, COALESCE(ct.Name, f.Name), COALESCE(ct.Description, f.Description), f.Help, 'Y',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785602
         LEFT JOIN AD_Column_Trl ct ON ct.AD_Column_ID = f.AD_Column_ID AND ct.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785603, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541106, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'Y', 'Y', 52, 52, 0, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Table_ID = 540980 AND c.ColumnName = 'BonusRecipient'
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, COALESCE(ct.Name, f.Name), COALESCE(ct.Description, f.Description), f.Help, 'Y',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785603
         LEFT JOIN AD_Column_Trl ct ON ct.AD_Column_ID = f.AD_Column_ID AND ct.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785604, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541106, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'Y', 'Y', 71, 71, 0, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Table_ID = 540980 AND c.ColumnName = 'IsPackingOptionFiltered'
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, COALESCE(ct.Name, f.Name), COALESCE(ct.Description, f.Description), f.Help, 'Y',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785604
         LEFT JOIN AD_Column_Trl ct ON ct.AD_Column_ID = f.AD_Column_ID AND ct.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;

-- 2) UI elements on tab 541106 (existing group 541612); SeqNo/SeqNoGrid are collision-free on the AD_UI_Element layer
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType, Name, Description, Help,
                          IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField, SeqNo, SeqNoGrid, SeqNo_SideList, WidgetSize)
SELECT 654929, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541106, 541612, f.AD_Field_ID, 'F', f.Name, f.Description, f.Help,
       'Y', 'Y', 'N', 'N', 12, 12, 0, 'L'
FROM AD_Field f
WHERE f.AD_Field_ID = 785601
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType, Name, Description, Help,
                          IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField, SeqNo, SeqNoGrid, SeqNo_SideList, WidgetSize)
SELECT 654930, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541106, 541612, f.AD_Field_ID, 'F', f.Name, f.Description, f.Help,
       'Y', 'Y', 'N', 'N', 14, 14, 0, 'L'
FROM AD_Field f
WHERE f.AD_Field_ID = 785602
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType, Name, Description, Help,
                          IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField, SeqNo, SeqNoGrid, SeqNo_SideList, WidgetSize)
SELECT 654931, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541106, 541612, f.AD_Field_ID, 'F', f.Name, f.Description, f.Help,
       'Y', 'Y', 'N', 'N', 85, 85, 0, 'M'
FROM AD_Field f
WHERE f.AD_Field_ID = 785603
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType, Name, Description, Help,
                          IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField, SeqNo, SeqNoGrid, SeqNo_SideList, WidgetSize)
SELECT 654932, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541106, 541612, f.AD_Field_ID, 'F', f.Name, f.Description, f.Help,
       'Y', 'Y', 'N', 'N', 105, 105, 0, NULL
FROM AD_Field f
WHERE f.AD_Field_ID = 785604
;

-- 3) Child tab for the packaging options
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                    Name, Description, AD_Table_ID, AD_Window_ID, SeqNo, TabLevel, IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly,
                    Parent_Column_ID, DisplayLogic, EntityType, InternalName, AD_Element_ID, IsInsertRecord, IsSortTab, HasTree, ImportFields)
SELECT 549512, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100,
       e.Name, e.Description, 542654, 540113, 40, 2, 'N', 'N', 'N', 'N',
       593704, '@IsPackingOptionFiltered@=''Y''', 'de.metas.contracts', 'C_Flatrate_RefundConfig_PackingOption', e.AD_Element_ID, 'Y', 'N', 'N', 'N'
FROM AD_Element e
WHERE e.AD_Element_ID = 585515
;
INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, COALESCE(et.Name, t.Name), COALESCE(et.Description, t.Description), 'Y',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l
         JOIN AD_Tab t ON t.AD_Tab_ID = 549512
         LEFT JOIN AD_Element_Trl et ON et.AD_Element_ID = t.AD_Element_ID AND et.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785605, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'Y', 'Y', 10, 10, 1, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Table_ID = 542654 AND c.ColumnName = 'M_HU_PackingMaterial_ID'
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, COALESCE(ct.Name, f.Name), COALESCE(ct.Description, f.Description), f.Help, 'Y',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785605
         LEFT JOIN AD_Column_Trl ct ON ct.AD_Column_ID = f.AD_Column_ID AND ct.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785606, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'Y', 'Y', 20, 20, 0, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Table_ID = 542654 AND c.ColumnName = 'IsActive'
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, COALESCE(ct.Name, f.Name), COALESCE(ct.Description, f.Description), f.Help, 'Y',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785606
         LEFT JOIN AD_Column_Trl ct ON ct.AD_Column_ID = f.AD_Column_ID AND ct.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785607, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'N', 'N', 0, 0, 0, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Table_ID = 542654 AND c.ColumnName = 'C_Flatrate_RefundConfig_ID'
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, COALESCE(ct.Name, f.Name), COALESCE(ct.Description, f.Description), f.Help, 'Y',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785607
         LEFT JOIN AD_Column_Trl ct ON ct.AD_Column_ID = f.AD_Column_ID AND ct.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785608, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'Y', 'Y', 30, 30, 0, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Table_ID = 542654 AND c.ColumnName = 'AD_Org_ID'
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, COALESCE(ct.Name, f.Name), COALESCE(ct.Description, f.Description), f.Help, 'Y',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785608
         LEFT JOIN AD_Column_Trl ct ON ct.AD_Column_ID = f.AD_Column_ID AND ct.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785609, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'N', 'N', 0, 0, 0, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Table_ID = 542654 AND c.ColumnName = 'AD_Client_ID'
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, COALESCE(ct.Name, f.Name), COALESCE(ct.Description, f.Description), f.Help, 'Y',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785609
         LEFT JOIN AD_Column_Trl ct ON ct.AD_Column_ID = f.AD_Column_ID AND ct.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;

INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tab_ID, SeqNo, Value)
VALUES (548004, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100, 549512, 10, 'main')
;
INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES (549773, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100, 548004, 10)
;
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555800, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100, 549773, 10, 'primary', 'default')
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType, Name, Description, Help,
                          IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField, SeqNo, SeqNoGrid, SeqNo_SideList, WidgetSize)
SELECT 654933, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, 555800, f.AD_Field_ID, 'F', f.Name, f.Description, f.Help,
       'Y', 'Y', 'N', 'N', 10, 10, 0, 'L'
FROM AD_Field f
WHERE f.AD_Field_ID = 785605
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType, Name, Description, Help,
                          IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField, SeqNo, SeqNoGrid, SeqNo_SideList, WidgetSize)
SELECT 654934, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, 555800, f.AD_Field_ID, 'F', f.Name, f.Description, f.Help,
       'Y', 'Y', 'N', 'N', 20, 20, 0, NULL
FROM AD_Field f
WHERE f.AD_Field_ID = 785606
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType, Name, Description, Help,
                          IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField, SeqNo, SeqNoGrid, SeqNo_SideList, WidgetSize)
SELECT 654935, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:38', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:39', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, 555800, f.AD_Field_ID, 'F', f.Name, f.Description, f.Help,
       'Y', 'Y', 'N', 'N', 30, 30, 0, 'M'
FROM AD_Field f
WHERE f.AD_Field_ID = 785608
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType, Name, Description, Help,
                          IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField, SeqNo, SeqNoGrid, SeqNo_SideList, WidgetSize)
SELECT 654936, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 12:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 12:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, 555800, f.AD_Field_ID, 'F', f.Name, f.Description, f.Help,
       'Y', 'N', 'N', 'N', 40, 40, 0, NULL
FROM AD_Field f
WHERE f.AD_Field_ID = 785609
;
