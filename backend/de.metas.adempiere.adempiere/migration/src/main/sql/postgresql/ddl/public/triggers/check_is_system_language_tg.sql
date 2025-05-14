DROP TRIGGER IF EXISTS check_is_system_language_tg ON ad_language
;

DROP FUNCTION IF EXISTS check_is_system_language_tgfn()
;


CREATE OR REPLACE FUNCTION check_is_system_language_tgfn()
    RETURNS TRIGGER
AS
$$
DECLARE
    supported_languages CONSTANT TEXT[] := ARRAY ['de_DE', 'de_CH', 'en_US'];
BEGIN

    -- Make sure nobody is deactivating one of the languages that we have support on each instance we have
    IF NEW.ad_language = ANY (supported_languages) THEN
        IF NEW.issystemlanguage != 'Y' AND NEW.isbaselanguage != 'Y' THEN
            RAISE EXCEPTION 'IsSystemLanguage or IsBaseLanguage must be ''Y'' for ad_language = %', NEW.ad_language;
        END IF;
        IF NEW.isactive != 'Y' THEN
            RAISE EXCEPTION 'IsActive must be ''Y'' for ad_language = %', NEW.ad_language;
        END IF;
    END IF;

    RETURN NEW;
END;
$$
    LANGUAGE plpgsql
;

CREATE TRIGGER check_is_system_language_tg
    BEFORE INSERT OR UPDATE
    ON ad_language
    FOR EACH ROW
EXECUTE PROCEDURE check_is_system_language_tgfn()
;

