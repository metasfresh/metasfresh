DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6))
RETURNS TABLE 
(
	printname character varying(60),
	io_printname character varying(60),
	documentno character varying(30),
	io_documentno text,
	bp_name character varying(60),
	bp_value character varying(40),
	inventorydate timestamp without time zone,
	movementdate timestamp without time zone
)
AS
$$
SELECT 
	COALESCE(dtt.printName, dt.printName) as printname,
	COALESCE(io_dtt.printName, io_dt.printName) as io_printname,
	i.DocumentNo AS documentNo,
	CASE
		WHEN il.DocNo_hi = il.DocNo_lo THEN il.DocNo_lo
		ELSE il.DocNo_lo || ' ff.'
	END AS io_documentno,
	bp.Name as BP_Name,
	bp.Value as BP_Value,
	i.movementDate as inventorydate,
	il.movementDate as movementdate

FROM M_Inventory i

-- data from inventory
INNER JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.isActive = 'Y' AND dtt.AD_Language = $2

--data from inout
INNER JOIN (
	SELECT 
		il.M_Inventory_ID,
		MAX(io.movementdate) as movementdate,
		MIN(io.Documentno) as Docno_lo,
		MAX(io.Documentno) as Docno_hi,
		MAX(io.C_DocType_ID) AS C_DocType_ID,
		io.C_Bpartner_ID 
	FROM M_InventoryLine il
	INNER JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
	INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'

	GROUP BY M_Inventory_ID, C_Bpartner_ID
) il ON i.M_Inventory_ID = il.M_Inventory_ID

INNER JOIN C_BPartner bp ON il.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
INNER JOIN C_DocType io_dt ON il.C_DocType_ID = io_dt.C_DocType_ID AND io_dt.isActive = 'Y'
LEFT OUTER JOIN C_DocType_Trl io_dtt ON io_dt.C_DocType_ID = io_dtt.C_DocType_ID AND io_dtt.isActive = 'Y' AND io_dtt.AD_Language = $2

WHERE i.M_Inventory_ID = $1 AND i.isActive = 'Y'

ORDER BY bp_value, io_documentno, movementdate
$$
LANGUAGE sql STABLE
;