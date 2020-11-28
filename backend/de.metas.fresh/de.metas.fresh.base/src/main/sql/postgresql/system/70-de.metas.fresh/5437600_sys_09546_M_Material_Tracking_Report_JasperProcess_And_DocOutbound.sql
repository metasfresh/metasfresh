-- 15.12.2015 12:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540633,'N',TO_TIMESTAMP('2015-12-15 12:02:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking.ch.lagerkonf','Y','N','N','N','Y','N',0,'M_Material_Tracking_Report','N','Y',0,0,'Java',TO_TIMESTAMP('2015-12-15 12:02:24','YYYY-MM-DD HH24:MI:SS'),100,'M_Material_Tracking_Report')
;

-- 15.12.2015 12:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540633 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 15.12.2015 12:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/materialtracking/report.jasper',Updated=TO_TIMESTAMP('2015-12-15 12:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540633
;

-- 15.12.2015 12:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540633,540692,TO_TIMESTAMP('2015-12-15 12:07:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking.ch.lagerkonf','Y',TO_TIMESTAMP('2015-12-15 12:07:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.12.2015 12:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='org.compiere.report.ReportStarter',Updated=TO_TIMESTAMP('2015-12-15 12:11:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540633
;

-- 15.12.2015 13:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_PrintTableFormat_ID,AD_Table_ID,CreateCopy,Created,CreatedBy,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,JasperProcess_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,100,130,'None',540048,102,100,540692,'N',TO_TIMESTAMP('2015-12-15 13:43:58','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Y','N','N','Y','Y',540633,'M_Material_Tracking_Report',TO_TIMESTAMP('2015-12-15 13:43:58','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 15.12.2015 13:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Doc_Outbound_Config (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,AD_Table_ID,C_Doc_Outbound_Config_ID,Created,CreatedBy,IsActive,IsCreatePrintJob,IsDirectEnqueue,Updated,UpdatedBy) VALUES (1000000,1000000,540048,540692,540005,TO_TIMESTAMP('2015-12-15 13:45:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N',TO_TIMESTAMP('2015-12-15 13:45:40','YYYY-MM-DD HH24:MI:SS'),100)
;