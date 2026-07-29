
--
-- 5788030_sys_gh28014_C_Period_Ordered_Asc_reference.sql originally created an AD_reference with ID=542051
-- That ID was "guessed". Now, a later script 5789650_sys_ReceiveUnitType_List.sql dies to insert a different AD_reference with the same ID and fails.
--
-- Fix:
-- 1st: DONE EARLIER: 5788030_sys_gh28014_C_Period_Ordered_Asc_reference to use a legit AD_Reference_ID=542058 instead of 542051 in commit 69e4f9b8
-- 2nd: NOW: add this file to update DBs where the SQL with the wrong ID was already applied     
--

UPDATE AD_Reference SET AD_Reference_ID=542058 WHERE AD_Reference_ID=542051 and not exists (select 1 from AD_Reference where AD_Reference_ID=542058);
UPDATE AD_Reference_Trl SET AD_Reference_ID=542058 WHERE AD_Reference_ID=542051 and not exists (select 1 from AD_Reference_Trl where AD_Reference_ID=542058);
UPDATE AD_Ref_Table SET AD_Reference_ID=542058 WHERE AD_Reference_ID=542051;
UPDATE AD_Process_Para SET AD_Reference_Value_ID=542058 WHERE AD_Reference_Value_ID=542051;
