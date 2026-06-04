-- Fix SeqNo collision in AD_TreeNodeMM under parent 1000016 (Logistik, AD_Tree_ID=10).
-- Script 5805800 inserted the new menu entry (542333) at SeqNo=7, which collided with
-- the existing "Packvorschrift Version" entry (540831, previously SeqNo=7).
-- Correct layout: Packvorschrift=6, GRAI-Packvorschrift-Zuordnung=7, then shift
-- all previously SeqNo>=7 entries (excluding the new one) by +1.

-- Step 1: shift existing entries at SeqNo >= 7 (except the new one) upward
UPDATE AD_TreeNodeMM
SET    SeqNo    = SeqNo + 1,
       Updated  = TO_TIMESTAMP('2026-06-02 11:10:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Tree_ID  = 10
  AND  Parent_ID   = 1000016
  AND  Node_ID    <> 542333 /*new GRAI menu entry — keep at 7*/
  AND  SeqNo      >= 7;
