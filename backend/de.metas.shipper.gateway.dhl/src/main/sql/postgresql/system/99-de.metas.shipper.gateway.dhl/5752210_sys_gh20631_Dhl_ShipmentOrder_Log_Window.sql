-- Column: DHL_ShipmentOrder.DHL_ShipmentOrder_ID
-- Column: DHL_ShipmentOrder.DHL_ShipmentOrder_ID
-- 2025-04-16T16:43:13.642Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-04-16 16:43:13.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569086
;

-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T16:46:56.515Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589908,577156,0,19,541426,'XX','DHL_ShipmentOrder_ID',TO_TIMESTAMP('2025-04-16 16:46:55.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.shipper.gateway.dhl',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'DHL_ShipmetnOrder',0,0,TO_TIMESTAMP('2025-04-16 16:46:55.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-16T16:46:56.565Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589908 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-16T16:46:56.683Z
/* DDL */  select update_Column_Translation_From_AD_Element(577156) 
;

-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T16:47:13.508Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2025-04-16 16:47:13.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589908
;

-- 2025-04-16T16:47:32.775Z
INSERT INTO t_alter_column values('dhl_shipmentorder_log','DHL_ShipmentOrder_ID','NUMERIC(10)',null,null)
;

-- Tab: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log
-- Table: Dhl_ShipmentOrder_Log
-- Tab: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log
-- Table: Dhl_ShipmentOrder_Log
-- 2025-04-16T16:50:09.658Z
UPDATE AD_Tab SET IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2025-04-16 16:50:09.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547956
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> DHL_ShipmetnOrder
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> DHL_ShipmetnOrder
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T16:50:49.530Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589908,741975,0,547956,0,TO_TIMESTAMP('2025-04-16 16:50:48.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'DHL_ShipmetnOrder',0,0,90,0,1,1,TO_TIMESTAMP('2025-04-16 16:50:48.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-16T16:50:49.583Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-16T16:50:49.637Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577156) 
;

-- 2025-04-16T16:50:49.700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741975
;

-- 2025-04-16T16:50:49.756Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741975)
;

-- UI Element: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log.DHL_ShipmetnOrder
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.DHL_ShipmetnOrder
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T16:51:44.141Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741975,0,547956,552739,631370,'F',TO_TIMESTAMP('2025-04-16 16:51:43.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'DHL_ShipmetnOrder',5,0,0,TO_TIMESTAMP('2025-04-16 16:51:43.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T16:54:05.584Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-04-16 16:54:05.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589908
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> DHL Versandauftrag
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> DHL Versandauftrag
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T16:58:55.671Z
UPDATE AD_Field SET AD_Name_ID=577225, Description=NULL, Help=NULL, Name='DHL Versandauftrag',Updated=TO_TIMESTAMP('2025-04-16 16:58:55.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=741975
;

-- 2025-04-16T16:58:55.720Z
UPDATE AD_Field_Trl trl SET Name='DHL Versandauftrag' WHERE AD_Field_ID=741975 AND AD_Language='de_DE'
;

-- 2025-04-16T16:59:00.465Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577225) 
;

-- 2025-04-16T16:59:00.515Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741975
;

-- 2025-04-16T16:59:00.564Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741975)
;

-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T17:01:54.789Z
UPDATE AD_Column SET AD_Reference_Value_ID=541070, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-04-16 17:01:54.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589908
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> DHL Versandauftrag
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> DHL Versandauftrag
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T17:02:58.917Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=541070,Updated=TO_TIMESTAMP('2025-04-16 17:02:58.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=741975
;

-- Field: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log -> DHL Versandauftrag
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> DHL Versandauftrag
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T17:05:48.309Z
UPDATE AD_Field SET AD_Reference_ID=NULL, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2025-04-16 17:05:48.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=741975
;

-- UI Element: DHL Versandauftragsprotokoll -> Dhl ShipmentOrder Log.DHL_ShipmetnOrder
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.DHL_ShipmetnOrder
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T17:06:12.736Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2025-04-16 17:06:12.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631370
;

-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- Column: Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_ID
-- 2025-04-16T17:08:40.883Z
UPDATE AD_Column SET AD_Reference_Value_ID=NULL, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2025-04-16 17:08:40.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589908
;

