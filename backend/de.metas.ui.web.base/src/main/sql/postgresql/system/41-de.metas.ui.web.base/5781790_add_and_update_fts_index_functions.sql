CREATE OR REPLACE FUNCTION ops.reindex_c_bpartner_fts(p_c_bpartner_id NUMERIC DEFAULT NULL)
    RETURNS void
AS
$$
WITH UserText AS (
    -- Pre-aggregate user data
    SELECT u.c_bpartner_id,
           STRING_AGG(u.name || ' ' || COALESCE(u.firstname, '') || ' ' || COALESCE(u.lastname, '') || ' ' ||
                      COALESCE(u.email, '') || ' ' || COALESCE(u.phone, '') || ' ' || COALESCE(u.mobilephone, ''), ' ') AS text
    FROM ad_user u
    WHERE p_c_bpartner_id IS NULL
       OR u.c_bpartner_id = p_c_bpartner_id
    GROUP BY u.c_bpartner_id),
     LocationText AS (
         -- Pre-aggregate location data
         SELECT bpl.c_bpartner_id,
                STRING_AGG(bpl.name || ' ' || COALESCE(bpl.phone, '') || ' ' || COALESCE(bpl.email, '') || ' ' ||
                           COALESCE(l.address1, '') || COALESCE(l.city, '') || ' ' || COALESCE(l.postal, '') || ' ' || c.name, ' ') AS text
         FROM c_bpartner_location bpl
                  JOIN c_location l ON bpl.c_location_id = l.c_location_id
                  JOIN c_country c ON l.c_country_id = c.c_country_id
         WHERE p_c_bpartner_id IS NULL
            OR bpl.c_bpartner_id = p_c_bpartner_id
         GROUP BY bpl.c_bpartner_id),
     ExternalReferenceText AS (
         SELECT er.record_id AS c_bpartner_id,
                STRING_AGG(er.externalreference, ' ') AS text
         FROM S_ExternalReference er
         WHERE Type = 'BPartner'
           AND referenced_ad_table_id = get_table_id('C_BPartner')
           AND (p_c_bpartner_id IS NULL OR er.record_id = p_c_bpartner_id)
         GROUP BY er.record_id),
     BPartnerText AS (SELECT bp.c_bpartner_id,
                             (
                                 bp.name || ' ' || COALESCE(bp.name2, '') || ' ' || bp.value || ' ' ||
                                 COALESCE(bp.firstname, '') || ' ' || COALESCE(bp.lastname, '') || ' ' ||
                                 COALESCE(bp.debtorid::TEXT, '') || ' ' || COALESCE(bp.creditorid::TEXT, '') || ' ' ||
                                 COALESCE(ut.text, '') || ' ' ||
                                 COALESCE(lt.text, '') || ' ' ||
                                 COALESCE(er.text, '')
                                 ) AS aggregated_text
                      FROM c_bpartner bp
                               LEFT JOIN UserText ut ON bp.c_bpartner_id = ut.c_bpartner_id
                               LEFT JOIN LocationText lt ON bp.c_bpartner_id = lt.c_bpartner_id
                               LEFT JOIN ExternalReferenceText er ON bp.c_bpartner_id = er.c_bpartner_id
                      WHERE (p_c_bpartner_id IS NULL OR bp.c_bpartner_id = p_c_bpartner_id))
-- Perform an "UPSERT" into the FTS table.
INSERT
INTO C_BPartner_FTS (c_bpartner_id, fts_string, fts_document, updated)
SELECT BPartnerText.c_bpartner_id,
       BPartnerText.aggregated_text,
       TO_TSVECTOR(get_fts_config(), BPartnerText.aggregated_text),
       NOW()
FROM BPartnerText
ON CONFLICT (c_bpartner_id) DO UPDATE
    SET fts_document = EXCLUDED.fts_document,
        fts_string   = EXCLUDED.fts_string,
        updated      = NOW();
$$
    LANGUAGE sql
;


COMMENT ON FUNCTION ops.reindex_c_bpartner_fts(NUMERIC) IS 'Rebuilds the FTS index for all C_BPartner records if no ID is provided or updates the index for a single C_BPartner if an ID is provided.'
;



CREATE OR REPLACE FUNCTION ops.reindex_c_invoice_fts(p_c_invoice_id NUMERIC DEFAULT NULL)
    RETURNS void
AS
$$
WITH InvoiceText AS (
    SELECT i.c_invoice_id,
           (
               COALESCE(i.poreference, '') || ' ' ||
               COALESCE(i.externalid, '') || ' ' ||
               COALESCE(i.documentno, '') || ' ' ||
               COALESCE(i.dateinvoiced::TEXT, '') || ' ' ||
               COALESCE(i.description, '') || ' ' ||
               COALESCE(i.descriptionbottom, '') || ' ' ||
               COALESCE(bp.name, '') || ' ' ||
               COALESCE(bp.name2, '') || ' ' ||
               COALESCE(bp.value, '') || ' ' ||
               COALESCE(bp.firstname, '') || ' ' ||
               COALESCE(bp.lastname, '') || ' ' ||
               COALESCE(bp.debtorid::TEXT, '') || ' ' ||
               COALESCE(bp.creditorid::TEXT, '') || ' ' ||
               COALESCE(l.address1, '') || ' ' ||
               COALESCE(l.city, '') || ' ' ||
               COALESCE(l.postal, '') || ' ' ||
               COALESCE(c.name, '') || ' ' ||
               COALESCE(u.name, '') || ' ' ||
               COALESCE(u.firstname, '') || ' ' ||
               COALESCE(u.lastname, '') || ' ' ||
               COALESCE(dt.name, '') || ' ' ||
               COALESCE(wh.name, '') || ' ' ||
               COALESCE((
                            SELECT STRING_AGG(ExternalReference, ' ')
                            FROM S_ExternalReference
                            WHERE Type = 'BPartner'
                              AND referenced_ad_table_id = get_table_id('C_BPartner')
                              AND record_id = bp.C_BPartner_ID
                        ), '')
               ) AS aggregated_text
    FROM C_Invoice i -- Keep joins strictly 1:1 so the CTE yields one row per c_invoice_id!
             JOIN C_BPartner bp ON i.c_bpartner_id = bp.c_bpartner_id
             LEFT JOIN c_location l ON i.c_bpartner_location_value_id = l.c_location_id
             LEFT JOIN c_country c ON l.c_country_id = c.c_country_id
             LEFT JOIN AD_User u ON i.ad_user_id = u.ad_user_id
             LEFT JOIN c_doctype dt ON i.c_doctypetarget_id = dt.c_doctype_id
             LEFT JOIN m_warehouse wh ON i.m_warehouse_id = wh.m_warehouse_id
             JOIN ad_org o ON i.ad_org_id = o.ad_org_id
    WHERE (p_c_invoice_id IS NULL OR i.c_invoice_id = p_c_invoice_id)
)

-- Perform an "UPSERT" into the FTS table.
INSERT
INTO C_Invoice_FTS (c_invoice_id, fts_string, fts_document, updated)
SELECT InvoiceText.c_invoice_id,
       InvoiceText.aggregated_text,
       TO_TSVECTOR(get_fts_config(), InvoiceText.aggregated_text),
       NOW()
FROM InvoiceText
ON CONFLICT (c_invoice_id) DO UPDATE
    SET fts_document = EXCLUDED.fts_document,
        fts_string   = EXCLUDED.fts_string,
        updated      = NOW();
$$
    LANGUAGE sql
;

COMMENT ON FUNCTION ops.reindex_c_invoice_fts(NUMERIC) IS 'Rebuilds the FTS index for all C_Invoice records if no ID is provided or updates the index for a single C_Invoice if an ID is provided.'
;


CREATE OR REPLACE FUNCTION ops.reindex_m_product_fts(p_product_id NUMERIC DEFAULT NULL)
    RETURNS void
AS
$$
WITH ProductText AS (SELECT p.m_product_id,
                            (
                                p.name || ' ' || p.value
                                ) AS aggregated_text
                     FROM M_Product p
                     WHERE (p_product_id IS NULL OR p.m_product_id = p_product_id))
-- Perform an "UPSERT" into the FTS table.
INSERT
INTO M_Product_FTS (m_product_id, fts_string, fts_document, updated)
SELECT ProductText.m_product_id,
       ProductText.aggregated_text,
       TO_TSVECTOR(get_fts_config(), ProductText.aggregated_text),
       NOW()
FROM ProductText
ON CONFLICT (m_product_id) DO UPDATE
    SET fts_document = EXCLUDED.fts_document,
        fts_string   = EXCLUDED.fts_string,
        updated      = NOW();
$$
    LANGUAGE sql
;


COMMENT ON FUNCTION ops.reindex_m_product_fts(NUMERIC) IS 'Rebuilds the FTS index for all M_Product records if no ID is provided or updates the index for a single M_Product if an ID is provided.'
;
