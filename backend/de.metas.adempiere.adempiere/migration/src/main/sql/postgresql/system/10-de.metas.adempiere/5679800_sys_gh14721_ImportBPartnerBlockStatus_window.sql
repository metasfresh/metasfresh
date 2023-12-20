-- 2023-03-01T06:03:28.306Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582115,0,TO_TIMESTAMP('2023-03-01 08:03:27','YYYY-MM-DD HH24:MI:SS'),100,'The "Import Business Partner block status" window contains the block statuses of business partners to be imported the metasfresh through the "Import BPartner Block Status" process.','D','Y','Import Business Partner block status','Import Business Partner block status',TO_TIMESTAMP('2023-03-01 08:03:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:03:28.315Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582115 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Import Business Partner block status, InternalName=Import Business Partner block status
-- 2023-03-01T06:04:07.561Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582115,0,541683,TO_TIMESTAMP('2023-03-01 08:04:07','YYYY-MM-DD HH24:MI:SS'),100,'The "Import Business Partner block status" window contains the block statuses of business partners to be imported the metasfresh through the "Import BPartner Block Status" process.','D','Import Business Partner block status','Y','N','N','Y','N','N','N','Y','Import Business Partner block status','N',TO_TIMESTAMP('2023-03-01 08:04:07','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-03-01T06:04:07.565Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541683 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-03-01T06:04:07.594Z
/* DDL */  select update_window_translation_from_ad_element(582115) 
;

-- 2023-03-01T06:04:07.606Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541683
;

-- 2023-03-01T06:04:07.611Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541683)
;

-- 2023-03-01T06:05:09.483Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582116,0,TO_TIMESTAMP('2023-03-01 08:05:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Import BPartner Block Status','Import BPartner Block Status',TO_TIMESTAMP('2023-03-01 08:05:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:05:09.485Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582116 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Tab: Import Business Partner block status(541683,D) -> Import BP Block Status
-- Table: I_BPartner_BlockStatus
-- 2023-03-01T06:06:48.352Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582109,0,546843,542318,541683,'Y',TO_TIMESTAMP('2023-03-01 08:06:48','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','I_BPartner_BlockStatus','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Import BP Block Status','N',10,0,TO_TIMESTAMP('2023-03-01 08:06:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:06:48.365Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546843 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-03-01T06:06:48.369Z
/* DDL */  select update_tab_translation_from_ad_element(582109) 
;

-- 2023-03-01T06:06:48.373Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546843)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Mandant
-- Column: I_BPartner_BlockStatus.AD_Client_ID
-- 2023-03-01T06:07:08.989Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586244,712736,0,546843,TO_TIMESTAMP('2023-03-01 08:07:08','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-03-01 08:07:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:08.995Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712736 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:09.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-03-01T06:07:09.481Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712736
;

-- 2023-03-01T06:07:09.487Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712736)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Probleme
-- Column: I_BPartner_BlockStatus.AD_Issue_ID
-- 2023-03-01T06:07:09.577Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586245,712737,0,546843,TO_TIMESTAMP('2023-03-01 08:07:09','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2023-03-01 08:07:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:09.579Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712737 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:09.580Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2023-03-01T06:07:09.592Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712737
;

-- 2023-03-01T06:07:09.592Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712737)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Sektion
-- Column: I_BPartner_BlockStatus.AD_Org_ID
-- 2023-03-01T06:07:09.685Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586246,712738,0,546843,TO_TIMESTAMP('2023-03-01 08:07:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-03-01 08:07:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:09.688Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712738 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:09.690Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-03-01T06:07:09.840Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712738
;

-- 2023-03-01T06:07:09.841Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712738)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Daten Import
-- Column: I_BPartner_BlockStatus.C_DataImport_ID
-- 2023-03-01T06:07:09.949Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586247,712739,0,546843,TO_TIMESTAMP('2023-03-01 08:07:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Daten Import',TO_TIMESTAMP('2023-03-01 08:07:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:09.950Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712739 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:09.952Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913) 
;

-- 2023-03-01T06:07:09.957Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712739
;

-- 2023-03-01T06:07:09.958Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712739)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Data Import Run
-- Column: I_BPartner_BlockStatus.C_DataImport_Run_ID
-- 2023-03-01T06:07:10.048Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586248,712740,0,546843,TO_TIMESTAMP('2023-03-01 08:07:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data Import Run',TO_TIMESTAMP('2023-03-01 08:07:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:10.050Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712740 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:10.052Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577114) 
;

-- 2023-03-01T06:07:10.057Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712740
;

-- 2023-03-01T06:07:10.057Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712740)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Import-Fehlermeldung
-- Column: I_BPartner_BlockStatus.I_ErrorMsg
-- 2023-03-01T06:07:10.141Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586251,712741,0,546843,TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden',2000,'D','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','N','N','N','N','N','Import-Fehlermeldung',TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:10.143Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712741 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:10.144Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(912) 
;

-- 2023-03-01T06:07:10.158Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712741
;

-- 2023-03-01T06:07:10.158Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712741)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Importiert
-- Column: I_BPartner_BlockStatus.I_IsImported
-- 2023-03-01T06:07:10.251Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586252,712742,0,546843,TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?',1,'D','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','N','N','N','N','N','Importiert',TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:10.252Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712742 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:10.254Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(913) 
;

-- 2023-03-01T06:07:10.261Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712742
;

-- 2023-03-01T06:07:10.262Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712742)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Import Line Content
-- Column: I_BPartner_BlockStatus.I_LineContent
-- 2023-03-01T06:07:10.347Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586253,712743,0,546843,TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100,4000,'D','Y','N','N','N','N','N','N','N','Import Line Content',TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:10.349Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712743 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:10.351Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577115) 
;

-- 2023-03-01T06:07:10.356Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712743
;

-- 2023-03-01T06:07:10.356Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712743)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Import Line No
-- Column: I_BPartner_BlockStatus.I_LineNo
-- 2023-03-01T06:07:10.445Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586254,712744,0,546843,TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Import Line No',TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:10.448Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712744 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:10.450Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577116) 
;

-- 2023-03-01T06:07:10.453Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712744
;

-- 2023-03-01T06:07:10.454Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712744)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Aktiv
-- Column: I_BPartner_BlockStatus.IsActive
-- 2023-03-01T06:07:10.542Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586255,712745,0,546843,TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:10.543Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712745 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:10.545Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-03-01T06:07:10.674Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712745
;

-- 2023-03-01T06:07:10.674Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712745)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Verarbeitet
-- Column: I_BPartner_BlockStatus.Processed
-- 2023-03-01T06:07:10.769Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586256,712746,0,546843,TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:10.770Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712746 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:10.771Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-03-01T06:07:10.795Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712746
;

-- 2023-03-01T06:07:10.796Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712746)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Import BP Block Status
-- Column: I_BPartner_BlockStatus.I_BPartner_BlockStatus_ID
-- 2023-03-01T06:07:10.884Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586259,712747,0,546843,TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Import BP Block Status',TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:10.885Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712747 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:10.887Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582109) 
;

-- 2023-03-01T06:07:10.892Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712747
;

-- 2023-03-01T06:07:10.892Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712747)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> SAP BPartner Id
-- Column: I_BPartner_BlockStatus.SAP_BPartnerCode
-- 2023-03-01T06:07:10.979Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586261,712748,0,546843,TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','SAP BPartner Id',TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:10.981Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712748 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:10.982Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581932) 
;

-- 2023-03-01T06:07:10.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712748
;

-- 2023-03-01T06:07:10.986Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712748)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Grund
-- Column: I_BPartner_BlockStatus.Reason
-- 2023-03-01T06:07:11.072Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586262,712749,0,546843,TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','N','N','N','N','N','N','N','Grund',TO_TIMESTAMP('2023-03-01 08:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:11.074Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712749 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:11.077Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576788) 
;

-- 2023-03-01T06:07:11.082Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712749
;

-- 2023-03-01T06:07:11.082Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712749)
;

-- Field: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> Sperrstatus
-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-01T06:07:11.168Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586263,712750,0,546843,TO_TIMESTAMP('2023-03-01 08:07:11','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Sperrstatus',TO_TIMESTAMP('2023-03-01 08:07:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T06:07:11.170Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712750 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T06:07:11.173Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582089) 
;

-- 2023-03-01T06:07:11.179Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712750
;

-- 2023-03-01T06:07:11.179Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712750)
;

-- Tab: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D)
-- UI Section: main
-- 2023-03-01T06:09:48.588Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546843,545463,TO_TIMESTAMP('2023-03-01 08:09:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-01 08:09:48','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-03-01T06:09:48.591Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545463 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main
-- UI Column: 10
-- 2023-03-01T06:09:59.734Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546673,545463,TO_TIMESTAMP('2023-03-01 08:09:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-01 08:09:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main
-- UI Column: 20
-- 2023-03-01T06:10:01.381Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546674,545463,TO_TIMESTAMP('2023-03-01 08:10:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-03-01 08:10:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20
-- UI Element Group: flags
-- 2023-03-01T06:10:19.332Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546674,550417,TO_TIMESTAMP('2023-03-01 08:10:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-03-01 08:10:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> flags.Aktiv
-- Column: I_BPartner_BlockStatus.IsActive
-- 2023-03-01T06:11:06.107Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712745,0,546843,615936,550417,'F',TO_TIMESTAMP('2023-03-01 08:11:05','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-03-01 08:11:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20
-- UI Element Group: import
-- 2023-03-01T06:11:22.767Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546674,550418,TO_TIMESTAMP('2023-03-01 08:11:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','import',20,TO_TIMESTAMP('2023-03-01 08:11:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> import.Importiert
-- Column: I_BPartner_BlockStatus.I_IsImported
-- 2023-03-01T06:11:51.409Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712742,0,546843,615937,550418,'F',TO_TIMESTAMP('2023-03-01 08:11:51','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','Y','N','N','N',0,'Importiert',10,0,0,TO_TIMESTAMP('2023-03-01 08:11:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> import.Import-Fehlermeldung
-- Column: I_BPartner_BlockStatus.I_ErrorMsg
-- 2023-03-01T06:12:03.246Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712741,0,546843,615938,550418,'F',TO_TIMESTAMP('2023-03-01 08:12:03','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','Y','N','N','N',0,'Import-Fehlermeldung',20,0,0,TO_TIMESTAMP('2023-03-01 08:12:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> import.Import Line No
-- Column: I_BPartner_BlockStatus.I_LineNo
-- 2023-03-01T06:12:14.121Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712744,0,546843,615939,550418,'F',TO_TIMESTAMP('2023-03-01 08:12:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import Line No',30,0,0,TO_TIMESTAMP('2023-03-01 08:12:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> import.Import Line Content
-- Column: I_BPartner_BlockStatus.I_LineContent
-- 2023-03-01T06:12:25.194Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712743,0,546843,615940,550418,'F',TO_TIMESTAMP('2023-03-01 08:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import Line Content',40,0,0,TO_TIMESTAMP('2023-03-01 08:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> import.Daten Import
-- Column: I_BPartner_BlockStatus.C_DataImport_ID
-- 2023-03-01T06:13:32.929Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712739,0,546843,615941,550418,'F',TO_TIMESTAMP('2023-03-01 08:13:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Daten Import',50,0,0,TO_TIMESTAMP('2023-03-01 08:13:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> import.Data Import Run
-- Column: I_BPartner_BlockStatus.C_DataImport_Run_ID
-- 2023-03-01T06:13:50.303Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712740,0,546843,615942,550418,'F',TO_TIMESTAMP('2023-03-01 08:13:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data Import Run',60,0,0,TO_TIMESTAMP('2023-03-01 08:13:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20
-- UI Element Group: org
-- 2023-03-01T06:14:08.075Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546674,550419,TO_TIMESTAMP('2023-03-01 08:14:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2023-03-01 08:14:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> org.Sektion
-- Column: I_BPartner_BlockStatus.AD_Org_ID
-- 2023-03-01T06:14:54.171Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712738,0,546843,615943,550419,'F',TO_TIMESTAMP('2023-03-01 08:14:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2023-03-01 08:14:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> org.Mandant
-- Column: I_BPartner_BlockStatus.AD_Client_ID
-- 2023-03-01T06:15:06.756Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712736,0,546843,615944,550419,'F',TO_TIMESTAMP('2023-03-01 08:15:06','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-03-01 08:15:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 20 -> flags.Verarbeitet
-- Column: I_BPartner_BlockStatus.Processed
-- 2023-03-01T06:16:29.897Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712746,0,546843,615945,550417,'F',TO_TIMESTAMP('2023-03-01 08:16:29','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',20,0,0,TO_TIMESTAMP('2023-03-01 08:16:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 10
-- UI Element Group: main
-- 2023-03-01T06:17:08.501Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546673,550420,TO_TIMESTAMP('2023-03-01 08:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-03-01 08:17:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 10 -> main.SAP BPartner Id
-- Column: I_BPartner_BlockStatus.SAP_BPartnerCode
-- 2023-03-01T06:17:34.420Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712748,0,546843,615946,550420,'F',TO_TIMESTAMP('2023-03-01 08:17:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SAP BPartner Id',10,0,0,TO_TIMESTAMP('2023-03-01 08:17:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 10 -> main.Sperrstatus
-- Column: I_BPartner_BlockStatus.BlockStatus
-- 2023-03-01T06:17:44.888Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712750,0,546843,615947,550420,'F',TO_TIMESTAMP('2023-03-01 08:17:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sperrstatus',20,0,0,TO_TIMESTAMP('2023-03-01 08:17:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Business Partner block status(541683,D) -> Import BP Block Status(546843,D) -> main -> 10 -> main.Grund
-- Column: I_BPartner_BlockStatus.Reason
-- 2023-03-01T06:17:58.216Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712749,0,546843,615948,550420,'F',TO_TIMESTAMP('2023-03-01 08:17:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Grund',30,0,0,TO_TIMESTAMP('2023-03-01 08:17:58','YYYY-MM-DD HH24:MI:SS'),100)
;


-- Name: Import Business Partner block status
-- Action Type: W
-- Window: Import Business Partner block status(541683,D)
-- 2023-03-01T08:04:04.214Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582115,542064,0,541683,TO_TIMESTAMP('2023-03-01 10:04:04','YYYY-MM-DD HH24:MI:SS'),100,'The "Import Business Partner block status" window contains the block statuses of business partners to be imported the metasfresh through the "Import BPartner Block Status" process.','D','Import Business Partner block status','Y','N','N','N','N','Import Business Partner block status',TO_TIMESTAMP('2023-03-01 10:04:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T08:04:04.217Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542064 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-03-01T08:04:04.219Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542064, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542064)
;

-- 2023-03-01T08:04:04.259Z
/* DDL */  select update_menu_translation_from_ad_element(582115)
;

-- Reordering children of `Shipment`
-- Node name: `Shipment (M_InOut)`
-- 2023-03-01T08:04:04.437Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2023-03-01T08:04:04.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2023-03-01T08:04:04.443Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2023-03-01T08:04:04.443Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2023-03-01T08:04:04.444Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2023-03-01T08:04:04.445Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2023-03-01T08:04:04.445Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2023-03-01T08:04:04.446Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2023-03-01T08:04:04.447Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2023-03-01T08:04:04.447Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2023-03-01T08:04:04.448Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2023-03-01T08:04:04.450Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2023-03-01T08:04:04.450Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2023-03-01T08:04:04.451Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2023-03-01T08:04:04.451Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2023-03-01T08:04:04.452Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-03-01T08:04:04.453Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI Lieferavis Pack (DESADV) (EDI_DesadvLine_Pack)`
-- 2023-03-01T08:04:04.454Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-03-01T08:04:04.454Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-03-01T08:04:04.455Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2023-03-01T08:04:04.456Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner block status`
-- 2023-03-01T08:04:04.456Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542064 AND AD_Tree_ID=10
;

-- Reordering children of `CRM`
-- Node name: `Import Business Partner block status`
-- 2023-03-01T08:04:19.370Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542064 AND AD_Tree_ID=10
;

-- Node name: `Change Business Partner Block (C_BPartner_Block_File)`
-- 2023-03-01T08:04:19.371Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542062 AND AD_Tree_ID=10
;

-- Node name: `Request (R_Request)`
-- 2023-03-01T08:04:19.372Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=237 AND AD_Tree_ID=10
;

-- Node name: `Request (all) (R_Request)`
-- 2023-03-01T08:04:19.373Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=308 AND AD_Tree_ID=10
;

-- Node name: `Company Phone Book (AD_User)`
-- 2023-03-01T08:04:19.374Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- Node name: `Business Partner (C_BPartner)`
-- 2023-03-01T08:04:19.374Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- Node name: `Partner Export (C_BPartner_Export)`
-- 2023-03-01T08:04:19.375Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541728 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents (C_Doc_Outbound_Log)`
-- 2023-03-01T08:04:19.376Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-03-01T08:04:19.376Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-03-01T08:04:19.377Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-03-01T08:04:19.378Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- Node name: `BPartne Global ID (I_BPartner_GlobalID)`
-- 2023-03-01T08:04:19.379Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- Node name: `Import User (I_User)`
-- 2023-03-01T08:04:19.379Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- Node name: `Phone call (R_Request)`
-- 2023-03-01T08:04:19.380Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541896 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema (C_Phonecall_Schema)`
-- 2023-03-01T08:04:19.381Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Node name: `Phonecall Schema Version (C_Phonecall_Schema_Version)`
-- 2023-03-01T08:04:19.382Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schedule (C_Phonecall_Schedule)`
-- 2023-03-01T08:04:19.382Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;

