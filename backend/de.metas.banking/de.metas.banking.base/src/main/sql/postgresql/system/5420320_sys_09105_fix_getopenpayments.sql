-- Function: getopenpayments(numeric, numeric, character varying, numeric, timestamp without time zone, numeric)

-- DROP FUNCTION getopenpayments(numeric, numeric, character varying, numeric, timestamp without time zone, numeric);

CREATE OR REPLACE FUNCTION getopenpayments(c_bpartner_id numeric, c_currency_id numeric, ismulticurrency character varying, ad_org_id numeric, date timestamp without time zone, c_payment_id numeric)
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
	p.MultiplierAP::numeric
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
