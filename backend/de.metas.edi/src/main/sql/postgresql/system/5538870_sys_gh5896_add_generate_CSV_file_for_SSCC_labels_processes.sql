-- 2019-12-12T16:05:59.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541231,'Y','de.metas.edi.process.EDI_DesadvGenerateCSV_FileForSSCC_Labels','N',TO_TIMESTAMP('2019-12-12 18:05:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','N','N','N','N','N','N','Y','Y',0,'SSCC18 labels to CSV','N','N','Java',TO_TIMESTAMP('2019-12-12 18:05:58','YYYY-MM-DD HH24:MI:SS'),100,'EDI_DesadvSSCC_ToCSV')
;

-- 2019-12-12T16:05:59.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541231 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-12-12T16:07:43.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541231,540644,540767,TO_TIMESTAMP('2019-12-12 18:07:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2019-12-12 18:07:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- 2019-12-16T16:01:01.259Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=1000,Updated=TO_TIMESTAMP('2019-12-16 18:01:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=50196
;

-- moved to a dedicated file
-- 2019-12-16T16:01:06.616Z
-- URL zum KonzeptEDI_DesadvGenerateCSV_FileForSSCC_Label
--INSERT INTO t_alter_column values('ad_sysconfig','Value','VARCHAR(1000)',null,null)
--;

-- 2019-12-13T14:05:50.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541302,'S',TO_TIMESTAMP('2019-12-13 16:05:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.handlingunit.sscc18Label.zebra.sql-select',TO_TIMESTAMP('2019-12-13 16:05:50','YYYY-MM-DD HH24:MI:SS'),100,'select no_of_labels, SSCC, order_reference, TO_CHAR(date_shipped,''DD.MM.YYYY''), GTIN, product_description, amount, weight, TO_CHAR(best_before,''YYMMDD''), lot_no,bp_address_GLN, bp_address_name1, bp_address_name2, bp_address_street, bp_address_zip_code, bp_address_city,ho_address_GLN, ho_address_name1, ho_address_name2, ho_address_street, ho_address_zip_code, ho_address_city from edi_desadvpack_sscc_label where p_instance_id = ?')
;

-- 2019-12-17T07:42:26.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541304,'S',TO_TIMESTAMP('2019-12-17 09:42:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.handlingunit.sscc18Label.zebra.header.line-2',TO_TIMESTAMP('2019-12-17 09:42:26','YYYY-MM-DD HH24:MI:SS'),100,'%END%')
;

-- 2019-12-17T07:44:32.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541305,'S',TO_TIMESTAMP('2019-12-17 09:44:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.handlingunit.sscc18Label.zebra.header.line-1',TO_TIMESTAMP('2019-12-17 09:44:32','YYYY-MM-DD HH24:MI:SS'),100,'%BTW% /AF="\\V-APSRV01\PRAGMA\ETIKETTEN\LAYOUTS\SSCC.BTW" /D="<TRIGGER FILE NAME>" /PRN="\\V-DCSRV02\ETIKETTEN01" /R=3 /P /D')
;

-- 2019-12-17T07:54:41.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541232,'Y','de.metas.edi.process.EDI_DesadvLinePackGenerateCSV_FileForSSCC_Labels','N',TO_TIMESTAMP('2019-12-17 09:54:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','N','N','N','N','N','N','Y','Y',0,'EDI_DesadvLinePackGenerateCSV_FileForSSCC_Labels','N','N','Java',TO_TIMESTAMP('2019-12-17 09:54:41','YYYY-MM-DD HH24:MI:SS'),100,'EDI_DesadvLinePackGenerateCSV_FileForSSCC_Labels')
;

-- 2019-12-17T07:54:41.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541232 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-12-17T07:55:44.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541232,540676,540768,TO_TIMESTAMP('2019-12-17 09:55:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2019-12-17 09:55:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- 2019-12-17T09:46:42.048Z
-- URL zum Konzept
UPDATE AD_Process SET Name='Create SSCC labels for Zebra printers',Updated=TO_TIMESTAMP('2019-12-17 11:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541231
;

-- 2019-12-17T09:46:54.457Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='SSCC-Etiketten für Zebra-Drucker erstellen',Updated=TO_TIMESTAMP('2019-12-17 11:46:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541231
;

-- 2019-12-17T09:46:59.904Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='SSCC-Etiketten für Zebra-Drucker erstellen',Updated=TO_TIMESTAMP('2019-12-17 11:46:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541231
;

-- 2019-12-17T09:47:11.026Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Create SSCC labels for Zebra printers',Updated=TO_TIMESTAMP('2019-12-17 11:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541231
;

-- 2019-12-17T09:47:24.160Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='SSCC-Etiketten für Zebra-Drucker erstellen',Updated=TO_TIMESTAMP('2019-12-17 11:47:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541231
;

-- 2019-12-17T09:53:52.935Z
-- URL zum Konzept
UPDATE AD_Process SET Name='Create SSCC labels for Zebra printers',Updated=TO_TIMESTAMP('2019-12-17 11:53:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541232
;

-- 2019-12-17T09:54:31.447Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='SSCC-Etiketten für Zebra-Drucker erstellen',Updated=TO_TIMESTAMP('2019-12-17 11:54:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541232
;

-- 2019-12-17T09:54:37.195Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='SSCC-Etiketten für Zebra-Drucker erstellen',Updated=TO_TIMESTAMP('2019-12-17 11:54:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541232
;

-- 2019-12-17T09:54:49.868Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='SSCC-Etiketten für Zebra-Drucker erstellen',Updated=TO_TIMESTAMP('2019-12-17 11:54:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541232
;

-- 2019-12-17T09:55:02.326Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Create SSCC labels for Zebra printers',Updated=TO_TIMESTAMP('2019-12-17 11:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541232
;

-- 2019-12-17T10:09:50.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541306,'S',TO_TIMESTAMP('2019-12-17 12:09:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.handlingunit.sscc18Label.zebra.encoding',TO_TIMESTAMP('2019-12-17 12:09:49','YYYY-MM-DD HH24:MI:SS'),100,'windows-1252')
;
