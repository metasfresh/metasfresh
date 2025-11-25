SELECT db_drop_functions('*.purchase_order_dynamics_json')
;

CREATE OR REPLACE FUNCTION purchase_order_dynamics_json(p_order_id numeric)
    RETURNS TABLE
            (
                "orgCode"         text,
                "orderDocumentNo" text,
                "orderNumber"     numeric,
                "currencyCode"    text,
                "partnerValue"    text,
                "partnerName"     text,

                "dropShip"        jsonb,
                "Lines"           jsonb
            )
    LANGUAGE sql
    STABLE
AS
$$
WITH order_lines AS (
    SELECT
        ol.c_order_id,
        COALESCE(
                JSONB_AGG(
                        JSONB_BUILD_OBJECT(
                                'orderLineId', ol.c_orderline_id,
                                'orderLineNumber', ol.line,
                                'uom', ouom.x12de355,
                                'qty', ol.qtyentered,
                                'price', ol.priceactual,
                                'discount', ol.discount,
                                'productName', product.name,
                                'productDescription', product.description,
                                'productIdentifier', product.value
                            ) ORDER BY ol.line
                    ),
                '[]'::jsonb
            ) AS json_data
    FROM c_orderline ol
             INNER JOIN m_product product ON product.m_product_id = ol.m_product_id
             INNER JOIN c_uom ouom ON ouom.c_uom_id = ol.c_uom_id
    WHERE ol.c_order_id = p_order_id
    GROUP BY ol.c_order_id
),
     dropship_info AS (
         SELECT
             dropShipBPartner.c_bpartner_id,
             JSONB_BUILD_OBJECT(
                     'partnerID', dropShipBPartner.c_bpartner_id,
                     'partnerValue', dropShipBPartner.value,
                     'partnerName', dropShipBPartner.name,
                     'address1', dropShipLocation.address1,
                     'address2', dropShipLocation.address2,
                     'address3', dropShipLocation.address3,
                     'address4', dropShipLocation.address4,
                     'postal', dropShipLocation.postal,
                     'city', dropShipLocation.city,
                     'country', country.countrycode
                 ) AS json_data
         FROM c_bpartner dropShipBPartner
                  INNER JOIN c_bpartner_location dropShipBPartnerLocation
                             ON dropShipBPartner.c_bpartner_id = dropShipBPartnerLocation.c_bpartner_id
                  LEFT JOIN c_location dropShipLocation
                            ON dropShipBPartnerLocation.c_location_id = dropShipLocation.c_location_id
                  LEFT JOIN c_country country
                            ON dropShipLocation.c_country_id = country.c_country_id
         WHERE dropShipBPartner.c_bpartner_id = (
             SELECT dropship_bpartner_id
             FROM c_order
             WHERE c_order_id = p_order_id
         )
     )
SELECT
    org.value,
    porder.poreference,
    porder.c_order_id,
    c.iso_code,
    partner.value,
    partner.name,
    dropship.json_data,
    COALESCE(lines.json_data, '[]'::jsonb)
FROM c_order porder
         INNER JOIN ad_org org ON porder.ad_org_id = org.ad_org_id
         INNER JOIN c_bpartner partner ON porder.c_bpartner_id = partner.c_bpartner_id
         INNER JOIN c_currency c ON c.c_currency_id = porder.c_currency_id
         LEFT JOIN order_lines lines ON lines.c_order_id = porder.c_order_id
         LEFT JOIN dropship_info dropship ON dropship.c_bpartner_id = porder.dropship_bpartner_id
WHERE porder.c_order_id = p_order_id
  AND porder.issotrx = 'N';
$$
;
