-- 2021-10-13T06:05:52.537Z
-- URL zum Konzept
INSERT INTO AD_UserGroup (AD_Client_ID,AD_Org_ID,AD_UserGroup_ID,Created,CreatedBy,IsActive,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540003,TO_TIMESTAMP('2021-10-13 08:05:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','Partner Created From Another Organization Notify User Group',TO_TIMESTAMP('2021-10-13 08:05:52','YYYY-MM-DD HH24:MI:SS'),100)
;



UPDATE ad_orginfo
SET c_bpartner_createdfromanotherorg_notify_usergroup_id =540003
WHERE c_bpartner_createdfromanotherorg_notify_usergroup_id IS NULL
;