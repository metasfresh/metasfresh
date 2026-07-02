-- Fix M_Picking_OrderBoard_v AD_Table: CloningEnabled='N' is not a valid value.
-- Valid codes (X_AD_Table.java): E (Enabled), D (Disabled), A (All).
-- A read-only view cannot be cloned; set to 'D'.
UPDATE AD_Table
SET    CloningEnabled = 'D',
       Updated        = TO_TIMESTAMP('2026-07-02 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy      = 100
WHERE  AD_Table_ID = 542622 /*M_Picking_OrderBoard_v, from migration 5809900*/
  AND  CloningEnabled = 'N';
