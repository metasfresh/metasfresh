

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_Details(numeric, numeric, date, date, date);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_Details(numeric, numeric, date, date, date, IN AD_Language Character Varying (6));

DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_Details;

-- Table: de_metas_endcustomer_fresh_reports.docs_hubalance_report_details

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_Details
(
  documentno character varying,
  printname character varying,
  movementdate date,
  "name" character varying,
  outgoing numeric,
  incoming numeric,
  carryoutgoing numeric,
  carryincoming numeric,
  uomsymbol character varying
)
WITH (
  OIDS=FALSE
);


-- Function: de_metas_endcustomer_fresh_reports.docs_hubalance_report_details(numeric, numeric, date, date, date, character varying (6))

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_Details(m_material_balance_config_id numeric, c_bpartner_id numeric, startdate date, enddate date, refdate date, AD_Language Character Varying (6) = null)
  RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_Details AS
$BODY$
SELECT
	mbd.DocumentNo,
	COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
	mbd.MovementDate::date,
	COALESCE(pt.Name, p.Name) AS Name, --Product
	SUM (mbd.QtyOutgoing) AS Outgoing,
	SUM (mbd.QtyIncoming) AS Incoming,
	COALESCE( CarryOutgoing, 0 ) AS CarryOutgoing,
	COALESCE( CarryIncoming, 0 ) AS CarryIncoming,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol
FROM
	M_Material_Balance_Config mbc
	INNER JOIN M_Material_Balance_Detail mbd ON mbc.M_Material_Balance_Config_ID = mbd.M_Material_Balance_Config_ID
	INNER JOIN C_UOM uom ON mbd.C_UOM_ID = uom.C_UOM_ID
	LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $6
	INNER JOIN C_DocType dt ON mbd.C_DocType_ID = dt.C_DocType_ID
	LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $6
	INNER JOIN M_Product p ON mbd.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $6
	INNER JOIN M_InOutLine iol ON mbd.M_InOutLine_ID = iol.M_InOutLine_ID
	LEFT OUTER JOIN (
		SELECT	SUM( QtyIncoming ) AS CarryIncoming, SUM( QtyOutgoing ) AS CarryOutgoing, mbd.M_Product_ID, mbd.C_BPartner_ID
		FROM	M_Material_Balance_Detail mbd
			INNER JOIN M_Material_Balance_Config mbc ON mbd.M_Material_Balance_Config_ID = mbc.M_Material_Balance_Config_ID
		WHERE	MovementDate::date < $3 AND ( mbd.IsReset = 'N' OR ( mbd.IsReset = 'Y' AND mbd.ResetDateEffective > $5 ))
		GROUP BY mbd.M_Product_ID, mbd.C_BPartner_ID
	) carry ON mbd.C_BPartner_ID = carry.C_BPartner_ID AND mbd.M_Product_ID = carry.M_Product_ID
WHERE
	mbc.M_Material_Balance_Config_ID = $1
	AND mbd.isActive = 'Y'
	AND ( mbd.IsReset = 'N' OR ( mbd.IsReset = 'Y' AND mbd.ResetDateEffective > $5 ))
	AND mbd.C_BPartner_ID = $2
	AND COALESCE( mbd.MovementDate::date >= $3, true )
	AND COALESCE( mbd.MovementDate::date <= $4, true )
GROUP BY
	mbd.DocumentNo,
	COALESCE(dtt.PrintName, dt.PrintName),
	mbd.MovementDate,
	COALESCE(pt.Name, p.Name),
	CarryIncoming,
	CarryOutgoing,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol)
ORDER BY
	Name, mbd.MovementDate, mbd.DocumentNo
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
