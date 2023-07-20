/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

-- 2023-01-18T10:56:09.572Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585185,'Y','de.metas.externalsystem.other.export.bpartner.C_BPartner_SyncTo_Other','N',TO_TIMESTAMP('2023-01-18 12:56:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'An Other senden','json','N','N','xls','Java',TO_TIMESTAMP('2023-01-18 12:56:09','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_SyncTo_Other')
;

-- 2023-01-18T10:56:09.581Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585185 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-01-18T10:56:43.710Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578728,0,585185,542455,18,541703,'ExternalSystem_Config_ID',TO_TIMESTAMP('2023-01-18 12:56:43','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','External System Config',10,TO_TIMESTAMP('2023-01-18 12:56:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-18T10:56:43.719Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542455 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-01-18T11:00:57.882Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585185,291,541331,TO_TIMESTAMP('2023-01-18 13:00:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-01-18 13:00:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2023-01-25T06:45:57.265Z
UPDATE AD_Process_Trl SET Name='Send to Other',Updated=TO_TIMESTAMP('2023-01-25 08:45:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585185
;

-- 2023-01-25T06:46:16.515Z
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2023-01-25 08:46:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585185
;


