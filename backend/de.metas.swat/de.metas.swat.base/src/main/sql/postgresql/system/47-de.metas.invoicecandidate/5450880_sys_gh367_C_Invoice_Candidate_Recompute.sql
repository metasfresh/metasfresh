
--
-- make sure that ICs that match the scenario of our corner case get their M_PriceList_Version_ID updated.
--
INSERT INTO C_Invoice_Candidate_Recompute(C_Invoice_Candidate_ID)
SELECT ic.C_Invoice_Candidate_ID
FROM C_Invoice_Candidate ic
	JOIN M_InOutLine iol ON iol.M_InOutLine_ID=ic.Record_ID
		JOIN M_InOut io ON io.M_InOut_ID=iol.M_InOut_ID
	JOIN C_BPartner bp ON bp.C_BPartner_ID=ic.Bill_BPartner_ID
WHERE true
	AND ic.AD_Table_ID=get_table_id('M_InOutLine')
	AND ic.M_PriceList_Version_ID IS NULL
	AND ic.Processed='N'
	AND io.MovementType IN ('C+','V-') -- return movement 
	AND bp.IsActive='Y'
	AND bp.IsVendor='Y' 
	AND bp.IsCustomer='Y' 
	AND (
		bp.M_PricingSystem_ID IS NULL AND bp.PO_PricingSystem_ID IS NOT NULL
		OR
		bp.M_PricingSystem_ID IS NOT NULL AND bp.PO_PricingSystem_ID IS NULL
	);
	