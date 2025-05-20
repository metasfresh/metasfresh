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

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Format_Invoice_Description_For_QR_Code(p_input TEXT)
    RETURNS TEXT
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_cleaned TEXT;
BEGIN
    -- Replace CR and LF with space
    v_cleaned := REGEXP_REPLACE(p_input, '[\r\n]+', ' ', 'g');

    -- Remove all non-alphanumeric or dot characters (excluding spaces)
    v_cleaned := REGEXP_REPLACE(v_cleaned, '[^A-Za-z0-9\. ]+', '', 'g');

    -- Collapse multiple spaces into a single space
    v_cleaned := REGEXP_REPLACE(v_cleaned, '\s+', ' ', 'g');

    -- Trim leading and trailing spaces
    v_cleaned := TRIM(v_cleaned);

    -- Truncate to max 140 characters
    v_cleaned := LEFT(v_cleaned, 140);

    RETURN v_cleaned;
END;
$$;
