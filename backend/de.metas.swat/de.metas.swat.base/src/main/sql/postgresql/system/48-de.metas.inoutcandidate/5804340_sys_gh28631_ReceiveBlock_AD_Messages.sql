-- gh#28631 follow-up: two AD_Messages for the receipt-side delivery-stop guard
-- CannotReceive_DeliveryStop_Multi (ID=545711) — process-level guard, lists blocked vendors
-- CannotReceive_DeliveryStop_Single (ID=545712) — M_InOut BEFORE_PREPARE interceptor

-- ===========================================================================
-- CannotReceive_DeliveryStop_Multi
-- ===========================================================================

INSERT INTO AD_Message
(AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, ErrorCode, Updated, UpdatedBy, Value)
VALUES
(0, 545711, 0, TO_TIMESTAMP('2026-05-24 00:00', 'YYYY-MM-DD HH24:MI'), 0, 'de.metas.inoutcandidate', 'Y',
 'Receipt not possible: selected receipt schedules belong to business partners with an active delivery/order block: {0}. Please unselect and retry.',
 'E', 'CANNOT_RECEIVE_DELIVERY_STOP_MULTI',
 TO_TIMESTAMP('2026-05-24 00:00', 'YYYY-MM-DD HH24:MI'), 0, 'ERR_RECEIPT_DELIVERY_STOP_MULTI')
;

INSERT INTO AD_Message_Trl
(AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Message_ID = 545711
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText      = 'Wareneingang nicht möglich: ausgewählte Wareneingangsdispositionen gehören zu Geschäftspartnern mit aktiver Liefer-/Auftragssperre: {0}. Bitte abwählen und erneut versuchen.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-24 00:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 0
WHERE AD_Message_ID = 545711 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Message_Trl
SET MsgText      = 'Receipt not possible: selected receipt schedules belong to business partners with an active delivery/order block: {0}. Please unselect and retry.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-24 00:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 0
WHERE AD_Message_ID = 545711 AND AD_Language = 'en_US'
;

-- ===========================================================================
-- CannotReceive_DeliveryStop_Single
-- ===========================================================================

INSERT INTO AD_Message
(AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, ErrorCode, Updated, UpdatedBy, Value)
VALUES
(0, 545712, 0, TO_TIMESTAMP('2026-05-24 00:00', 'YYYY-MM-DD HH24:MI'), 0, 'de.metas.inoutcandidate', 'Y',
 'Cannot complete receipt: business partner {0} has an active delivery/order block (Shipment Restriction {1}). Open the Shipment Restrictions window to review or release it.',
 'E', 'CANNOT_RECEIVE_DELIVERY_STOP_SINGLE',
 TO_TIMESTAMP('2026-05-24 00:00', 'YYYY-MM-DD HH24:MI'), 0, 'ERR_RECEIPT_DELIVERY_STOP_SINGLE')
;

INSERT INTO AD_Message_Trl
(AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Message_ID = 545712
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText      = 'Wareneingang kann nicht fertiggestellt werden: Geschäftspartner {0} hat eine aktive Liefer-/Auftragssperre (Lieferung Einschränkung {1}). Bitte im Fenster Lieferung Einschränkung prüfen oder aufheben.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-24 00:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 0
WHERE AD_Message_ID = 545712 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Message_Trl
SET MsgText      = 'Cannot complete receipt: business partner {0} has an active delivery/order block (Shipment Restriction {1}). Open the Shipment Restrictions window to review or release it.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-24 00:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 0
WHERE AD_Message_ID = 545712 AND AD_Language = 'en_US'
;
