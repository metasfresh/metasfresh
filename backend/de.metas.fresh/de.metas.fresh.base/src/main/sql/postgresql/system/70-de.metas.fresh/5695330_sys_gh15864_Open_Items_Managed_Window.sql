-- Element: Open_Items_Managed_V_ID
-- 2023-07-11T10:40:14.970Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Offene Posten (verwaltet)', PrintName='Offene Posten (verwaltet)',Updated=TO_TIMESTAMP('2023-07-11 11:40:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582544 AND AD_Language='de_DE'
;

-- 2023-07-11T10:40:14.973Z
UPDATE AD_Element SET Name='Offene Posten (verwaltet)', PrintName='Offene Posten (verwaltet)', Updated=TO_TIMESTAMP('2023-07-11 11:40:14','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=582544
;

-- 2023-07-11T10:40:15.522Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582544,'de_DE') 
;

-- 2023-07-11T10:40:15.526Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582544,'de_DE') 
;

-- Window: Offene Posten (verwaltet), InternalName=null
-- 2023-07-11T10:40:55.781Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582544,0,541716,TO_TIMESTAMP('2023-07-11 11:40:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Offene Posten (verwaltet)','N',TO_TIMESTAMP('2023-07-11 11:40:55','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-07-11T10:40:55.784Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541716 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-07-11T10:40:55.790Z
/* DDL */  select update_window_translation_from_ad_element(582544) 
;

-- 2023-07-11T10:40:55.799Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541716
;

-- 2023-07-11T10:40:55.802Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541716)
;

-- Tab: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)
-- Table: Open_Items_Managed_V
-- 2023-07-11T10:41:43.339Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582544,0,547024,542349,541716,'Y',TO_TIMESTAMP('2023-07-11 11:41:43','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','Open_Items_Managed_V','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Offene Posten (verwaltet)','N',10,0,TO_TIMESTAMP('2023-07-11 11:41:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:43.343Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547024 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-07-11T10:41:43.347Z
/* DDL */  select update_tab_translation_from_ad_element(582544) 
;

-- 2023-07-11T10:41:43.353Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547024)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Offene Posten (verwaltet)
-- Column: Open_Items_Managed_V.Open_Items_Managed_V_ID
-- 2023-07-11T10:41:54.622Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587061,716636,0,547024,TO_TIMESTAMP('2023-07-11 11:41:54','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Offene Posten (verwaltet)',TO_TIMESTAMP('2023-07-11 11:41:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:54.624Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716636 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:54.626Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582544) 
;

-- 2023-07-11T10:41:54.630Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716636
;

-- 2023-07-11T10:41:54.631Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716636)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Mandant
-- Column: Open_Items_Managed_V.AD_Client_ID
-- 2023-07-11T10:41:54.817Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587062,716637,0,547024,TO_TIMESTAMP('2023-07-11 11:41:54','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-07-11 11:41:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:54.819Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716637 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:54.821Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-07-11T10:41:54.930Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716637
;

-- 2023-07-11T10:41:54.931Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716637)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Organisation
-- Column: Open_Items_Managed_V.AD_Org_ID
-- 2023-07-11T10:41:55.114Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587063,716638,0,547024,TO_TIMESTAMP('2023-07-11 11:41:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-07-11 11:41:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:55.117Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716638 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:55.119Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-07-11T10:41:55.215Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716638
;

-- 2023-07-11T10:41:55.217Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716638)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Aktiv
-- Column: Open_Items_Managed_V.IsActive
-- 2023-07-11T10:41:55.394Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587068,716639,0,547024,TO_TIMESTAMP('2023-07-11 11:41:55','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-07-11 11:41:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:55.396Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716639 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:55.397Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-07-11T10:41:55.461Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716639
;

-- 2023-07-11T10:41:55.463Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716639)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Buchungsdatum
-- Column: Open_Items_Managed_V.DateAcct
-- 2023-07-11T10:41:56.059Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587071,716642,0,547024,TO_TIMESTAMP('2023-07-11 11:41:55','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Date',29,'D','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','N','N','N','N','N','N','Buchungsdatum',TO_TIMESTAMP('2023-07-11 11:41:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:56.061Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716642 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:56.063Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(263) 
;

-- 2023-07-11T10:41:56.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716642
;

-- 2023-07-11T10:41:56.068Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716642)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Department
-- Column: Open_Items_Managed_V.M_Department_ID
-- 2023-07-11T10:41:56.248Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587072,716643,0,547024,TO_TIMESTAMP('2023-07-11 11:41:56','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-07-11 11:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:56.252Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716643 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:56.254Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581944) 
;

-- 2023-07-11T10:41:56.259Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716643
;

-- 2023-07-11T10:41:56.261Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716643)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Sektionskennung
-- Column: Open_Items_Managed_V.M_SectionCode_ID
-- 2023-07-11T10:41:56.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587073,716644,0,547024,TO_TIMESTAMP('2023-07-11 11:41:56','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Sektionskennung',TO_TIMESTAMP('2023-07-11 11:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:56.421Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716644 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:56.423Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-07-11T10:41:56.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716644
;

-- 2023-07-11T10:41:56.431Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716644)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> UserElementString1
-- Column: Open_Items_Managed_V.UserElementString1
-- 2023-07-11T10:41:56.619Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587074,716645,0,547024,TO_TIMESTAMP('2023-07-11 11:41:56','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','N','N','N','N','N','N','N','UserElementString1',TO_TIMESTAMP('2023-07-11 11:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:56.621Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716645 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:56.623Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-07-11T10:41:56.626Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716645
;

-- 2023-07-11T10:41:56.627Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716645)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Nr.
-- Column: Open_Items_Managed_V.DocumentNo
-- 2023-07-11T10:41:56.810Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587075,716646,0,547024,TO_TIMESTAMP('2023-07-11 11:41:56','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',40,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2023-07-11 11:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:56.813Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716646 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:56.816Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2023-07-11T10:41:56.824Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716646
;

-- 2023-07-11T10:41:56.827Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716646)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Document date
-- Column: Open_Items_Managed_V.DocumentDate
-- 2023-07-11T10:41:57.015Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587076,716647,0,547024,TO_TIMESTAMP('2023-07-11 11:41:56','YYYY-MM-DD HH24:MI:SS'),100,29,'D','Y','N','N','N','N','N','N','N','Document date',TO_TIMESTAMP('2023-07-11 11:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:57.018Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716647 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:57.021Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578710) 
;

-- 2023-07-11T10:41:57.024Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716647
;

-- 2023-07-11T10:41:57.025Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716647)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Referenz
-- Column: Open_Items_Managed_V.POReference
-- 2023-07-11T10:41:57.207Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587077,716648,0,547024,TO_TIMESTAMP('2023-07-11 11:41:57','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',2147483647,'D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','N','N','N','N','N','Referenz',TO_TIMESTAMP('2023-07-11 11:41:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:57.210Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716648 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:57.213Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2023-07-11T10:41:57.221Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716648
;

-- 2023-07-11T10:41:57.223Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716648)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Beschreibung
-- Column: Open_Items_Managed_V.Description
-- 2023-07-11T10:41:57.409Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587078,716649,0,547024,TO_TIMESTAMP('2023-07-11 11:41:57','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2023-07-11 11:41:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:57.411Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716649 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:57.413Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-07-11T10:41:57.450Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716649
;

-- 2023-07-11T10:41:57.453Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716649)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Amount (FC)
-- Column: Open_Items_Managed_V.Amount_FC
-- 2023-07-11T10:41:57.634Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587079,716650,0,547024,TO_TIMESTAMP('2023-07-11 11:41:57','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Amount (FC)',TO_TIMESTAMP('2023-07-11 11:41:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:57.637Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716650 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:57.639Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582508) 
;

-- 2023-07-11T10:41:57.643Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716650
;

-- 2023-07-11T10:41:57.645Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716650)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Amount (LC)
-- Column: Open_Items_Managed_V.Amount_LC
-- 2023-07-11T10:41:57.831Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587080,716651,0,547024,TO_TIMESTAMP('2023-07-11 11:41:57','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Amount (LC)',TO_TIMESTAMP('2023-07-11 11:41:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:57.833Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716651 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:57.837Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582509) 
;

-- 2023-07-11T10:41:57.841Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716651
;

-- 2023-07-11T10:41:57.842Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716651)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Offener Posten Verwaltet
-- Column: Open_Items_Managed_V.IsOpenItem
-- 2023-07-11T10:41:58.016Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587081,716652,0,547024,TO_TIMESTAMP('2023-07-11 11:41:57','YYYY-MM-DD HH24:MI:SS'),100,'Dieses Kennzeichen zeigt an, dass es sich bei dem ausgewählten Konto um ein Konto mit Offener-Posten-Verwaltung handelt.',1,'D','Y','N','N','N','N','N','N','N','Offener Posten Verwaltet',TO_TIMESTAMP('2023-07-11 11:41:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T10:41:58.020Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716652 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T10:41:58.022Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582534) 
;

-- 2023-07-11T10:41:58.026Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716652
;

-- 2023-07-11T10:41:58.028Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716652)
;

-- Tab: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D)
-- UI Section: main
-- 2023-07-11T10:44:33.922Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547024,545633,TO_TIMESTAMP('2023-07-11 11:44:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-07-11 11:44:33','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-07-11T10:44:33.924Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545633 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main
-- UI Column: 10
-- 2023-07-11T10:45:05.164Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546881,545633,TO_TIMESTAMP('2023-07-11 11:45:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-07-11 11:45:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10
-- UI Element Group: default
-- 2023-07-11T10:45:31.986Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546881,550819,TO_TIMESTAMP('2023-07-11 11:45:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-07-11 11:45:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Beschreibung
-- Column: Open_Items_Managed_V.Description
-- 2023-07-11T10:48:07.484Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716649,0,547024,550819,618224,'F',TO_TIMESTAMP('2023-07-11 11:48:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',30,0,0,TO_TIMESTAMP('2023-07-11 11:48:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10
-- UI Element Group: Document
-- 2023-07-11T10:48:34.849Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546881,550820,TO_TIMESTAMP('2023-07-11 11:48:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','Document',20,TO_TIMESTAMP('2023-07-11 11:48:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Document.Nr.
-- Column: Open_Items_Managed_V.DocumentNo
-- 2023-07-11T10:48:51.213Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716646,0,547024,550820,618225,'F',TO_TIMESTAMP('2023-07-11 11:48:50','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Nr.',10,0,0,TO_TIMESTAMP('2023-07-11 11:48:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Document.Document date
-- Column: Open_Items_Managed_V.DocumentDate
-- 2023-07-11T10:49:04.002Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716647,0,547024,550820,618226,'F',TO_TIMESTAMP('2023-07-11 11:49:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Document date',20,0,0,TO_TIMESTAMP('2023-07-11 11:49:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Document.Referenz
-- Column: Open_Items_Managed_V.POReference
-- 2023-07-11T10:49:43.365Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716648,0,547024,550820,618227,'F',TO_TIMESTAMP('2023-07-11 11:49:43','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Referenz',30,0,0,TO_TIMESTAMP('2023-07-11 11:49:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Document.Document date
-- Column: Open_Items_Managed_V.DocumentDate
-- 2023-07-11T10:50:27.362Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=618226
;

-------------------------------
--------------------------------
-- UI Column: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10
-- UI Element Group: Amount
-- 2023-07-11T16:14:28.165Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546881,550821,TO_TIMESTAMP('2023-07-11 17:14:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','Amount',30,TO_TIMESTAMP('2023-07-11 17:14:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Document currency
-- Column: Open_Items_Managed_V.Source_Currency_ID
-- 2023-07-11T16:15:43.462Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587082,716653,0,547024,TO_TIMESTAMP('2023-07-11 17:15:43','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Document currency',TO_TIMESTAMP('2023-07-11 17:15:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T16:15:43.464Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716653 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T16:15:43.466Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578756) 
;

-- 2023-07-11T16:15:43.471Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716653
;

-- 2023-07-11T16:15:43.473Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716653)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Accounting Currency
-- Column: Open_Items_Managed_V.Acct_Currency_ID
-- 2023-07-11T16:21:02.448Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587083,716654,0,547024,0,TO_TIMESTAMP('2023-07-11 17:21:02','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Accounting Currency',0,10,0,1,1,TO_TIMESTAMP('2023-07-11 17:21:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T16:21:02.450Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716654 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T16:21:02.467Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581877)
;

-- 2023-07-11T16:21:02.477Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716654
;

-- 2023-07-11T16:21:02.479Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716654)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Document currency
-- Column: Open_Items_Managed_V.Source_Currency_ID
-- 2023-07-11T16:22:10.850Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716653,0,547024,550821,618228,'F',TO_TIMESTAMP('2023-07-11 17:22:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Document currency',10,0,0,TO_TIMESTAMP('2023-07-11 17:22:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Amount (FC)
-- Column: Open_Items_Managed_V.Amount_FC
-- 2023-07-11T16:22:35.994Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716650,0,547024,550821,618229,'F',TO_TIMESTAMP('2023-07-11 17:22:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Amount (FC)',20,0,0,TO_TIMESTAMP('2023-07-11 17:22:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Accounting Currency
-- Column: Open_Items_Managed_V.Acct_Currency_ID
-- 2023-07-11T16:23:03.231Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716654,0,547024,550821,618230,'F',TO_TIMESTAMP('2023-07-11 17:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Accounting Currency',30,0,0,TO_TIMESTAMP('2023-07-11 17:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Amount (LC)
-- Column: Open_Items_Managed_V.Amount_LC
-- 2023-07-11T16:23:18.579Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716651,0,547024,550821,618231,'F',TO_TIMESTAMP('2023-07-11 17:23:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Amount (LC)',40,0,0,TO_TIMESTAMP('2023-07-11 17:23:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main
-- UI Column: 20
-- 2023-07-11T16:23:50.950Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546882,545633,TO_TIMESTAMP('2023-07-11 17:23:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-07-11 17:23:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20
-- UI Element Group: flags
-- 2023-07-11T16:24:00.260Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546882,550822,TO_TIMESTAMP('2023-07-11 17:23:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-07-11 17:23:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> flags.Aktiv
-- Column: Open_Items_Managed_V.IsActive
-- 2023-07-11T16:24:31.033Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716639,0,547024,550822,618232,'F',TO_TIMESTAMP('2023-07-11 17:24:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-07-11 17:24:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> flags.Offener Posten Verwaltet
-- Column: Open_Items_Managed_V.IsOpenItem
-- 2023-07-11T16:24:50.458Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716652,0,547024,550822,618233,'F',TO_TIMESTAMP('2023-07-11 17:24:50','YYYY-MM-DD HH24:MI:SS'),100,'Dieses Kennzeichen zeigt an, dass es sich bei dem ausgewählten Konto um ein Konto mit Offener-Posten-Verwaltung handelt.','Y','N','N','Y','N','N','N',0,'Offener Posten Verwaltet',20,0,0,TO_TIMESTAMP('2023-07-11 17:24:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20
-- UI Element Group: dates
-- 2023-07-11T16:25:10.432Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546882,550823,TO_TIMESTAMP('2023-07-11 17:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2023-07-11 17:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> dates.Buchungsdatum
-- Column: Open_Items_Managed_V.DateAcct
-- 2023-07-11T16:26:00.563Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716642,0,547024,550823,618234,'F',TO_TIMESTAMP('2023-07-11 17:26:00','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','N','Y','N','N','N',0,'Buchungsdatum',10,0,0,TO_TIMESTAMP('2023-07-11 17:26:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> dates.Document date
-- Column: Open_Items_Managed_V.DocumentDate
-- 2023-07-11T16:26:13.426Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716647,0,547024,550823,618235,'F',TO_TIMESTAMP('2023-07-11 17:26:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Document date',20,0,0,TO_TIMESTAMP('2023-07-11 17:26:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20
-- UI Element Group: org
-- 2023-07-11T16:26:33.137Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546882,550824,TO_TIMESTAMP('2023-07-11 17:26:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2023-07-11 17:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.UserElementString1
-- Column: Open_Items_Managed_V.UserElementString1
-- 2023-07-11T16:26:53.576Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716645,0,547024,550824,618236,'F',TO_TIMESTAMP('2023-07-11 17:26:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString1',10,0,0,TO_TIMESTAMP('2023-07-11 17:26:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Sektionskennung
-- Column: Open_Items_Managed_V.M_SectionCode_ID
-- 2023-07-11T16:27:18.031Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716644,0,547024,550824,618237,'F',TO_TIMESTAMP('2023-07-11 17:27:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektionskennung',20,0,0,TO_TIMESTAMP('2023-07-11 17:27:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Department
-- Column: Open_Items_Managed_V.M_Department_ID
-- 2023-07-11T16:27:30.418Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716643,0,547024,550824,618238,'F',TO_TIMESTAMP('2023-07-11 17:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Department',30,0,0,TO_TIMESTAMP('2023-07-11 17:27:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Organisation
-- Column: Open_Items_Managed_V.AD_Org_ID
-- 2023-07-11T16:27:41.255Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716638,0,547024,550824,618239,'F',TO_TIMESTAMP('2023-07-11 17:27:41','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',40,0,0,TO_TIMESTAMP('2023-07-11 17:27:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Mandant
-- Column: Open_Items_Managed_V.AD_Client_ID
-- 2023-07-11T16:27:55.797Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716637,0,547024,550824,618240,'F',TO_TIMESTAMP('2023-07-11 17:27:55','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',50,0,0,TO_TIMESTAMP('2023-07-11 17:27:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Geschäftspartner-Schlüssel
-- Column: Open_Items_Managed_V.BPartnerValue
-- 2023-07-11T16:30:26.327Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618222
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Name Geschäftspartner
-- Column: Open_Items_Managed_V.BPartnerName
-- 2023-07-11T16:30:26.334Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618223
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> flags.Offener Posten Verwaltet
-- Column: Open_Items_Managed_V.IsOpenItem
-- 2023-07-11T16:30:26.339Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618233
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Document.Nr.
-- Column: Open_Items_Managed_V.DocumentNo
-- 2023-07-11T16:30:26.345Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618225
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Document.Referenz
-- Column: Open_Items_Managed_V.POReference
-- 2023-07-11T16:30:26.349Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618227
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> dates.Document date
-- Column: Open_Items_Managed_V.DocumentDate
-- 2023-07-11T16:30:26.354Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618235
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> dates.Buchungsdatum
-- Column: Open_Items_Managed_V.DateAcct
-- 2023-07-11T16:30:26.359Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618234
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Document currency
-- Column: Open_Items_Managed_V.Source_Currency_ID
-- 2023-07-11T16:30:26.364Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618228
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Amount (FC)
-- Column: Open_Items_Managed_V.Amount_FC
-- 2023-07-11T16:30:26.368Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618229
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Accounting Currency
-- Column: Open_Items_Managed_V.Acct_Currency_ID
-- 2023-07-11T16:30:26.374Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618230
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Amount (LC)
-- Column: Open_Items_Managed_V.Amount_LC
-- 2023-07-11T16:30:26.378Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618231
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Beschreibung
-- Column: Open_Items_Managed_V.Description
-- 2023-07-11T16:30:26.383Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618224
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.UserElementString1
-- Column: Open_Items_Managed_V.UserElementString1
-- 2023-07-11T16:30:26.388Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618236
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Sektionskennung
-- Column: Open_Items_Managed_V.M_SectionCode_ID
-- 2023-07-11T16:30:26.393Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618237
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Department
-- Column: Open_Items_Managed_V.M_Department_ID
-- 2023-07-11T16:30:26.398Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618238
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Organisation
-- Column: Open_Items_Managed_V.AD_Org_ID
-- 2023-07-11T16:30:26.403Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618239
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Mandant
-- Column: Open_Items_Managed_V.AD_Client_ID
-- 2023-07-11T16:30:26.408Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-07-11 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618240
;

-- Name: Offene Posten (verwaltet)
-- Action Type: W
-- Window: Offene Posten (verwaltet)(541716,D)
-- 2023-07-11T16:37:39.956Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582544,542090,0,541716,TO_TIMESTAMP('2023-07-11 17:37:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Open Items (managed)','Y','N','N','N','N','Offene Posten (verwaltet)',TO_TIMESTAMP('2023-07-11 17:37:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T16:37:39.958Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542090 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-07-11T16:37:39.961Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542090, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542090)
;

-- 2023-07-11T16:37:39.983Z
/* DDL */  select update_menu_translation_from_ad_element(582544)
;

-- Reordering children of `CRM`
-- Node name: `Import Business Partner Block Status (I_BPartner_BlockStatus)`
-- 2023-07-11T16:37:40.730Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542064 AND AD_Tree_ID=10
;

-- Node name: `Change Business Partner Block (C_BPartner_Block_File)`
-- 2023-07-11T16:37:40.731Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542062 AND AD_Tree_ID=10
;

-- Node name: `Request (R_Request)`
-- 2023-07-11T16:37:40.732Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=237 AND AD_Tree_ID=10
;

-- Node name: `Request (all) (R_Request)`
-- 2023-07-11T16:37:40.734Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=308 AND AD_Tree_ID=10
;

-- Node name: `Company Phone Book (AD_User)`
-- 2023-07-11T16:37:40.734Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- Node name: `Business Partner (C_BPartner)`
-- 2023-07-11T16:37:40.735Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- Node name: `Partner Export (C_BPartner_Export)`
-- 2023-07-11T16:37:40.736Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541728 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents (C_Doc_Outbound_Log)`
-- 2023-07-11T16:37:40.737Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-07-11T16:37:40.738Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-07-11T16:37:40.739Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-07-11T16:37:40.741Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- Node name: `Businesspartner Global ID (I_BPartner_GlobalID)`
-- 2023-07-11T16:37:40.743Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- Node name: `Import User (I_User)`
-- 2023-07-11T16:37:40.744Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- Node name: `Phone call (R_Request)`
-- 2023-07-11T16:37:40.745Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541896 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema (C_Phonecall_Schema)`
-- 2023-07-11T16:37:40.746Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema Version (C_Phonecall_Schema_Version)`
-- 2023-07-11T16:37:40.747Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schedule (C_Phonecall_Schedule)`
-- 2023-07-11T16:37:40.747Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;

-- Node name: `Offene Posten (verwaltet)`
-- 2023-07-11T16:37:40.748Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542090 AND AD_Tree_ID=10
;

-- Reordering children of `Finance`
-- Node name: `Offene Posten (verwaltet)`
-- 2023-07-11T16:37:43.970Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542090 AND AD_Tree_ID=10
;

-- Node name: `Open Items (Excel) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-07-11T16:37:43.971Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542075 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2023-07-11T16:37:43.972Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2023-07-11T16:37:43.973Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2023-07-11T16:37:43.975Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2023-07-11T16:37:43.975Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2023-07-11T16:37:43.976Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2023-07-11T16:37:43.977Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2023-07-11T16:37:43.978Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2023-07-11T16:37:43.979Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2023-07-11T16:37:43.980Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2023-07-11T16:37:43.981Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2023-07-11T16:37:43.982Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2023-07-11T16:37:43.983Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2023-07-11T16:37:43.984Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2023-07-11T16:37:43.984Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2023-07-11T16:37:43.985Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2023-07-11T16:37:43.986Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2023-07-11T16:37:43.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2023-07-11T16:37:43.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2023-07-11T16:37:43.989Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2023-07-11T16:37:43.990Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2023-07-11T16:37:43.991Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2023-07-11T16:37:43.992Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-07-11T16:37:43.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Cost Element (M_CostElement)`
-- 2023-07-11T16:37:43.994Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Cost Type (C_Cost_Type)`
-- 2023-07-11T16:37:43.995Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-07-11T16:37:43.995Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2023-07-11T16:37:43.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2023-07-11T16:37:43.997Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2023-07-11T16:37:43.998Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2023-07-11T16:37:43.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2023-07-11T16:37:44Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2023-07-11T16:37:44.001Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2023-07-11T16:37:44.002Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2023-07-11T16:37:44.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2023-07-11T16:37:44.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2023-07-11T16:37:44.005Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2023-07-11T16:37:44.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-07-11T16:37:44.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2023-07-11T16:37:44.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-07-11T16:37:44.008Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-07-11T16:37:44.009Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2023-07-11T16:37:44.010Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2023-07-11T16:37:44.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Foreign Exchange Contract (C_ForeignExchangeContract)`
-- 2023-07-11T16:37:44.012Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542042 AND AD_Tree_ID=10
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Geschäftspartner-Schlüssel
-- Column: Open_Items_Managed_V.BPartnerValue
-- 2023-07-11T16:54:02.321Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=618222
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Name Geschäftspartner
-- Column: Open_Items_Managed_V.BPartnerName
-- 2023-07-11T16:54:05.546Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=618223
;

-- 2023-07-11T16:54:16.309Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716640
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Geschäftspartner-Schlüssel
-- Column: Open_Items_Managed_V.BPartnerValue
-- 2023-07-11T16:54:16.314Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=716640
;

-- 2023-07-11T16:54:16.320Z
DELETE FROM AD_Field WHERE AD_Field_ID=716640
;

-- 2023-07-11T16:54:20.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716641
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Name Geschäftspartner
-- Column: Open_Items_Managed_V.BPartnerName
-- 2023-07-11T16:54:20.973Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=716641
;

-- 2023-07-11T16:54:20.978Z
DELETE FROM AD_Field WHERE AD_Field_ID=716641
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Geschäftspartner
-- Column: Open_Items_Managed_V.C_BPartner_ID
-- 2023-07-11T16:59:23.689Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587084,716655,0,547024,0,TO_TIMESTAMP('2023-07-11 17:59:23','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',0,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',0,20,0,1,1,TO_TIMESTAMP('2023-07-11 17:59:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T16:59:23.691Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716655 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-11T16:59:23.693Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2023-07-11T16:59:23.731Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716655
;

-- 2023-07-11T16:59:23.734Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716655)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Beschreibung
-- Column: Open_Items_Managed_V.Description
-- 2023-07-11T16:59:53.488Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-07-11 17:59:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618224
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Geschäftspartner
-- Column: Open_Items_Managed_V.C_BPartner_ID
-- 2023-07-11T17:00:03.219Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716655,0,547024,550819,618241,'F',TO_TIMESTAMP('2023-07-11 18:00:02','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',10,0,0,TO_TIMESTAMP('2023-07-11 18:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Geschäftspartner
-- Column: Open_Items_Managed_V.C_BPartner_ID
-- 2023-07-11T17:00:19.498Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618241
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> flags.Offener Posten Verwaltet
-- Column: Open_Items_Managed_V.IsOpenItem
-- 2023-07-11T17:00:19.505Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618233
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Document.Nr.
-- Column: Open_Items_Managed_V.DocumentNo
-- 2023-07-11T17:00:19.512Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618225
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Document.Referenz
-- Column: Open_Items_Managed_V.POReference
-- 2023-07-11T17:00:19.518Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618227
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> dates.Document date
-- Column: Open_Items_Managed_V.DocumentDate
-- 2023-07-11T17:00:19.524Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618235
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> dates.Buchungsdatum
-- Column: Open_Items_Managed_V.DateAcct
-- 2023-07-11T17:00:19.529Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618234
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Document currency
-- Column: Open_Items_Managed_V.Source_Currency_ID
-- 2023-07-11T17:00:19.535Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618228
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Amount (FC)
-- Column: Open_Items_Managed_V.Amount_FC
-- 2023-07-11T17:00:19.539Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618229
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Accounting Currency
-- Column: Open_Items_Managed_V.Acct_Currency_ID
-- 2023-07-11T17:00:19.544Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618230
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Amount (LC)
-- Column: Open_Items_Managed_V.Amount_LC
-- 2023-07-11T17:00:19.551Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618231
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Beschreibung
-- Column: Open_Items_Managed_V.Description
-- 2023-07-11T17:00:19.556Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618224
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.UserElementString1
-- Column: Open_Items_Managed_V.UserElementString1
-- 2023-07-11T17:00:19.560Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618236
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Sektionskennung
-- Column: Open_Items_Managed_V.M_SectionCode_ID
-- 2023-07-11T17:00:19.566Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618237
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Department
-- Column: Open_Items_Managed_V.M_Department_ID
-- 2023-07-11T17:00:19.571Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618238
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Organisation
-- Column: Open_Items_Managed_V.AD_Org_ID
-- 2023-07-11T17:00:19.575Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618239
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Mandant
-- Column: Open_Items_Managed_V.AD_Client_ID
-- 2023-07-11T17:00:19.580Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-07-11 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618240
;

-- UI Column: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10
-- UI Element Group: Description
-- 2023-07-11T18:38:32.338Z
UPDATE AD_UI_ElementGroup SET Name='Description',Updated=TO_TIMESTAMP('2023-07-11 19:38:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550820
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Description.Beschreibung
-- Column: Open_Items_Managed_V.Description
-- 2023-07-11T18:39:12.987Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550820, SeqNo=40,Updated=TO_TIMESTAMP('2023-07-11 19:39:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618224
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Nr.
-- Column: Open_Items_Managed_V.DocumentNo
-- 2023-07-11T18:39:30.872Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550819, SeqNo=20,Updated=TO_TIMESTAMP('2023-07-11 19:39:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618225
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Referenz
-- Column: Open_Items_Managed_V.POReference
-- 2023-07-11T18:39:39.952Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550819, SeqNo=30,Updated=TO_TIMESTAMP('2023-07-11 19:39:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618227
;

-- 2023-07-11T18:45:45.889Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582545,0,TO_TIMESTAMP('2023-07-11 19:45:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Document Currency Amount','Document Currency Amount',TO_TIMESTAMP('2023-07-11 19:45:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T18:45:45.891Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582545 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-07-11T18:46:07.370Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582546,0,TO_TIMESTAMP('2023-07-11 19:46:07','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Local Currency Amount','Local Currency Amount',TO_TIMESTAMP('2023-07-11 19:46:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-11T18:46:07.372Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582546 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-07-11T18:46:41.822Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dokument Währung Betrag', PrintName='Dokument Währung Betrag',Updated=TO_TIMESTAMP('2023-07-11 19:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582545 AND AD_Language='de_DE'
;

-- 2023-07-11T18:46:41.823Z
UPDATE AD_Element SET Name='Dokument Währung Betrag', PrintName='Dokument Währung Betrag', Updated=TO_TIMESTAMP('2023-07-11 19:46:41','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=582545
;

-- 2023-07-11T18:46:42.157Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582545,'de_DE')
;

-- 2023-07-11T18:46:42.162Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582545,'de_DE')
;

-- Element: null
-- 2023-07-11T18:47:11.058Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Landeswährung Betrag', PrintName='Landeswährung Betrag',Updated=TO_TIMESTAMP('2023-07-11 19:47:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582546 AND AD_Language='de_DE'
;

-- 2023-07-11T18:47:11.059Z
UPDATE AD_Element SET Name='Landeswährung Betrag', PrintName='Landeswährung Betrag', Updated=TO_TIMESTAMP('2023-07-11 19:47:11','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=582546
;

-- 2023-07-11T18:47:11.393Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582546,'de_DE')
;

-- 2023-07-11T18:47:11.394Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582546,'de_DE')
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Dokument Währung Betrag
-- Column: Open_Items_Managed_V.Amount_FC
-- 2023-07-11T18:48:02.770Z
UPDATE AD_Field SET AD_Name_ID=582545, Description=NULL, Help=NULL, Name='Dokument Währung Betrag',Updated=TO_TIMESTAMP('2023-07-11 19:48:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716650
;

-- 2023-07-11T18:48:02.772Z
UPDATE AD_Field_Trl trl SET Name='Dokument Währung Betrag' WHERE AD_Field_ID=716650 AND AD_Language='de_DE'
;

-- 2023-07-11T18:48:02.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582545)
;

-- 2023-07-11T18:48:02.782Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716650
;

-- 2023-07-11T18:48:02.784Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716650)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Landeswährung Betrag
-- Column: Open_Items_Managed_V.Amount_LC
-- 2023-07-11T18:48:22.590Z
UPDATE AD_Field SET AD_Name_ID=582546, Description=NULL, Help=NULL, Name='Landeswährung Betrag',Updated=TO_TIMESTAMP('2023-07-11 19:48:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716651
;

-- 2023-07-11T18:48:22.591Z
UPDATE AD_Field_Trl trl SET Name='Landeswährung Betrag' WHERE AD_Field_ID=716651 AND AD_Language='de_DE'
;

-- 2023-07-11T18:48:22.593Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582546)
;

-- 2023-07-11T18:48:22.596Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716651
;

-- 2023-07-11T18:48:22.596Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716651)
;

-- Element: LocalCurrencyCode
-- 2023-07-11T18:50:47.825Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Landeswährung', PrintName='Landeswährung',Updated=TO_TIMESTAMP('2023-07-11 19:50:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582493 AND AD_Language='de_DE'
;

-- 2023-07-11T18:50:47.826Z
UPDATE AD_Element SET Name='Landeswährung', PrintName='Landeswährung', Updated=TO_TIMESTAMP('2023-07-11 19:50:47','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=582493
;

-- 2023-07-11T18:50:48.150Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582493,'de_DE')
;

-- 2023-07-11T18:50:48.152Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582493,'de_DE')
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Landeswährung
-- Column: Open_Items_Managed_V.Acct_Currency_ID
-- 2023-07-11T18:51:33.221Z
UPDATE AD_Field SET AD_Name_ID=582493, Description=NULL, Help=NULL, Name='Landeswährung',Updated=TO_TIMESTAMP('2023-07-11 19:51:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716654
;

-- 2023-07-11T18:51:33.222Z
UPDATE AD_Field_Trl trl SET Name='Landeswährung' WHERE AD_Field_ID=716654 AND AD_Language='de_DE'
;

-- 2023-07-11T18:51:33.224Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582493)
;

-- 2023-07-11T18:51:33.227Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716654
;

-- 2023-07-11T18:51:33.228Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716654)
;
