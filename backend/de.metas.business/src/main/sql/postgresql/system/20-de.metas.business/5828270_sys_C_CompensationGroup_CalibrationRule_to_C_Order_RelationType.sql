-- Calibration rules: navigation from a rule to the sales orders whose lines carry it.
--
-- The order line FK C_OrderLine.C_CompensationGroup_CalibrationRule_ID alone gives no
-- Related-Documents entry, because C_OrderLine is not on a header tab. An explicit AD_RelationType
-- lists, for a selected calibration rule, the sales orders having at least one line that carries the rule.
-- The target window is left empty on purpose: it is resolved from the window of C_Order, so the same
-- script opens the sales order window of each instance (including an override window).
--
-- IDs allocated from idserver.metas.de on 2026-10-06:
--   AD_MigrationScript 5828270 (this script)
--   AD_Reference 542146 (source: calibration rule), 542147 (target: C_Order)
--   AD_RelationType 540511
-- AD_Key: 593714 = C_CompensationGroup_CalibrationRule.C_CompensationGroup_CalibrationRule_ID (5828210), 2161 = C_Order.C_Order_ID

-- Source reference: the calibration rule
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542146 /*From ID Server*/, TO_TIMESTAMP('2026-10-06 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.order', 'Y', 'N', 'C_CompensationGroup_CalibrationRule', TO_TIMESTAMP('2026-10-06 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Reference_ID = 542146
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy)
VALUES (0, 593714, 0, 542146, 542655, TO_TIMESTAMP('2026-10-06 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.order', 'Y', 'N', 'Y', TO_TIMESTAMP('2026-10-06 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- Target reference: sales orders with a line carrying the selected rule
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542147 /*From ID Server*/, TO_TIMESTAMP('2026-10-06 13:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.order', 'Y', 'N', 'C_Order target for C_CompensationGroup_CalibrationRule', TO_TIMESTAMP('2026-10-06 13:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Reference_ID = 542147
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 2161, 0, 542147, 259, TO_TIMESTAMP('2026-10-06 13:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.order', 'Y', 'N', 'N', TO_TIMESTAMP('2026-10-06 13:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_Order.IsSOTrx=''Y'' AND EXISTS (SELECT 1 FROM C_OrderLine ol WHERE ol.C_Order_ID=C_Order.C_Order_ID AND ol.C_CompensationGroup_CalibrationRule_ID=@C_CompensationGroup_CalibrationRule_ID@)')
;

-- Relation type: calibration rule -> sales orders
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 542146, 542147, 540511 /*From ID Server*/, TO_TIMESTAMP('2026-10-06 13:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.order', 'C_CompensationGroup_CalibrationRule_to_C_Order_SO', 'Y', 'N', 'C_CompensationGroup_CalibrationRule -> C_Order (SO)', TO_TIMESTAMP('2026-10-06 13:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
