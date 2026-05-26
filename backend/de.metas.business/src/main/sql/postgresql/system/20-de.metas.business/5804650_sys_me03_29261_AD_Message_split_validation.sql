-- me03 #29261: Order Line Split
-- AD_Messages for validation errors
-- IDs from ID server (http://idserver.metas.de):
-- AD_Message -> 545719, 545720, 545721, 545722

-- 2026-05-26T00:00:00.000Z
INSERT INTO AD_Message (
    AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Value, MsgText, MsgType, EntityType
) VALUES
    (545719, 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'OrderLineSplit_QtyTooLarge', 'Qty to split off ({0}) must be greater than zero and less than the line''s ordered qty ({1}).', 'E', 'de.metas.order'),
    (545720, 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'OrderLineSplit_QtyBelowDelivered', 'Resulting line qty ({0}) cannot be below already-delivered qty ({1}).', 'E', 'de.metas.order'),
    (545721, 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'OrderLineSplit_QtyBelowInvoiced', 'Resulting line qty ({0}) cannot be below already-invoiced qty ({1}).', 'E', 'de.metas.order'),
    (545722, 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'OrderLineSplit_OrderNotCompleted', 'Order must be completed before splitting a line.', 'E', 'de.metas.order')
;

-- AD_Message_Trl
INSERT INTO AD_Message_Trl (
    AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    MsgText, IsTranslated
) VALUES
    (545719, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Die abzutrennende Menge ({0}) muss größer als Null und kleiner als die bestellte Menge der Position ({1}) sein.', 'Y'),
    (545719, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Die abzutrennende Menge ({0}) muss grösser als Null und kleiner als die bestellte Menge der Position ({1}) sein.', 'Y'),
    (545720, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Die verbleibende Menge der Position ({0}) darf nicht unter der bereits gelieferten Menge ({1}) liegen.', 'Y'),
    (545720, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Die verbleibende Menge der Position ({0}) darf nicht unter der bereits gelieferten Menge ({1}) liegen.', 'Y'),
    (545721, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Die verbleibende Menge der Position ({0}) darf nicht unter der bereits fakturierten Menge ({1}) liegen.', 'Y'),
    (545721, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Die verbleibende Menge der Position ({0}) darf nicht unter der bereits fakturierten Menge ({1}) liegen.', 'Y'),
    (545722, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Der Auftrag muss fertig gestellt sein, bevor eine Position aufgeteilt werden kann.', 'Y'),
    (545722, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Der Auftrag muss fertig gestellt sein, bevor eine Position aufgeteilt werden kann.', 'Y')
;
