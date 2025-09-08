UPDATE c_bp_printformat bpp
Set seqno = print_formats.seqNo,
    updated = TO_TIMESTAMP('2023-11-03 15:38:32','YYYY-MM-DD HH24:MI:SS'),
    updatedby = 99
FROM (SELECT
          pf.c_bp_printformat_id,
          (row_number() OVER (PARTITION BY pf.c_bpartner_id ORDER BY pf.c_bp_printformat_id)) * 10 AS seqNo
      FROM c_bp_printformat pf
     ) AS print_formats
WHERE bpp.c_bp_printformat_id = print_formats.c_bp_printformat_id
;

-- 2023-11-03T07:44:00.214Z
CREATE UNIQUE INDEX bpartner_seqno_ux ON C_BP_PrintFormat (C_BPartner_ID,SeqNo) WHERE IsActive='Y'
;