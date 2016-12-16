-- Function: "de.metas.materialtracking".c_invoice_candidate_delete_for_m_material_tracking_lot(character varying)

-- DROP FUNCTION "de.metas.materialtracking".c_invoice_candidate_delete_for_m_material_tracking_lot(character varying);

CREATE OR REPLACE FUNCTION "de.metas.materialtracking".c_invoice_candidate_delete_for_m_material_tracking_lot(m_material_tracking_lot character varying)
  RETURNS void AS
$BODY$

--
-- Delete c_invoice_clearing_allocs
DELETE
FROM c_invoice_clearing_alloc
WHERE C_Invoice_Candidate_ID IN (
	SELECT --count(8)
	ic.C_Invoice_Candidate_ID
	FROM C_Invoice_Candidate ic
		JOIN M_Material_Tracking mt ON mt.M_Material_Tracking_ID=ic.M_Material_Tracking_ID
	WHERE C_DocTypeInvoice_ID IN (select C_Doctype_ID from C_Doctype where DocBaseType='API' AND DocSubType IN ('DP' /*Akontozahlung*/, 'QI' /*Endabrechnung*/))
		AND COALESCE(ic.QtyInvoiced,0)=0 
		AND COALESCE(ic.Processed_Override, ic.Processed)='N'
		AND ic.AD_Table_ID=get_table_id('PP_Order')
		AND trim(mt.Lot)=trim($1)
--		AND mt.Lot='14260401010008'
);
DELETE
FROM c_invoice_clearing_alloc
WHERE c_invoice_cand_toclear_id IN (
	SELECT --count(8)
	ic.C_Invoice_Candidate_ID
	FROM C_Invoice_Candidate ic
		JOIN M_Material_Tracking mt ON mt.M_Material_Tracking_ID=ic.M_Material_Tracking_ID
	WHERE C_DocTypeInvoice_ID IN (select C_Doctype_ID from C_Doctype where DocBaseType='API' AND DocSubType IN ('DP' /*Akontozahlung*/, 'QI' /*Endabrechnung*/))
		AND COALESCE(ic.QtyInvoiced,0)=0 
		AND COALESCE(ic.Processed_Override, ic.Processed)='N'
		AND ic.AD_Table_ID=get_table_id('PP_Order')
		AND trim(mt.Lot)=trim($1)
);

--
-- delete the actual ICs
DELETE
FROM C_Invoice_Candidate ic
WHERE C_Invoice_Candidate_ID IN (
	SELECT --count(8)
	ic.C_Invoice_Candidate_ID
	FROM C_Invoice_Candidate ic
		JOIN M_Material_Tracking mt ON mt.M_Material_Tracking_ID=ic.M_Material_Tracking_ID
	WHERE true
		AND C_DocTypeInvoice_ID IN (select C_Doctype_ID from C_Doctype where DocBaseType='API' AND DocSubType IN ('DP' /*Akontozahlung*/, 'QI' /*Endabrechnung*/))
		AND COALESCE(ic.QtyInvoiced,0)=0 
		AND COALESCE(ic.Processed_Override, ic.Processed)='N'
		AND ic.AD_Table_ID=get_table_id('PP_Order')
		AND NOT EXISTS (select 1 from c_invoice_line_alloc ila where ila.C_Invoice_Candidate_ID=ic.C_Invoice_Candidate_ID)
		AND trim(mt.Lot)=trim($1)
--		AND mt.Lot='14260401010010' -- AND C_Invoice_Candidate_ID=2536550
);

--
-- delete the M_Material_Tracking_Refs
DELETE FROM
--SELECT * FROM 
M_Material_Tracking_Ref
WHERE (
	(AD_Table_ID=get_Table_ID('M_InOutLine') AND NOT EXISTS (select 1 from M_InOutLine where M_InOutLine_ID=Record_ID))
	OR (AD_Table_ID=get_Table_ID('C_OrderLine') AND NOT EXISTS (select 1 from C_OrderLine where C_OrderLine_ID=Record_ID))
	OR (AD_Table_ID=get_Table_ID('C_Invoice_Candidate') AND NOT EXISTS (select 1 from C_Invoice_Candidate where C_Invoice_Candidate_ID=Record_ID))
    OR (AD_Table_ID=get_Table_ID('PP_Order') AND NOT EXISTS (select 1 from PP_Order where PP_Order_ID=Record_ID))
	)
;

--
-- unset IsInvoiceCandidate for thise PP_Orders that just lost their ICs
UPDATE PP_Order ppo_outer
SET IsInvoiceCandidate='N'
FROM (
	SELECT ppo.PP_Order_ID
	FROM PP_Order ppo
		JOIN M_Material_Tracking mt ON mt.M_Material_Tracking_ID=ppo.M_Material_Tracking_ID
		LEFT JOIN C_Invoice_Detail id ON id.PP_Order_ID=ppo.PP_Order_ID
	WHERE true
		AND ppo.IsInvoiceCandidate='Y'
		AND id.C_Invoice_Detail_ID IS NULL
		AND trim(mt.Lot)=trim($1)
) data
WHERE data.PP_Order_ID=ppo_outer.PP_Order_ID
;

$BODY$
  LANGUAGE sql VOLATILE
  COST 100;

COMMENT ON FUNCTION "de.metas.materialtracking".c_invoice_candidate_delete_for_m_material_tracking_lot(character varying) IS 
'Deletes (not-yet-invoiced) C_Invoice_Candidates for a given Lot.
Note: ICs that already have an C_Invoice_Line_Alloc record are skipped.

/* this delete is to prepare ICs with reversed invoices..
DELETE FROM C_Invoice_Line_Alloc WHERE C_invoice_candidate_ID IN
(
select C_Invoice_Candidate_ID 
FROM C_Invoice_Candidate ic
	JOIN m_material_tracking mt on mt.m_material_tracking_ID=ic.m_material_tracking_ID
where trim(mt.Lot)=''9898989898'' AND COALESCE(ic.Processed_Override, ic.Processed)=''N''
);*/
';
