/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

WITH current_max AS (
    SELECT COALESCE(MAX(seqno), -10) AS max_seqno
    FROM c_workplace
    WHERE seqno IS NOT NULL
),
     numbered_rows AS (
         SELECT
             c_workplace_id,
             (SELECT (FLOOR(cm.max_seqno / 10.0) + 1) * 10 FROM current_max cm)
                 + (ROW_NUMBER() OVER (ORDER BY c_workplace_id ASC) - 1) * 10 AS new_seqno
         FROM c_workplace
         WHERE seqno IS NULL
     )
UPDATE c_workplace
SET seqno = numbered_rows.new_seqno,
    updated = '2025-12-09 12:00:00.000000',
    updatedby = 99
FROM numbered_rows
WHERE c_workplace.c_workplace_id = numbered_rows.c_workplace_id
;
