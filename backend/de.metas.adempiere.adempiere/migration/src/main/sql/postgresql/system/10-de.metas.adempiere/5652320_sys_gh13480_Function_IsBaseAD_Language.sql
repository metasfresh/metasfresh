DROP FUNCTION IF EXISTS isBaseAD_Language(
    p_AD_Language character varying
)
;

CREATE OR REPLACE FUNCTION isBaseAD_Language(
    p_AD_Language character varying
)
    RETURNS CHAR(1)
    LANGUAGE SQL
    STABLE
AS
$$
SELECT l.isbaselanguage
FROM AD_Language l
WHERE l.AD_Language = p_AD_Language
  AND l.IsActive = 'Y'
UNION ALL
SELECT 'N'
LIMIT 1;
$$
;


-- Tests
/*
SELECT isBaseAD_Language(null);
SELECT isBaseAD_Language('en_US');
SELECT isBaseAD_Language('de_DE');
SELECT isBaseAD_Language('xx_XX');
 */

