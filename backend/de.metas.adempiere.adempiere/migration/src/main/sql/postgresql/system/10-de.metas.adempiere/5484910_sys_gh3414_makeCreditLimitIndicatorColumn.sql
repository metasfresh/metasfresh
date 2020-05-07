-- 2018-02-08T13:36:28.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='coalesce(select case when coalesce(cl.Amount,0) = 0 then 0 else round((s.SO_CreditUsed*100) /cl.Amount,3) end from C_BPartner_Stats s   join C_BPartner_CreditLimit cl on (s.C_BPartner_ID = cl.C_Bpartner_ID and s.C_Bpartner_ID = C_BPartner.C_BPartner_ID)  AND (cl.DateFrom IS NULL OR cl.DateFrom <= now())  AND (cl.DateTo IS NULL OR cl.DateTo >= now()) AND cl.IsActive=''Y'' ORDER BY cl.Type ASC NULLS LAST, cl.DateTo ASC NULLS LAST LIMIT 1, 0)',Updated=TO_TIMESTAMP('2018-02-08 13:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- 2018-02-08T13:52:05.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=10, ColumnSQL='',Updated=TO_TIMESTAMP('2018-02-08 13:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- 2018-02-08T13:52:20.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN CreditLimitIndicator VARCHAR(14)')
;

-- 2018-02-08T13:52:52.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-02-08 13:52:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561919
;

