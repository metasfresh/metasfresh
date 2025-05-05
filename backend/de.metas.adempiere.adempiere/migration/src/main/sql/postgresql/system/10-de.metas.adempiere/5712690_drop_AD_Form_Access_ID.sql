-- Run mode: SWING_CLIENT

-- 2023-12-06T08:51:07.358Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558412
;

-- Field: Rolle - Verwaltung(111,D) -> Fenster (nicht dynamisch)-Zugriff(306,D) -> AD_Form_Access
-- Column: AD_Form_Access.AD_Form_Access_ID
-- 2023-12-06T08:51:07.362Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558412
;

-- 2023-12-06T08:51:07.368Z
DELETE FROM AD_Field WHERE AD_Field_ID=558412
;

-- 2023-12-06T08:51:07.381Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558413
;

-- Field: Fenster (nicht dynamisch)(187,D) -> Berechtigung(309,D) -> AD_Form_Access
-- Column: AD_Form_Access.AD_Form_Access_ID
-- 2023-12-06T08:51:07.385Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558413
;

-- 2023-12-06T08:51:07.391Z
DELETE FROM AD_Field WHERE AD_Field_ID=558413
;

-- 2023-12-06T08:51:07.409Z
/* DDL */ SELECT public.db_alter_table('AD_Form_Access','ALTER TABLE AD_Form_Access DROP COLUMN IF EXISTS AD_Form_Access_ID')
;

-- Column: AD_Form_Access.AD_Form_Access_ID
-- 2023-12-06T08:51:07.428Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=556779
;

-- 2023-12-06T08:51:07.434Z
DELETE FROM AD_Column WHERE AD_Column_ID=556779
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
    where t.tablename ilike 'ad_form_access' and c.isparent='Y';
*/

ALTER TABLE ad_form_access DROP CONSTRAINT IF EXISTS ad_form_access_pkey;
ALTER TABLE ad_form_access ADD CONSTRAINT ad_form_access_pkey PRIMARY KEY (ad_role_id, ad_form_id);
DROP SEQUENCE IF EXISTS ad_form_access_seq;

