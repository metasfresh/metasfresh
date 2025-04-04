-- Tab: Geschäftspartner_OLD -> Belegart
-- Table: C_BPartner_DocType
-- Tab: Geschäftspartner_OLD(123,D) -> Belegart
-- Table: C_BPartner_DocType
-- 2025-04-03T19:34:43.619Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583570,0,547946,542470,123,'Y',TO_TIMESTAMP('2025-04-03 19:34:42.594000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','C_BPartner_DocType','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Belegart','N',290,0,TO_TIMESTAMP('2025-04-03 19:34:42.594000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:34:43.675Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547946 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-04-03T19:34:43.729Z
/* DDL */  select update_tab_translation_from_ad_element(583570) 
;

-- 2025-04-03T19:34:43.803Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547946)
;

-- Tab: Geschäftspartner_OLD -> Belegart
-- Table: C_BPartner_DocType
-- Tab: Geschäftspartner_OLD(123,D) -> Belegart
-- Table: C_BPartner_DocType
-- 2025-04-03T19:36:06.037Z
UPDATE AD_Tab SET AD_Column_ID=589852, Parent_Column_ID=563682, TabLevel=1,Updated=TO_TIMESTAMP('2025-04-03 19:36:06.037000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547946
;

-- Field: Geschäftspartner_OLD -> Belegart -> Mandant
-- Column: C_BPartner_DocType.AD_Client_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegart(547946,D) -> Mandant
-- Column: C_BPartner_DocType.AD_Client_ID
-- 2025-04-03T19:37:30.560Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589844,741862,0,547946,TO_TIMESTAMP('2025-04-03 19:37:29.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-04-03 19:37:29.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:37:30.613Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:37:30.666Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2025-04-03T19:37:30.777Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741862
;

-- 2025-04-03T19:37:30.830Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741862)
;

-- Field: Geschäftspartner_OLD -> Belegart -> Sektion
-- Column: C_BPartner_DocType.AD_Org_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegart(547946,D) -> Sektion
-- Column: C_BPartner_DocType.AD_Org_ID
-- 2025-04-03T19:37:31.828Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589845,741863,0,547946,TO_TIMESTAMP('2025-04-03 19:37:30.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-04-03 19:37:30.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:37:31.883Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:37:31.936Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2025-04-03T19:37:32.042Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741863
;

-- 2025-04-03T19:37:32.095Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741863)
;

-- Field: Geschäftspartner_OLD -> Belegart -> Aktiv
-- Column: C_BPartner_DocType.IsActive
-- Field: Geschäftspartner_OLD(123,D) -> Belegart(547946,D) -> Aktiv
-- Column: C_BPartner_DocType.IsActive
-- 2025-04-03T19:37:33.113Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589848,741864,0,547946,TO_TIMESTAMP('2025-04-03 19:37:32.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-04-03 19:37:32.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:37:33.170Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:37:33.223Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2025-04-03T19:37:33.329Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741864
;

-- 2025-04-03T19:37:33.384Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741864)
;

-- Field: Geschäftspartner_OLD -> Belegart -> Belegart
-- Column: C_BPartner_DocType.C_BPartner_DocType_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegart(547946,D) -> Belegart
-- Column: C_BPartner_DocType.C_BPartner_DocType_ID
-- 2025-04-03T19:37:34.367Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589851,741865,0,547946,TO_TIMESTAMP('2025-04-03 19:37:33.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2025-04-03 19:37:33.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:37:34.420Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:37:34.474Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583570) 
;

-- 2025-04-03T19:37:34.526Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741865
;

-- 2025-04-03T19:37:34.577Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741865)
;

-- Field: Geschäftspartner_OLD -> Belegart -> Geschäftspartner
-- Column: C_BPartner_DocType.C_BPartner_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegart(547946,D) -> Geschäftspartner
-- Column: C_BPartner_DocType.C_BPartner_ID
-- 2025-04-03T19:37:35.547Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589852,741866,0,547946,TO_TIMESTAMP('2025-04-03 19:37:34.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2025-04-03 19:37:34.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:37:35.599Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:37:35.652Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2025-04-03T19:37:35.708Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741866
;

-- 2025-04-03T19:37:35.760Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741866)
;

-- Field: Geschäftspartner_OLD -> Belegart -> Belegart
-- Column: C_BPartner_DocType.C_DocType_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegart(547946,D) -> Belegart
-- Column: C_BPartner_DocType.C_DocType_ID
-- 2025-04-03T19:37:36.745Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589853,741867,0,547946,TO_TIMESTAMP('2025-04-03 19:37:35.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',10,'D','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2025-04-03 19:37:35.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:37:36.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:37:36.853Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196) 
;

-- 2025-04-03T19:37:36.911Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741867
;

-- 2025-04-03T19:37:36.963Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741867)
;

-- Tab: Geschäftspartner_OLD(123,D) -> Belegart(547946,D)
-- UI Section: main
-- 2025-04-03T19:38:22.015Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547946,546528,TO_TIMESTAMP('2025-04-03 19:38:21.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-03 19:38:21.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-04-03T19:38:22.069Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546528 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Geschäftspartner_OLD(123,D) -> Belegart(547946,D) -> main
-- UI Column: 10
-- 2025-04-03T19:38:47.750Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547970,546528,TO_TIMESTAMP('2025-04-03 19:38:47.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-03 19:38:47.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Geschäftspartner_OLD(123,D) -> Belegart(547946,D) -> main -> 10
-- UI Element Group: default
-- 2025-04-03T19:39:05.943Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547970,552722,TO_TIMESTAMP('2025-04-03 19:39:05.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-04-03 19:39:05.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner_OLD -> Belegart.Belegart
-- Column: C_BPartner_DocType.C_DocType_ID
-- UI Element: Geschäftspartner_OLD(123,D) -> Belegart(547946,D) -> main -> 10 -> default.Belegart
-- Column: C_BPartner_DocType.C_DocType_ID
-- 2025-04-03T19:40:19.108Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741867,0,547946,552722,631309,'F',TO_TIMESTAMP('2025-04-03 19:40:18.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','Y','N','N','N',0,'Belegart',10,0,0,TO_TIMESTAMP('2025-04-03 19:40:18.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Geschäftspartner_OLD -> Belegtexte
-- Table: C_BPartner_Report_Text
-- Tab: Geschäftspartner_OLD(123,D) -> Belegtexte
-- Table: C_BPartner_Report_Text
-- 2025-04-03T19:42:16.066Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,589841,583568,0,547947,542469,123,'Y',TO_TIMESTAMP('2025-04-03 19:42:15.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','C_BPartner_Report_Text','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Belegtexte',563682,'N',300,1,TO_TIMESTAMP('2025-04-03 19:42:15.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:42:16.121Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547947 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-04-03T19:42:16.174Z
/* DDL */  select update_tab_translation_from_ad_element(583568) 
;

-- 2025-04-03T19:42:16.230Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547947)
;

-- Field: Geschäftspartner_OLD -> Belegtexte -> Mandant
-- Column: C_BPartner_Report_Text.AD_Client_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> Mandant
-- Column: C_BPartner_Report_Text.AD_Client_ID
-- 2025-04-03T19:43:11.381Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589833,741868,0,547947,TO_TIMESTAMP('2025-04-03 19:43:10.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-04-03 19:43:10.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:43:11.433Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:43:11.485Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2025-04-03T19:43:11.592Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741868
;

-- 2025-04-03T19:43:11.646Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741868)
;

-- Field: Geschäftspartner_OLD -> Belegtexte -> Sektion
-- Column: C_BPartner_Report_Text.AD_Org_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> Sektion
-- Column: C_BPartner_Report_Text.AD_Org_ID
-- 2025-04-03T19:43:12.618Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589834,741869,0,547947,TO_TIMESTAMP('2025-04-03 19:43:11.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-04-03 19:43:11.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:43:12.671Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:43:12.723Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2025-04-03T19:43:12.828Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741869
;

-- 2025-04-03T19:43:12.880Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741869)
;

-- Field: Geschäftspartner_OLD -> Belegtexte -> Aktiv
-- Column: C_BPartner_Report_Text.IsActive
-- Field: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> Aktiv
-- Column: C_BPartner_Report_Text.IsActive
-- 2025-04-03T19:43:13.866Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589837,741870,0,547947,TO_TIMESTAMP('2025-04-03 19:43:12.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-04-03 19:43:12.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:43:13.921Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:43:13.977Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2025-04-03T19:43:14.085Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741870
;

-- 2025-04-03T19:43:14.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741870)
;

-- Field: Geschäftspartner_OLD -> Belegtexte -> Belegtexte
-- Column: C_BPartner_Report_Text.C_BPartner_Report_Text_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> Belegtexte
-- Column: C_BPartner_Report_Text.C_BPartner_Report_Text_ID
-- 2025-04-03T19:43:15.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589840,741871,0,547947,TO_TIMESTAMP('2025-04-03 19:43:14.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Belegtexte',TO_TIMESTAMP('2025-04-03 19:43:14.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:43:15.243Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:43:15.295Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583568) 
;

-- 2025-04-03T19:43:15.349Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741871
;

-- 2025-04-03T19:43:15.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741871)
;

-- Field: Geschäftspartner_OLD -> Belegtexte -> Geschäftspartner
-- Column: C_BPartner_Report_Text.C_BPartner_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> Geschäftspartner
-- Column: C_BPartner_Report_Text.C_BPartner_ID
-- 2025-04-03T19:43:16.372Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589841,741872,0,547947,TO_TIMESTAMP('2025-04-03 19:43:15.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2025-04-03 19:43:15.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:43:16.424Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:43:16.479Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2025-04-03T19:43:16.535Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741872
;

-- 2025-04-03T19:43:16.586Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741872)
;

-- Field: Geschäftspartner_OLD -> Belegtexte -> Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- 2025-04-03T19:43:17.545Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589842,741873,0,547947,TO_TIMESTAMP('2025-04-03 19:43:16.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2025-04-03 19:43:16.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:43:17.597Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:43:17.649Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583570) 
;

-- 2025-04-03T19:43:17.702Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741873
;

-- 2025-04-03T19:43:17.753Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741873)
;

-- Field: Geschäftspartner_OLD -> Belegtexte -> Hinweistext
-- Column: C_BPartner_Report_Text.AdditionalText
-- Field: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> Hinweistext
-- Column: C_BPartner_Report_Text.AdditionalText
-- 2025-04-03T19:43:18.709Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589843,741874,0,547947,TO_TIMESTAMP('2025-04-03 19:43:17.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,3000,'D','Y','N','N','N','N','N','N','N','Hinweistext',TO_TIMESTAMP('2025-04-03 19:43:17.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T19:43:18.763Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T19:43:18.814Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583569) 
;

-- 2025-04-03T19:43:18.866Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741874
;

-- 2025-04-03T19:43:18.918Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741874)
;

-- Tab: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D)
-- UI Section: main
-- 2025-04-03T19:45:17.715Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547947,546529,TO_TIMESTAMP('2025-04-03 19:45:17.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-03 19:45:17.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-04-03T19:45:17.767Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546529 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> main
-- UI Column: 10
-- 2025-04-03T19:45:26.795Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547971,546529,TO_TIMESTAMP('2025-04-03 19:45:26.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-03 19:45:26.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> main -> 10
-- UI Element Group: default
-- 2025-04-03T19:45:42.771Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547971,552723,TO_TIMESTAMP('2025-04-03 19:45:42.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-04-03 19:45:42.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner_OLD -> Belegtexte.Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- UI Element: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> main -> 10 -> default.Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- 2025-04-03T19:47:42.559Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741873,0,547947,552723,631310,'L',TO_TIMESTAMP('2025-04-03 19:47:41.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',741867,547946,0,'Belegart',10,0,0,TO_TIMESTAMP('2025-04-03 19:47:41.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner_OLD -> Belegtexte.Hinweistext
-- Column: C_BPartner_Report_Text.AdditionalText
-- UI Element: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> main -> 10 -> default.Hinweistext
-- Column: C_BPartner_Report_Text.AdditionalText
-- 2025-04-03T19:48:28.031Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741874,0,547947,552723,631311,'F',TO_TIMESTAMP('2025-04-03 19:48:27.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Hinweistext',20,0,0,TO_TIMESTAMP('2025-04-03 19:48:27.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner_OLD -> Belegtexte.Sektion
-- Column: C_BPartner_Report_Text.AD_Org_ID
-- UI Element: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner_Report_Text.AD_Org_ID
-- 2025-04-03T19:49:18.388Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741869,0,547947,552723,631312,'F',TO_TIMESTAMP('2025-04-03 19:49:17.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',30,0,0,TO_TIMESTAMP('2025-04-03 19:49:17.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Geschäftspartner_OLD -> Belegtexte
-- Table: C_BPartner_Report_Text
-- Tab: Geschäftspartner_OLD(123,D) -> Belegtexte
-- Table: C_BPartner_Report_Text
-- 2025-04-03T19:52:36.987Z
UPDATE AD_Tab SET Parent_Column_ID=2893, SeqNo=180,Updated=TO_TIMESTAMP('2025-04-03 19:52:36.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547947
;

-------------------------------------------

-- UI Element: Geschäftspartner_OLD -> Belegtexte.Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- UI Element: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> main -> 10 -> default.Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- 2025-04-03T20:38:02.722Z
UPDATE AD_UI_Element SET Labels_Selector_Field_ID=741865,Updated=TO_TIMESTAMP('2025-04-03 20:38:02.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631310
;

-- UI Element: Geschäftspartner_OLD -> Belegtexte.Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- UI Element: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> main -> 10 -> default.Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- 2025-04-03T20:39:25.382Z
UPDATE AD_UI_Element SET Labels_Selector_Field_ID=741867,Updated=TO_TIMESTAMP('2025-04-03 20:39:25.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631310
;

-- Field: Geschäftspartner_OLD -> Belegtexte -> Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- 2025-04-03T20:40:25.150Z
UPDATE AD_Field SET DefaultValue='@C_BPartner_DocType_ID@',Updated=TO_TIMESTAMP('2025-04-03 20:40:25.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=741873
;

-- Field: Geschäftspartner_OLD -> Belegtexte -> Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- 2025-04-03T20:41:18.178Z
UPDATE AD_Field SET DefaultValue='@C_BPartner_DocType_ID/-1@',Updated=TO_TIMESTAMP('2025-04-03 20:41:18.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=741873
;

-- Field: Geschäftspartner_OLD -> Belegtexte -> Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- Field: Geschäftspartner_OLD(123,D) -> Belegtexte(547947,D) -> Belegart
-- Column: C_BPartner_Report_Text.C_BPartner_DocType_ID
-- 2025-04-03T20:41:52.092Z
UPDATE AD_Field SET DefaultValue='',Updated=TO_TIMESTAMP('2025-04-03 20:41:52.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=741873
;

-- Tab: Geschäftspartner_OLD -> Belegart
-- Table: C_BPartner_DocType
-- Tab: Geschäftspartner_OLD(123,D) -> Belegart
-- Table: C_BPartner_DocType
-- 2025-04-03T21:11:11.836Z
UPDATE AD_Tab SET Parent_Column_ID=NULL, SeqNo=175,Updated=TO_TIMESTAMP('2025-04-03 21:11:11.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547946
;

-- Tab: Geschäftspartner_OLD -> Belegart
-- Table: C_BPartner_DocType
-- Tab: Geschäftspartner_OLD(123,D) -> Belegart
-- Table: C_BPartner_DocType
-- 2025-04-03T21:23:18.371Z
UPDATE AD_Tab SET Parent_Column_ID=2893,Updated=TO_TIMESTAMP('2025-04-03 21:23:18.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547946
;

-- Tab: Geschäftspartner -> Belegart
-- Table: C_DocType
-- Tab: Geschäftspartner(541858,D) -> Belegart
-- Table: C_DocType
-- 2025-04-03T21:32:16.506Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,196,0,547948,217,541858,'Y',TO_TIMESTAMP('2025-04-03 21:32:15.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben','D','N','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','N','A','C_DocType','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Belegart','N',290,0,TO_TIMESTAMP('2025-04-03 21:32:15.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T21:32:16.560Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547948 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-04-03T21:32:16.613Z
/* DDL */  select update_tab_translation_from_ad_element(196) 
;

-- 2025-04-03T21:32:16.669Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547948)
;

-- Field: Geschäftspartner -> Belegart -> Belegart
-- Column: C_DocType.C_DocType_ID
-- Field: Geschäftspartner(541858,D) -> Belegart(547948,D) -> Belegart
-- Column: C_DocType.C_DocType_ID
-- 2025-04-03T21:47:26.075Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1501,741875,0,547948,0,TO_TIMESTAMP('2025-04-03 21:47:25.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',0,'U',0,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Belegart',0,0,10,0,1,1,TO_TIMESTAMP('2025-04-03 21:47:25.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T21:47:26.128Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T21:47:26.184Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196) 
;

-- 2025-04-03T21:47:26.244Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741875
;

-- 2025-04-03T21:47:26.298Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741875)
;

-- Tab: Geschäftspartner(541858,D) -> Belegart(547948,D)
-- UI Section: main
-- 2025-04-03T21:47:47.490Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547948,546530,TO_TIMESTAMP('2025-04-03 21:47:46.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-03 21:47:46.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-04-03T21:47:47.544Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546530 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

