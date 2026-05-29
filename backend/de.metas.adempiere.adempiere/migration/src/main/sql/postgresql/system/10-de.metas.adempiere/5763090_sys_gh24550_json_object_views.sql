CREATE OR REPLACE VIEW json_object.currency_object_v AS
SELECT c.c_currency_id,
       JSONB_BUILD_OBJECT(
               'ISO_Code', c.iso_code,
               'CurSymbol', c.cursymbol
           ) AS currency_json
FROM c_currency c
;

CREATE OR REPLACE VIEW json_object.bpartner_object_v AS
SELECT bp.c_bpartner_id,
       JSONB_BUILD_OBJECT(
               'Value', bp.value,
               'Name', bp.name,
               'Name2', bp.name2
           ) AS bpartner_json
FROM c_bpartner bp
;

CREATE OR REPLACE VIEW json_object.bpartner_location_object_v AS
SELECT bpl.c_bpartner_location_id,
       JSONB_BUILD_OBJECT(
               'GLN', bpl.gln,
               'Address1', l.address1,
               'Address2', l.address2,
               'Postal', l.postal,
               'City', l.city,
               'CountryCode', c.countrycode
           ) AS bpartner_location_json
FROM c_bpartner_location bpl
         JOIN c_location l ON l.c_location_id = bpl.c_location_id
         LEFT JOIN c_country c ON c.c_country_id = l.c_country_id
;
