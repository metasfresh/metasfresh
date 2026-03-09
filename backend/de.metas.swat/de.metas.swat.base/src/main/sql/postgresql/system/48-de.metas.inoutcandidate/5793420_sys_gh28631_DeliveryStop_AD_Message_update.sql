-- gh#28631: Update CannotCompleteOrder_DeliveryStop message
-- - Add ErrorCode for API consumers
-- - Make text generic (works for both SO and PO)

UPDATE AD_Message
SET MsgText = 'Cannot complete order because the business partner has a delivery stop. Check Shipment Restrictions window.',
    ErrorCode = 'CANNOT_COMPLETE_ORDER_DELIVERY_STOP',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Message_ID = 544514;

UPDATE AD_Message_Trl
SET MsgText = 'Der Auftrag kann nicht fertiggestellt werden, weil für den Geschäftspartner eine Liefer-/Auftragssperre besteht. Prüfen Sie das Fenster Lieferung Einschränkung.',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Message_ID = 544514 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Message_Trl
SET MsgText = 'Cannot complete order because the business partner has a delivery stop. Check Shipment Restrictions window.',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Message_ID = 544514 AND AD_Language = 'en_US';

-- ==========================================================================
-- New AD_Message: DeliveryStopReasonRequired (validation error)
-- ==========================================================================
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgType, MsgText, EntityType, ErrorCode)
VALUES (545643, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        'DeliveryStopReasonRequired', 'E',
        'Bitte geben Sie einen Grund für die Liefer-/Auftragssperre an.',
        'de.metas.inoutcandidate', 'DELIVERY_STOP_REASON_REQUIRED');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            MsgText, IsTranslated)
SELECT 545643, AD_Language, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
       'Bitte geben Sie einen Grund für die Liefer-/Auftragssperre an.', 'N'
FROM AD_Language WHERE IsSystemLanguage = 'Y' AND AD_Language NOT IN (SELECT AD_Language FROM AD_Message_Trl WHERE AD_Message_ID = 545643);

UPDATE AD_Message_Trl
SET MsgText = 'Please provide a reason for the delivery/order stop.', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Message_ID = 545643 AND AD_Language = 'en_US';
