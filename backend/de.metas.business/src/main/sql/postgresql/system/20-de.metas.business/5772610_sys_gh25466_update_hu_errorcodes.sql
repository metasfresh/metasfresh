-- For message "de.metas.handlingunits.HUProductsNotMatchingIssuingProducts"
UPDATE ad_message
SET errorcode = 'HU_PRODUCTS_NOT_MATCHING', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
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
SET errorcode = 'QUEUED_HUS_ON_SLOT', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
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
SET errorcode = 'HU_LOCATOR_NOT_MATCHING', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545492
;

-- For message "de.metas.handlingunits.movement.MoveHUCommand.ONLY_TUS_ON_LUS"
UPDATE ad_message
SET errorcode = 'ONLY_TUS_ON_LUS', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545508
;

-- For message "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingAggregatedTUsNotAllowed"
UPDATE ad_message
SET errorcode = 'ISSUING_AGGREGATED_TUS_NOT_ALLOWED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544324
;

-- For message "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingHUsWithMultipleProductsNotAllowed"
UPDATE ad_message
SET errorcode = 'ISSUING_MULTI_PRODUCT_HUS_NOT_ALLOWED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544325
;

-- For message "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingVHUsNotAllowed"
UPDATE ad_message
SET errorcode = 'ISSUING_VHUS_NOT_ALLOWED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544326
;

-- For message "WEBUI_Picking_Wrong_HU_Status"
UPDATE ad_message
SET errorcode = 'HU_WRONG_STATUS', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544462
;

-- For message "WEBUI_M_HU_MoveTUsToDirectWarehouse.NotEnoughTUsFound"
UPDATE ad_message
SET errorcode = 'NOT_ENOUGH_TUS_FOUND', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544485
;

-- For message "pharma.RepackNumberNotSetForHU"
UPDATE ad_message
SET errorcode = 'REPACK_NUMBER_NOT_SET', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544713
;

-- For message "WEBUI_HUs_IN_Quarantine"
UPDATE ad_message
SET errorcode = 'HUS_IN_QUARANTINE', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544778
;

-- For message "de.metas.handlingunits.inventory.ExistingLinesWithDifferentHUAggregationType"
UPDATE ad_message
SET errorcode = 'DIFFERENT_HU_AGGREGATION_TYPE', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544913
;

-- For message "WEBUI_Picking_PickingToTheSameHUForMultipleOrders"
UPDATE ad_message
SET errorcode = 'PICKING_TO_SAME_HU_MULTIPLE_ORDERS', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544993
;

-- For message "de.metas.handlingunits.ddorder.api.impl.HUDDOrderBL.NoHu_For_Product"
UPDATE ad_message
SET errorcode = 'NO_HU_FOR_PRODUCT', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 544995
;

-- For message "OnlyClearedHUsCanBeIssued"
UPDATE ad_message
SET errorcode = 'ONLY_CLEARED_HUS_ISSUED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545117
;

-- For message "OnlyClearedHusCanBePicked"
UPDATE ad_message
SET errorcode = 'ONLY_CLEARED_HUS_PICKED', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545116
;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- For message "de.metas.handlingunits.pporder.IssuingNotClearedHUsNotAllowed"
UPDATE ad_message
SET errorcode = 'ISSUING_NOT_CLEARED_HUS', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545165
;

-- For message "manufacturing.printReceivedHUQRCodes.NoFinishedGoodsAvailableForLabeling"
UPDATE ad_message
SET errorcode = 'NO_FINISHED_GOODS_FOR_LABELING', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545220
;

-- For message "de.metas.handlingunits.HUReceiptSchedule.PackageNumberNotMatching"
UPDATE ad_message
SET errorcode = 'PACKAGE_NUMBER_NOT_MATCHING', Updated=TO_TIMESTAMP('2025-10-07 09:25:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_message_id = 545572
;
