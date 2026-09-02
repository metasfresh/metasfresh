-- Run mode: SWING_CLIENT

-- Element: Carrier_ShipmentOrder_Parcel_ID
-- 2025-10-03T13:54:26.902Z
UPDATE AD_Element_Trl SET Name='Versandauftragspaket', PrintName='Versandauftragspaket',Updated=TO_TIMESTAMP('2025-10-03 13:54:26.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584036 AND AD_Language='de_CH'
;

-- 2025-10-03T13:54:26.904Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-03T13:54:27.217Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584036,'de_CH')
;

-- Element: Carrier_ShipmentOrder_Parcel_ID
-- 2025-10-03T13:54:29.934Z
UPDATE AD_Element_Trl SET Name='Versandauftragspaket', PrintName='Versandauftragspaket',Updated=TO_TIMESTAMP('2025-10-03 13:54:29.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584036 AND AD_Language='fr_CH'
;

-- 2025-10-03T13:54:29.935Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-03T13:54:30.196Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584036,'fr_CH')
;

-- Element: Carrier_ShipmentOrder_Parcel_ID
-- 2025-10-03T13:54:32.922Z
UPDATE AD_Element_Trl SET Name='Versandauftragspaket', PrintName='Versandauftragspaket',Updated=TO_TIMESTAMP('2025-10-03 13:54:32.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584036 AND AD_Language='de_DE'
;

-- 2025-10-03T13:54:32.923Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-03T13:54:33.654Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584036,'de_DE')
;

-- 2025-10-03T13:54:33.655Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584036,'de_DE')
;

-- Window: Versandauftragspaket, InternalName=null
-- 2025-10-03T13:55:07.797Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584036,0,541957,TO_TIMESTAMP('2025-10-03 13:55:07.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Versandauftragspaket','N',TO_TIMESTAMP('2025-10-03 13:55:07.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Q',0,0,100)
;

-- 2025-10-03T13:55:07.798Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541957 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-10-03T13:55:07.801Z
/* DDL */  select update_window_translation_from_ad_element(584036)
;

-- 2025-10-03T13:55:07.811Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541957
;

-- 2025-10-03T13:55:07.813Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541957)
;

-- Tab: Versandauftragspaket(541957,D) -> Versandauftragspaket
-- Table: Carrier_ShipmentOrder_Parcel
-- 2025-10-03T13:55:30.365Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584036,0,548458,542535,541957,'Y',TO_TIMESTAMP('2025-10-03 13:55:30.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','Carrier_ShipmentOrder_Parcel','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Versandauftragspaket','N',10,0,TO_TIMESTAMP('2025-10-03 13:55:30.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:30.367Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548458 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-03T13:55:30.368Z
/* DDL */  select update_tab_translation_from_ad_element(584036)
;

-- 2025-10-03T13:55:30.371Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548458)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Mandant
-- Column: Carrier_ShipmentOrder_Parcel.AD_Client_ID
-- 2025-10-03T13:55:42.768Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591129,754751,0,548458,TO_TIMESTAMP('2025-10-03 13:55:42.554000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-10-03 13:55:42.554000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:42.770Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754751 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:42.772Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-03T13:55:43.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754751
;

-- 2025-10-03T13:55:43.135Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754751)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Sektion
-- Column: Carrier_ShipmentOrder_Parcel.AD_Org_ID
-- 2025-10-03T13:55:43.278Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591130,754752,0,548458,TO_TIMESTAMP('2025-10-03 13:55:43.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-10-03 13:55:43.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:43.280Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754752 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:43.281Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-03T13:55:43.399Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754752
;

-- 2025-10-03T13:55:43.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754752)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Aktiv
-- Column: Carrier_ShipmentOrder_Parcel.IsActive
-- 2025-10-03T13:55:43.530Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591133,754753,0,548458,TO_TIMESTAMP('2025-10-03 13:55:43.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-10-03 13:55:43.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:43.531Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754753 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:43.533Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-03T13:55:43.694Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754753
;

-- 2025-10-03T13:55:43.695Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754753)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Versandauftragspaket
-- Column: Carrier_ShipmentOrder_Parcel.Carrier_ShipmentOrder_Parcel_ID
-- 2025-10-03T13:55:43.827Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591136,754754,0,548458,TO_TIMESTAMP('2025-10-03 13:55:43.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Versandauftragspaket',TO_TIMESTAMP('2025-10-03 13:55:43.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:43.829Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754754 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:43.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584036)
;

-- 2025-10-03T13:55:43.832Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754754
;

-- 2025-10-03T13:55:43.833Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754754)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Versandauftrag
-- Column: Carrier_ShipmentOrder_Parcel.Carrier_ShipmentOrder_ID
-- 2025-10-03T13:55:43.954Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591137,754755,0,548458,TO_TIMESTAMP('2025-10-03 13:55:43.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Versandauftrag',TO_TIMESTAMP('2025-10-03 13:55:43.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:43.955Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754755 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:43.956Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584001)
;

-- 2025-10-03T13:55:43.959Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754755
;

-- 2025-10-03T13:55:43.960Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754755)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Sendungsnummer
-- Column: Carrier_ShipmentOrder_Parcel.awb
-- 2025-10-03T13:55:44.096Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591138,754756,0,548458,TO_TIMESTAMP('2025-10-03 13:55:43.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,100,'D','Y','N','N','N','N','N','N','N','Sendungsnummer',TO_TIMESTAMP('2025-10-03 13:55:43.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:44.097Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754756 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:44.099Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577217)
;

-- 2025-10-03T13:55:44.102Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754756
;

-- 2025-10-03T13:55:44.103Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754756)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Weight In Kg
-- Column: Carrier_ShipmentOrder_Parcel.WeightInKg
-- 2025-10-03T13:55:44.232Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591139,754757,0,548458,TO_TIMESTAMP('2025-10-03 13:55:44.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Weight In Kg',TO_TIMESTAMP('2025-10-03 13:55:44.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:44.233Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754757 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:44.235Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577306)
;

-- 2025-10-03T13:55:44.237Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754757
;

-- 2025-10-03T13:55:44.238Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754757)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Paketscheindaten PDF
-- Column: Carrier_ShipmentOrder_Parcel.PdfLabelData
-- 2025-10-03T13:55:44.375Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591140,754758,0,548458,TO_TIMESTAMP('2025-10-03 13:55:44.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D','Y','N','N','N','N','N','N','N','Paketscheindaten PDF',TO_TIMESTAMP('2025-10-03 13:55:44.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:44.377Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754758 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:44.378Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577216)
;

-- 2025-10-03T13:55:44.380Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754758
;

-- 2025-10-03T13:55:44.381Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754758)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Nachverfolgungs-URL
-- Column: Carrier_ShipmentOrder_Parcel.TrackingURL
-- 2025-10-03T13:55:44.509Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591141,754759,0,548458,TO_TIMESTAMP('2025-10-03 13:55:44.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'URL des Spediteurs um Sendungen zu verfolgen',255,'D','Die variable @TrackingNo@ in der URL wird durch die tatsächliche Identifizierungsnummer der Sendung ersetzt.','Y','N','N','N','N','N','N','N','Nachverfolgungs-URL',TO_TIMESTAMP('2025-10-03 13:55:44.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:44.510Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754759 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:44.511Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2127)
;

-- 2025-10-03T13:55:44.514Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754759
;

-- 2025-10-03T13:55:44.515Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754759)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Height In Cm
-- Column: Carrier_ShipmentOrder_Parcel.HeightInCm
-- 2025-10-03T13:55:44.646Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591142,754760,0,548458,TO_TIMESTAMP('2025-10-03 13:55:44.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Height In Cm',TO_TIMESTAMP('2025-10-03 13:55:44.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:44.647Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754760 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:44.648Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577309)
;

-- 2025-10-03T13:55:44.650Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754760
;

-- 2025-10-03T13:55:44.651Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754760)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Length In Cm
-- Column: Carrier_ShipmentOrder_Parcel.LengthInCm
-- 2025-10-03T13:55:44.781Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591143,754761,0,548458,TO_TIMESTAMP('2025-10-03 13:55:44.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Length In Cm',TO_TIMESTAMP('2025-10-03 13:55:44.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:44.782Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754761 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:44.783Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577307)
;

-- 2025-10-03T13:55:44.786Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754761
;

-- 2025-10-03T13:55:44.786Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754761)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Width In Cm
-- Column: Carrier_ShipmentOrder_Parcel.WidthInCm
-- 2025-10-03T13:55:44.915Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591144,754762,0,548458,TO_TIMESTAMP('2025-10-03 13:55:44.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Width In Cm',TO_TIMESTAMP('2025-10-03 13:55:44.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:44.917Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754762 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:44.918Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577308)
;

-- 2025-10-03T13:55:44.920Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754762
;

-- 2025-10-03T13:55:44.921Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754762)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Packstück
-- Column: Carrier_ShipmentOrder_Parcel.M_Package_ID
-- 2025-10-03T13:55:45.054Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591145,754763,0,548458,TO_TIMESTAMP('2025-10-03 13:55:44.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Shipment Package',10,'D','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','N','N','N','N','N','N','Packstück',TO_TIMESTAMP('2025-10-03 13:55:44.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:45.055Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754763 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:45.056Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2410)
;

-- 2025-10-03T13:55:45.060Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754763
;

-- 2025-10-03T13:55:45.061Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754763)
;

-- Field: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> Paketbeschreibung
-- Column: Carrier_ShipmentOrder_Parcel.PackageDescription
-- 2025-10-03T13:55:45.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591146,754764,0,548458,TO_TIMESTAMP('2025-10-03 13:55:45.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Paketbeschreibung',TO_TIMESTAMP('2025-10-03 13:55:45.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:55:45.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754764 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:55:45.194Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577246)
;

-- 2025-10-03T13:55:45.196Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754764
;

-- 2025-10-03T13:55:45.196Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754764)
;

-- Tab: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D)
-- UI Section: main
-- 2025-10-03T13:56:12.131Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548458,546984,TO_TIMESTAMP('2025-10-03 13:56:11.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-03 13:56:11.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-03T13:56:12.133Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546984 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main
-- UI Column: 10
-- 2025-10-03T13:56:15.097Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548510,546984,TO_TIMESTAMP('2025-10-03 13:56:14.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-03 13:56:14.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main
-- UI Column: 20
-- 2025-10-03T13:56:16.155Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548511,546984,TO_TIMESTAMP('2025-10-03 13:56:16.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-10-03 13:56:16.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 10
-- UI Element Group: main
-- 2025-10-03T13:56:27.994Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548510,553606,TO_TIMESTAMP('2025-10-03 13:56:27.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,'primary',TO_TIMESTAMP('2025-10-03 13:56:27.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 10 -> main.Versandauftrag
-- Column: Carrier_ShipmentOrder_Parcel.Carrier_ShipmentOrder_ID
-- 2025-10-03T13:56:43.172Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754755,0,548458,553606,637699,'F',TO_TIMESTAMP('2025-10-03 13:56:42.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Versandauftrag',10,0,0,TO_TIMESTAMP('2025-10-03 13:56:42.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20
-- UI Element Group: org
-- 2025-10-03T13:57:17.541Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548508,553607,TO_TIMESTAMP('2025-10-03 13:57:17.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',30,TO_TIMESTAMP('2025-10-03 13:57:17.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> org.Sektion
-- Column: Carrier_ShipmentOrder.AD_Org_ID
-- 2025-10-03T13:57:28.923Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754705,0,548456,553607,637700,'F',TO_TIMESTAMP('2025-10-03 13:57:28.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-10-03 13:57:28.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> org.Mandant
-- Column: Carrier_ShipmentOrder.AD_Client_ID
-- 2025-10-03T13:57:39.114Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754704,0,548456,553607,637701,'F',TO_TIMESTAMP('2025-10-03 13:57:38.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2025-10-03 13:57:38.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> main.Kundenreferenz
-- Column: Carrier_ShipmentOrder.CustomerReference
-- 2025-10-03T13:58:28.674Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754707,0,548456,553598,637702,'F',TO_TIMESTAMP('2025-10-03 13:58:28.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Kundenreferenz',40,0,0,TO_TIMESTAMP('2025-10-03 13:58:28.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> main.Lieferdatum
-- Column: Carrier_ShipmentOrder.ShipmentDate
-- 2025-10-03T13:58:35.327Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754718,0,548456,553598,637703,'F',TO_TIMESTAMP('2025-10-03 13:58:35.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Lieferdatum',50,0,0,TO_TIMESTAMP('2025-10-03 13:58:35.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> main.Lieferweg
-- Column: Carrier_ShipmentOrder.M_Shipper_ID
-- 2025-10-03T14:00:07.123Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-10-03 14:00:07.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637664
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> main.Produkt
-- Column: Carrier_ShipmentOrder.Carrier_Product
-- 2025-10-03T14:00:07.129Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-03 14:00:07.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637690
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> main.Lieferdatum
-- Column: Carrier_ShipmentOrder.ShipmentDate
-- 2025-10-03T14:00:07.134Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-03 14:00:07.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637703
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> flags.International Delivery
-- Column: Carrier_ShipmentOrder.InternationalDelivery
-- 2025-10-03T14:00:07.139Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-03 14:00:07.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637675
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> main.Kundenreferenz
-- Column: Carrier_ShipmentOrder.CustomerReference
-- 2025-10-03T14:00:07.144Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-03 14:00:07.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637702
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> main.Transport Auftrag
-- Column: Carrier_ShipmentOrder.M_ShipperTransportation_ID
-- 2025-10-03T14:00:07.149Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-03 14:00:07.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637663
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Geschäftspartner
-- Column: Carrier_ShipmentOrder.C_BPartner_ID
-- 2025-10-03T14:00:07.155Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-03 14:00:07.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637674
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> org.Sektion
-- Column: Carrier_ShipmentOrder.AD_Org_ID
-- 2025-10-03T14:00:07.159Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-10-03 14:00:07.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637700
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> org.Mandant
-- Column: Carrier_ShipmentOrder.AD_Client_ID
-- 2025-10-03T14:00:07.164Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-10-03 14:00:07.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637701
;

-- Column: Carrier_ShipmentOrder_Parcel.Carrier_ShipmentOrder_ID
-- 2025-10-03T14:02:41.235Z
UPDATE AD_Column SET IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2025-10-03 14:02:41.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591137
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 10 -> main.Packstück
-- Column: Carrier_ShipmentOrder_Parcel.M_Package_ID
-- 2025-10-03T14:03:10.901Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754763,0,548458,553606,637704,'F',TO_TIMESTAMP('2025-10-03 14:03:10.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Shipment Package','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','N','Y','N','N','N',0,'Packstück',20,0,0,TO_TIMESTAMP('2025-10-03 14:03:10.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 10 -> main.Sendungsnummer
-- Column: Carrier_ShipmentOrder_Parcel.awb
-- 2025-10-03T14:03:23.652Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754756,0,548458,553606,637705,'F',TO_TIMESTAMP('2025-10-03 14:03:23.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Sendungsnummer',30,0,0,TO_TIMESTAMP('2025-10-03 14:03:23.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 10 -> main.Nachverfolgungs-URL
-- Column: Carrier_ShipmentOrder_Parcel.TrackingURL
-- 2025-10-03T14:03:30.825Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754759,0,548458,553606,637706,'F',TO_TIMESTAMP('2025-10-03 14:03:30.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'URL des Spediteurs um Sendungen zu verfolgen','Die variable @TrackingNo@ in der URL wird durch die tatsächliche Identifizierungsnummer der Sendung ersetzt.','Y','N','N','Y','N','N','N',0,'Nachverfolgungs-URL',40,0,0,TO_TIMESTAMP('2025-10-03 14:03:30.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20
-- UI Element Group: other
-- 2025-10-03T14:03:44.412Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548511,553608,TO_TIMESTAMP('2025-10-03 14:03:44.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','other',10,TO_TIMESTAMP('2025-10-03 14:03:44.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20
-- UI Element Group: org
-- 2025-10-03T14:03:57.176Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548511,553609,TO_TIMESTAMP('2025-10-03 14:03:56.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-10-03 14:03:56.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> org.Sektion
-- Column: Carrier_ShipmentOrder_Parcel.AD_Org_ID
-- 2025-10-03T14:04:05.827Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754752,0,548458,553609,637707,'F',TO_TIMESTAMP('2025-10-03 14:04:05.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-10-03 14:04:05.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> org.Mandant
-- Column: Carrier_ShipmentOrder_Parcel.AD_Client_ID
-- 2025-10-03T14:04:24.062Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754751,0,548458,553609,637708,'F',TO_TIMESTAMP('2025-10-03 14:04:23.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2025-10-03 14:04:23.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> other.Length In Cm
-- Column: Carrier_ShipmentOrder_Parcel.LengthInCm
-- 2025-10-03T14:04:37.069Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754761,0,548458,553608,637709,'F',TO_TIMESTAMP('2025-10-03 14:04:36.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Length In Cm',10,0,0,TO_TIMESTAMP('2025-10-03 14:04:36.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> other.Height In Cm
-- Column: Carrier_ShipmentOrder_Parcel.HeightInCm
-- 2025-10-03T14:04:43.552Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754760,0,548458,553608,637710,'F',TO_TIMESTAMP('2025-10-03 14:04:43.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Height In Cm',20,0,0,TO_TIMESTAMP('2025-10-03 14:04:43.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> other.Width In Cm
-- Column: Carrier_ShipmentOrder_Parcel.WidthInCm
-- 2025-10-03T14:04:54.870Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754762,0,548458,553608,637711,'F',TO_TIMESTAMP('2025-10-03 14:04:54.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Width In Cm',30,0,0,TO_TIMESTAMP('2025-10-03 14:04:54.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> other.Weight In Kg
-- Column: Carrier_ShipmentOrder_Parcel.WeightInKg
-- 2025-10-03T14:05:01.461Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754757,0,548458,553608,637712,'F',TO_TIMESTAMP('2025-10-03 14:05:01.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Weight In Kg',40,0,0,TO_TIMESTAMP('2025-10-03 14:05:01.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Window: Versandauftragspaket, InternalName=541957 (Carrier_ShipmentOrder_Parcel)
-- 2025-10-03T14:05:49.242Z
UPDATE AD_Window SET InternalName='541957 (Carrier_ShipmentOrder_Parcel)',Updated=TO_TIMESTAMP('2025-10-03 14:05:49.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541957
;

-- Name: Versandauftragspaket
-- Action Type: W
-- Window: Versandauftragspaket(541957,D)
-- 2025-10-03T14:06:26.088Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584036,542260,0,541957,TO_TIMESTAMP('2025-10-03 14:06:25.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','541957 (Carrier_ShipmentOrder_Parcel)','Y','N','N','N','N','Versandauftragspaket',TO_TIMESTAMP('2025-10-03 14:06:25.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:06:26.089Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542260 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-10-03T14:06:26.092Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542260, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542260)
;

-- 2025-10-03T14:06:26.104Z
/* DDL */  select update_menu_translation_from_ad_element(584036)
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2025-10-03T14:06:26.681Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `QR Code Configuration (QRCode_Configuration)`
-- 2025-10-03T14:06:26.682Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542139 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2025-10-03T14:06:26.682Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2025-10-03T14:06:26.683Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2025-10-03T14:06:26.684Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2025-10-03T14:06:26.685Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2025-10-03T14:06:26.685Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2025-10-03T14:06:26.686Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2025-10-03T14:06:26.687Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2025-10-03T14:06:26.688Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2025-10-03T14:06:26.689Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2025-10-03T14:06:26.690Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2025-10-03T14:06:26.691Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Trace (M_HU_Trace)`
-- 2025-10-03T14:06:26.691Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2025-10-03T14:06:26.692Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2025-10-03T14:06:26.693Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2025-10-03T14:06:26.694Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2025-10-03T14:06:26.694Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2025-10-03T14:06:26.695Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2025-10-03T14:06:26.696Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2025-10-03T14:06:26.697Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2025-10-03T14:06:26.697Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2025-10-03T14:06:26.699Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2025-10-03T14:06:26.699Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-03T14:06:26.700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI HU Manager (MobileUI_HUManager)`
-- 2025-10-03T14:06:26.701Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542163 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-03T14:06:26.702Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-03T14:06:26.702Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2025-10-03T14:06:26.703Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2025-10-03T14:06:26.704Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2025-10-03T14:06:26.705Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Generate new HU QR Codes (de.metas.handlingunits.process.GenerateHUQRCodes)`
-- 2025-10-03T14:06:26.706Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542152 AND AD_Tree_ID=10
;

-- Node name: `Distribution Candidate (DD_Order_Candidate)`
-- 2025-10-03T14:06:26.706Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542164 AND AD_Tree_ID=10
;

-- Node name: `Scannable Code Format (C_ScannableCode_Format)`
-- 2025-10-03T14:06:26.707Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542221 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order Log (Dhl_ShipmentOrder_Log)`
-- 2025-10-03T14:06:26.708Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542218 AND AD_Tree_ID=10
;

-- Node name: `Shipment Order (Carrier_ShipmentOrder)`
-- 2025-10-03T14:06:26.709Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542259 AND AD_Tree_ID=10
;

-- Node name: `Carrier Order Log (Carrier_ShipmentOrder_Log)`
-- 2025-10-03T14:06:26.709Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542258 AND AD_Tree_ID=10
;

-- Node name: `Versandauftragspaket`
-- 2025-10-03T14:06:26.710Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542260 AND AD_Tree_ID=10
;

-- Column: Carrier_ShipmentOrder_Parcel.Carrier_ShipmentOrder_ID
-- 2025-10-03T14:07:33.564Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-03 14:07:33.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591137
;

-- Column: Carrier_ShipmentOrder_Parcel.M_Package_ID
-- 2025-10-03T14:07:58.571Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-03 14:07:58.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591145
;

-- Column: Carrier_ShipmentOrder_Parcel.awb
-- 2025-10-03T14:08:25.529Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-03 14:08:25.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591138
;

-- Tab: Versandauftragspaket(541957,D) -> Shipment Order Item
-- Table: Carrier_ShipmentOrder_Item
-- 2025-10-03T14:10:28.908Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584040,0,548459,542536,541957,'Y',TO_TIMESTAMP('2025-10-03 14:10:28.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','Carrier_ShipmentOrder_Item','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Shipment Order Item','N',20,1,TO_TIMESTAMP('2025-10-03 14:10:28.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:28.909Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548459 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-03T14:10:28.911Z
/* DDL */  select update_tab_translation_from_ad_element(584040)
;

-- 2025-10-03T14:10:28.915Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548459)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Mandant
-- Column: Carrier_ShipmentOrder_Item.AD_Client_ID
-- 2025-10-03T14:10:40.274Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591150,754765,0,548459,TO_TIMESTAMP('2025-10-03 14:10:40.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-10-03 14:10:40.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:40.275Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754765 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:40.277Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-03T14:10:40.356Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754765
;

-- 2025-10-03T14:10:40.357Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754765)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Sektion
-- Column: Carrier_ShipmentOrder_Item.AD_Org_ID
-- 2025-10-03T14:10:40.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591151,754766,0,548459,TO_TIMESTAMP('2025-10-03 14:10:40.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-10-03 14:10:40.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:40.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754766 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:40.494Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-03T14:10:40.554Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754766
;

-- 2025-10-03T14:10:40.555Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754766)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Aktiv
-- Column: Carrier_ShipmentOrder_Item.IsActive
-- 2025-10-03T14:10:40.682Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591154,754767,0,548459,TO_TIMESTAMP('2025-10-03 14:10:40.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-10-03 14:10:40.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:40.683Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754767 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:40.684Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-03T14:10:40.752Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754767
;

-- 2025-10-03T14:10:40.753Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754767)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Shipment Order Item
-- Column: Carrier_ShipmentOrder_Item.Carrier_ShipmentOrder_Item_ID
-- 2025-10-03T14:10:40.881Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591157,754768,0,548459,TO_TIMESTAMP('2025-10-03 14:10:40.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Shipment Order Item',TO_TIMESTAMP('2025-10-03 14:10:40.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:40.882Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754768 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:40.883Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584040)
;

-- 2025-10-03T14:10:40.885Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754768
;

-- 2025-10-03T14:10:40.886Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754768)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Versandauftragspaket
-- Column: Carrier_ShipmentOrder_Item.Carrier_ShipmentOrder_Parcel_ID
-- 2025-10-03T14:10:41.016Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591158,754769,0,548459,TO_TIMESTAMP('2025-10-03 14:10:40.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Versandauftragspaket',TO_TIMESTAMP('2025-10-03 14:10:40.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:41.017Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754769 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:41.018Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584036)
;

-- 2025-10-03T14:10:41.020Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754769
;

-- 2025-10-03T14:10:41.021Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754769)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Produktname
-- Column: Carrier_ShipmentOrder_Item.ProductName
-- 2025-10-03T14:10:41.152Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591159,754770,0,548459,TO_TIMESTAMP('2025-10-03 14:10:41.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name des Produktes',255,'D','Y','N','N','N','N','N','N','N','Produktname',TO_TIMESTAMP('2025-10-03 14:10:41.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:41.153Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754770 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:41.154Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659)
;

-- 2025-10-03T14:10:41.161Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754770
;

-- 2025-10-03T14:10:41.162Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754770)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Gesamtgewicht in kg
-- Column: Carrier_ShipmentOrder_Item.TotalWeightInKg
-- 2025-10-03T14:10:41.302Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591160,754771,0,548459,TO_TIMESTAMP('2025-10-03 14:10:41.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Gesamtgewicht in kg',TO_TIMESTAMP('2025-10-03 14:10:41.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:41.304Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:41.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584041)
;

-- 2025-10-03T14:10:41.307Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754771
;

-- 2025-10-03T14:10:41.307Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754771)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Qty Shipped
-- Column: Carrier_ShipmentOrder_Item.QtyShipped
-- 2025-10-03T14:10:41.437Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591161,754772,0,548459,TO_TIMESTAMP('2025-10-03 14:10:41.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Qty Shipped',TO_TIMESTAMP('2025-10-03 14:10:41.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:41.438Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754772 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:41.439Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579558)
;

-- 2025-10-03T14:10:41.442Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754772
;

-- 2025-10-03T14:10:41.443Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754772)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Maßeinheit
-- Column: Carrier_ShipmentOrder_Item.C_UOM_ID
-- 2025-10-03T14:10:41.565Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591162,754773,0,548459,TO_TIMESTAMP('2025-10-03 14:10:41.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'D','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2025-10-03 14:10:41.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:41.567Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754773 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:41.568Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2025-10-03T14:10:41.611Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754773
;

-- 2025-10-03T14:10:41.612Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754773)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Währung
-- Column: Carrier_ShipmentOrder_Item.C_Currency_ID
-- 2025-10-03T14:10:41.738Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591163,754774,0,548459,TO_TIMESTAMP('2025-10-03 14:10:41.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2025-10-03 14:10:41.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:41.739Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754774 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:41.740Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2025-10-03T14:10:41.755Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754774
;

-- 2025-10-03T14:10:41.756Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754774)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Gesamtpreis
-- Column: Carrier_ShipmentOrder_Item.TotalPrice
-- 2025-10-03T14:10:41.886Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591164,754775,0,548459,TO_TIMESTAMP('2025-10-03 14:10:41.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Gesamtpreis',TO_TIMESTAMP('2025-10-03 14:10:41.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:41.887Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754775 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:41.889Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583960)
;

-- 2025-10-03T14:10:41.891Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754775
;

-- 2025-10-03T14:10:41.892Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754775)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Preis
-- Column: Carrier_ShipmentOrder_Item.Price
-- 2025-10-03T14:10:42.020Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591165,754776,0,548459,TO_TIMESTAMP('2025-10-03 14:10:41.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Preis',10,'D','Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','N','N','N','N','N','N','Preis',TO_TIMESTAMP('2025-10-03 14:10:41.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:42.021Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754776 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:42.023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1416)
;

-- 2025-10-03T14:10:42.028Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754776
;

-- 2025-10-03T14:10:42.029Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754776)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> ArtikelNr
-- Column: Carrier_ShipmentOrder_Item.ArticleValue
-- 2025-10-03T14:10:42.158Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591166,754777,0,548459,TO_TIMESTAMP('2025-10-03 14:10:42.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','ArtikelNr',TO_TIMESTAMP('2025-10-03 14:10:42.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T14:10:42.159Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754777 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T14:10:42.160Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583535)
;

-- 2025-10-03T14:10:42.162Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754777
;

-- 2025-10-03T14:10:42.163Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754777)
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 10 -> main.Versandauftrag
-- Column: Carrier_ShipmentOrder_Parcel.Carrier_ShipmentOrder_ID
-- 2025-10-03T14:11:32.084Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-10-03 14:11:32.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637699
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 10 -> main.Packstück
-- Column: Carrier_ShipmentOrder_Parcel.M_Package_ID
-- 2025-10-03T14:11:32.090Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-03 14:11:32.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637704
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 10 -> main.Sendungsnummer
-- Column: Carrier_ShipmentOrder_Parcel.awb
-- 2025-10-03T14:11:32.096Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-03 14:11:32.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637705
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> other.Length In Cm
-- Column: Carrier_ShipmentOrder_Parcel.LengthInCm
-- 2025-10-03T14:11:32.102Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-03 14:11:32.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637709
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> other.Height In Cm
-- Column: Carrier_ShipmentOrder_Parcel.HeightInCm
-- 2025-10-03T14:11:32.108Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-03 14:11:32.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637710
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> other.Width In Cm
-- Column: Carrier_ShipmentOrder_Parcel.WidthInCm
-- 2025-10-03T14:11:32.114Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-03 14:11:32.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637711
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> other.Weight In Kg
-- Column: Carrier_ShipmentOrder_Parcel.WeightInKg
-- 2025-10-03T14:11:32.119Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-03 14:11:32.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637712
;

-- UI Element: Versandauftragspaket(541957,D) -> Versandauftragspaket(548458,D) -> main -> 20 -> org.Sektion
-- Column: Carrier_ShipmentOrder_Parcel.AD_Org_ID
-- 2025-10-03T14:11:32.124Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-10-03 14:11:32.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637707
;

-- Tab: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D)
-- UI Section: main
-- 2025-10-03T14:12:03.702Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548459,546985,TO_TIMESTAMP('2025-10-03 14:12:03.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-03 14:12:03.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-03T14:12:03.703Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546985 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main
-- UI Column: 10
-- 2025-10-03T14:12:06.385Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548512,546985,TO_TIMESTAMP('2025-10-03 14:12:06.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-03 14:12:06.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10
-- UI Element Group: main
-- 2025-10-03T14:12:14.792Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548512,553610,TO_TIMESTAMP('2025-10-03 14:12:14.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-10-03 14:12:14.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Währung
-- Column: Carrier_ShipmentOrder_Item.C_Currency_ID
-- 2025-10-03T14:13:40.904Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754774,0,548459,553610,637713,'F',TO_TIMESTAMP('2025-10-03 14:13:40.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N',0,'Währung',10,0,0,TO_TIMESTAMP('2025-10-03 14:13:40.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.ArtikelNr
-- Column: Carrier_ShipmentOrder_Item.ArticleValue
-- 2025-10-03T14:13:48.779Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754777,0,548459,553610,637714,'F',TO_TIMESTAMP('2025-10-03 14:13:48.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'ArtikelNr',20,0,0,TO_TIMESTAMP('2025-10-03 14:13:48.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Gesamtpreis
-- Column: Carrier_ShipmentOrder_Item.TotalPrice
-- 2025-10-03T14:13:59.438Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754775,0,548459,553610,637715,'F',TO_TIMESTAMP('2025-10-03 14:13:59.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Gesamtpreis',30,0,0,TO_TIMESTAMP('2025-10-03 14:13:59.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Preis
-- Column: Carrier_ShipmentOrder_Item.Price
-- 2025-10-03T14:14:26.669Z
UPDATE AD_UI_Element SET AD_Field_ID=754776, Description='Preis', Help='Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.', Name='Preis',Updated=TO_TIMESTAMP('2025-10-03 14:14:26.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637714
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Maßeinheit
-- Column: Carrier_ShipmentOrder_Item.C_UOM_ID
-- 2025-10-03T14:14:43.458Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754773,0,548459,553610,637716,'F',TO_TIMESTAMP('2025-10-03 14:14:43.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',40,0,0,TO_TIMESTAMP('2025-10-03 14:14:43.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Qty Shipped
-- Column: Carrier_ShipmentOrder_Item.QtyShipped
-- 2025-10-03T14:15:30.670Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754772,0,548459,553610,637717,'F',TO_TIMESTAMP('2025-10-03 14:15:30.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Qty Shipped',50,0,0,TO_TIMESTAMP('2025-10-03 14:15:30.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Gesamtgewicht in kg
-- Column: Carrier_ShipmentOrder_Item.TotalWeightInKg
-- 2025-10-03T14:15:42.320Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754771,0,548459,553610,637718,'F',TO_TIMESTAMP('2025-10-03 14:15:41.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Gesamtgewicht in kg',60,0,0,TO_TIMESTAMP('2025-10-03 14:15:41.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Produktname
-- Column: Carrier_ShipmentOrder_Item.ProductName
-- 2025-10-03T14:16:02.157Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754770,0,548459,553610,637719,'F',TO_TIMESTAMP('2025-10-03 14:16:01.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name des Produktes','Y','N','N','Y','N','N','N',0,'Produktname',10,0,0,TO_TIMESTAMP('2025-10-03 14:16:01.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Währung
-- Column: Carrier_ShipmentOrder_Item.C_Currency_ID
-- 2025-10-03T14:16:05.064Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2025-10-03 14:16:05.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637713
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Preis
-- Column: Carrier_ShipmentOrder_Item.Price
-- 2025-10-03T14:16:10.169Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2025-10-03 14:16:10.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637714
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Gesamtpreis
-- Column: Carrier_ShipmentOrder_Item.TotalPrice
-- 2025-10-03T14:16:12.902Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2025-10-03 14:16:12.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637715
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Maßeinheit
-- Column: Carrier_ShipmentOrder_Item.C_UOM_ID
-- 2025-10-03T14:16:16.779Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2025-10-03 14:16:16.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637716
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Qty Shipped
-- Column: Carrier_ShipmentOrder_Item.QtyShipped
-- 2025-10-03T14:16:19.415Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2025-10-03 14:16:19.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637717
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Gesamtgewicht in kg
-- Column: Carrier_ShipmentOrder_Item.TotalWeightInKg
-- 2025-10-03T14:16:23.304Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2025-10-03 14:16:23.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637718
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.ArtikelNr
-- Column: Carrier_ShipmentOrder_Item.ArticleValue
-- 2025-10-03T14:16:34.919Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754777,0,548459,553610,637720,'F',TO_TIMESTAMP('2025-10-03 14:16:34.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'ArtikelNr',20,0,0,TO_TIMESTAMP('2025-10-03 14:16:34.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

