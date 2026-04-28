-- 2025-04-16T13:51:10.677Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583583,0,TO_TIMESTAMP('2025-04-16 13:51:10.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','DHL Versandauftragsprotokoll','DHL Versandauftragsprotokoll',TO_TIMESTAMP('2025-04-16 13:51:10.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T13:51:10.730Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583583 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-04-16T13:51:45.411Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='DHL Delivery Order Log', PrintName='DHL Delivery Order Log',Updated=TO_TIMESTAMP('2025-04-16 13:51:45.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583583 AND AD_Language='en_US'
;

-- 2025-04-16T13:51:45.523Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583583,'en_US') 
;

-- Window: DHL Versandauftragsprotokoll, InternalName=null
-- Window: DHL Versandauftragsprotokoll, InternalName=null
-- 2025-04-16T13:52:39.958Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583583,0,541879,TO_TIMESTAMP('2025-04-16 13:52:39.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','DHL Versandauftragsprotokoll','N',TO_TIMESTAMP('2025-04-16 13:52:39.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-04-16T13:52:40.016Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541879 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-04-16T13:52:40.117Z
/* DDL */  select update_window_translation_from_ad_element(583583) 
;

-- 2025-04-16T13:52:40.170Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541879
;

-- 2025-04-16T13:52:40.221Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541879)
;

-- Tab: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log
-- Table: Dhl_ShipmentOrder_Log
-- Tab: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log
-- Table: Dhl_ShipmentOrder_Log
-- 2025-04-16T13:53:27.405Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,577227,0,547956,541426,541879,'Y',TO_TIMESTAMP('2025-04-16 13:53:26.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','Dhl_ShipmentOrder_Log','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Dhl ShipmentOrder Log','N',10,0,TO_TIMESTAMP('2025-04-16 13:53:26.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T13:53:27.459Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547956 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-04-16T13:53:27.511Z
/* DDL */  select update_tab_translation_from_ad_element(577227) 
;

-- 2025-04-16T13:53:27.563Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547956)
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> Mandant
-- Column: Dhl_ShipmentOrder_Log.AD_Client_ID
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> Mandant
-- Column: Dhl_ShipmentOrder_Log.AD_Client_ID
-- 2025-04-16T13:58:29.891Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569227,741967,0,547956,0,TO_TIMESTAMP('2025-04-16 13:58:28.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',0,'D',0,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Mandant',0,0,10,0,1,1,TO_TIMESTAMP('2025-04-16 13:58:28.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T13:58:29.942Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741967 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-16T13:58:29.991Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2025-04-16T13:58:30.095Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741967
;

-- 2025-04-16T13:58:30.144Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741967)
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> Probleme
-- Column: Dhl_ShipmentOrder_Log.AD_Issue_ID
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> Probleme
-- Column: Dhl_ShipmentOrder_Log.AD_Issue_ID
-- 2025-04-16T13:58:52.866Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569228,741968,0,547956,0,TO_TIMESTAMP('2025-04-16 13:58:51.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',0,'D',0,'',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Probleme',0,0,20,0,1,1,TO_TIMESTAMP('2025-04-16 13:58:51.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T13:58:52.917Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-16T13:58:52.969Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2025-04-16T13:58:53.021Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741968
;

-- 2025-04-16T13:58:53.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741968)
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> Organisation
-- Column: Dhl_ShipmentOrder_Log.AD_Org_ID
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> Organisation
-- Column: Dhl_ShipmentOrder_Log.AD_Org_ID
-- 2025-04-16T13:59:17.804Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569229,741969,0,547956,0,TO_TIMESTAMP('2025-04-16 13:59:16.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',0,'D',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Organisation',0,0,30,0,1,1,TO_TIMESTAMP('2025-04-16 13:59:16.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T13:59:17.854Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741969 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-16T13:59:17.905Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2025-04-16T13:59:18.009Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741969
;

-- 2025-04-16T13:59:18.058Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741969)
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> Config Summary
-- Column: Dhl_ShipmentOrder_Log.ConfigSummary
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> Config Summary
-- Column: Dhl_ShipmentOrder_Log.ConfigSummary
-- 2025-04-16T13:59:45.652Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569233,741970,0,547956,0,TO_TIMESTAMP('2025-04-16 13:59:44.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Config Summary',0,0,40,0,1,1,TO_TIMESTAMP('2025-04-16 13:59:44.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T13:59:45.750Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741970 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-16T13:59:45.802Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577229) 
;

-- 2025-04-16T13:59:45.863Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741970
;

-- 2025-04-16T13:59:45.913Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741970)
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> Aktiv
-- Column: Dhl_ShipmentOrder_Log.IsActive
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> Aktiv
-- Column: Dhl_ShipmentOrder_Log.IsActive
-- 2025-04-16T14:00:10.408Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569236,741971,0,547956,0,TO_TIMESTAMP('2025-04-16 14:00:09.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',0,'D',0,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Aktiv',0,0,50,0,1,1,TO_TIMESTAMP('2025-04-16 14:00:09.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T14:00:10.458Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741971 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-16T14:00:10.508Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2025-04-16T14:00:10.615Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741971
;

-- 2025-04-16T14:00:10.665Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741971)
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> Fehler
-- Column: Dhl_ShipmentOrder_Log.IsError
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> Fehler
-- Column: Dhl_ShipmentOrder_Log.IsError
-- 2025-04-16T14:00:33.985Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569237,741972,0,547956,0,TO_TIMESTAMP('2025-04-16 14:00:32.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Fehler ist bei der Durchführung aufgetreten',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Fehler',0,0,60,0,1,1,TO_TIMESTAMP('2025-04-16 14:00:32.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T14:00:34.036Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741972 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-16T14:00:34.086Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2395) 
;

-- 2025-04-16T14:00:34.141Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741972
;

-- 2025-04-16T14:00:34.198Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741972)
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> Request Message
-- Column: Dhl_ShipmentOrder_Log.RequestMessage
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> Request Message
-- Column: Dhl_ShipmentOrder_Log.RequestMessage
-- 2025-04-16T14:01:04.446Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569238,741973,0,547956,0,TO_TIMESTAMP('2025-04-16 14:01:03.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Request Message',0,0,70,0,1,1,TO_TIMESTAMP('2025-04-16 14:01:03.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T14:01:04.564Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741973 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-16T14:01:04.615Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543758) 
;

-- 2025-04-16T14:01:04.679Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741973
;

-- 2025-04-16T14:01:04.728Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741973)
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> Response Message
-- Column: Dhl_ShipmentOrder_Log.ResponseMessage
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> Response Message
-- Column: Dhl_ShipmentOrder_Log.ResponseMessage
-- 2025-04-16T14:01:26.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569239,741974,0,547956,0,TO_TIMESTAMP('2025-04-16 14:01:25.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Response Message',0,0,80,0,1,1,TO_TIMESTAMP('2025-04-16 14:01:25.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T14:01:26.321Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-16T14:01:26.372Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543759) 
;

-- 2025-04-16T14:01:26.425Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741974
;

-- 2025-04-16T14:01:26.474Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741974)
;

-- Tab: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D)
-- UI Section: main
-- 2025-04-16T14:01:49.442Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547956,546540,TO_TIMESTAMP('2025-04-16 14:01:48.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-16 14:01:48.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-04-16T14:01:49.493Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546540 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main
-- UI Column: 10
-- 2025-04-16T14:01:59.696Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547984,546540,TO_TIMESTAMP('2025-04-16 14:01:59.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-16 14:01:59.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main
-- UI Column: 20
-- 2025-04-16T14:02:05.570Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547985,546540,TO_TIMESTAMP('2025-04-16 14:02:04.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-04-16 14:02:04.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10
-- UI Element Group: msg
-- 2025-04-16T14:02:26.384Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547984,552739,TO_TIMESTAMP('2025-04-16 14:02:25.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','msg',10,'primary',TO_TIMESTAMP('2025-04-16 14:02:25.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: ConfigSummary
-- 2025-04-16T14:03:45.125Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Konfig-Zusammenfassung', PrintName='Konfig-Zusammenfassung',Updated=TO_TIMESTAMP('2025-04-16 14:03:45.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577229 AND AD_Language='de_DE'
;

-- 2025-04-16T14:03:45.177Z
UPDATE AD_Element SET Name='Konfig-Zusammenfassung', PrintName='Konfig-Zusammenfassung', Updated=TO_TIMESTAMP('2025-04-16 14:03:45.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=577229
;

-- 2025-04-16T14:03:58.370Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577229,'de_DE') 
;

-- 2025-04-16T14:03:58.419Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577229,'de_DE') 
;

-- Element: ConfigSummary
-- 2025-04-16T14:04:10.507Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Konfig-Zusammenfassung', PrintName='Konfig-Zusammenfassung',Updated=TO_TIMESTAMP('2025-04-16 14:04:10.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577229 AND AD_Language='de_CH'
;

-- 2025-04-16T14:04:10.609Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577229,'de_CH') 
;

-- UI Element: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log.Konfig-Zusammenfassung
-- Column: Dhl_ShipmentOrder_Log.ConfigSummary
-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.Konfig-Zusammenfassung
-- Column: Dhl_ShipmentOrder_Log.ConfigSummary
-- 2025-04-16T14:04:32.635Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741970,0,547956,552739,631362,'F',TO_TIMESTAMP('2025-04-16 14:04:31.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Konfig-Zusammenfassung',10,0,0,TO_TIMESTAMP('2025-04-16 14:04:31.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: RequestMessage
-- 2025-04-16T14:05:48.744Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Nachricht anfordern', PrintName='Nachricht anfordern',Updated=TO_TIMESTAMP('2025-04-16 14:05:48.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543758 AND AD_Language='de_CH'
;

-- 2025-04-16T14:05:48.845Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543758,'de_CH') 
;

-- Element: RequestMessage
-- 2025-04-16T14:06:05.574Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Nachricht anfordern', PrintName='Nachricht anfordern',Updated=TO_TIMESTAMP('2025-04-16 14:06:05.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543758 AND AD_Language='de_DE'
;

-- 2025-04-16T14:06:05.623Z
UPDATE AD_Element SET Name='Nachricht anfordern', PrintName='Nachricht anfordern', Updated=TO_TIMESTAMP('2025-04-16 14:06:05.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=543758
;

-- 2025-04-16T14:06:16.519Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543758,'de_DE') 
;

-- 2025-04-16T14:06:16.571Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543758,'de_DE') 
;

-- UI Element: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log.Nachricht anfordern
-- Column: Dhl_ShipmentOrder_Log.RequestMessage
-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.Nachricht anfordern
-- Column: Dhl_ShipmentOrder_Log.RequestMessage
-- 2025-04-16T14:06:29.573Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741973,0,547956,552739,631363,'F',TO_TIMESTAMP('2025-04-16 14:06:28.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Nachricht anfordern',20,0,0,TO_TIMESTAMP('2025-04-16 14:06:28.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: ResponseMessage
-- 2025-04-16T14:07:32.002Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Antwortnachricht', PrintName='Antwortnachricht',Updated=TO_TIMESTAMP('2025-04-16 14:07:32.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543759 AND AD_Language='de_DE'
;

-- 2025-04-16T14:07:32.054Z
UPDATE AD_Element SET Name='Antwortnachricht', PrintName='Antwortnachricht', Updated=TO_TIMESTAMP('2025-04-16 14:07:32.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=543759
;

-- 2025-04-16T14:07:46.259Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543759,'de_DE') 
;

-- 2025-04-16T14:07:46.309Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543759,'de_DE') 
;

-- Element: ResponseMessage
-- 2025-04-16T14:07:56.050Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Antwortnachricht', PrintName='Antwortnachricht',Updated=TO_TIMESTAMP('2025-04-16 14:07:56.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543759 AND AD_Language='de_CH'
;

-- 2025-04-16T14:07:56.152Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543759,'de_CH') 
;

-- UI Element: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log.Antwortnachricht
-- Column: Dhl_ShipmentOrder_Log.ResponseMessage
-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.Antwortnachricht
-- Column: Dhl_ShipmentOrder_Log.ResponseMessage
-- 2025-04-16T14:08:12.324Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741974,0,547956,552739,631364,'F',TO_TIMESTAMP('2025-04-16 14:08:11.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Antwortnachricht',30,0,0,TO_TIMESTAMP('2025-04-16 14:08:11.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log.Probleme
-- Column: Dhl_ShipmentOrder_Log.AD_Issue_ID
-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.Probleme
-- Column: Dhl_ShipmentOrder_Log.AD_Issue_ID
-- 2025-04-16T14:09:15.341Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741968,0,547956,552739,631365,'F',TO_TIMESTAMP('2025-04-16 14:09:14.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Probleme',40,0,0,TO_TIMESTAMP('2025-04-16 14:09:14.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 20
-- UI Element Group: flags
-- 2025-04-16T14:09:43.871Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547985,552740,TO_TIMESTAMP('2025-04-16 14:09:43.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-04-16 14:09:43.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log.Aktiv
-- Column: Dhl_ShipmentOrder_Log.IsActive
-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 20 -> flags.Aktiv
-- Column: Dhl_ShipmentOrder_Log.IsActive
-- 2025-04-16T14:10:16.831Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741971,0,547956,552740,631366,'F',TO_TIMESTAMP('2025-04-16 14:10:16.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-04-16 14:10:16.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: Dhl_ShipmentOrder_Log.IsError
-- Column: Dhl_ShipmentOrder_Log.IsError
-- 2025-04-16T14:11:14.990Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-04-16 14:11:14.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569237
;

-- UI Element: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log.Fehler
-- Column: Dhl_ShipmentOrder_Log.IsError
-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 20 -> flags.Fehler
-- Column: Dhl_ShipmentOrder_Log.IsError
-- 2025-04-16T14:11:36.245Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741972,0,547956,552740,631367,'F',TO_TIMESTAMP('2025-04-16 14:11:35.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Fehler ist bei der Durchführung aufgetreten','Y','N','N','Y','N','N','N',0,'Fehler',20,0,0,TO_TIMESTAMP('2025-04-16 14:11:35.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 20
-- UI Element Group: org
-- 2025-04-16T14:11:53.219Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547985,552741,TO_TIMESTAMP('2025-04-16 14:11:52.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-04-16 14:11:52.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log.Organisation
-- Column: Dhl_ShipmentOrder_Log.AD_Org_ID
-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 20 -> org.Organisation
-- Column: Dhl_ShipmentOrder_Log.AD_Org_ID
-- 2025-04-16T14:12:28.235Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741969,0,547956,552741,631368,'F',TO_TIMESTAMP('2025-04-16 14:12:27.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2025-04-16 14:12:27.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log.Mandant
-- Column: Dhl_ShipmentOrder_Log.AD_Client_ID
-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 20 -> org.Mandant
-- Column: Dhl_ShipmentOrder_Log.AD_Client_ID
-- 2025-04-16T14:12:55.835Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741967,0,547956,552741,631369,'F',TO_TIMESTAMP('2025-04-16 14:12:55.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2025-04-16 14:12:55.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: DHL Versandauftragsprotokoll
-- Action Type: W
-- Window: DHL Versandauftragsprotokoll(541879,D)
-- 2025-04-16T14:15:11.856Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583583,542218,0,541879,TO_TIMESTAMP('2025-04-16 14:15:10.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','DHL Versandauftragsprotokoll','Y','N','N','N','N','DHL Versandauftragsprotokoll',TO_TIMESTAMP('2025-04-16 14:15:10.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T14:15:11.907Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542218 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-04-16T14:15:11.959Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542218, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542218)
;

-- 2025-04-16T14:15:12.018Z
/* DDL */  select update_menu_translation_from_ad_element(583583) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour`
-- 2025-04-16T14:15:19.872Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `QR Code Configuration`
-- 2025-04-16T14:15:19.934Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542139 AND AD_Tree_ID=10
;

-- Node name: `Tourversion`
-- 2025-04-16T14:15:19.985Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days`
-- 2025-04-16T14:15:20.037Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order`
-- 2025-04-16T14:15:20.088Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor`
-- 2025-04-16T14:15:20.138Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction`
-- 2025-04-16T14:15:20.198Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version`
-- 2025-04-16T14:15:20.250Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation`
-- 2025-04-16T14:15:20.300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material`
-- 2025-04-16T14:15:20.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit`
-- 2025-04-16T14:15:20.400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code`
-- 2025-04-16T14:15:20.450Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction`
-- 2025-04-16T14:15:20.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Trace`
-- 2025-04-16T14:15:20.550Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition`
-- 2025-04-16T14:15:20.599Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery`
-- 2025-04-16T14:15:20.650Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions`
-- 2025-04-16T14:15:20.701Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order`
-- 2025-04-16T14:15:20.752Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package`
-- 2025-04-16T14:15:20.802Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use`
-- 2025-04-16T14:15:20.853Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders`
-- 2025-04-16T14:15:20.902Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders`
-- 2025-04-16T14:15:20.954Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order`
-- 2025-04-16T14:15:21.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order`
-- 2025-04-16T14:15:21.059Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI HU Manager`
-- 2025-04-16T14:15:21.110Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542163 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-04-16T14:15:21.162Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-04-16T14:15:21.211Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-04-16T14:15:21.263Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung`
-- 2025-04-16T14:15:21.314Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units`
-- 2025-04-16T14:15:21.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code`
-- 2025-04-16T14:15:21.416Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Distribution Candidate`
-- 2025-04-16T14:15:21.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542164 AND AD_Tree_ID=10
;

-- Node name: `Generate new HU QR Codes`
-- 2025-04-16T14:15:21.515Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542152 AND AD_Tree_ID=10
;

-- Node name: `DHL Versandauftragsprotokoll`
-- 2025-04-16T14:15:21.564Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542218 AND AD_Tree_ID=10
;

-- Table: Dhl_ShipmentOrder_Log
-- Table: Dhl_ShipmentOrder_Log
-- 2025-04-16T14:17:02.148Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2025-04-16 14:17:01.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=541426
;

