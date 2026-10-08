-- Run mode: SWING_CLIENT

-- Value: M_PickingSlot_PrintSingleQRCode
-- Classname: de.metas.picking.process.M_PickingSlot_PrintSingleQRCode
-- 2026-01-21T17:27:24.637Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585560,'Y','de.metas.picking.process.M_PickingSlot_PrintSingleQRCode','N',TO_TIMESTAMP('2026-01-21 17:27:24.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.picking','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'QR-Code drucken','json','N','N','xls','Java',TO_TIMESTAMP('2026-01-21 17:27:24.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_PickingSlot_PrintSingleQRCode')
;

-- 2026-01-21T17:27:24.649Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585560 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_PickingSlot_PrintSingleQRCode(de.metas.picking.process.M_PickingSlot_PrintSingleQRCode)
-- ParameterName: RenderedQRCode
-- 2026-01-21T17:27:53.710Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,580597,0,585560,543105,36,'RenderedQRCode',TO_TIMESTAMP('2026-01-21 17:27:53.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.','@RenderedQRCode/''''@!''''','de.metas.picking',0,'Y','N','Y','N','N','N','Rendered QR Code','1=1',10,TO_TIMESTAMP('2026-01-21 17:27:53.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:27:53.713Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543105 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_PickingSlot_PrintSingleQRCode(de.metas.picking.process.M_PickingSlot_PrintSingleQRCode)
-- Table: M_PickingSlot
-- EntityType: de.metas.picking
-- 2026-01-21T17:28:26.115Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585560,540543,541601,TO_TIMESTAMP('2026-01-21 17:28:25.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.picking','Y',TO_TIMESTAMP('2026-01-21 17:28:25.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N')
;

-- Process: M_PickingSlot_PrintQRCodes(de.metas.picking.process.M_PickingSlot_PrintQRCodes)
-- Table: M_PickingSlot
-- EntityType: D
-- 2026-01-21T17:28:54.410Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541576
;

-- Run mode: SWING_CLIENT

-- Process: M_PickingSlot_PrintSingleQRCode(de.metas.picking.process.M_PickingSlot_PrintSingleQRCode)
-- 2026-01-21T18:07:30.159Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-21 18:07:30.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585560
;

-- Process: M_PickingSlot_PrintSingleQRCode(de.metas.picking.process.M_PickingSlot_PrintSingleQRCode)
-- 2026-01-21T18:07:32.398Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-21 18:07:32.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585560
;

-- Process: M_PickingSlot_PrintSingleQRCode(de.metas.picking.process.M_PickingSlot_PrintSingleQRCode)
-- 2026-01-21T18:07:38.968Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Print QR Code',Updated=TO_TIMESTAMP('2026-01-21 18:07:38.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585560
;

-- 2026-01-21T18:07:38.969Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_PickingSlot_PrintSingleQRCode(de.metas.picking.process.M_PickingSlot_PrintSingleQRCode)
-- 2026-01-21T18:07:44.707Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Print QR Code',Updated=TO_TIMESTAMP('2026-01-21 18:07:44.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Process_ID=585560
;

-- 2026-01-21T18:07:44.709Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

