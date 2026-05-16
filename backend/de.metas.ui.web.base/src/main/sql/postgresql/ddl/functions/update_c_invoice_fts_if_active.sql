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
