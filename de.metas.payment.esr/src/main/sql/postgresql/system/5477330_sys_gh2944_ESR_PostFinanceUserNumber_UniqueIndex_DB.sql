
-- 2017-11-16T16:33:04.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX ESR_PostFinanceUserNumber_RenderedAccountNo_Unique ON ESR_PostFinanceUserNumber (ESR_RenderedAccountNo,C_BP_BankAccount_ID) WHERE IsActive = 'Y'
;

