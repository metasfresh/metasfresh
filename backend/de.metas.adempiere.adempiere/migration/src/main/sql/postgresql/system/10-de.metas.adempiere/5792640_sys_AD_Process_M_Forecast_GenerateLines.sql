-- Migration: AD_Process M_Forecast_GenerateLines
-- Forecast Generator feature: register the process and its parameters

-- AD_Element for process
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584636,0,'M_Forecast_GenerateLines',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'D','Y','Prognosezeilen generieren','Prognosezeilen generieren',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584636
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Generate Forecast Lines', PrintName='Generate Forecast Lines', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Element_ID=584636 AND AD_Language='en_US';
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584636,'en_US');

-- AD_Process: M_Forecast_GenerateLines
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,ShowHelp,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585593,'de.metas.mforecast.process.M_Forecast_GenerateLines',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'D','Y','N','N','N','N','N','N','N','Y',0,'Prognosezeilen generieren','S','Java',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'M_Forecast_GenerateLines');

INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585593
AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID);

-- AD_Process_Para: M_Product_Category_ID
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,453,0,585593,543143,19,'M_Product_Category_ID',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'D',10,'Y','N','Y','N','N','N','Produkt-Kategorie',10,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543143
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

-- AD_Process_Para: M_Product_ID
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,454,0,585593,543144,30,'M_Product_ID',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'D',10,'Y','Y','Y','N','N','N','Produkt',20,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543144
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

-- AD_Process_Para: Forecast_CalculationMethod
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,584630,0,585593,543145,17,542072,'Forecast_CalculationMethod',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'D',1,'Y','N','Y','N','N','N','Berechnungsmethode Prognose',30,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543145
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

-- AD_Process_Para: Forecast_PrecisionUnit
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,584631,0,585593,543146,17,542073,'Forecast_PrecisionUnit',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'D',1,'Y','N','Y','N','N','N','Prognose Zeiteinheit',40,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543146
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

-- AD_Process_Para: IsDeleteExistingLines (reuse existing AD_Element for IsDeleteExistingLines)
-- AD_Element 584635 for this process param
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584635,0,'IsDeleteExistingLines',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'D','Y','Bestehende Zeilen löschen','Bestehende Zeilen löschen',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584635
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);
UPDATE AD_Element_Trl SET Name='Delete existing lines', PrintName='Delete existing lines', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Element_ID=584635 AND AD_Language='en_US';
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584635,'en_US');

INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,584635,0,585593,543147,20,'IsDeleteExistingLines',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'N','D',1,'Y','N','Y','N','N','N','Bestehende Zeilen löschen',50,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543147
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

-- AD_Table_Process: Link process to M_Forecast table (AD_Table_ID=720)
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default)
VALUES (0,0,585593,720,541630,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'D','Y',TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,'Y','N','N','N','N');
