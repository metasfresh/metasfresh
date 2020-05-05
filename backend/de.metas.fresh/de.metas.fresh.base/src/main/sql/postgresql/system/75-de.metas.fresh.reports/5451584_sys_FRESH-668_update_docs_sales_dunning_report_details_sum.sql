DROP FUNCTION IF EXISTS report.Docs_Sales_Dunning_Report_details_sum ( IN Record_ID numeric) ;

CREATE FUNCTION report.Docs_Sales_Dunning_Report_details_sum ( IN Record_ID numeric) 
	RETURNS TABLE ( 
		grandtotal numeric,
		paidamt numeric,
		openamt numeric, 
		feeamt numeric,
		totalamt numeric,
		iso_code character
	) AS 
$$
SELECT
	SUM(doc.GrandTotal) AS grandtotal,
	SUM(doc.PaidAmt) AS paidamt,
	SUM(doc.GrandTotal - doc.PaidAmt) AS openamt,
	SUM(dl.amt) AS feeamt,
	SUM(invoiceopen(dc.Record_ID, 0::numeric) + dl.amt) AS totalamt,
	c.ISO_Code
FROM
	C_DunningDoc dd
	JOIN C_DunningDoc_line dl ON dd.C_DunningDoc_ID = dl.C_DunningDoc_Line_ID AND dl.isActive = 'Y'
	LEFT JOIN C_DunningDoc_Line_Source dls ON dl.C_DunningDoc_Line_ID = dls.C_DunningDoc_Line_ID AND dls.isActive = 'Y'
	LEFT JOIN C_Dunning_Candidate dc ON dls.C_Dunning_Candidate_ID = dc.C_Dunning_Candidate_ID AND dc.isActive = 'Y'
	LEFT JOIN C_Currency c ON dc.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
	LEFT JOIN	(
		SELECT 	sub_dc.C_Dunning_Candidate_ID,
			Documentpaid(sub_dc.Record_ID, sub_dc.AD_Table_ID, sub_dc.C_Currency_ID, i.MultiplierAP) AS PaidAmt,
			COALESCE(i.DocumentNo, o.DocumentNo) AS DocumentNo,
			COALESCE(i.DateInvoiced, o.DateOrdered) AS DocumentDate,
			COALESCE(i.C_Doctype_ID, o.C_Doctype_ID) AS C_Doctype_ID,
			COALESCE(i.C_PaymentTerm_ID, o.C_PaymentTerm_ID) AS C_PaymentTerm_ID,
			COALESCE(i.GrandTotal, o.GrandTotal) AS GrandTotal
		FROM	C_Dunning_Candidate sub_dc
			LEFT JOIN C_Invoice_v i ON sub_dc.Record_ID = i.C_Invoice_ID AND sub_dc.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice' AND isActive = 'Y') AND i.isActive = 'Y'
			LEFT JOIN C_Order o ON sub_dc.Record_ID = o.C_Order_ID AND sub_dc.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Order' AND isActive = 'Y') AND o.isActive = 'Y'
		WHERE  sub_dc.isActive = 'Y'
	) doc ON dc.C_Dunning_Candidate_ID = doc.C_Dunning_Candidate_ID
WHERE
	dd.C_DunningDoc_ID = $1 AND dd.isActive = 'Y'
GROUP BY
	dd.C_DunningDoc_ID,
	c.ISO_Code
;
$$ 
LANGUAGE sql STABLE;

