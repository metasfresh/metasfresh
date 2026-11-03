SELECT db_drop_functions('*.shipment_dynamics_json')
;

CREATE OR REPLACE FUNCTION shipment_dynamics_json(p_m_inout_id text)
    RETURNS TABLE
            (
                "orgCode"            text,
                "deliveryNoteNumber" text,
                "poReference"        text,

                "dateShip"           date,
                "partnerIdentifier"  text,
                "partnerValue"       text,
                "partnerName"        text,
                "lines"              jsonb
            )
    LANGUAGE sql
    STABLE
AS
$$
WITH dynamics_system AS (SELECT externalsystem_id
                         FROM externalsystem
                         WHERE value = 'Dynamics365'
                         LIMIT 1),
     shipping_items AS (SELECT assignment.record_id,
                               COALESCE(
                                       JSONB_AGG(
                                               JSONB_BUILD_OBJECT('serialNumber', attribute.value)
                                           ),
                                       '[]'::jsonb
                                   ) AS json_data
                        FROM m_hu_assignment assignment
                                 INNER JOIN m_hu hu ON assignment.m_hu_id = hu.m_hu_id
                                 INNER JOIN m_hu_attribute attribute ON attribute.m_hu_id = hu.m_hu_id
                        WHERE assignment.ad_table_id = get_table_id('M_InOutLine')
                          AND attribute.m_attribute_id = (SELECT m_attribute_id
                                                          FROM m_attribute
                                                          WHERE m_attribute.value = 'SerialNo')
                          AND assignment.record_id IN (SELECT m_inoutline_id
                                                       FROM m_inoutline
                                                       WHERE m_inout_id = p_m_inout_id::numeric)
                        GROUP BY assignment.record_id),
     shipment_lines AS (SELECT line.m_inout_id,
                               COALESCE(
                                       JSONB_AGG(
                                               JSONB_BUILD_OBJECT(
                                                       'poLineId', COALESCE(line.c_orderline_id, line.m_inoutline_id),
                                                       'productIdentifier', product.value,
                                                       'uom', ouom.x12de355,
                                                       'qty', line.qtyentered,
                                                       'shippingItems', COALESCE(items.json_data, '[]'::jsonb)
                                                   ) ORDER BY line.line
                                           ),
                                       '[]'::jsonb
                                   ) AS json_data
                        FROM m_inoutline line
                                 INNER JOIN m_product product ON product.m_product_id = line.m_product_id
                                 INNER JOIN c_uom ouom ON ouom.c_uom_id = line.c_uom_id
                                 LEFT JOIN shipping_items items ON items.record_id = line.m_inoutline_id
                        WHERE line.m_inout_id = p_m_inout_id::numeric
                        GROUP BY line.m_inout_id)
SELECT org.value,
       shipment.documentno,
       shipment.poreference,
       shipment.movementdate::date,
       COALESCE(ext_ref.externalreference, partner.c_bpartner_id::text),
       partner.value,
       partner.name,
       COALESCE(lines.json_data, '[]'::jsonb)
FROM m_inout shipment
         INNER JOIN ad_org org ON shipment.ad_org_id = org.ad_org_id
         INNER JOIN c_bpartner partner ON shipment.c_bpartner_id = partner.c_bpartner_id
         LEFT JOIN LATERAL (
    SELECT externalreference
    FROM s_externalreference
    WHERE record_id = partner.c_bpartner_id
      AND type = 'BPartner'
      AND externalsystem_id = (SELECT externalsystem_id FROM dynamics_system)
    LIMIT 1
    ) ext_ref ON TRUE
         LEFT JOIN shipment_lines lines ON lines.m_inout_id = shipment.m_inout_id
WHERE shipment.m_inout_id = p_m_inout_id::numeric
  AND shipment.issotrx = 'Y';
$$
;
