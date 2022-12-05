-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T07:55:22.089712600Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2022-07-15 10:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583687
;

-- Name: C_Project_WO_Resource_with_C_Project_ID
-- 2022-07-15T08:03:58.650741100Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540587,'C_Project_WO_Resource.C_Project_ID=@C_Project_ID@',TO_TIMESTAMP('2022-07-15 11:03:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Project_WO_Resource_with_C_Project_ID','S',TO_TIMESTAMP('2022-07-15 11:03:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_Project_WO_Resource_with_C_Project_ID2
-- 2022-07-15T08:04:14.355042900Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540588,'C_Project_WO_Resource.C_Project_ID=@C_Project_ID2@',TO_TIMESTAMP('2022-07-15 11:04:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Project_WO_Resource_with_C_Project_ID2','S',TO_TIMESTAMP('2022-07-15 11:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T08:04:43.352362200Z
UPDATE AD_Column SET AD_Val_Rule_ID=540587,Updated=TO_TIMESTAMP('2022-07-15 11:04:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583687
;

-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource2_ID
-- 2022-07-15T08:04:59.190592400Z
UPDATE AD_Column SET AD_Val_Rule_ID=540588,Updated=TO_TIMESTAMP('2022-07-15 11:04:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583689
;

-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_ID
-- 2022-07-15T08:09:52.223990500Z
UPDATE AD_Column SET AD_Val_Rule_ID=540587, IsUpdateable='N',Updated=TO_TIMESTAMP('2022-07-15 11:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583521
;

-- Table: C_Project_WO_Step_Simulation
-- 2022-07-15T08:10:46.479725Z
UPDATE AD_Table SET AD_Window_ID=541541,Updated=TO_TIMESTAMP('2022-07-15 11:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542175
;

-- Name: C_Project_WO_Step_with_C_Project_ID
-- 2022-07-15T08:12:42.453714100Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540589,'C_Project_WO_Step.C_Project_ID=@C_Project_ID@',TO_TIMESTAMP('2022-07-15 11:12:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Project_WO_Step_with_C_Project_ID','S',TO_TIMESTAMP('2022-07-15 11:12:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Project_WO_Step_Simulation.C_Project_WO_Step_ID
-- 2022-07-15T08:12:52.805361300Z
UPDATE AD_Column SET AD_Val_Rule_ID=540589, IsUpdateable='N',Updated=TO_TIMESTAMP('2022-07-15 11:12:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583507
;

-- Table: C_Project_WO_Step
-- 2022-07-15T08:13:13.946307500Z
UPDATE AD_Table SET AD_Window_ID=541512,Updated=TO_TIMESTAMP('2022-07-15 11:13:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542159
;

-- Table: C_Project_WO_Resource
-- 2022-07-15T08:13:27.301161900Z
UPDATE AD_Table SET AD_Window_ID=541512,Updated=TO_TIMESTAMP('2022-07-15 11:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542161
;

-- Table: C_Project_WO_Resource_Simulation
-- 2022-07-15T08:14:23.679878700Z
UPDATE AD_Table SET AD_Window_ID=541541,Updated=TO_TIMESTAMP('2022-07-15 11:14:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542176
;

