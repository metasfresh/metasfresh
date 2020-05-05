CREATE OR REPLACE VIEW public.C_BPartner_OpenAmounts_v  AS
SELECT C_BPartner_ID, SUM(OpenOrderAmt) AS OpenOrderAmt, SUM(OpenInvoiceAmt) AS OpenInvoiceAmt, SUM(UnallocatedPaymentAmt) AS UnallocatedPaymentAmt
FROM
(
	SELECT ic.Bill_BPartner_ID AS C_BPartner_ID,
	coalesce(SUM(currencyBase(ic.linenetamt + CASE
						WHEN (coalesce(ic.IsTaxIncluded_Override, ic.isTaxIncluded) ='N' AND t.isWholeTax='N')  THEN
							round((ic.linenetamt * round(t.rate/100, 12)),  c.StdPrecision)
						ELSE 0
		END,ic.C_Currency_ID,ic.DateOrdered, ic.AD_Client_ID,ic.AD_Org_ID)),0) as OpenOrderAmt,
		
	0 AS OpenInvoiceAmt,

	0 AS UnallocatedPaymentAmt 
	 
	FROM  C_Invoice_Candidate ic 
	LEFT JOIN C_Tax t ON (ic.C_Tax_ID = t.C_Tax_ID)
	LEFT JOIN C_Currency c ON (c.C_Currency_ID = ic.C_Currency_ID)
	WHERE ic.Processed='N' AND ic.IsSOTrx='Y'
	GROUP BY ic.Bill_BPartner_ID

	UNION ALL

	SELECT i.C_BPartner_ID, 0 AS OpenOrderAmt,
	coalesce(SUM(currencyBase(invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID),i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)),0) AS OpenInvoiceAmt,
	0 AS UnallocatedPaymentAmt 
	FROM C_Invoice_v i 
	WHERE i.IsSOTrx='Y' AND i.IsPaid='N' AND i.DocStatus IN ('CO','CL')
	GROUP BY i.C_BPartner_ID

	UNION ALL

	SELECT p.C_BPartner_ID, 0 AS OpenOrderAmt, 0 AS OpenInvoiceAmt,
	coalesce(SUM(currencyBase(Paymentavailable(p.C_Payment_ID),p.C_Currency_ID,p.DateTrx,p.AD_Client_ID,p.AD_Org_ID)),0)*(-1) AS UnallocatedPaymentAmt 
	FROM C_Payment_v p 
	WHERE p.IsAllocated='N' AND p.C_Charge_ID IS NULL AND p.DocStatus IN ('CO','CL')
	GROUP BY p.C_BPartner_ID
) openAmt
GROUP BY C_BPartner_ID;