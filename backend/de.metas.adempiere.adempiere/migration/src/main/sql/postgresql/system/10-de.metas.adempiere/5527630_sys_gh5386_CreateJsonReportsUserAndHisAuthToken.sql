-- create user Json Reports

-- 2019-07-19T09:29:11.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_User (AD_Client_ID,AD_Language,AD_Org_ID,AD_User_ID,Comments,Created,CreatedBy,Description,Fresh_Gift,IsAccountLocked,IsActive,IsBillToContact_Default,IsDecider,IsDefaultContact,IsFullBPAccess,IsInPayroll,IsLoginAsHostKey,IsManagement,IsMFProcurementUser,IsMultiplier,IsSystemUser,Login,LoginFailureCount,Name,NotificationType,Processing,UnlockAccount,Updated,UpdatedBy,Value) VALUES 
(1000000,'en_US',0,540057,'',TO_TIMESTAMP('2019-07-19 12:29:11','YYYY-MM-DD HH24:MI:SS'),100,'User used for authentication for Json Reports','N','N','Y','N','N','N','Y','N','N','N','N','N','Y','Json Reports',0,'Json Reports','N','N','N',TO_TIMESTAMP('2019-07-19 12:29:11','YYYY-MM-DD HH24:MI:SS'),100,'jreports')
;

update ad_user set value ='JsonReports', name ='Json Reports', IsSystemUser='N' where ad_user_id=540057;


-- add Json Report role

-- 2019-07-22T09:35:28.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Role 
(AD_Client_ID,AD_Org_ID,AD_Role_ID,Allow_Info_Account,Allow_Info_Asset,Allow_Info_BPartner,Allow_Info_CashJournal,Allow_Info_CRP,Allow_Info_InOut,Allow_Info_Invoice,Allow_Info_MRP,Allow_Info_Order,Allow_Info_Payment,Allow_Info_Product,Allow_Info_Resource,Allow_Info_Schedule,AmtApproval,ConfirmQueryRecords,Created,CreatedBy,Description,IsAccessAllOrgs,IsActive,IsAllowedInvoicingPriority,IsAllowedMigrationScripts,IsAllowedTrlBox,IsAllowLoginDateOverride,IsAttachmentDeletionAllowed,IsAutoRoleLogin,IsCanApproveOwnDoc,IsCanExport,IsCanReport,IsChangeLog,IsDiscountAllowedOnTotal,IsDiscountUptoLimitPrice,IsManual,IsMenuAvailable,IsOrgLoginMandatory,IsPersonalAccess,IsPersonalLock,IsRoleAlwaysUseBetaFunctions,IsShowAcct,IsShowAllEntityTypes,IsUseUserOrgAccess,Login_Org_ID,MaxQueryRecords,Name,OverwritePriceLimit,PreferenceType,Root_Menu_ID,Updated,UpdatedBy,UserLevel,WEBUI_Role) VALUES 
(1000000,0,540078,'N','N','N','N','N','N','N','N','N','N','N','N','N',0,500,TO_TIMESTAMP('2019-07-22 12:35:28','YYYY-MM-DD HH24:MI:SS'),100,'Role for Json reports','Y','Y','N','N','Y','N','N','Y','Y','Y','Y','N','N','N','Y','Y','Y','N','N','N','N','Y','N',1000000,5000,'JsonReports','N','C',1000007,TO_TIMESTAMP('2019-07-22 12:35:28','YYYY-MM-DD HH24:MI:SS'),100,'_CO','Y')
;

-- Create a default auth token for user Json Reports
INSERT INTO public.ad_user_authtoken (ad_client_id, ad_org_id, ad_role_id, ad_user_authtoken_id, ad_user_id, authtoken, created, createdby, description, isactive, updated, updatedby) VALUES 
(1000000, 0, 540078, 540001, 540057, replace((uuid_in(md5(random()::text || clock_timestamp()::text)::cstring))::text,'-', ''), '2019-07-19 09:32:54.000000', 100, null, 'Y', '2019-07-19 09:32:59.000000', 100);

