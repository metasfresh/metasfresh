DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Format_Invoice_Description_For_QR_Code(p_input TEXT);


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
