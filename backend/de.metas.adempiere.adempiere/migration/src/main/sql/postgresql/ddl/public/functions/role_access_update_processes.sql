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

