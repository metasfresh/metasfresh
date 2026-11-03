-- View: edi_cctop_000_v
-- EdiInvoicRecipientGLN resolved from C_BPartner_EDI_Setting via LATERAL:
-- lowest SeqNo (then lowest ID) among active rows matching the location's partner+location
-- (exact-location OR partner-default), mirroring Java EDIBPartnerConfigMap.resolve.

DROP VIEW IF EXISTS edi_cctop_000_v;
CREATE OR REPLACE VIEW edi_cctop_000_v AS
SELECT
    l.c_bpartner_location_id AS edi_cctop_000_v_id,
    l.c_bpartner_location_id,
    REGEXP_REPLACE(
        edi_setting.EdiInvoicRecipientGLN,
        '\s+$', '') AS EdiInvoicRecipientGLN,
    l.ad_client_id,
    l.ad_org_id,
    l.created,
    l.createdby,
    l.updated,
    l.updatedby,
    l.isactive
FROM c_bpartner_location l
         -- EDI setting: LATERAL picks the single active row with lowest SeqNo (then lowest ID)
         -- among rows matching partner+location exactly OR partner-default (location IS NULL).
         LEFT JOIN LATERAL (
             SELECT s.*
             FROM c_bpartner_edi_setting s
             WHERE s.c_bpartner_id = l.c_bpartner_id
               AND (s.c_bpartner_location_id = l.c_bpartner_location_id OR s.c_bpartner_location_id IS NULL)
               AND s.isactive = 'Y'
             ORDER BY s.seqno, s.c_bpartner_edi_setting_id
             LIMIT 1
         ) edi_setting ON TRUE;
