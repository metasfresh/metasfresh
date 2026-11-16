-- Material Cockpit v2 jump processes moved from package de.metas.material.process (de.metas.swat.base)
-- to de.metas.ui.web.material.cockpit.v2.jump (de.metas.ui.web.base), so AD_Process.Classname must follow.
-- Keyed on AD_Process_ID AND the expected old Classname so a re-run cannot clobber a hand-fixed row.
-- AD_Process_ID 585515 (QtyDemand_QtySupply_V_to_Forecast) is intentionally NOT touched here: it uses
-- de.metas.ui.web.view.process.RelationTypeInOverlayProcess, a shared class that is not moving.

UPDATE AD_Process
   SET Classname='de.metas.ui.web.material.cockpit.v2.jump.QtyDemand_QtySupply_V_to_ShipmentSchedule',
       Updated=TO_TIMESTAMP('2026-09-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
 WHERE AD_Process_ID=585513
   AND Classname='de.metas.material.process.QtyDemand_QtySupply_V_to_ShipmentSchedule'
;

UPDATE AD_Process
   SET Classname='de.metas.ui.web.material.cockpit.v2.jump.QtyDemand_QtySupply_V_to_ReceiptSchedule',
       Updated=TO_TIMESTAMP('2026-09-01 10:00:01','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
 WHERE AD_Process_ID=585514
   AND Classname='de.metas.material.process.QtyDemand_QtySupply_V_to_ReceiptSchedule'
;

UPDATE AD_Process
   SET Classname='de.metas.ui.web.material.cockpit.v2.jump.QtyDemand_QtySupply_V_to_PP_Order_Candidate',
       Updated=TO_TIMESTAMP('2026-09-01 10:00:02','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
 WHERE AD_Process_ID=585516
   AND Classname='de.metas.material.process.QtyDemand_QtySupply_V_to_PP_Order_Candidate'
;
