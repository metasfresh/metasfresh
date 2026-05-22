-- Run mode: SWING_CLIENT

-- Column: AD_PInstance.CreatedBy
-- 2026-05-21T20:11:24.757Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540401, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-05-21 20:11:24.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8224
;

-- 2026-05-21T20:17:11.894Z
UPDATE AD_User SET IsSystemUser='Y', Login='Migration', Value='migratio',Updated=TO_TIMESTAMP('2026-05-21 20:17:11.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_User_ID=99
;
-- Column: AD_PInstance_Log.P_Date
-- 2026-05-21T20:23:02.585Z
UPDATE AD_Column SET AD_Reference_ID=16, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-05-21 20:23:02.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8782
;

-- 2026-05-21T20:25:21.953Z
INSERT INTO t_alter_column values('ad_pinstance_log','P_Date','TIMESTAMP WITH TIME ZONE',null,null)
;

-- UI Element: Prozess-Revision(332,D) -> Protokoll(665,D) -> main -> 10 -> default.Process Message
-- Column: AD_PInstance_Log.P_Msg
-- 2026-05-21T20:29:02.936Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-05-21 20:29:02.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547987
;

-- UI Element: Prozess-Revision(332,D) -> Protokoll(665,D) -> main -> 10 -> default.Process Number
-- Column: AD_PInstance_Log.P_Number
-- 2026-05-21T20:29:10.069Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-05-21 20:29:10.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547986
;

-- UI Element: Prozess-Revision(332,D) -> Protokoll(665,D) -> main -> 10 -> default.Process Date
-- Column: AD_PInstance_Log.P_Date
-- 2026-05-21T20:29:33.132Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-05-21 20:29:33.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547985
;

-- UI Element: Prozess-Revision(332,D) -> Protokoll(665,D) -> main -> 10 -> default.Eintrag-Nr
-- Column: AD_PInstance_Log.Log_ID
-- 2026-05-21T20:30:52.267Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-05-21 20:30:52.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547984
;

-- Column: AD_PInstance.AD_Table_ID
-- 2026-05-22T07:34:10.878Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-05-22 07:34:10.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551938
;

-- Column: AD_PInstance.ErrorMsg
-- 2026-05-22T07:34:47.012Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-05-22 07:34:47.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=3433
;

-- Column: AD_PInstance.IsProcessing
-- 2026-05-22T07:35:19.692Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-05-22 07:35:19.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2783
;

-- Column: AD_PInstance.AD_User_ID
-- 2026-05-22T07:35:44.563Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-05-22 07:35:44.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=5951
;
