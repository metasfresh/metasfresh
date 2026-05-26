-- me03 #29261: Order Line Split — follow-up fix
-- Address code-review findings on 5804610 / 5804650:
--   - swap base Name/MsgText to German (metasfresh German-first convention)
--   - add en_US AD_Element_Trl + AD_Message_Trl rows for English-language sessions
--   - add ErrorCode on the 4 validation messages (consistent with recent practice)
-- IDs from ID server (http://idserver.metas.de):
-- AD_MigrationScript -> 5804660

-- 2026-05-26T00:00:00.000Z

-- ============================================================================
-- AD_Element 584915 — base language to German + en_US Trl override
-- ============================================================================

UPDATE AD_Element
SET Name = 'Aufzuteilende Menge',
    PrintName = 'Aufzuteilende Menge',
    Updated = NOW(),
    UpdatedBy = 100
WHERE AD_Element_ID = 584915;

INSERT INTO AD_Element_Trl (
    AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Name, PrintName, IsTranslated
) VALUES
    (584915, 'en_US', 0, 0, 'Y', NOW(), 100, NOW(), 100, 'Qty to split off', 'Qty to split off', 'Y');

-- ============================================================================
-- AD_Message 545719..545722 — base language to German + ErrorCode + en_US Trl
-- ============================================================================

UPDATE AD_Message SET
    MsgText = 'Die abzutrennende Menge ({0}) muss grösser als Null und kleiner als die bestellte Menge der Position ({1}) sein.',
    ErrorCode = 'ORDER_LINE_SPLIT_QTY_TOO_LARGE',
    Updated = NOW(),
    UpdatedBy = 100
WHERE AD_Message_ID = 545719;

UPDATE AD_Message SET
    MsgText = 'Die verbleibende Menge der Position ({0}) darf nicht unter der bereits gelieferten Menge ({1}) liegen.',
    ErrorCode = 'ORDER_LINE_SPLIT_QTY_BELOW_DELIVERED',
    Updated = NOW(),
    UpdatedBy = 100
WHERE AD_Message_ID = 545720;

UPDATE AD_Message SET
    MsgText = 'Die verbleibende Menge der Position ({0}) darf nicht unter der bereits fakturierten Menge ({1}) liegen.',
    ErrorCode = 'ORDER_LINE_SPLIT_QTY_BELOW_INVOICED',
    Updated = NOW(),
    UpdatedBy = 100
WHERE AD_Message_ID = 545721;

UPDATE AD_Message SET
    MsgText = 'Der Auftrag muss fertig gestellt sein, bevor eine Position aufgeteilt werden kann.',
    ErrorCode = 'ORDER_LINE_SPLIT_ORDER_NOT_COMPLETED',
    Updated = NOW(),
    UpdatedBy = 100
WHERE AD_Message_ID = 545722;

-- en_US AD_Message_Trl rows with the English text that previously lived in the base column
INSERT INTO AD_Message_Trl (
    AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    MsgText, IsTranslated
) VALUES
    (545719, 'en_US', 0, 0, 'Y', NOW(), 100, NOW(), 100, 'Qty to split off ({0}) must be greater than zero and less than the line''s ordered qty ({1}).', 'Y'),
    (545720, 'en_US', 0, 0, 'Y', NOW(), 100, NOW(), 100, 'Resulting line qty ({0}) cannot be below already-delivered qty ({1}).', 'Y'),
    (545721, 'en_US', 0, 0, 'Y', NOW(), 100, NOW(), 100, 'Resulting line qty ({0}) cannot be below already-invoiced qty ({1}).', 'Y'),
    (545722, 'en_US', 0, 0, 'Y', NOW(), 100, NOW(), 100, 'Order must be completed before splitting a line.', 'Y');
