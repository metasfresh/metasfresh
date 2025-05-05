-- Run mode: SWING_CLIENT

-- 2023-12-06T09:00:35.490Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558419
;

-- Field: Workflow(113,D) -> Berechtigung(312,D) -> AD_Workflow_Access
-- Column: AD_Workflow_Access.AD_Workflow_Access_ID
-- 2023-12-06T09:00:35.493Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558419
;

-- 2023-12-06T09:00:35.497Z
DELETE FROM AD_Field WHERE AD_Field_ID=558419
;

-- 2023-12-06T09:00:35.505Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558420
;

-- Field: Produktion Arbeitsablauf(53005,EE01) -> Access(53020,EE01) -> AD_Workflow_Access
-- Column: AD_Workflow_Access.AD_Workflow_Access_ID
-- 2023-12-06T09:00:35.508Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558420
;

-- 2023-12-06T09:00:35.512Z
DELETE FROM AD_Field WHERE AD_Field_ID=558420
;

-- 2023-12-06T09:00:35.522Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558418
;

-- Field: Rolle - Verwaltung(111,D) -> Workflow-Zugriff(307,D) -> AD_Workflow_Access
-- Column: AD_Workflow_Access.AD_Workflow_Access_ID
-- 2023-12-06T09:00:35.526Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558418
;

-- 2023-12-06T09:00:35.529Z
DELETE FROM AD_Field WHERE AD_Field_ID=558418
;

-- 2023-12-06T09:00:35.535Z
/* DDL */ SELECT public.db_alter_table('AD_Workflow_Access','ALTER TABLE AD_Workflow_Access DROP COLUMN IF EXISTS AD_Workflow_Access_ID')
;

-- Column: AD_Workflow_Access.AD_Workflow_Access_ID
-- 2023-12-06T09:00:35.546Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=556782
;

-- 2023-12-06T09:00:35.553Z
DELETE FROM AD_Column WHERE AD_Column_ID=556782
;

