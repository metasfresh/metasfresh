-- Switch all T_ temporary tables to UNLOGGED to avoid WAL overhead.
-- These tables hold transient data (view selections, query selections, report spools, etc.)
-- that does not need to survive a crash and can safely be recreated.
-- Note: t_alter_column is excluded because it is a DDL utility used by migration scripts.

DO $$
DECLARE
    v_table TEXT;
    v_tables TEXT[] := ARRAY[
        't_aging',
        't_boilerplate_spool',
        't_bomline',
        't_distributionrundetail',
        't_es_fts_search_result',
        't_inventoryvalue',
        't_invoicegl',
        't_letter_spool',
        't_lock',
        't_m_storage_candidate',
        't_md_stock_warehouseandproduct',
        't_mrp_crp',
        't_query_selection',
        't_query_selection_pagination',
        't_query_selection_todelete',
        't_replenish',
        't_report',
        't_reportstatement',
        't_selection',
        't_selection2',
        't_spool',
        't_trialbalance',
        't_webui_viewselection',
        't_webui_viewselection_todelete',
        't_webui_viewselectionline'
    ];
BEGIN
    FOREACH v_table IN ARRAY v_tables
    LOOP
        IF EXISTS (SELECT FROM pg_class c
                   JOIN pg_namespace n ON c.relnamespace = n.oid
                   WHERE n.nspname = 'public'
                     AND c.relname = v_table
                     AND c.relkind = 'r'
                     AND c.relpersistence = 'p')
        THEN
            EXECUTE format('ALTER TABLE %I SET UNLOGGED', v_table);
            RAISE NOTICE 'Set table % to UNLOGGED', v_table;
        ELSE
            RAISE NOTICE 'Skipping % (not found or already unlogged)', v_table;
        END IF;
    END LOOP;
END $$;
