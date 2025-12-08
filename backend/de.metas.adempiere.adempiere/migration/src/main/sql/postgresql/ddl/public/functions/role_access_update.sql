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



