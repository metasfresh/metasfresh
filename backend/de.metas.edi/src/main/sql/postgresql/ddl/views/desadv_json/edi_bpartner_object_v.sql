CREATE OR REPLACE VIEW "de.metas.edi".edi_bpartner_object_v AS
SELECT bp.c_bpartner_id,
       JSONB_BUILD_OBJECT(
               'Value', bp.value,
               'Name', bp.name,
               'Name2', bp.name2
       ) AS bpartner_json
FROM c_bpartner bp
;
