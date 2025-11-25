SELECT db_drop_functions('*.shipment_dynamics_json')
;

CREATE OR REPLACE FUNCTION shipment_dynamics_json(p_m_inout_id text)
    RETURNS TABLE
            (
                "orgCode"            text,
                "deliveryNoteNumber" text,
                "poReference"        text,

                "dateShip"           timestamp,
                "partnerIdentifier"  text,
                "partnerValue"       text,
                "partnerName"        text,
                "lines"              jsonb
            )
    LANGUAGE sql
    STABLE
AS
$$
WITH dynamics_system AS (
    SELECT externalsystem_id
    FROM externalsystem
    WHERE value = 'Dynamics365'
    LIMIT 1
)
SELECT org.value,
       shipment.documentno,
       shipment.poreference,
       shipment.movementdate::timestamp,
       COALESCE(partnerExternalReference.externalreference, partner.c_bpartner_id::text),
       partner.value,
       partner.name,
       lines.json_data::jsonb
FROM m_inout shipment
         INNER JOIN ad_org org ON shipment.ad_org_id = org.ad_org_id
         INNER JOIN c_bpartner partner ON shipment.c_bpartner_id = partner.c_bpartner_id
         LEFT JOIN s_externalreference partnerExternalReference ON partnerExternalReference.record_id = partner.c_bpartner_id
    AND partnerExternalReference.type = 'BPartner'
    AND partnerExternalReference.referenced_record_id = partner.c_bpartner_id
    AND partnerExternalReference.externalsystem_id = (SELECT externalsystem_id FROM dynamics_system)
         LEFT JOIN (SELECT line.m_inout_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'poLineId', line.m_inoutline_id,
                                            'productIdentifier', product.value,
                                            'uom', ouom.x12de355,
                                            'qty', line.qtyentered,
                                            'shippingItems', shippingItems.json_data::jsonb
                                        ) ORDER BY line.line) AS json_data
                    FROM m_inoutline line
                             INNER JOIN m_product product ON product.m_product_id = line.m_product_id
                             INNER JOIN c_uom ouom ON ouom.c_uom_id = line.c_uom_id
                             LEFT JOIN (SELECT assignment.record_id,
                                               JSON_AGG(JSON_BUILD_OBJECT('serialNumber', attribute.value)) AS json_data
                                        FROM m_hu_assignment assignment
                                                 INNER JOIN m_hu hu ON assignment.m_hu_id = hu.m_hu_id
                                                 INNER JOIN m_hu_attribute attribute ON attribute.m_hu_id = hu.m_hu_id
                                        WHERE ad_table_id = get_table_id('M_InOutLine')
                                          AND attribute.m_attribute_id = (SELECT m_attribute_id FROM m_attribute WHERE name = 'S/N')
                                        GROUP BY assignment.record_id) shippingItems ON shippingItems.record_id = line.m_inoutline_id
                    GROUP BY line.m_inout_id) lines ON lines.m_inout_id = shipment.m_inout_id
WHERE shipment.m_inout_id = p_m_inout_id::numeric
  AND shipment.issotrx = 'Y';
$$
;
