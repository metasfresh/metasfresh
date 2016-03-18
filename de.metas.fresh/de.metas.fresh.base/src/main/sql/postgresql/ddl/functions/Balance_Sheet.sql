--drop function if exists report.Balance_Sheet_Function(Date numeric, p_IncludePostingTypeStatistical char) ;

CREATE OR REPLACE FUNCTION report.Balance_Sheet_Function (IN Date Date,  IN p_IncludePostingTypeStatistical char(1) = 'N') 
RETURNS TABLE 
(
	
	parentname1 character varying(60),
	parentvalue1 character varying(60),
	parentname2 character varying(60),
	parentvalue2 character varying(60),
	parentname3 character varying(60),
	parentvalue3 character varying(60),
	name character varying(60),
	namevalue character varying(60),
	AccountType char(1),
	
	sameyearsum numeric,
	lastyearsum numeric,
	euroSaldo numeric,
	L3_sameyearsum numeric,
	L3_lastyearsum numeric,
	L2_sameyearsum numeric,
	L2_lastyearsum numeric,
	L1_sameyearsum numeric,
	L1_lastyearsum numeric,
	
	-- More info
	C_Calendar_ID numeric,
	C_ElementValue_ID numeric
)
AS
$$

SELECT * 
FROM report.saldobilanz_Report($1, 'N', 'N',$2) 
WHERE accountType IN('A','L')


$$
LANGUAGE sql STABLE;