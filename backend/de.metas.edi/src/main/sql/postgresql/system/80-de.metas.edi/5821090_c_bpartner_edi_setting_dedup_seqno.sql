-- C_BPartner_EDI_Setting — de-duplicate before the uniqueness index (step 1 of 2).
--
-- The follow-up script builds a partial unique index on
-- (SeqNo, C_BPartner_ID, COALESCE(location,0)) WHERE IsActive='Y'. A UNIQUE
-- INDEX cannot be built while duplicate active rows exist, so any legacy
-- duplicate must be resolved FIRST (data fix kept separate from the DDL).
--
-- Such duplicates are plausible on live instances: from 2026-06-09 until this
-- change the SeqNo column carried a CONSTANT default of 10, so a partner given
-- two no-location "partner default" rows (or two rows for the same location)
-- ends up with an identical (SeqNo=10, C_BPartner_ID, COALESCE(location,0)) key.
-- This step is a no-op on clean data (updates 0 rows) — defensive, so the
-- index build cannot abort a live instance's migration run.
--
-- Strategy: within each (C_BPartner_ID, COALESCE(location,0)) bucket, keep the
-- lowest-id row's SeqNo untouched (it stays the resolution winner: resolution is
-- lowest SeqNo, then lowest id) and move only the colliding rows to fresh SeqNos
-- above the bucket maximum, so they become unique without changing which row
-- wins. Only active rows are touched (the index is WHERE IsActive='Y').

SELECT backup_table('c_bpartner_edi_setting', '_edi_setting_seqno_dedup');

WITH ranked AS (
    SELECT s.C_BPartner_EDI_Setting_ID AS id,
           s.C_BPartner_ID             AS bp,
           COALESCE(s.C_BPartner_Location_ID, 0) AS loc,
           ROW_NUMBER() OVER (PARTITION BY s.C_BPartner_ID,
                                           COALESCE(s.C_BPartner_Location_ID, 0),
                                           s.SeqNo
                              ORDER BY s.C_BPartner_EDI_Setting_ID) AS dup_rn
    FROM C_BPartner_EDI_Setting s
    WHERE s.IsActive = 'Y'
),
losers AS (
    SELECT id, bp, loc,
           ROW_NUMBER() OVER (PARTITION BY bp, loc ORDER BY id) AS off
    FROM ranked
    WHERE dup_rn > 1
),
maxes AS (
    SELECT C_BPartner_ID AS bp,
           COALESCE(C_BPartner_Location_ID, 0) AS loc,
           MAX(SeqNo) AS bmax
    FROM C_BPartner_EDI_Setting
    WHERE IsActive = 'Y'
    GROUP BY C_BPartner_ID, COALESCE(C_BPartner_Location_ID, 0)
)
UPDATE C_BPartner_EDI_Setting t
SET SeqNo     = m.bmax + l.off * 10,
    UpdatedBy = 99,
    Updated   = TO_TIMESTAMP('2026-08-28 09:59:00', 'YYYY-MM-DD HH24:MI:SS')
FROM losers l
JOIN maxes m ON m.bp = l.bp AND m.loc = l.loc
WHERE t.C_BPartner_EDI_Setting_ID = l.id
;
