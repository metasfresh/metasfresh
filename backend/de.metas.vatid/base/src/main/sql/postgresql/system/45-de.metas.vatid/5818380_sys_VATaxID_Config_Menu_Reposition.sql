-- Follow-up to migration 5818320 (already applied and immutable): that script re-parented the
-- VATaxID_Config menu entry (AD_Menu_ID=542356) into "Finanzen -> Einstellungen" (1000072) but
-- appended it at SeqNo=21, after the unrelated "Mahnstufe Übersetzung" entry, instead of next to
-- the thematically related tax configuration windows Steuersatz (540842, SeqNo 9) and
-- Steuerkategorie (540843, SeqNo 11). This script moves it to SeqNo=13, directly after the
-- Steuersatz/Steuerkategorie block (SeqNo 9-12), and shifts the eight following siblings
-- (previously SeqNo 13-20) down by one to keep the sequence contiguous, without gaps or duplicates.

UPDATE AD_TreeNodeMM
SET SeqNo = 13,
    Updated = TO_TIMESTAMP('2026-08-12 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 542356; -- USt-IdNr.-Konfiguration: 21 -> 13

UPDATE AD_TreeNodeMM
SET SeqNo = 14,
    Updated = TO_TIMESTAMP('2026-08-12 09:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540810; -- Bank: 13 -> 14

UPDATE AD_TreeNodeMM
SET SeqNo = 15,
    Updated = TO_TIMESTAMP('2026-08-12 09:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540812; -- Währung: 14 -> 15

UPDATE AD_TreeNodeMM
SET SeqNo = 16,
    Updated = TO_TIMESTAMP('2026-08-12 09:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 541107; -- Währung Übersetzung: 15 -> 16

UPDATE AD_TreeNodeMM
SET SeqNo = 17,
    Updated = TO_TIMESTAMP('2026-08-12 09:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540813; -- Währungskurs: 16 -> 17

UPDATE AD_TreeNodeMM
SET SeqNo = 18,
    Updated = TO_TIMESTAMP('2026-08-12 09:00:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 542004; -- Kursart: 17 -> 18

UPDATE AD_TreeNodeMM
SET SeqNo = 19,
    Updated = TO_TIMESTAMP('2026-08-12 09:00:06', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 542006; -- Standardkursart: 18 -> 19

UPDATE AD_TreeNodeMM
SET SeqNo = 20,
    Updated = TO_TIMESTAMP('2026-08-12 09:00:07', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540780; -- Mahnart: 19 -> 20

UPDATE AD_TreeNodeMM
SET SeqNo = 21,
    Updated = TO_TIMESTAMP('2026-08-12 09:00:08', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 541103; -- Mahnstufe Übersetzung: 20 -> 21
