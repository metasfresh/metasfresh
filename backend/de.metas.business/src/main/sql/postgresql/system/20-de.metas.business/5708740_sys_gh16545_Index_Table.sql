-- Run mode: SWING_CLIENT

-- 2023-10-20T14:14:38.229Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582774,0,TO_TIMESTAMP('2023-10-20 17:14:38.004','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Index','Index',TO_TIMESTAMP('2023-10-20 17:14:38.004','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:14:38.256Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582774 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-10-20T14:14:47.712Z
UPDATE AD_Element_Trl SET Name='Index', PrintName='Index',Updated=TO_TIMESTAMP('2023-10-20 17:14:47.712','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582774 AND AD_Language='en_US'
;

-- 2023-10-20T14:14:47.740Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582774,'en_US')
;

-- Window: Index, InternalName=null
-- 2023-10-20T14:16:40.401Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582774,0,541746,TO_TIMESTAMP('2023-10-20 17:16:40.254','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','N','N','N','Y','Index','N',TO_TIMESTAMP('2023-10-20 17:16:40.254','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-10-20T14:16:40.407Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541746 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-10-20T14:16:40.417Z
/* DDL */  select update_window_translation_from_ad_element(582774)
;

-- 2023-10-20T14:16:40.442Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541746
;

-- 2023-10-20T14:16:40.450Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541746)
;

-- Tab: Index(541746,D) -> Index
-- Table: AD_Index_Table
-- 2023-10-20T14:18:19.789Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,540469,0,547262,540101,541746,'Y',TO_TIMESTAMP('2023-10-20 17:18:19.633','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','AD_Index_Table','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Index','N',10,0,TO_TIMESTAMP('2023-10-20 17:18:19.633','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:19.792Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547262 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-10-20T14:18:19.796Z
/* DDL */  select update_tab_translation_from_ad_element(540469)
;

-- 2023-10-20T14:18:19.801Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547262)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Mandant
-- Column: AD_Index_Table.AD_Client_ID
-- 2023-10-20T14:18:23.033Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541976,721610,0,547262,TO_TIMESTAMP('2023-10-20 17:18:22.918','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-10-20 17:18:22.918','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:23.036Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721610 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:23.040Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-10-20T14:18:23.220Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721610
;

-- 2023-10-20T14:18:23.224Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721610)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Organisation
-- Column: AD_Index_Table.AD_Org_ID
-- 2023-10-20T14:18:23.332Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541977,721611,0,547262,TO_TIMESTAMP('2023-10-20 17:18:23.241','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-10-20 17:18:23.241','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:23.334Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721611 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:23.336Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-10-20T14:18:23.449Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721611
;

-- 2023-10-20T14:18:23.449Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721611)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Index
-- Column: AD_Index_Table.AD_Index_Table_ID
-- 2023-10-20T14:18:23.545Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541978,721612,0,547262,TO_TIMESTAMP('2023-10-20 17:18:23.451','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Index',TO_TIMESTAMP('2023-10-20 17:18:23.451','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:23.547Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721612 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:23.549Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540469)
;

-- 2023-10-20T14:18:23.556Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721612
;

-- 2023-10-20T14:18:23.557Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721612)
;

-- Field: Index(541746,D) -> Index(547262,D) -> DB-Tabelle
-- Column: AD_Index_Table.AD_Table_ID
-- 2023-10-20T14:18:23.655Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541979,721613,0,547262,TO_TIMESTAMP('2023-10-20 17:18:23.56','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information',10,'D','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2023-10-20 17:18:23.56','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:23.657Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721613 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:23.659Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126)
;

-- 2023-10-20T14:18:23.682Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721613
;

-- 2023-10-20T14:18:23.683Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721613)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Beschreibung
-- Column: AD_Index_Table.Description
-- 2023-10-20T14:18:23.778Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541982,721614,0,547262,TO_TIMESTAMP('2023-10-20 17:18:23.686','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2023-10-20 17:18:23.686','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:23.780Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721614 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:23.783Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2023-10-20T14:18:23.857Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721614
;

-- 2023-10-20T14:18:23.858Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721614)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Entitäts-Art
-- Column: AD_Index_Table.EntityType
-- 2023-10-20T14:18:23.957Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541983,721615,0,547262,TO_TIMESTAMP('2023-10-20 17:18:23.859','YYYY-MM-DD HH24:MI:SS.US'),100,'Dictionary Entity Type; Determines ownership and synchronization',512,'D','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','N','N','N','N','N','N','Entitäts-Art',TO_TIMESTAMP('2023-10-20 17:18:23.859','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:23.959Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721615 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:23.961Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1682)
;

-- 2023-10-20T14:18:23.978Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721615
;

-- 2023-10-20T14:18:23.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721615)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Kommentar/Hilfe
-- Column: AD_Index_Table.Help
-- 2023-10-20T14:18:24.068Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541984,721616,0,547262,TO_TIMESTAMP('2023-10-20 17:18:23.982','YYYY-MM-DD HH24:MI:SS.US'),100,'Comment or Hint',2000,'D','The Help field contains a hint, comment or help about the use of this item.','Y','N','N','N','N','N','N','N','Kommentar/Hilfe',TO_TIMESTAMP('2023-10-20 17:18:23.982','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:24.070Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721616 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:24.072Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(326)
;

-- 2023-10-20T14:18:24.080Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721616
;

-- 2023-10-20T14:18:24.081Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721616)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Aktiv
-- Column: AD_Index_Table.IsActive
-- 2023-10-20T14:18:24.174Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541985,721617,0,547262,TO_TIMESTAMP('2023-10-20 17:18:24.084','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-10-20 17:18:24.084','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:24.175Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721617 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:24.177Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-10-20T14:18:24.292Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721617
;

-- 2023-10-20T14:18:24.292Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721617)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Unique
-- Column: AD_Index_Table.IsUnique
-- 2023-10-20T14:18:24.391Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541987,721618,0,547262,TO_TIMESTAMP('2023-10-20 17:18:24.294','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Unique',TO_TIMESTAMP('2023-10-20 17:18:24.294','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:24.393Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721618 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:24.395Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540471)
;

-- 2023-10-20T14:18:24.401Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721618
;

-- 2023-10-20T14:18:24.403Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721618)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Name
-- Column: AD_Index_Table.Name
-- 2023-10-20T14:18:24.494Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541988,721619,0,547262,TO_TIMESTAMP('2023-10-20 17:18:24.407','YYYY-MM-DD HH24:MI:SS.US'),100,'',60,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-10-20 17:18:24.407','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:24.496Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721619 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:24.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2023-10-20T14:18:24.573Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721619
;

-- 2023-10-20T14:18:24.574Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721619)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Process Now
-- Column: AD_Index_Table.Processing
-- 2023-10-20T14:18:24.680Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541989,721620,0,547262,TO_TIMESTAMP('2023-10-20 17:18:24.575','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Process Now',TO_TIMESTAMP('2023-10-20 17:18:24.575','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:24.682Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721620 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:24.684Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524)
;

-- 2023-10-20T14:18:24.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721620
;

-- 2023-10-20T14:18:24.712Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721620)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Sql WHERE
-- Column: AD_Index_Table.WhereClause
-- 2023-10-20T14:18:24.811Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542024,721621,0,547262,TO_TIMESTAMP('2023-10-20 17:18:24.715','YYYY-MM-DD HH24:MI:SS.US'),100,'Fully qualified SQL WHERE clause',2000,'D','The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','N','N','N','N','N','N','N','Sql WHERE',TO_TIMESTAMP('2023-10-20 17:18:24.715','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:24.813Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721621 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:24.815Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(630)
;

-- 2023-10-20T14:18:24.822Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721621
;

-- 2023-10-20T14:18:24.823Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721621)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Fehlermeldung
-- Column: AD_Index_Table.ErrorMsg
-- 2023-10-20T14:18:24.920Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542025,721622,0,547262,TO_TIMESTAMP('2023-10-20 17:18:24.827','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'D','Y','N','N','N','N','N','N','N','Fehlermeldung',TO_TIMESTAMP('2023-10-20 17:18:24.827','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:24.921Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721622 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:24.923Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1021)
;

-- 2023-10-20T14:18:24.932Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721622
;

-- 2023-10-20T14:18:24.933Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721622)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Before Change Code
-- Column: AD_Index_Table.BeforeChangeCode
-- 2023-10-20T14:18:25.024Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542162,721623,0,547262,TO_TIMESTAMP('2023-10-20 17:18:24.937','YYYY-MM-DD HH24:MI:SS.US'),100,'Code to be run if one of the indexed columns were changed. The code runs before the actual change.',2000,'D','Y','N','N','N','N','N','N','N','Before Change Code',TO_TIMESTAMP('2023-10-20 17:18:24.937','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:25.026Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721623 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:25.027Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540493)
;

-- 2023-10-20T14:18:25.032Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721623
;

-- 2023-10-20T14:18:25.032Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721623)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Before Change Code Type
-- Column: AD_Index_Table.BeforeChangeCodeType
-- 2023-10-20T14:18:25.111Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542163,721624,0,547262,TO_TIMESTAMP('2023-10-20 17:18:25.034','YYYY-MM-DD HH24:MI:SS.US'),100,4,'D','Y','N','N','N','N','N','N','N','Before Change Code Type',TO_TIMESTAMP('2023-10-20 17:18:25.034','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:25.112Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721624 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:25.114Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540494)
;

-- 2023-10-20T14:18:25.121Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721624
;

-- 2023-10-20T14:18:25.122Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721624)
;

-- Field: Index(541746,D) -> Index(547262,D) -> Before Change Warning
-- Column: AD_Index_Table.BeforeChangeWarning
-- 2023-10-20T14:18:25.220Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,544883,721625,0,547262,TO_TIMESTAMP('2023-10-20 17:18:25.125','YYYY-MM-DD HH24:MI:SS.US'),100,'Warning to be shown if one of the indexed columns were changed. ',2000,'D','Y','N','N','N','N','N','N','N','Before Change Warning',TO_TIMESTAMP('2023-10-20 17:18:25.125','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T14:18:25.221Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721625 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T14:18:25.224Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541261)
;

-- 2023-10-20T14:18:25.231Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721625
;

-- 2023-10-20T14:18:25.231Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721625)
;

-- Tab: Index(541746,D) -> Index(547262,D)
-- UI Section: main
-- 2023-10-20T14:26:46.539Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547262,545857,TO_TIMESTAMP('2023-10-20 17:26:46.411','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-10-20 17:26:46.411','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-10-20T14:26:46.549Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545857 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Index(541746,D) -> Index(547262,D) -> main
-- UI Column: 10
-- 2023-10-20T14:27:37.783Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547143,545857,TO_TIMESTAMP('2023-10-20 17:27:37.631','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-10-20 17:27:37.631','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Index(541746,D) -> Index(547262,D) -> main
-- UI Column: 20
-- 2023-10-20T14:27:42.682Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547144,545857,TO_TIMESTAMP('2023-10-20 17:27:42.585','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-10-20 17:27:42.585','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Index(541746,D) -> Index(547262,D) -> main -> 20
-- UI Element Group: flags
-- 2023-10-20T14:28:03.816Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547144,551263,TO_TIMESTAMP('2023-10-20 17:28:03.665','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2023-10-20 17:28:03.665','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Index_Table.IsActive
-- 2023-10-20T14:28:40.699Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721617,0,547262,621135,551263,'F',TO_TIMESTAMP('2023-10-20 17:28:40.554','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-10-20 17:28:40.554','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 20 -> flags.Unique
-- Column: AD_Index_Table.IsUnique
-- 2023-10-20T14:30:36.934Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721618,0,547262,621136,551263,'F',TO_TIMESTAMP('2023-10-20 17:30:36.803','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Unique',20,0,0,TO_TIMESTAMP('2023-10-20 17:30:36.803','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Index(541746,D) -> Index(547262,D) -> main -> 10
-- UI Element Group: main
-- 2023-10-20T14:31:44.244Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547143,551264,TO_TIMESTAMP('2023-10-20 17:31:44.128','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-10-20 17:31:44.128','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 10 -> main.Name
-- Column: AD_Index_Table.Name
-- 2023-10-20T14:32:32.246Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721619,0,547262,621137,551264,'F',TO_TIMESTAMP('2023-10-20 17:32:32.101','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2023-10-20 17:32:32.101','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 10 -> main.Beschreibung
-- Column: AD_Index_Table.Description
-- 2023-10-20T14:33:09.264Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721614,0,547262,621138,551264,'F',TO_TIMESTAMP('2023-10-20 17:33:09.123','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',20,0,0,TO_TIMESTAMP('2023-10-20 17:33:09.123','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 10 -> main.Kommentar/Hilfe
-- Column: AD_Index_Table.Help
-- 2023-10-20T14:33:41.844Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721616,0,547262,621139,551264,'F',TO_TIMESTAMP('2023-10-20 17:33:41.731','YYYY-MM-DD HH24:MI:SS.US'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','N','Y','N','N','N',0,'Kommentar/Hilfe',30,0,0,TO_TIMESTAMP('2023-10-20 17:33:41.731','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 10 -> main.Sql WHERE
-- Column: AD_Index_Table.WhereClause
-- 2023-10-20T14:34:10.874Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721621,0,547262,621140,551264,'F',TO_TIMESTAMP('2023-10-20 17:34:10.754','YYYY-MM-DD HH24:MI:SS.US'),100,'Fully qualified SQL WHERE clause','The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','N','N','Y','N','N','N',0,'Sql WHERE',40,0,0,TO_TIMESTAMP('2023-10-20 17:34:10.754','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 10 -> main.Fehlermeldung
-- Column: AD_Index_Table.ErrorMsg
-- 2023-10-20T14:34:38.150Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721622,0,547262,621141,551264,'F',TO_TIMESTAMP('2023-10-20 17:34:38.005','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Fehlermeldung',50,0,0,TO_TIMESTAMP('2023-10-20 17:34:38.005','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 10 -> main.Before Change Code Type
-- Column: AD_Index_Table.BeforeChangeCodeType
-- 2023-10-20T14:35:05.204Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721624,0,547262,621142,551264,'F',TO_TIMESTAMP('2023-10-20 17:35:05.054','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Before Change Code Type',60,0,0,TO_TIMESTAMP('2023-10-20 17:35:05.054','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 10 -> main.Before Change Code
-- Column: AD_Index_Table.BeforeChangeCode
-- 2023-10-20T14:35:33.347Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721623,0,547262,621143,551264,'F',TO_TIMESTAMP('2023-10-20 17:35:33.226','YYYY-MM-DD HH24:MI:SS.US'),100,'Code to be run if one of the indexed columns were changed. The code runs before the actual change.','Y','N','N','Y','N','N','N',0,'Before Change Code',70,0,0,TO_TIMESTAMP('2023-10-20 17:35:33.226','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 10 -> main.Before Change Warning
-- Column: AD_Index_Table.BeforeChangeWarning
-- 2023-10-20T14:36:01.408Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721625,0,547262,621144,551264,'F',TO_TIMESTAMP('2023-10-20 17:36:01.292','YYYY-MM-DD HH24:MI:SS.US'),100,'Warning to be shown if one of the indexed columns were changed. ','Y','N','N','Y','N','N','N',0,'Before Change Warning',80,0,0,TO_TIMESTAMP('2023-10-20 17:36:01.292','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index(541746,D) -> Index(547262,D) -> main -> 10 -> main.Entitäts-Art
-- Column: AD_Index_Table.EntityType
-- 2023-10-20T14:36:30.734Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721615,0,547262,621145,551264,'F',TO_TIMESTAMP('2023-10-20 17:36:30.594','YYYY-MM-DD HH24:MI:SS.US'),100,'Dictionary Entity Type; Determines ownership and synchronization','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','N','Y','N','N','N',0,'Entitäts-Art',90,0,0,TO_TIMESTAMP('2023-10-20 17:36:30.594','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Run mode: SWING_CLIENT

-- Tab: Tabelle Index(541746,D) -> Index Column
-- Table: AD_Index_Column
-- 2023-10-20T14:59:56.931Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721628
;

-- Field: Tabelle Index(541746,D) -> Index Column(547263,D) -> Mandant
-- Column: AD_Index_Column.AD_Client_ID
-- 2023-10-20T14:59:56.933Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=721628
;

-- 2023-10-20T14:59:56.940Z
DELETE FROM AD_Field WHERE AD_Field_ID=721628
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547263,D) -> main -> 10 -> main.Spalte
-- Column: AD_Index_Column.AD_Column_ID
-- 2023-10-20T14:59:56.951Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=621149
;

-- 2023-10-20T14:59:56.951Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721629
;

-- Field: Tabelle Index(541746,D) -> Index Column(547263,D) -> Spalte
-- Column: AD_Index_Column.AD_Column_ID
-- 2023-10-20T14:59:56.952Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=721629
;

-- 2023-10-20T14:59:56.955Z
DELETE FROM AD_Field WHERE AD_Field_ID=721629
;

-- 2023-10-20T14:59:56.959Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721630
;

-- Field: Tabelle Index(541746,D) -> Index Column(547263,D) -> Index Column
-- Column: AD_Index_Column.AD_Index_Column_ID
-- 2023-10-20T14:59:56.960Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=721630
;

-- 2023-10-20T14:59:56.962Z
DELETE FROM AD_Field WHERE AD_Field_ID=721630
;

-- 2023-10-20T14:59:56.966Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721631
;

-- Field: Tabelle Index(541746,D) -> Index Column(547263,D) -> Organisation
-- Column: AD_Index_Column.AD_Org_ID
-- 2023-10-20T14:59:56.968Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=721631
;

-- 2023-10-20T14:59:56.970Z
DELETE FROM AD_Field WHERE AD_Field_ID=721631
;

-- 2023-10-20T14:59:56.977Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721632
;

-- Field: Tabelle Index(541746,D) -> Index Column(547263,D) -> Table Index
-- Column: AD_Index_Column.AD_Index_Table_ID
-- 2023-10-20T14:59:56.978Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=721632
;

-- 2023-10-20T14:59:56.979Z
DELETE FROM AD_Field WHERE AD_Field_ID=721632
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547263,D) -> main -> 10 -> main.Column SQL
-- Column: AD_Index_Column.ColumnSQL
-- 2023-10-20T14:59:56.986Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=621150
;

-- 2023-10-20T14:59:56.988Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721633
;

-- Field: Tabelle Index(541746,D) -> Index Column(547263,D) -> Column SQL
-- Column: AD_Index_Column.ColumnSQL
-- 2023-10-20T14:59:56.991Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=721633
;

-- 2023-10-20T14:59:56.992Z
DELETE FROM AD_Field WHERE AD_Field_ID=721633
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547263,D) -> main -> 10 -> main.Entitäts-Art
-- Column: AD_Index_Column.EntityType
-- 2023-10-20T14:59:56.998Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=621151
;

-- 2023-10-20T14:59:56.999Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721634
;

-- Field: Tabelle Index(541746,D) -> Index Column(547263,D) -> Entitäts-Art
-- Column: AD_Index_Column.EntityType
-- 2023-10-20T14:59:56.999Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=721634
;

-- 2023-10-20T14:59:57.001Z
DELETE FROM AD_Field WHERE AD_Field_ID=721634
;

-- 2023-10-20T14:59:57.006Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721635
;

-- Field: Tabelle Index(541746,D) -> Index Column(547263,D) -> Aktiv
-- Column: AD_Index_Column.IsActive
-- 2023-10-20T14:59:57.007Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=721635
;

-- 2023-10-20T14:59:57.008Z
DELETE FROM AD_Field WHERE AD_Field_ID=721635
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547263,D) -> main -> 10 -> main.Reihenfolge
-- Column: AD_Index_Column.SeqNo
-- 2023-10-20T14:59:57.013Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=621148
;

-- 2023-10-20T14:59:57.014Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721636
;

-- Field: Tabelle Index(541746,D) -> Index Column(547263,D) -> Reihenfolge
-- Column: AD_Index_Column.SeqNo
-- 2023-10-20T14:59:57.015Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=721636
;

-- 2023-10-20T14:59:57.017Z
DELETE FROM AD_Field WHERE AD_Field_ID=721636
;

-- Tab: Tabelle Index(541746,D) -> Index Column(547263,D)
-- UI Section: main
-- UI Section: Tabelle Index(541746,D) -> Index Column(547263,D) -> main
-- UI Column: 10
-- UI Column: Tabelle Index(541746,D) -> Index Column(547263,D) -> main -> 10
-- UI Element Group: main
-- 2023-10-20T14:59:57.025Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=551266
;

-- 2023-10-20T14:59:57.027Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=547145
;

-- 2023-10-20T14:59:57.027Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=545858
;

-- 2023-10-20T14:59:57.029Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=545858
;

-- 2023-10-20T14:59:57.030Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=547263
;

-- 2023-10-20T14:59:57.031Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=547263
;

-- Tab: Tabelle Index(541746,D) -> Index Column
-- Table: AD_Index_Column
-- 2023-10-20T15:00:17.326Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,540472,0,547266,540102,541746,'Y',TO_TIMESTAMP('2023-10-20 18:00:16.207','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','AD_Index_Column','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Index Column','N',20,1,TO_TIMESTAMP('2023-10-20 18:00:16.207','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:00:17.328Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547266 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-10-20T15:00:17.330Z
/* DDL */  select update_tab_translation_from_ad_element(540472)
;

-- 2023-10-20T15:00:17.336Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547266)
;

-- Tab: Tabelle Index(541746,D) -> Index Column
-- Table: AD_Index_Column
-- 2023-10-20T15:00:33.017Z
UPDATE AD_Tab SET AD_Column_ID=541996,Updated=TO_TIMESTAMP('2023-10-20 18:00:33.017','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547266
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Mandant
-- Column: AD_Index_Column.AD_Client_ID
-- 2023-10-20T15:00:39.172Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541992,721637,0,547266,TO_TIMESTAMP('2023-10-20 18:00:39.02','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-10-20 18:00:39.02','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:00:39.174Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721637 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T15:00:39.177Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-10-20T15:00:39.326Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721637
;

-- 2023-10-20T15:00:39.326Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721637)
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Spalte
-- Column: AD_Index_Column.AD_Column_ID
-- 2023-10-20T15:00:39.432Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541993,721638,0,547266,TO_TIMESTAMP('2023-10-20 18:00:39.329','YYYY-MM-DD HH24:MI:SS.US'),100,'Spalte in der Tabelle',10,'D','Verbindung zur Spalte der Tabelle','Y','N','N','N','N','N','N','N','Spalte',TO_TIMESTAMP('2023-10-20 18:00:39.329','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:00:39.433Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721638 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T15:00:39.434Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(104)
;

-- 2023-10-20T15:00:39.440Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721638
;

-- 2023-10-20T15:00:39.441Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721638)
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Index Column
-- Column: AD_Index_Column.AD_Index_Column_ID
-- 2023-10-20T15:00:39.539Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541994,721639,0,547266,TO_TIMESTAMP('2023-10-20 18:00:39.445','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Index Column',TO_TIMESTAMP('2023-10-20 18:00:39.445','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:00:39.541Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721639 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T15:00:39.542Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540472)
;

-- 2023-10-20T15:00:39.549Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721639
;

-- 2023-10-20T15:00:39.549Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721639)
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Organisation
-- Column: AD_Index_Column.AD_Org_ID
-- 2023-10-20T15:00:39.647Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541995,721640,0,547266,TO_TIMESTAMP('2023-10-20 18:00:39.552','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-10-20 18:00:39.552','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:00:39.648Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721640 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T15:00:39.650Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-10-20T15:00:39.766Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721640
;

-- 2023-10-20T15:00:39.767Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721640)
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Table Index
-- Column: AD_Index_Column.AD_Index_Table_ID
-- 2023-10-20T15:00:39.856Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541996,721641,0,547266,TO_TIMESTAMP('2023-10-20 18:00:39.769','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Table Index',TO_TIMESTAMP('2023-10-20 18:00:39.769','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:00:39.857Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721641 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T15:00:39.858Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540469)
;

-- 2023-10-20T15:00:39.862Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721641
;

-- 2023-10-20T15:00:39.863Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721641)
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Column SQL
-- Column: AD_Index_Column.ColumnSQL
-- 2023-10-20T15:00:39.947Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541997,721642,0,547266,TO_TIMESTAMP('2023-10-20 18:00:39.865','YYYY-MM-DD HH24:MI:SS.US'),100,'Virtual Column (r/o)',2000,'D','You can define virtual columns (not stored in the database). If defined, the Column name is the synonym of the SQL expression defined here. The SQL expression must be valid.<br>
Example: updated-Created" would list the age of the entry in days','Y','N','N','N','N','N','N','N','Column SQL',TO_TIMESTAMP('2023-10-20 18:00:39.865','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:00:39.948Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721642 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T15:00:39.949Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2699)
;

-- 2023-10-20T15:00:39.966Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721642
;

-- 2023-10-20T15:00:39.967Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721642)
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Entitäts-Art
-- Column: AD_Index_Column.EntityType
-- 2023-10-20T15:00:40.060Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542000,721643,0,547266,TO_TIMESTAMP('2023-10-20 18:00:39.971','YYYY-MM-DD HH24:MI:SS.US'),100,'Dictionary Entity Type; Determines ownership and synchronization',512,'D','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','N','N','N','N','N','N','Entitäts-Art',TO_TIMESTAMP('2023-10-20 18:00:39.971','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:00:40.061Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721643 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T15:00:40.063Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1682)
;

-- 2023-10-20T15:00:40.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721643
;

-- 2023-10-20T15:00:40.070Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721643)
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Aktiv
-- Column: AD_Index_Column.IsActive
-- 2023-10-20T15:00:40.171Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542001,721644,0,547266,TO_TIMESTAMP('2023-10-20 18:00:40.072','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-10-20 18:00:40.072','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:00:40.172Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721644 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T15:00:40.174Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-10-20T15:00:40.307Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721644
;

-- 2023-10-20T15:00:40.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721644)
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Reihenfolge
-- Column: AD_Index_Column.SeqNo
-- 2023-10-20T15:00:40.403Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542002,721645,0,547266,TO_TIMESTAMP('2023-10-20 18:00:40.31','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2023-10-20 18:00:40.31','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:00:40.404Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721645 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T15:00:40.406Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2023-10-20T15:00:40.413Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721645
;

-- 2023-10-20T15:00:40.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721645)
;

-- Tab: Tabelle Index(541746,D) -> Index Column(547266,D)
-- UI Section: main
-- 2023-10-20T15:00:47.043Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547266,545859,TO_TIMESTAMP('2023-10-20 18:00:46.904','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-10-20 18:00:46.904','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-10-20T15:00:47.045Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545859 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Tabelle Index(541746,D) -> Index Column(547266,D) -> main
-- UI Column: 10
-- 2023-10-20T15:00:49.827Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547146,545859,TO_TIMESTAMP('2023-10-20 18:00:49.731','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-10-20 18:00:49.731','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Tabelle Index(541746,D) -> Index Column(547266,D) -> main -> 10
-- UI Element Group: main
-- 2023-10-20T15:00:54.159Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547146,551267,TO_TIMESTAMP('2023-10-20 18:00:54.06','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,TO_TIMESTAMP('2023-10-20 18:00:54.06','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547266,D) -> main -> 10 -> main.Reihenfolge
-- Column: AD_Index_Column.SeqNo
-- 2023-10-20T15:01:18.560Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721645,0,547266,621152,551267,'F',TO_TIMESTAMP('2023-10-20 18:01:18.451','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',10,0,0,TO_TIMESTAMP('2023-10-20 18:01:18.451','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547266,D) -> main -> 10 -> main.Spalte
-- Column: AD_Index_Column.AD_Column_ID
-- 2023-10-20T15:01:39.714Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721638,0,547266,621153,551267,'F',TO_TIMESTAMP('2023-10-20 18:01:39.594','YYYY-MM-DD HH24:MI:SS.US'),100,'Spalte in der Tabelle','Verbindung zur Spalte der Tabelle','Y','N','N','Y','N','N','N',0,'Spalte',20,0,0,TO_TIMESTAMP('2023-10-20 18:01:39.594','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547266,D) -> main -> 10 -> main.Column SQL
-- Column: AD_Index_Column.ColumnSQL
-- 2023-10-20T15:02:00.391Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721642,0,547266,621154,551267,'F',TO_TIMESTAMP('2023-10-20 18:02:00.234','YYYY-MM-DD HH24:MI:SS.US'),100,'Virtual Column (r/o)','You can define virtual columns (not stored in the database). If defined, the Column name is the synonym of the SQL expression defined here. The SQL expression must be valid.<br>
Example: updated-Created" would list the age of the entry in days','Y','N','N','Y','N','N','N',0,'Column SQL',30,0,0,TO_TIMESTAMP('2023-10-20 18:02:00.234','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547266,D) -> main -> 10 -> main.Entitäts-Art
-- Column: AD_Index_Column.EntityType
-- 2023-10-20T15:02:22.491Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721643,0,547266,621155,551267,'F',TO_TIMESTAMP('2023-10-20 18:02:22.35','YYYY-MM-DD HH24:MI:SS.US'),100,'Dictionary Entity Type; Determines ownership and synchronization','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','N','Y','N','N','N',0,'Entitäts-Art',40,0,0,TO_TIMESTAMP('2023-10-20 18:02:22.35','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547266,D) -> main -> 10 -> main.Reihenfolge
-- Column: AD_Index_Column.SeqNo
-- 2023-10-20T15:02:38.900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-20 18:02:38.9','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621152
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547266,D) -> main -> 10 -> main.Spalte
-- Column: AD_Index_Column.AD_Column_ID
-- 2023-10-20T15:02:38.906Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-20 18:02:38.906','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621153
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547266,D) -> main -> 10 -> main.Column SQL
-- Column: AD_Index_Column.ColumnSQL
-- 2023-10-20T15:02:38.911Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-20 18:02:38.911','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621154
;

-- UI Element: Tabelle Index(541746,D) -> Index Column(547266,D) -> main -> 10 -> main.Entitäts-Art
-- Column: AD_Index_Column.EntityType
-- 2023-10-20T15:02:38.915Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-20 18:02:38.915','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621155
;

-- Run mode: SWING_CLIENT

-- Name: Tabelle Index
-- Action Type: W
-- Window: Tabelle Index(541746,D)
-- 2023-10-20T15:48:49.079Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582774,542122,0,541746,TO_TIMESTAMP('2023-10-20 18:48:48.841','YYYY-MM-DD HH24:MI:SS.US'),100,'D','AD_Index_Table','Y','N','N','N','N','Tabelle Index',TO_TIMESTAMP('2023-10-20 18:48:48.841','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T15:48:49.097Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542122 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-10-20T15:48:49.102Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542122, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542122)
;

-- 2023-10-20T15:48:49.137Z
/* DDL */  select update_menu_translation_from_ad_element(582774)
;

-- Reordering children of `Shipment`
-- Node name: `Shipment (M_InOut)`
-- 2023-10-20T15:48:49.791Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipping Notification (M_Shipping_Notification)`
-- 2023-10-20T15:48:49.793Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542113 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2023-10-20T15:48:49.793Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2023-10-20T15:48:49.794Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2023-10-20T15:48:49.795Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2023-10-20T15:48:49.796Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2023-10-20T15:48:49.797Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2023-10-20T15:48:49.797Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2023-10-20T15:48:49.798Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2023-10-20T15:48:49.799Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2023-10-20T15:48:49.799Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2023-10-20T15:48:49.800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2023-10-20T15:48:49.802Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2023-10-20T15:48:49.802Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2023-10-20T15:48:49.802Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2023-10-20T15:48:49.803Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2023-10-20T15:48:49.803Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-10-20T15:48:49.804Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2023-10-20T15:48:49.805Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-10-20T15:48:49.805Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-10-20T15:48:49.806Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2023-10-20T15:48:49.806Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Node name: `Tabelle Index`
-- 2023-10-20T15:48:49.807Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542122 AND AD_Tree_ID=10
;

-- Reordering children of `Menu`
-- Node name: `Tabelle Index`
-- 2023-10-20T15:48:56.621Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542122 AND AD_Tree_ID=10
;

-- Node name: `webUI`
-- 2023-10-20T15:48:56.621Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- Node name: `Application Dictionary`
-- 2023-10-20T15:48:56.622Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- Node name: `Übersetzung`
-- 2023-10-20T15:48:56.622Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- Node name: `Handling Units`
-- 2023-10-20T15:48:56.623Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- Node name: `Application Dictionary`
-- 2023-10-20T15:48:56.623Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- Node name: `System Admin`
-- 2023-10-20T15:48:56.624Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2023-10-20T15:48:56.624Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- Node name: `Partner Relations`
-- 2023-10-20T15:48:56.625Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- Node name: `Quote-to-Invoice`
-- 2023-10-20T15:48:56.625Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Management`
-- 2023-10-20T15:48:56.626Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- Node name: `Requisition-to-Invoice`
-- 2023-10-20T15:48:56.626Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- Node name: `DPD`
-- 2023-10-20T15:48:56.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- Node name: `Materialsaldo`
-- 2023-10-20T15:48:56.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- Node name: `Returns`
-- 2023-10-20T15:48:56.628Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- Node name: `Open Items`
-- 2023-10-20T15:48:56.628Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- Node name: `Material Management`
-- 2023-10-20T15:48:56.629Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2023-10-20T15:48:56.629Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- Node name: `Performance Analysis`
-- 2023-10-20T15:48:56.630Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- Node name: `Assets`
-- 2023-10-20T15:48:56.631Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- Node name: `Call Center`
-- 2023-10-20T15:48:56.631Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- Node name: `Berichte`
-- 2023-10-20T15:48:56.631Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- Node name: `Human Resource & Payroll`
-- 2023-10-20T15:48:56.632Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- Node name: `EDI Definition (C_BP_EDI)`
-- 2023-10-20T15:48:56.632Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- Node name: `EDI Transaction`
-- 2023-10-20T15:48:56.633Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- Node name: `Berichte Materialwirtschaft`
-- 2023-10-20T15:48:56.633Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- Node name: `Einstellungen Verkauf`
-- 2023-10-20T15:48:56.634Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- Node name: `Berichte Verkauf`
-- 2023-10-20T15:48:56.634Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- Node name: `Berichte Geschäftspartner`
-- 2023-10-20T15:48:56.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- Node name: `Cockpit`
-- 2023-10-20T15:48:56.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- Node name: `Packstück (M_Package)`
-- 2023-10-20T15:48:56.636Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- Node name: `Lieferanten Abrufauftrag (C_OrderLine)`
-- 2023-10-20T15:48:56.636Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (@PREFIX@de/metas/reports/kassenbuch/report.jasper)`
-- 2023-10-20T15:48:56.637Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- Node name: `Nachlieferung (M_SubsequentDelivery_V)`
-- 2023-10-20T15:48:56.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- Node name: `Verpackung (M_PackagingContainer)`
-- 2023-10-20T15:48:56.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- Node name: `Abolieferplan aktualisieren (de.metas.contracts.subscription.process.C_SubscriptionProgress_Evaluate)`
-- 2023-10-20T15:48:56.639Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- Node name: `Umsatz pro Kunde (@PREFIX@de/metas/reports/umsatzprokunde/report.jasper)`
-- 2023-10-20T15:48:56.639Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- Node name: `Sponsoren Anlegen (de.metas.commission.process.CreateSponsors)`
-- 2023-10-20T15:48:56.640Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- Node name: `Arbeitszeit (@PREFIX@de/metas/reports/arbeitszeit/report.jasper)`
-- 2023-10-20T15:48:56.640Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- Node name: `Check Tree and Reset Sponsor Depths (de.metas.commission.process.CheckTreeResetDepths)`
-- 2023-10-20T15:48:56.641Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- Node name: `Bankeinzug (C_DirectDebit)`
-- 2023-10-20T15:48:56.641Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- Node name: `Spezial`
-- 2023-10-20T15:48:56.642Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- Node name: `Belege`
-- 2023-10-20T15:48:56.643Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- Node name: `Steuer`
-- 2023-10-20T15:48:56.644Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- Node name: `Währung`
-- 2023-10-20T15:48:56.644Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- Node name: `Hauptbuch`
-- 2023-10-20T15:48:56.645Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- Node name: `Speditionsauftrag (@PREFIX@de/metas/docs/sales/shippingorder/report.jasper)`
-- 2023-10-20T15:48:56.646Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- Node name: `Steueranmeldung (@PREFIX@de/metas/reports/taxregistration/report.jasper)`
-- 2023-10-20T15:48:56.646Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- Node name: `Wiederkehrende Zahlungen (C_RecurrentPayment)`
-- 2023-10-20T15:48:56.647Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- Node name: `Verkaufte Artikel (@PREFIX@de/metas/reports/soldproducts/report.jasper)`
-- 2023-10-20T15:48:56.647Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- Node name: `Versand (@PREFIX@de/metas/reports/versand/report.jasper)`
-- 2023-10-20T15:48:56.648Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- Node name: `Provision_LEGACY`
-- 2023-10-20T15:48:56.648Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- Node name: `Vertriebspartnerpunkte (@PREFIX@de/metas/reports/vertriebspartnerpunktzahl/report.jasper)`
-- 2023-10-20T15:48:56.649Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- Node name: `C_BPartner Convert Memo (de.metas.adempiere.process.ConvertBPartnerMemo)`
-- 2023-10-20T15:48:56.649Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- Node name: `Ladeliste (Jasper) (@PREFIX@de/metas/docs/sales/shippingorder/report.jasper)`
-- 2023-10-20T15:48:56.650Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- Node name: `Wiederkenhrende Zahlungs-Rechnungen erzeugen (de.metas.banking.process.C_RecurrentPaymentCreateInvoice)`
-- 2023-10-20T15:48:56.650Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- Node name: `Daten-Bereinigung (de.metas.adempiere.process.SweepTable)`
-- 2023-10-20T15:48:56.651Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- Node name: `Geschäftspartner importieren (org.compiere.process.ImportBPartner)`
-- 2023-10-20T15:48:56.651Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- Node name: `Update C_BPartner.IsSalesRep (de.metas.process.ExecuteUpdateSQL)`
-- 2023-10-20T15:48:56.652Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- Node name: `E/A`
-- 2023-10-20T15:48:56.653Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- Node name: `Sponsor-Statistik aktualisieren (de.metas.commission.process.UpdateSponsorStats)`
-- 2023-10-20T15:48:56.653Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- Node name: `Downline Navigator (de.metas.commision.form.zk.WSponsorBrowse)`
-- 2023-10-20T15:48:56.654Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- Node name: `B2B Adressen und Bankverbindung ändern (de.metas.commision.form.zk.WB2BAddressAccount)`
-- 2023-10-20T15:48:56.654Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- Node name: `B2B Auftrag erfassen (de.metas.commision.form.zk.WB2BOrder)`
-- 2023-10-20T15:48:56.655Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- Node name: `UserAccountLock`
-- 2023-10-20T15:48:56.655Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- Node name: `B2B Bestellübersicht (de.metas.commision.form.zk.WB2BOrderHistory)`
-- 2023-10-20T15:48:56.655Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- Node name: `VP-Ränge (@PREFIX@de/metas/reports/vertriebspartnerraenge/report.jasper)`
-- 2023-10-20T15:48:56.656Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- Node name: `User lock expire (de.metas.user.process.AD_User_ExpireLocks)`
-- 2023-10-20T15:48:56.656Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- Node name: `Orders Overview (de.metas.adempiere.form.swing.OrderOverview)`
-- 2023-10-20T15:48:56.657Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- Node name: `Kommissionier Terminal (de.metas.picking.terminal.form.swing.PickingTerminal)`
-- 2023-10-20T15:48:56.657Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- Node name: `Tour (M_Tour)`
-- 2023-10-20T15:48:56.658Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- Node name: `UI Trigger (AD_TriggerUI)`
-- 2023-10-20T15:48:56.659Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- Node name: `Auftragskandidaten verarbeiten (de.metas.ordercandidate.process.C_OLCandEnqueueForSalesOrderCreation)`
-- 2023-10-20T15:48:56.659Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- Node name: `ESR Zahlungsimport (ESR_Import)`
-- 2023-10-20T15:48:56.660Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- Node name: `Liefertage (M_DeliveryDay)`
-- 2023-10-20T15:48:56.660Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- Node name: `Create product costs (de.metas.adempiere.process.CreateProductCosts)`
-- 2023-10-20T15:48:56.661Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- Node name: `Document Management`
-- 2023-10-20T15:48:56.661Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- Node name: `Update Addresses (de.metas.adempiere.process.UpdateAddresses)`
-- 2023-10-20T15:48:56.662Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- Node name: `Massendruck`
-- 2023-10-20T15:48:56.662Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- Node name: `Abrechnung MwSt.-Korrektur (C_Invoice_VAT_Corr_Candidates_v1)`
-- 2023-10-20T15:48:56.663Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot (M_PickingSlot)`
-- 2023-10-20T15:48:56.663Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2023-10-20T15:48:56.664Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- Node name: `Picking Vorbereitung Liste (Jasper) (@PREFIX@de/metas/reports/pickingpreparation/report.jasper)`
-- 2023-10-20T15:48:56.664Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- Node name: `Parzelle (C_Allotment)`
-- 2023-10-20T15:48:56.665Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- Node name: `Export Format (EXP_Format)`
-- 2023-10-20T15:48:56.665Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- Node name: `Transportdisposition (M_Tour_Instance)`
-- 2023-10-20T15:48:56.666Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- Node name: `Belegzeile-Sortierung (C_DocLine_Sort)`
-- 2023-10-20T15:48:56.666Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- Node name: `Zählbestand Einkauf (fresh) (Fresh_QtyOnHand)`
-- 2023-10-20T15:48:56.666Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2023-10-20T15:48:56.667Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2023-10-20T15:48:56.667Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- Node name: `Transparenz zur Status ESR Import in Bankauszug (x_esr_import_in_c_bankstatement_v)`
-- 2023-10-20T15:48:56.668Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- Node name: `Offene Zahlung - Skonto Zuordnung (de.metas.payment.process.C_Payment_MassWriteOff)`
-- 2023-10-20T15:48:56.668Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- Node name: `Gebinde`
-- 2023-10-20T15:48:56.669Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- Node name: `Parzelle`
-- 2023-10-20T15:48:56.669Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- Node name: `AD_Table_CreateFromInputFile (org.adempiere.ad.table.process.AD_Table_CreateFromInputFile)`
-- 2023-10-20T15:48:56.670Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- Node name: `Shipment restrictions (M_Shipment_Constraint)`
-- 2023-10-20T15:48:56.670Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- Node name: `Board Configuration (WEBUI_Board)`
-- 2023-10-20T15:48:56.671Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- Node name: `Test facility group (S_HumanResourceTestGroup)`
-- 2023-10-20T15:48:56.671Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542082 AND AD_Tree_ID=10
;

-- Node name: `Request (R_Request)`
-- 2023-10-20T15:48:56.672Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- Node name: `Create Membership Contracts (de.metas.contracts.order.process.C_Order_CreateForAllMembers)`
-- 2023-10-20T15:48:56.673Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541832 AND AD_Tree_ID=10
;

-- Run mode: SWING_CLIENT

-- Reordering children of `Application Dictionary`
-- Node name: `Tabelle Index`
-- 2023-10-20T15:50:35.477Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542122 AND AD_Tree_ID=10
;

-- Node name: `Search Definition (AD_SearchDefinition)`
-- 2023-10-20T15:50:35.479Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53203 AND AD_Tree_ID=10
;

-- Node name: `Entity Type (AD_EntityType)`
-- 2023-10-20T15:50:35.480Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=586 AND AD_Tree_ID=10
;

-- Node name: `Relation Type (AD_RelationType)`
-- 2023-10-20T15:50:35.481Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53251 AND AD_Tree_ID=10
;

-- Node name: `Diagnose ZoomInfoFactory (de.metas.zoom.process.ZoomInfoFactoryExecute)`
-- 2023-10-20T15:50:35.481Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540994 AND AD_Tree_ID=10
;

-- Node name: `Element (AD_Element)`
-- 2023-10-20T15:50:35.482Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=138 AND AD_Tree_ID=10
;

-- Node name: `Application Elements (AD_Element_Trl)`
-- 2023-10-20T15:50:35.482Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541160 AND AD_Tree_ID=10
;

-- Node name: `Table and Column (AD_Table)`
-- 2023-10-20T15:50:35.483Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=139 AND AD_Tree_ID=10
;

-- Node name: `Field Group (AD_FieldGroup)`
-- 2023-10-20T15:50:35.483Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=249 AND AD_Tree_ID=10
;

-- Node name: `Window Management (AD_Window)`
-- 2023-10-20T15:50:35.484Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=141 AND AD_Tree_ID=10
;

-- Node name: `Field Context Menu (AD_Field_ContextMenu)`
-- 2023-10-20T15:50:35.484Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53455 AND AD_Tree_ID=10
;

-- Node name: `Form (AD_Form)`
-- 2023-10-20T15:50:35.485Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=216 AND AD_Tree_ID=10
;

-- Node name: `Info Window (AD_InfoWindow)`
-- 2023-10-20T15:50:35.485Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=589 AND AD_Tree_ID=10
;

-- Node name: `Reference (AD_Reference)`
-- 2023-10-20T15:50:35.486Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=140 AND AD_Tree_ID=10
;

-- Node name: `Workbench (AD_Workbench)`
-- 2023-10-20T15:50:35.486Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=300 AND AD_Tree_ID=10
;

-- Node name: `Validation Rules (AD_Val_Rule)`
-- 2023-10-20T15:50:35.487Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=142 AND AD_Tree_ID=10
;

-- Node name: `Desktop (AD_Desktop)`
-- 2023-10-20T15:50:35.487Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=295 AND AD_Tree_ID=10
;

-- Node name: `Model Validator (AD_ModelValidator)`
-- 2023-10-20T15:50:35.488Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53012 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2023-10-20T15:50:35.488Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=143 AND AD_Tree_ID=10
;

-- Node name: `Report View (AD_ReportView)`
-- 2023-10-20T15:50:35.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=201 AND AD_Tree_ID=10
;

-- Node name: `Report & Process (AD_Process)`
-- 2023-10-20T15:50:35.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=176 AND AD_Tree_ID=10
;

-- Node name: `Rule (AD_Rule)`
-- 2023-10-20T15:50:35.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53086 AND AD_Tree_ID=10
;

-- Node name: `Custom Attribute (AD_Attribute)`
-- 2023-10-20T15:50:35.490Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=239 AND AD_Tree_ID=10
;

-- Node name: `Window Customization (AD_UserDef_Win)`
-- 2023-10-20T15:50:35.490Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=517 AND AD_Tree_ID=10
;

-- Node name: `Reapply Customizations (org.compiere.process.ChangeLogProcess)`
-- 2023-10-20T15:50:35.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=499 AND AD_Tree_ID=10
;

-- Node name: `Migration (AD_Migration)`
-- 2023-10-20T15:50:35.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53221 AND AD_Tree_ID=10
;

-- Node name: `Import migration from XML (org.compiere.process.MigrationFromXML)`
-- 2023-10-20T15:50:35.492Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53222 AND AD_Tree_ID=10
;

-- Node name: `Migration Scripts (AD_MigrationScript)`
-- 2023-10-20T15:50:35.492Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53089 AND AD_Tree_ID=10
;

-- Node name: `Callouts Customization (AD_ColumnCallout)`
-- 2023-10-20T15:50:35.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53267 AND AD_Tree_ID=10
;

-- Node name: `Validate Application Dictionary (org.adempiere.appdict.validation.process.AppDictValidator)`
-- 2023-10-20T15:50:35.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53568 AND AD_Tree_ID=10
;

-- Node name: `Relationsart (AD_RelationType)`
-- 2023-10-20T15:50:35.494Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540037 AND AD_Tree_ID=10
;

-- Node name: `Java Classes (AD_JavaClass_Type)`
-- 2023-10-20T15:50:35.494Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540492 AND AD_Tree_ID=10
;

-- Node name: `Migrate column callout (org.adempiere.process.AD_ColumnCallout_Migrate)`
-- 2023-10-20T15:50:35.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53266 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WebUI) (WEBUI_Dashboard)`
-- 2023-10-20T15:50:35.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540735 AND AD_Tree_ID=10
;

-- Node name: `Properties Configuration (M_PropertiesConfig)`
-- 2023-10-20T15:50:35.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540575 AND AD_Tree_ID=10
;

-- Node name: `UI Element (AD_UI_Element)`
-- 2023-10-20T15:50:35.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541333 AND AD_Tree_ID=10
;

-- Node name: `Related processes (AD_Table_Process)`
-- 2023-10-20T15:50:35.497Z
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542000 AND AD_Tree_ID=10
;

-- Run mode: SWING_CLIENT

-- Process: AD_Index_Create(org.adempiere.process.AD_Index_Create)
-- Table: AD_Index_Table
-- Window: Tabelle Index(541746,D)
-- EntityType: D
-- 2023-10-20T15:57:01.197Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,540055,540101,541441,541746,TO_TIMESTAMP('2023-10-20 18:57:01.048','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2023-10-20 18:57:01.048','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Run mode: SWING_CLIENT

-- UI Column: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 20
-- UI Element Group: org
-- 2023-10-20T16:01:56.406Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547144,551268,TO_TIMESTAMP('2023-10-20 19:01:56.235','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',20,TO_TIMESTAMP('2023-10-20 19:01:56.235','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 20 -> org.Organisation
-- Column: AD_Index_Table.AD_Org_ID
-- 2023-10-20T16:02:58.939Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721611,0,547262,621156,551268,'F',TO_TIMESTAMP('2023-10-20 19:02:58.762','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-10-20 19:02:58.762','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 20 -> org.Mandant
-- Column: AD_Index_Table.AD_Client_ID
-- 2023-10-20T16:03:03.651Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721610,0,547262,621157,551268,'F',TO_TIMESTAMP('2023-10-20 19:03:03.563','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-10-20 19:03:03.563','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.DB-Tabelle
-- Column: AD_Index_Table.AD_Table_ID
-- 2023-10-20T16:03:37.384Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721613,0,547262,621158,551264,'F',TO_TIMESTAMP('2023-10-20 19:03:37.251','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','N','N',0,'DB-Tabelle',100,0,0,TO_TIMESTAMP('2023-10-20 19:03:37.251','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.DB-Tabelle
-- Column: AD_Index_Table.AD_Table_ID
-- 2023-10-20T16:03:42.991Z
UPDATE AD_UI_Element SET SeqNo=11,Updated=TO_TIMESTAMP('2023-10-20 19:03:42.991','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621158
;

-- Run mode: SWING_CLIENT

-- Column: AD_Index_Table.AD_Table_ID
-- 2023-10-20T16:13:10.494Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-20 19:13:10.494','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=541979
;

-- Column: AD_Index_Table.IsUnique
-- 2023-10-20T16:13:23.394Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-20 19:13:23.394','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=541987
;

-- Column: AD_Index_Table.WhereClause
-- 2023-10-20T16:13:40.199Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-20 19:13:40.199','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=542024
;

-- Column: AD_Index_Table.Description
-- 2023-10-20T16:13:58.714Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-20 19:13:58.714','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=541982
;

-- Column: AD_Index_Table.ErrorMsg
-- 2023-10-20T16:14:08.810Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-20 19:14:08.81','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=542025
;

-- Run mode: SWING_CLIENT

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Mandant
-- Column: AD_Index_Table.AD_Client_ID
-- 2023-10-20T16:55:24.539Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:24.539','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721610
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Organisation
-- Column: AD_Index_Table.AD_Org_ID
-- 2023-10-20T16:55:25.939Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:25.939','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721611
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Table Index
-- Column: AD_Index_Table.AD_Index_Table_ID
-- 2023-10-20T16:55:26.747Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:26.747','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721612
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> DB-Tabelle
-- Column: AD_Index_Table.AD_Table_ID
-- 2023-10-20T16:55:27.412Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:27.412','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721613
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Beschreibung
-- Column: AD_Index_Table.Description
-- 2023-10-20T16:55:28.083Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:28.083','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721614
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Entitäts-Art
-- Column: AD_Index_Table.EntityType
-- 2023-10-20T16:55:28.658Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:28.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721615
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Kommentar/Hilfe
-- Column: AD_Index_Table.Help
-- 2023-10-20T16:55:29.373Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:29.373','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721616
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Aktiv
-- Column: AD_Index_Table.IsActive
-- 2023-10-20T16:55:29.837Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:29.837','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721617
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Unique
-- Column: AD_Index_Table.IsUnique
-- 2023-10-20T16:55:30.422Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:30.422','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721618
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Name
-- Column: AD_Index_Table.Name
-- 2023-10-20T16:55:31.334Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:31.334','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721619
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Process Now
-- Column: AD_Index_Table.Processing
-- 2023-10-20T16:55:31.877Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:31.877','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721620
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Sql WHERE
-- Column: AD_Index_Table.WhereClause
-- 2023-10-20T16:55:32.331Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:32.331','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721621
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Fehlermeldung
-- Column: AD_Index_Table.ErrorMsg
-- 2023-10-20T16:55:32.900Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:32.9','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721622
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Before Change Code
-- Column: AD_Index_Table.BeforeChangeCode
-- 2023-10-20T16:55:33.613Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:33.613','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721623
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Before Change Code Type
-- Column: AD_Index_Table.BeforeChangeCodeType
-- 2023-10-20T16:55:34.133Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:55:34.133','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721624
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Before Change Warning
-- Column: AD_Index_Table.BeforeChangeWarning
-- 2023-10-20T16:56:05.249Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:56:05.249','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721625
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Mandant
-- Column: AD_Index_Column.AD_Client_ID
-- 2023-10-20T16:56:21.445Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:56:21.445','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721637
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Spalte
-- Column: AD_Index_Column.AD_Column_ID
-- 2023-10-20T16:56:22.260Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:56:22.26','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721638
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Index Column
-- Column: AD_Index_Column.AD_Index_Column_ID
-- 2023-10-20T16:56:22.747Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:56:22.747','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721639
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Organisation
-- Column: AD_Index_Column.AD_Org_ID
-- 2023-10-20T16:56:23.380Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:56:23.38','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721640
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Table Index
-- Column: AD_Index_Column.AD_Index_Table_ID
-- 2023-10-20T16:56:23.781Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:56:23.781','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721641
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Column SQL
-- Column: AD_Index_Column.ColumnSQL
-- 2023-10-20T16:56:24.100Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:56:24.1','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721642
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Entitäts-Art
-- Column: AD_Index_Column.EntityType
-- 2023-10-20T16:56:24.483Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:56:24.483','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721643
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Aktiv
-- Column: AD_Index_Column.IsActive
-- 2023-10-20T16:56:24.788Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:56:24.788','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721644
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Reihenfolge
-- Column: AD_Index_Column.SeqNo
-- 2023-10-20T16:56:25.992Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-20 19:56:25.992','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721645
;

-- Run mode: SWING_CLIENT

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.Name
-- Column: AD_Index_Table.Name
-- 2023-10-20T17:05:47.611Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.611','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621137
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.DB-Tabelle
-- Column: AD_Index_Table.AD_Table_ID
-- 2023-10-20T17:05:47.620Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.62','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621158
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.Beschreibung
-- Column: AD_Index_Table.Description
-- 2023-10-20T17:05:47.624Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.624','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621138
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.Kommentar/Hilfe
-- Column: AD_Index_Table.Help
-- 2023-10-20T17:05:47.629Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.629','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621139
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.Sql WHERE
-- Column: AD_Index_Table.WhereClause
-- 2023-10-20T17:05:47.633Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.633','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621140
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.Fehlermeldung
-- Column: AD_Index_Table.ErrorMsg
-- 2023-10-20T17:05:47.637Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.637','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621141
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.Before Change Code Type
-- Column: AD_Index_Table.BeforeChangeCodeType
-- 2023-10-20T17:05:47.640Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.64','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621142
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.Before Change Code
-- Column: AD_Index_Table.BeforeChangeCode
-- 2023-10-20T17:05:47.644Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.644','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621143
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.Before Change Warning
-- Column: AD_Index_Table.BeforeChangeWarning
-- 2023-10-20T17:05:47.648Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.647','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621144
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 10 -> main.Entitäts-Art
-- Column: AD_Index_Table.EntityType
-- 2023-10-20T17:05:47.652Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.652','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621145
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Index_Table.IsActive
-- 2023-10-20T17:05:47.654Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.654','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621135
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 20 -> flags.Unique
-- Column: AD_Index_Table.IsUnique
-- 2023-10-20T17:05:47.656Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.656','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621136
;

-- UI Element: Tabelle Index(541746,D) -> Table Index(547262,D) -> main -> 20 -> org.Organisation
-- Column: AD_Index_Table.AD_Org_ID
-- 2023-10-20T17:05:47.659Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-10-20 20:05:47.659','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621156
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Mandant
-- Column: AD_Index_Table.AD_Client_ID
-- 2023-10-20T17:06:50.383Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:50.383','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721610
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Organisation
-- Column: AD_Index_Table.AD_Org_ID
-- 2023-10-20T17:06:50.742Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:50.742','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721611
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Table Index
-- Column: AD_Index_Table.AD_Index_Table_ID
-- 2023-10-20T17:06:51.186Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:51.186','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721612
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> DB-Tabelle
-- Column: AD_Index_Table.AD_Table_ID
-- 2023-10-20T17:06:51.691Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:51.691','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721613
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Beschreibung
-- Column: AD_Index_Table.Description
-- 2023-10-20T17:06:52.397Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:52.397','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721614
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Entitäts-Art
-- Column: AD_Index_Table.EntityType
-- 2023-10-20T17:06:52.820Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:52.82','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721615
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Kommentar/Hilfe
-- Column: AD_Index_Table.Help
-- 2023-10-20T17:06:53.347Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:53.347','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721616
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Aktiv
-- Column: AD_Index_Table.IsActive
-- 2023-10-20T17:06:53.771Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:53.771','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721617
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Unique
-- Column: AD_Index_Table.IsUnique
-- 2023-10-20T17:06:54.332Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:54.332','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721618
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Name
-- Column: AD_Index_Table.Name
-- 2023-10-20T17:06:54.867Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:54.867','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721619
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Process Now
-- Column: AD_Index_Table.Processing
-- 2023-10-20T17:06:55.277Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:55.277','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721620
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Sql WHERE
-- Column: AD_Index_Table.WhereClause
-- 2023-10-20T17:06:55.798Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:55.797','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721621
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Fehlermeldung
-- Column: AD_Index_Table.ErrorMsg
-- 2023-10-20T17:06:56.357Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:56.357','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721622
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Before Change Code
-- Column: AD_Index_Table.BeforeChangeCode
-- 2023-10-20T17:06:56.981Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:56.981','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721623
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Before Change Code Type
-- Column: AD_Index_Table.BeforeChangeCodeType
-- 2023-10-20T17:06:57.349Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:57.349','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721624
;

-- Field: Tabelle Index(541746,D) -> Table Index(547262,D) -> Before Change Warning
-- Column: AD_Index_Table.BeforeChangeWarning
-- 2023-10-20T17:06:58.606Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:06:58.606','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721625
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Mandant
-- Column: AD_Index_Column.AD_Client_ID
-- 2023-10-20T17:07:09.550Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:07:09.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721637
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Spalte
-- Column: AD_Index_Column.AD_Column_ID
-- 2023-10-20T17:07:09.942Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:07:09.942','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721638
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Index Column
-- Column: AD_Index_Column.AD_Index_Column_ID
-- 2023-10-20T17:07:10.301Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:07:10.301','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721639
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Organisation
-- Column: AD_Index_Column.AD_Org_ID
-- 2023-10-20T17:07:10.837Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:07:10.837','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721640
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Table Index
-- Column: AD_Index_Column.AD_Index_Table_ID
-- 2023-10-20T17:07:11.325Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:07:11.325','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721641
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Column SQL
-- Column: AD_Index_Column.ColumnSQL
-- 2023-10-20T17:07:11.781Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:07:11.781','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721642
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Entitäts-Art
-- Column: AD_Index_Column.EntityType
-- 2023-10-20T17:07:12.195Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:07:12.195','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721643
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Aktiv
-- Column: AD_Index_Column.IsActive
-- 2023-10-20T17:07:12.530Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:07:12.53','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721644
;

-- Field: Tabelle Index(541746,D) -> Index Column(547266,D) -> Reihenfolge
-- Column: AD_Index_Column.SeqNo
-- 2023-10-20T17:07:13.936Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-20 20:07:13.936','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721645
;

-- Run mode: SWING_CLIENT

-- 2023-10-20T17:41:48.878Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582775,0,TO_TIMESTAMP('2023-10-20 20:41:48.721','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Index Übersetzungen','Index Übersetzungen',TO_TIMESTAMP('2023-10-20 20:41:48.721','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:41:48.907Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582775 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-10-20T17:41:58.929Z
UPDATE AD_Element_Trl SET Name='Index Translations', PrintName='Index Translations',Updated=TO_TIMESTAMP('2023-10-20 20:41:58.929','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582775 AND AD_Language='en_US'
;

-- 2023-10-20T17:41:58.954Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582775,'en_US')
;

-- Run mode: SWING_CLIENT

-- Window: Index Übersetzungen, InternalName=null
-- 2023-10-20T17:42:36.286Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582775,0,541747,TO_TIMESTAMP('2023-10-20 20:42:36.145','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','N','N','N','Y','Index Übersetzungen','N',TO_TIMESTAMP('2023-10-20 20:42:36.145','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-10-20T17:42:36.289Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541747 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-10-20T17:42:36.295Z
/* DDL */  select update_window_translation_from_ad_element(582775)
;

-- 2023-10-20T17:42:36.316Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541747
;

-- 2023-10-20T17:42:36.324Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541747)
;

-- Tab: Index Übersetzungen(541747,D) -> Index Übersetzungen
-- Table: AD_Index_Table_Trl
-- 2023-10-20T17:43:16.270Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582775,0,547267,540109,541747,'Y',TO_TIMESTAMP('2023-10-20 20:43:16.107','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','AD_Index_Table_Trl','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Index Übersetzungen','N',10,0,TO_TIMESTAMP('2023-10-20 20:43:16.107','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:43:16.272Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547267 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-10-20T17:43:16.274Z
/* DDL */  select update_tab_translation_from_ad_element(582775)
;

-- 2023-10-20T17:43:16.280Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547267)
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Table Index
-- Column: AD_Index_Table_Trl.AD_Index_Table_ID
-- 2023-10-20T17:43:21.232Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542070,721646,0,547267,TO_TIMESTAMP('2023-10-20 20:43:21.103','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Table Index',TO_TIMESTAMP('2023-10-20 20:43:21.103','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:43:21.234Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721646 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T17:43:21.236Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540469)
;

-- 2023-10-20T17:43:21.243Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721646
;

-- 2023-10-20T17:43:21.248Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721646)
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Sprache
-- Column: AD_Index_Table_Trl.AD_Language
-- 2023-10-20T17:43:21.370Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542071,721647,0,547267,TO_TIMESTAMP('2023-10-20 20:43:21.269','YYYY-MM-DD HH24:MI:SS.US'),100,'Sprache für diesen Eintrag',6,'D','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','N','N','N','N','N','Sprache',TO_TIMESTAMP('2023-10-20 20:43:21.269','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:43:21.372Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721647 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T17:43:21.373Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(109)
;

-- 2023-10-20T17:43:21.390Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721647
;

-- 2023-10-20T17:43:21.390Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721647)
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Mandant
-- Column: AD_Index_Table_Trl.AD_Client_ID
-- 2023-10-20T17:43:21.531Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542072,721648,0,547267,TO_TIMESTAMP('2023-10-20 20:43:21.393','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-10-20 20:43:21.393','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:43:21.532Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721648 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T17:43:21.533Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-10-20T17:43:21.742Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721648
;

-- 2023-10-20T17:43:21.742Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721648)
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Organisation
-- Column: AD_Index_Table_Trl.AD_Org_ID
-- 2023-10-20T17:43:22.079Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542073,721649,0,547267,TO_TIMESTAMP('2023-10-20 20:43:21.748','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-10-20 20:43:21.748','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:43:22.080Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721649 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T17:43:22.081Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-10-20T17:43:22.220Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721649
;

-- 2023-10-20T17:43:22.221Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721649)
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Aktiv
-- Column: AD_Index_Table_Trl.IsActive
-- 2023-10-20T17:43:22.330Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542074,721650,0,547267,TO_TIMESTAMP('2023-10-20 20:43:22.223','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-10-20 20:43:22.223','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:43:22.332Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721650 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T17:43:22.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-10-20T17:43:22.517Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721650
;

-- 2023-10-20T17:43:22.518Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721650)
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Fehlermeldung
-- Column: AD_Index_Table_Trl.ErrorMsg
-- 2023-10-20T17:43:22.772Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542079,721651,0,547267,TO_TIMESTAMP('2023-10-20 20:43:22.52','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'D','Y','N','N','N','N','N','N','N','Fehlermeldung',TO_TIMESTAMP('2023-10-20 20:43:22.52','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:43:22.773Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721651 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T17:43:22.775Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1021)
;

-- 2023-10-20T17:43:22.788Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721651
;

-- 2023-10-20T17:43:22.788Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721651)
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Übersetzt
-- Column: AD_Index_Table_Trl.IsTranslated
-- 2023-10-20T17:43:22.896Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542080,721652,0,547267,TO_TIMESTAMP('2023-10-20 20:43:22.792','YYYY-MM-DD HH24:MI:SS.US'),100,'Diese Spalte ist übersetzt',1,'D','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist','Y','N','N','N','N','N','N','N','Übersetzt',TO_TIMESTAMP('2023-10-20 20:43:22.792','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:43:22.898Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721652 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T17:43:22.900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(420)
;

-- 2023-10-20T17:43:22.919Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721652
;

-- 2023-10-20T17:43:22.920Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721652)
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Before Change Warning
-- Column: AD_Index_Table_Trl.BeforeChangeWarning
-- 2023-10-20T17:43:23.021Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,544896,721653,0,547267,TO_TIMESTAMP('2023-10-20 20:43:22.923','YYYY-MM-DD HH24:MI:SS.US'),100,'Warning to be shown if one of the indexed columns were changed. ',2000,'D','Y','N','N','N','N','N','N','N','Before Change Warning',TO_TIMESTAMP('2023-10-20 20:43:22.923','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:43:23.022Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721653 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T17:43:23.023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541261)
;

-- 2023-10-20T17:43:23.027Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721653
;

-- 2023-10-20T17:43:23.028Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721653)
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Beschreibung
-- Column: AD_Index_Table_Trl.Description
-- 2023-10-20T17:43:23.132Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563746,721654,0,547267,TO_TIMESTAMP('2023-10-20 20:43:23.03','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2023-10-20 20:43:23.03','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-20T17:43:23.133Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721654 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-20T17:43:23.134Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2023-10-20T17:43:23.243Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721654
;

-- 2023-10-20T17:43:23.243Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721654)
;

-- Tab: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D)
-- UI Section: main
-- 2023-10-20T17:44:13.664Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547267,545860,TO_TIMESTAMP('2023-10-20 20:44:13.516','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-10-20 20:44:13.516','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-10-20T17:44:13.667Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545860 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main
-- UI Column: 10
-- 2023-10-20T17:44:16.166Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547147,545860,TO_TIMESTAMP('2023-10-20 20:44:16.056','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-10-20 20:44:16.056','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main
-- UI Column: 20
-- 2023-10-20T17:44:17.168Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547148,545860,TO_TIMESTAMP('2023-10-20 20:44:17.068','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-10-20 20:44:17.068','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 10
-- UI Element Group: main
-- 2023-10-20T17:44:34.788Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547147,551269,TO_TIMESTAMP('2023-10-20 20:44:34.659','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,TO_TIMESTAMP('2023-10-20 20:44:34.659','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Run mode: SWING_CLIENT

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 10 -> main.Sprache
-- Column: AD_Index_Table_Trl.AD_Language
-- 2023-10-22T12:47:17.123Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721647,0,547267,621159,551269,'F',TO_TIMESTAMP('2023-10-22 15:47:16.715','YYYY-MM-DD HH24:MI:SS.US'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','Y','N','N','N',0,'Sprache',10,0,0,TO_TIMESTAMP('2023-10-22 15:47:16.715','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 10 -> main.Fehlermeldung
-- Column: AD_Index_Table_Trl.ErrorMsg
-- 2023-10-22T12:47:44.017Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721651,0,547267,621160,551269,'F',TO_TIMESTAMP('2023-10-22 15:47:43.894','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Fehlermeldung',20,0,0,TO_TIMESTAMP('2023-10-22 15:47:43.894','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 20
-- UI Element Group: flags
-- 2023-10-22T12:47:56.087Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547148,551270,TO_TIMESTAMP('2023-10-22 15:47:55.953','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2023-10-22 15:47:55.953','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Index_Table_Trl.IsActive
-- 2023-10-22T12:48:17.090Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721650,0,547267,621161,551270,'F',TO_TIMESTAMP('2023-10-22 15:48:16.983','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-10-22 15:48:16.983','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 20 -> flags.Übersetzt
-- Column: AD_Index_Table_Trl.IsTranslated
-- 2023-10-22T12:48:46.057Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721652,0,547267,621162,551270,'F',TO_TIMESTAMP('2023-10-22 15:48:45.939','YYYY-MM-DD HH24:MI:SS.US'),100,'Diese Spalte ist übersetzt','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist','Y','N','N','Y','N','N','N',0,'Übersetzt',20,0,0,TO_TIMESTAMP('2023-10-22 15:48:45.939','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 20
-- UI Element Group: org
-- 2023-10-22T12:48:52.699Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547148,551271,TO_TIMESTAMP('2023-10-22 15:48:52.599','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',20,TO_TIMESTAMP('2023-10-22 15:48:52.599','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 20 -> org.Organisation
-- Column: AD_Index_Table_Trl.AD_Org_ID
-- 2023-10-22T12:50:36.289Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721649,0,547267,621163,551271,'F',TO_TIMESTAMP('2023-10-22 15:50:36.148','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-10-22 15:50:36.148','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 20 -> org.Mandant
-- Column: AD_Index_Table_Trl.AD_Client_ID
-- 2023-10-22T12:51:02.533Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721648,0,547267,621164,551271,'F',TO_TIMESTAMP('2023-10-22 15:51:02.421','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-10-22 15:51:02.421','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Run mode: SWING_CLIENT

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Name
-- Column: AD_Index_Table.Name
-- 2023-10-25T16:53:25.070Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551833
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Beschreibung
-- Column: AD_Index_Table.Description
-- 2023-10-25T16:53:33.407Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551834
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Kommentar/Hilfe
-- Column: AD_Index_Table.Help
-- 2023-10-25T16:53:33.442Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551835
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Sql WHERE
-- Column: AD_Index_Table.WhereClause
-- 2023-10-25T16:53:33.472Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551836
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Error Msg
-- Column: AD_Index_Table.ErrorMsg
-- 2023-10-25T16:53:33.495Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551837
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Active
-- Column: AD_Index_Table.IsActive
-- 2023-10-25T16:53:33.526Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551838
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Unique
-- Column: AD_Index_Table.IsUnique
-- 2023-10-25T16:53:33.552Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551839
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Before Change Code Type
-- Column: AD_Index_Table.BeforeChangeCodeType
-- 2023-10-25T16:53:33.576Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551840
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Before Change Code
-- Column: AD_Index_Table.BeforeChangeCode
-- 2023-10-25T16:53:33.595Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551841
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Before Change Warning
-- Column: AD_Index_Table.BeforeChangeWarning
-- 2023-10-25T16:53:33.616Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551842
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Entity Type
-- Column: AD_Index_Table.EntityType
-- 2023-10-25T16:53:33.639Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551843
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Sektion
-- Column: AD_Index_Table.AD_Org_ID
-- 2023-10-25T16:53:33.661Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551858
;

-- UI Element: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10 -> default.Mandant
-- Column: AD_Index_Table.AD_Client_ID
-- 2023-10-25T16:53:33.680Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551859
;

-- UI Column: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main -> 10
-- UI Element Group: default
-- 2023-10-25T16:53:38.196Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=541570
;

-- UI Section: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> main
-- UI Column: 10
-- 2023-10-25T16:53:42.262Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540955
;

-- Tab: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex)
-- UI Section: main
-- 2023-10-25T16:53:49.050Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=540731
;

-- 2023-10-25T16:53:49.054Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540731
;

-- 2023-10-25T16:53:58.157Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542779
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Name
-- Column: AD_Index_Table.Name
-- 2023-10-25T16:53:58.169Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542779
;

-- 2023-10-25T16:53:58.172Z
DELETE FROM AD_Field WHERE AD_Field_ID=542779
;

-- 2023-10-25T16:53:58.274Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542777
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Beschreibung
-- Column: AD_Index_Table.Description
-- 2023-10-25T16:53:58.278Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542777
;

-- 2023-10-25T16:53:58.281Z
DELETE FROM AD_Field WHERE AD_Field_ID=542777
;

-- 2023-10-25T16:53:58.374Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542776
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Kommentar/Hilfe
-- Column: AD_Index_Table.Help
-- 2023-10-25T16:53:58.381Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542776
;

-- 2023-10-25T16:53:58.383Z
DELETE FROM AD_Field WHERE AD_Field_ID=542776
;

-- 2023-10-25T16:53:58.480Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542802
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Sql WHERE
-- Column: AD_Index_Table.WhereClause
-- 2023-10-25T16:53:58.482Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542802
;

-- 2023-10-25T16:53:58.484Z
DELETE FROM AD_Field WHERE AD_Field_ID=542802
;

-- 2023-10-25T16:53:58.574Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542801
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Fehlermeldung
-- Column: AD_Index_Table.ErrorMsg
-- 2023-10-25T16:53:58.576Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542801
;

-- 2023-10-25T16:53:58.578Z
DELETE FROM AD_Field WHERE AD_Field_ID=542801
;

-- 2023-10-25T16:53:58.664Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542774
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Aktiv
-- Column: AD_Index_Table.IsActive
-- 2023-10-25T16:53:58.666Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542774
;

-- 2023-10-25T16:53:58.669Z
DELETE FROM AD_Field WHERE AD_Field_ID=542774
;

-- 2023-10-25T16:53:58.750Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542784
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Unique
-- Column: AD_Index_Table.IsUnique
-- 2023-10-25T16:53:58.753Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542784
;

-- 2023-10-25T16:53:58.755Z
DELETE FROM AD_Field WHERE AD_Field_ID=542784
;

-- 2023-10-25T16:53:58.852Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542849
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Before Change Code Type
-- Column: AD_Index_Table.BeforeChangeCodeType
-- 2023-10-25T16:53:58.854Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542849
;

-- 2023-10-25T16:53:58.855Z
DELETE FROM AD_Field WHERE AD_Field_ID=542849
;

-- 2023-10-25T16:53:58.948Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=546892
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Before Change Warning
-- Column: AD_Index_Table.BeforeChangeWarning
-- 2023-10-25T16:53:58.950Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=546892
;

-- 2023-10-25T16:53:58.952Z
DELETE FROM AD_Field WHERE AD_Field_ID=546892
;

-- 2023-10-25T16:53:59.045Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542848
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Before Change Code
-- Column: AD_Index_Table.BeforeChangeCode
-- 2023-10-25T16:53:59.048Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542848
;

-- 2023-10-25T16:53:59.050Z
DELETE FROM AD_Field WHERE AD_Field_ID=542848
;

-- 2023-10-25T16:53:59.146Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542778
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Entitäts-Art
-- Column: AD_Index_Table.EntityType
-- 2023-10-25T16:53:59.148Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542778
;

-- 2023-10-25T16:53:59.150Z
DELETE FROM AD_Field WHERE AD_Field_ID=542778
;

-- 2023-10-25T16:53:59.255Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542781
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Process Now
-- Column: AD_Index_Table.Processing
-- 2023-10-25T16:53:59.257Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542781
;

-- 2023-10-25T16:53:59.259Z
DELETE FROM AD_Field WHERE AD_Field_ID=542781
;

-- 2023-10-25T16:53:59.379Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542783
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Table Index
-- Column: AD_Index_Table.AD_Index_Table_ID
-- 2023-10-25T16:53:59.382Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542783
;

-- 2023-10-25T16:53:59.383Z
DELETE FROM AD_Field WHERE AD_Field_ID=542783
;

-- 2023-10-25T16:53:59.488Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542775
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Mandant
-- Column: AD_Index_Table.AD_Client_ID
-- 2023-10-25T16:53:59.490Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542775
;

-- 2023-10-25T16:53:59.492Z
DELETE FROM AD_Field WHERE AD_Field_ID=542775
;

-- 2023-10-25T16:53:59.604Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542780
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> Organisation
-- Column: AD_Index_Table.AD_Org_ID
-- 2023-10-25T16:53:59.607Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542780
;

-- 2023-10-25T16:53:59.608Z
DELETE FROM AD_Field WHERE AD_Field_ID=542780
;

-- 2023-10-25T16:53:59.705Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542782
;

-- Field: Tabelle und Spalte(100,D) -> Index(540124,de.metas.tableindex) -> DB-Tabelle
-- Column: AD_Index_Table.AD_Table_ID
-- 2023-10-25T16:53:59.709Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542782
;

-- 2023-10-25T16:53:59.711Z
DELETE FROM AD_Field WHERE AD_Field_ID=542782
;

-- 2023-10-25T16:54:23.478Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542797
;

-- Field: Tabelle und Spalte(100,D) -> Index Translation(540126,de.metas.tableindex) -> Sprache
-- Column: AD_Index_Table_Trl.AD_Language
-- 2023-10-25T16:54:23.480Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542797
;

-- 2023-10-25T16:54:23.481Z
DELETE FROM AD_Field WHERE AD_Field_ID=542797
;

-- 2023-10-25T16:54:23.565Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542800
;

-- Field: Tabelle und Spalte(100,D) -> Index Translation(540126,de.metas.tableindex) -> Übersetzt
-- Column: AD_Index_Table_Trl.IsTranslated
-- 2023-10-25T16:54:23.567Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542800
;

-- 2023-10-25T16:54:23.569Z
DELETE FROM AD_Field WHERE AD_Field_ID=542800
;

-- 2023-10-25T16:54:23.650Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542796
;

-- Field: Tabelle und Spalte(100,D) -> Index Translation(540126,de.metas.tableindex) -> Fehlermeldung
-- Column: AD_Index_Table_Trl.ErrorMsg
-- 2023-10-25T16:54:23.653Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542796
;

-- 2023-10-25T16:54:23.655Z
DELETE FROM AD_Field WHERE AD_Field_ID=542796
;

-- 2023-10-25T16:54:23.745Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542794
;

-- Field: Tabelle und Spalte(100,D) -> Index Translation(540126,de.metas.tableindex) -> Aktiv
-- Column: AD_Index_Table_Trl.IsActive
-- 2023-10-25T16:54:23.746Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542794
;

-- 2023-10-25T16:54:23.747Z
DELETE FROM AD_Field WHERE AD_Field_ID=542794
;

-- 2023-10-25T16:54:23.827Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542795
;

-- Field: Tabelle und Spalte(100,D) -> Index Translation(540126,de.metas.tableindex) -> Mandant
-- Column: AD_Index_Table_Trl.AD_Client_ID
-- 2023-10-25T16:54:23.828Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542795
;

-- 2023-10-25T16:54:23.830Z
DELETE FROM AD_Field WHERE AD_Field_ID=542795
;

-- 2023-10-25T16:54:23.899Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542799
;

-- Field: Tabelle und Spalte(100,D) -> Index Translation(540126,de.metas.tableindex) -> Table Index
-- Column: AD_Index_Table_Trl.AD_Index_Table_ID
-- 2023-10-25T16:54:23.899Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542799
;

-- 2023-10-25T16:54:23.901Z
DELETE FROM AD_Field WHERE AD_Field_ID=542799
;

-- 2023-10-25T16:54:23.970Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542798
;

-- Field: Tabelle und Spalte(100,D) -> Index Translation(540126,de.metas.tableindex) -> Organisation
-- Column: AD_Index_Table_Trl.AD_Org_ID
-- 2023-10-25T16:54:23.972Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542798
;

-- 2023-10-25T16:54:23.973Z
DELETE FROM AD_Field WHERE AD_Field_ID=542798
;

-- 2023-10-25T16:54:33.010Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542792
;

-- Field: Tabelle und Spalte(100,D) -> Index Column(540125,de.metas.tableindex) -> Sequence
-- Column: AD_Index_Column.SeqNo
-- 2023-10-25T16:54:33.013Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542792
;

-- 2023-10-25T16:54:33.016Z
DELETE FROM AD_Field WHERE AD_Field_ID=542792
;

-- 2023-10-25T16:54:33.183Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542787
;

-- Field: Tabelle und Spalte(100,D) -> Index Column(540125,de.metas.tableindex) -> Spalte
-- Column: AD_Index_Column.AD_Column_ID
-- 2023-10-25T16:54:33.186Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542787
;

-- 2023-10-25T16:54:33.188Z
DELETE FROM AD_Field WHERE AD_Field_ID=542787
;

-- 2023-10-25T16:54:33.332Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542788
;

-- Field: Tabelle und Spalte(100,D) -> Index Column(540125,de.metas.tableindex) -> Column SQL
-- Column: AD_Index_Column.ColumnSQL
-- 2023-10-25T16:54:33.334Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542788
;

-- 2023-10-25T16:54:33.336Z
DELETE FROM AD_Field WHERE AD_Field_ID=542788
;

-- 2023-10-25T16:54:33.492Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542789
;

-- Field: Tabelle und Spalte(100,D) -> Index Column(540125,de.metas.tableindex) -> Entitäts-Art
-- Column: AD_Index_Column.EntityType
-- 2023-10-25T16:54:33.494Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542789
;

-- 2023-10-25T16:54:33.496Z
DELETE FROM AD_Field WHERE AD_Field_ID=542789
;

-- 2023-10-25T16:54:33.633Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542785
;

-- Field: Tabelle und Spalte(100,D) -> Index Column(540125,de.metas.tableindex) -> Aktiv
-- Column: AD_Index_Column.IsActive
-- 2023-10-25T16:54:33.634Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542785
;

-- 2023-10-25T16:54:33.636Z
DELETE FROM AD_Field WHERE AD_Field_ID=542785
;

-- 2023-10-25T16:54:33.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542786
;

-- Field: Tabelle und Spalte(100,D) -> Index Column(540125,de.metas.tableindex) -> Mandant
-- Column: AD_Index_Column.AD_Client_ID
-- 2023-10-25T16:54:33.755Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542786
;

-- 2023-10-25T16:54:33.757Z
DELETE FROM AD_Field WHERE AD_Field_ID=542786
;

-- 2023-10-25T16:54:33.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542793
;

-- Field: Tabelle und Spalte(100,D) -> Index Column(540125,de.metas.tableindex) -> Table Index
-- Column: AD_Index_Column.AD_Index_Table_ID
-- 2023-10-25T16:54:33.866Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542793
;

-- 2023-10-25T16:54:33.868Z
DELETE FROM AD_Field WHERE AD_Field_ID=542793
;

-- 2023-10-25T16:54:33.988Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542791
;

-- Field: Tabelle und Spalte(100,D) -> Index Column(540125,de.metas.tableindex) -> Organisation
-- Column: AD_Index_Column.AD_Org_ID
-- 2023-10-25T16:54:33.989Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542791
;

-- 2023-10-25T16:54:33.991Z
DELETE FROM AD_Field WHERE AD_Field_ID=542791
;

-- 2023-10-25T16:54:34.122Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=542790
;

-- Field: Tabelle und Spalte(100,D) -> Index Column(540125,de.metas.tableindex) -> Index Column
-- Column: AD_Index_Column.AD_Index_Column_ID
-- 2023-10-25T16:54:34.124Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=542790
;

-- 2023-10-25T16:54:34.126Z
DELETE FROM AD_Field WHERE AD_Field_ID=542790
;

-- Tab: Tabelle und Spalte(100,D) -> Index Column
-- Table: AD_Index_Column
-- 2023-10-25T16:54:43.235Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=540125
;

-- 2023-10-25T16:54:43.236Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=540125
;

-- Tab: Tabelle und Spalte(100,D) -> Index Translation
-- Table: AD_Index_Table_Trl
-- 2023-10-25T16:54:46.352Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=540126
;

-- 2023-10-25T16:54:46.353Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=540126
;

-- Tab: Tabelle und Spalte(100,D) -> Index
-- Table: AD_Index_Table
-- 2023-10-25T16:54:49.287Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=540124
;

-- 2023-10-25T16:54:49.287Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=540124
;

-- Run mode: SWING_CLIENT

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Table Index
-- Column: AD_Index_Table_Trl.AD_Index_Table_ID
-- 2023-10-26T09:48:12.613Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:12.613','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721646
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Sprache
-- Column: AD_Index_Table_Trl.AD_Language
-- 2023-10-26T09:48:14.091Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:14.091','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721647
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Mandant
-- Column: AD_Index_Table_Trl.AD_Client_ID
-- 2023-10-26T09:48:14.652Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:14.652','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721648
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Organisation
-- Column: AD_Index_Table_Trl.AD_Org_ID
-- 2023-10-26T09:48:15.075Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:15.075','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721649
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Aktiv
-- Column: AD_Index_Table_Trl.IsActive
-- 2023-10-26T09:48:15.442Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:15.442','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721650
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Fehlermeldung
-- Column: AD_Index_Table_Trl.ErrorMsg
-- 2023-10-26T09:48:15.978Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:15.978','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721651
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Übersetzt
-- Column: AD_Index_Table_Trl.IsTranslated
-- 2023-10-26T09:48:16.530Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:16.53','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721652
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Before Change Warning
-- Column: AD_Index_Table_Trl.BeforeChangeWarning
-- 2023-10-26T09:48:17.043Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:17.043','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721653
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Beschreibung
-- Column: AD_Index_Table_Trl.Description
-- 2023-10-26T09:48:20.903Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:20.903','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721654
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Table Index
-- Column: AD_Index_Table_Trl.AD_Index_Table_ID
-- 2023-10-26T09:48:21.331Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:21.331','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721646
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Sprache
-- Column: AD_Index_Table_Trl.AD_Language
-- 2023-10-26T09:48:21.866Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:21.866','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721647
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Mandant
-- Column: AD_Index_Table_Trl.AD_Client_ID
-- 2023-10-26T09:48:22.266Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:22.266','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721648
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Organisation
-- Column: AD_Index_Table_Trl.AD_Org_ID
-- 2023-10-26T09:48:22.771Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:22.771','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721649
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Aktiv
-- Column: AD_Index_Table_Trl.IsActive
-- 2023-10-26T09:48:23.267Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:23.266','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721650
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Fehlermeldung
-- Column: AD_Index_Table_Trl.ErrorMsg
-- 2023-10-26T09:48:23.762Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:23.762','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721651
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Übersetzt
-- Column: AD_Index_Table_Trl.IsTranslated
-- 2023-10-26T09:48:24.588Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:24.588','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721652
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Before Change Warning
-- Column: AD_Index_Table_Trl.BeforeChangeWarning
-- 2023-10-26T09:48:24.922Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:24.922','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721653
;

-- Field: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> Beschreibung
-- Column: AD_Index_Table_Trl.Description
-- 2023-10-26T09:48:27.123Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-10-26 12:48:27.123','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721654
;

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 10 -> main.Sprache
-- Column: AD_Index_Table_Trl.AD_Language
-- 2023-10-26T09:48:59.347Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-26 12:48:59.347','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621159
;

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 10 -> main.Fehlermeldung
-- Column: AD_Index_Table_Trl.ErrorMsg
-- 2023-10-26T09:48:59.356Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-26 12:48:59.356','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621160
;

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Index_Table_Trl.IsActive
-- 2023-10-26T09:48:59.363Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-26 12:48:59.363','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621161
;

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 20 -> flags.Übersetzt
-- Column: AD_Index_Table_Trl.IsTranslated
-- 2023-10-26T09:48:59.368Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-26 12:48:59.368','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621162
;

-- UI Element: Index Übersetzungen(541747,D) -> Index Übersetzungen(547267,D) -> main -> 20 -> org.Organisation
-- Column: AD_Index_Table_Trl.AD_Org_ID
-- 2023-10-26T09:48:59.374Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-26 12:48:59.373','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621163
;

