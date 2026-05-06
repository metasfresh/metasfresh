-- gh#28336: Create Intrastat_Report_Detail_V — detail view for Intrastat window.
-- Shows individual invoice/shipment line combinations for intra-EU trade.
-- Provides FK columns for zoom-into: shipment, invoice, product, partner.
-- Users can identify and fix issues like zero invoice amounts, missing tariff codes, etc.

DROP VIEW IF EXISTS Intrastat_Report_Detail_V$new;

CREATE OR REPLACE VIEW Intrastat_Report_Detail_V$new AS
SELECT
    ABS(('x' || SUBSTR(MD5(CONCAT_WS('#',
        iol.M_InOutLine_ID::text,
        il.C_InvoiceLine_ID::text
    )), 1, 10))::bit(32)::int) AS Intrastat_Report_Detail_V_ID,
    io.AD_Client_ID,
    io.AD_Org_ID,
    'Y'::char(1) AS IsActive,
    now() AS Created,
    0 AS CreatedBy,
    now() AS Updated,
    0 AS UpdatedBy,
    io.IsSOTrx,
    io.M_InOut_ID,
    i.C_Invoice_ID,
    iol.M_Product_ID,
    io.C_BPartner_ID,
    per.C_Period_ID,
    per.C_Year_ID,
    i.DateInvoiced,
    cn.Value AS CommodityNumber,
    ct.Value AS CustomsTariff,
    COALESCE(wlc.CountryCode, org_country.CountryCode) AS DeliveredFromCountry,
    co.CountryCode AS DeliveryCountry,
    pco.CountryCode AS OriginCountry,
    COALESCE(
        CASE
            WHEN iol.QtyDeliveredCatch IS NOT NULL THEN iol.QtyDeliveredCatch
            WHEN uomConvert(iol.M_Product_ID, iol.C_UOM_ID,
                (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' AND isactive = 'Y' ORDER BY isdefault DESC LIMIT 1),
                iol.QtyEntered) IS NOT NULL
            THEN uomConvert(iol.M_Product_ID, iol.C_UOM_ID,
                (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' AND isactive = 'Y' ORDER BY isdefault DESC LIMIT 1),
                iol.QtyEntered)
            ELSE iol.QtyEntered
        END, 0) AS MovementQty,
    CASE
        WHEN uomConvert(iol.M_Product_ID, iol.C_UOM_ID,
            (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' AND isactive = 'Y' ORDER BY isdefault DESC LIMIT 1),
            iol.QtyEntered) IS NOT NULL
        THEN uom.UOMSymbol
        ELSE uom_iol.UOMSymbol
    END AS UOMSymbol,
    COALESCE(
        COALESCE(iol.QtyDeliveredCatch,
            uomConvert(iol.M_Product_ID, iol.C_UOM_ID,
                (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' AND isactive = 'Y' ORDER BY isdefault DESC LIMIT 1),
                iol.QtyEntered)),
        iol.QtyEntered * p.Weight
    ) AS Weight,
    il.LineNetAmt,
    c.CurSymbol,
    bp.VATaxID
FROM M_InOut io
    JOIN AD_Org o ON io.AD_Org_ID = o.AD_Org_ID
    JOIN AD_OrgInfo org_info ON io.AD_Org_ID = org_info.AD_Org_ID
    LEFT JOIN C_BPartner_Location org_bpl ON org_info.OrgBP_Location_ID = org_bpl.C_BPartner_Location_ID
    LEFT JOIN C_Location org_loc ON org_bpl.C_Location_ID = org_loc.C_Location_ID
    LEFT JOIN C_Country org_country ON org_loc.C_Country_ID = org_country.C_Country_ID
    LEFT JOIN M_Warehouse w ON w.M_Warehouse_ID = io.M_Warehouse_ID
    LEFT JOIN C_Location wl ON wl.C_Location_ID = w.C_Location_ID
    LEFT JOIN C_Country wlc ON wlc.C_Country_ID = wl.C_Country_ID
    JOIN M_InOutLine iol ON iol.M_InOut_ID = io.M_InOut_ID
    LEFT JOIN C_InvoiceLine il ON il.M_InOutLine_ID = iol.M_InOutLine_ID
    JOIN C_Invoice i ON i.C_Invoice_ID = il.C_Invoice_ID AND i.DocStatus IN ('CO', 'CL')
    JOIN M_Product p ON p.M_Product_ID = iol.M_Product_ID
    LEFT JOIN M_CommodityNumber cn ON cn.M_CommodityNumber_ID = p.M_CommodityNumber_ID
    LEFT JOIN C_Country pco ON pco.C_Country_ID = p.RawMaterialOrigin_ID
    JOIN C_BPartner bp ON bp.C_BPartner_ID = io.C_BPartner_ID
    JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = io.C_BPartner_Location_ID
    JOIN C_Location l ON l.C_Location_ID = bpl.C_Location_ID
    JOIN C_Country co ON co.C_Country_ID = l.C_Country_ID
    JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID
    LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = COALESCE(iol.Catch_UOM_ID,
        (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' AND isactive = 'Y' ORDER BY isdefault DESC LIMIT 1))
    LEFT OUTER JOIN C_UOM uom_iol ON uom_iol.C_UOM_ID = iol.C_UOM_ID
    JOIN C_Period per ON i.DateInvoiced >= per.StartDate AND i.DateInvoiced <= per.EndDate
    LEFT OUTER JOIN M_CustomsTariff ct ON ct.M_CustomsTariff_ID = p.M_CustomsTariff_ID
    JOIN C_CountryArea_Assign eu_partner
        ON eu_partner.C_Country_ID = co.C_Country_ID
        AND eu_partner.C_CountryArea_ID = (SELECT ca.C_CountryArea_ID FROM C_CountryArea ca WHERE ca.value = 'EU' AND ca.isactive = 'Y')
        AND eu_partner.isactive = 'Y'
        AND i.DateInvoiced >= eu_partner.ValidFrom
        AND (eu_partner.ValidTo IS NULL OR i.DateInvoiced <= eu_partner.ValidTo)
WHERE io.IsActive = 'Y'
    AND iol.IsPackagingMaterial = 'N'
    AND NOT (ct.M_CustomsTariff_ID IS NULL AND il.LineNetAmt = 0)
    AND COALESCE(wlc.CountryCode, org_country.CountryCode) IS NOT NULL
    AND co.CountryCode != COALESCE(wlc.CountryCode, org_country.CountryCode);

SELECT db_alter_view(
    'Intrastat_Report_Detail_V',
    (SELECT view_definition FROM information_schema.views WHERE lower(table_name) = lower('intrastat_report_detail_v$new'))
);

DROP VIEW IF EXISTS Intrastat_Report_Detail_V$new;
