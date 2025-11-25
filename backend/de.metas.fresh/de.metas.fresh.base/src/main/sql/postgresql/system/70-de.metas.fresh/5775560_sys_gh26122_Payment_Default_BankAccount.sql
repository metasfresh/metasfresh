-- Run mode: SWING_CLIENT

-- Column: C_Payment.C_BP_BankAccount_ID
-- 2025-11-03T15:15:22.554Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT C_BP_BankAccount_ID FROM C_BP_BankAccount WHERE IsDefault=''Y''',Updated=TO_TIMESTAMP('2025-11-03 15:15:22.554000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=5298
;

