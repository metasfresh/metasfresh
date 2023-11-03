-- Value: WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig
-- Classname: de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig
-- 2023-10-13T14:35:49.418Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585326,'N','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig','N',TO_TIMESTAMP('2023-10-13 17:35:48','YYYY-MM-DD HH24:MI:SS'),100,'HUs annehmen mit veränderter Konfiguration.','U','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'HUs annehmen','json','Y','Y','Java',TO_TIMESTAMP('2023-10-13 17:35:48','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig')
;

-- 2023-10-13T14:35:49.473Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585326 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig)
-- ParameterName: IsSaveLUTUConfiguration
-- 2023-10-13T14:36:44.853Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543278,0,585326,542715,20,'IsSaveLUTUConfiguration',TO_TIMESTAMP('2023-10-13 17:36:44','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,'Y','N','Y','N','Y','N','Save configuration',5,TO_TIMESTAMP('2023-10-13 17:36:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-13T14:36:44.881Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542715 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-13T14:36:44.996Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542715
;

-- 2023-10-13T14:36:45.024Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542715, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541151
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig)
-- ParameterName: M_HU_PI_Item_Product_ID
-- 2023-10-13T14:36:45.324Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542132,0,585326,542716,30,'M_HU_PI_Item_Product_ID',TO_TIMESTAMP('2023-10-13 17:36:45','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','Y','N','Packvorschrift',10,TO_TIMESTAMP('2023-10-13 17:36:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-13T14:36:45.352Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542716 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-13T14:36:45.409Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542716
;

-- 2023-10-13T14:36:45.438Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542716, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541135
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig)
-- ParameterName: M_LU_HU_PI_ID
-- 2023-10-13T14:36:45.730Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542487,0,585326,542717,30,540396,'M_LU_HU_PI_ID',TO_TIMESTAMP('2023-10-13 17:36:45','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','Packvorschrift (LU)',20,TO_TIMESTAMP('2023-10-13 17:36:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-13T14:36:45.758Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542717 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-13T14:36:45.814Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542717
;

-- 2023-10-13T14:36:45.842Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542717, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541136
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig)
-- ParameterName: QtyCU
-- 2023-10-13T14:36:46.189Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542492,0,585326,542718,29,'QtyCU',TO_TIMESTAMP('2023-10-13 17:36:45','YYYY-MM-DD HH24:MI:SS'),100,'Menge der CUs pro Einzelgebinde (normalerweise TU)','U',0,'Y','N','Y','N','Y','N','Menge CU/TU',30,TO_TIMESTAMP('2023-10-13 17:36:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-13T14:36:46.219Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542718 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-13T14:36:46.277Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542718
;

-- 2023-10-13T14:36:46.305Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542718, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541137
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig)
-- ParameterName: QtyTU
-- 2023-10-13T14:36:46.597Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542490,0,585326,542719,29,'QtyTU',TO_TIMESTAMP('2023-10-13 17:36:46','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','Y','N','TU Anzahl',40,TO_TIMESTAMP('2023-10-13 17:36:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-13T14:36:46.625Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542719 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-13T14:36:46.683Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542719
;

-- 2023-10-13T14:36:46.709Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542719, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541138
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig)
-- ParameterName: QtyLU
-- 2023-10-13T14:36:47.036Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMin) VALUES (0,542491,0,585326,542720,29,'QtyLU',TO_TIMESTAMP('2023-10-13 17:36:46','YYYY-MM-DD HH24:MI:SS'),100,'1','@M_LU_HU_PI_ID/-1@>0','U',0,'Y','N','Y','N','N','N','LU Anzahl',50,TO_TIMESTAMP('2023-10-13 17:36:46','YYYY-MM-DD HH24:MI:SS'),100,'')
;

-- 2023-10-13T14:36:47.065Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542720 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-13T14:36:47.125Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542720
;

-- 2023-10-13T14:36:47.155Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542720, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541139
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig)
-- Table: M_HU
-- EntityType: de.metas.handlingunits
-- 2023-10-13T14:46:42.502Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585326,540516,541433,TO_TIMESTAMP('2023-10-13 17:46:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2023-10-13 17:46:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N')
;



-- Process: WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig)
-- 2023-10-13T14:58:41.592Z
UPDATE AD_Process_Trl SET Name='Receive HUs',Updated=TO_TIMESTAMP('2023-10-13 17:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585326
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig)
-- 2023-10-13T14:59:19.278Z
UPDATE AD_Process_Trl SET Description='Receive HUs using custom configuration',Updated=TO_TIMESTAMP('2023-10-13 17:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585326
;




-- Value: WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig
-- Classname: de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig
-- 2023-10-17T14:15:37.358Z
UPDATE AD_Process SET Classname='de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig', EntityType='de.metas.handlingunits', Value='WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig',Updated=TO_TIMESTAMP('2023-10-17 17:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585326
;

