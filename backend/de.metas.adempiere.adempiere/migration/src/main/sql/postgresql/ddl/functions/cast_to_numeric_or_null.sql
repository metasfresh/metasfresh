CREATE OR REPLACE FUNCTION cast_to_numeric_or_null(p_string text) RETURNS NUMERIC AS
$$
BEGIN
    RETURN p_string::NUMERIC;
EXCEPTION WHEN others THEN
    RETURN NULL;
END;
$$
    STRICT
    LANGUAGE plpgsql IMMUTABLE;
COMMENT ON FUNCTION cast_to_numeric_or_null(text) is 'Attempts to cast the given p_string to numeric. Returns null if that fails. Thx to https://stackoverflow.com/a/16206123/1012103';