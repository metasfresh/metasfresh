-- Move Intrastat_Preview_V from de_metas_endcustomer_fresh_reports schema into the default schema.
--
-- Root cause: migration 5816890 created the view in de_metas_endcustomer_fresh_reports, but the
-- WebUI generates unqualified SQL (FROM Intrastat_Preview_V master) against AD_Table-backed views.
-- de_metas_endcustomer_fresh_reports is not on the app-server's search_path, so opening the
-- Intrastat window fails with "relation intrastat_preview_v does not exist".
--
-- Every other AD_Table-backed view in de.metas.fresh.base (Intrastat_Report_Detail_V,
-- C_Order_M_InOut_C_Invoice_Overview_V, ...) lives in the default schema; only views consumed
-- purely by SQL functions (which fully-qualify their references) belong in
-- de_metas_endcustomer_fresh_reports.
--
-- Fix: drop the misplaced view and recreate it (same SELECT) in the default schema.
-- Source of truth: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/views/Intrastat_Preview_V.sql

DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.Intrastat_Preview_V;
DROP VIEW IF EXISTS Intrastat_Preview_V;

CREATE OR REPLACE VIEW Intrastat_Preview_V AS
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
