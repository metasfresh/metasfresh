-- Source DDL: backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/ddl/public/functions/fresh_Product_Statistics_Non0_Report.sql
-- Recreates report.fresh_product_statistics_non0_report(10 args) from the DDL source.
--
-- Also fixes a long-standing defect in the same body: the previous DDL read
-- `FROM report.fresh_product_statistics_report x` (no argument list), which
-- resolves to the empty backing TABLE rather than the SETOF-returning function
-- of the same name — silently returning 0 rows and ignoring every parameter
-- except C_BP_Group_ID. The parenthesised argument list below makes PostgreSQL
-- call the function and honour all 9 upstream filters.

DROP FUNCTION IF EXISTS report.fresh_product_statistics_non0_report(numeric, character varying, numeric, numeric, numeric, numeric, numeric, numeric, numeric, character varying);

CREATE OR REPLACE FUNCTION report.fresh_product_statistics_non0_report(
    IN p_C_Period_ID               numeric,
    IN p_issotrx                   character varying,
    IN p_C_BPartner_ID             numeric,
    IN p_C_BP_Group_ID             numeric,
    IN p_C_Activity_ID             numeric,
    IN p_M_Product_ID              numeric,
    IN p_M_Product_Category_ID     numeric,
    IN p_M_AttributeSetInstance_ID numeric,
    IN p_AD_Org_ID                 numeric,
    IN p_AD_Language               Character Varying(6)
)
    RETURNS SETOF report.fresh_product_statistics_report
AS
$BODY$


SELECT x.*
FROM report.fresh_product_statistics_report(
        p_C_Period_ID,
        p_issotrx,
        p_C_BPartner_ID,
        p_C_Activity_ID,
        p_M_Product_ID,
        p_M_Product_Category_ID,
        p_M_AttributeSetInstance_ID,
        p_AD_Org_ID,
        p_AD_Language
    ) x
         LEFT JOIN c_bpartner bp ON x.c_bpartner_id = bp.c_bpartner_id
WHERE (period1sum != 0
    OR period2sum != 0
    OR period3sum != 0
    OR period4sum != 0
    OR period5sum != 0
    OR period6sum != 0
    OR period7sum != 0
    OR period8sum != 0
    OR period9sum != 0
    OR period10sum != 0
    OR period11sum != 0
    OR period12sum != 0)
  AND (p_C_BP_Group_ID IS NULL OR bp.c_bp_group_id = p_C_BP_Group_ID);


$BODY$
    LANGUAGE sql VOLATILE
;
