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


CREATE TABLE fix.ad_window_trl_duplicated_names AS
SELECT base.ad_window_id  AS window1_ad_window_id,
       base.name          AS window1_name,
       trl.ad_language    AS window1_trl_ad_language,
       trl.name           AS window1_trl_name,
       base2.ad_window_id AS window2_ad_window_id,
       base2.name         AS window2_name,
       trl2.ad_language   AS window2_trl_ad_language,
       trl2.name          AS window2_trl_name
FROM ad_window base
         JOIN ad_window_trl trl ON base.ad_window_id = trl.ad_window_id
         JOIN ad_window_trl trl2 ON trl.ad_language = trl2.ad_language AND trl.name = trl2.name AND trl.ad_window_id > trl2.ad_window_id
         JOIN ad_window base2 ON base2.ad_window_id = trl2.ad_window_id;

CREATE TABLE fix.ad_window_trl_duplicated_names_fix AS
SELECT DISTINCT t.ad_window_id, t.ad_language, t.name, w.name AS name_fix
FROM ad_window_trl t
         JOIN fix.ad_window_trl_duplicated_names f ON (f.window1_ad_window_id = t.ad_window_id AND f.window1_trl_ad_language = t.ad_language
    OR f.window2_ad_window_id = t.ad_window_id AND f.window2_trl_ad_language = t.ad_language)
         JOIN ad_window w ON w.ad_window_id = t.ad_window_id;

select backup_table('ad_window_trl');
UPDATE ad_window_trl t
SET name=d.name_fix, updatedby=100, updated='2025-10-11 11:14:00'
FROM fix.ad_window_trl_duplicated_names_fix d
WHERE t.ad_window_id = d.ad_window_id
  AND t.ad_language = d.ad_language;
