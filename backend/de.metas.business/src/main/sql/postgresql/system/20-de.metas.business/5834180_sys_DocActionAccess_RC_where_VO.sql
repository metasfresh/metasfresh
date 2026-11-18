-- Grant "Reverse-Correct" (RC, AD_Ref_List_ID 184) to every role that holds an active "Void" (VO, 182)
-- grant on a document type of an in-scope document table, if the role has no active RC grant for it.
-- In-scope tables (via C_DocType.DocBaseType): C_Invoice, M_InOut, C_Payment, M_Movement, M_Inventory,
-- PP_Cost_Collector, C_AllocationHdr. Only AD_Document_Action_Access rows are inserted / reactivated;
-- nothing is removed. A backup of the table is taken first.
-- A pair whose RC row (active or not) is stored in a different AD_Client_ID than the VO row is NOT changed: the
-- role filter (login client) would not see that row anyway; it is logged once per pair and skipped.

DO
$$
    DECLARE
        v_inScope  text[] := ARRAY ['API', 'APC', 'APF', 'ARF', 'ARI', 'ARC', 'AEI', 'AVI', -- C_Invoice
            'MMS', 'MMR', -- M_InOut
            'ARR', 'APP', -- C_Payment
            'MMM', -- M_Movement
            'MMI', -- M_Inventory
            'MCC', -- PP_Cost_Collector
            'CMA' -- C_AllocationHdr
            ];
        r          record;
        v_inserted integer := 0;
        v_reactivated integer := 0;
        v_other    text;
        v_skipped  integer := 0;
    BEGIN
        PERFORM backup_table('AD_Document_Action_Access', '_RC_where_VO');

        FOR r IN
            SELECT vo.ad_client_id,
                   vo.ad_role_id,
                   vo.c_doctype_id,
                   TRIM(dt.docbasetype)::text AS docbasetype,
                   rc.ad_document_action_access_id AS rc_id,
                   rc.ad_client_id AS rc_client_id,
                   rc.isactive AS rc_isactive
            FROM ad_document_action_access vo
                     JOIN c_doctype dt ON dt.c_doctype_id = vo.c_doctype_id
                     LEFT JOIN ad_document_action_access rc
                               ON rc.ad_role_id = vo.ad_role_id
                                   AND rc.c_doctype_id = vo.c_doctype_id
                                   AND rc.ad_ref_list_id = 184
            WHERE vo.ad_ref_list_id = 182
              AND vo.isactive = 'Y'
              AND TRIM(dt.docbasetype) = ANY (v_inScope)
              AND (rc.ad_document_action_access_id IS NULL OR rc.isactive <> 'Y' OR rc.ad_client_id <> vo.ad_client_id)
            ORDER BY vo.ad_role_id, vo.c_doctype_id
            LOOP
                IF r.rc_id IS NOT NULL AND r.rc_client_id <> r.ad_client_id THEN
                    v_skipped := v_skipped + 1;
                    RAISE NOTICE 'DocActionAccess RC: skipped cross-client pair AD_Role_ID=% C_DocType_ID=% (DocBaseType %): VO row AD_Client_ID=%, RC row AD_Client_ID=%, RC IsActive=% (not changed)',
                        r.ad_role_id, r.c_doctype_id, r.docbasetype, r.ad_client_id, r.rc_client_id, r.rc_isactive;
                ELSIF r.rc_id IS NULL THEN
                    INSERT INTO ad_document_action_access (ad_client_id, ad_org_id, isactive, created, createdby,
                                                           updated, updatedby, c_doctype_id, ad_ref_list_id,
                                                           ad_role_id)
                    VALUES (r.ad_client_id, 0, 'Y', TO_TIMESTAMP('2026-11-11 10:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
                            TO_TIMESTAMP('2026-11-11 10:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100, r.c_doctype_id, 184,
                            r.ad_role_id);
                    v_inserted := v_inserted + 1;
                    RAISE NOTICE 'DocActionAccess RC: % for AD_Role_ID=% C_DocType_ID=% (DocBaseType %)',
                        'inserted', r.ad_role_id, r.c_doctype_id, r.docbasetype;
                ELSE
                    UPDATE ad_document_action_access
                    SET isactive = 'Y', updated = TO_TIMESTAMP('2026-11-11 10:30:01', 'YYYY-MM-DD HH24:MI:SS'), updatedby = 100
                    WHERE ad_document_action_access_id = r.rc_id;
                    v_reactivated := v_reactivated + 1;
                    RAISE NOTICE 'DocActionAccess RC: % for AD_Role_ID=% C_DocType_ID=% (DocBaseType %)',
                        'reactivated', r.ad_role_id, r.c_doctype_id, r.docbasetype;
                END IF;
            END LOOP;

        RAISE NOTICE 'DocActionAccess RC: % inserted, % reactivated, % skipped (cross-client)', v_inserted, v_reactivated, v_skipped;

        SELECT STRING_AGG(DISTINCT COALESCE(NULLIF(TRIM(dt.docbasetype), ''), '(blank)'), ', ')
        INTO v_other
        FROM ad_document_action_access vo
                 JOIN c_doctype dt ON dt.c_doctype_id = vo.c_doctype_id
        WHERE vo.ad_ref_list_id = 182
          AND vo.isactive = 'Y'
          AND NOT (TRIM(dt.docbasetype) = ANY (v_inScope));
        RAISE NOTICE 'DocActionAccess RC: DocBaseTypes with an active VO grant that are NOT in the in-scope list (untouched): %',
            COALESCE(v_other, 'none');
    END
$$;
