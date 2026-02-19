/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


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
