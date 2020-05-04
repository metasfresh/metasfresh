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
	p.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID') AS IsPackingMaterial,
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
			ic.AD_Org_ID
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
									LEFT OUTER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
								GROUP BY	ila.C_Invoice_Candidate_ID
							) il ON ic.C_Invoice_Candidate_ID = il.C_Invoice_Candidate_ID
						WHERE 	IsSOTrx = $4
							AND ( CASE WHEN $1 IS NULL THEN TRUE ELSE $1 = Bill_BPartner_ID END )
							AND PriceActual_Net_Effective != 0
					) ic
					LEFT OUTER JOIN C_Orderline ol ON ic.Record_ID = ol.C_OrderLine_ID
						AND ic.AD_Table_ID = ((SELECT Get_Table_ID('C_OrderLine')))
			) icq ON ic.C_Invoice_Candidate_ID = icq.C_Invoice_Candidate_ID
			INNER JOIN AD_ClientInfo ci ON ic.AD_Client_ID = ci.AD_Client_ID
			INNER JOIN C_AcctSchema ac ON ci.C_AcctSchema1_ID = ac.C_AcctSchema_ID
			LEFT OUTER JOIN
			(
				SELECT 	ila.C_Invoice_Candidate_ID,
					First_agg( i.DateAcct::text ORDER BY i.DateAcct)::Date AS DateAcct
				FROM 	C_Invoice_Line_Alloc ila
					LEFT OUTER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
					LEFT OUTER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
				GROUP BY	ila.C_Invoice_Candidate_ID
			) i ON ic.C_Invoice_Candidate_ID = i.C_Invoice_Candidate_ID
	) ic
	INNER JOIN C_BPartner bp ON ic.Bill_BPartner_ID = bp.C_BPartner_ID
	INNER JOIN M_Product p ON ic.M_Product_ID = p.M_Product_ID
	INNER JOIN C_Currency c ON ic.Base_Currency_ID = c.C_Currency_ID
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
	ic.ad_org_id
ORDER BY
	date_trunc( 'month', ic.Date ),
	bp.Value,
	p.M_Product_Category_ID =
		(SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')

$$
  LANGUAGE sql STABLE;