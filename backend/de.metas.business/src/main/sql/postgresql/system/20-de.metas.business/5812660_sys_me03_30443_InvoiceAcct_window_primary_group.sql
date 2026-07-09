-- F01010.4 Invoice Accounting Overrides
-- Mark the top-left element group of the "Invoice Accounting Overrides" window
-- (AD_Window 541659, tab 546735) as the primary group. The window had no primary
-- group at all; the layout convention requires exactly one primary group, placed
-- top-left. Group 550214 ("invoice&matching criteria") is column 1 / first, so it
-- is the correct primary group.
UPDATE AD_UI_ElementGroup
   SET UIStyle='primary',
       Updated=TO_TIMESTAMP('2026-07-07 17:45:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
 WHERE AD_UI_ElementGroup_ID=550214;
