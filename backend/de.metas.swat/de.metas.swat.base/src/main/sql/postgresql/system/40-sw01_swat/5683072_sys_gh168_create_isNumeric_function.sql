CREATE OR REPLACE FUNCTION isnumeric(text)
    RETURNS "pg_catalog"."bool"
AS
$BODY$
DECLARE
    x NUMERIC;
BEGIN
    x = $1::NUMERIC;
    RETURN TRUE;
EXCEPTION
    WHEN OTHERS THEN
        RETURN FALSE;
END;
$BODY$
    LANGUAGE 'plpgsql' IMMUTABLE
                       STRICT
                       COST 100
;

ALTER FUNCTION isnumeric(text) OWNER TO "metasfresh"
;
