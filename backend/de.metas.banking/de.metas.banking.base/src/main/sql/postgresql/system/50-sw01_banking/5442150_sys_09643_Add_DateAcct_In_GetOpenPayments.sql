

 DROP FUNCTION getopenpayments(numeric, numeric, character varying, numeric, timestamp without time zone, numeric);
 DROP VIEW t_getopenpayments;
 
 -- View: t_getopenpayments

CREATE OR REPLACE VIEW t_getopenpayments AS 
SELECT
	NULL::numeric AS ad_org_id
	, NULL::numeric AS ad_client_id
	, NULL::numeric AS c_payment_id
	, NULL::numeric AS c_bpartner_id
	, NULL::character varying AS docno
	, NULL::timestamp without time zone AS paymentdate
	, NULL::character varying AS doctype
	, NULL::character varying AS bpartnername
	, NULL::character(3) AS iso_code
	, NULL::numeric AS orig_total
	, NULL::numeric AS conv_total
	, NULL::numeric AS conv_open
	, NULL::numeric AS multiplierap
	, NULL::timestamp without time zone AS dateacct
 ;

COMMENT ON VIEW t_getopenpayments
  IS 'Used as return type in the SQL-function getopenpayments';


  
  -- Function: getopenpayments(numeric, numeric, character varying, numeric, timestamp without time zone, numeric)



CREATE OR REPLACE FUNCTION getopenpayments(
	c_bpartner_id numeric -- 1
	, c_currency_id numeric -- 2
	, ismulticurrency character varying -- 3
	, ad_org_id numeric -- 4
	, date timestamp without time zone -- 5
	, c_payment_id numeric -- 6
)
RETURNS SETOF t_getopenpayments AS
$BODY$
SELECT 
	p.AD_Org_ID, 
	p.AD_Client_ID, 
	p.C_Payment_ID, 
	p.C_Bpartner_ID, 
	p.DocumentNo AS DocNo, 
	p.DateTrx AS date, 
	dt.name AS DocType, 
	bp.name AS BPartnerName, 
	c.ISO_Code, 
	p.PayAmt AS Orig_Total, 
	currencyConvert(p.PayAmt, p.C_Currency_ID, $2, $5, p.C_ConversionType_ID, p.AD_Client_ID, p.AD_Org_ID) AS Conv_Total, 
	currencyConvert(paymentAvailable(C_Payment_ID), p.C_Currency_ID, $2, $5, p.C_ConversionType_ID, p.AD_Client_ID, p.AD_Org_ID) AS Conv_Open, 
	p.MultiplierAP::numeric,
	p.DateAcct
FROM 
	C_Payment_v p 
	INNER JOIN C_Currency c ON (p.C_Currency_ID = c.C_Currency_ID) 
	INNER JOIN C_DocType dt ON (p.C_DocType_ID = dt.C_DocType_ID) 
	INNER JOIN C_BPartner bp ON (p.C_BPartner_ID = bp.C_BPartner_ID) 
WHERE 
	( 
		p.C_BPartner_ID = $1 
		OR p.C_BPartner_ID IN (SELECT C_BPartnerRelation_ID FROM C_BP_Relation WHERE C_BPartner_ID = $1 AND isPayFrom = 'Y' AND isActive = 'Y') 
		OR p.C_Payment_ID::numeric = ($6) 
	) 
	AND p.IsAllocated='N' 
	AND p.Processed='Y' 
	AND p.C_Charge_ID IS NULL 
	AND (CASE WHEN $3 = 'Y' THEN p.C_Currency_ID ELSE $2 END) = p.C_Currency_ID 
	AND (CASE WHEN $4 = 0 THEN p.AD_Org_ID ELSE $4 END) = p.AD_Org_ID 
ORDER BY 
	p.DateTrx, p.DocumentNo 
; 
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

COMMENT ON FUNCTION getopenpayments(numeric, numeric, character varying, numeric, timestamp without time zone, numeric) IS '
* Used in de.mets.paymentallocation.form.Allocation.queryPaymentTable()
* Uses the view T_GetOpenPayments as return type';
