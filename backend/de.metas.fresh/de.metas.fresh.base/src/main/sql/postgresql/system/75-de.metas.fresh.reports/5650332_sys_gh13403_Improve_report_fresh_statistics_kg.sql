DROP FUNCTION IF EXISTS report.fresh_statistics_kg(
    IN p_C_Period_ID               numeric,
    IN p_issotrx                   character varying,
    IN p_C_Activity_ID             numeric,
    IN p_M_Product_ID              numeric,
    IN p_M_Product_Category_ID     numeric,
    IN p_M_AttributeSetInstance_ID numeric,
    IN p_convert_to_kg             character varying,
    IN p_AD_Org_ID                 numeric,
    IN p_AD_Language               Character Varying(6))
;

DROP TABLE IF EXISTS report.fresh_statistics_kg;

CREATE OR REPLACE FUNCTION report.fresh_statistics_kg(
    IN p_C_Period_ID               numeric,
    IN p_issotrx                   character varying,
    IN p_C_Activity_ID             numeric,
    IN p_M_Product_ID              numeric,
    IN p_M_Product_Category_ID     numeric,
    IN p_M_AttributeSetInstance_ID numeric,
    IN p_convert_to_kg             character varying,
    IN p_AD_Org_ID                 numeric,
    IN p_AD_Language               Character Varying(6)
)
    RETURNS TABLE
            (
                pc_name                varchar,
                p_name                 varchar,
                p_value                varchar,
                uomsymbol              varchar,
                col1                   date,
                col2                   date,
                col3                   date,
                col4                   date,
                col5                   date,
                col6                   date,
                col7                   date,
                col8                   date,
                col9                   date,
                col10                  date,
                col11                  date,
                col12                  date,
                period1sum             numeric,
                period2sum             numeric,
                period3sum             numeric,
                period4sum             numeric,
                period5sum             numeric,
                period6sum             numeric,
                period7sum             numeric,
                period8sum             numeric,
                period9sum             numeric,
                period10sum            numeric,
                period11sum            numeric,
                period12sum            numeric,
                totalsum               numeric,
                totalamt               numeric,
                startdate              text,
                enddate                text,
                param_activity         varchar(60),
                param_product          varchar(255),
                param_product_category varchar(60),
                param_attributes       text,
                ad_org_id              numeric,
                iso_code               char(3)
            )
AS
$$
DECLARE
    v_Periods RECORD
    ;

BEGIN

    SELECT p1.C_Period_ID  AS p1_C_Period_ID,
           p1.StartDate    AS p1_StartDate,
           p1.EndDate      AS p1_EndDate,
           p2.StartDate    AS p2_StartDate,
           p3.StartDate    AS p3_StartDate,
           p4.StartDate    AS p4_StartDate,
           p5.StartDate    AS p5_StartDate,
           p6.StartDate    AS p6_StartDate,
           p7.StartDate    AS p7_StartDate,
           p8.StartDate    AS p8_StartDate,
           p9.StartDate    AS p9_StartDate,
           p10.StartDate   AS p10_StartDate,
           p11.StartDate   AS p11_StartDate,
           p12.StartDate   AS p12_StartDate,
           p2.C_Period_ID  AS p2_C_Period_ID,
           p3.C_Period_ID  AS p3_C_Period_ID,
           p4.C_Period_ID  AS p4_C_Period_ID,
           p5.C_Period_ID  AS p5_C_Period_ID,
           p6.C_Period_ID  AS p6_C_Period_ID,
           p7.C_Period_ID  AS p7_C_Period_ID,
           p8.C_Period_ID  AS p8_C_Period_ID,
           p9.C_Period_ID  AS p9_C_Period_ID,
           p10.C_Period_ID AS p10_C_Period_ID,
           p11.C_Period_ID AS p11_C_Period_ID,
           p12.C_Period_ID AS p12_C_Period_ID
    INTO v_Periods
    FROM C_Period p1
             LEFT OUTER JOIN C_Period p2 ON p2.C_Period_ID = report.fresh_Get_Predecessor_Period(p1.C_Period_ID) AND p2.isActive = 'Y'
             LEFT OUTER JOIN C_Period p3 ON p3.C_Period_ID = report.fresh_Get_Predecessor_Period(p2.C_Period_ID) AND p3.isActive = 'Y'
             LEFT OUTER JOIN C_Period p4 ON p4.C_Period_ID = report.fresh_Get_Predecessor_Period(p3.C_Period_ID) AND p4.isActive = 'Y'
             LEFT OUTER JOIN C_Period p5 ON p5.C_Period_ID = report.fresh_Get_Predecessor_Period(p4.C_Period_ID) AND p5.isActive = 'Y'
             LEFT OUTER JOIN C_Period p6 ON p6.C_Period_ID = report.fresh_Get_Predecessor_Period(p5.C_Period_ID) AND p6.isActive = 'Y'
             LEFT OUTER JOIN C_Period p7 ON p7.C_Period_ID = report.fresh_Get_Predecessor_Period(p6.C_Period_ID) AND p7.isActive = 'Y'
             LEFT OUTER JOIN C_Period p8 ON p8.C_Period_ID = report.fresh_Get_Predecessor_Period(p7.C_Period_ID) AND p8.isActive = 'Y'
             LEFT OUTER JOIN C_Period p9 ON p9.C_Period_ID = report.fresh_Get_Predecessor_Period(p8.C_Period_ID) AND p9.isActive = 'Y'
             LEFT OUTER JOIN C_Period p10 ON p10.C_Period_ID = report.fresh_Get_Predecessor_Period(p9.C_Period_ID) AND p10.isActive = 'Y'
             LEFT OUTER JOIN C_Period p11 ON p11.C_Period_ID = report.fresh_Get_Predecessor_Period(p10.C_Period_ID) AND p11.isActive = 'Y'
             LEFT OUTER JOIN C_Period p12 ON p12.C_Period_ID = report.fresh_Get_Predecessor_Period(p11.C_Period_ID) AND p12.isActive = 'Y'
    WHERE p1.c_period_id = p_C_Period_ID;

    RETURN QUERY
        SELECT pc.Name                                                                                                                                          AS pc_name,
               COALESCE(pt.Name, p.Name)                                                                                                                        AS P_name,
               p.value                                                                                                                                          AS P_value,
               COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                                                                                                          AS UOMSymbol,
               a.Col1,
               a.Col2,
               a.Col3,
               a.Col4,
               a.Col5,
               a.Col6,
               a.Col7,
               a.Col8,
               a.Col9,
               a.Col10,
               a.Col11,
               a.Col12,
               a.Period1Sum,
               a.Period2Sum,
               a.Period3Sum,
               a.Period4Sum,
               a.Period5Sum,
               a.Period6Sum,
               a.Period7Sum,
               a.Period8Sum,
               a.Period9Sum,
               a.Period10Sum,
               a.Period11Sum,
               a.Period12Sum,
               a.TotalSum,
               a.TotalAmt,
               TO_CHAR(COALESCE(a.Col12, a.Col11, a.Col10, a.Col9, a.Col8, a.Col7, a.Col6, a.Col5, a.Col4, a.Col3, a.Col2, a.Col1), 'DD.MM.YYYY')               AS StartDate,
               TO_CHAR(a.p1_EndDate, 'DD.MM.YYYY')                                                                                                              AS EndDate,

               (SELECT name FROM C_Activity WHERE C_Activity_ID = p_C_Activity_ID AND isActive = 'Y')                                                           AS param_Activity,
               (SELECT COALESCE(pt.name, p.name)
                FROM M_Product p
                         LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language AND pt.isActive = 'Y'
                WHERE p.M_Product_ID = p_M_Product_ID
               )                                                                                                                                                AS param_product,
               (SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = p_M_Product_Category_ID AND isActive = 'Y')                                   AS param_Product_Category,
               (SELECT STRING_AGG(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID) AS Param_Attributes,
               a.org_id,
               a.iso_code

        FROM (
                 SELECT fa.M_Product_ID,
                        fa.UOMConv_ID,
                        v_Periods.p1_EndDate::Date,
                        v_Periods.p1_StartDate::Date                                                        AS Col1,
                        v_Periods.p2_StartDate::Date                                                        AS Col2,
                        v_Periods.p3_StartDate::Date                                                        AS Col3,
                        v_Periods.p4_StartDate::Date                                                        AS Col4,
                        v_Periods.p5_StartDate::Date                                                        AS Col5,
                        v_Periods.p6_StartDate::Date                                                        AS Col6,
                        v_Periods.p7_StartDate::Date                                                        AS Col7,
                        v_Periods.p8_StartDate::Date                                                        AS Col8,
                        v_Periods.p9_StartDate::Date                                                        AS Col9,
                        v_Periods.p10_StartDate::Date                                                       AS Col10,
                        v_Periods.p11_StartDate::Date                                                       AS Col11,
                        v_Periods.p12_StartDate::Date                                                       AS Col12,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p1_C_Period_ID THEN fa.QtySum ELSE 0 END)  AS Period1Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p2_C_Period_ID THEN fa.QtySum ELSE 0 END)  AS Period2Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p3_C_Period_ID THEN fa.QtySum ELSE 0 END)  AS Period3Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p4_C_Period_ID THEN fa.QtySum ELSE 0 END)  AS Period4Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p5_C_Period_ID THEN fa.QtySum ELSE 0 END)  AS Period5Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p6_C_Period_ID THEN fa.QtySum ELSE 0 END)  AS Period6Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p7_C_Period_ID THEN fa.QtySum ELSE 0 END)  AS Period7Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p8_C_Period_ID THEN fa.QtySum ELSE 0 END)  AS Period8Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p9_C_Period_ID THEN fa.QtySum ELSE 0 END)  AS Period9Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p10_C_Period_ID THEN fa.QtySum ELSE 0 END) AS Period10Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p11_C_Period_ID THEN fa.QtySum ELSE 0 END) AS Period11Sum,
                        SUM(CASE WHEN fa.C_Period_ID = v_Periods.p12_C_Period_ID THEN fa.QtySum ELSE 0 END) AS Period12Sum,
                        SUM(CASE
                                WHEN fa.C_Period_ID IN
                                     (v_Periods.p1_C_Period_ID, v_Periods.p2_C_Period_ID, v_Periods.p3_C_Period_ID, v_Periods.p4_C_Period_ID, v_Periods.p5_C_Period_ID, v_Periods.p6_C_Period_ID,
                                      v_Periods.p7_C_Period_ID, v_Periods.p8_C_Period_ID, v_Periods.p9_C_Period_ID, v_Periods.p10_C_Period_ID, v_Periods.p11_C_Period_ID, v_Periods.p12_C_Period_ID)
                                    THEN fa.QtySum
                                    ELSE 0
                            END
                            )                                                                               AS TotalSum,
                        SUM(CASE
                                WHEN fa.C_Period_ID IN
                                     (v_Periods.p1_C_Period_ID, v_Periods.p2_C_Period_ID, v_Periods.p3_C_Period_ID, v_Periods.p4_C_Period_ID, v_Periods.p5_C_Period_ID, v_Periods.p6_C_Period_ID,
                                      v_Periods.p7_C_Period_ID, v_Periods.p8_C_Period_ID, v_Periods.p9_C_Period_ID, v_Periods.p10_C_Period_ID, v_Periods.p11_C_Period_ID, v_Periods.p12_C_Period_ID)
                                    THEN fa.totalamt
                                    ELSE 0
                            END
                            )                                                                               AS TotalAmt,
                        fa.org_id,
                        fa.iso_code
                 FROM (
                          SELECT fa.C_Period_ID,
                                 fa.M_Product_ID,
                                 fa.org_id,
                                 fa.iso_code,
                                 fa.totalamt,
                                 CASE
                                     WHEN p_convert_to_kg = 'Y' AND fa.C_UOM_ID != kg_uom_id AND kgQty IS NOT NULL
                                         THEN kgQty
                                         ELSE fa.QtySum
                                 END
                                     AS QtySum,
                                 CASE
                                     WHEN p_convert_to_kg = 'Y' AND fa.C_UOM_ID != kg_uom_id AND kgQty IS NOT NULL
                                         THEN kg_uom_id
                                         ELSE fa.C_UOM_ID
                                 END AS UOMConv_ID --convert uom in KG where it's possible, only if convert_to_kg = 'Y'
                          FROM (
                                   SELECT fa.C_Period_ID,
                                          fa.c_uom_id,
                                          (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' and isactive='Y') as kg_uom_id,
                                          fa.M_Product_ID,
                                          fa.org_id,
                                          fa.iso_code,
                                          fa.QtySum,
                                          fa.totalamt,
                                          uomconvert(fa.M_Product_ID, fa.C_UOM_ID, (SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = 'KGM' and isactive='Y'), QtySum) AS kgQty

                                   FROM fresh_statistics_kg_MV AS fa
                                   WHERE TRUE
                                     AND v_Periods.p1_C_Period_ID = p_C_Period_ID

                                     AND i_IsSOtrx = p_issotrx
                                     AND (CASE WHEN p_C_Activity_ID IS NULL THEN TRUE ELSE fa.C_Activity_ID = p_C_Activity_ID END)
                                     AND (CASE WHEN p_M_Product_ID IS NULL THEN TRUE ELSE fa.M_Product_ID = p_M_Product_ID END)
                                     AND (CASE WHEN p_M_Product_Category_ID IS NULL THEN TRUE ELSE fa.M_Product_Category_ID = p_M_Product_Category_ID END
                                       -- It was a requirement to not have HU Packing material within the sums of the Statistics reports
                                       AND fa.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', il_AD_Client_ID, il_AD_Org_ID)
                                       )
                                     AND (
                                       -- If the given attribute set instance has values set...
                                       CASE
                                           WHEN EXISTS(SELECT ai_value FROM report.fresh_Attributes f WHERE f.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID)
                                               -- ... then apply following filter:
                                               THEN (
                                               -- Take lines where the attributes of the current InvoiceLine's asi are in the parameter asi and their Values Match
                                                   EXISTS(
                                                           SELECT 0
                                                           FROM report.fresh_Attributes a -- a = Attributes from invoice line, pa = Parameter Attributes
                                                                    INNER JOIN report.fresh_Attributes pa ON pa.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
                                                               AND a.at_value = pa.at_value -- same attribute
                                                               AND a.ai_value = pa.ai_value -- same value
                                                           WHERE a.M_AttributeSetInstance_ID = il_M_AttributeSetInstance_ID
                                                       )
                                                   -- Dismiss lines where the Attributes in the Parameter are not in the InvoiceLine's asi
                                                   AND NOT EXISTS(
                                                       SELECT 0
                                                       FROM report.fresh_Attributes pa
                                                                LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value
                                                           AND a.M_AttributeSetInstance_ID = il_M_AttributeSetInstance_ID
                                                       WHERE pa.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
                                                         AND a.M_AttributeSetInstance_ID IS NULL
                                                   )
                                               )
                                           -- ... else deactivate the filter
                                               ELSE TRUE
                                       END
                                       )
                                     AND fa.org_id = p_AD_Org_ID
                               ) AS fa
                      ) AS fa
                 WHERE TRUE
                 GROUP BY fa.M_Product_ID,
                          fa.UOMConv_ID,
                          v_Periods.p1_EndDate,
                          v_Periods.p1_StartDate, v_Periods.p2_StartDate, v_Periods.p3_StartDate, v_Periods.p4_StartDate, v_Periods.p5_StartDate, v_Periods.p6_StartDate,
                          v_Periods.p7_StartDate, v_Periods.p8_StartDate, v_Periods.p9_StartDate, v_Periods.p10_StartDate, v_Periods.p11_StartDate, v_Periods.p12_StartDate,
                          fa.org_id,
                          fa.iso_code
             ) a
                 INNER JOIN C_UOM uom ON a.UOMConv_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
                 LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $9 AND uomt.isActive = 'Y'
                 INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID
                 LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $9 AND pt.isActive = 'Y'
                 INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'

        ORDER BY pc.value, p.name;

END;
$$
    LANGUAGE plpgsql
;

select report.update_fresh_statistics_kg();