CREATE OR REPLACE FUNCTION dlm.archive_c_queue_data(IN p_daysback     integer,
                                                    IN p_limitrecords integer)
    RETURNS TABLE
            (
                count_workpackages_toarchive                    integer,
                count_queue_element_archived_inserted           integer,
                count_queue_workpackage_param_archived_inserted integer,
                count_queue_workpackage_log_archived_inserted   integer,
                count_queue_workpackage_archived_inserted       integer,
                count_queue_block_archived                      integer,
                remaining_toarchive                             integer
            )
AS
$BODY$
DECLARE
    Count_Workpackages_ToArchive                     int;
    Count_Queue_Element_Archived_Inserted            int;
    Count_Queue_WorkPackage_Param_Archived_Inserted  int;
    Count_Queue_WorkPackage_Log_Archived_Inserted    int;
    Count_Queue_Workpackage_Archived_Inserted        int;
    Count_Queue_Block_Archived_Inserted              int;
    Remaining_C_Queue_Workpackage_ToArchive_All      int;
    C_Queue_Workpackage_ToArchive_All_Initial_Count  int;
    C_Queue_Workpackage_ToArchive_All_Inserted_Count int;
BEGIN
    -- Prepare "to delete" list (ALL)
    SELECT COUNT(1) FROM dlm.C_Queue_Workpackage_ToArchive_All INTO C_Queue_Workpackage_ToArchive_All_Initial_Count;

    IF (C_Queue_Workpackage_ToArchive_All_Initial_Count = 0)
    THEN
        RAISE NOTICE 'Table dlm.C_Queue_Workpackage_ToArchive_All is empty; we recreate and fill it using parameter p_daysback=%',p_daysback;
        DROP TABLE IF EXISTS dlm.C_Queue_Workpackage_ToArchive_All;

        CREATE TABLE dlm.C_Queue_Workpackage_ToArchive_All AS
        SELECT C_Queue_Workpackage_ID, C_Async_Batch_ID
        FROM C_Queue_Workpackage wp
        WHERE wp.Processed = 'Y'
          AND wp.Updated <= NOW() - $1 -- older than $1 days
        ;

        GET DIAGNOSTICS C_Queue_Workpackage_ToArchive_All_Inserted_Count = ROW_COUNT;

        -- Index it
        CREATE INDEX ON dlm.C_Queue_Workpackage_ToArchive_All (C_Queue_Workpackage_ID);
        CREATE INDEX ON dlm.C_Queue_Workpackage_ToArchive_All (C_Async_Batch_ID);

        RAISE NOTICE 'Inserted % records into dlm.C_Queue_Workpackage_ToArchive_All.',C_Queue_Workpackage_ToArchive_All_Inserted_Count;
    ELSE
        RAISE NOTICE 'Table dlm.C_Queue_Workpackage_ToArchive_All still contains % records from a previous run, so we ignore parameter p_daysback=%',C_Queue_Workpackage_ToArchive_All_Initial_Count, p_daysback;
    END IF;


    --
    -- Prepare "to delete" list (Chunk)
    DROP TABLE IF EXISTS TMP_C_Queue_Workpackage_ToArchive;
    CREATE TEMPORARY TABLE TMP_C_Queue_Workpackage_ToArchive AS
    SELECT * FROM dlm.C_Queue_Workpackage_ToArchive_All LIMIT 0;

    RAISE NOTICE 'Inserting records into TMP_C_Queue_Workpackage_ToArchive.';

    -- move 'p_limitrecords' records from dlm.C_Queue_Workpackage_ToArchive_All to TMP_C_Queue_Workpackage_ToArchive
    WITH items AS (
        DELETE FROM dlm.C_Queue_Workpackage_ToArchive_All t
            WHERE t.C_Queue_Workpackage_ID IN (
                SELECT C_Queue_Workpackage_ID
                FROM dlm.C_Queue_Workpackage_ToArchive_All
                     -- order by t.C_Queue_Workpackage_ID -- oldest first; commented out, because it's too expensive
                LIMIT $2 -- Chunk size
            )
            RETURNING *
    )
    INSERT
    INTO TMP_C_Queue_Workpackage_ToArchive
    SELECT *
    FROM items;

    -- Index it and update the table statistics. the index is going to help us in moving additional records
    CREATE INDEX ON TMP_C_Queue_Workpackage_ToArchive (C_Queue_Workpackage_ID);
    CREATE INDEX ON TMP_C_Queue_Workpackage_ToArchive (C_Async_Batch_ID);
    ANALYZE TMP_C_Queue_Workpackage_ToArchive;

    -- also move such records from dlm.C_Queue_Workpackage_ToArchive_All to TMP_C_Queue_Workpackage_ToArchive that share a C_ASync_Batch_ID with an already moved record
    RAISE NOTICE 'Inserting additional records into TMP_C_Queue_Workpackage_ToArchive that share a C_ASync_Batch_ID with an already inserted record.';
    WITH items AS (
        DELETE FROM dlm.C_Queue_Workpackage_ToArchive_All t
            WHERE t.C_ASync_Batch_ID IN (
                SELECT C_ASync_Batch_ID
                FROM TMP_C_Queue_Workpackage_ToArchive
            )
            RETURNING *
    )
    INSERT
    INTO TMP_C_Queue_Workpackage_ToArchive
    SELECT *
    FROM items;

    -- update the table statistics again
    ANALYZE TMP_C_Queue_Workpackage_ToArchive;

    SELECT COUNT(1) FROM TMP_C_Queue_Workpackage_ToArchive INTO Count_Workpackages_ToArchive;
    RAISE NOTICE 'Workpackages to archive: % (param p_limitrecords=%) ', Count_Workpackages_ToArchive, p_limitrecords;

    -- Archive C_Queue_Elements
    WITH deleted_rows AS (
        DELETE FROM C_Queue_Element t
            WHERE EXISTS(SELECT 1 FROM TMP_C_Queue_Workpackage_ToArchive s WHERE s.C_Queue_Workpackage_ID = t.C_Queue_Workpackage_ID)
            RETURNING *
    )
    INSERT
    INTO dlm.C_Queue_Element_Archived
    SELECT *
    FROM deleted_rows;

    GET DIAGNOSTICS Count_Queue_Element_Archived_Inserted = ROW_COUNT;
    RAISE NOTICE 'Moved % records to table dlm.C_Queue_Element_Archived', Count_Queue_Element_Archived_Inserted;

    -- Archive C_Queue_WorkPackage_Param_Archived
    WITH deleted_rows AS (
        DELETE FROM C_Queue_Workpackage_Param t
            WHERE EXISTS(SELECT 1 FROM TMP_C_Queue_Workpackage_ToArchive s WHERE s.C_Queue_Workpackage_ID = t.C_Queue_Workpackage_ID)
            RETURNING *
    )
    INSERT
    INTO dlm.C_Queue_Workpackage_Param_Archived
    SELECT *
    FROM deleted_rows;

    GET DIAGNOSTICS Count_Queue_WorkPackage_Param_Archived_Inserted = ROW_COUNT;
    RAISE NOTICE 'Moved % records to table dlm.C_Queue_Workpackage_Param_Archived', Count_Queue_WorkPackage_Param_Archived_Inserted;

    -- Archive C_Queue_WorkPackage_Log_Archived
    WITH deleted_rows AS (
        DELETE FROM C_Queue_Workpackage_Log t
            WHERE EXISTS(SELECT 1 FROM TMP_C_Queue_Workpackage_ToArchive s WHERE s.C_Queue_Workpackage_ID = t.C_Queue_Workpackage_ID)
            RETURNING *
    )
    INSERT
    INTO dlm.C_Queue_Workpackage_Log_Archived
    SELECT *
    FROM deleted_rows;

    GET DIAGNOSTICS Count_Queue_WorkPackage_Log_Archived_Inserted = ROW_COUNT;
    RAISE NOTICE 'Moved % records to table dlm.C_Queue_Workpackage_Log_Archived', Count_Queue_WorkPackage_Log_Archived_Inserted;

    -- Archive C_Queue_Workpackages
    WITH deleted_rows AS (
        DELETE FROM C_Queue_Workpackage t
            WHERE EXISTS(SELECT 1 FROM TMP_C_Queue_Workpackage_ToArchive s WHERE s.C_Queue_Workpackage_ID = t.C_Queue_Workpackage_ID)
            RETURNING *
    )
    INSERT
    INTO dlm.C_Queue_Workpackage_Archived
    SELECT *
    FROM deleted_rows;

    GET DIAGNOSTICS Count_Queue_Workpackage_Archived_Inserted = ROW_COUNT;
    RAISE NOTICE 'Moved % records to table dlm.C_Queue_Workpackage_Archived', Count_Queue_Workpackage_Archived_Inserted;

    -- Count remaining things to delete
    SELECT COUNT(1) FROM dlm.C_Queue_Workpackage_ToArchive_All INTO Remaining_C_Queue_Workpackage_ToArchive_All;
    RAISE NOTICE 'Remaining records in dlm.C_Queue_Workpackage_ToArchive_All: % ', Remaining_C_Queue_Workpackage_ToArchive_All;

    RAISE NOTICE 'Updating the stats of our production tables (analyze)';
    ANALYZE c_queue_element;
    ANALYZE c_queue_workpackage;
    ANALYZE c_queue_workpackage_log;
    ANALYZE c_queue_workpackage_param;

    RETURN QUERY SELECT Count_Workpackages_ToArchive,
                        Count_Queue_Element_Archived_Inserted,
                        Count_Queue_WorkPackage_Param_Archived_Inserted,
                        Count_Queue_WorkPackage_Log_Archived_Inserted,
                        Count_Queue_Workpackage_Archived_Inserted,
                        Count_Queue_Block_Archived_Inserted,
                        Remaining_C_Queue_Workpackage_ToArchive_All;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
                     ROWS 1000
;

ALTER FUNCTION dlm.archive_c_queue_data(integer, integer)
    OWNER TO metasfresh
;

COMMENT ON FUNCTION dlm.archive_c_queue_data(integer, integer) IS 'Moves async wrokpackage data do and "archive"-table in the dlm schema;
Parameters:
	p_daysback: work packages older than the given number of days are moved
	p_limitrecords: the chunk-size, i.e. the number of workpackages that are moved per invokaction.

Tip: to see more about what the fucntion does, do
	BEGIN; SELECT * FROM dlm.Archive_C_Queue_Data(3,30000); ROLLBACK;
and follow the function''s output (notice level)
see https://github.com/metasfresh/metasfresh/issues/293'
;
