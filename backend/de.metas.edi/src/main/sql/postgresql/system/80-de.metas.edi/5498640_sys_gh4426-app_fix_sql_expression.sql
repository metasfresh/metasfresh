-- 2018-08-01T09:17:19.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT IsEdiRecipient from C_BPartner where C_BPartner_ID = C_Order.Bill_BPartner_ID)',Updated=TO_TIMESTAMP('2018-08-01 09:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552603
;

