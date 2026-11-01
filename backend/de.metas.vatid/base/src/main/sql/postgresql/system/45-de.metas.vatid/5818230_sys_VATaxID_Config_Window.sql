-- VAT-ID online check: VATaxID_Config window (single main tab, 2-column layout).
-- Layout: left/primary = the VIES connection settings; right/flags = IsActive + the two
-- enable switches (IsActive first); right/org = AD_Org_ID then AD_Client_ID (cornerstones
-- per metasfresh-window-design-rules). No customer override window exists yet for this
-- brand-new table, so there is nothing to mirror in a customer repo at this stage.

-- IDs allocated from idserver.metas.de:
--   AD_Window            542182
--   AD_Tab               549363
--   AD_UI_Section        547868
--   AD_UI_Column         549617..549618 (left, right)
--   AD_UI_ElementGroup    555538..555540 (left/primary, right/flags, right/org)
--   AD_Field              781903..781912 (10)
--   AD_UI_Element         652816..652825 (10, 1:1 with the fields above)

-- 1. AD_Window (reuses the table's own AD_Element 585165 for the caption)
INSERT INTO AD_Window (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Name, WindowType, IsSOTrx, EntityType, IsDefault, AD_Element_ID, ZoomIntoPriority)
VALUES (542182 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-11 14:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 14:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'USt-IdNr.-Konfiguration', 'M', 'Y', 'D', 'N', 585165, 100);

UPDATE AD_Table SET AD_Window_ID = 542182, Updated = TO_TIMESTAMP('2026-08-11 14:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542638;

-- 2. AD_Tab — single header/main tab (reuses the same element for its caption)
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                     Name, AD_Table_ID, AD_Window_ID, SeqNo, TabLevel, IsSingleRow, EntityType, AD_Element_ID)
VALUES (549363 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-11 14:10:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 14:10:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'USt-IdNr.-Konfiguration', 542638, 542182, 10, 0, 'Y', 'D', 585165);

INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Window_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Window t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Window_ID = 542182
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = t.AD_Window_ID);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 549363
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585165);

-- 3. Section / columns / element groups
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Tab_ID, SeqNo, Name)
VALUES (547868 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-11 14:10:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 14:10:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        549363, 10, 'default');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_Section_ID, SeqNo)
VALUES
    (549617 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 547868, 10),
    (549618 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:12', 'YYYY-MM-DD HH24:MI:SS'), 100, 547868, 20);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555538 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 549617, 10, 'primary', 'default'),
    (555539 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:14', 'YYYY-MM-DD HH24:MI:SS'), 100, 549618, 10, NULL, 'flags'),
    (555540 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 549618, 20, NULL, 'default');

-- 4. AD_Field — left/primary group: VIES connection settings
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, SeqNoGrid, IsDisplayedGrid, IsReadOnly, IsMandatory, EntityType)
VALUES
    (781903 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RestApiBaseURL', 549363, 593142, 'Y', 10, 0, 'N', 'N', 'N', 'D'),
    (781904 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RequesterMemberStateCode', 549363, 593143, 'Y', 20, 0, 'N', 'N', 'N', 'D'),
    (781905 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RequesterNumber', 549363, 593144, 'Y', 30, 0, 'N', 'N', 'N', 'D'),
    (781906 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:23', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:23', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RecheckAfterDays', 549363, 593145, 'Y', 40, 40, 'Y', 'N', 'Y', 'D'),
    (781907 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:24', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:24', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'OnServiceUnavailable', 549363, 593146, 'Y', 50, 50, 'Y', 'N', 'Y', 'D');

-- 5. AD_Field — right/flags group (IsActive first)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, SeqNoGrid, IsDisplayedGrid, IsReadOnly, IsMandatory, EntityType)
VALUES
    (781908 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'IsActive', 549363, 593135, 'Y', 10, 10, 'Y', 'N', 'Y', 'D'),
    (781909 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'IsFormatCheckEnabled', 549363, 593140, 'Y', 20, 20, 'Y', 'N', 'Y', 'D'),
    (781910 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:27', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:27', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'IsVIESCheckEnabled', 549363, 593141, 'Y', 30, 30, 'Y', 'N', 'Y', 'D');

-- 6. AD_Field — right/org group (Org, then Client — last two, cornerstone rule)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, SeqNoGrid, IsDisplayedGrid, IsReadOnly, IsMandatory, EntityType)
VALUES
    (781911 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:28', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:28', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'AD_Org_ID', 549363, 593134, 'Y', 10, 60, 'Y', 'N', 'Y', 'D'),
    (781912 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:10:29', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:10:29', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'AD_Client_ID', 549363, 593133, 'Y', 20, 0, 'N', 'N', 'Y', 'D');

-- 7. AD_Field_Trl skeleton rows for all 10 fields, then propagate from each column's element
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 549363
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(c.AD_Element_ID)
FROM AD_Column c
WHERE c.AD_Column_ID IN (593142, 593143, 593144, 593145, 593146, 593135, 593140, 593141, 593134, 593133);

DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (781903, 781904, 781905, 781906, 781907, 781908, 781909, 781910, 781911, 781912);
SELECT AD_Element_Link_Create_Missing_Field(f.AD_Field_ID)
FROM (VALUES (781903), (781904), (781905), (781906), (781907), (781908), (781909), (781910), (781911), (781912)) AS f(AD_Field_ID);

-- 8. AD_UI_Element — 1:1 with the AD_Fields above
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID,
                            AD_UI_ElementType, Created, CreatedBy, Updated, UpdatedBy, IsActive, IsAdvancedField,
                            IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList)
VALUES
    (0, 0, 652816 /*From ID Server*/, 549363, 555538, 781903, 'F',
     TO_TIMESTAMP('2026-08-11 14:10:30', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-11 14:10:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'RestApiBaseURL', 10, 0, 0),
    (0, 0, 652817 /*From ID Server*/, 549363, 555538, 781904, 'F',
     TO_TIMESTAMP('2026-08-11 14:10:31', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-11 14:10:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'RequesterMemberStateCode', 20, 0, 0),
    (0, 0, 652818 /*From ID Server*/, 549363, 555538, 781905, 'F',
     TO_TIMESTAMP('2026-08-11 14:10:32', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-11 14:10:32', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'RequesterNumber', 30, 0, 0),
    (0, 0, 652819 /*From ID Server*/, 549363, 555538, 781906, 'F',
     TO_TIMESTAMP('2026-08-11 14:10:33', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-11 14:10:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'RecheckAfterDays', 40, 40, 0),
    (0, 0, 652820 /*From ID Server*/, 549363, 555538, 781907, 'F',
     TO_TIMESTAMP('2026-08-11 14:10:34', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-11 14:10:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'OnServiceUnavailable', 50, 50, 0),
    (0, 0, 652821 /*From ID Server*/, 549363, 555539, 781908, 'F',
     TO_TIMESTAMP('2026-08-11 14:10:35', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-11 14:10:35', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'IsActive', 10, 10, 0),
    (0, 0, 652822 /*From ID Server*/, 549363, 555539, 781909, 'F',
     TO_TIMESTAMP('2026-08-11 14:10:36', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-11 14:10:36', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'IsFormatCheckEnabled', 20, 20, 0),
    (0, 0, 652823 /*From ID Server*/, 549363, 555539, 781910, 'F',
     TO_TIMESTAMP('2026-08-11 14:10:37', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-11 14:10:37', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'IsVIESCheckEnabled', 30, 30, 0),
    (0, 0, 652824 /*From ID Server*/, 549363, 555540, 781911, 'F',
     TO_TIMESTAMP('2026-08-11 14:10:38', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-11 14:10:38', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'AD_Org_ID', 10, 60, 0),
    (0, 0, 652825 /*From ID Server*/, 549363, 555540, 781912, 'F',
     TO_TIMESTAMP('2026-08-11 14:10:39', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-11 14:10:39', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'AD_Client_ID', 20, 0, 0);
