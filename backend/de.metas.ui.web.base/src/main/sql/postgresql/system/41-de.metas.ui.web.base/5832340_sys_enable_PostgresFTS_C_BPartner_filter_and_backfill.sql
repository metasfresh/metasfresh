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

-- Replaces the Elasticsearch-based C_BPartner full-text search: enable the Postgres FTS document filter...
UPDATE AD_SysConfig
SET Value='Y', Updated=NOW(), UpdatedBy=99
WHERE Name = 'de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled'
;

-- ...then (re)install the C_BPartner/AD_User/C_BPartner_Location FTS triggers and backfill C_BPartner_FTS for all pre-existing BPartners
-- (the function is a no-op unless the sysconfig above is 'Y').
SELECT ops.update_c_bpartner_fts_if_active()
;
