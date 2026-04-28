-- 01.12.2015 21:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID
	-- ,AllowProcessReRun
	,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value)
VALUES (
	'3',0,0,540629
	-- ,'Y'
	,'de.metas.banking.payment.process.C_BankStatementLine_UnLink_Payments','N',TO_TIMESTAMP('2015-12-01 21:33:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N','N','N','N','N',0,'Unlink Payments','N','Y',0,0,'Java',TO_TIMESTAMP('2015-12-01 21:33:19','YYYY-MM-DD HH24:MI:SS'),100,'C_BankStatementLine_UnLink_Payments')
;

-- 01.12.2015 21:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540629 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 01.12.2015 21:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540629,393,TO_TIMESTAMP('2015-12-01 21:33:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y',TO_TIMESTAMP('2015-12-01 21:33:36','YYYY-MM-DD HH24:MI:SS'),100)
;

