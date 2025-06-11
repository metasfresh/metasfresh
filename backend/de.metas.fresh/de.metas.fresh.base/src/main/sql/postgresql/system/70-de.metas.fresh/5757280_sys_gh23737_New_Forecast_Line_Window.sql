-- Run mode: SWING_CLIENT

-- 2025-06-10T15:46:10.493Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583719,0,TO_TIMESTAMP('2025-06-10 15:46:09.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.endcustomer.ic114','Y','Prognose Position','Prognose Position',TO_TIMESTAMP('2025-06-10 15:46:09.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:46:10.847Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583719 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-06-10T15:46:40.890Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Forecast Lines', PrintName='Forecast Lines',Updated=TO_TIMESTAMP('2025-06-10 15:46:40.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583719 AND AD_Language='en_US'
;

-- 2025-06-10T15:46:40.947Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T15:46:47.772Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583719,'en_US')
;

-- Window: Prognose Position, InternalName=null
-- 2025-06-10T15:47:48.308Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583719,0,541900,TO_TIMESTAMP('2025-06-10 15:47:47.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','Y','Prognose Position','N',TO_TIMESTAMP('2025-06-10 15:47:47.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-06-10T15:47:48.464Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541900 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-06-10T15:47:48.568Z
/* DDL */  select update_window_translation_from_ad_element(583719)
;

-- 2025-06-10T15:47:48.626Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541900
;

-- 2025-06-10T15:47:48.678Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541900)
;

-- Tab: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position
-- Table: M_ForecastLine
-- 2025-06-10T15:48:44.938Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,2499,0,548194,722,541900,'Y',TO_TIMESTAMP('2025-06-10 15:48:44.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Prognose-Position','de.metas.endcustomer.ic114','N','Forecast of Product Qyantity by Period','N','A','M_ForecastLine','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Prognose-Position','N',10,0,TO_TIMESTAMP('2025-06-10 15:48:44.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:48:45.097Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548194 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-06-10T15:48:45.149Z
/* DDL */  select update_tab_translation_from_ad_element(2499)
;

-- 2025-06-10T15:48:45.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548194)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Periode
-- Column: M_ForecastLine.C_Period_ID
-- 2025-06-10T15:48:57.311Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,11934,747556,0,548194,TO_TIMESTAMP('2025-06-10 15:48:56.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Periode des Kalenders',22,'de.metas.endcustomer.ic114','"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','N','N','N','N','N','N','Periode',TO_TIMESTAMP('2025-06-10 15:48:56.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:48:57.418Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747556 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:48:57.470Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(206)
;

-- 2025-06-10T15:48:57.526Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747556
;

-- 2025-06-10T15:48:57.578Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747556)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Mandant
-- Column: M_ForecastLine.AD_Client_ID
-- 2025-06-10T15:48:58.400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,11935,747557,0,548194,TO_TIMESTAMP('2025-06-10 15:48:57.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',22,'de.metas.endcustomer.ic114','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-06-10 15:48:57.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:48:58.553Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747557 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:48:58.604Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-06-10T15:48:58.710Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747557
;

-- 2025-06-10T15:48:58.761Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747557)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Sektion
-- Column: M_ForecastLine.AD_Org_ID
-- 2025-06-10T15:48:59.597Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,11937,747558,0,548194,TO_TIMESTAMP('2025-06-10 15:48:58.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',22,'de.metas.endcustomer.ic114','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-06-10 15:48:58.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:48:59.699Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747558 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:48:59.750Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-06-10T15:48:59.873Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747558
;

-- 2025-06-10T15:48:59.924Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747558)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Menge
-- Column: M_ForecastLine.Qty
-- 2025-06-10T15:49:00.759Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,11938,747559,0,548194,TO_TIMESTAMP('2025-06-10 15:49:00.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge',22,'de.metas.endcustomer.ic114','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2025-06-10 15:49:00.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:00.865Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747559 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:00.918Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2025-06-10T15:49:00.975Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747559
;

-- 2025-06-10T15:49:01.025Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747559)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Prognose
-- Column: M_ForecastLine.M_Forecast_ID
-- 2025-06-10T15:49:01.864Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,11939,747560,0,548194,TO_TIMESTAMP('2025-06-10 15:49:01.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Vorhersagen zu Material-/Produkt-/Artikelentwicklung',22,'de.metas.endcustomer.ic114','Vorhersagen zu Material-/Produkt-/Artikelentwicklung','Y','N','N','N','N','N','N','N','Prognose',TO_TIMESTAMP('2025-06-10 15:49:01.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:01.972Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747560 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:02.023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2498)
;

-- 2025-06-10T15:49:02.076Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747560
;

-- 2025-06-10T15:49:02.127Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747560)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Aktiv
-- Column: M_ForecastLine.IsActive
-- 2025-06-10T15:49:02.945Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,11940,747561,0,548194,TO_TIMESTAMP('2025-06-10 15:49:02.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.endcustomer.ic114','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-06-10 15:49:02.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:03.058Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747561 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:03.109Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-06-10T15:49:03.231Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747561
;

-- 2025-06-10T15:49:03.283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747561)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Produkt
-- Column: M_ForecastLine.M_Product_ID
-- 2025-06-10T15:49:04.103Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,11941,747562,0,548194,TO_TIMESTAMP('2025-06-10 15:49:03.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',22,'de.metas.endcustomer.ic114','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2025-06-10 15:49:03.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:04.209Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747562 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:04.260Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2025-06-10T15:49:04.328Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747562
;

-- 2025-06-10T15:49:04.379Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747562)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Prognose-Position
-- Column: M_ForecastLine.M_ForecastLine_ID
-- 2025-06-10T15:49:05.196Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,11942,747563,0,548194,TO_TIMESTAMP('2025-06-10 15:49:04.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Prognose-Position',22,'de.metas.endcustomer.ic114','Forecast of Product Qyantity by Period','Y','N','N','N','N','N','N','N','Prognose-Position',TO_TIMESTAMP('2025-06-10 15:49:04.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:05.302Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747563 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:05.355Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2499)
;

-- 2025-06-10T15:49:05.408Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747563
;

-- 2025-06-10T15:49:05.459Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747563)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Calculated Quantity
-- Column: M_ForecastLine.QtyCalculated
-- 2025-06-10T15:49:06.307Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,11959,747564,0,548194,TO_TIMESTAMP('2025-06-10 15:49:05.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Calculated Quantity',22,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','Calculated Quantity',TO_TIMESTAMP('2025-06-10 15:49:05.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:06.411Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747564 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:06.463Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2500)
;

-- 2025-06-10T15:49:06.516Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747564
;

-- 2025-06-10T15:49:06.568Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747564)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Lager
-- Column: M_ForecastLine.M_Warehouse_ID
-- 2025-06-10T15:49:07.405Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,53411,747565,0,548194,TO_TIMESTAMP('2025-06-10 15:49:06.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',22,'de.metas.endcustomer.ic114','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2025-06-10 15:49:06.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:07.511Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747565 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:07.562Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2025-06-10T15:49:07.620Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747565
;

-- 2025-06-10T15:49:07.670Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747565)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Zugesagter Termin
-- Column: M_ForecastLine.DatePromised
-- 2025-06-10T15:49:08.493Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,53412,747566,0,548194,TO_TIMESTAMP('2025-06-10 15:49:07.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',7,'de.metas.endcustomer.ic114','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','N','N','N','N','N','N','Zugesagter Termin',TO_TIMESTAMP('2025-06-10 15:49:07.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:08.596Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747566 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:08.648Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269)
;

-- 2025-06-10T15:49:08.702Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747566
;

-- 2025-06-10T15:49:08.752Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747566)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Kundenbetreuer
-- Column: M_ForecastLine.SalesRep_ID
-- 2025-06-10T15:49:09.590Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,56113,747567,0,548194,TO_TIMESTAMP('2025-06-10 15:49:08.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',22,'de.metas.endcustomer.ic114','','Y','N','N','N','N','N','N','N','Kundenbetreuer',TO_TIMESTAMP('2025-06-10 15:49:08.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:09.701Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747567 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:09.754Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063)
;

-- 2025-06-10T15:49:09.809Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747567
;

-- 2025-06-10T15:49:09.860Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747567)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Geschäftspartner
-- Column: M_ForecastLine.C_BPartner_ID
-- 2025-06-10T15:49:10.710Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550452,747568,0,548194,TO_TIMESTAMP('2025-06-10 15:49:09.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',22,'de.metas.endcustomer.ic114','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2025-06-10 15:49:09.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:10.815Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747568 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:10.868Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2025-06-10T15:49:10.926Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747568
;

-- 2025-06-10T15:49:10.978Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747568)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Standort
-- Column: M_ForecastLine.C_BPartner_Location_ID
-- 2025-06-10T15:49:11.817Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550454,747569,0,548194,TO_TIMESTAMP('2025-06-10 15:49:11.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',22,'de.metas.endcustomer.ic114','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2025-06-10 15:49:11.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:11.921Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747569 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:11.972Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189)
;

-- 2025-06-10T15:49:12.026Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747569
;

-- 2025-06-10T15:49:12.078Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747569)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Merkmale
-- Column: M_ForecastLine.M_AttributeSetInstance_ID
-- 2025-06-10T15:49:12.917Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551910,747570,0,548194,TO_TIMESTAMP('2025-06-10 15:49:12.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt',10,'de.metas.endcustomer.ic114','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','N','N','N','N','N','Merkmale',TO_TIMESTAMP('2025-06-10 15:49:12.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:13.020Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747570 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:13.072Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2025-06-10T15:49:13.130Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747570
;

-- 2025-06-10T15:49:13.183Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747570)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Packvorschrift
-- Column: M_ForecastLine.M_HU_PI_Item_Product_ID
-- 2025-06-10T15:49:14.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557889,747571,0,548194,TO_TIMESTAMP('2025-06-10 15:49:13.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','Packvorschrift',TO_TIMESTAMP('2025-06-10 15:49:13.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:14.826Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747571 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:14.947Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542132)
;

-- 2025-06-10T15:49:15.007Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747571
;

-- 2025-06-10T15:49:15.093Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747571)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Menge TU
-- Column: M_ForecastLine.QtyEnteredTU
-- 2025-06-10T15:49:15.971Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557890,747572,0,548194,TO_TIMESTAMP('2025-06-10 15:49:15.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','Menge TU',TO_TIMESTAMP('2025-06-10 15:49:15.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:16.080Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747572 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:16.132Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542397)
;

-- 2025-06-10T15:49:16.186Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747572
;

-- 2025-06-10T15:49:16.249Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747572)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Maßeinheit
-- Column: M_ForecastLine.C_UOM_ID
-- 2025-06-10T15:49:17.080Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557891,747573,0,548194,TO_TIMESTAMP('2025-06-10 15:49:16.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',22,'de.metas.endcustomer.ic114','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2025-06-10 15:49:16.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:17.182Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747573 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:17.244Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2025-06-10T15:49:17.302Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747573
;

-- 2025-06-10T15:49:17.352Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747573)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> UserElementString1
-- Column: M_ForecastLine.UserElementString1
-- 2025-06-10T15:49:18.166Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572451,747574,0,548194,TO_TIMESTAMP('2025-06-10 15:49:17.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','UserElementString1',TO_TIMESTAMP('2025-06-10 15:49:17.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:18.268Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747574 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:18.320Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653)
;

-- 2025-06-10T15:49:18.389Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747574
;

-- 2025-06-10T15:49:18.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747574)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> UserElementString2
-- Column: M_ForecastLine.UserElementString2
-- 2025-06-10T15:49:19.267Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572452,747575,0,548194,TO_TIMESTAMP('2025-06-10 15:49:18.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2025-06-10 15:49:18.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:19.372Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747575 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:19.425Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654)
;

-- 2025-06-10T15:49:19.479Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747575
;

-- 2025-06-10T15:49:19.534Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747575)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> UserElementString3
-- Column: M_ForecastLine.UserElementString3
-- 2025-06-10T15:49:20.387Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572453,747576,0,548194,TO_TIMESTAMP('2025-06-10 15:49:19.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','UserElementString3',TO_TIMESTAMP('2025-06-10 15:49:19.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:20.489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747576 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:20.539Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655)
;

-- 2025-06-10T15:49:20.592Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747576
;

-- 2025-06-10T15:49:20.646Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747576)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> UserElementString4
-- Column: M_ForecastLine.UserElementString4
-- 2025-06-10T15:49:21.480Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572454,747577,0,548194,TO_TIMESTAMP('2025-06-10 15:49:20.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2025-06-10 15:49:20.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:21.591Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747577 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:21.641Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656)
;

-- 2025-06-10T15:49:21.694Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747577
;

-- 2025-06-10T15:49:21.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747577)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> UserElementString5
-- Column: M_ForecastLine.UserElementString5
-- 2025-06-10T15:49:22.605Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572455,747578,0,548194,TO_TIMESTAMP('2025-06-10 15:49:21.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2025-06-10 15:49:21.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:22.725Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747578 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:22.777Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657)
;

-- 2025-06-10T15:49:22.832Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747578
;

-- 2025-06-10T15:49:22.891Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747578)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> UserElementString6
-- Column: M_ForecastLine.UserElementString6
-- 2025-06-10T15:49:23.736Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572456,747579,0,548194,TO_TIMESTAMP('2025-06-10 15:49:23.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2025-06-10 15:49:23.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:23.841Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747579 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:23.891Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658)
;

-- 2025-06-10T15:49:23.944Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747579
;

-- 2025-06-10T15:49:23.995Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747579)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> UserElementString7
-- Column: M_ForecastLine.UserElementString7
-- 2025-06-10T15:49:24.887Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572457,747580,0,548194,TO_TIMESTAMP('2025-06-10 15:49:24.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2025-06-10 15:49:24.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:25.021Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747580 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:25.073Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659)
;

-- 2025-06-10T15:49:25.132Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747580
;

-- 2025-06-10T15:49:25.190Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747580)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Kostenstelle
-- Column: M_ForecastLine.C_Activity_ID
-- 2025-06-10T15:49:26.271Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572514,747581,0,548194,TO_TIMESTAMP('2025-06-10 15:49:25.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kostenstelle',10,'de.metas.endcustomer.ic114','Erfassung der zugehörigen Kostenstelle','Y','N','N','N','N','N','N','N','Kostenstelle',TO_TIMESTAMP('2025-06-10 15:49:25.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:26.392Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747581 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:26.443Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005)
;

-- 2025-06-10T15:49:26.516Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747581
;

-- 2025-06-10T15:49:26.589Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747581)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Projekt
-- Column: M_ForecastLine.C_Project_ID
-- 2025-06-10T15:49:27.471Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572528,747582,0,548194,TO_TIMESTAMP('2025-06-10 15:49:26.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project',10,'de.metas.endcustomer.ic114','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2025-06-10 15:49:26.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:27.575Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747582 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:27.628Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2025-06-10T15:49:27.689Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747582
;

-- 2025-06-10T15:49:27.750Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747582)
;

-- Field: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> Werbemassnahme
-- Column: M_ForecastLine.C_Campaign_ID
-- 2025-06-10T15:49:28.633Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572529,747583,0,548194,TO_TIMESTAMP('2025-06-10 15:49:27.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Marketing Campaign',10,'de.metas.endcustomer.ic114','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.','Y','N','N','N','N','N','N','N','Werbemassnahme',TO_TIMESTAMP('2025-06-10 15:49:27.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T15:49:28.735Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747583 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T15:49:28.787Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550)
;

-- 2025-06-10T15:49:28.842Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747583
;

-- 2025-06-10T15:49:28.892Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747583)
;

-- Tab: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114)
-- UI Section: main
-- 2025-06-10T15:57:51.277Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548194,546735,TO_TIMESTAMP('2025-06-10 15:57:50.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-06-10 15:57:50.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-06-10T15:57:51.604Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546735 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main
-- UI Column: 10
-- 2025-06-10T15:58:01.121Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548203,546735,TO_TIMESTAMP('2025-06-10 15:58:00.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-06-10 15:58:00.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main
-- UI Column: 20
-- 2025-06-10T15:58:09.235Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548204,546735,TO_TIMESTAMP('2025-06-10 15:58:08.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-06-10 15:58:08.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10
-- UI Element Group: default
-- 2025-06-10T15:58:48.204Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548203,553100,TO_TIMESTAMP('2025-06-10 15:58:47.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'default',TO_TIMESTAMP('2025-06-10 15:58:47.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_Forecast
-- 2025-06-10T16:00:25.501Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541953,TO_TIMESTAMP('2025-06-10 16:00:24.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.endcustomer.ic114','Y','N','M_Forecast',TO_TIMESTAMP('2025-06-10 16:00:24.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-06-10T16:00:25.608Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541953 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Forecast
-- Table: M_Forecast
-- Key: M_Forecast.M_Forecast_ID
-- 2025-06-10T16:01:11.478Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,11918,0,541953,720,TO_TIMESTAMP('2025-06-10 16:01:11.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.endcustomer.ic114','Y','Y','N',TO_TIMESTAMP('2025-06-10 16:01:11.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_ForecastLine.M_Forecast_ID
-- 2025-06-10T16:01:24.461Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541953, IsExcludeFromZoomTargets='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-06-10 16:01:24.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11939
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> default.Prognose
-- Column: M_ForecastLine.M_Forecast_ID
-- 2025-06-10T16:02:00.557Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747560,0,548194,553100,633946,'F',TO_TIMESTAMP('2025-06-10 16:01:59.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Vorhersagen zu Material-/Produkt-/Artikelentwicklung','Vorhersagen zu Material-/Produkt-/Artikelentwicklung','Y','N','N','Y','N','N','N',0,'Prognose',10,0,0,TO_TIMESTAMP('2025-06-10 16:01:59.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> default.Produkt
-- Column: M_ForecastLine.M_Product_ID
-- 2025-06-10T16:03:37.451Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747562,0,548194,553100,633947,'F',TO_TIMESTAMP('2025-06-10 16:03:36.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',20,0,0,TO_TIMESTAMP('2025-06-10 16:03:36.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> default.Merkmale
-- Column: M_ForecastLine.M_AttributeSetInstance_ID
-- 2025-06-10T16:03:57.210Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747570,0,548194,553100,633948,'F',TO_TIMESTAMP('2025-06-10 16:03:56.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N',0,'Merkmale',30,0,0,TO_TIMESTAMP('2025-06-10 16:03:56.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> default.Menge TU
-- Column: M_ForecastLine.QtyEnteredTU
-- 2025-06-10T16:04:08.717Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747572,0,548194,553100,633949,'F',TO_TIMESTAMP('2025-06-10 16:04:08.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Menge TU',40,0,0,TO_TIMESTAMP('2025-06-10 16:04:08.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> default.Packvorschrift
-- Column: M_ForecastLine.M_HU_PI_Item_Product_ID
-- 2025-06-10T16:04:29.174Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747571,0,548194,553100,633950,'F',TO_TIMESTAMP('2025-06-10 16:04:28.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Packvorschrift',50,0,0,TO_TIMESTAMP('2025-06-10 16:04:28.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> default.Menge
-- Column: M_ForecastLine.Qty
-- 2025-06-10T16:04:48.763Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747559,0,548194,553100,633951,'F',TO_TIMESTAMP('2025-06-10 16:04:48.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','N','N',0,'Menge',60,0,0,TO_TIMESTAMP('2025-06-10 16:04:48.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> default.Maßeinheit
-- Column: M_ForecastLine.C_UOM_ID
-- 2025-06-10T16:05:03.198Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747573,0,548194,553100,633952,'F',TO_TIMESTAMP('2025-06-10 16:05:02.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',70,0,0,TO_TIMESTAMP('2025-06-10 16:05:02.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> default.Calculated Quantity
-- Column: M_ForecastLine.QtyCalculated
-- 2025-06-10T16:05:25.212Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747564,0,548194,553100,633953,'F',TO_TIMESTAMP('2025-06-10 16:05:24.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Calculated Quantity','Y','N','N','Y','N','N','N',0,'Calculated Quantity',80,0,0,TO_TIMESTAMP('2025-06-10 16:05:24.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: QtyCalculated
-- 2025-06-10T16:06:22.907Z
UPDATE AD_Element_Trl SET Description='Berechnete Menge', IsTranslated='Y', Name='Berechnete Menge', PrintName='Berechnete Menge',Updated=TO_TIMESTAMP('2025-06-10 16:06:22.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2500 AND AD_Language='de_DE'
;

-- 2025-06-10T16:06:22.958Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T16:06:30.729Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2500,'de_DE')
;

-- 2025-06-10T16:06:30.781Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2500,'de_DE')
;

-- UI Column: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10
-- UI Element Group: default
-- 2025-06-10T16:07:34.855Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2025-06-10 16:07:34.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553100
;

-- UI Column: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10
-- UI Element Group: forecast
-- 2025-06-10T16:07:55.125Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548203,553101,TO_TIMESTAMP('2025-06-10 16:07:54.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','forecast',20,TO_TIMESTAMP('2025-06-10 16:07:54.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> forecast.Lager
-- Column: M_ForecastLine.M_Warehouse_ID
-- 2025-06-10T16:08:15.122Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747565,0,548194,553101,633954,'F',TO_TIMESTAMP('2025-06-10 16:08:14.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','N','N',0,'Lager',10,0,0,TO_TIMESTAMP('2025-06-10 16:08:14.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> forecast.Geschäftspartner
-- Column: M_ForecastLine.C_BPartner_ID
-- 2025-06-10T16:08:28.866Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747568,0,548194,553101,633955,'F',TO_TIMESTAMP('2025-06-10 16:08:28.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',20,0,0,TO_TIMESTAMP('2025-06-10 16:08:28.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> forecast.Zugesagter Termin
-- Column: M_ForecastLine.DatePromised
-- 2025-06-10T16:08:42.602Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747566,0,548194,553101,633956,'F',TO_TIMESTAMP('2025-06-10 16:08:41.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','N','Y','N','N','N',0,'Zugesagter Termin',30,0,0,TO_TIMESTAMP('2025-06-10 16:08:41.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 10 -> forecast.Kundenbetreuer
-- Column: M_ForecastLine.SalesRep_ID
-- 2025-06-10T16:08:56.233Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747567,0,548194,553101,633957,'F',TO_TIMESTAMP('2025-06-10 16:08:55.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Kundenbetreuer',40,0,0,TO_TIMESTAMP('2025-06-10 16:08:55.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 20
-- UI Element Group: flags
-- 2025-06-10T16:09:12.366Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548204,553102,TO_TIMESTAMP('2025-06-10 16:09:11.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-06-10 16:09:11.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 20 -> flags.Aktiv
-- Column: M_ForecastLine.IsActive
-- 2025-06-10T16:09:29.623Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747561,0,548194,553102,633958,'F',TO_TIMESTAMP('2025-06-10 16:09:28.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-06-10 16:09:28.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 20
-- UI Element Group: org
-- 2025-06-10T16:09:41.175Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548204,553103,TO_TIMESTAMP('2025-06-10 16:09:40.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-06-10 16:09:40.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 20 -> org.Sektion
-- Column: M_ForecastLine.AD_Org_ID
-- 2025-06-10T16:09:58.092Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747558,0,548194,553103,633959,'F',TO_TIMESTAMP('2025-06-10 16:09:57.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-06-10 16:09:57.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position(548194,de.metas.endcustomer.ic114) -> main -> 20 -> org.Mandant
-- Column: M_ForecastLine.AD_Client_ID
-- 2025-06-10T16:10:13.784Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747557,0,548194,553103,633960,'F',TO_TIMESTAMP('2025-06-10 16:10:12.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2025-06-10 16:10:12.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Prognose Position(541900,de.metas.endcustomer.ic114) -> Prognose-Position
-- Table: M_ForecastLine
-- 2025-06-10T16:11:21.726Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-10 16:11:21.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548194
;

-- Name: Prognose Position
-- Action Type: W
-- Window: Prognose Position(541900,de.metas.endcustomer.ic114)
-- 2025-06-10T16:12:02.102Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583719,542225,0,541900,TO_TIMESTAMP('2025-06-10 16:12:01.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.endcustomer.ic114','Prognose Position','Y','N','N','N','N','Prognose Position',TO_TIMESTAMP('2025-06-10 16:12:01.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T16:12:02.365Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542225 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-06-10T16:12:02.418Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542225, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542225)
;

-- 2025-06-10T16:12:02.478Z
/* DDL */  select update_menu_translation_from_ad_element(583719)
;

-- Reordering children of `Warehouse Management`
-- Node name: `Users assigned to workplace`
-- 2025-06-10T16:12:14.065Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542121 AND AD_Tree_ID=10
;

-- Node name: `Workplace`
-- 2025-06-10T16:12:14.115Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Node name: `Warehouse`
-- 2025-06-10T16:12:14.167Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type`
-- 2025-06-10T16:12:14.219Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move`
-- 2025-06-10T16:12:14.269Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme`
-- 2025-06-10T16:12:14.320Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase`
-- 2025-06-10T16:12:14.371Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule`
-- 2025-06-10T16:12:14.423Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast`
-- 2025-06-10T16:12:14.474Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit`
-- 2025-06-10T16:12:14.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-06-10T16:12:14.575Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-06-10T16:12:14.626Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-06-10T16:12:14.678Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory`
-- 2025-06-10T16:12:14.728Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting`
-- 2025-06-10T16:12:14.778Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate`
-- 2025-06-10T16:12:14.828Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- Node name: `Prognose Position`
-- 2025-06-10T16:12:14.879Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542225 AND AD_Tree_ID=10
;

-- Reordering children of `Warehouse Management`
-- Node name: `Users assigned to workplace`
-- 2025-06-10T16:12:19.755Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542121 AND AD_Tree_ID=10
;

-- Node name: `Workplace`
-- 2025-06-10T16:12:19.808Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Node name: `Warehouse`
-- 2025-06-10T16:12:19.862Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type`
-- 2025-06-10T16:12:19.916Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move`
-- 2025-06-10T16:12:19.970Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme`
-- 2025-06-10T16:12:20.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase`
-- 2025-06-10T16:12:20.076Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule`
-- 2025-06-10T16:12:20.133Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast`
-- 2025-06-10T16:12:20.186Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Prognose Position`
-- 2025-06-10T16:12:20.238Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542225 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit`
-- 2025-06-10T16:12:20.289Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-06-10T16:12:20.344Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-06-10T16:12:20.396Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-06-10T16:12:20.449Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory`
-- 2025-06-10T16:12:20.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting`
-- 2025-06-10T16:12:20.554Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate`
-- 2025-06-10T16:12:20.606Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- Column: M_ForecastLine.M_Warehouse_ID
-- 2025-06-10T16:15:45.811Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-06-10 16:15:45.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53411
;

-- Column: M_ForecastLine.DatePromised
-- 2025-06-10T18:10:53.240Z
UPDATE AD_Column SET FilterOperator='B',Updated=TO_TIMESTAMP('2025-06-10 18:10:53.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53412
;

-- Column: M_ForecastLine.DatePromised
-- 2025-06-10T18:13:29.482Z
UPDATE AD_Column SET AD_Reference_ID=15, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-06-10 18:13:29.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53412
;

-- Column: M_ForecastLine.M_Warehouse_ID
-- 2025-06-10T16:17:16.794Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540420, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-06-10 16:17:16.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53411
;

-- Column: M_ForecastLine.M_Product_ID
-- 2025-06-10T16:17:48.203Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2025-06-10 16:17:48.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11941
;

-- Column: M_ForecastLine.M_Product_ID
-- 2025-06-10T16:18:16.381Z
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-06-10 16:18:16.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11941
;

-- Column: M_ForecastLine.AD_Org_ID
-- 2025-06-10T16:19:06.013Z
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2025-06-10 16:19:06.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11937
;

-- Column: M_ForecastLine.IsActive
-- 2025-06-10T16:19:13.699Z
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2025-06-10 16:19:13.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11940
;

-- Column: M_ForecastLine.M_Product_ID
-- 2025-06-10T16:19:22.914Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2025-06-10 16:19:22.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11941
;

-- Column: M_ForecastLine.M_Warehouse_ID
-- 2025-06-10T16:19:31.136Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2025-06-10 16:19:31.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53411
;

-- Column: M_ForecastLine.DatePromised
-- 2025-06-10T16:19:39.536Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2025-06-10 16:19:39.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53412
;

-- Column: M_ForecastLine.IsActive
-- 2025-06-10T16:20:16.461Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2025-06-10 16:20:16.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11940
;

-- Column: M_ForecastLine.AD_Org_ID
-- 2025-06-10T16:21:00.634Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2025-06-10 16:21:00.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11937
;

