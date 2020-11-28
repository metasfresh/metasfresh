DROP FUNCTION IF EXISTS report.Docs_Sales_Dunning_Report_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6)) ;

CREATE FUNCTION report.Docs_Sales_Dunning_Report_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6))
	RETURNS TABLE ( 
		processed character, 
		AD_Org_ID numeric,
		AD_Language  Text
	) AS 
$$

SELECT
	dd.processed,
	dd.AD_Org_ID,
	$2 AS AD_Language
FROM
	C_DunningDoc dd
WHERE
	dd.C_DunningDoc_ID = $1

;
$$ 
LANGUAGE sql STABLE;

