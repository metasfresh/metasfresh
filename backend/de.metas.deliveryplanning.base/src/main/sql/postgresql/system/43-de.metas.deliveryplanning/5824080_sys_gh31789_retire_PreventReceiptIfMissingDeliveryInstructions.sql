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
--
-- Safe for every other branch and customer, not just this one: the row's Value is 'N' and the coded default
-- of the getBooleanValue call that read it is false - the same answer - so a branch whose code still reads
-- this name behaves identically with the row gone. An org-level override is untouched, since this targets
-- AD_SysConfig_ID 541601, the system row at client/org 0.
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=541601
;
