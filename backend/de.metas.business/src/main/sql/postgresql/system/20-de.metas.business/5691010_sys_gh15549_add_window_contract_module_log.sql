-- 2023-06-09T15:29:27.925890250Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582418,0,TO_TIMESTAMP('2023-06-09 16:29:27.574','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Vertragsbaustein Log','Vertragsbaustein Log',TO_TIMESTAMP('2023-06-09 16:29:27.574','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:29:27.945506828Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582418 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-06-09T15:29:50.621397599Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Contract Module Log', PrintName='Contract Module Log',Updated=TO_TIMESTAMP('2023-06-09 16:29:50.621','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582418 AND AD_Language='en_US'
;

-- 2023-06-09T15:29:50.642484165Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582418,'en_US') 
;

-- Element: null
-- 2023-06-09T15:30:02.136715934Z
UPDATE AD_Element_Trl SET Name='Contract Module Log', PrintName='Contract Module Log',Updated=TO_TIMESTAMP('2023-06-09 16:30:02.136','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582418 AND AD_Language='fr_CH'
;

-- 2023-06-09T15:30:02.141581773Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582418,'fr_CH') 
;

-- Window: Vertragsbaustein Log, InternalName=null
-- 2023-06-09T15:30:23.753473581Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582418,0,541711,TO_TIMESTAMP('2023-06-09 16:30:23.461','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','N','N','N','Y','Vertragsbaustein Log','N',TO_TIMESTAMP('2023-06-09 16:30:23.461','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-06-09T15:30:23.757342084Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541711 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-06-09T15:30:23.762706932Z
/* DDL */  select update_window_translation_from_ad_element(582418) 
;

-- 2023-06-09T15:30:23.772207851Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541711
;

-- 2023-06-09T15:30:23.773218099Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541711)
;

-- Window: Vertragsbaustein Log, InternalName=null
-- 2023-06-09T15:30:25.848146352Z
UPDATE AD_Window SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-06-09 16:30:25.845','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541711
;

-- Tab: Vertragsbaustein Log(541711,D) -> Contract Module Log
-- Table: Contract_Module_Log
-- 2023-06-09T15:30:56.847828961Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582413,0,547012,542338,541711,'Y',TO_TIMESTAMP('2023-06-09 16:30:56.503','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','Contract_Module_Log','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Contract Module Log','N',10,0,TO_TIMESTAMP('2023-06-09 16:30:56.503','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:30:56.851706141Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547012 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-06-09T15:30:56.855136410Z
/* DDL */  select update_tab_translation_from_ad_element(582413) 
;

-- 2023-06-09T15:30:56.862350120Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547012)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Contract Module Log
-- Column: Contract_Module_Log.Contract_Module_Log_ID
-- 2023-06-09T15:31:19.673090528Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586758,716315,0,547012,TO_TIMESTAMP('2023-06-09 16:31:19.404','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Contract Module Log',TO_TIMESTAMP('2023-06-09 16:31:19.404','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:19.676625194Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716315 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:19.680005431Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582413) 
;

-- 2023-06-09T15:31:19.691644463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716315
;

-- 2023-06-09T15:31:19.693072520Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716315)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Mandant
-- Column: Contract_Module_Log.AD_Client_ID
-- 2023-06-09T15:31:19.907797229Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586759,716316,0,547012,TO_TIMESTAMP('2023-06-09 16:31:19.712','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-06-09 16:31:19.712','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:19.910833124Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:19.913825558Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-06-09T15:31:20.039273967Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716316
;

-- 2023-06-09T15:31:20.039711101Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716316)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Organisation
-- Column: Contract_Module_Log.AD_Org_ID
-- 2023-06-09T15:31:20.229965341Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586760,716317,0,547012,TO_TIMESTAMP('2023-06-09 16:31:20.041','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-06-09 16:31:20.041','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:20.233146753Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716317 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:20.236110025Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-06-09T15:31:20.350448669Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716317
;

-- 2023-06-09T15:31:20.350967430Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716317)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Erstellt
-- Column: Contract_Module_Log.Created
-- 2023-06-09T15:31:20.531712268Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586761,716318,0,547012,TO_TIMESTAMP('2023-06-09 16:31:20.352','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2023-06-09 16:31:20.352','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:20.534642805Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:20.537600218Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2023-06-09T15:31:20.572521442Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716318
;

-- 2023-06-09T15:31:20.572977418Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716318)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Erstellt durch
-- Column: Contract_Module_Log.CreatedBy
-- 2023-06-09T15:31:20.756593166Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586762,716319,0,547012,TO_TIMESTAMP('2023-06-09 16:31:20.574','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2023-06-09 16:31:20.574','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:20.759548996Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:20.762464459Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2023-06-09T15:31:20.798905994Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716319
;

-- 2023-06-09T15:31:20.799388300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716319)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Aktiv
-- Column: Contract_Module_Log.IsActive
-- 2023-06-09T15:31:20.971820016Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586763,716320,0,547012,TO_TIMESTAMP('2023-06-09 16:31:20.801','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-06-09 16:31:20.801','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:20.974735086Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:20.979172354Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-06-09T15:31:21.075209Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716320
;

-- 2023-06-09T15:31:21.075655593Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716320)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Aktualisiert
-- Column: Contract_Module_Log.Updated
-- 2023-06-09T15:31:21.255780064Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586764,716321,0,547012,TO_TIMESTAMP('2023-06-09 16:31:21.078','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2023-06-09 16:31:21.078','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:21.257329231Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716321 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:21.258999160Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2023-06-09T15:31:21.312465095Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716321
;

-- 2023-06-09T15:31:21.312955328Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716321)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Aktualisiert durch
-- Column: Contract_Module_Log.UpdatedBy
-- 2023-06-09T15:31:21.493123937Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586765,716322,0,547012,TO_TIMESTAMP('2023-06-09 16:31:21.314','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2023-06-09 16:31:21.314','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:21.496081950Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716322 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:21.499008975Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2023-06-09T15:31:21.552089949Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716322
;

-- 2023-06-09T15:31:21.552553872Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716322)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Produkt
-- Column: Contract_Module_Log.M_Product_ID
-- 2023-06-09T15:31:21.717946531Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586766,716323,0,547012,TO_TIMESTAMP('2023-06-09 16:31:21.554','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','N','Produkt',TO_TIMESTAMP('2023-06-09 16:31:21.554','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:21.720792364Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:21.723749539Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-06-09T15:31:21.778206625Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716323
;

-- 2023-06-09T15:31:21.778659883Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716323)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Verkaufstransaktion
-- Column: Contract_Module_Log.IsSOTrx
-- 2023-06-09T15:31:21.943886357Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586767,716324,0,547012,TO_TIMESTAMP('2023-06-09 16:31:21.78','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion',1,'D','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','Y','N','N','N','N','N','Verkaufstransaktion',TO_TIMESTAMP('2023-06-09 16:31:21.78','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:21.946771856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716324 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:21.949798218Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106) 
;

-- 2023-06-09T15:31:21.963086246Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716324
;

-- 2023-06-09T15:31:21.964296367Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716324)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T15:31:22.153727517Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586768,716325,0,547012,TO_TIMESTAMP('2023-06-09 16:31:21.968','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-06-09 16:31:21.968','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:22.156505676Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716325 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:22.159434824Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-06-09T15:31:22.196950372Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716325
;

-- 2023-06-09T15:31:22.197484997Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716325)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> DB-Tabelle
-- Column: Contract_Module_Log.AD_Table_ID
-- 2023-06-09T15:31:22.363700311Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586769,716326,0,547012,TO_TIMESTAMP('2023-06-09 16:31:22.199','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information',10,'D','The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2023-06-09 16:31:22.199','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:22.366536511Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716326 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:22.369473724Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2023-06-09T15:31:22.399483042Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716326
;

-- 2023-06-09T15:31:22.400177956Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716326)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Lager
-- Column: Contract_Module_Log.M_Warehouse_ID
-- 2023-06-09T15:31:22.569290393Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586770,716327,0,547012,TO_TIMESTAMP('2023-06-09 16:31:22.403','YYYY-MM-DD HH24:MI:SS.US'),100,'Lager oder Ort für Dienstleistung',10,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Y','N','N','N','N','N','Lager',TO_TIMESTAMP('2023-06-09 16:31:22.403','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:22.572129108Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716327 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:22.575039337Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2023-06-09T15:31:22.597450776Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716327
;

-- 2023-06-09T15:31:22.598556652Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716327)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Contract Module Type
-- Column: Contract_Module_Log.Contract_Module_Type_ID
-- 2023-06-09T15:31:22.769390822Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586771,716328,0,547012,TO_TIMESTAMP('2023-06-09 16:31:22.602','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','Y','N','N','N','N','N','Contract Module Type',TO_TIMESTAMP('2023-06-09 16:31:22.602','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:22.772002337Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716328 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:22.775006935Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582395) 
;

-- 2023-06-09T15:31:22.783661227Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716328
;

-- 2023-06-09T15:31:22.784705547Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716328)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Vorgangsdatum
-- Column: Contract_Module_Log.DateTrx
-- 2023-06-09T15:31:22.963578904Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586772,716329,0,547012,TO_TIMESTAMP('2023-06-09 16:31:22.789','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum',7,'D','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','Y','N','N','N','N','N','Vorgangsdatum',TO_TIMESTAMP('2023-06-09 16:31:22.789','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:22.966226916Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716329 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:22.969107082Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1297) 
;

-- 2023-06-09T15:31:22.980630933Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716329
;

-- 2023-06-09T15:31:22.981679498Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716329)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Datensatz-ID
-- Column: Contract_Module_Log.Record_ID
-- 2023-06-09T15:31:23.153809130Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586774,716330,0,547012,TO_TIMESTAMP('2023-06-09 16:31:22.986','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID',22,'D','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','Y','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2023-06-09 16:31:22.986','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:23.156430916Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716330 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:23.159550824Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2023-06-09T15:31:23.168557019Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716330
;

-- 2023-06-09T15:31:23.169018672Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716330)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Beschreibung
-- Column: Contract_Module_Log.Description
-- 2023-06-09T15:31:23.338966200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586775,716331,0,547012,TO_TIMESTAMP('2023-06-09 16:31:23.171','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','Y','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2023-06-09 16:31:23.171','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:23.341440625Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:23.344280420Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-06-09T15:31:23.404872100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716331
;

-- 2023-06-09T15:31:23.405245154Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716331)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Sammelstelle
-- Column: Contract_Module_Log.CollectionPoint_BPartner_ID
-- 2023-06-09T15:31:23.584922955Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586776,716332,0,547012,TO_TIMESTAMP('2023-06-09 16:31:23.406','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','Y','N','N','N','N','N','Sammelstelle',TO_TIMESTAMP('2023-06-09 16:31:23.406','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:23.587436376Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:23.590387085Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582414) 
;

-- 2023-06-09T15:31:23.598656394Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716332
;

-- 2023-06-09T15:31:23.599722641Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716332)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Produzent
-- Column: Contract_Module_Log.Producer_BPartner_ID
-- 2023-06-09T15:31:23.789086606Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586777,716333,0,547012,TO_TIMESTAMP('2023-06-09 16:31:23.604','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','Y','N','N','N','N','N','Produzent',TO_TIMESTAMP('2023-06-09 16:31:23.604','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:31:23.791571969Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716333 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:31:23.794492654Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582415) 
;

-- 2023-06-09T15:31:23.802759708Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716333
;

-- 2023-06-09T15:31:23.803864044Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716333)
;

-- Tab: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D)
-- UI Section: main
-- 2023-06-09T15:31:49.086979350Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547012,545619,TO_TIMESTAMP('2023-06-09 16:31:48.745','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-06-09 16:31:48.745','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-06-09T15:31:49.089305491Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545619 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- Tab: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D)
-- UI Section: advanced
-- 2023-06-09T15:31:54.823443609Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547012,545620,TO_TIMESTAMP('2023-06-09 16:31:54.546','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-06-09 16:31:54.546','YYYY-MM-DD HH24:MI:SS.US'),100,'advanced')
;

-- 2023-06-09T15:31:54.825868445Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545620 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main
-- UI Column: 10
-- 2023-06-09T15:32:02.855011Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546863,545619,TO_TIMESTAMP('2023-06-09 16:32:02.621','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-06-09 16:32:02.621','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main
-- UI Column: 20
-- 2023-06-09T15:32:05.437160961Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546864,545619,TO_TIMESTAMP('2023-06-09 16:32:05.274','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-06-09 16:32:05.274','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10
-- UI Element Group: primary
-- 2023-06-09T15:32:28.792428040Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546863,550775,TO_TIMESTAMP('2023-06-09 16:32:28.501','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2023-06-09 16:32:28.501','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10
-- UI Element Group: description
-- 2023-06-09T15:32:41.940218912Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546863,550776,TO_TIMESTAMP('2023-06-09 16:32:41.691','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','description',20,TO_TIMESTAMP('2023-06-09 16:32:41.691','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10
-- UI Element Group: details
-- 2023-06-09T15:32:50.177658466Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546863,550777,TO_TIMESTAMP('2023-06-09 16:32:49.924','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','details',30,TO_TIMESTAMP('2023-06-09 16:32:49.924','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20
-- UI Element Group: flags
-- 2023-06-09T15:32:59.773375703Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546864,550778,TO_TIMESTAMP('2023-06-09 16:32:59.511','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2023-06-09 16:32:59.511','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> primary.Sammelstelle
-- Column: Contract_Module_Log.CollectionPoint_BPartner_ID
-- 2023-06-09T15:33:58.759436066Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716332,0,547012,550775,617966,'F',TO_TIMESTAMP('2023-06-09 16:33:58.449','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Sammelstelle',10,0,0,TO_TIMESTAMP('2023-06-09 16:33:58.449','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> primary.Produzent
-- Column: Contract_Module_Log.Producer_BPartner_ID
-- 2023-06-09T15:34:08.412323004Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716333,0,547012,550775,617967,'F',TO_TIMESTAMP('2023-06-09 16:34:08.149','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Produzent',20,0,0,TO_TIMESTAMP('2023-06-09 16:34:08.149','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> primary.Produkt
-- Column: Contract_Module_Log.M_Product_ID
-- 2023-06-09T15:34:40.477634954Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716323,0,547012,550775,617968,'F',TO_TIMESTAMP('2023-06-09 16:34:40.198','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',30,0,0,TO_TIMESTAMP('2023-06-09 16:34:40.198','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> description.Beschreibung
-- Column: Contract_Module_Log.Description
-- 2023-06-09T15:35:06.687494186Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716331,0,547012,550776,617969,'F',TO_TIMESTAMP('2023-06-09 16:35:06.445','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2023-06-09 16:35:06.445','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Aktiv
-- Column: Contract_Module_Log.IsActive
-- 2023-06-09T15:36:01.763745589Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716320,0,547012,550778,617970,'F',TO_TIMESTAMP('2023-06-09 16:36:01.108','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-06-09 16:36:01.108','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T15:36:32.345795402Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716325,0,547012,550778,617971,'F',TO_TIMESTAMP('2023-06-09 16:36:32.061','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',20,0,0,TO_TIMESTAMP('2023-06-09 16:36:32.061','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20
-- UI Element Group: dates
-- 2023-06-09T15:36:44.643368399Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546864,550779,TO_TIMESTAMP('2023-06-09 16:36:44.418','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','dates',20,TO_TIMESTAMP('2023-06-09 16:36:44.418','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> dates.Vorgangsdatum
-- Column: Contract_Module_Log.DateTrx
-- 2023-06-09T15:37:30.389365469Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716329,0,547012,550779,617972,'F',TO_TIMESTAMP('2023-06-09 16:37:30.109','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','Y','N','N','N',0,'Vorgangsdatum',10,0,0,TO_TIMESTAMP('2023-06-09 16:37:30.109','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20
-- UI Element Group: org
-- 2023-06-09T15:38:20.671277533Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546864,550780,TO_TIMESTAMP('2023-06-09 16:38:20.369','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',30,TO_TIMESTAMP('2023-06-09 16:38:20.369','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> org.Organisation
-- Column: Contract_Module_Log.AD_Org_ID
-- 2023-06-09T15:38:28.559442512Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716317,0,547012,550780,617973,'F',TO_TIMESTAMP('2023-06-09 16:38:28.322','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-06-09 16:38:28.322','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> org.Mandant
-- Column: Contract_Module_Log.AD_Client_ID
-- 2023-06-09T15:38:35.388129292Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716316,0,547012,550780,617974,'F',TO_TIMESTAMP('2023-06-09 16:38:35.147','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-06-09 16:38:35.147','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: Vertragsbaustein Log
-- Action Type: W
-- Window: Vertragsbaustein Log(541711,D)
-- 2023-06-09T15:40:09.365568517Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582418,542087,0,541711,TO_TIMESTAMP('2023-06-09 16:40:09.046','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Vertragsbaustein Log','Y','N','N','N','N','Vertragsbaustein Log',TO_TIMESTAMP('2023-06-09 16:40:09.046','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:40:09.368524212Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542087 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-06-09T15:40:09.371798889Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542087, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542087)
;

-- 2023-06-09T15:40:09.381570541Z
/* DDL */  select update_menu_translation_from_ad_element(582418) 
;

-- Reordering children of `Contract Management`
-- Node name: `Contractpartner (C_Flatrate_Data)`
-- 2023-06-09T15:40:17.452565906Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Term)`
-- 2023-06-09T15:40:17.453480273Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- Node name: `Contract Invoicecandidates (C_Invoice_Clearing_Alloc)`
-- 2023-06-09T15:40:17.454003971Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- Node name: `Subscription History (C_SubscriptionProgress)`
-- 2023-06-09T15:40:17.454431952Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- Node name: `Contract Terms (C_Flatrate_Conditions)`
-- 2023-06-09T15:40:17.454797295Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Transition)`
-- 2023-06-09T15:40:17.455208636Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- Node name: `Contract Module Type (Contract_Module_Type)`
-- 2023-06-09T15:40:17.455670796Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542086 AND AD_Tree_ID=10
;

-- Node name: `Subscriptions import (I_Flatrate_Term)`
-- 2023-06-09T15:40:17.456131728Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- Node name: `Membership Month (C_MembershipMonth)`
-- 2023-06-09T15:40:17.456544663Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- Node name: `Abo-Rabatt (C_SubscrDiscount)`
-- 2023-06-09T15:40:17.456958157Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541766 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Flatrate Term (C_InterimInvoice_FlatrateTerm)`
-- 2023-06-09T15:40:17.457309314Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541979 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Settings (C_Interim_Invoice_Settings)`
-- 2023-06-09T15:40:17.457799058Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541974 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-06-09T15:40:17.458158985Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-06-09T15:40:17.458498300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- Node name: `Type specific settings`
-- 2023-06-09T15:40:17.458835761Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- Node name: `Create/Update Customer Retentions (de.metas.contracts.process.C_Customer_Retention_CreateUpdate)`
-- 2023-06-09T15:40:17.459173566Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- Node name: `Call-off order overview (C_CallOrderSummary)`
-- 2023-06-09T15:40:17.468146621Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541909 AND AD_Tree_ID=10
;

-- Node name: `Vertragsbaustein Log`
-- 2023-06-09T15:40:17.468566276Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542087 AND AD_Tree_ID=10
;

-- Reordering children of `Contract Management`
-- Node name: `Contractpartner (C_Flatrate_Data)`
-- 2023-06-09T15:40:30.723189195Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Term)`
-- 2023-06-09T15:40:30.723987275Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- Node name: `Contract Invoicecandidates (C_Invoice_Clearing_Alloc)`
-- 2023-06-09T15:40:30.724684070Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- Node name: `Subscription History (C_SubscriptionProgress)`
-- 2023-06-09T15:40:30.725361032Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- Node name: `Contract Terms (C_Flatrate_Conditions)`
-- 2023-06-09T15:40:30.726028894Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Transition)`
-- 2023-06-09T15:40:30.726621521Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- Node name: `Contract Module Type (Contract_Module_Type)`
-- 2023-06-09T15:40:30.727253914Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542086 AND AD_Tree_ID=10
;

-- Node name: `Vertragsbaustein Log`
-- 2023-06-09T15:40:30.727947415Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542087 AND AD_Tree_ID=10
;

-- Node name: `Subscriptions import (I_Flatrate_Term)`
-- 2023-06-09T15:40:30.728631270Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- Node name: `Membership Month (C_MembershipMonth)`
-- 2023-06-09T15:40:30.729207765Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- Node name: `Abo-Rabatt (C_SubscrDiscount)`
-- 2023-06-09T15:40:30.729569016Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541766 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Flatrate Term (C_InterimInvoice_FlatrateTerm)`
-- 2023-06-09T15:40:30.729910902Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541979 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Settings (C_Interim_Invoice_Settings)`
-- 2023-06-09T15:40:30.730344851Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541974 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-06-09T15:40:30.730678524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-06-09T15:40:30.731051754Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- Node name: `Type specific settings`
-- 2023-06-09T15:40:30.731468152Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- Node name: `Create/Update Customer Retentions (de.metas.contracts.process.C_Customer_Retention_CreateUpdate)`
-- 2023-06-09T15:40:30.731870340Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- Node name: `Call-off order overview (C_CallOrderSummary)`
-- 2023-06-09T15:40:30.732282010Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541909 AND AD_Tree_ID=10
;

-- Element: null
-- 2023-06-09T15:40:52.165257469Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Vertragsbaustein Log',Updated=TO_TIMESTAMP('2023-06-09 16:40:52.164','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582418 AND AD_Language='de_CH'
;

-- 2023-06-09T15:40:52.169650024Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582418,'de_CH') 
;

-- Element: null
-- 2023-06-09T15:40:58.524556877Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Vertragsbaustein Log',Updated=TO_TIMESTAMP('2023-06-09 16:40:58.524','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582418 AND AD_Language='de_DE'
;

-- 2023-06-09T15:40:58.526715007Z
UPDATE AD_Element SET WEBUI_NameBrowse='Vertragsbaustein Log' WHERE AD_Element_ID=582418
;

-- 2023-06-09T15:40:58.897826336Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582418,'de_DE') 
;

-- 2023-06-09T15:40:58.898547306Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582418,'de_DE') 
;

-- Element: null
-- 2023-06-09T15:41:04.749599469Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Contract Module Log',Updated=TO_TIMESTAMP('2023-06-09 16:41:04.749','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582418 AND AD_Language='en_US'
;

-- 2023-06-09T15:41:04.753883729Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582418,'en_US') 
;

-- Element: null
-- 2023-06-09T15:41:12.357671372Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Contract Module Log',Updated=TO_TIMESTAMP('2023-06-09 16:41:12.357','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582418 AND AD_Language='fr_CH'
;

-- 2023-06-09T15:41:12.360188724Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582418,'fr_CH') 
;

-- 2023-06-09T15:45:00.389031926Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN C_Invoice_Candidate_ID NUMERIC(10)')
;

-- 2023-06-09T15:45:00.396462063Z
ALTER TABLE Contract_Module_Log ADD CONSTRAINT CInvoiceCandidate_ContractModuleLog FOREIGN KEY (C_Invoice_Candidate_ID) REFERENCES public.C_Invoice_Candidate DEFERRABLE INITIALLY DEFERRED
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> primary.Sammelstelle
-- Column: Contract_Module_Log.CollectionPoint_BPartner_ID
-- 2023-06-09T15:46:51.576594800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-06-09 16:46:51.576','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617966
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> primary.Produzent
-- Column: Contract_Module_Log.Producer_BPartner_ID
-- 2023-06-09T15:46:51.590796762Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-06-09 16:46:51.59','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617967
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> primary.Produkt
-- Column: Contract_Module_Log.M_Product_ID
-- 2023-06-09T15:46:51.603418139Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-06-09 16:46:51.603','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617968
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> dates.Vorgangsdatum
-- Column: Contract_Module_Log.DateTrx
-- 2023-06-09T15:46:51.611369670Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-06-09 16:46:51.611','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617972
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> description.Beschreibung
-- Column: Contract_Module_Log.Description
-- 2023-06-09T15:46:51.615296708Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-06-09 16:46:51.615','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617969
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Aktiv
-- Column: Contract_Module_Log.IsActive
-- 2023-06-09T15:46:51.618949936Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-06-09 16:46:51.618','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617970
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T15:46:51.622397622Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-06-09 16:46:51.622','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617971
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> org.Organisation
-- Column: Contract_Module_Log.AD_Org_ID
-- 2023-06-09T15:46:51.624583784Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-06-09 16:46:51.624','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617973
;

-- Table: Contract_Module_Log
-- 2023-06-09T15:47:47.004862976Z
UPDATE AD_Table SET AD_Window_ID=541711,Updated=TO_TIMESTAMP('2023-06-09 16:47:47.002','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542338
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T15:48:38.696267488Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716325,0,547012,550778,617975,'F',TO_TIMESTAMP('2023-06-09 16:48:38.38','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',30,0,0,TO_TIMESTAMP('2023-06-09 16:48:38.38','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T15:49:02.955587237Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-06-09 16:49:02.955','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617975
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T15:49:02.964125495Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-06-09 16:49:02.963','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617971
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> org.Organisation
-- Column: Contract_Module_Log.AD_Org_ID
-- 2023-06-09T15:49:02.971627197Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-06-09 16:49:02.971','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617973
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> primary.Lager
-- Column: Contract_Module_Log.M_Warehouse_ID
-- 2023-06-09T15:50:07.729493996Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716327,0,547012,550775,617976,'F',TO_TIMESTAMP('2023-06-09 16:50:07.423','YYYY-MM-DD HH24:MI:SS.US'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','N','N',0,'Lager',40,0,0,TO_TIMESTAMP('2023-06-09 16:50:07.423','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> primary.Lager
-- Column: Contract_Module_Log.M_Warehouse_ID
-- 2023-06-09T15:51:27.938403189Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-06-09 16:51:27.938','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617976
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> dates.Vorgangsdatum
-- Column: Contract_Module_Log.DateTrx
-- 2023-06-09T15:51:27.951983395Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-06-09 16:51:27.951','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617972
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> description.Beschreibung
-- Column: Contract_Module_Log.Description
-- 2023-06-09T15:51:27.959978159Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-06-09 16:51:27.959','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617969
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Aktiv
-- Column: Contract_Module_Log.IsActive
-- 2023-06-09T15:51:27.963535025Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-06-09 16:51:27.963','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617970
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T15:51:27.967393924Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-06-09 16:51:27.967','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617975
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T15:51:27.970605656Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-06-09 16:51:27.97','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617971
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> org.Organisation
-- Column: Contract_Module_Log.AD_Org_ID
-- 2023-06-09T15:51:27.973891650Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-06-09 16:51:27.973','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617973
;

-- UI Column: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20
-- UI Element Group: org
-- 2023-06-09T15:52:12.978742869Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2023-06-09 16:52:12.978','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550780
;

-- UI Column: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20
-- UI Element Group: records
-- 2023-06-09T15:52:26.093232608Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546864,550781,TO_TIMESTAMP('2023-06-09 16:52:25.808','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','records',30,TO_TIMESTAMP('2023-06-09 16:52:25.808','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> records.DB-Tabelle
-- Column: Contract_Module_Log.AD_Table_ID
-- 2023-06-09T15:52:46.064522175Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716326,0,547012,550781,617977,'F',TO_TIMESTAMP('2023-06-09 16:52:45.841','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','N','N',0,'DB-Tabelle',10,0,0,TO_TIMESTAMP('2023-06-09 16:52:45.841','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> records.Datensatz-ID
-- Column: Contract_Module_Log.Record_ID
-- 2023-06-09T15:52:54.033201485Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716330,0,547012,550781,617978,'F',TO_TIMESTAMP('2023-06-09 16:52:53.808','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','N','N',0,'Datensatz-ID',20,0,0,TO_TIMESTAMP('2023-06-09 16:52:53.808','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: Contract_Module_Log.C_Invoice_Candidate_ID
-- 2023-06-09T15:55:12.215638705Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-06-09 16:55:12.215','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586773
;

-- Column: Contract_Module_Log.C_Invoice_Candidate_ID
-- 2023-06-09T15:56:20.457666476Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2023-06-09 16:56:20.457','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586773
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Rechnungskandidat
-- Column: Contract_Module_Log.C_Invoice_Candidate_ID
-- 2023-06-09T15:56:55.065659464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586773,716334,0,547012,TO_TIMESTAMP('2023-06-09 16:56:54.773','YYYY-MM-DD HH24:MI:SS.US'),100,'',10,'D','Y','Y','N','N','N','N','N','Rechnungskandidat',TO_TIMESTAMP('2023-06-09 16:56:54.773','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:56:55.068667286Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716334 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:56:55.071513278Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541266) 
;

-- 2023-06-09T15:56:55.085187430Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716334
;

-- 2023-06-09T15:56:55.087264081Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716334)
;

-- Column: Contract_Module_Log.C_Flatrate_Term_ID
-- 2023-06-09T15:57:27.363914456Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2023-06-09 16:57:27.363','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586778
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Pauschale - Vertragsperiode
-- Column: Contract_Module_Log.C_Flatrate_Term_ID
-- 2023-06-09T15:57:43.104060647Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586778,716335,0,547012,TO_TIMESTAMP('2023-06-09 16:57:42.822','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','Y','N','N','N','N','N','Pauschale - Vertragsperiode',TO_TIMESTAMP('2023-06-09 16:57:42.822','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T15:57:43.106358291Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716335 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-09T15:57:43.108425678Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447) 
;

-- 2023-06-09T15:57:43.114651803Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716335
;

-- 2023-06-09T15:57:43.115008212Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716335)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T15:58:09.866023546Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617975
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T15:58:32.112566350Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-06-09 16:58:32.112','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617971
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> records.DB-Tabelle
-- Column: Contract_Module_Log.AD_Table_ID
-- 2023-06-09T15:58:32.120962920Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-06-09 16:58:32.12','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617977
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> records.Datensatz-ID
-- Column: Contract_Module_Log.Record_ID
-- 2023-06-09T15:58:32.129301940Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-06-09 16:58:32.129','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617978
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> org.Organisation
-- Column: Contract_Module_Log.AD_Org_ID
-- 2023-06-09T15:58:32.137835658Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-06-09 16:58:32.137','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617973
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Pauschale - Vertragsperiode
-- Column: Contract_Module_Log.C_Flatrate_Term_ID
-- 2023-06-09T15:59:15.004611718Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716335,0,547012,550777,617979,'F',TO_TIMESTAMP('2023-06-09 16:59:14.7','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Pauschale - Vertragsperiode',10,0,0,TO_TIMESTAMP('2023-06-09 16:59:14.7','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Rechnungskandidat
-- Column: Contract_Module_Log.C_Invoice_Candidate_ID
-- 2023-06-09T15:59:29.033714171Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716334,0,547012,550777,617980,'F',TO_TIMESTAMP('2023-06-09 16:59:28.819','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungskandidat',20,0,0,TO_TIMESTAMP('2023-06-09 16:59:28.819','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verkaufstransaktion
-- Column: Contract_Module_Log.IsSOTrx
-- 2023-06-09T16:00:39.129529911Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716324,0,547012,550778,617981,'F',TO_TIMESTAMP('2023-06-09 17:00:38.835','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','Y','N','N','N',0,'Verkaufstransaktion',30,0,0,TO_TIMESTAMP('2023-06-09 17:00:38.835','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Contract Module Type
-- Column: Contract_Module_Log.Contract_Module_Type_ID
-- 2023-06-09T16:03:37.827726992Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716328,0,547012,550777,617982,'F',TO_TIMESTAMP('2023-06-09 17:03:37.517','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Contract Module Type',15,0,0,TO_TIMESTAMP('2023-06-09 17:03:37.517','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Pauschale - Vertragsperiode
-- Column: Contract_Module_Log.C_Flatrate_Term_ID
-- 2023-06-09T16:04:22.505125340Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-06-09 17:04:22.504','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617979
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Contract Module Type
-- Column: Contract_Module_Log.Contract_Module_Type_ID
-- 2023-06-09T16:04:22.518607014Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-06-09 17:04:22.518','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617982
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Rechnungskandidat
-- Column: Contract_Module_Log.C_Invoice_Candidate_ID
-- 2023-06-09T16:04:22.527576159Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-06-09 17:04:22.527','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617980
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Aktiv
-- Column: Contract_Module_Log.IsActive
-- 2023-06-09T16:04:22.536114742Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-06-09 17:04:22.535','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617970
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verkaufstransaktion
-- Column: Contract_Module_Log.IsSOTrx
-- 2023-06-09T16:04:22.544403996Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-06-09 17:04:22.544','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617981
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> flags.Verarbeitet
-- Column: Contract_Module_Log.Processed
-- 2023-06-09T16:04:22.549816673Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-06-09 17:04:22.549','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617971
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> records.DB-Tabelle
-- Column: Contract_Module_Log.AD_Table_ID
-- 2023-06-09T16:04:22.553143870Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-06-09 17:04:22.553','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617977
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> records.Datensatz-ID
-- Column: Contract_Module_Log.Record_ID
-- 2023-06-09T16:04:22.555705975Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-06-09 17:04:22.555','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617978
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> org.Organisation
-- Column: Contract_Module_Log.AD_Org_ID
-- 2023-06-09T16:04:22.557899779Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-06-09 17:04:22.557','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617973
;

-- Column: Contract_Module_Log.Contract_Module_Type_ID
-- 2023-06-09T16:05:43.989454436Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-06-09 17:05:43.989','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586771
;

-- Name: Conract Module Type
-- 2023-06-09T16:06:18.521665619Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,Help,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541741,TO_TIMESTAMP('2023-06-09 17:06:18.261','YYYY-MM-DD HH24:MI:SS.US'),100,'D','','Y','N','Conract Module Type',TO_TIMESTAMP('2023-06-09 17:06:18.261','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-06-09T16:06:18.524856085Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541741 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Name: Conract Module Type
-- 2023-06-09T16:07:52.958815321Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541741
;

-- 2023-06-09T16:07:52.961658252Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541741
;

-- Element: Contract_Module_Type_ID
-- 2023-06-09T17:38:24.865560836Z
UPDATE AD_Element_Trl SET Name='Vertragsbaustein Typ', PrintName='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-09 18:38:24.865','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582395 AND AD_Language='de_CH'
;

-- 2023-06-09T17:38:24.883885614Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582395,'de_CH')
;

-- Element: Contract_Module_Type_ID
-- 2023-06-09T17:38:34.351057988Z
UPDATE AD_Element_Trl SET Name='Vertragsbaustein Typ', PrintName='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-09 18:38:34.35','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582395 AND AD_Language='de_DE'
;

-- 2023-06-09T17:38:34.352004675Z
UPDATE AD_Element SET Name='Vertragsbaustein Typ', PrintName='Vertragsbaustein Typ' WHERE AD_Element_ID=582395
;

-- 2023-06-09T17:38:34.712421419Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582395,'de_DE')
;

-- 2023-06-09T17:38:34.713267338Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582395,'de_DE')
;

-- Element: Contract_Module_Type_ID
-- 2023-06-09T17:38:39.802638437Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-09 18:38:39.802','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582395 AND AD_Language='en_US'
;

-- 2023-06-09T17:38:39.807548823Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582395,'en_US')
;

