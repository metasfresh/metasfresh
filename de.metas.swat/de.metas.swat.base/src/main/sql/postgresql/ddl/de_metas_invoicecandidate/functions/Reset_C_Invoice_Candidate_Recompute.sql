--DROP FUNCTION IF EXISTS de_metas_invoicecandidate.reset_C_Invoice_Candidate_recompute(bigint);
CREATE OR REPLACE FUNCTION de_metas_invoicecandidate.Reset_C_Invoice_Candidate_Recompute(p_AD_PInstance_ID_max bigint DEFAULT 9223372036854775807)
  RETURNS integer AS
$BODY$
DECLARE
	v_CountDeleted_Stale integer;
	v_CountDeleted_MissingSched integer;
BEGIN

	DELETE FROM C_Invoice_Candidate_Recompute r WHERE NOT EXISTS (select 1 from C_Invoice_Candidate s where s.C_Invoice_Candidate_ID=r.C_Invoice_Candidate_ID);
	GET DIAGNOSTICS v_CountDeleted_MissingSched = ROW_COUNT;
	RAISE NOTICE 'Deleted % C_Invoice_Candidate_Recompute records whose C_Invoice_Candidate_IDs don''t exist anymore.', v_CountDeleted_MissingSched;

	INSERT INTO C_Invoice_Candidate_Recompute (C_Invoice_Candidate_ID)

	SELECT C_Invoice_Candidate_ID FROM C_Invoice_Candidate_Recompute WHERE COALESCE(AD_PInstance_ID,p_AD_PInstance_ID_max)<p_AD_PInstance_ID_max;
	GET DIAGNOSTICS v_CountDeleted_Stale = ROW_COUNT;
	
	DELETE FROM C_Invoice_Candidate_Recompute WHERE COALESCE(AD_PInstance_ID,p_AD_PInstance_ID_max)<p_AD_PInstance_ID_max;
	RAISE NOTICE 'Re-inserted % C_Invoice_Candidate_IDs that had an AD_PInstance_ID (param p_AD_PInstance_ID_max=%).', v_CountDeleted_Stale, p_AD_PInstance_ID_max;
	
	RETURN v_CountDeleted_Stale + v_CountDeleted_MissingSched; 

END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
;
COMMENT ON FUNCTION de_metas_invoicecandidate.Reset_C_Invoice_Candidate_Recompute(bigint) IS 
'#251, see https://github.com/metasfresh/metasfresh/issues/251

Helper function to clean up stale C_Invoice_Candidate_Recompute records; 
* This function is similar to de_metas_inoutcandidate.Reset_M_ShipmentSchedule_Recompute(bigint)
* The optional parameter p_AD_PInstance_ID_max can be used to manually call the function during production. By giving a max value, (choose an AD_Pinstance_ID you know from one stale record), more recent records won''t be harmed.
* We also remove M_ShipmentSchedule_Recompute records whose M_ShipmentSchedule rcords don''t exist anymore.
* giving some output (NOTICE level).'
;
--select de_metas_invoicecandidate.reset_C_Invoice_Candidate_recompute()