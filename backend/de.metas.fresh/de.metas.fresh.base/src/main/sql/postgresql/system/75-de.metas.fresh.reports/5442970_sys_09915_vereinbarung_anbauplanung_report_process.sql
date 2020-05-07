-- 18.03.2016 17:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,JasperReport,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540673,'Y','org.compiere.report.ReportStarter',TO_TIMESTAMP('2016-03-18 17:34:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','Y','N','N','N','@PREFIX@de/metas/docs/procurement_conditions/report.jasper','Vereinbarung Anbauplanung','N','S',0,0,'Java',TO_TIMESTAMP('2016-03-18 17:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Vereinbarung Anbauplanung (Jasper)')
;

-- 18.03.2016 17:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540673 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 18.03.2016 17:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsReport='Y',Updated=TO_TIMESTAMP('2016-03-18 17:34:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540673
;

-- 18.03.2016 17:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540673,540320,TO_TIMESTAMP('2016-03-18 17:35:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.flatrate','Y',TO_TIMESTAMP('2016-03-18 17:35:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.03.2016 17:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543831,0,TO_TIMESTAMP('2016-03-18 17:36:26','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','ProcessError','I',TO_TIMESTAMP('2016-03-18 17:36:26','YYYY-MM-DD HH24:MI:SS'),0,'ProcessError')
;

-- 18.03.2016 17:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543831 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

