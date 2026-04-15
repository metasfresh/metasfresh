-- me03#29295: Add window "Lieferkandidaten - Handler" (AD_Menu_ID=540383) to WebUI menu tree
-- In the old tree (ad_tree_id=10), path is: Verkauf > Lieferscheine > Admin > Lieferkandidaten - Handler
-- In the WebUI tree (ad_tree_id=1000019), "Lieferscheine" and "Admin" folders don't exist.
-- Place under "Einstellungen Verkauf" (node 1000001) which is the sales admin folder under "Verkauf".

INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, AD_Tree_ID, Node_ID, Parent_ID, SeqNo, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT 0, 0, 1000019, 540383, 1000001, 8, '2026-04-15 14:00', 99, '2026-04-15 14:00', 99, 'Y'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_TreeNodeMM WHERE AD_Tree_ID = 1000019 AND Node_ID = 540383
);
