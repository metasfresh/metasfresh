

-- Window: Test facility group, InternalName=null
-- 2023-05-02T08:40:28.005019500Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582271,0,541701,TO_TIMESTAMP('2023-05-02 11:40:27.787','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','N','N','N','Y','Test facility group','N',TO_TIMESTAMP('2023-05-02 11:40:27.787','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-05-02T08:40:28.010156900Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541701 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-05-02T08:40:28.016161700Z
/* DDL */  select update_window_translation_from_ad_element(582271) 
;

-- 2023-05-02T08:40:28.045156300Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541701
;

-- 2023-05-02T08:40:28.051159400Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541701)
;

-- Tab: Test facility group(541701,D) -> Test facility group
-- Table: S_HumanResourceTestGroup
-- 2023-05-02T08:41:13.051596900Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582271,0,546958,542326,541701,'Y',TO_TIMESTAMP('2023-05-02 11:41:12.856','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','S_HumanResourceTestGroup','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Test facility group','N',10,0,TO_TIMESTAMP('2023-05-02 11:41:12.856','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:41:13.055009800Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546958 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-05-02T08:41:13.057225300Z
/* DDL */  select update_tab_translation_from_ad_element(582271) 
;

-- 2023-05-02T08:41:13.061233200Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546958)
;

-- Field: Test facility group(541701,D) -> Test facility group(546958,D) -> Mandant
-- Column: S_HumanResourceTestGroup.AD_Client_ID
-- 2023-05-02T08:41:23.702574Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586498,715046,0,546958,TO_TIMESTAMP('2023-05-02 11:41:23.508','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-05-02 11:41:23.508','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:41:23.705567200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T08:41:23.707577500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-05-02T08:41:24.041009500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715046
;

-- 2023-05-02T08:41:24.042005700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715046)
;

-- Field: Test facility group(541701,D) -> Test facility group(546958,D) -> Organisation
-- Column: S_HumanResourceTestGroup.AD_Org_ID
-- 2023-05-02T08:41:24.136939600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586499,715047,0,546958,TO_TIMESTAMP('2023-05-02 11:41:24.046','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-05-02 11:41:24.046','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:41:24.137937800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T08:41:24.138937300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-05-02T08:41:24.495677Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715047
;

-- 2023-05-02T08:41:24.496678300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715047)
;

-- Field: Test facility group(541701,D) -> Test facility group(546958,D) -> Aktiv
-- Column: S_HumanResourceTestGroup.IsActive
-- 2023-05-02T08:41:24.598115900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586502,715048,0,546958,TO_TIMESTAMP('2023-05-02 11:41:24.498','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-05-02 11:41:24.498','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:41:24.599118500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T08:41:24.600116200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-05-02T08:41:24.984991700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715048
;

-- 2023-05-02T08:41:24.985995100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715048)
;

-- Field: Test facility group(541701,D) -> Test facility group(546958,D) -> Test facility group
-- Column: S_HumanResourceTestGroup.S_HumanResourceTestGroup_ID
-- 2023-05-02T08:41:25.110163300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586505,715049,0,546958,TO_TIMESTAMP('2023-05-02 11:41:24.987','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Test facility group',TO_TIMESTAMP('2023-05-02 11:41:24.987','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:41:25.110673300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T08:41:25.111682100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582271) 
;

-- 2023-05-02T08:41:25.115687700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715049
;

-- 2023-05-02T08:41:25.115687700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715049)
;

-- Field: Test facility group(541701,D) -> Test facility group(546958,D) -> Name
-- Column: S_HumanResourceTestGroup.Name
-- 2023-05-02T08:41:25.230955400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586506,715050,0,546958,TO_TIMESTAMP('2023-05-02 11:41:25.117','YYYY-MM-DD HH24:MI:SS.US'),100,'',40,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-05-02 11:41:25.117','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:41:25.231962500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T08:41:25.232969700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-05-02T08:41:25.283623100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715050
;

-- 2023-05-02T08:41:25.283623100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715050)
;

-- Field: Test facility group(541701,D) -> Test facility group(546958,D) -> Abteilung
-- Column: S_HumanResourceTestGroup.Department
-- 2023-05-02T08:41:25.385345400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586507,715051,0,546958,TO_TIMESTAMP('2023-05-02 11:41:25.286','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Abteilung',TO_TIMESTAMP('2023-05-02 11:41:25.286','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:41:25.386249500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T08:41:25.387855300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582261) 
;

-- 2023-05-02T08:41:25.396780200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715051
;

-- 2023-05-02T08:41:25.397776400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715051)
;

-- Field: Test facility group(541701,D) -> Test facility group(546958,D) -> LPG
-- Column: S_HumanResourceTestGroup.GroupIdentifier
-- 2023-05-02T08:41:25.483232300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586508,715052,0,546958,TO_TIMESTAMP('2023-05-02 11:41:25.399','YYYY-MM-DD HH24:MI:SS.US'),100,'Gemittelte Wochenkapazität',40,'D','Y','N','N','N','N','N','N','N','LPG',TO_TIMESTAMP('2023-05-02 11:41:25.399','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:41:25.485232500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T08:41:25.486231Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582272) 
;

-- 2023-05-02T08:41:25.488750Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715052
;

-- 2023-05-02T08:41:25.488750Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715052)
;

-- Field: Test facility group(541701,D) -> Test facility group(546958,D) -> Kapazität in Stunden
-- Column: S_HumanResourceTestGroup.CapacityInHours
-- 2023-05-02T08:41:25.579568600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586509,715053,0,546958,TO_TIMESTAMP('2023-05-02 11:41:25.49','YYYY-MM-DD HH24:MI:SS.US'),100,'Personelle Kapazität Stunden pro Woche',14,'D','Y','N','N','N','N','N','N','N','Kapazität in Stunden',TO_TIMESTAMP('2023-05-02 11:41:25.49','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:41:25.580581900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T08:41:25.581092200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582273) 
;

-- 2023-05-02T08:41:25.584102300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715053
;

-- 2023-05-02T08:41:25.584102300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715053)
;

-- Field: Test facility group(541701,D) -> Test facility group(546958,D) -> Ressource
-- Column: S_HumanResourceTestGroup.S_Resource_ID
-- 2023-05-02T08:41:25.677430600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586510,715054,0,546958,TO_TIMESTAMP('2023-05-02 11:41:25.586','YYYY-MM-DD HH24:MI:SS.US'),100,'Ressource',10,'D','Y','N','N','N','N','N','N','N','Ressource',TO_TIMESTAMP('2023-05-02 11:41:25.586','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:41:25.678430300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T08:41:25.679431600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1777) 
;

-- 2023-05-02T08:41:25.684935600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715054
;

-- 2023-05-02T08:41:25.685944700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715054)
;

-- 2023-05-02T08:41:38.615413Z
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- Tab: Test facility group(541701,D) -> Test facility group(546958,D)
-- UI Section: main
-- 2023-05-02T08:41:44.208080300Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546958,545566,TO_TIMESTAMP('2023-05-02 11:41:44.093','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-05-02 11:41:44.093','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-05-02T08:41:44.210195200Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545566 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Test facility group(541701,D) -> Test facility group(546958,D) -> main
-- UI Column: 10
-- 2023-05-02T08:41:44.382339400Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546796,545566,TO_TIMESTAMP('2023-05-02 11:41:44.244','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-05-02 11:41:44.244','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Test facility group(541701,D) -> Test facility group(546958,D) -> main
-- UI Column: 20
-- 2023-05-02T08:41:44.484242500Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546797,545566,TO_TIMESTAMP('2023-05-02 11:41:44.386','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-05-02 11:41:44.386','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 10
-- UI Element Group: default
-- 2023-05-02T08:41:44.632893800Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546796,550648,TO_TIMESTAMP('2023-05-02 11:41:44.509','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-05-02 11:41:44.509','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 10 -> default.Name
-- Column: S_HumanResourceTestGroup.Name
-- 2023-05-02T08:42:13.588828900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715050,0,546958,617280,550648,'F',TO_TIMESTAMP('2023-05-02 11:42:13.421','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2023-05-02 11:42:13.421','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 10 -> default.Abteilung
-- Column: S_HumanResourceTestGroup.Department
-- 2023-05-02T08:42:22.613979900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715051,0,546958,617281,550648,'F',TO_TIMESTAMP('2023-05-02 11:42:22.443','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Abteilung',20,0,0,TO_TIMESTAMP('2023-05-02 11:42:22.443','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 10 -> default.LPG
-- Column: S_HumanResourceTestGroup.GroupIdentifier
-- 2023-05-02T08:42:39.156547500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715052,0,546958,617282,550648,'F',TO_TIMESTAMP('2023-05-02 11:42:38.985','YYYY-MM-DD HH24:MI:SS.US'),100,'Gemittelte Wochenkapazität','Y','N','N','Y','N','N','N',0,'LPG',5,0,0,TO_TIMESTAMP('2023-05-02 11:42:38.985','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 10 -> default.Ressource
-- Column: S_HumanResourceTestGroup.S_Resource_ID
-- 2023-05-02T08:42:50.796806Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715054,0,546958,617283,550648,'F',TO_TIMESTAMP('2023-05-02 11:42:50.657','YYYY-MM-DD HH24:MI:SS.US'),100,'Ressource','Y','N','N','Y','N','N','N',0,'Ressource',30,0,0,TO_TIMESTAMP('2023-05-02 11:42:50.657','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 10 -> default.Kapazität in Stunden
-- Column: S_HumanResourceTestGroup.CapacityInHours
-- 2023-05-02T08:43:18.756162300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715053,0,546958,617284,550648,'F',TO_TIMESTAMP('2023-05-02 11:43:18.623','YYYY-MM-DD HH24:MI:SS.US'),100,'Personelle Kapazität Stunden pro Woche','Y','N','N','Y','N','N','N',0,'Kapazität in Stunden',40,0,0,TO_TIMESTAMP('2023-05-02 11:43:18.623','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 20
-- UI Element Group: flags
-- 2023-05-02T08:43:31.897567300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546797,550649,TO_TIMESTAMP('2023-05-02 11:43:31.729','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2023-05-02 11:43:31.729','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 20 -> flags.Aktiv
-- Column: S_HumanResourceTestGroup.IsActive
-- 2023-05-02T08:43:43.183032200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715048,0,546958,617285,550649,'F',TO_TIMESTAMP('2023-05-02 11:43:43.032','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-05-02 11:43:43.032','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 20
-- UI Element Group: org
-- 2023-05-02T08:43:52.275036800Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546797,550650,TO_TIMESTAMP('2023-05-02 11:43:52.131','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',20,TO_TIMESTAMP('2023-05-02 11:43:52.131','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 20 -> org.Organisation
-- Column: S_HumanResourceTestGroup.AD_Org_ID
-- 2023-05-02T08:44:02.457798600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715047,0,546958,617286,550650,'F',TO_TIMESTAMP('2023-05-02 11:44:02.315','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-05-02 11:44:02.315','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 20 -> org.Mandant
-- Column: S_HumanResourceTestGroup.AD_Client_ID
-- 2023-05-02T08:44:12.299743400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715046,0,546958,617287,550650,'F',TO_TIMESTAMP('2023-05-02 11:44:12.164','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-05-02 11:44:12.164','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: Test facility group
-- Action Type: W
-- Window: Test facility group(541701,D)
-- 2023-05-02T08:46:04.316683400Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582271,542082,0,541701,TO_TIMESTAMP('2023-05-02 11:46:04.163','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Test facility group','Y','N','N','N','N','Test facility group',TO_TIMESTAMP('2023-05-02 11:46:04.163','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:46:04.317682600Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542082 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-05-02T08:46:04.319700100Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542082, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542082)
;

-- 2023-05-02T08:46:04.336683300Z
/* DDL */  select update_menu_translation_from_ad_element(582271) 
;




-- UI Element: Test facility group(541701,D) -> Test facility group(546958,D) -> main -> 10 -> default.Ressource
-- Column: S_HumanResourceTestGroup.S_Resource_ID
-- 2023-05-02T08:48:36.689234400Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617283
;

-- 2023-05-02T08:48:36.690331800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715054
;

-- Field: Test facility group(541701,D) -> Test facility group(546958,D) -> Ressource
-- Column: S_HumanResourceTestGroup.S_Resource_ID
-- 2023-05-02T08:48:36.691269600Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=715054
;

-- 2023-05-02T08:48:36.693784900Z
DELETE FROM AD_Field WHERE AD_Field_ID=715054
;

-- 2023-05-02T08:48:36.694784800Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE S_HumanResourceTestGroup DROP COLUMN IF EXISTS S_Resource_ID')
;

-- Column: S_HumanResourceTestGroup.S_Resource_ID
-- 2023-05-02T08:48:36.735155700Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586510
;

-- 2023-05-02T08:48:36.737087600Z
DELETE FROM AD_Column WHERE AD_Column_ID=586510
;

