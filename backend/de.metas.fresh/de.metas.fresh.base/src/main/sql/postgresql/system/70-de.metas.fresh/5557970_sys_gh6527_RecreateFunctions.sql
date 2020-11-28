DROP FUNCTION IF EXISTS report.fresh_statistics ( 
	IN C_Period_ID numeric, IN issotrx character varying, IN C_BPartner_ID numeric, IN C_Activity_ID numeric,
	IN M_Product_ID numeric, IN M_Product_Category_ID numeric, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric
);
DROP FUNCTION IF EXISTS report.fresh_statistics ( 
	IN C_Period_ID numeric, IN issotrx character varying, IN C_BPartner_ID numeric, IN C_Activity_ID numeric,
	IN M_Product_ID numeric, IN M_Product_Category_ID numeric, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric, IN AD_Language Character Varying (6)
);



DROP TABLE IF EXISTS report.fresh_statistics;

/* ***************************************************************** */

CREATE TABLE report.fresh_statistics
(
  bp_name character varying,
  bp_value character varying,
  pc_name character varying,
  p_name character varying,
  p_value character varying,
  uomsymbol character varying,
  col1 date,
  col2 date,
  col3 date,
  col4 date,
  col5 date,
  col6 date,
  col7 date,
  col8 date,
  col9 date,
  col10 date,
  col11 date,
  col12 date,
  period1sum numeric,
  period2sum numeric,
  period3sum numeric,
  period4sum numeric,
  period5sum numeric,
  period6sum numeric,
  period7sum numeric,
  period8sum numeric,
  period9sum numeric,
  period10sum numeric,
  period11sum numeric,
  period12sum numeric,
  totalsum numeric,
  totalamt numeric,
  startdate text,
  enddate text,
  param_bp character varying(60),
  param_activity character varying(60),
  param_product character varying(255),
  param_product_category character varying(60),
  param_attributes character varying(255),
  ad_org_id numeric,
  iso_code char(3)
)
WITH (
  OIDS=FALSE
);

/* ***************************************************************** */

CREATE OR REPLACE FUNCTION report.fresh_statistics
	(
		IN C_Period_ID numeric, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric,
		IN AD_Language Character Varying (6) 
	) 
  RETURNS SETOF report.fresh_statistics AS
$BODY$
SELECT
	bp.Name AS bp_name,
	bp.value AS bp_Value,
	pc.Name AS pc_name, 
	COALESCE(pt.Name,p.Name) AS P_name,
	p.value AS P_value,
	COALESCE(uomt.UOMSymbol,uom.UOMSymbol) AS UOMSymbol,
	Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
	Period1Sum, Period2Sum, Period3Sum, Period4Sum, Period5Sum, Period6Sum, Period7Sum, Period8Sum, Period9Sum, Period10Sum, Period11Sum, Period12Sum,
	TotalSum, TotalAmt,
	to_char( COALESCE(Col12, Col11, Col10, Col9, Col8, Col7, Col6, Col5, Col4, Col3, Col2, Col1), 'DD.MM.YYYY' ) AS StartDate,
	to_char( EndDate, 'DD.MM.YYYY' ) AS EndDate,
	(SELECT name FROM C_BPartner WHERE C_BPartner_ID = $3 AND isActive = 'Y') AS param_bp,
	(SELECT name FROM C_Activity WHERE C_Activity_ID = $4 AND isActive = 'Y') AS param_Activity,
	(SELECT COALESCE(pt.name, p.name) FROM M_Product p 
		LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $9 AND pt.isActive = 'Y'
		WHERE p.M_Product_ID = $5
	) AS param_product,
	(SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $6 AND isActive = 'Y') AS param_Product_Category,
	(SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $7) AS Param_Attributes,
	a.ad_org_id,
	a.iso_code

FROM
	(
		SELECT
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			fa.C_UOM_ID, 
			p1.EndDate::Date,
			p1.StartDate::Date AS Col1, p2.StartDate::Date AS Col2, p3.StartDate::Date AS Col3, p4.StartDate::Date AS Col4, 
			p5.StartDate::Date AS Col5, p6.StartDate::Date AS Col6, p7.StartDate::Date AS Col7, p8.StartDate::Date AS Col8, 
			p9.StartDate::Date AS Col9, p10.StartDate::Date AS Col10, p11.StartDate::Date AS Col11, p12.StartDate::Date AS Col12,
			SUM( CASE WHEN fa.C_Period_ID = p1.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period1Sum,
			SUM( CASE WHEN fa.C_Period_ID = p2.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period2Sum,
			SUM( CASE WHEN fa.C_Period_ID = p3.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period3Sum,
			SUM( CASE WHEN fa.C_Period_ID = p4.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period4Sum,
			SUM( CASE WHEN fa.C_Period_ID = p5.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period5Sum,
			SUM( CASE WHEN fa.C_Period_ID = p6.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period6Sum,
			SUM( CASE WHEN fa.C_Period_ID = p7.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period7Sum,
			SUM( CASE WHEN fa.C_Period_ID = p8.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period8Sum,
			SUM( CASE WHEN fa.C_Period_ID = p9.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period9Sum,
			SUM( CASE WHEN fa.C_Period_ID = p10.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period10Sum,
			SUM( CASE WHEN fa.C_Period_ID = p11.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period11Sum,
			SUM( CASE WHEN fa.C_Period_ID = p12.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period12Sum,
			SUM( CASE WHEN fa.C_Period_ID IN 
					(p1.C_Period_ID, p2.C_Period_ID, p3.C_Period_ID, p4.C_Period_ID, p5.C_Period_ID, p6.C_Period_ID, 
					p7.C_Period_ID, p8.C_Period_ID, p9.C_Period_ID, p10.C_Period_ID, p11.C_Period_ID, p12.C_Period_ID)
				THEN fa.QtyAcct ELSE 0 END
			) AS TotalSum,
			SUM( CASE WHEN fa.C_Period_ID IN 
					(p1.C_Period_ID, p2.C_Period_ID, p3.C_Period_ID, p4.C_Period_ID, p5.C_Period_ID, p6.C_Period_ID, 
					p7.C_Period_ID, p8.C_Period_ID, p9.C_Period_ID, p10.C_Period_ID, p11.C_Period_ID, p12.C_Period_ID)
				THEN fa.AmtAcct ELSE 0 END
			) AS TotalAmt,
			fa.ad_org_id,
			fa.iso_code
		FROM
			C_Period p1
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
				SELECT *, ABS(qty) * SIGN(AmtAcct) AS QtyAcct
				FROM (	
					SELECT 	fa.*, CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct, c.iso_code
					FROM 	Fact_Acct fa 
					JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
					INNER JOIN AD_Org o ON fa.ad_org_id = o.ad_org_id
					INNER JOIN AD_ClientInfo ci ON o.AD_Client_ID=ci.ad_client_id
					LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID=ci.C_AcctSchema1_ID
					LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID=c.C_Currency_ID
					WHERE	AD_Table_ID = (SELECT Get_Table_ID('C_Invoice')) AND fa.isActive = 'Y'
				) fa
			) fa ON true
			INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
			INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
		WHERE
			fa.AD_Table_ID = ( SELECT Get_Table_ID( 'C_Invoice' ) )
			-- Akontozahlung invoices are not included. See FRESH_609
			AND i.C_DocType_ID NOT IN (SELECT C_DocType_ID FROM C_DocType WHERE docbasetype='API' AND docsubtype='DP')
			AND p1.C_Period_ID = $1 AND p1.isActive = 'Y'
			AND i.IsSOtrx = $2
			AND ( CASE WHEN $3 IS NULL THEN TRUE ELSE fa.C_BPartner_ID = $3 END )
			AND ( CASE WHEN $4 IS NULL THEN TRUE ELSE fa.C_Activity_ID = $4 END )
			AND ( CASE WHEN $5 IS NULL THEN TRUE ELSE p.M_Product_ID = $5 END )
			AND ( CASE WHEN $6 IS NULL THEN TRUE ELSE p.M_Product_Category_ID = $6 END 
				-- It was a requirement to not have HU Packing material within the sums of the Statistics reports 
				AND p.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
			)
			AND ( 
				-- If the given attribute set instance has values set... 
				CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $7 )
				-- ... then apply following filter:
				THEN ( 
					-- Take lines where the attributes of the current InvoiceLine's asi are in the parameter asi and their Values Match
					EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes a -- a = Attributes from invoice line, pa = Parameter Attributes
							INNER JOIN report.fresh_Attributes pa ON pa.M_AttributeSetInstance_ID = $7
								AND a.at_value = pa.at_value -- same attribute
								AND a.ai_value = pa.ai_value -- same value
						WHERE	a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
					)
					-- Dismiss lines where the Attributes in the Parameter are not in the InvoiceLine's asi
					AND NOT EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes pa
							LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
						WHERE	pa.M_AttributeSetInstance_ID = $7
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				-- ... else deactivate the filter 
				ELSE TRUE END
			)
			AND fa.ad_org_id = $8
		GROUP BY
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			fa.C_UOM_ID, 
			p1.EndDate,
			p1.StartDate, p2.StartDate, p3.StartDate, p4.StartDate, p5.StartDate, p6.StartDate, 
			p7.StartDate, p8.StartDate, p9.StartDate, p10.StartDate, p11.StartDate, p12.StartDate,
			fa.ad_org_id,
			fa.iso_code
	) a
	INNER JOIN C_UOM uom ON a.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $9 AND uomt.isActive = 'Y'
	INNER JOIN C_BPartner bp ON a.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID 
	LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $9 AND pt.isActive = 'Y'
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
ORDER BY
	bp.Name, pc.value, p.name
$BODY$
LANGUAGE sql VOLATILE;

COMMENT ON FUNCTION report.fresh_product_statistics_report(numeric, character varying, numeric, numeric, numeric, numeric, numeric,numeric, character varying) IS 'Making this function volatile is currently our only known way to avoid
http://postgresql.nabble.com/BUG-8393-quot-ERROR-failed-to-locate-grouping-columns-quot-on-grouping-by-varchar-returned-from-funcn-td5768367.html';







DROP FUNCTION IF EXISTS report.fresh_ADR_umsatzliste_bpartner_report
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
	
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		
		IN M_AttributeSetInstance_ID numeric,
		
		IN AD_Language character varying
	);
	
DROP FUNCTION IF EXISTS report.fresh_ADR_umsatzliste_bpartner_report
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
	
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		
		IN M_AttributeSetInstance_ID numeric,
		
		IN AD_Language character varying,
		IN AD_Org_ID numeric
	);
DROP TABLE IF EXISTS report.fresh_ADR_umsatzliste_bpartner_report;

CREATE TABLE report.fresh_ADR_umsatzliste_bpartner_report
(
	productname character varying(250),
	sameperiodsum numeric,
	productcategory character varying(250),
	bpartnername character varying(250),
	attributes character varying(250),
	
	startdate character varying(250),
	enddate character varying(250),
	ad_org_id numeric
	
);


CREATE FUNCTION report.fresh_ADR_umsatzliste_bpartner_report
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Language character varying,
		IN AD_Org_ID numeric
	) 
	RETURNS SETOF report.fresh_ADR_umsatzliste_bpartner_report AS
$BODY$
SELECT
	
	 COALESCE(pt.name, p.name) as productname
	 ,sum (um.sameperiodsum) as sameperiodsum
	 ,bpp.productcategory 
	 ,COALESCE ((SELECT name FROM C_BPartner WHERE C_BPartner_ID = $4), 'alle' ) AS bpartnername
	 ,COALESCE ((SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $5), 'alle') AS attributes
	 ,to_char($1, 'DD.MM.YYYY') AS Base_Period_Start
	 ,to_char($2, 'DD.MM.YYYY') AS Base_Period_End
	 ,um.ad_org_id 
	 FROM report.fresh_umsatzliste_bpartner_report(
			$1,
			$2,
			 null, --$P{Comp_Period_Start},
			 null, --$P{Comp_Period_End},
			$3, --$P{IsSOTrx},
			$4, --$P{C_BPartner_ID},
			 null, --$P{C_Activity_ID},
			 null, --$P{M_Product_ID},
			 null, --$P{M_Product_Category_ID},
			$5, --$P{M_AttributeSetInstance_ID}
			$7 -- AD_Org_ID
			) um 
	join m_product p on p.name = um.p_name AND p.ad_org_id = $7
	LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $6 AND pt.isActive = 'Y'
	left join C_BPartner_Product bpp  ON p.M_Product_ID = bpp.M_Product_ID and bpp.c_bpartner_id = $4 AND bpp.isActive = 'Y'
	group by  COALESCE(pt.name, p.name), bpp.productcategory,um.ad_org_id 
	
$BODY$
LANGUAGE sql STABLE;







DROP FUNCTION IF EXISTS report.fresh_umsatzliste_bpartner_report_sub
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric
	);

DROP FUNCTION IF EXISTS report.fresh_umsatzliste_bpartner_report 
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric
	) ;	
	
DROP TABLE IF EXISTS report.fresh_umsatzliste_bpartner_report;
DROP TABLE IF EXISTS report.fresh_umsatzliste_bpartner_report_sub;




/* ***************************************************************** */


CREATE TABLE report.fresh_umsatzliste_bpartner_report_sub
(
	bp_name character varying(60),
	pc_name character varying(60),
	p_name character varying(255),
	sameperiodsum numeric,
	compperiodsum numeric,
	perioddifference numeric,
	perioddiffpercentage numeric,
	Base_Period_Start character varying(10),
	Base_Period_End character varying(10),
	Comp_Period_Start character varying(10),
	Comp_Period_End character varying(10),
	param_IsSOTrx  character varying,
	param_bp character varying(60),
	param_Activity character varying(60),
	param_product character varying(255),
	param_Product_Category character varying(60),
	Param_Attributes character varying(255),
	ad_org_id numeric
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.fresh_umsatzliste_bpartner_report_sub 
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric
	) 
	RETURNS SETOF report.fresh_umsatzliste_bpartner_report_sub AS
$BODY$
SELECT
	bp.Name AS bp_name,
	pc.Name AS pc_name, 
	p.Name AS P_name,
	SamePeriodSum,
	CompPeriodSum,
	SamePeriodSum - CompPeriodSum AS PeriodDifference,
	CASE WHEN SamePeriodSum - CompPeriodSum != 0 AND CompPeriodSum != 0
		THEN (SamePeriodSum - CompPeriodSum) / CompPeriodSum * 100 ELSE NULL
	END AS PeriodDiffPercentage,
	to_char($1, 'DD.MM.YYYY') AS Base_Period_Start,
	to_char($2, 'DD.MM.YYYY') AS Base_Period_End,
	COALESCE( to_char($3, 'DD.MM.YYYY'), '') AS Comp_Period_Start,
	COALESCE( to_char($4, 'DD.MM.YYYY'), '') AS Comp_Period_End,
	CASE WHEN $5 = 'N' THEN 'Einkauf' WHEN $5 = 'Y' THEN 'Verkauf' ELSE 'alle' END AS param_IsSOTrx,
	COALESCE ((SELECT name FROM C_BPartner WHERE C_BPartner_ID = $6), 'alle' ) AS param_bp,
	COALESCE ((SELECT name FROM C_Activity WHERE C_Activity_ID = $7), 'alle' ) AS param_Activity,
	COALESCE ((SELECT name FROM M_Product WHERE M_Product_ID = $8), 'alle' ) AS param_product,
	COALESCE ((SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $9), 'alle' ) AS param_Product_Category,
	COALESCE ((SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $10), 'alle') AS Param_Attributes,
	a.ad_org_id
FROM
	(
		SELECT
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			SUM( CASE WHEN IsInPeriod THEN AmtAcct ELSE 0 END ) AS SamePeriodSum,
			SUM( CASE WHEN IsInCompPeriod THEN AmtAcct ELSE 0 END  ) AS CompPeriodSum,
			1 AS Line_Order,
			fa.ad_org_id
		FROM
			(
				SELECT 	fa.*, 
					( fa.DateAcct >= $1 AND fa.DateAcct <= $2 ) AS IsInPeriod,
					( fa.DateAcct >= $3 AND fa.DateAcct <= $4 ) AS IsInCompPeriod,
					CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct 
				FROM 	Fact_Acct fa JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID
				WHERE	AD_Table_ID = (SELECT Get_Table_ID('C_Invoice')) AND fa.isActive = 'Y'
			) fa
			INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
			INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID 
		WHERE
			AD_Table_ID = ( SELECT Get_Table_ID( 'C_Invoice' ) )
			AND ( IsInPeriod OR IsInCompPeriod )
			AND i.IsSOtrx = $5
			AND ( CASE WHEN $6 IS NULL THEN TRUE ELSE fa.C_BPartner_ID = $6 END )
			AND ( CASE WHEN $7 IS NULL THEN TRUE ELSE fa.C_Activity_ID = $7 END )
			AND ( CASE WHEN $8 IS NULL THEN TRUE ELSE p.M_Product_ID = $8 END AND p.M_Product_ID IS NOT NULL )
			AND ( CASE WHEN $9 IS NULL THEN TRUE ELSE p.M_Product_Category_ID = $9 END 
				-- It was a requirement to not have HU Packing material within the sums of this report 
				AND p.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
			)
			AND ( 
				CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $10 )
				THEN ( 
					EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes a
							INNER JOIN report.fresh_Attributes pa ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND pa.M_AttributeSetInstance_ID = $10			
						WHERE	a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
					)
					AND NOT EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes pa
							LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
						WHERE	pa.M_AttributeSetInstance_ID = $10
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				ELSE TRUE END
			)
			AND fa.ad_org_id = $11
		GROUP BY
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			fa.ad_org_id
	) a
	INNER JOIN C_BPartner bp ON a.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID 
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
$BODY$
LANGUAGE sql STABLE;


/* ***************************************************************** */


CREATE TABLE report.fresh_umsatzliste_bpartner_report
(
	bp_name character varying(60),
	pc_name character varying(60),
	p_name character varying(255),
	sameperiodsum numeric,
	compperiodsum numeric,
	perioddifference numeric,
	perioddiffpercentage numeric,
	Base_Period_Start character varying(10),
	Base_Period_End character varying(10),
	Comp_Period_Start character varying(10),
	Comp_Period_End character varying(10),
	param_IsSOTrx  character varying,
	param_bp character varying(60),
	param_Activity character varying(60),
	param_product character varying(255),
	param_Product_Category character varying(60),
	Param_Attributes character varying(255),
	ad_org_id numeric,
	unionorder integer
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.fresh_umsatzliste_bpartner_report 
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric
	) 
	RETURNS SETOF report.fresh_umsatzliste_bpartner_report AS
$BODY$
	SELECT 
		*, 1 AS UnionOrder
	FROM 	
		report.fresh_umsatzliste_bpartner_report_sub ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)
UNION ALL
	SELECT 
		bp_name, pc_name, null AS P_name,
		SUM( SamePeriodSum ) AS SamePeriodSum,
		SUM( CompPeriodSum ) AS CompPeriodSum,
		SUM( SamePeriodSum ) - SUM( CompPeriodSum ) AS PeriodDifference,
		CASE WHEN SUM( SamePeriodSum ) - SUM( CompPeriodSum ) != 0 AND SUM( CompPeriodSum ) != 0
			THEN (SUM( SamePeriodSum ) - SUM( CompPeriodSum )) / SUM( CompPeriodSum ) * 100 ELSE NULL
		END AS PeriodDiffPercentage,
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, param_IsSOTrx, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id, 
		2 AS UnionOrder
	FROM 	
		report.fresh_umsatzliste_bpartner_report_sub ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)
	GROUP BY
		bp_name, pc_name, 
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, param_IsSOTrx, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id
UNION ALL
	SELECT 
		bp_name, null, null,
		SUM( SamePeriodSum ) AS SamePeriodSum,
		SUM( CompPeriodSum ) AS CompPeriodSum,
		SUM( SamePeriodSum ) - SUM( CompPeriodSum ) AS PeriodDifference,
		CASE WHEN SUM( SamePeriodSum ) - SUM( CompPeriodSum ) != 0 AND SUM( CompPeriodSum ) != 0
			THEN (SUM( SamePeriodSum ) - SUM( CompPeriodSum )) / SUM( CompPeriodSum ) * 100 ELSE NULL
		END AS PeriodDiffPercentage,
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, param_IsSOTrx, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id,
		3 AS UnionOrder
	FROM 	
		report.fresh_umsatzliste_bpartner_report_sub ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)
	GROUP BY
		bp_name, 
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, param_IsSOTrx, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id
ORDER BY
	bp_name, pc_name NULLS LAST, UnionOrder, p_name
$BODY$
LANGUAGE sql STABLE;





DROP FUNCTION IF EXISTS report.fresh_umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric);

DROP TABLE IF EXISTS report.fresh_umsatzreport_report;

DROP FUNCTION IF EXISTS report.fresh_Umsatzreport_Report_Sub (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric);

DROP TABLE IF EXISTS report.fresh_Umsatzreport_Report_Sub;

CREATE TABLE report.fresh_Umsatzreport_Report_Sub
(
	name character varying(60),
	periodend date,
	lastyearperiodend date,
	year character varying(10),
	lastyear character varying(10),
	sameperiodsum numeric,
	sameperiodlastyearsum numeric,
	perioddifference numeric,
	perioddiffpercentage numeric,
	sameyearsum numeric,
	lastyearsum numeric,
	yeardifference numeric,
	yeardiffpercentage numeric,
	attributesetinstance character varying(60)
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.fresh_Umsatzreport_Report_Sub(IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric ) RETURNS SETOF report.fresh_Umsatzreport_Report_Sub AS
$BODY$
SELECT
	CASE WHEN Length(name) <= 45 THEN name ELSE substring(name FOR 43 ) || '...' END AS name,
	PeriodEnd,
	LastYearPeriodEnd,
	Year,
	LastYear,
	SamePeriodSum AS SamePeriodSum,
	SamePeriodLastYearSum,
	SamePeriodSum - SamePeriodLastYearSum AS PeriodDifference,
	CASE WHEN SamePeriodSum - SamePeriodLastYearSum != 0 AND SamePeriodLastYearSum != 0
		THEN (SamePeriodSum - SamePeriodLastYearSum) / SamePeriodLastYearSum * 100 ELSE NULL
	END AS PeriodDiffPercentage,
	SameYearSum AS SameYearSum,
	LastYearSum AS LastYearSum,
	SameYearSum - LastYearSum AS YearDifference,
	CASE WHEN SameYearSum - LastYearSum != 0 AND LastYearSum != 0
		THEN (SameYearSum - LastYearSum) / LastYearSum * 100 ELSE NULL
	END AS YearDiffPercentage,
	Attributes as attributesetinstance

FROM
	(
		SELECT
			bp.name,
			p.EndDate::Date AS PeriodEnd,
			pp.EndDate::Date AS LastYearPeriodEnd,
			y.fiscalYear AS Year,
			py.fiscalYear AS LastYear,
			SUM( CASE WHEN fa.C_Period_ID = p.C_Period_ID THEN AmtAcct ELSE 0 END ) AS SamePeriodSum,
			SUM( CASE WHEN fap.C_Year_ID = p.C_Year_ID AND fap.periodNo <= p.PeriodNo THEN AmtAcct ELSE 0 END ) AS SameYearSum,
			SUM( CASE WHEN fa.C_Period_ID = pp.C_Period_ID THEN AmtAcct ELSE 0 END ) AS SamePeriodLastYearSum,
			SUM( CASE WHEN fap.C_Year_ID = pp.C_Year_ID AND fap.periodNo <= pp.PeriodNo THEN AmtAcct ELSE 0 END ) AS LastYearSum,
			att.Attributes
		FROM
			C_Period p
			INNER JOIN C_Year y ON p.C_Year_ID = y.C_Year_ID AND y.isActive = 'Y'
			-- Get same Period from previous year
			LEFT OUTER JOIN C_Period pp ON pp.C_Period_ID = report.Get_Predecessor_Period_Recursive ( p.C_Period_ID,
				( SELECT count(0) FROM C_Period sp WHERE sp.C_Year_ID = p.C_Year_ID and isActive = 'Y' )::int ) AND pp.isActive = 'Y'
			LEFT OUTER JOIN C_Year py ON pp.C_Year_ID = py.C_Year_ID AND py.isActive = 'Y'
			
			-- Get data from fact account
			INNER JOIN (	
				SELECT 	
					fa.M_Product_ID, fa.C_Period_ID, fa.C_BPartner_ID,
					CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct,
					il.M_AttributeSetInstance_ID, il.AD_Client_ID, il.AD_Org_ID
					 
				FROM 	
					Fact_Acct fa 
					JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
					JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
				WHERE	
					AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
					AND IsSOtrx = $2 AND fa.isActive = 'Y'
					AND ( 
				-- If the given attribute set instance has values set... 
				CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $3 )
				-- ... then apply following filter:
				THEN ( 
					-- Take lines where the attributes of the current InvoiceLine's asi are in the parameter asi and their Values Match
					EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes a -- a = Attributes from invoice line, pa = Parameter Attributes
							INNER JOIN report.fresh_Attributes pa ON pa.M_AttributeSetInstance_ID = $3 
								AND a.at_value = pa.at_value -- same attribute
								AND a.ai_value = pa.ai_value -- same value
						WHERE	a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
					)
					-- Dismiss lines where the Attributes in the Parameter are not in the InvoiceLine's asi
					AND NOT EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes pa
							LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
						WHERE	pa.M_AttributeSetInstance_ID = $3
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				-- ... else deactivate the filter 
				ELSE TRUE END
			)
			) fa ON true
			INNER JOIN C_Period fap ON fa.C_Period_ID = fap.C_Period_ID AND fap.isActive = 'Y'
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product pr ON fa.M_Product_ID = pr.M_Product_ID 
				AND pr.M_Product_Category_ID !=  getSysConfigAsNumeric('PackingMaterialProductCategoryID', fa.AD_Client_ID, fa.AD_Org_ID)
			INNER JOIN C_BPartner bp ON fa.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'

			LEFT OUTER JOIN	(
					SELECT 	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes, M_AttributeSetInstance_ID FROM Report.fresh_Attributes
					GROUP BY M_AttributeSetInstance_ID
					) att ON $3 = att.M_AttributeSetInstance_ID
		WHERE
			p.C_Period_ID = $1 AND p.isActive = 'Y'
			
		GROUP BY
			bp.name,
			p.EndDate,
			pp.EndDate,
			y.fiscalYear,
			py.fiscalYear,
			att.Attributes
	) a
ORDER BY
	SameYearSum DESC$BODY$
LANGUAGE sql STABLE;



DROP FUNCTION IF EXISTS report.fresh_umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric);

DROP TABLE IF EXISTS report.fresh_umsatzreport_report;

CREATE TABLE report.fresh_umsatzreport_report
(
	name character varying(60),
	periodend date,
	lastyearperiodend date,
	year character varying(10),
	lastyear character varying(10),
	sameperiodsum numeric,
	sameperiodlastyearsum numeric,
	perioddifference numeric,
	perioddiffpercentage numeric,
	sameyearsum numeric,
	lastyearsum numeric,
	yeardifference numeric,
	yeardiffpercentage numeric,
	attributesetinstance character varying(60),
	unionorder integer
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.fresh_umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric) RETURNS SETOF report.fresh_umsatzreport_report AS
$BODY$
	SELECT *, 1 AS UnionOrder FROM report.fresh_Umsatzreport_Report_Sub ($1, $2, $3)
UNION ALL
	SELECT 
		null as name, 
		PeriodEnd,
		LastYearPeriodEnd,
		Year,
		LastYear,
		SUM( SamePeriodSum ) AS SamePeriodSum,
		SUM( SamePeriodLastYearSum ) AS SamePeriodLastYearSum,
		SUM( SamePeriodSum ) - SUM( SamePeriodLastYearSum ) AS PeriodDifference,
		CASE WHEN SUM( SamePeriodSum ) - SUM( SamePeriodLastYearSum ) != 0 AND SUM( SamePeriodLastYearSum ) != 0
			THEN (SUM( SamePeriodSum ) - SUM( SamePeriodLastYearSum ) ) / SUM( SamePeriodLastYearSum ) * 100 ELSE NULL
		END AS PeriodDiffPercentage,
		SUM( SameYearSum ) AS SameYearSum,
		SUM( LastYearSum ) AS LastYearSum,
		SUM( SameYearSum ) - SUM( LastYearSum ) AS YearDifference,
		CASE WHEN SUM( SameYearSum ) - SUM( LastYearSum ) != 0 AND SUM( LastYearSum ) != 0
			THEN (SUM( SameYearSum ) - SUM( LastYearSum ) ) / SUM( LastYearSum ) * 100 ELSE NULL
		END AS YearDiffPercentage,
		attributesetinstance,
		2 AS UnionOrder
		
	FROM 
		report.fresh_Umsatzreport_Report_Sub ($1, $2, $3)
	GROUP BY
		PeriodEnd,
		LastYearPeriodEnd,
		Year,
		LastYear,
		attributesetinstance
ORDER BY
	UnionOrder, SameYearSum DESC
$BODY$
LANGUAGE sql STABLE;





DROP FUNCTION IF EXISTS report.umsatzliste_bpartner_week_report (IN numeric, IN numeric, IN integer, IN integer, IN numeric, IN numeric, IN integer, IN integer, IN character varying, IN numeric, IN numeric, IN numeric, IN numeric, IN numeric, IN numeric );
DROP FUNCTION IF EXISTS report.umsatzliste_bpartner_week_report (IN numeric, IN numeric, IN integer, IN integer, IN numeric, IN numeric, IN integer, IN integer, IN character varying, IN numeric, IN numeric, IN numeric, IN numeric, IN numeric, IN character varying );

CREATE OR REPLACE FUNCTION report.umsatzliste_bpartner_week_report
	(
		IN Base_Year_Start numeric, --$1
		IN Base_Year_End numeric, --$2
		IN Base_Week_Start integer, --$3
		IN Base_Week_End integer,  --$4
		IN Comp_Year_Start numeric, --$5
		IN Comp_Year_End numeric, --$6
		IN Comp_Week_Start integer,  --$7
		IN Comp_Week_End integer,  --$8
		IN issotrx character varying, --$9
		IN C_BPartner_ID numeric,  --$10
		IN M_Product_ID numeric, --$11
		IN M_Product_Category_ID numeric, --$12
		IN M_AttributeSetInstance_ID numeric, --$13
		IN C_Currency_ID numeric, --$14
		IN AD_Org_ID numeric, --$15
		IN AD_Language Character Varying (6) --$16
	) 
	RETURNS TABLE
	(
		bp_name character varying(60),
		pc_name character varying(60),
		p_name character varying(255),
		sameperiodsum numeric,
		compperiodsum numeric,
		sameperiodqtysum numeric,
		compperiodqtysum numeric,
		perioddifference numeric,
		periodqtydifference numeric,
		perioddiffpercentage numeric,
		periodqtydiffpercentage numeric,
		Base_Week_Start character varying(10),
		Base_Week_End character varying(10),
		Comp_Week_Start character varying(10),
		Comp_Week_End character varying(10),
		param_IsSOTrx  character varying,
		param_bp character varying(60),
		param_product character varying(255),
		param_Product_Category character varying(60),
		Param_Attributes character varying(255),
		param_currency character(3),
		ad_org_id numeric
	)
AS
$$
SELECT
	bp.Name AS bp_name,
	pc.Name AS pc_name, 
	COALESCE(pt.Name, p.Name) AS P_name,
	SamePeriodSum,
	CompPeriodSum,
	SamePeriodQtySum,
	CompPeriodQtySum,
	SamePeriodSum - CompPeriodSum AS PeriodDifference,
	SamePeriodQtySum - CompPeriodQtySum AS PeriodQtyDifference,
	CASE WHEN SamePeriodSum - CompPeriodSum != 0 AND CompPeriodSum != 0
		THEN (SamePeriodSum - CompPeriodSum) / CompPeriodSum * 100 ELSE NULL
	END AS PeriodDiffPercentage,
	CASE WHEN SamePeriodQtySum - CompPeriodQtySum != 0 AND CompPeriodQtySum != 0
		THEN (SamePeriodQtySum - CompPeriodQtySum) / CompPeriodQtySum * 100 ELSE NULL
	END AS PeriodQtyDiffPercentage,
	
	$3 || ' ' || (select fiscalyear from c_year where c_year_id =$1 AND isActive = 'Y') AS Base_Week_Start,
	$4 || ' ' || (select fiscalyear from c_year where c_year_id =$2 AND isActive = 'Y') AS Base_Week_End,
	COALESCE( ($7 || ' ' || (select fiscalyear from c_year where c_year_id =$5 AND isActive = 'Y')),'') AS Comp_Week_Start,
	COALESCE( ($8 || ' ' || (select fiscalyear from c_year where c_year_id =$6 AND isActive = 'Y')),'') AS Comp_Week_End,

	

	 $9 AS param_IsSOTrx,
	(SELECT name FROM C_BPartner WHERE C_BPartner_ID = $10 AND isActive = 'Y') AS param_bp,
	(SELECT COALESCE(pt.name, p.name) FROM M_Product p 
		LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $16 AND pt.isActive = 'Y'
		WHERE p.M_Product_ID = $11 
	) AS param_product,
	(SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $12 AND isActive = 'Y') AS param_Product_Category,
	(SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $13) AS Param_Attributes,

	(SELECT iso_code FROM C_Currency WHERE C_Currency_ID = $14  AND isActive = 'Y'),
	a.ad_org_id

FROM
	(
		SELECT
			io.C_BPartner_ID,
			iol.M_Product_ID,
			SUM( CASE WHEN( io.MovementDate::date >= (select (to_timestamp($3 || ' ' ||(select fiscalyear from c_year where c_year_id =$1 AND isActive = 'Y'),'IW IYYY')))::date   -- base_week_start
						AND io.MovementDate::date <= (select (to_timestamp($4 || ' ' ||(select fiscalyear from c_year where c_year_id =$2 AND isActive = 'Y'),'IW IYYY')+ interval '6' day))::date ) -- base_week_end
				THEN ROUND((COALESCE(ic.PriceActual_Override, ic.PriceActual) * iol.MovementQty * COALESCE (uconv.multiplyrate, 1)),2)
				ELSE 0 
			END) AS SamePeriodSum,
			SUM( CASE WHEN( io.MovementDate::date >= (select (to_timestamp($7 || ' ' ||(select fiscalyear from c_year where c_year_id =$5 AND isActive = 'Y'),'IW IYYY')))::date  -- comp_week_start 
						AND io.MovementDate::date <= (select (to_timestamp($8 || ' ' ||(select fiscalyear from c_year where c_year_id =$6 AND isActive = 'Y'),'IW IYYY')+ interval '6' day))::date ) -- comp_week_end 
			  THEN ROUND((COALESCE(ic.PriceActual_Override, ic.PriceActual) * iol.MovementQty * COALESCE (uconv.multiplyrate, 1)),2)
			  ELSE 0 
			END) AS CompPeriodSum,

			SUM( CASE WHEN( io.MovementDate::date >= (select (to_timestamp($3 || ' ' ||(select fiscalyear from c_year where c_year_id =$1 AND isActive = 'Y'),'IW IYYY')))::date   -- base_week_start
						AND io.MovementDate::date <= (select (to_timestamp($4 || ' ' ||(select fiscalyear from c_year where c_year_id =$2 AND isActive = 'Y'),'IW IYYY')+ interval '6' day))::date ) -- base_week_end
			  THEN iol.MovementQty
			  ELSE 0 
			END) AS SamePeriodQtySum,

			SUM( CASE WHEN( io.MovementDate::date >= (select (to_timestamp($7 || ' ' ||(select fiscalyear from c_year where c_year_id =$5 AND isActive = 'Y'),'IW IYYY')))::date  -- comp_week_start 
						AND io.MovementDate::date <= (select (to_timestamp($8 || ' ' ||(select fiscalyear from c_year where c_year_id =$6 AND isActive = 'Y'),'IW IYYY')+ interval '6' day))::date ) -- comp_week_end 
			  THEN iol.MovementQty
			  ELSE 0 
			END) AS CompPeriodQtySum,
			iciol.ad_org_id			
						
			FROM  C_InvoiceCandidate_InOutLine iciol	 
			INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
			INNER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID AND iol.isActive = 'Y'
			INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'

			INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID 
			
			LEFT OUTER JOIN C_UOM uom ON ic.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
			LEFT OUTER JOIN C_UOM_Conversion uconv ON uconv.C_UOM_ID = iol.C_UOM_ID
												AND uconv.C_UOM_To_ID = ic.Price_UOM_ID
												AND iol.M_Product_ID = uconv.M_Product_ID
												AND uconv.isActive = 'Y'		
		WHERE
			( 
			((io.MovementDate::date >= (select (to_timestamp($3 || ' ' ||(select fiscalyear from c_year where c_year_id =$1 AND isActive = 'Y'),'IW IYYY')))::date ) -- base_week_start 
				AND io.MovementDate::date<= (select (to_timestamp($4 || ' ' ||(select fiscalyear from c_year where c_year_id =$2 AND isActive = 'Y'),'IW IYYY')+ interval '6' day))::date)  -- base_week_end
			OR ((io.MovementDate::date >= (select (to_timestamp($7 || ' ' ||(select fiscalyear from c_year where c_year_id =$5 AND isActive = 'Y'),'IW IYYY')))::date )  -- comp_week_start 
				AND io.MovementDate::date <= (select (to_timestamp($8 || ' ' ||(select fiscalyear from c_year where c_year_id =$6 AND isActive = 'Y'),'IW IYYY')+ interval '6' day))::date) -- comp_week_end 
			)  
			AND io.IsSOtrx = $9
			AND ( CASE WHEN $10 IS NULL THEN TRUE ELSE io.C_BPartner_ID = $10 END )
			AND ( CASE WHEN $11 IS NULL THEN TRUE ELSE p.M_Product_ID = $11 END AND p.M_Product_ID IS NOT NULL )
			AND ( CASE WHEN $12 IS NULL THEN TRUE ELSE p.M_Product_Category_ID = $12 END 
				-- It was a requirement to not have HU Packing material within the sums of this report 
				AND p.M_Product_Category_ID !=  getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
			)
			AND ( 
				CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $13 )
				THEN ( 
					EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes a
							INNER JOIN report.fresh_Attributes pa ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND pa.M_AttributeSetInstance_ID = $13			
						WHERE	a.M_AttributeSetInstance_ID = iol.M_AttributeSetInstance_ID
					)
					AND NOT EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes pa
							LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND a.M_AttributeSetInstance_ID = iol.M_AttributeSetInstance_ID
						WHERE	pa.M_AttributeSetInstance_ID = $13
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				ELSE TRUE END
			)
			AND io.docstatus in ('CO','CL')
			AND ic.C_Currency_ID = $14
			AND iciol.ad_org_id = $15
		GROUP BY
			io.C_BPartner_ID,
			iol.M_Product_ID,
			iciol.ad_org_id
	) a
	INNER JOIN C_BPartner bp ON a.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID 
	LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $16 AND pt.isActive = 'Y'
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	
	ORDER BY
	bp_name, pc_name NULLS LAST, p_name
$$
LANGUAGE sql STABLE;





DROP FUNCTION IF EXISTS report.umsatzliste_bpartner_report
	(
		IN Base_Period_Start date,IN Base_Period_End date, IN Comp_Period_Start date, IN Comp_Period_End date, IN issotrx character varying,IN C_BPartner_ID numeric, IN C_Activity_ID numeric,IN M_Product_ID numeric,IN M_Product_Category_ID numeric,IN M_AttributeSetInstance_ID numeric,IN AD_Org_ID numeric
	);

DROP FUNCTION IF EXISTS report.umsatzliste_bpartner_report_sub
	(
		IN Base_Period_Start date,IN Base_Period_End date, IN Comp_Period_Start date, IN Comp_Period_End date, IN issotrx character varying,IN C_BPartner_ID numeric, IN C_Activity_ID numeric,	IN M_Product_ID numeric,IN M_Product_Category_ID numeric,IN M_AttributeSetInstance_ID numeric,IN AD_Org_ID numeric
	);
DROP FUNCTION IF EXISTS report.umsatzliste_bpartner_report
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric,
		IN AD_Language Character Varying (6)
	);

DROP FUNCTION IF EXISTS report.umsatzliste_bpartner_report_sub
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric,
		IN AD_Language Character Varying (6)
	);	
DROP TABLE IF EXISTS report.umsatzliste_bpartner_report;
DROP TABLE IF EXISTS report.umsatzliste_bpartner_report_sub;




/* ***************************************************************** */


CREATE TABLE report.umsatzliste_bpartner_report_sub
(
	bp_name character varying(60),
	pc_name character varying(60),
	p_name character varying(255),
	sameperiodsum numeric,
	compperiodsum numeric,
	sameperiodqtysum numeric,
	compperiodqtysum numeric,
	perioddifference numeric,
	periodqtydifference numeric,
	perioddiffpercentage numeric,
	periodqtydiffpercentage numeric,
	Base_Period_Start character varying(10),
	Base_Period_End character varying(10),
	Comp_Period_Start character varying(10),
	Comp_Period_End character varying(10),
	param_bp character varying(60),
	param_Activity character varying(60),
	param_product character varying(255),
	param_Product_Category character varying(60),
	Param_Attributes character varying(255),
	currency character(3),
	ad_org_id numeric
)
WITH (
	OIDS=FALSE
);

CREATE FUNCTION report.umsatzliste_bpartner_report_sub 
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric,
		IN AD_Language Character Varying (6)
	) 
	RETURNS SETOF report.umsatzliste_bpartner_report_sub AS
$BODY$
SELECT
	bp.Name AS bp_name,
	pc.Name AS pc_name, 
	COALESCE(pt.Name, p.Name) AS P_name,
	SamePeriodSum,
	CompPeriodSum,
	SamePeriodQtySum,
	CompPeriodQtySum,
	SamePeriodSum - CompPeriodSum AS PeriodDifference,
	SamePeriodQtySum - CompPeriodQtySum AS PeriodQtyDifference,
	CASE WHEN SamePeriodSum - CompPeriodSum != 0 AND CompPeriodSum != 0
		THEN (SamePeriodSum - CompPeriodSum) / CompPeriodSum * 100 ELSE NULL
	END AS PeriodDiffPercentage,
	
	CASE WHEN SamePeriodQtySum - CompPeriodQtySum != 0 AND CompPeriodQtySum != 0
		THEN (SamePeriodQtySum - CompPeriodQtySum) / CompPeriodQtySum * 100 ELSE NULL
	END AS PeriodQtyDiffPercentage,
	
	to_char($1, 'DD.MM.YYYY') AS Base_Period_Start,
	to_char($2, 'DD.MM.YYYY') AS Base_Period_End,
	COALESCE( to_char($3, 'DD.MM.YYYY'), '') AS Comp_Period_Start,
	COALESCE( to_char($4, 'DD.MM.YYYY'), '') AS Comp_Period_End,
	
	(SELECT name FROM C_BPartner WHERE C_BPartner_ID = $6 and isActive = 'Y') AS param_bp,
	(SELECT name FROM C_Activity WHERE C_Activity_ID = $7 and isActive = 'Y') AS param_Activity,
	(SELECT COALESCE(pt.name, p.name) FROM M_Product p 
		LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $12 AND pt.isActive = 'Y'
		WHERE p.M_Product_ID = $8
	) AS param_product,
	(SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $9 and isActive = 'Y') AS param_Product_Category,
	(SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $10) AS Param_Attributes,

	c.iso_code AS currency,
	a.ad_org_id

FROM
	(
		SELECT
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			SUM( CASE WHEN IsInPeriod THEN AmtAcct ELSE 0 END ) AS SamePeriodSum,
			SUM( CASE WHEN IsInCompPeriod THEN AmtAcct ELSE 0 END  ) AS CompPeriodSum,
			
			SUM( CASE WHEN IsInPeriod THEN 	il.qtyinvoiced ELSE 0 END ) AS SamePeriodQtySum,
			SUM( CASE WHEN IsInCompPeriod THEN il.qtyinvoiced ELSE 0 END  ) AS CompPeriodQtySum,
			1 AS Line_Order,
			fa.ad_org_id
		FROM
			(
				SELECT 	fa.*, 
					( fa.DateAcct >= $1 AND fa.DateAcct <= $2 ) AS IsInPeriod,
					( fa.DateAcct >= $3 AND fa.DateAcct <= $4 ) AS IsInCompPeriod,
					
				CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct 
				FROM 	Fact_Acct fa JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
				WHERE	AD_Table_ID = (SELECT Get_Table_ID('C_Invoice')) AND fa.isActive = 'Y'

			) fa
			INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
			INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
		WHERE
			AD_Table_ID = ( SELECT Get_Table_ID( 'C_Invoice' ) )
			AND ( IsInPeriod OR IsInCompPeriod )
			AND i.IsSOtrx = $5
			AND ( CASE WHEN $6 IS NULL THEN TRUE ELSE fa.C_BPartner_ID = $6 END )
			AND ( CASE WHEN $7 IS NULL THEN TRUE ELSE fa.C_Activity_ID = $7 END )
			AND ( CASE WHEN $8 IS NULL THEN TRUE ELSE p.M_Product_ID = $8 END AND p.M_Product_ID IS NOT NULL )
			AND ( CASE WHEN $9 IS NULL THEN TRUE ELSE p.M_Product_Category_ID = $9 END 
				-- It was a requirement to not have HU Packing material within the sums of this report 
				AND p.M_Product_Category_ID !=  getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
			)
			AND ( 
				CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $10 )
				THEN ( 
					EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes a
							INNER JOIN report.fresh_Attributes pa ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND pa.M_AttributeSetInstance_ID = $10			
						WHERE	a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
					)
					AND NOT EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes pa
							LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
						WHERE	pa.M_AttributeSetInstance_ID = $10
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				ELSE TRUE END
			)
			AND fa.ad_org_id = $11
		GROUP BY
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			fa.ad_org_id
	) a

	INNER JOIN C_BPartner bp ON a.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $12 AND pt.isActive = 'Y'
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	--taking the currency of the client
	LEFT OUTER JOIN AD_ClientInfo ci ON ci.AD_Client_ID=bp.ad_client_id AND ci.isActive = 'Y'
	LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID=ci.C_AcctSchema1_ID AND acs.isActive = 'Y'
	LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID=c.C_Currency_ID AND c.isActive = 'Y'
	

$BODY$
LANGUAGE sql STABLE;


/* ***************************************************************** */


CREATE TABLE report.umsatzliste_bpartner_report
(
	bp_name character varying(60),
	pc_name character varying(60),
	p_name character varying(255),
	sameperiodsum numeric,
	compperiodsum numeric,
	sameperiodqtysum numeric,
	compperiodqtysum numeric,
	perioddifference numeric,
	periodqtydifference numeric,
	perioddiffpercentage numeric,
	periodqtydiffpercentage numeric,
	Base_Period_Start character varying(10),
	Base_Period_End character varying(10),
	Comp_Period_Start character varying(10),
	Comp_Period_End character varying(10),
	param_bp character varying(60),
	param_Activity character varying(60),
	param_product character varying(255),
	param_Product_Category character varying(60),
	Param_Attributes character varying(255),
	currency character(3),
	ad_org_id numeric,
	unionorder integer
)
WITH (
	OIDS=FALSE
);

CREATE FUNCTION report.umsatzliste_bpartner_report 
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric,
		IN AD_Language Character Varying (6)
		
	) 
	RETURNS SETOF report.umsatzliste_bpartner_report AS
$BODY$
	SELECT 
		*, 1 AS UnionOrder
	FROM 	
		report.umsatzliste_bpartner_report_sub ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12)
UNION ALL
	SELECT 
		bp_name, pc_name, null AS P_name,
		SUM( SamePeriodSum ) AS SamePeriodSum,
		SUM( CompPeriodSum ) AS CompPeriodSum,
		
		SUM( SamePeriodQtySum ) AS SamePeriodQtySum,
		SUM( CompPeriodQtySum ) AS CompPeriodQtySum,
		
		SUM( SamePeriodSum ) - SUM( CompPeriodSum ) AS PeriodDifference,
		SUM( SamePeriodQtySum ) - SUM( CompPeriodQtySum ) AS PeriodQtyDifference,
		
		CASE WHEN SUM( SamePeriodSum ) - SUM( CompPeriodSum ) != 0 AND SUM( CompPeriodSum ) != 0
			THEN (SUM( SamePeriodSum ) - SUM( CompPeriodSum )) / SUM( CompPeriodSum ) * 100 ELSE NULL
		END AS PeriodDiffPercentage,
		
		CASE WHEN SUM( SamePeriodQtySum ) - SUM( CompPeriodQtySum ) != 0 AND SUM( CompPeriodQtySum ) != 0
			THEN (SUM( SamePeriodQtySum ) - SUM( CompPeriodQtySum )) / SUM( CompPeriodQtySum ) * 100 ELSE NULL
		END AS PeriodQtyDiffPercentage,		
		
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, currency, ad_org_id,
		2 AS UnionOrder
	FROM 	
		report.umsatzliste_bpartner_report_sub ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12)
	GROUP BY
		bp_name, pc_name, 
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, currency, ad_org_id
UNION ALL
	SELECT 
		bp_name, null, null,
		SUM( SamePeriodSum ) AS SamePeriodSum,
		SUM( CompPeriodSum ) AS CompPeriodSum,
		
		SUM( SamePeriodQtySum ) AS SamePeriodQtySum,
		SUM( CompPeriodQtySum ) AS CompPeriodQtySum,
		
		SUM( SamePeriodSum ) - SUM( CompPeriodSum ) AS PeriodDifference,
		SUM( SamePeriodQtySum ) - SUM( CompPeriodQtySum ) AS PeriodQtyDifference,
		
		CASE WHEN SUM( SamePeriodSum ) - SUM( CompPeriodSum ) != 0 AND SUM( CompPeriodSum ) != 0
			THEN (SUM( SamePeriodSum ) - SUM( CompPeriodSum )) / SUM( CompPeriodSum ) * 100 ELSE NULL
		END AS PeriodDiffPercentage,
		
		CASE WHEN SUM( SamePeriodQtySum ) - SUM( CompPeriodQtySum ) != 0 AND SUM( CompPeriodQtySum ) != 0
			THEN (SUM( SamePeriodQtySum ) - SUM( CompPeriodQtySum )) / SUM( CompPeriodQtySum ) * 100 ELSE NULL
		END AS PeriodQtyDiffPercentage,
		
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, currency, ad_org_id,
		3 AS UnionOrder
	FROM 	
		report.umsatzliste_bpartner_report_sub ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12)
	GROUP BY
		bp_name, 
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, currency, ad_org_id
ORDER BY
	bp_name, pc_name NULLS LAST, UnionOrder, p_name
$BODY$
LANGUAGE sql STABLE;







DROP FUNCTION IF EXISTS report.umsatzreport_week_report 
(
	IN Base_Year numeric, --$1
	IN Base_Week integer, --$2
	IN Comp_Year numeric, --$3
	IN Comp_Week integer,  --$4
	IN issotrx character varying, --$5
	IN M_AttributeSetInstance_ID numeric, --$6
	IN AD_Org_ID numeric --$7
);

DROP FUNCTION IF EXISTS report.Umsatzreport_week_Report_Sub 
(
	IN Base_Year numeric, --$1
	IN Base_Week integer, --$2
	IN Comp_Year numeric, --$3
	IN Comp_Week integer,  --$4
	IN issotrx character varying, --$5
	IN M_AttributeSetInstance_ID numeric, --$6
	IN AD_Org_ID numeric --$7
);


CREATE FUNCTION report.Umsatzreport_Week_Report_Sub
(
	IN Base_Year numeric, --$1
	IN Base_Week integer, --$2
	IN Comp_Year numeric, --$3
	IN Comp_Week integer,  --$4
	IN issotrx character varying, --$5
	IN M_AttributeSetInstance_ID numeric, --$6
	IN AD_Org_ID numeric --$7
) 
RETURNS TABLE 
(
	name character varying(60),
	Base_Week character varying(10),
	Comp_Week character varying(10),
	Base_Year character varying(10),
	Comp_Year character varying(10),

	base_week_sum numeric,
	comp_week_sum numeric,
	week_difference numeric,
	week_difference_percentage numeric,
	base_year_sum numeric,
	comp_year_sum numeric,

	year_difference numeric,
	year_difference_percentage numeric,
	attributesetinstance character varying(60),
	ad_org_id numeric
)

AS
$$
SELECT
	CASE WHEN Length(name) <= 45 THEN name ELSE substring(name FOR 43 ) || '...' END AS name,
	Base_Week,
	Comp_Week,
	Base_Year,
	Comp_Year,

	base_week_sum AS base_week_sum,
	comp_week_sum,
	base_week_sum - comp_week_sum AS week_difference,
	CASE WHEN base_week_sum - comp_week_sum != 0 AND comp_week_sum != 0
		THEN (base_week_sum - comp_week_sum) / comp_week_sum * 100 ELSE NULL
	END AS week_difference_percentage,
	base_year_sum AS base_year_sum,
	comp_year_sum AS comp_year_sum,
	base_year_sum - comp_year_sum AS Year_Difference,
	CASE WHEN base_year_sum - comp_year_sum != 0 AND comp_year_sum != 0
		THEN (base_year_sum - comp_year_sum) / comp_year_sum * 100 ELSE NULL
	END AS year_difference_percentage,


	Attributes as attributesetinstance,
	ad_org_id
FROM
	(
		SELECT
			bp.name,

			$2 || ' ' || (select fiscalyear from c_year where c_year_id =$1 AND isActive = 'Y') AS Base_Week,
			$4 || ' ' || (select fiscalyear from c_year where c_year_id =$3 AND isActive = 'Y') AS Comp_Week,

			y.fiscalYear AS Base_Year,
			cy.fiscalYear AS Comp_Year,

			SUM( CASE WHEN 
						fa.DateAcct::date >= (select (to_timestamp($2 || ' ' ||(select fiscalyear from c_year where c_year_id =$1 AND isActive = 'Y'),'IW IYYY')))::date AND
						fa.DateAcct::date <= ((select (to_timestamp($2 || ' ' ||(select fiscalyear from c_year where c_year_id =$1 AND isActive = 'Y'),'IW IYYY')))+ interval '6' day)::date
				THEN 	(CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END) ELSE 0 END ) 
			AS base_week_sum,
			
			SUM( CASE WHEN fap.C_Year_ID = $1 AND fa.DateAcct::date <= ((select (to_timestamp($2 || ' ' ||(select fiscalyear from c_year where c_year_id =$1 AND isActive = 'Y'),'IW IYYY')))+ interval '6' day)::date
				THEN (CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END) ELSE 0 END ) 
			AS base_year_sum,

			SUM( CASE WHEN 
						fa.DateAcct::date >= (select (to_timestamp($4 || ' ' ||(select fiscalyear from c_year where c_year_id =$3 AND isActive = 'Y'),'IW IYYY')))::date AND
						fa.DateAcct::date <= ((select (to_timestamp($4 || ' ' ||(select fiscalyear from c_year where c_year_id =$3 AND isActive = 'Y'),'IW IYYY')))+ interval '6' day)::date
				THEN 	(CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END) ELSE 0 END ) 
			AS comp_week_sum,

			SUM( CASE WHEN fap.C_Year_ID = $3 AND fa.DateAcct::date <= ((select (to_timestamp($4 || ' ' ||(select fiscalyear from c_year where c_year_id =$3 AND isActive = 'Y'),'IW IYYY')))+ interval '6' day)::date
				THEN (CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END) ELSE 0 END )   
			AS comp_year_sum,

			att.Attributes,
			fa.ad_org_id
		FROM
			Fact_Acct fa
			INNER JOIN C_Year y ON y.C_Year_ID = $1 AND y.isActive = 'Y'
			INNER JOIN C_Year cy ON cy.C_Year_ID = $3 AND cy.isActive = 'Y'
			JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
			JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'

			INNER JOIN C_Period fap ON fa.C_Period_ID = fap.C_Period_ID AND fap.isActive = 'Y'
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product pr ON fa.M_Product_ID = pr.M_Product_ID  
				AND pr.M_Product_Category_ID !=  getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
			INNER JOIN C_BPartner bp ON fa.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'

			LEFT OUTER JOIN	(
					SELECT 	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes, M_AttributeSetInstance_ID FROM Report.fresh_Attributes
					GROUP BY M_AttributeSetInstance_ID
					) att ON $6 = att.M_AttributeSetInstance_ID
		WHERE	
					AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
					AND IsSOtrx = $5 AND fa.isActive = 'Y'
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
						WHERE	a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
					)
					-- Dismiss lines where the Attributes in the Parameter are not in the InvoiceLine's asi
					AND NOT EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes pa
							LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
						WHERE	pa.M_AttributeSetInstance_ID = $6
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				-- ... else deactivate the filter 
				ELSE TRUE END
				)
			AND fa.ad_org_id = $7

		GROUP BY
			bp.name,
			y.fiscalYear,
			cy.fiscalYear,
			att.Attributes,
			fa.ad_org_id
	) a
ORDER BY
	base_year_sum DESC
$$
LANGUAGE sql STABLE;





DROP FUNCTION IF EXISTS report.umsatzreport_week_report 
(
	IN Base_Year numeric, --$1
	IN Base_Week integer, --$2
	IN Comp_Year numeric, --$3
	IN Comp_Week integer,  --$4
	IN issotrx character varying, --$5
	IN M_AttributeSetInstance_ID numeric, --$6
	IN AD_Org_ID numeric --$7
);



CREATE FUNCTION report.umsatzreport_week_report 
(
	IN Base_Year numeric, --$1
	IN Base_Week integer, --$2
	IN Comp_Year numeric, --$3
	IN Comp_Week integer,  --$4
	IN issotrx character varying, --$5
	IN M_AttributeSetInstance_ID numeric, --$6
	IN AD_Org_ID numeric --$7
)
 RETURNS TABLE
(
	name character varying(60),
	
	Base_Week character varying(10),
	Comp_Week character varying(10),
	Base_Year character varying(10),
	Comp_Year character varying(10),

	base_week_sum numeric,
	comp_week_sum numeric,
	week_difference numeric,
	week_difference_percentage numeric,
	base_year_sum numeric,
	comp_year_sum numeric,

	year_difference numeric,
	year_difference_percentage numeric,

	attributesetinstance character varying(60),
	ad_org_id numeric,
	unionorder integer
)

 AS
$BODY$
	SELECT *, 1 AS UnionOrder FROM report.Umsatzreport_Week_Report_Sub ($1, $2, $3, $4, $5, $6, $7)
UNION ALL
	SELECT 
		null as name, 
		Base_Week,
		Comp_Week,
		Base_Year,
		Comp_Year,
		SUM( base_week_sum ) AS base_week_sum,
		SUM( comp_week_sum ) AS comp_week_sum,
		SUM( base_week_sum ) - SUM( comp_week_sum ) AS week_difference,
		CASE WHEN SUM( base_week_sum ) - SUM( comp_week_sum ) != 0 AND SUM( comp_week_sum ) != 0
			THEN (SUM( base_week_sum ) - SUM( comp_week_sum ) ) / SUM( comp_week_sum ) * 100 ELSE NULL
		END AS week_difference_percentage,
		SUM( base_year_sum ) AS base_year_sum,
		SUM( comp_year_sum ) AS comp_year_sum,
		SUM( base_year_sum ) - SUM( comp_year_sum ) AS year_difference,
		CASE WHEN SUM( base_year_sum ) - SUM( comp_year_sum ) != 0 AND SUM( comp_year_sum ) != 0
			THEN (SUM( base_year_sum ) - SUM( comp_year_sum ) ) / SUM( comp_year_sum ) * 100 ELSE NULL
		END AS year_difference_percentage,
		attributesetinstance,
		ad_org_id,
		2 AS UnionOrder
		
	FROM 
		report.Umsatzreport_Week_Report_Sub ($1, $2, $3, $4, $5, $6, $7)
	GROUP BY
		Base_Week,
		Comp_Week,
		Base_Year,
		Comp_Year,
		attributesetinstance,
		ad_org_id
ORDER BY
	UnionOrder, base_year_sum DESC
$BODY$
LANGUAGE sql STABLE;







--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.request_report(IN ad_org_id numeric, IN R_RequestType_ID numeric, IN StartDate Date, IN EndDate Date, IN C_BPartner_ID numeric, IN QualityNote numeric, IN performanceType character varying(50), IN isMaterialReturned character(1),IN R_Resolution_ID numeric, IN R_Status_ID numeric, IN M_Product_ID numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.request_report(IN ad_org_id numeric, IN R_RequestType_ID numeric, IN StartDate Date, IN EndDate Date, IN C_BPartner_ID numeric, IN QualityNote numeric, IN performanceType character varying(50), IN isMaterialReturned character(1), IN R_Status_ID numeric, IN M_Product_ID numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.request_report(IN ad_org_id numeric, IN R_RequestType_ID numeric, IN StartDate Date, IN EndDate Date, IN C_BPartner_ID numeric, IN QualityNote numeric, IN performanceType character varying(50), IN isMaterialReturned character(1), IN R_Status_ID numeric, IN M_Product_ID numeric, IN AD_Language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.request_report(
	IN ad_org_id numeric, --$1
	IN R_RequestType_ID numeric, --$2
	IN StartDate Date, --$3
	IN EndDate Date, --$4
	IN C_BPartner_ID numeric, --$5
	IN QualityNote numeric, --$6
	IN performanceType character varying(50), --$7
	IN isMaterialReturned character(1), --$8
	IN R_Status_ID numeric, --$9
	IN M_Product_ID numeric, --$10
	IN AD_Language Character Varying (6) --$11 
	)

RETURNS TABLE
(
	--data
	Date date,
	BP_Value character varying(40),
	BP_Name character varying(60),
	IO_Docno character varying(30),
	P_Value character varying(40),
	P_Name character varying(255),
	QualityNote character varying(250),
	Performance character varying(50),
	isMaterialReturned character(1),
	lastresult character varying(200),
	Status character varying(60),
	--parameters
	p_doctype character varying,
	p_startdate text,
	p_enddate text,
	p_bpartner character varying,
	p_qualitynote character varying,
	p_performancetype character varying,
	p_ismaterialreturned character,
	p_status character varying,
	p_product character varying
)
AS
$$

SELECT 
	--data
	req.DateDelivered::Date as Date, 
	bp.Value AS BP_Value, 
	bp.Name AS BP_Name,
	First_agg ( DISTINCT io.DocumentNo ORDER BY io.DocumentNo ) ||
				CASE WHEN Count(DISTINCT io.documentNo) > 1 THEN ' ff.' ELSE '' END AS IO_Docno,
	p.Value AS P_Value,
	COALESCE(pt.Name,p.Name) AS P_Name,
	qn.Name AS QualityNote,
	req.performanceType AS Performance,
	req.isMaterialReturned AS isMaterialReturned,
	req.LastResult As lastresult,
	r.Name AS Status,
	--parameters
	(SELECT Name FROM R_RequestType WHERE R_RequestType_ID = $2 AND isActive = 'Y') AS p_doctype,
	to_char($3, 'DD.MM.YYYY') AS p_startdate,
	to_char($4, 'DD.MM.YYYY') AS p_enddate,

	(SELECT Name FROM C_BPartner WHERE C_BPartner_ID = $5 AND isActive = 'Y') AS p_bpartner,
	(SELECT Name FROM M_QualityNote WHERE M_QualityNote_ID = $6 AND isActive = 'Y') AS p_qualitynote,
	$7 AS p_performancetype,
	$8 AS p_ismaterialreturned,
	(SELECT Name FROM R_Status WHERE R_Status_ID = $9 AND isActive = 'Y') AS p_status,
	(SELECT COALESCE(pt.name, p.name) FROM M_Product p 
		LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $11 AND pt.isActive = 'Y'
		WHERE p.M_Product_ID = $10
	) AS p_product
	

FROM R_Request req

LEFT OUTER JOIN C_BPartner bp ON req.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
LEFT OUTER JOIN M_InOut io ON req.Record_ID = io.M_InOut_ID AND io.isActive = 'Y'
LEFT OUTER JOIN M_Product p ON req.M_Product_ID = p.M_Product_ID
LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $11 AND pt.isActive = 'Y'

LEFT OUTER JOIN R_Status r ON req.R_Status_ID = r.R_Status_ID AND r.isActive = 'Y'

LEFT OUTER JOIN M_QualityNote qn ON req.M_QualityNote_ID = qn.M_QualityNote_ID


WHERE 
	req.isActive = 'Y'
	AND req.DateDelivered::date >= $3 AND req.DateDelivered::date <= $4
	AND (CASE WHEN $1 IS NOT NULL THEN req.AD_Org_ID = $1 ELSE TRUE END)
	AND (CASE WHEN $2 IS NOT NULL THEN req.R_RequestType_ID = $2 ELSE TRUE END)
	AND (CASE WHEN $5 IS NOT NULL THEN req.C_BPartner_ID = $5 ELSE TRUE END)
	AND (CASE WHEN $6 IS NOT NULL THEN req.M_QualityNote_ID = $6 ELSE TRUE END)
	AND (CASE WHEN $7 IS NOT NULL THEN req.performanceType = $7 ELSE TRUE END)
	AND (CASE WHEN $8 IS NOT NULL THEN req.isMaterialReturned = $8 ELSE TRUE END)
	AND (CASE WHEN $9 IS NOT NULL THEN req.R_Status_ID = $9 ELSE TRUE END)
	AND (CASE WHEN $10 IS NOT NULL THEN req.M_Product_ID = $10 ELSE TRUE END)

GROUP BY  Date, 
	BP_Value, 
	BP_Name,
	P_Value,
	P_Name,
	QualityNote,
	Performance,
	isMaterialReturned,
	lastresult,
	Status,
	p_doctype,
	p_startdate,
	p_enddate,
	p_bpartner,
	p_qualitynote,
	p_performancetype,
	p_ismaterialreturned,
	p_status,
	p_product

ORDER BY req.DateDelivered::Date, 
	bp.Value, 
	bp.Name,
	IO_Docno,
	p.Value,
	P_Name
	
$$
LANGUAGE sql STABLE;	






DROP FUNCTION IF EXISTS report.fresh_statistics_kg_week ( 
	IN C_Year_ID numeric, IN week integer,IN issotrx character varying,IN C_Activity_ID numeric,IN M_Product_ID numeric, IN M_Product_Category_ID numeric, IN M_AttributeSetInstance_ID numeric, IN convert_to_kg character varying, IN ad_org_id numeric
);

DROP FUNCTION IF EXISTS report.fresh_statistics_kg_week ( 
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
);

DROP TABLE IF EXISTS report.fresh_statistics_kg_week;

/* ***************************************************************** */

CREATE TABLE report.fresh_statistics_kg_week
(
  pc_name character varying,
  p_name character varying,
  p_value character varying,
  uomsymbol character varying,
  col1 integer,
  col2 integer,
  col3 integer,
  col4 integer,
  col5 integer,
  col6 integer,
  col7 integer,
  col8 integer,
  col9 integer,
  col10 integer,
  col11 integer,
  col12 integer,
  week1sum numeric,
  week2sum numeric,
  week3sum numeric,
  week4sum numeric,
  week5sum numeric,
  week6sum numeric,
  week7sum numeric,
  week8sum numeric,
  week9sum numeric,
  week10sum numeric,
  week11sum numeric,
  week12sum numeric,
  totalsum numeric,
  totalamt numeric,
  startdate text,
  enddate text,
  param_activity character varying(60),
  param_product character varying(255),
  param_product_category character varying(60),
  param_attributes character varying(255),
  ad_org_id numeric,
  iso_code char(3)
)
WITH (
  OIDS=FALSE
);

/* ***************************************************************** */

CREATE FUNCTION report.fresh_statistics_kg_week
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
	pc.Name AS pc_name, 
	p.Name AS P_name,
	p.value AS P_value,
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
			fa.M_Product_ID,
			fa.UOMConv_ID,
			(SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0)) AS EndDate,
			(SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11)) AS StartDate,
			
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0)) AS Col1,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -1)) AS Col2,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -2)) AS Col3,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -3)) AS Col4,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -4)) AS Col5,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -5)) AS Col6,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -6)) AS Col7,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -7)) AS Col8,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -8)) AS Col9,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -9)) AS Col10,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -10)) AS Col11,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11)) AS Col12,
			
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
				THEN fa.QtyAcct ELSE 0 END ) AS Week1Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -1))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -1))
				THEN fa.QtyAcct ELSE 0 END ) AS Week2Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -2))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -2))
				THEN fa.QtyAcct ELSE 0 END ) AS Week3Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -3))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -3))
				THEN fa.QtyAcct ELSE 0 END ) AS Week4Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -4))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -4))
				THEN fa.QtyAcct ELSE 0 END ) AS Week5Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -5))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -5))
				THEN fa.QtyAcct ELSE 0 END ) AS Week6Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -6))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -6))
				THEN fa.QtyAcct ELSE 0 END ) AS Week7Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -7))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -7))
				THEN fa.QtyAcct ELSE 0 END ) AS Week8Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -8))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -8))
				THEN fa.QtyAcct ELSE 0 END ) AS Week9Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -9))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -9))
				THEN fa.QtyAcct ELSE 0 END ) AS Week10Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -10))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -10))
				THEN fa.QtyAcct ELSE 0 END ) AS Week11Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
				THEN fa.QtyAcct ELSE 0 END ) AS Week12Sum,
				
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
				THEN fa.QtyAcct ELSE 0 END ) AS TotalSum,
				
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
				THEN fa.AmtAcct ELSE 0 END ) AS TotalAmt,
			fa.ad_org_id,
			fa.iso_code

		FROM
			C_Year y
			
			LEFT OUTER JOIN 
			(
				SELECT *,
				CASE WHEN $8 = 'Y' AND fa.C_UOM_ID != uomkg AND convQty IS NOT NULL 
					THEN ABS(convQty) * SIGN(AmtAcct) 
				ELSE ABS(qty) * SIGN(AmtAcct) END
				AS QtyAcct, --QtyAcct is calculated in KG where it's possible, only if convert_to_kg = 'Y'
				CASE WHEN $8 = 'Y' AND fa.C_UOM_ID != uomkg AND convQty IS NOT NULL 
					THEN uomkg
				ELSE fa.C_UOM_ID END AS UOMConv_ID --convert uom in KG where it's possible, only if convert_to_kg = 'Y'
				FROM (	
					SELECT 	fa.*, CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct, uomconvert(fa.M_Product_ID, fa.C_UOM_ID,(SELECT C_UOM_ID as uom_conv FROM C_UOM WHERE x12de355='KGM' and IsActive='Y'),qty ) AS convQty, (SELECT C_UOM_ID as uom_conv FROM C_UOM WHERE x12de355='KGM' and IsActive='Y') AS uomkg, c.iso_code
					FROM 	Fact_Acct fa 
					JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
					INNER JOIN AD_Org o ON fa.ad_org_id = o.ad_org_id
					INNER JOIN AD_ClientInfo ci ON o.AD_Client_ID=ci.ad_client_id
					LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID=ci.C_AcctSchema1_ID
					LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID=c.C_Currency_ID
					WHERE	AD_Table_ID = (SELECT Get_Table_ID('C_Invoice')) AND fa.isActive = 'Y'	            
				) fa
			) fa ON true
			
			INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
			INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
		WHERE
			fa.AD_Table_ID = ( SELECT Get_Table_ID( 'C_Invoice' ) )
			-- Akontozahlung invoices are not included. See FRESH_609
			AND i.C_DocType_ID NOT IN (SELECT C_DocType_ID FROM C_DocType WHERE docbasetype='API' AND docsubtype='DP')
			AND y.C_Year_ID = $1
			AND i.IsSOtrx = $3
			AND ( CASE WHEN $4 IS NULL THEN TRUE ELSE fa.C_Activity_ID = $4 END )
			AND ( CASE WHEN $5 IS NULL THEN TRUE ELSE p.M_Product_ID = $5 END )
			AND ( CASE WHEN $6 IS NULL THEN TRUE ELSE p.M_Product_Category_ID = $6 END 
				-- It was a requirement to not have HU Packing material within the sums of the Statistics reports 
				AND p.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
			)
			AND ( 
				-- If the given attribute set instance has values set... 
				CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $7 )
				-- ... then apply following filter:
				THEN ( 
					-- Take lines where the attributes of the current InvoiceLine's asi are in the parameter asi and their Values Match
					EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes a -- a = Attributes from invoice line, pa = Parameter Attributes
							INNER JOIN report.fresh_Attributes pa ON pa.M_AttributeSetInstance_ID = $7
								AND a.at_value = pa.at_value -- same attribute
								AND a.ai_value = pa.ai_value -- same value
						WHERE	a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
					)
					-- Dismiss lines where the Attributes in the Parameter are not in the InvoiceLine's asi
					AND NOT EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes pa
							LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
						WHERE	pa.M_AttributeSetInstance_ID = $7
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				-- ... else deactivate the filter 
				ELSE TRUE END
			)
			AND fa.ad_org_id = $9
		GROUP BY
			fa.M_Product_ID,
			fa.UOMConv_ID, 
			fa.AD_Org_ID,
			y.fiscalyear,
			fa.iso_code

	) a
	INNER JOIN C_UOM uom ON a.UOMConv_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $10 AND uomt.isActive = 'Y'
	INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $10 AND pt.isActive = 'Y'
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	
ORDER BY 
	pc.value, p.name
	
$$
LANGUAGE sql STABLE;






DROP FUNCTION IF EXISTS report.fresh_statistics_week ( 
	IN C_Year_ID numeric, IN week integer, IN issotrx character varying, IN C_BPartner_ID numeric, IN C_Activity_ID numeric,
	IN M_Product_ID numeric, IN M_Product_Category_ID numeric, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric
);

DROP FUNCTION IF EXISTS report.fresh_statistics_week ( 
	IN C_Year_ID numeric, IN week integer, IN issotrx character varying, IN C_BPartner_ID numeric, IN C_Activity_ID numeric,
	IN M_Product_ID numeric, IN M_Product_Category_ID numeric, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric, IN AD_Language Character Varying (6)
);

DROP TABLE IF EXISTS report.fresh_statistics_week;

/* ***************************************************************** */

CREATE TABLE report.fresh_statistics_week
(
  bp_name character varying,
  bp_value character varying,
  pc_name character varying,
  p_name character varying,
  p_value character varying,
  uomsymbol character varying,
  col1 integer,
  col2 integer,
  col3 integer,
  col4 integer,
  col5 integer,
  col6 integer,
  col7 integer,
  col8 integer,
  col9 integer,
  col10 integer,
  col11 integer,
  col12 integer,
  Week1sum numeric,
  Week2sum numeric,
  Week3sum numeric,
  Week4sum numeric,
  Week5sum numeric,
  Week6sum numeric,
  Week7sum numeric,
  Week8sum numeric,
  Week9sum numeric,
  Week10sum numeric,
  Week11sum numeric,
  Week12sum numeric,
  totalsum numeric,
  totalamt numeric,
  startdate text,
  enddate text,
  param_bp character varying(60),
  param_activity character varying(60),
  param_product character varying(255),
  param_product_category character varying(60),
  param_attributes character varying(255),
  ad_org_id numeric,
  iso_code char(3)
)
WITH (
  OIDS=FALSE
);

/* ***************************************************************** */

CREATE FUNCTION report.fresh_statistics_week
	(
		IN C_Year_ID numeric,
		IN week integer, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric,
		IN AD_Language Character Varying (6) 
	) 
  RETURNS SETOF report.fresh_statistics_week AS
$BODY$
SELECT
	bp.Name AS bp_name,
	bp.value AS bp_Value,
	pc.Name AS pc_name, 
	COALESCE(pt.Name, p.Name) AS P_name,
	p.value AS P_value,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
	Week1Sum, Week2Sum, Week3Sum, Week4Sum, Week5Sum, Week6Sum, Week7Sum, Week8Sum, Week9Sum, Week10Sum, Week11Sum, Week12Sum,
	TotalSum, TotalAmt,
	to_char( StartDate, 'DD.MM.YYYY' ) AS StartDate,
	to_char( EndDate, 'DD.MM.YYYY' ) AS EndDate,
	(SELECT name FROM C_BPartner WHERE C_BPartner_ID = $4) AS param_bp,
	(SELECT name FROM C_Activity WHERE C_Activity_ID = $5) AS param_Activity,
	(SELECT COALESCE(pt.name, p.name) FROM M_Product p 
		LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $10 AND pt.isActive = 'Y'
		WHERE p.M_Product_ID = $6
	) AS param_product,
	(SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $7) AS param_Product_Category,
	(SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $8) AS Param_Attributes,
	a.ad_org_id,
	a.iso_code

FROM
	(
		SELECT
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			fa.C_UOM_ID, 
			(SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0)) AS EndDate,
			(SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11)) AS StartDate,
			
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0)) AS Col1,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -1)) AS Col2,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -2)) AS Col3,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -3)) AS Col4,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -4)) AS Col5,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -5)) AS Col6,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -6)) AS Col7,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -7)) AS Col8,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -8)) AS Col9,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -9)) AS Col10,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -10)) AS Col11,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11)) AS Col12,
	
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
				THEN fa.QtyAcct ELSE 0 END ) AS Week1Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -1))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -1))
				THEN fa.QtyAcct ELSE 0 END ) AS Week2Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -2))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -2))
				THEN fa.QtyAcct ELSE 0 END ) AS Week3Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -3))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -3))
				THEN fa.QtyAcct ELSE 0 END ) AS Week4Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -4))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -4))
				THEN fa.QtyAcct ELSE 0 END ) AS Week5Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -5))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -5))
				THEN fa.QtyAcct ELSE 0 END ) AS Week6Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -6))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -6))
				THEN fa.QtyAcct ELSE 0 END ) AS Week7Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -7))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -7))
				THEN fa.QtyAcct ELSE 0 END ) AS Week8Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -8))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -8))
				THEN fa.QtyAcct ELSE 0 END ) AS Week9Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -9))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -9))
				THEN fa.QtyAcct ELSE 0 END ) AS Week10Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -10))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -10))
				THEN fa.QtyAcct ELSE 0 END ) AS Week11Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
				THEN fa.QtyAcct ELSE 0 END ) AS Week12Sum,


			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
				THEN fa.QtyAcct ELSE 0 END ) AS TotalSum,
				
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
				THEN fa.AmtAcct ELSE 0 END ) AS TotalAmt,
			fa.ad_org_id,
			fa.iso_code

	
		FROM
			C_Year y
			
			LEFT OUTER JOIN 
			(
				SELECT *, ABS(qty) * SIGN(AmtAcct) AS QtyAcct
				FROM (	
					SELECT 	fa.*, CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct, c.iso_code 
					FROM 	Fact_Acct fa 
					JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID
					INNER JOIN AD_Org o ON fa.ad_org_id = o.ad_org_id
					INNER JOIN AD_ClientInfo ci ON o.AD_Client_ID=ci.ad_client_id
					LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID=ci.C_AcctSchema1_ID
					LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID=c.C_Currency_ID
					WHERE	AD_Table_ID = (SELECT Get_Table_ID('C_Invoice')) AND fa.isActive = 'Y'
				) fa
			) fa ON true
			INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
			INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
			
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
		WHERE
			fa.AD_Table_ID = ( SELECT Get_Table_ID( 'C_Invoice' ) )
			-- Akontozahlung invoices are not included. See FRESH_609
			AND i.C_DocType_ID NOT IN (SELECT C_DocType_ID FROM C_DocType WHERE docbasetype='API' AND docsubtype='DP')
			AND y.C_Year_ID = $1
			AND i.IsSOtrx = $3
			AND ( CASE WHEN $4 IS NULL THEN TRUE ELSE fa.C_BPartner_ID = $4 END )
			AND ( CASE WHEN $5 IS NULL THEN TRUE ELSE fa.C_Activity_ID = $5 END )
			AND ( CASE WHEN $6 IS NULL THEN TRUE ELSE p.M_Product_ID = $6 END )
			AND ( CASE WHEN $7 IS NULL THEN TRUE ELSE p.M_Product_Category_ID = $7 END 
				-- It was a requirement to not have HU Packing material within the sums of the Statistics reports 
				AND p.M_Product_Category_ID !=  getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
			)
			AND ( 
				-- If the given attribute set instance has values set... 
				CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $8 )
				-- ... then apply following filter:
				THEN ( 
					-- Take lines where the attributes of the current InvoiceLine's asi are in the parameter asi and their Values Match
					EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes a -- a = Attributes from invoice line, pa = Parameter Attributes
							INNER JOIN report.fresh_Attributes pa ON pa.M_AttributeSetInstance_ID = $8 
								AND a.at_value = pa.at_value -- same attribute
								AND a.ai_value = pa.ai_value -- same value
						WHERE	a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
					)
					-- Dismiss lines where the Attributes in the Parameter are not in the InvoiceLine's asi
					AND NOT EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes pa
							LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
						WHERE	pa.M_AttributeSetInstance_ID = $8
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				-- ... else deactivate the filter 
				ELSE TRUE END
			)
			AND fa.ad_org_id = $9
		GROUP BY
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			fa.C_UOM_ID,
			y.fiscalyear,
			fa.ad_org_id,
			fa.iso_code
			
	
	) a
	INNER JOIN C_UOM uom ON a.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $10 AND uomt.isActive = 'Y'
	INNER JOIN C_BPartner bp ON a.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $10 AND pt.isActive = 'Y'
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
ORDER BY
	bp.Name, pc.value, p.name
$BODY$
LANGUAGE sql STABLE;







-- Function: de_metas_endcustomer_fresh_reports.trace_report(numeric, numeric, numeric, numeric, numeric, numeric, character varying, numeric)

 DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.trace_report(numeric, numeric, numeric, numeric, numeric, numeric, character varying, numeric);
 
 DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.trace_report(numeric, numeric, numeric, numeric, numeric, numeric, character varying, numeric, character varying (3));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.trace_report(
    IN ad_org_id numeric,
    IN c_period_st_id numeric,
    IN c_period_end_id numeric,
    IN c_activity_id numeric,
    IN c_bpartner_id numeric,
    IN m_product_id numeric,
    IN issotrx character varying,
    IN m_attributesetinstance_id numeric,
    IN AD_Language Character Varying (6))
  RETURNS TABLE(
		dateordered timestamp without time zone, 
		documentno character varying, 
		o_bp_value character varying, 
		o_bp_name character varying, 
		o_p_value character varying, 
		o_p_name character varying, 
		attributes text, 
		price numeric, 
		total numeric, 
		currency character, 
		o_uom character varying, 
		o_qty numeric, 
		movementdate timestamp without time zone, 
		orderdocumentno character varying, 
		io_bp_value character varying, 
		io_bp_name character varying, 
		io_qty numeric, 
		io_uom character varying,
		isPOfromSO character varying
		) 
AS
$$

SELECT 
	a.DateOrdered,
	a.oDocumentNo,
	a.bp_value,
	a.bp_name,
	a.p_value,
	a.p_name,
	a.attributes,
	a.price,	
	a.total,
	a.currency,
	a.uomsymbol,
	a.qty,

	--inout part
	a.movementdate,
	a.orderdocumentno,
	a.io_bp_value,
	a.io_bp_name,
	SUM(a.io_qty),
	a.io_uom,
	'N' AS isPOfromSO
FROM (
SELECT distinct
	o.DateOrdered,
	o.DocumentNo AS ODocumentNo,
	bp.Value AS bp_value,
	bp.Name AS bp_name,
	p.Value AS p_value,
	COALESCE(pt.Name, p.Name) AS p_name,
	(select attributes_value from de_metas_endcustomer_fresh_reports.get_attributes_value(o_iol.M_AttributeSetInstance_ID)) AS attributes,
	ol.PriceEntered AS price,	
	ol.linenetamt AS total,
	c.iso_code AS currency,
	COALESCE(uomt.uomsymbol, uom.uomsymbol) AS uomsymbol,
	ol.qtyEntered AS qty,

	--inout part
	c_io.movementdate,
	c_o.documentno AS orderdocumentno,
	c_bp.value as io_bp_value,
	c_bp.name as io_bp_name,
	c_iol.qtyentered as io_qty,
	COALESCE(c_uomt.uomsymbol, c_uom.uomsymbol) as io_uom,

	--order and sales inout docno (for tracing purpose). Not used in reports
	c_io.DocumentNo AS counterdocno
	
	

FROM C_Order o

INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID AND ol.isActive = 'Y'
INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $9 AND pt.isActive = 'Y'

LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
INNER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $9 AND uomt.isActive = 'Y'
INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

		
INNER JOIN C_Period per_st ON $2 = per_st.C_Period_ID AND per_st.isActive = 'Y'
INNER JOIN C_Period per_end ON $3 = per_end.C_Period_ID AND per_end.isActive = 'Y'

--order's inout and hus
INNER JOIN M_InOutLine o_iol ON ol.C_OrderLine_ID = o_iol.C_OrderLine_ID AND o_iol.isActive = 'Y'
INNER JOIN M_InOut o_io ON o_iol.M_InOut_ID = o_io.M_InOut_ID AND o_io.isActive = 'Y'
INNER JOIN M_HU_Assignment huas ON huas.ad_table_id = get_table_id('M_InOutLine') AND huas.Record_ID = o_iol.M_InOutLine_ID AND huas.isActive = 'Y'

--counter inout's hus and inout 
								
INNER JOIN M_InOutLine c_iol ON c_iol.M_InOutLine_ID = ANY (ARRAY(SELECT Record_ID from "de.metas.handlingunits".hu_assigment_tracking(huas.m_hu_assignment_id) )) AND c_iol.isActive = 'Y'
INNER JOIN M_InOut c_io ON c_iol.M_InOut_ID = c_io.M_InOut_ID AND c_io.isSOTrx != o_io.isSOTrx AND c_io.isActive = 'Y'
INNER JOIN C_InvoiceCandidate_InOutLine iciol ON c_iol.M_InOutLine_ID = iciol.M_InOutLine_ID AND iciol.isActive = 'Y'
INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ic.isActive = 'Y'


--data for inout 
INNER JOIN C_OrderLine c_ol ON c_iol.C_OrderLine_ID = c_ol.C_OrderLine_ID AND c_ol.isActive = 'Y'
INNER JOIN C_Order c_o ON c_ol.C_Order_ID = c_o.C_Order_ID AND c_o.isActive = 'Y'
INNER JOIN C_BPartner c_bp ON c_bp.C_BPartner_ID = c_io.C_BPartner_ID AND c_bp.isActive = 'Y'
INNER JOIN C_UOM c_uom ON ic.price_UOM_ID = c_uom.C_UOM_ID AND c_uom.isActive = 'Y'
LEFT OUTER JOIN C_UOM_Trl c_uomt ON c_uom.C_UOM_ID = c_uomt.C_UOM_ID AND c_uomt.AD_Language = $9 AND c_uomt.isActive = 'Y'


WHERE 
	o.AD_Org_ID = (CASE WHEN $1 IS NULL THEN o.AD_Org_ID ELSE $1 END)
	AND per_st.startdate::date <= o.DateOrdered::date
	AND per_end.enddate::date >= o.DateOrdered::date
	AND ol.C_Activity_ID = (CASE WHEN $4 IS NULL THEN ol.C_Activity_ID ELSE $4 END)
	AND o.C_BPartner_ID = (CASE WHEN $5 IS NULL THEN o.C_BPartner_ID ELSE $5 END)
	AND ol.M_Product_ID = (CASE WHEN $6 IS NULL THEN ol.M_Product_ID ELSE $6 END)
	AND o.isSOTrx= $7
	AND o.isActive ='Y'
	AND o.docStatus IN ('CO', 'CL')
	AND o_io.docStatus IN ('CO', 'CL')
	AND c_o.docStatus IN ('CO', 'CL')
	AND c_io.docStatus IN ('CO', 'CL')
	AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
	
	AND
		(CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $8 )
			-- ... then apply following filter:
			THEN ( 
			-- Take lines where the attributes of the current InoutLine's asi are in the parameter asi and their Values Match
				EXISTS (
					SELECT	0
					FROM	report.fresh_Attributes_ConcreteADR a -- a = Attributes from inout line, pa = Parameter Attributes
					INNER JOIN report.fresh_Attributes_ConcreteADR pa ON pa.M_AttributeSetInstance_ID =6487321
					AND a.at_value = pa.at_value -- same attribute
					AND (CASE WHEN a.at_value = '1000015' THEN  ('%'||substring(a.ai_value from 5)||'%' like '%'||substring(pa.ai_value from 5)||'%' OR '%'||substring(pa.ai_value from 5)||'%' like '%'||substring(a.ai_value from 5)||'%' ) --case of adr containing similar value
					ELSE a.ai_value = pa.ai_value END)  --same value
					WHERE	a.M_AttributeSetInstance_ID = o_iol.M_AttributeSetInstance_ID
					)
					-- Dismiss lines where the Attributes in the Parameter are not in the inoutline's asi
				AND NOT EXISTS (
					SELECT	0
					FROM	report.fresh_Attributes_ConcreteADR pa
						LEFT OUTER JOIN report.fresh_Attributes_ConcreteADR a 
						ON a.at_value = pa.at_value AND
						(CASE WHEN a.at_value = '1000015' THEN  ('%'||substring(a.ai_value from 5)||'%' like '%'||substring(pa.ai_value from 5)||'%' OR '%'||substring(pa.ai_value from 5)||'%' like '%'||substring(a.ai_value from 5)||'%' ) --case of adr containing similar value
					ELSE a.ai_value = pa.ai_value END)
							AND a.M_AttributeSetInstance_ID = o_iol.M_AttributeSetInstance_ID
					WHERE	pa.M_AttributeSetInstance_ID = $8
						AND a.M_AttributeSetInstance_ID IS null
						)
					)	
		ELSE TRUE END)
		
	
)a

GROUP BY 
	a.DateOrdered,
	a.oDocumentNo,
	a.bp_value,
	a.bp_name,
	a.p_value,
	a.p_name,
	a.attributes,
	a.price,	
	a.currency,
	a.uomsymbol,
	a.total,
	a.qty,
	--inout part
	a.movementdate,
	a.orderdocumentno,
	a.io_bp_value,
	a.io_bp_name,
	a.io_uom



UNION 

(
select 	
	o.DateOrdered,
	o.DocumentNo AS ODocumentNo,
	bp.Value AS bp_value,
	bp.Name AS bp_name,
	p.Value AS p_value,
	COALESCE(pt.Name, p.Name) AS p_name,
	(select attributes_value from de_metas_endcustomer_fresh_reports.get_attributes_value(ol.M_AttributeSetInstance_ID)) AS attributes,
	ol.PriceEntered AS price,	
	ol.linenetamt AS total,
	c.iso_code AS currency,
	COALESCE(uomt.uomsymbol, uom.uomsymbol) AS uomsymbol,
	ol.qtyEntered AS qty,

	--inout part
	c_o.dateordered as movementdate,
	c_o.documentno AS orderdocumentno,
	c_bp.value as io_bp_value,
	c_bp.name as io_bp_name,
	c_ol.qtyentered as io_qty,
	COALESCE(c_uomt.uomsymbol, c_uom.uomsymbol) as io_uom,
	'Y' AS isPOfromSO

	
from C_Order o
join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID AND ol.isActive = 'Y'
join C_OrderLine c_ol on (ol.C_OrderLine_ID = c_ol.Link_OrderLine_ID OR c_ol.C_OrderLine_ID = ol.Link_OrderLine_ID) AND c_ol.isActive = 'Y'
join C_Order c_o on c_ol.C_Order_ID = c_o.C_Order_ID AND c_o.isActive = 'Y'

INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $9 AND pt.isActive = 'Y'
LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
INNER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $9 AND uomt.isActive = 'Y'
INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

		
INNER JOIN C_Period per_st ON $2 = per_st.C_Period_ID AND per_st.isActive = 'Y'
INNER JOIN C_Period per_end ON $3 = per_end.C_Period_ID AND per_end.isActive = 'Y'

INNER JOIN C_BPartner c_bp ON c_bp.C_BPartner_ID = c_o.C_BPartner_ID AND c_bp.isActive = 'Y'
INNER JOIN C_UOM c_uom ON c_ol.price_UOM_ID = c_uom.C_UOM_ID AND c_uom.isActive = 'Y'
LEFT OUTER JOIN C_UOM_Trl c_uomt ON c_uom.C_UOM_ID = c_uomt.C_UOM_ID AND c_uomt.AD_Language = $9 AND c_uomt.isActive = 'Y'

WHERE 
	o.AD_Org_ID = (CASE WHEN $1 IS NULL THEN o.AD_Org_ID ELSE $1 END)
	AND per_st.startdate::date <= o.DateOrdered::date
	AND per_end.enddate::date >= o.DateOrdered::date
	AND ol.C_Activity_ID = (CASE WHEN $4 IS NULL THEN ol.C_Activity_ID ELSE $4 END)
	AND o.C_BPartner_ID = (CASE WHEN $5 IS NULL THEN o.C_BPartner_ID ELSE $5 END)
	AND ol.M_Product_ID = (CASE WHEN $6 IS NULL THEN ol.M_Product_ID ELSE $6 END)
	AND o.isSOTrx= $7
	AND o.isActive ='Y'
	AND o.docStatus IN ('CO', 'CL')
	AND c_o.docStatus IN ('CO', 'CL')
	AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
	
	AND
		(CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $8 )
			-- ... then apply following filter:
			THEN ( 
			-- Take lines where the attributes of the current InoutLine's asi are in the parameter asi and their Values Match
				EXISTS (
					SELECT	0
					FROM	report.fresh_Attributes_ConcreteADR a -- a = Attributes from order line, pa = Parameter Attributes
					INNER JOIN report.fresh_Attributes_ConcreteADR pa ON pa.M_AttributeSetInstance_ID = $8
					AND a.at_value = pa.at_value -- same attribute
					AND (CASE WHEN a.at_value = '1000015' THEN  ('%'||substring(a.ai_value from 5)||'%' like '%'||substring(pa.ai_value from 5)||'%' OR '%'||substring(pa.ai_value from 5)||'%' like '%'||substring(a.ai_value from 5)||'%' ) --case of adr containing similar value
					ELSE a.ai_value = pa.ai_value END)  --same value
					WHERE	a.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
					)
					-- Dismiss lines where the Attributes in the Parameter are not in the inoutline's asi
				AND NOT EXISTS (
					SELECT	0
					FROM	report.fresh_Attributes_ConcreteADR pa
						LEFT OUTER JOIN report.fresh_Attributes_ConcreteADR a 
						ON a.at_value = pa.at_value AND
						(CASE WHEN a.at_value = '1000015' THEN  ('%'||substring(a.ai_value from 5)||'%' like '%'||substring(pa.ai_value from 5)||'%' OR '%'||substring(pa.ai_value from 5)||'%' like '%'||substring(a.ai_value from 5)||'%' ) --case of adr containing similar value
					ELSE a.ai_value = pa.ai_value END)
							AND a.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
					WHERE	pa.M_AttributeSetInstance_ID = $8
						AND a.M_AttributeSetInstance_ID IS null
						)
					)	
		ELSE TRUE END)
		



ORDER BY 

	DateOrdered,
	oDocumentNo,
	bp_value,
	p_value,
	movementdate,
	orderdocumentno,
	io_bp_value
	
)
	
$$
  LANGUAGE sql STABLE;






DROP FUNCTION IF EXISTS report.umsatzliste_report_details(IN C_BPartner_ID numeric, IN StartDate date, IN EndDate date, IN isSOTrx char(1));
DROP FUNCTION IF EXISTS report.umsatzliste_report_details(IN C_BPartner_ID numeric, IN StartDate date, IN EndDate date, IN isSOTrx char(1), IN ad_org_id numeric);

CREATE FUNCTION report.umsatzliste_report_details(IN C_BPartner_ID numeric, IN StartDate date, IN EndDate date, IN isSOTrx char(1), IN ad_org_id numeric) RETURNS TABLE
	(
	BP_Value Character Varying,
	BP_Name Character Varying, 
	P_Value Character Varying,
	P_Name Character Varying,
	TotalInvoiced numeric,
	TotalShipped numeric,
	TotalOrdered numeric,
	IsPackingMaterial boolean,
	Month timestamp with time zone,
	ISO_Code char(3),
	ad_org_id numeric
	)
AS 
$$
SELECT
	bp.Value AS BP_Value,
	bp.Name AS BP_Name,
	p.value AS P_Value,
	p.Name AS P_Name,
	SUM( ic.QtyInvoiced * ic.PriceActual ) AS TotalInvoiced,
	SUM( ic.QtyInvoicable * ic.PriceActual ) AS TotalShipped,
	SUM( CASE WHEN s_Ordered != Sign( ic.QtyOpen ) THEN 0 ELSE ic.QtyOpen END * ic.PriceActual ) AS TotalOrdered,
	p.M_Product_Category_ID =  getSysConfigAsNumeric('PackingMaterialProductCategoryID', ic.AD_Client_ID, ic.AD_Org_ID) AS IsPackingMaterial,
	date_trunc( 'month', ic.Date ) AS Month,
	c.ISO_Code,
	ic.ad_org_id
FROM
	(
		SELECT
			ic.Bill_BPartner_ID, ic.M_Product_ID,
			-- Quantities
			icq.QtyInvoiced,
			icq.QtyInvoicable,
			icq.QtyOrderedInPriceUOM - icq.QtyInvoicable - icq.QtyInvoiced AS QtyOpen,
			sign( icq.QtyOrderedInPriceUOM ) AS s_Ordered,
			-- PriceActual_Net converted to base Currency
			CurrencyBase( ic.PriceActual_Net_Effective, ic.C_Currency_ID, now(), ic.AD_Client_ID, ic.AD_Org_ID ) AS PriceActual,
			ac.C_Currency_ID AS Base_Currency_ID,

			-- Date for filtering
			COALESCE ( i.DateAcct, ic.DeliveryDate, icq.DatePromised ) AS Date,
			ic.AD_Org_ID, ic.AD_Client_ID
		FROM
			C_Invoice_Candidate ic
			INNER JOIN (
				SELECT
					C_Invoice_Candidate_ID,
					-- Invoiced or flatrate invoiced
					ic.QtyInvoiced,
					-- Shipped, not invoiced. If, due to subtration, the sign of the number changed, it is considered 0
					CASE WHEN s_Delivered != Sign( ic.QtyInvoicable ) THEN 0 ELSE ic.QtyInvoicable END AS QtyInvoicable,
					-- Ordered, Calculation must be done later. If the datePromised hasn't passed, QtyOrdered is considered 0
					ic.QtyOrderedInPriceUOM * CASE WHEN ol.DatePromised::date <= Now()::date THEN 1 ELSE 0 END AS QtyOrderedInPriceUOM,
					ol.DatePromised
				FROM
					(
						SELECT 	ic.C_Invoice_Candidate_ID, Record_ID, AD_Table_ID,
							COALESCE( CASE WHEN isToClear = 'N'
									THEN QtyInvoicedInPriceUOM
									ELSE 0
								END, 0) AS QtyInvoiced,
						COALESCE( uomconvert(ic.m_product_id, ic.c_uom_id, ic.price_uom_id, QtyOrdered),0) AS QtyOrderedInPriceUOM,
							sign( uomconvert(ic.m_product_id, ic.c_uom_id, ic.price_uom_id, QtyDelivered)) AS s_Delivered,
							COALESCE( uomconvert(ic.m_product_id, ic.c_uom_id, ic.price_uom_id, QtyDelivered - QtyInvoiced), 0) AS QtyInvoicable
						FROM 	C_Invoice_Candidate ic
							LEFT OUTER JOIN (
								SELECT	ila.C_Invoice_Candidate_ID, SUM(QtyInvoicedInPriceUOM) as QtyInvoicedInPriceUOM
								FROM	C_Invoice_Line_Alloc ila
									LEFT OUTER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
								WHERE ila.isActive = 'Y'
								GROUP BY	ila.C_Invoice_Candidate_ID
							) il ON ic.C_Invoice_Candidate_ID = il.C_Invoice_Candidate_ID
						WHERE 	IsSOTrx = $4
							AND ( CASE WHEN $1 IS NULL THEN TRUE ELSE $1 = Bill_BPartner_ID END )
							AND PriceActual_Net_Effective != 0
							AND ic.isActive = 'Y'
					) ic
					LEFT OUTER JOIN C_Orderline ol ON ic.Record_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
						AND ic.AD_Table_ID = ((SELECT Get_Table_ID('C_OrderLine')))
			) icq ON ic.C_Invoice_Candidate_ID = icq.C_Invoice_Candidate_ID
			INNER JOIN AD_ClientInfo ci ON ic.AD_Client_ID = ci.AD_Client_ID AND ci.isActive = 'Y'
			INNER JOIN C_AcctSchema ac ON ci.C_AcctSchema1_ID = ac.C_AcctSchema_ID AND ac.isActive = 'Y'
			LEFT OUTER JOIN
			(
				SELECT 	ila.C_Invoice_Candidate_ID,
					First_agg( i.DateAcct::text ORDER BY i.DateAcct)::Date AS DateAcct
				FROM 	C_Invoice_Line_Alloc ila
					LEFT OUTER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
					LEFT OUTER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
				WHERE  ila.isActive = 'Y'
				GROUP BY	ila.C_Invoice_Candidate_ID
			) i ON ic.C_Invoice_Candidate_ID = i.C_Invoice_Candidate_ID
	WHERE ic.isActive = 'Y'
	) ic
	INNER JOIN C_BPartner bp ON ic.Bill_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	INNER JOIN M_Product p ON ic.M_Product_ID = p.M_Product_ID
	INNER JOIN C_Currency c ON ic.Base_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
WHERE
	ic.AD_Org_ID = $5
	AND Date::date >= $2 AND Date::date <= $3
	AND COALESCE( ic.PriceActual, 0 ) != 0
	AND ( ic.QtyInvoiced != 0 OR ic.QtyInvoicable != 0
	OR (CASE WHEN s_Ordered != Sign( ic.QtyOpen ) THEN 0 ELSE ic.QtyOpen END) != 0 )
	
GROUP BY
	bp.Value,
	bp.Name,
	p.value,
	p.Name,
	p.M_Product_Category_ID,
	date_trunc( 'month', ic.Date ),
	c.ISO_Code,
	ic.ad_org_id,
	ic.ad_client_id
ORDER BY
	date_trunc( 'month', ic.Date ),
	bp.Value,
	p.M_Product_Category_ID =
		 getSysConfigAsNumeric('PackingMaterialProductCategoryID', ic.AD_Client_ID, ic.AD_Org_ID)

$$
  LANGUAGE sql STABLE;






