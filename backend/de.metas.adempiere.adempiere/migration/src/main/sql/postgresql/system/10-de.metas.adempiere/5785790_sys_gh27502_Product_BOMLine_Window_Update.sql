-- Run mode: SWING_CLIENT

-- Field: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Komponente
-- Column: PP_Product_BOMLine.M_Product_ID
-- 2026-01-29T07:42:42.388Z
UPDATE AD_Field SET AD_Name_ID=584437, Description=NULL, Help=NULL, Name='Komponente',Updated=TO_TIMESTAMP('2026-01-29 07:42:42.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=589236
;

-- 2026-01-29T07:42:42.448Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Komponente' WHERE AD_Field_ID=589236 AND AD_Language='de_DE'
;

-- 2026-01-29T07:42:42.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584437)
;

-- 2026-01-29T07:42:42.618Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589236
;

-- 2026-01-29T07:42:42.683Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(589236)
;

-- 2026-01-29T07:57:30.422Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584473,0,'Parent_Product_ID',TO_TIMESTAMP('2026-01-29 07:57:29.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Fertigprodukt','Fertigprodukt',TO_TIMESTAMP('2026-01-29 07:57:29.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-29T07:57:30.484Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584473 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Parent_Product_ID
-- 2026-01-29T07:58:20.202Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Finished product', PrintName='Finished product',Updated=TO_TIMESTAMP('2026-01-29 07:58:20.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584473 AND AD_Language='en_US'
;

-- 2026-01-29T07:58:20.263Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-29T07:58:32.036Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584473,'en_US')
;

-- Column: PP_Product_BOMLine.Parent_Product_ID
-- 2026-01-29T08:05:47.864Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591897,584473,0,30,53019,'XX','Parent_Product_ID','(SELECT M_Product_ID from PP_Product_BOM where PP_Product_BOM_ID = PP_Product_BOMLine.PP_Product_BOM_ID)',TO_TIMESTAMP('2026-01-29 08:05:46.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','EE01',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N',0,'Fertigprodukt',0,0,TO_TIMESTAMP('2026-01-29 08:05:46.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-29T08:05:47.925Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591897 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-29T08:05:48.049Z
/* DDL */  select update_Column_Translation_From_AD_Element(584473)
;

-- Field: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Fertigprodukt
-- Column: PP_Product_BOMLine.Parent_Product_ID
-- 2026-01-29T08:06:38.954Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591897,771047,0,542034,0,TO_TIMESTAMP('2026-01-29 08:06:38.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Fertigprodukt',0,0,210,0,1,1,TO_TIMESTAMP('2026-01-29 08:06:38.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-29T08:06:39.014Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-29T08:06:39.075Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584473)
;

-- 2026-01-29T08:06:39.136Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771047
;

-- 2026-01-29T08:06:39.197Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771047)
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> description.Fertigprodukt
-- Column: PP_Product_BOMLine.Parent_Product_ID
-- 2026-01-29T08:07:41.011Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771047,0,542034,543048,646202,'F',TO_TIMESTAMP('2026-01-29 08:07:40.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Fertigprodukt',15,0,0,TO_TIMESTAMP('2026-01-29 08:07:40.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> description.BOM & Formula
-- Column: PP_Product_BOMLine.PP_Product_BOM_ID
-- 2026-01-29T08:08:11.786Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-01-29 08:08:11.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563092
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> description.Fertigprodukt
-- Column: PP_Product_BOMLine.Parent_Product_ID
-- 2026-01-29T08:08:12.208Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-01-29 08:08:12.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646202
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> default.Product
-- Column: PP_Product_BOMLine.M_Product_ID
-- 2026-01-29T08:08:12.575Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-01-29 08:08:12.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563084
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> default.Component Type
-- Column: PP_Product_BOMLine.ComponentType
-- 2026-01-29T08:08:12.940Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-01-29 08:08:12.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563108
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> description.QtyBOM
-- Column: PP_Product_BOMLine.QtyBOM
-- 2026-01-29T08:08:13.312Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-01-29 08:08:13.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563114
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> default.% Qty
-- Column: PP_Product_BOMLine.QtyBatch
-- 2026-01-29T08:08:13.675Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-01-29 08:08:13.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563113
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> default.Qty
-- Column: PP_Product_BOMLine.QtyBOM
-- 2026-01-29T08:08:14.044Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-01-29 08:08:14.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563109
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> default.Qty Attribute
-- Column: PP_Product_BOMLine.Qty_Attribute_ID
-- 2026-01-29T08:08:14.413Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-01-29 08:08:14.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563111
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> default.Attributes
-- Column: PP_Product_BOMLine.M_AttributeSetInstance_ID
-- 2026-01-29T08:08:14.774Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-01-29 08:08:14.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563110
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 20 -> flags.Active
-- Column: PP_Product_BOMLine.IsActive
-- 2026-01-29T08:08:15.157Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-01-29 08:08:15.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563099
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> description.CU Label Qty
-- Column: PP_Product_BOMLine.CULabelQuanitity
-- 2026-01-29T08:08:15.542Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-01-29 08:08:15.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563105
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 20 -> org.Client
-- Column: PP_Product_BOMLine.AD_Client_ID
-- 2026-01-29T08:08:15.907Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-01-29 08:08:15.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563098
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> description.Description
-- Column: PP_Product_BOMLine.Description
-- 2026-01-29T08:08:16.270Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-01-29 08:08:16.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563091
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 20 -> flags.Is Critical Component
-- Column: PP_Product_BOMLine.IsCritical
-- 2026-01-29T08:08:16.629Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-01-29 08:08:16.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563117
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 20 -> flags.Is Qty Percentage
-- Column: PP_Product_BOMLine.IsQtyPercentage
-- 2026-01-29T08:08:16.986Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-01-29 08:08:16.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563100
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> description.Issue Method
-- Column: PP_Product_BOMLine.IssueMethod
-- 2026-01-29T08:08:17.346Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-01-29 08:08:17.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563115
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 20 -> org.Org
-- Column: PP_Product_BOMLine.AD_Org_ID
-- 2026-01-29T08:08:17.706Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-01-29 08:08:17.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563097
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> description.Scrap
-- Column: PP_Product_BOMLine.Scrap
-- 2026-01-29T08:08:18.064Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-01-29 08:08:18.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563090
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 20 -> flags.ShowSubBOMIngredients
-- Column: PP_Product_BOMLine.ShowSubBOMIngredients
-- 2026-01-29T08:08:18.425Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-01-29 08:08:18.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563116
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> default.UOM
-- Column: PP_Product_BOMLine.C_UOM_ID
-- 2026-01-29T08:08:18.781Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-01-29 08:08:18.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563086
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 20 -> valid.Valid from
-- Column: PP_Product_BOMLine.ValidFrom
-- 2026-01-29T08:08:19.140Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2026-01-29 08:08:19.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563101
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 20 -> valid.Valid to
-- Column: PP_Product_BOMLine.ValidTo
-- 2026-01-29T08:08:19.499Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2026-01-29 08:08:19.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563102
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> description.VariantGroup
-- Column: PP_Product_BOMLine.VariantGroup
-- 2026-01-29T08:08:19.856Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2026-01-29 08:08:19.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563112
;

-- Column: PP_Product_BOMLine.Parent_Product_ID
-- 2026-01-29T08:09:18.463Z
UPDATE AD_Column SET AD_Reference_Value_ID=540272, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-01-29 08:09:18.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591897
;

-- Column: PP_Product_BOMLine.PP_Product_BOM_ID
-- 2026-01-29T08:10:22.017Z
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2026-01-29 08:10:22.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53366
;

-- Column: PP_Product_BOMLine.Parent_Product_ID
-- 2026-01-29T08:10:35.060Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2026-01-29 08:10:35.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591897
;

-- Column: PP_Product_BOMLine.M_Product_ID
-- 2026-01-29T08:10:43.564Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2026-01-29 08:10:43.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53364
;

-- Column: PP_Product_BOMLine.ComponentType
-- 2026-01-29T08:10:52.237Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2026-01-29 08:10:52.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53350
;

-- Column: PP_Product_BOMLine.IsActive
-- 2026-01-29T08:10:59.256Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2026-01-29 08:10:59.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53356
;

-- Column: PP_Product_BOMLine.AD_Org_ID
-- 2026-01-29T08:11:06.466Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2026-01-29 08:11:06.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53346
;

-- Table: PP_Product_BOMLine
-- 2026-01-29T08:13:30.392Z
UPDATE AD_Table SET AD_Window_ID=540720,Updated=TO_TIMESTAMP('2026-01-29 08:13:30.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=53019
;

-- Field: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Stücklistenbestandteile
-- Column: PP_Product_BOMLine.PP_Product_BOMLine_ID
-- 2026-01-29T08:19:20.647Z
UPDATE AD_Field SET AD_Name_ID=572848, Description='Components of the BOM & Formula', Help='The information relative to every component that will be used in the BOM & Formula of the finished product.', Name='Stücklistenbestandteile',Updated=TO_TIMESTAMP('2026-01-29 08:19:20.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=53482
;

-- 2026-01-29T08:19:20.708Z
UPDATE AD_Field_Trl trl SET Description='Components of the BOM & Formula',Help='The information relative to every component that will be used in the BOM & Formula of the finished product.',Name='Stücklistenbestandteile' WHERE AD_Field_ID=53482 AND AD_Language='de_DE'
;

-- 2026-01-29T08:19:20.768Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572848)
;

-- 2026-01-29T08:19:20.831Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=53482
;

-- 2026-01-29T08:19:20.890Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(53482)
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Stücklistenbestandteile
-- Column: PP_Product_BOMLine.PP_Product_BOMLine_ID
-- 2026-01-29T08:21:52.110Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53482,0,53029,540444,646203,'F',TO_TIMESTAMP('2026-01-29 08:21:51.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Components of the BOM & Formula','The information relative to every component that will be used in the BOM & Formula of the finished product.','Y','N','N','Y','N','N','N',0,'Stücklistenbestandteile',15,0,0,TO_TIMESTAMP('2026-01-29 08:21:51.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Stücklistenbestandteile
-- Column: PP_Product_BOMLine.PP_Product_BOMLine_ID
-- 2026-01-29T08:22:11.171Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-01-29 08:22:11.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646203
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Produkt
-- Column: PP_Product_BOMLine.M_Product_ID
-- 2026-01-29T08:22:11.540Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-01-29 08:22:11.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544398
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Merkmale
-- Column: PP_Product_BOMLine.M_AttributeSetInstance_ID
-- 2026-01-29T08:22:11.896Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-01-29 08:22:11.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544402
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Komponententyp
-- Column: PP_Product_BOMLine.ComponentType
-- 2026-01-29T08:22:12.256Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-01-29 08:22:12.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544399
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.VariantGroup
-- Column: PP_Product_BOMLine.VariantGroup
-- 2026-01-29T08:22:12.612Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-01-29 08:22:12.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544400
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Menge
-- Column: PP_Product_BOMLine.QtyBOM
-- 2026-01-29T08:22:12.973Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-01-29 08:22:12.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544411
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Ist %
-- Column: PP_Product_BOMLine.IsQtyPercentage
-- 2026-01-29T08:22:13.332Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-01-29 08:22:13.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544408
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.% Menge
-- Column: PP_Product_BOMLine.QtyBatch
-- 2026-01-29T08:22:13.697Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-01-29 08:22:13.696000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544410
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Qty Attribute
-- Column: PP_Product_BOMLine.Qty_Attribute_ID
-- 2026-01-29T08:22:14.068Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-01-29 08:22:14.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552447
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.% Ausschuss
-- Column: PP_Product_BOMLine.Scrap
-- 2026-01-29T08:22:14.437Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-01-29 08:22:14.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544412
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Maßeinheit
-- Column: PP_Product_BOMLine.C_UOM_ID
-- 2026-01-29T08:22:14.799Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-01-29 08:22:14.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544401
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Menge Konsumentenlabel
-- Column: PP_Product_BOMLine.CULabelQuanitity
-- 2026-01-29T08:22:15.159Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-01-29 08:22:15.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552311
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Inhaltsstoffe Unterstückliste anzeigen
-- Column: PP_Product_BOMLine.ShowSubBOMIngredients
-- 2026-01-29T08:22:15.522Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-01-29 08:22:15.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552312
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Issue Method
-- Column: PP_Product_BOMLine.IssueMethod
-- 2026-01-29T08:22:15.893Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-01-29 08:22:15.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544416
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Jedes Produkt zuteilbar
-- Column: PP_Product_BOMLine.IsAllowIssuingAnyProduct
-- 2026-01-29T08:22:16.258Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-01-29 08:22:16.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=620374
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Aktiv
-- Column: PP_Product_BOMLine.IsActive
-- 2026-01-29T08:22:16.622Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-01-29 08:22:16.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544405
;

-- UI Element: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Sektion
-- Column: PP_Product_BOMLine.AD_Org_ID
-- 2026-01-29T08:22:16.986Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-01-29 08:22:16.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547353
;

-- Tab: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile
-- Table: PP_Product_BOMLine
-- 2026-01-29T08:27:55.891Z
UPDATE AD_Tab SET IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2026-01-29 08:27:55.891000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=542034
;

-- Reference: PP_Product_BOMLine for M_Product
-- Table: PP_Product_BOM
-- Key: PP_Product_BOM.PP_Product_BOM_ID
-- 2026-01-29T08:34:31.727Z
UPDATE AD_Ref_Table SET AD_Window_ID=540720,Updated=TO_TIMESTAMP('2026-01-29 08:34:31.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541135
;

-- Table: PP_Product_BOMLine_Trl
-- 2026-01-29T08:38:04.859Z
UPDATE AD_Table SET AD_Window_ID=540720,Updated=TO_TIMESTAMP('2026-01-29 08:38:04.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=53193
;

-- Tab: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile
-- Table: PP_Product_BOMLine
-- 2026-01-29T08:40:17.701Z
UPDATE AD_Tab SET IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2026-01-29 08:40:17.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=53029
;

-- Reference: PP_Product_BOMLine
-- Table: PP_Product_BOMLine
-- Key: PP_Product_BOMLine.PP_Product_BOMLine_ID
-- 2026-01-29T08:45:57.310Z
UPDATE AD_Ref_Table SET AD_Window_ID=540720,Updated=TO_TIMESTAMP('2026-01-29 08:45:57.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541079
;

-- Field: Stücklistenkonfiguration Version_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Stücklistenbestandteile
-- Column: PP_Product_BOMLine.PP_Product_BOMLine_ID
-- 2026-01-29T08:46:03.950Z
UPDATE AD_Field SET AD_Reference_ID=18, AD_Reference_Value_ID=541079,Updated=TO_TIMESTAMP('2026-01-29 08:46:03.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=53482
;

-- Name: BOM_Version_Line
-- 2026-01-29T09:02:47.157Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542045,TO_TIMESTAMP('2026-01-29 09:02:46.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','BOM_Version_Line',TO_TIMESTAMP('2026-01-29 09:02:46.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2026-01-29T09:02:47.218Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542045 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: BOM_Version_Line
-- Table: PP_Product_BOMLine
-- Key: PP_Product_BOMLine.PP_Product_BOMLine_ID
-- 2026-01-29T09:06:09.472Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,53365,0,542045,53019,53006,TO_TIMESTAMP('2026-01-29 09:06:09.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2026-01-29 09:06:09.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

