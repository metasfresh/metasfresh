SELECT db_drop_functions('*.sales_order_dynamics_json')
;

CREATE OR REPLACE FUNCTION sales_order_dynamics_json(p_order_id text)
    RETURNS TABLE
            (
                "orgCode"           text,
                "orderNumber"       numeric,
                "dateOrdered"       timestamp,

                "datePromised"      timestamp,
                "partnerIdentifier" text,
                "partnerValue"      text,
                "partnerName"       text,
                "currency"          text,
                "Lines"             jsonb,
                "charges"           jsonb
            )
    LANGUAGE sql
    STABLE
AS
$$
SELECT org.value,
       sorder.c_order_id,
       sorder.dateordered,
       sorder.datepromised::timestamp,
       partner.c_bpartner_id::text,
       partner.value,
       partner.name,
       c.iso_code::text,
       lines.json_data::jsonb,
       charges.json_data::jsonb
FROM c_order sorder
         INNER JOIN ad_org org ON sorder.ad_org_id = org.ad_org_id
         INNER JOIN c_bpartner partner ON sorder.c_bpartner_id = partner.c_bpartner_id
         INNER JOIN c_currency c ON c.C_Currency_ID = sorder.C_Currency_ID
         LEFT JOIN (SELECT ol.c_order_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'orderLineId', ol.c_orderline_id,
                                            'orderLineNumber', ol.line,
                                            'uom', ouom.x12de355,
                                            'qty', ol.qtyentered,
                                            'price', ol.priceactual,
                                            'discount', ol.discount,
                                            'productName', product.name,
                                            'productDescription', product.description,
                                            'productIdentifier', product.value
                                        ) ORDER BY ol.line) AS json_data
                    FROM c_orderline ol
                             INNER JOIN m_product product ON product.m_product_id = ol.m_product_id
                             INNER JOIN c_uom ouom ON ouom.c_uom_id = ol.c_uom_id
                    WHERE product.producttype = 'I'
                    GROUP BY ol.c_order_id) lines ON lines.c_order_id = sorder.C_Order_ID
         LEFT JOIN (SELECT ol.c_order_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'chargeIdentifier', ol.c_orderline_id,
                                            'price', ol.priceactual
                                        ) ORDER BY ol.line) AS json_data
                    FROM c_orderline ol
                             INNER JOIN m_product product ON product.m_product_id = ol.m_product_id
                    WHERE product.producttype != 'I'
                    GROUP BY ol.c_order_id) charges ON charges.c_order_id = sorder.C_Order_ID
WHERE sorder.c_order_id = p_order_id::numeric
  AND sorder.issotrx = 'Y';
$$
;
