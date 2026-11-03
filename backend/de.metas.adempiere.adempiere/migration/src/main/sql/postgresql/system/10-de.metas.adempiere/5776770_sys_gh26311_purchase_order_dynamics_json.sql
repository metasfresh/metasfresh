SELECT db_drop_functions('*.purchase_order_dynamics_json')
;

CREATE OR REPLACE FUNCTION purchase_order_dynamics_json(p_order_id text)
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
SELECT org.value,
       sorder.poreference,
       sorder.c_order_id,
       c.iso_code::text,
       partner.value,
       partner.name,
       dropShipBPartners.json_data::jsonb,
       lines.json_data::jsonb
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
                    GROUP BY ol.c_order_id) lines ON lines.c_order_id = sorder.C_Order_ID
         LEFT JOIN (SELECT dropShipBPartner.c_bpartner_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
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
                                        ) ORDER BY dropShipBPartner.c_bpartner_id) AS json_data
                    FROM c_bpartner dropShipBPartner
                             INNER JOIN c_bpartner_location dropShipBPartnerLocation ON dropShipBPartner.c_bpartner_id = dropShipBPartnerLocation.c_bpartner_id
                             LEFT JOIN c_location dropShipLocation ON dropShipBPartnerLocation.c_location_id = dropShipLocation.c_location_id
                             LEFT JOIN c_country country ON dropShipLocation.c_country_id = country.c_country_id
                    GROUP BY dropShipBPartner.c_bpartner_id) dropShipBPartners ON dropShipBPartners.c_bpartner_id = sorder.dropship_bpartner_id
WHERE sorder.c_order_id = p_order_id::numeric
  AND sorder.issotrx = 'N';
$$
;
