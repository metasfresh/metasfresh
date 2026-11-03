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

DROP FUNCTION IF EXISTS extract_street_and_number (p_full_address TEXT)
;

CREATE OR REPLACE FUNCTION extract_street_and_number(p_full_address TEXT)
    RETURNS TABLE (street TEXT, number TEXT)
    LANGUAGE plpgsql
AS $$
DECLARE
    v_extracted_number TEXT;
    v_extracted_street TEXT;
BEGIN
    -- Take the trailing street-number token from the END of the string.
    -- Supports: 12, 12A, 12/2, 12-B, 12-3, etc.
    v_extracted_number := substring(p_full_address FROM '(\d+[A-Za-z]?(?:[/-]\w+)*)\s*$');

    IF v_extracted_number IS NOT NULL THEN
        -- Remove that trailing token from the address to get the street
        v_extracted_street := btrim(
                regexp_replace(p_full_address, '\s*\d+[A-Za-z]?(?:[/-]\w+)*\s*$', '')
                              );
    ELSE
        v_extracted_street := btrim(p_full_address);
    END IF;

    RETURN QUERY SELECT v_extracted_street, v_extracted_number;
END;
$$;
