-- Run mode: SWING_CLIENT

-- 2023-12-06T08:49:58.566Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558409
;

-- Field: Rolle - Verwaltung(111,D) -> Fenster-Zugriff(304,D) -> AD_Window_Access
-- Column: AD_Window_Access.AD_Window_Access_ID
-- 2023-12-06T08:49:58.577Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558409
;

-- 2023-12-06T08:49:58.586Z
DELETE FROM AD_Field WHERE AD_Field_ID=558409
;

-- 2023-12-06T08:49:58.603Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558410
;

-- Field: Fenster Verwaltung(102,D) -> Berechtigung(311,D) -> AD_Window_Access
-- Column: AD_Window_Access.AD_Window_Access_ID
-- 2023-12-06T08:49:58.610Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558410
;

-- 2023-12-06T08:49:58.615Z
DELETE FROM AD_Field WHERE AD_Field_ID=558410
;

-- 2023-12-06T08:49:58.621Z
/* DDL */ SELECT public.db_alter_table('AD_Window_Access','ALTER TABLE AD_Window_Access DROP COLUMN IF EXISTS AD_Window_Access_ID')
;

-- Column: AD_Window_Access.AD_Window_Access_ID
-- 2023-12-06T08:49:58.646Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=556777
;

-- 2023-12-06T08:49:58.729Z
DELETE FROM AD_Column WHERE AD_Column_ID=556777
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
    where t.tablename ilike 'ad_window_access' and c.isparent='Y';
*/

ALTER TABLE ad_window_access DROP CONSTRAINT IF EXISTS ad_window_access_pkey;
ALTER TABLE ad_window_access ADD CONSTRAINT ad_window_access_pkey PRIMARY KEY (ad_role_id, ad_window_id);
DROP SEQUENCE IF EXISTS ad_window_access_seq;

