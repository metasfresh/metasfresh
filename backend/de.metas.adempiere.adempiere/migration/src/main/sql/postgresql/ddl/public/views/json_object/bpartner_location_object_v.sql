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
