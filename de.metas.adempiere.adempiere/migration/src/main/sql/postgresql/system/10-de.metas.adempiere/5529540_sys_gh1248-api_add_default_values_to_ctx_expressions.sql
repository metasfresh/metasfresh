-- 2019-08-29T10:44:04.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Action/''XX''@="SU"',Updated=TO_TIMESTAMP('2019-08-29 12:44:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540392
;

-- 2019-08-29T10:44:17.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Action/''XX''@="ST"', ReadOnlyLogic='@Action/''XX''@!"ST"',Updated=TO_TIMESTAMP('2019-08-29 12:44:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540393
;

-- C_Invoice
-- 2019-08-29T10:48:05.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsUseBPartnerAddress/''N''@=''N''',Updated=TO_TIMESTAMP('2019-08-29 12:48:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=505108
;

-- 2019-08-29T10:48:33.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsEdiRecipient/''N''@ = N',Updated=TO_TIMESTAMP('2019-08-29 12:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548484
;

-- 2019-08-29T10:49:19.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@EDI_ExportStatus/''X''@!P & @EDI_ExportStatus/''X''@!E & @EDI_ExportStatus/''X''@!I',Updated=TO_TIMESTAMP('2019-08-29 12:49:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548468
;

-- 2019-08-29T10:50:38.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@DocBaseType/''X''@=''ARC''',Updated=TO_TIMESTAMP('2019-08-29 12:50:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556177
;

-- 2019-08-29T10:50:44.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Ref_Invoice_ID/''X''@!0',Updated=TO_TIMESTAMP('2019-08-29 12:50:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=9228
;

-- 2019-08-29T10:51:11.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Reversal_ID/''X''@!0',Updated=TO_TIMESTAMP('2019-08-29 12:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551570
;

-- 2019-08-29T10:51:19.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed/''X''@=Y',Updated=TO_TIMESTAMP('2019-08-29 12:51:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53257
;

-- 2019-08-29T10:51:33.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Ref_CreditMemo_ID/''X''@=0&@DocStatus@=''CO''&@C_DocTypeTarget_ID@=1000002 | @C_DocTypeTarget_ID@=1000003 | @C_DocTypeTarget_ID/''X''@=1000049 | @C_DocTypeTarget_ID/''X''@=1000056',Updated=TO_TIMESTAMP('2019-08-29 12:51:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=504605
;

-- 2019-08-29T10:52:26.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Ref_AdjustmentCharge_ID/-1@=0&@DocStatus/''X''@=''CO''& (@C_DocTypeTarget_ID/-1@=1000002 | @C_DocTypeTarget_ID/-1@=1000003 | @C_DocTypeTarget_ID/-1@=540899  | @C_DocTypeTarget_ID/-1@=540900)',Updated=TO_TIMESTAMP('2019-08-29 12:52:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553945
;

-- 2019-08-29T10:52:34.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Ref_Invoice_ID/-1@!0',Updated=TO_TIMESTAMP('2019-08-29 12:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=9228
;

-- 2019-08-29T10:52:36.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Reversal_ID/-1@!0',Updated=TO_TIMESTAMP('2019-08-29 12:52:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551570
;

-- 2019-08-29T10:53:10.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Ref_CreditMemo_ID/-1@=0&@DocStatus/''X''@=''CO''&@C_DocTypeTarget_ID/-1@=1000002 | @C_DocTypeTarget_ID/-1@=1000003 | @C_DocTypeTarget_ID/-1@=1000049 | @C_DocTypeTarget_ID/-1@=1000056',Updated=TO_TIMESTAMP('2019-08-29 12:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=504605
;

-- 2019-08-29T10:53:18.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_DocTypeTarget_ID/-1@=1000004',Updated=TO_TIMESTAMP('2019-08-29 12:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542147
;

-- 2019-08-29T10:53:37.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_Currency_ID/-1@!@$C_Currency_ID/-1@',Updated=TO_TIMESTAMP('2019-08-29 12:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8648
;

-- 2019-08-29T10:53:47.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@HasCharges/''X''@=''Y''',Updated=TO_TIMESTAMP('2019-08-29 12:53:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2953
;

-- 2019-08-29T10:53:49.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@HasCharges/''X''@=''Y''',Updated=TO_TIMESTAMP('2019-08-29 12:53:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2956
;

-- 2019-08-29T10:53:52.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@PaymentRule/''X''@=''P'' | @PaymentRule/''X''@=''D''',Updated=TO_TIMESTAMP('2019-08-29 12:53:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2774
;

-- 2019-08-29T10:53:54.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@$Element_PJ/''X''@=''Y''',Updated=TO_TIMESTAMP('2019-08-29 12:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2775
;

-- 2019-08-29T10:54:00.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@$Element_AY/''X''@=''Y''',Updated=TO_TIMESTAMP('2019-08-29 12:54:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2764
;

-- 2019-08-29T10:54:03.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@$Element_MC/''X''@=''Y''',Updated=TO_TIMESTAMP('2019-08-29 12:54:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2768
;

-- 2019-08-29T10:54:08.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@$Element_OT/''X''@=Y',Updated=TO_TIMESTAMP('2019-08-29 12:54:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6935
;

-- 2019-08-29T10:54:10.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@$Element_U1/''X''@=Y',Updated=TO_TIMESTAMP('2019-08-29 12:54:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=7794
;

-- 2019-08-29T10:54:14.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@$Element_U2/''X''@=Y',Updated=TO_TIMESTAMP('2019-08-29 12:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=7795
;

-- 2019-08-29T10:54:22.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsEdiEnabled/''X''@=''Y'' & (@DocStatus/''X''@=''CO'' | @DocStatus/''X''@=''CL'' )',Updated=TO_TIMESTAMP('2019-08-29 12:54:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551554
;

-- 2019-08-29T10:54:27.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@EDI_ExportStatus/''X''@=''E'' & @IsEdiEnabled/''X''@=''Y''',Updated=TO_TIMESTAMP('2019-08-29 12:54:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551574
;

-- 2019-08-29T10:54:31.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed/''X''@=Y & @#ShowAcct@=Y',Updated=TO_TIMESTAMP('2019-08-29 12:54:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3663
;

-- 2019-08-29T10:54:33.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed/''X''@=Y',Updated=TO_TIMESTAMP('2019-08-29 12:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3899
;

-- 2019-08-29T10:54:36.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed/''X''@=Y',Updated=TO_TIMESTAMP('2019-08-29 12:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=13700
;

-- 2019-08-29T10:54:39.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed/''X''@=Y',Updated=TO_TIMESTAMP('2019-08-29 12:54:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53258
;

-- 2019-08-29T10:54:50.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@UserFlag/''X''@!''''',Updated=TO_TIMESTAMP('2019-08-29 12:54:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556937
;

-- 2019-08-29T10:54:54.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed/''X''@=Y',Updated=TO_TIMESTAMP('2019-08-29 12:54:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10485
;

-- 2019-08-29T10:54:58.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Incoterm/''X''@!''''',Updated=TO_TIMESTAMP('2019-08-29 12:54:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501625
;


-- M_HU_PI_Item
-- 2019-08-29T10:58:36.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@ItemType/''X''@=PM',Updated=TO_TIMESTAMP('2019-08-29 12:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549293
;

-- M_ReceiptSchedule
-- 2019-08-29T11:00:43.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@LockedBy/''X''@!""',Updated=TO_TIMESTAMP('2019-08-29 13:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549504
;

-- 2019-08-29T11:00:50.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@LockedBy/''X''@!""',Updated=TO_TIMESTAMP('2019-08-29 13:00:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549512
;

-- 2019-08-29T11:00:58.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsBPartnerAddress_Override/''X''@=''N''',Updated=TO_TIMESTAMP('2019-08-29 13:00:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549496
;

-- 2019-08-29T11:01:06.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@C_BPartner_Override_ID/-1@!0',Updated=TO_TIMESTAMP('2019-08-29 13:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549498
;

