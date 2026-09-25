-- Make AD_Table_Access.AD_Table_ID a Search lookup (was Table Direct / reference 19) so the "Table"
-- field on the Role > Table Access tab (window 111 "Rolle - Verwaltung", tab 549493, added by 5825600)
-- is a searchable picker instead of a dropdown over the full AD_Table list.
--
-- Column-only change: both AD_Fields on this column (785047 on the new tab 549493 and 6718 on the
-- legacy role window 268) inherit the reference (AD_Field.AD_Reference_ID IS NULL), so no field change
-- is needed. No AD_Reference_Value is required: this matches the 65 other AD_Table_ID columns already
-- using Search (30) with the column-name convention (AD_Table_ID -> AD_Table), whose fields likewise
-- inherit the reference.
UPDATE AD_Column
SET AD_Reference_ID = 30, Updated = now(), UpdatedBy = 100
WHERE AD_Column_ID = 8574          -- AD_Table_Access.AD_Table_ID
  AND AD_Reference_ID = 19;
