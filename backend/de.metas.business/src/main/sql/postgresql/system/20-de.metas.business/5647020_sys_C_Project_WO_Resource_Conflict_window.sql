-- Window: Work Order Resource Conflict, InternalName=null
-- 2022-07-15T07:14:29.863357900Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581128,0,541556,TO_TIMESTAMP('2022-07-15 10:14:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Work Order Resource Conflict','N',TO_TIMESTAMP('2022-07-15 10:14:29','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-07-15T07:14:29.866355800Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541556 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-07-15T07:14:29.874356100Z
/* DDL */  select update_window_translation_from_ad_element(581128) 
;

-- 2022-07-15T07:14:29.877356600Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541556
;

-- 2022-07-15T07:14:29.884356100Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541556)
;

-- Tab: Work Order Resource Conflict -> Work Order Resource Conflict
-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T07:15:11.720904100Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581128,0,546446,542186,541556,'Y',TO_TIMESTAMP('2022-07-15 10:15:11','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_Resource_Conflict','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Work Order Resource Conflict','N',10,0,TO_TIMESTAMP('2022-07-15 10:15:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:11.725906900Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546446 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-07-15T07:15:11.730904Z
/* DDL */  select update_tab_translation_from_ad_element(581128) 
;

-- 2022-07-15T07:15:11.734902100Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546446)
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Mandant
-- Column: C_Project_WO_Resource_Conflict.AD_Client_ID
-- 2022-07-15T07:15:18.839576Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583678,701901,0,546446,TO_TIMESTAMP('2022-07-15 10:15:18','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-07-15 10:15:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:18.843575Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701901 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:15:18.851574800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-07-15T07:15:19.494277800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701901
;

-- 2022-07-15T07:15:19.498280800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701901)
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Sektion
-- Column: C_Project_WO_Resource_Conflict.AD_Org_ID
-- 2022-07-15T07:15:19.601906100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583679,701902,0,546446,TO_TIMESTAMP('2022-07-15 10:15:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-07-15 10:15:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:19.602905Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701902 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:15:19.604904700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-07-15T07:15:20.134732Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701902
;

-- 2022-07-15T07:15:20.135733200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701902)
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Aktiv
-- Column: C_Project_WO_Resource_Conflict.IsActive
-- 2022-07-15T07:15:20.232627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583682,701903,0,546446,TO_TIMESTAMP('2022-07-15 10:15:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-07-15 10:15:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:20.235626700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701903 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:15:20.237633600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-07-15T07:15:21.007679400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701903
;

-- 2022-07-15T07:15:21.008678800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701903)
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Work Order Resource Conflict
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_Conflict_ID
-- 2022-07-15T07:15:21.107345800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583685,701904,0,546446,TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Work Order Resource Conflict',TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:21.109347600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701904 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:15:21.111351900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581128) 
;

-- 2022-07-15T07:15:21.114346900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701904
;

-- 2022-07-15T07:15:21.115348900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701904)
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project_ID
-- 2022-07-15T07:15:21.213338600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583686,701905,0,546446,TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:21.215338500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701905 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:15:21.217341200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-07-15T07:15:21.316532500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701905
;

-- 2022-07-15T07:15:21.316532500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701905)
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T07:15:21.403167800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583687,701906,0,546446,TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Resource',TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:21.405167400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701906 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:15:21.406167300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580966) 
;

-- 2022-07-15T07:15:21.409167600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701906
;

-- 2022-07-15T07:15:21.409167600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701906)
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project2_ID
-- 2022-07-15T07:15:21.516201Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583688,701907,0,546446,TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:21.518201600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701907 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:15:21.520213300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581125) 
;

-- 2022-07-15T07:15:21.521211400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701907
;

-- 2022-07-15T07:15:21.521211400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701907)
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource2_ID
-- 2022-07-15T07:15:21.631113600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583689,701908,0,546446,TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Resource',TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:21.632110700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701908 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:15:21.634111900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581127) 
;

-- 2022-07-15T07:15:21.634111900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701908
;

-- 2022-07-15T07:15:21.635110600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701908)
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Simulation Plan
-- Column: C_Project_WO_Resource_Conflict.C_SimulationPlan_ID
-- 2022-07-15T07:15:21.742515200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583690,701909,0,546446,TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Simulation Plan',TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:21.743517400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701909 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:15:21.745517100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581064) 
;

-- 2022-07-15T07:15:21.746516800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701909
;

-- 2022-07-15T07:15:21.746516800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701909)
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Status
-- Column: C_Project_WO_Resource_Conflict.Status
-- 2022-07-15T07:15:21.843638900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583691,701910,0,546446,TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2022-07-15 10:15:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:21.845635900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701910 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:15:21.846635400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3020) 
;

-- 2022-07-15T07:15:21.853623400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701910
;

-- 2022-07-15T07:15:21.854604600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701910)
;

-- 2022-07-15T07:15:33.749086600Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546446,545091,TO_TIMESTAMP('2022-07-15 10:15:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-15 10:15:32','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-07-15T07:15:33.751084800Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545091 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-07-15T07:15:38.633111100Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546173,545091,TO_TIMESTAMP('2022-07-15 10:15:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-15 10:15:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:40.246313800Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546174,545091,TO_TIMESTAMP('2022-07-15 10:15:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-07-15 10:15:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:15:53.173607400Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546173,549524,TO_TIMESTAMP('2022-07-15 10:15:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2022-07-15 10:15:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project_ID
-- 2022-07-15T07:16:33.153063Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701905,0,546446,610360,549524,'F',TO_TIMESTAMP('2022-07-15 10:16:33','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','Projekt',10,0,0,TO_TIMESTAMP('2022-07-15 10:16:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T07:16:44.189310700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701906,0,546446,610361,549524,'F',TO_TIMESTAMP('2022-07-15 10:16:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Project Resource',20,0,0,TO_TIMESTAMP('2022-07-15 10:16:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:17:09.109329Z
UPDATE AD_UI_ElementGroup SET Name='resource 1',Updated=TO_TIMESTAMP('2022-07-15 10:17:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549524
;

-- 2022-07-15T07:17:14.165149800Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546173,549525,TO_TIMESTAMP('2022-07-15 10:17:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','resource 2',20,TO_TIMESTAMP('2022-07-15 10:17:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project2_ID
-- 2022-07-15T07:17:29.587652200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701907,0,546446,610362,549525,'F',TO_TIMESTAMP('2022-07-15 10:17:29','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','Projekt',10,0,0,TO_TIMESTAMP('2022-07-15 10:17:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource2_ID
-- 2022-07-15T07:17:38.684330300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701908,0,546446,610363,549525,'F',TO_TIMESTAMP('2022-07-15 10:17:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Project Resource',20,0,0,TO_TIMESTAMP('2022-07-15 10:17:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:17:47.072348400Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546173,549526,TO_TIMESTAMP('2022-07-15 10:17:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','status',30,TO_TIMESTAMP('2022-07-15 10:17:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Status
-- Column: C_Project_WO_Resource_Conflict.Status
-- 2022-07-15T07:17:58.977650600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701910,0,546446,610364,549526,'F',TO_TIMESTAMP('2022-07-15 10:17:58','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Status',10,0,0,TO_TIMESTAMP('2022-07-15 10:17:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:18:18.211678Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546173,549527,TO_TIMESTAMP('2022-07-15 10:18:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','simulation plan',40,TO_TIMESTAMP('2022-07-15 10:18:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:18:20.795515Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2022-07-15 10:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549527
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Simulation Plan
-- Column: C_Project_WO_Resource_Conflict.C_SimulationPlan_ID
-- 2022-07-15T07:18:32.853776800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701909,0,546446,610365,549527,'F',TO_TIMESTAMP('2022-07-15 10:18:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Simulation Plan',10,0,0,TO_TIMESTAMP('2022-07-15 10:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:18:43.321326800Z
UPDATE AD_UI_ElementGroup SET SeqNo=5,Updated=TO_TIMESTAMP('2022-07-15 10:18:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549527
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Simulation Plan
-- Column: C_Project_WO_Resource_Conflict.C_SimulationPlan_ID
-- 2022-07-15T07:19:31.079171800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-07-15 10:19:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610365
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T07:19:31.083172800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-07-15 10:19:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610361
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource2_ID
-- 2022-07-15T07:19:31.089172500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-07-15 10:19:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610363
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Status
-- Column: C_Project_WO_Resource_Conflict.Status
-- 2022-07-15T07:19:31.094171900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-07-15 10:19:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610364
;

-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T07:20:18.394778300Z
UPDATE AD_Table SET AD_Window_ID=541556,Updated=TO_TIMESTAMP('2022-07-15 10:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542186
;

-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T07:20:21.700179200Z
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2022-07-15 10:20:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542186
;

-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T07:21:04.350615200Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-07-15 10:21:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583687
;

-- Window: Work Order Resource Conflict, InternalName=C_Project_WO_Resource_Conflict
-- 2022-07-15T07:51:19.119468Z
UPDATE AD_Window SET InternalName='C_Project_WO_Resource_Conflict',Updated=TO_TIMESTAMP('2022-07-15 10:51:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541556
;

-- Name: Work Order Resource Conflict
-- Window: Work Order Resource Conflict
-- 2022-07-15T07:51:29.662016900Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581128,541972,0,541556,TO_TIMESTAMP('2022-07-15 10:51:29','YYYY-MM-DD HH24:MI:SS'),100,'D','C_Project_WO_Resource_Conflict','Y','N','N','N','N','Work Order Resource Conflict',TO_TIMESTAMP('2022-07-15 10:51:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:51:29.665023700Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541972 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-07-15T07:51:29.668016900Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541972, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541972)
;

-- 2022-07-15T07:51:29.680015200Z
/* DDL */  select update_menu_translation_from_ad_element(581128) 
;

-- Reordering children of `Project Management`
-- Node name: `Project Setup and Use`
-- 2022-07-15T07:51:37.945450600Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=219 AND AD_Tree_ID=10
;

-- Node name: `Project Type (C_ProjectType)`
-- 2022-07-15T07:51:37.946441800Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=364 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2022-07-15T07:51:37.947441500Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=116 AND AD_Tree_ID=10
;

-- Node name: `Project (Lines/Issues) (C_Project)`
-- 2022-07-15T07:51:37.948452400Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=387 AND AD_Tree_ID=10
;

-- Node name: `Generate PO from Project (de.metas.project.process.legacy.ProjectGenPO)`
-- 2022-07-15T07:51:37.948452400Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=402 AND AD_Tree_ID=10
;

-- Node name: `Issue to Project (de.metas.project.process.legacy.ProjectIssue)`
-- 2022-07-15T07:51:37.949442400Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=401 AND AD_Tree_ID=10
;

-- Node name: `Project Lines not Issued`
-- 2022-07-15T07:51:37.950442300Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=404 AND AD_Tree_ID=10
;

-- Node name: `Project POs not Issued`
-- 2022-07-15T07:51:37.950442300Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=405 AND AD_Tree_ID=10
;

-- Node name: `Project Margin (Work Order)`
-- 2022-07-15T07:51:37.951442700Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=410 AND AD_Tree_ID=10
;

-- Node name: `Project Reporting (C_Cycle)`
-- 2022-07-15T07:51:37.951442700Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=258 AND AD_Tree_ID=10
;

-- Node name: `Project Status Summary (org.compiere.report.ProjectStatus)`
-- 2022-07-15T07:51:37.952442100Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=260 AND AD_Tree_ID=10
;

-- Node name: `Project Cycle Report`
-- 2022-07-15T07:51:37.952442100Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=398 AND AD_Tree_ID=10
;

-- Node name: `Project Detail Accounting Report`
-- 2022-07-15T07:51:37.953441600Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=403 AND AD_Tree_ID=10
;

-- Node name: `Work Order Resource Conflict`
-- 2022-07-15T07:51:37.954441900Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541972 AND AD_Tree_ID=10
;

-- Reordering children of `Project Management`
-- Node name: `Project Type (C_ProjectType)`
-- 2022-07-15T07:52:03.113761100Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2022-07-15T07:52:03.114759600Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Project (C_Project)`
-- 2022-07-15T07:52:03.114759600Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541577 AND AD_Tree_ID=10
;

-- Node name: `Budget Project (C_Project)`
-- 2022-07-15T07:52:03.115759600Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541957 AND AD_Tree_ID=10
;

-- Node name: `Work Order Project (C_Project)`
-- 2022-07-15T07:52:03.116761700Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541961 AND AD_Tree_ID=10
;

-- Node name: `Work Order Resource Conflict`
-- 2022-07-15T07:52:03.117760200Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541972 AND AD_Tree_ID=10
;

