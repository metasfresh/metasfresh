create table dlm.R_RequestUpdate_Archived as select * from r_requestupdate limit 0;
create table dlm.R_Request_Archived as select * from r_request limit 0;

create index if not exists r_request_updated on r_request(updated);
create index if not exists r_requestupdate_r_request_id on r_request(r_request_id);


CREATE OR REPLACE VIEW dlm.R_Request_Archivable_v
AS
select 
	r.R_Request_ID, 
	r.Updated AS ArchivableSince,
	rt.name, 
	rt.isuseforpartnerrequestwindow, 
	rs.IsClosed,  
	r.created, 
	r.updated,
	o.C_Order_ID
from r_request r
	join r_requesttype rt on rt.r_requesttype_id=r.r_requesttype_id
	join r_status rs on rs.r_status_id = r.r_status_id 
	join C_Order o on o.C_Order_ID=r.C_Order_ID
where rt.name='eMail'
	AND o.QtyOrdered = o.QtyInvoiced AND o.QtyOrdered = o.QtyInvoiced
;
COMMENT ON VIEW dlm.R_Request_Archivable_v IS
'This view is used by the DB-function dlm.archive_r_request_data, to identify the archivable R_Request records.
See issue https://github.com/metasfresh/metasfresh/issues/5189';


CREATE OR REPLACE FUNCTION dlm.archive_r_request_data(
	p_daysback integer,
	p_limitrecords integer)
    RETURNS TABLE(
		Count_R_Requests_ToArchive integer, 
		Count_R_Request_Archived integer, 
		remaining_toarchive integer) 
    LANGUAGE 'plpgsql'
AS $BODY$
DECLARE
	v_Count_R_Request_Archived_Inserted int;
	v_Remaining_R_Request_ToArchive_All int;
	v_R_Request_ToArchive_All_Initial_Count int;
	v_R_Request_ToArchive_All_Inserted_Count int;
BEGIN
	-- Prepare "to delete" list (ALL)
	select count(1) from dlm.R_Request_ToArchive_All INTO v_R_Request_ToArchive_All_Initial_Count;

	IF (v_R_Request_ToArchive_All_Initial_Count=0) 
	THEN
		RAISE NOTICE 'Table dlm.R_Request_ToArchive_All is empty; we recreate and fill it using parameter p_daysback=%',p_daysback;
		drop table if exists dlm.R_Request_ToArchive_All;
		
		create table dlm.R_Request_ToArchive_All as
		select R_Request_ID
		from dlm.R_Request_Archivable_v r
		where r.ArchivableSince <= now() - (p_daysback || ' DAYS')::INTERVAL -- older than p_daysback days
		;

		GET DIAGNOSTICS v_R_Request_ToArchive_All_Initial_Count = ROW_COUNT;
		
		-- Index it
		create index on dlm.R_Request_ToArchive_All(R_Request_ID);
		
		RAISE NOTICE 'Inserted % records into dlm.R_Request_ToArchive_All.', v_R_Request_ToArchive_All_Inserted_Count;
	ELSE
		RAISE NOTICE 'Table dlm.R_Request_ToArchive_All still contains % records from a previous run, so we ignore parameter p_daysback=%',v_R_Request_ToArchive_All_Initial_Count, p_daysback;
	END IF;

	--
	-- Prepare "to delete" list (Chunk)
	drop table if exists TMP_R_Request_ToArchive;
	create temporary table TMP_R_Request_ToArchive as select * from dlm.R_Request_ToArchive_All limit 0;

	RAISE NOTICE 'Inserting records into TMP_R_Request_ToArchive.';

	-- move 'p_limitrecords' records from dlm.R_Request_ToArchive_All to TMP_R_Request_ToArchive
	with items as (
		delete from dlm.R_Request_ToArchive_All t
		where t.R_Request_ID in (
			select R_Request_ID
			from dlm.R_Request_ToArchive_All
			limit p_limitrecords -- Chunk size
		)
		returning *
	)
	insert into TMP_R_Request_ToArchive
	select * from items
	;

	-- Index it and update the table statistics. the index is going to help us in moving additional records
	create index on TMP_R_Request_ToArchive(R_Request_ID);
	ANALYZE TMP_R_Request_ToArchive;


	-- update the table statistics again
	ANALYZE TMP_R_Request_ToArchive;

	select count(1) from TMP_R_Request_ToArchive INTO Count_R_Requests_ToArchive;
	RAISE NOTICE 'Requests to archive: % (param p_limitrecords=%) ', Count_R_Requests_ToArchive, p_limitrecords;
	
	-- Archive R_RequestUpdates
	with deleted_rows as (
		delete from R_RequestUpdate t
		where true
			and exists (select 1 from TMP_R_Request_ToArchive s where s.R_Request_ID=t.R_Request_ID)
		returning *
	)
	insert into dlm.R_RequestUpdate_Archived
	select * from deleted_rows;
	
	-- Archive R_Requests
	with deleted_rows as (
		delete from R_Request t
		where true
			and exists (select 1 from TMP_R_Request_ToArchive s where s.R_Request_ID=t.R_Request_ID)
		returning *
	)
	insert into dlm.R_Request_Archived
	select * from deleted_rows;

	GET DIAGNOSTICS v_Count_R_Request_Archived_Inserted = ROW_COUNT;
	RAISE NOTICE 'Moved % records to table dlm.R_Request_Archived', v_Count_R_Request_Archived_Inserted;

	-- Count remaining things to delete
	select count(1) from dlm.R_Request_ToArchive_All INTO v_Remaining_R_Request_ToArchive_All;   
	RAISE NOTICE 'Remaining records in dlm.R_Request_ToArchive_All: % ', v_Remaining_R_Request_ToArchive_All;

	RETURN QUERY select Count_R_Requests_ToArchive, 
			v_Count_R_Request_Archived_Inserted,
			v_Remaining_R_Request_ToArchive_All;
END;
$BODY$;

ALTER FUNCTION dlm.archive_r_request_data(integer, integer)
    OWNER TO metasfresh;

COMMENT ON FUNCTION dlm.archive_r_request_data(integer, integer)
    IS 'Moves old R_Request data to an "archive"-table in the dlm schema;
Parameters:
	p_daysback: work packages older than the given number of days are moved
	p_limitrecords: the chunk-size, i.e. the number of workpackages that are moved per invokaction.
	
Tip: to see more about what the fucntion does, do
	BEGIN; SELECT * FROM dlm.archive_r_request_data(p_daysback := 3, p_limitrecords := 30000); ROLLBACK;
and follow the function''s output (notice level)
see https://github.com/metasfresh/metasfresh/issues/5189';
