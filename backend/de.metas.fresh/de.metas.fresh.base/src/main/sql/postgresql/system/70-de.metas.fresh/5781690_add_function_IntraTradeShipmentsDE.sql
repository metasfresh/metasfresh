
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.IntraTradeShipmentsDE(numeric,
                                                                                 numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.IntraTradeShipmentsDE(IN p_C_Period_ID numeric,
                                                                                    IN p_AD_Org_ID   numeric)
    RETURNS TABLE
            (
                "Verkehrsrichtung"          char(1),
                "Art des Geschäfts"         numeric(2),
                "Warennumer CN8"            varchar,
                "Produkt Beschreibung"      varchar,
                "Herkunft"                  char(2),
                "Land Partner"              char(2),
                "Bundesland Code"           char(2),
                "Netto Gewicht in Kg"       numeric,
                "Rechnung Betrag in EUR"    numeric,
                "Statistischer Wert in EUR" numeric,
                "Partner VAT ID"            varchar
            )
AS
$$
SELECT flow,
       typeOfTransaction,
       cn8,
       productDescription,
       OriginCountry,
       partnerCountry,
       regionCode,
       sum(weightInKg),
       sum(invoicenetamt),
       sum(invoicenetamt),
       partnervatid
FROM (SELECT CASE
                 WHEN io.issotrx = 'Y' THEN 'V' -- Versand/Dispatch
                                       ELSE 'E' --Eingang/Arrival
             END                                                        AS flow,
             CASE
                 WHEN io.issotrx = 'Y' AND dt.docbasetype = 'MMR' THEN 21 -- return
                 WHEN io.issotrx = 'N' AND dt.docbasetype = 'MMS' THEN 21 -- return
                                                                  ELSE 11 -- standard sales/purchase
             END                                                        AS typeOfTransaction,
             cn.value                                                   AS cn8,
             COALESCE(cn.name, p.name)                                  AS productDescription,
             COALESCE(countryOfOrigin.code, pco.countrycode, co.countrycode) AS OriginCountry,
             co.countrycode                                             AS partnerCountry,
             r.IntrastatCode                                            AS regionCode,
             COALESCE((COALESCE(uomConvert(iol.M_Product_ID,
                                           iol.catch_uom_id,
                                           uom_kg.c_uom_id,
                                           iol.qtydeliveredcatch),
                                uomConvert(iol.M_Product_ID,
                                           iol.C_UOM_ID,
                                           uom_kg.c_uom_id,
                                           iol.qtyentered))),
                      iol.qtyentered * p.weight)                        AS weightInKg, --p.weight currently is always KG
             CASE
                 WHEN
                     i.c_currency_id != cur_eur.c_currency_id
                     THEN currencyconvert(il.linenetamt, i.c_currency_id, cur_eur.c_currency_id, i.dateinvoiced, NULL, i.ad_client_id, i.ad_org_id)
                     ELSE il.linenetamt
             END                                                        AS invoicenetamt,
             CASE
                 WHEN io.issotrx = 'Y' THEN bp.vataxid
                                       ELSE ''
             END                                                        AS partnervatid
      FROM M_InOut io
               JOIN m_warehouse w ON w.m_warehouse_id = io.m_warehouse_id
               JOIN c_location wl ON wl.c_location_id = w.c_location_id -- not audit-proof
               JOIN C_country wlc ON wlc.c_country_id = wl.c_country_id
               LEFT JOIN c_doctype dt ON dt.c_doctype_id = io.c_doctype_id
               JOIN m_inoutline iol ON iol.m_inout_id = io.m_inout_id AND iol.ispackagingmaterial = 'N'
               LEFT JOIN c_invoiceline il ON il.m_inoutline_id = iol.m_inoutline_id
               JOIN C_invoice i ON i.C_invoice_id = il.C_invoice_id AND i.DocStatus IN ('CO', 'CL')
               JOIN m_product p ON p.m_product_id = iol.m_product_id
               LEFT JOIN m_commoditynumber cn ON cn.m_commoditynumber_id = p.m_commoditynumber_id
               LEFT JOIN C_country pco ON pco.c_country_id = p.rawmaterialorigin_id
               JOIN c_bpartner bp ON bp.c_bpartner_id = io.c_bpartner_id
               JOIN c_location l ON l.c_location_id = io.c_bpartner_location_value_id -- audit-proof compared to bpl.c_location_id
               LEFT JOIN c_region r ON r.c_region_id = l.c_region_id
               JOIN C_country co ON co.c_country_id = l.c_country_id
               JOIN c_currency c ON i.c_currency_id = c.C_Currency_id
               LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = COALESCE(iol.catch_uom_id, (SELECT C_UOM_ID
                                                                                       FROM C_UOM
                                                                                       WHERE x12de355 = 'KGM'
                                                                                         AND isactive = 'Y'
                                                                                       ORDER BY isdefault DESC
                                                                                       LIMIT 1)) -- fallback to KG
               LEFT OUTER JOIN C_UOM uom_iol ON uom_iol.C_UOM_ID = iol.C_UOM_ID

               JOIN C_Period per ON io.movementdate >= per.startdate AND io.movementdate <= per.enddate

               LEFT OUTER JOIN M_CustomsTariff ct ON ct.M_CustomsTariff_ID = p.M_CustomsTariff_ID
               LEFT JOIN LATERAL (
          SELECT DISTINCT STRING_AGG(ai.value, ', ') AS code --aggregation is not allowed, but it should be visible (invoice amounts split is not supported atm)
          FROM m_attributesetinstance asi
                   JOIN M_AttributeInstance ai ON ai.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID
                   JOIN M_Attribute a ON ai.m_attribute_id = a.m_attribute_id AND a.isActive = 'Y' AND a.value = '1000001' -- Herkunft
          WHERE iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
          ) countryOfOrigin ON TRUE
               LEFT JOIN LATERAL (
          (SELECT C_UOM_ID
           FROM C_UOM
           WHERE x12de355 = 'KGM'
             AND isactive = 'Y'
           ORDER BY isdefault DESC
           LIMIT 1)
          ) uom_kg ON TRUE
               LEFT JOIN LATERAL (
          (SELECT c_currency_id
           FROM c_currency
           WHERE iso_code = 'EUR')
          ) cur_eur ON TRUE
      WHERE per.C_Period_ID = p_C_Period_ID
        AND io.AD_Org_ID = p_AD_Org_ID
        AND io.isactive = 'Y'
        AND io.DocStatus IN ('CO', 'CL')
        AND wlc.countrycode = 'DE'
        AND co.countrycode != 'DE'
        AND iseucountry(co.c_country_id) = 'Y') AS v
GROUP BY flow,
         typeOfTransaction,
         cn8,
         productDescription,
         OriginCountry,
         partnerCountry,
         regionCode,
         partnervatid
ORDER BY regionCode, flow, cn8, productDescription, partnerCountry;
$$
    LANGUAGE sql
    STABLE
;
