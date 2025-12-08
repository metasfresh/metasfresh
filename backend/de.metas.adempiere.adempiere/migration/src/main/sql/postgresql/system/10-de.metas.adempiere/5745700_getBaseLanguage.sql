DROP FUNCTION IF EXISTS getBaseLanguage();

CREATE OR REPLACE FUNCTION getBaseLanguage(
)
    RETURNS character varying
    LANGUAGE SQL
    STABLE
AS
$$
SELECT AD_Language FROM AD_Language WHERE IsBaseLanguage='Y' AND IsActive = 'Y';
$$
;


-- Tests
/*
SELECT getBaseLanguage();
 */

