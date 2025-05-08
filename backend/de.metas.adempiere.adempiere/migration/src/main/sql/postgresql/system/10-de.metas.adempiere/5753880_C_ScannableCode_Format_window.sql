-- Window: Scannable Code Format, InternalName=null
-- Window: Scannable Code Format, InternalName=null
-- 2025-05-07T20:16:28.269Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583610,0,541880,TO_TIMESTAMP('2025-05-07 23:16:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Scannable Code Format','N',TO_TIMESTAMP('2025-05-07 23:16:27','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2025-05-07T20:16:28.274Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541880 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-05-07T20:16:28.514Z
/* DDL */  select update_window_translation_from_ad_element(583610) 
;

-- 2025-05-07T20:16:28.562Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541880
;

-- 2025-05-07T20:16:28.572Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541880)
;

-- Tab: Scannable Code Format -> Scannable Code Format
-- Table: C_ScannableCode_Format
-- Tab: Scannable Code Format(541880,D) -> Scannable Code Format
-- Table: C_ScannableCode_Format
-- 2025-05-07T20:17:22.460Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583610,0,547957,542475,541880,'Y',TO_TIMESTAMP('2025-05-07 23:17:22','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_ScannableCode_Format','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Scannable Code Format','N',10,0,TO_TIMESTAMP('2025-05-07 23:17:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:17:22.464Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547957 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-05-07T20:17:22.468Z
/* DDL */  select update_tab_translation_from_ad_element(583610) 
;

-- 2025-05-07T20:17:22.484Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547957)
;

-- Field: Scannable Code Format -> Scannable Code Format -> Mandant
-- Column: C_ScannableCode_Format.AD_Client_ID
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> Mandant
-- Column: C_ScannableCode_Format.AD_Client_ID
-- 2025-05-07T20:17:27.119Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589930,742008,0,547957,TO_TIMESTAMP('2025-05-07 23:17:26','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-05-07 23:17:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:17:27.124Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:17:27.130Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2025-05-07T20:17:27.797Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742008
;

-- 2025-05-07T20:17:27.801Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742008)
;

-- Field: Scannable Code Format -> Scannable Code Format -> Organisation
-- Column: C_ScannableCode_Format.AD_Org_ID
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> Organisation
-- Column: C_ScannableCode_Format.AD_Org_ID
-- 2025-05-07T20:17:27.960Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589931,742009,0,547957,TO_TIMESTAMP('2025-05-07 23:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2025-05-07 23:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:17:27.963Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:17:27.966Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2025-05-07T20:17:28.065Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742009
;

-- 2025-05-07T20:17:28.066Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742009)
;

-- Field: Scannable Code Format -> Scannable Code Format -> Aktiv
-- Column: C_ScannableCode_Format.IsActive
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> Aktiv
-- Column: C_ScannableCode_Format.IsActive
-- 2025-05-07T20:17:28.201Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589934,742010,0,547957,TO_TIMESTAMP('2025-05-07 23:17:28','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-05-07 23:17:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:17:28.203Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:17:28.205Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2025-05-07T20:17:28.332Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742010
;

-- 2025-05-07T20:17:28.334Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742010)
;

-- Field: Scannable Code Format -> Scannable Code Format -> Scannable Code Format
-- Column: C_ScannableCode_Format.C_ScannableCode_Format_ID
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> Scannable Code Format
-- Column: C_ScannableCode_Format.C_ScannableCode_Format_ID
-- 2025-05-07T20:17:28.468Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589937,742011,0,547957,TO_TIMESTAMP('2025-05-07 23:17:28','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Scannable Code Format',TO_TIMESTAMP('2025-05-07 23:17:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:17:28.471Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:17:28.472Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583610) 
;

-- 2025-05-07T20:17:28.477Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742011
;

-- 2025-05-07T20:17:28.478Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742011)
;

-- Field: Scannable Code Format -> Scannable Code Format -> Name
-- Column: C_ScannableCode_Format.Name
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> Name
-- Column: C_ScannableCode_Format.Name
-- 2025-05-07T20:17:28.651Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589938,742012,0,547957,TO_TIMESTAMP('2025-05-07 23:17:28','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2025-05-07 23:17:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:17:28.654Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:17:28.656Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2025-05-07T20:17:28.719Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742012
;

-- 2025-05-07T20:17:28.720Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742012)
;

-- Field: Scannable Code Format -> Scannable Code Format -> Beschreibung
-- Column: C_ScannableCode_Format.Description
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> Beschreibung
-- Column: C_ScannableCode_Format.Description
-- 2025-05-07T20:17:28.853Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589939,742013,0,547957,TO_TIMESTAMP('2025-05-07 23:17:28','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2025-05-07 23:17:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:17:28.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:17:28.858Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2025-05-07T20:17:28.937Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742013
;

-- 2025-05-07T20:17:28.939Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742013)
;

-- Tab: Scannable Code Format -> Scannable Code Format Part
-- Table: C_ScannableCode_Format_Part
-- Tab: Scannable Code Format(541880,D) -> Scannable Code Format Part
-- Table: C_ScannableCode_Format_Part
-- 2025-05-07T20:18:09.495Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,589948,583611,0,547958,542476,541880,'Y',TO_TIMESTAMP('2025-05-07 23:18:09','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_ScannableCode_Format_Part','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Scannable Code Format Part',589937,'N',20,1,TO_TIMESTAMP('2025-05-07 23:18:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:09.499Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547958 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-05-07T20:18:09.503Z
/* DDL */  select update_tab_translation_from_ad_element(583611) 
;

-- 2025-05-07T20:18:09.508Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547958)
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> Mandant
-- Column: C_ScannableCode_Format_Part.AD_Client_ID
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Mandant
-- Column: C_ScannableCode_Format_Part.AD_Client_ID
-- 2025-05-07T20:18:12.329Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589940,742014,0,547958,TO_TIMESTAMP('2025-05-07 23:18:12','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-05-07 23:18:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:12.333Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742014 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:18:12.336Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2025-05-07T20:18:12.417Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742014
;

-- 2025-05-07T20:18:12.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742014)
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> Organisation
-- Column: C_ScannableCode_Format_Part.AD_Org_ID
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Organisation
-- Column: C_ScannableCode_Format_Part.AD_Org_ID
-- 2025-05-07T20:18:12.600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589941,742015,0,547958,TO_TIMESTAMP('2025-05-07 23:18:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2025-05-07 23:18:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:12.602Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742015 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:18:12.605Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2025-05-07T20:18:12.656Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742015
;

-- 2025-05-07T20:18:12.659Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742015)
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> Aktiv
-- Column: C_ScannableCode_Format_Part.IsActive
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Aktiv
-- Column: C_ScannableCode_Format_Part.IsActive
-- 2025-05-07T20:18:12.802Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589944,742016,0,547958,TO_TIMESTAMP('2025-05-07 23:18:12','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-05-07 23:18:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:12.805Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742016 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:18:12.807Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2025-05-07T20:18:12.872Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742016
;

-- 2025-05-07T20:18:12.874Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742016)
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> Scannable Code Format Part
-- Column: C_ScannableCode_Format_Part.C_ScannableCode_Format_Part_ID
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Scannable Code Format Part
-- Column: C_ScannableCode_Format_Part.C_ScannableCode_Format_Part_ID
-- 2025-05-07T20:18:13.061Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589947,742017,0,547958,TO_TIMESTAMP('2025-05-07 23:18:12','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Scannable Code Format Part',TO_TIMESTAMP('2025-05-07 23:18:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:13.063Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742017 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:18:13.065Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583611) 
;

-- 2025-05-07T20:18:13.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742017
;

-- 2025-05-07T20:18:13.070Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742017)
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> Scannable Code Format
-- Column: C_ScannableCode_Format_Part.C_ScannableCode_Format_ID
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Scannable Code Format
-- Column: C_ScannableCode_Format_Part.C_ScannableCode_Format_ID
-- 2025-05-07T20:18:13.259Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589948,742018,0,547958,TO_TIMESTAMP('2025-05-07 23:18:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Scannable Code Format',TO_TIMESTAMP('2025-05-07 23:18:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:13.262Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742018 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:18:13.264Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583610) 
;

-- 2025-05-07T20:18:13.267Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742018
;

-- 2025-05-07T20:18:13.269Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742018)
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> Start No
-- Column: C_ScannableCode_Format_Part.StartNo
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Start No
-- Column: C_ScannableCode_Format_Part.StartNo
-- 2025-05-07T20:18:13.457Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589949,742019,0,547958,TO_TIMESTAMP('2025-05-07 23:18:13','YYYY-MM-DD HH24:MI:SS'),100,'Starting number/position',10,'D','The Start Number indicates the starting position in the line or field number in the line','Y','N','N','N','N','N','N','N','Start No',TO_TIMESTAMP('2025-05-07 23:18:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:13.459Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742019 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:18:13.461Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576) 
;

-- 2025-05-07T20:18:13.469Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742019
;

-- 2025-05-07T20:18:13.470Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742019)
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> End-Nr.
-- Column: C_ScannableCode_Format_Part.EndNo
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> End-Nr.
-- Column: C_ScannableCode_Format_Part.EndNo
-- 2025-05-07T20:18:13.658Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589950,742020,0,547958,TO_TIMESTAMP('2025-05-07 23:18:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','End-Nr.',TO_TIMESTAMP('2025-05-07 23:18:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:13.684Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:18:13.688Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1318) 
;

-- 2025-05-07T20:18:13.691Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742020
;

-- 2025-05-07T20:18:13.693Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742020)
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> Daten-Typ
-- Column: C_ScannableCode_Format_Part.DataType
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Daten-Typ
-- Column: C_ScannableCode_Format_Part.DataType
-- 2025-05-07T20:18:13.831Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589951,742021,0,547958,TO_TIMESTAMP('2025-05-07 23:18:13','YYYY-MM-DD HH24:MI:SS'),100,'Art der Daten',40,'D','Y','N','N','N','N','N','N','N','Daten-Typ',TO_TIMESTAMP('2025-05-07 23:18:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:13.833Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:18:13.836Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1315) 
;

-- 2025-05-07T20:18:13.841Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742021
;

-- 2025-05-07T20:18:13.842Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742021)
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> Data Format
-- Column: C_ScannableCode_Format_Part.DataFormat
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Data Format
-- Column: C_ScannableCode_Format_Part.DataFormat
-- 2025-05-07T20:18:14.028Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589952,742022,0,547958,TO_TIMESTAMP('2025-05-07 23:18:13','YYYY-MM-DD HH24:MI:SS'),100,'Format String in Java Notation, e.g. ddMMyy',20,'D','The Date Format indicates how dates are defined on the record to be imported.  It must be in Java Notation','Y','N','N','N','N','N','N','N','Data Format',TO_TIMESTAMP('2025-05-07 23:18:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:14.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:18:14.032Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1314) 
;

-- 2025-05-07T20:18:14.036Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742022
;

-- 2025-05-07T20:18:14.037Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742022)
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> Beschreibung
-- Column: C_ScannableCode_Format_Part.Description
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Beschreibung
-- Column: C_ScannableCode_Format_Part.Description
-- 2025-05-07T20:18:14.230Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589953,742023,0,547958,TO_TIMESTAMP('2025-05-07 23:18:14','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2025-05-07 23:18:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:18:14.233Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=742023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T20:18:14.236Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2025-05-07T20:18:14.277Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742023
;

-- 2025-05-07T20:18:14.278Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742023)
;

-- Tab: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D)
-- UI Section: main
-- 2025-05-07T20:18:22.512Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547957,546542,TO_TIMESTAMP('2025-05-07 23:18:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2025-05-07 23:18:22','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2025-05-07T20:18:22.517Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546542 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> main
-- UI Column: 10
-- 2025-05-07T20:18:22.787Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547988,546542,TO_TIMESTAMP('2025-05-07 23:18:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2025-05-07 23:18:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> main
-- UI Column: 20
-- 2025-05-07T20:18:22.924Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547989,546542,TO_TIMESTAMP('2025-05-07 23:18:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2025-05-07 23:18:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> main -> 10
-- UI Element Group: default
-- 2025-05-07T20:18:23.221Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547988,552748,TO_TIMESTAMP('2025-05-07 23:18:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2025-05-07 23:18:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D)
-- UI Section: main
-- 2025-05-07T20:18:23.408Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547958,546543,TO_TIMESTAMP('2025-05-07 23:18:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2025-05-07 23:18:23','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2025-05-07T20:18:23.410Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546543 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main
-- UI Column: 10
-- 2025-05-07T20:18:23.595Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547990,546543,TO_TIMESTAMP('2025-05-07 23:18:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2025-05-07 23:18:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10
-- UI Element Group: default
-- 2025-05-07T20:18:23.731Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547990,552749,TO_TIMESTAMP('2025-05-07 23:18:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2025-05-07 23:18:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format.Name
-- Column: C_ScannableCode_Format.Name
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> main -> 10 -> default.Name
-- Column: C_ScannableCode_Format.Name
-- 2025-05-07T20:19:06.536Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742012,0,547957,552748,631421,'F',TO_TIMESTAMP('2025-05-07 23:19:05','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2025-05-07 23:19:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format.Beschreibung
-- Column: C_ScannableCode_Format.Description
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> main -> 10 -> default.Beschreibung
-- Column: C_ScannableCode_Format.Description
-- 2025-05-07T20:19:17.788Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742013,0,547957,552748,631422,'F',TO_TIMESTAMP('2025-05-07 23:19:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2025-05-07 23:19:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> main -> 20
-- UI Element Group: flags
-- 2025-05-07T20:19:33.440Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547989,552750,TO_TIMESTAMP('2025-05-07 23:19:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2025-05-07 23:19:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format.Aktiv
-- Column: C_ScannableCode_Format.IsActive
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> main -> 20 -> flags.Aktiv
-- Column: C_ScannableCode_Format.IsActive
-- 2025-05-07T20:19:46.377Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742010,0,547957,552750,631423,'F',TO_TIMESTAMP('2025-05-07 23:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2025-05-07 23:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-05-07T20:19:56.168Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547989,552751,TO_TIMESTAMP('2025-05-07 23:19:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','org&client',20,TO_TIMESTAMP('2025-05-07 23:19:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format.Organisation
-- Column: C_ScannableCode_Format.AD_Org_ID
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> main -> 20 -> org&client.Organisation
-- Column: C_ScannableCode_Format.AD_Org_ID
-- 2025-05-07T20:20:13.421Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742009,0,547957,552751,631424,'F',TO_TIMESTAMP('2025-05-07 23:20:13','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2025-05-07 23:20:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format.Mandant
-- Column: C_ScannableCode_Format.AD_Client_ID
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> main -> 20 -> org&client.Mandant
-- Column: C_ScannableCode_Format.AD_Client_ID
-- 2025-05-07T20:20:20.220Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742008,0,547957,552751,631425,'F',TO_TIMESTAMP('2025-05-07 23:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2025-05-07 23:20:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.Start No
-- Column: C_ScannableCode_Format_Part.StartNo
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.Start No
-- Column: C_ScannableCode_Format_Part.StartNo
-- 2025-05-07T20:21:51.402Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742019,0,547958,552749,631426,'F',TO_TIMESTAMP('2025-05-07 23:21:51','YYYY-MM-DD HH24:MI:SS'),100,'Starting number/position','The Start Number indicates the starting position in the line or field number in the line','Y','N','Y','N','N','Start No',10,0,0,TO_TIMESTAMP('2025-05-07 23:21:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.End-Nr.
-- Column: C_ScannableCode_Format_Part.EndNo
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.End-Nr.
-- Column: C_ScannableCode_Format_Part.EndNo
-- 2025-05-07T20:22:03.340Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742020,0,547958,552749,631427,'F',TO_TIMESTAMP('2025-05-07 23:22:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','End-Nr.',20,0,0,TO_TIMESTAMP('2025-05-07 23:22:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.Daten-Typ
-- Column: C_ScannableCode_Format_Part.DataType
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.Daten-Typ
-- Column: C_ScannableCode_Format_Part.DataType
-- 2025-05-07T20:22:12.961Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742021,0,547958,552749,631428,'F',TO_TIMESTAMP('2025-05-07 23:22:12','YYYY-MM-DD HH24:MI:SS'),100,'Art der Daten','Y','N','Y','N','N','Daten-Typ',30,0,0,TO_TIMESTAMP('2025-05-07 23:22:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.Data Format
-- Column: C_ScannableCode_Format_Part.DataFormat
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.Data Format
-- Column: C_ScannableCode_Format_Part.DataFormat
-- 2025-05-07T20:22:21.994Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742022,0,547958,552749,631429,'F',TO_TIMESTAMP('2025-05-07 23:22:21','YYYY-MM-DD HH24:MI:SS'),100,'Format String in Java Notation, e.g. ddMMyy','The Date Format indicates how dates are defined on the record to be imported.  It must be in Java Notation','Y','N','Y','N','N','Data Format',40,0,0,TO_TIMESTAMP('2025-05-07 23:22:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.Beschreibung
-- Column: C_ScannableCode_Format_Part.Description
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.Beschreibung
-- Column: C_ScannableCode_Format_Part.Description
-- 2025-05-07T20:22:31.185Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742023,0,547958,552749,631430,'F',TO_TIMESTAMP('2025-05-07 23:22:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',50,0,0,TO_TIMESTAMP('2025-05-07 23:22:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main
-- UI Column: 20
-- 2025-05-07T20:22:39.771Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547991,546543,TO_TIMESTAMP('2025-05-07 23:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2025-05-07 23:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 20
-- UI Element Group: flags
-- 2025-05-07T20:22:46.432Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547991,552752,TO_TIMESTAMP('2025-05-07 23:22:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2025-05-07 23:22:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.Aktiv
-- Column: C_ScannableCode_Format_Part.IsActive
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 20 -> flags.Aktiv
-- Column: C_ScannableCode_Format_Part.IsActive
-- 2025-05-07T20:22:57.144Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742016,0,547958,552752,631431,'F',TO_TIMESTAMP('2025-05-07 23:22:55','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2025-05-07 23:22:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-05-07T20:23:04.349Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547991,552753,TO_TIMESTAMP('2025-05-07 23:23:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','org&client',20,TO_TIMESTAMP('2025-05-07 23:23:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-05-07T20:23:23.001Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=552753
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.Start No
-- Column: C_ScannableCode_Format_Part.StartNo
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.Start No
-- Column: C_ScannableCode_Format_Part.StartNo
-- 2025-05-07T20:23:38.962Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-05-07 23:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=631426
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.End-Nr.
-- Column: C_ScannableCode_Format_Part.EndNo
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.End-Nr.
-- Column: C_ScannableCode_Format_Part.EndNo
-- 2025-05-07T20:23:38.975Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-05-07 23:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=631427
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.Daten-Typ
-- Column: C_ScannableCode_Format_Part.DataType
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.Daten-Typ
-- Column: C_ScannableCode_Format_Part.DataType
-- 2025-05-07T20:23:38.987Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-05-07 23:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=631428
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.Data Format
-- Column: C_ScannableCode_Format_Part.DataFormat
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.Data Format
-- Column: C_ScannableCode_Format_Part.DataFormat
-- 2025-05-07T20:23:38.999Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-05-07 23:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=631429
;

-- UI Element: Scannable Code Format -> Scannable Code Format Part.Aktiv
-- Column: C_ScannableCode_Format_Part.IsActive
-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 20 -> flags.Aktiv
-- Column: C_ScannableCode_Format_Part.IsActive
-- 2025-05-07T20:23:39.014Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-05-07 23:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=631431
;

-- Field: Scannable Code Format -> Scannable Code Format Part -> Start No
-- Column: C_ScannableCode_Format_Part.StartNo
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Start No
-- Column: C_ScannableCode_Format_Part.StartNo
-- 2025-05-07T20:23:53.894Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2025-05-07 23:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=742019
;

-- Field: Scannable Code Format -> Scannable Code Format -> Name
-- Column: C_ScannableCode_Format.Name
-- Field: Scannable Code Format(541880,D) -> Scannable Code Format(547957,D) -> Name
-- Column: C_ScannableCode_Format.Name
-- 2025-05-07T20:24:24.597Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2025-05-07 23:24:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=742012
;

-- Window: Scannable Code Format, InternalName=scannableCodeFormat
-- Window: Scannable Code Format, InternalName=scannableCodeFormat
-- 2025-05-07T20:26:24.505Z
UPDATE AD_Window SET InternalName='scannableCodeFormat',Updated=TO_TIMESTAMP('2025-05-07 23:26:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541880
;

-- Name: Scannable Code Format
-- Action Type: W
-- Window: Scannable Code Format(541880,D)
-- 2025-05-07T20:26:33.243Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583610,542221,0,541880,TO_TIMESTAMP('2025-05-07 23:26:32','YYYY-MM-DD HH24:MI:SS'),100,'D','scannableCodeFormat','Y','N','N','N','N','Scannable Code Format',TO_TIMESTAMP('2025-05-07 23:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-07T20:26:33.247Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542221 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-05-07T20:26:33.252Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542221, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542221)
;

-- 2025-05-07T20:26:33.286Z
/* DDL */  select update_menu_translation_from_ad_element(583610) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2025-05-07T20:26:33.917Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2025-05-07T20:26:33.930Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2025-05-07T20:26:33.931Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2025-05-07T20:26:33.933Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2025-05-07T20:26:33.934Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2025-05-07T20:26:33.935Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2025-05-07T20:26:33.937Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2025-05-07T20:26:33.938Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2025-05-07T20:26:33.940Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2025-05-07T20:26:33.942Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2025-05-07T20:26:33.944Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2025-05-07T20:26:33.946Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Trace (M_HU_Trace)`
-- 2025-05-07T20:26:33.948Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2025-05-07T20:26:33.949Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2025-05-07T20:26:33.951Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2025-05-07T20:26:33.952Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2025-05-07T20:26:33.954Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2025-05-07T20:26:33.955Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2025-05-07T20:26:33.958Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2025-05-07T20:26:33.959Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2025-05-07T20:26:33.961Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2025-05-07T20:26:33.962Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2025-05-07T20:26:33.963Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-05-07T20:26:33.964Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI HU Manager (MobileUI_HUManager)`
-- 2025-05-07T20:26:33.966Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542163 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-05-07T20:26:33.967Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-05-07T20:26:33.968Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2025-05-07T20:26:33.970Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2025-05-07T20:26:33.971Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2025-05-07T20:26:33.972Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Generate new HU QR Codes (de.metas.handlingunits.process.GenerateHUQRCodes)`
-- 2025-05-07T20:26:33.974Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542152 AND AD_Tree_ID=10
;

-- Node name: `Distribution Candidate (DD_Order_Candidate)`
-- 2025-05-07T20:26:33.975Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542164 AND AD_Tree_ID=10
;

-- Node name: `Scannable Code Format`
-- 2025-05-07T20:26:33.976Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542221 AND AD_Tree_ID=10
;

