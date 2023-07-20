-- Tab: Simulationsplan -> WO Project Step Simulation
-- Table: C_Project_WO_Step_Simulation
-- 2023-04-03T08:36:11.778Z
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2023-04-03 11:36:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546391
;

-- Field: Simulationsplan -> WO Project Step Simulation -> WO Project Step Simulation
-- Column: C_Project_WO_Step_Simulation.C_Project_WO_Step_Simulation_ID
-- 2023-04-03T08:41:50.793Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-04-03 11:41:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700771
;

-- Field: Simulationsplan -> WO Project Step Simulation -> Project Step
-- Column: C_Project_WO_Step_Simulation.C_Project_WO_Step_ID
-- 2023-04-03T08:41:54.153Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-04-03 11:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700772
;

-- Field: Simulationsplan -> WO Project Step Simulation -> Endzeitpunkt
-- Column: C_Project_WO_Step_Simulation.DateEnd
-- 2023-04-03T08:41:57.772Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-04-03 11:41:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700776
;

-- Field: Simulationsplan -> WO Project Step Simulation -> Simulationsplan
-- Column: C_Project_WO_Step_Simulation.C_SimulationPlan_ID
-- 2023-04-03T08:42:00.749Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-04-03 11:42:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700774
;

-- Field: Simulationsplan -> WO Project Step Simulation -> Projekt
-- Column: C_Project_WO_Step_Simulation.C_Project_ID
-- 2023-04-03T08:45:14.110Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-04-03 11:45:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700773
;

-- Field: Simulationsplan -> WO Project Step Simulation -> Startdatum
-- Column: C_Project_WO_Step_Simulation.DateStart
-- 2023-04-03T08:45:18.259Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-04-03 11:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700775
;

-- Field: Simulationsplan -> WO Project Step Simulation -> Sektion
-- Column: C_Project_WO_Step_Simulation.AD_Org_ID
-- 2023-04-03T08:45:23.996Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-04-03 11:45:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700769
;

-- Field: Simulationsplan -> WO Project Step Simulation -> Aktiv
-- Column: C_Project_WO_Step_Simulation.IsActive
-- 2023-04-03T08:45:27.450Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-04-03 11:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700770
;


-- Column: C_Project_WO_Step_Simulation.C_Project_WO_Step_ID
-- 2023-04-04T08:03:22.112Z
UPDATE AD_Column SET IsParent='N',Updated=TO_TIMESTAMP('2023-04-04 11:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583507
;



-- 2023-04-04T08:03:26.604Z
INSERT INTO t_alter_column values('c_project_wo_step_simulation','C_Project_WO_Step_ID','NUMERIC(10)',null,null)
;

