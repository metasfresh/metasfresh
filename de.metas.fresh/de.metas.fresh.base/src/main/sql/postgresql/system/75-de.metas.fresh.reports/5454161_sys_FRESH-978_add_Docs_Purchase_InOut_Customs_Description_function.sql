DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Customs_Description(IN c_order_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Customs_Description(IN c_order_id numeric)
RETURNS TABLE 
	(
	documentno text,
	movementdate timestamp without time zone,
	bpartneraddress character varying,
	value character varying(40),
	deliverto character varying
	)
AS
$$	

SELECT
	CASE
		WHEN io.DocNo_hi = io.DocNo_lo THEN io.DocNo_lo
		ELSE io.DocNo_lo || ' ff.'
	END AS documentno,
	io.movementdate,
	io.BPartnerAddress,
	bp.value,
	io.deliverto
FROM
	C_Order o
	INNER JOIN (
		SELECT
			ol.C_Order_ID,
			MAX(io.movementdate) as movementdate,
			MIN(io.Documentno) as Docno_lo,
			MAX(io.Documentno) as Docno_hi,
			first_agg(io.C_BPartner_ID::text ORDER BY io.Documentno)::numeric AS C_BPartner_ID,
			first_agg(io.BPartnerAddress ORDER BY io.Documentno ) AS BPartnerAddress,
			first_agg(io.DeliveryToAddress ORDER BY io.Documentno )AS deliverto
		FROM
			C_OrderLine ol
			INNER JOIN M_ReceiptSchedule rs ON rs.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_OrderLine')
				AND ol.C_OrderLine_ID = rs.RECORD_ID
			INNER JOIN M_ReceiptSchedule_Alloc rsa ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
			INNER JOIN M_InOutLine iol ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID
			INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
		GROUP BY
			ol.C_Order_ID
	) io ON o.C_Order_ID = io.C_Order_ID
	INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
WHERE
	o.C_Order_ID = $1
	
$$
LANGUAGE sql STABLE;