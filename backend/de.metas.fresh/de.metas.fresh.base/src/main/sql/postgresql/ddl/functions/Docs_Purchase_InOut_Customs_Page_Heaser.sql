DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Customs_Page_Header(IN m_inout_id numeric, IN ad_language character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Customs_Page_Header(IN m_inout_id numeric, IN ad_language character varying)
RETURNS TABLE 
	(
	documentno character varying(30),
	value character varying(40),
	movementdate timestamp without time zone,
	printname character varying(60)
	)
AS
$$	
SELECT
	io.DocumentNo,
	bp.value,
	io.movementDate,
	COALESCE (dtt.Printname, dt.Printname) AS Printname
FROM
	M_InOut io
	INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
	INNER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2
	INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
WHERE
	M_InOut_ID = $1 AND io.isActive = 'Y'
$$
LANGUAGE sql STABLE;