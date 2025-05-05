-- Value: RemoveAttachment
-- Classname: de.metas.bpartner.impexp.blockstatus.process.RemoveAttachment
-- 2023-03-01T13:32:49.839Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585231,'Y','de.metas.bpartner.impexp.blockstatus.process.RemoveAttachment','N',TO_TIMESTAMP('2023-03-01 15:32:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Remove Attachment','json','N','N','xls','Java',TO_TIMESTAMP('2023-03-01 15:32:49','YYYY-MM-DD HH24:MI:SS'),100,'RemoveAttachment')
;

-- 2023-03-01T13:32:49.849Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585231 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: RemoveAttachment(de.metas.bpartner.impexp.blockstatus.process.RemoveAttachment)
-- Table: C_BPartner_Block_File
-- EntityType: D
-- 2023-03-01T13:33:11.627Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585231,542317,541367,TO_TIMESTAMP('2023-03-01 15:33:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-03-01 15:33:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Value: GenerateTemplate
-- Classname: de.metas.bpartner.impexp.blockstatus.process.GenerateTemplate
-- 2023-03-01T13:33:49.915Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585232,'Y','de.metas.bpartner.impexp.blockstatus.process.GenerateTemplate','N',TO_TIMESTAMP('2023-03-01 15:33:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Generate Template','json','N','N','xls','Java',TO_TIMESTAMP('2023-03-01 15:33:49','YYYY-MM-DD HH24:MI:SS'),100,'GenerateTemplate')
;

-- 2023-03-01T13:33:49.916Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585232 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: GenerateTemplate(de.metas.bpartner.impexp.blockstatus.process.GenerateTemplate)
-- Table: C_BPartner_Block_File
-- EntityType: D
-- 2023-03-01T13:34:03.233Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585232,542317,541368,TO_TIMESTAMP('2023-03-01 15:34:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-03-01 15:34:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Element: C_BPartner_Block_File_ID
-- 2023-03-01T09:04:10.210Z
UPDATE AD_Element_Trl SET Name='BPartner Block File', PrintName='BPartner Block File',Updated=TO_TIMESTAMP('2023-03-01 11:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582102 AND AD_Language='en_US'
;

-- 2023-03-01T09:04:10.235Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582102,'en_US')
;

-- Element: C_BPartner_Block_File_ID
-- 2023-03-01T09:04:14.657Z
UPDATE AD_Element_Trl SET Name='BPartner Block File', PrintName='BPartner Block File',Updated=TO_TIMESTAMP('2023-03-01 11:04:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582102 AND AD_Language='de_DE'
;

-- 2023-03-01T09:04:14.657Z
UPDATE AD_Element SET Name='BPartner Block File', PrintName='BPartner Block File' WHERE AD_Element_ID=582102
;

-- 2023-03-01T09:04:15.112Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582102,'de_DE')
;

-- 2023-03-01T09:04:15.115Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582102,'de_DE')
;

-- Column: I_BPartner_BlockStatus.C_BPartner_Block_File_ID
-- 2023-03-01T09:05:27.778Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586273,582102,0,19,542318,'C_BPartner_Block_File_ID',TO_TIMESTAMP('2023-03-01 11:05:27','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'BPartner Block File',0,0,TO_TIMESTAMP('2023-03-01 11:05:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-01T09:05:27.782Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586273 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-01T09:05:27.786Z
/* DDL */  select update_Column_Translation_From_AD_Element(582102)
;

-- 2023-03-01T09:05:28.826Z
/* DDL */ SELECT public.db_alter_table('I_BPartner_BlockStatus','ALTER TABLE public.I_BPartner_BlockStatus ADD COLUMN C_BPartner_Block_File_ID NUMERIC(10)')
;

-- 2023-03-01T09:05:28.847Z
ALTER TABLE I_BPartner_BlockStatus ADD CONSTRAINT CBPartnerBlockFile_IBPartnerBlockStatus FOREIGN KEY (C_BPartner_Block_File_ID) REFERENCES public.C_BPartner_Block_File DEFERRABLE INITIALLY DEFERRED
;

-- Tab: Change Business Partner Block(541682,D) -> Import BP Block Status
-- Table: I_BPartner_BlockStatus
-- 2023-03-01T10:35:11.919Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582109,0,546844,542318,541682,'Y',TO_TIMESTAMP('2023-03-01 12:35:11','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','I_BPartner_BlockStatus','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Import BP Block Status','N',20,1,TO_TIMESTAMP('2023-03-01 12:35:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:35:11.927Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546844 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-03-01T10:35:11.932Z
/* DDL */  select update_tab_translation_from_ad_element(582109)
;

-- 2023-03-01T10:35:11.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546844)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 10 -> main.SAP BPartner Id
-- Column: I_BPartner_BlockStatus.SAP_BPartnerCode
-- 2023-03-01T10:35:56.924Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-03-01 12:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615946
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 10 -> main.Sperrstatus
-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-01T10:35:56.930Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-01 12:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615947
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 10 -> main.Grund
-- Column: I_BPartner_BlockStatus.Reason
-- 2023-03-01T10:35:56.935Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-01 12:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615948
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> import.Importiert
-- Column: I_BPartner_BlockStatus.I_IsImported
-- 2023-03-01T10:35:56.938Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-01 12:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615937
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> import.Import-Fehlermeldung
-- Column: I_BPartner_BlockStatus.I_ErrorMsg
-- 2023-03-01T10:35:56.942Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-01 12:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615938
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> import.Daten Import
-- Column: I_BPartner_BlockStatus.C_DataImport_ID
-- 2023-03-01T10:35:56.945Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-01 12:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615941
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> org.Sektion
-- Column: I_BPartner_BlockStatus.AD_Org_ID
-- 2023-03-01T10:35:56.948Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-01 12:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615943
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Mandant
-- Column: I_BPartner_BlockStatus.AD_Client_ID
-- 2023-03-01T10:36:09.222Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586244,712752,0,546844,TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:09.226Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712752 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:09.233Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-03-01T10:36:09.388Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712752
;

-- 2023-03-01T10:36:09.391Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712752)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Probleme
-- Column: I_BPartner_BlockStatus.AD_Issue_ID
-- 2023-03-01T10:36:09.496Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586245,712753,0,546844,TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:09.496Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712753 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:09.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887)
;

-- 2023-03-01T10:36:09.502Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712753
;

-- 2023-03-01T10:36:09.503Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712753)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Sektion
-- Column: I_BPartner_BlockStatus.AD_Org_ID
-- 2023-03-01T10:36:09.593Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586246,712754,0,546844,TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:09.595Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712754 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:09.598Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-03-01T10:36:09.715Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712754
;

-- 2023-03-01T10:36:09.715Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712754)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Daten Import
-- Column: I_BPartner_BlockStatus.C_DataImport_ID
-- 2023-03-01T10:36:09.812Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586247,712755,0,546844,TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Daten Import',TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:09.814Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712755 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:09.816Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913)
;

-- 2023-03-01T10:36:09.821Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712755
;

-- 2023-03-01T10:36:09.821Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712755)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Data Import Run
-- Column: I_BPartner_BlockStatus.C_DataImport_Run_ID
-- 2023-03-01T10:36:09.911Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586248,712756,0,546844,TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data Import Run',TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:09.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712756 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:09.915Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577114)
;

-- 2023-03-01T10:36:09.921Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712756
;

-- 2023-03-01T10:36:09.922Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712756)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Import-Fehlermeldung
-- Column: I_BPartner_BlockStatus.I_ErrorMsg
-- 2023-03-01T10:36:10.010Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586251,712757,0,546844,TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden',2000,'D','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','N','N','N','N','N','Import-Fehlermeldung',TO_TIMESTAMP('2023-03-01 12:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:10.013Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712757 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:10.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(912)
;

-- 2023-03-01T10:36:10.022Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712757
;

-- 2023-03-01T10:36:10.022Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712757)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Importiert
-- Column: I_BPartner_BlockStatus.I_IsImported
-- 2023-03-01T10:36:10.108Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586252,712758,0,546844,TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?',1,'D','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','N','N','N','N','N','Importiert',TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:10.109Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712758 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:10.111Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(913)
;

-- 2023-03-01T10:36:10.115Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712758
;

-- 2023-03-01T10:36:10.115Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712758)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Import Line Content
-- Column: I_BPartner_BlockStatus.I_LineContent
-- 2023-03-01T10:36:10.219Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586253,712759,0,546844,TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,4000,'D','Y','N','N','N','N','N','N','N','Import Line Content',TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:10.221Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712759 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:10.223Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577115)
;

-- 2023-03-01T10:36:10.228Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712759
;

-- 2023-03-01T10:36:10.228Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712759)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Import Line No
-- Column: I_BPartner_BlockStatus.I_LineNo
-- 2023-03-01T10:36:10.312Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586254,712760,0,546844,TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Import Line No',TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:10.323Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712760 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:10.324Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577116)
;

-- 2023-03-01T10:36:10.326Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712760
;

-- 2023-03-01T10:36:10.327Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712760)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Aktiv
-- Column: I_BPartner_BlockStatus.IsActive
-- 2023-03-01T10:36:10.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586255,712761,0,546844,TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:10.421Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712761 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:10.423Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-03-01T10:36:10.552Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712761
;

-- 2023-03-01T10:36:10.552Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712761)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Verarbeitet
-- Column: I_BPartner_BlockStatus.Processed
-- 2023-03-01T10:36:10.648Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586256,712762,0,546844,TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:10.649Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712762 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:10.650Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2023-03-01T10:36:10.670Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712762
;

-- 2023-03-01T10:36:10.670Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712762)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Import BP Block Status
-- Column: I_BPartner_BlockStatus.I_BPartner_BlockStatus_ID
-- 2023-03-01T10:36:10.756Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586259,712763,0,546844,TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Import BP Block Status',TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:10.758Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712763 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:10.760Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582109)
;

-- 2023-03-01T10:36:10.765Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712763
;

-- 2023-03-01T10:36:10.765Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712763)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> SAP BPartner Id
-- Column: I_BPartner_BlockStatus.SAP_BPartnerCode
-- 2023-03-01T10:36:10.857Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586261,712764,0,546844,TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','SAP BPartner Id',TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:10.859Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712764 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:10.861Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581932)
;

-- 2023-03-01T10:36:10.866Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712764
;

-- 2023-03-01T10:36:10.867Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712764)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Grund
-- Column: I_BPartner_BlockStatus.Reason
-- 2023-03-01T10:36:10.963Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586262,712765,0,546844,TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','N','N','N','N','N','N','N','Grund',TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:10.965Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712765 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:10.968Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576788)
;

-- 2023-03-01T10:36:10.972Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712765
;

-- 2023-03-01T10:36:10.972Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712765)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> Sperrstatus
-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-01T10:36:11.055Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586263,712766,0,546844,TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Sperrstatus',TO_TIMESTAMP('2023-03-01 12:36:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:11.057Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712766 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:11.058Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582089)
;

-- 2023-03-01T10:36:11.061Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712766
;

-- 2023-03-01T10:36:11.062Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712766)
;

-- Field: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> BPartner Block File
-- Column: I_BPartner_BlockStatus.C_BPartner_Block_File_ID
-- 2023-03-01T10:36:11.152Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586273,712767,0,546844,TO_TIMESTAMP('2023-03-01 12:36:11','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','BPartner Block File',TO_TIMESTAMP('2023-03-01 12:36:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:36:11.154Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712767 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:36:11.156Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582102)
;

-- 2023-03-01T10:36:11.161Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712767
;

-- 2023-03-01T10:36:11.162Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712767)
;

-- Tab: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D)
-- UI Section: main
-- 2023-03-01T10:36:34.696Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546844,545464,TO_TIMESTAMP('2023-03-01 12:36:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-01 12:36:34','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-03-01T10:36:34.697Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545464 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main
-- UI Column: 10
-- 2023-03-01T10:36:38.610Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546675,545464,TO_TIMESTAMP('2023-03-01 12:36:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-01 12:36:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main
-- UI Column: 20
-- 2023-03-01T10:36:39.691Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546676,545464,TO_TIMESTAMP('2023-03-01 12:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-03-01 12:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20
-- UI Element Group: flags
-- 2023-03-01T10:36:46.276Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546676,550421,TO_TIMESTAMP('2023-03-01 12:36:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-03-01 12:36:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> flags.Aktiv
-- Column: I_BPartner_BlockStatus.IsActive
-- 2023-03-01T10:36:57.989Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712761,0,546844,615950,550421,'F',TO_TIMESTAMP('2023-03-01 12:36:57','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-03-01 12:36:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> flags.Verarbeitet
-- Column: I_BPartner_BlockStatus.Processed
-- 2023-03-01T10:37:07.779Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712762,0,546844,615951,550421,'F',TO_TIMESTAMP('2023-03-01 12:37:07','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',20,0,0,TO_TIMESTAMP('2023-03-01 12:37:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20
-- UI Element Group: import
-- 2023-03-01T10:37:14.143Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546676,550422,TO_TIMESTAMP('2023-03-01 12:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','import',20,TO_TIMESTAMP('2023-03-01 12:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> import.Importiert
-- Column: I_BPartner_BlockStatus.I_IsImported
-- 2023-03-01T10:37:27.587Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712758,0,546844,615952,550422,'F',TO_TIMESTAMP('2023-03-01 12:37:27','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','Y','N','N','N',0,'Importiert',10,0,0,TO_TIMESTAMP('2023-03-01 12:37:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> import.Import-Fehlermeldung
-- Column: I_BPartner_BlockStatus.I_ErrorMsg
-- 2023-03-01T10:37:41.559Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712757,0,546844,615953,550422,'F',TO_TIMESTAMP('2023-03-01 12:37:41','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','Y','N','N','N',0,'Import-Fehlermeldung',20,0,0,TO_TIMESTAMP('2023-03-01 12:37:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> import.Import Line No
-- Column: I_BPartner_BlockStatus.I_LineNo
-- 2023-03-01T10:37:49.235Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712760,0,546844,615954,550422,'F',TO_TIMESTAMP('2023-03-01 12:37:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import Line No',30,0,0,TO_TIMESTAMP('2023-03-01 12:37:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> import.Import Line Content
-- Column: I_BPartner_BlockStatus.I_LineContent
-- 2023-03-01T10:37:57.757Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712759,0,546844,615955,550422,'F',TO_TIMESTAMP('2023-03-01 12:37:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import Line Content',40,0,0,TO_TIMESTAMP('2023-03-01 12:37:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> import.Daten Import
-- Column: I_BPartner_BlockStatus.C_DataImport_ID
-- 2023-03-01T10:38:22.247Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712755,0,546844,615956,550422,'F',TO_TIMESTAMP('2023-03-01 12:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Daten Import',50,0,0,TO_TIMESTAMP('2023-03-01 12:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> import.Data Import Run
-- Column: I_BPartner_BlockStatus.C_DataImport_Run_ID
-- 2023-03-01T10:38:32.966Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712756,0,546844,615957,550422,'F',TO_TIMESTAMP('2023-03-01 12:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data Import Run',60,0,0,TO_TIMESTAMP('2023-03-01 12:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20
-- UI Element Group: org
-- 2023-03-01T10:38:39.927Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546676,550423,TO_TIMESTAMP('2023-03-01 12:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2023-03-01 12:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> org.Sektion
-- Column: I_BPartner_BlockStatus.AD_Org_ID
-- 2023-03-01T10:39:07.127Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712754,0,546844,615958,550423,'F',TO_TIMESTAMP('2023-03-01 12:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2023-03-01 12:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> org.Mandant
-- Column: I_BPartner_BlockStatus.AD_Client_ID
-- 2023-03-01T10:39:14.124Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712752,0,546844,615959,550423,'F',TO_TIMESTAMP('2023-03-01 12:39:13','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-03-01 12:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 10
-- UI Element Group: main
-- 2023-03-01T10:39:24.078Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546675,550424,TO_TIMESTAMP('2023-03-01 12:39:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-03-01 12:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 10 -> main.SAP BPartner Id
-- Column: I_BPartner_BlockStatus.SAP_BPartnerCode
-- 2023-03-01T10:39:47.260Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712764,0,546844,615960,550424,'F',TO_TIMESTAMP('2023-03-01 12:39:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SAP BPartner Id',10,0,0,TO_TIMESTAMP('2023-03-01 12:39:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 10 -> main.Sperrstatus
-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-01T10:39:54.897Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712766,0,546844,615961,550424,'F',TO_TIMESTAMP('2023-03-01 12:39:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sperrstatus',20,0,0,TO_TIMESTAMP('2023-03-01 12:39:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 10 -> main.Grund
-- Column: I_BPartner_BlockStatus.Reason
-- 2023-03-01T10:40:03.623Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712765,0,546844,615962,550424,'F',TO_TIMESTAMP('2023-03-01 12:40:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Grund',30,0,0,TO_TIMESTAMP('2023-03-01 12:40:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 10
-- UI Element Group: blockfile
-- 2023-03-01T10:40:14.169Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546675,550425,TO_TIMESTAMP('2023-03-01 12:40:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','blockfile',20,TO_TIMESTAMP('2023-03-01 12:40:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 10 -> blockfile.BPartner Block File
-- Column: I_BPartner_BlockStatus.C_BPartner_Block_File_ID
-- 2023-03-01T10:40:27.277Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712767,0,546844,615963,550425,'F',TO_TIMESTAMP('2023-03-01 12:40:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'BPartner Block File',10,0,0,TO_TIMESTAMP('2023-03-01 12:40:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 10 -> main.SAP BPartner Id
-- Column: I_BPartner_BlockStatus.SAP_BPartnerCode
-- 2023-03-01T10:41:19.781Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-03-01 12:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615960
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 10 -> main.Sperrstatus
-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-01T10:41:19.783Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-01 12:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615961
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 10 -> main.Grund
-- Column: I_BPartner_BlockStatus.Reason
-- 2023-03-01T10:41:19.785Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-01 12:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615962
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> import.Importiert
-- Column: I_BPartner_BlockStatus.I_IsImported
-- 2023-03-01T10:41:19.787Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-01 12:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615952
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> import.Import-Fehlermeldung
-- Column: I_BPartner_BlockStatus.I_ErrorMsg
-- 2023-03-01T10:41:19.790Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-01 12:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615953
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 10 -> blockfile.BPartner Block File
-- Column: I_BPartner_BlockStatus.C_BPartner_Block_File_ID
-- 2023-03-01T10:41:19.792Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-01 12:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615963
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> import.Daten Import
-- Column: I_BPartner_BlockStatus.C_DataImport_ID
-- 2023-03-01T10:41:19.794Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-01 12:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615956
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BP Block Status(546844,D) -> main -> 20 -> org.Sektion
-- Column: I_BPartner_BlockStatus.AD_Org_ID
-- 2023-03-01T10:41:19.795Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-01 12:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615958
;

-- UI Element: Change Business Partner Block(541682,D) -> BPartner Block File(546841,D) -> main -> 10 -> main.Dateiname
-- Column: C_BPartner_Block_File.FileName
-- 2023-03-01T10:41:42.106Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-03-01 12:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615914
;

-- UI Element: Change Business Partner Block(541682,D) -> BPartner Block File(546841,D) -> main -> 10 -> main.Daten Import
-- Column: C_BPartner_Block_File.C_DataImport_ID
-- 2023-03-01T10:41:42.107Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-01 12:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615921
;

-- UI Element: Change Business Partner Block(541682,D) -> BPartner Block File(546841,D) -> main -> 20 -> flags.Verarbeitet
-- Column: C_BPartner_Block_File.Processed
-- 2023-03-01T10:41:42.110Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-01 12:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615916
;

-- UI Element: Change Business Partner Block(541682,D) -> BPartner Block File(546841,D) -> main -> 20 -> flags.Fehler
-- Column: C_BPartner_Block_File.IsError
-- 2023-03-01T10:41:42.111Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-01 12:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615922
;

-- UI Element: Change Business Partner Block(541682,D) -> BPartner Block File(546841,D) -> main -> 20 -> org.Sektion
-- Column: C_BPartner_Block_File.AD_Org_ID
-- 2023-03-01T10:41:42.114Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-01 12:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615918
;

-- Tab: Change Business Partner Block(541682,D) -> Import BP Block Status
-- Table: I_BPartner_BlockStatus
-- 2023-03-01T10:42:47.178Z
UPDATE AD_Tab SET AD_Column_ID=586273,Updated=TO_TIMESTAMP('2023-03-01 12:42:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546844
;

-- Element: I_BPartner_BlockStatus_ID
-- 2023-03-01T10:43:47.066Z
UPDATE AD_Element_Trl SET Name='Import BPartner Block Status', PrintName='Import BPartner Block Status',Updated=TO_TIMESTAMP('2023-03-01 12:43:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582109 AND AD_Language='en_US'
;

-- 2023-03-01T10:43:47.067Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582109,'en_US')
;

-- Element: I_BPartner_BlockStatus_ID
-- 2023-03-01T10:43:55.099Z
UPDATE AD_Element_Trl SET Name='Import BPartner Block Status', PrintName='Import BPartner Block Status',Updated=TO_TIMESTAMP('2023-03-01 12:43:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582109 AND AD_Language='de_DE'
;

-- 2023-03-01T10:43:55.099Z
UPDATE AD_Element SET Name='Import BPartner Block Status', PrintName='Import BPartner Block Status' WHERE AD_Element_ID=582109
;

-- 2023-03-01T10:43:55.511Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582109,'de_DE')
;

-- 2023-03-01T10:43:55.512Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582109,'de_DE')
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 10 -> blockfile.BPartner Block File
-- Column: I_BPartner_BlockStatus.C_BPartner_Block_File_ID
-- 2023-03-01T10:44:33.193Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-03-01 12:44:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615963
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 20 -> import.Daten Import
-- Column: I_BPartner_BlockStatus.C_DataImport_ID
-- 2023-03-01T10:44:33.196Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-01 12:44:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615956
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 20 -> org.Sektion
-- Column: I_BPartner_BlockStatus.AD_Org_ID
-- 2023-03-01T10:44:33.200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-01 12:44:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615958
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 20 -> import.Daten Import
-- Column: I_BPartner_BlockStatus.C_DataImport_ID
-- 2023-03-01T10:44:43.918Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-03-01 12:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615956
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 20 -> org.Sektion
-- Column: I_BPartner_BlockStatus.AD_Org_ID
-- 2023-03-01T10:44:43.922Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-01 12:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615958
;

-- Field: Change Business Partner Block(541682,D) -> BPartner Block File(546841,D) -> Verarbeitet
-- Column: C_BPartner_Block_File.Processed
-- 2023-03-01T10:48:40.431Z
UPDATE AD_Field SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-03-01 12:48:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712718
;

-- Field: Change Business Partner Block(541682,D) -> BPartner Block File(546841,D) -> Fehler
-- Column: C_BPartner_Block_File.IsError
-- 2023-03-01T10:48:48.249Z
UPDATE AD_Field SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-03-01 12:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712722
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 10 -> blockfile.BPartner Block File
-- Column: I_BPartner_BlockStatus.C_BPartner_Block_File_ID
-- 2023-03-01T10:49:37.201Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615963
;

-- UI Column: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 10
-- UI Element Group: blockfile
-- 2023-03-01T10:49:41.207Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550425
;

-- Field: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> BPartner Block File
-- Column: I_BPartner_BlockStatus.C_BPartner_Block_File_ID
-- 2023-03-01T10:49:59.797Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586273,712769,0,546843,0,TO_TIMESTAMP('2023-03-01 12:49:59','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','BPartner Block File',0,10,0,1,1,TO_TIMESTAMP('2023-03-01 12:49:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T10:49:59.798Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712769 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T10:49:59.801Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582102)
;

-- 2023-03-01T10:49:59.805Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712769
;

-- 2023-03-01T10:49:59.805Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712769)
;

-- UI Column: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 10
-- UI Element Group: blockFile
-- 2023-03-01T10:50:08.011Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546673,550426,TO_TIMESTAMP('2023-03-01 12:50:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','blockFile',20,TO_TIMESTAMP('2023-03-01 12:50:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 10 -> blockFile.BPartner Block File
-- Column: I_BPartner_BlockStatus.C_BPartner_Block_File_ID
-- 2023-03-01T10:50:18.157Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712769,0,546843,615965,550426,'F',TO_TIMESTAMP('2023-03-01 12:50:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'BPartner Block File',10,0,0,TO_TIMESTAMP('2023-03-01 12:50:18','YYYY-MM-DD HH24:MI:SS'),100)
;



-- Column: I_BPartner_BlockStatus.Action
-- 2023-03-02T05:31:34.377Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586278,152,0,10,542318,'Action',TO_TIMESTAMP('2023-03-02 07:31:34','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,255,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Aktion',0,0,TO_TIMESTAMP('2023-03-02 07:31:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-02T05:31:34.379Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586278 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-02T05:31:34.384Z
/* DDL */  select update_Column_Translation_From_AD_Element(152)
;

-- 2023-03-02T05:31:35.184Z
/* DDL */ SELECT public.db_alter_table('I_BPartner_BlockStatus','ALTER TABLE public.I_BPartner_BlockStatus ADD COLUMN Action VARCHAR(255) NOT NULL')
;

-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-02T05:31:45.452Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-03-02 07:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586263
;

-- 2023-03-02T05:31:46.238Z
INSERT INTO t_alter_column values('i_bpartner_blockstatus','BlockStatus','VARCHAR(10)',null,null)
;

-- 2023-03-02T05:31:46.244Z
INSERT INTO t_alter_column values('i_bpartner_blockstatus','BlockStatus',null,'NULL',null)
;

-- 2023-03-02T06:02:32.196Z
UPDATE AD_ImpFormat_Row SET AD_Column_ID=586278,Updated=TO_TIMESTAMP('2023-03-02 08:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541830
;

-- Field: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> Aktion
-- Column: I_BPartner_BlockStatus.Action
-- 2023-03-02T05:43:43.767Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586278,712771,0,546843,0,TO_TIMESTAMP('2023-03-02 07:43:43','YYYY-MM-DD HH24:MI:SS'),100,'',0,'D','',0,'Y','Y','Y','N','N','N','N','N','Aktion',0,20,0,1,1,TO_TIMESTAMP('2023-03-02 07:43:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-02T05:43:43.771Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-02T05:43:43.797Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(152)
;

-- 2023-03-02T05:43:43.814Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712771
;

-- 2023-03-02T05:43:43.820Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712771)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 10 -> main.Aktion
-- Column: I_BPartner_BlockStatus.Action
-- 2023-03-02T05:44:05.549Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712771,0,546843,615967,550420,'F',TO_TIMESTAMP('2023-03-02 07:44:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Aktion',40,0,0,TO_TIMESTAMP('2023-03-02 07:44:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 10 -> main.Aktion
-- Column: I_BPartner_BlockStatus.Action
-- 2023-03-02T05:44:19.756Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-03-02 07:44:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615967
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 10 -> main.Sperrstatus
-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-02T05:44:23.318Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-03-02 07:44:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615947
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 10 -> main.Grund
-- Column: I_BPartner_BlockStatus.Reason
-- 2023-03-02T05:44:27.480Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-03-02 07:44:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615948
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 10 -> main.Aktion
-- Column: I_BPartner_BlockStatus.Action
-- 2023-03-02T05:44:41.955Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-02 07:44:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615967
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 10 -> main.Aktion
-- Column: I_BPartner_BlockStatus.Action
-- 2023-03-02T05:45:42.911Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-02 07:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615967
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 10 -> main.Sperrstatus
-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-02T05:45:42.917Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-02 07:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615947
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 10 -> main.Grund
-- Column: I_BPartner_BlockStatus.Reason
-- 2023-03-02T05:45:42.920Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-02 07:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615948
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 20 -> import.Importiert
-- Column: I_BPartner_BlockStatus.I_IsImported
-- 2023-03-02T05:45:42.925Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-02 07:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615937
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 20 -> import.Import-Fehlermeldung
-- Column: I_BPartner_BlockStatus.I_ErrorMsg
-- 2023-03-02T05:45:42.928Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-02 07:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615938
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 20 -> import.Daten Import
-- Column: I_BPartner_BlockStatus.C_DataImport_ID
-- 2023-03-02T05:45:42.931Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-02 07:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615941
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 20 -> flags.Verarbeitet
-- Column: I_BPartner_BlockStatus.Processed
-- 2023-03-02T05:45:42.934Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-02 07:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615945
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BPartner Block Status(546843,D) -> main -> 20 -> org.Sektion
-- Column: I_BPartner_BlockStatus.AD_Org_ID
-- 2023-03-02T05:45:42.936Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-02 07:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615943
;

-- Field: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> Aktion
-- Column: I_BPartner_BlockStatus.Action
-- 2023-03-02T06:04:03.665Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586278,712772,0,546844,0,TO_TIMESTAMP('2023-03-02 08:04:03','YYYY-MM-DD HH24:MI:SS'),100,'',0,'D','',0,'Y','Y','Y','N','N','N','N','N','Aktion',0,10,0,1,1,TO_TIMESTAMP('2023-03-02 08:04:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-02T06:04:03.668Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712772 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-02T06:04:03.674Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(152)
;

-- 2023-03-02T06:04:03.686Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712772
;

-- 2023-03-02T06:04:03.692Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712772)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 10 -> main.Aktion
-- Column: I_BPartner_BlockStatus.Action
-- 2023-03-02T06:04:19.521Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712772,0,546844,615968,550424,'F',TO_TIMESTAMP('2023-03-02 08:04:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Aktion',40,0,0,TO_TIMESTAMP('2023-03-02 08:04:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 10 -> main.Aktion
-- Column: I_BPartner_BlockStatus.Action
-- 2023-03-02T06:04:27.691Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-03-02 08:04:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615968
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 10 -> main.Sperrstatus
-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-02T06:04:30.602Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-03-02 08:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615961
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 10 -> main.Grund
-- Column: I_BPartner_BlockStatus.Reason
-- 2023-03-02T06:04:34.481Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-03-02 08:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615962
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 10 -> main.Aktion
-- Column: I_BPartner_BlockStatus.Action
-- 2023-03-02T06:05:29.389Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-02 08:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615968
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 10 -> main.Sperrstatus
-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-02T06:05:29.393Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-02 08:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615961
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 10 -> main.Grund
-- Column: I_BPartner_BlockStatus.Reason
-- 2023-03-02T06:05:29.396Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-02 08:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615962
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 20 -> import.Importiert
-- Column: I_BPartner_BlockStatus.I_IsImported
-- 2023-03-02T06:05:29.399Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-02 08:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615952
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 20 -> import.Import-Fehlermeldung
-- Column: I_BPartner_BlockStatus.I_ErrorMsg
-- 2023-03-02T06:05:29.401Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-02 08:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615953
;

-- UI Element: Change Business Partner Block(541682,D) -> Import BPartner Block Status(546844,D) -> main -> 20 -> org.Sektion
-- Column: I_BPartner_BlockStatus.AD_Org_ID
-- 2023-03-02T06:05:29.403Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-02 08:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615958
;

-- Value: de.metas.bpartner.blockfile.FileAlreadyAttached
-- 2023-03-02T10:26:16.914Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545248,0,TO_TIMESTAMP('2023-03-02 12:26:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Business partner block change already has one file attached!','E',TO_TIMESTAMP('2023-03-02 12:26:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.bpartner.blockfile.FileAlreadyAttached')
;

-- 2023-03-02T10:26:16.924Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545248 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.bpartner.blockfile.BPartnerBlockChangeProcessed
-- 2023-03-02T10:28:06.008Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545249,0,TO_TIMESTAMP('2023-03-02 12:28:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Business partner block change has already been processed!','E',TO_TIMESTAMP('2023-03-02 12:28:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.bpartner.blockfile.BPartnerBlockChangeProcessed')
;

-- 2023-03-02T10:28:06.012Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545249 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


