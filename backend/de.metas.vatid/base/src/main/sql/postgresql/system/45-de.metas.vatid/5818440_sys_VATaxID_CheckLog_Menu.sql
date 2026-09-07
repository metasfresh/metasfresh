-- VAT-ID online check: AD_Menu entry + tree placement for the VATaxID_CheckLog window (542183).
-- Placed directly after the VATaxID_Config entry (542356, "Finanzen -> Einstellungen", SeqNo=13 —
-- see migration 5818380) rather than derived from EntityType (lesson from the config window's own
-- menu history): a user who configures the VAT-ID check is the same user who wants to inspect its
-- results, so the log belongs immediately next to its configuration. Inserted at SeqNo=14, shifting
-- the eight following siblings (previously SeqNo 14-21) down by one to keep the sequence contiguous.

-- IDs allocated from idserver.metas.de:
--   AD_Menu 542357

INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      Name, Description, IsSummary, IsSOTrx, IsReadOnly, Action, AD_Window_ID, EntityType,
                      InternalName, IsCreateNew, AD_Element_ID)
VALUES (542357 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-12 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-12 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'USt-IdNr.-Prüfprotokoll', 'Protokoll der einzelnen USt-IdNr.-Prüfversuche gegen VIES.', 'N', 'N', 'Y', 'W',
        542183, 'D', 'VATaxID CheckLog', 'N', 585185);

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Menu_ID = 542357
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585185);

-- Tree placement — Menu tree (AD_Tree_ID=10), under "Finanzen -> Einstellungen" (1000072),
-- directly after VATaxID_Config (542356, SeqNo=13). Shift the eight following siblings down by one.
INSERT INTO AD_TreeNodeMM (AD_Tree_ID, Node_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Parent_ID, SeqNo)
VALUES (10, 542357, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-12 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-12 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        1000072, 14);

UPDATE AD_TreeNodeMM SET SeqNo = 15, Updated = TO_TIMESTAMP('2026-08-12 11:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540810; -- Bank: 14 -> 15

UPDATE AD_TreeNodeMM SET SeqNo = 16, Updated = TO_TIMESTAMP('2026-08-12 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540812; -- Währung: 15 -> 16

UPDATE AD_TreeNodeMM SET SeqNo = 17, Updated = TO_TIMESTAMP('2026-08-12 11:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 541107; -- Währung Übersetzung: 16 -> 17

UPDATE AD_TreeNodeMM SET SeqNo = 18, Updated = TO_TIMESTAMP('2026-08-12 11:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540813; -- Währungskurs: 17 -> 18

UPDATE AD_TreeNodeMM SET SeqNo = 19, Updated = TO_TIMESTAMP('2026-08-12 11:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 542004; -- Kursart: 18 -> 19

UPDATE AD_TreeNodeMM SET SeqNo = 20, Updated = TO_TIMESTAMP('2026-08-12 11:00:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 542006; -- Standardkursart: 19 -> 20

UPDATE AD_TreeNodeMM SET SeqNo = 21, Updated = TO_TIMESTAMP('2026-08-12 11:00:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 540780; -- Mahnart: 20 -> 21

UPDATE AD_TreeNodeMM SET SeqNo = 22, Updated = TO_TIMESTAMP('2026-08-12 11:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 541103; -- Mahnstufe Übersetzung: 21 -> 22
