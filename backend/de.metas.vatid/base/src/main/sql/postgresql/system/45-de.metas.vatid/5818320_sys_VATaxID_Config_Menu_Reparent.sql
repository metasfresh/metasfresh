-- VAT-ID online check: re-parent the VATaxID_Config menu entry (AD_Menu_ID=542356) from the
-- "Application-Dictionary" (153) developer/schema-maintenance branch to the business-facing
-- "Finanzen -> Einstellungen" (1000072) settings folder, alongside the adjacent tax configuration
-- windows Steuersatz (540842) and Steuerkategorie (540843). Follow-up to migration 5818280, which
-- is already applied and immutable — this script performs the re-placement instead.
--
-- SeqNo=21 appended after the existing 20 children (max SeqNo=20) rather than interleaved with the
-- Steuersatz/Steuerkategorie block (SeqNo 9-12), to avoid renumbering unrelated sibling rows.

UPDATE AD_TreeNodeMM
SET Parent_ID = 1000072,
    SeqNo = 21,
    Updated = TO_TIMESTAMP('2026-08-11 17:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 542356;
