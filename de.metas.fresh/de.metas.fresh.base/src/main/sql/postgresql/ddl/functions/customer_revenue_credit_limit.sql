DROP FUNCTION IF EXISTS report.customer_revenue_credit_limit 
( 
	IN p_Date date,
	IN p_C_BPartner_ID numeric
);
CREATE OR REPLACE FUNCTION report.customer_revenue_credit_limit 
( 
	IN p_Date date,
	IN p_C_BPartner_ID numeric
)
RETURNS TABLE
(
	BP_Value character varying(40),
	BP_Name1 character varying(60), 
	BP_Name2 character varying(60), 
	Address1 character varying(100), 
	Postal character varying(10), 
	City character varying(60),
	CountryName character varying(60),
	Responsible_Sales_Person character varying,
	Customer_Group character varying,
	Revenue_3_Months numeric,
	Revenue_2_Months numeric,
	Revenue_last_Month numeric,
	InvoiceSchedule character varying(60),
	PaymentTerm character varying(60),
	Paymentrule character varying(60),
	Creditlimit_Insurance numeric,
	Creditlimit_Management numeric,
	isActive character(1),
	Created date
)
AS $$

SELECT  
	bp.Value AS BP_Value,
	bp.Name AS BP_Name1,
	bp.Name2 AS BP_Name2,	
	l.Address1,
	l.Postal,
	l.City,
	c.Name AS CountryName,
	bp.salesresponsible AS Responsible_Sales_Person,
	refl_sg.name AS Customer_Group,
	
		SUM( CASE WHEN 	
			fa.DateAcct::date >= ((date_trunc('month', p_Date) - interval '3 month')::date)
			AND fa.DateAcct::date <= ((date_trunc('month', p_Date) - interval '2 month' - interval '1 day')::date)
			THEN AmtAcctCr - AmtAcctDr ELSE 0 END ) 
		
	AS Revenue_3_Months,
			
		SUM( CASE WHEN 		
				fa.DateAcct::date >= ((date_trunc('month', p_Date) - interval '2 month')::date)
				AND fa.DateAcct::date <= ((date_trunc('month', p_Date) - interval '1 month' - interval '1 day')::date)
				THEN AmtAcctCr - AmtAcctDr ELSE 0 END ) 
	AS Revenue_2_Months,
	
		SUM( CASE WHEN 	
				fa.DateAcct::date >= (SELECT (date_trunc('month', p_Date) - interval '1 month')::date)
				AND fa.DateAcct::date <= (SELECT (date_trunc('month', p_Date) - interval '1 day')::date)
				THEN AmtAcctCr - AmtAcctDr ELSE 0 END )  		
	AS Revenue_last_Month,
	
	isch.Name AS InvoiceSchedule,
	pt.Name AS PaymentTerm,
	refl.Name AS Paymentrule,
	bp_cl_i.Amount AS Creditlimit_Insurance,
	bp_cl_m.Amount AS Creditlimit_Management,
	bp.isActive,
	bp.Created::date
	


FROM Fact_Acct fa

INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID
INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
INNER JOIN C_BPartner bp ON fa.C_BPartner_ID = bp.C_BPartner_ID
INNER JOIN C_BPartner_Location bpl ON i.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID
INNER JOIN C_Location l ON bpl.C_Location_ID = l.C_Location_ID
INNER JOIN C_Country c ON l.C_Country_ID = c.C_Country_ID
LEFT JOIN C_InvoiceSchedule isch ON bp.C_InvoiceSchedule_ID = isch.C_InvoiceSchedule_ID
LEFT JOIN C_PaymentTerm pt ON bp.C_PaymentTerm_ID = pt.C_PaymentTerm_ID
LEFT JOIN AD_Ref_List refl ON refl.AD_Reference_ID = (SELECT AD_Reference_ID FROM AD_Reference WHERE Name LIKE '_Payment Rule') AND refl.Value = bp.PaymentRule
LEFT JOIN AD_Ref_List refl_sg ON refl_sg.AD_Reference_ID = (SELECT AD_Reference_ID FROM AD_Reference WHERE Name LIKE 'C_BPartner_Salesgroup') AND refl_sg.Value = bp.salesgroup
LEFT JOIN LATERAL (
				SELECT bp_cl_i.Amount 
				FROM C_BPartner_CreditLimit bp_cl_i
				WHERE bp.C_BPartner_ID = bp_cl_i.C_BPartner_ID 
					AND bp_cl_i.C_CreditLimit_Type_ID = (SELECT C_CreditLimit_Type_ID FROM C_CreditLimit_Type WHERE Name LIKE 'Insurance')
					AND bp_cl_i.dateFrom <= p_Date
					AND bp_cl_i.Processed = 'Y'
				ORDER BY bp_cl_i.dateFrom DESC 
				LIMIT 1
	)bp_cl_i ON TRUE

LEFT JOIN LATERAL (
				SELECT bp_cl_m.Amount 
				FROM C_BPartner_CreditLimit bp_cl_m
				WHERE bp.C_BPartner_ID = bp_cl_m.C_BPartner_ID 
					AND bp_cl_m.C_CreditLimit_Type_ID = (SELECT C_CreditLimit_Type_ID FROM C_CreditLimit_Type WHERE Name LIKE 'Management')
					AND bp_cl_m.dateFrom <= p_Date
					AND bp_cl_m.Processed = 'Y'
				ORDER BY bp_cl_m.dateFrom DESC 
				LIMIT 1
	)bp_cl_m ON TRUE

WHERE
	fa.AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
	AND i.IsSOtrx = 'Y'
	AND (CASE WHEN p_C_BPartner_ID IS NOT NULL THEN bp.C_BPartner_ID = p_C_BPartner_ID ELSE TRUE END)

GROUP BY 
	bp.Value,
	bp.Name,
	bp.Name2,	
	l.Address1,
	l.Postal,
	l.City,
	c.Name,	
	bp.salesresponsible,
	refl_sg.name,	
	isch.Name,
	pt.Name,
	refl.Name,
	bp_cl_i.Amount,
	bp_cl_m.Amount,
	bp.isActive,
	bp.Created::date

$$
LANGUAGE sql STABLE;	 
 