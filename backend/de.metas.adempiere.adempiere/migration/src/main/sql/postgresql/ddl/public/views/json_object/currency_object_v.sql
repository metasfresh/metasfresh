CREATE OR REPLACE VIEW json_object.currency_object_v AS
SELECT c.c_currency_id,
       JSONB_BUILD_OBJECT(
               'ISO_Code', c.iso_code,
               'CurSymbol', c.cursymbol
           ) AS currency_json
FROM c_currency c
;