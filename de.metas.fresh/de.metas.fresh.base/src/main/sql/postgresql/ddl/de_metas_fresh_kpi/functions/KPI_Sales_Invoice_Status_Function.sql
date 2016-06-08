CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Sales_Invoice_Status_Function (IN C_Invoice_ID numeric) 
RETURNS TABLE 
(
	
	Invoiced numeric,
	Paid numeric
	
)
AS
$$

SELECT x.Invoiced,
	Greatest(x.Payment, x.Allocated) AS Paid
	
FROM

(
	SELECT 
	(
	CASE 
		WHEN i.DocStatus IN ('CO', 'CL')
		THEN 1
		ELSE 0
		END
	) :: numeric AS Invoiced,

	(
	CASE
		WHEN SUM(p.PayAmt) is not null 
		THEN LEAST((SUM(p.PayAmt) /
			(CASE WHEN i.GrandTotal <> 0 THEN  i.GrandTotal ELSE 1 END)),1)
		ELSE 0
		END
	) :: numeric as Payment,

	(
	CASE
		WHEN SUM(al.Amount) is not null
		THEN LEAST(((@(SUM (al.Amount))) / (CASE WHEN i.GrandTotal <> 0 THEN  i.GrandTotal ELSE 1 END)) ,1)
		ELSE 0
		END
	) :: numeric as Allocated

	FROM C_Invoice i
	LEFT JOIN C_Payment p ON i.C_Invoice_ID = p.C_Invoice_ID
	LEFT JOIN C_AllocationLine al on al.c_invoice_ID = i.c_invoice_ID AND al.C_Payment_ID IS NOT NULL

	WHERE i.C_Invoice_ID = $1 AND i.IsSOTrx = 'Y'

	GROUP BY i.C_Invoice_ID
) x


$$
LANGUAGE sql STABLE;