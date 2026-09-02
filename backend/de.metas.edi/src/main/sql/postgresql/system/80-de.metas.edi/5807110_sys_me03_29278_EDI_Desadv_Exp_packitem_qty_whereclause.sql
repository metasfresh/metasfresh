-- Run mode: SWING_CLIENT

-- DESADV export must skip pack items whose OWN delivered qty is 0.
-- gh22804 added a parent-line qty filter (dl.QtyDeliveredInUOM>0); this adds the
-- pack-item-level filter (pi.MovementQty>0) so an orphan/zero-qty pack item on a
-- line that still has qty>0 is not exported as an empty line.

-- EDI_Exp_Desadv_Pack_Item (540418): also require the pack item's own MovementQty>0
UPDATE EXP_Format SET WhereClause=
'IsActive=''Y'' and MovementQty>0 and EDI_DesadvLine_ID in (select dl.EDI_DesadvLine_ID from EDI_DesadvLine dl where dl.IsActive=''Y'' and dl.QtyDeliveredInUOM>0)',
 Updated=TO_TIMESTAMP('2026-06-10 10:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE EXP_Format_ID=540418
;

-- EDI_Exp_Desadv_Pack (540419): a pack qualifies only if it has >=1 item with MovementQty>0 on a qty>0 line.
-- The pack-item subquery is deliberate: it checks individual item qty, not the pack header.
-- A full scan is acceptable here — EDI DESADV data volumes are small (dozens to hundreds of rows).
UPDATE EXP_Format SET WhereClause=
'IsActive=''Y'' and EDI_Desadv_Pack_ID in (select pi.EDI_Desadv_Pack_ID from EDI_Desadv_Pack_Item pi join EDI_DesadvLine dl on pi.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID where pi.IsActive=''Y'' and pi.MovementQty>0 and dl.IsActive=''Y'' and dl.QtyDeliveredInUOM>0)',
 Updated=TO_TIMESTAMP('2026-06-10 10:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE EXP_Format_ID=540419
;
