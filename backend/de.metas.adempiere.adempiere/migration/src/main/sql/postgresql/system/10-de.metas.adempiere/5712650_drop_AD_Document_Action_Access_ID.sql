-- Run mode: SWING_CLIENT

-- 2023-12-06T07:50:02.569Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=558411
;

-- Field: Rolle - Verwaltung(111,D) -> Belegaktion-Zugriff(53013,D) -> Document Action Access
-- Column: AD_Document_Action_Access.AD_Document_Action_Access_ID
-- 2023-12-06T07:50:02.663Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=558411
;

-- 2023-12-06T07:50:02.674Z
DELETE FROM AD_Field WHERE AD_Field_ID=558411
;

-- 2023-12-06T07:50:02.748Z
/* DDL */ SELECT public.db_alter_table('AD_Document_Action_Access','ALTER TABLE AD_Document_Action_Access DROP COLUMN IF EXISTS AD_Document_Action_Access_ID')
;

-- Column: AD_Document_Action_Access.AD_Document_Action_Access_ID
-- 2023-12-06T07:50:02.798Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=556778
;

-- 2023-12-06T07:50:02.807Z
DELETE FROM AD_Column WHERE AD_Column_ID=556778
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
    where t.tablename ilike 'AD_Document_Action_Access' and c.isparent='Y';
*/


ALTER TABLE AD_Document_Action_Access DROP CONSTRAINT IF EXISTS ad_document_action_access_pkey;
ALTER TABLE AD_Document_Action_Access ADD CONSTRAINT ad_document_action_access_pkey PRIMARY KEY (ad_role_id, c_doctype_id, ad_ref_list_id);
DROP SEQUENCE IF EXISTS ad_document_action_access_seq;

