-- Run mode: SWING_CLIENT

-- Value: ModCntr_Specific_Price_Update_Selection
-- Classname: de.metas.contracts.modular.process.ModCntr_Specific_Price_Update
-- 2024-06-18T15:09:08.459Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585401,'Y','de.metas.contracts.modular.process.ModCntr_Specific_Price_Update','N',TO_TIMESTAMP('2024-06-18 18:09:07.4','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Preis aktualisieren - selektion','json','N','N','xls','Updates the selected ModCntr_Specific_Price record with the given price/PriceUOM/Currency','Java',TO_TIMESTAMP('2024-06-18 18:09:07.4','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Specific_Price_Update_Selection')
;

-- 2024-06-18T15:09:08.467Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585401 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: ModCntr_Specific_Price_Update_Selection
-- Classname: de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection
-- 2024-06-18T15:09:12.010Z
UPDATE AD_Process SET Classname='de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection',Updated=TO_TIMESTAMP('2024-06-18 18:09:12.008','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585401
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: Price
-- 2024-06-18T15:10:23.597Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1416,0,585401,542846,12,'Price',TO_TIMESTAMP('2024-06-18 18:10:23.454','YYYY-MM-DD HH24:MI:SS.US'),100,'Preis','de.metas.contracts',0,'Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','Y','N','Y','N','Preis',10,TO_TIMESTAMP('2024-06-18 18:10:23.454','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-18T15:10:23.598Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542846 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-06-18T15:10:23.623Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(1416)
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: C_UOM_ID
-- 2024-06-18T15:10:47.167Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,215,0,585401,542847,30,114,'C_UOM_ID',TO_TIMESTAMP('2024-06-18 18:10:47.042','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','de.metas.contracts',0,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','Y','N','Maßeinheit',20,TO_TIMESTAMP('2024-06-18 18:10:47.042','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-18T15:10:47.168Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542847 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-06-18T15:10:47.171Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(215)
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: C_Currency_ID
-- 2024-06-18T15:11:13.786Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,193,0,585401,542848,30,112,'C_Currency_ID',TO_TIMESTAMP('2024-06-18 18:11:13.658','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','de.metas.contracts',0,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','Y','N','Währung',30,TO_TIMESTAMP('2024-06-18 18:11:13.658','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-18T15:11:13.787Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542848 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-06-18T15:11:13.788Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(193)
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: PriceUOM
-- 2024-06-18T15:11:59.994Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582754,0,585401,542849,30,114,'PriceUOM',TO_TIMESTAMP('2024-06-18 18:11:59.874','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',0,'Y','N','Y','N','N','N','Preiseinheit',40,TO_TIMESTAMP('2024-06-18 18:11:59.874','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-18T15:11:59.996Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542849 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-06-18T15:11:59.997Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582754)
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: PriceUOM
-- 2024-06-18T15:12:40.080Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542849
;

-- 2024-06-18T15:12:40.087Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542849
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: MinValue
-- 2024-06-18T15:13:04.731Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53400,0,585401,542850,12,'MinValue',TO_TIMESTAMP('2024-06-18 18:13:04.618','YYYY-MM-DD HH24:MI:SS.US'),100,'EE02',0,'Y','N','Y','N','Y','N','Mindestwert',40,TO_TIMESTAMP('2024-06-18 18:13:04.618','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-18T15:13:04.733Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542850 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-06-18T15:13:04.735Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(53400)
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: ModCntr_Type_ID
-- 2024-06-18T15:13:59.786Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582395,0,585401,542851,30,541861,'ModCntr_Type_ID',TO_TIMESTAMP('2024-06-18 18:13:59.673','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',0,'Y','N','Y','N','N','N','Vertragsbaustein Typ',50,TO_TIMESTAMP('2024-06-18 18:13:59.673','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-18T15:13:59.787Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542851 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-06-18T15:13:59.789Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582395)
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: ModCntr_Type_ID
-- 2024-06-18T15:16:43.207Z
UPDATE AD_Process_Para SET SeqNo=45,Updated=TO_TIMESTAMP('2024-06-18 18:16:43.207','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542851
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: MinValue
-- 2024-06-18T15:16:47.617Z
UPDATE AD_Process_Para SET SeqNo=50,Updated=TO_TIMESTAMP('2024-06-18 18:16:47.617','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542850
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: ModCntr_Type_ID
-- 2024-06-18T15:16:59.080Z
UPDATE AD_Process_Para SET SeqNo=40,Updated=TO_TIMESTAMP('2024-06-18 18:16:59.08','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542851
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: MinValue
-- 2024-06-18T15:17:36.496Z
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-06-18 18:17:36.496','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542850
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: MinValue
-- 2024-06-18T15:18:33.616Z
UPDATE AD_Process_Para SET DisplayLogic='@ModCntr_Type_ID@=540024 || @ModCntr_Type_ID@=540025',Updated=TO_TIMESTAMP('2024-06-18 18:18:33.616','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542850
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: Price_Old
-- 2024-06-18T15:19:28.065Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543096,0,585401,542852,12,'Price_Old',TO_TIMESTAMP('2024-06-18 18:19:27.921','YYYY-MM-DD HH24:MI:SS.US'),100,'@ModCntr_Type_ID@=540024 || @ModCntr_Type_ID@=540025','de.metas.contracts',0,'Y','N','Y','N','N','N','Preis (old)',60,TO_TIMESTAMP('2024-06-18 18:19:27.921','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-18T15:19:28.067Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542852 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-06-18T15:19:28.069Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(543096)
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- Table: C_Flatrate_Term
-- EntityType: de.metas.contracts
-- 2024-06-19T06:44:16.107Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585401,540320,541497,TO_TIMESTAMP('2024-06-19 09:44:15.848','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-06-19 09:44:15.848','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;


-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- 2024-06-19T12:58:50.537Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-19 15:58:50.536','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585401
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- 2024-06-19T12:58:52.512Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-19 15:58:52.512','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585401
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- 2024-06-19T12:59:03.106Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Update contract price - selection',Updated=TO_TIMESTAMP('2024-06-19 15:59:03.106','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585401
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: MinValue
-- 2024-06-19T13:02:10.261Z
UPDATE AD_Process_Para SET DisplayLogic='@ModCntr_Type_ID@=540024 | @ModCntr_Type_ID@=540025',Updated=TO_TIMESTAMP('2024-06-19 16:02:10.261','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542850
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: Price_Old
-- 2024-06-19T13:02:13.869Z
UPDATE AD_Process_Para SET DisplayLogic='@ModCntr_Type_ID@=540024 | @ModCntr_Type_ID@=540025',Updated=TO_TIMESTAMP('2024-06-19 16:02:13.869','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542852
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: M_Product_ID
-- 2024-06-19T13:04:31.749Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,585401,542853,29,540272,'M_Product_ID',TO_TIMESTAMP('2024-06-19 16:04:31.502','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','de.metas.contracts',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','Y','N','Produkt',70,TO_TIMESTAMP('2024-06-19 16:04:31.502','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-19T13:04:31.752Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542853 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-06-19T13:04:31.755Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(454)
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: M_Product_ID
-- 2024-06-19T13:04:34.661Z
UPDATE AD_Process_Para SET SeqNo=5,Updated=TO_TIMESTAMP('2024-06-19 16:04:34.661','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542853
;

-- Process: ModCntr_Specific_Price_Update_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update_Selection)
-- ParameterName: M_Product_ID
-- 2024-06-19T13:06:58.703Z
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2024-06-19 16:06:58.703','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542853
;

