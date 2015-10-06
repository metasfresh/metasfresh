
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root
(
	AD_User_ID numeric(10,0),
	AD_Org_ID numeric(10,0),
	M_InOut_ID numeric(10,0),
	DocStatus Character (2),
	C_BPartner_ID numeric(10,0),
	C_BPartner_Location_ID numeric (10,0),
	PrintName Character Varying(60),
	AD_Language Text,
	IsHidePackingMaterialInShipmentPrint Character (1)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root AS
$$SELECT
	io.ad_user_id,
	io.ad_org_id,
	io.m_inout_id,
	io.docstatus,
	io.c_bpartner_id,
	io.c_bpartner_location_id,
	CASE WHEN io.DocStatus = 'DR'
		THEN dt.printname
		ELSE COALESCE(dtt.printname,dt.printname)
	END AS printname,
	CASE WHEN io.DocStatus IN ('DR', 'IP') THEN 'de_CH' ELSE $2 END AS AD_Language,
	isHidePackingMaterialInShipmentPrint
FROM
	M_InOut io
	JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
	INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
	LEFT OUTER JOIN C_DocType_Trl dtt ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2
WHERE
	io.M_InOut_ID = $1
$$
LANGUAGE sql STABLE
;