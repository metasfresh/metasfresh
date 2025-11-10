-- Run mode: SWING_CLIENT

-- SysConfig Name: org.compiere.acct.Doc_GLJournal.DisableFactAcctTrxChecking
-- SysConfig Value: N
-- 2025-09-19T12:39:18.649Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541773,'S',TO_TIMESTAMP('2025-09-19 12:39:18.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','org.compiere.acct.Doc_GLJournal.DisableFactAcctTrxChecking',TO_TIMESTAMP('2025-09-19 12:39:18.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- SysConfig Name: org.compiere.acct.Doc_GLJournal.DisableFactAcctTrxChecking
-- SysConfig Value: Y
-- SysConfig Value (old): N
-- 2025-09-19T12:39:22.732Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='Y',Updated=TO_TIMESTAMP('2025-09-19 12:39:22.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541773
;

