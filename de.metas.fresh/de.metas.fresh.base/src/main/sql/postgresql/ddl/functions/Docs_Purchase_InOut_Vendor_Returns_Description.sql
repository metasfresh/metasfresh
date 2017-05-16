DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Description(IN record_id numeric, IN AD_Language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Description(IN record_id numeric, IN AD_Language Character Varying (6))
RETURNS TABLE 
	(
	printname character varying(60),
	io_printname character varying(60),
	documentno character varying(30),
	io_documentno text,
	bp_value character varying(40),
	movementdate_io timestamp without time zone, 
	movementdate timestamp without time zone
	)
AS
$$	
SELECT
	COALESCE(dtt.printName, dt.printName) as printname,
	COALESCE(io_dtt.printName, io_dt.printName) as io_printname,
	io.DocumentNo AS documentNo,
	CASE
		WHEN io_origin.DocNo_hi = io_origin.DocNo_lo THEN io_origin.DocNo_lo
		ELSE io_origin.DocNo_lo || ' ff.'
	END AS io_documentno,
	bp.Value as BP_Value,
	io.movementDate as movementDate,
	io_origin.movementDate as movementdate_io

FROM M_Inout io --vendor return
--INNER JOIN M_InOutLine iol ON io.M_Inout_ID = iol.M_Inout_ID
INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.isActive = 'Y' AND dtt.AD_Language = $2


--data from original inout
INNER JOIN (
	SELECT 
		iol.M_InOut_ID,
		MAX(io_origin.movementdate) as movementdate,
		MIN(io_origin.Documentno) as Docno_lo,
		MAX(io_origin.Documentno) as Docno_hi,
		MAX(io_origin.C_DocType_ID) AS C_DocType_ID
	FROM M_InOutLine iol
	INNER JOIN M_InOutLine iol_origin ON iol.vendorreturn_origin_inoutline_id = iol_origin.M_InOutLine_ID AND iol_origin.isActive = 'Y'
	INNER JOIN M_InOut io_origin ON iol_origin.M_InOut_ID = io_origin.M_InOut_ID AND io_origin.isActive = 'Y'
	
	GROUP BY iol.M_InOut_ID
) io_origin ON io.M_InOut_ID = io_origin.M_InOut_ID

INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
INNER JOIN C_DocType io_dt ON io_origin.C_DocType_ID = io_dt.C_DocType_ID AND io_dt.isActive = 'Y'
LEFT OUTER JOIN C_DocType_Trl io_dtt ON io_dt.C_DocType_ID = io_dtt.C_DocType_ID AND io_dtt.isActive = 'Y' AND io_dtt.AD_Language = $2

WHERE io.M_InOut_ID = $1

	
$$
LANGUAGE sql STABLE;