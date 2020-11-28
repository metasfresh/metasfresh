

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Details(numeric, numeric, numeric, numeric,  date, date, date);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Details(numeric, numeric, numeric, numeric, character varying, date, date, date);

DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Details;

-- Table: de_metas_endcustomer_fresh_reports.docs_hubalance_report_details

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Details
(
  bpartner character varying,
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


-- Function: de_metas_endcustomer_fresh_reports.docs_hubalance_report_general_details(numeric, numeric, numeric, numeric, character varying, date, date, date)

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Details
(
	m_material_balance_config_id numeric, --$1
	c_bpartner_id numeric,  --$2
	C_BP_Group_ID numeric,  --$3
	M_Product_ID numeric,  --$4
	isGebindeFlatrate character varying,  --$5
	startdate date,  --$6
	enddate date,  --$7
	refdate date  --$8
)
  RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Details AS
$BODY$
SELECT
	bp.name, 
	COALESCE(x.DocumentNo, ''),
	COALESCE(x.PrintName, ''),
	x.MovementDate::date,
	p.Name, --Product
	COALESCE(x.Outgoing, 0),
	COALESCE(x.Incoming, 0),
	COALESCE( carry.CarryOutgoing, 0 ) AS CarryOutgoing,
	COALESCE( carry.CarryIncoming, 0 ) AS CarryIncoming,
	COALESCE(x.UOMSymbol, uom.UOMSymbol)
	
FROM

	(
		SELECT	SUM( QtyIncoming ) AS CarryIncoming, SUM( QtyOutgoing ) AS CarryOutgoing, mbd.M_Product_ID, mbd.C_BPartner_ID
		FROM	M_Material_Balance_Detail mbd
			INNER JOIN M_Material_Balance_Config mbc ON mbd.M_Material_Balance_Config_ID = mbc.M_Material_Balance_Config_ID
		WHERE	MovementDate::date < $6 AND ( mbd.IsReset = 'N' OR ( mbd.IsReset = 'Y' AND mbd.ResetDateEffective > $7 ))
		GROUP BY  mbd.C_BPartner_ID, mbd.M_Product_ID
	) carry
	
	LEFT OUTER JOIN
	( Select 
		mbd. C_BPartner_ID,
		bp.name as bpartner, 
		mbd.DocumentNo,
		dt.PrintName,
		mbd.MovementDate::date,
		p.M_Product_ID,
		p.Name, --Product
		SUM (mbd.QtyOutgoing) AS Outgoing,
		SUM (mbd.QtyIncoming) AS Incoming,
		UOMSymbol
	
	FROM
		M_Material_Balance_Config mbc
		INNER JOIN M_Material_Balance_Detail mbd ON mbc.M_Material_Balance_Config_ID = mbd.M_Material_Balance_Config_ID
		INNER JOIN C_BPartner bp on mbd.C_BPartner_ID = bp.C_BPartner_ID
		INNER JOIN C_UOM uom ON mbd.C_UOM_ID = uom.C_UOM_ID
		INNER JOIN C_DocType dt ON mbd.C_DocType_ID = dt.C_DocType_ID
		INNER JOIN M_Product p ON mbd.M_Product_ID = p.M_Product_ID and case when  $4 >0 then p.M_Product_ID = $4 else 1=1 end
		INNER JOIN M_InOutLine iol ON mbd.M_InOutLine_ID = iol.M_InOutLine_ID
		
	WHERE
		mbc.M_Material_Balance_Config_ID = $1
		AND mbd.isActive = 'Y'
		AND ( mbd.IsReset = 'N' OR ( mbd.IsReset = 'Y' AND mbd.ResetDateEffective > $6 ))
		AND (
			case when $2 > 0 
			then mbd.C_BPartner_ID = $2
			else
			1=1
			end
			)
		AND (
			case when $3 > 0
			then $3 = (Select bp.C_BP_Group_ID from C_BPartner bp where bp.C_BPartner_ID = mbd.C_BPartner_ID)
			else 1=1
			end
			)
		AND COALESCE( mbd.MovementDate::date >= $6, true )
		AND COALESCE( mbd.MovementDate::date <= $7, true )
		AND 
			(
				
				case when $5 = 'Y' then
				( exists
					(select 1 from C_FLatrate_Term ft
					JOIN C_FLatrate_Data fd on ft.C_Flatrate_Data_ID = fd.C_Flatrate_Data_ID
					JOIN C_FLatrate_Conditions fc on ft.C_FLatrate_Conditions_ID = fc.C_FLatrate_Conditions_ID and fc.Type_Conditions = 'Refundable'
					JOIN C_Flatrate_Matching fm on fm.C_FLatrate_Conditions_ID = fc.C_FLatrate_Conditions_ID
					WHERE fd.C_BPartner_ID = mbd.C_BPartner_ID AND
								(fm.M_Product_ID = p.M_Product_ID OR ft.M_Product_ID = p.M_Product_ID OR (fm.M_Product_Category_Matching_ID  = p. M_Product_Category_ID AND fm.M_Product_ID IS NULL))
					AND COALESCE( ft.EndDate >= $6, true )
					AND COALESCE( ft.StartDate <= $7, true )
					)

				)
				when $5 = 'N' then
				(
				not exists
					(select 1 from C_FLatrate_Term ft
					JOIN C_FLatrate_Data fd on ft.C_Flatrate_Data_ID = fd.C_Flatrate_Data_ID
					JOIN C_FLatrate_Conditions fc on ft.C_FLatrate_Conditions_ID = fc.C_FLatrate_Conditions_ID and fc.Type_Conditions = 'Refundable'
					JOIN C_Flatrate_Matching fm on fm.C_FLatrate_Conditions_ID = fc.C_FLatrate_Conditions_ID
					WHERE fd.C_BPartner_ID = mbd.C_BPartner_ID AND
						(fm.M_Product_ID = p.M_Product_ID OR ft.M_Product_ID = p.M_Product_ID OR (fm.M_Product_Category_Matching_ID  = p. M_Product_Category_ID AND fm.M_Product_ID IS NULL))

					AND COALESCE( ft.EndDate >= $6, true )
					AND COALESCE( ft.StartDate <= $7, true )
					)
				)
				else 
				(
					1=1
				)
					
				end
			)
			
GROUP BY
	mbd. C_BPartner_ID,
	bp.name, 
	p.M_Product_ID,
	p.Name,
	mbd.DocumentNo,
	dt.PrintName,
	mbd.MovementDate,
	
	UOMSymbol
		
		
	) x  ON x.C_BPartner_ID = carry.C_BPartner_ID AND x.M_Product_ID = carry.M_Product_ID
	JOIN C_BPartner bp ON carry.C_BPartner_ID = bp.C_Bpartner_ID
	JOIN M_Product p ON carry.M_Product_ID = p.M_Product_ID
	JOIN C_UOM uom on p.C_UOM_ID = uom.C_UOM_ID


Order by 

	bp.name,
	p.name,
	x.movementDate,
	x.documentno,
	x.printname,
	carry.CarryIncoming,
	carry.CarryOutgoing,
	x.UOMSymbol
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

  
  
  
  
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Recap(numeric, numeric, numeric, numeric, character varying, date, date, date);

DROP TABLE   IF EXISTS de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Recap;
CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Recap
(
  
  "name" character varying,

  Outgoing numeric,
  Incoming numeric,
  carryincoming numeric,
  carryoutgoing numeric

)
WITH (
  OIDS=FALSE
);

  
  
 
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Recap(m_material_balance_config_id numeric, c_bpartner_id numeric, C_BP_Group_ID numeric, M_Product_ID numeric, isGebindeFlatrate character varying, startdate date, enddate date, refdate date)
  RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Recap AS
$BODY$

  select x.name, sum(x.outgoing), sum(x.incoming), sum(x.carryincoming), sum(x.carryoutgoing) 
  from

(
(SELECT

	rec.Name, 
	rec.bpartner,

	0::numeric as Outgoing,
	0::numeric as Incoming,
	rec.carryincoming,
	rec.carryoutgoing
FROM
	de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Details($1, $2, $3, $4, $5, $6, $7, $8) rec
	
GROUP BY
	
	rec.Name,rec.bpartner,
	rec.carryincoming,
	rec.carryoutgoing
ORDER BY
	
	rec.Name)



	UNION ALL 
(
SELECT

	rec.Name, 
	rec.bpartner,
	
	sum(rec.Outgoing),
	sum(rec.Incoming),
	0::numeric as carryoutgoing,
	0::numeric as carryincoming

FROM
	de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Details($1, $2, $3, $4, $5, $6, $7, $8) rec
	
GROUP BY
	
	rec.Name,rec.bpartner
	
ORDER BY
	
	rec.Name)) x

	group by x.name

	
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
