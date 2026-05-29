-- Window: Weighting Specifications, InternalName=null
-- 2022-11-10T13:40:35.280Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581666,0,541629,TO_TIMESTAMP('2022-11-10 15:40:35.093','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','Y','N','N','N','N','N','N','Y','Weighting Specifications','N',TO_TIMESTAMP('2022-11-10 15:40:35.093','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2022-11-10T13:40:35.282Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541629 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-11-10T13:40:35.293Z
/* DDL */  select update_window_translation_from_ad_element(581666) 
;

-- 2022-11-10T13:40:35.379Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541629
;

-- 2022-11-10T13:40:35.381Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541629)
;

-- Tab: Weighting Specifications -> Weighting Specifications
-- Table: PP_Weighting_Spec
-- 2022-11-10T13:41:24.506Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581666,0,546668,542256,541629,'Y',TO_TIMESTAMP('2022-11-10 15:41:24.337','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','N','N','A','PP_Weighting_Spec','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Weighting Specifications','N',10,0,TO_TIMESTAMP('2022-11-10 15:41:24.337','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:24.508Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546668 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-10T13:41:24.510Z
/* DDL */  select update_tab_translation_from_ad_element(581666) 
;

-- 2022-11-10T13:41:24.513Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546668)
;

-- Field: Weighting Specifications -> Weighting Specifications -> Mandant
-- Column: PP_Weighting_Spec.AD_Client_ID
-- 2022-11-10T13:41:28.145Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584919,707992,0,546668,TO_TIMESTAMP('2022-11-10 15:41:28.028','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-11-10 15:41:28.028','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:28.147Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707992 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:41:28.149Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-10T13:41:28.256Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707992
;

-- 2022-11-10T13:41:28.257Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707992)
;

-- Field: Weighting Specifications -> Weighting Specifications -> Sektion
-- Column: PP_Weighting_Spec.AD_Org_ID
-- 2022-11-10T13:41:28.373Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584920,707993,0,546668,TO_TIMESTAMP('2022-11-10 15:41:28.261','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-11-10 15:41:28.261','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:28.375Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707993 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:41:28.377Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-10T13:41:28.548Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707993
;

-- 2022-11-10T13:41:28.550Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707993)
;

-- Field: Weighting Specifications -> Weighting Specifications -> Aktiv
-- Column: PP_Weighting_Spec.IsActive
-- 2022-11-10T13:41:28.673Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584923,707994,0,546668,TO_TIMESTAMP('2022-11-10 15:41:28.553','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-11-10 15:41:28.553','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:28.675Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707994 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:41:28.676Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-10T13:41:28.812Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707994
;

-- 2022-11-10T13:41:28.814Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707994)
;

-- Field: Weighting Specifications -> Weighting Specifications -> Weighting Specifications
-- Column: PP_Weighting_Spec.PP_Weighting_Spec_ID
-- 2022-11-10T13:41:28.925Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584926,707995,0,546668,TO_TIMESTAMP('2022-11-10 15:41:28.817','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Weighting Specifications',TO_TIMESTAMP('2022-11-10 15:41:28.817','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:28.927Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707995 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:41:28.929Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581666) 
;

-- 2022-11-10T13:41:28.931Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707995
;

-- 2022-11-10T13:41:28.932Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707995)
;

-- Field: Weighting Specifications -> Weighting Specifications -> Name
-- Column: PP_Weighting_Spec.Name
-- 2022-11-10T13:41:29.041Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584927,707996,0,546668,TO_TIMESTAMP('2022-11-10 15:41:28.935','YYYY-MM-DD HH24:MI:SS.US'),100,'',40,'EE01','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2022-11-10 15:41:28.935','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:29.043Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707996 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:41:29.044Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-11-10T13:41:29.072Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707996
;

-- 2022-11-10T13:41:29.074Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707996)
;

-- Field: Weighting Specifications -> Weighting Specifications -> Required Weight Checks
-- Column: PP_Weighting_Spec.WeightChecksRequired
-- 2022-11-10T13:41:29.181Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584928,707997,0,546668,TO_TIMESTAMP('2022-11-10 15:41:29.077','YYYY-MM-DD HH24:MI:SS.US'),100,14,'EE01','Y','N','N','N','N','N','N','N','Required Weight Checks',TO_TIMESTAMP('2022-11-10 15:41:29.077','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:29.183Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:41:29.186Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581662) 
;

-- 2022-11-10T13:41:29.190Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707997
;

-- 2022-11-10T13:41:29.192Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707997)
;

-- Field: Weighting Specifications -> Weighting Specifications -> Toleranz %
-- Column: PP_Weighting_Spec.Tolerance_Perc
-- 2022-11-10T13:41:29.302Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584929,707998,0,546668,TO_TIMESTAMP('2022-11-10 15:41:29.196','YYYY-MM-DD HH24:MI:SS.US'),100,14,'EE01','Y','N','N','N','N','N','N','N','Toleranz %',TO_TIMESTAMP('2022-11-10 15:41:29.196','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:29.304Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:41:29.306Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580593) 
;

-- 2022-11-10T13:41:29.307Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707998
;

-- 2022-11-10T13:41:29.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707998)
;

-- Field: Weighting Specifications -> Weighting Specifications -> Beschreibung
-- Column: PP_Weighting_Spec.Description
-- 2022-11-10T13:41:29.409Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584932,707999,0,546668,TO_TIMESTAMP('2022-11-10 15:41:29.312','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'EE01','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-11-10 15:41:29.312','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:29.411Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:41:29.412Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-11-10T13:41:29.441Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707999
;

-- 2022-11-10T13:41:29.442Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707999)
;

-- 2022-11-10T13:41:43.763Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546668,545293,TO_TIMESTAMP('2022-11-10 15:41:43.613','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2022-11-10 15:41:43.613','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2022-11-10T13:41:43.765Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545293 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-11-10T13:41:43.933Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546441,545293,TO_TIMESTAMP('2022-11-10 15:41:43.822','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2022-11-10 15:41:43.822','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:44.028Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546442,545293,TO_TIMESTAMP('2022-11-10 15:41:43.935','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2022-11-10 15:41:43.935','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:41:44.189Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546441,550004,TO_TIMESTAMP('2022-11-10 15:41:44.084','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-11-10 15:41:44.084','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Name
-- Column: PP_Weighting_Spec.Name
-- 2022-11-10T13:42:35.670Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707996,0,546668,550004,613413,'F',TO_TIMESTAMP('2022-11-10 15:42:35.502','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2022-11-10 15:42:35.502','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Beschreibung
-- Column: PP_Weighting_Spec.Description
-- 2022-11-10T13:43:15.901Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707999,0,546668,550004,613414,'F',TO_TIMESTAMP('2022-11-10 15:43:15.745','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2022-11-10 15:43:15.745','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:43:27.708Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546441,550005,TO_TIMESTAMP('2022-11-10 15:43:27.55','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','params',20,TO_TIMESTAMP('2022-11-10 15:43:27.55','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Required Weight Checks
-- Column: PP_Weighting_Spec.WeightChecksRequired
-- 2022-11-10T13:43:38.296Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707997,0,546668,550005,613415,'F',TO_TIMESTAMP('2022-11-10 15:43:38.142','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Required Weight Checks',10,0,0,TO_TIMESTAMP('2022-11-10 15:43:38.142','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Toleranz %
-- Column: PP_Weighting_Spec.Tolerance_Perc
-- 2022-11-10T13:43:47.681Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707998,0,546668,550005,613416,'F',TO_TIMESTAMP('2022-11-10 15:43:47.523','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Toleranz %',20,0,0,TO_TIMESTAMP('2022-11-10 15:43:47.523','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:43:58.608Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546442,550006,TO_TIMESTAMP('2022-11-10 15:43:58.455','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2022-11-10 15:43:58.455','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Aktiv
-- Column: PP_Weighting_Spec.IsActive
-- 2022-11-10T13:44:09.889Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707994,0,546668,550006,613417,'F',TO_TIMESTAMP('2022-11-10 15:44:09.735','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2022-11-10 15:44:09.735','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:44:20.881Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546442,550007,TO_TIMESTAMP('2022-11-10 15:44:20.729','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org&client',20,TO_TIMESTAMP('2022-11-10 15:44:20.729','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Sektion
-- Column: PP_Weighting_Spec.AD_Org_ID
-- 2022-11-10T13:44:34.908Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707993,0,546668,550007,613418,'F',TO_TIMESTAMP('2022-11-10 15:44:33.747','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2022-11-10 15:44:33.747','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Mandant
-- Column: PP_Weighting_Spec.AD_Client_ID
-- 2022-11-10T13:44:41.218Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707992,0,546668,550007,613419,'F',TO_TIMESTAMP('2022-11-10 15:44:41.056','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-11-10 15:44:41.056','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Name
-- Column: PP_Weighting_Spec.Name
-- 2022-11-10T13:45:04.125Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-10 15:45:04.125','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613413
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Required Weight Checks
-- Column: PP_Weighting_Spec.WeightChecksRequired
-- 2022-11-10T13:45:04.135Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-10 15:45:04.134','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613415
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Toleranz %
-- Column: PP_Weighting_Spec.Tolerance_Perc
-- 2022-11-10T13:45:04.143Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-10 15:45:04.143','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613416
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Aktiv
-- Column: PP_Weighting_Spec.IsActive
-- 2022-11-10T13:45:04.154Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-10 15:45:04.154','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613417
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Beschreibung
-- Column: PP_Weighting_Spec.Description
-- 2022-11-10T13:45:04.166Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-11-10 15:45:04.165','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613414
;

-- Window: Weighting Run, InternalName=null
-- 2022-11-10T13:45:32.955Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581658,0,541630,TO_TIMESTAMP('2022-11-10 15:45:31.8','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','Y','N','N','N','N','N','N','Y','Weighting Run','N',TO_TIMESTAMP('2022-11-10 15:45:31.8','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2022-11-10T13:45:32.957Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541630 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-11-10T13:45:32.959Z
/* DDL */  select update_window_translation_from_ad_element(581658) 
;

-- 2022-11-10T13:45:32.961Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541630
;

-- 2022-11-10T13:45:32.962Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541630)
;

-- Window: Weighting Specifications, InternalName=ppWeightingSpec
-- 2022-11-10T13:45:48.271Z
UPDATE AD_Window SET InternalName='ppWeightingSpec',Updated=TO_TIMESTAMP('2022-11-10 15:45:48.27','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541629
;

-- Window: Weighting Run, InternalName=ppOrderWeightingRun
-- 2022-11-10T13:45:57.758Z
UPDATE AD_Window SET InternalName='ppOrderWeightingRun',Updated=TO_TIMESTAMP('2022-11-10 15:45:57.755','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541630
;

-- Tab: Weighting Run -> Weighting Run
-- Table: PP_Order_Weighting_Run
-- 2022-11-10T13:46:12.583Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581658,0,546669,542254,541630,'Y',TO_TIMESTAMP('2022-11-10 15:46:12.426','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','N','N','A','PP_Order_Weighting_Run','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Weighting Run','N',10,0,TO_TIMESTAMP('2022-11-10 15:46:12.426','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:12.584Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546669 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-10T13:46:12.586Z
/* DDL */  select update_tab_translation_from_ad_element(581658) 
;

-- 2022-11-10T13:46:12.588Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546669)
;

-- Field: Weighting Run -> Weighting Run -> Mandant
-- Column: PP_Order_Weighting_Run.AD_Client_ID
-- 2022-11-10T13:46:23.122Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584887,708000,0,546669,TO_TIMESTAMP('2022-11-10 15:46:22.957','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-11-10 15:46:22.957','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:23.124Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:23.125Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-10T13:46:23.226Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708000
;

-- 2022-11-10T13:46:23.227Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708000)
;

-- Field: Weighting Run -> Weighting Run -> Sektion
-- Column: PP_Order_Weighting_Run.AD_Org_ID
-- 2022-11-10T13:46:23.347Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584888,708001,0,546669,TO_TIMESTAMP('2022-11-10 15:46:23.23','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-11-10 15:46:23.23','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:23.366Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:23.368Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-10T13:46:23.478Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708001
;

-- 2022-11-10T13:46:23.479Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708001)
;

-- Field: Weighting Run -> Weighting Run -> Aktiv
-- Column: PP_Order_Weighting_Run.IsActive
-- 2022-11-10T13:46:23.590Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584891,708002,0,546669,TO_TIMESTAMP('2022-11-10 15:46:23.482','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-11-10 15:46:23.482','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:23.592Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:23.593Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-10T13:46:23.697Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708002
;

-- 2022-11-10T13:46:23.699Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708002)
;

-- Field: Weighting Run -> Weighting Run -> Weighting Run
-- Column: PP_Order_Weighting_Run.PP_Order_Weighting_Run_ID
-- 2022-11-10T13:46:23.806Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584894,708003,0,546669,TO_TIMESTAMP('2022-11-10 15:46:23.701','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Weighting Run',TO_TIMESTAMP('2022-11-10 15:46:23.701','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:23.807Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708003 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:23.808Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581658) 
;

-- 2022-11-10T13:46:23.810Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708003
;

-- 2022-11-10T13:46:23.811Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708003)
;

-- Field: Weighting Run -> Weighting Run -> Produktionsauftrag
-- Column: PP_Order_Weighting_Run.PP_Order_ID
-- 2022-11-10T13:46:23.920Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584895,708004,0,546669,TO_TIMESTAMP('2022-11-10 15:46:23.814','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Produktionsauftrag',TO_TIMESTAMP('2022-11-10 15:46:23.814','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:23.921Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:23.923Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53276) 
;

-- 2022-11-10T13:46:23.925Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708004
;

-- 2022-11-10T13:46:23.926Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708004)
;

-- Field: Weighting Run -> Weighting Run -> Manufacturing Order BOM Line
-- Column: PP_Order_Weighting_Run.PP_Order_BOMLine_ID
-- 2022-11-10T13:46:24.031Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584896,708005,0,546669,TO_TIMESTAMP('2022-11-10 15:46:23.928','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Manufacturing Order BOM Line',TO_TIMESTAMP('2022-11-10 15:46:23.928','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:24.032Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:24.033Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53275) 
;

-- 2022-11-10T13:46:24.036Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708005
;

-- 2022-11-10T13:46:24.037Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708005)
;

-- Field: Weighting Run -> Weighting Run -> Produkt
-- Column: PP_Order_Weighting_Run.M_Product_ID
-- 2022-11-10T13:46:24.154Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584897,708006,0,546669,TO_TIMESTAMP('2022-11-10 15:46:24.04','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'EE01','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2022-11-10 15:46:24.04','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:24.155Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:24.157Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-11-10T13:46:24.178Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708006
;

-- 2022-11-10T13:46:24.179Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708006)
;

-- Field: Weighting Run -> Weighting Run -> Belegdatum
-- Column: PP_Order_Weighting_Run.DateDoc
-- 2022-11-10T13:46:24.311Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584898,708007,0,546669,TO_TIMESTAMP('2022-11-10 15:46:24.182','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum des Belegs',7,'EE01','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','N','N','N','N','N','N','Belegdatum',TO_TIMESTAMP('2022-11-10 15:46:24.182','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:24.312Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708007 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:24.314Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(265) 
;

-- 2022-11-10T13:46:24.315Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708007
;

-- 2022-11-10T13:46:24.317Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708007)
;

-- Field: Weighting Run -> Weighting Run -> Verarbeitet
-- Column: PP_Order_Weighting_Run.Processed
-- 2022-11-10T13:46:24.426Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584899,708008,0,546669,TO_TIMESTAMP('2022-11-10 15:46:24.319','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'EE01','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2022-11-10 15:46:24.319','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:24.427Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:24.428Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2022-11-10T13:46:24.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708008
;

-- 2022-11-10T13:46:24.432Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708008)
;

-- Field: Weighting Run -> Weighting Run -> Zielgewicht
-- Column: PP_Order_Weighting_Run.TargetWeight
-- 2022-11-10T13:46:24.530Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584900,708009,0,546669,TO_TIMESTAMP('2022-11-10 15:46:24.434','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Zielgewicht',TO_TIMESTAMP('2022-11-10 15:46:24.434','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:24.532Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:24.533Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581664) 
;

-- 2022-11-10T13:46:24.534Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708009
;

-- 2022-11-10T13:46:24.535Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708009)
;

-- Field: Weighting Run -> Weighting Run -> Toleranz %
-- Column: PP_Order_Weighting_Run.Tolerance_Perc
-- 2022-11-10T13:46:24.643Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584901,708010,0,546669,TO_TIMESTAMP('2022-11-10 15:46:24.537','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Toleranz %',TO_TIMESTAMP('2022-11-10 15:46:24.537','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:24.644Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:24.646Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580593) 
;

-- 2022-11-10T13:46:24.647Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708010
;

-- 2022-11-10T13:46:24.647Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708010)
;

-- Field: Weighting Run -> Weighting Run -> Tolerance Excheeded
-- Column: PP_Order_Weighting_Run.IsToleranceExceeded
-- 2022-11-10T13:46:24.755Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584902,708011,0,546669,TO_TIMESTAMP('2022-11-10 15:46:24.649','YYYY-MM-DD HH24:MI:SS.US'),100,1,'EE01','Y','N','N','N','N','N','N','N','Tolerance Excheeded',TO_TIMESTAMP('2022-11-10 15:46:24.649','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:24.757Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:24.758Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581659) 
;

-- 2022-11-10T13:46:24.758Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708011
;

-- 2022-11-10T13:46:24.759Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708011)
;

-- Field: Weighting Run -> Weighting Run -> Min. Gewicht
-- Column: PP_Order_Weighting_Run.MinWeight
-- 2022-11-10T13:46:24.868Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584903,708012,0,546669,TO_TIMESTAMP('2022-11-10 15:46:24.761','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Min. Gewicht',TO_TIMESTAMP('2022-11-10 15:46:24.761','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:24.869Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:24.870Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581665) 
;

-- 2022-11-10T13:46:24.871Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708012
;

-- 2022-11-10T13:46:24.872Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708012)
;

-- Field: Weighting Run -> Weighting Run -> Max. Gewicht
-- Column: PP_Order_Weighting_Run.MaxWeight
-- 2022-11-10T13:46:24.977Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584904,708013,0,546669,TO_TIMESTAMP('2022-11-10 15:46:24.874','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Max. Gewicht',TO_TIMESTAMP('2022-11-10 15:46:24.874','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:24.978Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:24.979Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540039) 
;

-- 2022-11-10T13:46:24.980Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708013
;

-- 2022-11-10T13:46:24.981Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708013)
;

-- Field: Weighting Run -> Weighting Run -> Required Weight Checks
-- Column: PP_Order_Weighting_Run.WeightChecksRequired
-- 2022-11-10T13:46:25.078Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584905,708014,0,546669,TO_TIMESTAMP('2022-11-10 15:46:24.983','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Required Weight Checks',TO_TIMESTAMP('2022-11-10 15:46:24.983','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:25.079Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708014 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:25.080Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581662) 
;

-- 2022-11-10T13:46:25.082Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708014
;

-- 2022-11-10T13:46:25.083Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708014)
;

-- Field: Weighting Run -> Weighting Run -> Maßeinheit
-- Column: PP_Order_Weighting_Run.C_UOM_ID
-- 2022-11-10T13:46:25.190Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584918,708015,0,546669,TO_TIMESTAMP('2022-11-10 15:46:25.085','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit',10,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2022-11-10 15:46:25.085','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:25.191Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708015 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:25.193Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2022-11-10T13:46:25.201Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708015
;

-- 2022-11-10T13:46:25.202Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708015)
;

-- Field: Weighting Run -> Weighting Run -> Weighting Specifications
-- Column: PP_Order_Weighting_Run.PP_Weighting_Spec_ID
-- 2022-11-10T13:46:25.298Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584930,708016,0,546669,TO_TIMESTAMP('2022-11-10 15:46:25.204','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Weighting Specifications',TO_TIMESTAMP('2022-11-10 15:46:25.204','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:25.300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708016 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:25.301Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581666) 
;

-- 2022-11-10T13:46:25.302Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708016
;

-- 2022-11-10T13:46:25.303Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708016)
;

-- Field: Weighting Run -> Weighting Run -> Beschreibung
-- Column: PP_Order_Weighting_Run.Description
-- 2022-11-10T13:46:25.406Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584933,708017,0,546669,TO_TIMESTAMP('2022-11-10 15:46:25.305','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'EE01','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-11-10 15:46:25.305','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:46:25.407Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708017 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:46:25.409Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-11-10T13:46:25.437Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708017
;

-- 2022-11-10T13:46:25.439Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708017)
;

-- Tab: Weighting Run -> Wighting Check
-- Table: PP_Order_Weighting_RunCheck
-- 2022-11-10T13:47:04.783Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584914,581663,0,546670,542255,541630,'Y',TO_TIMESTAMP('2022-11-10 15:47:04.626','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','N','N','A','PP_Order_Weighting_RunCheck','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Wighting Check',584894,'N',20,1,TO_TIMESTAMP('2022-11-10 15:47:04.626','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:04.785Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546670 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-10T13:47:04.788Z
/* DDL */  select update_tab_translation_from_ad_element(581663) 
;

-- 2022-11-10T13:47:04.790Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546670)
;

-- Field: Weighting Run -> Wighting Check -> Mandant
-- Column: PP_Order_Weighting_RunCheck.AD_Client_ID
-- 2022-11-10T13:47:14.641Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584906,708018,0,546670,TO_TIMESTAMP('2022-11-10 15:47:14.472','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-11-10 15:47:14.472','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:14.643Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708018 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:47:14.644Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-10T13:47:14.734Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708018
;

-- 2022-11-10T13:47:14.736Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708018)
;

-- Field: Weighting Run -> Wighting Check -> Sektion
-- Column: PP_Order_Weighting_RunCheck.AD_Org_ID
-- 2022-11-10T13:47:14.859Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584907,708019,0,546670,TO_TIMESTAMP('2022-11-10 15:47:14.739','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-11-10 15:47:14.739','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:14.861Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708019 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:47:14.862Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-10T13:47:14.938Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708019
;

-- 2022-11-10T13:47:14.940Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708019)
;

-- Field: Weighting Run -> Wighting Check -> Aktiv
-- Column: PP_Order_Weighting_RunCheck.IsActive
-- 2022-11-10T13:47:15.047Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584910,708020,0,546670,TO_TIMESTAMP('2022-11-10 15:47:14.942','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-11-10 15:47:14.942','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:15.048Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:47:15.050Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-10T13:47:15.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708020
;

-- 2022-11-10T13:47:15.135Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708020)
;

-- Field: Weighting Run -> Wighting Check -> Wighting Check
-- Column: PP_Order_Weighting_RunCheck.PP_Order_Weighting_RunCheck_ID
-- 2022-11-10T13:47:15.255Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584913,708021,0,546670,TO_TIMESTAMP('2022-11-10 15:47:15.137','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Wighting Check',TO_TIMESTAMP('2022-11-10 15:47:15.137','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:15.256Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:47:15.257Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581663) 
;

-- 2022-11-10T13:47:15.259Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708021
;

-- 2022-11-10T13:47:15.260Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708021)
;

-- Field: Weighting Run -> Wighting Check -> Weighting Run
-- Column: PP_Order_Weighting_RunCheck.PP_Order_Weighting_Run_ID
-- 2022-11-10T13:47:15.368Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584914,708022,0,546670,TO_TIMESTAMP('2022-11-10 15:47:15.262','YYYY-MM-DD HH24:MI:SS.US'),100,10,'EE01','Y','N','N','N','N','N','N','N','Weighting Run',TO_TIMESTAMP('2022-11-10 15:47:15.262','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:15.369Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:47:15.370Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581658) 
;

-- 2022-11-10T13:47:15.372Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708022
;

-- 2022-11-10T13:47:15.373Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708022)
;

-- Field: Weighting Run -> Wighting Check -> Gewicht
-- Column: PP_Order_Weighting_RunCheck.Weight
-- 2022-11-10T13:47:15.499Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584915,708023,0,546670,TO_TIMESTAMP('2022-11-10 15:47:15.375','YYYY-MM-DD HH24:MI:SS.US'),100,'Gewicht eines Produktes',10,'EE01','The Weight indicates the weight  of the product in the Weight UOM of the Client','Y','N','N','N','N','N','N','N','Gewicht',TO_TIMESTAMP('2022-11-10 15:47:15.375','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:15.500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:47:15.502Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(629) 
;

-- 2022-11-10T13:47:15.505Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708023
;

-- 2022-11-10T13:47:15.506Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708023)
;

-- Field: Weighting Run -> Wighting Check -> Zeile Nr.
-- Column: PP_Order_Weighting_RunCheck.Line
-- 2022-11-10T13:47:15.613Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584916,708024,0,546670,TO_TIMESTAMP('2022-11-10 15:47:15.509','YYYY-MM-DD HH24:MI:SS.US'),100,'Einzelne Zeile in dem Dokument',10,'EE01','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','N','N','N','N','N','Zeile Nr.',TO_TIMESTAMP('2022-11-10 15:47:15.509','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:15.614Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708024 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:47:15.615Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(439) 
;

-- 2022-11-10T13:47:15.617Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708024
;

-- 2022-11-10T13:47:15.618Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708024)
;

-- Field: Weighting Run -> Wighting Check -> Maßeinheit
-- Column: PP_Order_Weighting_RunCheck.C_UOM_ID
-- 2022-11-10T13:47:15.757Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584917,708025,0,546670,TO_TIMESTAMP('2022-11-10 15:47:15.621','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit',10,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2022-11-10 15:47:15.621','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:15.760Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708025 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:47:15.761Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2022-11-10T13:47:15.771Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708025
;

-- 2022-11-10T13:47:15.772Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708025)
;

-- Field: Weighting Run -> Wighting Check -> Beschreibung
-- Column: PP_Order_Weighting_RunCheck.Description
-- 2022-11-10T13:47:15.876Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584931,708026,0,546670,TO_TIMESTAMP('2022-11-10 15:47:15.775','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'EE01','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-11-10 15:47:15.775','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:15.877Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-10T13:47:15.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-11-10T13:47:15.904Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708026
;

-- 2022-11-10T13:47:15.906Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708026)
;

-- Field: Weighting Run -> Wighting Check -> Zeile Nr.
-- Column: PP_Order_Weighting_RunCheck.Line
-- 2022-11-10T13:47:27.551Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2022-11-10 15:47:27.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708024
;

-- 2022-11-10T13:47:44.189Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546669,545294,TO_TIMESTAMP('2022-11-10 15:47:44.033','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2022-11-10 15:47:44.033','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2022-11-10T13:47:44.190Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545294 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-11-10T13:47:44.305Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546443,545294,TO_TIMESTAMP('2022-11-10 15:47:44.193','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2022-11-10 15:47:44.193','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:44.405Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546444,545294,TO_TIMESTAMP('2022-11-10 15:47:44.307','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2022-11-10 15:47:44.307','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:44.507Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546443,550008,TO_TIMESTAMP('2022-11-10 15:47:44.407','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-11-10 15:47:44.407','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:44.617Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546670,545295,TO_TIMESTAMP('2022-11-10 15:47:44.514','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2022-11-10 15:47:44.514','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2022-11-10T13:47:44.618Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545295 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-11-10T13:47:44.709Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546445,545295,TO_TIMESTAMP('2022-11-10 15:47:44.62','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2022-11-10 15:47:44.62','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:47:44.808Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546445,550009,TO_TIMESTAMP('2022-11-10 15:47:44.712','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-11-10 15:47:44.712','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Produktionsauftrag
-- Column: PP_Order_Weighting_Run.PP_Order_ID
-- 2022-11-10T13:48:38.245Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708004,0,546669,550008,613420,'F',TO_TIMESTAMP('2022-11-10 15:48:38.089','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Produktionsauftrag',10,0,0,TO_TIMESTAMP('2022-11-10 15:48:38.089','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Weighting Specifications
-- Column: PP_Order_Weighting_Run.PP_Weighting_Spec_ID
-- 2022-11-10T13:48:53.401Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708016,0,546669,550008,613421,'F',TO_TIMESTAMP('2022-11-10 15:48:53.25','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Weighting Specifications',20,0,0,TO_TIMESTAMP('2022-11-10 15:48:53.25','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Beschreibung
-- Column: PP_Order_Weighting_Run.Description
-- 2022-11-10T13:49:18.139Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708017,0,546669,550008,613422,'F',TO_TIMESTAMP('2022-11-10 15:49:17.977','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Beschreibung',30,0,0,TO_TIMESTAMP('2022-11-10 15:49:17.977','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:49:28.778Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546443,550010,TO_TIMESTAMP('2022-11-10 15:49:28.633','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','spec',20,TO_TIMESTAMP('2022-11-10 15:49:28.633','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Weighting Specifications
-- Column: PP_Order_Weighting_Run.PP_Weighting_Spec_ID
-- 2022-11-10T13:49:40.959Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550010, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-11-10 15:49:40.959','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613421
;

-- UI Element: Weighting Run -> Weighting Run.Required Weight Checks
-- Column: PP_Order_Weighting_Run.WeightChecksRequired
-- 2022-11-10T13:49:49.469Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708014,0,546669,550010,613423,'F',TO_TIMESTAMP('2022-11-10 15:49:49.319','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Required Weight Checks',20,0,0,TO_TIMESTAMP('2022-11-10 15:49:49.319','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Toleranz %
-- Column: PP_Order_Weighting_Run.Tolerance_Perc
-- 2022-11-10T13:50:05.446Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708010,0,546669,550010,613424,'F',TO_TIMESTAMP('2022-11-10 15:50:05.283','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Toleranz %',30,0,0,TO_TIMESTAMP('2022-11-10 15:50:05.283','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:50:46.742Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546443,550011,TO_TIMESTAMP('2022-11-10 15:50:46.573','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','qtys',30,TO_TIMESTAMP('2022-11-10 15:50:46.573','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Zielgewicht
-- Column: PP_Order_Weighting_Run.TargetWeight
-- 2022-11-10T13:50:59.773Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708009,0,546669,550011,613425,'F',TO_TIMESTAMP('2022-11-10 15:50:59.617','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Zielgewicht',10,0,0,TO_TIMESTAMP('2022-11-10 15:50:59.617','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Min. Gewicht
-- Column: PP_Order_Weighting_Run.MinWeight
-- 2022-11-10T13:51:07.091Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708012,0,546669,550011,613426,'F',TO_TIMESTAMP('2022-11-10 15:51:06.939','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Min. Gewicht',20,0,0,TO_TIMESTAMP('2022-11-10 15:51:06.939','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Max. Gewicht
-- Column: PP_Order_Weighting_Run.MaxWeight
-- 2022-11-10T13:51:22.715Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708013,0,546669,550011,613427,'F',TO_TIMESTAMP('2022-11-10 15:51:22.543','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Max. Gewicht',30,0,0,TO_TIMESTAMP('2022-11-10 15:51:22.543','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Maßeinheit
-- Column: PP_Order_Weighting_Run.C_UOM_ID
-- 2022-11-10T13:51:31.103Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708015,0,546669,550011,613428,'F',TO_TIMESTAMP('2022-11-10 15:51:30.947','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',40,0,0,TO_TIMESTAMP('2022-11-10 15:51:30.947','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:52:03.336Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546444,550012,TO_TIMESTAMP('2022-11-10 15:52:03.178','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2022-11-10 15:52:03.178','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Verarbeitet
-- Column: PP_Order_Weighting_Run.Processed
-- 2022-11-10T13:52:16.393Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708008,0,546669,550012,613429,'F',TO_TIMESTAMP('2022-11-10 15:52:16.241','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2022-11-10 15:52:16.241','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Tolerance Excheeded
-- Column: PP_Order_Weighting_Run.IsToleranceExceeded
-- 2022-11-10T13:52:23.746Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708011,0,546669,550012,613430,'F',TO_TIMESTAMP('2022-11-10 15:52:23.588','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Tolerance Excheeded',20,0,0,TO_TIMESTAMP('2022-11-10 15:52:23.588','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:52:37.159Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546444,550013,TO_TIMESTAMP('2022-11-10 15:52:36.997','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org&client',20,TO_TIMESTAMP('2022-11-10 15:52:36.997','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Sektion
-- Column: PP_Order_Weighting_Run.AD_Org_ID
-- 2022-11-10T13:52:53.232Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708001,0,546669,550013,613431,'F',TO_TIMESTAMP('2022-11-10 15:52:53.068','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2022-11-10 15:52:53.068','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Mandant
-- Column: PP_Order_Weighting_Run.AD_Client_ID
-- 2022-11-10T13:53:01.582Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708000,0,546669,550013,613432,'F',TO_TIMESTAMP('2022-11-10 15:53:01.426','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-11-10 15:53:01.426','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Belegdatum
-- Column: PP_Order_Weighting_Run.DateDoc
-- 2022-11-10T13:53:37.531Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708007,0,546669,550008,613433,'F',TO_TIMESTAMP('2022-11-10 15:53:37.373','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum des Belegs','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','Y','N','N','Belegdatum',40,0,0,TO_TIMESTAMP('2022-11-10 15:53:37.373','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Belegdatum
-- Column: PP_Order_Weighting_Run.DateDoc
-- 2022-11-10T13:53:53.180Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-11-10 15:53:53.18','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613433
;

-- 2022-11-10T13:54:00.288Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546443,550014,TO_TIMESTAMP('2022-11-10 15:54:00.134','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','product',40,TO_TIMESTAMP('2022-11-10 15:54:00.134','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Manufacturing Order BOM Line
-- Column: PP_Order_Weighting_Run.PP_Order_BOMLine_ID
-- 2022-11-10T13:54:10.693Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708005,0,546669,550014,613434,'F',TO_TIMESTAMP('2022-11-10 15:54:10.544','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Manufacturing Order BOM Line',10,0,0,TO_TIMESTAMP('2022-11-10 15:54:10.544','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Weighting Run.Produkt
-- Column: PP_Order_Weighting_Run.M_Product_ID
-- 2022-11-10T13:54:17.915Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708006,0,546669,550014,613435,'F',TO_TIMESTAMP('2022-11-10 15:54:17.758','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',20,0,0,TO_TIMESTAMP('2022-11-10 15:54:17.758','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T13:54:31.384Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2022-11-10 15:54:31.382','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550014
;

-- 2022-11-10T13:54:34.589Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2022-11-10 15:54:34.586','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550011
;

-- UI Element: Weighting Run -> Weighting Run.Produktionsauftrag
-- Column: PP_Order_Weighting_Run.PP_Order_ID
-- 2022-11-10T13:55:18.424Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-10 15:55:18.424','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613420
;

-- UI Element: Weighting Run -> Weighting Run.Belegdatum
-- Column: PP_Order_Weighting_Run.DateDoc
-- 2022-11-10T13:55:18.429Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-10 15:55:18.429','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613433
;

-- UI Element: Weighting Run -> Weighting Run.Produkt
-- Column: PP_Order_Weighting_Run.M_Product_ID
-- 2022-11-10T13:55:18.434Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-10 15:55:18.434','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613435
;

-- UI Element: Weighting Run -> Weighting Run.Tolerance Excheeded
-- Column: PP_Order_Weighting_Run.IsToleranceExceeded
-- 2022-11-10T13:55:18.439Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-10 15:55:18.439','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613430
;

-- UI Element: Weighting Run -> Weighting Run.Verarbeitet
-- Column: PP_Order_Weighting_Run.Processed
-- 2022-11-10T13:55:18.443Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-11-10 15:55:18.443','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613429
;

-- Field: Weighting Run -> Weighting Run -> Produkt
-- Column: PP_Order_Weighting_Run.M_Product_ID
-- 2022-11-10T13:55:45.648Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-10 15:55:45.648','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708006
;

-- Field: Weighting Run -> Weighting Run -> Verarbeitet
-- Column: PP_Order_Weighting_Run.Processed
-- 2022-11-10T13:55:48.971Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-10 15:55:48.971','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708008
;

-- Field: Weighting Run -> Weighting Run -> Zielgewicht
-- Column: PP_Order_Weighting_Run.TargetWeight
-- 2022-11-10T13:55:51.302Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-10 15:55:51.302','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708009
;

-- Field: Weighting Run -> Weighting Run -> Toleranz %
-- Column: PP_Order_Weighting_Run.Tolerance_Perc
-- 2022-11-10T13:55:58.131Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-10 15:55:58.131','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708010
;

-- Field: Weighting Run -> Weighting Run -> Tolerance Excheeded
-- Column: PP_Order_Weighting_Run.IsToleranceExceeded
-- 2022-11-10T13:56:00.171Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-10 15:56:00.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708011
;

-- Field: Weighting Run -> Weighting Run -> Min. Gewicht
-- Column: PP_Order_Weighting_Run.MinWeight
-- 2022-11-10T13:56:01.645Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-10 15:56:01.645','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708012
;

-- Field: Weighting Run -> Weighting Run -> Max. Gewicht
-- Column: PP_Order_Weighting_Run.MaxWeight
-- 2022-11-10T13:56:02.852Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-10 15:56:02.852','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708013
;

-- Field: Weighting Run -> Weighting Run -> Required Weight Checks
-- Column: PP_Order_Weighting_Run.WeightChecksRequired
-- 2022-11-10T13:56:04.590Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-10 15:56:04.59','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708014
;

-- Field: Weighting Run -> Weighting Run -> Maßeinheit
-- Column: PP_Order_Weighting_Run.C_UOM_ID
-- 2022-11-10T13:56:07.681Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-10 15:56:07.681','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708015
;

-- Table: PP_Order_Weighting_Run
-- 2022-11-10T13:56:38.626Z
UPDATE AD_Table SET AD_Window_ID=541630,Updated=TO_TIMESTAMP('2022-11-10 15:56:38.624','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542254
;

-- Table: PP_Order_Weighting_RunCheck
-- 2022-11-10T13:57:04.504Z
UPDATE AD_Table SET AD_Window_ID=541630,Updated=TO_TIMESTAMP('2022-11-10 15:57:04.501','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542255
;

-- Table: PP_Weighting_Spec
-- 2022-11-10T13:57:09.745Z
UPDATE AD_Table SET AD_Window_ID=541629,Updated=TO_TIMESTAMP('2022-11-10 15:57:09.743','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542256
;

-- 2022-11-10T15:02:31.477Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581666,542019,0,541629,TO_TIMESTAMP('2022-11-10 17:02:31.24','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','ppWeightingSpec','Y','N','N','N','N','Weighting Specifications',TO_TIMESTAMP('2022-11-10 17:02:31.24','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T15:02:31.479Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542019 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-11-10T15:02:31.482Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542019, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542019)
;

-- 2022-11-10T15:02:31.508Z
/* DDL */  select update_menu_translation_from_ad_element(581666) 
;

-- Reordering children of `Standard Costing Management`
-- Node name: `Frozen/UnFrozen Cost (org.eevolution.process.FrozenUnFrozenCost)`
-- 2022-11-10T15:02:32.159Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53199 AND AD_Tree_ID=10
;

-- Node name: `Create Doc Type to Manufacturing (org.eevolution.process.CreateDocType)`
-- 2022-11-10T15:02:32.161Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53075 AND AD_Tree_ID=10
;

-- Node name: `Create Element (org.eevolution.process.CreateCostElement)`
-- 2022-11-10T15:02:32.163Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53076 AND AD_Tree_ID=10
;

-- Node name: `Copy Price to Standard Cost (org.eevolution.process.CopyPriceToStandard)`
-- 2022-11-10T15:02:32.164Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53078 AND AD_Tree_ID=10
;

-- Node name: `Product Costing`
-- 2022-11-10T15:02:32.166Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53077 AND AD_Tree_ID=10
;

-- Node name: `Workflow Cost Roll-Up (org.eevolution.process.RollupWorkflow)`
-- 2022-11-10T15:02:32.167Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53079 AND AD_Tree_ID=10
;

-- Node name: `Cost Workflow & Process Details`
-- 2022-11-10T15:02:32.169Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53080 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material & Formula Cost Roll-UP (org.eevolution.process.RollupBillOfMaterial)`
-- 2022-11-10T15:02:32.170Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53081 AND AD_Tree_ID=10
;

-- Node name: `Cost BOM Multi Level Review (org.eevolution.report.CostBillOfMaterial)`
-- 2022-11-10T15:02:32.172Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53198 AND AD_Tree_ID=10
;

-- Node name: `Cost Collector (PP_Cost_Collector)`
-- 2022-11-10T15:02:32.173Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53082 AND AD_Tree_ID=10
;

-- Node name: `Weighting Specifications`
-- 2022-11-10T15:02:32.175Z
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542019 AND AD_Tree_ID=10
;

-- Reordering children of `Manufacturing`
-- Node name: `Manufacturing candidate (PP_Order_Candidate)`
-- 2022-11-10T15:02:43.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541831 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Order (PP_Order)`
-- 2022-11-10T15:02:43.591Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000081 AND AD_Tree_ID=10
;

-- Node name: `Cost Collector (PP_Cost_Collector)`
-- 2022-11-10T15:02:43.593Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541397 AND AD_Tree_ID=10
;

-- Node name: `Weighting Specifications`
-- 2022-11-10T15:02:43.595Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542019 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-11-10T15:02:43.596Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000055 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-11-10T15:02:43.599Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000063 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-11-10T15:02:43.600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000071 AND AD_Tree_ID=10
;

-- 2022-11-10T15:03:04.281Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581658,542020,0,541630,TO_TIMESTAMP('2022-11-10 17:03:04.153','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','ppOrderWeightingRun','Y','N','N','N','N','Weighting Run',TO_TIMESTAMP('2022-11-10 17:03:04.153','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-10T15:03:04.284Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542020 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-11-10T15:03:04.287Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542020, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542020)
;

-- 2022-11-10T15:03:04.290Z
/* DDL */  select update_menu_translation_from_ad_element(581658) 
;

-- Reordering children of `Manufacturing`
-- Node name: `Manufacturing candidate (PP_Order_Candidate)`
-- 2022-11-10T15:03:12.601Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541831 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Order (PP_Order)`
-- 2022-11-10T15:03:12.602Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000081 AND AD_Tree_ID=10
;

-- Node name: `Cost Collector (PP_Cost_Collector)`
-- 2022-11-10T15:03:12.604Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541397 AND AD_Tree_ID=10
;

-- Node name: `Weighting Specifications`
-- 2022-11-10T15:03:12.605Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542019 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-11-10T15:03:12.606Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000055 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-11-10T15:03:12.608Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000063 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-11-10T15:03:12.610Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000071 AND AD_Tree_ID=10
;

-- Node name: `Weighting Run`
-- 2022-11-10T15:03:12.611Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542020 AND AD_Tree_ID=10
;

-- Reordering children of `Manufacturing`
-- Node name: `Manufacturing candidate (PP_Order_Candidate)`
-- 2022-11-10T15:03:16.441Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541831 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Order (PP_Order)`
-- 2022-11-10T15:03:16.443Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000081 AND AD_Tree_ID=10
;

-- Node name: `Cost Collector (PP_Cost_Collector)`
-- 2022-11-10T15:03:16.444Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541397 AND AD_Tree_ID=10
;

-- Node name: `Weighting Specifications`
-- 2022-11-10T15:03:16.445Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542019 AND AD_Tree_ID=10
;

-- Node name: `Weighting Run`
-- 2022-11-10T15:03:16.447Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542020 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-11-10T15:03:16.448Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000055 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-11-10T15:03:16.449Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000063 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-11-10T15:03:16.450Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000071 AND AD_Tree_ID=10
;

-- UI Element: Weighting Run -> Wighting Check.Zeile Nr.
-- Column: PP_Order_Weighting_RunCheck.Line
-- 2022-11-10T17:24:22.832Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708024,0,546670,550009,613436,'F',TO_TIMESTAMP('2022-11-10 19:24:22.608','YYYY-MM-DD HH24:MI:SS.US'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','Zeile Nr.',10,0,0,TO_TIMESTAMP('2022-11-10 19:24:22.608','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Wighting Check.Gewicht
-- Column: PP_Order_Weighting_RunCheck.Weight
-- 2022-11-10T17:24:32.057Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708023,0,546670,550009,613437,'F',TO_TIMESTAMP('2022-11-10 19:24:31.922','YYYY-MM-DD HH24:MI:SS.US'),100,'Gewicht eines Produktes','The Weight indicates the weight  of the product in the Weight UOM of the Client','Y','N','Y','N','N','Gewicht',20,0,0,TO_TIMESTAMP('2022-11-10 19:24:31.922','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Wighting Check.Maßeinheit
-- Column: PP_Order_Weighting_RunCheck.C_UOM_ID
-- 2022-11-10T17:24:39.665Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708025,0,546670,550009,613438,'F',TO_TIMESTAMP('2022-11-10 19:24:39.529','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',30,0,0,TO_TIMESTAMP('2022-11-10 19:24:39.529','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Wighting Check.Beschreibung
-- Column: PP_Order_Weighting_RunCheck.Description
-- 2022-11-10T17:24:49.333Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708026,0,546670,550009,613439,'F',TO_TIMESTAMP('2022-11-10 19:24:49.184','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Beschreibung',40,0,0,TO_TIMESTAMP('2022-11-10 19:24:49.184','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Wighting Check.Zeile Nr.
-- Column: PP_Order_Weighting_RunCheck.Line
-- 2022-11-10T17:25:04.426Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-10 19:25:04.426','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613436
;

-- UI Element: Weighting Run -> Wighting Check.Gewicht
-- Column: PP_Order_Weighting_RunCheck.Weight
-- 2022-11-10T17:25:04.434Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-10 19:25:04.433','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613437
;

-- UI Element: Weighting Run -> Wighting Check.Maßeinheit
-- Column: PP_Order_Weighting_RunCheck.C_UOM_ID
-- 2022-11-10T17:25:04.439Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-10 19:25:04.439','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613438
;

-- UI Element: Weighting Run -> Wighting Check.Beschreibung
-- Column: PP_Order_Weighting_RunCheck.Description
-- 2022-11-10T17:25:04.445Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-10 19:25:04.445','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613439
;

-- UI Element: Weighting Run -> Wighting Check.Maßeinheit
-- Column: PP_Order_Weighting_RunCheck.C_UOM_ID
-- 2022-11-10T17:27:11.833Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2022-11-10 19:27:11.833','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613438
;

-- UI Element: Weighting Run -> Wighting Check.Zeile Nr.
-- Column: PP_Order_Weighting_RunCheck.Line
-- 2022-11-10T17:27:18.758Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2022-11-10 19:27:18.758','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613436
;

