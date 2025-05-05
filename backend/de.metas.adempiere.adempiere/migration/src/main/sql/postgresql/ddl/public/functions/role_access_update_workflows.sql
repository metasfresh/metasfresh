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

