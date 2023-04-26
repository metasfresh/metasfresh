/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

DROP FUNCTION IF EXISTS getCreditLimitForSectionGroupPartner (p_section_group_partner_id numeric,
                                                              p_Date                     date)
;

CREATE FUNCTION getCreditLimitForSectionGroupPartner(p_section_group_partner_id numeric,
                                                     p_Date                     date) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(SUM(lim.amount), 0)

FROM C_BPartner sectionGroupPartner
         JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id
         JOIN c_bpartner_stats stats ON sectionPartner.c_bpartner_id = stats.c_bpartner_id
         JOIN C_BPartner_CreditLimit lim ON sectionPartner.c_bpartner_id = lim.c_bpartner_id


WHERE sectionGroupPartner.c_bpartner_id = p_section_group_partner_id

  AND ((lim.processed = 'Y' AND lim.isactive = 'Y') OR (lim.processed = 'N' AND lim.isactive = 'N'))
  AND lim.datefrom <= p_date
  AND NOT EXISTS(SELECT 1
                 FROM c_bpartner_creditlimit lim2
                 WHERE lim.c_bpartner_id = lim2.c_bpartner_id
                   AND lim2.datefrom >= lim.datefrom
                   AND lim2.datefrom <= p_Date
                   AND ((lim2.processed = 'Y' AND lim2.isactive = 'Y') OR (lim2.processed = 'N' AND lim2.isactive = 'N'))
                   AND lim2.c_bpartner_creditlimit_id != lim.c_bpartner_creditlimit_id)

GROUP BY sectionPartner.section_group_partner_id

$$
;

COMMENT ON FUNCTION getCreditLimitForSectionGroupPartner (numeric, date) IS 'The credit limit always comes in base currency. TEST: SELECT getCreditLimitForSectionGroupPartner(2156017, now()::date);'
;
