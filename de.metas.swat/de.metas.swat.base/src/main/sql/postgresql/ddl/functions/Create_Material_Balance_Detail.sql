
DROP FUNCTION IF EXISTS  "de.metas.inout".create_material_balance_detail (p_M_Material_Balance_Config_ID numeric, p_AD_User_ID numeric);

CREATE OR REPLACE FUNCTION "de.metas.inout".create_material_balance_detail (p_M_Material_Balance_Config_ID numeric, p_AD_User_ID numeric) RETURNS text AS 
$BODY$
DECLARE
	v_detailsFound bigint;
	v_configFound boolean;
	v_Balance_Record M_Material_Balance_Detail%ROWTYPE;
	v_count int := 0;
BEGIN
	-- Check if the Paramter is valid
	SELECT EXISTS ( SELECT 0 FROM M_Material_Balance_Config mbd WHERE mbd.M_Material_Balance_Config_ID = $1 )
	INTO v_configFound;
	
	IF ( NOT v_configFound ) 
	THEN 
		RETURN 'Invalid config';
	ELSE
		RAISE NOTICE 'config found, ';
	END IF;
	
	-- Check if there already are details to prevent doubles
	-- It would be possible to exclude already existing detail lines from the insert, but that would slow down the query too much
	SELECT count(0) FROM M_Material_Balance_Detail mbd WHERE mbd.M_Material_Balance_Config_ID = p_M_Material_Balance_Config_ID
	INTO v_detailsFound;	
	RAISE NOTICE '% detail lines found, ', v_detailsFound;
	
	
	-- Using the original Material Balance query and the filled variables:
	-- 1. Find all relevant lines
	-- 2. Migrate them into M_Material_Balance_Detail
	RAISE NOTICE E'Creating Lines\n';
	INSERT INTO M_Material_Balance_Detail
		SELECT
			iol.AD_Client_ID, iol.AD_Org_ID, 
			bp.C_BPartner_ID, 
			io.C_DocType_ID, 
			pe.C_Period_ID, now(), p_AD_User_ID, 
			iol.C_UOM_ID,
			io.documentno,
			'Y',
			'N',
			io.M_InOut_ID,
			iol.M_InOutLine_ID,
			p_M_Material_Balance_Config_ID, nextval('m_material_balance_detail_seq'),
			io.MovementDate,
			p.M_Product_ID,
			CASE
				WHEN io.MovementType IN ('C+', 'V+') THEN iol.movementqty
				ELSE 0
			END,
			CASE
				WHEN io.MovementType IN ('C-', 'V-') THEN iol.movementqty
				ELSE 0
			END,
			now(), now(), p_AD_User_ID, term.isForFlatrate
			
		FROM
			C_BPartner bp

			-- Get Filters
			INNER JOIN M_Material_Balance_Config mbc ON mbc.M_Material_Balance_Config_ID = p_M_Material_Balance_Config_ID

			-- Get infos to filter for
			INNER JOIN M_InOut io ON bp.C_BPartner_ID = io.C_BPartner_ID AND io.IsActive = 'Y' AND io.DocStatus IN ('CO','CL')
			INNER JOIN M_InOutLine iol ON iol.M_InOut_ID = io.M_InOut_ID AND iol.IsActive = 'Y'
			INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
			INNER JOIN C_Period pe ON io.MovementDate >= pe.StartDate AND io.MovementDate <= pe.EndDate AND pe.C_Year_ID IN (SELECT y.C_Year_ID FROM C_Year y WHERE y.C_Calendar_ID = mbc.C_Calendar_ID)
			-- Get isForFlatrate Flag
			INNER JOIN (
				SELECT 	iol.M_InOutLine_ID, COALESCE ( isFlatrateFound, 'N')::Character AS IsForFlatrate
					FROM 
						(
						SELECT	iol.M_InOutLine_ID, io.MovementDate, p.M_Product_Category_ID, p.M_Product_ID, 
							COALESCE( o.Bill_BPartner_ID, io.C_BPartner_ID) AS Bill_BPartner_ID
						FROM	M_InOutLine iol
							INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
							INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID
							-- Get Bill BPartner
							LEFT OUTER JOIN M_ShipmentSchedule_QtyPicked ssqp ON iol.M_InOutLine_ID = ssqp.M_InOutLine_ID
							LEFT OUTER JOIN M_ShipmentSchedule ss ON ssqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID AND ss.C_OrderLine_ID IS NOT NULL
							LEFT OUTER JOIN C_Orderline ol ON ss.C_OrderLine_ID = ol.C_OrderLine_ID
							LEFT OUTER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
						) iol
						LEFT OUTER JOIN (
							SELECT 	t.Bill_BPartner_ID, t.StartDate, t.EndDate, m.M_Product_Category_Matching_ID, m.M_Product_ID, 'Y'::Character AS isFlatrateFound
							FROM 	C_Flatrate_Term t 
								INNER JOIN C_Flatrate_Matching m ON t.C_Flatrate_Conditions_ID = m.C_Flatrate_Conditions_ID
						) frt ON frt.Bill_BPartner_ID = iol.Bill_BPartner_ID 
							AND iol.MovementDate >= frt.StartDate AND iol.MovementDate <= frt.EndDate
							AND COALESCE ( frt.M_Product_Category_Matching_ID = iol.M_Product_Category_ID, true )
							AND COALESCE ( frt.M_Product_ID = iol.M_Product_ID, true )
			) term ON iol.M_InOutLine_ID = term.M_InOutLine_ID
			LEFT OUTER JOIN M_Material_Balance_Detail mbd ON mbc.M_Material_Balance_Config_ID = mbd.M_Material_Balance_Config_ID AND iol.M_InOutLine_ID = mbd.M_InOutLine_ID

		WHERE	
			-- Prevent doubles. Only create a line if there isn't already one with the same config and inoutline 
			mbd.M_Material_Balance_Detail_ID IS NULL
			
			-- Apply filters of the M_Material_Balance_Config
			-- None of the fields is mandatory. Filters that are set to NULL are considered as disabled
			AND COALESCE ( mbc.C_BPartner_ID = bp.C_BPartner_ID, true )
			AND COALESCE ( mbc.C_BP_Group_ID = bp.C_BP_Group_ID, true )
			AND COALESCE ( mbc.M_Warehouse_ID = io.M_Warehouse_ID, true ) 
			AND COALESCE ( mbc.M_Product_ID = p.M_Product_ID, true )
			AND COALESCE ( mbc.M_Product_Category_ID = pc.M_Product_Category_ID, true ) 
			-- Filter for customer flag if set ( The field in the config can be 'Y', 'N' or NULL )
			AND COALESCE ( mbc.isCustomer = bp.isCustomer, true )
			-- Filter for vendor flag if set ( The field in the config can be 'Y', 'N' or NULL )
			AND COALESCE ( mbc.isVendor = bp.isVendor, true )
			-- Find out if the InOutLine belongs to a flatrate
			AND COALESCE ( mbc.isForFlatrate = term.isForFlatrate, true )
		;
	
	GET DIAGNOSTICS v_count = ROW_COUNT;	
	RETURN v_count || ' Lines Added';
END;
$BODY$ 
	LANGUAGE plpgsql 
	VOLATILE
	COST 100;

ALTER FUNCTION "de.metas.inout".create_material_balance_detail (p_M_Material_Balance_Config_ID numeric, p_AD_User_ID numeric ) OWNER TO adempiere;
