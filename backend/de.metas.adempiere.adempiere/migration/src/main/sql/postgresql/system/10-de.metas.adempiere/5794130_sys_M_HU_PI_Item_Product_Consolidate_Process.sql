-- 2026-03-12
-- AD_Process, parameters, table-process link, menu entry, and tree node
-- for M_HU_PI_Item_Product consolidation

-- =============================================
-- 1. AD_Process
-- =============================================
INSERT INTO AD_Process (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Process_ID, Value, Name, Description, Help,
                        Classname, IsReport, AccessLevel, EntityType, ShowHelp, Type)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        585596 /*From ID Server*/,
        'M_HU_PI_Item_Product_Consolidate_Report',
        'CU-TU Zuordnung konsolidieren',
        'Normalisiert GTIN/EAN und konsolidiert doppelte Packvorschrift-Zuordnungen. Erstellt einen Excel-Bericht über Konflikte.',
        'Dieser Prozess führt zwei optionale Schritte aus: (1) GTIN/EAN Normalisierung: Kopiert EAN in GTIN wenn GTIN leer ist, löscht EAN wenn identisch mit GTIN. (2) Konsolidierung: Fasst doppelte Packvorschrift-Zuordnungen mit gleicher GTIN zu einem Eintrag ohne Geschäftspartner zusammen. Beide Schritte müssen explizit aktiviert werden. Betroffene Tabellen werden vor Änderungen gesichert. Der Prozess erstellt immer einen Excel-Bericht mit verbleibenden Konflikten.',
        'de.metas.handlingunits.process.M_HU_PI_Item_Product_Consolidate_Report',
        'N', '3', 'de.metas.handlingunits', 'Y', 'Java');

-- AD_Process_Trl: de_DE
INSERT INTO AD_Process_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Process_ID, Name, Description, Help, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        585596 /*From ID Server*/,
        'CU-TU Zuordnung konsolidieren',
        'Normalisiert GTIN/EAN und konsolidiert doppelte Packvorschrift-Zuordnungen. Erstellt einen Excel-Bericht über Konflikte.',
        'Dieser Prozess führt zwei optionale Schritte aus: (1) GTIN/EAN Normalisierung: Kopiert EAN in GTIN wenn GTIN leer ist, löscht EAN wenn identisch mit GTIN. (2) Konsolidierung: Fasst doppelte Packvorschrift-Zuordnungen mit gleicher GTIN zu einem Eintrag ohne Geschäftspartner zusammen. Beide Schritte müssen explizit aktiviert werden. Betroffene Tabellen werden vor Änderungen gesichert. Der Prozess erstellt immer einen Excel-Bericht mit verbleibenden Konflikten.',
        'N');

-- AD_Process_Trl: de_CH
INSERT INTO AD_Process_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Process_ID, Name, Description, Help, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        585596 /*From ID Server*/,
        'CU-TU Zuordnung konsolidieren',
        'Normalisiert GTIN/EAN und konsolidiert doppelte Packvorschrift-Zuordnungen. Erstellt einen Excel-Bericht über Konflikte.',
        'Dieser Prozess führt zwei optionale Schritte aus: (1) GTIN/EAN Normalisierung: Kopiert EAN in GTIN wenn GTIN leer ist, löscht EAN wenn identisch mit GTIN. (2) Konsolidierung: Fasst doppelte Packvorschrift-Zuordnungen mit gleicher GTIN zu einem Eintrag ohne Geschäftspartner zusammen. Beide Schritte müssen explizit aktiviert werden. Betroffene Tabellen werden vor Änderungen gesichert. Der Prozess erstellt immer einen Excel-Bericht mit verbleibenden Konflikten.',
        'N');

-- AD_Process_Trl: en_US
INSERT INTO AD_Process_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Process_ID, Name, Description, Help, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        585596 /*From ID Server*/,
        'Consolidate CU-TU Allocation',
        'Normalizes GTIN/EAN and consolidates duplicate packing instruction allocations. Generates an Excel report of conflicts.',
        'This process performs two optional steps: (1) GTIN/EAN normalization: Copies EAN to GTIN when GTIN is empty, clears EAN when identical to GTIN. (2) Consolidation: Merges duplicate packing instruction allocations with the same GTIN into one record without a business partner. Both steps must be explicitly activated. Affected tables are backed up before modifications. The process always generates an Excel report of remaining conflicts.',
        'Y');

-- =============================================
-- 2. AD_Process_Para: IsNormalizeGTIN
-- =============================================
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_Para_ID, AD_Process_ID, AD_Element_ID, ColumnName, Name, Description,
                             AD_Reference_ID, DefaultValue, SeqNo, IsMandatory, FieldLength, IsRange, EntityType, IsCentrallyMaintained)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        543156 /*From ID Server*/, 585596 /*From ID Server*/, 584660 /*From ID Server*/,
        'IsNormalizeGTIN', 'GTIN/EAN normalisieren',
        'Kopiert EAN in GTIN wenn GTIN leer ist, löscht EAN wenn identisch mit GTIN',
        20, 'N', 10, 'Y', 1, 'N', 'de.metas.handlingunits', 'Y');

-- AD_Process_Para_Trl: de_DE
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_Process_Para_ID, Name, Description, Help, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        543156 /*From ID Server*/,
        'GTIN/EAN normalisieren',
        'Kopiert EAN in GTIN wenn GTIN leer ist, löscht EAN wenn identisch mit GTIN',
        NULL, 'N');

-- AD_Process_Para_Trl: de_CH
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_Process_Para_ID, Name, Description, Help, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        543156 /*From ID Server*/,
        'GTIN/EAN normalisieren',
        'Kopiert EAN in GTIN wenn GTIN leer ist, löscht EAN wenn identisch mit GTIN',
        NULL, 'N');

-- AD_Process_Para_Trl: en_US
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_Process_Para_ID, Name, Description, Help, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        543156 /*From ID Server*/,
        'Normalize GTIN/EAN',
        'Copies EAN to GTIN when GTIN is empty, clears EAN when identical to GTIN',
        NULL, 'Y');

-- =============================================
-- 3. AD_Process_Para: IsConsolidate
-- =============================================
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_Para_ID, AD_Process_ID, AD_Element_ID, ColumnName, Name, Description,
                             AD_Reference_ID, DefaultValue, SeqNo, IsMandatory, FieldLength, IsRange, EntityType, IsCentrallyMaintained)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        543157 /*From ID Server*/, 585596 /*From ID Server*/, 584661 /*From ID Server*/,
        'IsConsolidate', 'Doppelte Einträge konsolidieren',
        'Fasst doppelte Packvorschrift-Zuordnungen mit gleicher GTIN zusammen',
        20, 'N', 20, 'Y', 1, 'N', 'de.metas.handlingunits', 'Y');

-- AD_Process_Para_Trl: de_DE
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_Process_Para_ID, Name, Description, Help, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        543157 /*From ID Server*/,
        'Doppelte Einträge konsolidieren',
        'Fasst doppelte Packvorschrift-Zuordnungen mit gleicher GTIN zusammen',
        NULL, 'N');

-- AD_Process_Para_Trl: de_CH
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_Process_Para_ID, Name, Description, Help, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        543157 /*From ID Server*/,
        'Doppelte Einträge konsolidieren',
        'Fasst doppelte Packvorschrift-Zuordnungen mit gleicher GTIN zusammen',
        NULL, 'N');

-- AD_Process_Para_Trl: en_US
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                 AD_Process_Para_ID, Name, Description, Help, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        543157 /*From ID Server*/,
        'Consolidate duplicates',
        'Merges duplicate packing instruction allocations with the same GTIN',
        NULL, 'Y');

-- =============================================
-- 4. AD_Table_Process (link process to M_HU_PI_Item_Product table)
-- =============================================
INSERT INTO AD_Table_Process (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Table_Process_ID, AD_Table_ID, AD_Process_ID, EntityType)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        541632 /*From ID Server*/, 540508, 585596 /*From ID Server*/, 'de.metas.handlingunits');

-- =============================================
-- 5. AD_Element for the process menu entry
-- =============================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Element_ID, ColumnName, Name, PrintName, EntityType)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584662 /*From ID Server*/,
        'M_HU_PI_Item_Product_Consolidate',
        'CU-TU Zuordnung konsolidieren',
        'CU-TU Zuordnung konsolidieren',
        'de.metas.handlingunits');

-- AD_Element_Trl: de_DE
INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, Help, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584662 /*From ID Server*/,
        'CU-TU Zuordnung konsolidieren', 'CU-TU Zuordnung konsolidieren', NULL, NULL, 'N');

-- AD_Element_Trl: de_CH
INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, Help, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584662 /*From ID Server*/,
        'CU-TU Zuordnung konsolidieren', 'CU-TU Zuordnung konsolidieren', NULL, NULL, 'N');

-- AD_Element_Trl: en_US
INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, Help, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584662 /*From ID Server*/,
        'Consolidate CU-TU Allocation', 'Consolidate CU-TU Allocation', NULL, NULL, 'Y');

-- =============================================
-- 6. AD_Menu
-- =============================================
INSERT INTO AD_Menu (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                     AD_Menu_ID, Name, InternalName, Action, AD_Process_ID, AD_Element_ID, EntityType, IsSOTrx, IsSummary, IsReadOnly)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        542306 /*From ID Server*/,
        'CU-TU Zuordnung konsolidieren',
        'M_HU_PI_Item_Product_Consolidate',
        'P', 585596 /*From ID Server*/, 584662 /*From ID Server*/, 'de.metas.handlingunits', 'N', 'N', 'N');

-- AD_Menu_Trl: de_DE
INSERT INTO AD_Menu_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Menu_ID, Name, Description, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        542306 /*From ID Server*/,
        'CU-TU Zuordnung konsolidieren', NULL, 'N');

-- AD_Menu_Trl: de_CH
INSERT INTO AD_Menu_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Menu_ID, Name, Description, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        542306 /*From ID Server*/,
        'CU-TU Zuordnung konsolidieren', NULL, 'N');

-- AD_Menu_Trl: en_US
INSERT INTO AD_Menu_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Menu_ID, Name, Description, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        542306 /*From ID Server*/,
        'Consolidate CU-TU Allocation', NULL, 'Y');

-- =============================================
-- 7. AD_TreeNodeMM (place menu entry under parent 1000016)
-- =============================================
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
       (SELECT MIN(AD_Tree_ID) FROM AD_Tree WHERE AD_Client_ID = 0 AND IsActive = 'Y' AND IsAllNodes = 'Y' AND AD_Table_ID = 116),
       542306 /*From ID Server*/,
       1000016,
       COALESCE((SELECT MAX(SeqNo) FROM AD_TreeNodeMM
                 WHERE Parent_ID = 1000016
                   AND AD_Tree_ID = (SELECT MIN(AD_Tree_ID) FROM AD_Tree WHERE AD_Client_ID = 0 AND IsActive = 'Y' AND IsAllNodes = 'Y' AND AD_Table_ID = 116)),
                0) + 10;
