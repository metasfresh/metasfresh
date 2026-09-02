CREATE OR REPLACE FUNCTION getopeninvoices
(
	c_bpartner_id numeric -- 1
	, c_currency_id numeric -- 2
	, ismulticurrency character varying -- 3
	, ad_org_id numeric -- 4
	, date timestamp without time zone -- 5
	, c_invoice_id numeric -- 6
	, c_order_id numeric -- 7
)
RETURNS SETOF t_getopeninvoices AS
$BODY$
--
WITH bpartners AS (
	select $1::integer as C_BPartner_ID
	union all
	select r.C_BPartner_ID FROM C_BP_Relation r WHERE r.C_BpartnerRelation_ID = $1 AND r.isPayFrom = 'Y' AND r.isActive = 'Y'
)
--
SELECT 
	i.AD_Org_ID, 
	i.AD_Client_ID,
	i.C_Invoice_ID,
	i.C_BPartner_ID, 
	i.isPrePayOrder,
	i.DocNo,
	i.Date AS InvoiceDate, 
	d.Name as DocType,
	bp.name as BPartnerName,
	c.ISO_Code,
	i.Total as Orig_Total,
	currencyConvert(i.Total,i.Cur,$2,$5,i.ConvType,i.AD_Client_ID,i.AD_Org_ID) as Conv_Total,
	currencyConvert(i.open,i.Cur,$2,$5,i.ConvType,i.AD_Client_ID,i.AD_Org_ID) as Conv_Open,
	currencyConvert(i.discount,i.Cur,$2,i.Date,i.ConvType,i.AD_Client_ID,i.AD_Org_ID) as Discount,
	i.multiplierAP, 
	i.multiplier,
	i.POReference,
	i.DateAcct  -- task 09643: separate transaction date form accounting date
FROM  
	(
		--
		-- Invoices
		(
			SELECT 
				C_Invoice_ID
				, DocumentNo as docno
				, invoiceOpen(i.C_Invoice_ID,C_InvoicePaySchedule_ID) as open
				, invoiceDiscount(i.C_Invoice_ID,$5,C_InvoicePaySchedule_ID) as discount
				, GrandTotal as total 
				, DateInvoiced as date
				, C_Currency_ID as cur
				, C_ConversionType_ID as convType
				, i.AD_Client_ID 
				, i.AD_Org_ID
				, multiplier, multiplierAP
				, C_BPartner_ID, i.C_DocType_ID
				, 'N'::character varying as isPrePayOrder
				, i.POReference
				, i.DateAcct
			FROM 	C_Invoice_v i 
			WHERE IsPaid='N' AND Processed='Y'
			AND (
				i.C_BPartner_ID = ANY ( array(select bp.C_BPartner_ID from bpartners bp) ) -- NOTE: we transform subquery to scalar for performances
				-- Include the invoice which were precisely specified, if any
				OR (i.C_Invoice_ID = $6)
			)
		)   
	) i  
	INNER JOIN C_DocType d ON (i.C_DocType_ID=d.C_DocType_ID)  
	INNER JOIN C_Currency c ON (i.Cur=c.C_Currency_ID)  
	INNER JOIN C_BPartner bp ON (i.C_BPartner_ID = bp.C_BPartner_ID)  
WHERE true 
	AND (CASE WHEN $3 = 'Y' THEN i.cur ELSE $2 END) = i.cur
	AND (CASE WHEN $4 = 1000000 THEN i.AD_Org_ID ELSE $4 END) = i.AD_Org_ID
ORDER BY i.Date, i.DocNo 
;$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

COMMENT ON FUNCTION getopeninvoices(numeric, numeric, character varying, numeric, timestamp without time zone, numeric, numeric) IS '
* Used in de.mets.paymentallocation.form.Allocation.queryInvoiceTable()
* Uses the view T_GetOpenInvoices as return type';
