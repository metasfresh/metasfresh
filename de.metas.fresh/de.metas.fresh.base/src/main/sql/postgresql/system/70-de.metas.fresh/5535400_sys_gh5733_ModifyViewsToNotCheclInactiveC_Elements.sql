DROP VIEW IF EXISTS C_Element_levels;
CREATE OR REPLACE VIEW C_Element_levels AS 
	SELECT
		ev1.C_ElementValue_ID AS lvl1_C_ElementValue_ID, ev1.value AS lvl1_value, ev1.name AS lvl1_name, (ev1.value::text || ' '::text) || ev1.name::text AS lvl1_label
		, ev2.C_ElementValue_ID AS lvl2_C_ElementValue_ID, ev2.value AS lvl2_value, ev2.name AS lvl2_name, (ev2.value::text || ' '::text) || ev2.name::text AS lvl2_label
		, ev3.C_ElementValue_ID AS lvl3_C_ElementValue_ID, ev3.value AS lvl3_value, ev3.name AS lvl3_name, (ev3.value::text || ' '::text) || ev3.name::text AS lvl3_label
		, ev4.C_ElementValue_ID AS lvl4_C_ElementValue_ID, ev4.value AS lvl4_value, ev4.name AS lvl4_name, (ev4.value::text || ' '::text) || ev4.name::text AS lvl4_label
		, ev5.C_ElementValue_ID AS lvl5_C_ElementValue_ID, ev5.value AS lvl5_value, ev5.name AS lvl5_name, (ev5.value::text || ' '::text) || ev5.name::text AS lvl5_label
		, COALESCE(ev5.C_ElementValue_ID, ev4.C_ElementValue_ID, ev3.C_ElementValue_ID, ev2.C_ElementValue_ID, ev1.C_ElementValue_ID) AS C_ElementValue_ID
		, (CASE
			WHEN ev5.C_ElementValue_ID IS NOT NULL THEN ev5.value
			WHEN ev4.C_ElementValue_ID IS NOT NULL THEN ev4.value
			WHEN ev3.C_ElementValue_ID IS NOT NULL THEN ev3.value
			WHEN ev2.C_ElementValue_ID IS NOT NULL THEN ev2.value
			WHEN ev1.C_ElementValue_ID IS NOT NULL THEN ev1.value
			ELSE NULL::character varying
		END) AS Value
		, (CASE
			WHEN ev5.C_ElementValue_ID IS NOT NULL THEN ev5.name
			WHEN ev4.C_ElementValue_ID IS NOT NULL THEN ev4.name
			WHEN ev3.C_ElementValue_ID IS NOT NULL THEN ev3.name
			WHEN ev2.C_ElementValue_ID IS NOT NULL THEN ev2.name
			WHEN ev1.C_ElementValue_ID IS NOT NULL THEN ev1.name
			ELSE NULL::character varying
		END) AS Name
		, (CASE
			WHEN ev5.C_ElementValue_ID IS NOT NULL THEN (ev5.value::text || ' '::text) || ev5.name::text
			WHEN ev4.C_ElementValue_ID IS NOT NULL THEN (ev4.value::text || ' '::text) || ev4.name::text
			WHEN ev3.C_ElementValue_ID IS NOT NULL THEN (ev3.value::text || ' '::text) || ev3.name::text
			WHEN ev2.C_ElementValue_ID IS NOT NULL THEN (ev2.value::text || ' '::text) || ev2.name::text
			WHEN ev1.C_ElementValue_ID IS NOT NULL THEN (ev1.value::text || ' '::text) || ev1.name::text
			ELSE NULL::text
		END) AS Label
		, acctBalance(COALESCE(ev5.C_ElementValue_ID, ev4.C_ElementValue_ID, ev3.C_ElementValue_ID, ev2.C_ElementValue_ID, ev1.C_ElementValue_ID), 1::numeric, 0::numeric) AS Multiplicator
FROM C_ElementValue ev1
JOIN C_Element e ON e.C_Element_ID = ev1.C_Element_ID AND e.isActive='Y'
JOIN AD_TreeNode tn1 ON tn1.AD_Tree_ID = e.AD_Tree_ID AND tn1.Node_ID = ev1.C_ElementValue_ID AND (tn1.Parent_ID IS NULL OR tn1.Parent_ID = 0::numeric)
LEFT JOIN AD_TreeNode tn2 ON tn2.AD_Tree_ID = e.AD_Tree_ID AND tn2.Parent_ID = tn1.Node_ID
LEFT JOIN C_ElementValue ev2 ON ev2.C_ElementValue_ID = tn2.Node_ID
LEFT JOIN AD_TreeNode tn3 ON tn3.AD_Tree_ID = e.AD_Tree_ID AND tn3.Parent_ID = tn2.Node_ID
LEFT JOIN C_ElementValue ev3 ON ev3.C_ElementValue_ID = tn3.Node_ID
LEFT JOIN AD_TreeNode tn4 ON tn4.AD_Tree_ID = e.AD_Tree_ID AND tn4.Parent_ID = tn3.Node_ID
LEFT JOIN C_ElementValue ev4 ON ev4.C_ElementValue_ID = tn4.Node_ID
LEFT JOIN AD_TreeNode tn5 ON tn5.AD_Tree_ID = e.AD_Tree_ID AND tn5.Parent_ID = tn4.Node_ID
LEFT JOIN C_ElementValue ev5 ON ev5.C_ElementValue_ID = tn5.Node_ID;


--
-- this overwrites a view in de.metas.fresh !
--
DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN Date, IN defaultAcc character varying);
DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN Date, IN defaultAcc character varying, IN showCurrencyExchange character varying);
DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN Date, IN defaultAcc character varying, IN showCurrencyExchange character varying,  IN p_IncludePostingTypeStatistical char(1));

DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN Date, IN defaultAcc character varying, IN showCurrencyExchange character varying, IN p_IncludePostingTypeStatistical char(1),  IN ad_org_id numeric(10,0));
DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN Date, IN defaultAcc character varying, IN showCurrencyExchange character varying, IN ad_org_id numeric(10,0), IN p_IncludePostingTypeStatistical char(1));
DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN Date, IN defaultAcc character varying, IN showCurrencyExchange character varying, IN ad_org_id numeric(10,0), IN p_IncludePostingTypeStatistical char(1), IN p_ExcludePostingTypeYearEnd char(1));

DROP TABLE IF EXISTS report.saldobilanz_Report;



CREATE TABLE report.saldobilanz_Report
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
	currency character(3)
)
WITH (
	OIDS=FALSE
);

CREATE FUNCTION report.saldobilanz_Report(IN Date Date, IN defaultAcc character varying, IN showCurrencyExchange character varying, IN ad_org_id numeric(10,0), IN p_IncludePostingTypeStatistical char(1) = 'N',  p_ExcludePostingTypeYearEnd char(1) = 'N') RETURNS SETOF report.saldobilanz_Report AS
$BODY$
SELECT
	parentname1,
	parentvalue1,
	parentname2,
	parentvalue2,
	parentname3,
	parentvalue3,
	name,
	value,
	AccountType,
	
	SameYearSum AS L4_SameYearSum,
	LastYearSum AS L4_LastYearSum,
	(case when IsConvertToEUR
		then currencyConvert(a.SameYearSum
			, a.C_Currency_ID -- p_curfrom_id
			, (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR' AND isActive = 'Y') -- p_curto_id
			, $1 -- p_convdate
			, (SELECT C_ConversionType_ID FROM C_ConversionType where Value='P' AND isActive = 'Y') -- p_conversiontype_id
			, a.AD_Client_ID
			, $4 --ad_org_id
			)
		else null
	end) as L4_euroSaldo,
	--
	SUM(CASE WHEN ParentValue3 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue3) AS L3_SameYearSum,
	SUM(CASE WHEN ParentValue3 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue3) AS L3_LastYearSum,
	SUM(CASE WHEN ParentValue2 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue2) AS L2_SameYearSum,
	SUM(CASE WHEN ParentValue2 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue2) AS L2_LastYearSum,
	SUM(CASE WHEN ParentValue1 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue1) AS L1_SameYearSum,
	SUM(CASE WHEN ParentValue1 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue1) AS L1_LastYearSum,
	
	-- More info:
	C_Calendar_ID
	, C_ElementValue_ID,
	
	$4 as ad_org_id,
	a.iso_code
FROM
	(
		SELECT
			lvl.Lvl1_name as ParentName1
			, lvl.Lvl1_value as ParentValue1
			, lvl.Lvl2_name as ParentName2
			, lvl.Lvl2_value as ParentValue2
			, lvl.Lvl3_name as ParentName3
			, lvl.Lvl3_value as ParentValue3
			, lvl.Name as Name
			, lvl.Value as Value
			, ev.AccountType
			
			, (de_metas_acct.acctBalanceToDate(ev.C_ElementValue_ID, acs.C_AcctSchema_ID, $1::date, $4, $5, $6)).Balance * ev.Multiplicator as SameYearSum
			, (de_metas_acct.acctBalanceToDate(ev.C_ElementValue_ID, acs.C_AcctSchema_ID, period_LastYearEnd.EndDate::date, $4, $5, $6)).Balance * ev.Multiplicator as LastYearSum
			
			-- Hardcoding replaced
			
			--show euro saldo only on 1021 and 2001 accounts
			--lvl.Value IN('1021','2001') AND $3='Y'
			-- AS IsConvertToEUR
			
			
			, CASE WHEN $3='N' -- we don't need to check if the elementValue has a foreign currency
			THEN false
			ELSE -- check if the element value is set to show the Internation currency and if this currency is EURO. Convert to EURO in this case
				(
					exists
					(select 1 from C_ElementValue elv where lvl.C_ElementValue_ID = elv.C_ElementValue_ID and elv.ShowIntCurrency = 'Y' and elv.Foreign_Currency_ID = (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR' AND isActive = 'Y') and elv.isActive = 'Y')
				)		
			END			
			AS IsConvertToEUR
			
			--
			, acs.C_Currency_ID -- Accounting currency
			, acs.AD_Client_ID
			
			, ci.C_Calendar_ID
			, lvl.C_ElementValue_ID
			, c.iso_code
		FROM
			C_Period p 
				-- Get last period of previous year
				LEFT OUTER JOIN C_Period period_LastYearEnd ON (period_LastYearEnd.C_Period_ID = report.Get_Predecessor_Period_Recursive (p.C_Period_ID, p.PeriodNo::int)) AND period_LastYearEnd.isActive = 'Y'
			--
			, C_Element_Levels lvl
				INNER JOIN (
					SELECT ev.C_ElementValue_ID
					-- NOTE: by customer requirement, we are not considering the account sign but always DR - CR
					, 1 as Multiplicator
					-- , acctBalance(C_ElementValue_ID, 1, 0) AS Multiplicator
					, ev.ad_client_id
					, ev.AccountType
					FROM C_ElementValue ev
					JOIN C_Element e on e.C_Element_id = ev.C_Element_ID and e.IsActive='Y'
					WHERE ev.isActive = 'Y' and e.IsNaturalAccount='Y'
				) ev ON (lvl.C_ElementValue_ID = ev.C_ElementValue_ID)
				LEFT OUTER JOIN AD_ClientInfo ci ON (ci.AD_Client_ID=ev.AD_Client_ID) AND ci.isActive = 'Y'
				LEFT OUTER JOIN C_AcctSchema acs ON (acs.C_AcctSchema_ID=ci.C_AcctSchema1_ID) AND acs.isActive = 'Y'
				LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID=c.C_Currency_ID AND c.isActive = 'Y'
		--
		WHERE true
			-- Period: determine it by DateAcct
			AND p.C_Period_ID = report.Get_Period( ci.C_Calendar_ID, $1 ) 
			-- Shall we Show default accounts?
			AND (CASE WHEN $2='Y' THEN true ELSE lvl1_value != 'ZZ' END)
			AND p.isActive = 'Y'
			
	) a
ORDER BY
	parentValue1, parentValue2, parentValue3, value 
$BODY$
LANGUAGE sql STABLE;

