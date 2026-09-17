-- Value: ChangeEDI_ExportStatus_M_InOut_GridView
-- Classname: de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_GridView
-- 2026-02-26T14:18:23.679Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585582,'Y','de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_GridView','N',TO_TIMESTAMP('2026-02-26 14:18:23.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'EDI-Status ändern','json','N','N','xls','Java',TO_TIMESTAMP('2026-02-26 14:18:23.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ChangeEDI_ExportStatus_M_InOut_GridView')
;

-- 2026-02-26T14:18:23.698Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585582 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ChangeEDI_ExportStatus_M_InOut_GridView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_GridView)
-- 2026-02-26T14:18:45.061Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Update EDI Export Status',Updated=TO_TIMESTAMP('2026-02-26 14:18:45.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585582
;

-- 2026-02-26T14:18:45.065Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: ChangeEDI_ExportStatus_M_InOut_GridView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_GridView)
-- 2026-02-26T14:18:46.006Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-26 14:18:46.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585582
;

-- Process: ChangeEDI_ExportStatus_M_InOut_GridView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_GridView)
-- 2026-02-26T14:18:47.823Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-26 14:18:47.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585582
;

-- Process: ChangeEDI_ExportStatus_M_InOut_GridView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_GridView)
-- ParameterName: EDI_ExportStatus
-- 2026-02-26T14:20:01.576Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,541997,0,585582,543130,17,540381,'EDI_ExportStatus',TO_TIMESTAMP('2026-02-26 14:20:01.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',1,'Y','N','Y','N','N','N','EDI-Sendestatus',10,'N',TO_TIMESTAMP('2026-02-26 14:20:01.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-26T14:20:01.589Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543130 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: ChangeEDI_ExportStatus_M_InOut_GridView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_GridView)
-- ParameterName: EDI_ExportStatus
-- 2026-02-26T14:21:00.306Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-02-26 14:21:00.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543130
;

-- Process: ChangeEDI_ExportStatus_M_InOut_GridView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_GridView)
-- Table: M_InOut
-- EntityType: de.metas.esb.edi
-- 2026-02-26T14:23:13.586Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585582,319,541619,TO_TIMESTAMP('2026-02-26 14:23:13.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2026-02-26 14:23:13.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Y','N','N')
;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Value: ChangeEDI_ExportStatus_M_InOut_SingleView
-- Classname: de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_SingleView
-- 2026-02-26T14:24:07.493Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585583,'Y','de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_SingleView','N',TO_TIMESTAMP('2026-02-26 14:24:07.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'EDI-Status ändern','json','N','N','xls','Java',TO_TIMESTAMP('2026-02-26 14:24:07.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ChangeEDI_ExportStatus_M_InOut_SingleView')
;

-- 2026-02-26T14:24:07.494Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585583 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ChangeEDI_ExportStatus_M_InOut_SingleView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_SingleView)
-- 2026-02-26T14:24:47.340Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Update EDI Export Status',Updated=TO_TIMESTAMP('2026-02-26 14:24:47.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585583
;

-- 2026-02-26T14:24:47.341Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: ChangeEDI_ExportStatus_M_InOut_SingleView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_SingleView)
-- 2026-02-26T14:24:47.906Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-26 14:24:47.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585583
;

-- Process: ChangeEDI_ExportStatus_M_InOut_SingleView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_SingleView)
-- 2026-02-26T14:24:49.442Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-26 14:24:49.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585583
;

-- Process: ChangeEDI_ExportStatus_M_InOut_SingleView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_SingleView)
-- ParameterName: EDI_ExportStatus
-- 2026-02-26T14:25:28.815Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,541997,0,585583,543131,17,540381,'EDI_ExportStatus',TO_TIMESTAMP('2026-02-26 14:25:28.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',1,'Y','N','Y','N','N','N','EDI-Sendestatus',10,'N',TO_TIMESTAMP('2026-02-26 14:25:28.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-26T14:25:28.821Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543131 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: ChangeEDI_ExportStatus_M_InOut_SingleView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_SingleView)
-- ParameterName: EDI_ExportStatus
-- 2026-02-26T14:25:35.549Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-02-26 14:25:35.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543131
;

-- Process: ChangeEDI_ExportStatus_M_InOut_SingleView(de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatus_M_InOut_SingleView)
-- Table: M_InOut
-- EntityType: de.metas.esb.edi
-- 2026-02-26T14:26:21.668Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585583,319,541620,TO_TIMESTAMP('2026-02-26 14:26:21.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2026-02-26 14:26:21.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N')
;

