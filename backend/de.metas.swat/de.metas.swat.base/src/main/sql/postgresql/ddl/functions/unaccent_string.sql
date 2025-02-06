DROP FUNCTION IF EXISTS unaccent_string(text, numeric)
;

CREATE OR REPLACE FUNCTION unaccent_string(text, numeric)
    RETURNS text AS
$BODY$
DECLARE
    input_string text := $1;
    version numeric := $2;
BEGIN
    input_string := public.unaccent(input_string); -- public schema needs to be set, so function is found on restore of c_doc_outbound_log_poreference_index

    if version = 1 then
        input_string := replace(lower(input_string),'ß','ss');
        input_string := replace(lower(input_string),'ä','ae');
        input_string := replace(lower(input_string),'ö','oe');
        input_string := replace(lower(input_string),'ü','ue');
    else
        input_string := replace(lower(input_string),'ss','ß');
        input_string := replace(lower(input_string),'ae','ä');
        input_string := replace(lower(input_string),'oe','ö');
        input_string := replace(lower(input_string),'ue','ü');
    end if;
    return input_string;
END;
$BODY$
    LANGUAGE plpgsql IMMUTABLE
                     COST 100;
