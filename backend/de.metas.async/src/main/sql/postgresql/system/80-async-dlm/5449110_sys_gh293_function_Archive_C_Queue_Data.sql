

DROP FUNCTION IF EXISTS dlm.Archive_C_Queue_Data(integer, integer);
CREATE OR REPLACE FUNCTION dlm.Archive_C_Queue_Data(daysBack integer, limitRecords integer) 
RETURNS TABLE(
		Count_Workpackages_ToArchive int, 
		Count_Queue_Element_Archived int,
		Count_Queue_WorkPackage_Param_Archived int,
		Count_Queue_WorkPackage_Log_Archived int,
		Count_Queue_Workpackage_Archived int,
		Count_Queue_Block_Archived int,
		Remaining_ToArchive int) 
AS $$
DECLARE
	Count_Workpackages_ToArchive int; 
	Count_Queue_Element_Archived int;
	Count_Queue_Element_Archived_Inserted int;
	Count_Queue_WorkPackage_Param_Archived int;
	Count_Queue_WorkPackage_Param_Archived_Inserted int;
	Count_Queue_WorkPackage_Log_Archived int;
	Count_Queue_WorkPackage_Log_Archived_Inserted int;
	Count_Queue_Workpackage_Archived int;
	Count_Queue_Workpackage_Archived_Inserted int;
	Count_Queue_Block_Archived int;
	Count_Queue_Block_Archived_Inserted int;
	Remaining_C_Queue_Workpackage_ToArchive_All int;
	C_Queue_Workpackage_ToArchive_All_Initial_Count int;
	C_Queue_Workpackage_ToArchive_All_Inserted_Count int;
BEGIN
	-- Prepare "to delete" list (ALL)
	select count(1) from dlm.C_Queue_Workpackage_ToArchive_All INTO C_Queue_Workpackage_ToArchive_All_Initial_Count;

	IF (C_Queue_Workpackage_ToArchive_All_Initial_Count=0) 
	THEN
		RAISE NOTICE 'Table dlm.C_Queue_Workpackage_ToArchive_All is empty; we recreate and fill it using parameter daysBack=%',daysBack;
		drop table if exists dlm.C_Queue_Workpackage_ToArchive_All;
		
		create table dlm.C_Queue_Workpackage_ToArchive_All as
		select C_Queue_Workpackage_ID, C_Queue_Block_ID, C_Async_Batch_ID
		from C_Queue_Workpackage wp
		where wp.Processed='Y'
		and wp.Updated <= now() - $1 -- older than $1 days
		;

		GET DIAGNOSTICS C_Queue_Workpackage_ToArchive_All_Inserted_Count = ROW_COUNT;
		
		-- Index it
		create index on dlm.C_Queue_Workpackage_ToArchive_All(C_Queue_Workpackage_ID);
		create index on dlm.C_Queue_Workpackage_ToArchive_All(C_Queue_Block_ID);
		create index on dlm.C_Queue_Workpackage_ToArchive_All(C_Async_Batch_ID);
		
		RAISE NOTICE 'Inserted % records into dlm.C_Queue_Workpackage_ToArchive_All.',C_Queue_Workpackage_ToArchive_All_Inserted_Count;
	ELSE
		RAISE NOTICE 'Table dlm.C_Queue_Workpackage_ToArchive_All still contains % records from a previous run, so we ignore parameter daysBack=%',C_Queue_Workpackage_ToArchive_All_Initial_Count, daysBack;
	END IF;
		

	--
	-- Prepare "to delete" list (Chunk)
	drop table if exists TMP_C_Queue_Workpackage_ToArchive;
	create temporary table TMP_C_Queue_Workpackage_ToArchive as select * from dlm.C_Queue_Workpackage_ToArchive_All limit 0;

	RAISE NOTICE 'Inserting records into TMP_C_Queue_Workpackage_ToArchive.';

	-- move 'limitRecords' records from dlm.C_Queue_Workpackage_ToArchive_All to TMP_C_Queue_Workpackage_ToArchive
	with items as (
		delete from dlm.C_Queue_Workpackage_ToArchive_All t
		where t.C_Queue_Workpackage_ID in (
			select C_Queue_Workpackage_ID
			from dlm.C_Queue_Workpackage_ToArchive_All
			-- order by t.C_Queue_Workpackage_ID -- oldest first; commented out, because it's too expensive
			limit $2 -- Chunk size
		)
		returning *
	)
	insert into TMP_C_Queue_Workpackage_ToArchive
	select * from items
	;

	-- Index it and update the table statistics. the index is going to help us in moving additional records
	create index on TMP_C_Queue_Workpackage_ToArchive(C_Queue_Workpackage_ID);
	create index on TMP_C_Queue_Workpackage_ToArchive(C_Queue_Block_ID);
	create index on TMP_C_Queue_Workpackage_ToArchive(C_Async_Batch_ID);
	ANALYZE TMP_C_Queue_Workpackage_ToArchive;

	-- also move such records from dlm.C_Queue_Workpackage_ToArchive_All to TMP_C_Queue_Workpackage_ToArchive that share a C_Queue_Block_ID with an already moved record
	RAISE NOTICE 'Inserting additional records into TMP_C_Queue_Workpackage_ToArchive that share a C_Queue_Block_ID with an already indested record.';
	with items as (
		delete from dlm.C_Queue_Workpackage_ToArchive_All t
		where t.C_Queue_Block_ID in (
			select C_Queue_Block_ID
			from TMP_C_Queue_Workpackage_ToArchive
		)
		returning *
	)
	insert into TMP_C_Queue_Workpackage_ToArchive
	select * from items
	;
	
	-- also move such records from dlm.C_Queue_Workpackage_ToArchive_All to TMP_C_Queue_Workpackage_ToArchive that share a C_ASync_Batch_ID with an already moved record
	RAISE NOTICE 'Inserting additional records into TMP_C_Queue_Workpackage_ToArchive that share a C_ASync_Batch_ID with an already indested record.';
	with items as (
		delete from dlm.C_Queue_Workpackage_ToArchive_All t
		where t.C_ASync_Batch_ID in (
			select C_ASync_Batch_ID
			from TMP_C_Queue_Workpackage_ToArchive
		)
		returning *
	)
	insert into TMP_C_Queue_Workpackage_ToArchive
	select * from items
	;

	-- update the table statistics again
	ANALYZE TMP_C_Queue_Workpackage_ToArchive;

	select count(1) from TMP_C_Queue_Workpackage_ToArchive INTO Count_Workpackages_ToArchive;
	RAISE NOTICE 'Workpackages to archive: % (param limitRecords=%) ', Count_Workpackages_ToArchive, limitRecords;
	
	-- Archive C_Queue_Elements
	with deleted_rows as (
		delete from C_Queue_Element t
		where exists (select 1 from TMP_C_Queue_Workpackage_ToArchive s where s.C_Queue_Workpackage_ID=t.C_Queue_Workpackage_ID)
		returning *
	)
	insert into dlm.C_Queue_Element_Archived
	select * from deleted_rows;
	
	GET DIAGNOSTICS Count_Queue_Element_Archived_Inserted = ROW_COUNT;
	SELECT count(1) from dlm.C_Queue_Element_Archived INTO Count_Queue_Element_Archived;
	RAISE NOTICE 'Moved % records to table dlm.C_Queue_Element_Archived, it now has % records', Count_Queue_Element_Archived_Inserted, Count_Queue_Element_Archived;
	
	-- Archive C_Queue_WorkPackage_Param_Archived 
	with deleted_rows as (
		delete from C_Queue_Workpackage_Param t
		where exists (select 1 from TMP_C_Queue_Workpackage_ToArchive s where s.C_Queue_Workpackage_ID=t.C_Queue_Workpackage_ID)
		returning *
	)
	insert into dlm.C_Queue_Workpackage_Param_Archived
	select * from deleted_rows;
	
	GET DIAGNOSTICS Count_Queue_WorkPackage_Param_Archived_Inserted = ROW_COUNT;
	SELECT count(1) from dlm.C_Queue_Workpackage_Param_Archived INTO Count_Queue_WorkPackage_Param_Archived;
	RAISE NOTICE 'Moved % records to table dlm.C_Queue_Workpackage_Param_Archived, it now has % records', Count_Queue_WorkPackage_Param_Archived_Inserted, Count_Queue_WorkPackage_Param_Archived;

	-- Archive C_Queue_WorkPackage_Log_Archived 
	with deleted_rows as (
		delete from C_Queue_Workpackage_Log t
		where exists (select 1 from TMP_C_Queue_Workpackage_ToArchive s where s.C_Queue_Workpackage_ID=t.C_Queue_Workpackage_ID)
		returning *
	)
	insert into dlm.C_Queue_Workpackage_Log_Archived
	select * from deleted_rows;
	
	GET DIAGNOSTICS Count_Queue_WorkPackage_Log_Archived_Inserted = ROW_COUNT;
	SELECT count(1) from dlm.C_Queue_Workpackage_Log_Archived INTO Count_Queue_WorkPackage_Log_Archived;
	RAISE NOTICE 'Moved % records to table dlm.C_Queue_Workpackage_Log_Archived, it now has % records', Count_Queue_WorkPackage_Log_Archived_Inserted, Count_Queue_WorkPackage_Log_Archived;
	
	-- Archive C_Queue_Workpackages
	with deleted_rows as (
		delete from C_Queue_Workpackage t
		where exists (select 1 from TMP_C_Queue_Workpackage_ToArchive s where s.C_Queue_Workpackage_ID=t.C_Queue_Workpackage_ID)
		returning *
	)
	insert into dlm.C_Queue_Workpackage_Archived
	select * from deleted_rows;

	GET DIAGNOSTICS Count_Queue_Workpackage_Archived_Inserted = ROW_COUNT;
	SELECT count(1) from dlm.C_Queue_Workpackage_Archived INTO Count_Queue_Workpackage_Archived;
	RAISE NOTICE 'Moved % records to table dlm.C_Queue_Workpackage_Archived, it now has % records', Count_Queue_Workpackage_Archived_Inserted, Count_Queue_Workpackage_Archived;
		
	-- Archive C_Queue_Blocks
	with deleted_rows as (
		delete from C_Queue_Block t
		where true
			and exists (select 1 from TMP_C_Queue_Workpackage_ToArchive s where s.C_Queue_Block_ID=t.C_Queue_Block_ID)
			and not exists (select 1 from C_Queue_Workpackage wp where wp.C_Queue_Block_ID=t.C_Queue_Block_ID) -- there are no other workpackages
		returning *
	)
	insert into dlm.C_Queue_Block_Archived
	select * from deleted_rows;

	GET DIAGNOSTICS Count_Queue_Block_Archived_Inserted = ROW_COUNT;
	SELECT count(1) from dlm.C_Queue_Block_Archived INTO Count_Queue_Block_Archived;
	RAISE NOTICE 'Moved % records to table dlm.C_Queue_Block_Archived, it now has % records', Count_Queue_Block_Archived_Inserted, Count_Queue_Block_Archived;

	-- Count remaining things to delete
	select count(1) from dlm.C_Queue_Workpackage_ToArchive_All INTO Remaining_C_Queue_Workpackage_ToArchive_All;   
	RAISE NOTICE 'Remaining records in dlm.C_Queue_Workpackage_ToArchive_All: % ', Remaining_C_Queue_Workpackage_ToArchive_All;

	RAISE NOTICE 'Updating the stats of our production tables (analyze)';
	ANALYZE c_queue_block;
	ANALYZE c_queue_element;
	ANALYZE c_queue_workpackage;
	ANALYZE c_queue_workpackage_log;
	ANALYZE c_queue_workpackage_param;
			
	RETURN QUERY select Count_Workpackages_ToArchive , 
			Count_Queue_Element_Archived ,
			Count_Queue_WorkPackage_Param_Archived ,
			Count_Queue_WorkPackage_Log_Archived,
			Count_Queue_Workpackage_Archived ,
			Count_Queue_Block_Archived,
			Remaining_C_Queue_Workpackage_ToArchive_All;
END;
$$ LANGUAGE plpgsql;


COMMENT ON FUNCTION dlm.Archive_C_Queue_Data(integer, integer) IS 'Moves async wrokpackage data do and "archive"-table in the dlm schema;
Parameters:
	daysBack: work packages older than the given number of days are moved
	limitRecords: the chunk-size, i.e. the number of workpackages that are moved per invokaction.
	
Tip: to see more about what the fucntion does, do
	BEGIN; SELECT * FROM dlm.Archive_C_Queue_Data(3,30000); ROLLBACK;
and follow the function''s output (notice level)
see https://github.com/metasfresh/metasfresh/issues/293';
