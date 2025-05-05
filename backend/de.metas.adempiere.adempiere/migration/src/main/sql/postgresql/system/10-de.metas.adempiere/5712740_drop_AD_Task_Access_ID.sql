-- Run mode: SWING_CLIENT

-- 2023-12-06T08:58:20.801Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558416
;

-- Field: Rolle - Verwaltung(111,D) -> Aufgaben-Zugriff(313,D) -> AD_Task_Access
-- Column: AD_Task_Access.AD_Task_Access_ID
-- 2023-12-06T08:58:20.805Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558416
;

-- 2023-12-06T08:58:20.810Z
DELETE FROM AD_Field WHERE AD_Field_ID=558416
;

-- 2023-12-06T08:58:20.821Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558417
;

-- Field: Aufgabe(114,D) -> Berechtigung(310,D) -> AD_Task_Access
-- Column: AD_Task_Access.AD_Task_Access_ID
-- 2023-12-06T08:58:20.824Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558417
;

-- 2023-12-06T08:58:20.833Z
DELETE FROM AD_Field WHERE AD_Field_ID=558417
;

-- 2023-12-06T08:58:20.837Z
/* DDL */ SELECT public.db_alter_table('AD_Task_Access','ALTER TABLE AD_Task_Access DROP COLUMN IF EXISTS AD_Task_Access_ID')
;

-- Column: AD_Task_Access.AD_Task_Access_ID
-- 2023-12-06T08:58:20.855Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=556781
;

-- 2023-12-06T08:58:20.860Z
DELETE FROM AD_Column WHERE AD_Column_ID=556781
;


--
--
--
--
--

/*
select t.tablename, c.columnname
    from ad_table t
    inner join ad_column c on c.ad_table_id=t.ad_table_id
    where t.tablename ilike 'ad_task_access' and c.isparent='Y';
*/

ALTER TABLE ad_task_access DROP CONSTRAINT IF EXISTS ad_task_access_pkey;
ALTER TABLE ad_task_access ADD CONSTRAINT ad_task_access_pkey PRIMARY KEY (ad_role_id, ad_task_id);
DROP SEQUENCE IF EXISTS ad_task_access_seq;

