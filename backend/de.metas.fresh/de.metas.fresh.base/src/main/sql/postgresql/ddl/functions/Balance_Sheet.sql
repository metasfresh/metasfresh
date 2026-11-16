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
	parentname4 character varying(60),
	parentvalue4 character varying(60),
	parentname5 character varying(60),
	parentvalue5 character varying(60),
	parentname6 character varying(60),
	parentvalue6 character varying(60),
	parentname7 character varying(60),
	parentvalue7 character varying(60),
	parentname8 character varying(60),
	parentvalue8 character varying(60),
	parentname9 character varying(60),
	parentvalue9 character varying(60),
	name character varying(60),
	namevalue character varying(60),
	AccountType char(1),

	sameyearsum numeric,
	lastyearsum numeric,
	euroSaldo numeric,
	L9_sameyearsum numeric,
	L9_lastyearsum numeric,
	L8_sameyearsum numeric,
	L8_lastyearsum numeric,
	L7_sameyearsum numeric,
	L7_lastyearsum numeric,
	L6_sameyearsum numeric,
	L6_lastyearsum numeric,
	L5_sameyearsum numeric,
	L5_lastyearsum numeric,
	L4_sameyearsum numeric,
	L4_lastyearsum numeric,
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
SELECT
	s.parentname1, s.parentvalue1,
	s.parentname2, s.parentvalue2,
	s.parentname3, s.parentvalue3,
	s.parentname4, s.parentvalue4,
	s.parentname5, s.parentvalue5,
	s.parentname6, s.parentvalue6,
	s.parentname7, s.parentvalue7,
	s.parentname8, s.parentvalue8,
	s.parentname9, s.parentvalue9,
	s.name, s.namevalue, s.AccountType,
	s.sameyearsum, s.lastyearsum, s.eurosaldo,
	s.L9_sameyearsum, s.L9_lastyearsum,
	s.L8_sameyearsum, s.L8_lastyearsum,
	s.L7_sameyearsum, s.L7_lastyearsum,
	s.L6_sameyearsum, s.L6_lastyearsum,
	s.L5_sameyearsum, s.L5_lastyearsum,
	s.L4_sameyearsum, s.L4_lastyearsum,
	s.L3_sameyearsum, s.L3_lastyearsum,
	s.L2_sameyearsum, s.L2_lastyearsum,
	s.L1_sameyearsum, s.L1_lastyearsum,
	s.C_Calendar_ID, s.C_ElementValue_ID,
	s.ad_org_id, s.currency AS iso_code
FROM report.saldobilanz_Report($1, 'N', 'N', $2, $3) s
WHERE s.accountType IN('A','L')
AND s.ad_Org_ID = $2
$$
LANGUAGE sql STABLE;
