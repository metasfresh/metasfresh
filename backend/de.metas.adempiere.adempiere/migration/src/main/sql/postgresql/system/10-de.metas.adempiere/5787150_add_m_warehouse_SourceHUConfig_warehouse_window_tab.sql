
-- Tab: Lager und Lagerort(139,D) -> Quell-HU Konfiguration
-- Table: M_Warehouse_SourceHUConfig
-- 2026-02-05T21:27:43.798Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,591961,584499,0,548984,542577,139,'Y',TO_TIMESTAMP('2026-02-05 21:27:43.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Konfiguration erlaubt für Lager, die eingehende HUs als Quell-HU annehmen, diese nach gegebenen Kriterien einzugrenzen.','D','N','N','A','M_Warehouse_SourceHUConfig','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Quell-HU Konfiguration','N',70,1,TO_TIMESTAMP('2026-02-05 21:27:43.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T21:27:43.807Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548984 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-05T21:27:43.813Z
/* DDL */  select update_tab_translation_from_ad_element(584499)
;

-- 2026-02-05T21:27:43.826Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548984)
;

-- Field: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> Mandant
-- Column: M_Warehouse_SourceHUConfig.AD_Client_ID
-- 2026-02-05T21:41:18.924Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591953,771932,0,548984,TO_TIMESTAMP('2026-02-05 21:41:18.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2026-02-05 21:41:18.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T21:41:18.933Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771932 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T21:41:18.939Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-05T21:41:19.573Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771932
;

-- 2026-02-05T21:41:19.577Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771932)
;

-- Field: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> Sektion
-- Column: M_Warehouse_SourceHUConfig.AD_Org_ID
-- 2026-02-05T21:41:19.684Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591954,771933,0,548984,TO_TIMESTAMP('2026-02-05 21:41:19.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2026-02-05 21:41:19.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T21:41:19.688Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T21:41:19.689Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-05T21:41:20.076Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771933
;

-- 2026-02-05T21:41:20.078Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771933)
;

-- Field: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> Aktiv
-- Column: M_Warehouse_SourceHUConfig.IsActive
-- 2026-02-05T21:41:20.174Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591957,771934,0,548984,TO_TIMESTAMP('2026-02-05 21:41:20.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-02-05 21:41:20.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T21:41:20.180Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771934 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T21:41:20.184Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-05T21:41:20.358Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771934
;

-- 2026-02-05T21:41:20.359Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771934)
;

-- Field: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> Quell-HU Konfiguration
-- Column: M_Warehouse_SourceHUConfig.M_Warehouse_SourceHUConfig_ID
-- 2026-02-05T21:41:20.467Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591960,771935,0,548984,TO_TIMESTAMP('2026-02-05 21:41:20.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Konfiguration erlaubt für Lager, die eingehende HUs als Quell-HU annehmen, diese nach gegebenen Kriterien einzugrenzen.',10,'D','Y','N','N','N','N','N','N','N','Quell-HU Konfiguration',TO_TIMESTAMP('2026-02-05 21:41:20.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T21:41:20.474Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771935 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T21:41:20.478Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584499)
;

-- 2026-02-05T21:41:20.483Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771935
;

-- 2026-02-05T21:41:20.484Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771935)
;

-- Field: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> Lager
-- Column: M_Warehouse_SourceHUConfig.M_Warehouse_ID
-- 2026-02-05T21:41:20.573Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591961,771936,0,548984,TO_TIMESTAMP('2026-02-05 21:41:20.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',10,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2026-02-05 21:41:20.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T21:41:20.581Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771936 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T21:41:20.587Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2026-02-05T21:41:20.621Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771936
;

-- 2026-02-05T21:41:20.622Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771936)
;

-- Field: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> Produkt Kategorie
-- Column: M_Warehouse_SourceHUConfig.M_Product_Category_ID
-- 2026-02-05T21:41:20.722Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591962,771937,0,548984,TO_TIMESTAMP('2026-02-05 21:41:20.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes',10,'D','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','N','N','N','N','N','Produkt Kategorie',TO_TIMESTAMP('2026-02-05 21:41:20.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T21:41:20.729Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771937 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T21:41:20.735Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453)
;

-- 2026-02-05T21:41:20.757Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771937
;

-- 2026-02-05T21:41:20.763Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771937)
;

-- Tab: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D)
-- UI Section: main
-- 2026-02-05T21:49:04.340Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548984,547506,TO_TIMESTAMP('2026-02-05 21:49:04.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-05 21:49:04.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-05T21:49:04.373Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547506 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> main
-- UI Column: 10
-- 2026-02-05T21:49:09.897Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549165,547506,TO_TIMESTAMP('2026-02-05 21:49:09.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-05 21:49:09.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> main -> 10
-- UI Element Group: main
-- 2026-02-05T21:49:15.962Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549165,554794,TO_TIMESTAMP('2026-02-05 21:49:15.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-02-05 21:49:15.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> main -> 10 -> main.Produkt Kategorie
-- Column: M_Warehouse_SourceHUConfig.M_Product_Category_ID
-- 2026-02-05T21:49:35.578Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771937,0,548984,554794,646858,'F',TO_TIMESTAMP('2026-02-05 21:49:35.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','Y','N','N','N',0,'Produkt Kategorie',10,0,0,TO_TIMESTAMP('2026-02-05 21:49:35.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> main -> 10 -> main.Aktiv
-- Column: M_Warehouse_SourceHUConfig.IsActive
-- 2026-02-05T21:49:46.124Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771934,0,548984,554794,646859,'F',TO_TIMESTAMP('2026-02-05 21:49:46.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2026-02-05 21:49:46.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> main -> 10 -> main.Sektion
-- Column: M_Warehouse_SourceHUConfig.AD_Org_ID
-- 2026-02-05T21:49:59.407Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771933,0,548984,554794,646860,'F',TO_TIMESTAMP('2026-02-05 21:49:59.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',30,0,0,TO_TIMESTAMP('2026-02-05 21:49:59.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> main -> 10 -> main.Produkt Kategorie
-- Column: M_Warehouse_SourceHUConfig.M_Product_Category_ID
-- 2026-02-05T21:50:09.526Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-02-05 21:50:09.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646858
;

-- UI Element: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> main -> 10 -> main.Aktiv
-- Column: M_Warehouse_SourceHUConfig.IsActive
-- 2026-02-05T21:50:09.533Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-05 21:50:09.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646859
;

-- UI Element: Lager und Lagerort(139,D) -> Quell-HU Konfiguration(548984,D) -> main -> 10 -> main.Sektion
-- Column: M_Warehouse_SourceHUConfig.AD_Org_ID
-- 2026-02-05T21:50:09.539Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-05 21:50:09.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646860
;

