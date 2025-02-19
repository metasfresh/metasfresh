UPDATE ad_language
SET issystemlanguage='Y', isactive='Y'
WHERE ad_language IN ('de_CH', 'de_DE', 'en_US')
;

