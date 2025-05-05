-- Run mode: SWING_CLIENT

-- 2023-12-06T08:52:23.978Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558414
;

-- Field: Rolle - Verwaltung(111,D) -> Prozess-Zugriff(305,D) -> Process Access
-- Column: AD_Process_Access.AD_Process_Access_ID
-- 2023-12-06T08:52:23.981Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558414
;

-- 2023-12-06T08:52:23.988Z
DELETE FROM AD_Field WHERE AD_Field_ID=558414
;

-- 2023-12-06T08:52:23.995Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558415
;

-- Field: Bericht & Prozess(165,D) -> Berechtigung Berichts Zugriff(308,D) -> Process Access
-- Column: AD_Process_Access.AD_Process_Access_ID
-- 2023-12-06T08:52:23.998Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558415
;

-- 2023-12-06T08:52:24.004Z
DELETE FROM AD_Field WHERE AD_Field_ID=558415
;

-- 2023-12-06T08:52:24.008Z
/* DDL */ SELECT public.db_alter_table('AD_Process_Access','ALTER TABLE AD_Process_Access DROP COLUMN IF EXISTS AD_Process_Access_ID')
;

-- Column: AD_Process_Access.AD_Process_Access_ID
-- 2023-12-06T08:52:24.018Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=556780
;

-- 2023-12-06T08:52:24.025Z
DELETE FROM AD_Column WHERE AD_Column_ID=556780
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
    where t.tablename ilike 'ad_process_access' and c.isparent='Y';
*/

ALTER TABLE ad_process_access DROP CONSTRAINT IF EXISTS ad_process_access_pkey;
ALTER TABLE ad_process_access ADD CONSTRAINT ad_process_access_pkey PRIMARY KEY (ad_role_id, ad_process_id);
DROP SEQUENCE IF EXISTS ad_process_access_seq;

