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

-- Value: SAP_Export_FactAcct_MInOut
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2023-02-28T11:48:39.238Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JSONPath,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585230,'Y','de.metas.postgrest.process.PostgRESTProcessExecutor','N',TO_TIMESTAMP('2023-02-28 13:48:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Y','N','Y','/fact_acct?ad_table_id=eq.319&record_id=eq.@Record_ID/-1@',0,'SAP_Export_FactAcct_MInOut','json','N','N','xls','PostgREST',TO_TIMESTAMP('2023-02-28 13:48:39','YYYY-MM-DD HH24:MI:SS'),100,'SAP_Export_FactAcct_MInOut')
;

-- 2023-02-28T11:48:39.240Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585230 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: SAP_Export_FactAcct_MInOut(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: M_InOut_ID
-- 2023-02-28T11:49:59.300Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1025,0,585230,542565,30,'M_InOut_ID',TO_TIMESTAMP('2023-02-28 13:49:59','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','de.metas.externalsystem',100,'The Material Shipment / Receipt ','Y','N','Y','N','N','N','Shipment/ Receipt',10,TO_TIMESTAMP('2023-02-28 13:49:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T11:49:59.303Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542565 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-02-28T11:49:59.308Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(1025) 
;

-- Process: SAP_Export_FactAcct_MInOut(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: M_InOut_ID
-- 2023-02-28T11:50:07.596Z
UPDATE AD_Process_Para SET FieldLength=20,Updated=TO_TIMESTAMP('2023-02-28 13:50:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542565
;

-- Value: SAP_Export_FactAcct_MInOut
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2023-02-28T11:50:59.099Z
UPDATE AD_Process SET JSONPath='/fact_acct?ad_table_id=eq.319&record_id=eq.@M_InOut_ID/-1@',Updated=TO_TIMESTAMP('2023-02-28 13:50:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585230
;

