-- Run mode: SWING_CLIENT

-- Column: M_Material_Needs_Planner_V.IsBOM
-- 2025-08-12T13:18:18.921Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,590604,1326,0,20,542466,'IsBOM',TO_TIMESTAMP('2025-08-12 13:18:18.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stückliste','D',1,'Das Selektionsfeld "Stückliste" zeigt an, ob dieses Produkt aus Produkten auf einer Stückliste besteht.','Y','Y','N','N','N','N','N','N','N','N','N','Stückliste',TO_TIMESTAMP('2025-08-12 13:18:18.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-12T13:18:19.320Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590604 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-12T13:18:19.474Z
/* DDL */  select update_Column_Translation_From_AD_Element(1326)
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Stückliste
-- Column: M_Material_Needs_Planner_V.IsBOM
-- 2025-08-12T13:19:09.790Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590604,751859,0,547920,0,TO_TIMESTAMP('2025-08-12 13:19:08.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stückliste',0,'D',0,'Das Selektionsfeld "Stückliste" zeigt an, ob dieses Produkt aus Produkten auf einer Stückliste besteht.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Stückliste',0,0,10,0,1,1,TO_TIMESTAMP('2025-08-12 13:19:08.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-12T13:19:10.123Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-12T13:19:10.180Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1326)
;

-- 2025-08-12T13:19:10.246Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751859
;

-- 2025-08-12T13:19:10.301Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751859)
;

-- 2025-08-12T13:19:46.544Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583856,0,TO_TIMESTAMP('2025-08-12 13:19:45.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Stückliste vorhanden','Stückliste vorhanden',TO_TIMESTAMP('2025-08-12 13:19:45.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-12T13:19:46.719Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583856 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-08-12T13:20:31.367Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='BOM Version', PrintName='BOM Version',Updated=TO_TIMESTAMP('2025-08-12 13:20:31.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583856 AND AD_Language='en_US'
;

-- 2025-08-12T13:20:31.420Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-12T13:20:35.449Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583856,'en_US')
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Stückliste vorhanden
-- Column: M_Material_Needs_Planner_V.IsBOM
-- 2025-08-12T13:21:03.425Z
UPDATE AD_Field SET AD_Name_ID=583856, Description=NULL, Help=NULL, Name='Stückliste vorhanden',Updated=TO_TIMESTAMP('2025-08-12 13:21:03.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=751859
;

-- 2025-08-12T13:21:03.588Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Stückliste vorhanden' WHERE AD_Field_ID=751859 AND AD_Language='de_DE'
;

-- 2025-08-12T13:21:03.641Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583856)
;

-- 2025-08-12T13:21:03.699Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751859
;

-- 2025-08-12T13:21:03.754Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751859)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 20 -> default.Stückliste vorhanden
-- Column: M_Material_Needs_Planner_V.IsBOM
-- 2025-08-12T13:35:50.610Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,751859,0,547920,552635,636138,'F',TO_TIMESTAMP('2025-08-12 13:35:49.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Stückliste vorhanden',20,0,0,TO_TIMESTAMP('2025-08-12 13:35:49.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 20 -> default.Stückliste vorhanden
-- Column: M_Material_Needs_Planner_V.IsBOM
-- 2025-08-12T13:36:12.487Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-08-12 13:36:12.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636138
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Packvorschrift
-- Column: M_Material_Needs_Planner_V.M_HU_PI_Item_Product_ID
-- 2025-08-12T13:36:12.884Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-08-12 13:36:12.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=634189
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.1
-- Column: M_Material_Needs_Planner_V.Total_Qty_One_Week_Ago
-- 2025-08-12T13:36:13.276Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-08-12 13:36:13.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630637
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.2
-- Column: M_Material_Needs_Planner_V.Total_Qty_Two_Weeks_Ago
-- 2025-08-12T13:36:13.670Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-08-12 13:36:13.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630638
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.3
-- Column: M_Material_Needs_Planner_V.Total_Qty_Three_Weeks_Ago
-- 2025-08-12T13:36:14.062Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-08-12 13:36:14.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630639
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.4
-- Column: M_Material_Needs_Planner_V.Total_Qty_Four_Weeks_Ago
-- 2025-08-12T13:36:14.453Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-08-12 13:36:14.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630640
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.5
-- Column: M_Material_Needs_Planner_V.Total_Qty_Five_Weeks_Ago
-- 2025-08-12T13:36:14.850Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-08-12 13:36:14.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630641
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.6
-- Column: M_Material_Needs_Planner_V.Total_Qty_Six_Weeks_Ago
-- 2025-08-12T13:36:15.244Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-08-12 13:36:15.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630642
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.6 Wo-Schnitt
-- Column: M_Material_Needs_Planner_V.Average_Qty_Last_Six_Weeks
-- 2025-08-12T13:36:15.636Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-08-12 13:36:15.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630643
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lagerbestand
-- Column: M_Material_Needs_Planner_V.QuantityOnHand
-- 2025-08-12T13:36:16.022Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-08-12 13:36:16.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630644
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-08-12T13:36:16.406Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-08-12 13:36:16.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630663
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Maximalmenge
-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-08-12T13:36:16.792Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-08-12 13:36:16.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630664
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lager
-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-08-12T13:36:17.190Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-08-12 13:36:17.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630652
;

-- Column: M_Material_Needs_Planner_V.IsBOM
-- 2025-08-12T13:37:12.586Z
UPDATE AD_Column SET FilterDefaultValue='Y', FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-12 13:37:12.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590604
;

-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-08-12T13:38:29.366Z
UPDATE AD_Column SET FilterDefaultValue='540008', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-08-12 13:38:29.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589737
;

