create unique index if not exists idx_unq_C_PaymentTerm_Value_Org on C_PaymentTerm (AD_Org_ID, Value) where isActive='Y'
;