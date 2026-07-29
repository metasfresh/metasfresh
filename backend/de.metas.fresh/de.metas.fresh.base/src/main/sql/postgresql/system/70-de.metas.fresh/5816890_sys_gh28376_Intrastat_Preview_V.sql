-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/views/Intrastat_Preview_V.sql
--
-- Creates de_metas_endcustomer_fresh_reports.Intrastat_Preview_V — WebUI grid twin of
-- report.Intrastat_Export. Same SELECT + WHERE as the export function, but numeric columns
-- stay `numeric` (no TO_CHAR) so grid sort / filter / summing work naturally.

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
