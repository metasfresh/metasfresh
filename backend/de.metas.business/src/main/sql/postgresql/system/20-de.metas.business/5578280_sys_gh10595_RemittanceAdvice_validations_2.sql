-- 2021-02-03T16:06:51.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=541023,Updated=TO_TIMESTAMP('2021-02-03 18:06:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541574
;

-- 2021-02-03T16:11:18.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:11:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572599
;

-- 2021-02-03T16:12:39.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:12:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572593
;

-- 2021-02-03T16:14:12.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsInvoiceDocTypeValid/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:14:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572593
;

-- 2021-02-03T16:17:48.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsCBPartnerValid/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572594
;

-- 2021-02-03T16:18:09.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsBPartnerValid/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:18:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572594
;

-- 2021-02-03T16:20:11.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsBPartnerValid/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572592
;

-- 2021-02-03T16:20:14.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','InvoiceDate','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2021-02-03T16:20:38.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsInvoiceDateValid/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572592
;

-- 2021-02-03T16:47:21.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsInvoiceDateValid/''Y''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572592
;

-- 2021-02-03T16:47:43.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsBPartnerValid/''Y''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:47:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572594
;

-- 2021-02-03T16:48:50.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572596
;

-- 2021-02-03T16:48:57.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572595
;

-- 2021-02-03T16:56:54.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572598
;

-- 2021-02-03T16:57:36.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsServiceFeeVatRateValid/''Y''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 18:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572598
;

-- 2021-02-03T17:01:12.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 19:01:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572597
;

-- 2021-02-03T17:08:27.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@I_IsImported/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 19:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628796
;

-- 2021-02-03T17:08:47.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@I_IsImported/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-03 19:08:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628795
;

-- 2021-02-03T17:14:53.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2021-02-03 19:14:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572617
;

-- 2021-02-03T17:15:03.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2021-02-03 19:15:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572620
;

-- 2021-02-03T17:15:11.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2021-02-03 19:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572615
;

-- 2021-02-03T17:15:26.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2021-02-03 19:15:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572615
;

-- 2021-02-03T17:15:31.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2021-02-03 19:15:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572617
;

-- 2021-02-03T17:15:36.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2021-02-03 19:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572620
;

-- 2021-02-03T17:17:10.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsBPartnerValid/''Y''@ = ''Y'' & @C_Invoice_ID/0@ > 0',Updated=TO_TIMESTAMP('2021-02-03 19:17:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572594
;

-- 2021-02-03T17:17:27.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsInvoiceDocTypeValid/''N''@ = ''Y'' & @C_Invoice_ID/0@ > 0',Updated=TO_TIMESTAMP('2021-02-03 19:17:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572593
;

-- 2021-02-03T17:23:07.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & ( @C_Invoice_ID/-1@ < 0 | @IsInvoiceDocTypeValid/''N''@ = ''Y'')',Updated=TO_TIMESTAMP('2021-02-03 19:23:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572593
;

-- 2021-02-03T17:25:53.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & ( @C_Invoice_ID/0@ = 0 | @IsInvoiceDocTypeValid/''N''@ = ''Y'')',Updated=TO_TIMESTAMP('2021-02-03 19:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572593
;

-- 2021-02-03T17:27:22.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & ( @C_Invoice_ID/0@ = 0 | @IsBPartnerVaid/''N''@ = ''Y'')',Updated=TO_TIMESTAMP('2021-02-03 19:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572594
;

-- 2021-02-03T17:34:13.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@#Date@',Updated=TO_TIMESTAMP('2021-02-03 19:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572613
;

-- 2021-02-08T15:50:25.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-02-08 17:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572594
;

-- 2021-02-08T15:52:57.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2021-02-08T15:52:57.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','C_BPartner_ID',null,'NULL',null)
;

