DROP VIEW IF EXISTS fresh_statistics_kg_week_view
;

CREATE OR REPLACE VIEW fresh_statistics_kg_week_view
AS
SELECT fa.M_Product_ID,
       p.M_Product_Category_ID,
       Y.fiscalyear,
       y.C_Year_id,
       fa.dateacct,
       fa.AmtAcct,
       fa.qty,
       fa.C_Activity_ID,
       fa.ad_org_id,
       fa.iso_code,
       il.M_AttributeSetInstance_ID AS il_M_AttributeSetInstance_ID,
       fa.C_UOM_ID,
       fa.uomkg,
       fa.convQty,
       i.IsSOtrx,
       pc.value AS pc_value,
       p.Name AS P_name,
       p.value AS P_value
FROM C_Year Y
         LEFT OUTER JOIN
     (
         SELECT *
         FROM (
                  SELECT fa.*,
                         CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct,
                         uomconvert(fa.M_Product_ID, fa.C_UOM_ID, (SELECT C_UOM_ID AS uom_conv
                                                                   FROM C_UOM
                                                                   WHERE x12de355 = 'KGM'
                                                                     AND IsActive = 'Y'
                         ), qty
                             )                                                                             AS convQty,
                         (SELECT C_UOM_ID AS uom_conv
                          FROM C_UOM
                          WHERE x12de355 = 'KGM'
                            AND IsActive = 'Y'
                         )                                                                                 AS uomkg,
                         C.iso_code
                  FROM Fact_Acct fa
                           JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
                           INNER JOIN AD_Org o ON fa.ad_org_id = o.ad_org_id
                           INNER JOIN AD_ClientInfo ci ON o.AD_Client_ID = ci.ad_client_id
                           LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID
                           LEFT OUTER JOIN C_Currency C ON acs.C_Currency_ID = C.C_Currency_ID
                  WHERE AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'
                                                  )
                  )
                    AND fa.isActive = 'Y'
              ) fa
     ) fa ON TRUE
         INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
         INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
    /* Please note: This is an important implicit filter. Inner Joining the Product
     * filters Fact Acct records for e.g. Taxes
     */
         INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
         INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
WHERE fa.AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'
                                   )
)
  -- Akontozahlung invoices are not included. See FRESH_609
  AND i.C_DocType_ID NOT IN (SELECT C_DocType_ID
                             FROM C_DocType
                             WHERE docbasetype = 'API'
                               AND docsubtype = 'DP'
)
  -- It was a requirement to not have HU Packing material within the sums of the Statistics reports
  AND p.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
;

DROP TABLE IF EXISTS  fresh_statistics_kg_week_MV;

CREATE TABLE fresh_statistics_kg_week_mv
(
    m_product_id                 numeric(10),
    m_product_category_id        numeric(10),
    fiscalyear                   varchar(20),
    c_year_id                    numeric(10),
    dateacct                     timestamp,
    amtacct                      numeric,
    qty                          numeric,
    c_activity_id                numeric(10),
    ad_org_id                    numeric(10),
    iso_code                     char(3),
    il_m_attributesetinstance_id numeric(10),
    c_uom_id                     numeric(10),
    uomkg                        numeric(10),
    convqty                      numeric,
    issotrx                      char,
    pc_value                     varchar(40),
    pc_name                      varchar(60),
    p_name                       varchar(255),
    p_value                      varchar(40)
)
;
-- indices that shall improve ordering and filtering
CREATE INDEX fresh_statistics_kg_week_year_Index ON fresh_statistics_kg_week_MV (C_Year_ID, issotrx);
CREATE INDEX fresh_statistics_kg_week_p_name_pc_name ON fresh_statistics_kg_week_MV (pc_value, P_name, C_Year_ID, issotrx);



----------- recreate function fresh_statistics_kg_week -------------

CREATE OR REPLACE FUNCTION report.fresh_statistics_kg_week
	(
		IN C_Year_ID numeric, 
		IN week integer,
		IN issotrx character varying,
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN convert_to_kg character varying,
		IN ad_org_id numeric,
		IN AD_Language Character Varying (6)
	) 
  RETURNS SETOF report.fresh_statistics_kg_week AS
$$
SELECT
	pc_value, 
	P_name,
	P_value,
	uom.UOMSymbol,
	Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
	Week1Sum, Week2Sum, Week3Sum, Week4Sum, Week5Sum, Week6Sum, Week7Sum, Week8Sum, Week9Sum, Week10Sum, Week11Sum, Week12Sum,
	TotalSum, TotalAmt,
	to_char( StartDate, 'DD.MM.YYYY' ) AS StartDate,
	to_char( EndDate, 'DD.MM.YYYY' ) AS EndDate,
	(SELECT name FROM C_Activity WHERE C_Activity_ID = $4) AS param_Activity,
	(SELECT COALESCE(pt.name, p.name) FROM M_Product p 
		LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $10 AND pt.isActive = 'Y'
		WHERE p.M_Product_ID = $5 
	) AS param_product,
	(SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $6) AS param_Product_Category,
	(SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $7) AS Param_Attributes,
	a.ad_org_id,
	a.iso_code
FROM
	(
		SELECT
			v.M_Product_ID,
			v.UOMConv_ID,
			(SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0)) AS EndDate,
			(SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11)) AS StartDate,
			
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0)) AS Col1,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -1)) AS Col2,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -2)) AS Col3,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -3)) AS Col4,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -4)) AS Col5,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -5)) AS Col6,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -6)) AS Col7,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -7)) AS Col8,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -8)) AS Col9,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -9)) AS Col10,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -10)) AS Col11,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11)) AS Col12,
			
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0))
				THEN v.QtyAcct ELSE 0 END ) AS Week1Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -1))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -1))
				THEN v.QtyAcct ELSE 0 END ) AS Week2Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -2))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -2))
				THEN v.QtyAcct ELSE 0 END ) AS Week3Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -3))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -3))
				THEN v.QtyAcct ELSE 0 END ) AS Week4Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -4))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -4))
				THEN v.QtyAcct ELSE 0 END ) AS Week5Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -5))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -5))
				THEN v.QtyAcct ELSE 0 END ) AS Week6Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -6))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -6))
				THEN v.QtyAcct ELSE 0 END ) AS Week7Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -7))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -7))
				THEN v.QtyAcct ELSE 0 END ) AS Week8Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -8))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -8))
				THEN v.QtyAcct ELSE 0 END ) AS Week9Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -9))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -9))
				THEN v.QtyAcct ELSE 0 END ) AS Week10Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -10))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -10))
				THEN v.QtyAcct ELSE 0 END ) AS Week11Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11))
				THEN v.QtyAcct ELSE 0 END ) AS Week12Sum,
				
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0))
				THEN v.QtyAcct ELSE 0 END ) AS TotalSum,
				
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0))
				THEN v.AmtAcct ELSE 0 END ) AS TotalAmt,
			v.ad_org_id,
			v.iso_code,
			v.pc_value,
            v.P_name,
            v.P_value

		 FROM (SELECT *,
                      CASE
                          WHEN $8 = 'Y' AND v.C_UOM_ID != uomkg AND convQty IS NOT NULL
                              THEN ABS(convQty) * SIGN(AmtAcct)
                              ELSE ABS(qty) * SIGN(AmtAcct)
                      END
                          AS QtyAcct,   --QtyAcct is calculated in KG where it's possible, only if convert_to_kg = 'Y'
                      CASE
                          WHEN $8 = 'Y' AND v.C_UOM_ID != uomkg AND convQty IS NOT NULL
                              THEN uomkg
                              ELSE v.C_UOM_ID
                      END AS UOMConv_ID --convert uom in KG where it's possible, only if convert_to_kg = 'Y'

               FROM fresh_statistics_kg_week_MV v) AS v
         WHERE v.C_Year_ID = $1
           AND v.IsSOtrx = $3
           AND (v.C_Activity_ID = $4 OR $4 IS NULL)
           AND (v.M_Product_ID = $5  OR $5 IS NULL)
           AND (v.M_Product_Category_ID = $6 OR $6 IS NULL)
           AND (
             -- If the given attribute set instance has values set...
             CASE
                 WHEN EXISTS(SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $7)
                     -- ... then apply following filter:
                     THEN (
                     -- Take lines where the attributes of the current InvoiceLine's asi are in the parameter asi and their Values Match
                         EXISTS(
                                 SELECT 0
                                 FROM report.fresh_Attributes a -- a = Attributes from invoice line, pa = Parameter Attributes
                                          INNER JOIN report.fresh_Attributes pa ON pa.M_AttributeSetInstance_ID = $7
                                     AND a.at_value = pa.at_value -- same attribute
                                     AND a.ai_value = pa.ai_value -- same value
                                 WHERE a.M_AttributeSetInstance_ID = v.il_M_AttributeSetInstance_ID
                             )
                         -- Dismiss lines where the Attributes in the Parameter are not in the InvoiceLine's asi
                         AND NOT EXISTS(
                             SELECT 0
                             FROM report.fresh_Attributes pa
                                      LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value
                                 AND a.M_AttributeSetInstance_ID = v.il_M_AttributeSetInstance_ID
                             WHERE pa.M_AttributeSetInstance_ID = $7
                               AND a.M_AttributeSetInstance_ID IS NULL
                         )
                     )
                 -- ... else deactivate the filter
                     ELSE TRUE
             END
             )
           AND v.ad_org_id = $9
         GROUP BY v.M_Product_ID,
                  v.pc_value,
                  v.p_value,
                  v.p_name,
                  v.UOMConv_ID,
                  v.AD_Org_ID,
                  v.fiscalyear,
                  v.iso_code

	) a
	INNER JOIN C_UOM uom ON a.UOMConv_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	
ORDER BY 
	pc_value, P_name
	
$$
LANGUAGE sql STABLE;

