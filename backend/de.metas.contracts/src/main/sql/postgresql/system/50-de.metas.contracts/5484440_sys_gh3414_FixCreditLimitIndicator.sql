-- 2018-02-01T11:59:07.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select round((s.SO_CreditUsed*100) /cl.Amount,3) from C_BPartner_Stats s join C_BPartner_CreditLimit cl on (s.C_BPartner_ID = cl.C_Bpartner_ID and s.C_Bpartner_ID = C_BPartner.C_BPartner_ID) order by Type limit 1)',Updated=TO_TIMESTAMP('2018-02-01 11:59:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

