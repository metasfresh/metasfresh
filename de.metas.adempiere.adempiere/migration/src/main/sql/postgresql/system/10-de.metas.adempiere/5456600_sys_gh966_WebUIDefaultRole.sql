-- 15.02.2017 08:14
-- URL zum Konzept
INSERT INTO AD_Role (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Tree_Menu_ID,Allow_Info_Account,Allow_Info_Asset,Allow_Info_BPartner,Allow_Info_CashJournal,Allow_Info_CRP,Allow_Info_InOut,Allow_Info_Invoice,Allow_Info_MRP,Allow_Info_Order,Allow_Info_Payment,Allow_Info_Product,Allow_Info_Resource,Allow_Info_Schedule,AmtApproval,ConfirmQueryRecords,Created,CreatedBy,IsAccessAllOrgs,IsActive,IsAllowLoginDateOverride,IsAutoRoleLogin,IsCanApproveOwnDoc,IsCanExport,IsCanReport,IsChangeLog,IsDiscountAllowedOnTotal,IsDiscountUptoLimitPrice,IsManual,IsMenuAvailable,IsOrgLoginMandatory,IsPersonalAccess,IsPersonalLock,IsRoleAlwaysUseBetaFunctions,IsShowAcct,IsShowAllEntityTypes,IsUseUserOrgAccess,Login_Org_ID,MaxQueryRecords,Name,OverwritePriceLimit,PreferenceType,Updated,UpdatedBy,UserLevel) VALUES (1000000,0,540024,1000039,'Y','N','Y','N','Y','Y','Y','Y','Y','Y','Y','N','Y',0,500,TO_TIMESTAMP('2017-02-15 08:14:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','Y','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','Y','N',1000000,5000,'WebUI','N','C',TO_TIMESTAMP('2017-02-15 08:14:00','YYYY-MM-DD HH24:MI:SS'),100,'_CO')
;
-- 15.02.2017 08:14
-- URL zum Konzept
INSERT INTO AD_User_Roles (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_User_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,540024,100,TO_TIMESTAMP('2017-02-15 08:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2017-02-15 08:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 15.02.2017 08:14
-- URL zum Konzept
INSERT INTO AD_Window_Access (AD_Window_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) SELECT DISTINCT w.AD_Window_ID, 540024,1000000,0,'Y', now(),100, now(),100,'Y' FROM AD_Window w INNER JOIN AD_Tab t ON (w.AD_Window_ID=t.AD_Window_ID) INNER JOIN AD_Table tt ON (t.AD_Table_ID=tt.AD_Table_ID) WHERE t.SeqNo=(SELECT MIN(SeqNo) FROM AD_Tab xt WHERE xt.AD_Window_ID=w.AD_Window_ID)AND tt.AccessLevel IN ('7','6','3','2','1')
;

-- 15.02.2017 08:14
-- URL zum Konzept
INSERT INTO AD_Process_Access (AD_Process_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) SELECT DISTINCT p.AD_Process_ID, 540024,1000000,0,'Y', now(),100, now(),100,'Y' FROM AD_Process p WHERE AccessLevel IN ('7','6','3','2','1')
;

-- 15.02.2017 08:14
-- URL zum Konzept
INSERT INTO AD_Form_Access (AD_Form_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) SELECT f.AD_Form_ID, 540024,1000000,0,'Y', now(),100, now(),100,'Y' FROM AD_Form f WHERE AccessLevel IN ('7','6','3','2','1')
;

-- 15.02.2017 08:14
-- URL zum Konzept
INSERT INTO AD_WorkFlow_Access (AD_WorkFlow_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) SELECT w.AD_WorkFlow_ID, 540024,1000000,0,'Y', now(),100, now(),100,'Y' FROM AD_WorkFlow w WHERE AccessLevel IN ('7','6','3','2','1')
;


-- 15.02.2017 08:14
-- URL zum Konzept
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID AND rol.AD_Role_ID=540024) )
;

-- 15.02.2017 08:14
-- URL zum Konzept
INSERT INTO AD_Role_OrgAccess (AD_Client_ID,AD_Org_ID,AD_Role_ID,Created,CreatedBy,IsActive,IsReadOnly,Updated,UpdatedBy) VALUES (1000000,1000000,540024,TO_TIMESTAMP('2017-02-15 08:14:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2017-02-15 08:14:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.02.2017 08:14
-- URL zum Konzept
INSERT INTO AD_Role_OrgAccess (AD_Client_ID,AD_Org_ID,AD_Role_ID,Created,CreatedBy,IsActive,IsReadOnly,Updated,UpdatedBy) VALUES (1000000,0,540024,TO_TIMESTAMP('2017-02-15 08:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2017-02-15 08:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;
