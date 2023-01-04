-- Window: Table Process, InternalName=null
-- 2022-08-29T10:25:48.382Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,54444,0,541608,TO_TIMESTAMP('2022-08-29 13:25:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Table Process','N',TO_TIMESTAMP('2022-08-29 13:25:48','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-08-29T10:25:48.387Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541608 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-08-29T10:25:48.395Z
/* DDL */  select update_window_translation_from_ad_element(54444) 
;

-- 2022-08-29T10:25:48.398Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541608
;

-- 2022-08-29T10:25:48.401Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541608)
;

-- Tab: Table Process(541608,D) -> Table Process
-- Table: AD_Table_Process
-- 2022-08-29T10:27:57.337Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,
                    IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,54444,0,546608,53304,541608,'Y',TO_TIMESTAMP('2022-08-29 13:27:57','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','AD_Table_Process','Y','N','Y','Y','N','N','N','Y',
        'Y','N','N','N','Y','Y','N','N','N',0,'Table Process','N',10,0,TO_TIMESTAMP('2022-08-29 13:27:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:27:57.338Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546608 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-08-29T10:27:57.342Z
/* DDL */  select update_tab_translation_from_ad_element(54444) 
;

-- 2022-08-29T10:27:57.347Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546608)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Mandant
-- Column: AD_Table_Process.AD_Client_ID
-- 2022-08-29T10:28:07.130Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,60334,706241,0,546608,TO_TIMESTAMP('2022-08-29 13:28:07','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-08-29 13:28:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:07.131Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706241 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:07.133Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-08-29T10:28:07.422Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706241
;

-- 2022-08-29T10:28:07.422Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706241)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Organisation
-- Column: AD_Table_Process.AD_Org_ID
-- 2022-08-29T10:28:07.528Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,60335,706242,0,546608,TO_TIMESTAMP('2022-08-29 13:28:07','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-08-29 13:28:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:07.529Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706242 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:07.530Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-08-29T10:28:07.750Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706242
;

-- 2022-08-29T10:28:07.751Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706242)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Aktiv
-- Column: AD_Table_Process.IsActive
-- 2022-08-29T10:28:07.849Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,60340,706243,0,546608,TO_TIMESTAMP('2022-08-29 13:28:07','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-08-29 13:28:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:07.850Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706243 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:07.851Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-08-29T10:28:08.140Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706243
;

-- 2022-08-29T10:28:08.140Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706243)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> DB-Tabelle
-- Column: AD_Table_Process.AD_Table_ID
-- 2022-08-29T10:28:08.248Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,60345,706244,0,546608,TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',10,'D','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:08.249Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706244 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:08.250Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2022-08-29T10:28:08.264Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706244
;

-- 2022-08-29T10:28:08.264Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706244)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Prozess
-- Column: AD_Table_Process.AD_Process_ID
-- 2022-08-29T10:28:08.376Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,60346,706245,0,546608,TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht',10,'D','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','N','N','N','N','N','N','Prozess',TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:08.377Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706245 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:08.378Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(117) 
;

-- 2022-08-29T10:28:08.381Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706245
;

-- 2022-08-29T10:28:08.381Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706245)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Entitäts-Art
-- Column: AD_Table_Process.EntityType
-- 2022-08-29T10:28:08.496Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,60347,706246,0,546608,TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100,'Dictionary Entity Type; Determines ownership and synchronization',512,'D','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','N','N','N','N','N','N','Entitäts-Art',TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:08.496Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706246 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:08.497Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1682) 
;

-- 2022-08-29T10:28:08.508Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706246
;

-- 2022-08-29T10:28:08.508Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706246)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Quick action
-- Column: AD_Table_Process.WEBUI_ViewQuickAction
-- 2022-08-29T10:28:08.601Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556031,706247,0,546608,TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Quick action',TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:08.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706247 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:08.602Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543267) 
;

-- 2022-08-29T10:28:08.603Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706247
;

-- 2022-08-29T10:28:08.604Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706247)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Default quick action
-- Column: AD_Table_Process.WEBUI_ViewQuickAction_Default
-- 2022-08-29T10:28:08.699Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556032,706248,0,546608,TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Default quick action',TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:08.700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706248 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:08.701Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543268) 
;

-- 2022-08-29T10:28:08.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706248
;

-- 2022-08-29T10:28:08.702Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706248)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Fenster
-- Column: AD_Table_Process.AD_Window_ID
-- 2022-08-29T10:28:08.786Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557003,706249,0,546608,TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100,'Eingabe- oder Anzeige-Fenster',10,'D','Das Feld "Fenster" identifiziert ein bestimmtes Fenster im system.','Y','N','N','N','N','N','N','N','Fenster',TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:08.787Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706249 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:08.789Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(143) 
;

-- 2022-08-29T10:28:08.794Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706249
;

-- 2022-08-29T10:28:08.794Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706249)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Register
-- Column: AD_Table_Process.AD_Tab_ID
-- 2022-08-29T10:28:08.894Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563908,706250,0,546608,TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100,'Register auf einem Fenster',10,'D','"Register" definiert ein Register zur Anzeige auf einem Fenster.','Y','N','N','N','N','N','N','N','Register',TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:08.895Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706250 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:08.896Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(125) 
;

-- 2022-08-29T10:28:08.898Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706250
;

-- 2022-08-29T10:28:08.898Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706250)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Is Single Document Action
-- Column: AD_Table_Process.WEBUI_DocumentAction
-- 2022-08-29T10:28:08.995Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563909,706251,0,546608,TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Is Single Document Action',TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:08.996Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706251 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:08.997Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576032) 
;

-- 2022-08-29T10:28:08.998Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706251
;

-- 2022-08-29T10:28:08.998Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706251)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Is View Action
-- Column: AD_Table_Process.WEBUI_ViewAction
-- 2022-08-29T10:28:09.104Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563910,706252,0,546608,TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Is View Action',TO_TIMESTAMP('2022-08-29 13:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:09.105Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706252 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:09.106Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576033) 
;

-- 2022-08-29T10:28:09.107Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706252
;

-- 2022-08-29T10:28:09.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706252)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Is Included Tab Top Action
-- Column: AD_Table_Process.WEBUI_IncludedTabTopAction
-- 2022-08-29T10:28:09.202Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563911,706253,0,546608,TO_TIMESTAMP('2022-08-29 13:28:09','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Is Included Tab Top Action',TO_TIMESTAMP('2022-08-29 13:28:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:09.203Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706253 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:09.204Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576034) 
;

-- 2022-08-29T10:28:09.204Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706253
;

-- 2022-08-29T10:28:09.205Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706253)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Table Process
-- Column: AD_Table_Process.AD_Table_Process_ID
-- 2022-08-29T10:28:09.310Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563987,706254,0,546608,TO_TIMESTAMP('2022-08-29 13:28:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Table Process',TO_TIMESTAMP('2022-08-29 13:28:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:09.311Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706254 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:09.312Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(54444) 
;

-- 2022-08-29T10:28:09.312Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706254
;

-- 2022-08-29T10:28:09.312Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706254)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Shortcut
-- Column: AD_Table_Process.WEBUI_Shortcut
-- 2022-08-29T10:28:09.418Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564215,706255,0,546608,TO_TIMESTAMP('2022-08-29 13:28:09','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','N','N','N','N','N','N','N','Shortcut',TO_TIMESTAMP('2022-08-29 13:28:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:28:09.418Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706255 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T10:28:09.419Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576145) 
;

-- 2022-08-29T10:28:09.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=706255
;

-- 2022-08-29T10:28:09.421Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(706255)
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Prozess
-- Column: AD_Table_Process.AD_Process_ID
-- 2022-08-29T10:29:26.591Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706245
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> DB-Tabelle
-- Column: AD_Table_Process.AD_Table_ID
-- 2022-08-29T10:29:26.593Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706244
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Fenster
-- Column: AD_Table_Process.AD_Window_ID
-- 2022-08-29T10:29:26.595Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706249
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Register
-- Column: AD_Table_Process.AD_Tab_ID
-- 2022-08-29T10:29:26.596Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706250
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Entitäts-Art
-- Column: AD_Table_Process.EntityType
-- 2022-08-29T10:29:26.598Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706246
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Aktiv
-- Column: AD_Table_Process.IsActive
-- 2022-08-29T10:29:26.599Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706243
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Is Single Document Action
-- Column: AD_Table_Process.WEBUI_DocumentAction
-- 2022-08-29T10:29:26.600Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706251
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Is Included Tab Top Action
-- Column: AD_Table_Process.WEBUI_IncludedTabTopAction
-- 2022-08-29T10:29:26.602Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706253
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Is View Action
-- Column: AD_Table_Process.WEBUI_ViewAction
-- 2022-08-29T10:29:26.603Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706252
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Quick action
-- Column: AD_Table_Process.WEBUI_ViewQuickAction
-- 2022-08-29T10:29:26.604Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706247
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Default quick action
-- Column: AD_Table_Process.WEBUI_ViewQuickAction_Default
-- 2022-08-29T10:29:26.606Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2022-08-29 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706248
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Shortcut
-- Column: AD_Table_Process.WEBUI_Shortcut
-- 2022-08-29T10:29:30.558Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2022-08-29 13:29:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706255
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Register
-- Column: AD_Table_Process.AD_Tab_ID
-- 2022-08-29T10:31:57.578Z
UPDATE AD_Field SET DisplayLogic='@AD_Window_ID/0@ > 0',Updated=TO_TIMESTAMP('2022-08-29 13:31:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706250
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Fenster
-- Column: AD_Table_Process.AD_Window_ID
-- 2022-08-29T10:32:08.164Z
UPDATE AD_Field SET DisplayLogic='@AD_Table_ID/0@>0',Updated=TO_TIMESTAMP('2022-08-29 13:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706249
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Mandant
-- Column: AD_Table_Process.AD_Client_ID
-- 2022-08-29T10:32:44.172Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706241
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Organisation
-- Column: AD_Table_Process.AD_Org_ID
-- 2022-08-29T10:32:44.174Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706242
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Aktiv
-- Column: AD_Table_Process.IsActive
-- 2022-08-29T10:32:44.176Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706243
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> DB-Tabelle
-- Column: AD_Table_Process.AD_Table_ID
-- 2022-08-29T10:32:44.177Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706244
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Prozess
-- Column: AD_Table_Process.AD_Process_ID
-- 2022-08-29T10:32:44.178Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706245
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Entitäts-Art
-- Column: AD_Table_Process.EntityType
-- 2022-08-29T10:32:44.179Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706246
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Quick action
-- Column: AD_Table_Process.WEBUI_ViewQuickAction
-- 2022-08-29T10:32:44.180Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706247
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Default quick action
-- Column: AD_Table_Process.WEBUI_ViewQuickAction_Default
-- 2022-08-29T10:32:44.181Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706248
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Fenster
-- Column: AD_Table_Process.AD_Window_ID
-- 2022-08-29T10:32:44.182Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706249
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Register
-- Column: AD_Table_Process.AD_Tab_ID
-- 2022-08-29T10:32:44.183Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706250
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Is Single Document Action
-- Column: AD_Table_Process.WEBUI_DocumentAction
-- 2022-08-29T10:32:44.184Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706251
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Is View Action
-- Column: AD_Table_Process.WEBUI_ViewAction
-- 2022-08-29T10:32:44.186Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706252
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Is Included Tab Top Action
-- Column: AD_Table_Process.WEBUI_IncludedTabTopAction
-- 2022-08-29T10:32:44.187Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706253
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Table Process
-- Column: AD_Table_Process.AD_Table_Process_ID
-- 2022-08-29T10:32:44.188Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706254
;

-- Field: Table Process(541608,D) -> Table Process(546608,D) -> Shortcut
-- Column: AD_Table_Process.WEBUI_Shortcut
-- 2022-08-29T10:32:44.189Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-29 13:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706255
;

-- Tab: Table Process(541608,D) -> Table Process(546608,D)
-- UI Section: main
-- 2022-08-29T10:32:59.985Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546608,545235,TO_TIMESTAMP('2022-08-29 13:32:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-29 13:32:59','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-08-29T10:32:59.986Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545235 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Table Process(541608,D) -> Table Process(546608,D) -> main
-- UI Column: 10
-- 2022-08-29T10:33:00.087Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546365,545235,TO_TIMESTAMP('2022-08-29 13:32:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-29 13:32:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Table Process(541608,D) -> Table Process(546608,D) -> main
-- UI Column: 20
-- 2022-08-29T10:33:00.194Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546366,545235,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10
-- UI Element Group: default
-- 2022-08-29T10:33:00.303Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546365,549879,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Prozess
-- Column: AD_Table_Process.AD_Process_ID
-- 2022-08-29T10:33:00.401Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706245,0,546608,612552,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','Y','N','Y','Prozess',10,0,10,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.DB-Tabelle
-- Column: AD_Table_Process.AD_Table_ID
-- 2022-08-29T10:33:00.492Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706244,0,546608,612553,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','Y','N','Y','DB-Tabelle',20,0,20,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Fenster
-- Column: AD_Table_Process.AD_Window_ID
-- 2022-08-29T10:33:00.595Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706249,0,546608,612554,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Eingabe- oder Anzeige-Fenster','Das Feld "Fenster" identifiziert ein bestimmtes Fenster im system.','Y','N','Y','N','Y','Fenster',30,0,30,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Register
-- Column: AD_Table_Process.AD_Tab_ID
-- 2022-08-29T10:33:00.686Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706250,0,546608,612555,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Register auf einem Fenster','"Register" definiert ein Register zur Anzeige auf einem Fenster.','Y','N','Y','N','Y','Register',40,0,40,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Entitäts-Art
-- Column: AD_Table_Process.EntityType
-- 2022-08-29T10:33:00.780Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706246,0,546608,612556,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Dictionary Entity Type; Determines ownership and synchronization','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','Y','N','Y','Entitäts-Art',50,0,50,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Aktiv
-- Column: AD_Table_Process.IsActive
-- 2022-08-29T10:33:00.871Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706243,0,546608,612557,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','Y','Aktiv',60,0,60,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Is Single Document Action
-- Column: AD_Table_Process.WEBUI_DocumentAction
-- 2022-08-29T10:33:00.972Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706251,0,546608,612558,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Is Single Document Action',70,0,70,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Is Included Tab Top Action
-- Column: AD_Table_Process.WEBUI_IncludedTabTopAction
-- 2022-08-29T10:33:01.074Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706253,0,546608,612559,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Is Included Tab Top Action',80,0,80,TO_TIMESTAMP('2022-08-29 13:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Is View Action
-- Column: AD_Table_Process.WEBUI_ViewAction
-- 2022-08-29T10:33:01.181Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706252,0,546608,612560,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Is View Action',90,0,90,TO_TIMESTAMP('2022-08-29 13:33:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Quick action
-- Column: AD_Table_Process.WEBUI_ViewQuickAction
-- 2022-08-29T10:33:01.271Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706247,0,546608,612561,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Quick action',100,0,100,TO_TIMESTAMP('2022-08-29 13:33:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Default quick action
-- Column: AD_Table_Process.WEBUI_ViewQuickAction_Default
-- 2022-08-29T10:33:01.366Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706248,0,546608,612562,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Default quick action',110,0,110,TO_TIMESTAMP('2022-08-29 13:33:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 10 -> default.Shortcut
-- Column: AD_Table_Process.WEBUI_Shortcut
-- 2022-08-29T10:33:01.463Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,706255,0,546608,612563,549879,'F',TO_TIMESTAMP('2022-08-29 13:33:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Shortcut',120,0,120,TO_TIMESTAMP('2022-08-29 13:33:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Table Process(541608,D) -> Table Process(546608,D) -> main -> 20
-- UI Element Group: flags
-- 2022-08-29T10:33:23.487Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546366,549880,TO_TIMESTAMP('2022-08-29 13:33:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-08-29 13:33:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 20 -> flags.Is Single Document Action
-- Column: AD_Table_Process.WEBUI_DocumentAction
-- 2022-08-29T10:33:39.469Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549880, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-08-29 13:33:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612558
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 20 -> flags.Is Included Tab Top Action
-- Column: AD_Table_Process.WEBUI_IncludedTabTopAction
-- 2022-08-29T10:33:47.303Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549880, IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-08-29 13:33:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612559
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 20 -> flags.Is View Action
-- Column: AD_Table_Process.WEBUI_ViewAction
-- 2022-08-29T10:33:54.024Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549880, IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-08-29 13:33:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612560
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 20 -> flags.Quick action
-- Column: AD_Table_Process.WEBUI_ViewQuickAction
-- 2022-08-29T10:34:03.583Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549880, IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2022-08-29 13:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612561
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 20 -> flags.Default quick action
-- Column: AD_Table_Process.WEBUI_ViewQuickAction_Default
-- 2022-08-29T10:34:14.565Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549880, IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2022-08-29 13:34:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612562
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 20 -> flags.Shortcut
-- Column: AD_Table_Process.WEBUI_Shortcut
-- 2022-08-29T10:34:20.317Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549880, IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2022-08-29 13:34:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612563
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Table_Process.IsActive
-- 2022-08-29T10:34:41.793Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549880, IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2022-08-29 13:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612557
;

-- UI Element: Table Process(541608,D) -> Table Process(546608,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Table_Process.IsActive
-- 2022-08-29T10:34:49.526Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2022-08-29 13:34:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612557
;

-- Element: null
-- 2022-08-29T10:35:48.853Z
UPDATE AD_Element_Trl SET Name='Related processes', PrintName='Related processes',Updated=TO_TIMESTAMP('2022-08-29 13:35:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=572631 AND AD_Language='de_CH'
;

-- 2022-08-29T10:35:48.860Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572631,'de_CH') 
;

-- Element: null
-- 2022-08-29T10:35:54.195Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Related processes', PrintName='Related processes',Updated=TO_TIMESTAMP('2022-08-29 13:35:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=572631 AND AD_Language='en_US'
;

-- 2022-08-29T10:35:54.196Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572631,'en_US') 
;

-- Element: null
-- 2022-08-29T10:36:01.456Z
UPDATE AD_Element_Trl SET Name='Related processes', PrintName='Related processes',Updated=TO_TIMESTAMP('2022-08-29 13:36:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=572631 AND AD_Language='de_DE'
;

-- 2022-08-29T10:36:01.456Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572631,'de_DE') 
;

-- 2022-08-29T10:36:01.463Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(572631,'de_DE') 
;

-- 2022-08-29T10:36:01.466Z
UPDATE AD_Column SET ColumnName=NULL, Name='Related processes', Description=NULL, Help=NULL WHERE AD_Element_ID=572631
;

-- 2022-08-29T10:36:01.466Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Related processes', Description=NULL, Help=NULL WHERE AD_Element_ID=572631 AND IsCentrallyMaintained='Y'
;

-- 2022-08-29T10:36:01.466Z
UPDATE AD_Field SET Name='Related processes', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=572631) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 572631)
;

-- 2022-08-29T10:36:01.489Z
UPDATE AD_PrintFormatItem pi SET PrintName='Related processes', Name='Related processes' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=572631)
;

-- 2022-08-29T10:36:01.490Z
UPDATE AD_Tab SET Name='Related processes', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 572631
;

-- 2022-08-29T10:36:01.491Z
UPDATE AD_WINDOW SET Name='Related processes', Description=NULL, Help=NULL WHERE AD_Element_ID = 572631
;

-- 2022-08-29T10:36:01.491Z
UPDATE AD_Menu SET   Name = 'Related processes', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 572631
;

-- Window: Related processes, InternalName=null
-- 2022-08-29T10:36:19.049Z
UPDATE AD_Window SET AD_Element_ID=572631, Description=NULL, Help=NULL, Name='Related processes',Updated=TO_TIMESTAMP('2022-08-29 13:36:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541608
;

-- 2022-08-29T10:36:19.063Z
/* DDL */  select update_window_translation_from_ad_element(572631) 
;

-- 2022-08-29T10:36:19.065Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541608
;

-- 2022-08-29T10:36:19.065Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541608)
;

-- Column: AD_Table_Process.AD_Process_ID
-- 2022-08-29T10:41:12.728Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-08-29 13:41:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=60346
;

-- Column: AD_Table_Process.AD_Table_ID
-- 2022-08-29T10:41:22.647Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-08-29 13:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=60345
;

-- Column: AD_Table_Process.AD_Window_ID
-- 2022-08-29T10:41:32.830Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-08-29 13:41:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557003
;

-- Window: Related processes, InternalName=adTableProcess
-- 2022-08-29T10:44:47.841Z
UPDATE AD_Window SET InternalName='adTableProcess',Updated=TO_TIMESTAMP('2022-08-29 13:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541608
;

-- Name: Related processes
-- Action Type: W
-- Window: Related processes(541608,D)
-- 2022-08-29T10:44:52.145Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,572631,542000,0,541608,TO_TIMESTAMP('2022-08-29 13:44:51','YYYY-MM-DD HH24:MI:SS'),100,'D','adTableProcess','Y','N','N','N','N','Related processes',TO_TIMESTAMP('2022-08-29 13:44:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:44:52.147Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542000 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-08-29T10:44:52.149Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542000, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542000)
;

-- 2022-08-29T10:44:52.166Z
/* DDL */  select update_menu_translation_from_ad_element(572631) 
;

-- Reordering children of `Application Dictionary`
-- Node name: `Search Definition (AD_SearchDefinition)`
-- 2022-08-29T10:45:00.325Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53203 AND AD_Tree_ID=10
;

-- Node name: `Entity Type (AD_EntityType)`
-- 2022-08-29T10:45:00.327Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=586 AND AD_Tree_ID=10
;

-- Node name: `Relation Type (AD_RelationType)`
-- 2022-08-29T10:45:00.327Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53251 AND AD_Tree_ID=10
;

-- Node name: `Diagnose ZoomInfoFactory (de.metas.zoom.process.ZoomInfoFactoryExecute)`
-- 2022-08-29T10:45:00.327Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540994 AND AD_Tree_ID=10
;

-- Node name: `Element (AD_Element)`
-- 2022-08-29T10:45:00.328Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=138 AND AD_Tree_ID=10
;

-- Node name: `Application Elements (AD_Element_Trl)`
-- 2022-08-29T10:45:00.328Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541160 AND AD_Tree_ID=10
;

-- Node name: `Table and Column (AD_Table)`
-- 2022-08-29T10:45:00.328Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=139 AND AD_Tree_ID=10
;

-- Node name: `Field Group (AD_FieldGroup)`
-- 2022-08-29T10:45:00.329Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=249 AND AD_Tree_ID=10
;

-- Node name: `Window Management (AD_Window)`
-- 2022-08-29T10:45:00.329Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=141 AND AD_Tree_ID=10
;

-- Node name: `Field Context Menu (AD_Field_ContextMenu)`
-- 2022-08-29T10:45:00.329Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53455 AND AD_Tree_ID=10
;

-- Node name: `Form (AD_Form)`
-- 2022-08-29T10:45:00.330Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=216 AND AD_Tree_ID=10
;

-- Node name: `Info Window (AD_InfoWindow)`
-- 2022-08-29T10:45:00.330Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=589 AND AD_Tree_ID=10
;

-- Node name: `Reference (AD_Reference)`
-- 2022-08-29T10:45:00.331Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=140 AND AD_Tree_ID=10
;

-- Node name: `Workbench (AD_Workbench)`
-- 2022-08-29T10:45:00.331Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=300 AND AD_Tree_ID=10
;

-- Node name: `Validation Rules (AD_Val_Rule)`
-- 2022-08-29T10:45:00.331Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=142 AND AD_Tree_ID=10
;

-- Node name: `Desktop (AD_Desktop)`
-- 2022-08-29T10:45:00.332Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=295 AND AD_Tree_ID=10
;

-- Node name: `Model Validator (AD_ModelValidator)`
-- 2022-08-29T10:45:00.332Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53012 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2022-08-29T10:45:00.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=143 AND AD_Tree_ID=10
;

-- Node name: `Report View (AD_ReportView)`
-- 2022-08-29T10:45:00.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=201 AND AD_Tree_ID=10
;

-- Node name: `Report & Process (AD_Process)`
-- 2022-08-29T10:45:00.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=176 AND AD_Tree_ID=10
;

-- Node name: `Rule (AD_Rule)`
-- 2022-08-29T10:45:00.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53086 AND AD_Tree_ID=10
;

-- Node name: `Custom Attribute (AD_Attribute)`
-- 2022-08-29T10:45:00.334Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=239 AND AD_Tree_ID=10
;

-- Node name: `Window Customization (AD_UserDef_Win)`
-- 2022-08-29T10:45:00.334Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=517 AND AD_Tree_ID=10
;

-- Node name: `Reapply Customizations (org.compiere.process.ChangeLogProcess)`
-- 2022-08-29T10:45:00.334Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=499 AND AD_Tree_ID=10
;

-- Node name: `Migration (AD_Migration)`
-- 2022-08-29T10:45:00.335Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53221 AND AD_Tree_ID=10
;

-- Node name: `Import migration from XML (org.compiere.process.MigrationFromXML)`
-- 2022-08-29T10:45:00.335Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53222 AND AD_Tree_ID=10
;

-- Node name: `Migration Scripts (AD_MigrationScript)`
-- 2022-08-29T10:45:00.335Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53089 AND AD_Tree_ID=10
;

-- Node name: `Callouts Customization (AD_ColumnCallout)`
-- 2022-08-29T10:45:00.336Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53267 AND AD_Tree_ID=10
;

-- Node name: `Validate Application Dictionary (org.adempiere.appdict.validation.process.AppDictValidator)`
-- 2022-08-29T10:45:00.336Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53568 AND AD_Tree_ID=10
;

-- Node name: `Relationsart (AD_RelationType)`
-- 2022-08-29T10:45:00.336Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540037 AND AD_Tree_ID=10
;

-- Node name: `Java Classes (AD_JavaClass_Type)`
-- 2022-08-29T10:45:00.336Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540492 AND AD_Tree_ID=10
;

-- Node name: `Migrate column callout (org.adempiere.process.AD_ColumnCallout_Migrate)`
-- 2022-08-29T10:45:00.337Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53266 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WebUI) (WEBUI_Dashboard)`
-- 2022-08-29T10:45:00.337Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540735 AND AD_Tree_ID=10
;

-- Node name: `Properties Configuration (M_PropertiesConfig)`
-- 2022-08-29T10:45:00.337Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540575 AND AD_Tree_ID=10
;

-- Node name: `UI Element (AD_UI_Element)`
-- 2022-08-29T10:45:00.338Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541333 AND AD_Tree_ID=10
;

-- Node name: `Related processes`
-- 2022-08-29T10:45:00.338Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542000 AND AD_Tree_ID=10
;

