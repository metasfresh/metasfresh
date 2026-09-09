-- me03#29295: Move window "Lieferkandidaten - Handler" (AD_Menu_ID=540383) to
-- "Lieferung > Einstellungen" in the menu tree (ad_tree_id=10).
-- The node already exists in tree 10 under "Admin" (parent 540382) — move it to
-- "Einstellungen" (parent 1000079) under the "Lieferung" folder (node 1000019).

-- Remove wrong placement from tree 1000019 (if applied from earlier version of this script)
DELETE FROM AD_TreeNodeMM WHERE AD_Tree_ID = 1000019 AND Node_ID = 540383;

-- Move existing node from old parent (540382 = Admin) to new parent (1000079 = Einstellungen)
UPDATE AD_TreeNodeMM
SET Parent_ID = 1000079,
    SeqNo = COALESCE((SELECT MAX(SeqNo) FROM AD_TreeNodeMM WHERE AD_Tree_ID = 10 AND Parent_ID = 1000079), 0) + 1,
    Updated = '2026-04-15 14:00',
    UpdatedBy = 99
WHERE AD_Tree_ID = 10 AND Node_ID = 540383;
