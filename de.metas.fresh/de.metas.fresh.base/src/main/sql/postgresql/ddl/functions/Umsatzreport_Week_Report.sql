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
				AND pr.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y') AND pr.isActive = 'Y'
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

