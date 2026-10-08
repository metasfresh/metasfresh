-- gh#28565: Place Promotion Code menu entries under Verkauf > Aufträge (457)
--
-- Both menu entries were created at root level (Parent_ID=0, SeqNo=999).
-- Move them under the "Aufträge" (Orders) folder where they belong.
--
-- 542302 = Aktionskennzeichen (Window)
-- 542303 = Aktionskennzeichen Auswertung (Report)

UPDATE AD_TreeNodeMM
SET Parent_ID = 457,
    SeqNo     = 17,
    Updated   = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE Node_ID = 542302
  AND AD_Tree_ID = (SELECT MIN(AD_Tree_ID)
                    FROM AD_Tree
                    WHERE AD_Client_ID = 0
                      AND IsActive = 'Y'
                      AND IsAllNodes = 'Y'
                      AND AD_Table_ID = 116);

UPDATE AD_TreeNodeMM
SET Parent_ID = 457,
    SeqNo     = 18,
    Updated   = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE Node_ID = 542303
  AND AD_Tree_ID = (SELECT MIN(AD_Tree_ID)
                    FROM AD_Tree
                    WHERE AD_Client_ID = 0
                      AND IsActive = 'Y'
                      AND IsAllNodes = 'Y'
                      AND AD_Table_ID = 116);
