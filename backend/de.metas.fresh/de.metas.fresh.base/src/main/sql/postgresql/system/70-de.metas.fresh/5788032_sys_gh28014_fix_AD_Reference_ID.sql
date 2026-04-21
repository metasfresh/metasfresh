
--
-- 5788030_sys_gh28014_C_Period_Ordered_Asc_reference.sql originally created an AD_reference with ID=542058
-- That ID was "guessed". Now, a later script 5789650_sys_ReceiveUnitType_List.sql dies to insert a different AD_reference with the same ID and fails.
--
-- Fix:
-- 1st: update 5788030_sys_gh28014_C_Period_Ordered_Asc_reference to use a legit AD_Reference_ID=542086 instead of 542058
-- 2nd: add this file to update DBs where the SQL with the wrong ID was already applied     
--
UPDATE AD_Reference SET AD_Reference_ID=542086 WHERE AD_Reference_ID=542058;
UPDATE AD_Ref_Table SET AD_Reference_ID=542086 WHERE AD_Reference_ID=542058;
UPDATE AD_Process_Para SET AD_Reference_Value_ID=542086 WHERE AD_Reference_Value_ID=542058;
