DROP FUNCTION IF EXISTS role_access_add_window_recursively
(
    p_AD_Role_ID    numeric,
    p_AD_Window_IDs numeric[]
)
;

CREATE OR REPLACE FUNCTION role_access_add_window_recursively(
    p_AD_Role_ID    numeric,
    p_AD_Window_IDs numeric[]
)
    RETURNS void
    LANGUAGE plpgsql
    VOLATILE
AS
$BODY$
DECLARE
    v_roleInfo record;
    v_rowcount integer;
BEGIN
    RAISE NOTICE 'role_access_add_window_recursively: AD_Role_ID=%, AD_Window_IDs=%', p_AD_Role_ID, p_AD_Window_IDs;

    --
    -- Get role info
    SELECT r.ad_role_id, r.ad_client_id
    INTO v_roleInfo
    FROM ad_role r
    WHERE r.ad_role_id = p_AD_Role_ID;
    IF (v_roleInfo.ad_role_id IS NULL) THEN
        RAISE EXCEPTION 'No Role found for AD_Role_ID=%', p_AD_Role_ID;
    END IF;

    --
    --
    -- Window access
    WITH windows AS (SELECT w.ad_window_id
                     FROM ad_window w
                     WHERE w.ad_window_id = ANY (p_AD_Window_IDs))
    INSERT
    INTO ad_window_access (ad_window_id, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, isreadwrite)
    SELECT windows.ad_window_id    AS AD_Window_ID,
           p_AD_Role_ID            AS AD_Role_ID,
           v_roleInfo.ad_client_id AS AD_Client_ID,
           0                       AS AD_Org_ID,
           'Y'                     AS IsActive,
           NOW()                   AS Created,
           0                       AS CreatedBy,
           NOW()                   AS Updated,
           0                       AS UpdatedBy,
           'Y'                     AS IsReadWrite
    FROM windows
    WHERE NOT EXISTS (SELECT 1 FROM ad_window_access z WHERE z.ad_window_id = windows.ad_window_id AND z.ad_role_id = p_AD_Role_ID);
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'role_access_add_window_recursively: Granted access to % windows', v_rowcount;

    --
    --
    -- Table assigned processes
    PERFORM role_access_add_process_recursively(
            p_AD_Role_ID := v_roleInfo.ad_role_id,
            p_AD_Process_IDs := (SELECT ARRAY_AGG(DISTINCT tp.ad_process_id)
                                 FROM ad_window w
                                          INNER JOIN ad_tab tt ON tt.ad_window_id = w.ad_window_id
                                          INNER JOIN ad_table t ON t.ad_table_id = tt.ad_table_id
                                          INNER JOIN ad_table_process tp ON (
                                             tp.ad_table_id = tt.ad_table_id
                                         AND (tp.ad_window_id IS NULL OR tp.ad_window_id = w.ad_window_id)
                                         AND (tp.ad_tab_id IS NULL OR tp.ad_tab_id = tt.ad_tab_id)
                                     )
                                 WHERE w.ad_window_id = ANY (p_AD_Window_IDs)),
            p_ScopeInfo := 'Table assigned processes'
        );

    --
    --
    -- AD_Tab.AD_Process_ID (i.e. Print reports)
    PERFORM role_access_add_process_recursively(
            p_AD_Role_ID := v_roleInfo.ad_role_id,
            p_AD_Process_IDs := (SELECT ARRAY_AGG(DISTINCT tt.ad_process_id)
                                 FROM ad_window w
                                          INNER JOIN ad_tab tt ON tt.ad_window_id = w.ad_window_id
                                 WHERE w.ad_window_id = ANY (p_AD_Window_IDs)
                                   AND tt.ad_process_id IS NOT NULL),
            p_ScopeInfo := 'Print reports'
        );

    --
    --
    -- AD_Column.AD_Process_ID
    PERFORM role_access_add_process_recursively(
            p_AD_Role_ID := v_roleInfo.ad_role_id,
            p_AD_Process_IDs := (SELECT ARRAY_AGG(DISTINCT c.ad_process_id)
                                 FROM ad_window w
                                          INNER JOIN ad_tab tt ON tt.ad_window_id = w.ad_window_id
                                          INNER JOIN ad_table t ON t.ad_table_id = tt.ad_table_id
                                          INNER JOIN ad_column c ON c.ad_table_id = t.ad_table_id
                                 WHERE w.ad_window_id = ANY (p_AD_Window_IDs)
                                   AND c.ad_process_id IS NOT NULL),
            p_ScopeInfo := 'Column processes'
        );
END;
$BODY$
;



--
--
-- Test
/*
DELETE
FROM ad_window_access
WHERE ad_role_id = 540154
;

DELETE
FROM ad_process_access
WHERE ad_role_id = 540154
;

SELECT role_access_add_window_recursively(
               p_AD_Role_ID := 540154,
               p_AD_Window_IDs := ARRAY [143]
           )
;
 */

