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

-- Per-table sysconfig keys for the Postgres FTS filter: the converter now reads ".partner" for C_BPartner.
-- Rename the pre-existing un-suffixed rows (matched by name, so it works on every instance) and keep their values.
-- No row present => nothing to do (the converter then falls back to its defaults, as before).
UPDATE AD_SysConfig
SET Name      = 'de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.partner',
    Updated   = NOW(),
    UpdatedBy = 100
WHERE Name = 'de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance'
;

UPDATE AD_SysConfig
SET Name      = 'de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.partner',
    Updated   = NOW(),
    UpdatedBy = 100
WHERE Name = 'de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit'
;
