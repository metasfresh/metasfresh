
DROP FUNCTION IF EXISTS KPI_OperatingCash_Function (numeric, date, date);

DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_OperatingCash_Function (numeric, date, date);

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_OperatingCash_Function (IN AD_Client_ID numeric,  IN DateFrom date, IN DateTo date)
RETURNS TABLE 
(
	OperatingCashDifference numeric 
)
AS
$$


SELECT x.IncomingCash - y.OutgoingCash as OperatingCashDifference

FROM

(
SELECT 
	SUM(currencyConvert(p.PayAMT, p.C_Currency_ID, acs.C_Currency_ID, p.DateAcct, p.C_ConversionType_ID, p.AD_Client_ID, p.AD_Org_ID)) as IncomingCash
	
FROM 

	C_Payment p
	JOIN C_DocType dt ON p.C_DocType_ID = dt.C_DocType_ID
	JOIN AD_ClientInfo ci ON ci.AD_Client_ID = $1
	JOIN C_AcctSchema acs ON ci.C_AcctSchema1_ID = acs.C_AcctSchema_ID
	
WHERE 
	dt.DocBaseType = 'ARR' -- incoming payments
	AND p.DateAcct >= $2 AND p.DateAcct <=$3
	AND p.DocStatus IN ('CO', 'CL')
) x,

(
SELECT 
	SUM(currencyConvert(p.PayAMT, p.C_Currency_ID, acs.C_Currency_ID, p.DateAcct, p.C_ConversionType_ID, p.AD_Client_ID, p.AD_Org_ID)) as OutgoingCash
	
FROM 

	C_Payment p
	JOIN C_DocType dt ON p.C_DocType_ID = dt.C_DocType_ID
	JOIN AD_ClientInfo ci ON ci.AD_Client_ID = $1
	JOIN C_AcctSchema acs ON ci.C_AcctSchema1_ID = acs.C_AcctSchema_ID
	
WHERE 
	dt.DocBaseType = 'APP' -- incoming payments
	AND p.DateAcct >= $2 AND p.DateAcct <=$3
	AND p.DocStatus IN ('CO', 'CL')
) y


	
$$
LANGUAGE sql STABLE;