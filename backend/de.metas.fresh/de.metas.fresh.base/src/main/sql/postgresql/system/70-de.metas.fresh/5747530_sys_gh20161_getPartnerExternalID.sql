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

DROP FUNCTION IF EXISTS report.getPartnerExternalID(numeric)
;

CREATE FUNCTION report.getPartnerExternalID(p_record_id numeric)
    RETURNS character varying
    STABLE
    LANGUAGE sql
AS
$$
SELECT exr.ExternalReference AS External_ID
FROM S_ExternalReference exr
WHERE exr.Type = 'BPartner'
  AND exr.ExternalSystem = 'Other'
  AND exr.referenced_ad_table_id = 291
  AND exr.record_id = p_record_id
$$
;

