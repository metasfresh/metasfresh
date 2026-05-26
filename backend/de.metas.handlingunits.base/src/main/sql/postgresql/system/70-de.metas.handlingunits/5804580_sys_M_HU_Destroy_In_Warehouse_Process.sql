-- 2026-05-26
-- AD_Process, parameter, element, menu entry and tree node
-- for the "HU Vernichten im Lager" process.
--
-- Process classname: de.metas.handlingunits.process.M_HU_Destroy_In_Warehouse
-- SQL function:      m_hu_destroy_in_warehouse(p_m_warehouse_id, p_ad_pinstance_id)

-- =============================================
-- 1. AD_Element (for menu entry)
-- =============================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Element_ID, ColumnName, Name, PrintName, EntityType)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5849130 /*From ID Server*/,
        'M_HU_Destroy_In_Warehouse',
        'HU Vernichten im Lager',
        'HU Vernichten im Lager',
        'de.metas.handlingunits');

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, Help, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5849130, 'HU Vernichten im Lager', 'HU Vernichten im Lager', NULL, NULL, 'N');

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, Help, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5849130, 'HU Vernichten im Lager', 'HU Vernichten im Lager', NULL, NULL, 'N');

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, Help, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5849130, 'Destroy HUs in Warehouse', 'Destroy HUs in Warehouse', NULL, NULL, 'Y');

-- =============================================
-- 2. AD_Process
-- =============================================
INSERT INTO AD_Process (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Process_ID, Value, Name, Description, Help,
                        Classname, IsReport, AccessLevel, EntityType, ShowHelp, Type)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5856210 /*From ID Server*/,
        'M_HU_Destroy_In_Warehouse',
        'HU Vernichten im Lager',
        'Setzt alle HUs im gewählten Lager auf Status "Vernichtet" und erstellt einen Excel-Bericht.',
        'Dieser Prozess vernichtet alle aktiven Handling Units (HUs) im gewählten Lager, deren Status auf den Lagerbestand angerechnet wird (Aktiv, Gepickt, Ausgegeben). HUs mit Status "Versandt" oder bereits "Vernichtet" werden übersprungen. Vor jeder Änderung wird eine Sicherungskopie der Tabelle m_hu angelegt. Am Ende wird eine Excel-Datei mit allen vernichteten HUs (Produkte, Menge, Lagerort) erstellt und kann gespeichert werden.',
        'de.metas.handlingunits.process.M_HU_Destroy_In_Warehouse',
        'N', '3', 'de.metas.handlingunits', 'Y', 'Java');

-- de_DE
INSERT INTO AD_Process_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Process_ID, Name, Description, Help, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5856210,
        'HU Vernichten im Lager',
        'Setzt alle HUs im gewählten Lager auf Status "Vernichtet" und erstellt einen Excel-Bericht.',
        'Dieser Prozess vernichtet alle aktiven Handling Units (HUs) im gewählten Lager, deren Status auf den Lagerbestand angerechnet wird (Aktiv, Gepickt, Ausgegeben). HUs mit Status "Versandt" oder bereits "Vernichtet" werden übersprungen. Vor jeder Änderung wird eine Sicherungskopie der Tabelle m_hu angelegt. Am Ende wird eine Excel-Datei mit allen vernichteten HUs (Produkte, Menge, Lagerort) erstellt und kann gespeichert werden.',
        'N');

-- de_CH
INSERT INTO AD_Process_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Process_ID, Name, Description, Help, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5856210,
        'HU Vernichten im Lager',
        'Setzt alle HUs im gewählten Lager auf Status "Vernichtet" und erstellt einen Excel-Bericht.',
        'Dieser Prozess vernichtet alle aktiven Handling Units (HUs) im gewählten Lager, deren Status auf den Lagerbestand angerechnet wird (Aktiv, Gepickt, Ausgegeben). HUs mit Status "Versandt" oder bereits "Vernichtet" werden übersprungen. Vor jeder Änderung wird eine Sicherungskopie der Tabelle m_hu angelegt. Am Ende wird eine Excel-Datei mit allen vernichteten HUs (Produkte, Menge, Lagerort) erstellt und kann gespeichert werden.',
        'N');

-- en_US
INSERT INTO AD_Process_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Process_ID, Name, Description, Help, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5856210,
        'Destroy HUs in Warehouse',
        'Sets all HUs in the selected warehouse to status "Destroyed" and generates an Excel report.',
        'This process destroys all Handling Units (HUs) in the selected warehouse whose status counts toward warehouse stock (Active, Picked, Issued). HUs with status Shipped or already Destroyed are skipped. A backup of the m_hu table is created before each modification. An Excel file listing all destroyed HUs (products, quantities, locator) is generated at the end.',
        'Y');

-- =============================================
-- 3. AD_Process_Para: M_Warehouse_ID
-- =============================================
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_Para_ID, AD_Process_ID, AD_Element_ID, ColumnName, Name, Description,
                             AD_Reference_ID, AD_Val_Rule_ID, DefaultValue, SeqNo, IsMandatory, FieldLength, IsRange,
                             EntityType, IsCentrallyMaintained)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5432080 /*From ID Server*/, 5856210 /*AD_Process_ID*/, 459 /*M_Warehouse_ID element*/,
        'M_Warehouse_ID',
        'Lager',
        'Lager, dessen HUs vernichtet werden sollen',
        30 /*Table*/, 540369 /*M_Warehouse val rule*/,
        '1000109', -- default: Materialentnahmelager (vanilla DB)
        10, 'Y', 0, 'N', 'de.metas.handlingunits', 'Y');

-- de_DE
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_Process_Para_ID, Name, Description, Help, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5432080, 'Lager', 'Lager, dessen HUs vernichtet werden sollen', NULL, 'N');

-- de_CH
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_Process_Para_ID, Name, Description, Help, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5432080, 'Lager', 'Lager, dessen HUs vernichtet werden sollen', NULL, 'N');

-- en_US
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_Process_Para_ID, Name, Description, Help, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5432080, 'Warehouse', 'Warehouse whose HUs shall be destroyed', NULL, 'Y');

-- =============================================
-- 4. AD_Menu
-- =============================================
INSERT INTO AD_Menu (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                     AD_Menu_ID, Name, InternalName, Action, AD_Process_ID, AD_Element_ID,
                     EntityType, IsSOTrx, IsSummary, IsReadOnly)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5423300 /*From ID Server*/,
        'HU Vernichten im Lager',
        'M_HU_Destroy_In_Warehouse',
        'P', 5856210, 5849130, 'de.metas.handlingunits', 'N', 'N', 'N');

-- de_DE
INSERT INTO AD_Menu_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Menu_ID, Name, Description, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5423300, 'HU Vernichten im Lager', NULL, 'N');

-- de_CH
INSERT INTO AD_Menu_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Menu_ID, Name, Description, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5423300, 'HU Vernichten im Lager', NULL, 'N');

-- en_US
INSERT INTO AD_Menu_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Menu_ID, Name, Description, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        5423300, 'Destroy HUs in Warehouse', NULL, 'Y');

-- =============================================
-- 5. AD_TreeNodeMM (place under Verpackung/HU management node 1000016)
-- =============================================
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
       TO_TIMESTAMP('2026-05-26 12:00', 'YYYY-MM-DD HH24:MI'), 0,
       (SELECT MIN(AD_Tree_ID) FROM AD_Tree WHERE AD_Client_ID = 0 AND IsActive = 'Y' AND IsAllNodes = 'Y' AND AD_Table_ID = 116),
       5423300 /*AD_Menu_ID*/,
       1000016 /*Parent: Verpackung / HU management*/,
       COALESCE((SELECT MAX(SeqNo)
                 FROM AD_TreeNodeMM
                 WHERE Parent_ID = 1000016
                   AND AD_Tree_ID = (SELECT MIN(AD_Tree_ID)
                                     FROM AD_Tree
                                     WHERE AD_Client_ID = 0
                                       AND IsActive = 'Y'
                                       AND IsAllNodes = 'Y'
                                       AND AD_Table_ID = 116)), 0) + 10;
