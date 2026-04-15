-- me03#29295: Add window "Lieferkandidaten - Handler" (AD_Menu_ID=540383) to the
-- "Lieferung > Einstellungen" folder in the menu tree (ad_tree_id=10).
-- Parent node 1000079 = "Einstellungen" folder under "Lieferung" (node 1000019).

-- Remove wrong placement (if applied from earlier version of this script)
DELETE FROM AD_TreeNodeMM WHERE AD_Tree_ID = 1000019 AND Node_ID = 540383;

-- Insert into the correct location: Lieferung > Einstellungen
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, AD_Tree_ID, Node_ID, Parent_ID, SeqNo, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT 0, 0, 10, 540383, 1000079,
       COALESCE((SELECT MAX(SeqNo) FROM AD_TreeNodeMM WHERE AD_Tree_ID = 10 AND Parent_ID = 1000079), 0) + 1,
       '2026-04-15 14:00', 99, '2026-04-15 14:00', 99, 'Y'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_TreeNodeMM WHERE AD_Tree_ID = 10 AND Node_ID = 540383 AND Parent_ID = 1000079
);
