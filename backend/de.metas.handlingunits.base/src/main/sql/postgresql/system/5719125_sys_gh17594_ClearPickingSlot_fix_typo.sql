-- Value: ClearPickingSlot
-- Classname: de.metas.handlingunits.picking.process.ClearPickingSlot
-- 2024-03-13T12:09:05.034Z
UPDATE AD_Process SET Description='If none of the parameters is checked, metasfresh tries to release the picking slot performing the following actions: first, it checks for allocated but not started picking jobs (i.e. a job where nothing was picked) and performs an ''Abort'' operation. Then, if the slot is not allocated to any other ongoing jobs, or ongoing pickings performed in the Picking Terminal, and no HUs were already picked and queued on it, the slot is released.',Updated=TO_TIMESTAMP('2024-03-13 14:09:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:09:57.205Z
UPDATE AD_Process_Trl SET Description='If none of the parameters is checked, metasfresh tries to release the picking slot performing the following actions: first, it checks for allocated but not started picking jobs (i.e. a job where nothing was picked) and performs an ''Abort'' operation. Then, if the slot is not allocated to any other ongoing jobs, or ongoing pickings performed in the Picking Terminal, and no HUs were already picked and queued on it, the slot is released.',Updated=TO_TIMESTAMP('2024-03-13 14:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585360
;