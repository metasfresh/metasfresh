-- 2018-06-29T17:00:04.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560539
;

-- 2018-06-29T17:00:04.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=560539
;

-- 2018-06-29T17:00:34.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=565010
;

-- 2018-06-29T17:00:34.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=565010
;

-- 2018-06-29T17:00:40.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560548
;

-- 2018-06-29T17:00:40.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=560548
;

-- 2018-06-29T17:01:26.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=53777, ColumnName='TransactionCode', Description='The transaction code represents the search definition', FieldLength=1, Help=NULL, Name='Transaction Code',Updated=TO_TIMESTAMP('2018-06-29 17:01:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560564
;

-- 2018-06-29T17:01:26.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Transaction Code', Description='The transaction code represents the search definition', Help=NULL WHERE AD_Column_ID=560564
;

-- 2018-06-29T17:01:28.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Datev_Payment','ALTER TABLE public.I_Datev_Payment ADD COLUMN TransactionCode VARCHAR(1)')
;

/* DDL */ SELECT public.db_alter_table('I_Datev_Payment','ALTER TABLE public.I_Datev_Payment DROp COLUMN C_DocType_ID')
;


/* DDL */ SELECT public.db_alter_table('I_Datev_Payment','ALTER TABLE public.I_Datev_Payment DROP COLUMN AccountNo')
;


;
-- 2018-06-29T17:21:30.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540987,'Y','de.metas.banking.payment.process.ImportDatevPayment','N',TO_TIMESTAMP('2018-06-29 17:21:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N','N','N','N','N','N','Y',0,'Import Datev Payment','N','Y','Java',TO_TIMESTAMP('2018-06-29 17:21:30','YYYY-MM-DD HH24:MI:SS'),100,'ImportDatevPayment')
;

-- 2018-06-29T17:21:30.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540987 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-06-29T17:21:44.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540987,540999,TO_TIMESTAMP('2018-06-29 17:21:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y',TO_TIMESTAMP('2018-06-29 17:21:44','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

