DROP FUNCTION IF EXISTS role_access_update_mobileApplications(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_CreatedBy    numeric
)
;

CREATE OR REPLACE FUNCTION role_access_update_mobileApplications(
    p_AD_Role_ID   numeric,
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric,
    p_CreatedBy    numeric = 0 -- System
)
    RETURNS void
AS
$BODY$
DECLARE
    v_count   integer;
    v_info    text;
    v_changes BOOLEAN := FALSE;
BEGIN
    --
    -- Create a temporary table of Mobile_Application_Access as we would like to have them
    DROP TABLE IF EXISTS TMP_Mobile_Application_Access;
    CREATE TEMPORARY TABLE TMP_Mobile_Application_Access AS
    SELECT DISTINCT '-'::char(1)              AS row_action -- to be updated later
                  , app.Mobile_Application_ID AS Mobile_Application_ID
                  , p_AD_Role_ID              AS AD_Role_ID
                  , p_AD_Client_ID            AS AD_Client_ID
                  , p_AD_Org_ID               AS AD_Org_ID
                  , 'Y'::char(1)              AS IsActive
                  , app.value                 AS appInfo
    FROM Mobile_Application app
    WHERE app.AD_Client_ID IN (0, p_AD_Client_ID);
    --
    CREATE UNIQUE INDEX ON TMP_Mobile_Application_Access (AD_Role_ID, Mobile_Application_ID);

    --
    -- Determine those access records that does not currently exist and we have to add them
    UPDATE TMP_Mobile_Application_Access t
    SET row_action='I'
    WHERE NOT EXISTS (SELECT 1 FROM Mobile_Application_Access a WHERE a.AD_Role_ID = p_AD_Role_ID AND a.Mobile_Application_ID = t.Mobile_Application_ID);

    --
    -- Determine those access records that does exist but they are different from how we expect them to be
    UPDATE TMP_Mobile_Application_Access t
    SET row_action=(CASE
                        WHEN
                            a.AD_Client_ID IS DISTINCT FROM t.ad_client_id
                                OR a.AD_Org_ID IS DISTINCT FROM t.ad_org_id
                                OR a.IsActive IS DISTINCT FROM t.IsActive
                            THEN 'U' -- have changes
                            ELSE '-' -- no change
                    END
        )
    FROM Mobile_Application_Access a
    WHERE a.AD_Role_ID = p_AD_Role_ID
      AND a.Mobile_Application_ID = t.Mobile_Application_ID;

    --
    -- Determine those access records that currently exist but they shall be removed
    INSERT INTO TMP_Mobile_Application_Access (row_action, Mobile_Application_ID, AD_Role_ID, AD_Client_ID, AD_Org_ID, IsActive, appInfo)
    SELECT 'D'::char(1)            AS row_action
         , a.Mobile_Application_Access_ID
         , a.AD_Role_ID
         , a.AD_Client_ID
         , a.AD_Org_ID
         , a.isactive
         , COALESCE(app.name, '?') AS appInfo
    FROM Mobile_Application_Access a
             LEFT OUTER JOIN Mobile_Application app ON app.Mobile_Application_ID = a.Mobile_Application_ID
    WHERE a.ad_role_id = p_AD_Role_ID
      AND NOT EXISTS (SELECT 1 FROM TMP_Mobile_Application_Access t WHERE a.AD_Role_ID = p_AD_Role_ID AND a.Mobile_Application_ID = t.Mobile_Application_ID);

    --
    -- Cleanup TMP_Mobile_Application_Access: remove those records on which we don't have changes (i.e. we will keep them as they are)
    DELETE FROM TMP_Mobile_Application_Access WHERE row_action = '-';

    --
    --
    -- PERFORM CHANGES
    --
    --

    --
    -- Insert new records
    INSERT INTO Mobile_Application_Access(Mobile_Application_ID, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby)
    SELECT t.Mobile_Application_ID,
           t.AD_Role_ID,
           t.AD_Client_ID,
           t.AD_Org_ID,
           t.IsActive,
           NOW()       AS Created,
           p_CreatedBy AS CreatedBy,
           NOW()       AS Updated,
           p_CreatedBy AS UpdatedBy
    FROM TMP_Mobile_Application_Access t
    WHERE t.row_action = 'I';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.appInfo, ', ' ORDER BY t.Mobile_Application_ID) INTO v_info FROM TMP_Mobile_Application_Access t WHERE t.row_action = 'I';
        RAISE NOTICE 'Granted access to % applications(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Update existing records
    UPDATE Mobile_Application_Access a
    SET isactive=t.IsActive, updated=NOW(), updatedby=p_CreatedBy
    FROM TMP_Mobile_Application_Access t
    WHERE t.AD_Role_ID = a.ad_role_id
      AND t.Mobile_Application_ID = a.Mobile_Application_ID
      AND t.row_action = 'U';
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.appInfo, ', ' ORDER BY t.Mobile_Application_ID) INTO v_info FROM TMP_Mobile_Application_Access t WHERE t.row_action = 'U';
        RAISE NOTICE 'Updated access to % applications(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    --
    -- Remote records
    DELETE
    FROM Mobile_Application_Access a
    WHERE EXISTS (SELECT 1 FROM TMP_Mobile_Application_Access t WHERE t.AD_Role_ID = a.ad_role_id AND t.Mobile_Application_ID = a.Mobile_Application_ID AND t.row_action = 'D');
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count > 0) THEN
        SELECT STRING_AGG(t.appInfo, ', ' ORDER BY t.Mobile_Application_ID) INTO v_info FROM TMP_Mobile_Application_Access t WHERE t.row_action = 'D';
        RAISE NOTICE 'Revoked access to % applications(s): %', v_count, v_info;
        v_changes := TRUE;
    END IF;

    IF (NOT v_changes) THEN
        RAISE NOTICE 'No mobile application access changes performed (all are up2date)';
    END IF;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
;

























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
            PERFORM role_access_update_mobileApplications(
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



