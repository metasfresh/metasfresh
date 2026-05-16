-- Enable PostgreSQL FTS and populate FTS tables,
-- but only on systems that previously had Elasticsearch FTS enabled.
-- Systems without ES FTS get PG FTS enabled too (it replaces ES FTS),
-- but the heavy full-reindex is skipped if there was nothing to migrate.
DO
$$
    BEGIN
        -- Check if Elasticsearch FTS was previously active on this system
        IF EXISTS(
            SELECT 1
            FROM AD_SysConfig
            WHERE Name = 'de.metas.elasticsearch.enabled'
              AND Value = 'Y'
        )
        THEN
            -- System had ES FTS active → enable PostgreSQL FTS and do full reindex
            RAISE NOTICE 'Elasticsearch FTS was enabled — enabling PostgreSQL FTS and performing full reindex...';

            UPDATE AD_SysConfig
            SET Value     = 'Y',
                Updated   = NOW(),
                UpdatedBy = 100
            WHERE Name = 'de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled';

            -- Create triggers and populate FTS tables (BPartner, Invoice, Product)
            PERFORM ops.update_all_fts_if_active();
        ELSE
            RAISE NOTICE 'Elasticsearch FTS was NOT enabled — skipping PostgreSQL FTS activation.';
        END IF;
    END
$$
;
