UPDATE ad_Sysconfig
SET value='http://app:8282/api',
    description='Base URL for the app Server''s REST-API. Used by jasper reports that do not only use SQL, but also API-Responses (JSON).'
WHERE name = 'API_URL'
;
