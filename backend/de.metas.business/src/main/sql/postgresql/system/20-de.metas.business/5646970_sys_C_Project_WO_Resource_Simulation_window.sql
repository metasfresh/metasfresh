-- Window: WO Project Resource Simulation, InternalName=null
-- 2022-07-14T18:26:50.061206900Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581066,0,541554,TO_TIMESTAMP('2022-07-14 21:26:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','WO Project Resource Simulation','N',TO_TIMESTAMP('2022-07-14 21:26:49','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-07-14T18:26:50.066214600Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541554 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-07-14T18:26:50.102179700Z
/* DDL */  select update_window_translation_from_ad_element(581066) 
;

-- 2022-07-14T18:26:50.117621Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541554
;

-- 2022-07-14T18:26:50.128747900Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541554)
;

-- Tab: WO Project Resource Simulation -> WO Project Resource Simulation
-- Table: C_Project_WO_Resource_Simulation
-- 2022-07-14T18:29:53.182810700Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581066,0,546445,542176,541554,'Y',TO_TIMESTAMP('2022-07-14 21:29:53','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_Resource_Simulation','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'WO Project Resource Simulation','N',10,0,TO_TIMESTAMP('2022-07-14 21:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:29:53.188910Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546445 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-07-14T18:29:53.192909900Z
/* DDL */  select update_tab_translation_from_ad_element(581066) 
;

-- 2022-07-14T18:29:53.196903600Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546445)
;

-- Field: WO Project Resource Simulation -> WO Project Resource Simulation -> Mandant
-- Column: C_Project_WO_Resource_Simulation.AD_Client_ID
-- 2022-07-14T18:30:02.379576800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583512,701891,0,546445,TO_TIMESTAMP('2022-07-14 21:30:01','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-07-14 21:30:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:02.384543700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-14T18:30:02.390547200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-07-14T18:30:03.198415700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701891
;

-- 2022-07-14T18:30:03.198415700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701891)
;

-- Field: WO Project Resource Simulation -> WO Project Resource Simulation -> Sektion
-- Column: C_Project_WO_Resource_Simulation.AD_Org_ID
-- 2022-07-14T18:30:03.323677500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583513,701892,0,546445,TO_TIMESTAMP('2022-07-14 21:30:03','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-07-14 21:30:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:03.326708600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-14T18:30:03.328675600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-07-14T18:30:03.863551400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701892
;

-- 2022-07-14T18:30:03.863551400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701892)
;

-- Field: WO Project Resource Simulation -> WO Project Resource Simulation -> Aktiv
-- Column: C_Project_WO_Resource_Simulation.IsActive
-- 2022-07-14T18:30:03.981198700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583516,701893,0,546445,TO_TIMESTAMP('2022-07-14 21:30:03','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-07-14 21:30:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:03.984238Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-14T18:30:03.986227200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-07-14T18:30:04.596779500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701893
;

-- 2022-07-14T18:30:04.596779500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701893)
;

-- Field: WO Project Resource Simulation -> WO Project Resource Simulation -> WO Project Resource Simulation
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_Simulation_ID
-- 2022-07-14T18:30:04.703258900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583519,701894,0,546445,TO_TIMESTAMP('2022-07-14 21:30:04','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','WO Project Resource Simulation',TO_TIMESTAMP('2022-07-14 21:30:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:04.706256500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701894 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-14T18:30:04.708293500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581066) 
;

-- 2022-07-14T18:30:04.711301300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701894
;

-- 2022-07-14T18:30:04.711301300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701894)
;

-- Field: WO Project Resource Simulation -> WO Project Resource Simulation -> Projekt
-- Column: C_Project_WO_Resource_Simulation.C_Project_ID
-- 2022-07-14T18:30:04.813265800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583520,701895,0,546445,TO_TIMESTAMP('2022-07-14 21:30:04','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-07-14 21:30:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:04.815301400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701895 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-14T18:30:04.817263300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-07-14T18:30:04.917479900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701895
;

-- 2022-07-14T18:30:04.917479900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701895)
;

-- Field: WO Project Resource Simulation -> WO Project Resource Simulation -> Project Resource
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_ID
-- 2022-07-14T18:30:05.032479700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583521,701896,0,546445,TO_TIMESTAMP('2022-07-14 21:30:04','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Resource',TO_TIMESTAMP('2022-07-14 21:30:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:05.035511900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701896 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-14T18:30:05.037477200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580966) 
;

-- 2022-07-14T18:30:05.041479800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701896
;

-- 2022-07-14T18:30:05.041479800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701896)
;

-- Field: WO Project Resource Simulation -> WO Project Resource Simulation -> Zuordnung von
-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom
-- 2022-07-14T18:30:05.152524600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583523,701897,0,546445,TO_TIMESTAMP('2022-07-14 21:30:05','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab',7,'D','Beginn Zuordnung','Y','N','N','N','N','N','N','N','Zuordnung von',TO_TIMESTAMP('2022-07-14 21:30:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:05.155513500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701897 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-14T18:30:05.157514300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1754) 
;

-- 2022-07-14T18:30:05.163478800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701897
;

-- 2022-07-14T18:30:05.164478900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701897)
;

-- Field: WO Project Resource Simulation -> WO Project Resource Simulation -> Zuordnung bis
-- Column: C_Project_WO_Resource_Simulation.AssignDateTo
-- 2022-07-14T18:30:05.253106600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583524,701898,0,546445,TO_TIMESTAMP('2022-07-14 21:30:05','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis',7,'D','Zuordnung endet','Y','N','N','N','N','N','N','N','Zuordnung bis',TO_TIMESTAMP('2022-07-14 21:30:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:05.255071200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701898 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-14T18:30:05.257071700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1755) 
;

-- 2022-07-14T18:30:05.261131300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701898
;

-- 2022-07-14T18:30:05.261131300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701898)
;

-- Field: WO Project Resource Simulation -> WO Project Resource Simulation -> All day
-- Column: C_Project_WO_Resource_Simulation.IsAllDay
-- 2022-07-14T18:30:05.371338800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583525,701899,0,546445,TO_TIMESTAMP('2022-07-14 21:30:05','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','All day',TO_TIMESTAMP('2022-07-14 21:30:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:05.373336900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-14T18:30:05.375335800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580861) 
;

-- 2022-07-14T18:30:05.378337700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701899
;

-- 2022-07-14T18:30:05.378337700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701899)
;

-- Field: WO Project Resource Simulation -> WO Project Resource Simulation -> Simulation Plan
-- Column: C_Project_WO_Resource_Simulation.C_SimulationPlan_ID
-- 2022-07-14T18:30:05.472342Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583526,701900,0,546445,TO_TIMESTAMP('2022-07-14 21:30:05','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Simulation Plan',TO_TIMESTAMP('2022-07-14 21:30:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:05.474434300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701900 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-14T18:30:05.476403600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581064) 
;

-- 2022-07-14T18:30:05.480435800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701900
;

-- 2022-07-14T18:30:05.481398900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701900)
;

-- 2022-07-14T18:30:14.867964700Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546445,545090,TO_TIMESTAMP('2022-07-14 21:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-14 21:30:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-07-14T18:30:14.872996900Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545090 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-07-14T18:30:19.112322900Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546171,545090,TO_TIMESTAMP('2022-07-14 21:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-14 21:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:20.836277800Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546172,545090,TO_TIMESTAMP('2022-07-14 21:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-07-14 21:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:30:41.192848300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546171,549522,TO_TIMESTAMP('2022-07-14 21:30:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','project resource 1',10,TO_TIMESTAMP('2022-07-14 21:30:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.Projekt
-- Column: C_Project_WO_Resource_Simulation.C_Project_ID
-- 2022-07-14T18:40:33.518033100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701895,0,546445,610353,549522,'F',TO_TIMESTAMP('2022-07-14 21:40:31','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','Projekt',10,0,0,TO_TIMESTAMP('2022-07-14 21:40:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.Project Resource
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_ID
-- 2022-07-14T18:40:42.090978600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701896,0,546445,610354,549522,'F',TO_TIMESTAMP('2022-07-14 21:40:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Project Resource',20,0,0,TO_TIMESTAMP('2022-07-14 21:40:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:40:45.998988100Z
UPDATE AD_UI_ElementGroup SET Name='project resource',Updated=TO_TIMESTAMP('2022-07-14 21:40:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549522
;

-- 2022-07-14T18:40:52.349861600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546171,549523,TO_TIMESTAMP('2022-07-14 21:40:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2022-07-14 21:40:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.Zuordnung von
-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom
-- 2022-07-14T18:41:09.940873400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701897,0,546445,610355,549523,'F',TO_TIMESTAMP('2022-07-14 21:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab','Beginn Zuordnung','Y','N','Y','N','N','Zuordnung von',10,0,0,TO_TIMESTAMP('2022-07-14 21:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.Zuordnung bis
-- Column: C_Project_WO_Resource_Simulation.AssignDateTo
-- 2022-07-14T18:41:17.646019400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701898,0,546445,610356,549523,'F',TO_TIMESTAMP('2022-07-14 21:41:17','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis','Zuordnung endet','Y','N','Y','N','N','Zuordnung bis',20,0,0,TO_TIMESTAMP('2022-07-14 21:41:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.All day
-- Column: C_Project_WO_Resource_Simulation.IsAllDay
-- 2022-07-14T18:41:26.388790700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701899,0,546445,610357,549523,'F',TO_TIMESTAMP('2022-07-14 21:41:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','All day',30,0,0,TO_TIMESTAMP('2022-07-14 21:41:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-14T18:41:42.032196400Z
UPDATE AD_UI_ElementGroup SET Name='main', UIStyle='primary',Updated=TO_TIMESTAMP('2022-07-14 21:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549522
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.Simulation Plan
-- Column: C_Project_WO_Resource_Simulation.C_SimulationPlan_ID
-- 2022-07-14T18:41:57.991240200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701900,0,546445,610358,549522,'F',TO_TIMESTAMP('2022-07-14 21:41:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Simulation Plan',30,0,0,TO_TIMESTAMP('2022-07-14 21:41:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.Simulation Plan
-- Column: C_Project_WO_Resource_Simulation.C_SimulationPlan_ID
-- 2022-07-14T18:42:14.595243200Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2022-07-14 21:42:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610358
;

-- 2022-07-14T18:42:56.896154800Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=546172, SeqNo=10,Updated=TO_TIMESTAMP('2022-07-14 21:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549523
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.Simulation Plan
-- Column: C_Project_WO_Resource_Simulation.C_SimulationPlan_ID
-- 2022-07-14T18:43:25.329805400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-07-14 21:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610358
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.Project Resource
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_ID
-- 2022-07-14T18:43:25.336768700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-07-14 21:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610354
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.Zuordnung von
-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom
-- 2022-07-14T18:43:25.340767500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-07-14 21:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610355
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.Zuordnung bis
-- Column: C_Project_WO_Resource_Simulation.AssignDateTo
-- 2022-07-14T18:43:25.344767600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-07-14 21:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610356
;

-- UI Element: WO Project Resource Simulation -> WO Project Resource Simulation.All day
-- Column: C_Project_WO_Resource_Simulation.IsAllDay
-- 2022-07-14T18:43:25.349767600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-07-14 21:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610357
;

-- Table: C_Project_WO_Resource_Simulation
-- 2022-07-15T06:53:34.576895500Z
UPDATE AD_Table SET AD_Window_ID=541554, IsHighVolume='Y',Updated=TO_TIMESTAMP('2022-07-15 09:53:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542176
;

-- Column: C_Project_WO_Resource_Simulation.C_Project_ID
-- 2022-07-15T06:53:55.783324Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-07-15 09:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583520
;

-- Column: C_Project_WO_Resource_Simulation.C_SimulationPlan_ID
-- 2022-07-15T06:54:30.771886100Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-07-15 09:54:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583526
;

