--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE 
(
	documentno text,
	dateinvoiced timestamp without time zone, 
	bp_value character varying(40),
	PrintName character varying(60),
	o_documentno character varying(30)
)
AS
$$
SELECT
	CASE
		WHEN io.DocNo_hi = io.DocNo_lo THEN io.DocNo_lo
		ELSE io.DocNo_lo || ' ff.'
	END AS documentno,
	io.movementdate AS dateinvoiced,
	bp.value AS bp_value,
	COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
	o.documentno AS o_documentno
FROM
	C_OrderLine ol
	INNER JOIN (
		SELECT
			ol.C_Order_ID,
			MAX(io.movementdate) as movementdate,
			MIN(io.Documentno) as Docno_lo,
			MAX(io.Documentno) as Docno_hi,
			MAX(io.C_DocType_ID) AS C_DocType_ID
		FROM
			C_OrderLine ol
			INNER JOIN M_InOutLine iol ON ol.C_OrderLine_ID = iol.C_OrderLine_ID AND iol.isActive = 'Y'
			INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
		WHERE ol.c_order_id = ( select c_order_id from c_orderline where C_OrderLine_ID = $1) AND ol.isActive = 'Y'
		GROUP BY
			ol.C_Order_ID
	) io ON ol.C_Order_ID = io.C_Order_ID
	INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
	INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
WHERE
	ol.C_OrderLine_ID = $1 AND ol.isActive = 'Y'
$$
LANGUAGE sql STABLE
;



