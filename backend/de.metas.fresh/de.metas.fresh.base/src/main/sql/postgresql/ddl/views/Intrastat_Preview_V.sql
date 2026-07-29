-- Preview view for the Intrastat window — WebUI grid twin of report.Intrastat_Export.
--
-- Mirrors the SELECT + WHERE of report.Intrastat_Export so grid rows equal the exported
-- payload for the same (year, period, IsSOTrx='Y') parameter set.
--
-- Differences vs. report.Intrastat_Export:
--   * numeric columns (NetMass / SupplementaryUnits / InvoiceValue / StatisticalValue) are kept
--     as `numeric`, NOT TO_CHAR-formatted — so WebUI sort / filter / summing work naturally.
--   * IntrastaNatureOfTransaction is exposed as a hardcoded '11' constant column (matches the
--     default the export process passes to the function). Turning this into a real filter is a
--     future extension.
--   * CountryOfOrigin uses COALESCE(deliveredFromCountry, OriginCountry, deliveryCountry) —
--     for IsSOTrx='Y' (Export) this equals deliveredFromCountry, matching the function's
--     Export branch verbatim.
--
-- CHANGE COORDINATION: any change to report.Intrastat_Export's WHERE clauses MUST be mirrored
-- here — otherwise the WebUI grid and the exported RTIC payload diverge.

DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.Intrastat_Preview_V;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.Intrastat_Preview_V AS
SELECT
    -- Synthetic PK for AD_Table (single integer key — metasfresh does not support composite PKs on views)
    ABS(('x' || SUBSTR(MD5(CONCAT_WS('#',
        CustomsTariff,
        deliveryCountry,
        deliveredFromCountry,
        OriginCountry,
        C_Year_ID::text,
        C_Period_ID::text,
        IsSOTrx
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

    -- Preview columns — same content as report.Intrastat_Export but kept as native numeric
    CustomsTariff                                                   AS CNCode,
    productName                                                     AS GoodsDescription,
    deliveryCountry                                                 AS CountryDestinationConsignment,
    COALESCE(deliveredFromCountry, OriginCountry, deliveryCountry)  AS CountryOfOrigin,
    '11'::varchar                                                   AS IntrastaNatureOfTransaction,
    weight                                                          AS NetMass,
    movementqty                                                     AS SupplementaryUnits,
    LineNetAmt                                                      AS InvoiceValue,
    LineNetAmt                                                      AS StatisticalValue,
    vataxid                                                         AS RecipientVATNo

FROM de_metas_endcustomer_fresh_reports.Intrastat_Report_V
WHERE IsPackagingMaterial = 'N'
  AND CustomsTariff IS NOT NULL
  AND Product_IsStocked = 'Y'
;
