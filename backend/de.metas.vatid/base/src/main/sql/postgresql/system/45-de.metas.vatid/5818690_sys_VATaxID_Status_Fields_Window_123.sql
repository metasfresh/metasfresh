-- VAT-ID online check: place the three status fields on the core Business Partner window 123.
-- Window 123 is the LIVE core window for both C_BPartner and C_BPartner_Location -- verified by
-- query, not assumed: ad_table.ad_window_id = 123 for AD_Table_ID 291 AND 293, and on core the
-- window is still named "Geschäftspartner". An installation that carries an override window for
-- this table renames the base window locally and points its users at the override instead; that
-- side is placed by a separate, next-numbered script in the repo that owns the override window, and
-- the core placement below must not be judged against such a local rename.
--
-- Placement rule: each field inherits exactly the AD_Field.IsDisplayed, AD_UI_Element.IsDisplayed
-- and AD_UI_Element.IsAdvancedField values that VATaxID carries on that same tab, and lands in
-- VATaxID's own element group, immediately after it. The status annotates the VAT-ID, so it is
-- visible exactly where the VAT-ID is -- no per-tab judgement call.
--
-- GRID -- the one deliberate exception to that mirror: only VATaxIDStatus goes in the grid.
-- VATaxIDCheckedAt and VATaxID_CheckLog_ID are IsDisplayedGrid='N' everywhere: a timestamp and a
-- zoom reference cost grid width and earn nothing at a glance.
-- Note which flag that means. Read live off the DB, core's VATaxID carries the two grid flags
-- INCONSISTENTLY on these tabs: AD_Field.IsDisplayedGrid='Y' (SeqNoGrid 80/200) while
-- AD_UI_Element.IsDisplayedGrid='N' (SeqNoGrid 0). This script mirrors that literally, writing
-- AD_Field.IsDisplayedGrid='Y' + AD_UI_Element.IsDisplayedGrid='N' for the status.
--
-- CORRECTION (comment-only; this script is already applied, so the fix lives in 5819230). The
-- reasoning above originally justified that combination by claiming VATaxID "demonstrably does appear
-- in the grid", and concluded AD_Field.IsDisplayedGrid was the effective flag. That premise is FALSE.
-- The WebUI builds a tab's grid layout from AD_UI_Element: it takes the tab's AD_UI_Element rows with
-- IsDisplayedGrid='Y' and orders the columns by AD_UI_Element.SeqNoGrid. AD_Field.IsDisplayedGrid and
-- AD_Field.SeqNoGrid have no WebUI consumer at all -- they are read by the generator process that
-- seeds AD_UI_Element rows from AD_Field rows, and by the legacy Swing client. VATaxID does NOT in
-- fact render in the grid on tab 220 or 222; the two-table inconsistency is pre-existing core state
-- that never took effect either way. Consequence: the "only VATaxIDStatus in the grid" decision below
-- did not take effect. Script 5819230 implements it, by setting AD_UI_Element.IsDisplayedGrid='Y' plus
-- a real SeqNoGrid on the VATaxIDStatus AD_UI_Element rows created here. VATaxID's own grid
-- visibility is left as core has it -- changing it is out of scope for this change.
--
-- Values mirrored from VATaxID, all read live from the DB before writing:
--   tab 220 Geschäftspartner  -- AD_Field Y/grid Y (SeqNoGrid 80); AD_UI_Element grp 1000013 SeqNo 30, IsAdvancedField N
--   tab 222 Adresse           -- AD_Field Y/grid Y (SeqNoGrid 200); AD_UI_Element grp 1000034 SeqNo 50, IsAdvancedField N
--   tab 548422 Einmaladresse  -- AD_Field N/grid N (hidden); AD_UI_Element grp 553516 SeqNo 70, IsDisplayed Y
-- The Einmaladresse tab DOES get the fields, created hidden exactly as VATaxID is there, so nothing
-- has to change if that tab is ever revealed.
--
-- SeqNo choices: the element-group gaps immediately after VATaxID were confirmed FREE by query
-- (group 1000013 next occupied 40; 1000034 next 60; 553516 next 80), so 31/32/33, 51/52/53 and
-- 71/72/73 place the fields directly after the VAT-ID without renumbering any existing element.
-- Grid slots likewise confirmed free: tab 220 has VATaxID at 80 and IsCompany at 90 -> 81;
-- tab 222 has VATaxID at 200 and M_Shipper_RoutingCode_ID at 220 -> 201.
--
-- IsReadOnly='Y' on all three: they are outcomes of a VIES check, never user input. A hand-edited
-- status would contradict VATaxID_CheckLog, which is the append-only legal evidence for that status.
-- IsMandatory='N' at field level even though C_BPartner.VATaxIDStatus is a mandatory column -- the
-- DB default 'NotChecked' fills it, and a read-only mandatory field has nothing for a user to do.
--
-- IDs allocated from idserver.metas.de:
--   AD_Field       782043..782051 (9: 3 fields x 3 tabs)
--   AD_UI_Element  652912..652920 (9, 1:1 with the fields above)

-- 1. AD_Field -- tab 220 Geschäftspartner (C_BPartner columns 593201/593202/593203)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, SeqNoGrid, IsDisplayedGrid, IsReadOnly, IsMandatory, EntityType)
VALUES
    (782043 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:20:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:20:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxIDStatus', 220, 593201, 'Y', 410, 81, 'Y', 'Y', 'N', 'D'),
    (782044 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:20:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:20:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxIDCheckedAt', 220, 593202, 'Y', 420, 0, 'N', 'Y', 'N', 'D'),
    (782045 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:20:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:20:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxID_CheckLog_ID', 220, 593203, 'Y', 430, 0, 'N', 'Y', 'N', 'D');

-- 2. AD_Field -- tab 222 Adresse (C_BPartner_Location columns 593204/593205/593206)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, SeqNoGrid, IsDisplayedGrid, IsReadOnly, IsMandatory, EntityType)
VALUES
    (782046 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:20:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:20:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxIDStatus', 222, 593204, 'Y', 200, 201, 'Y', 'Y', 'N', 'D'),
    (782047 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:20:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:20:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxIDCheckedAt', 222, 593205, 'Y', 210, 0, 'N', 'Y', 'N', 'D'),
    (782048 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:20:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:20:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxID_CheckLog_ID', 222, 593206, 'Y', 220, 0, 'N', 'Y', 'N', 'D');

-- 3. AD_Field -- tab 548422 Einmaladresse: hidden, mirroring VATaxID's IsDisplayed='N' there
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, SeqNoGrid, IsDisplayedGrid, IsReadOnly, IsMandatory, EntityType)
VALUES
    (782049 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:20:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:20:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxIDStatus', 548422, 593204, 'N', 10, 0, 'N', 'Y', 'N', 'D'),
    (782050 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:20:17', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:20:17', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxIDCheckedAt', 548422, 593205, 'N', 20, 0, 'N', 'Y', 'N', 'D'),
    (782051 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 17:20:18', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 17:20:18', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxID_CheckLog_ID', 548422, 593206, 'N', 30, 0, 'N', 'Y', 'N', 'D');

-- 4. AD_Field_Trl skeleton rows, then propagate each column's element translations.
-- NOTE the deliberate difference from 5818230 (the config window): that script scoped this insert by
-- AD_Tab_ID, which was safe there because the tab was brand-new and held only its own fields. These
-- are long-lived core tabs with dozens of pre-existing fields, so scoping by tab would reach far
-- beyond this change. Scoped to the nine new AD_Field_IDs instead.
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID IN (782043, 782044, 782045, 782046, 782047, 782048, 782049, 782050, 782051)
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(c.AD_Element_ID)
FROM AD_Column c
WHERE c.AD_Column_ID IN (593201, 593202, 593203, 593204, 593205, 593206);

DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (782043, 782044, 782045, 782046, 782047, 782048, 782049, 782050, 782051);
SELECT AD_Element_Link_Create_Missing_Field(f.AD_Field_ID)
FROM (VALUES (782043), (782044), (782045), (782046), (782047), (782048), (782049), (782050), (782051)) AS f(AD_Field_ID);

-- 5. AD_UI_Element -- 1:1 with the fields above, each in VATaxID's own group directly after it.
-- IsDisplayedGrid='N' / SeqNoGrid=0 throughout, mirroring VATaxID on all three tabs (see the GRID
-- note in the header: the effective grid flag on these tabs is AD_Field.IsDisplayedGrid, set above).
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID,
                            AD_UI_ElementType, Created, CreatedBy, Updated, UpdatedBy, IsActive, IsAdvancedField,
                            IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList)
VALUES
    (0, 0, 652912 /*From ID Server*/, 220, 1000013, 782043, 'F',
     TO_TIMESTAMP('2026-08-12 17:20:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:20:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'VATaxIDStatus', 31, 0, 0),
    (0, 0, 652913 /*From ID Server*/, 220, 1000013, 782044, 'F',
     TO_TIMESTAMP('2026-08-12 17:20:21', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:20:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'VATaxIDCheckedAt', 32, 0, 0),
    (0, 0, 652914 /*From ID Server*/, 220, 1000013, 782045, 'F',
     TO_TIMESTAMP('2026-08-12 17:20:22', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:20:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'VATaxID_CheckLog_ID', 33, 0, 0),
    (0, 0, 652915 /*From ID Server*/, 222, 1000034, 782046, 'F',
     TO_TIMESTAMP('2026-08-12 17:20:23', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:20:23', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'VATaxIDStatus', 51, 0, 0),
    (0, 0, 652916 /*From ID Server*/, 222, 1000034, 782047, 'F',
     TO_TIMESTAMP('2026-08-12 17:20:24', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:20:24', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'VATaxIDCheckedAt', 52, 0, 0),
    (0, 0, 652917 /*From ID Server*/, 222, 1000034, 782048, 'F',
     TO_TIMESTAMP('2026-08-12 17:20:25', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:20:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'VATaxID_CheckLog_ID', 53, 0, 0),
    (0, 0, 652918 /*From ID Server*/, 548422, 553516, 782049, 'F',
     TO_TIMESTAMP('2026-08-12 17:20:26', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:20:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'VATaxIDStatus', 71, 0, 0),
    (0, 0, 652919 /*From ID Server*/, 548422, 553516, 782050, 'F',
     TO_TIMESTAMP('2026-08-12 17:20:27', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:20:27', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'VATaxIDCheckedAt', 72, 0, 0),
    (0, 0, 652920 /*From ID Server*/, 548422, 553516, 782051, 'F',
     TO_TIMESTAMP('2026-08-12 17:20:28', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-12 17:20:28', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y', 'N', 'N', 'VATaxID_CheckLog_ID', 73, 0, 0);
