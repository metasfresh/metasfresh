-- For message "de.metas.handlingunits.HUProductsNotMatchingIssuingProducts"
UPDATE ad_message
SET errorcode = 'HU_PRODUCTS_NOT_MATCHING_ISSUING_PRODUCTS', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545215
;

-- For message "de.metas.handlingunits.HUIsEmpty"
UPDATE ad_message
SET errorcode = 'HU_IS_EMPTY', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545214
;

-- For message "de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG"
UPDATE ad_message
SET errorcode = 'HU_CANNOT_BE_PICKED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545384
;

-- For message "de.metas.handlingunits.picking.QUEUED_HUS_ON_SLOT_ERR_MSG"
UPDATE ad_message
SET errorcode = 'QUEUED_HUS_ON_TARGET_SLOT', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545381
;

-- For message "de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.NO_QTY_RESERVED_ERROR_MSG"
UPDATE ad_message
SET errorcode = 'NO_QTY_RESERVED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545456
;

-- For message "de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.RESERVED_ERROR_MSG"
UPDATE ad_message
SET errorcode = 'CANNOT_RESERVE_FULL_QTY', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545457
;

-- For message "HULocatorNotMatchingPickFromLocator"
UPDATE ad_message
SET errorcode = 'HU_LOCATOR_NOT_MATCHING_PICK_FROM_LOCATOR', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545492
;

-- For message "de.metas.handlingunits.movement.MoveHUCommand.ONLY_TUS_ON_LUS"
UPDATE ad_message
SET errorcode = 'ONLY_TUS_ON_LUS', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545508
;

-- For message "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingAggregatedTUsNotAllowed"
UPDATE ad_message
SET errorcode = 'ISSUING_AGGREGATED_TU_NOT_ALLOWED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544324
;

-- For message "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingHUsWithMultipleProductsNotAllowed"
UPDATE ad_message
SET errorcode = 'ISSUING_HUS_WITH_MULTIPLE_PRODUCTS_NOT_ALLOWED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544325
;

-- For message "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingVHUsNotAllowed"
UPDATE ad_message
SET errorcode = 'ISSUING_V_HUS_NOT_ALLOWED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544326
;

-- For message "WEBUI_Picking_Wrong_HU_Status"
UPDATE ad_message
SET errorcode = 'HU_WRONG_STATUS', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544462
;

-- For message "WEBUI_M_HU_MoveTUsToDirectWarehouse.NotEnoughTUsFound"
UPDATE ad_message
SET errorcode = 'HU_NOT_ENOUGH_TU', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544485
;

-- For message "pharma.RepackNumberNotSetForHU"
UPDATE ad_message
SET errorcode = 'REPACK_NUMBER_NOT_SET_FOR_HU', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544713
;

-- For message "WEBUI_HUs_IN_Quarantine"
UPDATE ad_message
SET errorcode = 'SELECTED_HUS_IN_QUARANTINE', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544778
;

-- For message "de.metas.handlingunits.inventory.ExistingLinesWithDifferentHUAggregationType"
UPDATE ad_message
SET errorcode = 'EXISTING_LINES_WITH_DIFFERENT_HU_AGGREGATION_TYPE', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544913
;

-- For message "WEBUI_Picking_PickingToTheSameHUForMultipleOrders"
UPDATE ad_message
SET errorcode = 'PICKING_TO_THE_SAME_HU_FOR_MULTIPLE_ORDERS', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544993
;

-- For message "de.metas.handlingunits.ddorder.api.impl.HUDDOrderBL.NoHu_For_Product"
UPDATE ad_message
SET errorcode = 'NO_HU_FOR_PRODUCT', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544995
;

-- For message "OnlyClearedHUsCanBeIssued"
UPDATE ad_message
SET errorcode = 'ONLY_CLEARED_HUS_CAN_BE_ISSUED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545117
;

-- For message "OnlyClearedHusCanBePicked"
UPDATE ad_message
SET errorcode = 'ONLY_CLEARED_HUS_CAN_BE_PICKED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545116
;

-- For message "de.metas.handlingunits.pporder.IssuingNotClearedHUsNotAllowed"
UPDATE ad_message
SET errorcode = 'ISSUING_NOT_CLEARED_HUS_NOT_ALLOWED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545165
;

-- For message "manufacturing.printReceivedHUQRCodes.NoFinishedGoodsAvailableForLabeling"
UPDATE ad_message
SET errorcode = 'NO_FINISHED_GOODS_AVAILABLE_FOR_LABELING', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545220
;

-- For message "de.metas.handlingunits.HUReceiptSchedule.PackageNumberNotMatching"
UPDATE ad_message
SET errorcode = 'PACKAGE_NUMBER_NOT_MATCHING', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545572
;
