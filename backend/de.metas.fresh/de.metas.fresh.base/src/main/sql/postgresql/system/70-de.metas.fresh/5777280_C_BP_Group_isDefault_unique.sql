SELECT backup_table('c_bp_group')
;

CREATE TEMP TABLE c_bp_group_first_defaults AS
SELECT DISTINCT ON (g.ad_client_id, g.ad_org_id) g.*
FROM c_bp_group g
WHERE g.isdefault = 'Y'
ORDER BY g.ad_client_id,
         g.ad_org_id,
         g.updated, -- oldest first
         g.c_bp_group_id -- stable tiebreaker
;

UPDATE c_bp_group
SET isdefault='N',
    updatedby= 99,
    updated  = TO_TIMESTAMP('2025-11-18 21:00:00', 'YYYY-MM-DD HH24:MI:SS')
WHERE isdefault = 'Y'
  AND c_bp_group_id NOT IN (SELECT c_bp_group_id FROM c_bp_group_first_defaults)
;

CREATE UNIQUE INDEX idx_c_bp_group_defaults ON c_bp_group (ad_client_id, ad_org_id) WHERE isdefault = 'Y'
;

