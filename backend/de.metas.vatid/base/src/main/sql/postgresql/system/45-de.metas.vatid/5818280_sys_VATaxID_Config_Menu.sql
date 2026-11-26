-- VAT-ID online check: AD_Menu entry + tree placement for the VATaxID_Config window (542182).
-- Without this, the window has no click-path in the WebUI menu (only reachable via the raw
-- /window/542182 URL) even though AD_Window_Access already grants roles read-write on it.
-- Shape follows the sibling "*_Konfiguration" menu entries (Standardwerte-Konfiguration 542290,
-- Reifung Konfiguration 542133): reuses the window's own AD_Element for the caption, EntityType='D',
-- placed under the "Application-Dictionary" (153) admin-configuration branch, matching
-- Standardwerte-Konfiguration's placement (both EntityType='D', Action='W', IsSOTrx='N').

-- IDs allocated from idserver.metas.de:
--   AD_Menu 542356

INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      Name, Description, IsSummary, IsSOTrx, IsReadOnly, Action, AD_Window_ID, EntityType,
                      InternalName, IsCreateNew, AD_Element_ID)
VALUES (542356 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-11 16:40:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 16:40:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'USt-IdNr.-Konfiguration', 'Konfiguration der USt-IdNr.-Prüfung je Organisation.', 'N', 'N', 'N', 'W',
        542182, 'D', 'VATaxID Config', 'N', 585165);

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Menu_ID = 542356
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585165);

-- Tree placement — Menu tree (AD_Tree_ID=10), under "Application-Dictionary" (153), same branch
-- as Standardwerte-Konfiguration (542290, SeqNo=1 there); next free SeqNo at this parent is 36.
INSERT INTO AD_TreeNodeMM (AD_Tree_ID, Node_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Parent_ID, SeqNo)
VALUES (10, 542356, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-11 16:40:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 16:40:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        153, 36);
