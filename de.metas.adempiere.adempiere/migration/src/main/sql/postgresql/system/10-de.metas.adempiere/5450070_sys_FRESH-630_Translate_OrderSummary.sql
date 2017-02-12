-- 01.09.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2016-09-01 15:04:03','YYYY-MM-DD HH24:MI:SS'),MsgText='Lines: @lines@ - Gross: @GrandTotal@@CurSymbol@ - Tax Sum.: @Sum_Tax@@CurSymbol@ - Net: @NetSum@@CurSymbol@ - Net in base currency: @net_in_base_currency@@BaseCurSymbol@' WHERE AD_Message_ID=377 AND AD_Language='en_US'
;

