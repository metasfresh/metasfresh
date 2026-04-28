-- Run mode: SWING_CLIENT

-- Tab: Wiederauffüllung(541869,D) -> Materialbedarf
-- Table: M_Material_Needs_Planner_V
-- 2025-06-24T12:32:56.186Z
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2025-06-24 12:32:56.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547920
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Sektion
-- Column: M_Material_Needs_Planner_V.AD_Org_ID
-- 2025-06-24T12:33:47.132Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:33:47.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740326
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Aktiv
-- Column: M_Material_Needs_Planner_V.IsActive
-- 2025-06-24T12:33:48.454Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:33:48.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740327
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> -1
-- Column: M_Material_Needs_Planner_V.Total_Qty_One_Week_Ago
-- 2025-06-24T12:35:01.607Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:01.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740331
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> -2
-- Column: M_Material_Needs_Planner_V.Total_Qty_Two_Weeks_Ago
-- 2025-06-24T12:35:02.702Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:02.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740332
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> -3
-- Column: M_Material_Needs_Planner_V.Total_Qty_Three_Weeks_Ago
-- 2025-06-24T12:35:03.989Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:03.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740333
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> -4
-- Column: M_Material_Needs_Planner_V.Total_Qty_Four_Weeks_Ago
-- 2025-06-24T12:35:05.349Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:05.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740334
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> -5
-- Column: M_Material_Needs_Planner_V.Total_Qty_Five_Weeks_Ago
-- 2025-06-24T12:35:06.665Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:06.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740335
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> 6 Wo-Schnitt
-- Column: M_Material_Needs_Planner_V.Average_Qty_Last_Six_Weeks
-- 2025-06-24T12:35:09.394Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:09.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740337
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Lagerbestand
-- Column: M_Material_Needs_Planner_V.QuantityOnHand
-- 2025-06-24T12:35:11.567Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:11.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740338
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> -6
-- Column: M_Material_Needs_Planner_V.Total_Qty_Six_Weeks_Ago
-- 2025-06-24T12:35:13.125Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:13.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740336
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Lager
-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-06-24T12:35:14.225Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:14.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740341
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Produkt Kategorie
-- Column: M_Material_Needs_Planner_V.M_Product_Category_ID
-- 2025-06-24T12:35:15.319Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:15.319000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740342
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Produkt
-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-06-24T12:35:16.774Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-06-24 12:35:16.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740343
;

-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-06-24T13:01:27.640Z
UPDATE AD_Column SET ReadOnlyLogic='@M_Product_ID/0@<0 | @M_Warehouse_ID/0@<0',Updated=TO_TIMESTAMP('2025-06-24 13:01:27.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589752
;

-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-06-24T13:01:32.209Z
UPDATE AD_Column SET ReadOnlyLogic='@M_Product_ID/0@<0 | @M_Warehouse_ID/0@<0',Updated=TO_TIMESTAMP('2025-06-24 13:01:32.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589753
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-06-24T13:08:30.450Z
UPDATE AD_UI_Element SET ViewEditMode='Y',Updated=TO_TIMESTAMP('2025-06-24 13:08:30.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630663
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Maximalmenge
-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-06-24T13:08:36.177Z
UPDATE AD_UI_Element SET ViewEditMode='Y',Updated=TO_TIMESTAMP('2025-06-24 13:08:36.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630664
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Maximalmenge
-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-06-24T14:13:54.066Z
UPDATE AD_UI_Element SET ViewEditMode='D',Updated=TO_TIMESTAMP('2025-06-24 14:13:54.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630664
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-06-24T14:13:56.982Z
UPDATE AD_UI_Element SET ViewEditMode='D',Updated=TO_TIMESTAMP('2025-06-24 14:13:56.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630663
;

