/*
 * This function prepares invoice descriptions for inclusion in the Swiss QR Code.
 * According to the SIX Implementation Guidelines (v2.2), QR Code text fields must be:
 * - UTF-8 encoded
 * - limited to the permitted Latin character set (see Chapter 4.1.1 of the guide)
 * - using only alphanumeric characters (A–Z, a–z, 0–9), the decimal point ".", and space
 * - free from control characters (e.g. CR, LF, SOH, etc.)
 * - max 140 characters long
 *
 * The function therefore:
 * 1. Replaces carriage return (CR) and line feed (LF) with a space
 * 2. Removes all characters except letters, digits, dot (.), and space
 * 3. Collapses multiple spaces to a single space
 * 4. Trims leading and trailing whitespace
 * 5. Truncates the result to a maximum of 140 characters
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
