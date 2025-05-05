-- Run mode: SWING_CLIENT

-- Tab: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices
-- Table: ModCntr_Specific_Price
-- 2024-04-10T16:46:11.106Z
UPDATE AD_Tab SET AllowQuickInput='N',Updated=TO_TIMESTAMP('2024-04-10 19:46:11.106','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547499
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Reihenfolge
-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-04-10T16:55:07.751Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2024-04-10 19:55:07.751','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727299
;

-- Tab: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices
-- Table: ModCntr_Specific_Price
-- 2024-04-10T17:06:07.179Z
UPDATE AD_Tab SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-04-10 20:06:07.179','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547499
;

-- Value: ModCntr_Specific_Price_Update
-- Classname: de.metas.contracts.modular.process.ModCntr_Specific_Price_Update
-- 2024-04-11T07:39:07.069Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585382,'Y','de.metas.contracts.modular.process.ModCntr_Specific_Price_Update','N',TO_TIMESTAMP('2024-04-11 10:39:06.826','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Update Price','json','N','N','xls','Updates the selected ModCntr_Specific_Price record with the given price/PriceUOM/Currency','Java',TO_TIMESTAMP('2024-04-11 10:39:06.826','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Specific_Price_Update')
;

-- 2024-04-11T07:39:07.077Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585382 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- 2024-04-11T07:39:25.938Z
UPDATE AD_Process_Trl SET Name='Preis aktualisieren',Updated=TO_TIMESTAMP('2024-04-11 10:39:25.938','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585382
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- 2024-04-11T07:39:27.427Z
UPDATE AD_Process_Trl SET Name='Preis aktualisieren',Updated=TO_TIMESTAMP('2024-04-11 10:39:27.427','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585382
;

-- 2024-04-11T07:39:27.429Z
UPDATE AD_Process SET Name='Preis aktualisieren' WHERE AD_Process_ID=585382
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- 2024-04-11T07:39:29.165Z
UPDATE AD_Process_Trl SET Name='Preis aktualisieren',Updated=TO_TIMESTAMP('2024-04-11 10:39:29.165','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585382
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- 2024-04-11T07:39:31.300Z
UPDATE AD_Process_Trl SET Name='Preis aktualisieren',Updated=TO_TIMESTAMP('2024-04-11 10:39:31.3','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Process_ID=585382
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- ParameterName: Price
-- 2024-04-11T07:40:30.240Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1416,0,585382,542800,12,'Price',TO_TIMESTAMP('2024-04-11 10:40:30.084','YYYY-MM-DD HH24:MI:SS.US'),100,'Preis','de.metas.contracts',0,'Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','Y','N','N','N','Preis',10,TO_TIMESTAMP('2024-04-11 10:40:30.084','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-11T07:40:30.241Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542800 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-11T07:40:30.276Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(1416)
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- ParameterName: C_UOM_ID
-- 2024-04-11T07:41:41.561Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,215,0,585382,542801,30,114,'C_UOM_ID',TO_TIMESTAMP('2024-04-11 10:41:41.438','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','de.metas.contracts',0,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','Y','N','Maßeinheit',20,TO_TIMESTAMP('2024-04-11 10:41:41.438','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-11T07:41:41.562Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542801 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-11T07:41:41.563Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(215)
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- ParameterName: Price
-- 2024-04-11T07:41:47.442Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-11 10:41:47.442','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542800
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- ParameterName: C_Currency_ID
-- 2024-04-11T07:42:24.272Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,193,0,585382,542802,30,112,'C_Currency_ID',TO_TIMESTAMP('2024-04-11 10:42:24.133','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','de.metas.contracts',0,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','Y','N','Währung',30,TO_TIMESTAMP('2024-04-11 10:42:24.133','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-11T07:42:24.273Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542802 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-11T07:42:24.275Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(193)
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- Table: ModCntr_Specific_Price
-- EntityType: de.metas.contracts
-- 2024-04-11T07:44:06.008Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585382,542405,541475,TO_TIMESTAMP('2024-04-11 10:44:05.874','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-04-11 10:44:05.874','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- Table: ModCntr_Specific_Price
-- Window: Vertrag(540359,de.metas.contracts)
-- EntityType: de.metas.contracts
-- 2024-04-11T07:44:56.059Z
UPDATE AD_Table_Process SET AD_Window_ID=540359,Updated=TO_TIMESTAMP('2024-04-11 10:44:56.059','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541475
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- Table: ModCntr_Specific_Price
-- Tab: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D)
-- Window: Vertrag(540359,de.metas.contracts)
-- EntityType: de.metas.contracts
-- 2024-04-11T07:45:01.766Z
UPDATE AD_Table_Process SET AD_Tab_ID=547499,Updated=TO_TIMESTAMP('2024-04-11 10:45:01.766','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541475
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- 2024-04-11T07:52:39.103Z
UPDATE AD_Process_Trl SET Name='Update price',Updated=TO_TIMESTAMP('2024-04-11 10:52:39.103','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585382
;

-- Tab: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices
-- Table: ModCntr_Specific_Price
-- 2024-04-11T08:32:54.058Z
UPDATE AD_Tab SET DisplayLogic='',Updated=TO_TIMESTAMP('2024-04-11 11:32:54.058','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547499
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- ParameterName: C_Currency_ID
-- 2024-04-11T08:40:00.714Z
UPDATE AD_Process_Para SET DefaultValue='@C_Currency_ID@',Updated=TO_TIMESTAMP('2024-04-11 11:40:00.714','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542802
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- ParameterName: C_UOM_ID
-- 2024-04-11T08:40:08.582Z
UPDATE AD_Process_Para SET DefaultValue='@C_UOM_ID@',Updated=TO_TIMESTAMP('2024-04-11 11:40:08.582','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542801
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- ParameterName: Price
-- 2024-04-11T08:40:25.639Z
UPDATE AD_Process_Para SET DefaultValue='@Price@',Updated=TO_TIMESTAMP('2024-04-11 11:40:25.639','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542800
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Maßeinheit
-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-04-11T08:51:12.893Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588112,727823,0,547499,TO_TIMESTAMP('2024-04-11 11:51:12.629','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit',10,'de.metas.contracts','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2024-04-11 11:51:12.629','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-11T08:51:12.894Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-11T08:51:12.895Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2024-04-11T08:51:12.916Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727823
;

-- 2024-04-11T08:51:12.917Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727823)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Maßeinheit
-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-04-11T08:54:35.197Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727823,0,547499,551739,624371,'F',TO_TIMESTAMP('2024-04-11 11:54:34.966','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',50,0,0,TO_TIMESTAMP('2024-04-11 11:54:34.966','YYYY-MM-DD HH24:MI:SS.US'),100)
;
