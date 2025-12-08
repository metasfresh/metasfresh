DROP FUNCTION IF EXISTS report.TextSnippet(IN p_ad_boilerplate_id numeric)
;

DROP FUNCTION IF EXISTS report.TextSnippet(IN p_ad_boilerplate_id numeric,
                                           IN p_ad_language       character varying)
;

CREATE OR REPLACE FUNCTION report.TextSnippet(IN p_ad_boilerplate_id numeric,
                                              IN p_ad_language       character varying DEFAULT 'de_DE')
    RETURNS text
AS
$BODY$
DECLARE

    p_textsnippet_name text;

BEGIN
    SELECT COALESCE(abp_trl.textsnippet, abp.textsnippet)
    INTO p_textsnippet_name
    FROM ad_boilerplate abp
             LEFT JOIN ad_boilerplate_trl abp_trl
                       ON abp_trl.ad_boilerplate_id = abp.ad_boilerplate_id AND abp_trl.ad_language = p_AD_Language
    WHERE abp.ad_boilerplate_id = p_ad_boilerplate_id
      AND abp.IsActive = 'Y';

    RETURN p_textsnippet_name;
END;
$BODY$
    LANGUAGE plpgsql
;


