/*
 * #%L
 * de.metas.externalsystem
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

-- "Change EPCIS Export Status" shipment-header process on M_InOut, mirroring the EDI
-- ChangeEDI_ExportStatus_M_InOut_{Grid,Single}View pair. Lets an operator change a shipment's EPCIS
-- scripted-export status (target: "Noch nicht gesendet" P / "Soll nicht gesendet werden" N) from any
-- current state by writing a new, process-instance-stamped attempt row (who/when audit); setting P/N
-- clears the effective in-flight status so the reverse/reactivate guard releases as a consequence.
--   * GridView  (AD_Process 585645): multi-select shipments in the M_InOut view  -> WEBUI_ViewAction
--   * SingleView(AD_Process 585646): single shipment document                    -> WEBUI_DocumentAction
-- Target-status parameter uses the existing EPCIS ExportStatus ref-list (AD_Reference 542104) and the
-- shared "ExportStatus" element (AD_Element 577791) -- no new list value, no new element.

------------------------------------------------------------------------------------------------------
-- 1) GridView process (multi-select, view action)
------------------------------------------------------------------------------------------------------
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585645 /*From ID Server*/,'Y','de.metas.ui.web.externalsystem.ChangeEpcisExportStatus_M_InOut_GridView','N',TO_TIMESTAMP('2026-07-21 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'EPCIS-Status ändern','json','N','N','xls','Java',TO_TIMESTAMP('2026-07-21 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'ChangeEpcisExportStatus_M_InOut_GridView')
;

INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585645
AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

UPDATE AD_Process_Trl SET Name='Change EPCIS Export Status', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-21 09:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585645 AND AD_Language='en_US'
;
UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-21 09:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585645 AND AD_Language IN ('de_DE','de_CH')
;

------------------------------------------------------------------------------------------------------
-- 2) SingleView process (single shipment, document action)
------------------------------------------------------------------------------------------------------
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585646 /*From ID Server*/,'Y','de.metas.ui.web.externalsystem.ChangeEpcisExportStatus_M_InOut_SingleView','N',TO_TIMESTAMP('2026-07-21 09:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'EPCIS-Status ändern','json','N','N','xls','Java',TO_TIMESTAMP('2026-07-21 09:00:10','YYYY-MM-DD HH24:MI:SS'),100,'ChangeEpcisExportStatus_M_InOut_SingleView')
;

INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585646
AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

UPDATE AD_Process_Trl SET Name='Change EPCIS Export Status', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-21 09:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585646 AND AD_Language='en_US'
;
UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-21 09:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585646 AND AD_Language IN ('de_DE','de_CH')
;

------------------------------------------------------------------------------------------------------
-- 3) Target-status parameter (List, EPCIS ExportStatus ref-list 542104) -- one per process
--    ColumnName 'ExportStatus' matches the @Param parameterName; centrally maintained from element 577791.
------------------------------------------------------------------------------------------------------
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy)
VALUES (0,577791,0,585645,543271 /*From ID Server*/,17,542104,'ExportStatus',TO_TIMESTAMP('2026-07-21 09:00:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',1,'Y','N','Y','N','Y','N','Export Status',10,'N',TO_TIMESTAMP('2026-07-21 09:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543271
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy)
VALUES (0,577791,0,585646,543272 /*From ID Server*/,17,542104,'ExportStatus',TO_TIMESTAMP('2026-07-21 09:00:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',1,'Y','N','Y','N','Y','N','Export Status',10,'N',TO_TIMESTAMP('2026-07-21 09:00:25','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543272
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

------------------------------------------------------------------------------------------------------
-- 4) Attach the processes to the M_InOut (AD_Table 319) view/document actions
------------------------------------------------------------------------------------------------------
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default)
VALUES (0,0,585645,319,541657 /*From ID Server*/,TO_TIMESTAMP('2026-07-21 09:00:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2026-07-21 09:00:30','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N')
;
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default)
VALUES (0,0,585646,319,541658 /*From ID Server*/,TO_TIMESTAMP('2026-07-21 09:00:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2026-07-21 09:00:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;
