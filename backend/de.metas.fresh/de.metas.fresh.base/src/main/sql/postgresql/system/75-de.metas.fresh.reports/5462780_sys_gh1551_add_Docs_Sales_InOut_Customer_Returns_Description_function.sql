DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Customer_Returns_Description(IN record_id numeric, IN AD_Language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Customer_Returns_Description(IN record_id numeric, IN AD_Language Character Varying (6))
RETURNS TABLE 
	(
	printname character varying(60),
	documentno character varying(30),
	bp_value character varying(40),
	movementdate timestamp without time zone
	)
AS
$$	
SELECT
	COALESCE(dtt.printName, dt.printName) as printname,
	io.DocumentNo AS documentNo,
	bp.Value as BP_Value,
	io.movementDate as movementDate

FROM M_Inout io --customer return
INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.isActive = 'Y' AND dtt.AD_Language = $2


INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'

WHERE io.M_InOut_ID = $1

	
$$
LANGUAGE sql STABLE;