-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/views/edi_cctop_000_v_view.sql
-- EDI export views — resolve recipient config by lowest SeqNo
--
-- Replaces the two-LEFT-JOIN + COALESCE pattern (exact-location row / partner-default row)
-- with a single LEFT JOIN LATERAL that picks the active C_BPartner_EDI_Setting row with the
-- lowest SeqNo (ties broken by C_BPartner_EDI_Setting_ID), matching the resolution logic in
-- Java EDIBPartnerConfigMap.resolve (Comparator: SeqNo asc, then ID asc, among rows where
-- C_BPartner_Location_ID = <doc-location> OR C_BPartner_Location_ID IS NULL).
--
-- This file: edi_cctop_000_v  — EdiInvoicRecipientGLN

-- ==============================================================================================
-- edi_cctop_000_v
-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/views/edi_cctop_000_v_view.sql
-- ==============================================================================================

DROP VIEW IF EXISTS edi_cctop_000_v$new;

CREATE OR REPLACE VIEW edi_cctop_000_v$new AS
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

SELECT public.db_alter_view(
    'edi_cctop_000_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(table_name) = lower('edi_cctop_000_v$new'))
);

DROP VIEW IF EXISTS edi_cctop_000_v$new;
