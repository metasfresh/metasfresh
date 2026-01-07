CREATE OR REPLACE VIEW "de.metas.edi".edi_uom_object_v AS
SELECT c.c_uom_id,
       JSONB_BUILD_OBJECT(
               'X12DE355', c.X12DE355,
               'Name', c.name
       ) AS uom_json
FROM c_uom c
;

comment ON VIEW "de.metas.edi".edi_uom_object_v IS 'This view is used to create the Unit of Measure JSON objects that are sent to the EDI desadv';