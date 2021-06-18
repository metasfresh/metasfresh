-- 2021-06-18T13:45:13.215Z
-- URL zum Konzept
INSERT INTO AD_Role (AD_Client_ID,AD_Org_ID,AD_Role_ID,Allow_Info_Account,Allow_Info_Asset,Allow_Info_BPartner,Allow_Info_CashJournal,Allow_Info_CRP,Allow_Info_InOut,Allow_Info_Invoice,Allow_Info_MRP,Allow_Info_Order,Allow_Info_Payment,Allow_Info_Product,Allow_Info_Resource,Allow_Info_Schedule,C_Currency_ID,ConfirmQueryRecords,Created,CreatedBy,IsAccessAllOrgs,IsActive,IsAllowedInvoicingPriority,IsAllowedMigrationScripts,IsAllowedTrlBox,IsAllowLoginDateOverride,IsAttachmentDeletionAllowed,IsAutoRoleLogin,IsCanApproveOwnDoc,IsCanExport,IsCanReport,IsChangeLog,IsDiscountAllowedOnTotal,IsDiscountUptoLimitPrice,IsManual,IsMenuAvailable,IsOrgLoginMandatory,IsPersonalAccess,IsPersonalLock,IsRoleAlwaysUseBetaFunctions,IsShowAcct,IsShowAllEntityTypes,IsUseUserOrgAccess,MaxQueryRecords,Name,OverwritePriceLimit,PreferenceType,Root_Menu_ID,SeqNo,Updated,UpdatedBy,UserLevel,WEBUI_Role) VALUES (1000000,0,540084,'N','N','N','N','Y','N','N','Y','N','N','Y','N','N',102,0,TO_TIMESTAMP('2021-06-18 15:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','N','N','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N','N','N','N','N',0,'x_API_ARevision','N','O',1000007,70,TO_TIMESTAMP('2021-06-18 15:45:13','YYYY-MM-DD HH24:MI:SS'),100,'_CO','Y')
;

-- 2021-06-18T13:45:13.333Z
-- URL zum Konzept
INSERT INTO AD_User_Roles (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_User_ID,AD_User_Roles_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,540084,100,540118,TO_TIMESTAMP('2021-06-18 15:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-06-18 15:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-18T13:45:16.323Z
-- URL zum Konzept
UPDATE AD_Role SET MaxQueryRecords=50000,Updated=TO_TIMESTAMP('2021-06-18 15:45:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540084
;

-- 2021-06-18T13:45:36.591Z
-- URL zum Konzept
INSERT INTO AD_Window_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Window_Access_ID,AD_Window_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,540084,540124,541128,TO_TIMESTAMP('2021-06-18 15:45:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2021-06-18 15:45:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-18T13:45:43.504Z
-- URL zum Konzept
INSERT INTO AD_Window_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Window_Access_ID,AD_Window_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,540084,540125,541127,TO_TIMESTAMP('2021-06-18 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2021-06-18 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-18T13:45:50.059Z
-- URL zum Konzept
INSERT INTO AD_Window_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Window_Access_ID,AD_Window_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,540084,540126,541158,TO_TIMESTAMP('2021-06-18 15:45:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2021-06-18 15:45:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-18T13:45:57.284Z
-- URL zum Konzept
INSERT INTO AD_Window_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Window_Access_ID,AD_Window_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,540084,540127,541125,TO_TIMESTAMP('2021-06-18 15:45:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2021-06-18 15:45:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-18T13:46:13.197Z
-- URL zum Konzept
INSERT INTO AD_Process_Access (AD_Client_ID,AD_Org_ID,AD_Process_Access_ID,AD_Process_ID,AD_Role_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,540038,584837,540084,TO_TIMESTAMP('2021-06-18 15:46:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2021-06-18 15:46:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-18T13:46:20.053Z
-- URL zum Konzept
INSERT INTO AD_Process_Access (AD_Client_ID,AD_Org_ID,AD_Process_Access_ID,AD_Process_ID,AD_Role_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,540039,584836,540084,TO_TIMESTAMP('2021-06-18 15:46:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2021-06-18 15:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-18T13:46:34.462Z
-- URL zum Konzept
UPDATE AD_Role SET IsAccessAllOrgs='N',Updated=TO_TIMESTAMP('2021-06-18 15:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540084
;

-- 2021-06-18T13:47:05.184Z
-- URL zum Konzept
UPDATE AD_Role SET Root_Menu_ID=NULL,Updated=TO_TIMESTAMP('2021-06-18 15:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540084
;

-- 2021-06-18T13:47:21.868Z
-- URL zum Konzept
UPDATE AD_Role SET IsMenuAvailable='N',Updated=TO_TIMESTAMP('2021-06-18 15:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540084
;

-- 2021-06-18T13:47:22.812Z
-- URL zum Konzept
UPDATE AD_Role SET Allow_Info_MRP='N',Updated=TO_TIMESTAMP('2021-06-18 15:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540084
;

-- 2021-06-18T13:47:23.374Z
-- URL zum Konzept
UPDATE AD_Role SET Allow_Info_CRP='N',Updated=TO_TIMESTAMP('2021-06-18 15:47:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540084
;

-- 2021-06-18T13:47:23.857Z
-- URL zum Konzept
UPDATE AD_Role SET Allow_Info_Product='N',Updated=TO_TIMESTAMP('2021-06-18 15:47:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540084
;

-- 2021-06-18T13:47:26.501Z
-- URL zum Konzept
UPDATE AD_Role SET IsCanExport='N',Updated=TO_TIMESTAMP('2021-06-18 15:47:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540084
;

-- 2021-06-18T13:47:27.034Z
-- URL zum Konzept
UPDATE AD_Role SET IsCanReport='N',Updated=TO_TIMESTAMP('2021-06-18 15:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540084
;

-- 2021-06-18T13:49:28.390Z
-- URL zum Konzept
UPDATE AD_Role SET Name='x_API_Revision',Updated=TO_TIMESTAMP('2021-06-18 15:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540084
;
