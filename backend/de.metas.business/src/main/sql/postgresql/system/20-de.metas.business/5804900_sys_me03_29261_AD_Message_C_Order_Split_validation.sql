-- AD_Messages for OrderSplit validation errors
-- Base language is German per metasfresh convention (DE-base + en_US translation).

INSERT INTO AD_Message (
    AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Value, MsgText, MsgType, EntityType
) VALUES
    (545728, 0, 0, 'Y', NOW(), 100, NOW(), 100,
     'C_Order_Split_NoShipments',
     'Der Auftrag hat keine fertig gestellten Lieferscheine — eine Aufteilung ist nur nach mindestens einer Lieferung sinnvoll.',
     'E', 'de.metas.order'),
    (545729, 0, 0, 'Y', NOW(), 100, NOW(), 100,
     'C_Order_Split_NothingToSplit',
     'Der Auftrag hat keine offene Liefermenge — es gibt nichts aufzuteilen.',
     'E', 'de.metas.order');

-- English translation (en_US)
INSERT INTO AD_Message_Trl (
    AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    MsgText, IsTranslated
) VALUES
    (545728, 'en_US', 0, 0, 'Y', NOW(), 100, NOW(), 100,
     'Order has no completed shipments — splitting is only meaningful after at least one shipment.',
     'Y'),
    (545729, 'en_US', 0, 0, 'Y', NOW(), 100, NOW(), 100,
     'Order has no unshipped quantity — nothing to split off.',
     'Y');
