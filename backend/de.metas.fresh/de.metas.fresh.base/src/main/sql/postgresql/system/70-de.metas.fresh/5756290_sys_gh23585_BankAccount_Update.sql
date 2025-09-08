-- Run mode: SWING_CLIENT

-- Column: C_BP_BankAccount.QR_IBAN
-- 2025-05-30T08:57:46.884Z
UPDATE AD_Column SET MandatoryLogic='@IsEsrAccount@=''Y''',Updated=TO_TIMESTAMP('2025-05-30 08:57:46.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=570890
;

