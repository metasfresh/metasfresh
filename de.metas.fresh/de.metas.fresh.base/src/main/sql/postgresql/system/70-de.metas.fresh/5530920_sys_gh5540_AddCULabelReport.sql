-- 2019-09-17T15:45:11.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,Classname,CopyFromProcess,UpdatedBy,JasperReport,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,Name,EntityType,AD_Org_ID) VALUES (0,'Y',TO_TIMESTAMP('2019-09-17 18:45:11','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-09-17 18:45:11','YYYY-MM-DD HH24:MI:SS'),'Y','Y','3','N','Y','N','de.metas.report.jasper.client.process.JasperReportStarter','N',100,'@PREFIX@de/metas/docs/label/halbfabrikat/report_cu.jasper',541195,'Halbfabrikat Etikett CU (Jasper)','N','Y','N','N','N',0,'JasperReportsSQL','Y','Halbfabrikat Etikett','de.metas.fresh',0)
;

-- 2019-09-17T15:45:11.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541195 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-09-17T15:45:45.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541195,540506,540747,TO_TIMESTAMP('2019-09-17 18:45:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y',TO_TIMESTAMP('2019-09-17 18:45:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;


-- 2019-09-17T20:07:38.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,IsActive,IsApplyToCUs,IsApplyToLUs,IsApplyToTopLevelHUsOnly,IsApplyToTUs,IsProvideAsUserAction,M_HU_Process_ID,Updated,UpdatedBy) VALUES (0,0,541195,TO_TIMESTAMP('2019-09-17 23:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','N','N','Y',540018,TO_TIMESTAMP('2019-09-17 23:07:38','YYYY-MM-DD HH24:MI:SS'),100)
;

