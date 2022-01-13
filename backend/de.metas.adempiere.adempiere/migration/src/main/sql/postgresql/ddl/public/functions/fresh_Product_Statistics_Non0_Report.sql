DROP FUNCTION IF EXISTS report.fresh_product_statistics_non0_report
(
    IN C_Period_ID               numeric,
    IN issotrx                   character varying,
    IN C_BPartner_ID             numeric,
    IN C_BP_Group_ID             numeric,
    IN C_Activity_ID             numeric,
    IN M_Product_ID              numeric,
    IN M_Product_Category_ID     numeric,
    IN M_AttributeSetInstance_ID numeric,
    IN AD_Org_ID                 numeric,
    IN AD_Language               Character Varying(6)
)
;




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


SELECT *
FROM report.fresh_product_statistics_report x
         LEFT JOIN c_bpartner bp ON x.bp_value = bp.value and bp.ad_org_id = x.ad_org_id
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
