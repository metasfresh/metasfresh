
-- 2020-07-09T17:50:46.801Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES
 (0,0,540511,'AD_User.C_Bpartner_ID = @C_BPartner_ID/-1@
AND AD_User.IsBillToContact_Default = ''Y''',TO_TIMESTAMP('2020-07-09 20:50:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
'AD_User_BillToDefault of Context  Partner','S',TO_TIMESTAMP('2020-07-09 20:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2020-07-09T17:51:49.277Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Name='AD_User_BillToDefault of Context Partner',Updated=TO_TIMESTAMP('2020-07-09 20:51:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540511
;

-- 2020-07-09T17:52:19.262Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='AD_User.C_Bpartner_ID = @C_BPartner_ID/-1@
AND AD_User.IsBillToContact_Default = ''Y''',Updated=TO_TIMESTAMP('2020-07-09 20:52:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540511
;



-- 2020-07-09T18:04:42.665Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='AD_User.C_Bpartner_ID = @C_BPartner_ID/-1@
AND AD_User.IsBillToContact_Default = ''Y''
AND AD_User.C_BPartner_ID IS NOT NULL',Updated=TO_TIMESTAMP('2020-07-09 21:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540511
;

-- 2020-07-09T18:05:32.191Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='AD_User.C_Bpartner_ID = @C_BPartner_ID/-1@
AND AD_User.IsBillToContact_Default = ''Y''
AND AD_User.C_BPartner_ID >0',Updated=TO_TIMESTAMP('2020-07-09 21:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540511
;



-- 2020-07-09T18:19:00.185Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='AD_User.C_Bpartner_ID = @C_BPartner_ID/-1@
AND AD_User.IsBillToContact_Default = ''Y''
',Updated=TO_TIMESTAMP('2020-07-09 21:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540511
;





-- 2020-07-13T11:45:41.349Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
 VALUES (0,0,541330,'O',TO_TIMESTAMP('2020-07-13 14:45:40','YYYY-MM-DD HH24:MI:SS'),100,
 'If this sys config is on ''Y'' and the validation rule AD_User_BillToDefault of Context Partner is set on the column C_Invoice.AD_User (or a linked field) then  only the BillToDefault_Contact will be eligible as user in the sales invoice. If the billTODefault_Contact is not set, no user will be available.','D','Y','C_Invoice.SOTrx_OnlyAllowBillToDefault_Contact',TO_TIMESTAMP('2020-07-13 14:45:40','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2020-07-13T11:46:48.978Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Description='If the sys config C_Invoice.SOTrx_OnlyAllowBillToDefault_Contact is on true and this validation rule is set on C_Invoice_AD_User ( column or field) then only the BillToDefault_Contact will be eligible as user in a sales invoice.',Updated=TO_TIMESTAMP('2020-07-13 14:46:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540511
;






