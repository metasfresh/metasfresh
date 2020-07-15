-- 2020-05-25T14:01:41.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584695,'N','de.metas.report.jasper.client.process.JasperReportStarter',TO_TIMESTAMP('2020-05-25 17:01:41','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Y','Y','N','N','Y','N','Y','Y','@PREFIX@de/metas/docs/sales/distributionorder/report.jasper','Distributionaufstrag (Jasper)','N','N','JasperReportsSQL',TO_TIMESTAMP('2020-05-25 17:01:41','YYYY-MM-DD HH24:MI:SS'),100,'Distributionaufstrag (Jasper)')
;

-- 2020-05-25T14:01:41.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584695 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-05-25T14:04:45.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Distributionsaufstrag (Jasper)', Value='Distributionsaufstrag (Jasper)',Updated=TO_TIMESTAMP('2020-05-25 17:04:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584695
;

-- 2020-05-25T14:08:31.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_Table_ID,CreateCopy,Created,CreatedBy,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,JasperProcess_ID,Name,Updated,UpdatedBy) VALUES (0,0,100,540006,'None',540106,102,53037,'N',TO_TIMESTAMP('2020-05-25 17:08:31','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Y','N','N','Y','Y',584695,'Distributionsauftrag',TO_TIMESTAMP('2020-05-25 17:08:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-25T14:09:46.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AD_PrintFormat_ID=540106,Updated=TO_TIMESTAMP('2020-05-25 17:09:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=53044
;

-- 2020-05-25T14:12:44.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormat SET AD_PrintTableFormat_ID=100,Updated=TO_TIMESTAMP('2020-05-25 17:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540106
;

-- 2020-05-25T15:20:38.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='Y',Updated=TO_TIMESTAMP('2020-05-25 18:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=53044
;

-- 2020-05-25T15:26:07.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormat SET AD_PrintFont_ID=540008,Updated=TO_TIMESTAMP('2020-05-25 18:26:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540106
;

-- 2020-05-25T15:33:48.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AD_PrintFormat_ID=NULL,Updated=TO_TIMESTAMP('2020-05-25 18:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=53044
;

-- 2020-05-25T15:34:06.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_PrintFormat WHERE AD_PrintFormat_ID=540106
;

-- 2020-05-25T15:35:25.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormat SET JasperProcess_ID=584695,Updated=TO_TIMESTAMP('2020-05-25 18:35:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=50011
;

-- 2020-05-25T15:39:02.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AD_PrintFormat_ID=50011,Updated=TO_TIMESTAMP('2020-05-25 18:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=53044
;

-- 2020-05-25T15:50:31.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='1',Updated=TO_TIMESTAMP('2020-05-25 18:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53039
;

-- 2020-05-25T16:54:37.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormat SET Description='Distribution Order Header', Name='DistributionOrder_Header ',Updated=TO_TIMESTAMP('2020-05-25 19:54:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=50011
;

