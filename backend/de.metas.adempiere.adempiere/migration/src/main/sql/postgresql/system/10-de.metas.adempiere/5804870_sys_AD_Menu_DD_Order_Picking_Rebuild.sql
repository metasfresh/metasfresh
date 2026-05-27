-- 2026-05-27
-- AD_Menu entry for DD_Order_Picking_Rebuild process
-- Allows admin users to manually trigger the DD_Order picking reconciliation from the WebUI (REQUIREMENTS §3.5)

-- =============================================
-- AD_Element for the menu entry
-- =============================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Element_ID, ColumnName, Name, PrintName, EntityType)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584873 /*From ID Server*/,
        'DD_Order_Picking_Rebuild',
        'Picking DD_Orders neu aufbauen',
        'Picking DD_Orders neu aufbauen',
        'de.metas.handlingunits');

-- AD_Element_Trl: de_DE
INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, Help, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584873 /*From ID Server*/,
        'Picking DD_Orders neu aufbauen', 'Picking DD_Orders neu aufbauen', NULL, NULL, 'N');

-- AD_Element_Trl: de_CH
INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, Help, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584873 /*From ID Server*/,
        'Picking DD_Orders neu aufbauen', 'Picking DD_Orders neu aufbauen', NULL, NULL, 'N');

-- AD_Element_Trl: en_US
INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, Help, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584873 /*From ID Server*/,
        'Rebuild Picking DD_Orders', 'Rebuild Picking DD_Orders', NULL, NULL, 'Y');

-- =============================================
-- AD_Menu pointing to the existing process (585623)
-- =============================================
INSERT INTO AD_Menu (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                     AD_Menu_ID, Name, InternalName, Action, AD_Process_ID, AD_Element_ID, EntityType, IsSOTrx, IsSummary, IsReadOnly)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        542331 /*From ID Server*/,
        'Picking DD_Orders neu aufbauen',
        'DD_Order_Picking_Rebuild',
        'P', 585623 /*From ID Server*/, 584873 /*From ID Server*/, 'de.metas.handlingunits', 'N', 'N', 'N');

-- AD_Menu_Trl: de_DE
INSERT INTO AD_Menu_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Menu_ID, Name, Description, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        542331 /*From ID Server*/,
        'Picking DD_Orders neu aufbauen', NULL, 'N');

-- AD_Menu_Trl: de_CH
INSERT INTO AD_Menu_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Menu_ID, Name, Description, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        542331 /*From ID Server*/,
        'Picking DD_Orders neu aufbauen', NULL, 'N');

-- AD_Menu_Trl: en_US
INSERT INTO AD_Menu_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Menu_ID, Name, Description, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        542331 /*From ID Server*/,
        'Rebuild Picking DD_Orders', NULL, 'Y');

-- =============================================
-- AD_TreeNodeMM (place menu entry under System Distribution parent 1000016)
-- =============================================
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT 0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-27 14:00', 'YYYY-MM-DD HH24:MI'), 0,
       (SELECT MIN(AD_Tree_ID) FROM AD_Tree WHERE AD_Client_ID = 0 AND IsActive = 'Y' AND IsAllNodes = 'Y' AND AD_Table_ID = 116),
       542331 /*From ID Server*/,
       1000016,
       COALESCE((SELECT MAX(SeqNo) FROM AD_TreeNodeMM
                 WHERE Parent_ID = 1000016
                   AND AD_Tree_ID = (SELECT MIN(AD_Tree_ID) FROM AD_Tree WHERE AD_Client_ID = 0 AND IsActive = 'Y' AND IsAllNodes = 'Y' AND AD_Table_ID = 116)),
                0) + 10;
