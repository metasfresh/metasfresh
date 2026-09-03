-- 2022-02-11T09:35:19.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584982,'Y','de.metas.handlingunits.process.M_HU_Assign_QRCode','N',TO_TIMESTAMP('2022-02-11 11:35:19','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','Y','N','N','N','Y','Y',0,'Assign QR Code','json','N','S','xls','Java',TO_TIMESTAMP('2022-02-11 11:35:19','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_Assign_QRCode')
;

-- 2022-02-11T09:35:19.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584982 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-02-11T09:35:37.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='QR-Code zuweisen',Updated=TO_TIMESTAMP('2022-02-11 11:35:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584982
;

-- 2022-02-11T09:35:41.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='QR-Code zuweisen',Updated=TO_TIMESTAMP('2022-02-11 11:35:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584982
;

-- 2022-02-11T09:35:41.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='QR-Code zuweisen',Updated=TO_TIMESTAMP('2022-02-11 11:35:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584982
;

-- 2022-02-11T09:35:59.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-02-11 11:35:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584982
;

-- 2022-02-11T09:37:08.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,IsActive,IsApplyToCUs,IsApplyToLUs,IsApplyToTopLevelHUsOnly,IsApplyToTUs,IsProvideAsUserAction,M_HU_Process_ID,Updated,UpdatedBy) VALUES (0,0,584982,TO_TIMESTAMP('2022-02-11 11:37:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Y',540020,TO_TIMESTAMP('2022-02-11 11:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-11T10:32:39.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET TechnicalNote='Process that generates the QR Codes and assignes them for the selected HUs.',Updated=TO_TIMESTAMP('2022-02-11 12:32:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584982
;

-- 2022-02-11T10:45:09.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2022-02-11 12:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584982
;


-- 2022-02-11T11:36:19.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToLUs='N',Updated=TO_TIMESTAMP('2022-02-11 13:36:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540020
;

-- 2022-02-11T11:41:41.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,BarcodeScannerType,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543328,0,584982,542189,10,'qrCode','Barcode',TO_TIMESTAMP('2022-02-11 13:41:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','Y','N','Barcode',10,TO_TIMESTAMP('2022-02-11 13:41:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-11T11:41:41.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542189 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-02-11T11:57:17.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET ShowHelp='Y',Updated=TO_TIMESTAMP('2022-02-11 13:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584982
;

-- 2022-02-11T11:58:37.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM M_HU_Process WHERE M_HU_Process_ID=540020
;

-- 2022-02-11T11:59:11.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584982,540516,541060,TO_TIMESTAMP('2022-02-11 13:59:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2022-02-11 13:59:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2022-02-11T12:10:29.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2022-02-11 14:10:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541060
;

-- 2022-02-11T14:14:52.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Assign_QRCode',Updated=TO_TIMESTAMP('2022-02-11 16:14:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584982
;

