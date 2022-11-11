-- Value: Call_External_System_Metasfresh
-- Classname: de.metas.externalsystem.process.InvokeMetasfreshAction
-- 2022-11-07T17:06:09.683Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585141,'Y','de.metas.externalsystem.process.InvokeMetasfreshAction','N',TO_TIMESTAMP('2022-11-07 19:06:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','Y','N','N','N','Y','Y',0,'Invoke Metasfresh','json','N','N','xls','Java',TO_TIMESTAMP('2022-11-07 19:06:09','YYYY-MM-DD HH24:MI:SS'),100,'Call_External_System_Metasfresh')
;

-- 2022-11-07T17:06:09.688Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585141 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Name: External_Request_Metasfresh
-- 2022-11-07T17:08:16.820Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541688,TO_TIMESTAMP('2022-11-07 19:08:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','External_Request_Metasfresh',TO_TIMESTAMP('2022-11-07 19:08:16','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-11-07T17:08:16.825Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541688 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: External_Request_Metasfresh
-- Value: enableRestAPI
-- ValueName: API Starten
-- 2022-11-07T17:14:46.916Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543333,541688,TO_TIMESTAMP('2022-11-07 19:14:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','API Starten',TO_TIMESTAMP('2022-11-07 19:14:46','YYYY-MM-DD HH24:MI:SS'),100,'enableRestAPI','API Starten')
;

-- 2022-11-07T17:14:46.918Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543333 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External_Request_Metasfresh -> enableRestAPI_API Starten
-- 2022-11-07T17:15:06.155Z
UPDATE AD_Ref_List_Trl SET Name='Start API',Updated=TO_TIMESTAMP('2022-11-07 19:15:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543333
;

-- Reference: External_Request_Metasfresh
-- Value: disableRestAPI
-- ValueName: API Stoppen
-- 2022-11-07T17:15:26.282Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543334,541688,TO_TIMESTAMP('2022-11-07 19:15:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','API Stoppen',TO_TIMESTAMP('2022-11-07 19:15:26','YYYY-MM-DD HH24:MI:SS'),100,'disableRestAPI','API Stoppen')
;

-- 2022-11-07T17:15:26.283Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543334 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External_Request_Metasfresh -> disableRestAPI_API Stoppen
-- 2022-11-07T17:15:34.097Z
UPDATE AD_Ref_List_Trl SET Name='Stop API',Updated=TO_TIMESTAMP('2022-11-07 19:15:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543334
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- ParameterName: External_Request
-- 2022-11-07T17:16:10.192Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578757,0,585141,542361,17,'External_Request',TO_TIMESTAMP('2022-11-07 19:16:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',30,'Y','N','Y','N','Y','N','Befehl',10,TO_TIMESTAMP('2022-11-07 19:16:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T17:16:10.198Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542361 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- ParameterName: External_Request
-- 2022-11-07T17:16:17.136Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541688,Updated=TO_TIMESTAMP('2022-11-07 19:16:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542361
;

