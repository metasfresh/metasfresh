
-- Required view for currency objects
CREATE OR REPLACE VIEW "de.metas.edi".edi_currency_object_v AS
SELECT
    c.c_currency_id,
    jsonb_build_object(
            'ISO_Code', c.iso_code,
            'CurSymbol', c.cursymbol
    ) AS currency_json
FROM c_currency c;
