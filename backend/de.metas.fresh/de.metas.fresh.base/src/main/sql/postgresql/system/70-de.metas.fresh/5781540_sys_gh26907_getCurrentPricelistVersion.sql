/*
 * #%L
 * de.metas.fresh.base
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

DROP FUNCTION IF EXISTS getCurrentPricelistVersion(numeric,
                                                   timestamp with time zone)
;

CREATE OR REPLACE FUNCTION getCurrentPricelistVersion(IN p_PriceList_ID numeric,
                                                      p_Date            timestamp with time zone)
    RETURNS numeric
    LANGUAGE sql
AS
$$
SELECT plv.m_pricelist_version_id
FROM m_pricelist_version plv
WHERE plv.isActive = 'Y'
  AND plv.validfrom <= p_Date
  AND plv.m_pricelist_id = p_PriceList_ID
ORDER BY plv.validfrom DESC
LIMIT 1
    ;
$$
;
