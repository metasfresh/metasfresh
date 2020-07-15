DROP FUNCTION IF EXISTS KPI_Accounts_Payable_Turnover_Function ( date, date);
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Accounts_Payable_Turnover_Function ( date, date);
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Accounts_Payable_Turnover_Function (IN DateFrom date, IN DateTo date)
RETURNS TABLE 
(
	TurnoverAccountsReceivable numeric,
	AverageDaysDue numeric 
)

AS
$$

SELECT 
	ROUND(x.DaysAvg) as  TurnoverAccountsReceivable, 

	ROUND ((x.ComparisonDueDate)  :: numeric / GREATEST(x.InvCount,1)) AS AverageDaysDue
	 

FROM

(

SELECT
	SUM(date_part('day', (ah.dateTrx - i.DateInvoiced)))  :: numeric /Greatest (COUNT(i.C_Invoice_ID), 1 :: bigint) as DaysAvg,
	COALESCE ( SUM(date_part('day',  ah.dateTrx - (i.DateInvoiced + pt.NetDays))) ,0) AS ComparisonDueDate,
	COUNT(i.C_Invoice_ID) AS InvCount
	
FROM

	C_Invoice i 
	JOIN C_AllocationLine al ON i.C_Invoice_ID = al.C_Invoice_ID
	JOIN C_AllocationHdr ah ON al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID
	JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID OR i.C_DocTypeTarget_ID = dt.C_DocType_ID
	JOIN C_PaymentTerm pt ON i.C_PaymentTerm_ID = pt.C_PaymentTerm_ID

WHERE 
	dt.DocBaseType = 'ARI' -- incoming Invoices
	AND i.DocStatus IN ('CO', 'CL')
	AND i.DateInvoiced >= $1 AND i.DateInvoiced <= $2
	
	
) x


	
$$
LANGUAGE sql STABLE;
