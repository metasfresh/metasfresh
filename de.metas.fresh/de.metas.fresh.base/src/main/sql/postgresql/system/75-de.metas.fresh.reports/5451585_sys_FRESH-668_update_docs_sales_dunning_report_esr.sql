
DROP FUNCTION IF EXISTS report.Docs_Sales_Dunning_Report_esr ( IN Record_ID numeric, IN AD_Language Character Varying (6)) ;

CREATE FUNCTION report.Docs_Sales_Dunning_Report_esr ( IN Record_ID numeric, IN AD_Language Character Varying (6))
	RETURNS TABLE ( 
		receiver character varying,
		account character varying, 
		referenceno character varying,
		codeline character varying,
		bpartneraddress character varying,
		truncategrandtotal numeric,
		grandtotaldecimal numeric
	) AS 
$$
SELECT
	x.receiver,
	x.Account,
	substring(x.referenceNo, 1, 2) || ' ' ||
	substring(x.referenceNo, 3, 5) || ' ' ||
	substring(x.referenceNo, 8, 5) || ' ' ||
	substring(x.referenceNo, 13, 5) || ' ' ||
	substring(x.referenceNo, 18, 5) || ' ' ||
	substring(x.referenceNo, 23, 7) AS referenceno,
	x.codeline,
	x.bpartneraddress,
	to_number(substring(x.Codeline_for_total, 3, 8), '999999999') AS truncategrandtotal,
	to_number(substring(x.Codeline_for_total, 11, 2), '99') as grandTotaldecimal
FROM
	(SELECT
		CASE
			WHEN bpb.C_BPartner_ID IS NULL THEN 'ERROR: Could not find bank account for section'
			ELSE COALESCE (ESR_RenderedReceiver, 'ERROR: The bank account is lacking a rendered receiver')
		END AS receiver,

		CASE
			WHEN bpb.C_BPartner_ID IS NULL THEN 'No account found'
			ELSE COALESCE (ESR_RenderedAccountNo, 'No acc.no. found')
		END AS Account,
		COALESCE(rn.ReferenceNo, 'ERROR: ReferenceNo missing') AS referenceno,
		COALESCE(cl.ReferenceNo, 'ERROR: ReferenceNo missing') AS codeline,
		CASE
			WHEN cl.ReferenceNo IS NULL OR cl.ReferenceNo = '' THEN '00000000000'
			ELSE cl.ReferenceNo
		END AS codeline_for_total,
		COALESCE(i.bpartneraddress, 'ERROR: Rendered address field in document is empty') AS bpartneraddress,
		i.GrandTotal
	FROM
		C_Invoice i
		LEFT JOIN (
			SELECT	rn.referenceNo, rnd.Record_ID
			FROM	C_ReferenceNo_Doc rnd
				LEFT JOIN C_ReferenceNo rn	ON rnd.C_ReferenceNo_ID = rn.C_ReferenceNo_ID AND rn.isActive = 'Y'
			WHERE	AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice' AND isActive = 'Y')
				AND rn.C_ReferenceNo_Type_ID = (SELECT C_ReferenceNo_Type_ID FROM C_ReferenceNo_Type WHERE name = 'InvoiceReference' AND isActive = 'Y')
				AND  rnd.isActive = 'Y'
		) rn ON i.C_Invoice_ID = rn.Record_ID

		LEFT JOIN (
			SELECT	rn.referenceNo, rnd.Record_ID
			FROM	C_ReferenceNo_Doc rnd
				LEFT JOIN C_ReferenceNo rn	ON rnd.C_ReferenceNo_ID = rn.C_ReferenceNo_ID AND rn.isActive = 'Y'
			WHERE	AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice' AND isActive = 'Y')
				AND rn.C_ReferenceNo_Type_ID = (SELECT C_ReferenceNo_Type_ID FROM C_ReferenceNo_Type WHERE name = 'ESRReferenceNumber' AND isActive = 'Y')
				AND rnd.isActive = 'Y'
		) cl ON i.C_Invoice_ID = cl.Record_ID

		LEFT JOIN AD_Org o		ON i.AD_Org_ID = o.AD_Org_ID AND o.isActive = 'Y'
		LEFT JOIN C_BPartner bp 	ON o.AD_Org_ID = bp.AD_OrgBP_ID AND bp.isActive = 'Y'
		LEFT JOIN C_BP_Bankaccount bpb ON bp.C_BPartner_ID = bpb.C_BPartner_ID AND bpb.IsEsrAccount='Y' AND bpb.isActive = 'Y'

		LEFT JOIN C_Dunning_Candidate cand ON i.C_Invoice_ID = cand.Record_ID AND cand.AD_Table_ID = Get_Table_ID('C_Invoice') AND cand.isActive = 'Y'
		LEFT JOIN C_DunningDoc_Line_Source dls ON cand.C_Dunning_Candidate_ID = dls.C_Dunning_Candidate_ID AND dls.isActive = 'Y'
		LEFT JOIN C_DunningDoc_line dl ON dls.C_DunningDoc_Line_ID = dl.C_DunningDoc_Line_ID AND dl.isActive = 'Y'
		LEFT JOIN C_DunningDoc dd ON dl.C_DunningDoc_ID = dd.C_DunningDoc_ID AND dd.isActive = 'Y'

	WHERE
		dd.C_DunningDoc_ID = $1 AND i.isActive = 'Y'
	ORDER BY
		bpb.IsDefaultESR DESC, bpb.C_BP_BankAccount_ID
	) x
;
$$ 
LANGUAGE sql STABLE;


