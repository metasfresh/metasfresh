DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.IntraTradeShipmentsDE(numeric,
                                                                                 numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.IntraTradeShipmentsDE(IN p_C_Period_ID numeric,
                                                                                    IN p_AD_Org_ID   numeric)
    RETURNS TABLE
            (
                "Verkehrsrichtung"          char(1),
                "Art des Geschäfts"         integer,
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
WITH period AS (SELECT startdate, enddate FROM C_Period p WHERE p.C_Period_ID = p_C_Period_ID),
     uom_kg AS (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' AND isactive = 'Y' ORDER BY isdefault DESC LIMIT 1),
     cur_eur AS (SELECT c_currency_id, stdprecision FROM c_currency WHERE iso_code = 'EUR')
SELECT flow,
       typeOfTransaction,
       cn8,
       productDescription,
       OriginCountry,
       partnerCountry,
       regionCode,
       SUM(weightInKg),
       SUM(invoicenetamt),
       SUM(invoicenetamt),
       partnervatid
FROM (SELECT CASE
                 WHEN io.issotrx = 'Y' THEN 'V' -- Versand/Dispatch
                                       ELSE 'E' -- Eingang/Arrival
             END                                                                    AS flow,
             CASE
                 WHEN io.issotrx = 'Y' AND dt.docbasetype = 'MMR' THEN 21 -- return
                 WHEN io.issotrx = 'N' AND dt.docbasetype = 'MMS' THEN 21 -- return
                                                                  ELSE 11 -- standard sales/purchase
             END                                                                    AS typeOfTransaction,
             ct.value                                                               AS cn8,
             COALESCE(ct.name, p.name)                                              AS productDescription,
             COALESCE(asi_countryOfOrigin.code, pco.countrycode, bp_co.countrycode) AS OriginCountry,
             bp_co.countrycode                                                      AS partnerCountry,
             wr.IntrastatCode                                                       AS regionCode,
             COALESCE(
                     uomConvert(iol.M_Product_ID, iol.catch_uom_id, uom_kg.c_uom_id, iol.qtydeliveredcatch),
                     uomConvert(iol.M_Product_ID, iol.C_UOM_ID, uom_kg.c_uom_id, iol.movementqty),
                     iol.qtyentered * p.weight
             )                                                                      AS weightInKg, --p.weight currently is always KG
             il_sum.invoicenetamt                                                   AS invoicenetamt,
             CASE
                 WHEN io.issotrx = 'Y' THEN COALESCE(bpl.vataxid, bp.vataxid)
                                       ELSE ''
             END                                                                    AS partnervatid
      FROM period per,
           uom_kg,
           cur_eur,
           M_InOut io
               JOIN m_warehouse w ON w.m_warehouse_id = io.m_warehouse_id
               JOIN c_location wl ON wl.c_location_id = w.c_location_id -- not audit-proof
               JOIN C_country wlc ON wlc.c_country_id = wl.c_country_id
               LEFT JOIN c_region wr ON wr.c_region_id = wl.c_region_id
               JOIN c_doctype dt ON dt.c_doctype_id = io.c_doctype_id
               JOIN m_inoutline iol ON iol.m_inout_id = io.m_inout_id AND iol.ispackagingmaterial = 'N'
               JOIN m_product p ON p.m_product_id = iol.m_product_id
               LEFT JOIN C_country pco ON pco.c_country_id = p.rawmaterialorigin_id
               JOIN c_bpartner bp ON bp.c_bpartner_id = io.c_bpartner_id
               JOIN c_bpartner_location bpl ON bpl.c_bpartner_location_id = io.c_bpartner_location_id
               JOIN c_location l ON l.c_location_id = io.c_bpartner_location_value_id -- audit-proof compared to bpl.c_location_id
               JOIN C_country bp_co ON bp_co.c_country_id = l.c_country_id
               LEFT JOIN M_CustomsTariff ct ON ct.M_CustomsTariff_ID = p.M_CustomsTariff_ID
               LEFT JOIN LATERAL (SELECT STRING_AGG(DISTINCT ai.value, ', ') AS code --aggregation is not allowed, but it should be visible (invoice amounts split is not supported atm)
                                  FROM m_attributesetinstance asi
                                           JOIN M_AttributeInstance ai ON ai.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID
                                           JOIN M_Attribute a ON ai.m_attribute_id = a.m_attribute_id AND a.value = '1000001' -- Herkunft
                                  WHERE iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
               ) asi_countryOfOrigin ON TRUE
               LEFT JOIN LATERAL ( SELECT ROUND(
                                                  SUM(
                                                          currencyconvert(
                                                                  CASE
                                                                      WHEN il.QtyInvoiced <> 0
                                                                          THEN il.LineNetAmt * (mi.Qty / il.QtyInvoiced)
                                                                          ELSE 0
                                                                  END,
                                                                  i.c_currency_id,
                                                                  cur_eur.c_currency_id,
                                                                  i.dateinvoiced,
                                                                  NULL,
                                                                  i.ad_client_id,
                                                                  i.ad_org_id
                                                          )),
                                                  cur_eur.stdprecision
                                          ) AS invoicenetamt
                                   FROM M_MatchInv mi
                                            JOIN C_InvoiceLine il ON il.C_InvoiceLine_ID = mi.C_InvoiceLine_ID AND il.IsActive = 'Y'
                                            JOIN C_Invoice i ON i.C_Invoice_ID = il.C_Invoice_ID AND i.DocStatus IN ('CO', 'CL') AND i.IsActive = 'Y'
                                   WHERE mi.IsActive = 'Y'
                                     AND mi.Type = 'M'
                                     AND mi.M_InOutLine_ID = iol.M_InOutLine_ID
               ) il_sum ON TRUE
      WHERE io.movementdate BETWEEN per.startdate AND per.enddate
        AND io.AD_Org_ID = p_AD_Org_ID
        AND io.isactive = 'Y'
        AND io.DocStatus IN ('CO', 'CL')
        AND wlc.countrycode = 'DE'
        AND bp_co.countrycode != 'DE'
        AND iseucountry(bp_co.c_country_id) = 'Y') AS v
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
