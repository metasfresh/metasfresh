-- Window: Project Resource Budget, InternalName=null
-- 2022-05-30T10:51:49.612Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,580947,0,541506,TO_TIMESTAMP('2022-05-30 13:51:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Project Resource Budget','N',TO_TIMESTAMP('2022-05-30 13:51:49','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-05-30T10:51:49.614Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541506 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-05-30T10:51:49.645Z
/* DDL */  select update_window_translation_from_ad_element(580947) 
;

-- 2022-05-30T10:51:49.658Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541506
;

-- 2022-05-30T10:51:49.661Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541506)
;

-- Tab: Project Resource Budget -> Projekt
-- Table: C_Project
-- 2022-05-30T11:34:46.515Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,IncludedTabNewRecordInputMode,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,1349,572687,0,217,546269,203,541506,'Y',TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100,'Define Project','D','N','The Project Tab is used to define the Value, Name and Description for each project.  It also is defines the tracks the amounts assigned to, committed to and used for this project. Note that when the project Type is changed, the Phases and Tasks are re-created.','A','Y','N','Y','Y','Y','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Projekt','N',10,0,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:46.517Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546269 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-05-30T11:34:46.548Z
/* DDL */  select update_tab_translation_from_ad_element(572687) 
;

-- 2022-05-30T11:34:46.562Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546269)
;

-- 2022-05-30T11:34:46.569Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546269
;

-- 2022-05-30T11:34:46.569Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546269, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 546197
;

-- Field: Project Resource Budget -> Projekt -> Generate To
-- Column: C_Project.GenerateTo
-- 2022-05-30T11:34:46.681Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5747,696858,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100,'Generate To',23,'@IsSummary@=N','D',0,'Y','N','N','N','N','N','N','Y','Generate To',10,0,1,1,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:46.682Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:46.685Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1491) 
;

-- 2022-05-30T11:34:46.689Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696858
;

-- 2022-05-30T11:34:46.689Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696858)
;

-- 2022-05-30T11:34:46.691Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696858
;

-- 2022-05-30T11:34:46.691Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696858, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693830
;

-- Field: Project Resource Budget -> Projekt -> Zusage ist Obergrenze
-- Column: C_Project.IsCommitCeiling
-- 2022-05-30T11:34:46.779Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8978,696859,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100,'Betrag / Menge der Zusage ist die Obergrenze für die Abrechnung',1,'@IsSummary@=N & @IsCommitment@=Y','D','Zusage-Betrag und -Menge sind maximal abrechenbarer Betrag und Menge. Ignoriert, wenn Betrag oder Menge gleich 0.',0,'Y','N','N','N','N','N','N','Y','Zusage ist Obergrenze',20,0,1,1,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:46.779Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:46.780Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2077) 
;

-- 2022-05-30T11:34:46.783Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696859
;

-- 2022-05-30T11:34:46.783Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696859)
;

-- 2022-05-30T11:34:46.784Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696859
;

-- 2022-05-30T11:34:46.785Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696859, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693831
;

-- Field: Project Resource Budget -> Projekt -> Verarbeitet
-- Column: C_Project.Processed
-- 2022-05-30T11:34:46.872Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5749,696860,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','Verarbeitet',30,0,1,1,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:46.872Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:46.873Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2022-05-30T11:34:46.888Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696860
;

-- 2022-05-30T11:34:46.888Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696860)
;

-- 2022-05-30T11:34:46.890Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696860
;

-- 2022-05-30T11:34:46.890Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696860, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693832
;

-- Field: Project Resource Budget -> Projekt -> Project Category
-- Column: C_Project.ProjectCategory
-- 2022-05-30T11:34:46.980Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9856,696861,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100,'Project Category',14,'D','The Project Category determines the behavior of the project:
General - no special accounting, e.g. for Presales or general tracking
Service - no special accounting, e.g. for Service/Charge projects
Work Order - creates Project/Job WIP transactions - ability to issue material
Asset - create Project Asset transactions - ability to issue material
',0,'Y','N','N','N','N','N','N','N','Project Category',40,0,1,1,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:46.981Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:46.982Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2179) 
;

-- 2022-05-30T11:34:46.985Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696861
;

-- 2022-05-30T11:34:46.985Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696861)
;

-- 2022-05-30T11:34:46.988Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696861
;

-- 2022-05-30T11:34:46.988Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696861, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693833
;

-- Field: Project Resource Budget -> Projekt -> Projekt
-- Column: C_Project.C_Project_ID
-- 2022-05-30T11:34:47.078Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1349,696862,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',14,'D','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Projekt',50,0,1,1,TO_TIMESTAMP('2022-05-30 14:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:47.079Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:47.080Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-05-30T11:34:47.092Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696862
;

-- 2022-05-30T11:34:47.092Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696862)
;

-- 2022-05-30T11:34:47.094Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696862
;

-- 2022-05-30T11:34:47.094Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696862, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693834
;

-- Field: Project Resource Budget -> Projekt -> Commitment
-- Column: C_Project.IsCommitment
-- 2022-05-30T11:34:47.181Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3904,696863,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:47','YYYY-MM-DD HH24:MI:SS'),100,'Is this document a (legal) commitment?',1,'@IsSummary@=N','D','Commitment indicates if the document is legally binding.',0,'Y','N','N','N','N','N','N','N','Commitment',60,0,1,1,TO_TIMESTAMP('2022-05-30 14:34:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:47.182Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:47.183Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1101) 
;

-- 2022-05-30T11:34:47.185Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696863
;

-- 2022-05-30T11:34:47.185Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696863)
;

-- 2022-05-30T11:34:47.187Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696863
;

-- 2022-05-30T11:34:47.188Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696863, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693835
;

-- Field: Project Resource Budget -> Projekt -> Mandant
-- Column: C_Project.AD_Client_ID
-- 2022-05-30T11:34:47.278Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1351,696864,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:47','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2022-05-30 14:34:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:47.279Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:47.280Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-05-30T11:34:47.513Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696864
;

-- 2022-05-30T11:34:47.513Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696864)
;

-- 2022-05-30T11:34:47.515Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696864
;

-- 2022-05-30T11:34:47.515Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696864, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693836
;

-- Field: Project Resource Budget -> Projekt -> Sektion
-- Column: C_Project.AD_Org_ID
-- 2022-05-30T11:34:47.603Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1350,696865,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:47','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2022-05-30 14:34:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:47.604Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:47.605Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-05-30T11:34:47.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696865
;

-- 2022-05-30T11:34:47.820Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696865)
;

-- 2022-05-30T11:34:47.823Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696865
;

-- 2022-05-30T11:34:47.824Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696865, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693837
;

-- Field: Project Resource Budget -> Projekt -> Projekt Nummer
-- Column: C_Project.Value
-- 2022-05-30T11:34:47.916Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2010,696866,577009,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:47','YYYY-MM-DD HH24:MI:SS'),100,20,'D',0,'Y','Y','Y','N','N','N','N','N','Projekt Nummer',30,30,-1,1,1,TO_TIMESTAMP('2022-05-30 14:34:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:47.916Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:47.917Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577009) 
;

-- 2022-05-30T11:34:47.918Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696866
;

-- 2022-05-30T11:34:47.918Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696866)
;

-- 2022-05-30T11:34:47.920Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696866
;

-- 2022-05-30T11:34:47.920Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696866, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693838
;

-- Field: Project Resource Budget -> Projekt -> Kundenbetreuer
-- Column: C_Project.SalesRep_ID
-- 2022-05-30T11:34:48.006Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5752,696867,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:47','YYYY-MM-DD HH24:MI:SS'),100,'',14,'@IsSummary@=N','D','',0,'Y','Y','Y','Y','N','N','N','Y','Kundenbetreuer',40,40,1,1,TO_TIMESTAMP('2022-05-30 14:34:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:48.007Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:48.008Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063) 
;

-- 2022-05-30T11:34:48.020Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696867
;

-- 2022-05-30T11:34:48.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696867)
;

-- 2022-05-30T11:34:48.021Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696867
;

-- 2022-05-30T11:34:48.022Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696867, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693839
;

-- Field: Project Resource Budget -> Projekt -> Name
-- Column: C_Project.Name
-- 2022-05-30T11:34:48.110Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1356,696868,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100,'',60,'D','',0,'Y','Y','Y','N','N','N','N','N','Name',50,50,999,1,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:48.111Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:48.111Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-05-30T11:34:48.170Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696868
;

-- 2022-05-30T11:34:48.170Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696868)
;

-- 2022-05-30T11:34:48.172Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696868
;

-- 2022-05-30T11:34:48.172Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696868, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693840
;

-- Field: Project Resource Budget -> Projekt -> Beschreibung
-- Column: C_Project.Description
-- 2022-05-30T11:34:48.262Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1358,696869,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100,60,'D',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',60,60,999,1,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:48.263Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:48.264Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-05-30T11:34:48.330Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696869
;

-- 2022-05-30T11:34:48.330Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696869)
;

-- 2022-05-30T11:34:48.332Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696869
;

-- 2022-05-30T11:34:48.332Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696869, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693841
;

-- Field: Project Resource Budget -> Projekt -> Aktiv
-- Column: C_Project.IsActive
-- 2022-05-30T11:34:48.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1352,696870,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',70,70,1,1,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:48.420Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:48.421Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-05-30T11:34:48.767Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696870
;

-- 2022-05-30T11:34:48.767Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696870)
;

-- 2022-05-30T11:34:48.769Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696870
;

-- 2022-05-30T11:34:48.769Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696870, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693842
;

-- Field: Project Resource Budget -> Projekt -> Zusammenfassungseintrag
-- Column: C_Project.IsSummary
-- 2022-05-30T11:34:48.863Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1360,696871,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag',1,'D','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.',0,'Y','Y','Y','N','N','N','N','Y','Zusammenfassungseintrag',80,80,1,1,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:48.863Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:48.864Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(416) 
;

-- 2022-05-30T11:34:48.873Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696871
;

-- 2022-05-30T11:34:48.873Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696871)
;

-- 2022-05-30T11:34:48.875Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696871
;

-- 2022-05-30T11:34:48.875Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696871, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693843
;

-- Field: Project Resource Budget -> Projekt -> Notiz
-- Column: C_Project.Note
-- 2022-05-30T11:34:48.953Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5750,696872,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',60,'D','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',0,'Y','Y','Y','N','N','N','N','N','Notiz',90,90,999,1,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:48.954Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:48.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2022-05-30T11:34:48.958Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696872
;

-- 2022-05-30T11:34:48.959Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696872)
;

-- 2022-05-30T11:34:48.960Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696872
;

-- 2022-05-30T11:34:48.960Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696872, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693844
;

-- Field: Project Resource Budget -> Projekt -> Line Level
-- Column: C_Project.ProjectLineLevel
-- 2022-05-30T11:34:49.050Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15469,696873,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100,'Project Line Level',1,'@IsSummary@=N','D','Level on which Project Lines are maintained',0,'Y','Y','Y','N','N','N','N','N','Line Level',100,100,1,1,TO_TIMESTAMP('2022-05-30 14:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:49.050Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:49.052Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3035) 
;

-- 2022-05-30T11:34:49.053Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696873
;

-- 2022-05-30T11:34:49.053Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696873)
;

-- 2022-05-30T11:34:49.055Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696873
;

-- 2022-05-30T11:34:49.055Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696873, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693845
;

-- Field: Project Resource Budget -> Projekt -> Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-05-30T11:34:49.137Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8757,696874,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Type of the project',23,'','D','Type of the project with optional phases of the project with standard performance information',0,'Y','Y','Y','N','N','N','N','N','Projektart',110,110,1,1,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:49.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:49.138Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2033) 
;

-- 2022-05-30T11:34:49.139Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696874
;

-- 2022-05-30T11:34:49.140Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696874)
;

-- 2022-05-30T11:34:49.141Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696874
;

-- 2022-05-30T11:34:49.141Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696874, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693846
;

-- Field: Project Resource Budget -> Projekt -> Standard-Phase
-- Column: C_Project.C_Phase_ID
-- 2022-05-30T11:34:49.232Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8755,696875,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Status oder Phase dieser Projektart',14,'@IsSummary@=N','D','Phase of the project with standard performance information with standard work',0,'Y','Y','Y','N','N','N','N','Y','Standard-Phase',120,120,1,1,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:49.232Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:49.233Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2032) 
;

-- 2022-05-30T11:34:49.235Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696875
;

-- 2022-05-30T11:34:49.235Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696875)
;

-- 2022-05-30T11:34:49.236Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696875
;

-- 2022-05-30T11:34:49.237Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696875, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693847
;

-- Field: Project Resource Budget -> Projekt -> Datum Auftragseingang
-- Column: C_Project.DateContract
-- 2022-05-30T11:34:49.322Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5745,696876,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftragseingangs',14,'@IsSummary@=N','D','The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.',0,'Y','Y','Y','N','N','N','N','N','Datum Auftragseingang',130,130,1,1,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:49.322Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:49.323Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1556) 
;

-- 2022-05-30T11:34:49.325Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696876
;

-- 2022-05-30T11:34:49.325Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696876)
;

-- 2022-05-30T11:34:49.326Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696876
;

-- 2022-05-30T11:34:49.326Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696876, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693848
;

-- Field: Project Resource Budget -> Projekt -> Projektabschluss
-- Column: C_Project.DateFinish
-- 2022-05-30T11:34:49.418Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5746,696877,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Finish or (planned) completion date',14,'@IsSummary@=N','D','Dieses Datum gibt das erwartete oder tatsächliche Projektende an',0,'Y','Y','Y','N','N','N','N','Y','Projektabschluss',140,140,1,1,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:49.419Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:49.419Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1557) 
;

-- 2022-05-30T11:34:49.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696877
;

-- 2022-05-30T11:34:49.421Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696877)
;

-- 2022-05-30T11:34:49.422Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696877
;

-- 2022-05-30T11:34:49.422Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696877, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693849
;

-- Field: Project Resource Budget -> Projekt -> Geschäftspartner
-- Column: C_Project.C_BPartner_ID
-- 2022-05-30T11:34:49.514Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_FieldGroup_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3902,696878,104,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'@IsSummary@=N','D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',150,150,1,1,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:49.515Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696878 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:49.516Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2022-05-30T11:34:49.524Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696878
;

-- 2022-05-30T11:34:49.524Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696878)
;

-- 2022-05-30T11:34:49.526Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696878
;

-- 2022-05-30T11:34:49.526Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696878, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693850
;

-- Field: Project Resource Budget -> Projekt -> BPartner (Agent)
-- Column: C_Project.C_BPartnerSR_ID
-- 2022-05-30T11:34:49.614Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14095,696879,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner (Agent or Sales Rep)',10,'@IsSummary@=N','D',0,'Y','Y','Y','N','N','N','N','Y','BPartner (Agent)',160,160,1,1,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:49.615Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:49.616Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2810) 
;

-- 2022-05-30T11:34:49.617Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696879
;

-- 2022-05-30T11:34:49.617Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696879)
;

-- 2022-05-30T11:34:49.618Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696879
;

-- 2022-05-30T11:34:49.619Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696879, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693851
;

-- Field: Project Resource Budget -> Projekt -> Standort
-- Column: C_Project.C_BPartner_Location_ID
-- 2022-05-30T11:34:49.720Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5798,696880,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',14,'@IsSummary@=N','D','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','N','N','N','N','N','Standort',170,170,1,1,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:49.721Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:49.721Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2022-05-30T11:34:49.725Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696880
;

-- 2022-05-30T11:34:49.725Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696880)
;

-- 2022-05-30T11:34:49.726Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696880
;

-- 2022-05-30T11:34:49.727Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696880, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693852
;

-- Field: Project Resource Budget -> Projekt -> Ansprechpartner
-- Column: C_Project.AD_User_ID
-- 2022-05-30T11:34:49.813Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5797,696881,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'@IsSummary@=N','D','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','Y','Ansprechpartner',180,180,1,1,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:49.814Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:49.815Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2022-05-30T11:34:49.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696881
;

-- 2022-05-30T11:34:49.820Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696881)
;

-- 2022-05-30T11:34:49.821Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696881
;

-- 2022-05-30T11:34:49.822Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696881, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693853
;

-- Field: Project Resource Budget -> Projekt -> Zahlungsbedingung
-- Column: C_Project.C_PaymentTerm_ID
-- 2022-05-30T11:34:49.914Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5796,696882,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',14,'@IsSummary@=N','D','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','Y','Y','N','N','N','N','N','Zahlungsbedingung',190,190,1,1,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:49.914Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696882 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:49.915Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(204) 
;

-- 2022-05-30T11:34:49.923Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696882
;

-- 2022-05-30T11:34:49.924Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696882)
;

-- 2022-05-30T11:34:49.926Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696882
;

-- 2022-05-30T11:34:49.926Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696882, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693854
;

-- Field: Project Resource Budget -> Projekt -> Referenz
-- Column: C_Project.POReference
-- 2022-05-30T11:34:50.015Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5794,696883,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',20,'@IsSummary@=N','D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','Y','Y','N','N','N','N','Y','Referenz',200,200,1,1,TO_TIMESTAMP('2022-05-30 14:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.015Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696883 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.016Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2022-05-30T11:34:50.024Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696883
;

-- 2022-05-30T11:34:50.024Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696883)
;

-- 2022-05-30T11:34:50.025Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696883
;

-- 2022-05-30T11:34:50.026Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696883, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693855
;

-- Field: Project Resource Budget -> Projekt -> Lager
-- Column: C_Project.M_Warehouse_ID
-- 2022-05-30T11:34:50.109Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9637,696884,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',14,'@IsSummary@=N','D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','Y','Y','N','N','N','N','N','Lager',210,210,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.110Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696884 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.110Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2022-05-30T11:34:50.121Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696884
;

-- 2022-05-30T11:34:50.122Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696884)
;

-- 2022-05-30T11:34:50.123Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696884
;

-- 2022-05-30T11:34:50.123Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696884, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693856
;

-- Field: Project Resource Budget -> Projekt -> Werbemassnahme
-- Column: C_Project.C_Campaign_ID
-- 2022-05-30T11:34:50.221Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5795,696885,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',14,'@IsSummary@=N & @$Element_MC@=Y','D','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','Y','Y','N','N','N','N','Y','Werbemassnahme',220,220,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.222Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696885 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.223Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550) 
;

-- 2022-05-30T11:34:50.235Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696885
;

-- 2022-05-30T11:34:50.235Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696885)
;

-- 2022-05-30T11:34:50.238Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696885
;

-- 2022-05-30T11:34:50.238Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696885, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693857
;

-- Field: Project Resource Budget -> Projekt -> Version Preisliste
-- Column: C_Project.M_PriceList_Version_ID
-- 2022-05-30T11:34:50.316Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5753,696886,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste',14,'@IsSummary@=N','D','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ',0,'Y','Y','Y','N','N','N','N','N','Version Preisliste',230,230,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.317Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696886 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.318Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(450) 
;

-- 2022-05-30T11:34:50.321Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696886
;

-- 2022-05-30T11:34:50.321Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696886)
;

-- 2022-05-30T11:34:50.323Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696886
;

-- 2022-05-30T11:34:50.323Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696886, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693858
;

-- Field: Project Resource Budget -> Projekt -> Währung
-- Column: C_Project.C_Currency_ID
-- 2022-05-30T11:34:50.412Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3901,696887,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',14,'@IsSummary@=N','D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','Y','N','N','N','N','Y','Währung',240,240,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.412Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696887 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.413Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2022-05-30T11:34:50.437Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696887
;

-- 2022-05-30T11:34:50.438Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696887)
;

-- 2022-05-30T11:34:50.440Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696887
;

-- 2022-05-30T11:34:50.440Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696887, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693859
;

-- Field: Project Resource Budget -> Projekt -> VK Total
-- Column: C_Project.PlannedAmt
-- 2022-05-30T11:34:50.538Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_FieldGroup_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5755,696888,103,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'VK Total',26,'@IsSummary@=N','D','The Planned Amount indicates the anticipated amount for this project or project line.',0,'Y','Y','Y','N','N','N','N','N','VK Total',250,250,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.539Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696888 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.540Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1564) 
;

-- 2022-05-30T11:34:50.542Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696888
;

-- 2022-05-30T11:34:50.542Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696888)
;

-- 2022-05-30T11:34:50.543Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696888
;

-- 2022-05-30T11:34:50.544Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696888, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693860
;

-- Field: Project Resource Budget -> Projekt -> Geplante Menge
-- Column: C_Project.PlannedQty
-- 2022-05-30T11:34:50.626Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_FieldGroup_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5756,696889,103,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'',26,'@IsSummary@=N','D','',0,'Y','Y','Y','N','N','N','N','Y','Geplante Menge',260,260,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.627Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696889 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.628Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1568) 
;

-- 2022-05-30T11:34:50.629Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696889
;

-- 2022-05-30T11:34:50.629Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696889)
;

-- 2022-05-30T11:34:50.630Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696889
;

-- 2022-05-30T11:34:50.631Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696889, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693861
;

-- Field: Project Resource Budget -> Projekt -> DB1
-- Column: C_Project.PlannedMarginAmt
-- 2022-05-30T11:34:50.718Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5757,696890,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'Project''s planned margin amount',26,'@IsSummary@=N','D','The Planned Margin Amount indicates the anticipated margin amount for this project or project line.',0,'Y','Y','Y','N','N','N','N','N','DB1',270,270,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.719Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696890 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.720Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1566) 
;

-- 2022-05-30T11:34:50.721Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696890
;

-- 2022-05-30T11:34:50.721Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696890)
;

-- 2022-05-30T11:34:50.723Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696890
;

-- 2022-05-30T11:34:50.723Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696890, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693862
;

-- Field: Project Resource Budget -> Projekt -> Rechnungsstellung
-- Column: C_Project.ProjInvoiceRule
-- 2022-05-30T11:34:50.798Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15449,696891,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Rule for the project',1,'@IsSummary@=N','D','The Invoice Rule for the project determines how orders (and consequently invoices) are created.  The selection on project level can be overwritten on Phase or Task',0,'Y','Y','Y','N','N','N','N','Y','Rechnungsstellung',280,280,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.801Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3031) 
;

-- 2022-05-30T11:34:50.802Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696891
;

-- 2022-05-30T11:34:50.802Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696891)
;

-- 2022-05-30T11:34:50.803Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696891
;

-- 2022-05-30T11:34:50.804Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696891, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693863
;

-- Field: Project Resource Budget -> Projekt -> Committed Amount
-- Column: C_Project.CommittedAmt
-- 2022-05-30T11:34:50.890Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3907,696892,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'The (legal) commitment amount',26,'@IsSummary@=N','D','The commitment amount is independent from the planned amount. You would use the planned amount for your realistic estimation, which might be higher or lower than the commitment amount.',0,'Y','Y','Y','N','N','N','N','N','Committed Amount',290,290,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.891Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.892Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1081) 
;

-- 2022-05-30T11:34:50.894Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696892
;

-- 2022-05-30T11:34:50.894Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696892)
;

-- 2022-05-30T11:34:50.896Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696892
;

-- 2022-05-30T11:34:50.896Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696892, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693864
;

-- Field: Project Resource Budget -> Projekt -> Committed Quantity
-- Column: C_Project.CommittedQty
-- 2022-05-30T11:34:50.987Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8759,696893,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'The (legal) commitment Quantity',26,'@IsSummary@=N','D','The commitment amount is independent from the planned amount. You would use the planned amount for your realistic estimation, which might be higher or lower than the commitment amount.',0,'Y','Y','Y','N','N','N','N','Y','Committed Quantity',300,300,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:50.988Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:50.989Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2036) 
;

-- 2022-05-30T11:34:50.990Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696893
;

-- 2022-05-30T11:34:50.990Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696893)
;

-- 2022-05-30T11:34:50.992Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696893
;

-- 2022-05-30T11:34:50.992Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696893, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693865
;

-- Field: Project Resource Budget -> Projekt -> Invoiced Amount
-- Column: C_Project.InvoicedAmt
-- 2022-05-30T11:34:51.083Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_FieldGroup_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8753,696894,105,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100,'The amount invoiced',26,'@IsSummary@=N','D','The amount invoiced',0,'Y','Y','Y','N','N','N','Y','N','Invoiced Amount',310,310,1,1,TO_TIMESTAMP('2022-05-30 14:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:51.083Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696894 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:51.084Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2044) 
;

-- 2022-05-30T11:34:51.085Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696894
;

-- 2022-05-30T11:34:51.086Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696894)
;

-- 2022-05-30T11:34:51.087Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696894
;

-- 2022-05-30T11:34:51.087Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696894, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693866
;

-- Field: Project Resource Budget -> Projekt -> Berechnete Menge
-- Column: C_Project.InvoicedQty
-- 2022-05-30T11:34:51.174Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8756,696895,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,'The quantity invoiced',26,'@IsSummary@=N','D',0,'Y','Y','Y','N','N','N','Y','Y','Berechnete Menge',320,320,1,1,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:51.175Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696895 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:51.175Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2045) 
;

-- 2022-05-30T11:34:51.177Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696895
;

-- 2022-05-30T11:34:51.177Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696895)
;

-- 2022-05-30T11:34:51.178Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696895
;

-- 2022-05-30T11:34:51.179Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696895, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693867
;

-- Field: Project Resource Budget -> Projekt -> Project Balance
-- Column: C_Project.ProjectBalanceAmt
-- 2022-05-30T11:34:51.268Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8758,696896,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,'Total Project Balance',26,'@IsSummary@=N','D','The project balance is the sum of all invoices and payments',0,'Y','Y','Y','N','N','N','Y','N','Project Balance',330,330,1,1,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:51.269Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696896 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:51.270Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2054) 
;

-- 2022-05-30T11:34:51.271Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696896
;

-- 2022-05-30T11:34:51.271Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696896)
;

-- 2022-05-30T11:34:51.272Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696896
;

-- 2022-05-30T11:34:51.272Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696896, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693868
;

-- Field: Project Resource Budget -> Projekt -> Copy From
-- Column: C_Project.CopyFrom
-- 2022-05-30T11:34:51.347Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8754,696897,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,'Copy From Record',23,'@IsSummary@=N','D','Copy From Record',0,'Y','Y','Y','N','N','N','N','N','Copy From',340,340,1,1,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:51.348Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696897 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:51.348Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2037) 
;

-- 2022-05-30T11:34:51.350Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696897
;

-- 2022-05-30T11:34:51.350Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696897)
;

-- 2022-05-30T11:34:51.351Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696897
;

-- 2022-05-30T11:34:51.352Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696897, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693869
;

-- Field: Project Resource Budget -> Projekt -> Process Now
-- Column: C_Project.Processing
-- 2022-05-30T11:34:51.438Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9861,696898,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,23,'@IsSummary@=N','D',0,'Y','Y','Y','N','N','N','N','N','Process Now',350,350,1,1,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:51.438Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696898 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:51.439Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2022-05-30T11:34:51.462Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696898
;

-- 2022-05-30T11:34:51.462Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696898)
;

-- 2022-05-30T11:34:51.463Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696898
;

-- 2022-05-30T11:34:51.464Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696898, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693870
;

-- Field: Project Resource Budget -> Projekt -> Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-05-30T11:34:51.558Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559693,696899,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','Y','N','N','N','N','N','N','Projektstatus',360,1,1,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:51.559Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:51.560Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544077) 
;

-- 2022-05-30T11:34:51.563Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696899
;

-- 2022-05-30T11:34:51.563Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696899)
;

-- 2022-05-30T11:34:51.565Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696899
;

-- 2022-05-30T11:34:51.565Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696899, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693871
;

-- Field: Project Resource Budget -> Projekt -> Externe Projektreferenz
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-05-30T11:34:51.654Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582993,696900,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Externe Projektreferenz',370,360,0,1,1,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:51.655Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696900 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:51.655Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580859) 
;

-- 2022-05-30T11:34:51.656Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696900
;

-- 2022-05-30T11:34:51.656Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696900)
;

-- 2022-05-30T11:34:51.657Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696900
;

-- 2022-05-30T11:34:51.658Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696900, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693872
;

-- Field: Project Resource Budget -> Projekt -> Elternprojekt
-- Column: C_Project.C_Project_Parent_ID
-- 2022-05-30T11:34:51.745Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582994,696901,0,546269,0,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Elternprojekt',380,370,0,1,1,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:51.746Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696901 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-30T11:34:51.746Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580860) 
;

-- 2022-05-30T11:34:51.747Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696901
;

-- 2022-05-30T11:34:51.747Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696901)
;

-- 2022-05-30T11:34:51.748Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 696901
;

-- 2022-05-30T11:34:51.749Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 696901, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 693873
;

-- 2022-05-30T11:34:51.882Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546269,544914,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,'1000028')
;

-- 2022-05-30T11:34:51.883Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544914 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-30T11:34:51.886Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544914
;

-- 2022-05-30T11:34:51.887Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544914, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544835
;

-- 2022-05-30T11:34:52.040Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545940,544914,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-30 14:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:52.166Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545940,549125,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,'primary',TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-05-30T11:34:52.307Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696874,0,546269,607793,549125,'F',TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektart',5,0,0,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Projektnummer
-- Column: C_Project.Value
-- 2022-05-30T11:34:52.405Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696866,0,546269,607794,549125,'F',TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N','Projektnummer',10,0,60,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Name
-- Column: C_Project.Name
-- 2022-05-30T11:34:52.494Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696868,0,546269,607795,549125,'F',TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N','Name',20,0,50,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-05-30T11:34:52.589Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696899,0,546269,607796,549125,'F',TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektstatus',25,0,0,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Externe Projektreferenz
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-05-30T11:34:52.679Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696900,0,546269,607797,549125,'F',TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N',0,'Externe Projektreferenz',35,0,70,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Elternprojekt
-- Column: C_Project.C_Project_Parent_ID
-- 2022-05-30T11:34:52.770Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696901,0,546269,607798,549125,'F',TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Elternprojekt',45,0,0,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:52.856Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545940,549126,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Beschreibung
-- Column: C_Project.Description
-- 2022-05-30T11:34:52.951Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696869,0,546269,607799,549126,'F',TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N','Beschreibung',10,0,30,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:53.030Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545941,544914,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-05-30 14:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:53.115Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545941,549127,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','active',5,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Aktiv
-- Column: C_Project.IsActive
-- 2022-05-30T11:34:53.213Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696870,0,546269,607800,549127,'F',TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N','Aktiv',10,0,10,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:53.303Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545941,549128,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','partner',10,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Kunde
-- Column: C_Project.C_BPartner_ID
-- 2022-05-30T11:34:53.396Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696878,0,546269,607801,549128,'F',TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N','Kunde',10,0,40,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Aussendienst
-- Column: C_Project.SalesRep_ID
-- 2022-05-30T11:34:53.477Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696867,0,546269,607802,549128,'F',TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N','Aussendienst',30,0,20,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Datum AE
-- Column: C_Project.DateContract
-- 2022-05-30T11:34:53.565Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696876,0,546269,607803,549128,'F',TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Datum AE',40,0,0,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Projektabschluss
-- Column: C_Project.DateFinish
-- 2022-05-30T11:34:53.653Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696877,0,546269,607804,549128,'F',TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektabschluss',50,0,0,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:34:53.735Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545941,549129,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Mandant
-- Column: C_Project.AD_Client_ID
-- 2022-05-30T11:34:53.828Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696864,0,546269,607805,549129,'F',TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Resource Budget -> Projekt.Sektion
-- Column: C_Project.AD_Org_ID
-- 2022-05-30T11:34:53.919Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696865,0,546269,607806,549129,'F',TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-05-30 14:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Project Resource Budget -> Projekt
-- Table: C_Project
-- 2022-05-30T11:35:18.636Z
UPDATE AD_Tab SET AD_Column_ID=NULL, AD_Process_ID=NULL,Updated=TO_TIMESTAMP('2022-05-30 14:35:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546269
;

-- 2022-05-30T11:39:06.908Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,580947,541957,0,541506,TO_TIMESTAMP('2022-05-30 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Project Resource Budget','Y','N','N','N','N','Project Resource Budget',TO_TIMESTAMP('2022-05-30 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-30T11:39:06.909Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541957 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-05-30T11:39:06.911Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541957, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541957)
;

-- 2022-05-30T11:39:06.920Z
/* DDL */  select update_menu_translation_from_ad_element(580947) 
;

-- Reordering children of `Project Management`
-- Node name: `Project Type (C_ProjectType)`
-- 2022-05-30T11:39:14.969Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2022-05-30T11:39:14.971Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Project (C_Project)`
-- 2022-05-30T11:39:14.971Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541577 AND AD_Tree_ID=10
;

-- Node name: `Project Resource Budget`
-- 2022-05-30T11:39:14.973Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541957 AND AD_Tree_ID=10
;





































-- 2022-05-30T11:53:30.254Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543200,288,TO_TIMESTAMP('2022-05-30 14:53:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Budget / Forecast',TO_TIMESTAMP('2022-05-30 14:53:30','YYYY-MM-DD HH24:MI:SS'),100,'B','Budget')
;

-- 2022-05-30T11:53:30.257Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543200 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Tab: Project Resource Budget -> Projekt
-- Table: C_Project
-- 2022-05-30T12:03:12.840Z
UPDATE AD_Tab SET WhereClause='ProjectCategory=''B''',Updated=TO_TIMESTAMP('2022-05-30 15:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546269
;

















-- 2022-05-30T12:34:51.977Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540582,'C_ProjectType.ProjectCategory=''B''',TO_TIMESTAMP('2022-05-30 15:34:51','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','C_ProjectType_ID with ProjectCategory=Budget','S',TO_TIMESTAMP('2022-05-30 15:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_ProjectType_ID with ProjectCategory=Budget
-- 2022-05-30T12:42:07.437Z
UPDATE AD_Val_Rule SET Description='',Updated=TO_TIMESTAMP('2022-05-30 15:42:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540582
;

-- Field: Project Resource Budget -> Projekt -> Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-05-30T12:43:27.837Z
UPDATE AD_Field SET AD_Val_Rule_ID=540582,Updated=TO_TIMESTAMP('2022-05-30 15:43:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696874
;
















-- 2022-05-30T12:47:18.273Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696897
;

-- Field: Project Resource Budget -> Projekt -> Copy From
-- Column: C_Project.CopyFrom
-- 2022-05-30T12:47:18.278Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=696897
;

-- 2022-05-30T12:47:18.285Z
DELETE FROM AD_Field WHERE AD_Field_ID=696897
;

-- 2022-05-30T12:47:27.508Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696858
;

-- Field: Project Resource Budget -> Projekt -> Generate To
-- Column: C_Project.GenerateTo
-- 2022-05-30T12:47:27.509Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=696858
;

-- 2022-05-30T12:47:27.511Z
DELETE FROM AD_Field WHERE AD_Field_ID=696858
;

-- UI Element: Project Resource Budget -> Projekt.Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-05-30T12:49:15.260Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=607796
;

-- 2022-05-30T12:49:30.464Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696899
;

-- Field: Project Resource Budget -> Projekt -> Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-05-30T12:49:30.468Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=696899
;

-- 2022-05-30T12:49:30.470Z
DELETE FROM AD_Field WHERE AD_Field_ID=696899
;

-- Field: Project Resource Budget -> Projekt -> Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-05-30T12:53:26.804Z
UPDATE AD_Field SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-05-30 15:53:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696874
;

















