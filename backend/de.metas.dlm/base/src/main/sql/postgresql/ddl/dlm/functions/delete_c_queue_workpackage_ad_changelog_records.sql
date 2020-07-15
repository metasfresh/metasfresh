
-- DROP FUNCTION dlm.delete_c_queue_workpackage_ad_changelog_records(integer);

CREATE OR REPLACE FUNCTION dlm.delete_c_queue_workpackage_ad_changelog_records(IN p_delete_limit integer)
  RETURNS TABLE(deleted_limit integer, deleted_count integer) AS
$BODY$
DECLARE
	v_deleted_count integer;
BEGIN
	DELETE FROM public.AD_ChangeLog
	WHERE AD_ChangeLog_ID IN (
		select l.AD_ChangeLog_ID 
		from AD_ChangeLog l 
		where l.AD_Table_ID=540425 -- C_Queue_WorkPackage
		--ordering forces the DB to iterate the PK index and check for each record whether it is a hit, 
		-- whereas we *actually* want to get rid of all records referening that table
		--order by AD_ChangeLog_ID 
		limit p_delete_limit
	);

	GET DIAGNOSTICS v_deleted_count = ROW_COUNT;
	RAISE NOTICE 'Deleted % AD_ChangeLog_ID records that referenced the C_Queue_WorkPackage table', v_deleted_count;

	RETURN QUERY select p_delete_limit, v_deleted_count;
END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

COMMENT ON FUNCTION dlm.delete_c_queue_workpackage_ad_changelog_records(integer) IS 'This function deletes the <p_delete_limit> oldest (by the Updated value) AD_ChangeLog records that reference C_Queue_WorkPackage columns';
