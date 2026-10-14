-- Backfill existing M_InOut.EDI_Desadv_ID single-FK rows into the EDI_Desadv_M_InOut junction table.

INSERT INTO edi_desadv_m_inout (
    edi_desadv_m_inout_id, edi_desadv_id, m_inout_id,
    ad_client_id, ad_org_id,
    created, createdby, updated, updatedby, isactive)
SELECT nextval('edi_desadv_m_inout_seq'),
       io.edi_desadv_id, io.m_inout_id,
       io.ad_client_id, io.ad_org_id,
       now(), io.createdby, now(), io.updatedby, 'Y'
FROM m_inout io
WHERE io.edi_desadv_id IS NOT NULL AND io.edi_desadv_id > 0
ON CONFLICT (edi_desadv_id, m_inout_id) DO NOTHING;

-- Self-check: every populated M_InOut.EDI_Desadv_ID is now mirrored in the junction.
DO $$
DECLARE
    legacy_count   bigint;
    junction_count bigint;
BEGIN
    SELECT count(*) INTO legacy_count   FROM m_inout WHERE edi_desadv_id IS NOT NULL AND edi_desadv_id > 0;
    SELECT count(*) INTO junction_count FROM edi_desadv_m_inout edmi
        WHERE EXISTS (SELECT 1 FROM m_inout io WHERE io.m_inout_id = edmi.m_inout_id AND io.edi_desadv_id = edmi.edi_desadv_id);
    IF junction_count < legacy_count THEN
        RAISE EXCEPTION 'EDI_Desadv_M_InOut backfill incomplete: legacy=% junction=%', legacy_count, junction_count;
    END IF;
    RAISE NOTICE 'Backfill OK: legacy=% junction=% (junction may exceed legacy if old rows had EDI_Desadv_ID=0)', legacy_count, junction_count;
END $$;
