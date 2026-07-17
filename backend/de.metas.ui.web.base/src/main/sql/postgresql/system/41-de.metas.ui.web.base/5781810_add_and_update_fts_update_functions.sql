CREATE OR REPLACE FUNCTION ops.update_c_bpartner_fts_if_active()
    RETURNS void
AS
$$
BEGIN
    DROP TRIGGER IF EXISTS c_bpartner_fts_trigger ON c_bpartner;
    DROP TRIGGER IF EXISTS ad_user_fts_trigger ON ad_user;
    DROP TRIGGER IF EXISTS c_bpartner_location_fts_trigger ON c_bpartner_location;
    DROP TRIGGER IF EXISTS s_externalreference_fts_trigger ON s_externalreference;

    IF (get_sysconfig_value('de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled', 'N') <> 'Y') THEN
        RETURN;
    END IF;

    CREATE TRIGGER c_bpartner_fts_trigger
        AFTER INSERT OR UPDATE
        ON c_bpartner
        FOR EACH ROW
    EXECUTE PROCEDURE c_bpartner_fts_trigger_function();

    CREATE TRIGGER ad_user_fts_trigger
        AFTER INSERT OR UPDATE OR DELETE
        ON ad_user
        FOR EACH ROW
    EXECUTE PROCEDURE ad_user_fts_trigger_function();

    CREATE TRIGGER c_bpartner_location_fts_trigger
        AFTER INSERT OR UPDATE OR DELETE
        ON c_bpartner_location
        FOR EACH ROW
    EXECUTE PROCEDURE c_bpartner_location_fts_trigger_function();

    CREATE TRIGGER s_externalreference_fts_trigger
        AFTER INSERT OR UPDATE OR DELETE
        ON s_externalreference
        FOR EACH ROW
    EXECUTE PROCEDURE s_externalreference_fts_trigger_function();

    -- Reindex all (UPSERT handles existing records without ACCESS EXCLUSIVE lock)
    PERFORM ops.reindex_c_bpartner_fts();
    -- Clean up stale FTS entries whose source records no longer exist
    DELETE FROM C_BPartner_FTS WHERE NOT EXISTS (SELECT 1 FROM C_BPartner WHERE C_BPartner.C_BPartner_ID = C_BPartner_FTS.C_BPartner_ID);
    ANALYSE C_BPartner_FTS;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION ops.update_c_bpartner_fts_if_active() IS 'Rebuilds the entire FTS index for all C_BPartner records and enables the triggers. This is a maintenance operation and not intended for frequent use.'
;


CREATE OR REPLACE FUNCTION ops.update_c_invoice_fts_if_active()
    RETURNS void
AS
$$
BEGIN
    DROP TRIGGER IF EXISTS c_invoice_fts_trigger ON c_invoice;

    IF (get_sysconfig_value('de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled', 'N') <> 'Y') THEN
        RETURN;
    END IF;

    CREATE TRIGGER c_invoice_fts_trigger
        AFTER INSERT OR UPDATE
        ON c_invoice
        FOR EACH ROW
    EXECUTE PROCEDURE c_invoice_fts_trigger_function();

    -- Reindex all (UPSERT handles existing records without ACCESS EXCLUSIVE lock)
    PERFORM ops.reindex_c_invoice_fts();
    -- Clean up stale FTS entries whose source records no longer exist
    DELETE FROM C_Invoice_FTS WHERE NOT EXISTS (SELECT 1 FROM C_Invoice WHERE C_Invoice.C_Invoice_ID = C_Invoice_FTS.C_Invoice_ID);
    ANALYSE C_Invoice_FTS;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION ops.update_c_invoice_fts_if_active() IS 'Rebuilds the entire FTS index for all C_Invoice records and enables the triggers. This is a maintenance operation and not intended for frequent use.'
;


CREATE OR REPLACE FUNCTION ops.update_m_product_fts_if_active()
    RETURNS void
AS
$$
BEGIN
    DROP TRIGGER IF EXISTS m_product_fts_trigger ON m_product;

    IF (get_sysconfig_value('de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled', 'N') <> 'Y') THEN
        RETURN;
    END IF;

    CREATE TRIGGER m_product_fts_trigger
        AFTER INSERT OR UPDATE
        ON m_product
        FOR EACH ROW
    EXECUTE PROCEDURE m_product_fts_trigger_function();

    -- Reindex all (UPSERT handles existing records without ACCESS EXCLUSIVE lock)
    PERFORM ops.reindex_m_product_fts();
    -- Clean up stale FTS entries whose source records no longer exist
    DELETE FROM M_Product_FTS WHERE NOT EXISTS (SELECT 1 FROM M_Product WHERE M_Product.M_Product_ID = M_Product_FTS.M_Product_ID);
    ANALYSE M_Product_FTS;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION ops.update_m_product_fts_if_active() IS 'Rebuilds the entire FTS index for all m_product records and enables the triggers. This is a maintenance operation and not intended for frequent use.'
;


CREATE OR REPLACE FUNCTION ops.update_all_fts_if_active()
    RETURNS void
AS
$$
BEGIN
    -- Each entity reindex is independent; partial failure should not roll back the others.
    BEGIN
        PERFORM ops.update_c_bpartner_fts_if_active();
    EXCEPTION
        WHEN OTHERS THEN RAISE WARNING 'update_c_bpartner_fts_if_active failed: %', SQLERRM;
    END;

    BEGIN
        PERFORM ops.update_c_invoice_fts_if_active();
    EXCEPTION
        WHEN OTHERS THEN RAISE WARNING 'update_c_invoice_fts_if_active failed: %', SQLERRM;
    END;

    BEGIN
        PERFORM ops.update_m_product_fts_if_active();
    EXCEPTION
        WHEN OTHERS THEN RAISE WARNING 'update_m_product_fts_if_active failed: %', SQLERRM;
    END;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION ops.update_all_fts_if_active() IS 'Rebuilds the entire FTS index for all FTS records and enables the triggers. This is a maintenance operation and not intended for frequent use.'
;

-- SysConfig Name: de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled
-- SysConfig Value: Y
-- 2025-12-17T17:00:05.573Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Postgres FTS feature activation. ops.update_all_if_active() needs to be run after activation!',Updated=TO_TIMESTAMP('2025-12-17 17:00:05.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541776
;
