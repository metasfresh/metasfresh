-- Place the "CU-TU Zuordnung" window (AD_Window 540191) menu node under "Logistik"
-- (AD_Menu 1000016) in the main menu tree (AD_Tree_ID=10), as a sibling of the other
-- packing-instruction config windows already there — Packvorschrift (540830),
-- Packvorschrift Version (540831), GRAI-Packvorschrift-Zuordnung (542333).
--
-- Until now this window's menu node sat under a different summary folder, so it was not
-- findable next to the other packing-instruction windows: opening it by window id in the
-- WebUI shows no proper menu path. It is a standard TU/HU packing-instruction config
-- window and belongs under Logistik with its siblings.
--
-- Idempotent: the node is resolved from AD_Menu by its window id (no hard-coded menu id),
-- re-parented only when it is not already under Logistik, and a tree node is inserted only
-- if the window's menu entry has none at all. Re-running the script is a no-op.

-- 1. Re-parent the existing tree node under Logistik (appended after the current children).
UPDATE AD_TreeNodeMM t
SET    Parent_ID = 1000016 /*Logistik*/,
       SeqNo     = (SELECT COALESCE(MAX(t2.SeqNo), 0) + 1
                    FROM   AD_TreeNodeMM t2
                    WHERE  t2.AD_Tree_ID = 10
                      AND  t2.Parent_ID  = 1000016
                      AND  t2.Node_ID   <> t.Node_ID),
       Updated   = TO_TIMESTAMP('2026-07-23 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  t.AD_Tree_ID = 10
  AND  t.Node_ID IN (SELECT m.AD_Menu_ID FROM AD_Menu m
                     WHERE m.AD_Window_ID = 540191 AND m.IsActive = 'Y')
  AND  t.Parent_ID <> 1000016;

-- 2. Safety net: if the window's menu entry has no tree node at all, create one under Logistik.
INSERT INTO AD_TreeNodeMM
    (AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT 0, 0, 'Y',
       TO_TIMESTAMP('2026-07-23 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-23 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       10, m.AD_Menu_ID, 1000016 /*Logistik*/,
       (SELECT COALESCE(MAX(SeqNo), 0) + 1 FROM AD_TreeNodeMM
        WHERE AD_Tree_ID = 10 AND Parent_ID = 1000016)
FROM   AD_Menu m
WHERE  m.AD_Window_ID = 540191 AND m.IsActive = 'Y'
  AND  NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM t
                   WHERE t.AD_Tree_ID = 10 AND t.Node_ID = m.AD_Menu_ID);
