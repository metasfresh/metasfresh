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

CREATE OR REPLACE FUNCTION getLocalTaxReportingConversionRateDate(p_taxReportRateBase varchar, p_c_calendar_id numeric, p_date timestamp with time zone)
    RETURNS timestamp with time zone
    LANGUAGE plpgsql
AS
$$

DECLARE

    dateResult timestamp with time zone := trunc(p_date, 'DD');
    count numeric;
    attempt int := 1;

BEGIN
    IF (p_taxReportRateBase IS NULL OR TRIM(p_taxReportRateBase) = '') THEN
        RAISE EXCEPTION 'taxReportRateBase is not set';
    END IF;

    IF (p_c_calendar_id <= 0) THEN
        RAISE EXCEPTION 'c_calendar_id is not set';
    END IF;

    IF (p_date IS NULL) THEN
        RAISE EXCEPTION 'date is not set';
    END IF;

    -- BI - 1 day before invoice date
    -- I  - invoice date
    -- S  - goods issue / shipment date
    IF (p_taxReportRateBase = 'BI') THEN
        dateResult = dateResult - 1;
    END IF;

    WHILE (attempt < 10) LOOP
            SELECT count(*)
            FROM c_nonbusinessday
            WHERE c_calendar_id = p_c_calendar_id
              AND isactive = 'Y'
              AND trunc(date1, 'DD') = dateResult
            INTO count;

            IF (count = 0) THEN
                RETURN dateResult;
            END IF;

            dateResult = dateResult - 1;
            attempt := attempt + 1;
        END LOOP;

    RAISE EXCEPTION 'No business day found after going back % days', attempt;

EXCEPTION WHEN OTHERS THEN
    RAISE NOTICE '%', SQLERRM;
    RETURN NULL;
END;

$$
;

COMMENT ON FUNCTION getLocalTaxReportingConversionRateDate(varchar, numeric, timestamp with time zone) IS
    'Find last business day in given calendar starting from given date or 1 day back if p_taxReportRateBase is BI ( 1 day before invoice date )'
;

ALTER FUNCTION getLocalTaxReportingConversionRateDate(varchar, numeric, timestamp with time zone) OWNER TO metasfresh
;

