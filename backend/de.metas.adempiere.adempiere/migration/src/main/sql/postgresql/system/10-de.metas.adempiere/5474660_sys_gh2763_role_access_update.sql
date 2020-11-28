DROP FUNCTION IF EXISTS role_access_update(numeric, numeric);

CREATE OR REPLACE FUNCTION role_access_update
(
	p_AD_Role_ID numeric = null
	, p_CreatedBy numeric = 0 -- System
)
RETURNS void AS
$BODY$
/*
 * NOTE to developer:
 * * don't change parameter names because they are used on function calls (e.g. org.adempiere.ad.security.impl.UserRolePermissionsDAO.updateAccessRecords(I_AD_Role))
 * * when adding new parameters please set a default value for them
 */
DECLARE
	createdByEffective		numeric;
	sqlRoleAccessLevelArr	VARCHAR[];
	excludeWindowsForAll	BOOLEAN;
	--
	sqlDeleteCount			INTEGER;
	sqlInsertCount			INTEGER;
	roleUpdated				BOOLEAN := false; -- will be set to true if at least one role was updated
	--
	r						RECORD;
BEGIN
	FOR r IN (SELECT AD_Role_ID, UserLevel, Name, AD_Client_ID, AD_Org_ID, UpdatedBy
				FROM AD_Role
				WHERE
					IsManual = 'N'
					AND IsActive='Y'
					AND (p_AD_Role_ID is null or AD_Role_ID=p_AD_Role_ID)
				ORDER BY AD_Role_ID)
	LOOP
		raise notice 'Updating role % (AD_Role_ID=%, UserLevel=%, AD_Client_ID=%, AD_Org_ID=%)', r.Name, r.AD_Role_ID, r.UserLevel, r.AD_Client_ID, r.AD_Org_ID;
		
		--
		-- Determine the effective CreatedBy/UpdatedBy to be used
		if(p_CreatedBy is not null) then
			createdByEffective := p_CreatedBy;
			raise notice '    Using: CreatedBy(effective)=% (provided as parameter)', createdByEffective;
		else
			createdByEffective := r.UpdatedBy;
			raise notice '    Using: CreatedBy(effective)=% (from role)', createdByEffective;
		end if;
		
		--
		-- Determine AccessLevel to be enforced
		IF r.UserLevel = 'S__'                                        -- system
		THEN
			sqlRoleAccessLevelArr := array['4', '7', '6'];
			excludeWindowsForAll := false;
		ELSIF r.UserLevel = '_C_'                                      -- client
		THEN
			sqlRoleAccessLevelArr := array['7', '6', '3', '2'];
			excludeWindowsForAll := false;
		ELSIF r.UserLevel = '_CO'                                -- client + org
		THEN
			sqlRoleAccessLevelArr := array['7', '6', '3', '2', '1'];
			excludeWindowsForAll := false;
		ELSE                                                    -- org or others
			sqlRoleAccessLevelArr := array['3', '1', '7'];
			excludeWindowsForAll := true;
		END IF;
		--
		raise notice '    Using: AccessLevel=%, excludeWindowsForAll=%', sqlRoleAccessLevelArr, excludeWindowsForAll;
		
		--
		-- AD_Window_Access
		DELETE FROM AD_Window_Access WHERE AD_Role_ID = r.AD_Role_ID;
		GET DIAGNOSTICS sqlDeleteCount = ROW_COUNT;
		--
		INSERT INTO AD_Window_Access (AD_Window_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite)
			SELECT DISTINCT w.AD_Window_ID, r.AD_Role_ID
				, r.AD_Client_ID, r.AD_Org_ID
				, 'Y', now(), createdByEffective, now(), createdByEffective
				, 'Y' as IsReadWrite
			FROM AD_Window w
			LEFT OUTER JOIN AD_Tab t ON (w.AD_Window_ID=t.AD_Window_ID AND t.SeqNo=(SELECT MIN(SeqNo) FROM AD_Tab xt WHERE xt.AD_Window_ID=w.AD_Window_ID AND xt.IsActive='Y'))
			LEFT OUTER JOIN AD_Table tt ON (t.AD_Table_ID=tt.AD_Table_ID)
			WHERE
				(t.AD_Tab_ID IS NULL OR tt.AccessLevel = any(sqlRoleAccessLevelArr))
				AND ((NOT excludeWindowsForAll) OR w.Name NOT LIKE '%(all)%');
		--
		GET DIAGNOSTICS sqlInsertCount = ROW_COUNT;
		raise notice '    Updated AD_Window_Access: % rows inserted, % rows deleted', sqlInsertCount, sqlDeleteCount;

		--
		-- AD_Process_Access
		DELETE FROM AD_Process_Access WHERE AD_Role_ID = r.AD_Role_ID;
		GET DIAGNOSTICS sqlDeleteCount = ROW_COUNT;
		--
		INSERT INTO AD_Process_Access (AD_Process_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite)
			SELECT DISTINCT p.AD_Process_ID, r.AD_Role_ID
				, r.AD_Client_ID, r.AD_Org_ID
				, 'Y', now(), createdByEffective, now(), createdByEffective
				, 'Y' as IsReadWrite
			FROM AD_Process p
			WHERE AccessLevel = any(sqlRoleAccessLevelArr);
		--
		GET DIAGNOSTICS sqlInsertCount = ROW_COUNT;
		raise notice '    Updated AD_Process_Access: % rows inserted, % rows deleted', sqlInsertCount, sqlDeleteCount;

		--
		-- AD_Form_Access
		DELETE FROM AD_Form_Access WHERE AD_Role_ID = r.AD_Role_ID;
		GET DIAGNOSTICS sqlDeleteCount = ROW_COUNT;
		--
		INSERT INTO AD_Form_Access (AD_Form_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite)
			SELECT f.AD_Form_ID, r.AD_Role_ID
				, r.AD_Client_ID, r.AD_Org_ID
				, 'Y', now(), createdByEffective, now(), createdByEffective
				, 'Y' as IsReadWrite
			FROM AD_Form f
			WHERE AccessLevel = any(sqlRoleAccessLevelArr);
		--
		GET DIAGNOSTICS sqlInsertCount = ROW_COUNT;
		raise notice '    Updated AD_Form_Access: % rows inserted, % rows deleted', sqlInsertCount, sqlDeleteCount;

		--
		-- AD_Workflow_Access
		DELETE FROM AD_Workflow_Access WHERE AD_Role_ID = r.AD_Role_ID;
		GET DIAGNOSTICS sqlDeleteCount = ROW_COUNT;
		--
		INSERT INTO AD_Workflow_Access (AD_WorkFlow_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite)
			SELECT w.AD_WorkFlow_ID, r.AD_Role_ID
				, r.AD_Client_ID, r.AD_Org_ID
				, 'Y', now(), createdByEffective, now(), createdByEffective
				, 'Y' as IsReadWrite
			FROM AD_WorkFlow w
			WHERE AccessLevel = any(sqlRoleAccessLevelArr);
		--
		GET DIAGNOSTICS sqlInsertCount = ROW_COUNT;
		raise notice '    Updated AD_Workflow_Access: % rows inserted, % rows deleted', sqlInsertCount, sqlDeleteCount;
		
		--
		-- AD_Task_Access
		DELETE FROM AD_Task_Access WHERE AD_Role_ID = r.AD_Role_ID;
		GET DIAGNOSTICS sqlDeleteCount = ROW_COUNT;
		--
		INSERT INTO AD_Task_Access (AD_Task_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite)
			SELECT t.AD_Task_ID, r.AD_Role_ID
				, r.AD_Client_ID, r.AD_Org_ID
				, 'Y', now(), createdByEffective, now(), createdByEffective
				, 'Y' as IsReadWrite
			FROM AD_Task t
			WHERE t.AccessLevel = any(sqlRoleAccessLevelArr);
		--
		GET DIAGNOSTICS sqlInsertCount = ROW_COUNT;
		raise notice '    Updated AD_Task_Access: % rows inserted, % rows deleted', sqlInsertCount, sqlDeleteCount;
		
		--
		-- AD_Document_Action_Access
		DELETE FROM AD_Document_Action_Access WHERE AD_Role_ID = r.AD_Role_ID;
		GET DIAGNOSTICS sqlDeleteCount = ROW_COUNT;
		--
		INSERT INTO AD_Document_Action_Access (
			C_DocType_ID, AD_Ref_List_ID, AD_Role_ID
			, AD_Client_ID,AD_Org_ID
			, IsActive,Created,CreatedBy,Updated,UpdatedBy)
			SELECT
				doctype.C_DocType_ID, action.AD_Ref_List_ID, r.AD_Role_ID
				, r.AD_Client_ID, r.AD_Org_ID
				, 'Y', now(), createdByEffective, now(), createdByEffective
			FROM C_DocType doctype
			INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135)
			WHERE
				doctype.AD_Client_ID=r.AD_Client_ID;
		--
		GET DIAGNOSTICS sqlInsertCount = ROW_COUNT;
		raise notice '    Updated AD_Document_Action_Access: % rows inserted, % rows deleted', sqlInsertCount, sqlDeleteCount;
		
		--
		roleUpdated := true;
	END LOOP;

	--
	-- If we were asked to explicitelly update a given role and nothing was actually updated,
	-- then throw exception because it seems the role could not be found.
	if(p_AD_Role_ID is not null AND not roleUpdated) then
		raise exception 'Role AD_Role_ID=% was not found or it''s not active or it''s a manual role', p_AD_Role_ID;
	end if;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

