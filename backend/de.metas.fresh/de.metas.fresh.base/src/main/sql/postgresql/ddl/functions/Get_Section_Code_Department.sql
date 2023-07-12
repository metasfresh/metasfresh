/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

DROP FUNCTION IF EXISTS get_section_code_department(IN p_m_sectioncode_id numeric,
                                                    IN p_date             date)
;

CREATE OR REPLACE FUNCTION get_section_code_department(IN p_m_sectioncode_id numeric,
                                                       IN p_date             date)
    RETURNS numeric(10)
    IMMUTABLE
    LANGUAGE sql
AS
$$
SELECT m_department_id
FROM m_department_sectioncode
WHERE isactive = 'Y'
  AND m_sectioncode_id = p_m_sectioncode_id
  AND ((p_date >= validfrom::date)
    AND (validto IS NULL OR p_date < validto::date))
ORDER BY ValidFrom DESC, m_department_sectioncode_id DESC
LIMIT 1
$$
;
