-- VAT-ID online check: place the three status fields on the core Business Partner B2C window 540354.
-- Separate script from 5818690 on purpose -- one window per script, so a placement can be corrected
-- or reverted per window without touching the other.
--
-- Same placement rule as 5818690: each field inherits the AD_Field.IsDisplayed,
-- AD_UI_Element.IsDisplayed and AD_UI_Element.IsAdvancedField values that VATaxID carries on this
-- tab, and lands in VATaxID's own element group directly after it.
--
-- What is specific to this window: its VATaxID sits in element group 540893 "advanced edit" with
-- AD_UI_Element.IsAdvancedField='Y', so all three new fields are advanced-edit fields here. They are
-- NOT hidden -- AD_Field.IsDisplayed='Y' and AD_UI_Element.IsDisplayed='Y', exactly as VATaxID -- they
-- simply live behind the window's advanced-edit toggle, which is where this window keeps its VAT-ID.
--
-- GRID: only VATaxIDStatus, same exception as 5818690. VATaxID on tab 540843 carries
-- AD_Field.IsDisplayedGrid='Y' (SeqNoGrid 80) while its AD_UI_Element.IsDisplayedGrid='N'
-- (SeqNoGrid 0); mirroring that literally is what the values below do.
--
-- CORRECTION (comment-only; this script is already applied, so the fix lives in 5819230). 5818690
-- justified that mirror by claiming VATaxID appears in the grid and that AD_Field.IsDisplayedGrid is
-- therefore the effective flag; that premise is FALSE. The WebUI builds a tab's grid layout from
-- AD_UI_Element -- the rows with IsDisplayedGrid='Y', ordered by AD_UI_Element.SeqNoGrid.
-- AD_Field.IsDisplayedGrid and AD_Field.SeqNoGrid have no WebUI consumer; they are read by the
-- generator process that seeds AD_UI_Element rows from AD_Field rows, and by the legacy Swing client.
-- So the grid decision below did not take effect. Script 5819230 implements it by setting
-- AD_UI_Element.IsDisplayedGrid='Y' plus a real SeqNoGrid on the VATaxIDStatus AD_UI_Element row
-- created here. VATaxID's own grid visibility is left as core has it -- out of scope for this change.
--
-- Values mirrored from VATaxID on tab 540843, read live from the DB before writing:
--   AD_Field       IsDisplayed Y, IsDisplayedGrid Y, SeqNo 110, SeqNoGrid 80
--   AD_UI_Element  group 540893 "advanced edit", SeqNo 50, IsAdvancedField Y, IsDisplayedGrid N
-- SeqNo/grid slots confirmed free by query: group 540893 has VATaxID at 50 and NumberEmployees at 60
-- (so 51/52/53 insert cleanly), tab max AD_Field.SeqNo is 280 (so 290/300/310 append), and the grid
-- has VATaxID at 80 with IsCompany at 90 (so 81 is free).
--
-- IsReadOnly='Y' / IsMandatory='N' for the same reasons as 5818690: these are outcomes of a VIES
-- check rather than user input, and a hand-edited status would contradict the append-only check log.
--
-- IDs allocated from idserver.metas.de:
--   AD_Field       782052..782054 (3)
--   AD_UI_Element  652921..652923 (3, 1:1 with the fields above)

-- 1. AD_Field -- tab 540843 Geschäftspartner (C_BPartner columns 593201/593202/593203)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, SeqNoGrid, IsDisplayedGrid, IsReadOnly, IsMandatory, EntityType)
VALUES
    (782052 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:21:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:21:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxIDStatus', 540843, 593201, 'Y', 290, 81, 'Y', 'Y', 'N', 'D'),
    (782053 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:21:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:21:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxIDCheckedAt', 540843, 593202, 'Y', 300, 0, 'N', 'Y', 'N', 'D'),
    (782054 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:21:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:21:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxID_CheckLog_ID', 540843, 593203, 'Y', 310, 0, 'N', 'Y', 'N', 'D');

-- 2. AD_Field_Trl skeleton rows, then propagate the columns' element translations.
-- Scoped to the three new AD_Field_IDs, not to AD_Tab_ID: this is a long-lived core tab with many
-- pre-existing fields (see the same note in 5818690).
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID IN (782052, 782053, 782054)
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(c.AD_Element_ID)
FROM AD_Column c
WHERE c.AD_Column_ID IN (593201, 593202, 593203);

DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (782052, 782053, 782054);
SELECT AD_Element_Link_Create_Missing_Field(f.AD_Field_ID)
FROM (VALUES (782052), (782053), (782054)) AS f(AD_Field_ID);

-- 3. AD_UI_Element -- 1:1 with the fields above, in the "advanced edit" group after VATaxID
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID,
                            AD_UI_ElementType, Created, CreatedBy, Updated, UpdatedBy, IsActive, IsAdvancedField,
                            IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList)
VALUES
    (0, 0, 652921 /*From ID Server*/, 540843, 540893, 782052, 'F',
     TO_TIMESTAMP('2026-08-12 17:21:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:21:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'Y', 'Y', 'N', 'N', 'VATaxIDStatus', 51, 0, 0),
    (0, 0, 652922 /*From ID Server*/, 540843, 540893, 782053, 'F',
     TO_TIMESTAMP('2026-08-12 17:21:21', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:21:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'Y', 'Y', 'N', 'N', 'VATaxIDCheckedAt', 52, 0, 0),
    (0, 0, 652923 /*From ID Server*/, 540843, 540893, 782054, 'F',
     TO_TIMESTAMP('2026-08-12 17:21:22', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:21:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'Y', 'Y', 'N', 'N', 'VATaxID_CheckLog_ID', 53, 0, 0);
