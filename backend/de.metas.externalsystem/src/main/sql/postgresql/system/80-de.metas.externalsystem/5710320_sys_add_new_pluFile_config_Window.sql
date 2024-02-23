-- 2023-11-14T09:57:14.401Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582803,0,TO_TIMESTAMP('2023-11-14 10:57:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','PLU-Datei Konfiguration','PLU-Datei Konfiguration',TO_TIMESTAMP('2023-11-14 10:57:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T09:57:14.414Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582803 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-11-14T09:57:34.012Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-14 10:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582803 AND AD_Language='de_CH'
;

-- 2023-11-14T09:57:34.022Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582803,'de_CH')
;

-- Element: null
-- 2023-11-14T09:57:35.583Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-14 10:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582803 AND AD_Language='de_DE'
;

-- 2023-11-14T09:57:35.598Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582803,'de_DE')
;

-- 2023-11-14T09:57:35.609Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582803,'de_DE')
;

-- Element: null
-- 2023-11-14T09:58:21.683Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLU File Configuration', PrintName='PLU File Configuration',Updated=TO_TIMESTAMP('2023-11-14 10:58:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582803 AND AD_Language='en_US'
;

-- 2023-11-14T09:58:21.685Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582803,'en_US')
;

-- Window: PLU-Datei Konfiguration, InternalName=null
-- Window: PLU-Datei Konfiguration, InternalName=null
-- 2023-11-14T12:24:47.541Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582803,0,541751,TO_TIMESTAMP('2023-11-14 13:24:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','N','N','N','Y','PLU-Datei Konfiguration','N',TO_TIMESTAMP('2023-11-14 13:24:47','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-11-14T12:24:47.566Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541751 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-11-14T12:24:47.609Z
/* DDL */  select update_window_translation_from_ad_element(582803)
;

-- 2023-11-14T12:24:47.647Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541751
;

-- 2023-11-14T12:24:47.672Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541751)
;

-- Tab: PLU-Datei Konfiguration -> PLU File Configuration
-- Table: LeichMehl_PluFile_ConfigGroup
-- Tab: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration
-- Table: LeichMehl_PluFile_ConfigGroup
-- 2023-11-14T12:27:36.377Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582801,0,547278,542378,541751,'Y',TO_TIMESTAMP('2023-11-14 13:27:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','LeichMehl_PluFile_ConfigGroup','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'PLU File Configuration','N',10,0,TO_TIMESTAMP('2023-11-14 13:27:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:27:36.392Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547278 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-11-14T12:27:36.401Z
/* DDL */  select update_tab_translation_from_ad_element(582801)
;

-- 2023-11-14T12:27:36.423Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547278)
;

-- Field: PLU-Datei Konfiguration -> PLU File Configuration -> Mandant
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Client_ID
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> Mandant
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Client_ID
-- 2023-11-14T12:28:53.214Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587633,721819,0,547278,TO_TIMESTAMP('2023-11-14 13:28:53','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-11-14 13:28:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:28:53.224Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:28:53.234Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-11-14T12:28:55.428Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721819
;

-- 2023-11-14T12:28:55.429Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721819)
;

-- Field: PLU-Datei Konfiguration -> PLU File Configuration -> Sektion
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Org_ID
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> Sektion
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Org_ID
-- 2023-11-14T12:28:55.527Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587634,721820,0,547278,TO_TIMESTAMP('2023-11-14 13:28:55','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-11-14 13:28:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:28:55.528Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:28:55.529Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-11-14T12:28:56.036Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721820
;

-- 2023-11-14T12:28:56.037Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721820)
;

-- Field: PLU-Datei Konfiguration -> PLU File Configuration -> Aktiv
-- Column: LeichMehl_PluFile_ConfigGroup.IsActive
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> Aktiv
-- Column: LeichMehl_PluFile_ConfigGroup.IsActive
-- 2023-11-14T12:28:56.123Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587637,721821,0,547278,TO_TIMESTAMP('2023-11-14 13:28:56','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-11-14 13:28:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:28:56.125Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721821 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:28:56.127Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-11-14T12:28:56.432Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721821
;

-- 2023-11-14T12:28:56.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721821)
;

-- Field: PLU-Datei Konfiguration -> PLU File Configuration -> PLU File Configuration
-- Column: LeichMehl_PluFile_ConfigGroup.LeichMehl_PluFile_ConfigGroup_ID
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> PLU File Configuration
-- Column: LeichMehl_PluFile_ConfigGroup.LeichMehl_PluFile_ConfigGroup_ID
-- 2023-11-14T12:28:56.522Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587640,721822,0,547278,TO_TIMESTAMP('2023-11-14 13:28:56','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','PLU File Configuration',TO_TIMESTAMP('2023-11-14 13:28:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:28:56.524Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:28:56.525Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582801)
;

-- 2023-11-14T12:28:56.529Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721822
;

-- 2023-11-14T12:28:56.529Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721822)
;

-- Field: PLU-Datei Konfiguration -> PLU File Configuration -> Name
-- Column: LeichMehl_PluFile_ConfigGroup.Name
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> Name
-- Column: LeichMehl_PluFile_ConfigGroup.Name
-- 2023-11-14T12:28:56.615Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587641,721823,0,547278,TO_TIMESTAMP('2023-11-14 13:28:56','YYYY-MM-DD HH24:MI:SS'),100,'',40,'de.metas.externalsystem','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-11-14 13:28:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:28:56.617Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:28:56.618Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2023-11-14T12:28:57.045Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721823
;

-- 2023-11-14T12:28:57.046Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721823)
;

-- Tab: PLU-Datei Konfiguration -> PLU-Datei Konfig
-- Table: LeichMehl_PluFile_Config
-- Tab: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig
-- Table: LeichMehl_PluFile_Config
-- 2023-11-14T12:30:21.119Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581079,0,547279,542182,541751,'Y',TO_TIMESTAMP('2023-11-14 13:30:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','LeichMehl_PluFile_Config','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'PLU-Datei Konfig','N',20,1,TO_TIMESTAMP('2023-11-14 13:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:30:21.124Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547279 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-11-14T12:30:21.125Z
/* DDL */  select update_tab_translation_from_ad_element(581079)
;

-- 2023-11-14T12:30:21.132Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547279)
;

-- Tab: PLU-Datei Konfiguration -> PLU-Datei Konfig
-- Table: LeichMehl_PluFile_Config
-- Tab: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig
-- Table: LeichMehl_PluFile_Config
-- 2023-11-14T15:07:22.576Z
UPDATE AD_Tab SET Parent_Column_ID=587640,Updated=TO_TIMESTAMP('2023-11-14 16:07:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547279
;

-- Tab: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem)
-- UI Section: main
-- 2023-11-14T12:30:42.677Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547278,545867,TO_TIMESTAMP('2023-11-14 13:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-11-14 13:30:42','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-11-14T12:30:42.681Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545867 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2023-11-14T12:30:42.766Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547160,545867,TO_TIMESTAMP('2023-11-14 13:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-11-14 13:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main
-- UI Column: 20
-- 2023-11-14T12:30:42.873Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547161,545867,TO_TIMESTAMP('2023-11-14 13:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-11-14 13:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main -> 10
-- UI Element Group: default
-- 2023-11-14T12:30:42.963Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547160,551297,TO_TIMESTAMP('2023-11-14 13:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-11-14 13:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem)
-- UI Section: main
-- 2023-11-14T12:30:43.044Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547279,545868,TO_TIMESTAMP('2023-11-14 13:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-11-14 13:30:42','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-11-14T12:30:43.045Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545868 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2023-11-14T12:30:43.134Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547162,545868,TO_TIMESTAMP('2023-11-14 13:30:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-11-14 13:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10
-- UI Element Group: default
-- 2023-11-14T12:30:43.209Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547162,551298,TO_TIMESTAMP('2023-11-14 13:30:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-11-14 13:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> Mandant
-- Column: LeichMehl_PluFile_Config.AD_Client_ID
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> Mandant
-- Column: LeichMehl_PluFile_Config.AD_Client_ID
-- 2023-11-14T12:32:37.692Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583570,721824,0,547279,TO_TIMESTAMP('2023-11-14 13:32:37','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-11-14 13:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:32:37.695Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:32:37.698Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-11-14T12:32:37.819Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721824
;

-- 2023-11-14T12:32:37.820Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721824)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> Sektion
-- Column: LeichMehl_PluFile_Config.AD_Org_ID
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> Sektion
-- Column: LeichMehl_PluFile_Config.AD_Org_ID
-- 2023-11-14T12:32:37.912Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583571,721825,0,547279,TO_TIMESTAMP('2023-11-14 13:32:37','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-11-14 13:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:32:37.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:32:37.914Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-11-14T12:32:38.028Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721825
;

-- 2023-11-14T12:32:38.029Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721825)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> Aktiv
-- Column: LeichMehl_PluFile_Config.IsActive
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> Aktiv
-- Column: LeichMehl_PluFile_Config.IsActive
-- 2023-11-14T12:32:38.113Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583574,721826,0,547279,TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:32:38.115Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:32:38.117Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-11-14T12:32:38.174Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721826
;

-- 2023-11-14T12:32:38.175Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721826)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> PLU-Datei Konfig
-- Column: LeichMehl_PluFile_Config.LeichMehl_PluFile_Config_ID
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> PLU-Datei Konfig
-- Column: LeichMehl_PluFile_Config.LeichMehl_PluFile_Config_ID
-- 2023-11-14T12:32:38.266Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583577,721827,0,547279,TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','PLU-Datei Konfig',TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:32:38.267Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:32:38.268Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581079)
;

-- 2023-11-14T12:32:38.272Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721827
;

-- 2023-11-14T12:32:38.272Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721827)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> Zielfeld
-- Column: LeichMehl_PluFile_Config.TargetFieldName
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> Zielfeld
-- Column: LeichMehl_PluFile_Config.TargetFieldName
-- 2023-11-14T12:32:38.357Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583579,721828,0,547279,TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100,'Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Zielfeld',TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:32:38.358Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:32:38.360Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581080)
;

-- 2023-11-14T12:32:38.364Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721828
;

-- 2023-11-14T12:32:38.365Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721828)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> Zielfeldtyp
-- Column: LeichMehl_PluFile_Config.TargetFieldType
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> Zielfeldtyp
-- Column: LeichMehl_PluFile_Config.TargetFieldType
-- 2023-11-14T12:32:38.438Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583580,721829,0,547279,TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100,'Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.',255,'de.metas.externalsystem','','Y','N','N','N','N','N','N','N','Zielfeldtyp',TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:32:38.439Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:32:38.441Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581081)
;

-- 2023-11-14T12:32:38.448Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721829
;

-- 2023-11-14T12:32:38.449Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721829)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> Ersatz
-- Column: LeichMehl_PluFile_Config.Replacement
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> Ersatz
-- Column: LeichMehl_PluFile_Config.Replacement
-- 2023-11-14T12:32:38.536Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583582,721830,0,547279,TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100,'Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Ersatz',TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:32:38.537Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:32:38.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581083)
;

-- 2023-11-14T12:32:38.542Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721830
;

-- 2023-11-14T12:32:38.542Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721830)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> Ersatzquelle
-- Column: LeichMehl_PluFile_Config.ReplacementSource
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> Ersatzquelle
-- Column: LeichMehl_PluFile_Config.ReplacementSource
-- 2023-11-14T12:32:38.631Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583589,721831,0,547279,TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100,'Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.',5,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Ersatzquelle',TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:32:38.632Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:32:38.633Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581089)
;

-- 2023-11-14T12:32:38.637Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721831
;

-- 2023-11-14T12:32:38.638Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721831)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> Regulärer Ersetzungsausdruck
-- Column: LeichMehl_PluFile_Config.ReplaceRegExp
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> Regulärer Ersetzungsausdruck
-- Column: LeichMehl_PluFile_Config.ReplaceRegExp
-- 2023-11-14T12:32:38.728Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583590,721832,0,547279,TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100,'Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Regulärer Ersetzungsausdruck',TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:32:38.730Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:32:38.732Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581091)
;

-- 2023-11-14T12:32:38.736Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721832
;

-- 2023-11-14T12:32:38.737Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721832)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> PLU File Configuration
-- Column: LeichMehl_PluFile_Config.LeichMehl_PluFile_ConfigGroup_ID
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> PLU File Configuration
-- Column: LeichMehl_PluFile_Config.LeichMehl_PluFile_ConfigGroup_ID
-- 2023-11-14T12:32:38.815Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587644,721833,0,547279,TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','PLU File Configuration',TO_TIMESTAMP('2023-11-14 13:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T12:32:38.816Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T12:32:38.818Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582801)
;

-- 2023-11-14T12:32:38.821Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721833
;

-- 2023-11-14T12:32:38.822Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721833)
;

-- UI Element: PLU-Datei Konfiguration -> PLU File Configuration.Name
-- Column: LeichMehl_PluFile_ConfigGroup.Name
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main -> 10 -> default.Name
-- Column: LeichMehl_PluFile_ConfigGroup.Name
-- 2023-11-14T12:35:20.416Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721823,0,547278,551297,621254,'F',TO_TIMESTAMP('2023-11-14 13:35:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2023-11-14 13:35:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main -> 20
-- UI Element Group: default
-- 2023-11-14T12:37:53.436Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547161,551299,TO_TIMESTAMP('2023-11-14 13:37:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'',TO_TIMESTAMP('2023-11-14 13:37:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU File Configuration.Aktiv
-- Column: LeichMehl_PluFile_ConfigGroup.IsActive
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main -> 20 -> default.Aktiv
-- Column: LeichMehl_PluFile_ConfigGroup.IsActive
-- 2023-11-14T12:38:20.974Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721821,0,547278,551299,621255,'F',TO_TIMESTAMP('2023-11-14 13:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-11-14 13:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main -> 20
-- UI Element Group: orgClient
-- 2023-11-14T12:38:44.280Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547161,551300,TO_TIMESTAMP('2023-11-14 13:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','orgClient',20,TO_TIMESTAMP('2023-11-14 13:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU File Configuration.Sektion
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Org_ID
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main -> 20 -> orgClient.Sektion
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Org_ID
-- 2023-11-14T12:39:36.141Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721820,0,547278,551300,621256,'F',TO_TIMESTAMP('2023-11-14 13:39:36','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2023-11-14 13:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU File Configuration.Mandant
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Client_ID
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main -> 20 -> orgClient.Mandant
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Client_ID
-- 2023-11-14T12:40:15.165Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721819,0,547278,551300,621257,'F',TO_TIMESTAMP('2023-11-14 13:40:15','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-11-14 13:40:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU File Configuration.Name
-- Column: LeichMehl_PluFile_ConfigGroup.Name
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main -> 10 -> default.Name
-- Column: LeichMehl_PluFile_ConfigGroup.Name
-- 2023-11-14T12:40:41.918Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-11-14 13:40:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621254
;

-- UI Element: PLU-Datei Konfiguration -> PLU File Configuration.Aktiv
-- Column: LeichMehl_PluFile_ConfigGroup.IsActive
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main -> 20 -> default.Aktiv
-- Column: LeichMehl_PluFile_ConfigGroup.IsActive
-- 2023-11-14T12:40:41.926Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-11-14 13:40:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621255
;

-- UI Element: PLU-Datei Konfiguration -> PLU File Configuration.Sektion
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Org_ID
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU File Configuration(547278,de.metas.externalsystem) -> main -> 20 -> orgClient.Sektion
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Org_ID
-- 2023-11-14T12:40:41.934Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-11-14 13:40:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621256
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.PLU-Datei Konfig
-- Column: LeichMehl_PluFile_Config.LeichMehl_PluFile_Config_ID
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.PLU-Datei Konfig
-- Column: LeichMehl_PluFile_Config.LeichMehl_PluFile_Config_ID
-- 2023-11-14T12:47:11.916Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721827,0,547279,551298,621258,'F',TO_TIMESTAMP('2023-11-14 13:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'PLU-Datei Konfig',10,0,0,TO_TIMESTAMP('2023-11-14 13:47:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Zielfeld
-- Column: LeichMehl_PluFile_Config.TargetFieldName
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Zielfeld
-- Column: LeichMehl_PluFile_Config.TargetFieldName
-- 2023-11-14T12:47:42.395Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721828,0,547279,551298,621259,'F',TO_TIMESTAMP('2023-11-14 13:47:42','YYYY-MM-DD HH24:MI:SS'),100,'Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ','Y','N','N','Y','N','N','N',0,'Zielfeld',20,0,0,TO_TIMESTAMP('2023-11-14 13:47:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Zielfeldtyp
-- Column: LeichMehl_PluFile_Config.TargetFieldType
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Zielfeldtyp
-- Column: LeichMehl_PluFile_Config.TargetFieldType
-- 2023-11-14T12:48:27.675Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721829,0,547279,551298,621260,'F',TO_TIMESTAMP('2023-11-14 13:48:27','YYYY-MM-DD HH24:MI:SS'),100,'Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.','Y','N','N','Y','N','N','N',0,'Zielfeldtyp',30,0,0,TO_TIMESTAMP('2023-11-14 13:48:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Ersatz
-- Column: LeichMehl_PluFile_Config.Replacement
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Ersatz
-- Column: LeichMehl_PluFile_Config.Replacement
-- 2023-11-14T12:49:36.759Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721830,0,547279,551298,621261,'F',TO_TIMESTAMP('2023-11-14 13:49:36','YYYY-MM-DD HH24:MI:SS'),100,'Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.','Y','N','N','Y','N','N','N',0,'Ersatz',40,0,0,TO_TIMESTAMP('2023-11-14 13:49:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Regulärer Ersetzungsausdruck
-- Column: LeichMehl_PluFile_Config.ReplaceRegExp
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Regulärer Ersetzungsausdruck
-- Column: LeichMehl_PluFile_Config.ReplaceRegExp
-- 2023-11-14T12:51:18.888Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721832,0,547279,551298,621262,'F',TO_TIMESTAMP('2023-11-14 13:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."','Y','N','N','Y','N','N','N',0,'Regulärer Ersetzungsausdruck',50,0,0,TO_TIMESTAMP('2023-11-14 13:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Ersatzquelle
-- Column: LeichMehl_PluFile_Config.ReplacementSource
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Ersatzquelle
-- Column: LeichMehl_PluFile_Config.ReplacementSource
-- 2023-11-14T12:51:50.837Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721831,0,547279,551298,621263,'F',TO_TIMESTAMP('2023-11-14 13:51:50','YYYY-MM-DD HH24:MI:SS'),100,'Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.','Y','N','N','Y','N','N','N',0,'Ersatzquelle',60,0,0,TO_TIMESTAMP('2023-11-14 13:51:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Aktiv
-- Column: LeichMehl_PluFile_Config.IsActive
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Aktiv
-- Column: LeichMehl_PluFile_Config.IsActive
-- 2023-11-14T12:53:11.726Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721826,0,547279,551298,621264,'F',TO_TIMESTAMP('2023-11-14 13:53:11','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',70,0,0,TO_TIMESTAMP('2023-11-14 13:53:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.PLU-Datei Konfig
-- Column: LeichMehl_PluFile_Config.LeichMehl_PluFile_Config_ID
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.PLU-Datei Konfig
-- Column: LeichMehl_PluFile_Config.LeichMehl_PluFile_Config_ID
-- 2023-11-14T12:53:57.015Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-11-14 13:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621258
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Zielfeld
-- Column: LeichMehl_PluFile_Config.TargetFieldName
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Zielfeld
-- Column: LeichMehl_PluFile_Config.TargetFieldName
-- 2023-11-14T12:53:57.035Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-11-14 13:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621259
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Zielfeldtyp
-- Column: LeichMehl_PluFile_Config.TargetFieldType
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Zielfeldtyp
-- Column: LeichMehl_PluFile_Config.TargetFieldType
-- 2023-11-14T12:53:57.043Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-11-14 13:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621260
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Ersatz
-- Column: LeichMehl_PluFile_Config.Replacement
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Ersatz
-- Column: LeichMehl_PluFile_Config.Replacement
-- 2023-11-14T12:53:57.048Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-11-14 13:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621261
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Regulärer Ersetzungsausdruck
-- Column: LeichMehl_PluFile_Config.ReplaceRegExp
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Regulärer Ersetzungsausdruck
-- Column: LeichMehl_PluFile_Config.ReplaceRegExp
-- 2023-11-14T12:53:57.054Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-11-14 13:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621262
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Ersatzquelle
-- Column: LeichMehl_PluFile_Config.ReplacementSource
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Ersatzquelle
-- Column: LeichMehl_PluFile_Config.ReplacementSource
-- 2023-11-14T12:53:57.059Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-11-14 13:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621263
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Aktiv
-- Column: LeichMehl_PluFile_Config.IsActive
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Aktiv
-- Column: LeichMehl_PluFile_Config.IsActive
-- 2023-11-14T12:53:57.063Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-11-14 13:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621264
;

-- Name: PLU-Datei Konfiguration
-- Action Type: W
-- Window: PLU-Datei Konfiguration(541751,de.metas.externalsystem)
-- 2023-11-14T13:20:30.334Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582803,542129,0,541751,TO_TIMESTAMP('2023-11-14 14:20:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','leichMehl_pluFile_configGroup','Y','N','N','N','N','PLU-Datei Konfiguration',TO_TIMESTAMP('2023-11-14 14:20:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T13:20:30.338Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542129 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-11-14T13:20:30.341Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542129, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542129)
;

-- 2023-11-14T13:20:30.344Z
/* DDL */  select update_menu_translation_from_ad_element(582803)
;

-- Reordering children of `Settings`
-- Node name: `Quality Note (M_QualityNote)`
-- 2023-11-14T13:20:35.064Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000076, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540978 AND AD_Tree_ID=10
;

-- Node name: `PLU-Datei Konfiguration`
-- 2023-11-14T13:20:35.065Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000076, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542129 AND AD_Tree_ID=10
;

-- Reordering children of `Settings`
-- Node name: `PLU-Datei Konfiguration`
-- 2023-11-14T13:20:51.514Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542129 AND AD_Tree_ID=10
;

-- Node name: `Attribute`
-- 2023-11-14T13:20:51.515Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541598 AND AD_Tree_ID=10
;

-- Node name: `Dimension`
-- 2023-11-14T13:20:51.516Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540610 AND AD_Tree_ID=10
;

-- Node name: `Product Translation (M_Product_Trl)`
-- 2023-11-14T13:20:51.517Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541054 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Translation (PP_Product_BOM_Trl)`
-- 2023-11-14T13:20:51.518Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- Node name: `Product Businesspartner Translation (C_BPartner_Product_Trl)`
-- 2023-11-14T13:20:51.519Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541068 AND AD_Tree_ID=10
;

-- Node name: `Unit of Measure (C_UOM)`
-- 2023-11-14T13:20:51.520Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540794 AND AD_Tree_ID=10
;

-- Node name: `Unit of Measure Translation (C_UOM_Trl)`
-- 2023-11-14T13:20:51.521Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541101 AND AD_Tree_ID=10
;

-- Node name: `Compensation Group Schema (C_CompensationGroup_Schema)`
-- 2023-11-14T13:20:51.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541040 AND AD_Tree_ID=10
;

-- Node name: `Compensation Group Schema Category (C_CompensationGroup_Schema_Category)`
-- 2023-11-14T13:20:51.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541726 AND AD_Tree_ID=10
;

-- Node name: `Product Category (M_Product_Category)`
-- 2023-11-14T13:20:51.525Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000096 AND AD_Tree_ID=10
;

-- Node name: `Shop Category (M_Shop_Category)`
-- 2023-11-14T13:20:51.526Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541722 AND AD_Tree_ID=10
;

-- Node name: `Product Category Trl (M_Product_Category_Trl)`
-- 2023-11-14T13:20:51.527Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541130 AND AD_Tree_ID=10
;

