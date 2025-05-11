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

DROP FUNCTION IF EXISTS report.TextSnippet(IN p_ad_boilerplate_id numeric,
                                           IN p_ad_language       character varying)
;

CREATE OR REPLACE FUNCTION report.TextSnippet(IN p_ad_boilerplate_id numeric,
                                              IN p_ad_language       character varying DEFAULT 'de_DE')
    RETURNS text
AS
$BODY$
DECLARE

    p_textsnippet_name text;

BEGIN
    SELECT COALESCE(abp_trl.textsnippet, abp.textsnippet)
    INTO p_textsnippet_name
    FROM ad_boilerplate abp
             LEFT JOIN ad_boilerplate_trl abp_trl
                       ON abp_trl.ad_boilerplate_id = abp.ad_boilerplate_id AND abp_trl.ad_language = p_AD_Language
    WHERE abp.ad_boilerplate_id = p_ad_boilerplate_id
      AND abp.IsActive = 'Y';

    RETURN p_textsnippet_name;
END;
$BODY$
    LANGUAGE plpgsql
;


