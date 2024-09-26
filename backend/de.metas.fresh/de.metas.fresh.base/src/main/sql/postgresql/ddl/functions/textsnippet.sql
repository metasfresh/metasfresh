DROP FUNCTION if exists report.textsnippet(p_ad_boilerplate_id numeric, p_ad_language Character Varying (6));
CREATE OR REPLACE FUNCTION report.textsnippet(p_ad_boilerplate_id numeric, p_ad_language Character Varying (6) default 'de_DE')
    RETURNS text AS
$BODY$
DECLARE

    p_textsnippet_name text;

BEGIN
    select coalesce(bpt.textsnippet, bp.textsnippet) into p_textsnippet_name
                       from ad_boilerplate bp
                       INNER JOIN ad_boilerplate_trl bpt ON bp.ad_boilerplate_id = bpt.ad_boilerplate_id and bpt.ad_language=p_ad_language
                       where bp.ad_boilerplate_id = p_ad_boilerplate_id and bp.IsActive='Y';

    RETURN p_textsnippet_name;
END;
$BODY$ LANGUAGE plpgsql;


