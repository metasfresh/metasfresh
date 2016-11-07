/*
	Removing invoice candidates related AD_ColumnCallout entries because we are going to register those callouts programatically.
	
	Query to identify those callouts was:
		select t.tableName, c.ColumnName, cc.Classname, cc.AD_ColumnCallout_ID
		from AD_ColumnCallout cc
		inner join AD_Column c on (c.AD_Column_ID=cc.AD_Column_ID)
		inner join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
		where true
		and cc.Classname ilike '%Invoice%Candidate%'
		order by t.TableName, cc.ClassName, c.ColumnName
		
	While returned:
		"C_ILCandHandler";"Classname";"de.metas.invoicecandidate.callout.ILCandHandler.className";540927
		"C_Invoice_Candidate";"Discount_Override";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt";540905
		"C_Invoice_Candidate";"IsTaxIncluded_Override";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt";541007
		"C_Invoice_Candidate";"PriceActual";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt";540896
		"C_Invoice_Candidate";"PriceActual_Override";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt";540897
		"C_Invoice_Candidate";"PriceEntered_Override";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt";540976
		"C_Invoice_Candidate";"QtyToInvoice";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt";540898
		"C_Invoice_Candidate";"Bill_BPartner_ID";"de.metas.invoicecandidate.callout.InvoiceCandidate.Bill_BPartner_ID";540964
		"C_Invoice_Candidate";"IsManual";"de.metas.invoicecandidate.callout.InvoiceCandidate.IsManual";540963
		"C_Invoice_Candidate_Agg";"Classname";"de.metas.invoicecandidate.callout.InvoiceCandidateAgg.className";540928
		"M_IolCandHandler";"Classname";"de.metas.invoicecandidate.callout.ILCandHandler.className";540946
*/

delete from AD_ColumnCallout where AD_ColumnCallout_ID in (
	540927 -- "C_ILCandHandler";"Classname";"de.metas.invoicecandidate.callout.ILCandHandler.className"
	, 540905 -- "C_Invoice_Candidate";"Discount_Override";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt"
	, 541007 -- "C_Invoice_Candidate";"IsTaxIncluded_Override";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt"
	, 540896 -- "C_Invoice_Candidate";"PriceActual";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt"
	, 540897 -- "C_Invoice_Candidate";"PriceActual_Override";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt"
	, 540976 -- "C_Invoice_Candidate";"PriceEntered_Override";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt"
	, 540898 -- "C_Invoice_Candidate";"QtyToInvoice";"de.metas.invoicecandidate.callout.InvoiceCandidate.amt"
	, 540964 -- "C_Invoice_Candidate";"Bill_BPartner_ID";"de.metas.invoicecandidate.callout.InvoiceCandidate.Bill_BPartner_ID"
	, 540963 -- "C_Invoice_Candidate";"IsManual";"de.metas.invoicecandidate.callout.InvoiceCandidate.IsManual"
	, 540928 -- "C_Invoice_Candidate_Agg";"Classname";"de.metas.invoicecandidate.callout.InvoiceCandidateAgg.className"
	, 540946 -- "M_IolCandHandler";"Classname";"de.metas.invoicecandidate.callout.ILCandHandler.className"
);
