-- Run mode: SWING_CLIENT

-- Window: Materialart, InternalName=null
-- 2025-09-18T17:21:20.734Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583978,0,541945,TO_TIMESTAMP('2025-09-18 17:21:20.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Materialart','N',TO_TIMESTAMP('2025-09-18 17:21:20.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-09-18T17:21:20.784Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541945 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-09-18T17:21:20.883Z
/* DDL */  select update_window_translation_from_ad_element(583978)
;

-- 2025-09-18T17:21:20.954Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541945
;

-- 2025-09-18T17:21:21.002Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541945)
;

-- Tab: Materialart(541945,D) -> Materialart
-- Table: M_PackageLicensing_MaterialGroup
-- 2025-09-18T17:22:11.524Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583978,0,548423,542527,541945,'Y',TO_TIMESTAMP('2025-09-18 17:22:10.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','M_PackageLicensing_MaterialGroup','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Materialart','N',10,0,TO_TIMESTAMP('2025-09-18 17:22:10.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:22:11.574Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548423 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-09-18T17:22:11.623Z
/* DDL */  select update_tab_translation_from_ad_element(583978)
;

-- 2025-09-18T17:22:11.673Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548423)
;

-- Field: Materialart(541945,D) -> Materialart(548423,D) -> Mandant
-- Column: M_PackageLicensing_MaterialGroup.AD_Client_ID
-- 2025-09-18T17:22:27.261Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590942,753832,0,548423,TO_TIMESTAMP('2025-09-18 17:22:26.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-09-18 17:22:26.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:22:27.309Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T17:22:27.357Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-09-18T17:22:27.464Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753832
;

-- 2025-09-18T17:22:27.511Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753832)
;

-- Field: Materialart(541945,D) -> Materialart(548423,D) -> Sektion
-- Column: M_PackageLicensing_MaterialGroup.AD_Org_ID
-- 2025-09-18T17:22:28.330Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590943,753833,0,548423,TO_TIMESTAMP('2025-09-18 17:22:27.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-09-18 17:22:27.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:22:28.378Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T17:22:28.428Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-09-18T17:22:28.530Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753833
;

-- 2025-09-18T17:22:28.578Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753833)
;

-- Field: Materialart(541945,D) -> Materialart(548423,D) -> Aktiv
-- Column: M_PackageLicensing_MaterialGroup.IsActive
-- 2025-09-18T17:22:29.382Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590946,753834,0,548423,TO_TIMESTAMP('2025-09-18 17:22:28.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-09-18 17:22:28.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:22:29.432Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T17:22:29.480Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-09-18T17:22:29.584Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753834
;

-- 2025-09-18T17:22:29.633Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753834)
;

-- Field: Materialart(541945,D) -> Materialart(548423,D) -> Materialart
-- Column: M_PackageLicensing_MaterialGroup.M_PackageLicensing_MaterialGroup_ID
-- 2025-09-18T17:22:30.437Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590949,753835,0,548423,TO_TIMESTAMP('2025-09-18 17:22:29.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Materialart',TO_TIMESTAMP('2025-09-18 17:22:29.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:22:30.485Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T17:22:30.532Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583978)
;

-- 2025-09-18T17:22:30.581Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753835
;

-- 2025-09-18T17:22:30.630Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753835)
;

-- Field: Materialart(541945,D) -> Materialart(548423,D) -> Suchschlüssel
-- Column: M_PackageLicensing_MaterialGroup.Value
-- 2025-09-18T17:22:31.447Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590950,753836,0,548423,TO_TIMESTAMP('2025-09-18 17:22:30.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',40,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2025-09-18 17:22:30.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:22:31.537Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T17:22:31.586Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620)
;

-- 2025-09-18T17:22:31.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753836
;

-- 2025-09-18T17:22:31.688Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753836)
;

-- Field: Materialart(541945,D) -> Materialart(548423,D) -> Name
-- Column: M_PackageLicensing_MaterialGroup.Name
-- 2025-09-18T17:22:32.480Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590951,753837,0,548423,TO_TIMESTAMP('2025-09-18 17:22:31.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',40,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2025-09-18 17:22:31.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:22:32.529Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T17:22:32.575Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2025-09-18T17:22:32.638Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753837
;

-- 2025-09-18T17:22:32.686Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753837)
;

-- Field: Materialart(541945,D) -> Materialart(548423,D) -> Beschreibung
-- Column: M_PackageLicensing_MaterialGroup.Description
-- 2025-09-18T17:22:33.480Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590952,753838,0,548423,TO_TIMESTAMP('2025-09-18 17:22:32.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2025-09-18 17:22:32.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:22:33.528Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T17:22:33.576Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-09-18T17:22:33.635Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753838
;

-- 2025-09-18T17:22:33.681Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753838)
;

-- Field: Materialart(541945,D) -> Materialart(548423,D) -> Land
-- Column: M_PackageLicensing_MaterialGroup.C_Country_ID
-- 2025-09-18T17:22:34.467Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590953,753839,0,548423,TO_TIMESTAMP('2025-09-18 17:22:33.778000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Land',10,'D','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','N','N','N','N','N','Land',TO_TIMESTAMP('2025-09-18 17:22:33.778000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:22:34.515Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T17:22:34.562Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(192)
;

-- 2025-09-18T17:22:34.612Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753839
;

-- 2025-09-18T17:22:34.659Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753839)
;

-- Tab: Materialart(541945,D) -> Materialart(548423,D)
-- UI Section: main
-- 2025-09-18T17:23:05.751Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548423,546950,TO_TIMESTAMP('2025-09-18 17:23:05.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-18 17:23:05.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-09-18T17:23:05.801Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546950 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Materialart(541945,D) -> Materialart(548423,D) -> main
-- UI Column: 10
-- 2025-09-18T17:23:13.225Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548458,546950,TO_TIMESTAMP('2025-09-18 17:23:12.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-18 17:23:12.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Materialart(541945,D) -> Materialart(548423,D) -> main
-- UI Column: 20
-- 2025-09-18T17:23:19.301Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548459,546950,TO_TIMESTAMP('2025-09-18 17:23:18.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-09-18 17:23:18.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Materialart(541945,D) -> Materialart(548423,D) -> main -> 10
-- UI Element Group: name
-- 2025-09-18T17:23:39.260Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548458,553517,TO_TIMESTAMP('2025-09-18 17:23:38.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','name',10,'primary',TO_TIMESTAMP('2025-09-18 17:23:38.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 10 -> name.Suchschlüssel
-- Column: M_PackageLicensing_MaterialGroup.Value
-- 2025-09-18T17:24:40.012Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753836,0,548423,553517,637162,'F',TO_TIMESTAMP('2025-09-18 17:24:39.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Suchschlüssel',10,0,0,TO_TIMESTAMP('2025-09-18 17:24:39.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 10 -> name.Name
-- Column: M_PackageLicensing_MaterialGroup.Name
-- 2025-09-18T17:24:55.621Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753837,0,548423,553517,637163,'F',TO_TIMESTAMP('2025-09-18 17:24:54.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2025-09-18 17:24:54.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 10 -> name.Land
-- Column: M_PackageLicensing_MaterialGroup.C_Country_ID
-- 2025-09-18T17:25:17.591Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753839,0,548423,553517,637164,'F',TO_TIMESTAMP('2025-09-18 17:25:16.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','N','N',0,'Land',30,0,0,TO_TIMESTAMP('2025-09-18 17:25:16.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Materialart(541945,D) -> Materialart(548423,D) -> main -> 10
-- UI Element Group: desc
-- 2025-09-18T17:25:30.173Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548458,553518,TO_TIMESTAMP('2025-09-18 17:25:29.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','desc',20,TO_TIMESTAMP('2025-09-18 17:25:29.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 10 -> desc.Beschreibung
-- Column: M_PackageLicensing_MaterialGroup.Description
-- 2025-09-18T17:25:56.268Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753838,0,548423,553518,637165,'F',TO_TIMESTAMP('2025-09-18 17:25:55.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2025-09-18 17:25:55.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Materialart(541945,D) -> Materialart(548423,D) -> main -> 20
-- UI Element Group: flags
-- 2025-09-18T17:26:15.298Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548459,553519,TO_TIMESTAMP('2025-09-18 17:26:14.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-09-18 17:26:14.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 20 -> flags.Aktiv
-- Column: M_PackageLicensing_MaterialGroup.IsActive
-- 2025-09-18T17:26:41.403Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753834,0,548423,553519,637166,'F',TO_TIMESTAMP('2025-09-18 17:26:40.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-09-18 17:26:40.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Materialart(541945,D) -> Materialart(548423,D) -> main -> 20
-- UI Element Group: org
-- 2025-09-18T17:26:55.438Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548459,553520,TO_TIMESTAMP('2025-09-18 17:26:54.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-09-18 17:26:54.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 20 -> org.Sektion
-- Column: M_PackageLicensing_MaterialGroup.AD_Org_ID
-- 2025-09-18T17:27:36.029Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753833,0,548423,553520,637167,'F',TO_TIMESTAMP('2025-09-18 17:27:35.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-09-18 17:27:35.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 20 -> org.Mandant
-- Column: M_PackageLicensing_MaterialGroup.AD_Client_ID
-- 2025-09-18T17:28:02.261Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753832,0,548423,553520,637168,'F',TO_TIMESTAMP('2025-09-18 17:28:01.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2025-09-18 17:28:01.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 10 -> name.Suchschlüssel
-- Column: M_PackageLicensing_MaterialGroup.Value
-- 2025-09-18T17:28:30.278Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-09-18 17:28:30.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637162
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 10 -> name.Name
-- Column: M_PackageLicensing_MaterialGroup.Name
-- 2025-09-18T17:28:30.577Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-18 17:28:30.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637163
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 10 -> name.Land
-- Column: M_PackageLicensing_MaterialGroup.C_Country_ID
-- 2025-09-18T17:28:30.872Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-18 17:28:30.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637164
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 20 -> flags.Aktiv
-- Column: M_PackageLicensing_MaterialGroup.IsActive
-- 2025-09-18T17:28:31.163Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-18 17:28:31.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637166
;

-- UI Element: Materialart(541945,D) -> Materialart(548423,D) -> main -> 20 -> org.Mandant
-- Column: M_PackageLicensing_MaterialGroup.AD_Client_ID
-- 2025-09-18T17:28:31.455Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-18 17:28:31.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637168
;

-- 2025-09-18T17:32:48.898Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583980,0,TO_TIMESTAMP('2025-09-18 17:32:48.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Verpackungslizenzierung','Verpackungslizenzierung',TO_TIMESTAMP('2025-09-18 17:32:48.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:32:48.946Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583980 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-09-18T17:33:14.382Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Package-Licensing', PrintName='Package-Licensing',Updated=TO_TIMESTAMP('2025-09-18 17:33:14.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583980 AND AD_Language='en_US'
;

-- 2025-09-18T17:33:14.430Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-18T17:33:15.854Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583980,'en_US')
;

-- Name: Verpackungslizenzierung
-- Action Type: null
-- 2025-09-18T17:33:59.008Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,583980,542248,0,TO_TIMESTAMP('2025-09-18 17:33:58.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Verpackungslizenzierung','Y','N','N','N','Y','Verpackungslizenzierung',TO_TIMESTAMP('2025-09-18 17:33:58.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:33:59.056Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542248 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-09-18T17:33:59.107Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542248, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542248)
;

-- 2025-09-18T17:33:59.164Z
/* DDL */  select update_menu_translation_from_ad_element(583980)
;

-- Reordering children of `Settings`
-- Node name: `Picking Configuration`
-- 2025-09-18T17:34:03.008Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000079, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540991 AND AD_Tree_ID=10
;

-- Node name: `Picking Profil`
-- 2025-09-18T17:34:03.057Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000079, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541047 AND AD_Tree_ID=10
;

-- Node name: `Picking configuration (V2)`
-- 2025-09-18T17:34:03.105Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000079, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541380 AND AD_Tree_ID=10
;

-- Node name: `Verpackungslizenzierung`
-- 2025-09-18T17:34:03.154Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000079, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542248 AND AD_Tree_ID=10
;

-- Reordering children of `Product Management`
-- Node name: `Replenishment`
-- 2025-09-18T17:34:08.827Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542202 AND AD_Tree_ID=10
;

-- Node name: `Ingredients`
-- 2025-09-18T17:34:08.874Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541735 AND AD_Tree_ID=10
;

-- Node name: `Product Data Capture (Jasper)`
-- 2025-09-18T17:34:08.923Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- Node name: `Product`
-- 2025-09-18T17:34:08.970Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- Node name: `Food Advice`
-- 2025-09-18T17:34:09.018Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541963 AND AD_Tree_ID=10
;

-- Node name: `Customs Tariff`
-- 2025-09-18T17:34:09.066Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- Node name: `Commodity Number`
-- 2025-09-18T17:34:09.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Version`
-- 2025-09-18T17:34:09.162Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- Node name: `Components of the BOM & Formula`
-- 2025-09-18T17:34:09.210Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- Node name: `Discount Schema`
-- 2025-09-18T17:34:09.258Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- Node name: `Discount Rows`
-- 2025-09-18T17:34:09.311Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- Node name: `Lot control`
-- 2025-09-18T17:34:09.360Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- Node name: `Nutrition Fact`
-- 2025-09-18T17:34:09.408Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- Node name: `Nutrition Translation`
-- 2025-09-18T17:34:09.456Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- Node name: `Allergen`
-- 2025-09-18T17:34:09.505Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- Node name: `Allergen Übersetzung`
-- 2025-09-18T17:34:09.554Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- Node name: `BPartner Product statistics`
-- 2025-09-18T17:34:09.602Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol`
-- 2025-09-18T17:34:09.652Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541870 AND AD_Tree_ID=10
;

-- Node name: `Product Certification`
-- 2025-09-18T17:34:09.702Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541904 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol Trl`
-- 2025-09-18T17:34:09.750Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541871 AND AD_Tree_ID=10
;

-- Node name: `Download Partner Products`
-- 2025-09-18T17:34:09.798Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-09-18T17:34:09.847Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-09-18T17:34:09.895Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-09-18T17:34:09.943Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- Node name: `Verpackungslizenzierung`
-- 2025-09-18T17:34:09.991Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542248 AND AD_Tree_ID=10
;

-- Node name: `Produkt Marktpläze`
-- 2025-09-18T17:34:10.039Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541864 AND AD_Tree_ID=10
;

-- Node name: `Additives`
-- 2025-09-18T17:34:10.086Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541867 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material`
-- 2025-09-18T17:34:10.135Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Node name: `Additives translation`
-- 2025-09-18T17:34:10.183Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541873 AND AD_Tree_ID=10
;

-- Name: Materialart
-- Action Type: W
-- Window: Materialart(541945,D)
-- 2025-09-18T17:35:34.903Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583978,542249,0,541945,TO_TIMESTAMP('2025-09-18 17:35:34.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Materialart','Y','N','N','N','N','Materialart',TO_TIMESTAMP('2025-09-18 17:35:34.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T17:35:34.951Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542249 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-09-18T17:35:35Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542249, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542249)
;

-- 2025-09-18T17:35:35.049Z
/* DDL */  select update_menu_translation_from_ad_element(583978)
;

-- Reordering children of `Verpackungslizenzierung`
-- Node name: `Materialart`
-- 2025-09-18T17:35:46.100Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542249 AND AD_Tree_ID=10
;

