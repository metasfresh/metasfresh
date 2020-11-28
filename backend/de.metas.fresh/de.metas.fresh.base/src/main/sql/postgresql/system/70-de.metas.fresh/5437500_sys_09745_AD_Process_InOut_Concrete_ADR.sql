-- 25.01.2016 14:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540642,'org.compiere.report.ReportStarter','N',TO_TIMESTAMP('2016-01-25 14:39:47','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Y','N','Y','N','@PREFIX@de/metas/docs/sales/inout_concrete_adr/report.jasper',0,'Lieferschein mit ADR Attributwert','N','S',0,0,'Java',TO_TIMESTAMP('2016-01-25 14:39:47','YYYY-MM-DD HH24:MI:SS'),100,'Lieferschein mit ADR Attributwert')
;

-- 25.01.2016 14:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540642 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 25.01.2016 14:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2016-01-25 14:40:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540642
;

-- 25.01.2016 15:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Lieferschein mit ADR Attributwert (Jasper)', Value='Lieferschein mit ADR Attributwert (Jasper)',Updated=TO_TIMESTAMP('2016-01-25 15:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540642
;

-- 25.01.2016 15:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540642
;
