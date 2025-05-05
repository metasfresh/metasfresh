-- Value: C_BPartner_InterimContract
-- Classname: de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert
-- 2023-08-02T09:38:56.291854900Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585301,'N','de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert','N',TO_TIMESTAMP('2023-08-02 12:38:56.186','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','N','N','N','N','N','Y','N','Y',0,'C_BPartner_InterimContract','json','N','N','xls','Java',TO_TIMESTAMP('2023-08-02 12:38:56.186','YYYY-MM-DD HH24:MI:SS.US'),100,'C_BPartner_InterimContract')
;

-- 2023-08-02T09:38:56.295595900Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585301 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;



-- Process: C_BPartner_InterimContract(de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert)
-- ParameterName: C_Harvesting_Calendar_ID
-- 2023-08-02T09:54:22.620258400Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581157,0,585301,542675,13,540260,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-08-02 12:54:22.499','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',10,'Y','N','Y','Y','Y','N','Erntekalender',10,TO_TIMESTAMP('2023-08-02 12:54:22.499','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-02T09:54:22.622223600Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542675 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-08-02T09:54:22.624225800Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(581157)
;

-- Process: C_BPartner_InterimContract(de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert)
-- ParameterName: C_Harvesting_Calendar_ID
-- 2023-08-02T09:57:15.389396600Z
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-08-02 12:57:15.389','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542675
;

-- Process: C_BPartner_InterimContract(de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert)
-- ParameterName: C_Harvesting_Calendar_ID
-- 2023-08-02T09:58:50.629868Z
UPDATE AD_Process_Para SET FieldLength=0,Updated=TO_TIMESTAMP('2023-08-02 12:58:50.629','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542675
;

-- Process: C_BPartner_InterimContract(de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert)
-- ParameterName: Harvesting_Year_ID
-- 2023-08-02T09:59:45.411914400Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582471,0,585301,542676,30,540133,540647,'Harvesting_Year_ID',TO_TIMESTAMP('2023-08-02 12:59:45.232','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',0,'Y','N','Y','N','N','N','Erntejahr',20,TO_TIMESTAMP('2023-08-02 12:59:45.232','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-02T09:59:45.412923300Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542676 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-08-02T09:59:45.413999800Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582471)
;

/*
 * #%L
 * de.metas.contracts
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

-- Process: C_BPartner_InterimContract(de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert)
-- ParameterName: Harvesting_Year_ID
-- 2023-08-02T09:59:54.536531600Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-08-02 12:59:54.534','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542676
;

-- Process: C_BPartner_InterimContract(de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert)
-- ParameterName: Harvesting_Year_ID
-- 2023-08-02T10:00:40.596482200Z
UPDATE AD_Process_Para SET IsEncrypted='Y',Updated=TO_TIMESTAMP('2023-08-02 13:00:40.596','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542676
;

-- Process: C_BPartner_InterimContract(de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert)
-- ParameterName: IsInterimContract
-- 2023-08-02T10:01:12.430866900Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582618,0,585301,542677,20,'IsInterimContract',TO_TIMESTAMP('2023-08-02 13:01:12.274','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','de.metas.contracts',0,'Y','N','Y','N','Y','N','IsInterimContract',30,TO_TIMESTAMP('2023-08-02 13:01:12.274','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-02T10:01:12.431949300Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542677 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-08-02T10:01:12.432903900Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582618)
;


