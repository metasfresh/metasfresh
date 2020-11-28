-- View: fresh_material_balance_detail_v

DROP VIEW IF EXISTS "de.metas.inout".M_Material_Balance_Detail_V;

CREATE OR REPLACE VIEW "de.metas.inout".M_Material_Balance_Detail_V AS 

SELECT 
	mbd.*,
	CASE
	    WHEN ic.C_Invoice_Candidate_ID IS NULL THEN false
	    WHEN ic.isToClear = 'N' THEN ic.Processed = 'Y'
	    WHEN ic.isToClear = 'Y' THEN COALESCE(icc.Processed = 'Y', false)
	    ELSE NULL::boolean
	END AS isInvoiced
FROM 
	M_Material_Balance_Detail mbd
	LEFT JOIN C_Invoice_Candidate ic ON mbd.M_InOutLine_ID = ic.Record_ID AND ic.AD_Table_ID = ( SELECT Get_Table_ID('M_InOutLine') ) AND ic.isactive = 'Y'
	LEFT JOIN C_Invoice_Clearing_alloc ica ON ic.C_Invoice_Candidate_ID = ica.C_Invoice_Cand_ToClear_ID AND ica.isActive = 'Y'
	LEFT JOIN C_Invoice_Candidate icc ON ica.C_Invoice_Candidate_ID = icc.C_Invoice_Candidate_ID AND ic.AD_Table_ID = ( SELECT Get_Table_ID('C_Flatrate_Term') ) AND ic.isActive = 'Y';


