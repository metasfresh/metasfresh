-- Run mode: SWING_CLIENT

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Lager
-- Column: M_Inventory.M_Warehouse_ID
-- 2025-12-16T12:35:42.701Z
UPDATE AD_Field SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-12-16 12:35:42.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=2924
;

-- Column: M_Inventory.M_Locator_ID
-- 2025-12-16T12:40:43.447Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591758,448,0,30,321,'XX','M_Locator_ID','(SELECT     CASE         WHEN COUNT(DISTINCT M_Locator_ID) = 1 THEN MAX(M_Locator_ID)         ELSE NULL     END AS M_Locator_ID from m_inventoryline where m_inventory_id = M_Inventory.M_Inventory_ID)',TO_TIMESTAMP('2025-12-16 12:40:43.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Lagerort im Lager','D',0,10,'"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Lagerort',0,0,TO_TIMESTAMP('2025-12-16 12:40:43.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-16T12:40:43.457Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591758 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-16T12:40:43.480Z
/* DDL */  select update_Column_Translation_From_AD_Element(448)
;

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Lagerort
-- Column: M_Inventory.M_Locator_ID
-- 2025-12-16T12:41:13.315Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591758,760922,0,255,TO_TIMESTAMP('2025-12-16 12:41:13.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort im Lager',10,'D','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','N','N','N','N','N','N','Lagerort',TO_TIMESTAMP('2025-12-16 12:41:13.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T12:41:13.321Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760922 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T12:41:13.326Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(448)
;

-- 2025-12-16T12:41:13.375Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760922
;

-- 2025-12-16T12:41:13.380Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760922)
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> default.Lagerort
-- Column: M_Inventory.M_Locator_ID
-- 2025-12-16T12:41:57.840Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760922,0,255,541512,641263,'F',TO_TIMESTAMP('2025-12-16 12:41:57.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort im Lager','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','N','Y','N','N','N',0,'Lagerort',65,0,0,TO_TIMESTAMP('2025-12-16 12:41:57.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> default.Lagerort
-- Column: M_Inventory.M_Locator_ID
-- 2025-12-16T12:42:07.301Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-12-16 12:42:07.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641263
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> default.Lager
-- Column: M_Inventory.M_Warehouse_ID
-- 2025-12-16T12:42:07.307Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-12-16 12:42:07.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551229
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> description.Beschreibung
-- Column: M_Inventory.Description
-- 2025-12-16T12:42:07.313Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-12-16 12:42:07.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551228
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> default.Kostenstelle
-- Column: M_Inventory.C_Activity_ID
-- 2025-12-16T12:42:07.317Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-12-16 12:42:07.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551235
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> description.Freigegeben
-- Column: M_Inventory.IsApproved
-- 2025-12-16T12:42:07.323Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-12-16 12:42:07.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551240
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> description.Freigabe-Betrag
-- Column: M_Inventory.ApprovalAmt
-- 2025-12-16T12:42:07.329Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-12-16 12:42:07.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551241
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> default.Belegstatus
-- Column: M_Inventory.DocStatus
-- 2025-12-16T12:42:07.334Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-12-16 12:42:07.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551242
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 20 -> posted.Verbucht
-- Column: M_Inventory.Posted
-- 2025-12-16T12:42:07.339Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-12-16 12:42:07.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551244
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 20 -> org.Sektion
-- Column: M_Inventory.AD_Org_ID
-- 2025-12-16T12:42:07.345Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-12-16 12:42:07.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551225
;

-- Column: M_Inventory.M_Locator_ID
-- 2025-12-16T12:42:23.083Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-12-16 12:42:23.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591758
;

-- Column: M_Inventory.M_Locator_ID
-- Source Table: M_InventoryLine
-- 2025-12-16T13:07:18.901Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,591758,0,540186,321,TO_TIMESTAMP('2025-12-16 13:07:18.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L','Y',3564,3564,322,TO_TIMESTAMP('2025-12-16 13:07:18.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;




-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2025-12-16T13:15:48.897Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-12-16 13:15:48.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552506
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> packing.Packvorschrift
-- Column: M_InventoryLine.M_HU_PI_Item_Product_ID
-- 2025-12-16T13:15:48.910Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-12-16 13:15:48.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551319
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> packing.Packvorschrift (LU)
-- Column: M_InventoryLine.M_LU_HU_PI_ID
-- 2025-12-16T13:15:48.915Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-12-16 13:15:48.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637306
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Handling Unit
-- Column: M_InventoryLine.M_HU_ID
-- 2025-12-16T13:15:48.920Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-12-16 13:15:48.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551318
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.Description
-- 2025-12-16T13:15:48.925Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-12-16 13:15:48.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551254
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Inventory Type
-- Column: M_InventoryLine.InventoryType
-- 2025-12-16T13:15:48.930Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-12-16 13:15:48.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551255
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 20 -> org&client.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2025-12-16T13:15:48.936Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-12-16 13:15:48.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551246
;

