-- 2022-08-23T04:55:18.270Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581363,0,TO_TIMESTAMP('2022-08-23 07:55:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Import - Invoice Candidate','Import - Invoice Candidate',TO_TIMESTAMP('2022-08-23 07:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T04:55:18.274Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581363 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-08-23T04:55:39.439Z
UPDATE AD_Element_Trl SET Name='Import Invoice Candidate', PrintName='Import Invoice Candidate',Updated=TO_TIMESTAMP('2022-08-23 07:55:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='en_US'
;

-- 2022-08-23T04:55:39.461Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'en_US') 
;

-- Window: Import - Invoice Candidate, InternalName=null
-- 2022-08-23T04:56:10.794Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581363,0,541605,TO_TIMESTAMP('2022-08-23 07:56:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Import - Invoice Candidate','N',TO_TIMESTAMP('2022-08-23 07:56:10','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-08-23T04:56:10.798Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541605 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-08-23T04:56:10.800Z
/* DDL */  select update_window_translation_from_ad_element(581363) 
;

-- 2022-08-23T04:56:10.809Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541605
;

-- 2022-08-23T04:56:10.818Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541605)
;

-- Window: Import - Invoice Candidate, InternalName=I_Invoice_Candidate
-- 2022-08-23T04:56:42.768Z
UPDATE AD_Window SET InternalName='I_Invoice_Candidate',Updated=TO_TIMESTAMP('2022-08-23 07:56:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541605
;

-- 2022-08-23T04:58:47.238Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581364,0,TO_TIMESTAMP('2022-08-23 07:58:47','YYYY-MM-DD HH24:MI:SS'),100,'Import - Invoice Candidate','D','Y','Invoice Candidate','Invoice Candidate',TO_TIMESTAMP('2022-08-23 07:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T04:58:47.242Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581364 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Tab: Import - Invoice Candidate -> Import - Invoice candiate
-- Table: I_Invoice_Candidate
-- 2022-08-23T04:59:36.751Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581357,0,546594,542207,541605,'Y',TO_TIMESTAMP('2022-08-23 07:59:36','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','I_Invoice_Candidate','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','Y','N','N',0,'Import - Invoice candiate','N',10,0,TO_TIMESTAMP('2022-08-23 07:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T04:59:36.758Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546594 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-08-23T04:59:36.765Z
/* DDL */  select update_tab_translation_from_ad_element(581357) 
;

-- 2022-08-23T04:59:36.769Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546594)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Rechnungspartner
-- Column: I_Invoice_Candidate.Bill_BPartner_ID
-- 2022-08-23T05:00:35.939Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584198,705479,0,546594,TO_TIMESTAMP('2022-08-23 08:00:35','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner für die Rechnungsstellung',10,'D','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','N','N','N','N','N','Rechnungspartner',TO_TIMESTAMP('2022-08-23 08:00:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:00:35.944Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705479 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:00:35.947Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2039) 
;

-- 2022-08-23T05:00:35.976Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705479
;

-- 2022-08-23T05:00:35.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705479)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Mandant
-- Column: I_Invoice_Candidate.AD_Client_ID
-- 2022-08-23T05:01:08.200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584158,705480,0,546594,TO_TIMESTAMP('2022-08-23 08:01:08','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-08-23 08:01:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:01:08.202Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705480 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:01:08.203Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-08-23T05:01:08.685Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705480
;

-- 2022-08-23T05:01:08.685Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705480)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Sektion
-- Column: I_Invoice_Candidate.AD_Org_ID
-- 2022-08-23T05:01:21.670Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584159,705481,0,546594,TO_TIMESTAMP('2022-08-23 08:01:21','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-08-23 08:01:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:01:21.670Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705481 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:01:21.672Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-08-23T05:01:21.859Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705481
;

-- 2022-08-23T05:01:21.860Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705481)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Bill_BPartner_Value
-- Column: I_Invoice_Candidate.Bill_BPartner_Value
-- 2022-08-23T05:01:33.384Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584199,705482,0,546594,TO_TIMESTAMP('2022-08-23 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Bill_BPartner_Value',TO_TIMESTAMP('2022-08-23 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:01:33.385Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705482 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:01:33.388Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581361) 
;

-- 2022-08-23T05:01:33.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705482
;

-- 2022-08-23T05:01:33.391Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705482)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Rechnungsstandort
-- Column: I_Invoice_Candidate.Bill_Location_ID
-- 2022-08-23T05:01:42.676Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584168,705483,0,546594,TO_TIMESTAMP('2022-08-23 08:01:42','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'D','Y','N','N','N','N','N','N','N','Rechnungsstandort',TO_TIMESTAMP('2022-08-23 08:01:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:01:42.677Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705483 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:01:42.678Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2040) 
;

-- 2022-08-23T05:01:42.686Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705483
;

-- 2022-08-23T05:01:42.687Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705483)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Rechnungskontakt
-- Column: I_Invoice_Candidate.Bill_User_ID
-- 2022-08-23T05:01:52.504Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584169,705484,0,546594,TO_TIMESTAMP('2022-08-23 08:01:52','YYYY-MM-DD HH24:MI:SS'),100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung',10,'D','Y','N','N','N','N','N','N','N','Rechnungskontakt',TO_TIMESTAMP('2022-08-23 08:01:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:01:52.507Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705484 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:01:52.510Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2041) 
;

-- 2022-08-23T05:01:52.513Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705484
;

-- 2022-08-23T05:01:52.514Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705484)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Data Import Run
-- Column: I_Invoice_Candidate.C_DataImport_Run_ID
-- 2022-08-23T05:02:01.953Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584191,705485,0,546594,TO_TIMESTAMP('2022-08-23 08:02:01','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data Import Run',TO_TIMESTAMP('2022-08-23 08:02:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:02:01.954Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705485 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:02:01.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577114) 
;

-- 2022-08-23T05:02:01.958Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705485
;

-- 2022-08-23T05:02:01.958Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705485)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Maßeinheit
-- Column: I_Invoice_Candidate.C_UOM_ID
-- 2022-08-23T05:02:12.510Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584174,705486,0,546594,TO_TIMESTAMP('2022-08-23 08:02:12','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',22,'D','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2022-08-23 08:02:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:02:12.511Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705486 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:02:12.512Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2022-08-23T05:02:12.530Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705486
;

-- 2022-08-23T05:02:12.531Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705486)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> C_UOM_X12DE355
-- Column: I_Invoice_Candidate.C_UOM_X12DE355
-- 2022-08-23T05:02:22.085Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584200,705487,0,546594,TO_TIMESTAMP('2022-08-23 08:02:21','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','C_UOM_X12DE355',TO_TIMESTAMP('2022-08-23 08:02:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:02:22.086Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705487 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:02:22.089Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581362) 
;

-- 2022-08-23T05:02:22.090Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705487
;

-- 2022-08-23T05:02:22.091Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705487)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Auftragsdatum
-- Column: I_Invoice_Candidate.DateOrdered
-- 2022-08-23T05:02:35.128Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584171,705488,0,546594,TO_TIMESTAMP('2022-08-23 08:02:34','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags',7,'D','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','N','N','N','N','N','N','Auftragsdatum',TO_TIMESTAMP('2022-08-23 08:02:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:02:35.129Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705488 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:02:35.129Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(268) 
;

-- 2022-08-23T05:02:35.134Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705488
;

-- 2022-08-23T05:02:35.134Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705488)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Beschreibung
-- Column: I_Invoice_Candidate.Description
-- 2022-08-23T05:02:48.229Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584179,705489,0,546594,TO_TIMESTAMP('2022-08-23 08:02:48','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-08-23 08:02:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:02:48.230Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705489 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:02:48.232Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-08-23T05:02:48.293Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705489
;

-- 2022-08-23T05:02:48.293Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705489)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Dokument Basis Typ
-- Column: I_Invoice_Candidate.DocBaseType
-- 2022-08-23T05:02:58.114Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584186,705490,0,546594,TO_TIMESTAMP('2022-08-23 08:02:57','YYYY-MM-DD HH24:MI:SS'),100,'',3,'D','','Y','N','N','N','N','N','N','N','Dokument Basis Typ',TO_TIMESTAMP('2022-08-23 08:02:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:02:58.114Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705490 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:02:58.115Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(865) 
;

-- 2022-08-23T05:02:58.118Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705490
;

-- 2022-08-23T05:02:58.118Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705490)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Import-Fehlermeldung
-- Column: I_Invoice_Candidate.I_ErrorMsg
-- 2022-08-23T05:03:11.401Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584188,705491,0,546594,TO_TIMESTAMP('2022-08-23 08:03:11','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden',2000,'D','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','N','N','N','N','N','Import-Fehlermeldung',TO_TIMESTAMP('2022-08-23 08:03:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:03:11.402Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705491 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:03:11.403Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(912) 
;

-- 2022-08-23T05:03:11.405Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705491
;

-- 2022-08-23T05:03:11.406Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705491)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Doc Sub Type
-- Column: I_Invoice_Candidate.DocSubType
-- 2022-08-23T05:03:20.540Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584187,705492,0,546594,TO_TIMESTAMP('2022-08-23 08:03:20','YYYY-MM-DD HH24:MI:SS'),100,'Document Sub Type',3,'D','The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document','Y','N','N','N','N','N','N','N','Doc Sub Type',TO_TIMESTAMP('2022-08-23 08:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:03:20.541Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705492 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:03:20.544Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1018) 
;

-- 2022-08-23T05:03:20.548Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705492
;

-- 2022-08-23T05:03:20.548Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705492)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Import - Invoice candiate
-- Column: I_Invoice_Candidate.I_Invoice_Candidate_ID
-- 2022-08-23T05:03:29.553Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584165,705493,0,546594,TO_TIMESTAMP('2022-08-23 08:03:29','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Import - Invoice candiate',TO_TIMESTAMP('2022-08-23 08:03:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:03:29.555Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705493 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:03:29.556Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581357) 
;

-- 2022-08-23T05:03:29.557Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705493
;

-- 2022-08-23T05:03:29.557Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705493)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Importiert
-- Column: I_Invoice_Candidate.I_IsImported
-- 2022-08-23T05:03:37.414Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584189,705494,0,546594,TO_TIMESTAMP('2022-08-23 08:03:37','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?',1,'D','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','N','N','N','N','N','Importiert',TO_TIMESTAMP('2022-08-23 08:03:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:03:37.414Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705494 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:03:37.416Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(913) 
;

-- 2022-08-23T05:03:37.418Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705494
;

-- 2022-08-23T05:03:37.418Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705494)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Import Line Content
-- Column: I_Invoice_Candidate.I_LineContent
-- 2022-08-23T05:03:45.294Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584194,705495,0,546594,TO_TIMESTAMP('2022-08-23 08:03:45','YYYY-MM-DD HH24:MI:SS'),100,4000,'D','Y','N','N','N','N','N','N','N','Import Line Content',TO_TIMESTAMP('2022-08-23 08:03:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:03:45.295Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705495 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:03:45.298Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577115) 
;

-- 2022-08-23T05:03:45.303Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705495
;

-- 2022-08-23T05:03:45.304Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705495)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Import Line No
-- Column: I_Invoice_Candidate.I_LineNo
-- 2022-08-23T05:03:54.311Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584195,705496,0,546594,TO_TIMESTAMP('2022-08-23 08:03:54','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Import Line No',TO_TIMESTAMP('2022-08-23 08:03:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:03:54.312Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705496 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:03:54.313Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577116) 
;

-- 2022-08-23T05:03:54.314Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705496
;

-- 2022-08-23T05:03:54.314Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705496)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Rechnungsstellung
-- Column: I_Invoice_Candidate.InvoiceRule
-- 2022-08-23T05:04:02.431Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584177,705497,0,546594,TO_TIMESTAMP('2022-08-23 08:04:02','YYYY-MM-DD HH24:MI:SS'),100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.',255,'D','Y','N','N','N','N','N','N','N','Rechnungsstellung',TO_TIMESTAMP('2022-08-23 08:04:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:04:02.432Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705497 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:04:02.433Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(559) 
;

-- 2022-08-23T05:04:02.436Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705497
;

-- 2022-08-23T05:04:02.436Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705497)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Aktiv
-- Column: I_Invoice_Candidate.IsActive
-- 2022-08-23T05:04:13.618Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584162,705498,0,546594,TO_TIMESTAMP('2022-08-23 08:04:13','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-08-23 08:04:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:04:13.619Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705498 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:04:13.620Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-08-23T05:04:13.804Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705498
;

-- 2022-08-23T05:04:13.805Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705498)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Verkaufstransaktion
-- Column: I_Invoice_Candidate.IsSOTrx
-- 2022-08-23T05:04:21.728Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584175,705499,0,546594,TO_TIMESTAMP('2022-08-23 08:04:21','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist eine Verkaufstransaktion',1,'D','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','N','N','N','Verkaufstransaktion',TO_TIMESTAMP('2022-08-23 08:04:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:04:21.728Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705499 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:04:21.729Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106) 
;

-- 2022-08-23T05:04:21.731Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705499
;

-- 2022-08-23T05:04:21.732Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705499)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Product Value
-- Column: I_Invoice_Candidate.M_Product_Value
-- 2022-08-23T05:04:28.785Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584170,705500,0,546594,TO_TIMESTAMP('2022-08-23 08:04:28','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Product Value',TO_TIMESTAMP('2022-08-23 08:04:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:04:28.786Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705500 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:04:28.789Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577363) 
;

-- 2022-08-23T05:04:28.792Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705500
;

-- 2022-08-23T05:04:28.793Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705500)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Produkt
-- Column: I_Invoice_Candidate.M_Product_ID
-- 2022-08-23T05:04:37.682Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584197,705501,0,546594,TO_TIMESTAMP('2022-08-23 08:04:37','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2022-08-23 08:04:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:04:37.683Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705501 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:04:37.683Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-08-23T05:04:37.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705501
;

-- 2022-08-23T05:04:37.701Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705501)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Referenz
-- Column: I_Invoice_Candidate.POReference
-- 2022-08-23T05:04:45.243Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584166,705502,0,546594,TO_TIMESTAMP('2022-08-23 08:04:45','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',255,'D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','N','N','N','N','N','Referenz',TO_TIMESTAMP('2022-08-23 08:04:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:04:45.244Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705502 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:04:45.245Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2022-08-23T05:04:45.255Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705502
;

-- 2022-08-23T05:04:45.256Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705502)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Vorbelegtes Rechnungsdatum
-- Column: I_Invoice_Candidate.PresetDateInvoiced
-- 2022-08-23T05:04:56.368Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584178,705503,0,546594,TO_TIMESTAMP('2022-08-23 08:04:56','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Vorbelegtes Rechnungsdatum',TO_TIMESTAMP('2022-08-23 08:04:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:04:56.369Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705503 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:04:56.370Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577060) 
;

-- 2022-08-23T05:04:56.372Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705503
;

-- 2022-08-23T05:04:56.372Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705503)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Verarbeitet
-- Column: I_Invoice_Candidate.Processed
-- 2022-08-23T05:05:03.776Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584190,705504,0,546594,TO_TIMESTAMP('2022-08-23 08:05:03','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2022-08-23 08:05:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:05:03.777Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705504 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:05:03.778Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2022-08-23T05:05:03.782Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705504
;

-- 2022-08-23T05:05:03.782Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705504)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Process Now
-- Column: I_Invoice_Candidate.Processing
-- 2022-08-23T05:05:10.955Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584193,705505,0,546594,TO_TIMESTAMP('2022-08-23 08:05:10','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Process Now',TO_TIMESTAMP('2022-08-23 08:05:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:05:10.956Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705505 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:05:10.957Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2022-08-23T05:05:10.972Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705505
;

-- 2022-08-23T05:05:10.973Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705505)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Gelieferte Menge
-- Column: I_Invoice_Candidate.QtyDelivered
-- 2022-08-23T05:05:19.716Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584173,705506,0,546594,TO_TIMESTAMP('2022-08-23 08:05:19','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge',10,'D','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','N','N','N','N','N','N','Gelieferte Menge',TO_TIMESTAMP('2022-08-23 08:05:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:05:19.717Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705506 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:05:19.720Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(528) 
;

-- 2022-08-23T05:05:19.723Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705506
;

-- 2022-08-23T05:05:19.724Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705506)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Bestellt/ Beauftragt
-- Column: I_Invoice_Candidate.QtyOrdered
-- 2022-08-23T05:05:28.237Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584172,705507,0,546594,TO_TIMESTAMP('2022-08-23 08:05:28','YYYY-MM-DD HH24:MI:SS'),100,'Bestellt/ Beauftragt',10,'D','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','N','N','N','N','N','Bestellt/ Beauftragt',TO_TIMESTAMP('2022-08-23 08:05:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:05:28.237Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705507 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T05:05:28.241Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(531) 
;

-- 2022-08-23T05:05:28.244Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705507
;

-- 2022-08-23T05:05:28.245Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705507)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Data Import Run
-- Column: I_Invoice_Candidate.C_DataImport_Run_ID
-- 2022-08-23T05:06:21.145Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-23 08:06:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705485
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Import-Fehlermeldung
-- Column: I_Invoice_Candidate.I_ErrorMsg
-- 2022-08-23T05:06:22.836Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-23 08:06:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705491
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Importiert
-- Column: I_Invoice_Candidate.I_IsImported
-- 2022-08-23T05:06:36.798Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-23 08:06:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705494
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Import - Invoice candiate
-- Column: I_Invoice_Candidate.I_Invoice_Candidate_ID
-- 2022-08-23T05:06:43.559Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-23 08:06:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705493
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Import Line Content
-- Column: I_Invoice_Candidate.I_LineContent
-- 2022-08-23T05:06:44.251Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-23 08:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705495
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Import Line No
-- Column: I_Invoice_Candidate.I_LineNo
-- 2022-08-23T05:06:45.806Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-23 08:06:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705496
;

-- 2022-08-23T05:07:27.045Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546594,545222,TO_TIMESTAMP('2022-08-23 08:07:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-23 08:07:26','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-08-23T05:07:27.047Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545222 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-08-23T05:07:48.238Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546347,545222,TO_TIMESTAMP('2022-08-23 08:07:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-23 08:07:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:07:50.161Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546348,545222,TO_TIMESTAMP('2022-08-23 08:07:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-08-23 08:07:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:08:07.375Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546347,549832,TO_TIMESTAMP('2022-08-23 08:08:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-08-23 08:08:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:09:00.960Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546348,549833,TO_TIMESTAMP('2022-08-23 08:09:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','import status',10,TO_TIMESTAMP('2022-08-23 08:09:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Importiert
-- Column: I_Invoice_Candidate.I_IsImported
-- 2022-08-23T05:09:40.940Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705494,0,546594,612161,549833,'F',TO_TIMESTAMP('2022-08-23 08:09:40','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','Y','N','N','N',0,'Importiert',10,0,0,TO_TIMESTAMP('2022-08-23 08:09:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Import - Invoice candiate
-- Column: I_Invoice_Candidate.I_Invoice_Candidate_ID
-- 2022-08-23T05:10:48.057Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705493,0,546594,612162,549833,'F',TO_TIMESTAMP('2022-08-23 08:10:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import - Invoice candiate',20,0,0,TO_TIMESTAMP('2022-08-23 08:10:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Import-Fehlermeldung
-- Column: I_Invoice_Candidate.I_ErrorMsg
-- 2022-08-23T05:11:06.975Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705491,0,546594,612163,549833,'F',TO_TIMESTAMP('2022-08-23 08:11:06','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','Y','N','N','N',0,'Import-Fehlermeldung',30,0,0,TO_TIMESTAMP('2022-08-23 08:11:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Import Line No
-- Column: I_Invoice_Candidate.I_LineNo
-- 2022-08-23T05:11:27.938Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705496,0,546594,612164,549833,'F',TO_TIMESTAMP('2022-08-23 08:11:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import Line No',40,0,0,TO_TIMESTAMP('2022-08-23 08:11:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Import Line Content
-- Column: I_Invoice_Candidate.I_LineContent
-- 2022-08-23T05:11:37.824Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705495,0,546594,612165,549833,'F',TO_TIMESTAMP('2022-08-23 08:11:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import Line Content',50,0,0,TO_TIMESTAMP('2022-08-23 08:11:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Data Import Run
-- Column: I_Invoice_Candidate.C_DataImport_Run_ID
-- 2022-08-23T05:11:52.704Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705485,0,546594,612166,549833,'F',TO_TIMESTAMP('2022-08-23 08:11:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data Import Run',60,0,0,TO_TIMESTAMP('2022-08-23 08:11:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Import - Invoice candiate
-- Column: I_Invoice_Candidate.I_Invoice_Candidate_ID
-- 2022-08-23T05:12:35.561Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612162
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Import - Invoice candiate
-- Column: I_Invoice_Candidate.I_Invoice_Candidate_ID
-- 2022-08-23T05:12:57.437Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705493,0,546594,612167,549832,'F',TO_TIMESTAMP('2022-08-23 08:12:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import - Invoice candiate',10,0,0,TO_TIMESTAMP('2022-08-23 08:12:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Mandant
-- Column: I_Invoice_Candidate.AD_Client_ID
-- 2022-08-23T05:13:25.531Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705480,0,546594,612168,549832,'F',TO_TIMESTAMP('2022-08-23 08:13:25','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2022-08-23 08:13:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Sektion
-- Column: I_Invoice_Candidate.AD_Org_ID
-- 2022-08-23T05:13:34.385Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705481,0,546594,612169,549832,'F',TO_TIMESTAMP('2022-08-23 08:13:34','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',30,0,0,TO_TIMESTAMP('2022-08-23 08:13:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Bill_BPartner_Value
-- Column: I_Invoice_Candidate.Bill_BPartner_Value
-- 2022-08-23T05:13:57.928Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705482,0,546594,612170,549832,'F',TO_TIMESTAMP('2022-08-23 08:13:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Bill_BPartner_Value',40,0,0,TO_TIMESTAMP('2022-08-23 08:13:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Rechnungspartner
-- Column: I_Invoice_Candidate.Bill_BPartner_ID
-- 2022-08-23T05:14:56.001Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705479,0,546594,612171,549832,'F',TO_TIMESTAMP('2022-08-23 08:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner für die Rechnungsstellung','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','Y','N','N','N',0,'Rechnungspartner',50,0,0,TO_TIMESTAMP('2022-08-23 08:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Rechnungsstandort
-- Column: I_Invoice_Candidate.Bill_Location_ID
-- 2022-08-23T05:15:08.726Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705483,0,546594,612172,549832,'F',TO_TIMESTAMP('2022-08-23 08:15:08','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung','Y','N','N','Y','N','N','N',0,'Rechnungsstandort',60,0,0,TO_TIMESTAMP('2022-08-23 08:15:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Rechnungskontakt
-- Column: I_Invoice_Candidate.Bill_User_ID
-- 2022-08-23T05:15:18.749Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705484,0,546594,612173,549832,'F',TO_TIMESTAMP('2022-08-23 08:15:18','YYYY-MM-DD HH24:MI:SS'),100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung','Y','N','N','Y','N','N','N',0,'Rechnungskontakt',70,0,0,TO_TIMESTAMP('2022-08-23 08:15:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Product Value
-- Column: I_Invoice_Candidate.M_Product_Value
-- 2022-08-23T05:15:31.543Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705500,0,546594,612174,549832,'F',TO_TIMESTAMP('2022-08-23 08:15:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Product Value',80,0,0,TO_TIMESTAMP('2022-08-23 08:15:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Produkt
-- Column: I_Invoice_Candidate.M_Product_ID
-- 2022-08-23T05:15:47.120Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705501,0,546594,612175,549832,'F',TO_TIMESTAMP('2022-08-23 08:15:47','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',90,0,0,TO_TIMESTAMP('2022-08-23 08:15:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.C_UOM_X12DE355
-- Column: I_Invoice_Candidate.C_UOM_X12DE355
-- 2022-08-23T05:16:05.868Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705487,0,546594,612176,549832,'F',TO_TIMESTAMP('2022-08-23 08:16:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_UOM_X12DE355',100,0,0,TO_TIMESTAMP('2022-08-23 08:16:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Maßeinheit
-- Column: I_Invoice_Candidate.C_UOM_ID
-- 2022-08-23T05:16:15.010Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705486,0,546594,612177,549832,'F',TO_TIMESTAMP('2022-08-23 08:16:14','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',110,0,0,TO_TIMESTAMP('2022-08-23 08:16:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Beschreibung
-- Column: I_Invoice_Candidate.Description
-- 2022-08-23T05:17:24.436Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705489,0,546594,612178,549832,'F',TO_TIMESTAMP('2022-08-23 08:17:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',120,0,0,TO_TIMESTAMP('2022-08-23 08:17:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Auftragsdatum
-- Column: I_Invoice_Candidate.DateOrdered
-- 2022-08-23T05:17:35.123Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705488,0,546594,612179,549832,'F',TO_TIMESTAMP('2022-08-23 08:17:34','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','N','Y','N','N','N',0,'Auftragsdatum',130,0,0,TO_TIMESTAMP('2022-08-23 08:17:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Bestellt/ Beauftragt
-- Column: I_Invoice_Candidate.QtyOrdered
-- 2022-08-23T05:17:48.549Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705507,0,546594,612180,549832,'F',TO_TIMESTAMP('2022-08-23 08:17:48','YYYY-MM-DD HH24:MI:SS'),100,'Bestellt/ Beauftragt','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','Y','N','N','N',0,'Bestellt/ Beauftragt',140,0,0,TO_TIMESTAMP('2022-08-23 08:17:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Gelieferte Menge
-- Column: I_Invoice_Candidate.QtyDelivered
-- 2022-08-23T05:18:01.363Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705506,0,546594,612181,549832,'F',TO_TIMESTAMP('2022-08-23 08:18:01','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','N','Y','N','N','N',0,'Gelieferte Menge',150,0,0,TO_TIMESTAMP('2022-08-23 08:18:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Rechnungsstellung
-- Column: I_Invoice_Candidate.InvoiceRule
-- 2022-08-23T05:18:29.409Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705497,0,546594,612182,549832,'F',TO_TIMESTAMP('2022-08-23 08:18:29','YYYY-MM-DD HH24:MI:SS'),100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.','Y','N','N','Y','N','N','N',0,'Rechnungsstellung',160,0,0,TO_TIMESTAMP('2022-08-23 08:18:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Vorbelegtes Rechnungsdatum
-- Column: I_Invoice_Candidate.PresetDateInvoiced
-- 2022-08-23T05:18:44.765Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705503,0,546594,612183,549832,'F',TO_TIMESTAMP('2022-08-23 08:18:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vorbelegtes Rechnungsdatum',170,0,0,TO_TIMESTAMP('2022-08-23 08:18:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Dokument Basis Typ
-- Column: I_Invoice_Candidate.DocBaseType
-- 2022-08-23T05:19:10.319Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705490,0,546594,612184,549832,'F',TO_TIMESTAMP('2022-08-23 08:19:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Dokument Basis Typ',180,0,0,TO_TIMESTAMP('2022-08-23 08:19:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Doc Sub Type
-- Column: I_Invoice_Candidate.DocSubType
-- 2022-08-23T05:19:25.010Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705492,0,546594,612185,549832,'F',TO_TIMESTAMP('2022-08-23 08:19:24','YYYY-MM-DD HH24:MI:SS'),100,'Document Sub Type','The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document','Y','N','N','Y','N','N','N',0,'Doc Sub Type',190,0,0,TO_TIMESTAMP('2022-08-23 08:19:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T05:19:42.785Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546348,549834,TO_TIMESTAMP('2022-08-23 08:19:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',20,TO_TIMESTAMP('2022-08-23 08:19:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Aktiv
-- Column: I_Invoice_Candidate.IsActive
-- 2022-08-23T05:19:57.942Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705498,0,546594,612186,549834,'F',TO_TIMESTAMP('2022-08-23 08:19:57','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-08-23 08:19:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Verkaufstransaktion
-- Column: I_Invoice_Candidate.IsSOTrx
-- 2022-08-23T05:20:11.154Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705499,0,546594,612187,549834,'F',TO_TIMESTAMP('2022-08-23 08:20:11','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist eine Verkaufstransaktion','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','Y','N','N','N',0,'Verkaufstransaktion',20,0,0,TO_TIMESTAMP('2022-08-23 08:20:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Referenz
-- Column: I_Invoice_Candidate.POReference
-- 2022-08-23T05:20:26.345Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705502,0,546594,612188,549834,'F',TO_TIMESTAMP('2022-08-23 08:20:26','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Referenz',30,0,0,TO_TIMESTAMP('2022-08-23 08:20:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Import - Invoice candiate
-- Column: I_Invoice_Candidate.I_Invoice_Candidate_ID
-- 2022-08-23T05:22:23.140Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612167
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Importiert
-- Column: I_Invoice_Candidate.I_IsImported
-- 2022-08-23T05:22:23.144Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612161
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Import-Fehlermeldung
-- Column: I_Invoice_Candidate.I_ErrorMsg
-- 2022-08-23T05:22:23.147Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612163
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Mandant
-- Column: I_Invoice_Candidate.AD_Client_ID
-- 2022-08-23T05:22:23.149Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612168
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Sektion
-- Column: I_Invoice_Candidate.AD_Org_ID
-- 2022-08-23T05:22:23.152Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612169
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Bill_BPartner_Value
-- Column: I_Invoice_Candidate.Bill_BPartner_Value
-- 2022-08-23T05:22:23.154Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612170
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Rechnungspartner
-- Column: I_Invoice_Candidate.Bill_BPartner_ID
-- 2022-08-23T05:22:23.156Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612171
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Product Value
-- Column: I_Invoice_Candidate.M_Product_Value
-- 2022-08-23T05:22:23.159Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612174
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Produkt
-- Column: I_Invoice_Candidate.M_Product_ID
-- 2022-08-23T05:22:23.161Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612175
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Rechnungsstandort
-- Column: I_Invoice_Candidate.Bill_Location_ID
-- 2022-08-23T05:22:23.163Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612172
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Rechnungskontakt
-- Column: I_Invoice_Candidate.Bill_User_ID
-- 2022-08-23T05:22:23.164Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612173
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Dokument Basis Typ
-- Column: I_Invoice_Candidate.DocBaseType
-- 2022-08-23T05:22:23.164Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612184
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Doc Sub Type
-- Column: I_Invoice_Candidate.DocSubType
-- 2022-08-23T05:22:23.165Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612185
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Auftragsdatum
-- Column: I_Invoice_Candidate.DateOrdered
-- 2022-08-23T05:22:23.166Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612179
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Bestellt/ Beauftragt
-- Column: I_Invoice_Candidate.QtyOrdered
-- 2022-08-23T05:22:23.168Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612180
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Gelieferte Menge
-- Column: I_Invoice_Candidate.QtyDelivered
-- 2022-08-23T05:22:23.170Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612181
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Rechnungsstellung
-- Column: I_Invoice_Candidate.InvoiceRule
-- 2022-08-23T05:22:23.171Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612182
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.C_UOM_X12DE355
-- Column: I_Invoice_Candidate.C_UOM_X12DE355
-- 2022-08-23T05:22:23.172Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612176
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Maßeinheit
-- Column: I_Invoice_Candidate.C_UOM_ID
-- 2022-08-23T05:22:23.173Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2022-08-23 08:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612177
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Daten Import
-- Column: I_Invoice_Candidate.C_DataImport_ID
-- 2022-08-23T06:51:10.096Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584201,705508,0,546594,TO_TIMESTAMP('2022-08-23 09:51:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Daten Import',TO_TIMESTAMP('2022-08-23 09:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T06:51:10.098Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705508 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T06:51:10.104Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913)
;

-- 2022-08-23T06:51:10.113Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705508
;

-- 2022-08-23T06:51:10.117Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705508)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Externe Datensatz-Kopf-ID
-- Column: I_Invoice_Candidate.ExternalHeaderId
-- 2022-08-23T06:51:21.634Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584202,705509,0,546594,TO_TIMESTAMP('2022-08-23 09:51:21','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Externe Datensatz-Kopf-ID',TO_TIMESTAMP('2022-08-23 09:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T06:51:21.636Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705509 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T06:51:21.639Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575915)
;

-- 2022-08-23T06:51:21.641Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705509
;

-- 2022-08-23T06:51:21.645Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705509)
;

-- Field: Import - Invoice Candidate -> Import - Invoice candiate -> Externe Datensatz-Zeilen-ID
-- Column: I_Invoice_Candidate.ExternalLineId
-- 2022-08-23T06:51:32.094Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584203,705510,0,546594,TO_TIMESTAMP('2022-08-23 09:51:31','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Externe Datensatz-Zeilen-ID',TO_TIMESTAMP('2022-08-23 09:51:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T06:51:32.095Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705510 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T06:51:32.096Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575914)
;

-- 2022-08-23T06:51:32.098Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705510
;

-- 2022-08-23T06:51:32.099Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705510)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Externe Datensatz-Kopf-ID
-- Column: I_Invoice_Candidate.ExternalHeaderId
-- 2022-08-23T06:52:06.118Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705509,0,546594,612189,549832,'F',TO_TIMESTAMP('2022-08-23 09:52:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externe Datensatz-Kopf-ID',200,0,0,TO_TIMESTAMP('2022-08-23 09:52:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Externe Datensatz-Zeilen-ID
-- Column: I_Invoice_Candidate.ExternalLineId
-- 2022-08-23T06:52:16.390Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705510,0,546594,612190,549832,'F',TO_TIMESTAMP('2022-08-23 09:52:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externe Datensatz-Zeilen-ID',210,0,0,TO_TIMESTAMP('2022-08-23 09:52:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Daten Import
-- Column: I_Invoice_Candidate.C_DataImport_ID
-- 2022-08-23T06:52:41.150Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705508,0,546594,612191,549833,'F',TO_TIMESTAMP('2022-08-23 09:52:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Daten Import',70,0,0,TO_TIMESTAMP('2022-08-23 09:52:41','YYYY-MM-DD HH24:MI:SS'),100)
;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Daten Import
-- Column: I_Invoice_Candidate.C_DataImport_ID
-- 2022-08-23T06:52:55.928Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2022-08-23 09:52:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612191
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Data Import Run
-- Column: I_Invoice_Candidate.C_DataImport_Run_ID
-- 2022-08-23T06:53:01.304Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2022-08-23 09:53:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612166
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Daten Import
-- Column: I_Invoice_Candidate.C_DataImport_ID
-- 2022-08-23T06:53:28.063Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2022-08-23 09:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612191
;

-- UI Element: Import - Invoice Candidate -> Import - Invoice candiate.Data Import Run
-- Column: I_Invoice_Candidate.C_DataImport_Run_ID
-- 2022-08-23T06:53:28.066Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2022-08-23 09:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612166
;

