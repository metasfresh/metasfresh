DROP FUNCTION IF EXISTS role_access_add_process_recursively
(
    p_AD_Role_ID     numeric,
    p_AD_Process_IDs numeric[],
    p_ScopeInfo      text,
    p_IsReadWrite    char
)
;

CREATE OR REPLACE FUNCTION role_access_add_process_recursively(
    p_AD_Role_ID     numeric,
    p_AD_Process_IDs numeric[],
    p_ScopeInfo      text = '-',
    p_IsReadWrite    char = 'N'
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
    RAISE NOTICE 'role_access_add_process_recursively[%]: AD_Role_ID=%, AD_Process_IDs=%, IsReadWrite=%',p_ScopeInfo, p_AD_Role_ID, p_AD_Process_IDs,p_IsReadWrite;

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
    -- Workflow Access
    WITH workflows AS (SELECT p.ad_workflow_id
                       FROM ad_process p
                       WHERE p.ad_process_id = ANY (p_AD_Process_IDs)
                         AND p.ad_workflow_id IS NOT NULL)
    INSERT
    INTO ad_workflow_access(ad_workflow_id, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, isreadwrite)
    SELECT workflows.ad_workflow_id AS ad_workflow_id,
           p_AD_Role_ID             AS ad_role_id,
           v_roleInfo.ad_client_id  AS ad_client_id,
           0                        AS ad_org_id,
           'Y'                      AS IsActive,
           NOW()                    AS created,
           0                        AS createdby,
           NOW()                    AS updated,
           0                        AS updatedby,
           p_IsReadWrite            AS isreadwrite
    FROM workflows
    WHERE NOT EXISTS (SELECT 1 FROM ad_workflow_access z WHERE z.ad_workflow_id = workflows.ad_workflow_id AND z.ad_role_id = p_AD_Role_ID);
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'role_access_add_process_recursively[%]: Granted access to % workflows', p_ScopeInfo, v_rowcount;

    --
    -- Process Access
    INSERT INTO ad_process_access (ad_process_id, ad_role_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive, isreadwrite)
    SELECT p.ad_process_id         AS AD_Process_ID,
           p_AD_Role_ID            AS AD_Role_ID,
           v_roleInfo.ad_client_Id AS AD_Client_ID,
           0                       AS AD_Org_ID,
           NOW()                   AS Created,
           0                       AS CreatedBy,
           NOW()                   AS Updated,
           0                       AS UpdatedBy,
           'Y'                     AS IsActive,
           p_IsReadWrite           AS IsReadWrite
    FROM ad_process p
    WHERE p.ad_process_id = ANY (p_AD_Process_IDs)
      AND NOT EXISTS (SELECT 1 FROM ad_process_access z WHERE z.ad_process_id = p.ad_process_id AND z.ad_role_id = p_AD_Role_ID);
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'role_access_add_process_recursively[%]: Granted access to % processes', p_ScopeInfo, v_rowcount;
END;
$BODY$
;

