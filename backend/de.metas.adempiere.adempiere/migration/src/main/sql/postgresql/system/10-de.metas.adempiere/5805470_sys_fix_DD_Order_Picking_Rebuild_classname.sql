-- Fix classname for AD_Process DD_Order_Picking_Rebuild after package rename
-- picking.dd_order.reconcile → ddorder.replenishment

UPDATE AD_Process
SET Classname = 'de.metas.handlingunits.ddorder.replenishment.process.DD_Order_Picking_Rebuild'
WHERE AD_Process_ID = 585623
  AND Classname = 'de.metas.handlingunits.picking.dd_order.reconcile.process.DD_Order_Picking_Rebuild';
