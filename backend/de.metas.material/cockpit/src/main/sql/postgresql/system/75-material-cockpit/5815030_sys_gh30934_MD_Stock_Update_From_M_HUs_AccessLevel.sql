-- gh#30934: make MD_Stock_Update_From_M_HUs runnable from the WebUI (client+org) role.
--
-- The material-cockpit "correct stock with HU data" process (AD_Process_ID=540907) was AccessLevel='4'
-- (System only), so the WebUI role (UserLevel Client+Org) is denied by ProcessExecutor.assertPermissions.
-- Change it to '7' (All = System+Client+Org): WebUI (UserLevel _CO) can now run it, AND System access
-- is preserved so the scheduler AD_Scheduler_ID=550047 that runs this process keeps working.
-- The per-role AD_Process_Access rows are seeded automatically by role_access_update(), which
-- after_migration.sql runs on app start / migration-tool apply — no explicit grant here (and none
-- wanted, to avoid double entries).
UPDATE AD_Process
SET AccessLevel = '7',
    Updated = now(),
    UpdatedBy = 100
WHERE AD_Process_ID = 540907
  AND AccessLevel = '4'
;

