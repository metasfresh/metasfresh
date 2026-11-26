-- Calibration fields on the sales order line: advanced edit only, own element group.
--
-- Adds the three read-only calibration columns of C_OrderLine (AD_Column 593730..593732, migration
-- 5828250) to the line tab "Auftragsposition" (AD_Tab 187) of the sales order window (AD_Window 143,
-- the window that quotations/proposals use as well: they are C_Order documents of another doc type,
-- there is no separate quotation window). Fields are shown only on lines a calibration rule matched
-- (DisplayLogic), only in the advanced edit (IsAdvancedField), never as grid column.
--
-- Layout: the line tab has ONE element group ("main", primary) holding all fields incl. Org and Client
-- (seq 210/220, not advanced). The calibration group is a second, own group after it
-- (SeqNo 20, no primary style): own section for the three advanced fields. The uncalibrated quantity
-- carries the line UOM as satellite widget (UOM adjacency); the UOM field already exists on the tab.
--
-- IDs allocated from idserver.metas.de on 2026-10-07:
--   AD_MigrationScript 5828300 (this script)
--   AD_Field 785624..785626, AD_UI_ElementGroup 555804, AD_UI_Element 654951..654953, AD_UI_ElementField 542569

-- Kalibrierfaktor
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, DisplayLogic, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 593730, 785624 /*From ID Server*/, 0, 187, TO_TIMESTAMP('2026-10-07 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, '@C_CompensationGroup_CalibrationRule_ID/0@>0', 'de.metas.order', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Kalibrierfaktor', 360, TO_TIMESTAMP('2026-10-07 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785624
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585522)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785624
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785624)
;
-- Kalibrierungsregel
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, DisplayLogic, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 593732, 785625 /*From ID Server*/, 0, 187, TO_TIMESTAMP('2026-10-07 09:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, '@C_CompensationGroup_CalibrationRule_ID/0@>0', 'de.metas.order', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Kalibrierungsregel', 370, TO_TIMESTAMP('2026-10-07 09:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785625
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585521)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785625
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785625)
;
-- Menge unkalibriert
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, DisplayLogic, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 593731, 785626 /*From ID Server*/, 0, 187, TO_TIMESTAMP('2026-10-07 09:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, '@C_CompensationGroup_CalibrationRule_ID/0@>0', 'de.metas.order', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Menge unkalibriert', 380, TO_TIMESTAMP('2026-10-07 09:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785626
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585523)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785626
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785626)
;
-- Own element group, after the "main" group (SeqNo 10); UIStyle NULL (the primary group stays "main")
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 1000000, 555804 /*From ID Server*/, TO_TIMESTAMP('2026-10-07 09:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'calibration', 20, TO_TIMESTAMP('2026-10-07 09:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785624, 0, 187, 555804, 654951 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-07 09:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'Y', 'Y', 'N', 'N', 'Kalibrierfaktor', 10, 0, 0, TO_TIMESTAMP('2026-10-07 09:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785625, 0, 187, 555804, 654952 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-07 09:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'Y', 'Y', 'N', 'N', 'Kalibrierungsregel', 20, 0, 0, TO_TIMESTAMP('2026-10-07 09:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785626, 0, 187, 555804, 654953 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-07 09:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'Y', 'Y', 'N', 'N', 'Menge unkalibriert', 30, 0, 0, TO_TIMESTAMP('2026-10-07 09:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Line UOM next to the uncalibrated quantity: satellite widget of the existing UOM field (AD_Field 1128)
INSERT INTO AD_UI_ElementField (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_UI_Element_ID, AD_UI_ElementField_ID, Created, CreatedBy, IsActive, SeqNo, Type, Updated, UpdatedBy)
VALUES (0, 1128, 0, 654953, 542569 /*From ID Server*/, TO_TIMESTAMP('2026-10-07 09:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, 'widget', TO_TIMESTAMP('2026-10-07 09:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
