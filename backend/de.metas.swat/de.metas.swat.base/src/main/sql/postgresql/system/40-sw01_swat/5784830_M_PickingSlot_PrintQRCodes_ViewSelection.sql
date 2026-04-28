-- Run mode: SWING_CLIENT

-- Value: M_PickingSlot_PrintQRCodes_ViewSelection
-- Classname: de.metas.ui.web.picking.process.M_PickingSlot_PrintQRCodes_ViewSelection
-- 2026-01-21T17:44:48.981Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585561,'Y','de.metas.ui.web.picking.process.M_PickingSlot_PrintQRCodes_ViewSelection','N',TO_TIMESTAMP('2026-01-21 17:44:48.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.picking','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'QR-Codes drucken','json','N','N','xls','Java',TO_TIMESTAMP('2026-01-21 17:44:48.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_PickingSlot_PrintQRCodes_ViewSelection')
;

-- 2026-01-21T17:44:48.982Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585561 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_PickingSlot_PrintQRCodes_ViewSelection(de.metas.ui.web.picking.process.M_PickingSlot_PrintQRCodes_ViewSelection)
-- ParameterName: RenderedQRCode
-- 2026-01-21T17:45:12.196Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,580597,0,585561,543106,36,'RenderedQRCode',TO_TIMESTAMP('2026-01-21 17:45:12.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.','@RenderedQRCode/''''@!''''','de.metas.picking',0,'Y','N','Y','N','N','N','Rendered QR Code','1=1',10,TO_TIMESTAMP('2026-01-21 17:45:12.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:45:12.209Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543106 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_PickingSlot_PrintQRCodes_ViewSelection(de.metas.ui.web.picking.process.M_PickingSlot_PrintQRCodes_ViewSelection)
-- Table: M_PickingSlot
-- EntityType: de.metas.picking
-- 2026-01-21T17:46:20.123Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585561,540543,541602,TO_TIMESTAMP('2026-01-21 17:46:19.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.picking','Y',TO_TIMESTAMP('2026-01-21 17:46:19.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Y','Y','N')
;

-- Process: M_PickingSlot_PrintQRCodes_ViewSelection(de.metas.ui.web.picking.process.M_PickingSlot_PrintQRCodes_ViewSelection)
-- 2026-01-21T17:46:25.323Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-21 17:46:25.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585561
;

-- Process: M_PickingSlot_PrintQRCodes_ViewSelection(de.metas.ui.web.picking.process.M_PickingSlot_PrintQRCodes_ViewSelection)
-- 2026-01-21T17:46:26.689Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-21 17:46:26.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585561
;

-- Process: M_PickingSlot_PrintQRCodes_ViewSelection(de.metas.ui.web.picking.process.M_PickingSlot_PrintQRCodes_ViewSelection)
-- 2026-01-21T17:46:33.930Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Print QR Codes',Updated=TO_TIMESTAMP('2026-01-21 17:46:33.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585561
;

-- 2026-01-21T17:46:33.931Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

