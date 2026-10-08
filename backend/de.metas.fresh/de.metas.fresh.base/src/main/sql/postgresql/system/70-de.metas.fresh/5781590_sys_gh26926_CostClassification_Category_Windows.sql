-- Run mode: SWING_CLIENT

-- Element: C_CostClassification_Category_ID
-- 2025-12-16T19:27:44.484Z
UPDATE AD_Element_Trl SET Description='Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-16 19:27:44.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584374 AND AD_Language='de_DE'
;

-- 2025-12-16T19:27:44.571Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-16T19:27:49.760Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584374,'de_DE')
;

-- 2025-12-16T19:27:49.831Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584374,'de_DE')
;

-- Element: C_CostClassification_Category_ID
-- 2025-12-16T19:28:06.122Z
UPDATE AD_Element_Trl SET Description='Groups cost classifications into higher-level analytical categories to support structured and aggregated controlling reports.',Updated=TO_TIMESTAMP('2025-12-16 19:28:06.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584374 AND AD_Language='en_US'
;

-- 2025-12-16T19:28:06.194Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-16T19:28:12.188Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584374,'en_US')
;

-- Window: Kostenartengruppe, InternalName=null
-- 2025-12-16T19:28:30.438Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584374,0,541998,TO_TIMESTAMP('2025-12-16 19:28:29.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.','U','Y','N','N','N','N','N','N','Y','Kostenartengruppe','N',TO_TIMESTAMP('2025-12-16 19:28:29.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-12-16T19:28:30.511Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541998 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-12-16T19:28:30.660Z
/* DDL */  select update_window_translation_from_ad_element(584374)
;

-- 2025-12-16T19:28:30.737Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541998
;

-- 2025-12-16T19:28:30.813Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541998)
;

-- Window: Kostenartengruppe, InternalName=null
-- 2025-12-16T19:28:54.548Z
UPDATE AD_Window SET EntityType='D',Updated=TO_TIMESTAMP('2025-12-16 19:28:54.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541998
;

-- Tab: Kostenartengruppe(541998,D) -> Kostenartengruppe
-- Table: C_CostClassification_Category
-- 2025-12-16T19:30:04.840Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584374,0,548691,542565,541998,'Y',TO_TIMESTAMP('2025-12-16 19:30:03.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.','D','N','N','A','C_CostClassification_Category','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Kostenartengruppe','N',10,0,TO_TIMESTAMP('2025-12-16 19:30:03.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:30:04.914Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548691 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-12-16T19:30:04.991Z
/* DDL */  select update_tab_translation_from_ad_element(584374)
;

-- 2025-12-16T19:30:05.067Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548691)
;

-- Field: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> Mandant
-- Column: C_CostClassification_Category.AD_Client_ID
-- 2025-12-16T19:30:29.523Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591761,760924,0,548691,TO_TIMESTAMP('2025-12-16 19:30:28.778000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-12-16 19:30:28.778000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:30:29.596Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760924 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:30:29.672Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-12-16T19:30:30.050Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760924
;

-- 2025-12-16T19:30:30.121Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760924)
;

-- Field: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> Sektion
-- Column: C_CostClassification_Category.AD_Org_ID
-- 2025-12-16T19:30:30.925Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591762,760925,0,548691,TO_TIMESTAMP('2025-12-16 19:30:30.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-12-16 19:30:30.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:30:31.015Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760925 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:30:31.093Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-12-16T19:30:31.366Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760925
;

-- 2025-12-16T19:30:31.442Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760925)
;

-- Field: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> Aktiv
-- Column: C_CostClassification_Category.IsActive
-- 2025-12-16T19:30:32.210Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591765,760926,0,548691,TO_TIMESTAMP('2025-12-16 19:30:31.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-12-16 19:30:31.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:30:32.285Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760926 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:30:32.356Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-12-16T19:30:32.672Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760926
;

-- 2025-12-16T19:30:32.745Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760926)
;

-- Field: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> Kostenartengruppe
-- Column: C_CostClassification_Category.C_CostClassification_Category_ID
-- 2025-12-16T19:30:33.529Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591768,760927,0,548691,TO_TIMESTAMP('2025-12-16 19:30:32.891000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.',10,'D','Y','N','N','N','N','N','N','N','Kostenartengruppe',TO_TIMESTAMP('2025-12-16 19:30:32.891000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:30:33.600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760927 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:30:33.674Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584374)
;

-- 2025-12-16T19:30:33.749Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760927
;

-- 2025-12-16T19:30:33.820Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760927)
;



-- Field: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> Name
-- Column: C_CostClassification_Category.Name
-- 2025-12-16T19:36:17.038Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591781,760928,0,548691,0,TO_TIMESTAMP('2025-12-16 19:36:15.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',0,'D',0,'',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Name',0,0,10,0,1,1,TO_TIMESTAMP('2025-12-16 19:36:15.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:36:17.125Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760928 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:36:17.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2025-12-16T19:36:17.370Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760928
;

-- 2025-12-16T19:36:17.453Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760928)
;

-- Field: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> Beschreibung
-- Column: C_CostClassification_Category.Description
-- 2025-12-16T19:36:43.297Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591782,760929,0,548691,0,TO_TIMESTAMP('2025-12-16 19:36:42.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Beschreibung',0,0,20,0,1,1,TO_TIMESTAMP('2025-12-16 19:36:42.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:36:43.387Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760929 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:36:43.476Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-12-16T19:36:43.610Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760929
;

-- 2025-12-16T19:36:43.690Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760929)
;

-- Tab: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D)
-- UI Section: main
-- 2025-12-16T19:37:45.951Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548691,547197,TO_TIMESTAMP('2025-12-16 19:37:45.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-12-16 19:37:45.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-12-16T19:37:46.049Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547197 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main
-- UI Column: 10
-- 2025-12-16T19:37:57.793Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548778,547197,TO_TIMESTAMP('2025-12-16 19:37:57.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-12-16 19:37:57.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 10
-- UI Element Group: default
-- 2025-12-16T19:38:37.761Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548778,554101,TO_TIMESTAMP('2025-12-16 19:38:37.166000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-12-16 19:38:37.166000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 10
-- UI Element Group: description
-- 2025-12-16T19:38:54.898Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548778,554102,TO_TIMESTAMP('2025-12-16 19:38:54.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','description',20,TO_TIMESTAMP('2025-12-16 19:38:54.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 10 -> default.Name
-- Column: C_CostClassification_Category.Name
-- 2025-12-16T19:39:24.671Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760928,0,548691,554101,641265,'F',TO_TIMESTAMP('2025-12-16 19:39:23.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2025-12-16 19:39:23.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 10 -> description.Beschreibung
-- Column: C_CostClassification_Category.Description
-- 2025-12-16T19:40:09.946Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760929,0,548691,554102,641266,'F',TO_TIMESTAMP('2025-12-16 19:40:09.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2025-12-16 19:40:09.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main
-- UI Column: 20
-- 2025-12-16T19:40:43.958Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548779,547197,TO_TIMESTAMP('2025-12-16 19:40:43.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-12-16 19:40:43.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 20
-- UI Element Group: flags
-- 2025-12-16T19:41:10.289Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548779,554103,TO_TIMESTAMP('2025-12-16 19:41:09.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-12-16 19:41:09.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 20 -> flags.Aktiv
-- Column: C_CostClassification_Category.IsActive
-- 2025-12-16T19:41:38.407Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760926,0,548691,554103,641267,'F',TO_TIMESTAMP('2025-12-16 19:41:37.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-12-16 19:41:37.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 20
-- UI Element Group: org
-- 2025-12-16T19:42:06.655Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548779,554104,TO_TIMESTAMP('2025-12-16 19:42:06.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-12-16 19:42:06.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 10 -> default.Name
-- Column: C_CostClassification_Category.Name
-- 2025-12-16T19:42:18.958Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-12-16 19:42:18.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641265
;

-- UI Element: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 10 -> description.Beschreibung
-- Column: C_CostClassification_Category.Description
-- 2025-12-16T19:42:19.415Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-12-16 19:42:19.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641266
;

-- 2025-12-16T19:44:41.551Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584375,0,TO_TIMESTAMP('2025-12-16 19:44:41.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Kostenartengruppe Übersetzung','Kostenartengruppe Übersetzung',TO_TIMESTAMP('2025-12-16 19:44:41.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:44:41.629Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584375 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Kostenartengruppe Übersetzung, InternalName=null
-- 2025-12-16T19:45:06.291Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584375,0,541999,TO_TIMESTAMP('2025-12-16 19:45:05.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Kostenartengruppe Übersetzung','N',TO_TIMESTAMP('2025-12-16 19:45:05.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-12-16T19:45:06.368Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541999 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-12-16T19:45:06.523Z
/* DDL */  select update_window_translation_from_ad_element(584375)
;

-- 2025-12-16T19:45:06.608Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541999
;

-- 2025-12-16T19:45:06.698Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541999)
;

-- Tab: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung
-- Table: C_CostClassification_Category_Trl
-- 2025-12-16T19:47:10.947Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584375,0,548692,542566,541999,'Y',TO_TIMESTAMP('2025-12-16 19:47:10.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','C_CostClassification_Category_Trl','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Kostenartengruppe Übersetzung','N',10,0,TO_TIMESTAMP('2025-12-16 19:47:10.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:47:11.021Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548692 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-12-16T19:47:11.097Z
/* DDL */  select update_tab_translation_from_ad_element(584375)
;

-- 2025-12-16T19:47:11.168Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548692)
;

-- Field: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> Mandant
-- Column: C_CostClassification_Category_Trl.AD_Client_ID
-- 2025-12-16T19:47:32.576Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591769,760930,0,548692,TO_TIMESTAMP('2025-12-16 19:47:31.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-12-16 19:47:31.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:47:32.653Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760930 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:47:32.726Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-12-16T19:47:32.905Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760930
;

-- 2025-12-16T19:47:32.975Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760930)
;

-- Field: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> Sprache
-- Column: C_CostClassification_Category_Trl.AD_Language
-- 2025-12-16T19:47:33.760Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591770,760931,0,548692,TO_TIMESTAMP('2025-12-16 19:47:33.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sprache für diesen Eintrag',6,'D','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','N','N','N','N','N','Sprache',TO_TIMESTAMP('2025-12-16 19:47:33.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:47:33.834Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760931 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:47:33.907Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(109)
;

-- 2025-12-16T19:47:33.995Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760931
;

-- 2025-12-16T19:47:34.068Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760931)
;

-- Field: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> Sektion
-- Column: C_CostClassification_Category_Trl.AD_Org_ID
-- 2025-12-16T19:47:34.862Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591771,760932,0,548692,TO_TIMESTAMP('2025-12-16 19:47:34.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-12-16 19:47:34.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:47:34.936Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760932 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:47:35.016Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-12-16T19:47:35.211Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760932
;

-- 2025-12-16T19:47:35.285Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760932)
;

-- Field: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> Kostenartengruppe
-- Column: C_CostClassification_Category_Trl.C_CostClassification_Category_ID
-- 2025-12-16T19:47:36.074Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591772,760933,0,548692,TO_TIMESTAMP('2025-12-16 19:47:35.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.',10,'D','Y','N','N','N','N','N','N','N','Kostenartengruppe',TO_TIMESTAMP('2025-12-16 19:47:35.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:47:36.147Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:47:36.222Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584374)
;

-- 2025-12-16T19:47:36.301Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760933
;

-- 2025-12-16T19:47:36.376Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760933)
;

-- Field: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> Beschreibung
-- Column: C_CostClassification_Category_Trl.Description
-- 2025-12-16T19:47:37.156Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591775,760934,0,548692,TO_TIMESTAMP('2025-12-16 19:47:36.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2025-12-16 19:47:36.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:47:37.230Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760934 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:47:37.303Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-12-16T19:47:37.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760934
;

-- 2025-12-16T19:47:37.505Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760934)
;

-- Field: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> Aktiv
-- Column: C_CostClassification_Category_Trl.IsActive
-- 2025-12-16T19:47:38.295Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591776,760935,0,548692,TO_TIMESTAMP('2025-12-16 19:47:37.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-12-16 19:47:37.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:47:38.371Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760935 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:47:38.450Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-12-16T19:47:38.648Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760935
;

-- 2025-12-16T19:47:38.729Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760935)
;

-- Field: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> Übersetzt
-- Column: C_CostClassification_Category_Trl.IsTranslated
-- 2025-12-16T19:47:39.509Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591777,760936,0,548692,TO_TIMESTAMP('2025-12-16 19:47:38.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Diese Spalte ist übersetzt',1,'D','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist','Y','N','N','N','N','N','N','N','Übersetzt',TO_TIMESTAMP('2025-12-16 19:47:38.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:47:39.583Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760936 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:47:39.659Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(420)
;

-- 2025-12-16T19:47:39.744Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760936
;

-- 2025-12-16T19:47:39.814Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760936)
;

-- Field: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> Name
-- Column: C_CostClassification_Category_Trl.Name
-- 2025-12-16T19:47:40.600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591778,760937,0,548692,TO_TIMESTAMP('2025-12-16 19:47:39.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',60,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2025-12-16 19:47:39.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T19:47:40.675Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760937 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T19:47:40.748Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2025-12-16T19:47:40.875Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760937
;

-- 2025-12-16T19:47:40.947Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760937)
;

-- Tab: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D)
-- UI Section: main
-- 2025-12-16T19:48:44.113Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548692,547198,TO_TIMESTAMP('2025-12-16 19:48:43.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-12-16 19:48:43.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-12-16T19:48:44.187Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547198 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main
-- UI Column: 10
-- 2025-12-16T19:49:08.853Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548780,547198,TO_TIMESTAMP('2025-12-16 19:49:08.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-12-16 19:49:08.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main
-- UI Column: 20
-- 2025-12-16T19:49:16.064Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548781,547198,TO_TIMESTAMP('2025-12-16 19:49:15.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-12-16 19:49:15.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 10
-- UI Element Group: default
-- 2025-12-16T19:50:26.781Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548780,554105,TO_TIMESTAMP('2025-12-16 19:50:26.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-12-16 19:50:26.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 10
-- UI Element Group: description
-- 2025-12-16T19:50:38.823Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548780,554106,TO_TIMESTAMP('2025-12-16 19:50:38.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','description',20,TO_TIMESTAMP('2025-12-16 19:50:38.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 10 -> default.Sprache
-- Column: C_CostClassification_Category_Trl.AD_Language
-- 2025-12-16T19:51:56.076Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760931,0,548692,554105,641268,'F',TO_TIMESTAMP('2025-12-16 19:51:55.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','Y','N','N','N',0,'Sprache',10,0,0,TO_TIMESTAMP('2025-12-16 19:51:55.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 10 -> default.Name
-- Column: C_CostClassification_Category_Trl.Name
-- 2025-12-16T19:52:14.378Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760937,0,548692,554105,641269,'F',TO_TIMESTAMP('2025-12-16 19:52:13.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2025-12-16 19:52:13.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 10 -> description.Beschreibung
-- Column: C_CostClassification_Category_Trl.Description
-- 2025-12-16T19:52:52.477Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760934,0,548692,554106,641270,'F',TO_TIMESTAMP('2025-12-16 19:52:51.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2025-12-16 19:52:51.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 20
-- UI Element Group: flags
-- 2025-12-16T19:53:14.974Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548781,554107,TO_TIMESTAMP('2025-12-16 19:53:14.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-12-16 19:53:14.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 20 -> flags.Aktiv
-- Column: C_CostClassification_Category_Trl.IsActive
-- 2025-12-16T19:53:50.261Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760935,0,548692,554107,641271,'F',TO_TIMESTAMP('2025-12-16 19:53:49.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-12-16 19:53:49.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 20 -> flags.Übersetzt
-- Column: C_CostClassification_Category_Trl.IsTranslated
-- 2025-12-16T19:54:20.512Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760936,0,548692,554107,641272,'F',TO_TIMESTAMP('2025-12-16 19:54:19.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Diese Spalte ist übersetzt','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist','Y','N','N','Y','N','N','N',0,'Übersetzt',20,0,0,TO_TIMESTAMP('2025-12-16 19:54:19.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 20
-- UI Element Group: org
-- 2025-12-16T19:54:40.376Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548781,554108,TO_TIMESTAMP('2025-12-16 19:54:39.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-12-16 19:54:39.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 20 -> org.Sektion
-- Column: C_CostClassification_Category_Trl.AD_Org_ID
-- 2025-12-16T19:55:23.109Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760932,0,548692,554108,641273,'F',TO_TIMESTAMP('2025-12-16 19:55:22.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-12-16 19:55:22.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 20 -> org.Mandant
-- Column: C_CostClassification_Category_Trl.AD_Client_ID
-- 2025-12-16T19:55:55.401Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760930,0,548692,554108,641274,'F',TO_TIMESTAMP('2025-12-16 19:55:54.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2025-12-16 19:55:54.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 10 -> default.Sprache
-- Column: C_CostClassification_Category_Trl.AD_Language
-- 2025-12-16T19:56:31.837Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-12-16 19:56:31.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641268
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 10 -> default.Name
-- Column: C_CostClassification_Category_Trl.Name
-- 2025-12-16T19:56:32.480Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-12-16 19:56:32.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641269
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 10 -> description.Beschreibung
-- Column: C_CostClassification_Category_Trl.Description
-- 2025-12-16T19:56:33.092Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-12-16 19:56:33.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641270
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 20 -> flags.Aktiv
-- Column: C_CostClassification_Category_Trl.IsActive
-- 2025-12-16T19:56:33.707Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-12-16 19:56:33.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641271
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 20 -> flags.Übersetzt
-- Column: C_CostClassification_Category_Trl.IsTranslated
-- 2025-12-16T19:56:34.344Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-12-16 19:56:34.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641272
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 20 -> org.Sektion
-- Column: C_CostClassification_Category_Trl.AD_Org_ID
-- 2025-12-16T19:56:34.915Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-12-16 19:56:34.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641273
;

-- UI Element: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 20 -> org.Sektion
-- Column: C_CostClassification_Category.AD_Org_ID
-- 2025-12-16T19:57:58.034Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760925,0,548691,554104,641275,'F',TO_TIMESTAMP('2025-12-16 19:57:57.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-12-16 19:57:57.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 20 -> org.Mandant
-- Column: C_CostClassification_Category.AD_Client_ID
-- 2025-12-16T19:58:30.103Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760924,0,548691,554104,641276,'F',TO_TIMESTAMP('2025-12-16 19:58:29.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2025-12-16 19:58:29.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 20 -> flags.Aktiv
-- Column: C_CostClassification_Category.IsActive
-- 2025-12-16T19:58:46.252Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-12-16 19:58:46.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641267
;

-- UI Element: Kostenartengruppe(541998,D) -> Kostenartengruppe(548691,D) -> main -> 20 -> org.Sektion
-- Column: C_CostClassification_Category.AD_Org_ID
-- 2025-12-16T19:58:46.690Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-12-16 19:58:46.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641275
;

-- Name: Kostenartengruppe
-- Action Type: W
-- Window: Kostenartengruppe(541998,D)
-- 2025-12-16T20:00:24.975Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584374,542284,0,541998,TO_TIMESTAMP('2025-12-16 20:00:24.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.','D','Kostenartengruppe','Y','N','N','N','N','Kostenartengruppe',TO_TIMESTAMP('2025-12-16 20:00:24.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T20:00:25.053Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542284 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-12-16T20:00:25.133Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542284, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542284)
;

-- 2025-12-16T20:00:25.214Z
/* DDL */  select update_menu_translation_from_ad_element(584374)
;

-- Reordering children of `Finance`
-- Node name: `Lexware`
-- 2025-12-16T20:00:30.428Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542265 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV)`
-- 2025-12-16T20:00:30.511Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug`
-- 2025-12-16T20:00:30.587Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `GL Journal`
-- 2025-12-16T20:00:30.665Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account`
-- 2025-12-16T20:00:30.744Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP)`
-- 2025-12-16T20:00:30.824Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement`
-- 2025-12-16T20:00:30.898Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement`
-- 2025-12-16T20:00:30.976Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution`
-- 2025-12-16T20:00:31.052Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal`
-- 2025-12-16T20:00:31.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2025-12-16T20:00:31.210Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment`
-- 2025-12-16T20:00:31.284Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations`
-- 2025-12-16T20:00:31.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection`
-- 2025-12-16T20:00:31.440Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation`
-- 2025-12-16T20:00:31.527Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture`
-- 2025-12-16T20:00:31.612Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning`
-- 2025-12-16T20:00:31.694Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates`
-- 2025-12-16T20:00:31.773Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions`
-- 2025-12-16T20:00:31.851Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import`
-- 2025-12-16T20:00:31.929Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination`
-- 2025-12-16T20:00:32.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts`
-- 2025-12-16T20:00:32.081Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values`
-- 2025-12-16T20:00:32.152Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts`
-- 2025-12-16T20:00:32.226Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Cost Element`
-- 2025-12-16T20:00:32.298Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs`
-- 2025-12-16T20:00:32.368Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost`
-- 2025-12-16T20:00:32.441Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity`
-- 2025-12-16T20:00:32.515Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail`
-- 2025-12-16T20:00:32.588Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents`
-- 2025-12-16T20:00:32.658Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Type`
-- 2025-12-16T20:00:32.733Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation`
-- 2025-12-16T20:00:32.802Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center`
-- 2025-12-16T20:00:32.895Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No`
-- 2025-12-16T20:00:32.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type`
-- 2025-12-16T20:00:33.094Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export`
-- 2025-12-16T20:00:33.172Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices`
-- 2025-12-16T20:00:33.245Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents`
-- 2025-12-16T20:00:33.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment`
-- 2025-12-16T20:00:33.406Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents`
-- 2025-12-16T20:00:33.481Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-12-16T20:00:33.556Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2025-12-16T20:00:33.629Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2025-12-16T20:00:33.708Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-12-16T20:00:33.781Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-12-16T20:00:33.855Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File`
-- 2025-12-16T20:00:33.925Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides`
-- 2025-12-16T20:00:33.995Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Document Accounting Log`
-- 2025-12-16T20:00:34.071Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542238 AND AD_Tree_ID=10
;

-- Node name: `INTRASTAT RTIC File (AT)`
-- 2025-12-16T20:00:34.141Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542261 AND AD_Tree_ID=10
;

-- Node name: `Kostenartengruppe`
-- 2025-12-16T20:00:34.212Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542284 AND AD_Tree_ID=10
;

-- Name: Kostenartengruppe Übersetzung
-- Action Type: W
-- Window: Kostenartengruppe Übersetzung(541999,D)
-- 2025-12-16T20:01:16.622Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584375,542285,0,541999,TO_TIMESTAMP('2025-12-16 20:01:15.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Kostenartengruppe Übersetzung','Y','N','N','N','N','Kostenartengruppe Übersetzung',TO_TIMESTAMP('2025-12-16 20:01:15.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T20:01:16.694Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542285 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-12-16T20:01:16.769Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542285, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542285)
;

-- 2025-12-16T20:01:16.842Z
/* DDL */  select update_menu_translation_from_ad_element(584375)
;

-- Reordering children of `Billing`
-- Node name: `Zweitbelege RKSV Kassello JSON`
-- 2025-12-16T20:01:21.885Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542256 AND AD_Tree_ID=10
;

-- Node name: `Provisionsberechnung`
-- 2025-12-16T20:01:22.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542203 AND AD_Tree_ID=10
;

-- Node name: `Invoice verifcation`
-- 2025-12-16T20:01:22.078Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541712 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice Candidates`
-- 2025-12-16T20:01:22.153Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice Candidates`
-- 2025-12-16T20:01:22.286Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice`
-- 2025-12-16T20:01:22.414Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice`
-- 2025-12-16T20:01:22.490Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- Node name: `Customs Invoice`
-- 2025-12-16T20:01:22.559Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541271 AND AD_Tree_ID=10
;

-- Node name: `Shipment Line to Customs Invoice Line`
-- 2025-12-16T20:01:22.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541418 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-12-16T20:01:22.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-12-16T20:01:22.790Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-12-16T20:01:22.862Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;

-- Node name: `Invoicing Pool`
-- 2025-12-16T20:01:22.934Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542033 AND AD_Tree_ID=10
;

-- Node name: `Enqueue selection for invoicing and pdf printing`
-- 2025-12-16T20:01:23.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541886 AND AD_Tree_ID=10
;

-- Node name: `Commission Forecast`
-- 2025-12-16T20:01:23.093Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542214 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice Report (Excel)`
-- 2025-12-16T20:01:23.167Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542186 AND AD_Tree_ID=10
;

-- Node name: `Invoice Payment Allocations Report (Excel)`
-- 2025-12-16T20:01:23.240Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542227 AND AD_Tree_ID=10
;

-- Node name: `Kostenartengruppe Übersetzung`
-- 2025-12-16T20:01:23.310Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542285 AND AD_Tree_ID=10
;

-- Reordering children of `Settings`
-- Node name: `Kostenartengruppe Übersetzung`
-- 2025-12-16T20:01:29.126Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542285 AND AD_Tree_ID=10
;

-- Node name: `Customer Accounts`
-- 2025-12-16T20:01:29.198Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541000 AND AD_Tree_ID=10
;

-- Node name: `Vendor Accounts`
-- 2025-12-16T20:01:29.272Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541001 AND AD_Tree_ID=10
;

-- Node name: `Accounting Schema`
-- 2025-12-16T20:01:29.368Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541008 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Accounts`
-- 2025-12-16T20:01:29.443Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541002 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Group Accounts`
-- 2025-12-16T20:01:29.521Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541003 AND AD_Tree_ID=10
;

-- Node name: `GL Category`
-- 2025-12-16T20:01:29.596Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540956 AND AD_Tree_ID=10
;

-- Node name: `Calendar and Year`
-- 2025-12-16T20:01:29.671Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540881 AND AD_Tree_ID=10
;

-- Node name: `Calendar Periods`
-- 2025-12-16T20:01:29.755Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540882 AND AD_Tree_ID=10
;

-- Node name: `Tax Rate`
-- 2025-12-16T20:01:29.828Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540842 AND AD_Tree_ID=10
;

-- Node name: `Tax Rate Translation`
-- 2025-12-16T20:01:29.903Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541098 AND AD_Tree_ID=10
;

-- Node name: `Tax Category`
-- 2025-12-16T20:01:29.976Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540843 AND AD_Tree_ID=10
;

-- Node name: `Tax Category Translation`
-- 2025-12-16T20:01:30.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541097 AND AD_Tree_ID=10
;

-- Node name: `Bankenstamm`
-- 2025-12-16T20:01:30.130Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540810 AND AD_Tree_ID=10
;

-- Node name: `Currency`
-- 2025-12-16T20:01:30.208Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540812 AND AD_Tree_ID=10
;

-- Node name: `Currency Translation`
-- 2025-12-16T20:01:30.282Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541107 AND AD_Tree_ID=10
;

-- Node name: `Currency Rate`
-- 2025-12-16T20:01:30.366Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540813 AND AD_Tree_ID=10
;

-- Node name: `Currency Type`
-- 2025-12-16T20:01:30.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542004 AND AD_Tree_ID=10
;

-- Node name: `Default Currency Conversion Type`
-- 2025-12-16T20:01:30.517Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542006 AND AD_Tree_ID=10
;

-- Node name: `Dunning Type`
-- 2025-12-16T20:01:30.598Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540780 AND AD_Tree_ID=10
;

-- Node name: `Dunning Level Translation`
-- 2025-12-16T20:01:30.678Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541103 AND AD_Tree_ID=10
;

-- Reordering children of `Finance`
-- Node name: `Lexware`
-- 2025-12-16T20:05:46.487Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542265 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV)`
-- 2025-12-16T20:05:46.562Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug`
-- 2025-12-16T20:05:46.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `GL Journal`
-- 2025-12-16T20:05:46.715Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account`
-- 2025-12-16T20:05:46.789Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP)`
-- 2025-12-16T20:05:46.859Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement`
-- 2025-12-16T20:05:46.933Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement`
-- 2025-12-16T20:05:47.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution`
-- 2025-12-16T20:05:47.083Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal`
-- 2025-12-16T20:05:47.158Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2025-12-16T20:05:47.230Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment`
-- 2025-12-16T20:05:47.307Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations`
-- 2025-12-16T20:05:47.379Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection`
-- 2025-12-16T20:05:47.452Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation`
-- 2025-12-16T20:05:47.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture`
-- 2025-12-16T20:05:47.597Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning`
-- 2025-12-16T20:05:47.671Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates`
-- 2025-12-16T20:05:47.744Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions`
-- 2025-12-16T20:05:47.819Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import`
-- 2025-12-16T20:05:47.892Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination`
-- 2025-12-16T20:05:47.966Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts`
-- 2025-12-16T20:05:48.040Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values`
-- 2025-12-16T20:05:48.118Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts`
-- 2025-12-16T20:05:48.191Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Cost Element`
-- 2025-12-16T20:05:48.266Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs`
-- 2025-12-16T20:05:48.339Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost`
-- 2025-12-16T20:05:48.411Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity`
-- 2025-12-16T20:05:48.484Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail`
-- 2025-12-16T20:05:48.558Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents`
-- 2025-12-16T20:05:48.631Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Type`
-- 2025-12-16T20:05:48.704Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation`
-- 2025-12-16T20:05:48.779Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center`
-- 2025-12-16T20:05:48.853Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No`
-- 2025-12-16T20:05:48.926Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type`
-- 2025-12-16T20:05:49Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export`
-- 2025-12-16T20:05:49.071Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices`
-- 2025-12-16T20:05:49.155Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents`
-- 2025-12-16T20:05:49.229Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment`
-- 2025-12-16T20:05:49.301Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents`
-- 2025-12-16T20:05:49.377Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-12-16T20:05:49.449Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2025-12-16T20:05:49.519Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2025-12-16T20:05:49.593Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-12-16T20:05:49.666Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-12-16T20:05:49.753Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File`
-- 2025-12-16T20:05:49.839Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides`
-- 2025-12-16T20:05:49.918Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Document Accounting Log`
-- 2025-12-16T20:05:50.001Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542238 AND AD_Tree_ID=10
;

-- Node name: `INTRASTAT RTIC File (AT)`
-- 2025-12-16T20:05:50.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542261 AND AD_Tree_ID=10
;

-- Node name: `Kostenartengruppe Übersetzung`
-- 2025-12-16T20:05:50.166Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542285 AND AD_Tree_ID=10
;

-- Node name: `Cost Classification Category`
-- 2025-12-16T20:05:50.243Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542284 AND AD_Tree_ID=10
;

-- Window: Kostenartengruppe Übersetzung, InternalName=null
-- 2025-12-16T20:07:42.058Z
UPDATE AD_Window SET IsSOTrx='N',Updated=TO_TIMESTAMP('2025-12-16 20:07:41.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541999
;

-- 2025-12-16T20:10:29.261Z
INSERT INTO t_alter_column values('c_costclassification_category_trl','C_CostClassification_Category_ID','NUMERIC(10)',null,null)
;

-- Table: C_CostClassification_Category_Trl
-- 2025-12-16T20:53:03.824Z
UPDATE AD_Table SET AD_Window_ID=541999,Updated=TO_TIMESTAMP('2025-12-16 20:53:03.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542566
;

-- UI Element: Kostenartengruppe Übersetzung(541999,D) -> Kostenartengruppe Übersetzung(548692,D) -> main -> 10 -> default.Kostenartengruppe
-- Column: C_CostClassification_Category_Trl.C_CostClassification_Category_ID
-- 2025-12-17T08:23:57.611Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760933,0,548692,554105,641277,'F',TO_TIMESTAMP('2025-12-17 08:23:57.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.','Y','N','N','Y','N','N','N',0,'Kostenartengruppe',5,0,0,TO_TIMESTAMP('2025-12-17 08:23:57.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
