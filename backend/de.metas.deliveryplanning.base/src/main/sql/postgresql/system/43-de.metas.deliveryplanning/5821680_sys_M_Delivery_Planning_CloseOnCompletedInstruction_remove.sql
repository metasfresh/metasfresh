-- Removes AD_Message 545813 (de.metas.deliveryplanning.DeliveryPlanningService.CloseOnCompletedInstruction),
-- added by 5820810. Closing a delivery planning is a terminal indicator and no longer mutates anything - it
-- neither releases the allocation nor drops the ReleaseNo - so a planning on a completed delivery instruction
-- can be closed harmlessly and there is nothing left for this rejection to prevent.
--
-- No backup_table: AD_Message is structural application-dictionary metadata, not operator data.

DELETE FROM AD_Message_Trl WHERE AD_Message_ID=545813;

DELETE FROM AD_Message WHERE AD_Message_ID=545813;
