-- Run mode: SWING_CLIENT

-- 2026-03-31T14:12:24.641Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584741,0,TO_TIMESTAMP('2026-03-31 14:12:24.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Mobile Application Action Translation','Mobile Application Action Translation',TO_TIMESTAMP('2026-03-31 14:12:24.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:12:24.645Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584741 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-03-31T14:12:26.570Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-03-31 14:12:26.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584741
;

-- 2026-03-31T14:12:26.579Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584741,'de_DE')
;

-- Window: Mobile Application Action Translation, InternalName=null
-- 2026-03-31T14:12:45.145Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584741,0,542128,TO_TIMESTAMP('2026-03-31 14:12:44.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Mobile Application Action Translation','N',TO_TIMESTAMP('2026-03-31 14:12:44.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2026-03-31T14:12:45.150Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542128 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-03-31T14:12:45.154Z
/* DDL */  select update_window_translation_from_ad_element(584741)
;

-- 2026-03-31T14:12:45.180Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542128
;

-- 2026-03-31T14:12:45.183Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542128)
;

-- Window: Mobile Application Action Translation, InternalName=mobileAppActionTrl
-- 2026-03-31T14:12:59.391Z
UPDATE AD_Window SET InternalName='mobileAppActionTrl',Updated=TO_TIMESTAMP('2026-03-31 14:12:59.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542128
;

-- Tab: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation
-- Table: Mobile_Application_Action_Trl
-- 2026-03-31T14:13:19.427Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584741,0,549132,542589,542128,'Y',TO_TIMESTAMP('2026-03-31 14:13:19.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','Mobile_Application_Action_Trl','Y','N','Y','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','N',0,'Mobile Application Action Translation','N',10,0,TO_TIMESTAMP('2026-03-31 14:13:19.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:13:19.440Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549132 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-03-31T14:13:19.444Z
/* DDL */  select update_tab_translation_from_ad_element(584741)
;

-- 2026-03-31T14:13:19.448Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549132)
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Mandant
-- Column: Mobile_Application_Action_Trl.AD_Client_ID
-- 2026-03-31T14:13:25.450Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592330,776328,0,549132,TO_TIMESTAMP('2026-03-31 14:13:25.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2026-03-31 14:13:25.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:13:25.456Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=776328 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-31T14:13:25.461Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-03-31T14:13:26.139Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=776328
;

-- 2026-03-31T14:13:26.141Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(776328)
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Sektion
-- Column: Mobile_Application_Action_Trl.AD_Org_ID
-- 2026-03-31T14:13:26.277Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592331,776329,0,549132,TO_TIMESTAMP('2026-03-31 14:13:26.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2026-03-31 14:13:26.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:13:26.279Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=776329 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-31T14:13:26.282Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-03-31T14:13:26.672Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=776329
;

-- 2026-03-31T14:13:26.674Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(776329)
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Beschreibung
-- Column: Mobile_Application_Action_Trl.Description
-- 2026-03-31T14:13:26.804Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592334,776330,0,549132,TO_TIMESTAMP('2026-03-31 14:13:26.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2026-03-31 14:13:26.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:13:26.808Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=776330 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-31T14:13:26.812Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2026-03-31T14:13:26.965Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=776330
;

-- 2026-03-31T14:13:26.966Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(776330)
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Aktiv
-- Column: Mobile_Application_Action_Trl.IsActive
-- 2026-03-31T14:13:27.091Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592336,776331,0,549132,TO_TIMESTAMP('2026-03-31 14:13:26.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-03-31 14:13:26.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:13:27.094Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=776331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-31T14:13:27.098Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-03-31T14:13:27.422Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=776331
;

-- 2026-03-31T14:13:27.424Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(776331)
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Mobile Application
-- Column: Mobile_Application_Action_Trl.Mobile_Application_ID
-- 2026-03-31T14:13:27.548Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592338,776332,0,549132,TO_TIMESTAMP('2026-03-31 14:13:27.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Mobile Application',TO_TIMESTAMP('2026-03-31 14:13:27.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:13:27.552Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=776332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-31T14:13:27.554Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583308)
;

-- 2026-03-31T14:13:27.557Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=776332
;

-- 2026-03-31T14:13:27.558Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(776332)
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Name
-- Column: Mobile_Application_Action_Trl.Name
-- 2026-03-31T14:13:27.694Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592339,776333,0,549132,TO_TIMESTAMP('2026-03-31 14:13:27.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2026-03-31 14:13:27.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:13:27.697Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=776333 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-31T14:13:27.700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2026-03-31T14:13:27.815Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=776333
;

-- 2026-03-31T14:13:27.816Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(776333)
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Mobile Application Action
-- Column: Mobile_Application_Action_Trl.Mobile_Application_Action_ID
-- 2026-03-31T14:13:27.946Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592342,776334,0,549132,TO_TIMESTAMP('2026-03-31 14:13:27.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Mobile Application Action',TO_TIMESTAMP('2026-03-31 14:13:27.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:13:27.948Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=776334 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-31T14:13:27.949Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584031)
;

-- 2026-03-31T14:13:27.952Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=776334
;

-- 2026-03-31T14:13:27.952Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(776334)
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Sprache
-- Column: Mobile_Application_Action_Trl.AD_Language
-- 2026-03-31T14:13:28.083Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592343,776335,0,549132,TO_TIMESTAMP('2026-03-31 14:13:27.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sprache für diesen Eintrag',6,'D','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','N','N','N','N','N','Sprache',TO_TIMESTAMP('2026-03-31 14:13:27.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:13:28.086Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=776335 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-31T14:13:28.087Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(109)
;

-- 2026-03-31T14:13:28.115Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=776335
;

-- 2026-03-31T14:13:28.116Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(776335)
;

-- Tab: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D)
-- UI Section: main
-- 2026-03-31T14:13:43.661Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549132,547653,TO_TIMESTAMP('2026-03-31 14:13:42.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-03-31 14:13:42.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-03-31T14:13:43.675Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547653 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main
-- UI Column: 10
-- 2026-03-31T14:13:43.859Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549342,547653,TO_TIMESTAMP('2026-03-31 14:13:43.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-03-31 14:13:43.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main
-- UI Column: 20
-- 2026-03-31T14:13:43.984Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549343,547653,TO_TIMESTAMP('2026-03-31 14:13:43.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-03-31 14:13:43.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main -> 10
-- UI Element Group: default
-- 2026-03-31T14:13:44.177Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549342,555087,TO_TIMESTAMP('2026-03-31 14:13:44.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-03-31 14:13:44.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main -> 10 -> default.Mobile Application Action
-- Column: Mobile_Application_Action_Trl.Mobile_Application_Action_ID
-- 2026-03-31T14:14:14.027Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,776334,0,549132,555087,649430,'F',TO_TIMESTAMP('2026-03-31 14:14:13.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Mobile Application Action',10,0,0,TO_TIMESTAMP('2026-03-31 14:14:13.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main -> 10 -> default.Sprache
-- Column: Mobile_Application_Action_Trl.AD_Language
-- 2026-03-31T14:14:21.952Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,776335,0,549132,555087,649431,'F',TO_TIMESTAMP('2026-03-31 14:14:21.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','Y','N','N','Sprache',20,0,0,TO_TIMESTAMP('2026-03-31 14:14:21.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main -> 10
-- UI Element Group: trls
-- 2026-03-31T14:14:30.234Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549342,555088,TO_TIMESTAMP('2026-03-31 14:14:30.055000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','trls',20,TO_TIMESTAMP('2026-03-31 14:14:30.055000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main -> 10 -> trls.Name
-- Column: Mobile_Application_Action_Trl.Name
-- 2026-03-31T14:14:43.024Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,776333,0,549132,555088,649432,'F',TO_TIMESTAMP('2026-03-31 14:14:41.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2026-03-31 14:14:41.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main -> 10 -> trls.Beschreibung
-- Column: Mobile_Application_Action_Trl.Description
-- 2026-03-31T14:14:50.115Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,776330,0,549132,555088,649433,'F',TO_TIMESTAMP('2026-03-31 14:14:49.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2026-03-31 14:14:49.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main -> 10 -> default.Mobile Application Action
-- Column: Mobile_Application_Action_Trl.Mobile_Application_Action_ID
-- 2026-03-31T14:15:05.976Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-03-31 14:15:05.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=649430
;

-- UI Element: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main -> 10 -> default.Sprache
-- Column: Mobile_Application_Action_Trl.AD_Language
-- 2026-03-31T14:15:05.982Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-03-31 14:15:05.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=649431
;

-- UI Element: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main -> 10 -> trls.Name
-- Column: Mobile_Application_Action_Trl.Name
-- 2026-03-31T14:15:05.988Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-03-31 14:15:05.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=649432
;

-- UI Element: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> main -> 10 -> trls.Beschreibung
-- Column: Mobile_Application_Action_Trl.Description
-- 2026-03-31T14:15:05.994Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-03-31 14:15:05.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=649433
;

-- 2026-03-31T14:15:24.944Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=776332
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Mobile Application
-- Column: Mobile_Application_Action_Trl.Mobile_Application_ID
-- 2026-03-31T14:15:24.949Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=776332
;

-- 2026-03-31T14:15:24.953Z
DELETE FROM AD_Field WHERE AD_Field_ID=776332
;

-- 2026-03-31T14:15:24.956Z
/* DDL */ SELECT public.db_alter_table('Mobile_Application_Action_Trl','ALTER TABLE Mobile_Application_Action_Trl DROP COLUMN IF EXISTS Mobile_Application_ID')
;

-- Column: Mobile_Application_Action_Trl.Mobile_Application_ID
-- 2026-03-31T14:15:24.978Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=592338
;

-- 2026-03-31T14:15:24.984Z
DELETE FROM AD_Column WHERE AD_Column_ID=592338
;

-- Column: Mobile_Application_Action.Name
-- 2026-03-31T14:15:45.748Z
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-31 14:15:45.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592329
;

-- Column: Mobile_Application_Action.Description
-- 2026-03-31T14:15:54.140Z
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-31 14:15:54.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591112
;

-- Column: Mobile_Application_Action_Trl.Mobile_Application_Action_ID
-- 2026-03-31T14:16:14.813Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2026-03-31 14:16:14.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592342
;

-- Column: Mobile_Application_Action_Trl.AD_Language
-- 2026-03-31T14:16:27.116Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2026-03-31 14:16:27.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592343
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Mobile Application Action
-- Column: Mobile_Application_Action_Trl.Mobile_Application_Action_ID
-- 2026-03-31T14:16:55.297Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2026-03-31 14:16:55.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=776334
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Sprache
-- Column: Mobile_Application_Action_Trl.AD_Language
-- 2026-03-31T14:16:59.333Z
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2026-03-31 14:16:59.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=776335
;

-- Field: Mobile Application Action Translation(542128,D) -> Mobile Application Action Translation(549132,D) -> Name
-- Column: Mobile_Application_Action_Trl.Name
-- 2026-03-31T14:17:03.223Z
UPDATE AD_Field SET SortNo=3.000000000000,Updated=TO_TIMESTAMP('2026-03-31 14:17:03.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=776333
;

-- Name: Mobile Application Action Translation
-- Action Type: W
-- Window: Mobile Application Action Translation(542128,D)
-- 2026-03-31T14:18:04.646Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584741,542315,0,542128,TO_TIMESTAMP('2026-03-31 14:18:04.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','mobileAppActionTrl','Y','N','N','N','N','Mobile Application Action Translation',TO_TIMESTAMP('2026-03-31 14:18:04.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-31T14:18:04.651Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542315 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-03-31T14:18:04.655Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542315, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542315)
;

-- 2026-03-31T14:18:04.682Z
/* DDL */  select update_menu_translation_from_ad_element(584741)
;

-- Reordering children of `Mobile`
-- Node name: `Mobile Configuration (MobileConfiguration)`
-- 2026-03-31T14:18:05.267Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542136 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application (Mobile_Application)`
-- 2026-03-31T14:18:05.270Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542180 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application Role Access (Mobile_Application_Access)`
-- 2026-03-31T14:18:05.271Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542181 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application Action Access (Mobile_Application_Action_Access)`
-- 2026-03-31T14:18:05.272Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542251 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application Action Translation`
-- 2026-03-31T14:18:05.273Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542315 AND AD_Tree_ID=10
;

-- Reordering children of `Mobile`
-- Node name: `Mobile Configuration (MobileConfiguration)`
-- 2026-03-31T14:18:09.209Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542136 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application (Mobile_Application)`
-- 2026-03-31T14:18:09.210Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542180 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application Action Translation`
-- 2026-03-31T14:18:09.210Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542315 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application Role Access (Mobile_Application_Access)`
-- 2026-03-31T14:18:09.212Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542181 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application Action Access (Mobile_Application_Action_Access)`
-- 2026-03-31T14:18:09.212Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542251 AND AD_Tree_ID=10
;

