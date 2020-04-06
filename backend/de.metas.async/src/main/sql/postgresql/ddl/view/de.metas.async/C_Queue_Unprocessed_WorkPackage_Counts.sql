
CREATE OR REPLACE VIEW "de.metas.async".C_Queue_Unprocessed_WorkPackage_Counts_v AS
SELECT COUNT(*) as count, classname 
FROM "de.metas.async".c_queue_overview_v v 
JOIN c_queue_workpackage qwp ON qwp.c_queue_workpackage_id=v.c_queue_workpackage_id 
	AND qwp.IsActive='Y' 
	AND qwp.IsReadyForProcessing='Y'
	AND qwp.processed='N' 
	AND qwp.iserror='N'
 GROUP BY classname;
COMMENT ON VIEW "de.metas.async".C_Queue_Unprocessed_WorkPackage_Counts_v IS
'Lists the number of unprocessed packages per processor className.
Note that I join c_queue_workpackage just to have additional conditions (like IsReadyForProcessing) that are not in the view';
