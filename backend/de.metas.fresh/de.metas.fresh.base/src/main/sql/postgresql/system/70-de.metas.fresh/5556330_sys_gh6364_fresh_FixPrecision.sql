CREATE OR REPLACE FUNCTION getPricePattern(p_precision int)
    RETURNS varchar AS
$BODY$
DECLARE
    v_pattern varchar;
BEGIN

    select CASE
               WHEN p_precision = 0
                   THEN '9999999999'
               ELSE Substring('9999999990D999999' FROM 0 FOR 12 + p_precision :: integer) END
    into v_pattern;

    return v_pattern;
END;
$BODY$
    language plpgsql IMMUTABLE;
	
COMMENT ON FUNCTION getPricePattern(p_precision int)
  IS 'Builds a pattern using the given precision';

