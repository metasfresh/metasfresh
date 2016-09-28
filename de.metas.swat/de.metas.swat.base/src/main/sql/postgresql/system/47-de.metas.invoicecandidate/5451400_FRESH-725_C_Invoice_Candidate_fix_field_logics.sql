-- Sep 28, 2016 7:22 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT IsEDIRecipient FROM C_BPartner where C_BPartner_ID = @Bill_BPartner_ID@',Updated=TO_TIMESTAMP('2016-09-28 19:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552564
;

-- Sep 28, 2016 8:12 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual@=''Y''|@Description/@!''''',Updated=TO_TIMESTAMP('2016-09-28 20:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551170
;

-- Sep 28, 2016 8:14 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@InvoiceScheduleAmtStatus/@!''OK''',Updated=TO_TIMESTAMP('2016-09-28 20:14:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=549713
;

-- Sep 28, 2016 8:26 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsEdiRecipient/N@=N',Updated=TO_TIMESTAMP('2016-09-28 20:26:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552599
;

-- Sep 28, 2016 8:27 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE( (SELECT IsEDIRecipient FROM C_BPartner where C_BPartner_ID = @Bill_BPartner_ID/-1@), ''N'')',Updated=TO_TIMESTAMP('2016-09-28 20:27:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552564
;

