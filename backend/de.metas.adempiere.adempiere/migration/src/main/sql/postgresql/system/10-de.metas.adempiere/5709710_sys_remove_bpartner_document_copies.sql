SELECT backup_table('C_BPartner');

SELECT backup_table('exp_formatline');
DELETE FROM exp_formatline
WHERE AD_Column_ID = 3086
;

SELECT backup_table('AD_Element_Link');
DELETE FROM ad_element_link
WHERE ad_field_id IN (SELECT ad_field_id FROM ad_field WHERE AD_Column_ID = 3086)
;

SELECT backup_table('AD_UI_Element');
DELETE FROM AD_UI_Element
WHERE ad_field_id IN (SELECT ad_field_id FROM ad_field WHERE AD_Column_ID = 3086)
;

SELECT backup_table('AD_Field_Trl');
DELETE FROM ad_field_trl
WHERE ad_field_id IN (SELECT ad_field_id FROM ad_field WHERE AD_Column_ID = 3086)
;

SELECT backup_table('AD_Field');
DELETE FROM ad_field
WHERE ad_field_id IN (SELECT ad_field_id FROM ad_field WHERE AD_Column_ID = 3086)
;

-- 2023-11-07T08:46:42.545Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE C_BPartner DROP COLUMN IF EXISTS DocumentCopies')
;

-- Column: C_BPartner.DocumentCopies
-- Column: C_BPartner.DocumentCopies
-- 2023-11-07T08:46:45.782Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=3086
;

-- 2023-11-07T08:46:45.787Z
DELETE FROM AD_Column WHERE AD_Column_ID=3086
;

