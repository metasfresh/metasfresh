DROP FUNCTION IF EXISTS report.fresh_payselection_report_details (IN record_id numeric, IN c_bpartner_id numeric, isallowesrpayments character varying);

DROP TABLE IF EXISTS report.fresh_payselection_report_details;

CREATE TABLE report.fresh_payselection_report_details
(
	c_payselectionline_id numeric (10,0), 
	c_bpartner_id numeric (10,0),
	line numeric (10,0),
	description text,
	dateinvoiced timestamp without time zone,
	i_docno character varying (40),
	u_docno character varying (30),
	iso_code character (3),
	grandtotal numeric,
	discountamt numeric,
	payamt numeric,
	cm_description text ,
	cm_date timestamp without time zone,
	cm_docno character varying (30),
	cm_refno character varying (40),
	amount numeric
)
WITH (
	OIDS=FALSE
);

CREATE FUNCTION report.fresh_payselection_report_details(IN record_id numeric, IN c_bpartner_id numeric, IN isallowesrpayments character varying) RETURNS SETOF report.fresh_payselection_report_details AS
$BODY$SELECT
	psl.C_PaySelectionLine_ID,
	bp.C_BPartner_ID,
	psl.line,
	dt.printname || ': ' || i.Documentno AS Description,
	i.DateInvoiced,
	i.poreference AS I_DocNo,
	i.Documentno AS U_DocNo,
	c.ISO_Code,
	i.GrandTotal,
	psl.DiscountAmt,
	psl.PayAmt,
	adt.printname || ': ' || ai.Documentno AS cm_Description,
	ai.DateInvoiced AS CM_Date,
	ai.Documentno AS CM_DocNo,
	ai.POReference AS CM_RefNo,
	tal.amount * -1 AS amount
FROM
	-- Payselection records
	C_PaySelection ps
	LEFT OUTER JOIN C_PaySelectionLine psl ON ps.C_PaySelection_ID = psl.C_PaySelection_ID AND psl.IsActive = 'Y'
	-- Documents
	LEFT OUTER JOIN C_Invoice i ON psl.C_Invoice_ID = i.C_Invoice_ID AND i.IsActive = 'Y'
	LEFT OUTER JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID AND dt.IsActive = 'Y'
	LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID AND c.IsActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.IsActive = 'Y'
	-- Retrieve allocation details
	LEFT OUTER JOIN C_AllocationLine sal ON i.C_Invoice_ID = sal.C_Invoice_ID AND sal.IsActive = 'Y'
	LEFT OUTER JOIN C_AllocationHdr ah ON sal.C_AllocationHdr_ID = ah.C_AllocationHdr_ID AND ah.IsActive = 'Y'
	LEFT OUTER JOIN C_AllocationLine tal ON sal.C_AllocationHdr_ID = tal.C_AllocationHdr_ID AND i.C_Invoice_ID != tal.C_Invoice_ID AND tal.IsActive = 'Y'
	LEFT OUTER JOIN C_Invoice ai ON tal.C_Invoice_ID = ai.C_Invoice_ID AND ai.IsActive = 'Y'
	LEFT OUTER JOIN C_DocType adt ON ai.C_DocType_ID = adt.C_DocType_ID AND adt.IsActive = 'Y'
	LEFT OUTER JOIN C_Currency ac ON ah.C_Currency_ID = ac.C_Currency_ID AND ac.IsActive = 'Y'
	LEFT OUTER JOIN C_BP_BankAccount ba ON ba.C_BP_BankAccount_ID=psl.C_BP_BankAccount_ID AND ba.IsActive = 'Y'
	
WHERE
	ps.IsActive = 'Y'
	AND ps.C_PaySelection_ID = $1
	AND bp.C_BPartner_ID = $2
	-- We don't want to display ESR payments (if they are not allowed). See Task 08383
	AND (
		( $3 = 'N' AND ba.IsESRAccount='N' ) 
		OR $3 = 'Y'
	)

ORDER BY
	c.ISO_Code, line
$BODY$
LANGUAGE sql STABLE;
