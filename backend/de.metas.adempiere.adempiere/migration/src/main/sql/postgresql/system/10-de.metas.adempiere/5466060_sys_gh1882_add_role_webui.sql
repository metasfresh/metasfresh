-- 2017-06-23T20:26:57.792
-- URL zum Konzept
INSERT INTO AD_User_Roles (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_User_ID,AD_User_Roles_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) SELECT 1000000,0,540024,2188223,540005,TO_TIMESTAMP('2017-06-23 20:26:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2017-06-23 20:26:57','YYYY-MM-DD HH24:MI:SS'),100
where not exists (select 1 from AD_User_Roles  where ad_user_id=2188223 and ad_role_id=540024);

