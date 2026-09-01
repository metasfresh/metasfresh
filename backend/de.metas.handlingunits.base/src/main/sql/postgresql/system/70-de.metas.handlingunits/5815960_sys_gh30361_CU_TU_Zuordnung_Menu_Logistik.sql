-- Move the "CU-TU Zuordnung" window menu node from the "Handling Units" summary folder
-- to "Logistik" in the main menu tree, so it is findable beside the other packing config
-- windows instead of showing no proper menu path.
--   AD_Tree_ID   10       main menu tree
--   Node_ID      540489   AD_Menu entry of window "CU-TU Zuordnung" (AD_Window 540191)
--   old parent   540478   Handling Units
--   new parent   1000016  Logistik
--   SeqNo        47       directly before its counterpart "CU-TU Zuordnung konsolidieren" (48)
-- The menu tree is identical across all instances, so the node and its target slot are fixed.

UPDATE AD_TreeNodeMM
SET    Parent_ID = 1000016,
       SeqNo     = 47,
       Updated   = TO_TIMESTAMP('2026-07-23 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Tree_ID = 10
  AND  Node_ID    = 540489;
