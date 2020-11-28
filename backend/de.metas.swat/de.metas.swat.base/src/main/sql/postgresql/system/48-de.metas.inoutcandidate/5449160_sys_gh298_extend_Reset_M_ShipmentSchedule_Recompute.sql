DROP FUNCTION IF EXISTS de_metas_inoutcandidate.reset_m_shipmentschedule_recompute();
CREATE OR REPLACE FUNCTION de_metas_inoutcandidate.Reset_M_ShipmentSchedule_Recompute(p_AD_PInstance_ID_max integer DEFAULT 0)
  RETURNS integer AS
$BODY$
DECLARE
	v_CountDeleted_Stale integer;
	v_CountDeleted_MissingSched integer;
BEGIN

	DELETE FROM M_ShipmentSchedule_Recompute r WHERE NOT EXISTS (select 1 from M_ShipmentSchedule s where s.M_ShipmentSchedule_ID=r.M_ShipmentSchedule_ID);
	GET DIAGNOSTICS v_CountDeleted_MissingSched = ROW_COUNT;
	RAISE NOTICE 'Deleted % M_ShipmentSchedule_Recompute records whose M_ShipmentSchedule_IDs don''t exist anymore.', v_CountDeleted_MissingSched;

	INSERT INTO M_ShipmentSchedule_Recompute (M_ShipmentSchedule_ID)
	/* Note: if a record has no AD_PInstance_ID, it won't be hit, because p_AD_PInstance_ID_max+1 is greater than p_AD_PInstance_ID_max */
	SELECT M_ShipmentSchedule_ID FROM M_ShipmentSchedule_Recompute WHERE COALESCE(AD_PInstance_ID,p_AD_PInstance_ID_max+1)<=p_AD_PInstance_ID_max;
	GET DIAGNOSTICS v_CountDeleted_Stale = ROW_COUNT;
	
	DELETE FROM M_ShipmentSchedule_Recompute WHERE COALESCE(AD_PInstance_ID,p_AD_PInstance_ID_max+1)<=p_AD_PInstance_ID_max;
	RAISE NOTICE 'Re-inserted % M_ShipmentSchedule_IDs that had an AD_PInstance_ID (param p_AD_PInstance_ID_max=%).', v_CountDeleted_Stale, p_AD_PInstance_ID_max;
	
	RETURN v_CountDeleted_Stale + v_CountDeleted_MissingSched; 

END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION de_metas_inoutcandidate.Reset_M_ShipmentSchedule_Recompute(integer) IS 
'FRESH-342: this function can be called by Reset_M_ShipmentSchedule_Recompute (housekeeping task).
M_ShipmentSchedule_Recomputes which have an AD_Pinstance_ID at startup time are stale and won''t be processed.
This function is implemented in plpgsql because i didn''t know how else to return the number of deleted M_ShipmentSchedule_Recompute records;

Additions: #289 see https://github.com/metasfresh/metasfresh/issues/298
* The optional parameter p_AD_PInstance_ID_max can be used to manually call the function during production. By giving a max value, (choose the ID for one stale record), nore recent records won''t be harmed.
* We now also remove M_ShipmentSchedule_Recompute records whose M_ShipmentSchedule rcords don''t exist anymore.
* adding some output (NOTICE level).'
;
