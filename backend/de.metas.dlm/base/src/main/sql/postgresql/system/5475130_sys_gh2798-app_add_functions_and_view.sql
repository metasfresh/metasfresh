
DROP FUNCTION IF EXISTS dlm.delete_c_queue_workpackage_ad_changelog_records(integer);

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

------------------
DROP FUNCTION IF EXISTS dlm.delete_md_candidate_ad_changelog_records(integer);
CREATE OR REPLACE FUNCTION dlm.delete_md_candidate_ad_changelog_records(IN p_delete_limit integer)
  RETURNS TABLE(deleted_limit integer, deleted_count integer) AS
$BODY$
DECLARE
	v_deleted_count integer;
BEGIN
	DELETE FROM public.AD_ChangeLog
	WHERE AD_ChangeLog_ID IN (
		select l.AD_ChangeLog_ID 
		from AD_ChangeLog l 
		where l.AD_Table_ID=540808 -- "MD_Candidate"
		limit p_delete_limit
	);

	GET DIAGNOSTICS v_deleted_count = ROW_COUNT;
	RAISE NOTICE 'Deleted % AD_ChangeLog_ID records that referenced the MD_Candidate table', v_deleted_count;

	RETURN QUERY select p_delete_limit, v_deleted_count;
END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
COMMENT ON FUNCTION dlm.delete_md_candidate_ad_changelog_records(integer) IS 'This function deletes the <p_delete_limit> oldest (by the Updated value) AD_ChangeLog records that reference MD_Candidate columns';
-------------------
DROP VIEW IF EXISTS dlm.AD_ChangeLog_counts_v;

CREATE OR REPLACE VIEW dlm.AD_ChangeLog_counts_v as
SELECT t.TableName, c.ColumnName, count(l.*) as count,
-- bring your own 
-- DELETE FROM public.AD_ChangeLog WHERE false
-- to use these clauses all in one SQL
	' OR (AD_Column_ID='||l.AD_Column_ID||' AND AD_Table_ID='||l.AD_Table_ID||')' as delete_where_clause,
	'UPDATE AD_Column SET IsAllowLogging=''N'' WHERE AD_Column_ID='||l.AD_Column_ID||';' as update_column_sql
FROM public.AD_ChangeLog l
	JOIN public.AD_Table t ON l.AD_Table_ID=t.AD_Table_ID
	JOIN public.AD_Column c ON c.AD_Column_ID=l.AD_Column_ID
GROUP BY l.AD_Table_ID, l.AD_Column_ID, t.TableName, c.ColumnName
;
COMMENT ON view dlm.AD_ChangeLog_counts_v IS 'Selects the number of AD_Changelog records grouped by table and column.
This view might take a *long time* to complete.

The delete_where_clause and update_column_sql columns are supposed to provide usefull snippets to clean up where needed.

Suggested use:

create table backup.AD_ChangeLog_counts_<current-date> AS
select * from AD_ChangeLog_counts_v;
';