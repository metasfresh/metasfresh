-- Tax Declaration — window 542146 layout follow-up
--   * Make IsCorrection readonly in the window (set by the Create Correction process; user must not toggle)
--   * Group the 4 Correction-lifecycle fields into a dedicated AD_UI_ElementGroup on column 2,
--     below the existing "flags" group
--   * Reorder column 2 vertical stack:  flags -> correction -> dates -> org
--   * "dates" element-group moves from column 1 to column 2
--
-- Ref: https://github.com/metasfresh/me03/issues/29631

-- 1) Make IsCorrection readonly in the window
UPDATE AD_Field
   SET IsReadOnly = 'Y',
       Updated = TO_TIMESTAMP('2026-05-28 00:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Field_ID = 780479 /*IsCorrection — From ID Server*/;

-- 2) Create new AD_UI_ElementGroup "correction" on column 549512 (col-2 of section 547771)
INSERT INTO AD_UI_ElementGroup (
    AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_UI_Column_ID, Name, SeqNo, UIStyle)
VALUES (
    555411 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-05-28 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-05-28 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    549512, 'correction', 20, NULL);

-- 3) Reorder column-2 element-groups so the stack becomes:
--      flags      (555375) seq=10  — unchanged
--      correction (555411) seq=20  — new (inserted above)
--      dates      (555374) seq=30  — moved from column 549485 (col-1) to 549512 (col-2)
--      org        (555376) seq=40  — re-numbered from 20 to 40 to make room
UPDATE AD_UI_ElementGroup
   SET AD_UI_Column_ID = 549512,
       SeqNo = 30,
       Updated = TO_TIMESTAMP('2026-05-28 00:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_UI_ElementGroup_ID = 555374 /*dates*/;

UPDATE AD_UI_ElementGroup
   SET SeqNo = 40,
       Updated = TO_TIMESTAMP('2026-05-28 00:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_UI_ElementGroup_ID = 555376 /*org*/;

-- 4) Move the 4 Correction-lifecycle AD_UI_Element rows into the new group,
--    renumber SeqNo within the group starting at 10
UPDATE AD_UI_Element
   SET AD_UI_ElementGroup_ID = 555411 /*From ID Server*/,
       SeqNo = 10,
       Updated = TO_TIMESTAMP('2026-05-28 00:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_UI_Element_ID = 651834 /*IsCorrection — From ID Server*/;

UPDATE AD_UI_Element
   SET AD_UI_ElementGroup_ID = 555411 /*From ID Server*/,
       SeqNo = 20,
       Updated = TO_TIMESTAMP('2026-05-28 00:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_UI_Element_ID = 651835 /*C_TaxDeclaration_Original_ID — From ID Server*/;

UPDATE AD_UI_Element
   SET AD_UI_ElementGroup_ID = 555411 /*From ID Server*/,
       SeqNo = 30,
       Updated = TO_TIMESTAMP('2026-05-28 00:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_UI_Element_ID = 651836 /*IsCorrectionNeeded — From ID Server*/;

UPDATE AD_UI_Element
   SET AD_UI_ElementGroup_ID = 555411 /*From ID Server*/,
       SeqNo = 40,
       Updated = TO_TIMESTAMP('2026-05-28 00:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_UI_Element_ID = 651837 /*CorrectionNeededReason — From ID Server*/;
