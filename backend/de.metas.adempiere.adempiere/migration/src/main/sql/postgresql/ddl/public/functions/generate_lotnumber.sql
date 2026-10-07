-- Function to generate lot numbers in format yy.MM.dd.n
-- where n is auto-incremented per day for material receipts
-- Example: On 2026-01-09, generates: 26.01.09.1, 26.01.09.2, 26.01.09.3, etc.

CREATE OR REPLACE FUNCTION generate_lotnumber(
    p_date DATE DEFAULT NOW()::DATE
)
    RETURNS VARCHAR
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_lotnumber VARCHAR;
    v_sequence  INT;
    v_prefix    VARCHAR;
BEGIN
    -- Generate the date prefix in format yy.MM.dd
    v_prefix := TO_CHAR(p_date, 'YY.MM.DD');

    -- Count existing lot numbers with this date prefix and get next sequence
    SELECT COALESCE(MAX(CAST(SUBSTRING(hua.value FROM LENGTH(v_prefix) + 2) AS INT)), 0) + 1
    INTO v_sequence
    FROM m_hu_attribute hua
             JOIN m_attribute a ON a.m_attribute_id = hua.m_attribute_id
    WHERE a.value = 'Lot-Nummer'
      AND hua.value LIKE v_prefix || '.%';

    -- Construct the final lot number
    v_lotnumber := v_prefix || '.' || v_sequence;

    RETURN v_lotnumber;
END;
$$;

-- Example usage:
-- SELECT generate_lotnumber();                    -- Uses current date: 26.01.21.1
-- SELECT generate_lotnumber('2026-01-09'::DATE);  -- Specific date: 26.01.09.1
-- SELECT generate_lotnumber('2026-01-09'::DATE);  -- Next call same day: 26.01.09.2
