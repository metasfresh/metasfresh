-- VAT-ID online check: make the "USt-IdNr.-Prüfbericht" report reachable.
--
-- Two gaps, both reported from real use.
--
-- 1. IT WAS ONLY AVAILABLE IN THE GRID. AD_Table_Process 541664 (process 585652 on table
--    VATaxID_Config) carries WEBUI_ViewAction='Y' but WEBUI_DocumentAction='N', so the action appeared
--    when the configuration was listed and vanished the moment a record was opened. Both are set now.
--
-- 2. IT HAD NO MENU ENTRY AT ALL, so it was discoverable only by someone who already knew to look on a
--    configuration record. It now sits directly under the window it reports on.
--
-- THE PARAMETER, AND WHY IT IS THE CONFIG AND NOT THE ORGANISATION. The process's SQLStatement is
-- "SELECT * FROM VATaxID_Config_Report(@VATaxID_Config_ID@)" -- a context variable resolved from the
-- current record. Launched from the menu there is no record, so nothing resolves it and the report cannot
-- run. A mandatory AD_Process_Para named for that same column fills the gap: from the menu the user picks
-- the configuration, and from a record the parameter pre-fills from the record's own context via
-- DefaultValue '@VATaxID_Config_ID@'. The existing SQLStatement then works unchanged on BOTH paths, and
-- the SQL function keeps its signature. Taking AD_Org_ID instead would have meant resolving org->config
-- inside the statement and handling an organisation that has no configuration row -- more moving parts
-- for the same result, since the table holds one active row per organisation anyway.
--
-- IDs from idserver.metas.de: AD_MigrationScript 5819320, AD_Menu 542358, AD_Process_Para 543274,
-- AD_Element 585304. The parameter reuses the existing element 585165 (VATaxID_Config_ID) rather than
-- minting a second element for the same column.

-- ---------------------------------------------------------------------------------------------------
-- 1. Available from the single-record view as well as the grid
-- ---------------------------------------------------------------------------------------------------
UPDATE AD_Table_Process
SET WEBUI_DocumentAction = 'Y',
    Updated = TO_TIMESTAMP('2026-08-15 16:45:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_Process_ID = 541664;

-- ---------------------------------------------------------------------------------------------------
-- 2. The mandatory parameter
-- ---------------------------------------------------------------------------------------------------
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
                             Updated, UpdatedBy, AD_Process_ID, Name, ColumnName, AD_Reference_ID,
                             AD_Element_ID, IsMandatory, IsRange, SeqNo, FieldLength, EntityType,
                             DefaultValue, IsAutocomplete)
VALUES (543274 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-15 16:45:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-15 16:45:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        585652, 'USt-IdNr.-Konfiguration', 'VATaxID_Config_ID', 30 /* Table Direct */,
        585165, 'Y', 'N', 10, 10, 'D',
        '@VATaxID_Config_ID@', 'N');

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, IsTranslated, AD_Client_ID,
                                 AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created,
       t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Process_Para_ID = 543274
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585165);

-- ---------------------------------------------------------------------------------------------------
-- 3. The menu entry, under Einstellungen next to the configuration window it reports on
-- ---------------------------------------------------------------------------------------------------
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated,
                        UpdatedBy, ColumnName, Name, PrintName, EntityType)
VALUES (585304 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-15 16:45:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-15 16:45:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'VATaxID_Config_Report', 'USt-IdNr.-Prüfbericht', 'USt-IdNr.-Prüfbericht', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID,
                            AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created,
       t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585304
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                     Name, Description, IsSummary, IsSOTrx, IsReadOnly, Action, AD_Process_ID, EntityType,
                     InternalName, IsCreateNew, AD_Element_ID)
VALUES (542358 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-15 16:45:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-15 16:45:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'USt-IdNr.-Prüfbericht',
        'Übersicht über den Prüfstand der USt-IdNr. einer Organisation, als Excel-/CSV-Export.',
        'N', 'N', 'Y', 'P', 585652, 'D', 'VATaxID Config Report', 'N', 585304);

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID,
                         Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created,
       t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Menu_ID = 542358
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585304);

-- Tree placement -- Menu tree (AD_Tree_ID=10), under "Finanzen -> Einstellungen" (1000072), directly
-- after "USt-IdNr.-Konfiguration" (SeqNo 13). The siblings from 14 upwards shift by one in a single
-- statement rather than being renumbered individually, so this stays correct however many there are.
UPDATE AD_TreeNodeMM
SET SeqNo = SeqNo + 1,
    Updated = TO_TIMESTAMP('2026-08-15 16:45:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Parent_ID = 1000072 AND SeqNo >= 14;

INSERT INTO AD_TreeNodeMM (AD_Tree_ID, Node_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
                           Updated, UpdatedBy, Parent_ID, SeqNo)
VALUES (10, 542358, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-15 16:45:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-15 16:45:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
        1000072, 14);
