--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Details_HU_Bal ( IN Record_ID numeric );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Details_HU_Bal ( IN Record_ID numeric )
RETURNS TABLE 
(
	Name character varying, 
	Incoming numeric,
	Outgoing numeric,
	UOMSymbol character varying
)
AS
$$

SELECT
	mbd.Name, --Product
	SUM(Incoming) AS Incoming,
	SUM(Outgoing) AS Outgoing,
	mbd.UOMSymbol
FROM
	(
		SELECT	MAX(io.movementdate)::date AS Movementdate,
			First_Agg(io.C_BPartner_ID::text)::numeric AS C_BPartner_ID
		FROM	C_OrderLine ol
			INNER JOIN M_InOutLine iol ON ol.C_OrderLine_ID = iol.C_OrderLine_ID AND iol.isActive = 'Y'
			INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
		WHERE 	ol.C_Order_ID = $1 AND ol.isActive = 'Y'
	) io
	INNER JOIN de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_Details
		(
			540000::numeric,
			(SELECT C_BPartner_ID FROM C_Order WHERE C_Order_ID = $1 AND isActive = 'Y'),
			null::date,
			now()::date,
			now()::date
		) mbd ON true
GROUP BY
	mbd.Name,
	mbd.UOMSymbol
ORDER BY
	mbd.Name
$$
LANGUAGE sql STABLE
;	
	
