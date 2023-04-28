-- Tab: Project Resource Budget -> Project Resource Budget
-- Table: C_Project_Resource_Budget
-- 2022-05-30T13:11:37.230Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583210,580947,0,546281,542157,541506,'Y',TO_TIMESTAMP('2022-05-30 16:11:37','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_Resource_Budget','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Project Resource Budget',1349,'N',20,1,TO_TIMESTAMP('2022-05-30 16:11:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:37.234Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546281 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-05-30T13:11:37.263Z
/* DDL */  select update_tab_translation_from_ad_element(580947) 
;

-- 2022-05-30T13:11:37.268Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546281)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Mandant
-- Column: C_Project_Resource_Budget.AD_Client_ID
-- 2022-05-30T13:11:44.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583202,697305,0,546281,TO_TIMESTAMP('2022-05-30 16:11:43','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-05-30 16:11:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:44.117Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:44.119Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-05-30T13:11:44.419Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697305
;

-- 2022-05-30T13:11:44.422Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697305)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Sektion
-- Column: C_Project_Resource_Budget.AD_Org_ID
-- 2022-05-30T13:11:44.533Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583203,697306,0,546281,TO_TIMESTAMP('2022-05-30 16:11:44','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-05-30 16:11:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:44.534Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697306 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:44.535Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-05-30T13:11:44.714Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697306
;

-- 2022-05-30T13:11:44.715Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697306)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Aktiv
-- Column: C_Project_Resource_Budget.IsActive
-- 2022-05-30T13:11:44.808Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583206,697307,0,546281,TO_TIMESTAMP('2022-05-30 16:11:44','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-05-30 16:11:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:44.808Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697307 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:44.809Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-05-30T13:11:45.082Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697307
;

-- 2022-05-30T13:11:45.082Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697307)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Project Resource Budget
-- Column: C_Project_Resource_Budget.C_Project_Resource_Budget_ID
-- 2022-05-30T13:11:45.172Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583209,697308,0,546281,TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Resource Budget',TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:45.173Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:45.174Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580947) 
;

-- 2022-05-30T13:11:45.175Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697308
;

-- 2022-05-30T13:11:45.176Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697308)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Projekt
-- Column: C_Project_Resource_Budget.C_Project_ID
-- 2022-05-30T13:11:45.265Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583210,697309,0,546281,TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:45.266Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697309 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:45.274Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-05-30T13:11:45.286Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697309
;

-- 2022-05-30T13:11:45.287Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697309)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Resource Group
-- Column: C_Project_Resource_Budget.S_Resource_Group_ID
-- 2022-05-30T13:11:45.367Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583211,697310,0,546281,TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Resource Group',TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:45.368Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:45.369Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580932) 
;

-- 2022-05-30T13:11:45.371Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697310
;

-- 2022-05-30T13:11:45.371Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697310)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Ressource
-- Column: C_Project_Resource_Budget.S_Resource_ID
-- 2022-05-30T13:11:45.460Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583212,697311,0,546281,TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100,'Ressource',10,'D','Y','N','N','N','N','N','N','N','Ressource',TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:45.461Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:45.462Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1777) 
;

-- 2022-05-30T13:11:45.467Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697311
;

-- 2022-05-30T13:11:45.467Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697311)
;

-- Field: Project Resource Budget -> Project Resource Budget -> VK Total
-- Column: C_Project_Resource_Budget.PlannedAmt
-- 2022-05-30T13:11:45.555Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583213,697312,0,546281,TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100,'VK Total',10,'D','The Planned Amount indicates the anticipated amount for this project or project line.','Y','N','N','N','N','N','N','N','VK Total',TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:45.556Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:45.557Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1564) 
;

-- 2022-05-30T13:11:45.559Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697312
;

-- 2022-05-30T13:11:45.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697312)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Maßeinheit für Zeit
-- Column: C_Project_Resource_Budget.C_UOM_Time_ID
-- 2022-05-30T13:11:45.649Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583214,697313,0,546281,TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100,'Standardmaßeinheit für Zeit',10,'D','"Maßeinheit für Zeit" bezeichnet die Standardmaßeinheit, die bei Produkten mit Zeitangabe auf Belegen verwendet wird.','Y','N','N','N','N','N','N','N','Maßeinheit für Zeit',TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:45.650Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697313 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:45.650Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(219) 
;

-- 2022-05-30T13:11:45.651Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697313
;

-- 2022-05-30T13:11:45.652Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697313)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Price / Time UOM
-- Column: C_Project_Resource_Budget.PricePerTimeUOM
-- 2022-05-30T13:11:45.737Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583215,697314,0,546281,TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Price / Time UOM',TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:45.738Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:45.739Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580948) 
;

-- 2022-05-30T13:11:45.739Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697314
;

-- 2022-05-30T13:11:45.740Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697314)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Planned Duration
-- Column: C_Project_Resource_Budget.PlannedDuration
-- 2022-05-30T13:11:45.832Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583216,697315,0,546281,TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Planned Duration',TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:45.833Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697315 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:45.834Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580949) 
;

-- 2022-05-30T13:11:45.834Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697315
;

-- 2022-05-30T13:11:45.835Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697315)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Start Plan
-- Column: C_Project_Resource_Budget.DateStartPlan
-- 2022-05-30T13:11:45.923Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583217,697316,0,546281,TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100,'Planned Start Date',7,'D','Date when you plan to start','Y','N','N','N','N','N','N','N','Start Plan',TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:45.924Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:45.925Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2901) 
;

-- 2022-05-30T13:11:45.926Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697316
;

-- 2022-05-30T13:11:45.926Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697316)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Finish Plan
-- Column: C_Project_Resource_Budget.DateFinishPlan
-- 2022-05-30T13:11:46.013Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583218,697317,0,546281,TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100,'Planned Finish Date',7,'D','Date when you plan to finish','Y','N','N','N','N','N','N','N','Finish Plan',TO_TIMESTAMP('2022-05-30 16:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:46.014Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697317 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:46.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541244) 
;

-- 2022-05-30T13:11:46.015Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697317
;

-- 2022-05-30T13:11:46.015Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697317)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Beschreibung
-- Column: C_Project_Resource_Budget.Description
-- 2022-05-30T13:11:46.097Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583219,697318,0,546281,TO_TIMESTAMP('2022-05-30 16:11:46','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-05-30 16:11:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:11:46.098Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T13:11:46.099Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-05-30T13:11:46.153Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697318
;

-- 2022-05-30T13:11:46.154Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697318)
;

-- Field: Project Resource Budget -> Project Resource Budget -> Resource Group
-- Column: C_Project_Resource_Budget.S_Resource_Group_ID
-- 2022-05-30T13:13:01.024Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-05-30 16:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697310
;

-- Field: Project Resource Budget -> Project Resource Budget -> Ressource
-- Column: C_Project_Resource_Budget.S_Resource_ID
-- 2022-05-30T13:13:01.032Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-05-30 16:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697311
;

-- Field: Project Resource Budget -> Project Resource Budget -> VK Total
-- Column: C_Project_Resource_Budget.PlannedAmt
-- 2022-05-30T13:13:01.042Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-05-30 16:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697312
;

-- Field: Project Resource Budget -> Project Resource Budget -> Planned Duration
-- Column: C_Project_Resource_Budget.PlannedDuration
-- 2022-05-30T13:13:01.046Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2022-05-30 16:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697315
;

-- Field: Project Resource Budget -> Project Resource Budget -> Maßeinheit für Zeit
-- Column: C_Project_Resource_Budget.C_UOM_Time_ID
-- 2022-05-30T13:13:01.051Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2022-05-30 16:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697313
;

-- Field: Project Resource Budget -> Project Resource Budget -> Price / Time UOM
-- Column: C_Project_Resource_Budget.PricePerTimeUOM
-- 2022-05-30T13:13:01.056Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2022-05-30 16:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697314
;

-- Field: Project Resource Budget -> Project Resource Budget -> Start Plan
-- Column: C_Project_Resource_Budget.DateStartPlan
-- 2022-05-30T13:13:01.060Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2022-05-30 16:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697316
;

-- Field: Project Resource Budget -> Project Resource Budget -> Finish Plan
-- Column: C_Project_Resource_Budget.DateFinishPlan
-- 2022-05-30T13:13:01.063Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2022-05-30 16:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697317
;

-- Field: Project Resource Budget -> Project Resource Budget -> Beschreibung
-- Column: C_Project_Resource_Budget.Description
-- 2022-05-30T13:13:01.067Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2022-05-30 16:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697318
;

-- Table: C_Project_Resource_Budget
-- 2022-05-30T13:21:39.465Z
UPDATE AD_Table SET AD_Window_ID=541506,Updated=TO_TIMESTAMP('2022-05-30 16:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542157
;

-- 2022-05-30T13:26:21.656Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546281,544927,TO_TIMESTAMP('2022-05-30 16:26:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-30 16:26:21','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-05-30T13:26:21.657Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544927 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-30T13:26:27.172Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545956,544927,TO_TIMESTAMP('2022-05-30 16:26:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-30 16:26:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T13:26:36.296Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545956,549155,TO_TIMESTAMP('2022-05-30 16:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','resource',10,TO_TIMESTAMP('2022-05-30 16:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Resource Group
-- Column: C_Project_Resource_Budget.S_Resource_Group_ID
-- 2022-05-30T13:26:47.206Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697310,0,546281,608042,549155,'F',TO_TIMESTAMP('2022-05-30 16:26:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Resource Group',10,0,0,TO_TIMESTAMP('2022-05-30 16:26:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Ressource
-- Column: C_Project_Resource_Budget.S_Resource_ID
-- 2022-05-30T13:27:00.493Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697311,0,546281,608043,549155,'F',TO_TIMESTAMP('2022-05-30 16:27:00','YYYY-MM-DD HH24:MI:SS'),100,'Ressource','Y','N','Y','N','N','Ressource',20,0,0,TO_TIMESTAMP('2022-05-30 16:27:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T14:48:13.897Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545956,549168,TO_TIMESTAMP('2022-05-30 17:48:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','amounts and duration',20,TO_TIMESTAMP('2022-05-30 17:48:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.VK Total
-- Column: C_Project_Resource_Budget.PlannedAmt
-- 2022-05-30T14:48:55.587Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697312,0,546281,608176,549168,'F',TO_TIMESTAMP('2022-05-30 17:48:55','YYYY-MM-DD HH24:MI:SS'),100,'VK Total','The Planned Amount indicates the anticipated amount for this project or project line.','Y','N','Y','N','N','VK Total',10,0,0,TO_TIMESTAMP('2022-05-30 17:48:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Planned Duration
-- Column: C_Project_Resource_Budget.PlannedDuration
-- 2022-05-30T14:49:02.233Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697315,0,546281,608177,549168,'F',TO_TIMESTAMP('2022-05-30 17:49:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Planned Duration',20,0,0,TO_TIMESTAMP('2022-05-30 17:49:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Price / Time UOM
-- Column: C_Project_Resource_Budget.PricePerTimeUOM
-- 2022-05-30T14:49:17.411Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697314,0,546281,608178,549168,'F',TO_TIMESTAMP('2022-05-30 17:49:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Price / Time UOM',30,0,0,TO_TIMESTAMP('2022-05-30 17:49:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Maßeinheit für Zeit
-- Column: C_Project_Resource_Budget.C_UOM_Time_ID
-- 2022-05-30T14:49:33.973Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697313,0,546281,608179,549168,'F',TO_TIMESTAMP('2022-05-30 17:49:33','YYYY-MM-DD HH24:MI:SS'),100,'Standardmaßeinheit für Zeit','"Maßeinheit für Zeit" bezeichnet die Standardmaßeinheit, die bei Produkten mit Zeitangabe auf Belegen verwendet wird.','Y','N','Y','N','N','Maßeinheit für Zeit',40,0,0,TO_TIMESTAMP('2022-05-30 17:49:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T14:49:42.782Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545956,549169,TO_TIMESTAMP('2022-05-30 17:49:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',30,TO_TIMESTAMP('2022-05-30 17:49:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Start Plan
-- Column: C_Project_Resource_Budget.DateStartPlan
-- 2022-05-30T14:49:57.851Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697316,0,546281,608180,549169,'F',TO_TIMESTAMP('2022-05-30 17:49:57','YYYY-MM-DD HH24:MI:SS'),100,'Planned Start Date','Date when you plan to start','Y','N','Y','N','N','Start Plan',10,0,0,TO_TIMESTAMP('2022-05-30 17:49:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Finish Plan
-- Column: C_Project_Resource_Budget.DateFinishPlan
-- 2022-05-30T14:50:05.516Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697317,0,546281,608181,549169,'F',TO_TIMESTAMP('2022-05-30 17:50:05','YYYY-MM-DD HH24:MI:SS'),100,'Planned Finish Date','Date when you plan to finish','Y','N','Y','N','N','Finish Plan',20,0,0,TO_TIMESTAMP('2022-05-30 17:50:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T14:50:25.913Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545956,549171,TO_TIMESTAMP('2022-05-30 17:50:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','misc',40,TO_TIMESTAMP('2022-05-30 17:50:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Beschreibung
-- Column: C_Project_Resource_Budget.Description
-- 2022-05-30T14:50:38.454Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697318,0,546281,608182,549171,'F',TO_TIMESTAMP('2022-05-30 17:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2022-05-30 17:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T14:51:31.199Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545956,549172,TO_TIMESTAMP('2022-05-30 17:51:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','client and org',50,TO_TIMESTAMP('2022-05-30 17:51:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Sektion
-- Column: C_Project_Resource_Budget.AD_Org_ID
-- 2022-05-30T14:51:40.740Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697306,0,546281,608183,549172,'F',TO_TIMESTAMP('2022-05-30 17:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2022-05-30 17:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Mandant
-- Column: C_Project_Resource_Budget.AD_Client_ID
-- 2022-05-30T14:51:47.518Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697305,0,546281,608184,549172,'F',TO_TIMESTAMP('2022-05-30 17:51:47','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-05-30 17:51:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T14:52:28.564Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545962,544927,TO_TIMESTAMP('2022-05-30 17:52:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-05-30 17:52:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T14:52:46.294Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=545962, SeqNo=10,Updated=TO_TIMESTAMP('2022-05-30 17:52:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549168
;

-- 2022-05-30T14:52:57.385Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=545962, SeqNo=20,Updated=TO_TIMESTAMP('2022-05-30 17:52:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549169
;

-- 2022-05-30T14:53:08.371Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=545962, SeqNo=30,Updated=TO_TIMESTAMP('2022-05-30 17:53:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549172
;

-- UI Element: Project Resource Budget -> Project Resource Budget.VK Total
-- Column: C_Project_Resource_Budget.PlannedAmt
-- 2022-05-30T14:53:27.227Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-05-30 17:53:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608176
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Planned Duration
-- Column: C_Project_Resource_Budget.PlannedDuration
-- 2022-05-30T14:53:27.231Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-05-30 17:53:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608177
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Start Plan
-- Column: C_Project_Resource_Budget.DateStartPlan
-- 2022-05-30T14:53:27.235Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-05-30 17:53:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608180
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Finish Plan
-- Column: C_Project_Resource_Budget.DateFinishPlan
-- 2022-05-30T14:53:27.239Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-05-30 17:53:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608181
;

















-- Column: C_Project_Resource_Budget.C_Currency_ID
-- 2022-05-30T15:47:34.772Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583222,193,0,30,542157,'C_Currency_ID',TO_TIMESTAMP('2022-05-30 18:47:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Währung für diesen Eintrag','D',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2022-05-30 18:47:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-30T15:47:34.777Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583222 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-30T15:47:34.782Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- 2022-05-30T15:47:35.458Z
/* DDL */ SELECT public.db_alter_table('C_Project_Resource_Budget','ALTER TABLE public.C_Project_Resource_Budget ADD COLUMN C_Currency_ID NUMERIC(10) NOT NULL')
;

-- 2022-05-30T15:47:35.476Z
ALTER TABLE C_Project_Resource_Budget ADD CONSTRAINT CCurrency_CProjectResourceBudget FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- Field: Project Resource Budget -> Project Resource Budget -> Währung
-- Column: C_Project_Resource_Budget.C_Currency_ID
-- 2022-05-30T15:48:17.144Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583222,697943,0,546281,TO_TIMESTAMP('2022-05-30 18:48:16','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2022-05-30 18:48:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T15:48:17.146Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T15:48:17.149Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2022-05-30T15:48:17.171Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697943
;

-- 2022-05-30T15:48:17.174Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697943)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Währung
-- Column: C_Project_Resource_Budget.C_Currency_ID
-- 2022-05-30T15:48:40.227Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697943,0,546281,608277,549168,'F',TO_TIMESTAMP('2022-05-30 18:48:39','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','Währung',50,0,0,TO_TIMESTAMP('2022-05-30 18:48:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Project Resource Budget.Währung
-- Column: C_Project_Resource_Budget.C_Currency_ID
-- 2022-05-30T15:49:11.080Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2022-05-30 18:49:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608277
;

-- Column: C_Project_Resource_Budget.DateFinishPlan
-- 2022-05-30T15:54:50.936Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-05-30 18:54:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583218
;

-- 2022-05-30T17:15:13.999Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545941,549183,TO_TIMESTAMP('2022-05-30 20:15:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','pricing',15,TO_TIMESTAMP('2022-05-30 20:15:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Version Preisliste
-- Column: C_Project.M_PriceList_Version_ID
-- 2022-05-30T17:15:48.413Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696886,0,546269,608278,549183,'F',TO_TIMESTAMP('2022-05-30 20:15:48','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','Y','N','N','Version Preisliste',10,0,0,TO_TIMESTAMP('2022-05-30 20:15:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Währung
-- Column: C_Project.C_Currency_ID
-- 2022-05-30T17:16:11.598Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696887,0,546269,608279,549183,'F',TO_TIMESTAMP('2022-05-30 20:16:11','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','Währung',20,0,0,TO_TIMESTAMP('2022-05-30 20:16:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Project Resource Budget -> Projekt -> Währung
-- Column: C_Project.C_Currency_ID
-- 2022-05-30T17:16:36.012Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-05-30 20:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696887
;

-- Field: Project Resource Budget -> Projekt -> Währung
-- Column: C_Project.C_Currency_ID
-- 2022-05-30T17:22:52.623Z
UPDATE AD_Field SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-05-30 20:22:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696887
;

