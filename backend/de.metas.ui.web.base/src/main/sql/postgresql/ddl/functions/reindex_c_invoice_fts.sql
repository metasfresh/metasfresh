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

    -- TODO
WITH InvoiceText AS (SELECT i.c_invoice_id,
                            (
                                i.poreference || ' ' || i.externalid || ' ' || i.documentno
                                ) AS aggregated_text
                     FROM C_Invoice i
                     WHERE (c_invoice_id IS NULL OR i.c_invoice_id = p_c_invoice_id))

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
