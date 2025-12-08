-- Value: M_Inventory_RecomputeCosts
-- Classname: de.metas.inventory.process.M_Inventory_RecomputeCosts
-- 2023-06-26T15:44:18.203Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585275,'Y','de.metas.inventory.process.M_Inventory_RecomputeCosts','N',TO_TIMESTAMP('2023-06-26 18:44:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Recompute cost for products belonging to selected inventorie','json','N','N','xls','Java',TO_TIMESTAMP('2023-06-26 18:44:18','YYYY-MM-DD HH24:MI:SS'),100,'M_Inventory_RecomputeCosts')
;

-- 2023-06-26T15:44:18.204Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585275 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: M_Inventory_RecomputeCosts
-- Classname: de.metas.inventory.process.M_Inventory_RecomputeCosts
-- 2023-06-26T15:45:08.849Z
UPDATE AD_Process SET Name='Recompute costs',Updated=TO_TIMESTAMP('2023-06-26 18:45:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585275
;

-- Value: M_Inventory_RecomputeCosts
-- Classname: de.metas.inventory.process.M_Inventory_RecomputeCosts
-- 2023-06-26T15:45:23.005Z
UPDATE AD_Process SET Description='Recompute cost for products belonging to selected inventories',Updated=TO_TIMESTAMP('2023-06-26 18:45:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585275
;

-- Value: M_Inventory_RecomputeCosts
-- Classname: de.metas.inventory.process.M_Inventory_RecomputeCosts
-- 2023-06-26T15:45:37.386Z
UPDATE AD_Process SET Description='Berechnen Sie die Kosten für Produkte, die zu ausgewählten Beständen gehören, neu', Help=NULL, Name='Kosten neu berechnen',Updated=TO_TIMESTAMP('2023-06-26 18:45:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585275
;

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- 2023-06-26T15:45:37.381Z
UPDATE AD_Process_Trl SET Description='Berechnen Sie die Kosten für Produkte, die zu ausgewählten Beständen gehören, neu', IsTranslated='Y', Name='Kosten neu berechnen',Updated=TO_TIMESTAMP('2023-06-26 18:45:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585275
;

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- 2023-06-26T15:46:09.618Z
UPDATE AD_Process_Trl SET Description='Recompute cost for products belonging to selected inventories', IsTranslated='Y', Name='Recompute costs',Updated=TO_TIMESTAMP('2023-06-26 18:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585275
;

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- 2023-06-26T15:46:21.165Z
UPDATE AD_Process_Trl SET Description='Berechnen Sie die Kosten für Produkte, die zu ausgewählten Beständen gehören, neu',Updated=TO_TIMESTAMP('2023-06-26 18:46:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585275
;

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- 2023-06-26T15:46:29.062Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Kosten neu berechnen',Updated=TO_TIMESTAMP('2023-06-26 18:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585275
;

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- Table: M_Inventory
-- EntityType: D
-- 2023-06-26T15:50:42.506Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585275,321,541388,TO_TIMESTAMP('2023-06-26 18:50:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-06-26 18:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- ParameterName: C_AcctSchema_ID
-- 2023-06-27T13:16:10.950Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,585275,542650,19,106,'C_AcctSchema_ID',TO_TIMESTAMP('2023-06-27 16:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','D',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','Y','N','Buchführungs-Schema',10,TO_TIMESTAMP('2023-06-27 16:16:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-27T13:16:10.957Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542650 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

