
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Description ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) );


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Description ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE
(
	Description Character Varying (255),
	DocumentNo Character Varying (30),
	PreparationDate Timestamp With Time Zone,
	Reference Character Varying (40),
	BP_Value Character Varying (40),
	BP_Name Character Varying (60),
	PrintName Character Varying (60)
)
AS
$$
SELECT
	o.description AS description,
	o.documentno AS documentno,
	o.preparationdate,
	o.poreference AS reference,
	bp.value AS bp_value,
	bp.name AS bp_name,
	COALESCE ( dtt.printname, dt.printname ) AS printname
FROM
	C_Order o
	INNER JOIN C_DocType dt ON o.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
	INNER JOIN C_Bpartner bp ON o.C_Bpartner_ID	= bp.C_Bpartner_ID AND bp.isActive = 'Y'

WHERE
	o.C_Order_ID = $1 AND o.isActive = 'Y'
$$
LANGUAGE sql STABLE
;