

DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_ProjectedCash_Function (numeric, date, date);

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_ProjectedCash_Function (IN AD_Client_ID numeric,  IN DateFrom date, IN DateTo date)
RETURNS TABLE 
(
	ProjectedCashDifference numeric 
)
AS
$$


SELECT (COALESCE(x.IncomingCash, 0)) - (COALESCE(y.OutgoingCash,0)) as ProjectedCashDifference

FROM

(
SELECT 
	SUM(currencyConvert(i.GrandTotal, i.C_Currency_ID, acs.C_Currency_ID, i.DateAcct, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID)) as IncomingCash
	
FROM 

	C_Invoice i
	JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID OR i.C_DocTypeTarget_ID = dt.C_DocType_ID
	JOIN AD_ClientInfo ci ON ci.AD_Client_ID = $1
	JOIN C_AcctSchema acs ON ci.C_AcctSchema1_ID = acs.C_AcctSchema_ID
	
WHERE 
	dt.DocBaseType = 'ARI' -- incoming Invoices
	AND i.DateAcct >= $2 AND i.DateAcct <=$3
	
) x,

(
SELECT 
	SUM(currencyConvert(i.GrandTotal, i.C_Currency_ID, acs.C_Currency_ID, i.DateAcct, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID)) as OutgoingCash
	
FROM 

C_Invoice i
	JOIN C_DocType dt ON i.C_DocType_ID =  dt.C_DocType_ID OR i.C_DocTypeTarget_ID = dt.C_DocType_ID
	JOIN AD_ClientInfo ci ON ci.AD_Client_ID = $1
	JOIN C_AcctSchema acs ON ci.C_AcctSchema1_ID = acs.C_AcctSchema_ID
	
WHERE 
	dt.DocBaseType = 'API' -- incoming Invoices
	AND i.DateAcct >= $2 AND i.DateAcct <=$3
	
) y


	
$$
LANGUAGE sql STABLE;