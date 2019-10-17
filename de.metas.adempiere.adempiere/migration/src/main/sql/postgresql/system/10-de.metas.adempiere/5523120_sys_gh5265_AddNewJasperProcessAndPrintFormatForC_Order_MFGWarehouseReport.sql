-- 2019-06-03T12:21:04.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541142,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2019-06-03 12:21:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','Y','N','Y','Y','@PREFIX@de/metas/docs/sales/ordercheckup_combined/report.jasper',0,'Bestellkontrolle(both)','N','N','JasperReports',TO_TIMESTAMP('2019-06-03 12:21:03','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_MFGWarehouse_Report_Combined')
;

-- 2019-06-03T12:21:04.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541142 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-06-03T12:23:53.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_PrintTableFormat_ID,AD_Table_ID,CreateCopy,Created,CreatedBy,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,JasperProcess_ID,Name,Updated,UpdatedBy) VALUES (0,0,100,540008,'SO',540097,102,100,540683,'N',TO_TIMESTAMP('2019-06-03 12:23:53','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Y','N','N','Y','Y',541142,'C_Order_MFGWarehouse_Report_Combined',TO_TIMESTAMP('2019-06-03 12:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

UPDATE C_Doc_Outbound_Config set AD_PrintFormat_ID = 540097 WHERE C_Doc_Outbound_Config_ID=540002;
