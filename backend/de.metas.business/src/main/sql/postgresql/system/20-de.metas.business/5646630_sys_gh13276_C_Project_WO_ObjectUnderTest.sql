-- 2022-07-12T14:35:30.590Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581124,0,TO_TIMESTAMP('2022-07-12 15:35:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prüfgegenstand','Prüfgegenstand',TO_TIMESTAMP('2022-07-12 15:35:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:35:30.653Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581124 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T14:36:58.205Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Object Under Test', PrintName='Object Under Test',Updated=TO_TIMESTAMP('2022-07-12 15:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581124 AND AD_Language='en_US'
;

-- 2022-07-12T14:36:58.290Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581124,'en_US') 
;

-- Tab: Prüf Projekt -> Prüfgegenstand
-- Table: C_Project_WO_ObjectUnderTest
-- 2022-07-12T14:38:32.191Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581124,0,546433,542184,541512,'Y',TO_TIMESTAMP('2022-07-12 15:38:31','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_ObjectUnderTest','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Prüfgegenstand','N',40,0,TO_TIMESTAMP('2022-07-12 15:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:38:32.261Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546433 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-07-12T14:38:32.325Z
/* DDL */  select update_tab_translation_from_ad_element(581124) 
;

-- 2022-07-12T14:38:32.392Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546433)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Mandant
-- Column: C_Project_WO_ObjectUnderTest.AD_Client_ID
-- 2022-07-12T14:39:10.758Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583619,701411,0,546433,TO_TIMESTAMP('2022-07-12 15:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-07-12 15:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:10.821Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701411 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:10.885Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-07-12T14:39:11.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701411
;

-- 2022-07-12T14:39:11.127Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701411)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Sektion
-- Column: C_Project_WO_ObjectUnderTest.AD_Org_ID
-- 2022-07-12T14:39:12.091Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583620,701412,0,546433,TO_TIMESTAMP('2022-07-12 15:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-07-12 15:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:12.155Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701412 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:12.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-07-12T14:39:12.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701412
;

-- 2022-07-12T14:39:12.509Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701412)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Aktiv
-- Column: C_Project_WO_ObjectUnderTest.IsActive
-- 2022-07-12T14:39:13.453Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583623,701413,0,546433,TO_TIMESTAMP('2022-07-12 15:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-07-12 15:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:13.532Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701413 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:13.596Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-07-12T14:39:13.845Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701413
;

-- 2022-07-12T14:39:13.908Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701413)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> C_Project_WO_ObjectUnderTest
-- Column: C_Project_WO_ObjectUnderTest.C_Project_WO_ObjectUnderTest_ID
-- 2022-07-12T14:39:14.912Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583626,701414,0,546433,TO_TIMESTAMP('2022-07-12 15:39:14','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','C_Project_WO_ObjectUnderTest',TO_TIMESTAMP('2022-07-12 15:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:14.974Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701414 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:15.095Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581101) 
;

-- 2022-07-12T14:39:15.157Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701414
;

-- 2022-07-12T14:39:15.214Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701414)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Projekt
-- Column: C_Project_WO_ObjectUnderTest.C_Project_ID
-- 2022-07-12T14:39:16.307Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583627,701415,0,546433,TO_TIMESTAMP('2022-07-12 15:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-07-12 15:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:16.729Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701415 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:16.798Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-07-12T14:39:16.871Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701415
;

-- 2022-07-12T14:39:16.936Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701415)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Anzahl der Prüfgegenstände
-- Column: C_Project_WO_ObjectUnderTest.NumberOfObjectsUnderTest
-- 2022-07-12T14:39:18.667Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583628,701416,0,546433,TO_TIMESTAMP('2022-07-12 15:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu prüfenden Objekte',14,'D','Y','N','N','N','N','N','N','N','Anzahl der Prüfgegenstände',TO_TIMESTAMP('2022-07-12 15:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:18.730Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701416 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:18.792Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581102) 
;

-- 2022-07-12T14:39:18.856Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701416
;

-- 2022-07-12T14:39:18.914Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701416)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Lieferhinweis
-- Column: C_Project_WO_ObjectUnderTest.WODeliveryNote
-- 2022-07-12T14:39:19.859Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583629,701417,0,546433,TO_TIMESTAMP('2022-07-12 15:39:19','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Lieferhinweis',TO_TIMESTAMP('2022-07-12 15:39:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:19.923Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701417 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:19.984Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581103) 
;

-- 2022-07-12T14:39:20.103Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701417
;

-- 2022-07-12T14:39:20.162Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701417)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Hersteller
-- Column: C_Project_WO_ObjectUnderTest.WOManufacturer
-- 2022-07-12T14:39:21.099Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583630,701418,0,546433,TO_TIMESTAMP('2022-07-12 15:39:20','YYYY-MM-DD HH24:MI:SS'),100,'Hersteller des Prüfgegenstandes',255,'D','','Y','N','N','N','N','N','N','N','Hersteller',TO_TIMESTAMP('2022-07-12 15:39:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:21.163Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701418 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:21.227Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581104) 
;

-- 2022-07-12T14:39:21.286Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701418
;

-- 2022-07-12T14:39:21.406Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701418)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Klasse
-- Column: C_Project_WO_ObjectUnderTest.WOObjectType
-- 2022-07-12T14:39:23.285Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583631,701419,0,546433,TO_TIMESTAMP('2022-07-12 15:39:22','YYYY-MM-DD HH24:MI:SS'),100,'Art des Prüfgegenstandes',255,'D','Y','N','N','N','N','N','N','N','Klasse',TO_TIMESTAMP('2022-07-12 15:39:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:23.356Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701419 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:23.492Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581105) 
;

-- 2022-07-12T14:39:23.558Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701419
;

-- 2022-07-12T14:39:23.626Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701419)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Name
-- Column: C_Project_WO_ObjectUnderTest.WOObjectName
-- 2022-07-12T14:39:24.643Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583632,701420,0,546433,TO_TIMESTAMP('2022-07-12 15:39:23','YYYY-MM-DD HH24:MI:SS'),100,'Name des Prüfgegenstandes (z.B. Typbezeichnung)',255,'D','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2022-07-12 15:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:24.717Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701420 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:24.857Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581106) 
;

-- 2022-07-12T14:39:24.924Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701420
;

-- 2022-07-12T14:39:24.990Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701420)
;

-- Field: Prüf Projekt -> Prüfgegenstand -> Verbleib
-- Column: C_Project_WO_ObjectUnderTest.WOObjectWhereabouts
-- 2022-07-12T14:39:25.962Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583633,701421,0,546433,TO_TIMESTAMP('2022-07-12 15:39:25','YYYY-MM-DD HH24:MI:SS'),100,'Verbleib des Prüfgegenstandes nach der Prüfung.',255,'D','Y','N','N','N','N','N','N','N','Verbleib',TO_TIMESTAMP('2022-07-12 15:39:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:39:26.027Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701421 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-12T14:39:26.094Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581107) 
;

-- 2022-07-12T14:39:26.216Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701421
;

-- 2022-07-12T14:39:26.296Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701421)
;

-- 2022-07-12T14:41:30.600Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546433,545076,TO_TIMESTAMP('2022-07-12 15:41:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-12 15:41:29','YYYY-MM-DD HH24:MI:SS'),100,'default')
;

-- 2022-07-12T14:41:30.662Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545076 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-07-12T14:42:49.382Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546147,545076,TO_TIMESTAMP('2022-07-12 15:42:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-12 15:42:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:43:14.041Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546147,549474,TO_TIMESTAMP('2022-07-12 15:43:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2022-07-12 15:43:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Anzahl der Prüfgegenstände
-- Column: C_Project_WO_ObjectUnderTest.NumberOfObjectsUnderTest
-- 2022-07-12T14:45:31.913Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701416,0,546433,549474,610110,'F',TO_TIMESTAMP('2022-07-12 15:45:31','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu prüfenden Objekte','Y','N','N','Y','N','N','N',0,'Anzahl der Prüfgegenstände',10,0,0,TO_TIMESTAMP('2022-07-12 15:45:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Name
-- Column: C_Project_WO_ObjectUnderTest.WOObjectName
-- 2022-07-12T14:45:58.626Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701420,0,546433,549474,610111,'F',TO_TIMESTAMP('2022-07-12 15:45:57','YYYY-MM-DD HH24:MI:SS'),100,'Name des Prüfgegenstandes (z.B. Typbezeichnung)','Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2022-07-12 15:45:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Klasse
-- Column: C_Project_WO_ObjectUnderTest.WOObjectType
-- 2022-07-12T14:46:59.392Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701419,0,546433,549474,610112,'F',TO_TIMESTAMP('2022-07-12 15:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Art des Prüfgegenstandes','Y','N','N','Y','N','N','N',0,'Klasse',30,0,0,TO_TIMESTAMP('2022-07-12 15:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Hersteller
-- Column: C_Project_WO_ObjectUnderTest.WOManufacturer
-- 2022-07-12T14:47:27.265Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701418,0,546433,549474,610113,'F',TO_TIMESTAMP('2022-07-12 15:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Hersteller des Prüfgegenstandes','Y','N','N','Y','N','N','N',0,'Hersteller',40,0,0,TO_TIMESTAMP('2022-07-12 15:47:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Lieferhinweis
-- Column: C_Project_WO_ObjectUnderTest.WODeliveryNote
-- 2022-07-12T14:48:16.421Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701417,0,546433,549474,610114,'F',TO_TIMESTAMP('2022-07-12 15:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Lieferhinweis',50,0,0,TO_TIMESTAMP('2022-07-12 15:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Verbleib
-- Column: C_Project_WO_ObjectUnderTest.WOObjectWhereabouts
-- 2022-07-12T14:48:40.666Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701421,0,546433,549474,610115,'F',TO_TIMESTAMP('2022-07-12 15:48:39','YYYY-MM-DD HH24:MI:SS'),100,'Verbleib des Prüfgegenstandes nach der Prüfung.','Y','N','N','Y','N','N','N',0,'Verbleib',60,0,0,TO_TIMESTAMP('2022-07-12 15:48:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:49:15.736Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546148,545076,TO_TIMESTAMP('2022-07-12 15:49:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-07-12 15:49:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T14:49:56.670Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546148,549475,TO_TIMESTAMP('2022-07-12 15:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',10,TO_TIMESTAMP('2022-07-12 15:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Aktiv
-- Column: C_Project_WO_ObjectUnderTest.IsActive
-- 2022-07-12T14:50:28.372Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701413,0,546433,549475,610116,'F',TO_TIMESTAMP('2022-07-12 15:50:27','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-07-12 15:50:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Sektion
-- Column: C_Project_WO_ObjectUnderTest.AD_Org_ID
-- 2022-07-12T14:50:56.958Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701412,0,546433,549475,610117,'F',TO_TIMESTAMP('2022-07-12 15:50:56','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-07-12 15:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Prüfgegenstand.Mandant
-- Column: C_Project_WO_ObjectUnderTest.AD_Client_ID
-- 2022-07-12T14:51:21.068Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701411,0,546433,549475,610118,'F',TO_TIMESTAMP('2022-07-12 15:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',30,0,0,TO_TIMESTAMP('2022-07-12 15:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Prüf Projekt -> Prüfgegenstand
-- Table: C_Project_WO_ObjectUnderTest
-- 2022-07-12T14:53:29.804Z
UPDATE AD_Tab SET AD_Column_ID=583627,Updated=TO_TIMESTAMP('2022-07-12 15:53:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546433
;

-- Tab: Prüf Projekt -> Prüfgegenstand
-- Table: C_Project_WO_ObjectUnderTest
-- 2022-07-12T14:53:51.241Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2022-07-12 15:53:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546433
;

-- Tab: Prüf Projekt -> Prüfgegenstand
-- Table: C_Project_WO_ObjectUnderTest
-- 2022-07-12T14:54:08.114Z
UPDATE AD_Tab SET Parent_Column_ID=1349,Updated=TO_TIMESTAMP('2022-07-12 15:54:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546433
;

