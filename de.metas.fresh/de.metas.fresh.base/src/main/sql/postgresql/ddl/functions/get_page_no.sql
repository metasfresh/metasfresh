drop function if exists report.get_page_no(IN pn numeric);

CREATE OR REPLACE FUNCTION report.get_page_no(IN pn numeric)
  RETURNS SETOF numeric AS
$BODY$
DECLARE
    r numeric;
BEGIN
    FOR r IN 1..$1
    LOOP
        RETURN NEXT r;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE plpgsql;

