-- 2019-05-24T17:42:57.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541137,'Y','de.metas.replenishment.impexp.ImportReplenishment',TO_TIMESTAMP('2019-05-24 17:42:57','YYYY-MM-DD HH24:MI:SS'),100,'','D','','Y','N','Y','N','N','N','N','Y','Y','Import Replenishments','N','Y','Java',TO_TIMESTAMP('2019-05-24 17:42:57','YYYY-MM-DD HH24:MI:SS'),100,'ImportReplenishment')
;

-- 2019-05-24T17:42:57.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541137 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-05-24T17:43:58.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541137,541362,540717,TO_TIMESTAMP('2019-05-24 17:43:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2019-05-24 17:43:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;




-- 2019-05-27T09:58:23.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544922,0,TO_TIMESTAMP('2019-05-27 09:58:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Record is not valid. Warehouse , product, Level Max, Level Min and Replenish Type are mandatory fields.','E',TO_TIMESTAMP('2019-05-27 09:58:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.replenishment.impexp.ReplenishmentImportProcess.RecordNotValid')
;

-- 2019-05-27T09:58:23.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544922 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-05-27T10:04:23.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Eintrag ist nicht gültig. Lager, Produkt, Maximalwert, Minimalwert und Nachbestellungsart sind Pflichtfelder.',Updated=TO_TIMESTAMP('2019-05-27 10:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544922
;

-- 2019-05-27T10:04:32.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Eintrag ist nicht gültig. Lager, Produkt, Maximalwert, Minimalwert und Nachbestellungsart sind Pflichtfelder.',Updated=TO_TIMESTAMP('2019-05-27 10:04:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544922
;

