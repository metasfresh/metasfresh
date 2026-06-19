-- Run mode: SWING_CLIENT

-- Name: C_Invoice Not Paid and of (C_BPartner, null) and Org
-- 2026-03-27T11:19:49.296Z
UPDATE AD_Val_Rule SET Code='( (C_Invoice.IsPaid != ''Y'') AND (C_Invoice.IsFinancial = ''Y'') AND (C_Invoice.C_BPartner_ID=@C_BPartner_ID/-1@ OR @C_BPartner_ID/-1@=0) AND C_Invoice.AD_Org_ID IN (@AD_Org_ID/-1@, 0) )',Updated=TO_TIMESTAMP('2026-03-27 11:19:49.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540500
;

