DROP FUNCTION if exists report.textsnippet(IN p_ad_boilerplate_id numeric);
CREATE OR REPLACE FUNCTION report.textsnippet(IN p_ad_boilerplate_id numeric)
    RETURNS text AS
$BODY$
DECLARE

    p_textsnippet_name text;

BEGIN
    select textsnippet into p_textsnippet_name from ad_boilerplate where ad_boilerplate_id = p_ad_boilerplate_id and IsActive='Y';

    RETURN p_textsnippet_name;
END;
$BODY$ LANGUAGE plpgsql;


