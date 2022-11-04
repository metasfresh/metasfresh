-- UI Element: Simulation Plan -> WO Project Resource Simulation.Project Step
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Step_ID
-- 2022-07-13T08:35:45.702574400Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=609645
;

-- 2022-07-13T08:35:45.715985Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700783
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Project Step
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Step_ID
-- 2022-07-13T08:35:45.724451Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=700783
;

-- 2022-07-13T08:35:45.725450Z
DELETE FROM AD_Field WHERE AD_Field_ID=700783
;

-- 2022-07-13T08:35:45.728450800Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource_Simulation','ALTER TABLE C_Project_WO_Resource_Simulation DROP COLUMN IF EXISTS C_Project_WO_Step_ID')
;

-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Step_ID
-- 2022-07-13T08:35:45.769157700Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583522
;

-- 2022-07-13T08:35:45.771157400Z
DELETE FROM AD_Column WHERE AD_Column_ID=583522
;

