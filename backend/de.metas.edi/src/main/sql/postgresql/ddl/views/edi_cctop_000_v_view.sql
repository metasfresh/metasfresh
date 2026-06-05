-- View: edi_cctop_000_v
-- EdiInvoicRecipientGLN moved from C_BPartner to C_BPartner_EDI_Setting (EDI location routing).
-- Coalesce: exact-location row first, then partner-default (location-less) row.

DROP VIEW IF EXISTS edi_cctop_000_v;
CREATE OR REPLACE VIEW edi_cctop_000_v AS
SELECT
    l.c_bpartner_location_id AS edi_cctop_000_v_id,
    l.c_bpartner_location_id,
    REGEXP_REPLACE(
        COALESCE(edi_loc.EdiInvoicRecipientGLN, edi_def.EdiInvoicRecipientGLN),
        '\s+$', '') AS EdiInvoicRecipientGLN,
    l.ad_client_id,
    l.ad_org_id,
    l.created,
    l.createdby,
    l.updated,
    l.updatedby,
    l.isactive
FROM c_bpartner_location l
         -- exact-location EDI setting row
         LEFT JOIN c_bpartner_edi_setting edi_loc
              ON edi_loc.c_bpartner_id = l.c_bpartner_id
             AND edi_loc.c_bpartner_location_id = l.c_bpartner_location_id
             AND edi_loc.isactive = 'Y'
         -- partner-default EDI setting row (no location)
         LEFT JOIN c_bpartner_edi_setting edi_def
              ON edi_def.c_bpartner_id = l.c_bpartner_id
             AND edi_def.c_bpartner_location_id IS NULL
             AND edi_def.isactive = 'Y';
