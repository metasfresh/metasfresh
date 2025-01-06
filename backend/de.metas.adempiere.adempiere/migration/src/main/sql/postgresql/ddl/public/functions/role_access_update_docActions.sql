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

