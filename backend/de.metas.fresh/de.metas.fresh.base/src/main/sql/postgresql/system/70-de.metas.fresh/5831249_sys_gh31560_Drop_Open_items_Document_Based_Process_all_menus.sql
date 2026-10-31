-- Fixes failure of: 5831250_sys_gh31560_Drop_Open_items_Document_Based_Process.sql
--
-- 5831250 deletes the report process AD_Process 500174 ("Offene Posten (Belegbasiert)") but removes only the two
-- core menu entries (AD_Menu 540948 and 243). Databases that carry an additional AD_Menu entry for that process
-- (e.g. one added by a customer-repo migration) then fail 5831250 at COMMIT with
--   update or delete on table "ad_process" violates foreign key constraint "adprocess_admenu" on table "ad_menu".
-- This script runs right before 5831250 and removes every menu entry that still launches the process,
-- so 5831250 can delete it. On a database where 5831250 was already applied, no such menu entry exists
-- and every statement below deletes nothing.

DELETE FROM AD_Menu_Trl WHERE AD_Menu_ID IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Process_ID=500174)
;

DELETE FROM AD_TreeNodeMM n
WHERE n.Node_ID IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Process_ID=500174)
  AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

DELETE FROM AD_Menu WHERE AD_Process_ID=500174
;
