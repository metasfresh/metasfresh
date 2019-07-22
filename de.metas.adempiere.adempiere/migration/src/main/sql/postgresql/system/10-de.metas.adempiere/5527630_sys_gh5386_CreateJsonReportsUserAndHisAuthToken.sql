-- create user Json Reports

-- 2019-07-19T09:29:11.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_User (AD_Client_ID,AD_Language,AD_Org_ID,AD_User_ID,Comments,Created,CreatedBy,Description,Fresh_Gift,IsAccountLocked,IsActive,IsBillToContact_Default,IsDecider,IsDefaultContact,IsFullBPAccess,IsInPayroll,IsLoginAsHostKey,IsManagement,IsMFProcurementUser,IsMultiplier,IsSystemUser,Login,LoginFailureCount,Name,NotificationType,Processing,UnlockAccount,Updated,UpdatedBy,Value) VALUES (1000000,'en_US',1000000,540057,'',TO_TIMESTAMP('2019-07-19 12:29:11','YYYY-MM-DD HH24:MI:SS'),100,'User used for authentication for Json Reports','N','N','Y','N','N','N','Y','N','N','N','N','N','Y','Json Reports',0,'Json Reports','N','N','N',TO_TIMESTAMP('2019-07-19 12:29:11','YYYY-MM-DD HH24:MI:SS'),100,'jreports')
;

-- 2019-07-19T09:29:12.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_User_Record_Access (Access,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_User_Record_Access_ID,Created,CreatedBy,IsActive,Record_ID,Updated,UpdatedBy) VALUES ('R',1000000,1000000,114,100,540003,TO_TIMESTAMP('2019-07-19 12:29:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',540057,TO_TIMESTAMP('2019-07-19 12:29:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-19T09:29:12.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_User_Record_Access (Access,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_User_Record_Access_ID,Created,CreatedBy,IsActive,Record_ID,Updated,UpdatedBy) VALUES ('W',1000000,1000000,114,100,540004,TO_TIMESTAMP('2019-07-19 12:29:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',540057,TO_TIMESTAMP('2019-07-19 12:29:12','YYYY-MM-DD HH24:MI:SS'),100)
;

update ad_user set value ='JsonReports', name ='Json Reports' where ad_user_id=540057;



-- Create a default auth token for user Json Reports

INSERT INTO public.ad_user_authtoken (ad_client_id, ad_org_id, ad_role_id, ad_user_authtoken_id, ad_user_id, authtoken, created, createdby, description, isactive, updated, updatedby) VALUES (1000000, 1000000, 1000000, 540001, 540057, '0d46ecc8fc8148049ddc7eb19433d3b6', '2019-07-19 09:32:54.000000', 100, null, 'Y', '2019-07-19 09:32:59.000000', 100);


