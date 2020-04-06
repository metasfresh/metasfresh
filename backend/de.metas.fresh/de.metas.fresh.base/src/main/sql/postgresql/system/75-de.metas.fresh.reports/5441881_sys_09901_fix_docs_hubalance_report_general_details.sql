
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Details(m_material_balance_config_id numeric, c_bpartner_id numeric, C_BP_Group_ID numeric, M_Product_ID numeric, isGebindeFlatrate character varying, startdate date, enddate date, refdate date)
  RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_HUBalance_Report_General_Details AS
$BODY$
SELECT
	bp.name as bpartner, 
	mbd.DocumentNo,
	dt.PrintName,
	mbd.MovementDate::date,
	p.Name, --Product
	SUM (mbd.QtyOutgoing) AS Outgoing,
	SUM (mbd.QtyIncoming) AS Incoming,
	COALESCE( CarryOutgoing, 0 ) AS CarryOutgoing,
	COALESCE( CarryIncoming, 0 ) AS CarryIncoming,
	UOMSymbol
FROM
	M_Material_Balance_Config mbc
	INNER JOIN M_Material_Balance_Detail mbd ON mbc.M_Material_Balance_Config_ID = mbd.M_Material_Balance_Config_ID
	INNER JOIN C_BPartner bp on mbd.C_BPartner_ID = bp.C_BPartner_ID
	INNER JOIN C_UOM uom ON mbd.C_UOM_ID = uom.C_UOM_ID
	INNER JOIN C_DocType dt ON mbd.C_DocType_ID = dt.C_DocType_ID
	INNER JOIN M_Product p ON mbd.M_Product_ID = p.M_Product_ID and case when  $4 >0 then p.M_Product_ID = $4 else 1=1 end
	INNER JOIN M_InOutLine iol ON mbd.M_InOutLine_ID = iol.M_InOutLine_ID
	LEFT OUTER JOIN (
		SELECT	SUM( QtyIncoming ) AS CarryIncoming, SUM( QtyOutgoing ) AS CarryOutgoing, mbd.M_Product_ID, mbd.C_BPartner_ID
		FROM	M_Material_Balance_Detail mbd
			INNER JOIN M_Material_Balance_Config mbc ON mbd.M_Material_Balance_Config_ID = mbc.M_Material_Balance_Config_ID
		WHERE	MovementDate < $6 AND ( mbd.IsReset = 'N' OR ( mbd.IsReset = 'Y' AND mbd.ResetDateEffective > $7 ))
		GROUP BY  mbd.C_BPartner_ID, mbd.M_Product_ID
	) carry ON mbd.C_BPartner_ID = carry.C_BPartner_ID AND mbd.M_Product_ID = carry.M_Product_ID
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
	AND COALESCE( mbd.MovementDate >= $6, true )
	AND COALESCE( mbd.MovementDate <= $7, true )
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
	bp.name, 
	p.Name,
	mbd.DocumentNo,
	dt.PrintName,
	mbd.MovementDate,
	CarryIncoming,
	CarryOutgoing,
	UOMSymbol
ORDER BY
	bp.name, 
	p.Name,
	
	dt.PrintName,
	mbd.DocumentNo,
	mbd.MovementDate,
	CarryIncoming,
	CarryOutgoing,
	UOMSymbol
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;