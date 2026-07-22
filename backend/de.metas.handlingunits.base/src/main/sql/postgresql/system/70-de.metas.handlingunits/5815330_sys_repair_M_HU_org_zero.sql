-- Repair physical HUs left with AD_Org_ID=0 (the * / ANY org).
-- A physical HU lives in a locator -> warehouse -> exactly one real org, so AD_Org_ID=0 is corrupt: it makes
-- MD_Stock_From_HUs_V emit an org-0 aggregate row for a real-org warehouse, which MD_Stock_Update_From_M_HUs
-- cannot reconcile -> the whole stock-correction run fails. Set each such HU's org to its locator's
-- warehouse org. Idempotent: only touches org-0 HUs whose warehouse carries a real org, so a re-run
-- corrects nothing further.
SELECT backup_table('m_hu', '_repair_org_zero');

UPDATE m_hu hu
   SET ad_org_id = w.ad_org_id,
       updated   = TO_TIMESTAMP('2026-07-22 07:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby = 99
  FROM m_locator l
  JOIN m_warehouse w ON w.m_warehouse_id = l.m_warehouse_id
 WHERE hu.m_locator_id = l.m_locator_id
   AND hu.ad_org_id    = 0
   AND w.ad_org_id     > 0;
