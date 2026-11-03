-- VAT-ID online check: re-parent the VATaxID_CheckLog menu entry (AD_Menu_ID=542357) out of
-- "Finanzen -> Einstellungen" (1000072) and directly under "Finanzen" (1000015).
--
-- Corpus precedent for a STANDALONE read-only log/audit window (its own menu entry, not a tab
-- embedded in a config window) is a direct child of its domain's top-level folder, never nested
-- inside that domain's "Einstellungen" configuration subfolder: PayPal_Log sits under "PayPal",
-- M_Securpharm_Log under "Pharma", C_Doc_Outbound_Log under "Archive"/"CRM", AD_ChangeLog/AD_Issue
-- under "System"/"Sicherheit"/"System-Administration". Within "Finanzen" itself, "Document
-- Accounting Log" (SeqNo=36) and the "Buchführungs-Details"/Fact_Acct posting log (SeqNo=10) already
-- sit as direct children of "Finanzen", confirming the pattern for this exact domain. (AD_Replication_Log
-- and AD_WF_EventAudit are NOT standalone log windows — verified live, they are embedded detail tabs on
-- windows 284/297 respectively — so they are not precedents here.) "Finanzen -> Einstellungen"
-- (1000072), by contrast, holds only configuration/master-data windows (verified: all 21 remaining
-- siblings). VATaxID_CheckLog is moved to match.
--
-- Appended after the existing 53 children of "Finanzen" (max SeqNo=53, "Steuererklärung") rather
-- than interleaved, to avoid renumbering unrelated sibling rows (same convention as migration
-- 5818320's re-parent of VATaxID_Config).

UPDATE AD_TreeNodeMM
SET Parent_ID = 1000015, SeqNo = 54,
    Updated = TO_TIMESTAMP('2026-08-12 12:20:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 542357;

-- Close the gap left under "Finanzen -> Einstellungen" (shift the eight siblings, bumped up by
-- migration 5818440 to make room for VATaxID_CheckLog, back down by one).
UPDATE AD_TreeNodeMM SET SeqNo = 14, Updated = TO_TIMESTAMP('2026-08-12 12:20:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540810; -- Bank: 15 -> 14

UPDATE AD_TreeNodeMM SET SeqNo = 15, Updated = TO_TIMESTAMP('2026-08-12 12:20:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540812; -- Währung: 16 -> 15

UPDATE AD_TreeNodeMM SET SeqNo = 16, Updated = TO_TIMESTAMP('2026-08-12 12:20:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 541107; -- Währung Übersetzung: 17 -> 16

UPDATE AD_TreeNodeMM SET SeqNo = 17, Updated = TO_TIMESTAMP('2026-08-12 12:20:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540813; -- Währungskurs: 18 -> 17

UPDATE AD_TreeNodeMM SET SeqNo = 18, Updated = TO_TIMESTAMP('2026-08-12 12:20:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 542004; -- Kursart: 19 -> 18

UPDATE AD_TreeNodeMM SET SeqNo = 19, Updated = TO_TIMESTAMP('2026-08-12 12:20:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 542006; -- Standardkursart: 20 -> 19

UPDATE AD_TreeNodeMM SET SeqNo = 20, Updated = TO_TIMESTAMP('2026-08-12 12:20:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540780; -- Mahnart: 21 -> 20

UPDATE AD_TreeNodeMM SET SeqNo = 21, Updated = TO_TIMESTAMP('2026-08-12 12:20:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 541103; -- Mahnstufe Übersetzung: 22 -> 21
