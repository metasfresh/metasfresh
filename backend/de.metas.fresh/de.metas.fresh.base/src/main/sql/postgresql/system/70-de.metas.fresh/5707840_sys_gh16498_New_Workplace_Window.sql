-- Window: Arbeitsplatz, InternalName=null
-- Window: Arbeitsplatz, InternalName=null
-- 2023-10-19T19:28:52.611Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582772,0,541744,TO_TIMESTAMP('2023-10-19 20:28:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Arbeitsplatz','N',TO_TIMESTAMP('2023-10-19 20:28:52','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-10-19T19:28:52.613Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541744 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-10-19T19:28:52.617Z
/* DDL */  select update_window_translation_from_ad_element(582772) 
;

-- 2023-10-19T19:28:52.624Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541744
;

-- 2023-10-19T19:28:52.627Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541744)
;

-- Tab: Arbeitsplatz -> Arbeitsplatz
-- Table: C_Workplace
-- Tab: Arbeitsplatz(541744,D) -> Arbeitsplatz
-- Table: C_Workplace
-- 2023-10-19T19:29:42.048Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582772,0,547260,542375,541744,'Y',TO_TIMESTAMP('2023-10-19 20:29:41','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Workplace','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Arbeitsplatz','N',10,0,TO_TIMESTAMP('2023-10-19 20:29:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T19:29:42.050Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547260 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-10-19T19:29:42.052Z
/* DDL */  select update_tab_translation_from_ad_element(582772) 
;

-- 2023-10-19T19:29:42.056Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547260)
;

-- Field: Arbeitsplatz -> Arbeitsplatz -> Mandant
-- Column: C_Workplace.AD_Client_ID
-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Mandant
-- Column: C_Workplace.AD_Client_ID
-- 2023-10-19T19:29:53.967Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587575,721597,0,547260,TO_TIMESTAMP('2023-10-19 20:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-10-19 20:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T19:29:53.969Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721597 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T19:29:53.972Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-10-19T19:29:54.895Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721597
;

-- 2023-10-19T19:29:54.896Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721597)
;

-- Field: Arbeitsplatz -> Arbeitsplatz -> Sektion
-- Column: C_Workplace.AD_Org_ID
-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Sektion
-- Column: C_Workplace.AD_Org_ID
-- 2023-10-19T19:29:55.045Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587576,721598,0,547260,TO_TIMESTAMP('2023-10-19 20:29:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-10-19 20:29:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T19:29:55.046Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721598 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T19:29:55.047Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-10-19T19:29:55.155Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721598
;

-- 2023-10-19T19:29:55.156Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721598)
;

-- Field: Arbeitsplatz -> Arbeitsplatz -> Aktiv
-- Column: C_Workplace.IsActive
-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Aktiv
-- Column: C_Workplace.IsActive
-- 2023-10-19T19:29:55.296Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587579,721599,0,547260,TO_TIMESTAMP('2023-10-19 20:29:55','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-10-19 20:29:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T19:29:55.297Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721599 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T19:29:55.299Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-10-19T19:29:55.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721599
;

-- 2023-10-19T19:29:55.407Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721599)
;

-- Field: Arbeitsplatz -> Arbeitsplatz -> Arbeitsplatz
-- Column: C_Workplace.C_Workplace_ID
-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Arbeitsplatz
-- Column: C_Workplace.C_Workplace_ID
-- 2023-10-19T19:29:55.541Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587582,721600,0,547260,TO_TIMESTAMP('2023-10-19 20:29:55','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Arbeitsplatz',TO_TIMESTAMP('2023-10-19 20:29:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T19:29:55.543Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721600 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T19:29:55.544Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582772) 
;

-- 2023-10-19T19:29:55.547Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721600
;

-- 2023-10-19T19:29:55.547Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721600)
;

-- Field: Arbeitsplatz -> Arbeitsplatz -> Name
-- Column: C_Workplace.Name
-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Name
-- Column: C_Workplace.Name
-- 2023-10-19T19:29:55.675Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587583,721601,0,547260,TO_TIMESTAMP('2023-10-19 20:29:55','YYYY-MM-DD HH24:MI:SS'),100,'',40,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-10-19 20:29:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T19:29:55.676Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721601 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T19:29:55.677Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-10-19T19:29:55.912Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721601
;

-- 2023-10-19T19:29:55.913Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721601)
;

-- Field: Arbeitsplatz -> Arbeitsplatz -> Beschreibung
-- Column: C_Workplace.Description
-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Beschreibung
-- Column: C_Workplace.Description
-- 2023-10-19T19:29:56.053Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587584,721602,0,547260,TO_TIMESTAMP('2023-10-19 20:29:55','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2023-10-19 20:29:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T19:29:56.054Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721602 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T19:29:56.055Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-10-19T19:29:56.332Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721602
;

-- 2023-10-19T19:29:56.333Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721602)
;

-- Field: Arbeitsplatz -> Arbeitsplatz -> Lager
-- Column: C_Workplace.M_Warehouse_ID
-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Lager
-- Column: C_Workplace.M_Warehouse_ID
-- 2023-10-19T19:29:56.461Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587585,721603,0,547260,TO_TIMESTAMP('2023-10-19 20:29:56','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',10,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2023-10-19 20:29:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T19:29:56.462Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721603 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T19:29:56.464Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2023-10-19T19:29:56.521Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721603
;

-- 2023-10-19T19:29:56.522Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721603)
;

-- Tab: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D)
-- UI Section: main
-- 2023-10-19T19:31:48.973Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547260,545855,TO_TIMESTAMP('2023-10-19 20:31:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-10-19 20:31:48','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-10-19T19:31:48.975Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545855 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main
-- UI Column: 10
-- 2023-10-19T19:31:55.564Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547139,545855,TO_TIMESTAMP('2023-10-19 20:31:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-10-19 20:31:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main
-- UI Column: 20
-- 2023-10-19T19:31:58.464Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547140,545855,TO_TIMESTAMP('2023-10-19 20:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-10-19 20:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10
-- UI Element Group: main
-- 2023-10-19T19:32:24.962Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547139,551256,TO_TIMESTAMP('2023-10-19 20:32:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-10-19 20:32:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Name
-- Column: C_Workplace.Name
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Name
-- Column: C_Workplace.Name
-- 2023-10-19T19:32:57.713Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721601,0,547260,551256,621124,'F',TO_TIMESTAMP('2023-10-19 20:32:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2023-10-19 20:32:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Lager
-- Column: C_Workplace.M_Warehouse_ID
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Lager
-- Column: C_Workplace.M_Warehouse_ID
-- 2023-10-19T19:33:11.848Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721603,0,547260,551256,621125,'F',TO_TIMESTAMP('2023-10-19 20:33:11','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','N','N',0,'Lager',20,0,0,TO_TIMESTAMP('2023-10-19 20:33:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10
-- UI Element Group: description
-- 2023-10-19T19:33:32.779Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547139,551257,TO_TIMESTAMP('2023-10-19 20:33:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2023-10-19 20:33:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Beschreibung
-- Column: C_Workplace.Description
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> description.Beschreibung
-- Column: C_Workplace.Description
-- 2023-10-19T19:33:49.957Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721602,0,547260,551257,621126,'F',TO_TIMESTAMP('2023-10-19 20:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2023-10-19 20:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20
-- UI Element Group: flags
-- 2023-10-19T19:34:03.366Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547140,551258,TO_TIMESTAMP('2023-10-19 20:34:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-10-19 20:34:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Aktiv
-- Column: C_Workplace.IsActive
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Workplace.IsActive
-- 2023-10-19T19:34:20.712Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721599,0,547260,551258,621127,'F',TO_TIMESTAMP('2023-10-19 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-10-19 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20
-- UI Element Group: orgs
-- 2023-10-19T19:34:30.053Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547140,551259,TO_TIMESTAMP('2023-10-19 20:34:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','orgs',20,TO_TIMESTAMP('2023-10-19 20:34:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Sektion
-- Column: C_Workplace.AD_Org_ID
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> orgs.Sektion
-- Column: C_Workplace.AD_Org_ID
-- 2023-10-19T19:34:47.673Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721598,0,547260,551259,621128,'F',TO_TIMESTAMP('2023-10-19 20:34:47','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2023-10-19 20:34:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Mandant
-- Column: C_Workplace.AD_Client_ID
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> orgs.Mandant
-- Column: C_Workplace.AD_Client_ID
-- 2023-10-19T19:35:05.478Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721597,0,547260,551259,621129,'F',TO_TIMESTAMP('2023-10-19 20:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-10-19 20:35:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: Arbeitsplatz
-- Action Type: W
-- Window: Arbeitsplatz(541744,D)
-- 2023-10-19T19:36:52.187Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582772,542120,0,541744,TO_TIMESTAMP('2023-10-19 20:36:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Workplace','Y','N','N','N','N','Arbeitsplatz',TO_TIMESTAMP('2023-10-19 20:36:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T19:36:52.189Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542120 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-10-19T19:36:52.191Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542120, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542120)
;

-- 2023-10-19T19:36:52.197Z
/* DDL */  select update_menu_translation_from_ad_element(582772) 
;

-- Reordering children of `Material Management Rules`
-- Node name: `Product Setup`
-- 2023-10-19T19:36:52.782Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=268 AND AD_Tree_ID=10
;

-- Node name: `Warehouse & Locators (M_Warehouse)`
-- 2023-10-19T19:36:52.787Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=125 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Organization (org.compiere.process.OrgOwnership)`
-- 2023-10-19T19:36:52.788Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=422 AND AD_Tree_ID=10
;

-- Node name: `Unit of Measure (C_UOM)`
-- 2023-10-19T19:36:52.789Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=107 AND AD_Tree_ID=10
;

-- Node name: `Product Category (M_Product_Category)`
-- 2023-10-19T19:36:52.790Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=130 AND AD_Tree_ID=10
;

-- Node name: `Vendor Details (C_BPartner)`
-- 2023-10-19T19:36:52.791Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=188 AND AD_Tree_ID=10
;

-- Node name: `Vendor Selection`
-- 2023-10-19T19:36:52.792Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=227 AND AD_Tree_ID=10
;

-- Node name: `Freight Category (M_FreightCategory)`
-- 2023-10-19T19:36:52.792Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=381 AND AD_Tree_ID=10
;

-- Node name: `Product (M_Product)`
-- 2023-10-19T19:36:52.793Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=126 AND AD_Tree_ID=10
;

-- Node name: `Product Organization (org.compiere.process.OrgOwnership)`
-- 2023-10-19T19:36:52.794Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=421 AND AD_Tree_ID=10
;

-- Node name: `Price List Setup`
-- 2023-10-19T19:36:52.794Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=267 AND AD_Tree_ID=10
;

-- Node name: `Product BOM (M_Product)`
-- 2023-10-19T19:36:52.795Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=534 AND AD_Tree_ID=10
;

-- Node name: `Price List Schema (M_DiscountSchema)`
-- 2023-10-19T19:36:52.796Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=490 AND AD_Tree_ID=10
;

-- Node name: `Price List (M_PricingSystem)`
-- 2023-10-19T19:36:52.797Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=132 AND AD_Tree_ID=10
;

-- Node name: `Discount Schema (M_DiscountSchema)`
-- 2023-10-19T19:36:52.798Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=310 AND AD_Tree_ID=10
;

-- Node name: `Pricing Rule (C_PricingRule)`
-- 2023-10-19T19:36:52.799Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53432 AND AD_Tree_ID=10
;

-- Node name: `Shipper (M_Shipper)`
-- 2023-10-19T19:36:52.799Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=128 AND AD_Tree_ID=10
;

-- Node name: `Verify BOMs (org.eevolution.process.PP_Product_BOM_Check)`
-- 2023-10-19T19:36:52.800Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=585 AND AD_Tree_ID=10
;

-- Node name: `Promotion Group (M_PromotionGroup)`
-- 2023-10-19T19:36:52.801Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53210 AND AD_Tree_ID=10
;

-- Node name: `Perpetual Inventory (M_PerpetualInv)`
-- 2023-10-19T19:36:52.802Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=187 AND AD_Tree_ID=10
;

-- Node name: `Promotion (M_Promotion)`
-- 2023-10-19T19:36:52.803Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53211 AND AD_Tree_ID=10
;

-- Node name: `Test UOM Conversions (de.metas.uom.form.UOMConversionCheckFormPanel)`
-- 2023-10-19T19:36:52.803Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540646 AND AD_Tree_ID=10
;

-- Node name: `Arbeitsplatz`
-- 2023-10-19T19:36:52.804Z
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Reordering children of `Warehouse Management`
-- Node name: `Arbeitsplatz`
-- 2023-10-19T19:42:53.992Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Node name: `Warehouse (M_Warehouse)`
-- 2023-10-19T19:42:54Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type (M_Warehouse_Type)`
-- 2023-10-19T19:42:54.001Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move (M_Movement)`
-- 2023-10-19T19:42:54.002Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme (M_Inventory)`
-- 2023-10-19T19:42:54.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase (Fresh_QtyOnHand)`
-- 2023-10-19T19:42:54.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule (MD_Candidate)`
-- 2023-10-19T19:42:54.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast (M_Forecast)`
-- 2023-10-19T19:42:54.005Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit (MD_Cockpit)`
-- 2023-10-19T19:42:54.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-10-19T19:42:54.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-10-19T19:42:54.008Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-10-19T19:42:54.008Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory (M_Inventory)`
-- 2023-10-19T19:42:54.009Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting (M_InventoryLine)`
-- 2023-10-19T19:42:54.010Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate (M_Inventory_Candidate)`
-- 2023-10-19T19:42:54.010Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Name
-- Column: C_Workplace.Name
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Name
-- Column: C_Workplace.Name
-- 2023-10-23T14:40:18.546Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-23 15:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621124
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Lager
-- Column: C_Workplace.M_Warehouse_ID
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Lager
-- Column: C_Workplace.M_Warehouse_ID
-- 2023-10-23T14:40:19.562Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-23 15:40:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621125
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Beschreibung
-- Column: C_Workplace.Description
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> description.Beschreibung
-- Column: C_Workplace.Description
-- 2023-10-23T14:40:20.255Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-23 15:40:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621126
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Aktiv
-- Column: C_Workplace.IsActive
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Workplace.IsActive
-- 2023-10-23T14:40:20.939Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-23 15:40:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621127
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Sektion
-- Column: C_Workplace.AD_Org_ID
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> orgs.Sektion
-- Column: C_Workplace.AD_Org_ID
-- 2023-10-23T14:40:21.633Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-23 15:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621128
;

