-- Preview view for the Intrastat window — WebUI grid twin of report.Intrastat_Export.
--
-- Lives in the DEFAULT schema (like sibling AD-backed view Intrastat_Report_Detail_V), NOT in
-- de_metas_endcustomer_fresh_reports: the WebUI generates unqualified SQL against AD_Table-backed
-- views, and de_metas_endcustomer_fresh_reports is not on the app-server's search_path.
--
-- Aggregation granularity: PER-PRODUCT rows (M_Product_ID in GROUP BY + synthetic PK). This is
-- finer than report.Intrastat_Export's per-CN-code output — multiple products sharing a CN code
-- appear as multiple grid rows so the user can zoom into each product via M_Product_ID
-- (Table Direct). The AT RTIC CSV (AD_Process 585508, unchanged) aggregates further to CN-code
-- granularity via Intrastat_Report_V's own GROUP BY.
--
-- Semantic invariant: SUM(NetMass), SUM(SupplementaryUnits), SUM(InvoiceValue) grouped-back
-- by CN-code equals the AT RTIC CSV row for that CN code — the preview aggregation is a
-- REFINEMENT of the AT RTIC aggregation, not a different data source.
--
-- Inner SELECT mirrors Intrastat_Report_V's inner SELECT so filters (EU-only, partner-country
-- != org-country, DocStatus IN ('CO','CL')) stay in sync; Intrastat_Report_V itself is not
-- modified (it is a shared source view).

DROP VIEW IF EXISTS Intrastat_Preview_V;

CREATE OR REPLACE VIEW Intrastat_Preview_V AS
SELECT
    -- Synthetic PK — MD5 of the aggregation key
    ABS(('x' || SUBSTR(MD5(CONCAT_WS('#',
        CustomsTariff,
        deliveryCountry,
        deliveredFromCountry,
        OriginCountry,
        C_Year_ID::text,
        C_Period_ID::text,
        IsSOTrx,
        M_Product_ID::text,
        C_UOM_ID::text,
        C_Currency_ID::text,
        AD_Org_ID::text,
        vataxid
    )), 1, 10))::bit(32)::int) AS Intrastat_Preview_V_ID,

    -- Standard AD framework columns
    1000000::numeric(10,0)                                          AS AD_Client_ID,
    AD_Org_ID,
    'Y'::char(1)                                                    AS IsActive,
    now()                                                           AS Created,
    0                                                               AS CreatedBy,
    now()                                                           AS Updated,
    0                                                               AS UpdatedBy,

    -- Filter / context columns (surfaced as grid filters)
    IsSOTrx,
    C_Year_ID,
    C_Period_ID,

    -- Preview columns
    CustomsTariff                                                   AS CNCode,
    M_Product_ID,
    deliveryCountry                                                 AS CountryDestinationConsignment,
    COALESCE(deliveredFromCountry, OriginCountry, deliveryCountry)  AS CountryOfOrigin,
    '11'::varchar                                                   AS IntrastaNatureOfTransaction,
    SUM(weight)                                                     AS NetMass,
    SUM(movementqty)                                                AS SupplementaryUnits,
    C_UOM_ID,
    SUM(linenetamt)                                                 AS InvoiceValue,
    SUM(linenetamt)                                                 AS StatisticalValue,
    C_Currency_ID,
    vataxid                                                         AS RecipientVATNo

FROM (
    -- Mirror of Intrastat_Report_V's inner SELECT + additional ID columns (M_Product_ID,
    -- C_UOM_ID, C_Currency_ID) needed for AD_Table Table-Direct references.
    SELECT
        p.M_Product_ID,
        iol.C_UOM_ID,
        i.C_Currency_ID,
        COALESCE(wlc.countrycode, org_country.countrycode)          AS deliveredFromCountry,
        co.countrycode                                              AS deliveryCountry,
        pco.countrycode                                             AS OriginCountry,
        COALESCE((CASE
            WHEN qtydeliveredcatch IS NOT NULL
                THEN qtydeliveredcatch
            WHEN uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' AND isactive = 'Y' ORDER BY isdefault DESC LIMIT 1), iol.qtyentered) IS NOT NULL
                THEN uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' AND isactive = 'Y' ORDER BY isdefault DESC LIMIT 1), iol.qtyentered)
            ELSE iol.qtyentered
        END), 0)                                                    AS movementqty,
        il.linenetamt                                               AS linenetamt,
        C_Period_ID,
        io.AD_Org_ID,
        ct.value                                                    AS CustomsTariff,
        -- Per-line weight: catch weight first, then UOM conversion to KG, then product weight fallback
        COALESCE(
            COALESCE(iol.qtydeliveredcatch,
                     uomConvert(iol.M_Product_ID, iol.C_UOM_ID,
                                (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' AND isactive = 'Y' ORDER BY isdefault DESC LIMIT 1),
                                iol.qtyentered)),
            iol.qtyentered * p.weight
        )                                                           AS weight,
        bp.vataxid,
        per.c_year_id,
        io.issotrx,
        iol.ispackagingmaterial,
        p.IsStocked                                                 AS Product_IsStocked
    FROM M_InOut io
        JOIN AD_OrgInfo org_info ON io.AD_Org_ID = org_info.AD_Org_ID
        LEFT JOIN C_BPartner_Location org_bpl ON org_info.OrgBP_Location_ID = org_bpl.C_BPartner_Location_ID
        LEFT JOIN C_Location org_loc ON org_bpl.C_Location_ID = org_loc.C_Location_ID
        LEFT JOIN C_Country org_country ON org_loc.C_Country_ID = org_country.C_Country_ID
        LEFT JOIN M_Warehouse w ON w.M_Warehouse_ID = io.M_Warehouse_ID
        LEFT JOIN C_Location wl ON wl.C_Location_ID = w.C_Location_ID
        LEFT JOIN C_Country wlc ON wlc.C_Country_ID = wl.C_Country_ID
        JOIN M_InOutLine iol ON iol.M_InOut_ID = io.M_InOut_ID
        LEFT JOIN C_InvoiceLine il ON il.M_InOutLine_ID = iol.M_InOutLine_ID
        JOIN C_Invoice i ON i.C_Invoice_ID = il.C_Invoice_ID AND i.DocStatus IN ('CO','CL')
        JOIN M_Product p ON p.M_Product_ID = iol.M_Product_ID
        LEFT JOIN C_Country pco ON pco.C_Country_ID = p.rawmaterialorigin_id
        JOIN C_BPartner bp ON bp.C_BPartner_ID = io.C_BPartner_ID
        JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = io.C_BPartner_Location_ID
        JOIN C_Location l ON l.C_Location_ID = bpl.C_Location_ID
        JOIN C_Country co ON co.C_Country_ID = l.C_Country_ID
        JOIN C_Period per ON i.DateInvoiced >= per.StartDate AND i.DateInvoiced <= per.EndDate
        LEFT JOIN M_CustomsTariff ct ON ct.M_CustomsTariff_ID = p.M_CustomsTariff_ID
        -- Only intra-EU trade: partner country must be EU member at time of invoice
        JOIN C_CountryArea_Assign eu_partner
            ON eu_partner.C_Country_ID = co.C_Country_ID
           AND eu_partner.C_CountryArea_ID = (SELECT C_CountryArea_ID FROM C_CountryArea WHERE value = 'EU' AND isactive = 'Y')
           AND eu_partner.isactive = 'Y'
           AND i.DateInvoiced >= eu_partner.validfrom
           AND (eu_partner.validto IS NULL OR i.DateInvoiced <= eu_partner.validto)
    WHERE io.isactive = 'Y'
      AND COALESCE(wlc.CountryCode, org_country.CountryCode) IS NOT NULL
      AND co.CountryCode != COALESCE(wlc.CountryCode, org_country.CountryCode)
) v
WHERE IsPackagingMaterial = 'N'
  AND CustomsTariff IS NOT NULL
  AND Product_IsStocked = 'Y'
GROUP BY
    M_Product_ID,
    C_UOM_ID,
    C_Currency_ID,
    deliveredFromCountry,
    deliveryCountry,
    OriginCountry,
    C_Period_ID,
    AD_Org_ID,
    CustomsTariff,
    vataxid,
    c_year_id,
    IsSOTrx
;
