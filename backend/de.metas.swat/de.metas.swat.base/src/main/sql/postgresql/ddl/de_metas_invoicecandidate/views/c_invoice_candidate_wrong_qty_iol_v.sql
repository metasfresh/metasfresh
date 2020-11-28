
DROP VIEW IF EXISTS de_metas_invoicecandidate.c_invoice_candidate_wrong_qtydelivered_iol_v;
CREATE OR REPLACE VIEW de_metas_invoicecandidate.c_invoice_candidate_wrong_qty_iol_v AS 
SELECT 
	ic.c_invoice_candidate_id, 
	ic.created, 
	ic.updated, 
	dt.name, 
	COALESCE(ic.datetoinvoice_override, ic.datetoinvoice) AS "datetoinvoice_effective", 
	ic.qtydelivered, 
	sum(iol.movementqty) AS sum
FROM c_invoice_candidate ic
   JOIN c_invoicecandidate_inoutline ic_iol ON ic_iol.c_invoice_candidate_id = ic.c_invoice_candidate_id
   JOIN m_inoutline iol ON iol.m_inoutline_id = ic_iol.m_inoutline_id
   JOIN m_inout io ON io.m_inout_id = iol.m_inout_id
   LEFT JOIN c_doctype dt ON dt.c_doctype_id = ic.c_doctypeinvoice_id
   LEFT JOIN c_orderline ol ON ol.c_orderline_id = ic.c_orderline_id
   LEFT JOIN c_invoice_candidate_recompute icr ON icr.c_invoice_candidate_id = ic.c_invoice_candidate_id
WHERE true 
	AND ic.IsActive='Y'
	AND ic_iol.IsActive='Y'
	AND ic.c_orderline_id IS NULL 
	AND (ic.updated + interval '10 minutes') < now() /* last update more than 10 mintes ago */
	AND COALESCE(ic.processed_override, ic.processed) = 'N'
	AND io.docstatus IN ('CO', 'CL')
	AND icr.c_invoice_candidate_id IS NULL /* not currently flagged as recompute */
GROUP BY 
	ic.c_invoice_candidate_id, 
	ic.created, 
	ic.updated, 
	ic.QtyOrdered,
	dt.name, 
	COALESCE(ic.datetoinvoice_override, ic.datetoinvoice), 
	ic.qtydelivered
HAVING abs(ic.qtydelivered) <> abs(sum(iol.movementqty)) OR abs(ic.QtyOrdered) <> abs(sum(iol.MovementQty))
ORDER BY COALESCE(ic.datetoinvoice_override, ic.datetoinvoice);

COMMENT ON VIEW de_metas_invoicecandidate.c_invoice_candidate_wrong_qty_iol_v
  IS 'ICs that 
* do reference an M_InOutLine and 
* do _not_ reference a C_OrderLine and
* have an inconsistent QtyDelivered or QtyOrdered value and
* were created/updated more than 10 minutes ago.
see issue FRESH-93';
