-- Window: Modular Contract Log Status, InternalName=null
-- 2023-08-31T14:26:18.096493500Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582662,0,541731,TO_TIMESTAMP('2023-08-31 17:26:17.647','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','Y','N','N','N','Y','Modular Contract Log Status','N',TO_TIMESTAMP('2023-08-31 17:26:17.647','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-08-31T14:26:18.112821200Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541731 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-08-31T14:26:18.142750600Z
/* DDL */  select update_window_translation_from_ad_element(582662) 
;

-- 2023-08-31T14:26:18.154793400Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541731
;

-- 2023-08-31T14:26:18.160968400Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541731)
;

-- Tab: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status
-- Table: ModCntr_Log_Status
-- 2023-08-31T14:26:54.816539600Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582662,0,547210,542363,541731,'Y',TO_TIMESTAMP('2023-08-31 17:26:54.636','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','N','A','ModCntr_Log_Status','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Modular Contract Log Status','N',10,0,TO_TIMESTAMP('2023-08-31 17:26:54.636','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:26:54.822986800Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547210 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-08-31T14:26:54.829451800Z
/* DDL */  select update_tab_translation_from_ad_element(582662) 
;

-- 2023-08-31T14:26:54.837353300Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547210)
;

-- Tab: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts)
-- UI Section: main
-- 2023-08-31T14:27:03.378728700Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547210,545804,TO_TIMESTAMP('2023-08-31 17:27:03.262','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-31 17:27:03.262','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-08-31T14:27:03.383838500Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545804 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main
-- UI Column: 10
-- 2023-08-31T14:27:03.525003400Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547068,545804,TO_TIMESTAMP('2023-08-31 17:27:03.434','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-31 17:27:03.434','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main
-- UI Column: 20
-- 2023-08-31T14:27:03.613872800Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547069,545804,TO_TIMESTAMP('2023-08-31 17:27:03.529','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-08-31 17:27:03.529','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 10
-- UI Element Group: default
-- 2023-08-31T14:27:03.743782600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547068,551121,TO_TIMESTAMP('2023-08-31 17:27:03.644','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-08-31 17:27:03.644','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Tab: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table
-- Table: C_Queue_WorkPackage_Log
-- 2023-08-31T14:27:55.587181600Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,551786,542699,0,547211,540646,541731,'Y',TO_TIMESTAMP('2023-08-31 17:27:55.217','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','N','A','C_Queue_WorkPackage_Log','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Workpackage audit/log table',587338,'N',20,1,TO_TIMESTAMP('2023-08-31 17:27:55.217','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:27:55.589406800Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547211 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-08-31T14:27:55.591442800Z
/* DDL */  select update_tab_translation_from_ad_element(542699) 
;

-- 2023-08-31T14:27:55.595542900Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547211)
;

-- Tab: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts)
-- UI Section: main
-- 2023-08-31T14:28:09.250040400Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547211,545805,TO_TIMESTAMP('2023-08-31 17:28:09.108','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-31 17:28:09.108','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-08-31T14:28:09.253113900Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545805 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> main
-- UI Column: 10
-- 2023-08-31T14:28:09.358107300Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547070,545805,TO_TIMESTAMP('2023-08-31 17:28:09.264','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-31 17:28:09.264','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> main -> 10
-- UI Element Group: default
-- 2023-08-31T14:28:09.454709700Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547070,551122,TO_TIMESTAMP('2023-08-31 17:28:09.364','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-08-31 17:28:09.364','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> Mandant
-- Column: ModCntr_Log_Status.AD_Client_ID
-- 2023-08-31T14:28:52.272864100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587330,720337,0,547210,TO_TIMESTAMP('2023-08-31 17:28:52.078','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.contracts','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-08-31 17:28:52.078','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:28:52.278642Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720337 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:28:52.281600900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-08-31T14:28:52.499545400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720337
;

-- 2023-08-31T14:28:52.500566600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720337)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> Organisation
-- Column: ModCntr_Log_Status.AD_Org_ID
-- 2023-08-31T14:28:52.616818200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587331,720338,0,547210,TO_TIMESTAMP('2023-08-31 17:28:52.502','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.contracts','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-08-31 17:28:52.502','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:28:52.618766700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720338 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:28:52.620768300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-08-31T14:28:52.777274700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720338
;

-- 2023-08-31T14:28:52.778318800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720338)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> Aktiv
-- Column: ModCntr_Log_Status.IsActive
-- 2023-08-31T14:28:52.892933400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587334,720339,0,547210,TO_TIMESTAMP('2023-08-31 17:28:52.779','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.contracts','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-08-31 17:28:52.779','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:28:52.894930Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720339 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:28:52.897447Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-08-31T14:28:53.024126600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720339
;

-- 2023-08-31T14:28:53.024894400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720339)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> Modular Contract Log Status
-- Column: ModCntr_Log_Status.ModCntr_Log_Status_ID
-- 2023-08-31T14:28:53.149635800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587337,720340,0,547210,TO_TIMESTAMP('2023-08-31 17:28:53.027','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Modular Contract Log Status',TO_TIMESTAMP('2023-08-31 17:28:53.027','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:28:53.151714800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720340 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:28:53.152639800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582662) 
;

-- 2023-08-31T14:28:53.157149100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720340
;

-- 2023-08-31T14:28:53.158165300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720340)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> Asynchrone Verarbeitungswarteschlange
-- Column: ModCntr_Log_Status.C_Queue_WorkPackage_ID
-- 2023-08-31T14:28:53.275849200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587338,720341,0,547210,TO_TIMESTAMP('2023-08-31 17:28:53.16','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Asynchrone Verarbeitungswarteschlange',TO_TIMESTAMP('2023-08-31 17:28:53.16','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:28:53.277569200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720341 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:28:53.279670400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541904) 
;

-- 2023-08-31T14:28:53.289742200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720341
;

-- 2023-08-31T14:28:53.289742200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720341)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> DB-Tabelle
-- Column: ModCntr_Log_Status.AD_Table_ID
-- 2023-08-31T14:28:53.402932200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587339,720342,0,547210,TO_TIMESTAMP('2023-08-31 17:28:53.292','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information',10,'de.metas.contracts','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2023-08-31 17:28:53.292','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:28:53.404869800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720342 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:28:53.406952300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2023-08-31T14:28:53.441636600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720342
;

-- 2023-08-31T14:28:53.442731300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720342)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> Datensatz-ID
-- Column: ModCntr_Log_Status.Record_ID
-- 2023-08-31T14:28:53.547054800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587340,720343,0,547210,TO_TIMESTAMP('2023-08-31 17:28:53.444','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID',10,'de.metas.contracts','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2023-08-31 17:28:53.444','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:28:53.549061Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720343 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:28:53.550176200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2023-08-31T14:28:53.561951400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720343
;

-- 2023-08-31T14:28:53.562974600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720343)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> Probleme
-- Column: ModCntr_Log_Status.AD_Issue_ID
-- 2023-08-31T14:28:53.674643100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587341,720344,0,547210,TO_TIMESTAMP('2023-08-31 17:28:53.564','YYYY-MM-DD HH24:MI:SS.US'),100,'',10,'de.metas.contracts','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2023-08-31 17:28:53.564','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:28:53.676691800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720344 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:28:53.678380400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2023-08-31T14:28:53.689668500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720344
;

-- 2023-08-31T14:28:53.690615600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720344)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> Status
-- Column: ModCntr_Log_Status.ProcessingStatus
-- 2023-08-31T14:28:53.794225100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587342,720345,0,547210,TO_TIMESTAMP('2023-08-31 17:28:53.691','YYYY-MM-DD HH24:MI:SS.US'),100,2,'de.metas.contracts','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2023-08-31 17:28:53.691','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:28:53.795269600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720345 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:28:53.797883600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582663) 
;

-- 2023-08-31T14:28:53.802973200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720345
;

-- 2023-08-31T14:28:53.803877800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720345)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> Mandant
-- Column: C_Queue_WorkPackage_Log.AD_Client_ID
-- 2023-08-31T14:29:04.829920600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551777,720346,0,547211,TO_TIMESTAMP('2023-08-31 17:29:04.708','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.contracts','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-08-31 17:29:04.708','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:29:04.831920Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720346 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:29:04.834918300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-08-31T14:29:04.932672Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720346
;

-- 2023-08-31T14:29:04.933694800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720346)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> Organisation
-- Column: C_Queue_WorkPackage_Log.AD_Org_ID
-- 2023-08-31T14:29:05.015149500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551778,720347,0,547211,TO_TIMESTAMP('2023-08-31 17:29:04.935','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.contracts','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-08-31 17:29:04.935','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:29:05.015671300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720347 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:29:05.016215400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-08-31T14:29:05.088432100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720347
;

-- 2023-08-31T14:29:05.088432100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720347)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> Aktiv
-- Column: C_Queue_WorkPackage_Log.IsActive
-- 2023-08-31T14:29:05.450692200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551781,720348,0,547211,TO_TIMESTAMP('2023-08-31 17:29:05.09','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.contracts','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-08-31 17:29:05.09','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:29:05.452692700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720348 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:29:05.454784700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-08-31T14:29:05.530507800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720348
;

-- 2023-08-31T14:29:05.531480300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720348)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> Workpackage audit/log table
-- Column: C_Queue_WorkPackage_Log.C_Queue_WorkPackage_Log_ID
-- 2023-08-31T14:29:05.624857Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551784,720349,0,547211,TO_TIMESTAMP('2023-08-31 17:29:05.533','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Workpackage audit/log table',TO_TIMESTAMP('2023-08-31 17:29:05.533','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:29:05.626811700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720349 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:29:05.628172600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542699) 
;

-- 2023-08-31T14:29:05.634593500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720349
;

-- 2023-08-31T14:29:05.634593500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720349)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> Message Text
-- Column: C_Queue_WorkPackage_Log.MsgText
-- 2023-08-31T14:29:05.717981700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551785,720350,0,547211,TO_TIMESTAMP('2023-08-31 17:29:05.637','YYYY-MM-DD HH24:MI:SS.US'),100,'Textual Informational, Menu or Error Message',1874919423,'de.metas.contracts','The Message Text indicates the message that will display ','Y','N','N','N','N','N','N','N','Message Text',TO_TIMESTAMP('2023-08-31 17:29:05.637','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:29:05.720025500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720350 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:29:05.721972800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(463) 
;

-- 2023-08-31T14:29:05.728665700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720350
;

-- 2023-08-31T14:29:05.729757100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720350)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> Asynchrone Verarbeitungswarteschlange
-- Column: C_Queue_WorkPackage_Log.C_Queue_WorkPackage_ID
-- 2023-08-31T14:29:05.809905200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551786,720351,0,547211,TO_TIMESTAMP('2023-08-31 17:29:05.731','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Asynchrone Verarbeitungswarteschlange',TO_TIMESTAMP('2023-08-31 17:29:05.731','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:29:05.812002100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720351 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:29:05.813988600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541904) 
;

-- 2023-08-31T14:29:05.819897900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720351
;

-- 2023-08-31T14:29:05.820969300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720351)
;

-- Field: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> Probleme
-- Column: C_Queue_WorkPackage_Log.AD_Issue_ID
-- 2023-08-31T14:29:05.914573500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558432,720352,0,547211,TO_TIMESTAMP('2023-08-31 17:29:05.822','YYYY-MM-DD HH24:MI:SS.US'),100,'',10,'de.metas.contracts','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2023-08-31 17:29:05.822','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T14:29:05.915422700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720352 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T14:29:05.919055100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2023-08-31T14:29:05.924982900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720352
;

-- 2023-08-31T14:29:05.926489300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720352)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 10 -> default.Status
-- Column: ModCntr_Log_Status.ProcessingStatus
-- 2023-08-31T14:30:10.443820400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720345,0,547210,620397,551121,'F',TO_TIMESTAMP('2023-08-31 17:30:10.305','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Status',10,0,0,TO_TIMESTAMP('2023-08-31 17:30:10.305','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 10 -> default.DB-Tabelle
-- Column: ModCntr_Log_Status.AD_Table_ID
-- 2023-08-31T14:30:21.921857700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720342,0,547210,620398,551121,'F',TO_TIMESTAMP('2023-08-31 17:30:21.776','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','N','N',0,'DB-Tabelle',20,0,0,TO_TIMESTAMP('2023-08-31 17:30:21.776','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 10 -> default.Datensatz-ID
-- Column: ModCntr_Log_Status.Record_ID
-- 2023-08-31T14:30:28.035134900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720343,0,547210,620399,551121,'F',TO_TIMESTAMP('2023-08-31 17:30:27.885','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','N','N',0,'Datensatz-ID',30,0,0,TO_TIMESTAMP('2023-08-31 17:30:27.885','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 10 -> default.Asynchrone Verarbeitungswarteschlange
-- Column: ModCntr_Log_Status.C_Queue_WorkPackage_ID
-- 2023-08-31T14:30:35.083160700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720341,0,547210,620400,551121,'F',TO_TIMESTAMP('2023-08-31 17:30:34.933','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Asynchrone Verarbeitungswarteschlange',40,0,0,TO_TIMESTAMP('2023-08-31 17:30:34.933','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 10
-- UI Element Group: details
-- 2023-08-31T14:30:55.360035600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547068,551123,TO_TIMESTAMP('2023-08-31 17:30:55.231','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','details',20,TO_TIMESTAMP('2023-08-31 17:30:55.231','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 10 -> details.Probleme
-- Column: ModCntr_Log_Status.AD_Issue_ID
-- 2023-08-31T14:31:08.599691400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720344,0,547210,620401,551123,'F',TO_TIMESTAMP('2023-08-31 17:31:08.428','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Probleme',10,0,0,TO_TIMESTAMP('2023-08-31 17:31:08.428','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 20
-- UI Element Group: flags
-- 2023-08-31T14:31:23.116507Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547069,551124,TO_TIMESTAMP('2023-08-31 17:31:22.973','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2023-08-31 17:31:22.973','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 20 -> flags.Aktiv
-- Column: ModCntr_Log_Status.IsActive
-- 2023-08-31T14:31:31.002011900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720339,0,547210,620402,551124,'F',TO_TIMESTAMP('2023-08-31 17:31:30.828','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-08-31 17:31:30.828','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 20
-- UI Element Group: org+client
-- 2023-08-31T14:31:43.922411600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547069,551125,TO_TIMESTAMP('2023-08-31 17:31:43.545','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org+client',20,TO_TIMESTAMP('2023-08-31 17:31:43.545','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 20 -> org+client.Organisation
-- Column: ModCntr_Log_Status.AD_Org_ID
-- 2023-08-31T14:32:16.873218700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720338,0,547210,620403,551125,'F',TO_TIMESTAMP('2023-08-31 17:32:16.549','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-08-31 17:32:16.549','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Modular Contract Log Status(547210,de.metas.contracts) -> main -> 20 -> org+client.Mandant
-- Column: ModCntr_Log_Status.AD_Client_ID
-- 2023-08-31T14:32:23.452929900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720337,0,547210,620404,551125,'F',TO_TIMESTAMP('2023-08-31 17:32:23.305','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-08-31 17:32:23.305','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> main -> 10 -> default.Asynchrone Verarbeitungswarteschlange
-- Column: C_Queue_WorkPackage_Log.C_Queue_WorkPackage_ID
-- 2023-08-31T14:35:57.982435800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720351,0,547211,620405,551122,'F',TO_TIMESTAMP('2023-08-31 17:35:57.845','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Asynchrone Verarbeitungswarteschlange',10,0,0,TO_TIMESTAMP('2023-08-31 17:35:57.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> main -> 10 -> default.Message Text
-- Column: C_Queue_WorkPackage_Log.MsgText
-- 2023-08-31T14:36:05.557288900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720350,0,547211,620406,551122,'F',TO_TIMESTAMP('2023-08-31 17:36:05.432','YYYY-MM-DD HH24:MI:SS.US'),100,'Textual Informational, Menu or Error Message','The Message Text indicates the message that will display ','Y','N','N','Y','N','N','N',0,'Message Text',20,0,0,TO_TIMESTAMP('2023-08-31 17:36:05.432','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Modular Contract Log Status(541731,de.metas.contracts) -> Workpackage audit/log table(547211,de.metas.contracts) -> main -> 10 -> default.Probleme
-- Column: C_Queue_WorkPackage_Log.AD_Issue_ID
-- 2023-08-31T14:36:14.418502200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720352,0,547211,620407,551122,'F',TO_TIMESTAMP('2023-08-31 17:36:14.299','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Probleme',30,0,0,TO_TIMESTAMP('2023-08-31 17:36:14.299','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: ModCntr_Log_Status.C_Queue_WorkPackage_ID
-- 2023-08-31T16:33:23.451101800Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540527, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-08-31 19:33:23.451','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587338
;

-- Name: Modular Contract Log Status
-- Action Type: W
-- Window: Modular Contract Log Status(541731,de.metas.contracts)
-- 2023-08-31T16:38:25.117218200Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582662,542111,0,541731,TO_TIMESTAMP('2023-08-31 19:38:24.005','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Modular Contract Log Status_541731','Y','N','N','N','N','Modular Contract Log Status',TO_TIMESTAMP('2023-08-31 19:38:24.005','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T16:38:25.125216800Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542111 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-08-31T16:38:25.138000900Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542111, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542111)
;

-- 2023-08-31T16:38:25.161025500Z
/* DDL */  select update_menu_translation_from_ad_element(582662) 
;

-- Reordering children of `Contract Management`
-- Node name: `Contractpartner (C_Flatrate_Data)`
-- 2023-08-31T16:38:33.469855100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Term)`
-- 2023-08-31T16:38:33.470859800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- Node name: `Contract Invoicecandidates (C_Invoice_Clearing_Alloc)`
-- 2023-08-31T16:38:33.472746800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- Node name: `Subscription History (C_SubscriptionProgress)`
-- 2023-08-31T16:38:33.472746800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- Node name: `Contract Terms (C_Flatrate_Conditions)`
-- 2023-08-31T16:38:33.473843900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Transition)`
-- 2023-08-31T16:38:33.475279400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- Node name: `Contract Module Type (ModCntr_Type)`
-- 2023-08-31T16:38:33.476335800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542086 AND AD_Tree_ID=10
;

-- Node name: `Subscriptions import (I_Flatrate_Term)`
-- 2023-08-31T16:38:33.477853800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Log (ModCntr_Log)`
-- 2023-08-31T16:38:33.478860700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542087 AND AD_Tree_ID=10
;

-- Node name: `Membership Month (C_MembershipMonth)`
-- 2023-08-31T16:38:33.480062300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- Node name: `Abo-Rabatt (C_SubscrDiscount)`
-- 2023-08-31T16:38:33.481188Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541766 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Flatrate Term (C_InterimInvoice_FlatrateTerm)`
-- 2023-08-31T16:38:33.481409Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541979 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-08-31T16:38:33.483635600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Settings (C_Interim_Invoice_Settings)`
-- 2023-08-31T16:38:33.484652200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541974 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-08-31T16:38:33.486165900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- Node name: `Type specific settings`
-- 2023-08-31T16:38:33.488188300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- Node name: `Create/Update Customer Retentions (de.metas.contracts.process.C_Customer_Retention_CreateUpdate)`
-- 2023-08-31T16:38:33.489204Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- Node name: `Call-off order overview (C_CallOrderSummary)`
-- 2023-08-31T16:38:33.491203900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541909 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Settings (ModCntr_Settings)`
-- 2023-08-31T16:38:33.493205300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542088 AND AD_Tree_ID=10
;

-- Node name: `Invoice Group (ModCntr_InvoicingGroup)`
-- 2023-08-31T16:38:33.495205100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542106 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Log Status`
-- 2023-08-31T16:38:33.497741700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542111 AND AD_Tree_ID=10
;

-- 2023-08-31T16:43:31.628138300Z
UPDATE AD_Column SET AD_Reference_ID=28 WHERE AD_Column_ID=587340


;-- Table: ModCntr_Log_Status
-- 2023-08-31T16:59:32.432580400Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2023-08-31 19:59:32.431','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542363
;

-- Column: ModCntr_Log_Status.AD_Table_ID
-- 2023-08-31T17:00:15.134457900Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540750, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-08-31 20:00:15.134','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587339
;

-- Column: ModCntr_Log_Status.AD_Table_ID
-- 2023-08-31T17:00:28.800550500Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-31 20:00:28.8','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587339
;

-- Column: ModCntr_Log_Status.Record_ID
-- 2023-08-31T17:00:42.046917100Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-31 20:00:42.046','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587340
;

-- Column: ModCntr_Log_Status.ProcessingStatus
-- 2023-08-31T17:00:50.234762800Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-31 20:00:50.234','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587342
;

-- Column: ModCntr_Log_Status.AD_Table_ID
-- 2023-08-31T17:04:11.124207600Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-08-31 20:04:11.124','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587339
;

-- Column: ModCntr_Log_Status.Record_ID
-- 2023-08-31T17:11:18.214990400Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2023-08-31 20:11:18.214','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587340
;

-- Element: ModCntr_Log_Status_ID
-- 2023-08-31T17:29:50.416186Z
UPDATE AD_Element_Trl SET Name='Modularer Log-Erstellungsstatus', PrintName='Modularer Log-Erstellungsstatus',Updated=TO_TIMESTAMP('2023-08-31 20:29:50.416','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582662 AND AD_Language='de_CH'
;

-- 2023-08-31T17:29:50.516240800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582662,'de_CH')
;

-- Element: ModCntr_Log_Status_ID
-- 2023-08-31T17:29:53.620201500Z
UPDATE AD_Element_Trl SET Name='Modularer Log-Erstellungsstatus', PrintName='Modularer Log-Erstellungsstatus',Updated=TO_TIMESTAMP('2023-08-31 20:29:53.619','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582662 AND AD_Language='de_DE'
;

-- 2023-08-31T17:29:53.621563700Z
UPDATE AD_Element SET Name='Modularer Log-Erstellungsstatus', PrintName='Modularer Log-Erstellungsstatus' WHERE AD_Element_ID=582662
;

-- 2023-08-31T17:29:54.567045100Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582662,'de_DE')
;

-- 2023-08-31T17:29:54.574566900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582662,'de_DE')
;

-- Element: ModCntr_Log_Status_ID
-- 2023-08-31T17:29:59.576605200Z
UPDATE AD_Element_Trl SET Name='Modularer Log-Erstellungsstatus', PrintName='Modularer Log-Erstellungsstatus',Updated=TO_TIMESTAMP('2023-08-31 20:29:59.576','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582662 AND AD_Language='fr_CH'
;

-- 2023-08-31T17:29:59.579273900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582662,'fr_CH')
;

