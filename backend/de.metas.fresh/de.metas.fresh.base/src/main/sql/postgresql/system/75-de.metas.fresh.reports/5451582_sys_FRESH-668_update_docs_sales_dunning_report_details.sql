DROP FUNCTION IF EXISTS report.Docs_Sales_Dunning_Report_details ( IN Record_ID numeric, IN AD_Language Character Varying (6)) ;

CREATE FUNCTION report.Docs_Sales_Dunning_Report_details ( IN Record_ID numeric, IN AD_Language Character Varying (6)) 
	RETURNS TABLE ( 
		printname character varying, 
		documentno character varying, 
		documentdate timestamp, 
		currency character,
		grandtotal numeric,
		paidamt numeric,
		openamt numeric, 
		feeamt numeric,
		totalamt numeric,
		duedate timestamp with time zone,
		daysdue numeric
	) AS 
$$
SELECT
	COALESCE(dt_trl.printname, dt.PrintName) AS PrintName,
	COALESCE(doc.DocumentNo, 'ERROR') AS DocumentNo,
	doc.DocumentDate,
	c.iso_code AS currency,
	doc.GrandTotal,
	doc.PaidAmt AS paidamt,
	doc.GrandTotal - doc.PaidAmt AS openamt,
	dl.amt AS FeeAmt,
	invoiceopen(dc.Record_ID, 0::numeric) + dl.amt as totalamt,
	paymenttermduedate(doc.C_PaymentTerm_ID, doc.DocumentDate::timestamp with time zone) AS DueDate,
	dc.DaysDue
FROM
	C_DunningDoc dd
	LEFT JOIN C_DunningDoc_line dl ON dd.C_DunningDoc_ID = dl.C_DunningDoc_Line_ID AND dl.isActive = 'Y'
	LEFT JOIN C_DunningDoc_Line_Source dls ON dl.C_DunningDoc_Line_ID = dls.C_DunningDoc_Line_ID AND dls.isActive = 'Y'
	LEFT JOIN C_Dunning_Candidate dc ON dls.C_Dunning_Candidate_ID = dc.C_Dunning_Candidate_ID AND dc.isActive = 'Y'
	LEFT JOIN C_Currency c ON dc.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
	LEFT JOIN
	(
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
	LEFT JOIN c_doctype dt ON doc.c_doctype_id = dt.c_doctype_id  AND dt.isActive = 'Y'
	LEFT JOIN C_DocType_Trl dt_trl ON doc.c_doctype_id = dt_trl.c_doctype_id AND dt_trl.AD_Language = $2  AND dt_trl.isActive = 'Y'
WHERE
	dd.C_DunningDoc_ID = $1 AND dd.isActive = 'Y'
;
$$ 
LANGUAGE sql STABLE;


