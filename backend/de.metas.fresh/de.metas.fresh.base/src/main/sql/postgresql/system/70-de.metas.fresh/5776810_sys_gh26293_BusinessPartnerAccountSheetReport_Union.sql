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

DROP FUNCTION IF EXISTS BusinessPartnerAccountSheetReport_Union(p_c_bpartner_id numeric,
                                                                p_dateFrom      date,
                                                                p_dateTo        date,
                                                                p_ad_client_id  numeric,
                                                                p_ad_org_id     numeric,
                                                                p_isSoTrx       TEXT,
                                                                p_ad_language   text)
;


CREATE OR REPLACE FUNCTION BusinessPartnerAccountSheetReport_Union(p_c_bpartner_id numeric,
                                                                   p_dateFrom      date,
                                                                   p_dateTo        date,
                                                                   p_ad_client_id  numeric,
                                                                   p_ad_org_id     numeric,
                                                                   p_isSoTrx       TEXT,
                                                                   p_ad_language   text)
    RETURNS table
            (
                dateAcct         DATE,
                DocumentType     TEXT,
                documentno       TEXT,
                beginningBalance NUMERIC,
                amount           NUMERIC,
                endingBalance    NUMERIC,
                currencyCode     TEXT,
                description      TEXT,
                created          TIMESTAMP
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF p_issotrx = 'Y' OR p_issotrx = 'N' THEN
        RETURN QUERY
            SELECT *
            FROM BusinessPartnerAccountSheetReport(p_c_bpartner_id,
                                                   p_dateFrom,
                                                   p_dateTo,
                                                   p_ad_client_id,
                                                   p_ad_org_id,
                                                   p_isSoTrx,
                                                   p_ad_language);

    ELSIF p_issotrx IS NULL THEN
        RETURN QUERY
            SELECT *
            FROM BusinessPartnerAccountSheetReport(p_c_bpartner_id,
                                                   p_dateFrom,
                                                   p_dateTo,
                                                   p_ad_client_id,
                                                   p_ad_org_id,
                                                   'Y',
                                                   p_ad_language)
            UNION ALL
            SELECT *
            FROM BusinessPartnerAccountSheetReport(p_c_bpartner_id,
                                                   p_dateFrom,
                                                   p_dateTo,
                                                   p_ad_client_id,
                                                   p_ad_org_id,
                                                   'N',
                                                   p_ad_language);
    END IF;
END;
$$
;
