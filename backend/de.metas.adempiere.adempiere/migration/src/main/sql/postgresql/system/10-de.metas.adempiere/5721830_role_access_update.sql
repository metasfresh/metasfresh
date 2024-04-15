DROP FUNCTION IF EXISTS role_access_update_windows(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_UserLevel    varchar,
    p_CreatedBy    numeric
)
;

CREATE OR REPLACE FUNCTION role_access_update_windows(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_UserLevel    varchar,
    p_CreatedBy    numeric = 0 -- System
)
    RETURNS void
AS
$BODY$
DECLARE
    v_AccessLevels           varchar[];
    v_IsExcludeWindowsForAll BOOLEAN;
    v_count                  integer;
    v_info                   text;
    v_changes                BOOLEAN := FALSE;
BEGIN
    v_AccessLevels := userlevel_to_accesslevels(p_UserLevel);
    v_IsExcludeWindowsForAll := (p_UserLevel = '__O');
    -- RAISE NOTICE '    AccessLevels: %', v_AccessLevels;
    IF (v_IsExcludeWindowsForAll) THEN
        RAISE NOTICE '    Excluding all windows containing `(all)` string in their names';
    END IF;

    --
    -- Create a temporary table of AD_Window_Access as we would like to have them
    DROP TABLE IF EXISTS TMP_AD_Window_Access;
    CREATE TEMPORARY TABLE TMP_AD_Window_Access AS
    SELECT DISTINCT '-'::char(1)                            AS row_action -- to be updated later
                  , w.AD_Window_ID                          AS AD_Window_ID
                  , p_AD_Role_ID                            AS AD_Role_ID
                  , p_AD_Client_ID                          AS AD_Client_ID
                  , p_AD_Org_ID                             AS AD_Org_ID
                  , 'Y'::char(1)                            AS IsActive
                  , 'Y'::char(1)                            AS IsReadWrite
                  , w.name || ' (' || w.ad_window_id || ')' AS windowInfo
    FROM AD_Window w
             LEFT OUTER JOIN AD_Tab tab ON (
        w.AD_Window_ID = tab.AD_Window_ID
            AND tab.SeqNo = (SELECT MIN(SeqNo) FROM AD_Tab xt WHERE xt.AD_Window_ID = w.AD_Window_ID AND xt.IsActive = 'Y')
        )
             LEFT OUTER JOIN AD_Table t ON (t.AD_Table_ID = tab.AD_Table_ID)
    WHERE (tab.AD_Tab_ID IS NULL OR t.AccessLevel = ANY (v_AccessLevels))
      AND ((NOT v_IsExcludeWindowsForAll) OR w.Name NOT LIKE '%(all)%');
    --
    CREATE UNIQUE INDEX ON TMP_AD_Window_Access (AD_Role_ID, AD_Window_ID);

    --
    -- Determine those access records that does not currently exist and we have to add them
    UPDATE TMP_AD_Window_Access t
    SET row_action='I'
    WHERE NOT EXISTS (SELECT 1 FROM ad_window_access a WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Window_ID = t.AD_Window_ID);

    --
    -- Determine those access records that does exist but they are different from how we expect them to be
    UPDATE TMP_AD_Window_Access t
    SET row_action=(CASE
                        WHEN
                            a.AD_Client_ID IS DISTINCT FROM t.ad_client_id
                                OR a.AD_Org_ID IS DISTINCT FROM t.ad_org_id
                                OR a.IsActive IS DISTINCT FROM t.IsActive
                                OR a.IsReadWrite IS DISTINCT FROM t.IsReadWrite
                            THEN 'U' -- have changes
                            ELSE '-' -- no change
                    END
        )
    FROM ad_window_access a
    WHERE a.AD_Role_ID = p_AD_Role_ID
      AND a.AD_Window_ID = t.AD_Window_ID;

    --
    -- Determine those access records that currently exist but they shall be removed
    INSERT INTO TMP_AD_Window_Access (row_action, AD_Window_ID, AD_Role_ID, AD_Client_ID, AD_Org_ID, IsActive, IsReadWrite, windowInfo)
    SELECT 'D'::char(1)                                           AS row_action
         , a.AD_Window_ID
         , a.AD_Role_ID
         , a.AD_Client_ID
         , a.AD_Org_ID
         , a.isactive
         , a.IsReadWrite
         , COALESCE(w.name, '?') || ' (' || a.ad_window_id || ')' AS windowInfo
    FROM ad_window_access a
             LEFT OUTER JOIN ad_window w ON w.ad_window_id = a.ad_window_id
    WHERE a.ad_role_id = p_AD_Role_ID
      AND NOT EXISTS (SELECT 1 FROM TMP_AD_Window_Access t WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Window_ID = t.AD_Window_ID);

    --
    -- Cleanup TMP_AD_Window_Access: remove those records on which we don't have changes (i.e. we will keep them as they are)
    DELETE FROM TMP_AD_Window_Access WHERE row_action = '-';

    --
    --
    -- PERFORM CHANGES
    --
    --

    --
    -- Insert new records
    INSERT INTO ad_window_access(ad_window_id, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, isreadwrite)
    SELECT t.AD_Window_ID,
           t.AD_Role_ID,
           t.AD_Client_ID,
           t.AD_Org_ID,
           t.IsActive,
           NOW()       AS Created,
           p_CreatedBy AS CreatedBy,
           NOW()       AS Updated,
           p_CreatedBy AS UpdatedBy,
           t.IsReadWrite
    FROM TMP_AD_Window_Access t
    WHERE t.row_action = 'I';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.windowInfo, ', ' ORDER BY t.AD_Window_ID) INTO v_info FROM TMP_AD_Window_Access t WHERE t.row_action = 'I';
        RAISE NOTICE 'Granted access to % window(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Update existing records
    UPDATE ad_window_access a
    SET isactive=t.IsActive, updated=NOW(), updatedby=p_CreatedBy, isreadwrite=t.IsReadWrite
    FROM TMP_AD_Window_Access t
    WHERE t.AD_Role_ID = a.ad_role_id
      AND t.AD_Window_ID = a.ad_window_id
      AND t.row_action = 'U';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.windowInfo, ', ' ORDER BY t.AD_Window_ID) INTO v_info FROM TMP_AD_Window_Access t WHERE t.row_action = 'U';
        RAISE NOTICE 'Updated access to % window(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Remote records
    DELETE
    FROM ad_window_access a
    WHERE EXISTS (SELECT 1 FROM TMP_AD_Window_Access t WHERE t.AD_Role_ID = a.ad_role_id AND t.AD_Window_ID = a.ad_window_id AND t.row_action = 'D');
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.windowInfo, ', ' ORDER BY t.AD_Window_ID) INTO v_info FROM TMP_AD_Window_Access t WHERE t.row_action = 'D';
        RAISE NOTICE 'Revoked access to % window(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    IF (NOT v_changes) THEN
        RAISE NOTICE 'No window access changes performed (all are up2date)';
    END IF;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
;

-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS role_access_update_processes(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_UserLevel    varchar,
    p_CreatedBy    numeric
)
;

CREATE OR REPLACE FUNCTION role_access_update_processes(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_UserLevel    varchar,
    p_CreatedBy    numeric = 0 -- System
)
    RETURNS void
AS
$BODY$
DECLARE
    v_AccessLevels varchar[];
    v_count        integer;
    v_info         text;
    v_changes      BOOLEAN := FALSE;
BEGIN
    v_AccessLevels := userlevel_to_accesslevels(p_UserLevel);
    -- RAISE NOTICE '    AccessLevels: %', v_AccessLevels;

    --
    -- Create a temporary table of AD_Process_Access as we would like to have them
    DROP TABLE IF EXISTS TMP_AD_Process_Access;
    CREATE TEMPORARY TABLE TMP_AD_Process_Access AS
    SELECT DISTINCT '-'::char(1)                              AS row_action -- to be updated later
                  , p.AD_Process_ID                           AS AD_Process_ID
                  , p_AD_Role_ID                              AS AD_Role_ID
                  , p_AD_Client_ID                            AS AD_Client_ID
                  , p_AD_Org_ID                               AS AD_Org_ID
                  , 'Y'::char(1)                              AS IsActive
                  , 'Y'::char(1)                              AS IsReadWrite
                  , p.value || ' (' || p.ad_process_id || ')' AS processInfo
    FROM AD_Process p
    WHERE (p.AccessLevel = ANY (v_AccessLevels));
    --
    CREATE UNIQUE INDEX ON TMP_AD_Process_Access (AD_Role_ID, AD_Process_ID);

    --
    -- Determine those access records that does not currently exist and we have to add them
    UPDATE TMP_AD_Process_Access t
    SET row_action='I'
    WHERE NOT EXISTS (SELECT 1 FROM AD_Process_Access a WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Process_ID = t.AD_Process_ID);

    --
    -- Determine those access records that does exist but they are different from how we expect them to be
    UPDATE TMP_AD_Process_Access t
    SET row_action=(CASE
                        WHEN
                            a.AD_Client_ID IS DISTINCT FROM t.ad_client_id
                                OR a.AD_Org_ID IS DISTINCT FROM t.ad_org_id
                                OR a.IsActive IS DISTINCT FROM t.IsActive
                                OR a.IsReadWrite IS DISTINCT FROM t.IsReadWrite
                            THEN 'U' -- have changes
                            ELSE '-' -- no change
                    END
        )
    FROM AD_Process_Access a
    WHERE a.AD_Role_ID = p_AD_Role_ID
      AND a.AD_Process_ID = t.AD_Process_ID;

    --
    -- Determine those access records that currently exist but they shall be removed
    INSERT INTO TMP_AD_Process_Access (row_action, AD_Process_ID, AD_Role_ID, AD_Client_ID, AD_Org_ID, IsActive, IsReadWrite, processInfo)
    SELECT 'D'::char(1)                                             AS row_action
         , a.AD_Process_ID
         , a.AD_Role_ID
         , a.AD_Client_ID
         , a.AD_Org_ID
         , a.isactive
         , a.IsReadWrite
         , COALESCE(p.value, '?') || ' (' || a.AD_Process_ID || ')' AS processInfo
    FROM AD_Process_Access a
             LEFT OUTER JOIN ad_process p ON p.AD_Process_ID = a.AD_Process_ID
    WHERE a.ad_role_id = p_AD_Role_ID
      AND NOT EXISTS (SELECT 1 FROM TMP_AD_Process_Access t WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Process_ID = t.AD_Process_ID);

    --
    -- Cleanup TMP_AD_Process_Access: remove those records on which we don't have changes (i.e. we will keep them as they are)
    DELETE FROM TMP_AD_Process_Access WHERE row_action = '-';

    --
    --
    -- PERFORM CHANGES
    --
    --

    --
    -- Insert new records
    INSERT INTO AD_Process_Access(AD_Process_ID, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, isreadwrite)
    SELECT t.AD_Process_ID,
           t.AD_Role_ID,
           t.AD_Client_ID,
           t.AD_Org_ID,
           t.IsActive,
           NOW()       AS Created,
           p_CreatedBy AS CreatedBy,
           NOW()       AS Updated,
           p_CreatedBy AS UpdatedBy,
           t.IsReadWrite
    FROM TMP_AD_Process_Access t
    WHERE t.row_action = 'I';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.processInfo, ', ' ORDER BY t.AD_Process_ID) INTO v_info FROM TMP_AD_Process_Access t WHERE t.row_action = 'I';
        RAISE NOTICE 'Granted access to % process(es): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Update existing records
    UPDATE AD_Process_Access a
    SET isactive=t.IsActive, updated=NOW(), updatedby=p_CreatedBy, isreadwrite=t.IsReadWrite
    FROM TMP_AD_Process_Access t
    WHERE t.AD_Role_ID = a.ad_role_id
      AND t.AD_Process_ID = a.AD_Process_ID
      AND t.row_action = 'U';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.processInfo, ', ' ORDER BY t.AD_Process_ID) INTO v_info FROM TMP_AD_Process_Access t WHERE t.row_action = 'U';
        RAISE NOTICE 'Updated access to % process(es): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Remote records
    DELETE
    FROM AD_Process_Access a
    WHERE EXISTS (SELECT 1 FROM TMP_AD_Process_Access t WHERE t.AD_Role_ID = a.ad_role_id AND t.AD_Process_ID = a.AD_Process_ID AND t.row_action = 'D');
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.processInfo, ', ' ORDER BY t.AD_Process_ID) INTO v_info FROM TMP_AD_Process_Access t WHERE t.row_action = 'D';
        RAISE NOTICE 'Revoked access to % process(es): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    IF (NOT v_changes) THEN
        RAISE NOTICE 'No process access changes performed (all are up2date)';
    END IF;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
;



-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------


DROP FUNCTION IF EXISTS role_access_update_forms(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_UserLevel    varchar,
    p_CreatedBy    numeric
)
;

CREATE OR REPLACE FUNCTION role_access_update_forms(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_UserLevel    varchar,
    p_CreatedBy    numeric = 0 -- System
)
    RETURNS void
AS
$BODY$
DECLARE
    v_AccessLevels varchar[];
    v_count        integer;
    v_info         text;
    v_changes      BOOLEAN := FALSE;
BEGIN
    v_AccessLevels := userlevel_to_accesslevels(p_UserLevel);
    -- RAISE NOTICE '    AccessLevels: %', v_AccessLevels;

    --
    -- Create a temporary table of AD_Form_Access as we would like to have them
    DROP TABLE IF EXISTS TMP_AD_Form_Access;
    CREATE TEMPORARY TABLE TMP_AD_Form_Access AS
    SELECT DISTINCT '-'::char(1)                          AS row_action -- to be updated later
                  , f.AD_Form_ID                          AS AD_Form_ID
                  , p_AD_Role_ID                          AS AD_Role_ID
                  , p_AD_Client_ID                        AS AD_Client_ID
                  , p_AD_Org_ID                           AS AD_Org_ID
                  , 'Y'::char(1)                          AS IsActive
                  , 'Y'::char(1)                          AS IsReadWrite
                  , f.name || ' (' || f.AD_Form_id || ')' AS formInfo
    FROM AD_Form f
    WHERE (f.AccessLevel = ANY (v_AccessLevels));
    --
    CREATE UNIQUE INDEX ON TMP_AD_Form_Access (AD_Role_ID, AD_Form_ID);

    --
    -- Determine those access records that does not currently exist and we have to add them
    UPDATE TMP_AD_Form_Access t
    SET row_action='I'
    WHERE NOT EXISTS (SELECT 1 FROM AD_Form_Access a WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Form_ID = t.AD_Form_ID);

    --
    -- Determine those access records that does exist but they are different from how we expect them to be
    UPDATE TMP_AD_Form_Access t
    SET row_action=(CASE
                        WHEN
                            a.AD_Client_ID IS DISTINCT FROM t.ad_client_id
                                OR a.AD_Org_ID IS DISTINCT FROM t.ad_org_id
                                OR a.IsActive IS DISTINCT FROM t.IsActive
                                OR a.IsReadWrite IS DISTINCT FROM t.IsReadWrite
                            THEN 'U' -- have changes
                            ELSE '-' -- no change
                    END
        )
    FROM AD_Form_Access a
    WHERE a.AD_Role_ID = p_AD_Role_ID
      AND a.AD_Form_ID = t.AD_Form_ID;

    --
    -- Determine those access records that currently exist but they shall be removed
    INSERT INTO TMP_AD_Form_Access (row_action, AD_Form_ID, AD_Role_ID, AD_Client_ID, AD_Org_ID, IsActive, IsReadWrite, formInfo)
    SELECT 'D'::char(1)                                         AS row_action
         , a.AD_Form_ID
         , a.AD_Role_ID
         , a.AD_Client_ID
         , a.AD_Org_ID
         , a.isactive
         , a.IsReadWrite
         , COALESCE(f.name, '?') || ' (' || a.AD_Form_ID || ')' AS formInfo
    FROM AD_Form_Access a
             LEFT OUTER JOIN AD_Form f ON f.AD_Form_ID = a.AD_Form_ID
    WHERE a.ad_role_id = p_AD_Role_ID
      AND NOT EXISTS (SELECT 1 FROM TMP_AD_Form_Access t WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Form_ID = t.AD_Form_ID);

    --
    -- Cleanup TMP_AD_Form_Access: remove those records on which we don't have changes (i.e. we will keep them as they are)
    DELETE FROM TMP_AD_Form_Access WHERE row_action = '-';

    --
    --
    -- PERFORM CHANGES
    --
    --

    --
    -- Insert new records
    INSERT INTO AD_Form_Access(AD_Form_ID, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, isreadwrite)
    SELECT t.AD_Form_ID,
           t.AD_Role_ID,
           t.AD_Client_ID,
           t.AD_Org_ID,
           t.IsActive,
           NOW()       AS Created,
           p_CreatedBy AS CreatedBy,
           NOW()       AS Updated,
           p_CreatedBy AS UpdatedBy,
           t.IsReadWrite
    FROM TMP_AD_Form_Access t
    WHERE t.row_action = 'I';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.formInfo, ', ' ORDER BY t.AD_Form_ID) INTO v_info FROM TMP_AD_Form_Access t WHERE t.row_action = 'I';
        RAISE NOTICE 'Granted access to % form(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Update existing records
    UPDATE AD_Form_Access a
    SET isactive=t.IsActive, updated=NOW(), updatedby=p_CreatedBy, isreadwrite=t.IsReadWrite
    FROM TMP_AD_Form_Access t
    WHERE t.AD_Role_ID = a.ad_role_id
      AND t.AD_Form_ID = a.AD_Form_ID
      AND t.row_action = 'U';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.formInfo, ', ' ORDER BY t.AD_Form_ID) INTO v_info FROM TMP_AD_Form_Access t WHERE t.row_action = 'U';
        RAISE NOTICE 'Updated access to % form(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Remote records
    DELETE
    FROM AD_Form_Access a
    WHERE EXISTS (SELECT 1 FROM TMP_AD_Form_Access t WHERE t.AD_Role_ID = a.ad_role_id AND t.AD_Form_ID = a.AD_Form_ID AND t.row_action = 'D');
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.formInfo, ', ' ORDER BY t.AD_Form_ID) INTO v_info FROM TMP_AD_Form_Access t WHERE t.row_action = 'D';
        RAISE NOTICE 'Revoked access to % form(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    IF (NOT v_changes) THEN
        RAISE NOTICE 'No form access changes performed (all are up2date)';
    END IF;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
;



-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------


DROP FUNCTION IF EXISTS role_access_update_tasks(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_UserLevel    varchar,
    p_CreatedBy    numeric
)
;

CREATE OR REPLACE FUNCTION role_access_update_tasks(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_UserLevel    varchar,
    p_CreatedBy    numeric = 0 -- System
)
    RETURNS void
AS
$BODY$
DECLARE
    v_AccessLevels varchar[];
    v_count        integer;
    v_info         text;
    v_changes      BOOLEAN := FALSE;
BEGIN
    v_AccessLevels := userlevel_to_accesslevels(p_UserLevel);
    -- RAISE NOTICE '    AccessLevels: %', v_AccessLevels;

    --
    -- Create a temporary table of AD_Task_Access as we would like to have them
    DROP TABLE IF EXISTS TMP_AD_Task_Access;
    CREATE TEMPORARY TABLE TMP_AD_Task_Access AS
    SELECT DISTINCT '-'::char(1)                                AS row_action -- to be updated later
                  , task.AD_Task_ID                             AS AD_Task_ID
                  , p_AD_Role_ID                                AS AD_Role_ID
                  , p_AD_Client_ID                              AS AD_Client_ID
                  , p_AD_Org_ID                                 AS AD_Org_ID
                  , 'Y'::char(1)                                AS IsActive
                  , 'Y'::char(1)                                AS IsReadWrite
                  , task.name || ' (' || task.AD_Task_id || ')' AS taskInfo
    FROM AD_Task task
    WHERE (task.AccessLevel = ANY (v_AccessLevels));
    --
    CREATE UNIQUE INDEX ON TMP_AD_Task_Access (AD_Role_ID, AD_Task_ID);

    --
    -- Determine those access records that does not currently exist and we have to add them
    UPDATE TMP_AD_Task_Access t
    SET row_action='I'
    WHERE NOT EXISTS (SELECT 1 FROM AD_Task_Access a WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Task_ID = t.AD_Task_ID);

    --
    -- Determine those access records that does exist but they are different from how we expect them to be
    UPDATE TMP_AD_Task_Access t
    SET row_action=(CASE
                        WHEN
                            a.AD_Client_ID IS DISTINCT FROM t.ad_client_id
                                OR a.AD_Org_ID IS DISTINCT FROM t.ad_org_id
                                OR a.IsActive IS DISTINCT FROM t.IsActive
                                OR a.IsReadWrite IS DISTINCT FROM t.IsReadWrite
                            THEN 'U' -- have changes
                            ELSE '-' -- no change
                    END
        )
    FROM AD_Task_Access a
    WHERE a.AD_Role_ID = p_AD_Role_ID
      AND a.AD_Task_ID = t.AD_Task_ID;

    --
    -- Determine those access records that currently exist but they shall be removed
    INSERT INTO TMP_AD_Task_Access (row_action, AD_Task_ID, AD_Role_ID, AD_Client_ID, AD_Org_ID, IsActive, IsReadWrite, taskInfo)
    SELECT 'D'::char(1)                                            AS row_action
         , a.AD_Task_ID
         , a.AD_Role_ID
         , a.AD_Client_ID
         , a.AD_Org_ID
         , a.isactive
         , a.IsReadWrite
         , COALESCE(task.name, '?') || ' (' || a.AD_Task_ID || ')' AS taskInfo
    FROM AD_Task_Access a
             LEFT OUTER JOIN AD_Task task ON task.AD_Task_ID = a.AD_Task_ID
    WHERE a.ad_role_id = p_AD_Role_ID
      AND NOT EXISTS (SELECT 1 FROM TMP_AD_Task_Access t WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Task_ID = t.AD_Task_ID);

    --
    -- Cleanup TMP_AD_Task_Access: remove those records on which we don't have changes (i.e. we will keep them as they are)
    DELETE FROM TMP_AD_Task_Access WHERE row_action = '-';

    --
    --
    -- PERFORM CHANGES
    --
    --

    --
    -- Insert new records
    INSERT INTO AD_Task_Access(AD_Task_ID, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, isreadwrite)
    SELECT t.AD_Task_ID,
           t.AD_Role_ID,
           t.AD_Client_ID,
           t.AD_Org_ID,
           t.IsActive,
           NOW()       AS Created,
           p_CreatedBy AS CreatedBy,
           NOW()       AS Updated,
           p_CreatedBy AS UpdatedBy,
           t.IsReadWrite
    FROM TMP_AD_Task_Access t
    WHERE t.row_action = 'I';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.taskInfo, ', ' ORDER BY t.AD_Task_ID) INTO v_info FROM TMP_AD_Task_Access t WHERE t.row_action = 'I';
        RAISE NOTICE 'Granted access to % task(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Update existing records
    UPDATE AD_Task_Access a
    SET isactive=t.IsActive, updated=NOW(), updatedby=p_CreatedBy, isreadwrite=t.IsReadWrite
    FROM TMP_AD_Task_Access t
    WHERE t.AD_Role_ID = a.ad_role_id
      AND t.AD_Task_ID = a.AD_Task_ID
      AND t.row_action = 'U';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.taskInfo, ', ' ORDER BY t.AD_Task_ID) INTO v_info FROM TMP_AD_Task_Access t WHERE t.row_action = 'U';
        RAISE NOTICE 'Updated access to % task(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Remote records
    DELETE
    FROM AD_Task_Access a
    WHERE EXISTS (SELECT 1 FROM TMP_AD_Task_Access t WHERE t.AD_Role_ID = a.ad_role_id AND t.AD_Task_ID = a.AD_Task_ID AND t.row_action = 'D');
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.taskInfo, ', ' ORDER BY t.AD_Task_ID) INTO v_info FROM TMP_AD_Task_Access t WHERE t.row_action = 'D';
        RAISE NOTICE 'Revoked access to % task(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    IF (NOT v_changes) THEN
        RAISE NOTICE 'No task access changes performed (all are up2date)';
    END IF;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
;



-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS role_access_update_workflows(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_UserLevel    varchar,
    p_CreatedBy    numeric
)
;

CREATE OR REPLACE FUNCTION role_access_update_workflows(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_UserLevel    varchar,
    p_CreatedBy    numeric = 0 -- System
)
    RETURNS void
AS
$BODY$
DECLARE
    v_AccessLevels varchar[];
    v_count        integer;
    v_info         text;
    v_changes      BOOLEAN := FALSE;
BEGIN
    v_AccessLevels := userlevel_to_accesslevels(p_UserLevel);

    --
    -- Create a temporary table of AD_Workflow_Access as we would like to have them
    DROP TABLE IF EXISTS TMP_AD_Workflow_Access;
    CREATE TEMPORARY TABLE TMP_AD_Workflow_Access AS
    SELECT DISTINCT '-'::char(1)                                AS row_action -- to be updated later
                  , wf.AD_Workflow_ID                           AS AD_Workflow_ID
                  , p_AD_Role_ID                                AS AD_Role_ID
                  , p_AD_Client_ID                              AS AD_Client_ID
                  , p_AD_Org_ID                                 AS AD_Org_ID
                  , 'Y'::char(1)                                AS IsActive
                  , 'Y'::char(1)                                AS IsReadWrite
                  , wf.name || ' (' || wf.AD_Workflow_ID || ')' AS workflowInfo
    FROM AD_Workflow wf
    WHERE (wf.AccessLevel = ANY (v_AccessLevels));
    --
    CREATE UNIQUE INDEX ON TMP_AD_Workflow_Access (AD_Role_ID, AD_Workflow_ID);

    --
    -- Determine those access records that does not currently exist and we have to add them
    UPDATE TMP_AD_Workflow_Access t
    SET row_action='I'
    WHERE NOT EXISTS (SELECT 1 FROM AD_Workflow_access a WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Workflow_ID = t.AD_Workflow_ID);

    --
    -- Determine those access records that does exist but they are different from how we expect them to be
    UPDATE TMP_AD_Workflow_Access t
    SET row_action=(CASE
                        WHEN
                            a.AD_Client_ID IS DISTINCT FROM t.ad_client_id
                                OR a.AD_Org_ID IS DISTINCT FROM t.ad_org_id
                                OR a.IsActive IS DISTINCT FROM t.IsActive
                                OR a.IsReadWrite IS DISTINCT FROM t.IsReadWrite
                            THEN 'U' -- have changes
                            ELSE '-' -- no change
                    END
        )
    FROM AD_Workflow_access a
    WHERE a.AD_Role_ID = p_AD_Role_ID
      AND a.AD_Workflow_ID = t.AD_Workflow_ID;

    --
    -- Determine those access records that currently exist but they shall be removed
    INSERT INTO TMP_AD_Workflow_Access (row_action, AD_Workflow_ID, AD_Role_ID, AD_Client_ID, AD_Org_ID, IsActive, IsReadWrite, workflowInfo)
    SELECT 'D'::char(1)                                              AS row_action
         , a.AD_Workflow_ID
         , a.AD_Role_ID
         , a.AD_Client_ID
         , a.AD_Org_ID
         , a.isactive
         , a.IsReadWrite
         , COALESCE(wf.name, '?') || ' (' || a.AD_Workflow_ID || ')' AS workflowInfo
    FROM AD_Workflow_access a
             LEFT OUTER JOIN AD_Workflow wf ON wf.AD_Workflow_ID = a.AD_Workflow_ID
    WHERE a.ad_role_id = p_AD_Role_ID
      AND NOT EXISTS (SELECT 1 FROM TMP_AD_Workflow_Access t WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Workflow_ID = t.AD_Workflow_ID);

    --
    -- Cleanup TMP_AD_Workflow_Access: remove those records on which we don't have changes (i.e. we will keep them as they are)
    DELETE FROM TMP_AD_Workflow_Access WHERE row_action = '-';

    --
    --
    -- PERFORM CHANGES
    --
    --

    --
    -- Insert new records
    INSERT INTO AD_Workflow_access(AD_Workflow_ID, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, isreadwrite)
    SELECT t.AD_Workflow_ID,
           t.AD_Role_ID,
           t.AD_Client_ID,
           t.AD_Org_ID,
           t.IsActive,
           NOW()       AS Created,
           p_CreatedBy AS CreatedBy,
           NOW()       AS Updated,
           p_CreatedBy AS UpdatedBy,
           t.IsReadWrite
    FROM TMP_AD_Workflow_Access t
    WHERE t.row_action = 'I';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.workflowInfo, ', ' ORDER BY t.AD_Workflow_ID) INTO v_info FROM TMP_AD_Workflow_Access t WHERE t.row_action = 'I';
        RAISE NOTICE 'Granted access to % workflow(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Update existing records
    UPDATE AD_Workflow_access a
    SET isactive=t.IsActive, updated=NOW(), updatedby=p_CreatedBy, isreadwrite=t.IsReadWrite
    FROM TMP_AD_Workflow_Access t
    WHERE t.AD_Role_ID = a.ad_role_id
      AND t.AD_Workflow_ID = a.AD_Workflow_ID
      AND t.row_action = 'U';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.workflowInfo, ', ' ORDER BY t.AD_Workflow_ID) INTO v_info FROM TMP_AD_Workflow_Access t WHERE t.row_action = 'U';
        RAISE NOTICE 'Updated access to % workflow(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Remote records
    DELETE
    FROM AD_Workflow_access a
    WHERE EXISTS (SELECT 1 FROM TMP_AD_Workflow_Access t WHERE t.AD_Role_ID = a.ad_role_id AND t.AD_Workflow_ID = a.AD_Workflow_ID AND t.row_action = 'D');
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.workflowInfo, ', ' ORDER BY t.AD_Workflow_ID) INTO v_info FROM TMP_AD_Workflow_Access t WHERE t.row_action = 'D';
        RAISE NOTICE 'Revoked access to % workflow(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    IF (NOT v_changes) THEN
        RAISE NOTICE 'No workflow access changes performed (all are up2date)';
    END IF;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
;


-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS role_access_update_docActions(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_CreatedBy    numeric
)
;

CREATE OR REPLACE FUNCTION role_access_update_docActions(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_CreatedBy    numeric = 0 -- System
)
    RETURNS void
AS
$BODY$
DECLARE
    v_count        integer;
    v_info         text;
    v_changes      BOOLEAN := FALSE;
BEGIN
    --
    -- Create a temporary table of AD_Document_Action_Access as we would like to have them
    DROP TABLE IF EXISTS TMP_AD_Document_Action_Access;
    CREATE TEMPORARY TABLE TMP_AD_Document_Action_Access AS
    SELECT DISTINCT '-'::char(1)                                                                  AS row_action -- to be updated later
                  , doctype.C_DocType_ID                                                          AS C_DocType_ID
                  , docAction.AD_Ref_List_ID                                                      AS AD_Ref_List_ID
                  , p_AD_Role_ID                                                                  AS AD_Role_ID
                  , p_AD_Client_ID                                                                AS AD_Client_ID
                  , p_AD_Org_ID                                                                   AS AD_Org_ID
                  , 'Y'::char(1)                                                                  AS IsActive
                  , doctype.name || '/' || docAction.value || ' (' || doctype.C_DocType_ID || ')' AS docActionInfo
    FROM C_DocType doctype
             INNER JOIN AD_Ref_List docAction ON (docAction.AD_Reference_ID = 135)
    WHERE doctype.AD_Client_ID = p_AD_Client_ID;
    --
    CREATE UNIQUE INDEX ON TMP_AD_Document_Action_Access (AD_Role_ID, C_DocType_ID, AD_Ref_List_ID);

    --
    -- Determine those access records that does not currently exist and we have to add them
    UPDATE TMP_AD_Document_Action_Access t
    SET row_action='I'
    WHERE NOT EXISTS (SELECT 1 FROM AD_Document_Action_Access a WHERE a.AD_Role_ID = p_AD_Role_ID AND a.C_DocType_ID = t.C_DocType_ID AND a.AD_Ref_List_ID = t.AD_Ref_List_ID);

    --
    -- Determine those access records that does exist but they are different from how we expect them to be
    UPDATE TMP_AD_Document_Action_Access t
    SET row_action=(CASE
                        WHEN
                            a.AD_Client_ID IS DISTINCT FROM t.ad_client_id
                                OR a.AD_Org_ID IS DISTINCT FROM t.ad_org_id
                                OR a.IsActive IS DISTINCT FROM t.IsActive
                            THEN 'U' -- have changes
                            ELSE '-' -- no change
                    END
        )
    FROM AD_Document_Action_Access a
    WHERE a.AD_Role_ID = p_AD_Role_ID
      AND a.C_DocType_ID = t.C_DocType_ID
      AND a.AD_Ref_List_ID = t.AD_Ref_List_ID;

    --
    -- Determine those access records that currently exist but they shall be removed
    INSERT INTO TMP_AD_Document_Action_Access (row_action, C_DocType_ID, AD_Ref_List_ID, AD_Role_ID, AD_Client_ID, AD_Org_ID, IsActive, docActionInfo)
    SELECT 'D'::char(1)                                                                                          AS row_action
         , a.C_DocType_ID
         , a.AD_Ref_List_ID
         , a.AD_Role_ID
         , a.AD_Client_ID
         , a.AD_Org_ID
         , a.isactive
         , COALESCE(doctype.name, '?') || '/' || COALESCE(docAction.Value, '?') || ' (' || a.C_DocType_ID || ')' AS docActionInfo
    FROM AD_Document_Action_Access a
             LEFT OUTER JOIN C_DocType doctype ON doctype.C_DocType_ID = a.C_DocType_ID
             LEFT OUTER JOIN AD_Ref_List docAction ON docAction.AD_Ref_List_ID = a.AD_Ref_List_ID
    WHERE a.ad_role_id = p_AD_Role_ID
      AND NOT EXISTS (SELECT 1 FROM TMP_AD_Document_Action_Access t WHERE a.AD_Role_ID = p_AD_Role_ID AND a.AD_Ref_List_ID = t.AD_Ref_List_ID);

    --
    -- Cleanup TMP_AD_Document_Action_Access: remove those records on which we don't have changes (i.e. we will keep them as they are)
    DELETE FROM TMP_AD_Document_Action_Access WHERE row_action = '-';

    --
    --
    -- PERFORM CHANGES
    --
    --

    --
    -- Insert new records
    INSERT INTO AD_Document_Action_Access(C_DocType_ID, AD_Ref_List_ID, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby)
    SELECT t.C_DocType_ID,
           t.AD_Ref_List_ID,
           t.AD_Role_ID,
           t.AD_Client_ID,
           t.AD_Org_ID,
           t.IsActive,
           NOW()       AS Created,
           p_CreatedBy AS CreatedBy,
           NOW()       AS Updated,
           p_CreatedBy AS UpdatedBy
    FROM TMP_AD_Document_Action_Access t
    WHERE t.row_action = 'I';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.docActionInfo, ', ' ORDER BY t.C_DocType_ID, t.AD_Ref_List_ID) INTO v_info FROM TMP_AD_Document_Action_Access t WHERE t.row_action = 'I';
        RAISE NOTICE 'Granted access to % document action(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Update existing records
    UPDATE AD_Document_Action_Access a
    SET isactive=t.IsActive, updated=NOW(), updatedby=p_CreatedBy
    FROM TMP_AD_Document_Action_Access t
    WHERE t.AD_Role_ID = a.ad_role_id
      AND t.C_DocType_ID = a.C_DocType_ID
      AND t.AD_Ref_List_ID = a.AD_Ref_List_ID
      AND t.row_action = 'U';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.docActionInfo, ', ' ORDER BY t.C_DocType_ID, t.AD_Ref_List_ID) INTO v_info FROM TMP_AD_Document_Action_Access t WHERE t.row_action = 'U';
        RAISE NOTICE 'Updated access to % document action(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Remote records
    DELETE
    FROM AD_Document_Action_Access a
    WHERE EXISTS (SELECT 1 FROM TMP_AD_Document_Action_Access t WHERE t.AD_Role_ID = a.ad_role_id AND t.C_DocType_ID = a.C_DocType_ID AND t.AD_Ref_List_ID = a.AD_Ref_List_ID AND t.row_action = 'D');
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.docActionInfo, ', ' ORDER BY t.C_DocType_ID, t.AD_Ref_List_ID) INTO v_info FROM TMP_AD_Document_Action_Access t WHERE t.row_action = 'D';
        RAISE NOTICE 'Revoked access to % document action(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    IF (NOT v_changes) THEN
        RAISE NOTICE 'No document action access changes performed (all are up2date)';
    END IF;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
;


-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS role_access_update(numeric,
                                           numeric)
;

CREATE OR REPLACE FUNCTION role_access_update(
    p_AD_Role_ID numeric = NULL
, p_CreatedBy    numeric = 0 -- System
)
    RETURNS void
AS
$BODY$
    /*
     * NOTE to developer:
     * * don't change parameter names because they are used on function calls (e.g. org.adempiere.ad.security.impl.UserRolePermissionsDAO.updateAccessRecords(I_AD_Role))
     * * when adding new parameters please set a default value for them
     */
DECLARE
    createdByEffective numeric;
    roleUpdated        BOOLEAN := FALSE; -- will be set to true if at least one role was updated
    r                  RECORD;
BEGIN
    FOR r IN (SELECT AD_Role_ID,
                     UserLevel,
                     Name,
                     AD_Client_ID,
                     AD_Org_ID,
                     IsManual,
                     IsActive,
                     UpdatedBy
              FROM AD_Role
              WHERE (p_AD_Role_ID IS NULL OR AD_Role_ID = p_AD_Role_ID)
              ORDER BY IsActive      -- not active first
                     , IsManual DESC -- manual first
                     , AD_Role_ID)
        LOOP
            IF (r.IsActive = 'N') THEN
                RAISE NOTICE 'Skip role % (AD_Role_ID=%) because IsActive=N', r.Name, r.AD_Role_ID;
                CONTINUE;
            END IF;
            IF (r.IsManual = 'Y') THEN
                RAISE NOTICE 'Skip role % (AD_Role_ID=%) because IsManual=Y', r.Name, r.AD_Role_ID;
                CONTINUE;
            END IF;

            --
            --
            --

            RAISE NOTICE '------------------------------------------------------------------------------------';
            RAISE NOTICE 'Updating role % (AD_Role_ID=%, UserLevel=%, AD_Client_ID=%, AD_Org_ID=%)', r.Name, r.AD_Role_ID, r.UserLevel, r.AD_Client_ID, r.AD_Org_ID;

            --
            -- Determine the effective CreatedBy/UpdatedBy to be used
            IF (p_CreatedBy IS NOT NULL) THEN
                createdByEffective := p_CreatedBy;
                RAISE NOTICE '    Using: CreatedBy(effective)=% (provided as parameter)', createdByEffective;
            ELSE
                createdByEffective := r.UpdatedBy;
                RAISE NOTICE '    Using: CreatedBy(effective)=% (from role)', createdByEffective;
            END IF;

            PERFORM role_access_update_windows(
                    p_AD_Role_ID := r.AD_Role_ID,
                    p_AD_Client_ID := r.AD_Client_ID,
                    p_AD_Org_ID := r.AD_Org_ID,
                    p_UserLevel := r.UserLevel,
                    p_CreatedBy := createdByEffective
                    );
            PERFORM role_access_update_processes(
                    p_AD_Role_ID := r.AD_Role_ID,
                    p_AD_Client_ID := r.AD_Client_ID,
                    p_AD_Org_ID := r.AD_Org_ID,
                    p_UserLevel := r.UserLevel,
                    p_CreatedBy := createdByEffective
                    );
            PERFORM role_access_update_forms(
                    p_AD_Role_ID := r.AD_Role_ID,
                    p_AD_Client_ID := r.AD_Client_ID,
                    p_AD_Org_ID := r.AD_Org_ID,
                    p_UserLevel := r.UserLevel,
                    p_CreatedBy := createdByEffective
                    );
            PERFORM role_access_update_workflows(
                    p_AD_Role_ID := r.AD_Role_ID,
                    p_AD_Client_ID := r.AD_Client_ID,
                    p_AD_Org_ID := r.AD_Org_ID,
                    p_UserLevel := r.UserLevel,
                    p_CreatedBy := createdByEffective
                    );
            PERFORM role_access_update_tasks(
                    p_AD_Role_ID := r.AD_Role_ID,
                    p_AD_Client_ID := r.AD_Client_ID,
                    p_AD_Org_ID := r.AD_Org_ID,
                    p_UserLevel := r.UserLevel,
                    p_CreatedBy := createdByEffective
                    );
            PERFORM role_access_update_docActions(
                    p_AD_Role_ID := r.AD_Role_ID,
                    p_AD_Client_ID := r.AD_Client_ID,
                    p_AD_Org_ID := r.AD_Org_ID,
                    p_CreatedBy := createdByEffective
                    );

            --
            roleUpdated := TRUE;

            RAISE NOTICE '------------------------------------------------------------------------------------';
        END LOOP;

    --
    -- If we were asked to explicitly update a given role and nothing was actually updated,
    -- then throw exception because it seems the role could not be found.
    IF (p_AD_Role_ID IS NOT NULL AND NOT roleUpdated) THEN
        RAISE EXCEPTION 'Role AD_Role_ID=% was not found or it''s not active or it''s a manual role', p_AD_Role_ID;
    END IF;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
;



