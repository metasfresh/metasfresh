drop function if exists report.Balance_Sheet_Function(Date DATE, p_IncludePostingTypeStatistical char) ;

drop function if exists report.Balance_Sheet_Function(Date DATE,  ad_org_id numeric(10,0),  p_IncludePostingTypeStatistical char) ;

CREATE OR REPLACE FUNCTION report.Balance_Sheet_Function (IN Date Date,  IN ad_org_id numeric(10,0), IN p_IncludePostingTypeStatistical char(1) = 'N') 
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
	C_ElementValue_ID numeric,
	
	ad_org_id numeric,
	iso_code character(3)
)
AS
$$

SELECT * 
FROM report.saldobilanz_Report($1, 'N', 'N', $2 ,$3) 
WHERE accountType IN('A','L') 
AND ad_Org_ID = $2


$$
LANGUAGE sql STABLE;