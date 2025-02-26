-- Run mode: SWING_CLIENT

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lager
-- Column: M_Material_Needs_Planner_V.WarehouseName
-- 2025-02-25T17:59:51.357Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=630646
;

-- 2025-02-25T17:59:51.366Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740339
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Lager
-- Column: M_Material_Needs_Planner_V.WarehouseName
-- 2025-02-25T17:59:51.377Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=740339
;

-- 2025-02-25T17:59:51.379Z
DELETE FROM AD_Field WHERE AD_Field_ID=740339
;

-- Column: M_Material_Needs_Planner_V.WarehouseName
-- 2025-02-25T17:59:51.393Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589734
;

-- 2025-02-25T17:59:51.395Z
DELETE FROM AD_Column WHERE AD_Column_ID=589734
;

-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-02-25T18:01:03.011Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589737,459,0,30,542466,'XX','M_Warehouse_ID',TO_TIMESTAMP('2025-02-25 18:01:02.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Lager oder Ort für Dienstleistung','D',0,10,'E','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N',0,'Lager',0,0,TO_TIMESTAMP('2025-02-25 18:01:02.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-25T18:01:03.016Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589737 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-25T18:01:03.053Z
/* DDL */  select update_Column_Translation_From_AD_Element(459)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Produktkategorie
-- Column: M_Material_Needs_Planner_V.ProductCategoryName
-- 2025-02-25T18:02:30.505Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=630635
;

-- 2025-02-25T18:02:30.506Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740329
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Produktkategorie
-- Column: M_Material_Needs_Planner_V.ProductCategoryName
-- 2025-02-25T18:02:30.512Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=740329
;

-- 2025-02-25T18:02:30.514Z
DELETE FROM AD_Field WHERE AD_Field_ID=740329
;

-- Column: M_Material_Needs_Planner_V.ProductCategoryName
-- 2025-02-25T18:02:30.519Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589724
;

-- 2025-02-25T18:02:30.521Z
DELETE FROM AD_Column WHERE AD_Column_ID=589724
;

-- Column: M_Material_Needs_Planner_V.M_Product_Category_ID
-- 2025-02-25T18:02:56.208Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589738,453,0,30,542466,'XX','M_Product_Category_ID',TO_TIMESTAMP('2025-02-25 18:02:55.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Kategorie eines Produktes','D',0,10,'E','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N',0,'Produkt Kategorie',0,0,TO_TIMESTAMP('2025-02-25 18:02:55.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-25T18:02:56.211Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589738 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-25T18:02:56.214Z
/* DDL */  select update_Column_Translation_From_AD_Element(453)
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Lager
-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-02-25T18:04:59.859Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589737,740341,0,547920,TO_TIMESTAMP('2025-02-25 18:04:59.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',10,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2025-02-25 18:04:59.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-25T18:04:59.864Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740341 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-25T18:04:59.873Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2025-02-25T18:04:59.935Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740341
;

-- 2025-02-25T18:04:59.935Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740341)
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Produkt Kategorie
-- Column: M_Material_Needs_Planner_V.M_Product_Category_ID
-- 2025-02-25T18:05:10.807Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589738,740342,0,547920,TO_TIMESTAMP('2025-02-25 18:05:10.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes',10,'D','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','N','N','N','N','N','Produkt Kategorie',TO_TIMESTAMP('2025-02-25 18:05:10.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-25T18:05:10.809Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740342 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-25T18:05:10.810Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453)
;

-- 2025-02-25T18:05:10.845Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740342
;

-- 2025-02-25T18:05:10.846Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740342)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 20 -> default.Aktiv
-- Column: M_Material_Needs_Planner_V.IsActive
-- 2025-02-25T18:05:49.757Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,740327,0,547920,630649,552635,'F',TO_TIMESTAMP('2025-02-25 18:05:49.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-02-25 18:05:49.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 20
-- UI Element Group: org
-- 2025-02-25T18:06:07.435Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547933,552636,TO_TIMESTAMP('2025-02-25 18:06:07.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-02-25 18:06:07.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 20
-- UI Element Group: default
-- 2025-02-25T18:06:29.115Z
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2025-02-25 18:06:29.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552635
;

-- UI Column: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 20
-- UI Element Group: org
-- 2025-02-25T18:06:40.532Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547933,552637,TO_TIMESTAMP('2025-02-25 18:06:40.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',30,TO_TIMESTAMP('2025-02-25 18:06:40.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 20 -> org.Sektion
-- Column: M_Material_Needs_Planner_V.AD_Org_ID
-- 2025-02-25T18:07:08.678Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552637, SeqNo=10,Updated=TO_TIMESTAMP('2025-02-25 18:07:08.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630647
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 20 -> org.Mandant
-- Column: M_Material_Needs_Planner_V.AD_Client_ID
-- 2025-02-25T18:07:15.308Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552637, SeqNo=20,Updated=TO_TIMESTAMP('2025-02-25 18:07:15.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630648
;

-- UI Column: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 20
-- UI Element Group: org
-- 2025-02-25T18:07:30.336Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=552636
;

-- UI Column: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 20
-- UI Element Group: org
-- 2025-02-25T18:07:34.999Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2025-02-25 18:07:34.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552637
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Produktname
-- Column: M_Material_Needs_Planner_V.ProductName
-- 2025-02-25T18:08:31.250Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=630636
;

-- 2025-02-25T18:08:31.252Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740330
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Produkt
-- Column: M_Material_Needs_Planner_V.ProductName
-- 2025-02-25T18:08:31.256Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=740330
;

-- 2025-02-25T18:08:31.259Z
DELETE FROM AD_Field WHERE AD_Field_ID=740330
;

-- Column: M_Material_Needs_Planner_V.ProductName
-- 2025-02-25T18:08:31.262Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589725
;

-- 2025-02-25T18:08:31.264Z
DELETE FROM AD_Column WHERE AD_Column_ID=589725
;

-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-02-25T18:08:55.527Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589739,454,0,30,542466,'XX','M_Product_ID',TO_TIMESTAMP('2025-02-25 18:08:55.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Produkt, Leistung, Artikel','D',0,10,'E','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N',0,'Produkt',0,0,TO_TIMESTAMP('2025-02-25 18:08:55.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-25T18:08:55.531Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589739 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-25T18:08:55.536Z
/* DDL */  select update_Column_Translation_From_AD_Element(454)
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Produkt
-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-02-25T18:09:04.718Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589739,740343,0,547920,TO_TIMESTAMP('2025-02-25 18:09:04.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2025-02-25 18:09:04.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-25T18:09:04.721Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740343 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-25T18:09:04.724Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2025-02-25T18:09:04.846Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740343
;

-- 2025-02-25T18:09:04.847Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740343)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Produkt Kategorie
-- Column: M_Material_Needs_Planner_V.M_Product_Category_ID
-- 2025-02-25T18:09:28.942Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,740342,0,547920,630650,552634,'F',TO_TIMESTAMP('2025-02-25 18:09:28.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','Y','N','N','N',0,'Produkt Kategorie',10,0,0,TO_TIMESTAMP('2025-02-25 18:09:28.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Produkt
-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-02-25T18:09:42.234Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,740343,0,547920,630651,552634,'F',TO_TIMESTAMP('2025-02-25 18:09:42.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',20,0,0,TO_TIMESTAMP('2025-02-25 18:09:42.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Produkt Kategorie
-- Column: M_Material_Needs_Planner_V.M_Product_Category_ID
-- 2025-02-25T18:09:54.193Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-02-25 18:09:54.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630650
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Produkt
-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-02-25T18:09:54.197Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-02-25 18:09:54.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630651
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lager
-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-02-25T18:10:08.585Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,740341,0,547920,630652,552634,'F',TO_TIMESTAMP('2025-02-25 18:10:08.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','N','N',0,'Lager',120,0,0,TO_TIMESTAMP('2025-02-25 18:10:08.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lager
-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-02-25T18:10:21.775Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-02-25 18:10:21.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630652
;

-- Run mode: SWING_CLIENT

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T10:40:32.201Z
UPDATE AD_Element_Trl SET Name='6', PrintName='6',Updated=TO_TIMESTAMP('2025-02-26 10:40:32.201000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='de_CH'
;

-- 2025-02-26T10:40:32.202Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:40:33.114Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'de_CH')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T10:40:35.639Z
UPDATE AD_Element_Trl SET Name='6', PrintName='6',Updated=TO_TIMESTAMP('2025-02-26 10:40:35.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='de_DE'
;

-- 2025-02-26T10:40:35.640Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:40:36.818Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583506,'de_DE')
;

-- 2025-02-26T10:40:36.819Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'de_DE')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T10:40:40.812Z
UPDATE AD_Element_Trl SET Name='6', PrintName='6',Updated=TO_TIMESTAMP('2025-02-26 10:40:40.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='en_US'
;

-- 2025-02-26T10:40:40.814Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:40:41.468Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'en_US')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T10:40:43.917Z
UPDATE AD_Element_Trl SET Name='6', PrintName='6',Updated=TO_TIMESTAMP('2025-02-26 10:40:43.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='fr_CH'
;

-- 2025-02-26T10:40:43.917Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:40:44.533Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'fr_CH')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T10:41:38.323Z
UPDATE AD_Element_Trl SET Description='Vor sechs Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:41:38.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='de_CH'
;

-- 2025-02-26T10:41:38.324Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:41:38.932Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'de_CH')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T10:41:40.568Z
UPDATE AD_Element_Trl SET Description='Vor sechs Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:41:40.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='de_DE'
;

-- 2025-02-26T10:41:40.569Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:41:41.599Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583506,'de_DE')
;

-- 2025-02-26T10:41:41.601Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'de_DE')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T10:41:47.517Z
UPDATE AD_Element_Trl SET Description='Six weeks ago',Updated=TO_TIMESTAMP('2025-02-26 10:41:47.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='en_US'
;

-- 2025-02-26T10:41:47.518Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:41:48.002Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'en_US')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T10:41:51.242Z
UPDATE AD_Element_Trl SET Description='Vor sechs Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:41:51.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='fr_CH'
;

-- 2025-02-26T10:41:51.243Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:41:51.774Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'fr_CH')
;

-- Element: Total_Qty_One_Week_Ago
-- 2025-02-26T10:42:35.495Z
UPDATE AD_Element_Trl SET Description='Letzte Woche',Updated=TO_TIMESTAMP('2025-02-26 10:42:35.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583501 AND AD_Language='de_CH'
;

-- 2025-02-26T10:42:35.496Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:42:36.754Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583501,'de_CH')
;

-- Element: Total_Qty_One_Week_Ago
-- 2025-02-26T10:42:38.305Z
UPDATE AD_Element_Trl SET Description='Letzte Woche',Updated=TO_TIMESTAMP('2025-02-26 10:42:38.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583501 AND AD_Language='de_DE'
;

-- 2025-02-26T10:42:38.306Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:42:40.043Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583501,'de_DE')
;

-- 2025-02-26T10:42:40.047Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583501,'de_DE')
;

-- Element: Total_Qty_One_Week_Ago
-- 2025-02-26T10:42:44.317Z
UPDATE AD_Element_Trl SET Description='Last week',Updated=TO_TIMESTAMP('2025-02-26 10:42:44.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583501 AND AD_Language='en_US'
;

-- 2025-02-26T10:42:44.318Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:42:44.984Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583501,'en_US')
;

-- Element: Total_Qty_One_Week_Ago
-- 2025-02-26T10:42:52.468Z
UPDATE AD_Element_Trl SET Description='Letzte Woche',Updated=TO_TIMESTAMP('2025-02-26 10:42:52.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583501 AND AD_Language='fr_CH'
;

-- 2025-02-26T10:42:52.469Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:42:53.491Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583501,'fr_CH')
;

-- Element: Total_Qty_Two_Weeks_Ago
-- 2025-02-26T10:43:14.974Z
UPDATE AD_Element_Trl SET Description='Vor zwei Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:43:14.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583502 AND AD_Language='de_CH'
;

-- 2025-02-26T10:43:14.975Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:43:15.564Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583502,'de_CH')
;

-- Element: Total_Qty_Two_Weeks_Ago
-- 2025-02-26T10:43:17.366Z
UPDATE AD_Element_Trl SET Description='Vor zwei Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:43:17.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583502 AND AD_Language='de_DE'
;

-- 2025-02-26T10:43:17.367Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:43:18.384Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583502,'de_DE')
;

-- 2025-02-26T10:43:18.385Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583502,'de_DE')
;

-- Element: Total_Qty_Two_Weeks_Ago
-- 2025-02-26T10:43:21.561Z
UPDATE AD_Element_Trl SET Description='Two weeks ago',Updated=TO_TIMESTAMP('2025-02-26 10:43:21.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583502 AND AD_Language='en_US'
;

-- 2025-02-26T10:43:21.561Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:43:22.056Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583502,'en_US')
;

-- Element: Total_Qty_Two_Weeks_Ago
-- 2025-02-26T10:43:25.204Z
UPDATE AD_Element_Trl SET Description='Vor zwei Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:43:25.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583502 AND AD_Language='fr_CH'
;

-- 2025-02-26T10:43:25.205Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:43:25.736Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583502,'fr_CH')
;

-- Element: Total_Qty_Three_Weeks_Ago
-- 2025-02-26T10:43:44.657Z
UPDATE AD_Element_Trl SET Description='Vor drei Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:43:44.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583503 AND AD_Language='de_CH'
;

-- 2025-02-26T10:43:44.658Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:43:45.314Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583503,'de_CH')
;

-- Element: Total_Qty_Three_Weeks_Ago
-- 2025-02-26T10:43:46.682Z
UPDATE AD_Element_Trl SET Description='Vor drei Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:43:46.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583503 AND AD_Language='de_DE'
;

-- 2025-02-26T10:43:46.683Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:43:47.709Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583503,'de_DE')
;

-- 2025-02-26T10:43:47.710Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583503,'de_DE')
;

-- Element: Total_Qty_Three_Weeks_Ago
-- 2025-02-26T10:43:50.393Z
UPDATE AD_Element_Trl SET Description='Three weeks ago',Updated=TO_TIMESTAMP('2025-02-26 10:43:50.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583503 AND AD_Language='en_US'
;

-- 2025-02-26T10:43:50.393Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:43:51.006Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583503,'en_US')
;

-- Element: Total_Qty_Three_Weeks_Ago
-- 2025-02-26T10:43:54.524Z
UPDATE AD_Element_Trl SET Description='Vor drei Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:43:54.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583503 AND AD_Language='fr_CH'
;

-- 2025-02-26T10:43:54.525Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:43:55.171Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583503,'fr_CH')
;

-- Element: Total_Qty_Four_Weeks_Ago
-- 2025-02-26T10:44:15.612Z
UPDATE AD_Element_Trl SET Description='Vor vier Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:44:15.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583504 AND AD_Language='de_CH'
;

-- 2025-02-26T10:44:15.613Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:44:16.087Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583504,'de_CH')
;

-- Element: Total_Qty_Four_Weeks_Ago
-- 2025-02-26T10:44:17.699Z
UPDATE AD_Element_Trl SET Description='Vor vier Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:44:17.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583504 AND AD_Language='de_DE'
;

-- 2025-02-26T10:44:17.699Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:44:18.807Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583504,'de_DE')
;

-- 2025-02-26T10:44:18.808Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583504,'de_DE')
;

-- Element: Total_Qty_Four_Weeks_Ago
-- 2025-02-26T10:44:21.895Z
UPDATE AD_Element_Trl SET Description='Four weeks ago',Updated=TO_TIMESTAMP('2025-02-26 10:44:21.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583504 AND AD_Language='en_US'
;

-- 2025-02-26T10:44:21.896Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:44:22.537Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583504,'en_US')
;

-- Element: Total_Qty_Four_Weeks_Ago
-- 2025-02-26T10:44:25.473Z
UPDATE AD_Element_Trl SET Description='Vor vier Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:44:25.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583504 AND AD_Language='fr_CH'
;

-- 2025-02-26T10:44:25.475Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:44:26.077Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583504,'fr_CH')
;

-- Element: Total_Qty_Five_Weeks_Ago
-- 2025-02-26T10:44:44.238Z
UPDATE AD_Element_Trl SET Description='Vor fünf Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:44:44.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583505 AND AD_Language='de_CH'
;

-- 2025-02-26T10:44:44.239Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:44:45.023Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583505,'de_CH')
;

-- Element: Total_Qty_Five_Weeks_Ago
-- 2025-02-26T10:44:46.309Z
UPDATE AD_Element_Trl SET Description='Vor fünf Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:44:46.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583505 AND AD_Language='de_DE'
;

-- 2025-02-26T10:44:46.309Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:44:47.387Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583505,'de_DE')
;

-- 2025-02-26T10:44:47.389Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583505,'de_DE')
;

-- Element: Total_Qty_Five_Weeks_Ago
-- 2025-02-26T10:44:50.140Z
UPDATE AD_Element_Trl SET Description='Five weeks ago',Updated=TO_TIMESTAMP('2025-02-26 10:44:50.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583505 AND AD_Language='en_US'
;

-- 2025-02-26T10:44:50.140Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:44:50.824Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583505,'en_US')
;

-- Element: Total_Qty_Five_Weeks_Ago
-- 2025-02-26T10:44:53.916Z
UPDATE AD_Element_Trl SET Description='Vor fünf Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:44:53.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583505 AND AD_Language='fr_CH'
;

-- 2025-02-26T10:44:53.917Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:44:54.704Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583505,'fr_CH')
;

-- Element: Average_Qty_Last_Six_Weeks
-- 2025-02-26T10:45:31.894Z
UPDATE AD_Element_Trl SET Description='Durchschnittliche Menge in den letzten sechs Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:45:31.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583507 AND AD_Language='de_CH'
;

-- 2025-02-26T10:45:31.895Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:45:32.434Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583507,'de_CH')
;

-- Element: Average_Qty_Last_Six_Weeks
-- 2025-02-26T10:45:33.955Z
UPDATE AD_Element_Trl SET Description='Durchschnittliche Menge in den letzten sechs Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:45:33.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583507 AND AD_Language='de_DE'
;

-- 2025-02-26T10:45:33.956Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:45:34.838Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583507,'de_DE')
;

-- 2025-02-26T10:45:34.839Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583507,'de_DE')
;

-- Element: Average_Qty_Last_Six_Weeks
-- 2025-02-26T10:45:40.131Z
UPDATE AD_Element_Trl SET Description='Average quantity in the last six weeks',Updated=TO_TIMESTAMP('2025-02-26 10:45:40.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583507 AND AD_Language='en_US'
;

-- 2025-02-26T10:45:40.131Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:45:40.617Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583507,'en_US')
;

-- Element: Average_Qty_Last_Six_Weeks
-- 2025-02-26T10:45:44.074Z
UPDATE AD_Element_Trl SET Description='Durchschnittliche Menge in den letzten sechs Wochen',Updated=TO_TIMESTAMP('2025-02-26 10:45:44.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583507 AND AD_Language='fr_CH'
;

-- 2025-02-26T10:45:44.075Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T10:45:44.618Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583507,'fr_CH')
;

-- Run mode: SWING_CLIENT

-- Column: M_Material_Needs_Planner_V.M_Material_Needs_Planner_V_ID
-- 2025-02-26T14:26:40.326Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589736
;

-- 2025-02-26T14:26:40.341Z
DELETE FROM AD_Column WHERE AD_Column_ID=589736
;

-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-02-26T14:28:18.689Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-02-26 14:28:18.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589739
;

-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-02-26T14:28:27.014Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-02-26 14:28:27.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589737
;

-- Run mode: SWING_CLIENT

-- Element: Total_Qty_One_Week_Ago
-- 2025-02-26T16:32:19.236Z
UPDATE AD_Element_Trl SET Name='-1', PrintName='-1',Updated=TO_TIMESTAMP('2025-02-26 16:32:19.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583501 AND AD_Language='de_CH'
;

-- 2025-02-26T16:32:19.237Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:32:19.825Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583501,'de_CH')
;

-- Element: Total_Qty_One_Week_Ago
-- 2025-02-26T16:32:22.236Z
UPDATE AD_Element_Trl SET Name='-1', PrintName='-1',Updated=TO_TIMESTAMP('2025-02-26 16:32:22.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583501 AND AD_Language='de_DE'
;

-- 2025-02-26T16:32:22.238Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:32:24.298Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583501,'de_DE')
;

-- 2025-02-26T16:32:24.303Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583501,'de_DE')
;

-- Element: Total_Qty_One_Week_Ago
-- 2025-02-26T16:32:26.253Z
UPDATE AD_Element_Trl SET Name='-1', PrintName='-1',Updated=TO_TIMESTAMP('2025-02-26 16:32:26.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583501 AND AD_Language='en_US'
;

-- 2025-02-26T16:32:26.254Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:32:26.793Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583501,'en_US')
;

-- Element: Total_Qty_One_Week_Ago
-- 2025-02-26T16:32:29.345Z
UPDATE AD_Element_Trl SET Name='-1', PrintName='-1',Updated=TO_TIMESTAMP('2025-02-26 16:32:29.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583501 AND AD_Language='fr_CH'
;

-- 2025-02-26T16:32:29.346Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:32:29.837Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583501,'fr_CH')
;

-- Element: Total_Qty_Two_Weeks_Ago
-- 2025-02-26T16:32:53.537Z
UPDATE AD_Element_Trl SET Name='-2', PrintName='-2',Updated=TO_TIMESTAMP('2025-02-26 16:32:53.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583502 AND AD_Language='de_CH'
;

-- 2025-02-26T16:32:53.538Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:32:54.199Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583502,'de_CH')
;

-- Element: Total_Qty_Two_Weeks_Ago
-- 2025-02-26T16:32:55.963Z
UPDATE AD_Element_Trl SET Name='-2', PrintName='-2',Updated=TO_TIMESTAMP('2025-02-26 16:32:55.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583502 AND AD_Language='de_DE'
;

-- 2025-02-26T16:32:55.964Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:32:57.252Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583502,'de_DE')
;

-- 2025-02-26T16:32:57.253Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583502,'de_DE')
;

-- Element: Total_Qty_Two_Weeks_Ago
-- 2025-02-26T16:32:59.017Z
UPDATE AD_Element_Trl SET Name='-2', PrintName='-2',Updated=TO_TIMESTAMP('2025-02-26 16:32:59.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583502 AND AD_Language='en_US'
;

-- 2025-02-26T16:32:59.017Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:32:59.508Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583502,'en_US')
;

-- Element: Total_Qty_Two_Weeks_Ago
-- 2025-02-26T16:33:01.947Z
UPDATE AD_Element_Trl SET Name='-2', PrintName='-2',Updated=TO_TIMESTAMP('2025-02-26 16:33:01.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583502 AND AD_Language='fr_CH'
;

-- 2025-02-26T16:33:01.948Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:33:02.401Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583502,'fr_CH')
;

-- Element: Total_Qty_Three_Weeks_Ago
-- 2025-02-26T16:33:16.811Z
UPDATE AD_Element_Trl SET Name='-3', PrintName='-3',Updated=TO_TIMESTAMP('2025-02-26 16:33:16.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583503 AND AD_Language='de_CH'
;

-- 2025-02-26T16:33:16.812Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:33:17.863Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583503,'de_CH')
;

-- Element: Total_Qty_Three_Weeks_Ago
-- 2025-02-26T16:33:19.667Z
UPDATE AD_Element_Trl SET Name='-3', PrintName='-3',Updated=TO_TIMESTAMP('2025-02-26 16:33:19.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583503 AND AD_Language='de_DE'
;

-- 2025-02-26T16:33:19.667Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:33:21.386Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583503,'de_DE')
;

-- 2025-02-26T16:33:21.387Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583503,'de_DE')
;

-- Element: Total_Qty_Three_Weeks_Ago
-- 2025-02-26T16:33:23.123Z
UPDATE AD_Element_Trl SET Name='-3', PrintName='-3',Updated=TO_TIMESTAMP('2025-02-26 16:33:23.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583503 AND AD_Language='en_US'
;

-- 2025-02-26T16:33:23.124Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:33:23.834Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583503,'en_US')
;

-- Element: Total_Qty_Three_Weeks_Ago
-- 2025-02-26T16:33:25.715Z
UPDATE AD_Element_Trl SET Name='-3', PrintName='-3',Updated=TO_TIMESTAMP('2025-02-26 16:33:25.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583503 AND AD_Language='fr_CH'
;

-- 2025-02-26T16:33:25.716Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:33:26.790Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583503,'fr_CH')
;

-- Element: Total_Qty_Four_Weeks_Ago
-- 2025-02-26T16:33:44.110Z
UPDATE AD_Element_Trl SET Name='-4', PrintName='-4',Updated=TO_TIMESTAMP('2025-02-26 16:33:44.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583504 AND AD_Language='de_CH'
;

-- 2025-02-26T16:33:44.111Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:33:44.685Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583504,'de_CH')
;

-- Element: Total_Qty_Four_Weeks_Ago
-- 2025-02-26T16:33:46.772Z
UPDATE AD_Element_Trl SET Name='-4', PrintName='-4',Updated=TO_TIMESTAMP('2025-02-26 16:33:46.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583504 AND AD_Language='de_DE'
;

-- 2025-02-26T16:33:46.773Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:33:47.982Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583504,'de_DE')
;

-- 2025-02-26T16:33:47.983Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583504,'de_DE')
;

-- Element: Total_Qty_Four_Weeks_Ago
-- 2025-02-26T16:33:49.751Z
UPDATE AD_Element_Trl SET Name='-4', PrintName='-4',Updated=TO_TIMESTAMP('2025-02-26 16:33:49.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583504 AND AD_Language='en_US'
;

-- 2025-02-26T16:33:49.753Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:33:50.265Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583504,'en_US')
;

-- Element: Total_Qty_Four_Weeks_Ago
-- 2025-02-26T16:33:52.293Z
UPDATE AD_Element_Trl SET Name='-4', PrintName='-4',Updated=TO_TIMESTAMP('2025-02-26 16:33:52.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583504 AND AD_Language='fr_CH'
;

-- 2025-02-26T16:33:52.294Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:33:52.781Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583504,'fr_CH')
;

-- Element: Total_Qty_Five_Weeks_Ago
-- 2025-02-26T16:34:08.348Z
UPDATE AD_Element_Trl SET Name='-5', PrintName='-5',Updated=TO_TIMESTAMP('2025-02-26 16:34:08.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583505 AND AD_Language='de_CH'
;

-- 2025-02-26T16:34:08.349Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:34:08.827Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583505,'de_CH')
;

-- Element: Total_Qty_Five_Weeks_Ago
-- 2025-02-26T16:34:11.119Z
UPDATE AD_Element_Trl SET Name='-5', PrintName='-5',Updated=TO_TIMESTAMP('2025-02-26 16:34:11.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583505 AND AD_Language='de_DE'
;

-- 2025-02-26T16:34:11.120Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:34:12.070Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583505,'de_DE')
;

-- 2025-02-26T16:34:12.071Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583505,'de_DE')
;

-- Element: Total_Qty_Five_Weeks_Ago
-- 2025-02-26T16:34:13.938Z
UPDATE AD_Element_Trl SET Name='-5', PrintName='-5',Updated=TO_TIMESTAMP('2025-02-26 16:34:13.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583505 AND AD_Language='en_US'
;

-- 2025-02-26T16:34:13.939Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:34:14.428Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583505,'en_US')
;

-- Element: Total_Qty_Five_Weeks_Ago
-- 2025-02-26T16:34:16.323Z
UPDATE AD_Element_Trl SET Name='-5', PrintName='-5',Updated=TO_TIMESTAMP('2025-02-26 16:34:16.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583505 AND AD_Language='fr_CH'
;

-- 2025-02-26T16:34:16.323Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:34:16.896Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583505,'fr_CH')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T16:34:42.523Z
UPDATE AD_Element_Trl SET Name='-6', PrintName='-6',Updated=TO_TIMESTAMP('2025-02-26 16:34:42.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='de_CH'
;

-- 2025-02-26T16:34:42.525Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:34:43.026Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'de_CH')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T16:34:45.173Z
UPDATE AD_Element_Trl SET Name='-6', PrintName='-6',Updated=TO_TIMESTAMP('2025-02-26 16:34:45.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='de_DE'
;

-- 2025-02-26T16:34:45.173Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:34:46.063Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583506,'de_DE')
;

-- 2025-02-26T16:34:46.064Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'de_DE')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T16:34:48.103Z
UPDATE AD_Element_Trl SET Name='-6', PrintName='-6',Updated=TO_TIMESTAMP('2025-02-26 16:34:48.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='en_US'
;

-- 2025-02-26T16:34:48.103Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:34:48.663Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'en_US')
;

-- Element: Total_Qty_Six_Weeks_Ago
-- 2025-02-26T16:34:50.520Z
UPDATE AD_Element_Trl SET Name='-6', PrintName='-6',Updated=TO_TIMESTAMP('2025-02-26 16:34:50.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583506 AND AD_Language='fr_CH'
;

-- 2025-02-26T16:34:50.521Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-26T16:34:51.088Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583506,'fr_CH')
;

CREATE INDEX md_candidate_demand_latest_date_perf
    ON md_candidate (dateprojected DESC)
    WHERE isactive = 'Y' AND md_candidate_type = 'DEMAND'
;

-- Run mode: SWING_CLIENT

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Produkt Kategorie
-- Column: M_Material_Needs_Planner_V.M_Product_Category_ID
-- 2025-02-26T17:17:31.539Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2025-02-26 17:17:31.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740342
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Produkt
-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-02-26T17:17:44.630Z
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2025-02-26 17:17:44.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740343
;

-- Run mode: SWING_CLIENT

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lager
-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-02-26T17:19:34.622Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2025-02-26 17:19:34.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630652
;
