-- VAT-ID online check: VATaxID_CheckLog window (single main tab, 2-column layout).
-- Layout: left/primary = the identifying + timing fields an operator scans (BPartner, VATaxID,
-- status, dates, VIES consultation number); left/secondary = the not-yet-used qualified-check +
-- evidence fields (Advanced Edit only, per metasfresh-window-design-rules); right/flags =
-- IsActive; right/org = AD_Org_ID then AD_Client_ID (cornerstones). Every field is read-only —
-- rows are written exclusively by the repository (Task 6b), never by a user in this window.
-- Grid shows exactly the columns an operator needs to scan a partner's check history at a glance
-- (BPartner, VATaxID, status, request/response dates, VIES consultation number); the not-yet-used
-- qualified-check fields and the evidence-only process/session references stay out of the grid.
-- No customer override window exists yet for this brand-new table, so there is nothing to mirror
-- in a customer repo at this stage.

-- IDs allocated from idserver.metas.de:
--   AD_Window            542183
--   AD_Tab               549365
--   AD_UI_Section        547870
--   AD_UI_Column         549620..549621 (left, right)
--   AD_UI_ElementGroup   555542..555545 (left/primary, left/secondary-advanced, right/flags, right/org)
--   AD_Field             781955..781971 (17)
--   AD_UI_Element        652838..652854 (17, 1:1 with the fields above)

-- 1. AD_Window (reuses the table's own AD_Element 585185 for the caption)
INSERT INTO AD_Window (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Name, WindowType, IsSOTrx, EntityType, IsDefault, AD_Element_ID, ZoomIntoPriority)
VALUES (542183 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-12 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-12 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'USt-IdNr.-Prüfprotokoll', 'M', 'Y', 'D', 'N', 585185, 100);

UPDATE AD_Table SET AD_Window_ID = 542183, Updated = TO_TIMESTAMP('2026-08-12 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542639;

-- 2. AD_Tab — single header/main tab (reuses the same element for its caption)
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                     Name, AD_Table_ID, AD_Window_ID, SeqNo, TabLevel, IsSingleRow, EntityType, AD_Element_ID)
VALUES (549365 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-12 10:10:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-12 10:10:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'USt-IdNr.-Prüfprotokoll', 542639, 542183, 10, 0, 'N', 'D', 585185);

INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Window_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Window t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Window_ID = 542183
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = t.AD_Window_ID);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 549365
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585185);

-- 3. Section / columns / element groups
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Tab_ID, SeqNo, Name)
VALUES (547870 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-12 10:10:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-12 10:10:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        549365, 10, 'default');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_Section_ID, SeqNo)
VALUES
    (549620 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 547870, 10),
    (549621 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:12', 'YYYY-MM-DD HH24:MI:SS'), 100, 547870, 20);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555542 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 549620, 10, 'primary', 'default'),
    (555543 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:14', 'YYYY-MM-DD HH24:MI:SS'), 100, 549620, 20, NULL, 'default'),
    (555544 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 549621, 10, NULL, 'flags'),
    (555545 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:16', 'YYYY-MM-DD HH24:MI:SS'), 100, 549621, 20, NULL, 'default');

-- 4. AD_Field — left/primary group: identifying + timing fields (all read-only, written by the repository)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, SeqNoGrid, IsDisplayedGrid, IsReadOnly, IsMandatory, EntityType)
VALUES
    (781955 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'C_BPartner_ID', 549365, 593172, 'Y', 10, 20, 'Y', 'Y', 'Y', 'D'),
    (781956 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'C_BPartner_Location_ID', 549365, 593173, 'Y', 20, 0, 'N', 'Y', 'N', 'D'),
    (781957 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxID', 549365, 593174, 'Y', 30, 30, 'Y', 'Y', 'Y', 'D'),
    (781958 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:23', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:23', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxIDStatus', 549365, 593175, 'Y', 40, 40, 'Y', 'Y', 'Y', 'D'),
    (781959 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:24', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:24', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RequestDate', 549365, 593176, 'Y', 50, 50, 'Y', 'Y', 'Y', 'D'),
    (781960 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ResponseDate', 549365, 593177, 'Y', 60, 60, 'Y', 'Y', 'N', 'D'),
    (781961 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RequestIdentifier', 549365, 593178, 'Y', 70, 70, 'Y', 'Y', 'N', 'D');

-- 5. AD_Field — left/secondary group: not-yet-used qualified-check + evidence fields (Advanced Edit only)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, SeqNoGrid, IsDisplayedGrid, IsReadOnly, IsMandatory, EntityType)
VALUES
    (781962 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:27', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:27', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'AD_PInstance_ID', 549365, 593179, 'Y', 10, 0, 'N', 'Y', 'N', 'D'),
    (781963 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:28', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:28', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'AD_Session_ID', 549365, 593180, 'Y', 20, 0, 'N', 'Y', 'N', 'D'),
    (781964 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:29', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:29', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ReturnedName', 549365, 593181, 'Y', 30, 0, 'N', 'Y', 'N', 'D'),
    (781965 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ReturnedAddress', 549365, 593182, 'Y', 40, 0, 'N', 'Y', 'N', 'D'),
    (781966 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'TraderNameMatch', 549365, 593183, 'Y', 50, 0, 'N', 'Y', 'N', 'D'),
    (781967 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:32', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:32', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'TraderAddressMatch', 549365, 593184, 'Y', 60, 0, 'N', 'Y', 'N', 'D'),
    (781968 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RawResponse', 549365, 593185, 'Y', 70, 0, 'N', 'Y', 'N', 'D');

-- 6. AD_Field — right/flags group (IsActive) and right/org group (Org, then Client — cornerstone rule)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, SeqNoGrid, IsDisplayedGrid, IsReadOnly, IsMandatory, EntityType)
VALUES
    (781969 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'IsActive', 549365, 593167, 'Y', 10, 10, 'Y', 'N', 'Y', 'D'),
    (781970 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:35', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:35', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'AD_Org_ID', 549365, 593166, 'Y', 10, 80, 'Y', 'N', 'Y', 'D'),
    (781971 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:10:36', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:10:36', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'AD_Client_ID', 549365, 593165, 'Y', 20, 0, 'N', 'N', 'Y', 'D');

-- 7. AD_Field_Trl skeleton rows for all 17 fields, then propagate from each column's element
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 549365
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(c.AD_Element_ID)
FROM AD_Column c
WHERE c.AD_Column_ID IN (593172, 593173, 593174, 593175, 593176, 593177, 593178,
                          593179, 593180, 593181, 593182, 593183, 593184, 593185,
                          593167, 593166, 593165);

DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (781955, 781956, 781957, 781958, 781959, 781960, 781961,
                                                    781962, 781963, 781964, 781965, 781966, 781967, 781968,
                                                    781969, 781970, 781971);
SELECT AD_Element_Link_Create_Missing_Field(f.AD_Field_ID)
FROM (VALUES (781955), (781956), (781957), (781958), (781959), (781960), (781961),
             (781962), (781963), (781964), (781965), (781966), (781967), (781968),
             (781969), (781970), (781971)) AS f(AD_Field_ID);

-- 8. AD_UI_Element — 1:1 with the AD_Fields above; the left/secondary group is Advanced-Edit-only
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID,
                            AD_UI_ElementType, Created, CreatedBy, Updated, UpdatedBy, IsActive, IsAdvancedField,
                            IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList)
VALUES
    (0, 0, 652838 /*From ID Server*/, 549365, 555542, 781955, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:40', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'C_BPartner_ID', 10, 20, 0),
    (0, 0, 652839 /*From ID Server*/, 549365, 555542, 781956, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:41', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'C_BPartner_Location_ID', 20, 0, 0),
    (0, 0, 652840 /*From ID Server*/, 549365, 555542, 781957, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:42', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:42', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'VATaxID', 30, 30, 0),
    (0, 0, 652841 /*From ID Server*/, 549365, 555542, 781958, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:43', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:43', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'VATaxIDStatus', 40, 40, 0),
    (0, 0, 652842 /*From ID Server*/, 549365, 555542, 781959, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:44', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:44', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'RequestDate', 50, 50, 0),
    (0, 0, 652843 /*From ID Server*/, 549365, 555542, 781960, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:45', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:45', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'ResponseDate', 60, 60, 0),
    (0, 0, 652844 /*From ID Server*/, 549365, 555542, 781961, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:46', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:46', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'RequestIdentifier', 70, 70, 0),
    (0, 0, 652845 /*From ID Server*/, 549365, 555543, 781962, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:47', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:47', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'Y', 'Y', 'N', 'N', 'AD_PInstance_ID', 10, 0, 0),
    (0, 0, 652846 /*From ID Server*/, 549365, 555543, 781963, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:48', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:48', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'Y', 'Y', 'N', 'N', 'AD_Session_ID', 20, 0, 0),
    (0, 0, 652847 /*From ID Server*/, 549365, 555543, 781964, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:49', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:49', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'Y', 'Y', 'N', 'N', 'ReturnedName', 30, 0, 0),
    (0, 0, 652848 /*From ID Server*/, 549365, 555543, 781965, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:50', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'Y', 'Y', 'N', 'N', 'ReturnedAddress', 40, 0, 0),
    (0, 0, 652849 /*From ID Server*/, 549365, 555543, 781966, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:51', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'Y', 'Y', 'N', 'N', 'TraderNameMatch', 50, 0, 0),
    (0, 0, 652850 /*From ID Server*/, 549365, 555543, 781967, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:52', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:52', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'Y', 'Y', 'N', 'N', 'TraderAddressMatch', 60, 0, 0),
    (0, 0, 652851 /*From ID Server*/, 549365, 555543, 781968, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:53', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:53', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'Y', 'Y', 'N', 'N', 'RawResponse', 70, 0, 0),
    (0, 0, 652852 /*From ID Server*/, 549365, 555544, 781969, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:54', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:54', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'IsActive', 10, 10, 0),
    (0, 0, 652853 /*From ID Server*/, 549365, 555545, 781970, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:55', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:55', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'Y', 'N', 'AD_Org_ID', 10, 80, 0),
    (0, 0, 652854 /*From ID Server*/, 549365, 555545, 781971, 'F',
     TO_TIMESTAMP('2026-08-12 10:10:56', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 10:10:56', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'AD_Client_ID', 20, 0, 0);
