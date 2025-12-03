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

CREATE OR REPLACE FUNCTION ops.reindex_all_c_bpartner_fts()
    RETURNS void AS $$
BEGIN
    TRUNCATE TABLE C_BPartner_FTS;
    PERFORM ops.reindex_c_bpartner_fts();
END;
$$ LANGUAGE plpgsql
;

COMMENT ON FUNCTION ops.reindex_all_c_bpartner_fts() IS 'Rebuilds the entire FTS index for all C_BPartner records. This is a maintenance operation and not intended for frequent use.'
;

