DROP VIEW IF EXISTS C_Dunning_Candidate_Invoice_v1;

CREATE OR REPLACE VIEW C_Dunning_Candidate_Invoice_v1 AS 
SELECT   
	i.C_Invoice_ID
	, i.C_InvoicePaySchedule_ID
	, i.AD_Client_ID
	, i.AD_Org_ID
	, i.C_BPartner_ID
	, i.C_BPartner_Location_ID
	, i.AD_User_ID
	, i.C_Currency_ID
	, GREATEST(bp.DunningGrace, i.DunningGrace) AS DunningGrace
	, i.GrandTotal*i.MultiplierAP AS GrandTotal
	, invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID)*MultiplierAP AS OpenAmt
	, i.DateInvoiced
	, COALESCE(ips.DueDate, paymentTermDueDate(i.C_PaymentTerm_ID,i.DateInvoiced)) AS DueDate
	, i.C_PaymentTerm_ID
	-- , COALESCE(daysBetween(?,ips.DueDate), paymentTermDueDays(i.C_PaymentTerm_ID,i.DateInvoiced,?)) AS DaysDue
	, i.IsInDispute
	, COALESCE(bp.C_Dunning_ID, bpg.C_Dunning_ID, dunnOrg.C_Dunning_ID) as C_Dunning_ID
	, i.IsActive
FROM C_Invoice_v i 
	LEFT OUTER JOIN C_InvoicePaySchedule ips ON (i.C_InvoicePaySchedule_ID=ips.C_InvoicePaySchedule_ID) 
	LEFT OUTER JOIN C_Doctype dt ON (i.C_Doctype_ID = dt.C_Doctype_ID)
	INNER JOIN C_BPartner bp ON (bp.C_BPartner_ID=i.C_BPartner_ID)
	INNER JOIN C_BP_Group bpg ON (bpg.C_BP_Group_ID=bp.C_BP_Group_ID)
	LEFT OUTER JOIN C_Dunning dunnOrg ON (dunnOrg.AD_Org_ID = i.AD_Org_ID AND dunnOrg.IsActive='Y' AND dunnOrg.IsDefault='Y')
WHERE
	-- Only sales invoices
	i.IsSOTrx = 'Y'
	-- Only Not paid invoices
	AND i.IsPaid = 'N'
	-- Exclude Credit Memos
	AND dt.DocBaseType <> 'ARC'
	-- Only Processed
	AND i.Processed='Y'
	-- Only Completed/Closed
	AND i.DocStatus IN ('CO', 'CL')
;

