-- Name: C_PaySelection_EligibleToBeReconciliatedWithBankStatement
-- 2026-05-06T09:32:00.147Z
UPDATE AD_Val_Rule SET Code='C_PaySelection.DocStatus=''CO'' AND C_PaySelection.IsReconciled=''N'' AND EXISTS (SELECT 1 FROM c_payselectionline psl where psl.C_PaySelection_ID=C_PaySelection.C_PaySelection_ID AND psl.c_payment_id IS NOT NULL)',Updated=TO_TIMESTAMP('2026-05-06 09:32:00.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540489
;

