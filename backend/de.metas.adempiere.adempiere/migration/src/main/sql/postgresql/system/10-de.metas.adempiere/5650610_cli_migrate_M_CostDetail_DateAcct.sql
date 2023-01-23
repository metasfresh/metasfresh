UPDATE M_CostDetail cd
SET DateAcct=mi.DateAcct
FROM m_matchinv mi
WHERE mi.m_matchinv_id = cd.m_matchinv_id
;

UPDATE M_CostDetail cd
SET DateAcct=mpo.DateAcct
FROM m_matchpo mpo
WHERE mpo.m_matchpo_id = cd.m_matchpo_id
;

UPDATE M_CostDetail cd
SET DateAcct=cc.DateAcct
FROM pp_cost_collector cc
WHERE cc.pp_cost_collector_id = cd.pp_cost_collector_id
;

UPDATE M_CostDetail cd
SET DateAcct=(SELECT inv.movementdate FROM m_inventory inv WHERE inv.m_inventory_id = invl.m_inventory_id)
FROM m_inventoryline invl
WHERE invl.m_inventoryline_id = cd.m_inventoryline_id
;

UPDATE M_CostDetail cd
SET DateAcct=(SELECT m.movementdate FROM m_movement m WHERE m.m_movement_id = ml.m_movement_id)
FROM m_movementline ml
WHERE ml.m_movementline_id = cd.m_movementline_id
;

UPDATE M_CostDetail cd
SET DateAcct=(SELECT io.dateacct FROM m_inout io WHERE io.m_inout_id = iol.m_inout_id)
FROM m_inoutline iol
WHERE iol.m_inoutline_id = cd.m_inoutline_id
;

