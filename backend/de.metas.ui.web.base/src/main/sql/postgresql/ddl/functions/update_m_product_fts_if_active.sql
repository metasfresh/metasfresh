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
