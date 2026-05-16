CREATE OR REPLACE FUNCTION s_externalreference_fts_trigger_function()
    RETURNS trigger
AS
$$
DECLARE
    v_c_bpartner_table_id CONSTANT numeric := get_table_id('C_BPartner');
BEGIN
    IF (TG_OP = 'UPDATE') THEN
        IF NEW.record_id IS DISTINCT FROM OLD.record_id THEN
            IF OLD.Type = 'BPartner' AND OLD.referenced_ad_table_id = v_c_bpartner_table_id THEN
                PERFORM ops.reindex_c_bpartner_fts(OLD.record_id);
            END IF;
            IF NEW.Type = 'BPartner' AND NEW.referenced_ad_table_id = v_c_bpartner_table_id THEN
                PERFORM ops.reindex_c_bpartner_fts(NEW.record_id);
            END IF;
        ELSE
            IF NEW.Type = 'BPartner' AND NEW.referenced_ad_table_id = v_c_bpartner_table_id THEN
                PERFORM ops.reindex_c_bpartner_fts(NEW.record_id);
            END IF;
        END IF;
    ELSIF (TG_OP = 'DELETE') THEN
        IF OLD.Type = 'BPartner' AND OLD.referenced_ad_table_id = v_c_bpartner_table_id THEN
            PERFORM ops.reindex_c_bpartner_fts(OLD.record_id);
        END IF;
    ELSIF (TG_OP = 'INSERT') THEN
        IF NEW.Type = 'BPartner' AND NEW.referenced_ad_table_id = v_c_bpartner_table_id THEN
            PERFORM ops.reindex_c_bpartner_fts(NEW.record_id);
        END IF;
    END IF;
    RETURN NULL;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION s_externalreference_fts_trigger_function() IS 'Refresh the C_BPartner_FTS table when a S_ExternalReference record is inserted or updated'
;


CREATE OR REPLACE FUNCTION c_invoice_fts_trigger_function()
    RETURNS trigger
AS
$$
BEGIN
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        PERFORM ops.reindex_c_invoice_fts(NEW.c_invoice_id);
    END IF;
    -- The DELETE case is handled automatically by the "ON DELETE CASCADE" constraint.
    -- CONSTRAINT CInvoice_CInvoiceFTS FOREIGN KEY (C_Invoice_ID) REFERENCES public.C_Invoice ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED
    RETURN NULL;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION c_invoice_fts_trigger_function() IS 'Refresh the C_Invoice_FTS table when a C_Invoice record is inserted or updated'
;


CREATE OR REPLACE FUNCTION m_product_fts_trigger_function()
    RETURNS trigger
AS
$$
BEGIN
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        PERFORM ops.reindex_m_product_fts(NEW.m_product_id);
    END IF;
    -- The DELETE case is handled automatically by the "ON DELETE CASCADE" constraint.
    -- CONSTRAINT MProduct_MProductFTS FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED
    RETURN NULL;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION m_product_fts_trigger_function() IS 'Refresh the M_Product_FTS table when a M_Product record is inserted or updated'
;
