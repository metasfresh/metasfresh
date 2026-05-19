-- gh#28631: Update CannotCompleteOrder_DeliveryStop message
-- - Add ErrorCode for API consumers
-- - Make text generic (works for both SO and PO)
-- - Include BPartner-ID and Shipment-Constraint-ID placeholders so the user can disambiguate
--   manual vs dunning-generated blocks (the Java throw site passes them as parameters).

UPDATE AD_Message
SET MsgText = 'Cannot complete order: business partner {0} has an active delivery stop (Shipment Restriction {1}). Open the Shipment Restrictions window to review or release it.',
    ErrorCode = 'CANNOT_COMPLETE_ORDER_DELIVERY_STOP',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Message_ID = 544514;

UPDATE AD_Message_Trl
SET MsgText = 'Auftrag kann nicht fertiggestellt werden: Geschäftspartner {0} hat eine aktive Liefer-/Auftragssperre (Lieferung Einschränkung {1}). Bitte im Fenster Lieferung Einschränkung prüfen oder aufheben.',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Message_ID = 544514 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Message_Trl
SET MsgText = 'Cannot complete order: business partner {0} has an active delivery stop (Shipment Restriction {1}). Open the Shipment Restrictions window to review or release it.',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Message_ID = 544514 AND AD_Language = 'en_US';

-- NOTE (gh#28631): The previous version of this script created AD_Message 'DeliveryStopReasonRequired'.
-- That message is now obsolete — the mandatory-reason validation is enforced at the AD_Field MandatoryLogic
-- layer (@IsDeliveryStop/N@=Y on C_BPartner.DeliveryStopReason). No Java/MI validation needed.
