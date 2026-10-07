-- The two "receive HUs" actions, also on the DELIVERY-PLANNING window (541632, AD_Table 542259
-- M_Delivery_Planning) - in ADDITION to M_Delivery_Planning_GenerateReceipt, which stays exactly as it is.
--
-- Same two AD_Process rows the receipt-disposition window already offers (585658 "HUs annehmen Voreinst.",
-- 585659 "HUs annehmen", created by 5822600), pointed at a second table. No process, parameter or class is
-- duplicated: both windows funnel into ReceiptFromReceiptScheduleService.createReceipt, and the action classes
-- read their two source ids off the selected GRID ROW - M_ReceiptSchedule_ID and M_Delivery_Planning_ID, which
-- M_Delivery_Planning spells exactly as RV_ReceiptDisposition_DeliveryPlanning does. What GenerateReceipt cannot
-- do and these can: receive into real handling units built from a packing instruction, instead of one planning
-- VHU for a typed quantity.
--
-- AD_Window_ID is NULL, i.e. the actions appear on every window over M_Delivery_Planning. The table has exactly
-- one (541632), so scoping would hard-code an id that adds nothing; M_Delivery_Planning_GenerateReceipt,
-- _GenerateShipment and _Close are NULL for the same reason. (The four delivery-instruction actions ARE scoped
-- to 541632 - the outlier here, not the pattern. Contrast M_ShipperTransportation, which genuinely has two
-- windows and therefore genuinely needs scoping.)
--
-- WEBUI_ViewQuickAction_Default is 'N' on BOTH rows, deliberately: on THIS table the default quick action is
-- M_Delivery_Planning_CombineIntoDeliveryInstruction (AD_Table_Process 541665) and must stay so. On the
-- receipt-disposition table "HUs annehmen Voreinst." holds the default - so copying that table's row
-- configuration over would silently take the one-click slot away from the combine action.
--
-- An OUTGOING delivery planning has no receipt schedule at all (measured on deep_tundra_release: all 1919
-- Outgoing plannings have M_ReceiptSchedule_ID NULL, all 2631 Incoming/Dropship ones have it set), which is why
-- ReceiptDispositionDeliveryPlanningReceiveHUsProcess refuses such a row before it reads the schedule.
--
-- Ids 541692, 541693 (AD_Table_Process): From ID Server.

INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
                              Updated, UpdatedBy, EntityType, AD_Table_ID, AD_Process_ID, AD_Window_ID,
                              WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default,
                              WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_IncludedTabTopAction)
VALUES
 (541692 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-09 19:20:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-09 19:20:00','YYYY-MM-DD HH24:MI:SS'),100,'D',542259,585658,NULL,'Y','N','Y','Y','N'),
 (541693 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-09 19:20:01','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-09 19:20:01','YYYY-MM-DD HH24:MI:SS'),100,'D',542259,585659,NULL,'Y','N','Y','Y','N')
;
