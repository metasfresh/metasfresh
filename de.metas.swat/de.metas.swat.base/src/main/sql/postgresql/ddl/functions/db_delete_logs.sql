-- DROP FUNCTION db_delete_logs(integer);
CREATE FUNCTION db_delete_logs(ignore_AD_Session_ID integer, ignore_AD_PInstance_ID integer)
RETURNS Integer AS
$$
	BEGIN
		DECLARE
			affected_rows Integer DEFAULT 0;
			total_affected_rows Integer DEFAULT 0;
		BEGIN
			DELETE FROM AD_ChangeLog where AD_Client_ID<>0 and AD_Session_ID <> ignore_AD_Session_ID; -- don't delete current session
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from AD_ChangeLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM AD_Session WHERE AD_Session_ID <> ignore_AD_Session_ID; -- don't delete current session
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from AD_Session has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM AD_WF_Activity ;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from AD_WF_Activity has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM ad_wf_eventaudit;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from ad_wf_eventaudit has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM AD_WF_Process ;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from AD_WF_Process has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM ad_pinstance_log where AD_PInstance_ID <> ignore_AD_PInstance_ID;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from ad_pinstance_log has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM ad_pinstance_para where AD_PInstance_ID <> ignore_AD_PInstance_ID; 
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETEfrom ad_pinstance_para has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM ad_pinstance where AD_PInstance_ID <> ignore_AD_PInstance_ID;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from ad_pinstance has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM AD_Replication_Log ;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from AD_Replication_Log has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM R_RequestProcessorLog ;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from R_RequestProcessorLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM AD_SchedulerLog ;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from AD_SchedulerLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM C_AcctProcessorLog;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from C_AcctProcessorLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;		

			DELETE FROM AD_WorkflowProcessorLog;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from AD_WorkflowProcessorLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM AD_AlertProcessorLog;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from AD_AlertProcessorLog has affected (%) records', affected_rows;	
			total_affected_rows = total_affected_rows + affected_rows;	
			
			DELETE FROM AD_AccessLog;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from AD_AccessLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM CM_WebAccessLog;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from CM_WebAccessLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM K_IndexLog;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from K_IndexLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM AD_LdapProcessorLog;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from AD_LdapProcessorLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM IMP_ProcessorLog;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from IMP_ProcessorLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			DELETE FROM C_Datev_ExportLog;
			GET DIAGNOSTICS affected_rows = ROW_COUNT;
			RAISE NOTICE 'This DELETE from C_Datev_ExportLog has affected (%) records', affected_rows;
			total_affected_rows = total_affected_rows + affected_rows;
			
			RETURN total_affected_rows;
		END;
	END;
$$ LANGUAGE plpgsql;
