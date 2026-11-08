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
