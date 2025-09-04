-- Window: Belegtexte, InternalName=null
-- Window: Belegtexte, InternalName=null
-- 2025-04-05T19:00:40.147Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583575,0,541878,TO_TIMESTAMP('2025-04-05 19:00:39.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Belegtexte','N',TO_TIMESTAMP('2025-04-05 19:00:39.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-04-05T19:00:40.150Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541878 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-04-05T19:00:40.152Z
/* DDL */  select update_window_translation_from_ad_element(583575) 
;

-- 2025-04-05T19:00:40.160Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541878
;

-- 2025-04-05T19:00:40.165Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541878)
;

-- 2025-04-05T19:01:39.576Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Report Texts',Updated=TO_TIMESTAMP('2025-04-05 19:01:39.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542474
;

-- Element: C_BPartner_Report_Text_ID
-- 2025-04-05T19:01:53.072Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Report Texts', PrintName='Report Texts',Updated=TO_TIMESTAMP('2025-04-05 19:01:53.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583575 AND AD_Language='en_US'
;

-- 2025-04-05T19:01:53.074Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583575,'en_US') 
;

-- Tab: Belegtexte -> Belegtexte
-- Table: C_BPartner_Report_Text
-- Tab: Belegtexte(541878,D) -> Belegtexte
-- Table: C_BPartner_Report_Text
-- 2025-04-05T19:02:27.995Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583575,0,547954,542474,541878,'Y',TO_TIMESTAMP('2025-04-05 19:02:27.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','C_BPartner_Report_Text','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Belegtexte','N',10,0,TO_TIMESTAMP('2025-04-05 19:02:27.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:02:27.997Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547954 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-04-05T19:02:27.999Z
/* DDL */  select update_tab_translation_from_ad_element(583575) 
;

-- 2025-04-05T19:02:28.003Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547954)
;

-- Field: Belegtexte -> Belegtexte -> Mandant
-- Column: C_BPartner_Report_Text.AD_Client_ID
-- Field: Belegtexte(541878,D) -> Belegtexte(547954,D) -> Mandant
-- Column: C_BPartner_Report_Text.AD_Client_ID
-- 2025-04-05T19:02:44.700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589887,741949,0,547954,TO_TIMESTAMP('2025-04-05 19:02:44.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-04-05 19:02:44.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:02:44.705Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741949 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:02:44.709Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2025-04-05T19:02:45.496Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741949
;

-- 2025-04-05T19:02:45.498Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741949)
;

-- Field: Belegtexte -> Belegtexte -> Sektion
-- Column: C_BPartner_Report_Text.AD_Org_ID
-- Field: Belegtexte(541878,D) -> Belegtexte(547954,D) -> Sektion
-- Column: C_BPartner_Report_Text.AD_Org_ID
-- 2025-04-05T19:02:45.624Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589888,741950,0,547954,TO_TIMESTAMP('2025-04-05 19:02:45.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-04-05 19:02:45.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:02:45.627Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741950 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:02:45.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2025-04-05T19:02:45.951Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741950
;

-- 2025-04-05T19:02:45.952Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741950)
;

-- Field: Belegtexte -> Belegtexte -> Aktiv
-- Column: C_BPartner_Report_Text.IsActive
-- Field: Belegtexte(541878,D) -> Belegtexte(547954,D) -> Aktiv
-- Column: C_BPartner_Report_Text.IsActive
-- 2025-04-05T19:02:46.069Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589891,741951,0,547954,TO_TIMESTAMP('2025-04-05 19:02:45.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-04-05 19:02:45.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:02:46.071Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741951 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:02:46.073Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2025-04-05T19:02:46.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741951
;

-- 2025-04-05T19:02:46.392Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741951)
;

-- Field: Belegtexte -> Belegtexte -> Belegtexte
-- Column: C_BPartner_Report_Text.C_BPartner_Report_Text_ID
-- Field: Belegtexte(541878,D) -> Belegtexte(547954,D) -> Belegtexte
-- Column: C_BPartner_Report_Text.C_BPartner_Report_Text_ID
-- 2025-04-05T19:02:46.526Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589894,741952,0,547954,TO_TIMESTAMP('2025-04-05 19:02:46.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Belegtexte',TO_TIMESTAMP('2025-04-05 19:02:46.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:02:46.528Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741952 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:02:46.529Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583575) 
;

-- 2025-04-05T19:02:46.532Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741952
;

-- 2025-04-05T19:02:46.533Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741952)
;

-- Field: Belegtexte -> Belegtexte -> Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- Field: Belegtexte(541878,D) -> Belegtexte(547954,D) -> Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- 2025-04-05T19:02:46.694Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589895,741953,0,547954,TO_TIMESTAMP('2025-04-05 19:02:46.535000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2025-04-05 19:02:46.535000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:02:46.696Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741953 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:02:46.697Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583574) 
;

-- 2025-04-05T19:02:46.699Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741953
;

-- 2025-04-05T19:02:46.700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741953)
;

-- Field: Belegtexte -> Belegtexte -> Geschäftspartner
-- Column: C_BPartner_Report_Text.C_BPartner_ID
-- Field: Belegtexte(541878,D) -> Belegtexte(547954,D) -> Geschäftspartner
-- Column: C_BPartner_Report_Text.C_BPartner_ID
-- 2025-04-05T19:02:46.842Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589896,741954,0,547954,TO_TIMESTAMP('2025-04-05 19:02:46.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2025-04-05 19:02:46.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:02:46.843Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741954 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:02:46.844Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2025-04-05T19:02:46.892Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741954
;

-- 2025-04-05T19:02:46.893Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741954)
;

-- Field: Belegtexte -> Belegtexte -> Hinweistext
-- Column: C_BPartner_Report_Text.AdditionalText
-- Field: Belegtexte(541878,D) -> Belegtexte(547954,D) -> Hinweistext
-- Column: C_BPartner_Report_Text.AdditionalText
-- 2025-04-05T19:02:47.031Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589897,741955,0,547954,TO_TIMESTAMP('2025-04-05 19:02:46.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,3000,'D','Y','N','N','N','N','N','N','N','Hinweistext',TO_TIMESTAMP('2025-04-05 19:02:46.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:02:47.033Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:02:47.035Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583576) 
;

-- 2025-04-05T19:02:47.037Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741955
;

-- 2025-04-05T19:02:47.037Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741955)
;

-- Field: Belegtexte -> Belegtexte -> Suchschlüssel
-- Column: C_BPartner_Report_Text.Value
-- Field: Belegtexte(541878,D) -> Belegtexte(547954,D) -> Suchschlüssel
-- Column: C_BPartner_Report_Text.Value
-- 2025-04-05T19:06:57.410Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589900,741956,0,547954,0,TO_TIMESTAMP('2025-04-05 19:06:57.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',0,'D',0,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Suchschlüssel',0,0,10,0,1,1,TO_TIMESTAMP('2025-04-05 19:06:57.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:06:57.411Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741956 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:06:57.413Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2025-04-05T19:06:57.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741956
;

-- 2025-04-05T19:06:57.432Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741956)
;

-- Tab: Belegtexte -> Belegart
-- Table: C_BPartner_DocType
-- Tab: Belegtexte(541878,D) -> Belegart
-- Table: C_BPartner_DocType
-- 2025-04-05T19:07:44.299Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583574,0,547955,542473,541878,'Y',TO_TIMESTAMP('2025-04-05 19:07:44.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','C_BPartner_DocType','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Belegart','N',20,0,TO_TIMESTAMP('2025-04-05 19:07:44.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:07:44.301Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547955 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-04-05T19:07:44.303Z
/* DDL */  select update_tab_translation_from_ad_element(583574) 
;

-- 2025-04-05T19:07:44.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547955)
;

-- Field: Belegtexte -> Belegart -> Mandant
-- Column: C_BPartner_DocType.AD_Client_ID
-- Field: Belegtexte(541878,D) -> Belegart(547955,D) -> Mandant
-- Column: C_BPartner_DocType.AD_Client_ID
-- 2025-04-05T19:07:51.728Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589877,741957,0,547955,TO_TIMESTAMP('2025-04-05 19:07:51.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-04-05 19:07:51.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:07:51.729Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741957 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:07:51.730Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2025-04-05T19:07:51.800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741957
;

-- 2025-04-05T19:07:51.801Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741957)
;

-- Field: Belegtexte -> Belegart -> Sektion
-- Column: C_BPartner_DocType.AD_Org_ID
-- Field: Belegtexte(541878,D) -> Belegart(547955,D) -> Sektion
-- Column: C_BPartner_DocType.AD_Org_ID
-- 2025-04-05T19:07:51.927Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589878,741958,0,547955,TO_TIMESTAMP('2025-04-05 19:07:51.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-04-05 19:07:51.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:07:51.929Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741958 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:07:51.930Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2025-04-05T19:07:51.998Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741958
;

-- 2025-04-05T19:07:51.999Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741958)
;

-- Field: Belegtexte -> Belegart -> Aktiv
-- Column: C_BPartner_DocType.IsActive
-- Field: Belegtexte(541878,D) -> Belegart(547955,D) -> Aktiv
-- Column: C_BPartner_DocType.IsActive
-- 2025-04-05T19:07:52.142Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589881,741959,0,547955,TO_TIMESTAMP('2025-04-05 19:07:52.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-04-05 19:07:52.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:07:52.144Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741959 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:07:52.145Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2025-04-05T19:07:52.216Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741959
;

-- 2025-04-05T19:07:52.218Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741959)
;

-- Field: Belegtexte -> Belegart -> Belegart
-- Column: C_BPartner_DocType.C_BPartner_DocType_ID
-- Field: Belegtexte(541878,D) -> Belegart(547955,D) -> Belegart
-- Column: C_BPartner_DocType.C_BPartner_DocType_ID
-- 2025-04-05T19:07:52.340Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589884,741960,0,547955,TO_TIMESTAMP('2025-04-05 19:07:52.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2025-04-05 19:07:52.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:07:52.341Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741960 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:07:52.344Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583574) 
;

-- 2025-04-05T19:07:52.346Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741960
;

-- 2025-04-05T19:07:52.347Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741960)
;

-- Field: Belegtexte -> Belegart -> Geschäftspartner
-- Column: C_BPartner_DocType.C_BPartner_ID
-- Field: Belegtexte(541878,D) -> Belegart(547955,D) -> Geschäftspartner
-- Column: C_BPartner_DocType.C_BPartner_ID
-- 2025-04-05T19:07:52.472Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589885,741961,0,547955,TO_TIMESTAMP('2025-04-05 19:07:52.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2025-04-05 19:07:52.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:07:52.473Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741961 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:07:52.474Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2025-04-05T19:07:52.482Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741961
;

-- 2025-04-05T19:07:52.483Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741961)
;

-- Field: Belegtexte -> Belegart -> Belegart
-- Column: C_BPartner_DocType.C_DocType_ID
-- Field: Belegtexte(541878,D) -> Belegart(547955,D) -> Belegart
-- Column: C_BPartner_DocType.C_DocType_ID
-- 2025-04-05T19:07:52.620Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589886,741962,0,547955,TO_TIMESTAMP('2025-04-05 19:07:52.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',10,'D','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2025-04-05 19:07:52.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:07:52.622Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741962 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:07:52.623Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196) 
;

-- 2025-04-05T19:07:52.636Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741962
;

-- 2025-04-05T19:07:52.637Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741962)
;

-- Field: Belegtexte -> Belegart -> Belegtexte
-- Column: C_BPartner_DocType.C_BPartner_Report_Text_ID
-- Field: Belegtexte(541878,D) -> Belegart(547955,D) -> Belegtexte
-- Column: C_BPartner_DocType.C_BPartner_Report_Text_ID
-- 2025-04-05T19:07:52.764Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589898,741963,0,547955,TO_TIMESTAMP('2025-04-05 19:07:52.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Belegtexte',TO_TIMESTAMP('2025-04-05 19:07:52.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:07:52.765Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741963 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-05T19:07:52.766Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583575) 
;

-- 2025-04-05T19:07:52.768Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741963
;

-- 2025-04-05T19:07:52.769Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741963)
;

-- Tab: Belegtexte(541878,D) -> Belegtexte(547954,D)
-- UI Section: main
-- 2025-04-05T19:09:08.183Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547954,546537,TO_TIMESTAMP('2025-04-05 19:09:07.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-05 19:09:07.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-04-05T19:09:08.186Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546537 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main
-- UI Column: 10
-- 2025-04-05T19:09:13.122Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547979,546537,TO_TIMESTAMP('2025-04-05 19:09:12.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-05 19:09:12.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main
-- UI Column: 20
-- 2025-04-05T19:09:15.036Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547980,546537,TO_TIMESTAMP('2025-04-05 19:09:14.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-04-05 19:09:14.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main -> 10
-- UI Element Group: default
-- 2025-04-05T19:09:38.332Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547979,552731,TO_TIMESTAMP('2025-04-05 19:09:38.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-04-05 19:09:38.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Belegtexte -> Belegtexte.Suchschlüssel
-- Column: C_BPartner_Report_Text.Value
-- UI Element: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main -> 10 -> default.Suchschlüssel
-- Column: C_BPartner_Report_Text.Value
-- 2025-04-05T19:11:22.856Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741956,0,547954,552731,631329,'F',TO_TIMESTAMP('2025-04-05 19:11:22.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Suchschlüssel',10,0,0,TO_TIMESTAMP('2025-04-05 19:11:22.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Belegtexte -> Belegtexte.Geschäftspartner
-- Column: C_BPartner_Report_Text.C_BPartner_ID
-- UI Element: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_BPartner_Report_Text.C_BPartner_ID
-- 2025-04-05T19:11:40.199Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741954,0,547954,552731,631330,'F',TO_TIMESTAMP('2025-04-05 19:11:40.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',20,0,0,TO_TIMESTAMP('2025-04-05 19:11:40.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Belegtexte -> Belegtexte.Belegart
-- UI Element: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main -> 10 -> default.Belegart
-- 2025-04-05T19:12:10.425Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,547954,552731,631331,'L',TO_TIMESTAMP('2025-04-05 19:12:10.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',741962,547955,0,'Belegart',30,0,0,TO_TIMESTAMP('2025-04-05 19:12:10.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Belegtexte -> Belegtexte.Hinweistext
-- Column: C_BPartner_Report_Text.AdditionalText
-- UI Element: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main -> 10 -> default.Hinweistext
-- Column: C_BPartner_Report_Text.AdditionalText
-- 2025-04-05T19:12:37.243Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741955,0,547954,552731,631332,'F',TO_TIMESTAMP('2025-04-05 19:12:37.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Hinweistext',40,0,0,TO_TIMESTAMP('2025-04-05 19:12:37.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main -> 20
-- UI Element Group: flags
-- 2025-04-05T19:13:38.869Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547980,552732,TO_TIMESTAMP('2025-04-05 19:13:38.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-04-05 19:13:38.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Belegtexte -> Belegtexte.Aktiv
-- Column: C_BPartner_Report_Text.IsActive
-- UI Element: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main -> 20 -> flags.Aktiv
-- Column: C_BPartner_Report_Text.IsActive
-- 2025-04-05T19:13:57.837Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741951,0,547954,552732,631333,'F',TO_TIMESTAMP('2025-04-05 19:13:57.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-04-05 19:13:57.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main -> 20
-- UI Element Group: org
-- 2025-04-05T19:14:15.740Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547980,552733,TO_TIMESTAMP('2025-04-05 19:14:15.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-04-05 19:14:15.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Belegtexte -> Belegtexte.Sektion
-- Column: C_BPartner_Report_Text.AD_Org_ID
-- UI Element: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main -> 20 -> org.Sektion
-- Column: C_BPartner_Report_Text.AD_Org_ID
-- 2025-04-05T19:14:32.952Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741950,0,547954,552733,631334,'F',TO_TIMESTAMP('2025-04-05 19:14:32.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-04-05 19:14:32.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Belegtexte -> Belegtexte.Mandant
-- Column: C_BPartner_Report_Text.AD_Client_ID
-- UI Element: Belegtexte(541878,D) -> Belegtexte(547954,D) -> main -> 20 -> org.Mandant
-- Column: C_BPartner_Report_Text.AD_Client_ID
-- 2025-04-05T19:14:44.790Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741949,0,547954,552733,631335,'F',TO_TIMESTAMP('2025-04-05 19:14:44.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2025-04-05 19:14:44.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: Belegtexte
-- Action Type: W
-- Window: Belegtexte(541878,D)
-- 2025-04-05T19:15:42.718Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583575,542217,0,541878,TO_TIMESTAMP('2025-04-05 19:15:42.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Belegtexte','Y','N','N','N','N','Belegtexte',TO_TIMESTAMP('2025-04-05 19:15:42.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-05T19:15:42.721Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542217 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-04-05T19:15:42.723Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542217, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542217)
;

-- 2025-04-05T19:15:42.730Z
/* DDL */  select update_menu_translation_from_ad_element(583575) 
;

-- Reordering children of `Partner Relations`
-- Node name: `Business Partner Rules`
-- 2025-04-05T19:15:43.307Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=165 AND AD_Tree_ID=10
;

-- Node name: `Web`
-- 2025-04-05T19:15:43.309Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=372 AND AD_Tree_ID=10
;

-- Node name: `Service`
-- 2025-04-05T19:15:43.310Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=271 AND AD_Tree_ID=10
;

-- Node name: `Request`
-- 2025-04-05T19:15:43.311Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=528 AND AD_Tree_ID=10
;

-- Node name: `Sales Rep Info`
-- 2025-04-05T19:15:43.312Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=414 AND AD_Tree_ID=10
;

-- Node name: `Mail Template`
-- 2025-04-05T19:15:43.314Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=238 AND AD_Tree_ID=10
;

-- Node name: `Mail Template Translations`
-- 2025-04-05T19:15:43.315Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541315 AND AD_Tree_ID=10
;

-- Node name: `Umsatzliste`
-- 2025-04-05T19:15:43.316Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540594 AND AD_Tree_ID=10
;

-- Node name: `Import Users`
-- 2025-04-05T19:15:43.317Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540964 AND AD_Tree_ID=10
;

-- Node name: `Import Request`
-- 2025-04-05T19:15:43.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540962 AND AD_Tree_ID=10
;

-- Node name: `Belegtexte`
-- 2025-04-05T19:15:43.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542217 AND AD_Tree_ID=10
;

-- Reordering children of `webUI`
-- Node name: `CRM`
-- 2025-04-05T19:15:46.558Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Belegtexte`
-- 2025-04-05T19:15:46.560Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542217 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2025-04-05T19:15:46.560Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2025-04-05T19:15:46.561Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2025-04-05T19:15:46.562Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2025-04-05T19:15:46.563Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2025-04-05T19:15:46.564Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2025-04-05T19:15:46.564Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2025-04-05T19:15:46.566Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2025-04-05T19:15:46.568Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2025-04-05T19:15:46.569Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2025-04-05T19:15:46.570Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2025-04-05T19:15:46.571Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2025-04-05T19:15:46.571Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2025-04-05T19:15:46.572Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2025-04-05T19:15:46.573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2025-04-05T19:15:46.573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2025-04-05T19:15:46.574Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2025-04-05T19:15:46.575Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2025-04-05T19:15:46.576Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2025-04-05T19:15:46.576Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2025-04-05T19:15:46.578Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Reordering children of `CRM`
-- Node name: `Belegtexte`
-- 2025-04-05T19:15:49.460Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542217 AND AD_Tree_ID=10
;

-- Node name: `Request`
-- 2025-04-05T19:15:49.461Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=237 AND AD_Tree_ID=10
;

-- Node name: `Request (all)`
-- 2025-04-05T19:15:49.462Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=308 AND AD_Tree_ID=10
;

-- Node name: `Company Phone Book`
-- 2025-04-05T19:15:49.463Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- Node name: `Business Partner`
-- 2025-04-05T19:15:49.464Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- Node name: `Partner Export`
-- 2025-04-05T19:15:49.465Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541728 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents`
-- 2025-04-05T19:15:49.465Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-04-05T19:15:49.466Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-04-05T19:15:49.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-04-05T19:15:49.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- Node name: `Businesspartner Global ID`
-- 2025-04-05T19:15:49.469Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- Node name: `Import User`
-- 2025-04-05T19:15:49.470Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- Node name: `Phone call`
-- 2025-04-05T19:15:49.471Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541896 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema`
-- 2025-04-05T19:15:49.472Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema Version`
-- 2025-04-05T19:15:49.472Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schedule`
-- 2025-04-05T19:15:49.473Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;

