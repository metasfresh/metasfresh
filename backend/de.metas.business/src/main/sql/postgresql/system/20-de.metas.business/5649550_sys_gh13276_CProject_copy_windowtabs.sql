-- 2022-08-04T12:13:38.408Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581212,0,TO_TIMESTAMP('2022-08-04 14:13:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prüf Projekt','Prüf Projekt',TO_TIMESTAMP('2022-08-04 14:13:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-04T12:13:38.414Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581212 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-08-04T12:13:45.844Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-04 14:13:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581212 AND AD_Language='de_DE'
;

-- 2022-08-04T12:13:45.875Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581212,'de_DE')
;

-- 2022-08-04T12:13:45.886Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581212,'de_DE')
;

-- 2022-08-04T12:13:56.816Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Test Project', PrintName='Test Project',Updated=TO_TIMESTAMP('2022-08-04 14:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581212 AND AD_Language='en_US'
;

-- 2022-08-04T12:13:56.818Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581212,'en_US')
;

-- Window: Prüf Projekt, InternalName=null
-- 2022-08-04T12:32:22.152Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581212,0,541586,TO_TIMESTAMP('2022-08-04 14:32:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Prüf Projekt','N',TO_TIMESTAMP('2022-08-04 14:32:21','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-08-04T12:32:22.153Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541586 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-08-04T12:32:22.155Z
/* DDL */  select update_window_translation_from_ad_element(581212)
;

-- 2022-08-04T12:32:22.157Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541586
;

-- 2022-08-04T12:32:22.157Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541586)
;

-- Tab: Prüf Projekt -> Projekt
-- Table: C_Project
-- 2022-08-05T09:47:45.460Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,572687,0,546542,203,541586,'Y',TO_TIMESTAMP('2022-08-05 11:47:45','YYYY-MM-DD HH24:MI:SS'),100,'Define Project','D','N','The Project Tab is used to define the Value, Name and Description for each project.  It also is defines the tracks the amounts assigned to, committed to and used for this project. Note that when the project Type is changed, the Phases and Tasks are re-created.','A','workOrderProject','Y','N','Y','Y','Y','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Projekt','N',10,0,TO_TIMESTAMP('2022-08-05 11:47:45','YYYY-MM-DD HH24:MI:SS'),100,'ProjectCategory=''W''')
;

-- 2022-08-05T09:47:45.461Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546542 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-08-05T09:47:45.463Z
/* DDL */  select update_tab_translation_from_ad_element(572687)
;

-- 2022-08-05T09:47:45.466Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546542)
;

-- 2022-08-05T09:47:45.469Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546542
;

-- 2022-08-05T09:47:45.470Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546542, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 546289
;

-- Field: Prüf Projekt -> Projekt -> Zusage ist Obergrenze
-- Column: C_Project.IsCommitCeiling
-- 2022-08-05T09:47:45.567Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8978,703829,0,546542,0,TO_TIMESTAMP('2022-08-05 11:47:45','YYYY-MM-DD HH24:MI:SS'),100,'Betrag / Menge der Zusage ist die Obergrenze für die Abrechnung',1,'@IsSummary@=N & @IsCommitment@=Y','D','Zusage-Betrag und -Menge sind maximal abrechenbarer Betrag und Menge. Ignoriert, wenn Betrag oder Menge gleich 0.',0,'Y','N','N','N','N','N','N','Y','Zusage ist Obergrenze',20,0,1,1,TO_TIMESTAMP('2022-08-05 11:47:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:47:45.568Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:47:45.570Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2077)
;

-- 2022-08-05T09:47:45.574Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703829
;

-- 2022-08-05T09:47:45.575Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703829)
;

-- 2022-08-05T09:47:45.578Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703829
;

-- 2022-08-05T09:47:45.579Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703829, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697962
;

-- Field: Prüf Projekt -> Projekt -> Verarbeitet
-- Column: C_Project.Processed
-- 2022-08-05T09:47:45.664Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5749,703830,0,546542,0,TO_TIMESTAMP('2022-08-05 11:47:45','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','Verarbeitet',30,0,1,1,TO_TIMESTAMP('2022-08-05 11:47:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:47:45.665Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:47:45.666Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2022-08-05T09:47:45.682Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703830
;

-- 2022-08-05T09:47:45.683Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703830)
;

-- 2022-08-05T09:47:45.684Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703830
;

-- 2022-08-05T09:47:45.685Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703830, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697963
;

-- Field: Prüf Projekt -> Projekt -> Project Category
-- Column: C_Project.ProjectCategory
-- 2022-08-05T09:47:45.771Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9856,703831,0,546542,0,TO_TIMESTAMP('2022-08-05 11:47:45','YYYY-MM-DD HH24:MI:SS'),100,'Project Category',14,'D','The Project Category determines the behavior of the project:
General - no special accounting, e.g. for Presales or general tracking
Service - no special accounting, e.g. for Service/Charge projects
Work Order - creates Project/Job WIP transactions - ability to issue material
Asset - create Project Asset transactions - ability to issue material
',0,'Y','N','N','N','N','N','N','N','Project Category',40,0,1,1,TO_TIMESTAMP('2022-08-05 11:47:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:47:45.772Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:47:45.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2179)
;

-- 2022-08-05T09:47:45.778Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703831
;

-- 2022-08-05T09:47:45.779Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703831)
;

-- 2022-08-05T09:47:45.781Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703831
;

-- 2022-08-05T09:47:46.001Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703831, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697964
;

-- Field: Prüf Projekt -> Projekt -> Projekt
-- Column: C_Project.C_Project_ID
-- 2022-08-05T09:47:46.077Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1349,703832,0,546542,0,TO_TIMESTAMP('2022-08-05 11:47:46','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',14,'D','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Projekt',50,0,1,1,TO_TIMESTAMP('2022-08-05 11:47:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:47:46.078Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:47:46.080Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2022-08-05T09:47:46.099Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703832
;

-- 2022-08-05T09:47:46.100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703832)
;

-- 2022-08-05T09:47:46.102Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703832
;

-- 2022-08-05T09:47:46.103Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703832, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697965
;

-- Field: Prüf Projekt -> Projekt -> Commitment
-- Column: C_Project.IsCommitment
-- 2022-08-05T09:47:46.190Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3904,703833,0,546542,0,TO_TIMESTAMP('2022-08-05 11:47:46','YYYY-MM-DD HH24:MI:SS'),100,'Is this document a (legal) commitment?',1,'@IsSummary@=N','D','Commitment indicates if the document is legally binding.',0,'Y','N','N','N','N','N','N','N','Commitment',60,0,1,1,TO_TIMESTAMP('2022-08-05 11:47:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:47:46.191Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:47:46.193Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1101)
;

-- 2022-08-05T09:47:46.195Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703833
;

-- 2022-08-05T09:47:46.196Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703833)
;

-- 2022-08-05T09:47:46.199Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703833
;

-- 2022-08-05T09:47:46.199Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703833, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697966
;

-- Field: Prüf Projekt -> Projekt -> Mandant
-- Column: C_Project.AD_Client_ID
-- 2022-08-05T09:47:46.285Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1351,703834,0,546542,0,TO_TIMESTAMP('2022-08-05 11:47:46','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2022-08-05 11:47:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:47:46.287Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:47:46.289Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2022-08-05T09:47:52.231Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703834
;

-- 2022-08-05T09:47:52.234Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703834)
;

-- 2022-08-05T09:47:52.236Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703834
;

-- 2022-08-05T09:47:52.237Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703834, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697967
;

-- Field: Prüf Projekt -> Projekt -> Sektion
-- Column: C_Project.AD_Org_ID
-- 2022-08-05T09:47:52.370Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1350,703835,0,546542,0,TO_TIMESTAMP('2022-08-05 11:47:52','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2022-08-05 11:47:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:47:52.371Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:47:52.372Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2022-08-05T09:47:58.308Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703835
;

-- 2022-08-05T09:47:58.309Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703835)
;

-- 2022-08-05T09:47:58.311Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703835
;

-- 2022-08-05T09:47:58.311Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703835, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697968
;

-- Field: Prüf Projekt -> Projekt -> Projekt Nummer
-- Column: C_Project.Value
-- 2022-08-05T09:47:58.423Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2010,703836,577009,0,546542,0,TO_TIMESTAMP('2022-08-05 11:47:58','YYYY-MM-DD HH24:MI:SS'),100,20,'D',0,'Y','Y','Y','N','N','N','N','N','Projekt Nummer',30,30,-1,1,1,TO_TIMESTAMP('2022-08-05 11:47:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:47:58.424Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:47:58.425Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577009)
;

-- 2022-08-05T09:47:58.426Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703836
;

-- 2022-08-05T09:47:58.427Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703836)
;

-- 2022-08-05T09:47:58.430Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703836
;

-- 2022-08-05T09:47:58.430Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703836, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697969
;

-- Field: Prüf Projekt -> Projekt -> Kundenbetreuer
-- Column: C_Project.SalesRep_ID
-- 2022-08-05T09:47:58.509Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5752,703837,0,546542,0,TO_TIMESTAMP('2022-08-05 11:47:58','YYYY-MM-DD HH24:MI:SS'),100,'',14,'@IsSummary@=N','D','',0,'Y','Y','Y','Y','N','N','N','Y','Kundenbetreuer',40,40,1,1,TO_TIMESTAMP('2022-08-05 11:47:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:47:58.510Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:47:58.512Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063)
;

-- 2022-08-05T09:47:58.649Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703837
;

-- 2022-08-05T09:47:58.650Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703837)
;

-- 2022-08-05T09:47:58.653Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703837
;

-- 2022-08-05T09:47:58.654Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703837, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697970
;

-- Field: Prüf Projekt -> Projekt -> Name
-- Column: C_Project.Name
-- 2022-08-05T09:47:58.733Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1356,703838,0,546542,0,TO_TIMESTAMP('2022-08-05 11:47:58','YYYY-MM-DD HH24:MI:SS'),100,'',60,'D','',0,'Y','Y','Y','N','N','N','N','N','Name',50,50,999,1,TO_TIMESTAMP('2022-08-05 11:47:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:47:58.734Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:47:58.736Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2022-08-05T09:48:00.071Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703838
;

-- 2022-08-05T09:48:00.071Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703838)
;

-- 2022-08-05T09:48:00.073Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703838
;

-- 2022-08-05T09:48:00.074Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703838, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697971
;

-- Field: Prüf Projekt -> Projekt -> Beschreibung
-- Column: C_Project.Description
-- 2022-08-05T09:48:00.174Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1358,703839,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:00','YYYY-MM-DD HH24:MI:SS'),100,60,'D',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',60,60,999,1,TO_TIMESTAMP('2022-08-05 11:48:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:00.175Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:00.177Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2022-08-05T09:48:01.779Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703839
;

-- 2022-08-05T09:48:01.779Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703839)
;

-- 2022-08-05T09:48:01.782Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703839
;

-- 2022-08-05T09:48:01.782Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703839, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697972
;

-- Field: Prüf Projekt -> Projekt -> Aktiv
-- Column: C_Project.IsActive
-- 2022-08-05T09:48:01.870Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1352,703840,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:01','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',70,70,1,1,TO_TIMESTAMP('2022-08-05 11:48:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:01.871Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:01.872Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2022-08-05T09:48:07.779Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703840
;

-- 2022-08-05T09:48:07.780Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703840)
;

-- 2022-08-05T09:48:07.782Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703840
;

-- 2022-08-05T09:48:07.783Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703840, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697973
;

-- Field: Prüf Projekt -> Projekt -> Zusammenfassungseintrag
-- Column: C_Project.IsSummary
-- 2022-08-05T09:48:07.912Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1360,703841,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:07','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag',1,'D','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.',0,'Y','Y','Y','N','N','N','N','Y','Zusammenfassungseintrag',80,80,1,1,TO_TIMESTAMP('2022-08-05 11:48:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:07.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:07.914Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(416)
;

-- 2022-08-05T09:48:08.009Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703841
;

-- 2022-08-05T09:48:08.010Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703841)
;

-- 2022-08-05T09:48:08.012Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703841
;

-- 2022-08-05T09:48:08.013Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703841, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697974
;

-- Field: Prüf Projekt -> Projekt -> Notiz
-- Column: C_Project.Note
-- 2022-08-05T09:48:08.099Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5750,703842,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',60,'D','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',0,'Y','Y','Y','N','N','N','N','N','Notiz',90,90,999,1,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:08.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:08.102Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115)
;

-- 2022-08-05T09:48:08.167Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703842
;

-- 2022-08-05T09:48:08.168Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703842)
;

-- 2022-08-05T09:48:08.170Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703842
;

-- 2022-08-05T09:48:08.171Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703842, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697975
;

-- Field: Prüf Projekt -> Projekt -> Line Level
-- Column: C_Project.ProjectLineLevel
-- 2022-08-05T09:48:08.253Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15469,703843,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100,'Project Line Level',1,'@IsSummary@=N','D','Level on which Project Lines are maintained',0,'Y','Y','Y','N','N','N','N','N','Line Level',100,100,1,1,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:08.254Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:08.255Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3035)
;

-- 2022-08-05T09:48:08.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703843
;

-- 2022-08-05T09:48:08.263Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703843)
;

-- 2022-08-05T09:48:08.265Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703843
;

-- 2022-08-05T09:48:08.266Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703843, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697976
;

-- Field: Prüf Projekt -> Projekt -> Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-08-05T09:48:08.364Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Reference_ID,AD_Tab_ID,AD_Val_Rule_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8757,703844,0,18,546542,540583,0,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100,'Type of the project',23,'','D','Type of the project with optional phases of the project with standard performance information',0,'Y','Y','Y','N','N','N','Y','N','N','Projektart',110,110,1,1,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:08.366Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:08.367Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2033)
;

-- 2022-08-05T09:48:08.388Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703844
;

-- 2022-08-05T09:48:08.389Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703844)
;

-- 2022-08-05T09:48:08.391Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703844
;

-- 2022-08-05T09:48:08.392Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703844, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697977
;

-- Field: Prüf Projekt -> Projekt -> Standard-Phase
-- Column: C_Project.C_Phase_ID
-- 2022-08-05T09:48:08.478Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8755,703845,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100,'Status oder Phase dieser Projektart',14,'@IsSummary@=N','D','Phase of the project with standard performance information with standard work',0,'Y','Y','Y','N','N','N','N','Y','Standard-Phase',120,120,1,1,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:08.479Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:08.480Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2032)
;

-- 2022-08-05T09:48:08.507Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703845
;

-- 2022-08-05T09:48:08.508Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703845)
;

-- 2022-08-05T09:48:08.510Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703845
;

-- 2022-08-05T09:48:08.510Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703845, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697978
;

-- Field: Prüf Projekt -> Projekt -> Datum Auftragseingang
-- Column: C_Project.DateContract
-- 2022-08-05T09:48:08.597Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5745,703846,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftragseingangs',14,'@IsSummary@=N','D','The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.',0,'Y','Y','Y','N','N','N','N','N','Datum Auftragseingang',130,130,1,1,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:08.599Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703846 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:08.600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1556)
;

-- 2022-08-05T09:48:08.606Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703846
;

-- 2022-08-05T09:48:08.606Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703846)
;

-- 2022-08-05T09:48:08.608Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703846
;

-- 2022-08-05T09:48:08.609Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703846, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697979
;

-- Field: Prüf Projekt -> Projekt -> Projektabschluss
-- Column: C_Project.DateFinish
-- 2022-08-05T09:48:08.699Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5746,703847,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100,'Finish or (planned) completion date',14,'@IsSummary@=N','D','Dieses Datum gibt das erwartete oder tatsächliche Projektende an',0,'Y','Y','Y','N','N','N','N','Y','Projektabschluss',140,140,1,1,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:08.700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:08.701Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1557)
;

-- 2022-08-05T09:48:08.718Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703847
;

-- 2022-08-05T09:48:08.719Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703847)
;

-- 2022-08-05T09:48:08.721Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703847
;

-- 2022-08-05T09:48:08.722Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703847, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697980
;

-- Field: Prüf Projekt -> Projekt -> Geschäftspartner
-- Column: C_Project.C_BPartner_ID
-- 2022-08-05T09:48:08.808Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3902,104,703848,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'@IsSummary@=N','D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',150,150,1,1,TO_TIMESTAMP('2022-08-05 11:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:08.809Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703848 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:08.811Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2022-08-05T09:48:09.294Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703848
;

-- 2022-08-05T09:48:09.295Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703848)
;

-- 2022-08-05T09:48:09.297Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703848
;

-- 2022-08-05T09:48:09.297Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703848, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697981
;

-- Field: Prüf Projekt -> Projekt -> BPartner (Agent)
-- Column: C_Project.C_BPartnerSR_ID
-- 2022-08-05T09:48:09.383Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14095,703849,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:09','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner (Agent or Sales Rep)',10,'@IsSummary@=N','D',0,'Y','Y','Y','N','N','N','N','Y','BPartner (Agent)',160,160,1,1,TO_TIMESTAMP('2022-08-05 11:48:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:09.384Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703849 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:09.385Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2810)
;

-- 2022-08-05T09:48:09.398Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703849
;

-- 2022-08-05T09:48:09.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703849)
;

-- 2022-08-05T09:48:09.400Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703849
;

-- 2022-08-05T09:48:09.401Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703849, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697982
;

-- Field: Prüf Projekt -> Projekt -> Standort
-- Column: C_Project.C_BPartner_Location_ID
-- 2022-08-05T09:48:09.473Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5798,703850,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:09','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',14,'@IsSummary@=N','D','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','N','N','N','N','N','Standort',170,170,1,1,TO_TIMESTAMP('2022-08-05 11:48:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:09.474Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703850 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:09.475Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189)
;

-- 2022-08-05T09:48:09.738Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703850
;

-- 2022-08-05T09:48:09.739Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703850)
;

-- 2022-08-05T09:48:09.741Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703850
;

-- 2022-08-05T09:48:09.742Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703850, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697983
;

-- Field: Prüf Projekt -> Projekt -> Ansprechpartner
-- Column: C_Project.AD_User_ID
-- 2022-08-05T09:48:09.829Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5797,703851,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:09','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'@IsSummary@=N','D','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','Y','Ansprechpartner',180,180,1,1,TO_TIMESTAMP('2022-08-05 11:48:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:09.831Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703851 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:09.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138)
;

-- 2022-08-05T09:48:10.301Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703851
;

-- 2022-08-05T09:48:10.302Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703851)
;

-- 2022-08-05T09:48:10.304Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703851
;

-- 2022-08-05T09:48:10.305Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703851, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697984
;

-- Field: Prüf Projekt -> Projekt -> Zahlungsbedingung
-- Column: C_Project.C_PaymentTerm_ID
-- 2022-08-05T09:48:10.392Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5796,703852,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:10','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',14,'@IsSummary@=N','D','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','Y','Y','N','N','N','N','N','Zahlungsbedingung',190,190,1,1,TO_TIMESTAMP('2022-08-05 11:48:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:10.393Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703852 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:10.395Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(204)
;

-- 2022-08-05T09:48:10.510Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703852
;

-- 2022-08-05T09:48:10.511Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703852)
;

-- 2022-08-05T09:48:10.514Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703852
;

-- 2022-08-05T09:48:10.515Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703852, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697985
;

-- Field: Prüf Projekt -> Projekt -> Referenz
-- Column: C_Project.POReference
-- 2022-08-05T09:48:10.600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5794,703853,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:10','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',20,'@IsSummary@=N','D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','Y','Y','N','N','N','N','Y','Referenz',200,200,1,1,TO_TIMESTAMP('2022-08-05 11:48:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:10.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703853 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:10.602Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952)
;

-- 2022-08-05T09:48:10.687Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703853
;

-- 2022-08-05T09:48:10.688Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703853)
;

-- 2022-08-05T09:48:10.690Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703853
;

-- 2022-08-05T09:48:10.690Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703853, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697986
;

-- Field: Prüf Projekt -> Projekt -> Lager
-- Column: C_Project.M_Warehouse_ID
-- 2022-08-05T09:48:10.773Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9637,703854,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:10','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',14,'@IsSummary@=N','D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','Y','Y','N','N','N','N','N','Lager',210,210,1,1,TO_TIMESTAMP('2022-08-05 11:48:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:10.775Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703854 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:10.776Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2022-08-05T09:48:11.034Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703854
;

-- 2022-08-05T09:48:11.035Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703854)
;

-- 2022-08-05T09:48:11.037Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703854
;

-- 2022-08-05T09:48:11.038Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703854, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697987
;

-- Field: Prüf Projekt -> Projekt -> Werbemassnahme
-- Column: C_Project.C_Campaign_ID
-- 2022-08-05T09:48:11.122Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5795,703855,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:11','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',14,'@IsSummary@=N & @$Element_MC@=Y','D','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','Y','Y','N','N','N','N','Y','Werbemassnahme',220,220,1,1,TO_TIMESTAMP('2022-08-05 11:48:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:11.123Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703855 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:11.124Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550)
;

-- 2022-08-05T09:48:11.294Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703855
;

-- 2022-08-05T09:48:11.296Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703855)
;

-- 2022-08-05T09:48:11.298Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703855
;

-- 2022-08-05T09:48:11.298Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703855, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697988
;

-- Field: Prüf Projekt -> Projekt -> Version Preisliste
-- Column: C_Project.M_PriceList_Version_ID
-- 2022-08-05T09:48:11.388Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5753,703856,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:11','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste',14,'@IsSummary@=N','D','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ',0,'Y','Y','Y','N','N','N','N','N','Version Preisliste',230,230,1,1,TO_TIMESTAMP('2022-08-05 11:48:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:11.390Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703856 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:11.391Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(450)
;

-- 2022-08-05T09:48:11.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703856
;

-- 2022-08-05T09:48:11.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703856)
;

-- 2022-08-05T09:48:11.435Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703856
;

-- 2022-08-05T09:48:11.435Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703856, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697989
;

-- Field: Prüf Projekt -> Projekt -> Währung
-- Column: C_Project.C_Currency_ID
-- 2022-08-05T09:48:11.516Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3901,703857,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:11','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',14,'@IsSummary@=N','D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','Y','N','N','N','Y','Y','Y','Währung',240,240,1,1,TO_TIMESTAMP('2022-08-05 11:48:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:11.517Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703857 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:11.519Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2022-08-05T09:48:11.941Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703857
;

-- 2022-08-05T09:48:11.942Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703857)
;

-- 2022-08-05T09:48:11.944Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703857
;

-- 2022-08-05T09:48:11.945Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703857, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697990
;

-- Field: Prüf Projekt -> Projekt -> VK Total
-- Column: C_Project.PlannedAmt
-- 2022-08-05T09:48:12.018Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5755,103,703858,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:11','YYYY-MM-DD HH24:MI:SS'),100,'VK Total',26,'@IsSummary@=N','D','The Planned Amount indicates the anticipated amount for this project or project line.',0,'Y','Y','Y','N','N','N','N','N','VK Total',250,250,1,1,TO_TIMESTAMP('2022-08-05 11:48:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:12.020Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:12.021Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1564)
;

-- 2022-08-05T09:48:12.047Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703858
;

-- 2022-08-05T09:48:12.048Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703858)
;

-- 2022-08-05T09:48:12.050Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703858
;

-- 2022-08-05T09:48:12.051Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703858, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697991
;

-- Field: Prüf Projekt -> Projekt -> Geplante Menge
-- Column: C_Project.PlannedQty
-- 2022-08-05T09:48:12.138Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5756,103,703859,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100,'',26,'@IsSummary@=N','D','',0,'Y','Y','Y','N','N','N','N','Y','Geplante Menge',260,260,1,1,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:12.139Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:12.140Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1568)
;

-- 2022-08-05T09:48:12.171Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703859
;

-- 2022-08-05T09:48:12.172Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703859)
;

-- 2022-08-05T09:48:12.174Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703859
;

-- 2022-08-05T09:48:12.175Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703859, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697992
;

-- Field: Prüf Projekt -> Projekt -> DB1
-- Column: C_Project.PlannedMarginAmt
-- 2022-08-05T09:48:12.252Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5757,703860,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100,'Project''s planned margin amount',26,'@IsSummary@=N','D','The Planned Margin Amount indicates the anticipated margin amount for this project or project line.',0,'Y','Y','Y','N','N','N','N','N','DB1',270,270,1,1,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:12.254Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:12.255Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1566)
;

-- 2022-08-05T09:48:12.267Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703860
;

-- 2022-08-05T09:48:12.269Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703860)
;

-- 2022-08-05T09:48:12.270Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703860
;

-- 2022-08-05T09:48:12.271Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703860, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697993
;

-- Field: Prüf Projekt -> Projekt -> Rechnungsstellung
-- Column: C_Project.ProjInvoiceRule
-- 2022-08-05T09:48:12.356Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15449,703861,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Rule for the project',1,'@IsSummary@=N','D','The Invoice Rule for the project determines how orders (and consequently invoices) are created.  The selection on project level can be overwritten on Phase or Task',0,'Y','Y','Y','N','N','N','N','Y','Rechnungsstellung',280,280,1,1,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:12.357Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:12.359Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3031)
;

-- 2022-08-05T09:48:12.376Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703861
;

-- 2022-08-05T09:48:12.377Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703861)
;

-- 2022-08-05T09:48:12.378Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703861
;

-- 2022-08-05T09:48:12.379Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703861, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697994
;

-- Field: Prüf Projekt -> Projekt -> Committed Amount
-- Column: C_Project.CommittedAmt
-- 2022-08-05T09:48:12.464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3907,703862,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100,'The (legal) commitment amount',26,'@IsSummary@=N','D','The commitment amount is independent from the planned amount. You would use the planned amount for your realistic estimation, which might be higher or lower than the commitment amount.',0,'Y','Y','Y','N','N','N','N','N','Committed Amount',290,290,1,1,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:12.465Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:12.466Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1081)
;

-- 2022-08-05T09:48:12.498Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703862
;

-- 2022-08-05T09:48:12.499Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703862)
;

-- 2022-08-05T09:48:12.501Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703862
;

-- 2022-08-05T09:48:12.501Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703862, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697995
;

-- Field: Prüf Projekt -> Projekt -> Committed Quantity
-- Column: C_Project.CommittedQty
-- 2022-08-05T09:48:12.589Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8759,703863,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100,'The (legal) commitment Quantity',26,'@IsSummary@=N','D','The commitment amount is independent from the planned amount. You would use the planned amount for your realistic estimation, which might be higher or lower than the commitment amount.',0,'Y','Y','Y','N','N','N','N','Y','Committed Quantity',300,300,1,1,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:12.590Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:12.592Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2036)
;

-- 2022-08-05T09:48:12.611Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703863
;

-- 2022-08-05T09:48:12.613Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703863)
;

-- 2022-08-05T09:48:12.615Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703863
;

-- 2022-08-05T09:48:12.616Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703863, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697996
;

-- Field: Prüf Projekt -> Projekt -> Invoiced Amount
-- Column: C_Project.InvoicedAmt
-- 2022-08-05T09:48:12.704Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8753,105,703864,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100,'The amount invoiced',26,'@IsSummary@=N','D','The amount invoiced',0,'Y','Y','Y','N','N','N','Y','N','Invoiced Amount',310,310,1,1,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:12.706Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:12.707Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2044)
;

-- 2022-08-05T09:48:12.719Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703864
;

-- 2022-08-05T09:48:12.720Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703864)
;

-- 2022-08-05T09:48:12.722Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703864
;

-- 2022-08-05T09:48:12.722Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703864, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697997
;

-- Field: Prüf Projekt -> Projekt -> Berechnete Menge
-- Column: C_Project.InvoicedQty
-- 2022-08-05T09:48:12.806Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8756,703865,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100,'The quantity invoiced',26,'@IsSummary@=N','D',0,'Y','Y','Y','N','N','N','Y','Y','Berechnete Menge',320,320,1,1,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:12.807Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:12.808Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2045)
;

-- 2022-08-05T09:48:12.824Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703865
;

-- 2022-08-05T09:48:12.824Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703865)
;

-- 2022-08-05T09:48:12.827Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703865
;

-- 2022-08-05T09:48:12.827Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703865, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697998
;

-- Field: Prüf Projekt -> Projekt -> Project Balance
-- Column: C_Project.ProjectBalanceAmt
-- 2022-08-05T09:48:12.917Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8758,703866,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100,'Total Project Balance',26,'@IsSummary@=N','D','The project balance is the sum of all invoices and payments',0,'Y','Y','Y','N','N','N','Y','N','Project Balance',330,330,1,1,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:12.918Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:12.920Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2054)
;

-- 2022-08-05T09:48:12.926Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703866
;

-- 2022-08-05T09:48:12.927Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703866)
;

-- 2022-08-05T09:48:12.929Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703866
;

-- 2022-08-05T09:48:12.930Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703866, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697999
;

-- Field: Prüf Projekt -> Projekt -> Process Now
-- Column: C_Project.Processing
-- 2022-08-05T09:48:13.010Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9861,703867,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100,23,'@IsSummary@=N','D',0,'Y','Y','Y','N','N','N','N','N','Process Now',350,350,1,1,TO_TIMESTAMP('2022-08-05 11:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:13.012Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:13.013Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524)
;

-- 2022-08-05T09:48:13.670Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703867
;

-- 2022-08-05T09:48:13.671Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703867)
;

-- 2022-08-05T09:48:13.673Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703867
;

-- 2022-08-05T09:48:13.674Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703867, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698000
;

-- Field: Prüf Projekt -> Projekt -> Externe Projektreferenz
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-08-05T09:48:13.757Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582993,703868,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:13','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Externe Projektreferenz',370,360,0,1,1,TO_TIMESTAMP('2022-08-05 11:48:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:13.758Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:13.759Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580859)
;

-- 2022-08-05T09:48:13.763Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703868
;

-- 2022-08-05T09:48:13.764Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703868)
;

-- 2022-08-05T09:48:13.766Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703868
;

-- 2022-08-05T09:48:13.767Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703868, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698001
;

-- Field: Prüf Projekt -> Projekt -> Elternprojekt
-- Column: C_Project.C_Project_Parent_ID
-- 2022-08-05T09:48:13.838Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582994,703869,0,546542,0,TO_TIMESTAMP('2022-08-05 11:48:13','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Elternprojekt',380,370,0,1,1,TO_TIMESTAMP('2022-08-05 11:48:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:13.840Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:48:13.841Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580860)
;

-- 2022-08-05T09:48:13.845Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703869
;

-- 2022-08-05T09:48:13.846Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703869)
;

-- 2022-08-05T09:48:13.848Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703869
;

-- 2022-08-05T09:48:13.848Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703869, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698002
;

-- 2022-08-05T09:48:13.961Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546542,545162,TO_TIMESTAMP('2022-08-05 11:48:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2022-08-05 11:48:13','YYYY-MM-DD HH24:MI:SS'),100,'1000028')
;

-- 2022-08-05T09:48:13.962Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545162 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-08-05T09:48:13.964Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545162
;

-- 2022-08-05T09:48:13.966Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545162, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544937
;

-- 2022-08-05T09:48:14.083Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546269,545162,TO_TIMESTAMP('2022-08-05 11:48:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-05 11:48:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:14.201Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546269,549686,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,'primary',TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-08-05T09:48:14.344Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703844,0,546542,549686,611272,'F',TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektart',5,0,0,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Projektnummer
-- Column: C_Project.Value
-- 2022-08-05T09:48:14.437Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703836,0,546542,549686,611273,'F',TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Projektnummer',10,60,0,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Name
-- Column: C_Project.Name
-- 2022-08-05T09:48:14.529Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703838,0,546542,549686,611274,'F',TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Name',20,50,0,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Externe Projektreferenz
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-08-05T09:48:14.635Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703868,0,546542,549686,611275,'F',TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Externe Projektreferenz',35,70,0,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Elternprojekt
-- Column: C_Project.C_Project_Parent_ID
-- 2022-08-05T09:48:14.726Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703869,0,546542,549686,611276,'F',TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Elternprojekt',45,0,0,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:14.805Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546269,549687,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Beschreibung
-- Column: C_Project.Description
-- 2022-08-05T09:48:14.904Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703839,0,546542,549687,611277,'F',TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Beschreibung',10,30,0,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:14.984Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546270,545162,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:15.061Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546270,549688,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','active',5,TO_TIMESTAMP('2022-08-05 11:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Aktiv
-- Column: C_Project.IsActive
-- 2022-08-05T09:48:15.149Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703840,0,546542,549688,611278,'F',TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Aktiv',10,10,0,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:15.234Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546270,549689,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','partner',10,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Kunde
-- Column: C_Project.C_BPartner_ID
-- 2022-08-05T09:48:15.338Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703848,0,546542,549689,611279,'F',TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Kunde',10,40,0,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Aussendienst
-- Column: C_Project.SalesRep_ID
-- 2022-08-05T09:48:15.426Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703837,0,546542,549689,611280,'F',TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Aussendienst',30,20,0,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Datum AE
-- Column: C_Project.DateContract
-- 2022-08-05T09:48:15.515Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703846,0,546542,549689,611281,'F',TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Datum AE',40,0,0,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Projektabschluss
-- Column: C_Project.DateFinish
-- 2022-08-05T09:48:15.603Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703847,0,546542,549689,611282,'F',TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektabschluss',50,0,0,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:15.688Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546270,549690,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','pricing',15,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Version Preisliste
-- Column: C_Project.M_PriceList_Version_ID
-- 2022-08-05T09:48:15.785Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703856,0,546542,549690,611283,'F',TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','N','Y','N','N','N','Version Preisliste',10,0,0,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Währung
-- Column: C_Project.C_Currency_ID
-- 2022-08-05T09:48:15.875Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703857,0,546542,549690,611284,'F',TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N','Währung',20,0,0,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:48:15.960Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546270,549691,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Mandant
-- Column: C_Project.AD_Client_ID
-- 2022-08-05T09:48:16.054Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703834,0,546542,549691,611285,'F',TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2022-08-05 11:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Sektion
-- Column: C_Project.AD_Org_ID
-- 2022-08-05T09:48:16.146Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703835,0,546542,549691,611286,'F',TO_TIMESTAMP('2022-08-05 11:48:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-08-05 11:48:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Prüf Projekt -> Project Step
-- Table: C_Project_WO_Step
-- 2022-08-05T09:49:05.225Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583231,580965,0,546543,542159,541586,'Y',TO_TIMESTAMP('2022-08-05 11:49:05','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_Step','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Project Step',1349,'N',20,1,TO_TIMESTAMP('2022-08-05 11:49:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:05.226Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546543 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-08-05T09:49:05.228Z
/* DDL */  select update_tab_translation_from_ad_element(580965)
;

-- 2022-08-05T09:49:05.230Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546543)
;

-- 2022-08-05T09:49:05.232Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546543
;

-- 2022-08-05T09:49:05.233Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546543, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 546290
;

-- Field: Prüf Projekt -> Project Step -> Mandant
-- Column: C_Project_WO_Step.AD_Client_ID
-- 2022-08-05T09:49:05.322Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583223,703870,0,546543,0,TO_TIMESTAMP('2022-08-05 11:49:05','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2022-08-05 11:49:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:05.323Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:49:05.325Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2022-08-05T09:49:11.248Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703870
;

-- 2022-08-05T09:49:11.249Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703870)
;

-- 2022-08-05T09:49:11.251Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703870
;

-- 2022-08-05T09:49:11.252Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703870, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698003
;

-- Field: Prüf Projekt -> Project Step -> Sektion
-- Column: C_Project_WO_Step.AD_Org_ID
-- 2022-08-05T09:49:11.361Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583224,703871,0,546543,0,TO_TIMESTAMP('2022-08-05 11:49:11','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',20,1,1,TO_TIMESTAMP('2022-08-05 11:49:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:11.363Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:49:11.364Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2022-08-05T09:49:17.303Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703871
;

-- 2022-08-05T09:49:17.304Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703871)
;

-- 2022-08-05T09:49:17.306Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703871
;

-- 2022-08-05T09:49:17.306Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703871, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698004
;

-- Field: Prüf Projekt -> Project Step -> Aktiv
-- Column: C_Project_WO_Step.IsActive
-- 2022-08-05T09:49:17.429Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583227,703872,0,546543,0,TO_TIMESTAMP('2022-08-05 11:49:17','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',30,1,1,TO_TIMESTAMP('2022-08-05 11:49:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:17.430Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:49:17.432Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2022-08-05T09:49:23.392Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703872
;

-- 2022-08-05T09:49:23.393Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703872)
;

-- 2022-08-05T09:49:23.395Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703872
;

-- 2022-08-05T09:49:23.395Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703872, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698005
;

-- Field: Prüf Projekt -> Project Step -> Project Step
-- Column: C_Project_WO_Step.C_Project_WO_Step_ID
-- 2022-08-05T09:49:23.522Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583230,703873,0,546543,0,TO_TIMESTAMP('2022-08-05 11:49:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Project Step',40,1,1,TO_TIMESTAMP('2022-08-05 11:49:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:23.523Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:49:23.524Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580965)
;

-- 2022-08-05T09:49:23.532Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703873
;

-- 2022-08-05T09:49:23.533Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703873)
;

-- 2022-08-05T09:49:23.535Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703873
;

-- 2022-08-05T09:49:23.535Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703873, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698006
;

-- Field: Prüf Projekt -> Project Step -> Projekt
-- Column: C_Project_WO_Step.C_Project_ID
-- 2022-08-05T09:49:23.621Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583231,703874,0,546543,0,TO_TIMESTAMP('2022-08-05 11:49:23','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Projekt',50,1,1,TO_TIMESTAMP('2022-08-05 11:49:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:23.622Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:49:23.623Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2022-08-05T09:49:23.900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703874
;

-- 2022-08-05T09:49:23.901Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703874)
;

-- 2022-08-05T09:49:23.903Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703874
;

-- 2022-08-05T09:49:23.903Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703874, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698007
;

-- Field: Prüf Projekt -> Project Step -> Reihenfolge
-- Column: C_Project_WO_Step.SeqNo
-- 2022-08-05T09:49:23.991Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583232,703875,0,546543,0,TO_TIMESTAMP('2022-08-05 11:49:23','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','N','N','N','N','N','N','N','Reihenfolge',60,1,1,TO_TIMESTAMP('2022-08-05 11:49:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:23.992Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:49:23.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2022-08-05T09:49:24.618Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703875
;

-- 2022-08-05T09:49:24.620Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703875)
;

-- 2022-08-05T09:49:24.622Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703875
;

-- 2022-08-05T09:49:24.623Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703875, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698008
;

-- Field: Prüf Projekt -> Project Step -> Name
-- Column: C_Project_WO_Step.Name
-- 2022-08-05T09:49:24.697Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583233,703876,0,546543,0,TO_TIMESTAMP('2022-08-05 11:49:24','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','',0,'Y','N','N','N','N','N','N','N','Name',70,1,1,TO_TIMESTAMP('2022-08-05 11:49:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:24.698Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:49:24.699Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2022-08-05T09:49:26.035Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703876
;

-- 2022-08-05T09:49:26.035Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703876)
;

-- 2022-08-05T09:49:26.037Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703876
;

-- 2022-08-05T09:49:26.038Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703876, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698009
;

-- Field: Prüf Projekt -> Project Step -> Beschreibung
-- Column: C_Project_WO_Step.Description
-- 2022-08-05T09:49:26.145Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583234,703877,0,546543,0,TO_TIMESTAMP('2022-08-05 11:49:26','YYYY-MM-DD HH24:MI:SS'),100,2000,'D',0,'Y','N','N','N','N','N','N','N','Beschreibung',80,1,1,TO_TIMESTAMP('2022-08-05 11:49:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:26.146Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:49:26.148Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2022-08-05T09:49:27.757Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703877
;

-- 2022-08-05T09:49:27.758Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703877)
;

-- 2022-08-05T09:49:27.760Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703877
;

-- 2022-08-05T09:49:27.761Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703877, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698010
;

-- Field: Prüf Projekt -> Project Step -> Startdatum
-- Column: C_Project_WO_Step.DateStart
-- 2022-08-05T09:49:27.839Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583254,703878,0,546543,0,TO_TIMESTAMP('2022-08-05 11:49:27','YYYY-MM-DD HH24:MI:SS'),100,7,'D',0,'Y','N','N','N','N','N','N','N','Startdatum',90,1,1,TO_TIMESTAMP('2022-08-05 11:49:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:27.840Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703878 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:49:27.842Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53280)
;

-- 2022-08-05T09:49:27.868Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703878
;

-- 2022-08-05T09:49:27.869Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703878)
;

-- 2022-08-05T09:49:27.870Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703878
;

-- 2022-08-05T09:49:27.871Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703878, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698011
;

-- Field: Prüf Projekt -> Project Step ->  Endzeitpunkt
-- Column: C_Project_WO_Step.DateEnd
-- 2022-08-05T09:49:27.951Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583255,703879,0,546543,0,TO_TIMESTAMP('2022-08-05 11:49:27','YYYY-MM-DD HH24:MI:SS'),100,7,'D',0,'Y','N','N','N','N','N','N','N',' Endzeitpunkt',100,1,1,TO_TIMESTAMP('2022-08-05 11:49:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:27.952Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:49:27.954Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579243)
;

-- 2022-08-05T09:49:27.961Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703879
;

-- 2022-08-05T09:49:27.962Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703879)
;

-- 2022-08-05T09:49:27.964Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703879
;

-- 2022-08-05T09:49:27.965Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703879, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698012
;

-- 2022-08-05T09:49:28.048Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546543,545163,TO_TIMESTAMP('2022-08-05 11:49:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-05 11:49:27','YYYY-MM-DD HH24:MI:SS'),100,'default')
;

-- 2022-08-05T09:49:28.049Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545163 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-08-05T09:49:28.051Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545163
;

-- 2022-08-05T09:49:28.052Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545163, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544938
;

-- 2022-08-05T09:49:28.133Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546271,545163,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:28.214Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546271,549692,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Step.Reihenfolge
-- Column: C_Project_WO_Step.SeqNo
-- 2022-08-05T09:49:28.305Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703875,0,546543,549692,611287,'F',TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','Y','N','N','Reihenfolge',10,10,0,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Step.Name
-- Column: C_Project_WO_Step.Name
-- 2022-08-05T09:49:28.393Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703876,0,546543,549692,611288,'F',TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','Y','N','N','Name',20,20,0,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Step.Beschreibung
-- Column: C_Project_WO_Step.Description
-- 2022-08-05T09:49:28.479Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703877,0,546543,549692,611289,'F',TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Beschreibung',30,0,0,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:28.564Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546272,545163,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:28.645Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546272,549693,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',10,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Step.Startdatum
-- Column: C_Project_WO_Step.DateStart
-- 2022-08-05T09:49:28.737Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703878,0,546543,549693,611290,'F',TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Startdatum',10,30,0,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Step. Endzeitpunkt
-- Column: C_Project_WO_Step.DateEnd
-- 2022-08-05T09:49:28.825Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703879,0,546543,549693,611291,'F',TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',' Endzeitpunkt',20,40,0,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:49:28.899Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546272,549694,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','client&org',20,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Step.Sektion
-- Column: C_Project_WO_Step.AD_Org_ID
-- 2022-08-05T09:49:28.993Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703871,0,546543,549694,611292,'F',TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N','Sektion',10,0,0,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Step.Mandant
-- Column: C_Project_WO_Step.AD_Client_ID
-- 2022-08-05T09:49:29.065Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703870,0,546543,549694,611293,'F',TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-08-05 11:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Prüf Projekt -> Project Resource
-- Table: C_Project_WO_Resource
-- 2022-08-05T09:50:24.950Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583243,580966,0,546544,542161,541586,'Y',TO_TIMESTAMP('2022-08-05 11:50:24','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_Resource','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Project Resource',583231,'N',30,1,TO_TIMESTAMP('2022-08-05 11:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:24.951Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546544 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-08-05T09:50:24.953Z
/* DDL */  select update_tab_translation_from_ad_element(580966)
;

-- 2022-08-05T09:50:24.956Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546544)
;

-- 2022-08-05T09:50:24.959Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546544
;

-- 2022-08-05T09:50:24.960Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546544, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 546291
;

-- Field: Prüf Projekt -> Project Resource -> Mandant
-- Column: C_Project_WO_Resource.AD_Client_ID
-- 2022-08-05T09:50:25.052Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583235,703880,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:24','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2022-08-05 11:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:25.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:25.055Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2022-08-05T09:50:25.223Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703880
;

-- 2022-08-05T09:50:25.224Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703880)
;

-- 2022-08-05T09:50:25.227Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703880
;

-- 2022-08-05T09:50:25.227Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703880, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698013
;

-- Field: Prüf Projekt -> Project Resource -> Sektion
-- Column: C_Project_WO_Resource.AD_Org_ID
-- 2022-08-05T09:50:25.303Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583236,703881,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:25','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',20,1,1,TO_TIMESTAMP('2022-08-05 11:50:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:25.304Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:25.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2022-08-05T09:50:25.502Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703881
;

-- 2022-08-05T09:50:25.502Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703881)
;

-- 2022-08-05T09:50:25.505Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703881
;

-- 2022-08-05T09:50:25.506Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703881, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698014
;

-- Field: Prüf Projekt -> Project Resource -> Aktiv
-- Column: C_Project_WO_Resource.IsActive
-- 2022-08-05T09:50:25.594Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583239,703882,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:25','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',30,1,1,TO_TIMESTAMP('2022-08-05 11:50:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:25.595Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703882 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:25.596Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2022-08-05T09:50:25.788Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703882
;

-- 2022-08-05T09:50:25.789Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703882)
;

-- 2022-08-05T09:50:25.791Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703882
;

-- 2022-08-05T09:50:25.792Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703882, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698015
;

-- Field: Prüf Projekt -> Project Resource -> Project Resource
-- Column: C_Project_WO_Resource.C_Project_WO_Resource_ID
-- 2022-08-05T09:50:25.874Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583242,703883,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Project Resource',40,1,1,TO_TIMESTAMP('2022-08-05 11:50:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:25.875Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703883 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:25.876Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580966)
;

-- 2022-08-05T09:50:25.879Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703883
;

-- 2022-08-05T09:50:25.879Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703883)
;

-- 2022-08-05T09:50:25.882Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703883
;

-- 2022-08-05T09:50:25.882Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703883, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698016
;

-- Field: Prüf Projekt -> Project Resource -> Projekt
-- Column: C_Project_WO_Resource.C_Project_ID
-- 2022-08-05T09:50:25.965Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583243,703884,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:25','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Projekt',50,1,1,TO_TIMESTAMP('2022-08-05 11:50:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:25.967Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703884 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:25.968Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2022-08-05T09:50:25.987Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703884
;

-- 2022-08-05T09:50:25.989Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703884)
;

-- 2022-08-05T09:50:25.990Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703884
;

-- 2022-08-05T09:50:25.992Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703884, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698017
;

-- Field: Prüf Projekt -> Project Resource -> Project Step
-- Column: C_Project_WO_Resource.C_Project_WO_Step_ID
-- 2022-08-05T09:50:26.080Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583244,703885,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Project Step',60,1,1,TO_TIMESTAMP('2022-08-05 11:50:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:26.081Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703885 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:26.082Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580965)
;

-- 2022-08-05T09:50:26.091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703885
;

-- 2022-08-05T09:50:26.091Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703885)
;

-- 2022-08-05T09:50:26.094Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703885
;

-- 2022-08-05T09:50:26.095Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703885, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698018
;

-- Field: Prüf Projekt -> Project Resource -> Ressource
-- Column: C_Project_WO_Resource.S_Resource_ID
-- 2022-08-05T09:50:26.173Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583245,703886,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100,'Ressource',10,'D',0,'Y','N','N','N','N','N','N','N','Ressource',70,1,1,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:26.174Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703886 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:26.175Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1777)
;

-- 2022-08-05T09:50:26.259Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703886
;

-- 2022-08-05T09:50:26.260Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703886)
;

-- 2022-08-05T09:50:26.262Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703886
;

-- 2022-08-05T09:50:26.262Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703886, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698019
;

-- Field: Prüf Projekt -> Project Resource -> Zuordnung von
-- Column: C_Project_WO_Resource.AssignDateFrom
-- 2022-08-05T09:50:26.341Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583246,703887,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab',7,'D','Beginn Zuordnung',0,'Y','N','N','N','N','N','N','N','Zuordnung von',80,1,1,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:26.342Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703887 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:26.343Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1754)
;

-- 2022-08-05T09:50:26.360Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703887
;

-- 2022-08-05T09:50:26.361Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703887)
;

-- 2022-08-05T09:50:26.362Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703887
;

-- 2022-08-05T09:50:26.363Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703887, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698020
;

-- Field: Prüf Projekt -> Project Resource -> Zuordnung bis
-- Column: C_Project_WO_Resource.AssignDateTo
-- 2022-08-05T09:50:26.440Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583247,703888,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis',7,'D','Zuordnung endet',0,'Y','N','N','N','N','N','N','N','Zuordnung bis',90,1,1,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:26.441Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703888 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:26.443Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1755)
;

-- 2022-08-05T09:50:26.459Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703888
;

-- 2022-08-05T09:50:26.460Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703888)
;

-- 2022-08-05T09:50:26.462Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703888
;

-- 2022-08-05T09:50:26.463Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703888, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698021
;

-- Field: Prüf Projekt -> Project Resource -> All day
-- Column: C_Project_WO_Resource.IsAllDay
-- 2022-08-05T09:50:26.544Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583248,703889,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','N','N','N','N','N','N','N','All day',100,1,1,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:26.545Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703889 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:26.546Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580861)
;

-- 2022-08-05T09:50:26.557Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703889
;

-- 2022-08-05T09:50:26.558Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703889)
;

-- 2022-08-05T09:50:26.560Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703889
;

-- 2022-08-05T09:50:26.561Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703889, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698022
;

-- Field: Prüf Projekt -> Project Resource -> Dauer
-- Column: C_Project_WO_Resource.Duration
-- 2022-08-05T09:50:26.646Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583249,703890,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','',0,'Y','N','N','N','N','N','Y','N','Dauer',110,1,1,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:26.647Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703890 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:26.649Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2320)
;

-- 2022-08-05T09:50:26.675Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703890
;

-- 2022-08-05T09:50:26.676Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703890)
;

-- 2022-08-05T09:50:26.677Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703890
;

-- 2022-08-05T09:50:26.678Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703890, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698023
;

-- Field: Prüf Projekt -> Project Resource -> Einheit
-- Column: C_Project_WO_Resource.DurationUnit
-- 2022-08-05T09:50:26.763Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583250,703891,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100,'Masseinheit der Dauer',1,'D','',0,'Y','N','N','N','N','N','Y','N','Einheit',120,1,1,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:26.764Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:26.765Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2321)
;

-- 2022-08-05T09:50:26.791Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703891
;

-- 2022-08-05T09:50:26.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703891)
;

-- 2022-08-05T09:50:26.794Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703891
;

-- 2022-08-05T09:50:26.794Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703891, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698024
;

-- Field: Prüf Projekt -> Project Resource -> Beschreibung
-- Column: C_Project_WO_Resource.Description
-- 2022-08-05T09:50:26.875Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583251,703892,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100,2000,'D',0,'Y','N','N','N','N','N','N','N','Beschreibung',130,1,1,TO_TIMESTAMP('2022-08-05 11:50:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:26.876Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:26.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2022-08-05T09:50:28.507Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703892
;

-- 2022-08-05T09:50:28.507Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703892)
;

-- 2022-08-05T09:50:28.509Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703892
;

-- 2022-08-05T09:50:28.510Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703892, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698025
;

-- Field: Prüf Projekt -> Project Resource -> Project Resource Budget
-- Column: C_Project_WO_Resource.C_Project_Resource_Budget_ID
-- 2022-08-05T09:50:28.581Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583252,703893,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','Y','N','Project Resource Budget',140,1,1,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:28.582Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:28.583Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580947)
;

-- 2022-08-05T09:50:28.592Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703893
;

-- 2022-08-05T09:50:28.592Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703893)
;

-- 2022-08-05T09:50:28.594Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703893
;

-- 2022-08-05T09:50:28.595Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703893, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698026
;

-- Field: Prüf Projekt -> Project Resource -> Budget Project
-- Column: C_Project_WO_Resource.Budget_Project_ID
-- 2022-08-05T09:50:28.677Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583253,703894,0,546544,0,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','Y','N','Budget Project',150,1,1,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:28.679Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703894 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:50:28.680Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580967)
;

-- 2022-08-05T09:50:28.684Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703894
;

-- 2022-08-05T09:50:28.685Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703894)
;

-- 2022-08-05T09:50:28.687Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703894
;

-- 2022-08-05T09:50:28.687Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703894, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 698027
;

-- 2022-08-05T09:50:28.786Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546544,545164,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-08-05T09:50:28.787Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545164 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-08-05T09:50:28.788Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545164
;

-- 2022-08-05T09:50:28.790Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545164, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544939
;

-- 2022-08-05T09:50:28.870Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546273,545164,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:28.953Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546273,549695,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Project Step
-- Column: C_Project_WO_Resource.C_Project_WO_Step_ID
-- 2022-08-05T09:50:29.043Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703885,0,546544,549695,611294,'F',TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Project Step',10,10,0,TO_TIMESTAMP('2022-08-05 11:50:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Beschreibung
-- Column: C_Project_WO_Resource.Description
-- 2022-08-05T09:50:29.135Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703892,0,546544,549695,611295,'F',TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:29.211Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546273,549696,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','resource',20,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Ressource
-- Column: C_Project_WO_Resource.S_Resource_ID
-- 2022-08-05T09:50:29.298Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703886,0,546544,549696,611296,'F',TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Ressource','Y','N','N','Y','Y','N','N','Ressource',10,20,0,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:29.368Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546273,549697,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates & duration',30,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Zuordnung von
-- Column: C_Project_WO_Resource.AssignDateFrom
-- 2022-08-05T09:50:29.457Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703887,0,546544,549697,611297,'F',TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab','Beginn Zuordnung','Y','N','N','Y','Y','N','N','Zuordnung von',10,40,0,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Zuordnung bis
-- Column: C_Project_WO_Resource.AssignDateTo
-- 2022-08-05T09:50:29.550Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703888,0,546544,549697,611298,'F',TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis','Zuordnung endet','Y','N','N','Y','Y','N','N','Zuordnung bis',20,50,0,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.All day
-- Column: C_Project_WO_Resource.IsAllDay
-- 2022-08-05T09:50:29.641Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703889,0,546544,549697,611299,'F',TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','All day',30,0,0,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Duration Unit
-- Column: C_Project_WO_Resource.DurationUnit
-- 2022-08-05T09:50:29.730Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703891,0,546544,549697,611300,'F',TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Duration','Unit to define the length of time for the execution','Y','N','N','Y','N','N','N','Duration Unit',40,0,0,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Duration
-- Column: C_Project_WO_Resource.Duration
-- 2022-08-05T09:50:29.821Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703890,0,546544,549697,611301,'F',TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Normal Duration in Duration Unit','Expected (normal) Length of time for the execution','Y','N','N','Y','Y','N','N','Duration',50,30,0,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:29.889Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546274,545164,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:29.979Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546274,549698,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','budget',10,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Budget Project
-- Column: C_Project_WO_Resource.Budget_Project_ID
-- 2022-08-05T09:50:30.065Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703894,0,546544,549698,611302,'F',TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Budget Project',10,0,0,TO_TIMESTAMP('2022-08-05 11:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Project Resource Budget
-- Column: C_Project_WO_Resource.C_Project_Resource_Budget_ID
-- 2022-08-05T09:50:30.154Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703893,0,546544,549698,611303,'F',TO_TIMESTAMP('2022-08-05 11:50:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Project Resource Budget',20,0,0,TO_TIMESTAMP('2022-08-05 11:50:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:50:30.237Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546274,549699,TO_TIMESTAMP('2022-08-05 11:50:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','client & org',20,TO_TIMESTAMP('2022-08-05 11:50:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Sektion
-- Column: C_Project_WO_Resource.AD_Org_ID
-- 2022-08-05T09:50:30.332Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703881,0,546544,549699,611304,'F',TO_TIMESTAMP('2022-08-05 11:50:30','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N','Sektion',10,0,0,TO_TIMESTAMP('2022-08-05 11:50:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Project Resource.Mandant
-- Column: C_Project_WO_Resource.AD_Client_ID
-- 2022-08-05T09:50:30.417Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703880,0,546544,549699,611305,'F',TO_TIMESTAMP('2022-08-05 11:50:30','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-08-05 11:50:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Prüf Projekt -> Prüfgegenstand
-- Table: C_Project_WO_ObjectUnderTest
-- 2022-08-05T09:51:20.521Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583627,581124,0,546545,542184,541586,'Y',TO_TIMESTAMP('2022-08-05 11:51:20','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_ObjectUnderTest','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Prüfgegenstand',1349,'N',40,1,TO_TIMESTAMP('2022-08-05 11:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:20.522Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546545 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-08-05T09:51:20.523Z
/* DDL */  select update_tab_translation_from_ad_element(581124)
;

-- 2022-08-05T09:51:20.525Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546545)
;

-- 2022-08-05T09:51:20.527Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546545
;

-- 2022-08-05T09:51:20.528Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546545, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 546433
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Mandant
-- Column: C_Project_WO_ObjectUnderTest.AD_Client_ID
-- 2022-08-05T09:51:20.613Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583619,703895,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2022-08-05 11:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:20.615Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703895 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:20.616Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2022-08-05T09:51:26.566Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703895
;

-- 2022-08-05T09:51:26.567Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703895)
;

-- 2022-08-05T09:51:26.569Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703895
;

-- 2022-08-05T09:51:26.569Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703895, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701411
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Sektion
-- Column: C_Project_WO_ObjectUnderTest.AD_Org_ID
-- 2022-08-05T09:51:26.692Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583620,703896,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',20,1,1,TO_TIMESTAMP('2022-08-05 11:51:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:26.693Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703896 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:26.694Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2022-08-05T09:51:32.633Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703896
;

-- 2022-08-05T09:51:32.634Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703896)
;

-- 2022-08-05T09:51:32.638Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703896
;

-- 2022-08-05T09:51:32.639Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703896, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701412
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Aktiv
-- Column: C_Project_WO_ObjectUnderTest.IsActive
-- 2022-08-05T09:51:32.764Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583623,703897,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:32','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',30,1,1,TO_TIMESTAMP('2022-08-05 11:51:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:32.765Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703897 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:32.766Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2022-08-05T09:51:38.699Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703897
;

-- 2022-08-05T09:51:38.699Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703897)
;

-- 2022-08-05T09:51:38.701Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703897
;

-- 2022-08-05T09:51:38.702Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703897, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701413
;

-- Field: Prüf Projekt -> Prüfgegenstand -> C_Project_WO_ObjectUnderTest
-- Column: C_Project_WO_ObjectUnderTest.C_Project_WO_ObjectUnderTest_ID
-- 2022-08-05T09:51:38.814Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583626,703898,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:38','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','C_Project_WO_ObjectUnderTest',40,1,1,TO_TIMESTAMP('2022-08-05 11:51:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:38.815Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703898 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:38.816Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581101)
;

-- 2022-08-05T09:51:38.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703898
;

-- 2022-08-05T09:51:38.820Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703898)
;

-- 2022-08-05T09:51:38.822Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703898
;

-- 2022-08-05T09:51:38.823Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703898, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701414
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Projekt
-- Column: C_Project_WO_ObjectUnderTest.C_Project_ID
-- 2022-08-05T09:51:38.902Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583627,703899,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:38','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Projekt',50,1,1,TO_TIMESTAMP('2022-08-05 11:51:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:38.903Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:38.904Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2022-08-05T09:51:39.178Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703899
;

-- 2022-08-05T09:51:39.180Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703899)
;

-- 2022-08-05T09:51:39.182Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703899
;

-- 2022-08-05T09:51:39.182Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703899, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701415
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Anzahl der Prüfgegenstände
-- Column: C_Project_WO_ObjectUnderTest.NumberOfObjectsUnderTest
-- 2022-08-05T09:51:39.266Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583628,703900,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu prüfenden Objekte',14,'D',0,'Y','N','N','N','N','N','N','N','Anzahl der Prüfgegenstände',60,1,1,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:39.267Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703900 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:39.268Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581102)
;

-- 2022-08-05T09:51:39.273Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703900
;

-- 2022-08-05T09:51:39.273Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703900)
;

-- 2022-08-05T09:51:39.275Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703900
;

-- 2022-08-05T09:51:39.276Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703900, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701416
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Lieferhinweis
-- Column: C_Project_WO_ObjectUnderTest.WODeliveryNote
-- 2022-08-05T09:51:39.364Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583629,703901,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','N','N','N','N','N','N','N','Lieferhinweis',70,1,1,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:39.365Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703901 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:39.367Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581103)
;

-- 2022-08-05T09:51:39.371Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703901
;

-- 2022-08-05T09:51:39.372Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703901)
;

-- 2022-08-05T09:51:39.374Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703901
;

-- 2022-08-05T09:51:39.374Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703901, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701417
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Hersteller
-- Column: C_Project_WO_ObjectUnderTest.WOManufacturer
-- 2022-08-05T09:51:39.461Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583630,703902,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Hersteller des Prüfgegenstandes',255,'D','',0,'Y','N','N','N','N','N','N','N','Hersteller',80,1,1,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:39.463Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703902 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:39.464Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581104)
;

-- 2022-08-05T09:51:39.468Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703902
;

-- 2022-08-05T09:51:39.469Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703902)
;

-- 2022-08-05T09:51:39.471Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703902
;

-- 2022-08-05T09:51:39.472Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703902, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701418
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Klasse
-- Column: C_Project_WO_ObjectUnderTest.WOObjectType
-- 2022-08-05T09:51:39.551Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583631,703903,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Art des Prüfgegenstandes',255,'D',0,'Y','N','N','N','N','N','N','N','Klasse',90,1,1,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:39.553Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703903 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:39.554Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581105)
;

-- 2022-08-05T09:51:39.558Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703903
;

-- 2022-08-05T09:51:39.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703903)
;

-- 2022-08-05T09:51:39.561Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703903
;

-- 2022-08-05T09:51:39.562Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703903, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701419
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Name
-- Column: C_Project_WO_ObjectUnderTest.WOObjectName
-- 2022-08-05T09:51:39.640Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583632,703904,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Name des Prüfgegenstandes (z.B. Typbezeichnung)',255,'D',0,'Y','N','N','N','N','N','N','N','Name',100,1,1,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:39.641Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703904 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:39.643Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581106)
;

-- 2022-08-05T09:51:39.647Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703904
;

-- 2022-08-05T09:51:39.647Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703904)
;

-- 2022-08-05T09:51:39.650Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703904
;

-- 2022-08-05T09:51:39.651Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703904, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701420
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Verbleib
-- Column: C_Project_WO_ObjectUnderTest.WOObjectWhereabouts
-- 2022-08-05T09:51:39.732Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583633,703905,0,546545,0,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Verbleib des Prüfgegenstandes nach der Prüfung.',255,'D',0,'Y','N','N','N','N','N','N','N','Verbleib',110,1,1,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:39.733Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703905 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T09:51:39.734Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581107)
;

-- 2022-08-05T09:51:39.739Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703905
;

-- 2022-08-05T09:51:39.739Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703905)
;

-- 2022-08-05T09:51:39.742Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 703905
;

-- 2022-08-05T09:51:39.743Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 703905, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 701421
;

-- 2022-08-05T09:51:39.820Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546545,545165,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'default')
;

-- 2022-08-05T09:51:39.821Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545165 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-08-05T09:51:39.823Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545165
;

-- 2022-08-05T09:51:39.824Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545165, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545076
;

-- 2022-08-05T09:51:39.901Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546275,545165,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:39.979Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546275,549700,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Anzahl der Prüfgegenstände
-- Column: C_Project_WO_ObjectUnderTest.NumberOfObjectsUnderTest
-- 2022-08-05T09:51:40.063Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703900,0,546545,549700,611306,'F',TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu prüfenden Objekte','Y','N','N','Y','N','N','N',0,'Anzahl der Prüfgegenstände',10,0,0,TO_TIMESTAMP('2022-08-05 11:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Name
-- Column: C_Project_WO_ObjectUnderTest.WOObjectName
-- 2022-08-05T09:51:40.147Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703904,0,546545,549700,611307,'F',TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Name des Prüfgegenstandes (z.B. Typbezeichnung)','Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Klasse
-- Column: C_Project_WO_ObjectUnderTest.WOObjectType
-- 2022-08-05T09:51:40.231Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703903,0,546545,549700,611308,'F',TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Art des Prüfgegenstandes','Y','N','N','Y','N','N','N',0,'Klasse',30,0,0,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Hersteller
-- Column: C_Project_WO_ObjectUnderTest.WOManufacturer
-- 2022-08-05T09:51:40.315Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703902,0,546545,549700,611309,'F',TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Hersteller des Prüfgegenstandes','Y','N','N','Y','N','N','N',0,'Hersteller',40,0,0,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Lieferhinweis
-- Column: C_Project_WO_ObjectUnderTest.WODeliveryNote
-- 2022-08-05T09:51:40.400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703901,0,546545,549700,611310,'F',TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Lieferhinweis',50,0,0,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Verbleib
-- Column: C_Project_WO_ObjectUnderTest.WOObjectWhereabouts
-- 2022-08-05T09:51:40.482Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703905,0,546545,549700,611311,'F',TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Verbleib des Prüfgegenstandes nach der Prüfung.','Y','N','N','Y','N','N','N',0,'Verbleib',60,0,0,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:40.557Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546276,545165,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T09:51:40.640Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546276,549701,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',10,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Aktiv
-- Column: C_Project_WO_ObjectUnderTest.IsActive
-- 2022-08-05T09:51:40.739Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703897,0,546545,549701,611312,'F',TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Sektion
-- Column: C_Project_WO_ObjectUnderTest.AD_Org_ID
-- 2022-08-05T09:51:40.813Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703896,0,546545,549701,611313,'F',TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Mandant
-- Column: C_Project_WO_ObjectUnderTest.AD_Client_ID
-- 2022-08-05T09:51:40.895Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703895,0,546545,549701,611314,'F',TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',30,0,0,TO_TIMESTAMP('2022-08-05 11:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

