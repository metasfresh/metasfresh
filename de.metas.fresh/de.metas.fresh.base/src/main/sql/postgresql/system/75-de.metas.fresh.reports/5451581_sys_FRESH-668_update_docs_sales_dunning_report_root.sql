DROP FUNCTION IF EXISTS report.Docs_Sales_Dunning_Report_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6)) ;

CREATE FUNCTION report.Docs_Sales_Dunning_Report_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6))
	RETURNS TABLE ( 
		processed character, 
		AD_Org_ID numeric,
		AD_Language  Text,
		countrycode character (2)
	) AS 
$$

SELECT
	dd.processed,
	dd.AD_Org_ID,
	$2 AS AD_Language,
	c.countrycode
FROM
	C_DunningDoc dd

LEFT OUTER JOIN AD_OrgInfo orginfo ON orginfo.ad_org_id = dd.ad_org_id AND orginfo.isActive = 'Y'
LEFT OUTER JOIN C_BPartner_Location org_loc ON orginfo.Orgbp_Location_ID = org_loc.C_BPartner_Location_ID AND org_loc.isActive = 'Y'
LEFT OUTER JOIN C_Location org_l ON org_loc.C_Location_ID = org_l.C_Location_ID AND org_l.isActive = 'Y'
LEFT OUTER JOIN C_Country c ON org_l.C_Country_ID = c.C_Country_ID AND c.isActive = 'Y'

WHERE
	dd.C_DunningDoc_ID = $1 AND dd.isActive = 'Y'

;
$$ 
LANGUAGE sql STABLE;



