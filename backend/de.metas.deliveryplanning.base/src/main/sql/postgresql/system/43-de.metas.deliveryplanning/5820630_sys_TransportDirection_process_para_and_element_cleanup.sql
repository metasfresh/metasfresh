-- Finish the M_Delivery_Planning_Type -> TransportDirection rename in the application dictionary:
-- the "Add to Delivery Instruction" value rule and process parameter, the reference name, and the
-- element left behind.

-- 1) AD_Val_Rule 540796, the M_ShipperTransportation_ID lookup on process 585654: both the
--    host-qualified column reference and the "@ParamName@" placeholder must follow the column
--    rename, or the lookup silently stops filtering.
UPDATE AD_Val_Rule
SET    Code      = replace(Code, 'M_Delivery_Planning_Type', 'TransportDirection'),
       Updated   = TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Val_Rule_ID = 540796
;

-- 2) AD_Process_Para 543277: repoint to AD_Element 585383 (TransportDirection) and rename its
--    ColumnName to match -- the process binds the parameter by
--    I_M_ShipperTransportation.COLUMNNAME_TransportDirection.
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

-- 4) AD_Element 581679: unreferenced after steps 1-3; deactivated, never deleted -- a dictionary
--    row that once shipped stays as history.
UPDATE AD_Element
SET    IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-27 12:00:30','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Element_ID = 581679
;
