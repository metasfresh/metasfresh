-- Tax Declaration Iter4 fix: only the top-left UI element group should be 'primary'.
-- Migration 5803040 incorrectly created all 3 new groups (dates, flags, org) with
-- UIStyle='primary' too. Clear UIStyle on the non-top-left groups.

UPDATE AD_UI_ElementGroup
SET UIStyle = NULL,
    Updated = TIMESTAMP '2026-05-19 00:00:00',
    UpdatedBy = 100
WHERE AD_UI_ElementGroup_ID IN (
    555374, -- dates  (left column, below primary)
    555375, -- flags  (right column, top)
    555376  -- org    (right column, below flags)
);
