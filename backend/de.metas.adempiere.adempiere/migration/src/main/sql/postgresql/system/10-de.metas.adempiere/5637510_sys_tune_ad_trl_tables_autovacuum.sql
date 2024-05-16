DO
$$
    DECLARE
        v_tableInfo record;
    BEGIN
        FOR v_tableInfo IN (SELECT pg_namespace.nspname AS schema,
                                   relname              AS tablename,
                                   reloptions
                            FROM pg_class
                                     INNER JOIN pg_namespace ON pg_namespace.oid = pg_class.relnamespace
                            WHERE TRUE
                              AND pg_namespace.nspname = 'public'
                              AND relname LIKE 'ad\_%\_trl' ESCAPE '\'
                                AND NOT (COALESCE(reloptions::text, '') LIKE '%autovacuum%')
                            ORDER BY relname)
            LOOP
                EXECUTE 'ALTER TABLE ' || QUOTE_IDENT(v_tableInfo.schema) || '.' || QUOTE_IDENT(v_tableInfo.tablename) || ' SET (AUTOVACUUM_VACUUM_SCALE_FACTOR = 0);';
                EXECUTE 'ALTER TABLE ' || QUOTE_IDENT(v_tableInfo.schema) || '.' || QUOTE_IDENT(v_tableInfo.tablename) || ' SET (AUTOVACUUM_VACUUM_THRESHOLD = 1000);';
                RAISE NOTICE 'Configured autovacuum for %.%', v_tableInfo.schema, v_tableInfo.tablename;
            END LOOP;
    END;
$$
;


-- After running this script, following tables will have autovacuum configured:
/*
schema	tablename	reloptions
public	ad_boilerplate_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_column_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_desktop_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_element_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_field_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_fieldgroup_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_form_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_index_table_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_infocolumn_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_infowindow_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_menu_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_message_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_printformatitem_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_printlabelline_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_process_para_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_process_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_ref_list_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_reference_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_tab_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_table_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_task_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_ui_section_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_wf_node_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_window_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_workbench_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
public	ad_workflow_trl	{autovacuum_vacuum_scale_factor=0,autovacuum_vacuum_threshold=1000}
 */
