

ALTER TABLE c_flatrate_conditions DROP COLUMN IF EXISTS c_prefinancing_settings_id;




-- 2022-08-01T13:02:41.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Conditions','ALTER TABLE public.C_Flatrate_Conditions ADD COLUMN C_Interim_Invoice_Settings_ID NUMERIC(10)')
;

-- 2022-08-01T13:02:41.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Flatrate_Conditions ADD CONSTRAINT CInterimInvoiceSettings_CFlatrateConditions FOREIGN KEY (C_Interim_Invoice_Settings_ID) REFERENCES public.C_Interim_Invoice_Settings DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-01T13:05:27.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='IncotermInvoice', ValueName='IncotermInvoice',Updated=TO_TIMESTAMP('2022-08-01 16:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543260
;

-- 2022-08-01T13:05:51.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Incoterm Invoice',Updated=TO_TIMESTAMP('2022-08-01 16:05:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543260
;

-- 2022-08-01T13:05:54.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Incoterm Invoice',Updated=TO_TIMESTAMP('2022-08-01 16:05:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543260
;

-- 2022-08-01T13:05:58.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Incoterm Invoice',Updated=TO_TIMESTAMP('2022-08-01 16:05:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543260
;

-- 2022-08-01T13:06:58.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Interim Invoice',Updated=TO_TIMESTAMP('2022-08-01 16:06:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543260
;

-- 2022-08-01T13:07:02.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Interim Invoice',Updated=TO_TIMESTAMP('2022-08-01 16:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543260
;

-- 2022-08-01T13:07:04.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Interim Invoice',Updated=TO_TIMESTAMP('2022-08-01 16:07:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543260
;

-- 2022-08-01T13:07:20.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='InterimInvoice', ValueName='InterimInvoice',Updated=TO_TIMESTAMP('2022-08-01 16:07:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543260
;

-- 2022-08-01T13:07:35.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','Type_Conditions','VARCHAR(40)',null,null)
;




-- 2022-08-01T15:48:17.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''''@=''InterimInvoice''',Updated=TO_TIMESTAMP('2022-08-01 18:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583805
;

-- 2022-08-01T15:48:31.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''''@=''InterimInvoice''',Updated=TO_TIMESTAMP('2022-08-01 18:48:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545656
;

-- 2022-08-01T15:49:25.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''''@=''InterimInvoice''',Updated=TO_TIMESTAMP('2022-08-01 18:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702171
;





