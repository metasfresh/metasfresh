DROP FUNCTION IF EXISTS dlm.delete_oldest_ad_issue_records(integer, integer);

CREATE OR REPLACE FUNCTION dlm.delete_oldest_ad_issue_records(
	p_delete_limit integer,
	p_older_than_days integer)
   RETURNS TABLE(deleted_created_before date, deleted_limit integer, deleted_count integer) AS
$BODY$
DECLARE
	v_created_before date;
	v_deleted_count integer;
BEGIN

	v_created_before = (now() - p_older_than_days);
	DELETE FROM public.AD_Issue 
	WHERE AD_Issue_ID IN (
		select ad_issue_id from ad_issue where updated < v_created_before
		order by ad_issue_id 
		limit p_delete_limit
	);
	GET DIAGNOSTICS v_deleted_count = ROW_COUNT;
	RAISE NOTICE 'Deleted % AD_Issue records that were older than %', v_deleted_count, v_created_before;

	RETURN QUERY select v_created_before, p_delete_limit, v_deleted_count;
END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
COMMENT ON FUNCTION dlm.delete_oldest_ad_issue_records(integer, integer) IS
'Deletes the oldest records from public.AD_Issue. 
The max number of deleted records is p_delete_limit. 
Records to be deleted need have an updated value that is before now() - p_older_than_days.
See https://github.com/metasfresh/metasfresh/issues/2754';

-- select * from dlm.delete_oldest_ad_issue_records(10000, 30)
