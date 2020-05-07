DROP FUNCTION IF EXISTS report.saldobilanz_summary_Report (IN Date, IN defaultAcc character varying, IN showCurrencyExchange character varying, IN ad_org_id numeric(10,0), IN p_IncludePostingTypeStatistical char(1));
DROP FUNCTION IF EXISTS report.saldobilanz_summary_Report (IN Date, IN defaultAcc character varying, IN showCurrencyExchange character varying, IN ad_org_id numeric(10,0), IN p_IncludePostingTypeStatistical char(1), IN p_ExcludePostingTypeYearEnd char(1));

DROP TABLE IF EXISTS report.saldobilanz_summary_Report;



CREATE TABLE report.saldobilanz_summary_Report
(
	parentname1 character varying(60),
	parentvalue1 character varying(60),	

	L1_sameyearsum numeric,
	L1_lastyearsum numeric,
	
	ad_org_id numeric,
	unionorder integer
)
WITH (
	OIDS=FALSE
);

CREATE FUNCTION report.saldobilanz_summary_Report(IN Date Date, IN defaultAcc character varying, IN showCurrencyExchange character varying, IN ad_org_id numeric(10,0), IN p_IncludePostingTypeStatistical char(1) = 'N',  p_ExcludePostingTypeYearEnd char(1) = 'N') RETURNS SETOF report.saldobilanz_summary_Report AS
$BODY$
SELECT
	*,
	1 as unionorder
	FROM report.saldobilanz_summary_Report_sub($1,$2,$3,$4,$5,$6)

UNION ALL SELECT
	null AS parentname1,
	null AS parentvalue1,
	SUM(L1_sameyearsum) AS total_SameYearSum,
	SUM(L1_lastyearsum) AS total_LastYearSum,
	$4 as ad_org_id,
	2 as unionorder
FROM report.saldobilanz_summary_Report_sub($1,$2,$3,$4,$5,$6)
GROUP BY ad_org_id, unionorder

$BODY$
LANGUAGE sql STABLE;