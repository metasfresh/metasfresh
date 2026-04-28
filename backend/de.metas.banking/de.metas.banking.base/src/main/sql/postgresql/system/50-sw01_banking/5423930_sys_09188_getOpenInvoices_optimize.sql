-- View: t_getopeninvoices

-- DROP VIEW t_getopeninvoices;

CREATE OR REPLACE VIEW t_getopeninvoices AS 
SELECT
	NULL::numeric AS ad_org_id
	, NULL::numeric AS ad_client_id
	, NULL::numeric AS c_invoice_id
	, NULL::numeric AS c_bpartner_id
	, NULL::character(1) AS isprepayorder
	, NULL::character varying AS docno
	, NULL::timestamp without time zone AS invoicedate
	, NULL::character varying AS doctype
	, NULL::character varying AS bpartnername
	, NULL::character(3) AS iso_code
	, NULL::numeric AS orig_total
	, NULL::numeric AS conv_total
	, NULL::numeric AS conv_open
	, NULL::numeric AS discount
	, NULL::numeric AS multiplierap
	, NULL::numeric AS multiplier
	, NULL::character varying AS POReference
;

COMMENT ON VIEW t_getopeninvoices
  IS 'Used as return type in the SQL-function getopeninvoices';






-- Function: getopeninvoices(numeric, numeric, character varying, numeric, timestamp without time zone, numeric, numeric)

-- DROP FUNCTION getopeninvoices(numeric, numeric, character varying, numeric, timestamp without time zone, numeric, numeric);

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
	i.POReference
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
			FROM 	C_Invoice_v i 
			WHERE IsPaid='N' AND Processed='Y'
			AND (
				i.C_BPartner_ID = ANY ( array(select bp.C_BPartner_ID from bpartners bp) ) -- NOTE: we transform subquery to scalar for performances
				-- Include the invoice which were precisely specified, if any
				OR (i.C_Invoice_ID = $6)
			)
		) 
		--
		-- Add prepaid orders
		UNION ALL 
		(
			SELECT
				o.C_Order_ID
				, DocumentNo
				, GrandTotal - coalesce(o_paid.paid,0)
				, 0
				, GrandTotal
				, DateOrdered 
				, C_Currency_ID
				, C_ConversionType_ID
				, o.AD_Client_ID
				, o.AD_Org_ID
				, 1.0
				, 1.0
				, o.C_BPartner_ID 
				, o.C_DocType_ID
				, 'Y'::character varying as isPrePayOrder
				, o.POReference
			FROM 	C_Order o 
			LEFT OUTER JOIN 
				(
					SELECT SUM(amount + discountamt + writeoffamt) AS paid, C_Order_ID 
					FROM C_AllocationLine al 
					INNER JOIN C_AllocationHdr hdr ON (hdr.C_AllocationHdr_ID = al.C_AllocationHdr_ID AND hdr.DocStatus <>'RE') 
					WHERE al.C_Order_ID IS NOT NULL 
					GROUP BY al.C_Order_ID 
				) o_paid ON (o.C_Order_ID = o_paid.C_Order_ID)  
			WHERE o.Docstatus = 'WP'
			--
			AND (
				o.C_BPartner_ID = ANY ( array(select bp.C_BPartner_ID from bpartners bp) ) -- NOTE: we transform subquery to scalar for performances
				-- Include the order which were precisely specified, if any
				OR (o.C_Order_ID = $7)
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
