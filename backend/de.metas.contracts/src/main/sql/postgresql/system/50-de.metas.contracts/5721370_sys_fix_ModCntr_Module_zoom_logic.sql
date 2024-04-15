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
-- 2024-04-11T10:03:34.757Z
UPDATE AD_Tab SET Parent_Column_ID=NULL, SeqNo=10,Updated=TO_TIMESTAMP('2024-04-11 13:03:34.757','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547014
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine
-- Table: ModCntr_Module
-- 2024-04-11T10:06:44.595Z
UPDATE AD_Tab SET SeqNo=20,Updated=TO_TIMESTAMP('2024-04-11 13:06:44.595','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547014
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

-- Column: ModCntr_Module.ModCntr_Settings_ID
-- 2024-04-11T10:32:12.223Z
UPDATE AD_Column SET AD_Reference_ID=19, AD_Reference_Value_ID=541830, IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-11 13:32:12.223','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586804
;

-- Column: ModCntr_Module.ModCntr_Settings_ID
-- 2024-04-11T10:34:43.381Z
UPDATE AD_Column SET AD_Reference_Value_ID=NULL, IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-11 13:34:43.381','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586804
;

-- Name: ModCntr_Settings
-- 2024-04-11T10:38:13.263Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541866,TO_TIMESTAMP('2024-04-11 13:38:13.041','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','ModCntr_Settings',TO_TIMESTAMP('2024-04-11 13:38:13.041','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2024-04-11T10:38:13.267Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541866 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Settings
-- Table: ModCntr_Settings
-- Key: ModCntr_Settings.ModCntr_Settings_ID
-- 2024-04-11T10:38:26.095Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,586789,0,541866,542339,TO_TIMESTAMP('2024-04-11 13:38:26.092','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','N','N',TO_TIMESTAMP('2024-04-11 13:38:26.092','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Reference: ModCntr_Settings
-- Table: ModCntr_Settings
-- Key: ModCntr_Settings.ModCntr_Settings_ID
-- 2024-04-11T10:38:30.830Z
UPDATE AD_Ref_Table SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-11 13:38:30.83','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541866
;

-- Column: ModCntr_Module.ModCntr_Settings_ID
-- 2024-04-11T10:38:42.866Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=541866, IsExcludeFromZoomTargets='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-11 13:38:42.866','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586804
;

-- 2024-04-11T10:39:12.083Z
INSERT INTO t_alter_column values('modcntr_module','ModCntr_Settings_ID','NUMERIC(10)',null,null)
;

-- Column: ModCntr_Module.ModCntr_Settings_ID
-- 2024-04-11T10:43:04.896Z
UPDATE AD_Column SET AD_Reference_ID=19, IsExcludeFromZoomTargets='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-11 13:43:04.896','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586804
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine
-- Table: ModCntr_Module
-- 2024-04-11T10:43:37.756Z
UPDATE AD_Tab SET Parent_Column_ID=586789,Updated=TO_TIMESTAMP('2024-04-11 13:43:37.756','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547014
;

