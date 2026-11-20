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

-- Replaces the Elasticsearch-based M_Product full-text search: install the M_Product FTS trigger and backfill M_Product_FTS
-- for all pre-existing products (the function is a no-op unless the PostgresFTS filter sysconfig is 'Y', see the C_BPartner enable script).
-- Deliberately M_Product-specific: the C_BPartner FTS is not touched.
SELECT ops.update_m_product_fts_if_active()
;
