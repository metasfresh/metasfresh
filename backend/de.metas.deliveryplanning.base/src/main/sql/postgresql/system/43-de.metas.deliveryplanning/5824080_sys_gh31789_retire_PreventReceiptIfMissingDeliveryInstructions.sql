-- Retire the SysConfig de.metas.deliveryplanning.webui.process.PreventReceiptIfMissingDeliveryInstructions
-- (AD_SysConfig_ID 541601, created 2023-03-13 by 5681590_sys_gh14843_...).
--
-- It gated the "a receipt requires a COMPLETED delivery instruction" check on the Delivery Planning window,
-- defaulting to 'N' - so the receive action was offered for a planning the window itself showed as not ready.
-- Receiving before the instruction is completed makes no business sense, so that check is now unconditional
-- (DeliveryPlanningGenerateProcessesHelper.checkEligibleToCreateReceipt, matching the outgoing sibling
-- checkEligibleToCreateShipment, which never had a switch). Nothing reads this SysConfig any more.
--
-- Deleted rather than left in place: a config row that still promises an effect it no longer has is a trap -
-- the next admin sets it and nothing happens.
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=541601
;
