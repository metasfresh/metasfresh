-- 29.01.2016 17:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540650,'de.metas.banking.process.C_PaySelection_ReActivate','N',TO_TIMESTAMP('2016-01-29 17:38:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N','N','N','N','N',0,'Zahlung anweisen reaktivieren','N','N',0,0,'Java',TO_TIMESTAMP('2016-01-29 17:38:06','YYYY-MM-DD HH24:MI:SS'),100,'C_PaySelection_ReActivate')
;

-- 29.01.2016 17:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540650 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 29.01.2016 17:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540650,426,TO_TIMESTAMP('2016-01-29 17:38:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y',TO_TIMESTAMP('2016-01-29 17:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.01.2016 18:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543768,0,TO_TIMESTAMP('2016-01-29 18:29:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Reaktivierung nicht möglich. Die Zahlung anweisen Zeile {1} existiert bereits im Bankauszug: {2}','E',TO_TIMESTAMP('2016-01-29 18:29:11','YYYY-MM-DD HH24:MI:SS'),100,'CannotReactivate_PaySelectionLineInBankStatement')
;

-- 29.01.2016 18:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543768 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

