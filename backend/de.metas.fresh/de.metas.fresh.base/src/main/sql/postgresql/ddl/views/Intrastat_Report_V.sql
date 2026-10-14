DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.Intrastat_Report_V
;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.Intrastat_Report_V AS
SELECT commoditynumber,
       productName,
       productDescription,
       deliveredFromCountry,
       deliveryCountry,
       OriginCountry,
       SUM(movementqty) AS movementqty,
       UOMSymbol,
       SUM(linenetamt)  AS linenetamt,
       cursymbol,
       C_Period_ID,
       Period,
       AD_Org_ID,
       OrgName,
       CustomsTariff,
       SUM(weight)      AS weight,
       vataxid,
       c_year_id,
       issotrx,
       IsPackagingMaterial,
       Product_IsStocked
FROM (SELECT cn.value                                           AS commoditynumber,
             p.Name                                             AS productName,
             p.description                                      AS productDescription,
             COALESCE(wlc.countrycode, org_country.countrycode) AS deliveredFromCountry,
             co.countrycode                                     AS deliveryCountry,
             pco.countrycode                                    AS OriginCountry,

             COALESCE((CASE
                           WHEN qtydeliveredcatch IS NOT NULL
                               THEN qtydeliveredcatch
                           WHEN uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (SELECT C_UOM_ID
                                                                            FROM C_UOM
                                                                            WHERE x12de355 = 'KGM'
                                                                              AND isactive = 'Y'
                                                                            ORDER BY isdefault DESC
                                                                            LIMIT 1), iol.qtyentered) IS NOT NULL
                               THEN uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (SELECT C_UOM_ID
                                                                                FROM C_UOM
                                                                                WHERE x12de355 = 'KGM'
                                                                                  AND isactive = 'Y'
                                                                                ORDER BY isdefault DESC
                                                                                LIMIT 1),
                                               iol.qtyentered) -- hard coded UOM for KG
                               ELSE iol.qtyentered
                       END), 0)                                 AS movementqty,

             (CASE
                  WHEN uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (SELECT C_UOM_ID
                                                                   FROM C_UOM
                                                                   WHERE x12de355 = 'KGM'
                                                                     AND isactive = 'Y'
                                                                   ORDER BY isdefault DESC
                                                                   LIMIT 1), iol.qtyentered) IS NOT NULL
                      THEN uom.UOMSymbol
                      ELSE uom_iol.UOMSymbol
              END)
                                                                AS UOMSymbol,
             il.linenetamt                                      AS linenetamt,
             c.cursymbol,
             C_Period_ID,
             per.name                                           AS Period,
             io.AD_Org_ID,
             o.Name                                             AS OrgName,
             ct.value                                           AS CustomsTariff,
             -- Per-line weight: catch weight first, then UOM conversion to KG, then product weight fallback
             COALESCE(
                     COALESCE(iol.qtydeliveredcatch,
                              uomConvert(iol.M_Product_ID, iol.C_UOM_ID,
                                         (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' AND isactive = 'Y' ORDER BY isdefault DESC LIMIT 1),
                                         iol.qtyentered)),
                     iol.qtyentered * p.weight
             )                                                  AS weight,
             bp.vataxid,
             per.c_year_id,
             io.issotrx,
             iol.ispackagingmaterial,
             p.IsStocked                                        AS Product_IsStocked
      FROM M_InOut io
               JOIN AD_Org o ON io.ad_org_id = o.ad_org_id
          -- Org's country via AD_OrgInfo -> OrgBP_Location -> C_Location -> C_Country
               JOIN AD_OrgInfo org_info ON io.ad_org_id = org_info.ad_org_id
               LEFT JOIN C_BPartner_Location org_bpl ON org_info.OrgBP_Location_ID = org_bpl.C_BPartner_Location_ID
               LEFT JOIN C_Location org_loc ON org_bpl.C_Location_ID = org_loc.C_Location_ID
               LEFT JOIN C_Country org_country ON org_loc.C_Country_ID = org_country.C_Country_ID
               LEFT JOIN m_warehouse w ON w.m_warehouse_id = io.m_warehouse_id
               LEFT JOIN c_location wl ON wl.c_location_id = w.c_location_id
               LEFT JOIN C_country wlc ON wlc.c_country_id = wl.c_country_id
               JOIN m_inoutline iol ON iol.m_inout_id = io.m_inout_id
               LEFT JOIN c_invoiceline il ON il.m_inoutline_id = iol.m_inoutline_id
               JOIN C_invoice i ON i.C_invoice_id = il.C_invoice_id AND i.DocStatus IN ('CO', 'CL')
               JOIN m_product p ON p.m_product_id = iol.m_product_id
               LEFT JOIN m_commoditynumber cn ON cn.m_commoditynumber_id = p.m_commoditynumber_id
               LEFT JOIN C_country pco ON pco.c_country_id = p.rawmaterialorigin_id
               JOIN c_bpartner bp ON bp.c_bpartner_id = io.c_bpartner_id
               JOIN c_bpartner_location bpl ON bpl.c_bpartner_location_id = io.c_bpartner_location_id
               JOIN c_location l ON l.c_location_id = bpl.c_location_id
               JOIN C_country co ON co.c_country_id = l.c_country_id
               JOIN c_currency c ON i.c_currency_id = c.C_Currency_id

               LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = COALESCE(iol.catch_uom_id, (SELECT C_UOM_ID
                                                                                       FROM C_UOM
                                                                                       WHERE x12de355 = 'KGM'
                                                                                         AND isactive = 'Y'
                                                                                       ORDER BY isdefault DESC
                                                                                       LIMIT 1)) -- fallback to KG
               LEFT OUTER JOIN C_UOM uom_iol ON uom_iol.C_UOM_ID = iol.C_UOM_ID

               JOIN C_Period per ON i.dateinvoiced >= per.startdate AND i.dateinvoiced <= per.enddate

               LEFT OUTER JOIN M_CustomsTariff ct ON ct.M_CustomsTariff_ID = p.M_CustomsTariff_ID

          -- Only intra-EU trade: partner country must be EU member at time of invoice
               JOIN C_CountryArea_Assign eu_partner
                    ON eu_partner.C_Country_ID = co.C_Country_ID
                        AND eu_partner.C_CountryArea_ID = (SELECT ca.C_CountryArea_ID FROM C_CountryArea ca WHERE ca.value = 'EU' AND ca.isactive = 'Y')
                        AND eu_partner.isactive = 'Y'
                        AND i.dateinvoiced >= eu_partner.validfrom
                        AND (eu_partner.validto IS NULL OR i.dateinvoiced <= eu_partner.validto)

      WHERE io.isactive = 'Y'
        -- Only international shipments: partner country differs from org/warehouse country.
        AND COALESCE(wlc.countrycode, org_country.countrycode) IS NOT NULL
        AND co.countrycode != COALESCE(wlc.countrycode, org_country.countrycode)) AS v
GROUP BY commoditynumber,
         productName,
         productDescription,
         deliveredFromCountry,
         deliveryCountry,
         OriginCountry,
         UOMSymbol,
         cursymbol,
         C_Period_ID,
         Period,
         AD_Org_ID,
         OrgName,
         CustomsTariff,
         vataxid,
         c_year_id,
         IsSOTrx,
         IsPackagingMaterial,
         Product_IsStocked
ORDER BY deliveryCountry, commoditynumber
;
