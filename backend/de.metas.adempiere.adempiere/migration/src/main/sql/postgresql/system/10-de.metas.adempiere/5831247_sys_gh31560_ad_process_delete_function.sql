-- Fixes failure of: 5831250_sys_gh31560_Drop_Open_items_Document_Based_Process.sql (sorted before it so that 5831248 can call the function)
-- Source DDL: backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/ddl/public/functions/ad_process_delete.sql

DROP FUNCTION IF EXISTS ad_process_delete(numeric)
;

/*
 * Deletes one AD_Process and handles every row that references it via a foreign key, so the delete does not
 * fail on databases that carry extra references (customer menu entries, archived report runs, print jobs, ...).
 *
 * Per-table policy for the foreign keys on AD_Process:
 *   FK_CASCADE  - removed by the FK itself: AD_Process_Para, AD_Process_Trl, AD_Scheduler (its AD_SchedulerRecipient
 *                 rows are deleted first, since that FK does not cascade), AD_Role_PermRequest
 *   FK_SET_NULL - nulled by the FK itself: AD_Column.AD_Process_ID, AD_Issue.AD_Process_ID
 *   DELETE      - row deleted: AD_Menu (with its AD_Menu_Trl and menu-tree AD_TreeNodeMM rows), AD_Table_Process,
 *                 AD_WF_Node (with its AD_WF_Node_Para rows), AD_WorkbenchWindow, AD_PrinterRouting, AD_MailConfig,
 *                 M_HU_Process
 *   SET_NULL    - column set to NULL: AD_Tab.AD_Process_ID
 *   REPOINT     - column set to the inactive placeholder process 585692 "Process was deleted", because the row
 *                 itself must survive: AD_Archive, C_Printing_Queue, C_Queue_Block, AD_PrintFormat.JasperProcess_ID,
 *                 AD_BoilerPlate.JasperProcess_ID, PA_Report.JasperProcess_ID, M_HU_Label_Config.LabelReport_Process_ID,
 *                 ExternalSystem_Config_ScriptedExportConversion.AD_Process_OutboundData_ID,
 *                 Leichmehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
 * Tables or columns missing on a database are skipped.
 * AD_Process_Access (role grants) and AD_Process_Stats rows of the process (no FK) are deleted as well.
 *
 * Backups: the affected rows - and only those - are copied to backup.<first 20 chars of the table>_bkp_<timestamp>_adpdel_<AD_Process_ID>
 * (registered in backup.backup_tables) before anything is changed, for
 *   - the tables marked BACKUP in the policy: business tables (AD_Archive, C_Printing_Queue, C_Queue_Block, PA_Report,
 *     M_HU_Label_Config, M_HU_Process, ExternalSystem_Config_ScriptedExportConversion, Leichmehl_PluFile_ConfigGroup)
 *     and operator-configured AD rows (AD_PrinterRouting, AD_MailConfig);
 *   - AD_Scheduler (with its AD_Scheduler_Para and AD_SchedulerRecipient rows) and AD_Process_Access.
 * backup_table() is not used because it always copies the whole table (AD_Archive holds the archived documents).
 * A table without affected rows gets no backup table. The name stays within PostgreSQL's 63-character identifier
 * limit and is unique per process, also when several processes are deleted in one transaction.
 * In addition, for every policy table the IDs of the changed rows (the 100 lowest) are written to the log via RAISE NOTICE.
 *
 * Before changing anything, the function refuses (raises an exception) when
 *   - p_AD_Process_ID is the placeholder process itself, or the placeholder does not exist;
 *   - a row references the process through a non-cascading FK that the policy does not handle
 *     (e.g. a customer table, an FK whose delete rule differs from the policy above, or a policy table without its
 *     <table>_id key column);
 *   - a row that would be deleted - by this function or by an FK cascade, followed through up to 6 levels of cascading
 *     FKs - is referenced through a non-cascading FK (e.g. AD_PInstance runs of a cascaded AD_Scheduler,
 *     PP_Order_Node rows of a deleted AD_WF_Node, AD_Note rows of a cascaded AD_WF_Activity).
 * Calling it for a process that does not exist (any more) does nothing, so it can be re-run.
 */
CREATE OR REPLACE FUNCTION ad_process_delete(p_AD_Process_ID numeric)
    RETURNS void
AS
$BODY$
DECLARE
    c_ProcessWasDeleted_ID CONSTANT numeric := 585692;
    c_policy               CONSTANT text[]  := ARRAY [
        'ad_process_para.ad_process_id:FK_CASCADE',
        'ad_process_trl.ad_process_id:FK_CASCADE',
        'ad_scheduler.ad_process_id:FK_CASCADE',
        'ad_role_permrequest.ad_process_id:FK_CASCADE',
        'ad_column.ad_process_id:FK_SET_NULL',
        'ad_issue.ad_process_id:FK_SET_NULL',
        'ad_menu.ad_process_id:DELETE',
        'ad_table_process.ad_process_id:DELETE',
        'ad_wf_node.ad_process_id:DELETE',
        'ad_workbenchwindow.ad_process_id:DELETE',
        'ad_printerrouting.ad_process_id:DELETE:BACKUP',
        'ad_mailconfig.ad_process_id:DELETE:BACKUP',
        'm_hu_process.ad_process_id:DELETE:BACKUP',
        'ad_tab.ad_process_id:SET_NULL',
        'ad_archive.ad_process_id:REPOINT:BACKUP',
        'c_printing_queue.ad_process_id:REPOINT:BACKUP',
        'c_queue_block.ad_process_id:REPOINT:BACKUP',
        'ad_printformat.jasperprocess_id:REPOINT',
        'ad_boilerplate.jasperprocess_id:REPOINT',
        'pa_report.jasperprocess_id:REPOINT:BACKUP',
        'm_hu_label_config.labelreport_process_id:REPOINT:BACKUP',
        'externalsystem_config_scriptedexportconversion.ad_process_outbounddata_id:REPOINT:BACKUP',
        'leichmehl_plufile_configgroup.ad_process_customquery_id:REPOINT:BACKUP'
        ];
    v_policy               record;
    v_fk                   record;
    v_count                bigint;
    v_ids                  text;
    v_sql                  text;
    v_blockers             text    := '';
    v_backup               record;
    v_backupTableName      text;
    v_filter               text;
    v_wl_tables            text[];
    v_wl_filters           text[];
    v_wl_depths            int[];
    v_i                    int;
BEGIN
    IF p_AD_Process_ID IS NULL THEN
        RAISE EXCEPTION 'ad_process_delete: p_AD_Process_ID must not be NULL';
    END IF;

    IF p_AD_Process_ID = c_ProcessWasDeleted_ID THEN
        RAISE EXCEPTION 'ad_process_delete: refusing to delete the placeholder process AD_Process_ID=% (Process was deleted)', c_ProcessWasDeleted_ID;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM ad_process WHERE ad_process_id = p_AD_Process_ID) THEN
        RAISE NOTICE 'ad_process_delete: AD_Process_ID=% does not exist; nothing to do', p_AD_Process_ID;
        RETURN;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM ad_process WHERE ad_process_id = c_ProcessWasDeleted_ID) THEN
        RAISE EXCEPTION 'ad_process_delete: placeholder process AD_Process_ID=% (Process was deleted) is missing', c_ProcessWasDeleted_ID;
    END IF;

    --
    -- Refusal checks: all of them run before the first change.
    --

    -- 1. Rows that reference the process through a non-cascading FK which the policy does not handle
    FOR v_fk IN
        SELECT k.conrelid::regclass::text AS ref_table, a.attname::text AS ref_column, k.conname::text AS fk_name
        FROM pg_constraint k
                 JOIN pg_attribute a ON a.attrelid = k.conrelid AND a.attnum = k.conkey[1]
        WHERE k.contype = 'f'
          AND k.confrelid = 'ad_process'::regclass
          AND k.confdeltype IN ('a', 'r') -- NO ACTION, RESTRICT
          AND NOT EXISTS (SELECT 1
                          FROM UNNEST(c_policy) e
                          WHERE split_part(e, '.', 1) = k.conrelid::regclass::text
                            AND split_part(split_part(e, ':', 1), '.', 2) = a.attname::text
                            AND split_part(e, ':', 2) IN ('DELETE', 'SET_NULL', 'REPOINT')
                            AND EXISTS (SELECT 1 -- the apply loop below skips a table without its key column
                                        FROM information_schema.columns c
                                        WHERE c.table_schema = 'public'
                                          AND c.table_name = split_part(e, '.', 1)
                                          AND c.column_name = split_part(e, '.', 1) || '_id'))
        LOOP
            EXECUTE format('SELECT count(*) FROM %s WHERE %I = $1', v_fk.ref_table, v_fk.ref_column) INTO v_count USING p_AD_Process_ID;
            IF v_count > 0 THEN
                v_blockers := v_blockers || format(E'\n  %s row(s) in %s.%s (FK %s, not handled by ad_process_delete)', v_count, v_fk.ref_table, v_fk.ref_column, v_fk.fk_name);
            END IF;
        END LOOP;

    -- 2. Rows that would be deleted (by this function or by FK cascade) but are referenced through a non-cascading FK.
    --    Work list of (table, row filter, depth), starting with the policy tables whose rows are deleted;
    --    cascading FKs - and the children this function deletes itself - are followed, non-cascading FKs are counted.
    SELECT array_agg(split_part(e, '.', 1)), array_agg(format('%I = %L', split_part(split_part(e, ':', 1), '.', 2), p_AD_Process_ID)), array_agg(0)
    INTO v_wl_tables, v_wl_filters, v_wl_depths
    FROM UNNEST(c_policy) e
    WHERE split_part(e, ':', 2) IN ('DELETE', 'FK_CASCADE')
      AND EXISTS (SELECT 1
                  FROM information_schema.columns c
                  WHERE c.table_schema = 'public'
                    AND c.table_name = split_part(e, '.', 1)
                    AND c.column_name = split_part(split_part(e, ':', 1), '.', 2));
    v_i := 1;
    WHILE v_i <= coalesce(array_length(v_wl_tables, 1), 0)
        LOOP
            FOR v_fk IN
                SELECT k.conrelid::regclass::text AS ref_table, ra.attname::text AS ref_column, ta.attname::text AS target_column,
                       k.conname::text AS fk_name, k.confdeltype::text AS delete_rule
                FROM pg_constraint k
                         JOIN pg_attribute ra ON ra.attrelid = k.conrelid AND ra.attnum = k.conkey[1]
                         JOIN pg_attribute ta ON ta.attrelid = k.confrelid AND ta.attnum = k.confkey[1]
                WHERE k.contype = 'f'
                  AND k.confrelid = v_wl_tables[v_i]::regclass
                  AND array_length(k.conkey, 1) = 1
                  AND k.confdeltype IN ('a', 'r', 'c') -- NO ACTION, RESTRICT, CASCADE
                LOOP
                    v_filter := format('%I IN (SELECT %I FROM %s WHERE %s)', v_fk.ref_column, v_fk.target_column, v_wl_tables[v_i], v_wl_filters[v_i]);
                    IF v_fk.delete_rule = 'c'
                        -- children this function deletes itself, see below
                        OR (v_wl_tables[v_i] = 'ad_wf_node' AND v_fk.ref_table = 'ad_wf_node_para')
                        OR (v_wl_tables[v_i] = 'ad_scheduler' AND v_fk.ref_table = 'ad_schedulerrecipient')
                    THEN
                        IF v_wl_depths[v_i] < 6 THEN
                            v_wl_tables := v_wl_tables || v_fk.ref_table;
                            v_wl_filters := v_wl_filters || v_filter;
                            v_wl_depths := v_wl_depths || (v_wl_depths[v_i] + 1);
                        END IF;
                    ELSE
                        EXECUTE format('SELECT count(*) FROM %s WHERE %s', v_fk.ref_table, v_filter) INTO v_count;
                        IF v_count > 0 THEN
                            v_blockers := v_blockers || format(E'\n  %s row(s) in %s.%s still reference the %s row(s) that would be deleted (FK %s)',
                                                               v_count, v_fk.ref_table, v_fk.ref_column, v_wl_tables[v_i], v_fk.fk_name);
                        END IF;
                    END IF;
                END LOOP;
            v_i := v_i + 1;
        END LOOP;

    IF v_blockers <> '' THEN
        RAISE EXCEPTION 'ad_process_delete: cannot delete AD_Process_ID=%; nothing was changed. Blocking references:%', p_AD_Process_ID, v_blockers;
    END IF;

    --
    -- Back up the affected rows (only those) of the tables that need a backup
    --
    FOR v_backup IN
        SELECT split_part(e, '.', 1) AS table_name, format('%I = %L', split_part(split_part(e, ':', 1), '.', 2), p_AD_Process_ID) AS filter
        FROM UNNEST(c_policy) e
        WHERE split_part(e, ':', 3) = 'BACKUP'
          AND EXISTS (SELECT 1
                      FROM information_schema.columns c
                      WHERE c.table_schema = 'public'
                        AND c.table_name = split_part(e, '.', 1)
                        AND c.column_name = split_part(split_part(e, ':', 1), '.', 2))
        UNION ALL
        SELECT x.table_name, x.filter
        FROM (VALUES ('ad_scheduler', format('ad_process_id = %L', p_AD_Process_ID)),
                     ('ad_scheduler_para', format('ad_scheduler_id IN (SELECT s.ad_scheduler_id FROM ad_scheduler s WHERE s.ad_process_id = %L)', p_AD_Process_ID)),
                     ('ad_schedulerrecipient', format('ad_scheduler_id IN (SELECT s.ad_scheduler_id FROM ad_scheduler s WHERE s.ad_process_id = %L)', p_AD_Process_ID)),
                     ('ad_process_access', format('ad_process_id = %L', p_AD_Process_ID))) x(table_name, filter)
        WHERE to_regclass('public.' || x.table_name) IS NOT NULL
        LOOP
            EXECUTE format('SELECT count(*) FROM %I WHERE %s', v_backup.table_name, v_backup.filter) INTO v_count;
            IF v_count > 0 THEN
                v_backupTableName := left(v_backup.table_name, 20) || '_bkp_' || to_char(now(), 'YYYYMMDD_HH24MISS_MS') || '_adpdel_' || p_AD_Process_ID::text;
                EXECUTE format('CREATE TABLE backup.%I AS SELECT * FROM %I WHERE %s', v_backupTableName, v_backup.table_name, v_backup.filter);
                GET DIAGNOSTICS v_count = ROW_COUNT;
                CREATE TABLE IF NOT EXISTS backup.backup_tables
                (
                    backup_table_name text PRIMARY KEY,
                    source_table_name text        NOT NULL,
                    created_at        timestamptz NOT NULL DEFAULT NOW(),
                    row_count         integer
                );
                INSERT INTO backup.backup_tables (backup_table_name, source_table_name, created_at, row_count)
                VALUES ('backup.' || v_backupTableName, v_backup.table_name, NOW(), v_count);
                RAISE NOTICE 'ad_process_delete: AD_Process_ID=%: backed up % row(s) of % to backup.%', p_AD_Process_ID, v_count, v_backup.table_name, v_backupTableName;
            END IF;
        END LOOP;

    --
    -- Apply the policy
    --
    FOR v_policy IN
        SELECT split_part(e, '.', 1)                         AS table_name,
               split_part(split_part(e, ':', 1), '.', 2)     AS column_name,
               split_part(e, ':', 2)                         AS action
        FROM UNNEST(c_policy) WITH ORDINALITY AS p(e, seq)
        WHERE split_part(e, ':', 2) IN ('DELETE', 'SET_NULL', 'REPOINT')
          AND EXISTS (SELECT 1
                      FROM information_schema.columns c
                      WHERE c.table_schema = 'public'
                        AND c.table_name = split_part(e, '.', 1)
                        AND c.column_name = split_part(split_part(e, ':', 1), '.', 2))
          AND EXISTS (SELECT 1 -- key column, used to log the IDs of the changed rows
                      FROM information_schema.columns c
                      WHERE c.table_schema = 'public'
                        AND c.table_name = split_part(e, '.', 1)
                        AND c.column_name = split_part(e, '.', 1) || '_id')
        ORDER BY seq
        LOOP
            IF v_policy.action = 'REPOINT' THEN
                v_sql := format('UPDATE %I SET %I = $1, Updated = now(), UpdatedBy = 99 WHERE %I = $2 RETURNING %I',
                                v_policy.table_name, v_policy.column_name, v_policy.column_name, v_policy.table_name || '_id');
            ELSIF v_policy.action = 'SET_NULL' THEN
                v_sql := format('UPDATE %I SET %I = NULL, Updated = now(), UpdatedBy = 99 WHERE %I = $2 RETURNING %I',
                                v_policy.table_name, v_policy.column_name, v_policy.column_name, v_policy.table_name || '_id');
            ELSE -- DELETE
                IF v_policy.table_name = 'ad_menu' THEN
                    DELETE
                    FROM ad_treenodemm n
                    WHERE n.node_id IN (SELECT m.ad_menu_id FROM ad_menu m WHERE m.ad_process_id = p_AD_Process_ID)
                      AND EXISTS (SELECT 1 FROM ad_tree t WHERE t.ad_tree_id = n.ad_tree_id AND t.ad_table_id = 116); -- 116 = AD_Menu
                    DELETE FROM ad_menu_trl WHERE ad_menu_id IN (SELECT m.ad_menu_id FROM ad_menu m WHERE m.ad_process_id = p_AD_Process_ID);
                ELSIF v_policy.table_name = 'ad_wf_node' THEN
                    DELETE FROM ad_wf_node_para WHERE ad_wf_node_id IN (SELECT n.ad_wf_node_id FROM ad_wf_node n WHERE n.ad_process_id = p_AD_Process_ID);
                END IF;

                v_sql := format('DELETE FROM %I WHERE %I = $2 RETURNING %I',
                                v_policy.table_name, v_policy.column_name, v_policy.table_name || '_id');
            END IF;

            -- $1 is unused by SET_NULL and DELETE
            EXECUTE format('WITH changed AS (%s) SELECT (SELECT count(*) FROM changed), (SELECT string_agg(id::text, '','' ORDER BY id) FROM (SELECT c.* FROM changed c ORDER BY 1 LIMIT 100) l(id))', v_sql)
                INTO v_count, v_ids USING c_ProcessWasDeleted_ID, p_AD_Process_ID;
            IF v_count > 0 THEN
                RAISE NOTICE 'ad_process_delete: AD_Process_ID=%: % % row(s) in %.%; % IDs (lowest 100): %',
                    p_AD_Process_ID, v_policy.action, v_count, v_policy.table_name, v_policy.column_name, v_policy.table_name, v_ids;
            END IF;
        END LOOP;

    -- FK of AD_SchedulerRecipient on AD_Scheduler does not cascade; the AD_Scheduler rows are removed by the cascade below
    DELETE FROM ad_schedulerrecipient WHERE ad_scheduler_id IN (SELECT s.ad_scheduler_id FROM ad_scheduler s WHERE s.ad_process_id = p_AD_Process_ID);
    -- role grants and statistics; no FK on AD_Process
    DELETE FROM ad_process_access WHERE ad_process_id = p_AD_Process_ID;
    IF to_regclass('public.ad_process_stats') IS NOT NULL THEN
        DELETE FROM ad_process_stats WHERE ad_process_id = p_AD_Process_ID;
    END IF;
    DELETE FROM ad_process_trl WHERE ad_process_id = p_AD_Process_ID;
    DELETE FROM ad_process WHERE ad_process_id = p_AD_Process_ID;
    RAISE NOTICE 'ad_process_delete: deleted AD_Process_ID=%', p_AD_Process_ID;
END;
$BODY$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION ad_process_delete(numeric) IS
    'Deletes the given AD_Process and deletes, nulls or re-points (to placeholder AD_Process 585692 "Process was deleted") every row that references it via a foreign key. Refuses, without changing anything, if a reference is not covered by its policy or the process is the placeholder itself. Does nothing if the process does not exist.'
;
