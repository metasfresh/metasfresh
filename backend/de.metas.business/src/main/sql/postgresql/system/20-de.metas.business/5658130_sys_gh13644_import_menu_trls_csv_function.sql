-- backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/system/10-de.metas.adempiere/5658170_sys_fix_trl_migration.sql
SELECT public.db_alter_table('ad_tab_trl', 'alter table public.ad_tab_trl ALTER COLUMN Name TYPE varchar(255)')
;
SELECT public.db_alter_table('ad_window_trl', 'alter table public.ad_window_trl ALTER COLUMN Name TYPE varchar(255)')
;
SELECT public.db_alter_table('AD_Menu_Trl', 'alter table public.AD_Menu_Trl ALTER COLUMN Name TYPE varchar(255)')
;
SELECT public.db_alter_table('AD_Menu_Trl', 'alter table public.AD_Menu_Trl ALTER COLUMN webui_namebrowse TYPE varchar(255)')
;
SELECT public.db_alter_table('AD_Menu_Trl', 'alter table public.AD_Menu_Trl ALTER COLUMN webui_namenew TYPE varchar(255)')
;
SELECT public.db_alter_table('AD_Menu_Trl', 'alter table public.AD_Menu_Trl ALTER COLUMN webui_namenewbreadcrumb TYPE varchar(255)')
;

--
SELECT public.db_alter_table('AD_Process_Trl', 'alter table public.AD_Process_Trl ALTER COLUMN name TYPE varchar(255)')
;

SELECT public.db_alter_table('AD_Ref_List_Trl', 'alter table public.AD_Ref_List_Trl ALTER COLUMN name TYPE varchar(255)')
;
SELECT public.db_alter_table('AD_Reference_Trl', 'alter table public.AD_Reference_Trl ALTER COLUMN name TYPE varchar(255)')
;
SELECT public.db_alter_table('C_ElementValue_Trl', 'alter table public.C_ElementValue_Trl ALTER COLUMN name TYPE varchar(255)')
;
SELECT public.db_alter_table('C_Tax_Trl', 'alter table public.C_Tax_Trl ALTER COLUMN name TYPE varchar(255)')
;





DO
$$
    BEGIN
        UPDATE ad_element_trl
        SET name                    = data.name_trl
          , printname               = data.name_trl
          , description             = data.description_trl
          , webui_namenew           = data.webui_namenew_trl
          , webui_namebrowse        = data.webui_namebrowse_trl
          , webui_namenewbreadcrumb = data.webui_namenewbreadcrumb_trl
        FROM migration_data."AD_Menu_Trad" data
                 INNER JOIN ad_menu m ON data.ad_menu_id = m.ad_menu_id
        WHERE ad_element_trl.ad_element_id = m.ad_element_id
          AND ad_language IN ('fr_CH', 'fr_FR');

        PERFORM update_trl_tables_on_ad_element_trl_update(NULL, NULL);
    END;
$$
;
