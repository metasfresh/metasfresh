
-- Required view for business partner objects
CREATE OR REPLACE VIEW "de.metas.edi".edi_bpartner_object_v AS
SELECT
    bp.c_bpartner_id,
    jsonb_build_object(
            'Value', bp.value,
            'Name', bp.name,
            'IsVendor', bp.isvendor,
            'IsCustomer', bp.iscustomer,
            'GLN', bp.edidesadvrecipientgln
    ) AS bpartner_json
FROM c_bpartner bp;
