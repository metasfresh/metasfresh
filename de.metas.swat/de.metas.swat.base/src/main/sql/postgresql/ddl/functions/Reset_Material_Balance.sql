
DROP FUNCTION IF EXISTS "de.metas.inout".Reset_Material_Balance ( IN p_AD_PInstance_ID numeric );

CREATE FUNCTION "de.metas.inout".Reset_Material_Balance (IN p_AD_PInstance_ID numeric) RETURNS void AS
$BODY$	
DECLARE
	v_ResetDate date;
	v_M_Material_Balance_Config_ID numeric;
BEGIN
	SELECT	p_date
	INTO	v_ResetDate
	FROM	AD_PInstance_Para
	WHERE	AD_PInstance_ID = p_AD_PInstance_ID
		AND ParameterName = 'ResetDate'
	;
	
	SELECT	p_number
	INTO	v_M_Material_Balance_Config_ID
	FROM	AD_PInstance_Para
	WHERE	AD_PInstance_ID = p_AD_PInstance_ID
		AND ParameterName = 'M_Material_Balance_Config_ID'
	;
	-- Tag all records that are not tagged and older than the reset date as reset
	UPDATE	M_Material_Balance_Detail
	SET	isReset = 'Y', ResetDate = v_ResetDate
	WHERE	movementdate <= v_ResetDate AND isReset = 'N' 
		AND M_Material_Balance_Config_ID = v_M_Material_Balance_Config_ID;

	-- Those Records that are reset but within the reset buffer, additionaly get the ResetEffectiveDate set
	UPDATE	M_Material_Balance_Detail a
	SET	ResetDateEffective = b.ResetDateEffective
	FROM	(
			SELECT	
				M_Material_Balance_Detail_ID,
				report.add_period_to_date( 
					mbc.C_Calendar_ID, mbd.MovementDate::date, mbc.resetbufferinterval::int 
				) AS ResetDateEffective 
			FROM	
				M_Material_Balance_Detail mbd
				INNER JOIN M_Material_Balance_Config mbc ON mbd.M_Material_Balance_Config_ID = mbc.M_Material_Balance_Config_ID
			WHERE	
				mbd.IsReset = 'Y' AND mbd.M_Material_Balance_Config_ID = v_M_Material_Balance_Config_ID
				AND mbd.MovementDate >= report.subtract_period_from_date( mbc.C_Calendar_ID, v_ResetDate, mbc.resetbufferinterval::int )
		) b
	WHERE 	a.M_Material_Balance_Detail_ID = b.M_Material_Balance_Detail_ID;
	
	-- Update process result
	UPDATE AD_PInstance set Result=1 where AD_PInstance_ID=p_AD_PInstance_ID;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

ALTER FUNCTION "de.metas.inout".Reset_Material_Balance ( IN p_AD_PInstance_ID numeric ) OWNER TO adempiere;
