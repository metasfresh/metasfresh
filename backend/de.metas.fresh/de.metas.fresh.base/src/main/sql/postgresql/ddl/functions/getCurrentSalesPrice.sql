DROP FUNCTION IF EXISTS report.getCurrentSalesPrice(
    timestamp with time zone,
    numeric,
    numeric
);

CREATE OR REPLACE FUNCTION report.getCurrentSalesPrice(
    p_Date                    timestamp with time zone,
    p_C_BPartner_Location_ID  numeric,
    p_M_Product_ID            numeric
)
    RETURNS TABLE
            (
                PriceStd       numeric,
                C_UOM_ID       numeric,
                C_Currency_ID  numeric,
                M_PriceList_ID numeric
            )
AS
$$
WITH
    bpl AS (
        SELECT bpl.C_BPartner_ID,
               loc.C_Country_ID
        FROM C_BPartner_Location bpl
                 JOIN C_Location loc ON loc.C_Location_ID = bpl.C_Location_ID
        WHERE bpl.C_BPartner_Location_ID = p_C_BPartner_Location_ID
    ),
    pricing_system AS (
        SELECT COALESCE(bp.M_PricingSystem_ID, bpg.M_PricingSystem_ID) AS M_PricingSystem_ID,
               bpl.C_Country_ID
        FROM bpl
                 JOIN C_BPartner bp  ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                 LEFT JOIN C_BP_Group bpg ON bpg.C_BP_Group_ID = bp.C_BP_Group_ID
    ),
    -- Pick the SO price list matching the pricing system; country-specific wins over generic
    pricelist_root AS (
        SELECT pl.M_PriceList_ID
        FROM M_PriceList pl, pricing_system ps
        WHERE pl.M_PricingSystem_ID = ps.M_PricingSystem_ID
          AND pl.IsSOPriceList = 'Y'
          AND pl.IsActive = 'Y'
          AND (pl.C_Country_ID = ps.C_Country_ID OR pl.C_Country_ID IS NULL)
        ORDER BY (pl.C_Country_ID IS NOT NULL) DESC,
                 pl.M_PriceList_ID
        LIMIT 1
    )
SELECT gpp.priceStd,
       gpp.c_uom_id,
       gpp.c_currency_id,
       r.M_PriceList_ID
FROM pricelist_root r
         CROSS JOIN LATERAL de_metas_endcustomer_fresh_reports.get_Product_Price(
        p_M_Product_ID,
        r.M_PriceList_ID,
        p_Date::date
                            ) gpp
$$
    LANGUAGE sql
    STABLE;