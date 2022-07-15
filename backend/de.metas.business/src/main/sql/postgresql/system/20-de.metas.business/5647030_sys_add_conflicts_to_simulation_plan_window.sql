-- Table: C_SimulationPlan
-- 2022-07-15T07:30:28.167139200Z
UPDATE AD_Table SET AD_Window_ID=541541,Updated=TO_TIMESTAMP('2022-07-15 10:30:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542173
;

-- Tab: Simulation Plan -> Work Order Resource Conflict
-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T07:31:29.652462800Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581128,0,546447,542186,541541,'Y',TO_TIMESTAMP('2022-07-15 10:31:29','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_Resource_Conflict','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Work Order Resource Conflict','N',50,0,TO_TIMESTAMP('2022-07-15 10:31:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:31:29.654461900Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546447 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-07-15T07:31:29.659461800Z
/* DDL */  select update_tab_translation_from_ad_element(581128) 
;

-- 2022-07-15T07:31:29.664464100Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546447)
;

-- Tab: Simulation Plan -> Work Order Resource Conflict
-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T07:33:29.733956500Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=546447
;

-- 2022-07-15T07:33:29.749958300Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=546447
;

-- Tab: Simulation Plan -> Work Order Resource Conflict
-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T07:34:12.363194700Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581128,0,546448,542186,541541,'Y',TO_TIMESTAMP('2022-07-15 10:34:12','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_Resource_Conflict','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Work Order Resource Conflict','N',10,0,TO_TIMESTAMP('2022-07-15 10:34:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:12.365201600Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546448 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-07-15T07:34:12.366201400Z
/* DDL */  select update_tab_translation_from_ad_element(581128) 
;

-- 2022-07-15T07:34:12.368235500Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546448)
;

-- 2022-07-15T07:34:12.369198800Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546448
;

-- 2022-07-15T07:34:12.369198800Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546448, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 546446
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Mandant
-- Column: C_Project_WO_Resource_Conflict.AD_Client_ID
-- 2022-07-15T07:34:12.471791Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583678,701911,0,546448,0,TO_TIMESTAMP('2022-07-15 10:34:12','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2022-07-15 10:34:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:12.473790100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701911 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:34:12.475790700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-07-15T07:34:12.734870400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701911
;

-- 2022-07-15T07:34:12.735868900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701911)
;

-- 2022-07-15T07:34:12.737869100Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 701911
;

-- 2022-07-15T07:34:12.737869100Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 701911, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701901
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Sektion
-- Column: C_Project_WO_Resource_Conflict.AD_Org_ID
-- 2022-07-15T07:34:12.832121400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583679,701912,0,546448,0,TO_TIMESTAMP('2022-07-15 10:34:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',20,1,1,TO_TIMESTAMP('2022-07-15 10:34:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:12.834121400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701912 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:34:12.835120900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-07-15T07:34:13.008302800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701912
;

-- 2022-07-15T07:34:13.008302800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701912)
;

-- 2022-07-15T07:34:13.010300500Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 701912
;

-- 2022-07-15T07:34:13.010300500Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 701912, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701902
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Aktiv
-- Column: C_Project_WO_Resource_Conflict.IsActive
-- 2022-07-15T07:34:13.123514900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583682,701913,0,546448,0,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',30,1,1,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:13.124537100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701913 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:34:13.126516100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-07-15T07:34:13.372199600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701913
;

-- 2022-07-15T07:34:13.372199600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701913)
;

-- 2022-07-15T07:34:13.373200400Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 701913
;

-- 2022-07-15T07:34:13.374200900Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 701913, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701903
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Work Order Resource Conflict
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_Conflict_ID
-- 2022-07-15T07:34:13.482906500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583685,701914,0,546448,0,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Work Order Resource Conflict',40,1,1,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:13.483907100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701914 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:34:13.485905200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581128) 
;

-- 2022-07-15T07:34:13.487905700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701914
;

-- 2022-07-15T07:34:13.487905700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701914)
;

-- 2022-07-15T07:34:13.488913400Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 701914
;

-- 2022-07-15T07:34:13.488913400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 701914, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701904
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project_ID
-- 2022-07-15T07:34:13.594912500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583686,701915,0,546448,0,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Projekt',50,1,1,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:13.596906100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701915 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:34:13.597905900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-07-15T07:34:13.610904900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701915
;

-- 2022-07-15T07:34:13.610904900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701915)
;

-- 2022-07-15T07:34:13.612906100Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 701915
;

-- 2022-07-15T07:34:13.612906100Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 701915, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701905
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T07:34:13.711833Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583687,701916,0,546448,0,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Project Resource',60,1,1,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:13.712833Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701916 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:34:13.714876Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580966) 
;

-- 2022-07-15T07:34:13.716909400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701916
;

-- 2022-07-15T07:34:13.716909400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701916)
;

-- 2022-07-15T07:34:13.718909300Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 701916
;

-- 2022-07-15T07:34:13.718909300Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 701916, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701906
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project2_ID
-- 2022-07-15T07:34:13.822975600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583688,701917,0,546448,0,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Projekt',70,1,1,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:13.824974Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701917 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:34:13.826971500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581125) 
;

-- 2022-07-15T07:34:13.826971500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701917
;

-- 2022-07-15T07:34:13.827973600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701917)
;

-- 2022-07-15T07:34:13.829006Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 701917
;

-- 2022-07-15T07:34:13.829973500Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 701917, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701907
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource2_ID
-- 2022-07-15T07:34:13.926688900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583689,701918,0,546448,0,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Project Resource',80,1,1,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:13.928688700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701918 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:34:13.930685800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581127) 
;

-- 2022-07-15T07:34:13.931685900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701918
;

-- 2022-07-15T07:34:13.931685900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701918)
;

-- 2022-07-15T07:34:13.933685900Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 701918
;

-- 2022-07-15T07:34:13.933685900Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 701918, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701908
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Simulation Plan
-- Column: C_Project_WO_Resource_Conflict.C_SimulationPlan_ID
-- 2022-07-15T07:34:14.028621100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583690,701919,0,546448,0,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Simulation Plan',90,1,1,TO_TIMESTAMP('2022-07-15 10:34:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:14.030627400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:34:14.031643700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581064) 
;

-- 2022-07-15T07:34:14.032621300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701919
;

-- 2022-07-15T07:34:14.032621300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701919)
;

-- 2022-07-15T07:34:14.034619Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 701919
;

-- 2022-07-15T07:34:14.034619Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 701919, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701909
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Status
-- Column: C_Project_WO_Resource_Conflict.Status
-- 2022-07-15T07:34:14.128009Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583691,701920,0,546448,0,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','',0,'Y','N','N','N','N','N','N','N','Status',100,1,1,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:14.130008100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701920 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T07:34:14.131006900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3020) 
;

-- 2022-07-15T07:34:14.134055300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701920
;

-- 2022-07-15T07:34:14.134055300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701920)
;

-- 2022-07-15T07:34:14.135057300Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 701920
;

-- 2022-07-15T07:34:14.135057300Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 701920, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701910
;

-- 2022-07-15T07:34:14.236894200Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546448,545092,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-07-15T07:34:14.238893700Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545092 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-07-15T07:34:14.242892200Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545092
;

-- 2022-07-15T07:34:14.244904900Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545092, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545091
;

-- 2022-07-15T07:34:14.331984900Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546175,545092,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:14.456176500Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546175,549528,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','simulation plan',5,'primary',TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Simulation Plan
-- Column: C_Project_WO_Resource_Conflict.C_SimulationPlan_ID
-- 2022-07-15T07:34:14.564714800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701919,0,546448,610366,549528,'F',TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N','Simulation Plan',10,0,10,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:14.671120700Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546175,549529,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','resource 1',10,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project_ID
-- 2022-07-15T07:34:14.785299200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701915,0,546448,610367,549529,'F',TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N','Projekt',10,0,0,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T07:34:14.903087500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701916,0,546448,610368,549529,'F',TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N','Project Resource',20,0,20,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:15.005770600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546175,549530,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','resource 2',20,TO_TIMESTAMP('2022-07-15 10:34:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project2_ID
-- 2022-07-15T07:34:15.123931200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701917,0,546448,610369,549530,'F',TO_TIMESTAMP('2022-07-15 10:34:15','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N','Projekt',10,0,0,TO_TIMESTAMP('2022-07-15 10:34:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource2_ID
-- 2022-07-15T07:34:15.218140300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701918,0,546448,610370,549530,'F',TO_TIMESTAMP('2022-07-15 10:34:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N','Project Resource',20,0,30,TO_TIMESTAMP('2022-07-15 10:34:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:15.326301300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546175,549531,TO_TIMESTAMP('2022-07-15 10:34:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','status',30,TO_TIMESTAMP('2022-07-15 10:34:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Status
-- Column: C_Project_WO_Resource_Conflict.Status
-- 2022-07-15T07:34:15.438322600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701920,0,546448,610371,549531,'F',TO_TIMESTAMP('2022-07-15 10:34:15','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','N','Y','N','Status',10,0,40,TO_TIMESTAMP('2022-07-15 10:34:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T07:34:15.540318300Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546176,545092,TO_TIMESTAMP('2022-07-15 10:34:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-07-15 10:34:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Simulation Plan -> Work Order Resource Conflict
-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T07:34:42.945552100Z
UPDATE AD_Tab SET SeqNo=50,Updated=TO_TIMESTAMP('2022-07-15 10:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546448
;

-- Tab: Simulation Plan -> Work Order Resource Conflict
-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T07:36:02.654638100Z
UPDATE AD_Tab SET AD_Column_ID=583690, Parent_Column_ID=583496, TabLevel=1,Updated=TO_TIMESTAMP('2022-07-15 10:36:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546448
;

-- Tab: Simulation Plan -> Work Order Resource Conflict
-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T07:36:09.975072500Z
UPDATE AD_Tab SET IsAutodetectDefaultDateFilter='N',Updated=TO_TIMESTAMP('2022-07-15 10:36:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546448
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Simulation Plan
-- Column: C_Project_WO_Resource_Conflict.C_SimulationPlan_ID
-- 2022-07-15T07:36:48.878741200Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=610366
;

-- 2022-07-15T07:36:48.881740600Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=549528
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T07:37:17.067280Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-07-15 10:37:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610368
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource2_ID
-- 2022-07-15T07:37:17.072280400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-07-15 10:37:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610370
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Status
-- Column: C_Project_WO_Resource_Conflict.Status
-- 2022-07-15T07:37:17.076279900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-07-15 10:37:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610371
;

