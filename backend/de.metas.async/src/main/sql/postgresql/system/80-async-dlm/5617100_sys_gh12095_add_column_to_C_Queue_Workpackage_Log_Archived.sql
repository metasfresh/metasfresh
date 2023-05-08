DO
$$
    BEGIN
        ALTER TABLE IF EXISTS dlm.C_Queue_Workpackage_Log_Archived ADD COLUMN ad_issue_id numeric(10, 0);
    EXCEPTION
        WHEN SQLSTATE '42701' THEN
            RAISE NOTICE 'At least one column already existed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
    END
$$
;

DO
$$
    BEGIN
        ALTER TABLE dlm.C_Queue_Workpackage_Archived ADD batchenqueuedcount numeric(10) DEFAULT NULL::numeric;
    EXCEPTION
        WHEN SQLSTATE '42701' THEN
            RAISE NOTICE 'At least one column already existed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
    END
$$
;