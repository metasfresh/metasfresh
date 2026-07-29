-- https://github.com/metasfresh/me03/issues/29966
-- Update AD_Process classname after moving DD_Order picking-replenishment class group
-- from de.metas.handlingunits.ddorder.replenishment to de.metas.distribution.ddorder.replenishment.

UPDATE AD_Process
SET Classname='de.metas.distribution.ddorder.replenishment.process.DD_Order_Picking_Rebuild',
    Updated=now()
WHERE Classname='de.metas.handlingunits.ddorder.replenishment.process.DD_Order_Picking_Rebuild';
