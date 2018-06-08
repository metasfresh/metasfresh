-- 2018-03-22T16:46:43.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select MAX(CreditLimitIndicator) from C_BPartner_Stats s where s.C_BPartner_ID = C_BPartner.C_BPartner_ID)',Updated=TO_TIMESTAMP('2018-03-22 16:46:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

