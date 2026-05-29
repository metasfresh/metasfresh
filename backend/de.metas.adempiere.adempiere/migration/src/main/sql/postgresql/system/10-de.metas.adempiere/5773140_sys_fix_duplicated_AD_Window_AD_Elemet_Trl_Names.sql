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


--drop table fix.ad_element_trl_for_windows_20251011
CREATE TABLE fix.ad_element_trl_for_windows_20251011 AS
SELECT w1.ad_window_id                                                                         AS window1_ad_window_id,
       w1.name                                                                                 AS window1_name,
       e_trl1.ad_language                                                                      AS element_trl_ad_language,
       e_trl1.ad_element_id                                                                    AS element1_trl_ad_element_id,
       e_trl1.name                                                                             AS element1_trl_name,
       w2.ad_window_id                                                                         AS window2_ad_window_id,
       w2.name                                                                                 AS window2_name,
       e_trl2.ad_element_id                                                                    AS element2_trl_ad_element_id,
       e_trl2.name                                                                             AS element2_trl_name,
       -- note that we prefer updating the ad_element with the lower ID. That's because we assume that the one with the lower ID is usually the "_OLD" one that is not used on the respective instance.
       CASE WHEN w2.name = e_trl2.name THEN e_trl1.ad_element_id ELSE e_trl2.ad_element_id END AS ad_element_to_update_id,
       CASE WHEN w2.name = e_trl2.name THEN w1.name ELSE w2.name END                           AS ad_element_new_name
FROM ad_window w1
         JOIN ad_element_trl e_trl1 ON w1.ad_element_id = e_trl1.ad_element_id
         JOIN ad_element_trl e_trl2 ON e_trl1.ad_language = e_trl2.ad_language AND e_trl1.name = e_trl2.name AND e_trl1.ad_element_id > e_trl2.ad_element_id
         JOIN ad_window w2 ON w2.ad_element_id = e_trl2.ad_element_id
;

SELECT backup_table('ad_element_trl')
;

UPDATE ad_element_trl et
SET name=d.ad_element_new_name
FROM fix.ad_element_trl_for_windows_20251011 d
WHERE d.ad_element_to_update_id = et.ad_element_id
  AND d.element_trl_ad_language = et.ad_language
;
