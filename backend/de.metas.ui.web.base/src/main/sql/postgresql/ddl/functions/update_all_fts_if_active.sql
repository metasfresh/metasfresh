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
