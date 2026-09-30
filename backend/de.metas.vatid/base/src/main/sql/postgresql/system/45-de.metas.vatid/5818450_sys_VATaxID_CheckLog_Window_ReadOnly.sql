-- VAT-ID online check: make the VATaxID_CheckLog window genuinely read-only.
-- Precedent matched: AD_ChangeLog (AD_Tab 488) and AD_Issue (AD_Tab 777), the corpus's two
-- canonical evidence/audit-log windows, both lock the whole tab via IsReadOnly='Y' +
-- IsInsertRecord='N' rather than relying on per-field flags alone. This tab (549365) had neither
-- set, leaving the two fields IsActive (781969) / AD_Org_ID (781970) editable and the "+" (insert)
-- action available, even though every other field on the tab was already IsReadOnly='Y'.
-- Deletion is already prevented: AD_Table.IsDeleteable='N' was set correctly by migration 5818420.

UPDATE AD_Tab
SET IsReadOnly = 'Y', IsInsertRecord = 'N',
    Updated = TO_TIMESTAMP('2026-08-12 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 549365;

-- Field-level lock for the two fields the tab-level fix alone would still leave marked IsReadOnly='N',
-- for consistency with the other 14 fields already on this tab.
UPDATE AD_Field
SET IsReadOnly = 'Y',
    Updated = TO_TIMESTAMP('2026-08-12 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID IN (781969, 781970);

-- Column-level lock so the same two columns can't be written via any other window/REST document
-- path either — matching the "never edited" guarantee for this table, not just this one window.
UPDATE AD_Column
SET IsUpdateable = 'N',
    Updated = TO_TIMESTAMP('2026-08-12 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID IN (593166, 593167);
