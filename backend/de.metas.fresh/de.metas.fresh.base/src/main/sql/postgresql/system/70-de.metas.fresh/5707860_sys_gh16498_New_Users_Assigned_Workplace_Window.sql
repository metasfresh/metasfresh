-- Window: Dem Arbeitsplatz zugewiesene Benutzer, InternalName=null
-- Window: Dem Arbeitsplatz zugewiesene Benutzer, InternalName=null
-- 2023-10-19T20:18:16.176Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582773,0,541745,TO_TIMESTAMP('2023-10-19 21:18:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Dem Arbeitsplatz zugewiesene Benutzer','N',TO_TIMESTAMP('2023-10-19 21:18:15','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-10-19T20:18:16.178Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541745 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-10-19T20:18:16.179Z
/* DDL */  select update_window_translation_from_ad_element(582773) 
;

-- 2023-10-19T20:18:16.181Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541745
;

-- 2023-10-19T20:18:16.183Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541745)
;

-- Tab: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer
-- Table: C_Workplace_User_Assign
-- Tab: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer
-- Table: C_Workplace_User_Assign
-- 2023-10-19T20:19:05.307Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582773,0,547261,542376,541745,'Y',TO_TIMESTAMP('2023-10-19 21:19:05','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Workplace_User_Assign','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Dem Arbeitsplatz zugewiesene Benutzer','N',10,0,TO_TIMESTAMP('2023-10-19 21:19:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T20:19:05.309Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547261 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-10-19T20:19:05.310Z
/* DDL */  select update_tab_translation_from_ad_element(582773) 
;

-- 2023-10-19T20:19:05.319Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547261)
;

-- Field: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer -> Mandant
-- Column: C_Workplace_User_Assign.AD_Client_ID
-- Field: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> Mandant
-- Column: C_Workplace_User_Assign.AD_Client_ID
-- 2023-10-19T20:19:16.239Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587586,721604,0,547261,TO_TIMESTAMP('2023-10-19 21:19:16','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-10-19 21:19:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T20:19:16.241Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721604 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T20:19:16.243Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-10-19T20:19:16.599Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721604
;

-- 2023-10-19T20:19:16.600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721604)
;

-- Field: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer -> Sektion
-- Column: C_Workplace_User_Assign.AD_Org_ID
-- Field: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> Sektion
-- Column: C_Workplace_User_Assign.AD_Org_ID
-- 2023-10-19T20:19:16.751Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587587,721605,0,547261,TO_TIMESTAMP('2023-10-19 21:19:16','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-10-19 21:19:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T20:19:16.753Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721605 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T20:19:16.754Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-10-19T20:19:16.831Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721605
;

-- 2023-10-19T20:19:16.834Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721605)
;

-- Field: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer -> Aktiv
-- Column: C_Workplace_User_Assign.IsActive
-- Field: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> Aktiv
-- Column: C_Workplace_User_Assign.IsActive
-- 2023-10-19T20:19:16.976Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587590,721606,0,547261,TO_TIMESTAMP('2023-10-19 21:19:16','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-10-19 21:19:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T20:19:16.977Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721606 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T20:19:16.979Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-10-19T20:19:17.058Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721606
;

-- 2023-10-19T20:19:17.060Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721606)
;

-- Field: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer
-- Column: C_Workplace_User_Assign.C_Workplace_User_Assign_ID
-- Field: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> Dem Arbeitsplatz zugewiesene Benutzer
-- Column: C_Workplace_User_Assign.C_Workplace_User_Assign_ID
-- 2023-10-19T20:19:17.199Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587593,721607,0,547261,TO_TIMESTAMP('2023-10-19 21:19:17','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Dem Arbeitsplatz zugewiesene Benutzer',TO_TIMESTAMP('2023-10-19 21:19:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T20:19:17.201Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721607 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T20:19:17.202Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582773) 
;

-- 2023-10-19T20:19:17.205Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721607
;

-- 2023-10-19T20:19:17.205Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721607)
;

-- Field: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer -> Ansprechpartner
-- Column: C_Workplace_User_Assign.AD_User_ID
-- Field: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> Ansprechpartner
-- Column: C_Workplace_User_Assign.AD_User_ID
-- 2023-10-19T20:19:17.345Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587594,721608,0,547261,TO_TIMESTAMP('2023-10-19 21:19:17','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',10,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2023-10-19 21:19:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T20:19:17.347Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721608 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T20:19:17.348Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2023-10-19T20:19:17.353Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721608
;

-- 2023-10-19T20:19:17.354Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721608)
;

-- Field: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer -> Arbeitsplatz
-- Column: C_Workplace_User_Assign.C_Workplace_ID
-- Field: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> Arbeitsplatz
-- Column: C_Workplace_User_Assign.C_Workplace_ID
-- 2023-10-19T20:19:17.495Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587595,721609,0,547261,TO_TIMESTAMP('2023-10-19 21:19:17','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Arbeitsplatz',TO_TIMESTAMP('2023-10-19 21:19:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T20:19:17.496Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721609 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-19T20:19:17.497Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582772) 
;

-- 2023-10-19T20:19:17.499Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721609
;

-- 2023-10-19T20:19:17.500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721609)
;

-- Tab: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D)
-- UI Section: main
-- 2023-10-19T20:19:57.486Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547261,545856,TO_TIMESTAMP('2023-10-19 21:19:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-10-19 21:19:57','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-10-19T20:19:57.487Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545856 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main
-- UI Column: 10
-- 2023-10-19T20:20:02.776Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547141,545856,TO_TIMESTAMP('2023-10-19 21:20:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-10-19 21:20:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main
-- UI Column: 20
-- 2023-10-19T20:20:05.315Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547142,545856,TO_TIMESTAMP('2023-10-19 21:20:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-10-19 21:20:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 10
-- UI Element Group: main
-- 2023-10-19T20:20:37.362Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547141,551260,TO_TIMESTAMP('2023-10-19 21:20:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-10-19 21:20:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer.Arbeitsplatz
-- Column: C_Workplace_User_Assign.C_Workplace_ID
-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 10 -> main.Arbeitsplatz
-- Column: C_Workplace_User_Assign.C_Workplace_ID
-- 2023-10-19T20:21:03.661Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721609,0,547261,551260,621130,'F',TO_TIMESTAMP('2023-10-19 21:21:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Arbeitsplatz',10,0,0,TO_TIMESTAMP('2023-10-19 21:21:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer.Ansprechpartner
-- Column: C_Workplace_User_Assign.AD_User_ID
-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 10 -> main.Ansprechpartner
-- Column: C_Workplace_User_Assign.AD_User_ID
-- 2023-10-19T20:21:21.671Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721608,0,547261,551260,621131,'F',TO_TIMESTAMP('2023-10-19 21:21:21','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','N','N',0,'Ansprechpartner',20,0,0,TO_TIMESTAMP('2023-10-19 21:21:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 20
-- UI Element Group: flags
-- 2023-10-19T20:21:39.208Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547142,551261,TO_TIMESTAMP('2023-10-19 21:21:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-10-19 21:21:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer.Aktiv
-- Column: C_Workplace_User_Assign.IsActive
-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Workplace_User_Assign.IsActive
-- 2023-10-19T20:21:54.093Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721606,0,547261,551261,621132,'F',TO_TIMESTAMP('2023-10-19 21:21:53','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-10-19 21:21:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 20
-- UI Element Group: orgs
-- 2023-10-19T20:22:03.828Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547142,551262,TO_TIMESTAMP('2023-10-19 21:22:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','orgs',20,TO_TIMESTAMP('2023-10-19 21:22:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer.Sektion
-- Column: C_Workplace_User_Assign.AD_Org_ID
-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 20 -> orgs.Sektion
-- Column: C_Workplace_User_Assign.AD_Org_ID
-- 2023-10-19T20:22:24.459Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721605,0,547261,551262,621133,'F',TO_TIMESTAMP('2023-10-19 21:22:24','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2023-10-19 21:22:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer.Mandant
-- Column: C_Workplace_User_Assign.AD_Client_ID
-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 20 -> orgs.Mandant
-- Column: C_Workplace_User_Assign.AD_Client_ID
-- 2023-10-19T20:22:41.204Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721604,0,547261,551262,621134,'F',TO_TIMESTAMP('2023-10-19 21:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-10-19 21:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: Dem Arbeitsplatz zugewiesene Benutzer
-- Action Type: W
-- Window: Dem Arbeitsplatz zugewiesene Benutzer(541745,D)
-- 2023-10-19T20:25:33.597Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582773,542121,0,541745,TO_TIMESTAMP('2023-10-19 21:25:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Dem Arbeitsplatz zugewiesene Benutzer','Y','N','N','N','N','Dem Arbeitsplatz zugewiesene Benutzer',TO_TIMESTAMP('2023-10-19 21:25:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T20:25:33.599Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542121 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-10-19T20:25:33.600Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542121, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542121)
;

-- 2023-10-19T20:25:33.602Z
/* DDL */  select update_menu_translation_from_ad_element(582773) 
;

-- Reordering children of `Warehouse Management`
-- Node name: `Dem Arbeitsplatz zugewiesene Benutzer`
-- 2023-10-19T20:26:03.277Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542121 AND AD_Tree_ID=10
;

-- Node name: `Arbeitsplatz`
-- 2023-10-19T20:26:03.278Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Node name: `Warehouse (M_Warehouse)`
-- 2023-10-19T20:26:03.280Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type (M_Warehouse_Type)`
-- 2023-10-19T20:26:03.281Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move (M_Movement)`
-- 2023-10-19T20:26:03.282Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme (M_Inventory)`
-- 2023-10-19T20:26:03.283Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase (Fresh_QtyOnHand)`
-- 2023-10-19T20:26:03.284Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule (MD_Candidate)`
-- 2023-10-19T20:26:03.284Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast (M_Forecast)`
-- 2023-10-19T20:26:03.285Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit (MD_Cockpit)`
-- 2023-10-19T20:26:03.286Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-10-19T20:26:03.287Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-10-19T20:26:03.287Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-10-19T20:26:03.289Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory (M_Inventory)`
-- 2023-10-19T20:26:03.290Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting (M_InventoryLine)`
-- 2023-10-19T20:26:03.290Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate (M_Inventory_Candidate)`
-- 2023-10-19T20:26:03.291Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer.Arbeitsplatz
-- Column: C_Workplace_User_Assign.C_Workplace_ID
-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 10 -> main.Arbeitsplatz
-- Column: C_Workplace_User_Assign.C_Workplace_ID
-- 2023-10-23T14:42:08.782Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-23 15:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621130
;

-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer.Ansprechpartner
-- Column: C_Workplace_User_Assign.AD_User_ID
-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 10 -> main.Ansprechpartner
-- Column: C_Workplace_User_Assign.AD_User_ID
-- 2023-10-23T14:42:09.477Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-23 15:42:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621131
;

-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer.Aktiv
-- Column: C_Workplace_User_Assign.IsActive
-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Workplace_User_Assign.IsActive
-- 2023-10-23T14:42:10.169Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-23 15:42:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621132
;

-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer -> Dem Arbeitsplatz zugewiesene Benutzer.Sektion
-- Column: C_Workplace_User_Assign.AD_Org_ID
-- UI Element: Dem Arbeitsplatz zugewiesene Benutzer(541745,D) -> Dem Arbeitsplatz zugewiesene Benutzer(547261,D) -> main -> 20 -> orgs.Sektion
-- Column: C_Workplace_User_Assign.AD_Org_ID
-- 2023-10-23T14:42:10.871Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-23 15:42:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621133
;

