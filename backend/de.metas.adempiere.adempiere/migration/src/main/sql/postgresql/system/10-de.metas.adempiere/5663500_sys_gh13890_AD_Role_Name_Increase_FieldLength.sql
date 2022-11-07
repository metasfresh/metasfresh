
SELECT public.db_alter_table('AD_Role','ALTER TABLE ad_role ALTER COLUMN name TYPE varchar(256) USING name::varchar(256)');

UPDATE AD_Column Set fieldlength=256, updated='2022-11-07 14:58', updatedby=99 WHERE ad_Table_id = get_table_id('AD_Role') and columnname='Name';
