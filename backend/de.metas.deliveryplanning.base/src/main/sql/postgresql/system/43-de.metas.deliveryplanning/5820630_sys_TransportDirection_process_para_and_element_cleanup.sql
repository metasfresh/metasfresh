-- Finish the M_Delivery_Planning_Type -> TransportDirection rename:
--   1) fix AD_Val_Rule 540796's Code, which restricts the M_ShipperTransportation_ID lookup on
--      process 585654 (M_Delivery_Planning_AddToDeliveryInstruction, created by 5820460 in this
--      module) to drafts matching the sibling M_Delivery_Planning_Type process parameter -- both
--      the host-qualified column reference and the "@ParamName@" placeholder must follow the
--      column rename from 5820620, or the lookup silently stops filtering.
--   2) repoint that sibling AD_Process_Para (543277) from AD_Element 581679 to 585383
--      (TransportDirection, created by 5820600) and rename its ColumnName to match --
--      I_M_ShipperTransportation.COLUMNNAME_M_Delivery_Planning_Type becomes
--      COLUMNNAME_TransportDirection once the model is regenerated, and
--      M_Delivery_Planning_AddToDeliveryInstruction.java binds the parameter by that constant.
--   3) rename AD_Reference 541689 (the Incoming/Outgoing/Dropship list) off the old concept name.
--      Value codes (Incoming/Outgoing/Dropship) are UNCHANGED -- renaming those would be a data
--      migration and is out of scope.
--   4) deactivate AD_Element 581679, now unreferenced by any column, field or process parameter.
--
-- Evidence for step 4 (queried live, 2026-08-27, AFTER 5820600/5820610/5820620/this script's own
-- steps 1-3 have run): AD_Column WHERE AD_Element_ID=581679 -> 0 rows (585005 and 593410 both
-- repointed to 585383 by 5820610/5820620). AD_Field WHERE AD_Name_ID=581679 -> 0 rows (never was
-- any: none of the three fields over these two columns carries an AD_Name_ID override at all, so
-- all three follow the column). AD_Process_Para WHERE AD_Element_ID=581679 -> 0 rows after step 2 below
-- (543277 was the only one). AD_Window/AD_Tab/AD_Menu/WEBUI_KPI_Field WHERE AD_Element_ID=581679 ->
-- 0 rows (never referenced). Decision: deactivate (IsActive='N'), not delete -- AD_Element rows are
-- historical dictionary data (other environments' AD_Element_Link / audit trails may still point at
-- the id), and metasfresh convention is soft-delete via IsActive, never a hard DELETE of dictionary
-- rows that once shipped.
--
-- No new AD row is created here, so no ID-server allocation beyond this script's own
-- AD_MigrationScript 5820630 (idserver.metas.de, 2026-08-27).

-- 1) AD_Val_Rule 540796: rename both the column reference and the parameter placeholder
UPDATE AD_Val_Rule
SET    Code      = replace(Code, 'M_Delivery_Planning_Type', 'TransportDirection'),
       Updated   = TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Val_Rule_ID = 540796
;

-- 2) AD_Process_Para 543277: repoint to the new element and rename ColumnName to match
UPDATE AD_Process_Para
SET    AD_Element_ID = 585383,
       ColumnName    = 'TransportDirection',
       Updated       = TO_TIMESTAMP('2026-08-27 12:00:10','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy     = 100
WHERE  AD_Process_Para_ID = 543277
;
/* DDL */ select update_process_para_translation_from_ad_element(585383)
;

-- 3) AD_Reference 541689: rename off the old concept name (list values are data, unchanged)
UPDATE AD_Reference
SET    Name      = 'TransportDirection',
       Updated   = TO_TIMESTAMP('2026-08-27 12:00:20','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Reference_ID = 541689
;
UPDATE AD_Reference_Trl
SET    Name      = 'TransportDirection',
       Updated   = TO_TIMESTAMP('2026-08-27 12:00:21','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Reference_ID = 541689
;

-- 4) AD_Element 581679: deactivate -- see evidence above
UPDATE AD_Element
SET    IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-27 12:00:30','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Element_ID = 581679
;
