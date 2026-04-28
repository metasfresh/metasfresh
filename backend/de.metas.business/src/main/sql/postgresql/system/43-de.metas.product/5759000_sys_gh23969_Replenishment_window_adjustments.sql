-- Run mode: SWING_CLIENT

-- Element: Level_Max
-- 2025-06-25T10:55:51.130Z
UPDATE AD_Element_Trl SET Name='Sollmenge', PrintName='Sollmenge',Updated=TO_TIMESTAMP('2025-06-25 10:55:51.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=434 AND AD_Language='de_DE'
;

-- 2025-06-25T10:55:51.132Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:55:52.393Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(434,'de_DE')
;

-- 2025-06-25T10:55:52.395Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(434,'de_DE')
;

-- Element: Level_Max
-- 2025-06-25T10:55:57.113Z
UPDATE AD_Element_Trl SET Name='Sollmenge', PrintName='Sollmenge',Updated=TO_TIMESTAMP('2025-06-25 10:55:57.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=434 AND AD_Language='de_CH'
;

-- 2025-06-25T10:55:57.116Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:55:57.748Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(434,'de_CH')
;

-- Element: Level_Max
-- 2025-06-25T10:56:01.822Z
UPDATE AD_Element_Trl SET Name='Sollmenge', PrintName='Sollmenge',Updated=TO_TIMESTAMP('2025-06-25 10:56:01.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=434 AND AD_Language='en_GB'
;

-- 2025-06-25T10:56:01.823Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:56:02.403Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(434,'en_GB')
;

-- Element: Level_Max
-- 2025-06-25T10:56:07.922Z
UPDATE AD_Element_Trl SET Name='Sollmenge', PrintName='Sollmenge',Updated=TO_TIMESTAMP('2025-06-25 10:56:07.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=434 AND AD_Language='it_CH'
;

-- 2025-06-25T10:56:07.923Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='it_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:56:08.575Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(434,'it_CH')
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-06-25T10:57:15.150Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2025-06-25 10:57:15.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630663
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-06-25T10:58:33.709Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-06-25 10:58:33.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630663
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Maximalmenge
-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-06-25T10:58:33.715Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-06-25 10:58:33.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630664
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lager
-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-06-25T10:58:33.720Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-06-25 10:58:33.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630652
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Level_Min
-- 2025-06-25T11:01:18.690Z
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2025-06-25 11:01:18.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542932
;

-- Run mode: SWING_CLIENT

-- UI Element: Produkt_OLD(140,D) -> Nachbestellung(182,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Replenish.Level_Min
-- 2025-06-25T11:50:17.657Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-06-25 11:50:17.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000137
;

-- UI Element: Produkt_OLD(140,D) -> Nachbestellung(182,D) -> main -> 10 -> default.Maximalmenge
-- Column: M_Replenish.Level_Max
-- 2025-06-25T11:50:17.662Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-06-25 11:50:17.662000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000138
;

-- UI Element: Produkt_OLD(140,D) -> Nachbestellung(182,D) -> main -> 10 -> default.Aktiv
-- Column: M_Replenish.IsActive
-- 2025-06-25T11:50:17.666Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-06-25 11:50:17.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000135
;

-- UI Element: Produkt_OLD(140,D) -> Nachbestellung(182,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Replenish.Level_Min
-- 2025-06-25T11:50:29.976Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2025-06-25 11:50:29.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000137
;

-- Run mode: SWING_CLIENT

-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-06-25T11:52:21.399Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-06-25 11:52:21.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589752
;

-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-06-25T11:52:33.315Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsExcludeFromZoomTargets='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-25 11:52:33.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589753
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Level_Max
-- 2025-06-25T11:53:10.705Z
UPDATE AD_Process_Para SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2025-06-25 11:53:10.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542933
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Level_Min
-- 2025-06-25T11:53:17.987Z
UPDATE AD_Process_Para SET DefaultValue='0',Updated=TO_TIMESTAMP('2025-06-25 11:53:17.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542932
;

-- Run mode: SWING_CLIENT

-- Column: M_Material_Needs_Planner_V.QuantityOnHand
-- 2025-06-27T09:43:22.052Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsExcludeFromZoomTargets='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-27 09:43:22.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589733
;

-- Column: M_Material_Needs_Planner_V.Total_Qty_Five_Weeks_Ago
-- 2025-06-27T09:43:31.042Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsExcludeFromZoomTargets='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-27 09:43:31.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589730
;

-- Column: M_Material_Needs_Planner_V.Total_Qty_Four_Weeks_Ago
-- 2025-06-27T09:43:37.753Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsExcludeFromZoomTargets='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-27 09:43:37.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589729
;

-- Column: M_Material_Needs_Planner_V.Total_Qty_One_Week_Ago
-- 2025-06-27T09:43:44.595Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsExcludeFromZoomTargets='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-27 09:43:44.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589726
;

-- Column: M_Material_Needs_Planner_V.Total_Qty_Six_Weeks_Ago
-- 2025-06-27T09:43:51.671Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsExcludeFromZoomTargets='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-27 09:43:51.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589731
;

-- Column: M_Material_Needs_Planner_V.Total_Qty_Three_Weeks_Ago
-- 2025-06-27T09:43:58.941Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsExcludeFromZoomTargets='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-27 09:43:58.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589728
;

-- Column: M_Material_Needs_Planner_V.Total_Qty_Two_Weeks_Ago
-- 2025-06-27T09:44:06.606Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsExcludeFromZoomTargets='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-27 09:44:06.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589727
;

-- Column: M_Material_Needs_Planner_V.Average_Qty_Last_Six_Weeks
-- 2025-06-27T09:44:40.493Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsExcludeFromZoomTargets='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-27 09:44:40.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589732
;

-- Column: M_Material_Needs_Planner_V.M_HU_PI_Item_Product_ID
-- 2025-06-27T10:11:07.844Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590461,542132,0,30,542466,'XX','M_HU_PI_Item_Product_ID',TO_TIMESTAMP('2025-06-27 10:11:07.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Packvorschrift',0,0,TO_TIMESTAMP('2025-06-27 10:11:07.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-27T10:11:07.844Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590461 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-27T10:11:07.906Z
/* DDL */  select update_Column_Translation_From_AD_Element(542132)
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Packvorschrift
-- Column: M_Material_Needs_Planner_V.M_HU_PI_Item_Product_ID
-- 2025-06-27T10:20:44.277Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590461,747888,0,547920,TO_TIMESTAMP('2025-06-27 10:20:44.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Packvorschrift',TO_TIMESTAMP('2025-06-27 10:20:44.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-27T10:20:44.280Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747888 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-27T10:20:44.283Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542132)
;

-- 2025-06-27T10:20:44.315Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747888
;

-- 2025-06-27T10:20:44.321Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747888)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Packvorschrift
-- Column: M_Material_Needs_Planner_V.M_HU_PI_Item_Product_ID
-- 2025-06-27T10:21:43.205Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,747888,0,547920,634189,552634,'F',TO_TIMESTAMP('2025-06-27 10:21:43.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Packvorschrift',25,0,0,TO_TIMESTAMP('2025-06-27 10:21:43.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Packvorschrift
-- Column: M_Material_Needs_Planner_V.M_HU_PI_Item_Product_ID
-- 2025-06-27T10:23:26.698Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=634189
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.1
-- Column: M_Material_Needs_Planner_V.Total_Qty_One_Week_Ago
-- 2025-06-27T10:23:26.708Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630637
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.2
-- Column: M_Material_Needs_Planner_V.Total_Qty_Two_Weeks_Ago
-- 2025-06-27T10:23:26.717Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630638
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.3
-- Column: M_Material_Needs_Planner_V.Total_Qty_Three_Weeks_Ago
-- 2025-06-27T10:23:26.726Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630639
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.4
-- Column: M_Material_Needs_Planner_V.Total_Qty_Four_Weeks_Ago
-- 2025-06-27T10:23:26.734Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630640
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.5
-- Column: M_Material_Needs_Planner_V.Total_Qty_Five_Weeks_Ago
-- 2025-06-27T10:23:26.740Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630641
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.6
-- Column: M_Material_Needs_Planner_V.Total_Qty_Six_Weeks_Ago
-- 2025-06-27T10:23:26.745Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630642
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.6 Wo-Schnitt
-- Column: M_Material_Needs_Planner_V.Average_Qty_Last_Six_Weeks
-- 2025-06-27T10:23:26.750Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630643
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lagerbestand
-- Column: M_Material_Needs_Planner_V.QuantityOnHand
-- 2025-06-27T10:23:26.755Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630644
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Maximalmenge
-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-06-27T10:23:26.759Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630664
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lager
-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-06-27T10:23:26.762Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-06-27 10:23:26.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630652
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Level_Min
-- 2025-06-27T10:54:26.571Z
UPDATE AD_Process_Para SET DisplayLogic='1=0', IsActive='Y',Updated=TO_TIMESTAMP('2025-06-27 10:54:26.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542932
;
