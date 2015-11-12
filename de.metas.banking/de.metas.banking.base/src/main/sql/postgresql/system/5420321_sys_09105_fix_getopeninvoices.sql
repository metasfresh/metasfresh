-- Function: getopeninvoices(numeric, numeric, character varying, numeric, timestamp without time zone, numeric, numeric)

-- DROP FUNCTION getopeninvoices(numeric, numeric, character varying, numeric, timestamp without time zone, numeric, numeric);

CREATE OR REPLACE FUNCTION getopeninvoices(c_bpartner_id numeric, c_currency_id numeric, ismulticurrency character varying, ad_org_id numeric, date timestamp without time zone, c_invoice_id numeric, c_order_id numeric)
  RETURNS SETOF t_getopeninvoices AS
$BODY$
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
	i.multiplier
FROM  
	(
		(
			SELECT 
				C_Invoice_ID, DocumentNo as docno, invoiceOpen(i.C_Invoice_ID,C_InvoicePaySchedule_ID) as open, 
				invoiceDiscount(i.C_Invoice_ID,$5,C_InvoicePaySchedule_ID) as discount, GrandTotal as total, 
				DateInvoiced as date, C_Currency_ID as cur, C_ConversionType_ID as convType, i.AD_Client_ID, 
				i.AD_Org_ID, multiplier, multiplierAP, C_BPartner_ID, i.C_DocType_ID, 
				'N'::character varying as isPrePayOrder 
			FROM 	C_Invoice_v i 
			WHERE 	IsPaid='N' AND Processed='Y'
		) 
		UNION 
		(
			SELECT 	o.C_Order_ID,DocumentNo,GrandTotal-coalesce(o_paid.paid,0),0,GrandTotal, DateOrdered, 
				C_Currency_ID, C_ConversionType_ID,o.AD_Client_ID,o.AD_Org_ID,1.0,1.0,o.C_BPartner_ID, 
				o.C_DocType_ID, 'Y'::character varying as isPrePayOrder 
			FROM 	C_Order o 
				LEFT OUTER JOIN 
				(
					SELECT sum(amount + discountamt + writeoffamt) AS paid, C_Order_ID 
					FROM C_AllocationLine al 
					INNER JOIN C_AllocationHdr hdr ON (hdr.C_AllocationHdr_ID = al.C_AllocationHdr_ID AND hdr.docstatus <>'RE') 
					WHERE C_Order_ID IS NOT NULL 
					GROUP BY al.C_Order_ID 
				) o_paid ON (o.C_Order_ID = o_paid.C_Order_ID)  
			WHERE 	Docstatus = 'WP'
		)  
	) i  
	INNER JOIN C_DocType d ON (i.C_DocType_ID=d.C_DocType_ID)  
	INNER JOIN C_Currency c ON (i.Cur=c.C_Currency_ID)  
	INNER JOIN C_BPartner bp ON (i.C_BPartner_ID = bp.C_BPartner_ID)  
WHERE 
	(
		i.C_BPartner_ID = $1
		OR i.C_BPartner_ID IN (SELECT C_BPartner_ID FROM C_BP_Relation WHERE C_BpartnerRelation_ID = $1 AND isPayFrom = 'Y' AND isActive = 'Y')
		OR (i.isPrePayOrder = 'N' AND i.C_Invoice_ID::numeric = ($6))
		OR (i.isPrePayOrder = 'Y' AND i.C_Invoice_ID::numeric = ($7)) 
	) 
	AND (CASE WHEN $3 = 'Y' THEN i.cur ELSE $2 END) = i.cur
	AND (CASE WHEN $4 = 1000000 THEN i.AD_Org_ID ELSE $4 END) = i.AD_Org_ID
ORDER BY 
	i.Date, i.DocNo 
;$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
  
COMMENT ON FUNCTION getopeninvoices(numeric, numeric, character varying, numeric, timestamp without time zone, numeric, numeric) IS '
* Used in de.mets.paymentallocation.form.Allocation.queryInvoiceTable()
* Uses the view T_GetOpenInvoices as return type';
