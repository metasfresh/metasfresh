-- Run mode: SWING_CLIENT



-- Table: ModCntr_Module
-- 2024-04-11T09:12:03.300Z
UPDATE AD_Table SET AD_Window_ID=541712,Updated=TO_TIMESTAMP('2024-04-11 12:12:03.298','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542340
;

-- Column: ModCntr_Module.ModCntr_Module_ID
-- 2024-04-11T09:14:25.340Z
UPDATE AD_Column SET IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-11 12:14:25.34','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586802
;

-- Column: ModCntr_Module.ModCntr_Module_ID
-- 2024-04-11T09:14:35.013Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-11 12:14:35.013','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586802
;

-- Column: ModCntr_Module.ModCntr_Module_ID
-- 2024-04-11T09:25:58.294Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-11 12:25:58.294','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586802
;

-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-04-11T09:50:57.527Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2024-04-11 12:50:57.527','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588099
;

-- Table: ModCntr_Settings
-- 2024-04-11T09:54:08.920Z
UPDATE AD_Table SET AD_Window_ID=541712,Updated=TO_TIMESTAMP('2024-04-11 12:54:08.919','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542339
;

-- Column: ModCntr_Settings.ModCntr_Settings_ID
-- 2024-04-11T09:56:05.616Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-11 12:56:05.616','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586789
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine
-- Table: ModCntr_Module
-- 2024-04-11T10:09:18.137Z
UPDATE AD_Tab SET IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2024-04-11 13:09:18.137','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547014
;

-- Table: ModCntr_Specific_Price
-- 2024-04-11T10:13:03.527Z
UPDATE AD_Table SET AD_Window_ID=540359,Updated=TO_TIMESTAMP('2024-04-11 13:13:03.526','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542405
;

-- Column: ModCntr_Module.ModCntr_Settings_ID
-- 2024-04-11T10:20:13.976Z
UPDATE AD_Column SET AD_Reference_Value_ID=NULL, IsExcludeFromZoomTargets='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-11 13:20:13.976','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586804
;

-- 2024-04-11T10:39:12.083Z
INSERT INTO t_alter_column values('modcntr_module','ModCntr_Settings_ID','NUMERIC(10)',null,null)
;

-- Column: ModCntr_Module.ModCntr_Settings_ID
-- 2024-04-11T10:43:04.896Z
UPDATE AD_Column SET AD_Reference_ID=19, IsExcludeFromZoomTargets='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-11 13:43:04.896','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586804
;

