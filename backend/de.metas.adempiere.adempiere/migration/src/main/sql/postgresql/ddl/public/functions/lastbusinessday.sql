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

DROP FUNCTION IF EXISTS lastbusinessday(timestamp with time zone, numeric);

CREATE FUNCTION lastbusinessday(p_date timestamp with time zone, p_c_calendar_id numeric) RETURNS timestamp with time zone
    LANGUAGE plpgsql
AS
$$

DECLARE
    v_nextDate	date := trunc(p_Date);
    v_offset	numeric	:= 0;
    v_Saturday	numeric	:= TO_CHAR(TO_DATE('2000-01-01', 'YYYY-MM-DD'), 'D');
    v_Sunday	numeric	:= (case when v_Saturday = 7 then 1 else v_Saturday + 1 end);
    v_isHoliday	boolean	:= true;
    nbd C_NonBusinessDay%ROWTYPE;
begin
    v_isHoliday := true;
    loop
        SELECT	CASE TO_CHAR(v_nextDate,'D')::numeric
                      WHEN v_Saturday THEN -1
                      WHEN v_Sunday THEN -2
                                      ELSE 0
                  END INTO v_offset;
        v_nextDate := v_nextDate + v_offset::integer;
        v_isHoliday := false;
        FOR nbd IN	SELECT *
                      FROM C_NonBusinessDay
                      WHERE c_calendar_id=p_c_calendar_id and IsActive ='Y' and Date1 <= v_nextDate
                      ORDER BY Date1 DESC
            LOOP
                exit when v_nextDate <> trunc(nbd.Date1);
                v_nextDate := v_nextDate - 1;
                v_isHoliday := true;
            end loop;
        exit when v_isHoliday=false;
    end loop;
    --
    return v_nextDate::timestamp with time zone;
end;

$$
;

COMMENT ON FUNCTION lastbusinessday(timestamp with time zone, numeric) IS
    'Find last business day in given calendar including given date'
;

