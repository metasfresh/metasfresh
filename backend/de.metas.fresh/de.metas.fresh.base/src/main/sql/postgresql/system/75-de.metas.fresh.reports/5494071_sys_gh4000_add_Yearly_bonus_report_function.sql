DROP FUNCTION IF EXISTS report.yearly_bonus_report(IN p_C_BPartner_ID numeric, IN p_M_Product_ID NUMERIC, IN p_DateFrom DATE, IN p_DateTo DATE);

CREATE OR REPLACE FUNCTION report.yearly_bonus_report(IN p_C_BPartner_ID numeric, IN p_M_Product_ID NUMERIC, IN p_DateFrom DATE, IN p_DateTo DATE)
RETURNS TABLE
(	manufacturer character varying,
	bp_name character varying,
	p_value character varying,
	qty_ordered_sum numeric,
	scheduler character varying,
	contract_details text,
	month_to_invoice text,
	netamttoinvoice_sum numeric,
	netamtinvoiced_sum numeric
)
AS
$$
	SELECT manufacturer, 
	bp_name, 
	p_value, 
	SUM(qtyOrdered) as qty_ordered_sum, 
	scheduler, 
	contract_details, 
	month_to_invoice, 
	SUM(netamttoinvoice) as netamttoinvoice_sum, 
	SUM(netamtinvoiced) as netamtinvoiced_sum
FROM	
(
	select 
		p.manufacturer, 
		bp.Name as bp_name, 
		p.Value as p_value, 
		icn.qtyOrdered, 
		isch.Name as scheduler, 
		
		(to_char(ft.startdate, 'dd.MM.YYYY') || ' - ' || to_char(ft.enddate, 'dd.MM.YYYY')) as contract_details, 
		to_char(ic.DateToInvoice, 'MM.YYYY') AS month_to_invoice, 
		
		ic.netamttoinvoice as netamttoinvoice,
		SUM(icn.netamtinvoiced) as netamtinvoiced
		

	FROM C_Invoice_Candidate ic

	JOIN C_Flatrate_Term ft ON ic.AD_Table_ID = Get_Table_ID('C_Flatrate_Term') AND ic.Record_ID = ft.C_Flatrate_Term_ID
	JOIN C_Flatrate_Conditions fc ON ft.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
	
	--joining normal ics
	JOIN C_Invoice_Candidate_Assignment ica ON ic.C_Invoice_Candidate_ID = ica.C_Invoice_Candidate_Term_ID
	JOIN C_Invoice_Candidate icn ON ica.C_Invoice_Candidate_Assigned_ID = icn.C_Invoice_Candidate_ID
	
	--
	LEFT JOIN M_Product p ON icn.M_Product_ID = p.M_Product_ID
	LEFT JOIN C_BPartner bp ON icn.Bill_BPartner_ID = bp.C_BPartner_ID
	LEFT JOIN C_InvoiceSchedule isch ON ic.C_InvoiceSchedule_ID = isch.C_InvoiceSchedule_ID

	WHERE fc.type_conditions='Refund' 
	AND (CASE WHEN p_C_BPartner_ID IS NOT NULL THEN bp.c_bpartner_id = p_C_BPartner_ID ELSE TRUE END)
	AND (CASE WHEN p_M_Product_ID IS NOT NULL THEN p.M_Product_ID = p_M_Product_ID ELSE TRUE END)
	AND (CASE WHEN p_DateFrom IS NOT NULL THEN ic.DateToInvoice >= p_DateFrom ELSE TRUE END)
	AND (CASE WHEN p_DateTo IS NOT NULL THEN ic.DateToInvoice <= p_DateTo ELSE TRUE END)

	GROUP BY p.manufacturer, 
		bp.Name,
		p.Value, 
		icn.qtyOrdered,
		isch.Name, 
		(to_char(ft.startdate, 'dd.MM.YYYY') || ' - ' || to_char(ft.enddate, 'dd.MM.YYYY')), 
		to_char(ic.DateToInvoice, 'MM.YYYY'),
		ic.netamttoinvoice,
		ic.C_Invoice_Candidate_ID

	ORDER BY bp.Name, p.manufacturer, month_to_invoice, p.Value
	)a
	
GROUP BY manufacturer, bp_name, p_value, 
	scheduler, contract_details, month_to_invoice
	
ORDER BY bp_name, manufacturer, month_to_invoice, p_value
$$
LANGUAGE sql STABLE;
