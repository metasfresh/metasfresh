
CREATE OR REPLACE FUNCTION de_metas_inoutcandidate.Reset_M_ShipmentSchedule_Recompute()
  RETURNS integer AS
$BODY$
DECLARE
	v_CountDeleted integer;
BEGIN

	INSERT INTO M_ShipmentSchedule_Recompute (M_ShipmentSchedule_ID)
	SELECT M_ShipmentSchedule_ID FROM M_ShipmentSchedule_Recompute WHERE COALESCE(AD_PInstance_ID,0)!=0;

	DELETE FROM M_ShipmentSchedule_Recompute WHERE COALESCE(AD_PInstance_ID,0)!=0;

	GET DIAGNOSTICS v_CountDeleted = ROW_COUNT;

	RETURN v_CountDeleted; 

END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION de_metas_inoutcandidate.Reset_M_ShipmentSchedule_Recompute() IS 
'FRESH-342: this function can be called by Reset_M_ShipmentSchedule_Recompute (housekeeping task).
M_ShipmentSchedule_Recomputes which have an AD_Pinstance_ID at startup time are stale and won''t be processed.
This function is implemented in plpgsql because i didn''t know how else to return the number of deleted M_ShipmentSchedule_Recompute records'
;
