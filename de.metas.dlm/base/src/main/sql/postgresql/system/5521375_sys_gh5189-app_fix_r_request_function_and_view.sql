
DROP VIEW IF EXISTS dlm.R_Request_Archivable_v;
CREATE VIEW dlm.R_Request_Archivable_v
AS
select 
	GREATEST(r.Updated, COALESCE(r_related.Updated, r.Updated)) AS ArchivableSince,
	r.R_Request_ID,
	r.Updated AS r_Updated, 
	rt.name, 
	rt.isuseforpartnerrequestwindow, 
	rs.IsClosed,  
	r.created AS r_created, 

	r_related.R_Request_ID AS r_related_R_Request_ID,
	r_related.created AS r_related_created, 
	r_related.Updated AS r_related_Updated,
	o.C_Order_ID
from r_request r
	join r_requesttype rt on rt.r_requesttype_id=r.r_requesttype_id
	join r_status rs on rs.r_status_id = r.r_status_id 
	join C_Order o on o.C_Order_ID=r.C_Order_ID
	LEFT join r_request r_related ON r_related.r_requestrelated_id = r.r_request_id
where rt.name='eMail'
	AND o.QtyOrdered = o.QtyInvoiced AND o.QtyOrdered = o.QtyInvoiced
;
COMMENT ON VIEW dlm.R_Request_Archivable_v IS
'This view is used by the DB-function dlm.archive_r_request_data, to identify the archivable R_Request records.
See issue https://github.com/metasfresh/metasfresh/issues/5189';

/*
create table dlm.R_RequestUpdate_Archived as select * from r_requestupdate limit 0;
create table dlm.R_RequestAction_Archived as select * from R_RequestAction limit 0;
create table dlm.R_Request_Archived as select * from r_request limit 0;
 */

CREATE OR REPLACE FUNCTION dlm.archive_r_request_data(p_daysback integer)
    RETURNS TABLE(
		Count_R_Requests_ToArchive integer,
		Count_R_Request_Archived integer, 
		remaining_toarchive integer) 
    LANGUAGE 'plpgsql'
AS $BODY$
DECLARE
	v_Count_R_Requests_ToArchive int;
	v_Count_R_Request_Archived_Inserted int;
	v_Remaining_R_Request_ToArchive_All int;
	v_R_Request_ToArchive_All_Inserted_Count int;
BEGIN
	drop table if exists dlm.R_Request_ToArchive_All;

	create table dlm.R_Request_ToArchive_All as
	select R_Request_ID
	from dlm.R_Request_Archivable_v r
	where r.ArchivableSince <= now() - (p_daysback || ' DAYS')::INTERVAL -- older than p_daysback days
	UNION DISTINCT
	select r_related_R_Request_ID
	from dlm.R_Request_Archivable_v r
	where r.r_related_R_Request_ID IS NOT NULL AND r.ArchivableSince <= now() - (p_daysback || ' DAYS')::INTERVAL;

	GET DIAGNOSTICS v_R_Request_ToArchive_All_Inserted_Count = ROW_COUNT;
		
	-- Index it
	create index on dlm.R_Request_ToArchive_All(R_Request_ID);
	
	RAISE NOTICE 'Inserted % records into dlm.R_Request_ToArchive_All.', v_R_Request_ToArchive_All_Inserted_Count;

	select count(1) from dlm.R_Request_ToArchive_All INTO v_Count_R_Requests_ToArchive;
	RAISE NOTICE 'Requests to archive: %', v_Count_R_Requests_ToArchive;
	
	-- Archive R_RequestUpdates
	with deleted_rows as (
		delete from R_RequestUpdate t
		where true
			and exists (select 1 from dlm.R_Request_ToArchive_All s where s.R_Request_ID=t.R_Request_ID)
		returning *
	)
	insert into dlm.R_RequestUpdate_Archived
	select * from deleted_rows;
	
	-- Archive R_RequestAction
	with deleted_rows as (
		delete from R_RequestAction t
		where true
			and exists (select 1 from dlm.R_Request_ToArchive_All s where s.R_Request_ID=t.R_Request_ID)
		returning *
	)
	insert into dlm.R_RequestAction_Archived
	select * from deleted_rows;

	-- Archive R_Requests
	with deleted_rows as (
		delete from R_Request t
		where true
			and exists (select 1 from dlm.R_Request_ToArchive_All s where s.R_Request_ID=t.R_Request_ID)
		returning *
	)
	insert into dlm.R_Request_Archived
	select * from deleted_rows;

	GET DIAGNOSTICS v_Count_R_Request_Archived_Inserted = ROW_COUNT;
	RAISE NOTICE 'Moved % records to table dlm.R_Request_Archived', v_Count_R_Request_Archived_Inserted;

	-- Count remaining things to delete
	select count(1) from dlm.R_Request_ToArchive_All INTO v_Remaining_R_Request_ToArchive_All;   
	RAISE NOTICE 'Remaining records in dlm.R_Request_ToArchive_All: % ', v_Remaining_R_Request_ToArchive_All;

	RETURN QUERY select 
		v_Count_R_Requests_ToArchive,
		v_Count_R_Request_Archived_Inserted,
		v_Remaining_R_Request_ToArchive_All;
END;
$BODY$;

COMMENT ON FUNCTION dlm.archive_r_request_data(integer)
    IS 'Moves old R_Request data to an "archive"-table in the dlm schema;
Parameters:
	p_daysback: work packages older than the given number of days are moved
	
Tip: to see more about what the fucntion does, do
	BEGIN; SELECT * FROM dlm.archive_r_request_data(p_daysback := 3); ROLLBACK;
and follow the function''s output (notice level)
see https://github.com/metasfresh/metasfresh/issues/5189';
