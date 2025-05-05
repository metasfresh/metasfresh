-- Run mode: SWING_CLIENT

-- 2023-10-04T12:58:31.317Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582737,0,TO_TIMESTAMP('2023-10-04 13:58:30.93','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Import Module Contract Log','Import Module Contract Log',TO_TIMESTAMP('2023-10-04 13:58:30.93','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T12:58:31.329Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582737 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-10-04T12:58:53.174Z
UPDATE AD_Element_Trl SET Name='Import Vertragsbaustein Log', PrintName='Import Vertragsbaustein Log',Updated=TO_TIMESTAMP('2023-10-04 13:58:53.174','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582737 AND AD_Language='de_CH'
;

-- 2023-10-04T12:58:53.201Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582737,'de_CH')
;

-- Element: null
-- 2023-10-04T12:58:59.076Z
UPDATE AD_Element_Trl SET Name='Import Vertragsbaustein Log', PrintName='Import Vertragsbaustein Log',Updated=TO_TIMESTAMP('2023-10-04 13:58:59.076','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582737 AND AD_Language='de_DE'
;

-- 2023-10-04T12:58:59.077Z
UPDATE AD_Element SET Name='Import Vertragsbaustein Log', PrintName='Import Vertragsbaustein Log' WHERE AD_Element_ID=582737
;

-- 2023-10-04T12:58:59.474Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582737,'de_DE')
;

-- 2023-10-04T12:58:59.475Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582737,'de_DE')
;

-- Element: null
-- 2023-10-04T12:59:03.180Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-04 13:59:03.18','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582737 AND AD_Language='en_US'
;

-- 2023-10-04T12:59:03.182Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582737,'en_US')
;

-- Window: Import Vertragsbaustein Log, InternalName=null
-- 2023-10-04T12:59:41.425Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582737,0,541737,TO_TIMESTAMP('2023-10-04 13:59:31.313','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','N','N','N','Y','Import Vertragsbaustein Log','N',TO_TIMESTAMP('2023-10-04 13:59:31.313','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-10-04T12:59:41.426Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541737 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-10-04T12:59:41.428Z
/* DDL */  select update_window_translation_from_ad_element(582737)
;

-- 2023-10-04T12:59:41.433Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541737
;

-- 2023-10-04T12:59:41.434Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541737)
;

-- Tab: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs
-- Table: I_ModCntr_Log
-- 2023-10-04T13:01:29.353Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582735,0,547233,542372,541737,'Y',TO_TIMESTAMP('2023-10-04 14:01:28.675','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','N','A','I_ModCntr_Log','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Import Contract Module Logs','N',10,0,TO_TIMESTAMP('2023-10-04 14:01:28.675','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:01:29.355Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547233 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-10-04T13:01:29.356Z
/* DDL */  select update_tab_translation_from_ad_element(582735)
;

-- 2023-10-04T13:01:29.360Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547233)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Mandant
-- Column: I_ModCntr_Log.AD_Client_ID
-- 2023-10-04T13:02:11.220Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587492,720706,0,547233,TO_TIMESTAMP('2023-10-04 14:02:10.888','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.contracts','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-10-04 14:02:10.888','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:11.222Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720706 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:11.223Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-10-04T13:02:11.330Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720706
;

-- 2023-10-04T13:02:11.331Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720706)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Probleme
-- Column: I_ModCntr_Log.AD_Issue_ID
-- 2023-10-04T13:02:11.497Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587493,720707,0,547233,TO_TIMESTAMP('2023-10-04 14:02:11.338','YYYY-MM-DD HH24:MI:SS.US'),100,'',10,'de.metas.contracts','','Y','Y','N','N','N','N','N','Probleme',TO_TIMESTAMP('2023-10-04 14:02:11.338','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:11.498Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720707 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:11.499Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887)
;

-- 2023-10-04T13:02:11.503Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720707
;

-- 2023-10-04T13:02:11.504Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720707)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Organisation
-- Column: I_ModCntr_Log.AD_Org_ID
-- 2023-10-04T13:02:11.654Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587494,720708,0,547233,TO_TIMESTAMP('2023-10-04 14:02:11.505','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.contracts','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-10-04 14:02:11.505','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:11.655Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720708 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:11.656Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-10-04T13:02:11.749Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720708
;

-- 2023-10-04T13:02:11.750Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720708)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Daten Import
-- Column: I_ModCntr_Log.C_DataImport_ID
-- 2023-10-04T13:02:11.901Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587495,720709,0,547233,TO_TIMESTAMP('2023-10-04 14:02:11.751','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','Y','N','N','N','N','N','Daten Import',TO_TIMESTAMP('2023-10-04 14:02:11.751','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:11.902Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720709 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:11.903Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913)
;

-- 2023-10-04T13:02:11.906Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720709
;

-- 2023-10-04T13:02:11.907Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720709)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Daten Import Verlauf
-- Column: I_ModCntr_Log.C_DataImport_Run_ID
-- 2023-10-04T13:02:12.053Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587496,720710,0,547233,TO_TIMESTAMP('2023-10-04 14:02:11.909','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','Y','N','N','N','N','N','Daten Import Verlauf',TO_TIMESTAMP('2023-10-04 14:02:11.909','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:12.054Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720710 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:12.055Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577114)
;

-- 2023-10-04T13:02:12.057Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720710
;

-- 2023-10-04T13:02:12.058Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720710)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Erstellt
-- Column: I_ModCntr_Log.Created
-- 2023-10-04T13:02:12.215Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587497,720711,0,547233,TO_TIMESTAMP('2023-10-04 14:02:12.059','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.contracts','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2023-10-04 14:02:12.059','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:12.216Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720711 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:12.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2023-10-04T13:02:12.250Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720711
;

-- 2023-10-04T13:02:12.250Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720711)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Erstellt durch
-- Column: I_ModCntr_Log.CreatedBy
-- 2023-10-04T13:02:12.438Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587498,720712,0,547233,TO_TIMESTAMP('2023-10-04 14:02:12.253','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.contracts','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2023-10-04 14:02:12.253','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:12.439Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720712 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:12.440Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2023-10-04T13:02:12.467Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720712
;

-- 2023-10-04T13:02:12.468Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720712)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Import-Fehlermeldung
-- Column: I_ModCntr_Log.I_ErrorMsg
-- 2023-10-04T13:02:12.691Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587499,720713,0,547233,TO_TIMESTAMP('2023-10-04 14:02:12.469','YYYY-MM-DD HH24:MI:SS.US'),100,'Meldungen, die durch den Importprozess generiert wurden',2000,'de.metas.contracts','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','Y','N','N','N','N','N','Import-Fehlermeldung',TO_TIMESTAMP('2023-10-04 14:02:12.469','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:12.692Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720713 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:12.693Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(912)
;

-- 2023-10-04T13:02:12.697Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720713
;

-- 2023-10-04T13:02:12.698Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720713)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Importiert
-- Column: I_ModCntr_Log.I_IsImported
-- 2023-10-04T13:02:12.845Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587500,720714,0,547233,TO_TIMESTAMP('2023-10-04 14:02:12.699','YYYY-MM-DD HH24:MI:SS.US'),100,'Ist dieser Import verarbeitet worden?',1,'de.metas.contracts','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','Y','N','N','N','N','N','Importiert',TO_TIMESTAMP('2023-10-04 14:02:12.699','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:12.846Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720714 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:12.848Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(913)
;

-- 2023-10-04T13:02:12.851Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720714
;

-- 2023-10-04T13:02:12.852Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720714)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Zeileninhalt
-- Column: I_ModCntr_Log.I_LineContent
-- 2023-10-04T13:02:12.997Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587501,720715,0,547233,TO_TIMESTAMP('2023-10-04 14:02:12.854','YYYY-MM-DD HH24:MI:SS.US'),100,4000,'de.metas.contracts','Y','Y','N','N','N','N','N','Zeileninhalt',TO_TIMESTAMP('2023-10-04 14:02:12.854','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:12.998Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720715 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:13Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577115)
;

-- 2023-10-04T13:02:13.002Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720715
;

-- 2023-10-04T13:02:13.003Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720715)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Zeile Nr.
-- Column: I_ModCntr_Log.I_LineNo
-- 2023-10-04T13:02:13.153Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587502,720716,0,547233,TO_TIMESTAMP('2023-10-04 14:02:13.005','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','Y','N','N','N','N','N','Zeile Nr.',TO_TIMESTAMP('2023-10-04 14:02:13.005','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:13.154Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720716 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:13.155Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577116)
;

-- 2023-10-04T13:02:13.158Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720716
;

-- 2023-10-04T13:02:13.158Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720716)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Aktiv
-- Column: I_ModCntr_Log.IsActive
-- 2023-10-04T13:02:13.316Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587503,720717,0,547233,TO_TIMESTAMP('2023-10-04 14:02:13.16','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.contracts','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-10-04 14:02:13.16','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:13.317Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720717 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:13.318Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-10-04T13:02:13.379Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720717
;

-- 2023-10-04T13:02:13.379Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720717)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Verarbeitet
-- Column: I_ModCntr_Log.Processed
-- 2023-10-04T13:02:13.548Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587504,720718,0,547233,TO_TIMESTAMP('2023-10-04 14:02:13.381','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'de.metas.contracts','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-10-04 14:02:13.381','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:13.549Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720718 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:13.550Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2023-10-04T13:02:13.556Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720718
;

-- 2023-10-04T13:02:13.557Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720718)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Aktualisiert
-- Column: I_ModCntr_Log.Updated
-- 2023-10-04T13:02:13.695Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587505,720719,0,547233,TO_TIMESTAMP('2023-10-04 14:02:13.559','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.contracts','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2023-10-04 14:02:13.559','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:13.696Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720719 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:13.697Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2023-10-04T13:02:13.722Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720719
;

-- 2023-10-04T13:02:13.723Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720719)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Aktualisiert durch
-- Column: I_ModCntr_Log.UpdatedBy
-- 2023-10-04T13:02:13.875Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587506,720720,0,547233,TO_TIMESTAMP('2023-10-04 14:02:13.724','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.contracts','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2023-10-04 14:02:13.724','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:13.876Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720720 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:13.877Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2023-10-04T13:02:13.903Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720720
;

-- 2023-10-04T13:02:13.903Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720720)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Import Contract Module Logs
-- Column: I_ModCntr_Log.I_ModCntr_Log_ID
-- 2023-10-04T13:02:14.054Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587507,720721,0,547233,TO_TIMESTAMP('2023-10-04 14:02:13.905','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Import Contract Module Logs',TO_TIMESTAMP('2023-10-04 14:02:13.905','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:14.055Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720721 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:14.057Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582735)
;

-- 2023-10-04T13:02:14.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720721
;

-- 2023-10-04T13:02:14.060Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720721)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> DB-Tabelle
-- Column: I_ModCntr_Log.AD_Table_ID
-- 2023-10-04T13:02:14.215Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587508,720722,0,547233,TO_TIMESTAMP('2023-10-04 14:02:14.061','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information',10,'de.metas.contracts','The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2023-10-04 14:02:14.061','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:14.216Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720722 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:14.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126)
;

-- 2023-10-04T13:02:14.225Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720722
;

-- 2023-10-04T13:02:14.226Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720722)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Belegart
-- Column: I_ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-10-04T13:02:14.380Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587509,720723,0,547233,TO_TIMESTAMP('2023-10-04 14:02:14.227','YYYY-MM-DD HH24:MI:SS.US'),100,250,'de.metas.contracts','Y','Y','N','N','N','N','N','Belegart',TO_TIMESTAMP('2023-10-04 14:02:14.227','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:14.381Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720723 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:14.382Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582470)
;

-- 2023-10-04T13:02:14.384Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720723
;

-- 2023-10-04T13:02:14.385Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720723)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Verkaufstransaktion
-- Column: I_ModCntr_Log.IsSOTrx
-- 2023-10-04T13:02:14.533Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587510,720724,0,547233,TO_TIMESTAMP('2023-10-04 14:02:14.387','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion',1,'de.metas.contracts','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','Y','N','N','N','N','N','Verkaufstransaktion',TO_TIMESTAMP('2023-10-04 14:02:14.387','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:14.534Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720724 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:14.535Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106)
;

-- 2023-10-04T13:02:14.538Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720724
;

-- 2023-10-04T13:02:14.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720724)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Nr.
-- Column: I_ModCntr_Log.DocumentNo
-- 2023-10-04T13:02:14.695Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587511,720725,0,547233,TO_TIMESTAMP('2023-10-04 14:02:14.541','YYYY-MM-DD HH24:MI:SS.US'),100,'Document sequence number of the document',40,'de.metas.contracts','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','Nr.',TO_TIMESTAMP('2023-10-04 14:02:14.541','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:14.696Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720725 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:14.697Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290)
;

-- 2023-10-04T13:02:14.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720725
;

-- 2023-10-04T13:02:14.701Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720725)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Produktschlüssel
-- Column: I_ModCntr_Log.ProductValue
-- 2023-10-04T13:02:14.848Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587512,720726,0,547233,TO_TIMESTAMP('2023-10-04 14:02:14.703','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID',40,'de.metas.contracts','Y','Y','N','N','N','N','N','Produktschlüssel',TO_TIMESTAMP('2023-10-04 14:02:14.703','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:14.849Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720726 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:14.850Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1675)
;

-- 2023-10-04T13:02:14.854Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720726
;

-- 2023-10-04T13:02:14.854Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720726)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Menge
-- Column: I_ModCntr_Log.Qty
-- 2023-10-04T13:02:15.004Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587513,720727,0,547233,TO_TIMESTAMP('2023-10-04 14:02:14.856','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge',10,'de.metas.contracts','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','Y','N','N','N','N','N','Menge',TO_TIMESTAMP('2023-10-04 14:02:14.856','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:15.005Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720727 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:15.006Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2023-10-04T13:02:15.013Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720727
;

-- 2023-10-04T13:02:15.014Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720727)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Symbol
-- Column: I_ModCntr_Log.UOMSymbol
-- 2023-10-04T13:02:15.153Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587514,720728,0,547233,TO_TIMESTAMP('2023-10-04 14:02:15.016','YYYY-MM-DD HH24:MI:SS.US'),100,'Symbol für die Maßeinheit',10,'de.metas.contracts','Symbol" bezeichnet das Symbol, das bei Verwendung dieser Maßeinheit angezeigt und gedruckt wird.','Y','Y','N','N','N','N','N','Symbol',TO_TIMESTAMP('2023-10-04 14:02:15.016','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:15.154Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720728 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:15.155Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(602)
;

-- 2023-10-04T13:02:15.157Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720728
;

-- 2023-10-04T13:02:15.158Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720728)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Betrag
-- Column: I_ModCntr_Log.Amount
-- 2023-10-04T13:02:15.309Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587515,720729,0,547233,TO_TIMESTAMP('2023-10-04 14:02:15.16','YYYY-MM-DD HH24:MI:SS.US'),100,'Betrag in einer definierten Währung',10,'de.metas.contracts','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','Y','N','N','N','N','N','Betrag',TO_TIMESTAMP('2023-10-04 14:02:15.16','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:15.309Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720729 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:15.310Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1367)
;

-- 2023-10-04T13:02:15.314Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720729
;

-- 2023-10-04T13:02:15.314Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720729)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Geschäftspartner-Schlüssel
-- Column: I_ModCntr_Log.BPartnerValue
-- 2023-10-04T13:02:15.455Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587516,720730,0,547233,TO_TIMESTAMP('2023-10-04 14:02:15.316','YYYY-MM-DD HH24:MI:SS.US'),100,'Key of the Business Partner',40,'de.metas.contracts','Y','Y','N','N','N','N','N','Geschäftspartner-Schlüssel',TO_TIMESTAMP('2023-10-04 14:02:15.316','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:15.456Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720730 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:15.457Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2094)
;

-- 2023-10-04T13:02:15.460Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720730
;

-- 2023-10-04T13:02:15.460Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720730)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Rechnungspartner Suchschlüssel
-- Column: I_ModCntr_Log.Bill_BPartner_Value
-- 2023-10-04T13:02:15.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587517,720731,0,547233,TO_TIMESTAMP('2023-10-04 14:02:15.462','YYYY-MM-DD HH24:MI:SS.US'),100,'',255,'de.metas.contracts','Y','Y','N','N','N','N','N','Rechnungspartner Suchschlüssel',TO_TIMESTAMP('2023-10-04 14:02:15.462','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:15.628Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720731 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:15.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581361)
;

-- 2023-10-04T13:02:15.632Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720731
;

-- 2023-10-04T13:02:15.632Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720731)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Collection Point Key
-- Column: I_ModCntr_Log.CollectionPointValue
-- 2023-10-04T13:02:15.767Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587518,720732,0,547233,TO_TIMESTAMP('2023-10-04 14:02:15.634','YYYY-MM-DD HH24:MI:SS.US'),100,40,'de.metas.contracts','Y','Y','N','N','N','N','N','Collection Point Key',TO_TIMESTAMP('2023-10-04 14:02:15.634','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:15.768Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720732 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:15.769Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582736)
;

-- 2023-10-04T13:02:15.772Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720732
;

-- 2023-10-04T13:02:15.772Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720732)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Vorgangsdatum
-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-04T13:02:15.968Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587519,720733,0,547233,TO_TIMESTAMP('2023-10-04 14:02:15.774','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum',7,'de.metas.contracts','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','Y','N','N','N','N','N','Vorgangsdatum',TO_TIMESTAMP('2023-10-04 14:02:15.774','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:15.969Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720733 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:15.970Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1297)
;

-- 2023-10-04T13:02:15.974Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720733
;

-- 2023-10-04T13:02:15.974Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720733)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Lager
-- Column: I_ModCntr_Log.WarehouseName
-- 2023-10-04T13:02:16.275Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587520,720734,0,547233,TO_TIMESTAMP('2023-10-04 14:02:15.976','YYYY-MM-DD HH24:MI:SS.US'),100,'Lagerbezeichnung',60,'de.metas.contracts','Y','Y','N','N','N','N','N','Lager',TO_TIMESTAMP('2023-10-04 14:02:15.976','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:16.276Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720734 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:16.277Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2280)
;

-- 2023-10-04T13:02:16.279Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720734
;

-- 2023-10-04T13:02:16.280Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720734)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Jahr
-- Column: I_ModCntr_Log.FiscalYear
-- 2023-10-04T13:02:16.428Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587521,720735,0,547233,TO_TIMESTAMP('2023-10-04 14:02:16.281','YYYY-MM-DD HH24:MI:SS.US'),100,'The Fiscal Year',20,'de.metas.contracts','The Year identifies the accounting year for a calendar.','Y','Y','N','N','N','N','N','Jahr',TO_TIMESTAMP('2023-10-04 14:02:16.281','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:02:16.429Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720735 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T13:02:16.430Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3082)
;

-- 2023-10-04T13:02:16.432Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720735
;

-- 2023-10-04T13:02:16.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720735)
;

-- Tab: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts)
-- UI Section: primary
-- 2023-10-04T13:05:52.357Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547233,545826,TO_TIMESTAMP('2023-10-04 14:05:51.96','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','primary',10,TO_TIMESTAMP('2023-10-04 14:05:51.96','YYYY-MM-DD HH24:MI:SS.US'),100,'primary')
;

-- 2023-10-04T13:05:52.360Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545826 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary
-- UI Column: 10
-- 2023-10-04T13:05:58.450Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547100,545826,TO_TIMESTAMP('2023-10-04 14:05:58.183','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-10-04 14:05:58.183','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary
-- UI Column: 20
-- 2023-10-04T13:07:43.301Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547101,545826,TO_TIMESTAMP('2023-10-04 14:07:43.01','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-10-04 14:07:43.01','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10
-- UI Element Group: primary
-- 2023-10-04T13:08:03.887Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547100,551184,TO_TIMESTAMP('2023-10-04 14:08:03.515','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2023-10-04 14:08:03.515','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Collection Point Key
-- Column: I_ModCntr_Log.CollectionPointValue
-- 2023-10-04T13:09:13.592Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720732,0,547233,551184,620617,'F',TO_TIMESTAMP('2023-10-04 14:09:13.359','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Collection Point Key',10,0,0,TO_TIMESTAMP('2023-10-04 14:09:13.359','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Lager
-- Column: I_ModCntr_Log.WarehouseName
-- 2023-10-04T13:09:54.158Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720734,0,547233,551184,620618,'F',TO_TIMESTAMP('2023-10-04 14:09:53.916','YYYY-MM-DD HH24:MI:SS.US'),100,'Lagerbezeichnung','Y','N','N','Y','N','N','N',0,'Lager',20,0,0,TO_TIMESTAMP('2023-10-04 14:09:53.916','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Geschäftspartner-Schlüssel
-- Column: I_ModCntr_Log.BPartnerValue
-- 2023-10-04T13:10:17.814Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720730,0,547233,551184,620619,'F',TO_TIMESTAMP('2023-10-04 14:10:17.508','YYYY-MM-DD HH24:MI:SS.US'),100,'Key of the Business Partner','Y','N','N','Y','N','N','N',0,'Geschäftspartner-Schlüssel',30,0,0,TO_TIMESTAMP('2023-10-04 14:10:17.508','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Rechnungspartner Suchschlüssel
-- Column: I_ModCntr_Log.Bill_BPartner_Value
-- 2023-10-04T13:10:36.151Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720731,0,547233,551184,620620,'F',TO_TIMESTAMP('2023-10-04 14:10:35.905','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungspartner Suchschlüssel',40,0,0,TO_TIMESTAMP('2023-10-04 14:10:35.905','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Jahr
-- Column: I_ModCntr_Log.FiscalYear
-- 2023-10-04T13:11:15.652Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720735,0,547233,551184,620621,'F',TO_TIMESTAMP('2023-10-04 14:11:15.378','YYYY-MM-DD HH24:MI:SS.US'),100,'The Fiscal Year','The Year identifies the accounting year for a calendar.','Y','N','N','Y','N','N','N',0,'Jahr',50,0,0,TO_TIMESTAMP('2023-10-04 14:11:15.378','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: flags
-- 2023-10-04T13:11:56.208Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547101,551185,TO_TIMESTAMP('2023-10-04 14:11:55.959','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2023-10-04 14:11:55.959','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> flags.Aktiv
-- Column: I_ModCntr_Log.IsActive
-- 2023-10-04T13:12:18.074Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720717,0,547233,551185,620622,'F',TO_TIMESTAMP('2023-10-04 14:12:17.671','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-10-04 14:12:17.671','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: import
-- 2023-10-04T13:13:14.780Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547101,551186,TO_TIMESTAMP('2023-10-04 14:13:14.522','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','import',20,TO_TIMESTAMP('2023-10-04 14:13:14.522','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> import.Importiert
-- Column: I_ModCntr_Log.I_IsImported
-- 2023-10-04T13:13:30.785Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720714,0,547233,551186,620623,'F',TO_TIMESTAMP('2023-10-04 14:13:30.522','YYYY-MM-DD HH24:MI:SS.US'),100,'Ist dieser Import verarbeitet worden?','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','Y','N','N','N',0,'Importiert',10,0,0,TO_TIMESTAMP('2023-10-04 14:13:30.522','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> import.Import-Fehlermeldung
-- Column: I_ModCntr_Log.I_ErrorMsg
-- 2023-10-04T13:13:49.956Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720713,0,547233,551186,620624,'F',TO_TIMESTAMP('2023-10-04 14:13:49.471','YYYY-MM-DD HH24:MI:SS.US'),100,'Meldungen, die durch den Importprozess generiert wurden','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','Y','N','N','N',0,'Import-Fehlermeldung',20,0,0,TO_TIMESTAMP('2023-10-04 14:13:49.471','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> flags.Verarbeitet
-- Column: I_ModCntr_Log.Processed
-- 2023-10-04T13:14:13.838Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720718,0,547233,551185,620625,'F',TO_TIMESTAMP('2023-10-04 14:14:13.565','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',20,0,0,TO_TIMESTAMP('2023-10-04 14:14:13.565','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: org
-- 2023-10-04T13:14:22.155Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547101,551187,TO_TIMESTAMP('2023-10-04 14:14:21.813','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',30,TO_TIMESTAMP('2023-10-04 14:14:21.813','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> org.Organisation
-- Column: I_ModCntr_Log.AD_Org_ID
-- 2023-10-04T13:14:35.483Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720708,0,547233,551187,620626,'F',TO_TIMESTAMP('2023-10-04 14:14:35.156','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-10-04 14:14:35.156','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> org.Mandant
-- Column: I_ModCntr_Log.AD_Client_ID
-- 2023-10-04T13:14:48.919Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720706,0,547233,551187,620627,'F',TO_TIMESTAMP('2023-10-04 14:14:48.675','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-10-04 14:14:48.675','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> import.Probleme
-- Column: I_ModCntr_Log.AD_Issue_ID
-- 2023-10-04T13:15:09.193Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720707,0,547233,551186,620628,'F',TO_TIMESTAMP('2023-10-04 14:15:08.909','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Probleme',30,0,0,TO_TIMESTAMP('2023-10-04 14:15:08.909','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Window: Import Vertragsbaustein Log, InternalName=Import Vertragsbaustein Log
-- 2023-10-04T13:22:26.851Z
UPDATE AD_Window SET InternalName='Import Vertragsbaustein Log',Updated=TO_TIMESTAMP('2023-10-04 14:22:26.85','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541737
;

-- Name: Import Vertragsbaustein Log
-- Action Type: W
-- Window: Import Vertragsbaustein Log(541737,de.metas.contracts)
-- 2023-10-04T13:22:37.245Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582737,542117,0,541737,TO_TIMESTAMP('2023-10-04 14:22:36.714','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','sd','Y','N','N','N','N','Import Vertragsbaustein Log',TO_TIMESTAMP('2023-10-04 14:22:36.714','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T13:22:37.249Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542117 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-10-04T13:22:37.253Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542117, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542117)
;

-- 2023-10-04T13:22:37.263Z
/* DDL */  select update_menu_translation_from_ad_element(582737)
;

-- Reordering children of `Contract Management`
-- Node name: `Contractpartner (C_Flatrate_Data)`
-- 2023-10-04T13:22:45.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Term)`
-- 2023-10-04T13:22:45.334Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- Node name: `Contract Invoicecandidates (C_Invoice_Clearing_Alloc)`
-- 2023-10-04T13:22:45.335Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- Node name: `Subscription History (C_SubscriptionProgress)`
-- 2023-10-04T13:22:45.335Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- Node name: `Contract Terms (C_Flatrate_Conditions)`
-- 2023-10-04T13:22:45.336Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Transition)`
-- 2023-10-04T13:22:45.336Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- Node name: `Contract Module Type (ModCntr_Type)`
-- 2023-10-04T13:22:45.337Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542086 AND AD_Tree_ID=10
;

-- Node name: `Subscriptions import (I_Flatrate_Term)`
-- 2023-10-04T13:22:45.337Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Log (ModCntr_Log)`
-- 2023-10-04T13:22:45.338Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542087 AND AD_Tree_ID=10
;

-- Node name: `Membership Month (C_MembershipMonth)`
-- 2023-10-04T13:22:45.338Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- Node name: `Abo-Rabatt (C_SubscrDiscount)`
-- 2023-10-04T13:22:45.338Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541766 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-10-04T13:22:45.339Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-10-04T13:22:45.339Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- Node name: `Type specific settings`
-- 2023-10-04T13:22:45.340Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- Node name: `Create/Update Customer Retentions (de.metas.contracts.process.C_Customer_Retention_CreateUpdate)`
-- 2023-10-04T13:22:45.340Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- Node name: `Call-off order overview (C_CallOrderSummary)`
-- 2023-10-04T13:22:45.341Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541909 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Settings (ModCntr_Settings)`
-- 2023-10-04T13:22:45.341Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542088 AND AD_Tree_ID=10
;

-- Node name: `Invoice Group (ModCntr_InvoicingGroup)`
-- 2023-10-04T13:22:45.342Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542106 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Log Status (ModCntr_Log_Status)`
-- 2023-10-04T13:22:45.342Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542111 AND AD_Tree_ID=10
;

-- Node name: `Import Vertragsbaustein Log`
-- 2023-10-04T13:22:45.342Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542117 AND AD_Tree_ID=10
;

-- Name: Import Vertragsbaustein Log
-- Action Type: W
-- Window: Import Vertragsbaustein Log(541737,de.metas.contracts)
-- 2023-10-04T13:22:52.339Z
UPDATE AD_Menu SET InternalName='Import Vertragsbaustein Log',Updated=TO_TIMESTAMP('2023-10-04 14:22:52.339','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Menu_ID=542117
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: org
-- 2023-10-04T13:32:14.475Z
UPDATE AD_UI_ElementGroup SET SeqNo=100,Updated=TO_TIMESTAMP('2023-10-04 14:32:14.475','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551187
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: import
-- 2023-10-04T13:32:24.002Z
UPDATE AD_UI_ElementGroup SET SeqNo=90,Updated=TO_TIMESTAMP('2023-10-04 14:32:24.002','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551186
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: dates
-- 2023-10-04T13:34:10.863Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547101,551188,TO_TIMESTAMP('2023-10-04 14:34:10.436','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','dates',15,TO_TIMESTAMP('2023-10-04 14:34:10.436','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> dates.Vorgangsdatum
-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-04T13:34:34.042Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720733,0,547233,551188,620629,'F',TO_TIMESTAMP('2023-10-04 14:34:33.773','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','Y','N','N','N',0,'Vorgangsdatum',10,0,0,TO_TIMESTAMP('2023-10-04 14:34:33.773','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: qty
-- 2023-10-04T13:35:18.323Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547101,551189,TO_TIMESTAMP('2023-10-04 14:35:18.001','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','qty',20,TO_TIMESTAMP('2023-10-04 14:35:18.001','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Menge
-- Column: I_ModCntr_Log.Qty
-- 2023-10-04T13:35:50.205Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720727,0,547233,551189,620630,'F',TO_TIMESTAMP('2023-10-04 14:35:49.934','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','N','N',0,'Menge',10,0,0,TO_TIMESTAMP('2023-10-04 14:35:49.934','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Symbol
-- Column: I_ModCntr_Log.UOMSymbol
-- 2023-10-04T13:36:11.103Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720728,0,547233,551189,620631,'F',TO_TIMESTAMP('2023-10-04 14:36:10.808','YYYY-MM-DD HH24:MI:SS.US'),100,'Symbol für die Maßeinheit','Symbol" bezeichnet das Symbol, das bei Verwendung dieser Maßeinheit angezeigt und gedruckt wird.','Y','N','N','Y','N','N','N',0,'Symbol',20,0,0,TO_TIMESTAMP('2023-10-04 14:36:10.808','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Lager
-- Column: I_ModCntr_Log.WarehouseName
-- 2023-10-04T14:55:34.558Z
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2023-10-04 15:55:34.558','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620618
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Produktschlüssel
-- Column: I_ModCntr_Log.ProductValue
-- 2023-10-04T14:56:02.484Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720726,0,547233,551184,620632,'F',TO_TIMESTAMP('2023-10-04 15:56:01.691','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID','Y','N','N','Y','N','N','N',0,'Produktschlüssel',110,0,0,TO_TIMESTAMP('2023-10-04 15:56:01.691','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Produktschlüssel
-- Column: I_ModCntr_Log.ProductValue
-- 2023-10-04T14:56:22.659Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2023-10-04 15:56:22.659','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620632
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: tableIDs
-- 2023-10-04T14:57:34.993Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547101,551190,TO_TIMESTAMP('2023-10-04 15:57:34.683','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','tableIDs',25,TO_TIMESTAMP('2023-10-04 15:57:34.683','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> tableIDs.DB-Tabelle
-- Column: I_ModCntr_Log.AD_Table_ID
-- 2023-10-04T14:58:04.052Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720722,0,547233,551190,620633,'F',TO_TIMESTAMP('2023-10-04 15:58:03.781','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','N','N',0,'DB-Tabelle',10,0,0,TO_TIMESTAMP('2023-10-04 15:58:03.781','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Betrag
-- Column: I_ModCntr_Log.Amount
-- 2023-10-04T15:02:07.104Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720729,0,547233,551189,620634,'F',TO_TIMESTAMP('2023-10-04 16:02:06.744','YYYY-MM-DD HH24:MI:SS.US'),100,'Betrag in einer definierten Währung','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','Y','N','N','N',0,'Betrag',30,0,0,TO_TIMESTAMP('2023-10-04 16:02:06.744','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: I_ModCntr_Log.ISO_Code
-- 2023-10-04T15:06:41.962Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587522,328,0,10,542372,'ISO_Code',TO_TIMESTAMP('2023-10-04 16:06:41.47','YYYY-MM-DD HH24:MI:SS.US'),100,'N','	','Dreibuchstabiger ISO 4217 Code für die Währung','de.metas.contracts',0,3,'Für Details - http://www.unece.org/cefact/recommendations/rec09/rec09_ecetrd203.pdf','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ISO Währungscode',0,0,TO_TIMESTAMP('2023-10-04 16:06:41.47','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-04T15:06:41.966Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587522 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-04T15:06:41.973Z
/* DDL */  select update_Column_Translation_From_AD_Element(328)
;

-- 2023-10-04T15:06:46.844Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN ISO_Code VARCHAR(3) DEFAULT ''	''')
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> ISO Währungscode
-- Column: I_ModCntr_Log.ISO_Code
-- 2023-10-04T15:07:20.278Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587522,720736,0,547233,TO_TIMESTAMP('2023-10-04 16:07:19.803','YYYY-MM-DD HH24:MI:SS.US'),100,'Dreibuchstabiger ISO 4217 Code für die Währung',3,'de.metas.contracts','Für Details - http://www.unece.org/cefact/recommendations/rec09/rec09_ecetrd203.pdf','Y','N','N','N','N','N','N','N','ISO Währungscode',TO_TIMESTAMP('2023-10-04 16:07:19.803','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T15:07:20.281Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720736 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-04T15:07:20.284Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(328)
;

-- 2023-10-04T15:07:20.297Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720736
;

-- 2023-10-04T15:07:20.301Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720736)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.ISO Währungscode
-- Column: I_ModCntr_Log.ISO_Code
-- 2023-10-04T15:07:40.228Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720736,0,547233,551189,620635,'F',TO_TIMESTAMP('2023-10-04 16:07:39.864','YYYY-MM-DD HH24:MI:SS.US'),100,'Dreibuchstabiger ISO 4217 Code für die Währung','Für Details - http://www.unece.org/cefact/recommendations/rec09/rec09_ecetrd203.pdf','Y','N','N','Y','N','N','N',0,'ISO Währungscode',40,0,0,TO_TIMESTAMP('2023-10-04 16:07:39.864','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10
-- UI Element Group: secondary
-- 2023-10-04T15:09:20.516Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547100,551191,TO_TIMESTAMP('2023-10-04 16:09:20.235','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','secondary',20,TO_TIMESTAMP('2023-10-04 16:09:20.235','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Belegart
-- Column: I_ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-10-04T15:09:55.554Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720723,0,547233,551191,620636,'F',TO_TIMESTAMP('2023-10-04 16:09:55.19','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Belegart',10,0,0,TO_TIMESTAMP('2023-10-04 16:09:55.19','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Nr.
-- Column: I_ModCntr_Log.DocumentNo
-- 2023-10-04T15:10:49.406Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720725,0,547233,551191,620637,'F',TO_TIMESTAMP('2023-10-04 16:10:49.07','YYYY-MM-DD HH24:MI:SS.US'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Nr.',20,0,0,TO_TIMESTAMP('2023-10-04 16:10:49.07','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Zeile Nr.
-- Column: I_ModCntr_Log.I_LineNo
-- 2023-10-04T15:11:37.292Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720716,0,547233,551184,620638,'F',TO_TIMESTAMP('2023-10-04 16:11:36.792','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zeile Nr.',5,0,0,TO_TIMESTAMP('2023-10-04 16:11:36.792','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Jahr
-- Column: I_ModCntr_Log.FiscalYear
-- 2023-10-04T15:12:53.041Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551191, SeqNo=30,Updated=TO_TIMESTAMP('2023-10-04 16:12:53.041','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620621
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Rechnungspartner Suchschlüssel
-- Column: I_ModCntr_Log.Bill_BPartner_Value
-- 2023-10-04T18:12:52.392Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551191, SeqNo=40,Updated=TO_TIMESTAMP('2023-10-04 19:12:52.392','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620620
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Geschäftspartner-Schlüssel
-- Column: I_ModCntr_Log.BPartnerValue
-- 2023-10-04T18:14:34.039Z
UPDATE AD_UI_Element SET SeqNo=22,Updated=TO_TIMESTAMP('2023-10-04 19:14:34.039','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620619
;

