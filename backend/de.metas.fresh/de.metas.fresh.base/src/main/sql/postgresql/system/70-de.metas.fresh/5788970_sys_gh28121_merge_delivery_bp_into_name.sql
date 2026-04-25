-- gh#28121 Phase 2: Merge delivery recipient into billing partner name column
--
-- Instead of displaying delivery_bp_name as a separate column,
-- the billing partner name now shows:
--   Same or no delivery recipient: "BillingPartner"
--   Different delivery recipient:  "BillingPartner (DeliveryRecipient)"
--   Too long (>45 chars):          "BillingPartn… (DeliveryRec…)"


-- ==========================================================================
-- Step 1: Helper function for smart name merging with truncation
-- ==========================================================================
CREATE OR REPLACE FUNCTION report._merge_bp_name(billing_bp text, delivery_bp text)
RETURNS text AS
$$
SELECT CASE
    WHEN delivery_bp IS NULL OR delivery_bp = billing_bp
    THEN CASE WHEN length(billing_bp) <= 45 THEN billing_bp ELSE left(billing_bp, 44) || '…' END
    WHEN length(billing_bp) + length(delivery_bp) + 3 <= 45
    THEN billing_bp || ' (' || delivery_bp || ')'
    ELSE left(billing_bp, 24) || '… (' || left(delivery_bp, 14) || '…)'
END
$$
LANGUAGE sql IMMUTABLE;


-- ==========================================================================
-- Step 2: Period report - Umsatzreport_Report_Sub
-- Changed: name expression uses report._merge_bp_name instead of simple truncation
-- ==========================================================================
CREATE OR REPLACE FUNCTION report.Umsatzreport_Report_Sub(IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric) RETURNS SETOF report.Umsatzreport_Report_Sub AS
$BODY$
SELECT
	report._merge_bp_name(name, delivery_bp_name) AS name,
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
	Attributes as attributesetinstance,
	ad_org_id,
	delivery_bp_name
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
			att.Attributes,
			fa.ad_org_id,
			COALESCE(
				CASE WHEN ord.IsDropShip = 'Y' THEN bp_dropship.Name END,
				bp_orderer.Name
			) AS delivery_bp_name
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
					il.M_AttributeSetInstance_ID, fa.ad_org_id, fa.AD_Client_ID,
				il.C_OrderLine_ID
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
				AND pr.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', fa.AD_Client_ID, fa.AD_Org_ID) AND pr.isActive = 'Y'
			INNER JOIN C_BPartner bp ON fa.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'

			LEFT OUTER JOIN	(
					SELECT 	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes, M_AttributeSetInstance_ID FROM Report.fresh_Attributes
					GROUP BY M_AttributeSetInstance_ID
					) att ON $3 = att.M_AttributeSetInstance_ID

			-- DropShip / delivery recipient joins
			LEFT JOIN C_OrderLine ol_ds ON fa.C_OrderLine_ID = ol_ds.C_OrderLine_ID AND ol_ds.isActive = 'Y'
			LEFT JOIN C_Order ord ON ol_ds.C_Order_ID = ord.C_Order_ID AND ord.isActive = 'Y'
			LEFT JOIN C_BPartner bp_dropship ON ord.DropShip_BPartner_ID = bp_dropship.C_BPartner_ID AND bp_dropship.isActive = 'Y'
			LEFT JOIN C_BPartner bp_orderer ON ord.C_BPartner_ID = bp_orderer.C_BPartner_ID AND bp_orderer.isActive = 'Y'
		WHERE

			p.C_Period_ID = $1 AND p.isActive = 'Y'
			AND fa.ad_org_id = $4

		GROUP BY
			bp.name,
			p.EndDate,
			pp.EndDate,
			y.fiscalYear,
			py.fiscalYear,
			att.Attributes,
			fa.ad_org_id,
			COALESCE(CASE WHEN ord.IsDropShip = 'Y' THEN bp_dropship.Name END, bp_orderer.Name)
	) a
ORDER BY
	SameYearSum DESC$BODY$
LANGUAGE sql STABLE;


-- ==========================================================================
-- Step 3: Week report - Umsatzreport_Week_Report_Sub
-- Changed: name expression uses report._merge_bp_name instead of simple truncation
-- ==========================================================================
CREATE OR REPLACE FUNCTION report.Umsatzreport_Week_Report_Sub
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
	delivery_bp_name character varying(100)
)

AS
$$
SELECT
	report._merge_bp_name(name, delivery_bp_name) AS name,
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
	ad_org_id,
	delivery_bp_name
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
				THEN 	(CASE WHEN i.IsSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END) ELSE 0 END )
			AS base_week_sum,

			SUM( CASE WHEN fap.C_Year_ID = $1 AND fa.DateAcct::date <= ((select (to_timestamp($2 || ' ' ||(select fiscalyear from c_year where c_year_id =$1 AND isActive = 'Y'),'IW IYYY')))+ interval '6' day)::date
				THEN (CASE WHEN i.IsSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END) ELSE 0 END )
			AS base_year_sum,

			SUM( CASE WHEN
						fa.DateAcct::date >= (select (to_timestamp($4 || ' ' ||(select fiscalyear from c_year where c_year_id =$3 AND isActive = 'Y'),'IW IYYY')))::date AND
						fa.DateAcct::date <= ((select (to_timestamp($4 || ' ' ||(select fiscalyear from c_year where c_year_id =$3 AND isActive = 'Y'),'IW IYYY')))+ interval '6' day)::date
				THEN 	(CASE WHEN i.IsSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END) ELSE 0 END )
			AS comp_week_sum,

			SUM( CASE WHEN fap.C_Year_ID = $3 AND fa.DateAcct::date <= ((select (to_timestamp($4 || ' ' ||(select fiscalyear from c_year where c_year_id =$3 AND isActive = 'Y'),'IW IYYY')))+ interval '6' day)::date
				THEN (CASE WHEN i.IsSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END) ELSE 0 END )
			AS comp_year_sum,

			att.Attributes,
			fa.ad_org_id,
			COALESCE(
				CASE WHEN ord.IsDropShip = 'Y' THEN bp_dropship.Name END,
				bp_orderer.Name
			) AS delivery_bp_name
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

			-- DropShip / delivery recipient joins
			LEFT JOIN C_OrderLine ol_ds ON il.C_OrderLine_ID = ol_ds.C_OrderLine_ID AND ol_ds.isActive = 'Y'
			LEFT JOIN C_Order ord ON ol_ds.C_Order_ID = ord.C_Order_ID AND ord.isActive = 'Y'
			LEFT JOIN C_BPartner bp_dropship ON ord.DropShip_BPartner_ID = bp_dropship.C_BPartner_ID AND bp_dropship.isActive = 'Y'
			LEFT JOIN C_BPartner bp_orderer ON ord.C_BPartner_ID = bp_orderer.C_BPartner_ID AND bp_orderer.isActive = 'Y'
		WHERE
					AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
					AND i.IsSOtrx = $5 AND fa.isActive = 'Y'
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
			fa.ad_org_id,
			COALESCE(CASE WHEN ord.IsDropShip = 'Y' THEN bp_dropship.Name END, bp_orderer.Name)
	) a
ORDER BY
	base_year_sum DESC
$$
LANGUAGE sql STABLE;


-- ==========================================================================
-- Step 4: BPartner report - umsatzliste_bpartner_report_sub
-- Changed: bp_name expression uses report._merge_bp_name instead of raw bp.Name
-- ==========================================================================
CREATE OR REPLACE FUNCTION report.umsatzliste_bpartner_report_sub
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
	report._merge_bp_name(bp.Name, a.delivery_bp_name) AS bp_name,
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
	a.ad_org_id,
	a.delivery_bp_name

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
			fa.ad_org_id,
			COALESCE(
				CASE WHEN ord.IsDropShip = 'Y' THEN bp_dropship.Name END,
				bp_orderer.Name
			) AS delivery_bp_name
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

			-- DropShip / delivery recipient joins
			LEFT JOIN C_OrderLine ol_ds ON il.C_OrderLine_ID = ol_ds.C_OrderLine_ID AND ol_ds.isActive = 'Y'
			LEFT JOIN C_Order ord ON ol_ds.C_Order_ID = ord.C_Order_ID AND ord.isActive = 'Y'
			LEFT JOIN C_BPartner bp_dropship ON ord.DropShip_BPartner_ID = bp_dropship.C_BPartner_ID AND bp_dropship.isActive = 'Y'
			LEFT JOIN C_BPartner bp_orderer ON ord.C_BPartner_ID = bp_orderer.C_BPartner_ID AND bp_orderer.isActive = 'Y'
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
			fa.ad_org_id,
			COALESCE(CASE WHEN ord.IsDropShip = 'Y' THEN bp_dropship.Name END, bp_orderer.Name)
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


-- ==========================================================================
-- Step 5: Details report - umsatzliste_report_details
-- Changed: BP_Name expression uses report._merge_bp_name instead of raw bp.Name
-- ==========================================================================
CREATE OR REPLACE FUNCTION report.umsatzliste_report_details(IN C_BPartner_ID numeric, IN StartDate date, IN EndDate date, IN isSOTrx char(1), IN ad_org_id numeric) RETURNS TABLE
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
	ad_org_id numeric,
	delivery_bp_name character varying(100)
	)
AS
$$
SELECT
	bp.Value AS BP_Value,
	report._merge_bp_name(bp.Name, COALESCE(CASE WHEN ord.IsDropShip = 'Y' THEN bp_dropship.Name END, bp_orderer.Name)) AS BP_Name,
	p.value AS P_Value,
	p.Name AS P_Name,
	SUM( ic.QtyInvoiced * ic.PriceActual ) AS TotalInvoiced,
	SUM( ic.QtyInvoicable * ic.PriceActual ) AS TotalShipped,
	SUM( CASE WHEN s_Ordered != Sign( ic.QtyOpen ) THEN 0 ELSE ic.QtyOpen END * ic.PriceActual ) AS TotalOrdered,
	p.M_Product_Category_ID =  getSysConfigAsNumeric('PackingMaterialProductCategoryID', ic.AD_Client_ID, ic.AD_Org_ID) AS IsPackingMaterial,
	date_trunc( 'month', ic.Date ) AS Month,
	c.ISO_Code,
	ic.ad_org_id,
	COALESCE(
		CASE WHEN ord.IsDropShip = 'Y' THEN bp_dropship.Name END,
		bp_orderer.Name
	) AS delivery_bp_name
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
			ic.AD_Org_ID, ic.AD_Client_ID,
			icq.C_Order_ID
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
					ol.DatePromised,
					ol.C_Order_ID
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
						WHERE 	ic.IsSOTrx = $4
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

	-- DropShip / delivery recipient joins
	LEFT JOIN C_Order ord ON ic.C_Order_ID = ord.C_Order_ID AND ord.isActive = 'Y'
	LEFT JOIN C_BPartner bp_dropship ON ord.DropShip_BPartner_ID = bp_dropship.C_BPartner_ID AND bp_dropship.isActive = 'Y'
	LEFT JOIN C_BPartner bp_orderer ON ord.C_BPartner_ID = bp_orderer.C_BPartner_ID AND bp_orderer.isActive = 'Y'
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
	ic.ad_client_id,
	COALESCE(CASE WHEN ord.IsDropShip = 'Y' THEN bp_dropship.Name END, bp_orderer.Name)
ORDER BY
	date_trunc( 'month', ic.Date ),
	bp.Value,
	p.M_Product_Category_ID =
		 getSysConfigAsNumeric('PackingMaterialProductCategoryID', ic.AD_Client_ID, ic.AD_Org_ID)

$$
  LANGUAGE sql STABLE;
