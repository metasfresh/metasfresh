-- Run mode: SWING_CLIENT

-- Table: M_InventoryLine_HU
-- 2025-09-29T20:39:04.617Z
UPDATE AD_Table SET Name='Inventory Line Handling Units',Updated=TO_TIMESTAMP('2025-09-29 20:39:04.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=541345
;

-- 2025-09-29T20:39:05.323Z
UPDATE AD_Table_Trl trl SET Name='Inventory Line Handling Units' WHERE AD_Table_ID=541345 AND AD_Language='de_DE'
;

-- Element: M_InventoryLine_HU_ID
-- 2025-09-29T20:40:26.211Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inventurpositions-HUs', PrintName='Inventurpositions-HUs',Updated=TO_TIMESTAMP('2025-09-29 20:40:26.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=576581 AND AD_Language='de_CH'
;

-- 2025-09-29T20:40:26.213Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-29T20:40:26.797Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576581,'de_CH')
;

-- Element: M_InventoryLine_HU_ID
-- 2025-09-29T20:40:32.755Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inventurpositions-HUs', PrintName='Inventurpositions-HUs',Updated=TO_TIMESTAMP('2025-09-29 20:40:32.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=576581 AND AD_Language='de_DE'
;

-- 2025-09-29T20:40:32.756Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-29T20:40:33.976Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(576581,'de_DE')
;

-- 2025-09-29T20:40:33.977Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576581,'de_DE')
;

-- Element: M_InventoryLine_HU_ID
-- 2025-09-29T20:40:54.183Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inventory Line Handling Units', PrintName='Inventory Line Handling Units',Updated=TO_TIMESTAMP('2025-09-29 20:40:54.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=576581 AND AD_Language='en_US'
;

-- 2025-09-29T20:40:54.184Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-29T20:40:54.499Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576581,'en_US')
;

-- Window: Inventurpositions-HUs, InternalName=inventoryLineHU
-- 2025-09-29T20:41:32.102Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,576581,0,541951,TO_TIMESTAMP('2025-09-29 20:41:31.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','inventoryLineHU','Y','N','N','N','N','N','N','Y','Inventurpositions-HUs','N',TO_TIMESTAMP('2025-09-29 20:41:31.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-09-29T20:41:32.105Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541951 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-09-29T20:41:32.108Z
/* DDL */  select update_window_translation_from_ad_element(576581)
;

-- 2025-09-29T20:41:32.129Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541951
;

-- 2025-09-29T20:41:32.134Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541951)
;

-- Tab: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs
-- Table: M_InventoryLine_HU
-- 2025-09-29T20:42:12.837Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,576581,0,548441,541345,541951,'Y',TO_TIMESTAMP('2025-09-29 20:42:12.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','N','N','A','M_InventoryLine_HU','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Inventurpositions-HUs','N',10,0,TO_TIMESTAMP('2025-09-29 20:42:12.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:12.840Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548441 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-09-29T20:42:12.844Z
/* DDL */  select update_tab_translation_from_ad_element(576581)
;

-- 2025-09-29T20:42:12.848Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548441)
;

-- Tab: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs
-- Table: M_InventoryLine_HU
-- 2025-09-29T20:42:25.546Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-09-29 20:42:25.546000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548441
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Mandant
-- Column: M_InventoryLine_HU.AD_Client_ID
-- 2025-09-29T20:42:43.741Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567655,754214,0,548441,TO_TIMESTAMP('2025-09-29 20:42:43.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.handlingunits','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-09-29 20:42:43.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:43.746Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754214 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:43.751Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-09-29T20:42:44.537Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754214
;

-- 2025-09-29T20:42:44.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754214)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Sektion
-- Column: M_InventoryLine_HU.AD_Org_ID
-- 2025-09-29T20:42:44.699Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567656,754215,0,548441,TO_TIMESTAMP('2025-09-29 20:42:44.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.handlingunits','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-09-29 20:42:44.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:44.701Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754215 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:44.703Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-09-29T20:42:45.191Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754215
;

-- 2025-09-29T20:42:45.194Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754215)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Aktiv
-- Column: M_InventoryLine_HU.IsActive
-- 2025-09-29T20:42:45.328Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567659,754216,0,548441,TO_TIMESTAMP('2025-09-29 20:42:45.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.handlingunits','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-09-29 20:42:45.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:45.329Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754216 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:45.331Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-09-29T20:42:45.759Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754216
;

-- 2025-09-29T20:42:45.761Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754216)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Inventurpositions-HUs
-- Column: M_InventoryLine_HU.M_InventoryLine_HU_ID
-- 2025-09-29T20:42:45.899Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567662,754217,0,548441,TO_TIMESTAMP('2025-09-29 20:42:45.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Inventurpositions-HUs',TO_TIMESTAMP('2025-09-29 20:42:45.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:45.902Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754217 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:45.904Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576581)
;

-- 2025-09-29T20:42:45.906Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754217
;

-- 2025-09-29T20:42:45.906Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754217)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Inventur-Position
-- Column: M_InventoryLine_HU.M_InventoryLine_ID
-- 2025-09-29T20:42:46.044Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567663,754218,0,548441,TO_TIMESTAMP('2025-09-29 20:42:45.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Eindeutige Position in einem Inventurdokument',10,'de.metas.handlingunits','"Inventur-Position" bezeichnet die Poition in dem Inventurdokument (wenn zutreffend) für diese Transaktion.','Y','N','N','N','N','N','N','N','Inventur-Position',TO_TIMESTAMP('2025-09-29 20:42:45.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:46.046Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754218 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:46.049Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1028)
;

-- 2025-09-29T20:42:46.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754218
;

-- 2025-09-29T20:42:46.060Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754218)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Handling Unit
-- Column: M_InventoryLine_HU.M_HU_ID
-- 2025-09-29T20:42:46.184Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567664,754219,0,548441,TO_TIMESTAMP('2025-09-29 20:42:46.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Handling Unit',TO_TIMESTAMP('2025-09-29 20:42:46.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:46.185Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754219 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:46.187Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542140)
;

-- 2025-09-29T20:42:46.209Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754219
;

-- 2025-09-29T20:42:46.211Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754219)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Zählmenge
-- Column: M_InventoryLine_HU.QtyCount
-- 2025-09-29T20:42:46.342Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567665,754220,0,548441,TO_TIMESTAMP('2025-09-29 20:42:46.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gezählte Menge',10,'de.metas.handlingunits','"Zählmenge" zeigt die tatsächlich ermittelte Menge für ein Produkt im Bestand an.','Y','N','N','N','N','N','N','N','Zählmenge',TO_TIMESTAMP('2025-09-29 20:42:46.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:46.344Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754220 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:46.346Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1049)
;

-- 2025-09-29T20:42:46.350Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754220
;

-- 2025-09-29T20:42:46.352Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754220)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Maßeinheit
-- Column: M_InventoryLine_HU.C_UOM_ID
-- 2025-09-29T20:42:46.473Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567666,754221,0,548441,TO_TIMESTAMP('2025-09-29 20:42:46.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'de.metas.handlingunits','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2025-09-29 20:42:46.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:46.475Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754221 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:46.476Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2025-09-29T20:42:46.544Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754221
;

-- 2025-09-29T20:42:46.545Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754221)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Buchmenge
-- Column: M_InventoryLine_HU.QtyBook
-- 2025-09-29T20:42:46.677Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567667,754222,0,548441,TO_TIMESTAMP('2025-09-29 20:42:46.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Buchmenge',10,'de.metas.handlingunits','"Buchmenge" zeigt die im System gespeicherte Menge für ein Produkt im Bestand an.','Y','N','N','N','N','N','N','N','Buchmenge',TO_TIMESTAMP('2025-09-29 20:42:46.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:46.679Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754222 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:46.680Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1048)
;

-- 2025-09-29T20:42:46.683Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754222
;

-- 2025-09-29T20:42:46.684Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754222)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Internal Use Qty
-- Column: M_InventoryLine_HU.QtyInternalUse
-- 2025-09-29T20:42:46.816Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568305,754223,0,548441,TO_TIMESTAMP('2025-09-29 20:42:46.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Internal Use Quantity removed from Inventory',10,'de.metas.handlingunits','Quantity of product inventory used internally (positive if taken out - negative if returned)','Y','N','N','N','N','N','N','N','Internal Use Qty',TO_TIMESTAMP('2025-09-29 20:42:46.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:46.818Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754223 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:46.820Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2654)
;

-- 2025-09-29T20:42:46.824Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754223
;

-- 2025-09-29T20:42:46.826Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754223)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Inventur
-- Column: M_InventoryLine_HU.M_Inventory_ID
-- 2025-09-29T20:42:46.962Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571518,754224,0,548441,TO_TIMESTAMP('2025-09-29 20:42:46.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Parameter für eine physische Inventur',10,'de.metas.handlingunits','Bezeichnet die eindeutigen Parameter für eine physische Inventur','Y','N','N','N','N','N','N','N','Inventur',TO_TIMESTAMP('2025-09-29 20:42:46.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:46.963Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754224 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:46.965Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1027)
;

-- 2025-09-29T20:42:46.970Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754224
;

-- 2025-09-29T20:42:46.971Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754224)
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Rendered QR Code
-- Column: M_InventoryLine_HU.RenderedQRCode
-- 2025-09-29T20:42:47.103Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588188,754225,0,548441,TO_TIMESTAMP('2025-09-29 20:42:46.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.',9999999,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Rendered QR Code',TO_TIMESTAMP('2025-09-29 20:42:46.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:42:47.105Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754225 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:42:47.106Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580597)
;

-- 2025-09-29T20:42:47.113Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754225
;

-- 2025-09-29T20:42:47.114Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754225)
;

-- Tab: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits)
-- UI Section: main
-- 2025-09-29T20:43:06.585Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548441,546963,TO_TIMESTAMP('2025-09-29 20:43:06.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-09-29 20:43:06.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-09-29T20:43:06.587Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546963 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main
-- UI Column: 10
-- 2025-09-29T20:43:11.346Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548480,546963,TO_TIMESTAMP('2025-09-29 20:43:11.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-29 20:43:11.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main
-- UI Column: 20
-- 2025-09-29T20:43:12.984Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548481,546963,TO_TIMESTAMP('2025-09-29 20:43:12.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-09-29 20:43:12.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10
-- UI Element Group: primary
-- 2025-09-29T20:43:23.642Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548480,553552,TO_TIMESTAMP('2025-09-29 20:43:23.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','primary',10,'primary',TO_TIMESTAMP('2025-09-29 20:43:23.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> primary.Inventur
-- Column: M_InventoryLine_HU.M_Inventory_ID
-- 2025-09-29T20:43:47.853Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754224,0,548441,553552,637307,'F',TO_TIMESTAMP('2025-09-29 20:43:47.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Parameter für eine physische Inventur','Bezeichnet die eindeutigen Parameter für eine physische Inventur','Y','N','Y','N','N','Inventur',10,0,0,TO_TIMESTAMP('2025-09-29 20:43:47.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> primary.Inventurpositions-HUs
-- Column: M_InventoryLine_HU.M_InventoryLine_HU_ID
-- 2025-09-29T20:43:54.885Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754217,0,548441,553552,637308,'F',TO_TIMESTAMP('2025-09-29 20:43:54.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Inventurpositions-HUs',20,0,0,TO_TIMESTAMP('2025-09-29 20:43:54.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10
-- UI Element Group: qty
-- 2025-09-29T20:44:16.911Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548480,553553,TO_TIMESTAMP('2025-09-29 20:44:16.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','qty',20,TO_TIMESTAMP('2025-09-29 20:44:16.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> qty.Maßeinheit
-- Column: M_InventoryLine_HU.C_UOM_ID
-- 2025-09-29T20:44:40.270Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754221,0,548441,553553,637309,'F',TO_TIMESTAMP('2025-09-29 20:44:37.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',10,0,0,TO_TIMESTAMP('2025-09-29 20:44:37.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> qty.Buchmenge
-- Column: M_InventoryLine_HU.QtyBook
-- 2025-09-29T20:44:48.134Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754222,0,548441,553553,637310,'F',TO_TIMESTAMP('2025-09-29 20:44:47.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Buchmenge','"Buchmenge" zeigt die im System gespeicherte Menge für ein Produkt im Bestand an.','Y','N','Y','N','N','Buchmenge',20,0,0,TO_TIMESTAMP('2025-09-29 20:44:47.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> qty.Zählmenge
-- Column: M_InventoryLine_HU.QtyCount
-- 2025-09-29T20:44:54.624Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754220,0,548441,553553,637311,'F',TO_TIMESTAMP('2025-09-29 20:44:54.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gezählte Menge','"Zählmenge" zeigt die tatsächlich ermittelte Menge für ein Produkt im Bestand an.','Y','N','Y','N','N','Zählmenge',30,0,0,TO_TIMESTAMP('2025-09-29 20:44:54.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> qty.Internal Use Qty
-- Column: M_InventoryLine_HU.QtyInternalUse
-- 2025-09-29T20:45:01.793Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754223,0,548441,553553,637312,'F',TO_TIMESTAMP('2025-09-29 20:45:01.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Internal Use Quantity removed from Inventory','Quantity of product inventory used internally (positive if taken out - negative if returned)','Y','N','Y','N','N','Internal Use Qty',40,0,0,TO_TIMESTAMP('2025-09-29 20:45:01.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10
-- UI Element Group: hu
-- 2025-09-29T20:45:07.150Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548480,553554,TO_TIMESTAMP('2025-09-29 20:45:06.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','hu',30,TO_TIMESTAMP('2025-09-29 20:45:06.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> hu.Handling Unit
-- Column: M_InventoryLine_HU.M_HU_ID
-- 2025-09-29T20:45:17.583Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754219,0,548441,553554,637313,'F',TO_TIMESTAMP('2025-09-29 20:45:17.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Handling Unit',10,0,0,TO_TIMESTAMP('2025-09-29 20:45:17.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> hu.Rendered QR Code
-- Column: M_InventoryLine_HU.RenderedQRCode
-- 2025-09-29T20:45:25.237Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754225,0,548441,553554,637314,'F',TO_TIMESTAMP('2025-09-29 20:45:24.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.','Y','N','Y','N','N','Rendered QR Code',20,0,0,TO_TIMESTAMP('2025-09-29 20:45:24.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 20
-- UI Element Group: org&client
-- 2025-09-29T20:45:35.695Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548481,553555,TO_TIMESTAMP('2025-09-29 20:45:35.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org&client',10,TO_TIMESTAMP('2025-09-29 20:45:35.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 20 -> org&client.Sektion
-- Column: M_InventoryLine_HU.AD_Org_ID
-- 2025-09-29T20:45:46.100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754215,0,548441,553555,637315,'F',TO_TIMESTAMP('2025-09-29 20:45:45.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2025-09-29 20:45:45.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 20 -> org&client.Mandant
-- Column: M_InventoryLine_HU.AD_Client_ID
-- 2025-09-29T20:45:52.421Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754214,0,548441,553555,637316,'F',TO_TIMESTAMP('2025-09-29 20:45:52.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2025-09-29 20:45:52.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> primary.Inventur
-- Column: M_InventoryLine_HU.M_Inventory_ID
-- 2025-09-29T20:46:12.138Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-09-29 20:46:12.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637307
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> primary.Inventur-Position
-- Column: M_InventoryLine_HU.M_InventoryLine_ID
-- 2025-09-29T20:46:25.160Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754218,0,548441,553552,637317,'F',TO_TIMESTAMP('2025-09-29 20:46:23.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Eindeutige Position in einem Inventurdokument','"Inventur-Position" bezeichnet die Poition in dem Inventurdokument (wenn zutreffend) für diese Transaktion.','Y','N','Y','N','N','Inventur-Position',30,0,0,TO_TIMESTAMP('2025-09-29 20:46:23.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> primary.Inventurpositions-HUs
-- Column: M_InventoryLine_HU.M_InventoryLine_HU_ID
-- 2025-09-29T20:46:33.092Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637308
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> primary.Inventur-Position
-- Column: M_InventoryLine_HU.M_InventoryLine_ID
-- 2025-09-29T20:46:48.035Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-29 20:46:48.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637317
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> qty.Maßeinheit
-- Column: M_InventoryLine_HU.C_UOM_ID
-- 2025-09-29T20:46:48.043Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-29 20:46:48.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637309
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> qty.Buchmenge
-- Column: M_InventoryLine_HU.QtyBook
-- 2025-09-29T20:46:48.052Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-29 20:46:48.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637310
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> qty.Zählmenge
-- Column: M_InventoryLine_HU.QtyCount
-- 2025-09-29T20:46:48.059Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-29 20:46:48.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637311
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 10 -> hu.Handling Unit
-- Column: M_InventoryLine_HU.M_HU_ID
-- 2025-09-29T20:46:48.066Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-09-29 20:46:48.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637313
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Inventur
-- Column: M_InventoryLine_HU.M_Inventory_ID
-- 2025-09-29T20:47:02.298Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2025-09-29 20:47:02.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754224
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Inventur-Position
-- Column: M_InventoryLine_HU.M_InventoryLine_ID
-- 2025-09-29T20:47:09.340Z
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2025-09-29 20:47:09.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754218
;

-- Column: M_InventoryLine_HU.M_Inventory_ID
-- 2025-09-29T20:47:37.737Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-09-29 20:47:37.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=571518
;

-- Column: M_InventoryLine_HU.M_InventoryLine_ID
-- 2025-09-29T20:47:42.800Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-09-29 20:47:42.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=567663
;

-- Column: M_InventoryLine_HU.RenderedQRCode
-- 2025-09-29T20:47:53.032Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-09-29 20:47:53.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=588188
;

-- Column: M_InventoryLine_HU.M_HU_ID
-- 2025-09-29T20:48:01.757Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-09-29 20:48:01.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=567664
;

-- Name: Inventurpositions-HUs
-- Action Type: W
-- Window: Inventurpositions-HUs(541951,de.metas.handlingunits)
-- 2025-09-29T20:49:35.129Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,576581,542254,0,541951,TO_TIMESTAMP('2025-09-29 20:49:34.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','inventoryLineHU','Y','N','N','N','N','Inventurpositions-HUs',TO_TIMESTAMP('2025-09-29 20:49:34.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:49:35.135Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542254 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-09-29T20:49:35.145Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542254, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542254)
;

-- 2025-09-29T20:49:35.168Z
/* DDL */  select update_menu_translation_from_ad_element(576581)
;

-- Reordering children of `Warehouse Management`
-- Node name: `Users assigned to workplace (C_Workplace_User_Assign)`
-- 2025-09-29T20:49:35.775Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542121 AND AD_Tree_ID=10
;

-- Node name: `Workplace (C_Workplace)`
-- 2025-09-29T20:49:35.776Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Node name: `Warehouse (M_Warehouse)`
-- 2025-09-29T20:49:35.777Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type (M_Warehouse_Type)`
-- 2025-09-29T20:49:35.778Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move (M_Movement)`
-- 2025-09-29T20:49:35.780Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme (M_Inventory)`
-- 2025-09-29T20:49:35.780Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase (Fresh_QtyOnHand)`
-- 2025-09-29T20:49:35.781Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule (MD_Candidate)`
-- 2025-09-29T20:49:35.783Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast (M_Forecast)`
-- 2025-09-29T20:49:35.784Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Forecast Lines (M_ForecastLine)`
-- 2025-09-29T20:49:35.784Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542225 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit (MD_Cockpit)`
-- 2025-09-29T20:49:35.785Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-09-29T20:49:35.786Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-09-29T20:49:35.787Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-09-29T20:49:35.788Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory (M_Inventory)`
-- 2025-09-29T20:49:35.789Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting (M_InventoryLine)`
-- 2025-09-29T20:49:35.790Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate (M_Inventory_Candidate)`
-- 2025-09-29T20:49:35.791Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule Quantity Details (MD_Candidate_QtyDetails)`
-- 2025-09-29T20:49:35.792Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542226 AND AD_Tree_ID=10
;

-- Node name: `Inventurpositions-HUs`
-- 2025-09-29T20:49:35.793Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542254 AND AD_Tree_ID=10
;

-- Reordering children of `Warehouse Management`
-- Node name: `Users assigned to workplace (C_Workplace_User_Assign)`
-- 2025-09-29T20:49:40.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542121 AND AD_Tree_ID=10
;

-- Node name: `Workplace (C_Workplace)`
-- 2025-09-29T20:49:40.351Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Node name: `Warehouse (M_Warehouse)`
-- 2025-09-29T20:49:40.351Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type (M_Warehouse_Type)`
-- 2025-09-29T20:49:40.352Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move (M_Movement)`
-- 2025-09-29T20:49:40.353Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme (M_Inventory)`
-- 2025-09-29T20:49:40.354Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase (Fresh_QtyOnHand)`
-- 2025-09-29T20:49:40.355Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule (MD_Candidate)`
-- 2025-09-29T20:49:40.356Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast (M_Forecast)`
-- 2025-09-29T20:49:40.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Forecast Lines (M_ForecastLine)`
-- 2025-09-29T20:49:40.359Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542225 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit (MD_Cockpit)`
-- 2025-09-29T20:49:40.360Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-09-29T20:49:40.362Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-09-29T20:49:40.363Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-09-29T20:49:40.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory (M_Inventory)`
-- 2025-09-29T20:49:40.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting (M_InventoryLine)`
-- 2025-09-29T20:49:40.365Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventurpositions-HUs`
-- 2025-09-29T20:49:40.366Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542254 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate (M_Inventory_Candidate)`
-- 2025-09-29T20:49:40.367Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule Quantity Details (MD_Candidate_QtyDetails)`
-- 2025-09-29T20:49:40.367Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542226 AND AD_Tree_ID=10
;

