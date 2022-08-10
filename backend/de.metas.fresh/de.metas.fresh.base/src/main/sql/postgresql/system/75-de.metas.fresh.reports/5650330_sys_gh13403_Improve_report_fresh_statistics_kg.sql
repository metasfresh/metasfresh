--qty_statistics_kg_week/        -> fresh_qty_statistics_report_kg_week -> fresh_statistics_kg_week

---fresh_qty_statistics_report_kg -> fresh_qty_statistics_report_kg      -> fresh_statistics_kg

CREATE OR REPLACE VIEW fresh_statistics_kg_view AS
SELECT fa.M_Product_ID,
       p1.isActive                  AS p1_isActive,
       p1.EndDate                   AS p1_EndDate,
       p1.StartDate                 AS p1_StartDate,
       p2.StartDate                 AS p2_StartDate,
       p3.StartDate                 AS p3_StartDate,
       p4.StartDate                 AS p4_StartDate,
       p5.StartDate                 AS p5_StartDate,
       p6.StartDate                 AS p6_StartDate,
       p7.StartDate                 AS p7_StartDate,
       p8.StartDate                 AS p8_StartDate,
       p9.StartDate                 AS p9_StartDate,
       p10.StartDate                AS p10_StartDate,
       p11.StartDate                AS p11_StartDate,
       p12.StartDate                AS p12_StartDate,
       fa.C_Period_ID               AS fa_C_Period_ID,
       p1.C_Period_ID               AS p1_C_Period_ID,
       p2.C_Period_ID               AS p2_C_Period_ID,
       p3.C_Period_ID               AS p3_C_Period_ID,
       p4.C_Period_ID               AS p4_C_Period_ID,
       p5.C_Period_ID               AS p5_C_Period_ID,
       p6.C_Period_ID               AS p6_C_Period_ID,
       p7.C_Period_ID               AS p7_C_Period_ID,
       p8.C_Period_ID               AS p8_C_Period_ID,
       p9.C_Period_ID               AS p9_C_Period_ID,
       p10.C_Period_ID              AS p10_C_Period_ID,
       p11.C_Period_ID              AS p11_C_Period_ID,
       p12.C_Period_ID              AS p12_C_Period_ID,
       fa.org_id,
       fa.iso_code,
       fa.C_UOM_ID,
       fa.C_Activity_ID,
       uomkg,
       convQty,
       AmtAcct,
       qty,
       il.M_AttributeSetInstance_ID AS il_M_AttributeSetInstance_ID,
       il.AD_Client_ID              AS il_AD_Client_ID,
       il.AD_Org_ID                 AS il_AD_Org_ID,
       p.M_Product_ID               AS p_M_Product_ID,
       p.M_Product_Category_ID      AS p_M_Product_Category_ID,
       i.IsSOtrx                    AS i_IsSOtrx

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

         LEFT OUTER JOIN
     (
         SELECT *
         FROM (
                  SELECT fa.*, fa.ad_org_id AS org_id, CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct, uomconvert(fa.M_Product_ID, fa.C_UOM_ID, (SELECT C_UOM_ID AS uom_conv FROM C_UOM WHERE x12de355 = 'KGM' AND IsActive = 'Y'), qty) AS convQty, (SELECT C_UOM_ID AS uom_conv FROM C_UOM WHERE x12de355 = 'KGM' AND IsActive = 'Y') AS uomkg, c.iso_code
                  FROM Fact_Acct fa
                           JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
                           INNER JOIN AD_Org o ON fa.ad_org_id = o.ad_org_id
                           INNER JOIN AD_ClientInfo ci ON o.AD_Client_ID = ci.ad_client_id
                           LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID
                           LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID = c.C_Currency_ID
                  WHERE AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
                    AND fa.isActive = 'Y'
              ) fa
     ) fa ON TRUE

         INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
         INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
    /* Please note: This is an important implicit filter. Inner Joining the Product
     * filters Fact Acct records for e.g. Taxes
     */
         INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
WHERE fa.AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))

  -- Akontozahlung invoices are not included. See FRESH_609
  AND i.C_DocType_ID NOT IN (SELECT C_DocType_ID FROM C_DocType WHERE docbasetype = 'API' AND docsubtype = 'DP');



  
CREATE TABLE fresh_statistics_kg_MV
AS
SELECT *
FROM fresh_statistics_kg_view;



-- indices that shall improve filtering
CREATE INDEX fresh_statistics_kg_period_Index ON fresh_statistics_kg_MV (p1_C_Period_ID, i_issotrx);
CREATE INDEX fresh_statistics_kg_product_id ON fresh_statistics_kg_MV (M_Product_ID);




truncate report.fresh_statistics_kg;


CREATE OR REPLACE FUNCTION report.fresh_statistics_kg
(
    IN C_Period_ID numeric,
    IN issotrx character varying,
    IN C_Activity_ID numeric,
    IN M_Product_ID numeric,
    IN M_Product_Category_ID numeric,
    IN M_AttributeSetInstance_ID numeric,
    IN convert_to_kg character varying,
    IN AD_Org_ID numeric,
    IN AD_Language Character Varying (6)
)
    RETURNS SETOF report.fresh_statistics_kg AS
$BODY$
SELECT
    pc.Name AS pc_name,
    COALESCE(pt.Name, p.Name) AS P_name,
    p.value AS P_value,
    COALESCE(uomt.UOMSymbol,uom.UOMSymbol) AS UOMSymbol,
    Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
    Period1Sum, Period2Sum, Period3Sum, Period4Sum, Period5Sum, Period6Sum, Period7Sum, Period8Sum, Period9Sum, Period10Sum, Period11Sum, Period12Sum,
    TotalSum, TotalAmt,
    to_char( COALESCE(Col12, Col11, Col10, Col9, Col8, Col7, Col6, Col5, Col4, Col3, Col2, Col1), 'DD.MM.YYYY' ) AS StartDate,
    to_char( p1_EndDate, 'DD.MM.YYYY' ) AS EndDate,

    (SELECT name FROM C_Activity WHERE C_Activity_ID = $3 AND isActive = 'Y') AS param_Activity,
    (SELECT COALESCE(pt.name, p.name) FROM M_Product p
                                               LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $9 AND pt.isActive = 'Y'
     WHERE p.M_Product_ID = $4
    ) AS param_product,
    (SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $5 AND isActive = 'Y') AS param_Product_Category,
    (SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $6) AS Param_Attributes,
    a.org_id,
    a.iso_code

FROM
    (
        SELECT
            fa.M_Product_ID,
            fa.UOMConv_ID,
            p1_EndDate::Date,
            p1_StartDate::Date AS Col1, p2_StartDate::Date AS Col2, p3_StartDate::Date AS Col3, p4_StartDate::Date AS Col4,
            p5_StartDate::Date AS Col5, p6_StartDate::Date AS Col6, p7_StartDate::Date AS Col7, p8_StartDate::Date AS Col8,
            p9_StartDate::Date AS Col9, p10_StartDate::Date AS Col10, p11_StartDate::Date AS Col11, p12_StartDate::Date AS Col12,
            SUM( CASE WHEN fa_C_Period_ID= p1_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period1Sum,
            SUM( CASE WHEN fa_C_Period_ID = p2_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period2Sum,
            SUM( CASE WHEN fa_C_Period_ID = p3_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period3Sum,
            SUM( CASE WHEN fa_C_Period_ID = p4_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period4Sum,
            SUM( CASE WHEN fa_C_Period_ID = p5_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period5Sum,
            SUM( CASE WHEN fa_C_Period_ID= p6_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period6Sum,
            SUM( CASE WHEN fa_C_Period_ID = p7_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period7Sum,
            SUM( CASE WHEN fa_C_Period_ID = p8_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period8Sum,
            SUM( CASE WHEN fa_C_Period_ID = p9_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period9Sum,
            SUM( CASE WHEN fa_C_Period_ID = p10_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period10Sum,
            SUM( CASE WHEN fa_C_Period_ID = p11_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period11Sum,
            SUM( CASE WHEN fa_C_Period_ID = p12_C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period12Sum,
            SUM( CASE WHEN fa_C_Period_ID IN
                           (p1_C_Period_ID, p2_C_Period_ID, p3_C_Period_ID, p4_C_Period_ID, p5_C_Period_ID, p6_C_Period_ID,
                            p7_C_Period_ID, p8_C_Period_ID, p9_C_Period_ID, p10_C_Period_ID, p11_C_Period_ID, p12_C_Period_ID)
                          THEN fa.QtyAcct ELSE 0 END
                ) AS TotalSum,
            SUM( CASE WHEN fa_C_Period_ID IN
                           (p1_C_Period_ID, p2_C_Period_ID, p3_C_Period_ID, p4_C_Period_ID, p5_C_Period_ID, p6_C_Period_ID,
                            p7_C_Period_ID, p8_C_Period_ID, p9_C_Period_ID, p10_C_Period_ID, p11_C_Period_ID, p12_C_Period_ID)
                          THEN fa.AmtAcct ELSE 0 END
                ) AS TotalAmt,
            fa.org_id,
            fa.iso_code
        FROM
             (
                select *,
                       CASE WHEN $7 = 'Y' AND fa.C_UOM_ID != uomkg AND convQty IS NOT NULL
                                THEN ABS(convQty) * SIGN(AmtAcct)
                                ELSE ABS(qty) * SIGN(AmtAcct) END
                                                     AS QtyAcct, --QtyAcct is calculated in KG where it's possible, only if convert_to_kg = 'Y'
                       CASE WHEN $7 = 'Y' AND fa.C_UOM_ID != uomkg AND convQty IS NOT NULL
                                THEN uomkg
                                ELSE fa.C_UOM_ID END AS UOMConv_ID --convert uom in KG where it's possible, only if convert_to_kg = 'Y'
                FROM
                    fresh_statistics_kg_MV fa
                 ) as fa



        WHERE TRUE
          AND p1_C_Period_ID = $1 --AND p1.isActive = 'Y'

          AND i_IsSOtrx = $2
          AND ( CASE WHEN $3 IS NULL THEN TRUE ELSE fa.C_Activity_ID = $3 END )
          AND ( CASE WHEN $4 IS NULL THEN TRUE ELSE p_M_Product_ID = $4 END )
          AND ( CASE WHEN $5 IS NULL THEN TRUE ELSE p_M_Product_Category_ID = $5 END
            -- It was a requirement to not have HU Packing material within the sums of the Statistics reports
            AND p_M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', il_AD_Client_ID, il_AD_Org_ID)
            )
          AND (
            -- If the given attribute set instance has values set...
            CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $6 )
                     -- ... then apply following filter:
                     THEN (
                    -- Take lines where the attributes of the current InvoiceLine's asi are in the parameter asi and their Values Match
                        EXISTS (
                                SELECT	0
                                FROM	report.fresh_Attributes a -- a = Attributes from invoice line, pa = Parameter Attributes
                                            INNER JOIN report.fresh_Attributes pa ON pa.M_AttributeSetInstance_ID = $6
                                    AND a.at_value = pa.at_value -- same attribute
                                    AND a.ai_value = pa.ai_value -- same value
                                WHERE	a.M_AttributeSetInstance_ID = il_M_AttributeSetInstance_ID
                            )
                        -- Dismiss lines where the Attributes in the Parameter are not in the InvoiceLine's asi
                        AND NOT EXISTS (
                            SELECT	0
                            FROM	report.fresh_Attributes pa
                                        LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value
                                AND a.M_AttributeSetInstance_ID = il_M_AttributeSetInstance_ID
                            WHERE	pa.M_AttributeSetInstance_ID = $6
                              AND a.M_AttributeSetInstance_ID IS null
                        )
                    )
                -- ... else deactivate the filter
                     ELSE TRUE END
            )
          AND fa.org_id = $8
        GROUP BY
            fa.M_Product_ID,
            fa.UOMConv_ID,
            p1_EndDate,
            p1_StartDate, p2_StartDate, p3_StartDate, p4_StartDate, p5_StartDate, p6_StartDate,
            p7_StartDate, p8_StartDate, p9_StartDate, p10_StartDate, p11_StartDate, p12_StartDate,
            fa.org_id,
            fa.iso_code
    ) a
        INNER JOIN C_UOM uom ON a.UOMConv_ID = uom.C_UOM_ID   AND uom.isActive = 'Y'
        LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $9 AND uomt.isActive = 'Y'
        INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID
        LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $9 AND pt.isActive = 'Y'
        INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'

ORDER BY
    pc.value, p.name

$BODY$
    LANGUAGE sql VOLATILE;
