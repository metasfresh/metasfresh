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

    TRUNCATE TABLE C_BPartner_FTS;
    PERFORM ops.reindex_c_bpartner_fts();
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

    TRUNCATE TABLE C_Invoice_FTS;
    PERFORM ops.reindex_c_invoice_fts();
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

    TRUNCATE TABLE M_Product_FTS;
    PERFORM ops.reindex_m_product_fts();
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
    PERFORM ops.update_c_bpartner_fts_if_active();
    PERFORM ops.update_c_invoice_fts_if_active();
    PERFORM ops.update_m_product_fts_if_active();
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION ops.update_all_fts_if_active() IS 'Rebuilds the entire FTS index for all FTS records and enables the triggers. This is a maintenance operation and not intended for frequent use.'
;

