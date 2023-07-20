DROP FUNCTION IF EXISTS role_access_add_menu_recursively
(
    p_AD_Role_ID  numeric,
    p_AD_Menu_ID  numeric,
    p_IsReadWrite char
)
;

CREATE OR REPLACE FUNCTION role_access_add_menu_recursively(
    p_AD_Role_ID  numeric,
    p_AD_Menu_ID  numeric,
    p_IsReadWrite char
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
    RAISE NOTICE 'role_access_add_menu_recursively: AD_Role_ID=%, AD_Menu_ID=%, IsReadWrite=%', p_AD_Role_ID, p_AD_Menu_ID, p_IsReadWrite;

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
    -- Select eligible menu entries
    DROP TABLE IF EXISTS tmp_menu;
    CREATE TEMPORARY TABLE tmp_menu AS
    WITH RECURSIVE menu_tree AS ((SELECT tn.Node_ID
                                       , COALESCE(m.Name::text, tn.Node_ID::text) AS Name
                                       , ARRAY [tn.Node_ID::integer]              AS path
                                       , FALSE                                    AS IsCycle
                                  FROM AD_TreeNodeMM tn
                                           LEFT OUTER JOIN ad_menu m ON m.ad_menu_id = tn.node_id
                                  WHERE TRUE
                                    AND tn.AD_Tree_ID = 10
                                    AND tn.node_id = p_AD_Menu_ID)
                                 --
                                 UNION ALL
                                 --
                                 SELECT tn.Node_ID
                                      , parent.Name || ' -> ' || COALESCE(m.Name, tn.Node_ID::text) AS Name
                                      , parent.path || tn.Node_ID::integer                          AS path
                                      , (tn.Node_ID = ANY (parent.path))                            AS IsCycle
                                 FROM menu_tree parent
                                          INNER JOIN AD_TreeNodeMM tn ON (tn.Parent_ID = parent.Node_ID)
                                          LEFT OUTER JOIN ad_menu m ON m.ad_menu_id = tn.node_id
                                 WHERE NOT parent.IsCycle)
    SELECT *
    FROM (SELECT menu_tree.*,
                 m.action,
                 (CASE WHEN m.action = 'W' THEN m.ad_window_id END)          AS AD_Window_ID,
                 (CASE WHEN m.action IN ('P', 'R') THEN m.ad_process_id END) AS AD_Process_ID
          FROM menu_tree
                   LEFT OUTER JOIN ad_menu m ON m.ad_menu_id = menu_tree.node_id
          WHERE m.issummary = 'N') t
    WHERE (t.AD_Window_ID IS NOT NULL OR t.AD_Process_ID IS NOT NULL);
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'role_access_add_menu_recursively: Selected % eligible AD_Menu entries', v_rowcount;


    --
    -- Windows
    PERFORM role_access_add_window_recursively(
            p_ad_role_id := role_access_add_menu_recursively.p_ad_role_id,
            p_ad_window_ids := (SELECT ARRAY_AGG(DISTINCT AD_Window_ID) FROM tmp_menu WHERE AD_Window_ID IS NOT NULL),
            p_IsReadWrite := role_access_add_menu_recursively.p_IsReadWrite
        );

    --
    -- Processes
    PERFORM role_access_add_process_recursively(
            p_ad_role_id := role_access_add_menu_recursively.p_ad_role_id,
            p_AD_Process_IDs := (SELECT ARRAY_AGG(DISTINCT AD_Process_ID) FROM tmp_menu WHERE AD_Process_ID IS NOT NULL),
            p_ScopeInfo := 'Menu processes',
            p_IsReadWrite := role_access_add_menu_recursively.p_IsReadWrite
        );

    --

END;
$BODY$
;
