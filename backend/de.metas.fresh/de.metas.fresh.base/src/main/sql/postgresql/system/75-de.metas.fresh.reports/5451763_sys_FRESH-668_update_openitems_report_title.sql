DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report_Title(IN AD_Org_ID numeric, IN C_BPartner_ID numeric, IN IsSOTrx character varying, IN InvoiceCollectionType character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.OpenItems_Report_Title(IN AD_Org_ID numeric, IN C_BPartner_ID numeric, IN IsSOTrx character varying, IN InvoiceCollectionType character varying)
	RETURNS TABLE ( 
		today timestamp with time zone, 
		org character varying,
		BPartner text,
		InvColType character varying,
		IsSOTrx text
	) AS 
$$


SELECT
	getDate() AS today,
	COALESCE((SELECT Name FROM AD_Org WHERE $1 = AD_Org_ID AND isActive = 'Y'), 'Alle') AS org,
	COALESCE((
		SELECT Value || ' ' || Name FROM C_BPartner WHERE $2 = C_BPartner_ID AND isActive = 'Y'
	), 'Alle') as BPartner,
	COALESCE((SELECT Name FROM AD_Ref_List WHERE $4 = Value AND AD_Reference_ID = (
		SELECT AD_Reference_ID FROM AD_Reference WHERE name = 'C_Invoice InvoiceCollectionType' AND isActive = 'Y') AND isActive = 'Y'
	), 'Alle') AS InvColType,
	CASE
		WHEN $3 = 'Y' THEN 'Ja'
		WHEN $3 = 'N' THEN 'Nein'
		WHEN $3 IS NULL THEN 'Alle'
	END AS IsSOTrx
;

$$ 
LANGUAGE sql STABLE;