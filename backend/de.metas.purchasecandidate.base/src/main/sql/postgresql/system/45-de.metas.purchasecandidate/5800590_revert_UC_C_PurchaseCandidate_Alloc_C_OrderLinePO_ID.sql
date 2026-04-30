-- Revert of 5495680_gh4033-app_UC_C_PurchaseCandidate_Alloc_C_OrderLinePO_ID.sql
-- Drops the unique index UC_C_PurchaseCandidate_Alloc_C_OrderLinePO_ID
-- and its AD_Index_Table / AD_Index_Column / AD_Index_Table_Trl metadata.

-- Drop the physical index
DROP INDEX IF EXISTS UC_C_PurchaseCandidate_Alloc_C_OrderLinePO_ID
;

-- Drop the AD_Index_Column metadata (originally AD_Index_Column_ID=540866)
DELETE FROM AD_Index_Column WHERE AD_Index_Table_ID=540441
;

-- Drop the AD_Index_Table translations
DELETE FROM AD_Index_Table_Trl WHERE AD_Index_Table_ID=540441
;

-- Drop the AD_Index_Table metadata (originally AD_Index_Table_ID=540441)
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540441
;
