-- Function: de_metas_endcustomer_fresh_reports.docs_purchase_inout_page_header(numeric, character varying)

-- DROP FUNCTION de_metas_endcustomer_fresh_reports.docs_purchase_inout_page_header(numeric, character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_purchase_inout_page_header(IN record_id numeric, IN ad_language character varying)
  RETURNS TABLE(
				documentno text, 
				dateinvoiced timestamp without time zone, 
				bp_value character varying, 
				printname character varying
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
	COALESCE(dtt.PrintName, dt.PrintName) AS PrintName
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
		WHERE ol.C_OrderLine_ID = $1 AND ol.isActive = 'Y'
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
  LANGUAGE sql STABLE;
