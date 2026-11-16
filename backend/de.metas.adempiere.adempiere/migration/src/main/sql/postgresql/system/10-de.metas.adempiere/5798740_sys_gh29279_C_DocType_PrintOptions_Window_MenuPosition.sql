-- Move menu entry for window "Document Type Printing Options" (AD_Window_ID=541004)
-- into the printing cluster of the System menu — right after "Druck - Format"
-- (AD_Menu_ID=540827, SeqNo=37). The window used to sit at SeqNo=35, between
-- Textbaustein Übersetzung (34) and Textvariablen (36) — unrelated neighbors.
--
-- AD_Menu_ID                = 541563  (Document Type Printing Options)
-- AD_Tree_ID                = 10      (standard menu tree)
-- Parent_ID                 = 1000098 (System)
-- Old SeqNo                 = 35
-- New SeqNo                 = 38      (right after 540827 "Druck - Format" at 37,
--                                      before 541599 "Zebra Konfiguration")
--
-- Shift existing siblings with SeqNo >= 38 up by +1 to make room.
-- The old slot at 35 becomes a gap, which is harmless (AD_TreeNodeMM tolerates gaps).


-- 1) Make room: bump every sibling at SeqNo >= 38 by +1 (but skip our own node,
--    which is currently at 35 anyway).
UPDATE AD_TreeNodeMM
SET SeqNo     = SeqNo + 1,
    Updated   = TO_TIMESTAMP('2026-04-21 08:15','YYYY-MM-DD HH24:MI'),
    UpdatedBy = 0
WHERE AD_Tree_ID = 10
  AND Parent_ID  = 1000098
  AND SeqNo     >= 38
  AND Node_ID   <> 541563;

-- 2) Place our node at SeqNo=38.
UPDATE AD_TreeNodeMM
SET SeqNo     = 38,
    Updated   = TO_TIMESTAMP('2026-04-21 08:15','YYYY-MM-DD HH24:MI'),
    UpdatedBy = 0
WHERE AD_Tree_ID = 10
  AND Parent_ID  = 1000098
  AND Node_ID    = 541563;
